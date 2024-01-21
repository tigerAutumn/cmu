package cc.newex.dax.asset.rest.controller.outer.v1.common;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.common.util.BeanConvertUtil;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.dto.WithDrawInfoDto;
import cc.newex.dax.asset.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.asset.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.asset.rest.params.WithdrawParam;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.asset.service.UserAssetConfService;
import cc.newex.dax.asset.service.WithdrawAddressService;
import cc.newex.dax.asset.util.DomainUtil;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.wallet.client.WalletClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.exception.UnsupportedBiz;
import cc.newex.wallet.exception.UnsupportedCurrency;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;

/**
 * @author newex-team
 * @data 03/04/2018
 */
@Slf4j
@RestController
@RequestMapping("/v1/asset/{biz}/withdraw")
public class WithdrawController {

    @Autowired
    WithdrawAddressService withdrawAddressService;

    @Autowired
    TransferRecordService transferRecordService;
    @Autowired
    WalletClient walletClient;
    @Autowired
    UserAssetConfService userAssetConfService;
    @Autowired
    private UsersClient usersClient;

    @Autowired
    private AssetCurrencyCompressService currencyCompressService;

    /**
     * @param biz      业务编码
     * @param currency 币种id
     * @description 返回币种的体现额度信息
     * @date 2018/4/18 下午6:39
     */
    @PostMapping("/info/{currency}")
    public ResponseResult withdrawInfo(@PathVariable("biz") final String biz,
                                       @PathVariable("currency") final String currency,
                                       final HttpServletRequest request) {
        try {

            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }
            Integer brokerId = brokerIdResult.getData();
            BizEnum bizEnum = BizEnum.parseBiz(biz);
            final AssetCurrency info = this.currencyCompressService.getCurrency(currency, biz, brokerId);
            final Long userId = this.getUserId(request);
            final WithDrawInfoDto transferMsg = this.userAssetConfService.getTransferMsg(bizEnum, userId, currency, brokerId);
            log.info("transferMsg={}", JSONObject.toJSONString(transferMsg));
            if (info != null) {
                BeanConvertUtil.fillWithdrawInfo(transferMsg, info);
                return ResultUtils.success(transferMsg);
            } else {
                return ResultUtils.failure(BizErrorCodeEnum.INVALID_CURRENCY);
            }
        } catch (final Throwable e) {
            log.error("get withdraw currency info error {} ", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }

    }

    @RetryLimit(type = RetryLimitTypeEnum.WITHDRAW)
    @PostMapping("/{currency}")
    public ResponseResult withdraw(@PathVariable("biz") final String biz,
                                   @PathVariable("currency") final String currency,
                                   @RequestBody @Valid final WithdrawParam withdrawParam,
                                   final HttpServletRequest request) {
        try {

            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }

            Integer brokerId = brokerIdResult.getData();

            final CurrencyEnum coin = CurrencyEnum.parseName(currency);
            final BizEnum bizEnum = BizEnum.parseBiz(biz);
            final Long userId = this.getUserId(request);
            if (ObjectUtils.isEmpty(userId)) {
                return ResultUtils.failure(BizErrorCodeEnum.UNAUTHORIZED);
            }
            //精度为0的币种，amount必须为整数
            if (coin.getDecimal().compareTo(ONE) == 0) {
                final BigDecimal amount = withdrawParam.getAmount();
                //判断是否为整数
                if (new BigDecimal(amount.intValue()).compareTo(amount) != 0) {
                    return ResultUtils.failure(BizErrorCodeEnum.POSITIVE_INTEGER_REQUIRED);
                }
            }

            /**
             * 验证提现地址是否正确
             */
            try {
                final ResponseResult responseResult = this.walletClient.checkAddressValid(coin.getIndex(), withdrawParam.getAddress());
                final JSONObject jsonObject = JSON.parseObject(responseResult.getData() + "");
                if (BooleanUtils.isFalse(jsonObject.getBoolean("valid"))) {
                    log.error("address{}is invalid, currency: {}", withdrawParam.getAddress(),coin.getIndex());
                    return ResultUtils.failure(BizErrorCodeEnum.INVALID_ADDRESS);
                }
            } catch (final Exception e) {
                log.error("WithdrawController checkAddressValid is exception {}", e);
                return ResultUtils.failure(BizErrorCodeEnum.INVALID_ADDRESS);
            }

            /**
             * 校验是否是新的提现地址
             * 1小时内连续提现3笔，第3笔及之后需要出发身份验证
             * 单笔提现估值超过1BTC
             * 校验提现距离上次充值间隔1小时内
             */
            final ResponseResult checkAddressResult = this.withdrawAddressService.checkWithdrawLimit(userId, coin.getIndex(), withdrawParam.getAmount(), withdrawParam.getAddress(),
                    withdrawParam.getCardNumber(), withdrawParam.getEmailCode(), withdrawParam.getGoogleCode(), withdrawParam.getMobileCode(), brokerId);
            if (checkAddressResult.getCode() != 0) {
                log.error("checkAddressResult {}", checkAddressResult.getCode());
                if (checkAddressResult.getCode() == BizErrorCodeEnum.NEED_VALIDATE_IDCARD.getCode()) {
                    return checkAddressResult;
                }
                log.error("user {} use a currency {} new withdraw address {}", userId, coin.getIndex(), withdrawParam.getAddress());
                return ResultUtils.failure(checkAddressResult.getMsg());
            }

            /**
             * 验证可提现余额
             */
            final WithDrawInfoDto transferMsg = this.userAssetConfService.getTransferMsg(bizEnum, userId, currency, brokerId);
            if (withdrawParam.getAmount().compareTo(BigDecimal.ZERO) <= 0
                    || (withdrawParam.getAmount().add(withdrawParam.getFee())).compareTo(transferMsg.getCanWithdraw()) > 0) {
                return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_CONTROL_REFUSE);
            }

            final BigDecimal amount = withdrawParam.getAmount();
            final AssetCurrency info = this.currencyCompressService.getCurrency(currency, biz, brokerIdResult.getData());
            if (amount.compareTo(info.getMinWithdrawAmount()) < 0) {
                return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_AMOUNT_NOT_ENOUGH);
            }

            final BigDecimal fee = info.getWithdrawFee();

            final TransferRecord record = TransferRecord.builder()
                    //.from(biz)
                    .to(withdrawParam.getAddress())
                    .biz(bizEnum.getIndex())
                    .currency(coin.getIndex())
                    .userId(userId)
                    .amount(amount)
                    .fee(fee)
                    .transferType(TransferType.WITHDRAW.getCode())
                    .status((byte) TransferStatus.WAITING.getCode())
                    .brokerId(brokerId)
                    .build();
            final ResponseResult result = this.transferRecordService.createTransferRecord(record);
            if (result.getCode() == 0) {
                return ResultUtils.success();
            } else {
                WithdrawController.log.error("createWithdrawRecord fail");
                return result;
            }

        } catch (final UnsupportedCurrency | UnsupportedBiz e) {
            WithdrawController.log.error("withdraw error", e);
            return ResultUtils.failure(e);
        } catch (final Throwable e) {
            WithdrawController.log.error("withdraw error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }

    }

    private Long getUserId(final HttpServletRequest request) {
        final JwtUserDetails userJwt = JwtTokenUtils.getCurrentLoginUser(request);
        if (userJwt.getStatus() != 0) {
            return null;
        }
        return userJwt.getUserId();
    }
}
