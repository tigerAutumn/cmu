package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.spot.client.SpotOrderClient;
import cc.newex.dax.spot.dto.result.model.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 丁昆
 * @date 2018/6/4
 * @des 订单管理
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/spot/ordermanage")
public class OrderManageController {

    @Autowired
    SpotOrderClient spotOrderClient;

    /**
     * @param pager
     * @param code
     * @param startDateMillis
     * @param endDateMillis
     * @param price
     * @param size
     * @param type
     * @param source
     * @param userId          该字段不能为空
     * @return
     */
    @GetMapping(value = "/order-all")
    @OpLog(name = "所有挂单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_USER_ORDER_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager pager, String code,
                               final String startDateMillis, final String endDateMillis,
                               final BigDecimal price, final BigDecimal size,
                               String type, String source, final Long userId) {
        // 查询全部
        if (StringUtils.isBlank(code)) {
            code = null;
        }

        if (StringUtils.isBlank(type)) {
            type = null;
        }

        if (StringUtils.isBlank(source)) {
            source = null;
        }

        Long sDateMillis = null;
        Long eDateMillis = null;

        if (StringUtils.isNotBlank(startDateMillis)) {
            sDateMillis = DateUtil.getDateFromDateStr(startDateMillis);
        }

        if (StringUtils.isNotBlank(endDateMillis)) {
            eDateMillis = DateUtil.getDateFromDateStr(endDateMillis);
        }

        final ResponseResult<DataGridPagerResult<OrderInfo>> result = this.spotOrderClient.all(pager, code, sDateMillis, eDateMillis,
                type, price, size, source, userId, loginUser.getLoginBrokerId());

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final Long total = result.getData().getTotal();
        final List<OrderInfo> rows = result.getData().getRows();

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total.longValue(), rows);

        return ResultUtils.success(dataGridPagerResult);
    }

    @GetMapping(value = "/finish")
    @OpLog(name = "历史成交")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_USER_HISTORY_VIEW"})
    public ResponseResult orderFinish(@CurrentUser final User loginUser, final DataGridPager pager, final String code,
                                      final String startDateMillis, final String endDateMillis,
                                      final BigDecimal price, final BigDecimal size,
                                      String type, String source, final Boolean isHistory, final Long userId) {

        if (StringUtils.isBlank(type)) {
            type = null;
        }

        if (StringUtils.isBlank(source)) {
            source = null;
        }

        Long sDateMillis = null;
        Long eDateMillis = null;

        if (StringUtils.isNotBlank(startDateMillis)) {
            sDateMillis = DateUtil.getDateFromDateStr(startDateMillis);
        }

        if (StringUtils.isNotBlank(endDateMillis)) {
            eDateMillis = DateUtil.getDateFromDateStr(endDateMillis);
        }
        final ResponseResult<DataGridPagerResult<OrderInfo>> result = this.spotOrderClient.orderFinish(pager, code, sDateMillis, eDateMillis,
                type, price, size, source, isHistory, userId, loginUser.getLoginBrokerId());

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }
        return ResultUtil.getCheckedResponseResult(result);
    }

}
