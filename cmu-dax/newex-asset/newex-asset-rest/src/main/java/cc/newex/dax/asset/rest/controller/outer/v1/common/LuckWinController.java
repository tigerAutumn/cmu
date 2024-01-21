package cc.newex.dax.asset.rest.controller.outer.v1.common;

import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.exception.AssetBizException;
import cc.newex.dax.asset.common.util.BrokerIdUtil;
import cc.newex.dax.asset.dto.LuckWinExchangeVO;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.asset.service.UserAssetConfService;
import cc.newex.wallet.client.WalletClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author newex
 */
@Slf4j
@RestController
@RequestMapping("/v1/asset/lucky-win")
public class LuckWinController {


    @Autowired
    TransferRecordService recordService;
    @Autowired
    UserAssetConfService userAssetConfService;
    @Autowired
    private WalletClient walletClient;
    /**
     * 兑换
     *
     * @return
     */
    @RequestMapping("/exchange")
    public ResponseResult exchange(HttpServletRequest request,
                                   @RequestBody LuckWinExchangeVO luckWinExchangeVO) {

        //关闭兑换功能
        return ResultUtils.failure(BizErrorCodeEnum.TRANSFER_FAIL);
//
//        CurrencyEnum currencyEnum = CurrencyEnum.WIN;
//        BizEnum fromBiz = BizEnum.SPOT;
//        BizEnum toBiz = BizEnum.SPOT;
//        Long userId = this.getUserId(request);
//        ResponseResult<Integer> brokerIdResult = BrokerIdUtil.getBrokerId(request);
//        if (brokerIdResult.getCode() != 0) {
//            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
//        }
//        Integer brokerId = brokerIdResult.getData();
//        String luckyWinAddress = luckWinExchangeVO.getLuckyWinAddress();
//        if (StringUtils.isEmpty(luckyWinAddress)) {
//            return ResultUtils.failure(BizErrorCodeEnum.INVALID_ADDRESS);
//        }
//        ResponseResult result = walletClient.checkAddressValid(CurrencyEnum.LUCKYWIN.getIndex(), luckyWinAddress);
//        JSONObject jsonObject = JSON.parseObject(result.getData() + "");
//        if (BooleanUtils.isFalse(jsonObject.getBoolean("valid"))) {
//            return ResultUtils.failure(BizErrorCodeEnum.INVALID_ADDRESS);
//        }
//        try {
//            return this.recordService.winExchange(currencyEnum, fromBiz, toBiz, userId, luckWinExchangeVO, brokerId);
//        } catch (AssetBizException e) {
//            return ResultUtils.failure(BizErrorCodeEnum.parseByCode(e.getCode()));
//        }
    }

    private Long getUserId(HttpServletRequest request) {
        JwtUserDetails userJwt = JwtTokenUtils.getCurrentLoginUser(request);
        if (userJwt.getStatus() != 0) {
            return null;
        }
        return userJwt.getUserId();
    }
}
