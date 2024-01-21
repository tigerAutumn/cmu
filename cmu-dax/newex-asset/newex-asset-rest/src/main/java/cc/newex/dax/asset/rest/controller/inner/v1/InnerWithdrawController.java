package cc.newex.dax.asset.rest.controller.inner.v1;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.exception.AssetBizException;
import cc.newex.dax.asset.common.util.TradeNoUtil;
import cc.newex.dax.asset.dto.WithDrawInfoDto;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.UserAssetConfService;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.exception.UnsupportedCurrency;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * @author lilaizhen
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/asset")
public class InnerWithdrawController {
    @Autowired
    UserAssetConfService userAssetConfService;
    @Autowired
    private AssetCurrencyCompressService currencyCompressService;
    private static final long ONE_DAY = 24 * 60 * 60;

    @RequestMapping("getWithdrawLimit")
    public ResponseResult<WithDrawInfoDto> getWithdrawLimit(@RequestParam("userId") Long userId,
                                                            @RequestParam("currencyId") Integer currencyId,
                                                            @RequestParam("brokerId") Integer brokerId,
                                                            @RequestParam(value = "biz", defaultValue = "spot", required = false) String biz) {
        try {
            CurrencyEnum currencyEnum = CurrencyEnum.parseValue(currencyId);
            WithDrawInfoDto transferMsg = userAssetConfService.getTransferMsg(BizEnum.parseBiz(biz), userId, currencyEnum.getName(), brokerId);
            return ResultUtils.success(transferMsg);
        } catch (UnsupportedCurrency e) {
            return ResultUtils.failure(BizErrorCodeEnum.INVALID_CURRENCY);
        } catch (AssetBizException e) {
            log.error("unknow error {} ", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @RequestMapping("updateCanWithdrawNumber")
    public ResponseResult updateCanWithdrawNumber(@RequestParam("userId") Long userId,
                                                  @RequestParam("currencyId") Integer currencyId,
                                                  @RequestParam("brokerId") Integer brokerId,
                                                  @RequestParam("amount") BigDecimal amount) {
        String key = MessageFormat.format(RedisKeyCons.ASSET_RECORD_WITHDRAW_KEY_PRE_NEW, userId, brokerId);
        JSONObject content = new JSONObject();
        content.put("transferType", TransferType.WITHDRAW.getCode());
        content.put("currency", currencyId);
        content.put("amountBTC", amount.multiply(this.currencyCompressService.coinConverseBTCRate(currencyId, null)));
        content.put("traderNo", TradeNoUtil.getTradeNo());
        REDIS.zAdd(key, System.currentTimeMillis(), content.toJSONString());
        REDIS.expire(key, ONE_DAY);
        return ResultUtils.success();
    }
}
