package cc.newex.dax.asset.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.rest.params.SignDataDto;
import cc.newex.dax.asset.service.SignDataService;
import cc.newex.dax.asset.service.WithdrawAddressService;
import cc.newex.wallet.client.WalletClient;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.WithdrawTransaction;
import lombok.extern.slf4j.Slf4j;
import org.bitcoincashj.core.ECKey;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import sdk.bitcoincashj.bip.Bip32Node;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author newex-team
 * @data 2018/5/4
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/asset")
public class InnerSigController {


    @Autowired
    WithdrawAddressService withdrawAddressService;
    @Autowired
    WalletClient walletClient;
    @Value("${newex.net.pubKey}")
    private String signKeyPub;
    private ECKey signKey;
    @Autowired
    private SignDataService signDataService;

    @PostConstruct
    public void init() {
        this.signKey = Bip32Node.decode(this.signKeyPub).getEcKey();
    }

    @GetMapping("/sig")
    ResponseResult<?> getSig(@RequestParam("time") final Long time, @RequestParam("signature") final String signature) {

        try {
            if (this.checkRequestValid(time, signature)) {
                final List<String> txs = this.signDataService.getWithdrawTx();
                return ResultUtils.success(txs);
            } else {
                return ResultUtils.failure("request is valid");
            }

        } catch (final Throwable e) {
            log.error("getSig error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @PostMapping("/sig")
    ResponseResult<?> postSig(@RequestBody final SignDataDto<WithdrawTransaction> signDataDto) {
        try {
            if (ObjectUtils.isEmpty(signDataDto)) {
                return ResultUtils.failure("data is null");
            }

            if (this.checkRequestValid(signDataDto.getTime(), signDataDto.getSignature())) {
                final boolean success = this.signDataService.postWithdrawTx(signDataDto.getSignData());
                if (success) {
                    return ResultUtils.success();
                } else {
                    return ResultUtils.failure("postSig fail");
                }
            } else {
                return ResultUtils.failure("request is valid");

            }

        } catch (final Throwable e) {
            InnerSigController.log.error("postSig error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 主要针对一些没有公钥或者不能通过公钥生成地址的币种（比如iota）
     * 检查一些币种是否缺少地址。
     *
     * @param time
     * @param signature
     * @return
     */
    @GetMapping("/sig/address/{currency}")
    ResponseResult<?> getAddressCount(@PathVariable("currency") String currency, @RequestParam("time") final Long time, @RequestParam("signature") final String signature) {

        try {
            if (this.checkRequestValid(time, signature)) {
                CurrencyEnum currencyEnum = CurrencyEnum.parseName(currency);
                return this.walletClient.getNeedAddressCount(currencyEnum.getIndex());
            } else {
                return ResultUtils.failure("request is valid");
            }

        } catch (final Throwable e) {
            log.error("getSig error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 如果币种缺少地址，则离线生成之后，通过这个方法上传
     *
     * @param signDataDto
     * @return
     */
    @PostMapping("/sig/address/{currency}")
    ResponseResult<?> postNeedAddress(@PathVariable("currency") String currency, @RequestBody final SignDataDto<String> signDataDto) {
        try {
            if (ObjectUtils.isEmpty(signDataDto)) {
                return ResultUtils.failure("data is null");
            }

            if (this.checkRequestValid(signDataDto.getTime(), signDataDto.getSignature())) {
                CurrencyEnum currencyEnum = CurrencyEnum.parseName(currency);
                return this.walletClient.postNeedAddress(currencyEnum.getIndex(), signDataDto.getSignData());

            } else {
                return ResultUtils.failure("request is valid");

            }

        } catch (final Throwable e) {
            InnerSigController.log.error("postNeedAddress error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    private boolean checkRequestValid(Long time, String signature) {
        try {
            final Long fiveMinutes = 5 * 60 * 1000L;
            final Long now = System.currentTimeMillis();
            if (Math.abs(now - time) > fiveMinutes) {
                return false;
            }
            this.signKey.verifyMessage(time.toString(), new String(Hex.decode(signature)));
            return true;
        } catch (Throwable e) {
            log.error("checkRequestValid error,time:{},signature:{}", time, signature);
            return false;
        }
    }


}
