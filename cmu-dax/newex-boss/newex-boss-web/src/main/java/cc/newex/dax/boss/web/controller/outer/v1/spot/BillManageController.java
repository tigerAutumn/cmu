package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.spot.client.SpotBillClient;
import cc.newex.dax.spot.client.SpotCurrencyPairServiceClient;
import cc.newex.dax.spot.dto.ccex.CurrencyPairBaseInfoDTO;
import cc.newex.dax.spot.dto.ccex.UserBillExtendDTO;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 丁昆
 * @date 2018/6/4
 * @des 账单管理
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/spot/billmanage")
public class BillManageController {

    @Autowired
    SpotBillClient spotBillClient;

    @Autowired
    private SpotCurrencyPairServiceClient spotClient;

    @Autowired
    private AdminServiceClient adminServiceClient;

    /**
     * @param pager
     * @param currencyCode       币种
     * @param pairCode
     * @param startDateMillis    开始时间
     * @param endDateMillis      结束时间
     * @param billType           账单类型
     * @param userId
     * @param isHiisHistorystory
     * @return
     */
    @GetMapping(value = "/bill-all")
    @OpLog(name = "获取币币账单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_USER_BILL_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager pager,
                               @RequestParam(value = "userId", required = false) final Long userId,
                               @RequestParam(value = "pairCode", required = false) final String pairCode,
                               @RequestParam(value = "currencyCode", required = false) String currencyCode,
                               @RequestParam(value = "startDateMillis", required = false) final String startDateMillis,
                               @RequestParam(value = "endDateMillis", required = false) final String endDateMillis,
                               @RequestParam(value = "billType", required = false) String billType,
                               @RequestParam(value = "isHiisHistorystory", required = false) final Boolean isHiisHistorystory) {

        Long sDateMillis = null;
        Long eDateMillis = null;
        if (StringUtils.isNotBlank(startDateMillis)) {
            sDateMillis = DateUtil.getDateFromDateStr(startDateMillis);
        }
        if (StringUtils.isNotBlank(endDateMillis)) {
            eDateMillis = DateUtil.getDateFromDateStr(endDateMillis);
        }

        if (StringUtils.isNotBlank(currencyCode)) {
            currencyCode = currencyCode.toLowerCase();
        }

        // 查询全部账单类型
        if (StringUtils.isBlank(billType)) {
            billType = null;
        }

        final ResponseResult<DataGridPagerResult<UserBillExtendDTO>> result = this.spotBillClient.all(pager, currencyCode, pairCode, sDateMillis, eDateMillis, billType, userId,
                isHiisHistorystory, loginUser.getLoginBrokerId());
        return ResultUtil.getCheckedResponseResult(result);

    }

    @GetMapping(value = "/getType")
    @OpLog(name = "获取账单类型")
    public ResponseResult getType() {
        try {
            final ResponseResult<JSONArray> result = this.spotBillClient.billType();
            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error(" billManager getType  error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @GetMapping(value = "/getCurrencyCode")
    @OpLog(name = "获取币对")
    public ResponseResult getCurrencyCode() {
        final ResponseResult<List<CurrencyPairBaseInfoDTO>> responseResult = this.spotClient.getCurrencyPairs();

        if (responseResult == null || responseResult.getCode() != 0) {
            return ResultUtils.success();
        }

        final List<CurrencyPairBaseInfoDTO> currencyPairBaseInfoDTOList = responseResult.getData();
        if (CollectionUtils.isEmpty(currencyPairBaseInfoDTOList)) {
            return ResultUtils.success();
        }

        final List<Map<String, String>> codeList = new ArrayList<>();

        currencyPairBaseInfoDTOList.forEach(currencyPair -> {
            final String code = currencyPair.getCode().toUpperCase();

            final Map<String, String> map = new HashMap<>();
            map.put("id", currencyPair.getId().toString());
            map.put("code", code);

            codeList.add(map);
        });

        return ResultUtils.success(codeList);
    }

    @GetMapping(value = "/getadress")
    @OpLog(name = "获取转账地址")
    public ResponseResult getAdress(final String traderNo) {
        try {
            final ResponseResult result = this.adminServiceClient.queryTransferRecord(traderNo);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error(" billManager getAdress  error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }
}
