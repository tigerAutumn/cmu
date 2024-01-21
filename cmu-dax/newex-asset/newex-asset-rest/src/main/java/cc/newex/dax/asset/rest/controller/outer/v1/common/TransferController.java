package cc.newex.dax.asset.rest.controller.outer.v1.common;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.dto.PayTokenReqDto;
import cc.newex.dax.asset.rest.params.TransferParam;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.asset.util.DomainUtil;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.exception.UnsupportedBiz;
import cc.newex.wallet.exception.UnsupportedCurrency;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

/**
 * 繳納保證金
 *
 * @author lilaizhen
 */
@Slf4j
@RestController
@RequestMapping("/v1/asset/{biz}/withdraw")
public class TransferController {

    @Autowired
    private UsersClient usersClient;
    @Autowired
    TransferRecordService transferRecordService;
    @Autowired
    private AssetCurrencyCompressService assetCurrencyCompressService;

    @RequestMapping("/payToken")
    public ResponseResult payToken(@PathVariable("biz") String biz, @RequestBody PayTokenReqDto payTokenReqDto,
                                   HttpServletRequest request) {
        try {
            Long userId = this.getUserId(request);

            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }

            return this.transferRecordService.payToken(payTokenReqDto, biz, userId, brokerIdResult.getData());
        } catch (Exception e) {
            log.error("payToken unknow error {}", e);
            return ResultUtils.failure(BizErrorCodeEnum.PAY_TOKEN_FAILURE);
        }

    }

    @RequestMapping("transferSupport")
    public ResponseResult transferSupport(@RequestParam("currency") String currency, @PathVariable("biz") String biz, HttpServletRequest request) {
        CurrencyEnum currencyEnum = CurrencyEnum.parseName(currency);
        List<BizEnum> bizEnums = Arrays.asList(BizEnum.values());
        List<Map<String, Object>> data = Lists.newArrayList();

        String domain = DomainUtil.getDomain(request);
        ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
        if (brokerIdResult.getCode() != 0) {
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
        }

        bizEnums.forEach(bizEnum -> {
            //排除 asset 和 internal perpetual
            if (bizEnum.getName().equals(BizEnum.INTERNAL.getName()) || bizEnum.getName().equals(BizEnum.ASSET.getName()) || bizEnum.getName().equals(BizEnum.PERPETUAL.getName())) {
                return;
            }
            AssetCurrency exist = this.assetCurrencyCompressService.getCurrency(currencyEnum.getName().toUpperCase(), bizEnum.getIndex(), brokerIdResult.getData());
            if (ObjectUtils.isEmpty(exist)) {
                return;
            }
            if (exist.getTransfer() == 0) {
                return;
            }
            if (exist.getOnline() != 1) {
                return;
            }
            Map<String, Object> item = Maps.newHashMap();
            item.put("bizName", bizEnum.getName());
            item.put("bizSwitch", 1);
            data.add(item);
        });
        return ResultUtils.success(data);
    }

    @PostMapping("/transfer/{currency}")
    public ResponseResult transfer(@PathVariable("currency") final String currency, @RequestBody @Valid final TransferParam transferParam,
                                   final HttpServletRequest request) {
        try {
            final CurrencyEnum coin = CurrencyEnum.parseName(currency);
            final BizEnum fromBiz = BizEnum.parseBiz(transferParam.getFromBiz());
            final BizEnum toBiz = BizEnum.parseBiz(transferParam.getToBiz());

            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }

            if (brokerIdResult.getData() != 1) {
                return ResultUtils.failure(BizErrorCodeEnum.BIZ_NOT_SUPPORT);
            }

            final Long userId = this.getUserId(request);
            if (ObjectUtils.isEmpty(userId)) {
                return ResultUtils.failure(BizErrorCodeEnum.UNAUTHORIZED);
            }
            final BigDecimal amount = transferParam.getAmount();
            final TransferRecord record = TransferRecord.builder()
                    .from(fromBiz.getName())
                    .to(toBiz.getName())
                    .biz(fromBiz.getIndex())
                    .currency(coin.getIndex())
                    .userId(userId)
                    .amount(amount)
                    .fee(ZERO)
                    .transferType(TransferType.TRANSFER.getCode())
                    .status((byte) TransferStatus.WAITING.getCode())
                    .brokerId(brokerIdResult.getData())
                    .build();
            final ResponseResult result = this.transferRecordService.createTransferRecord(record);
            if (result.getCode() == 0) {
                return ResultUtils.success();
            } else {
                log.error("createWithdrawRecord fail");
                return result;
            }

        } catch (final UnsupportedCurrency | UnsupportedBiz e) {
            log.error("transfer error", e);
            return ResultUtils.failure(e);
        } catch (final Throwable e) {
            log.error("transfer error ", e);
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
