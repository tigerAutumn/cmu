package cc.newex.dax.boss.web.controller.outer.v1.c2c;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.c2c.client.C2COrderAdminClient;
import cc.newex.dax.c2c.client.C2CTradingOrderAdminClient;
import cc.newex.dax.c2c.client.C2CUserBalanceAdminClient;
import cc.newex.dax.c2c.client.C2CUserBillAdminClient;
import cc.newex.dax.c2c.dto.admin.OrderDTO;
import cc.newex.dax.c2c.dto.admin.OrderQueryDTO;
import cc.newex.dax.c2c.dto.admin.UserBalanceDTO;
import cc.newex.dax.c2c.dto.admin.UserBillDTO;
import cc.newex.dax.c2c.dto.admin.UserBillQueryDTO;
import cc.newex.dax.c2c.dto.common.PagedList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/c2c/user-currency")
public class UserCurrencyController {

    @Autowired
    private C2CUserBalanceAdminClient c2CUserBalanceAdminClient;

    @Autowired
    private C2CTradingOrderAdminClient c2CTradingOrderAdminClient;

    @Autowired
    private C2COrderAdminClient c2COrderAdminClient;

    @Autowired
    private C2CUserBillAdminClient c2CUserBillAdminClient;

    @Autowired
    private AdminServiceClient adminServiceClient;

    @RequestMapping(value = "{userId}/order", method = RequestMethod.GET)
    @OpLog(name = "法币订单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_USER_ORDER_VIEW"})
    public ResponseResult order(final DataGridPager pager, @PathVariable(value = "userId") final Long userId,
                                @RequestParam(value = "orderId", required = false) final Long orderId,
                                @RequestParam(value = "payType", required = false) final Integer payType,
                                @RequestParam(value = "currency", required = false) final String currency) {

        final OrderQueryDTO.OrderQueryDTOBuilder builder = OrderQueryDTO.builder();

        builder.tradingUserId(userId).page(pager.getPage()).pageSize(pager.getRows());

        if (orderId != null) {
            builder.publicOrderId(orderId);
        }

        if (payType != null && payType >= 0) {
            builder.paymentType(payType);
        }

        if (StringUtils.isNotBlank(currency)) {
            builder.digitalCurrencySymbol(currency);
        }

        final OrderQueryDTO orderQueryDTO = builder.build();

        final ResponseResult<PagedList<OrderDTO>> result = this.c2COrderAdminClient.getList(orderQueryDTO);

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final Long total = result.getData().getTotal();
        final List<OrderDTO> rows = result.getData().getItems();

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total, rows);

        return ResultUtils.success(dataGridPagerResult);
    }

    @RequestMapping(value = "/{userId}/userBill", method = RequestMethod.GET)
    @OpLog(name = "法币账单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_USER_BILL_VIEW"})
    public ResponseResult account(final DataGridPager pager, @PathVariable(value = "userId") final Long userId,
                                  @RequestParam(value = "orderId", required = false) final Long orderId,
                                  @RequestParam(value = "currency", required = false) final Long currency,
                                  @RequestParam(value = "legalTender", required = false) final Integer legalTender) {

        final UserBillQueryDTO.UserBillQueryDTOBuilder builder = UserBillQueryDTO.builder();

        builder.userId(userId).page(pager.getPage()).pageSize(pager.getRows());

        if (orderId != null) {
            builder.orderId(orderId);
        }

        if (currency != null && currency >= 0L) {
            builder.currencyId(currency);
        }

        if (legalTender != null && legalTender >= 0) {
            builder.type(legalTender);
        }

        final UserBillQueryDTO userBillQueryDTO = builder.build();

        final ResponseResult<PagedList<UserBillDTO>> result = this.c2CUserBillAdminClient.getUserList(userBillQueryDTO);

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final Long total = result.getData().getTotal();
        final List<UserBillDTO> rows = result.getData().getItems();

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total, rows);

        return ResultUtils.success(dataGridPagerResult);
    }

    @RequestMapping(value = "/{userId}/info", method = RequestMethod.GET)
    @OpLog(name = "法币资产")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_USER_BALANCE_VIEW"})
    public ResponseResult info(final DataGridPager pager, @PathVariable(value = "userId") final Long userId) {
        final ResponseResult<List<UserBalanceDTO>> result = this.c2CUserBalanceAdminClient.userBalance(userId);

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final List<UserBalanceDTO> rows = result.getData();
        final Integer total = rows.size();

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total.longValue(), rows);

        return ResultUtils.success(dataGridPagerResult);
    }

    @RequestMapping(value = "/{tradeNo}/bill", method = RequestMethod.GET)
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_CURRENCY_VIEW"})
    public ResponseResult billInfo(@PathVariable(value = "tradeNo", required = false) final String tradeNo) {
        if (tradeNo != null) {
            final ResponseResult result = this.adminServiceClient.queryTransferRecord(tradeNo);
            return ResultUtil.getCheckedResponseResult(result);
        }

        return ResultUtils.success();
    }

}
