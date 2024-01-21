package cc.newex.dax.boss.web.controller.outer.v1.c2c;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.annotation.SiteUserId;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.c2c.client.C2CCurrencyAdminClient;
import cc.newex.dax.c2c.client.C2COrderAdminClient;
import cc.newex.dax.c2c.client.C2CUserAdminClient;
import cc.newex.dax.c2c.dto.admin.*;
import cc.newex.dax.c2c.dto.common.PagedList;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.membership.UserFiatResDTO;
import cc.newex.dax.users.dto.security.UserFiatSettingDTO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author newex-jinlong
 * @date 2018-04-28
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/c2c/order")
public class OrderController {

    @Autowired
    private C2COrderAdminClient c2cClient;

    @Autowired
    private UsersClient usersClient;

    @Autowired
    private C2CCurrencyAdminClient c2cCurrencyClient;

    @Autowired
    private C2CUserAdminClient c2cUsersAdminClient;

    /**
     * 获取C2C所有的币种信息
     */
    @RequestMapping(value = "/currencies")
    @OpLog(name = "获取C2C所有的币种信息")
    public List<CurrencyConfigDTO> getAllCurrencies(final HttpServletRequest request, @RequestParam(value = "type", required = false) final Integer type) {
        final ResponseResult<List<CurrencyConfigDTO>> result = this.c2cCurrencyClient.getCurrencyList(type);

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return Lists.newArrayListWithCapacity(0);
        }

        return result.getData();
    }

    /**
     * 根据ID获取用户银行卡信息
     */
    @RequestMapping(value = "/getSellInfo")
    @OpLog(name = "根据ID获取用户银行卡信息")
    public List<UserFiatSettingDTO> getSellInfo(@RequestParam(value = "sellerId", required = false) final Integer sellerId) {
        try {
            final ResponseResult<List<UserFiatSettingDTO>> result = this.usersClient.getUserFiatSettingList(sellerId);

            if (result != null && result.getCode() == 0) {
                return result.getData();
            } else {
                log.error("get getSellInfo error code:" + result.getCode() + "msg:" + result.getMsg() + "data:" + result.getData());
            }

        } catch (final Exception e) {
            log.error("get getSellInfo api error", e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取订单统计
     */
    @RequestMapping(value = "/getOrderStatistics")
    @OpLog(name = "获取订单统计")
    public ResponseResult getOrderStatistics() {
        try {
            final ResponseResult<OrderStatisticsDTO> orderStatistics = this.c2cClient.getOrderStatistics();
            if (orderStatistics != null && orderStatistics.getCode() == 0) {
                return ResultUtils.success(orderStatistics.getData());
            } else {
                log.error("get getOrderStatistics error msg:" + orderStatistics.getMsg() + "code:" + orderStatistics.getCode() + "data:" + orderStatistics.getData());
            }

        } catch (final Exception e) {
            log.error("get getOrderStatistics error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
        return ResultUtils.success();
    }

    /**
     * 根据ID获取用户信息
     */
    @RequestMapping(value = "/getUserInfo")
    @OpLog(name = "根据ID获取用户信息")
    public ResponseResult getUserInfo(@RequestParam(value = "UserId", required = false) final Integer UserId) {
        try {
            final ResponseResult<UserFiatResDTO> userFiatRes = this.usersClient.getUserFiatRes(UserId);

            if (userFiatRes != null && userFiatRes.getCode() == 0) {
                return ResultUtils.success(userFiatRes.getData());
            } else {
                log.error("get getUserInfo error code:" + userFiatRes.getCode() + "msg:" + userFiatRes.getMsg() + "data:" + userFiatRes.getData());
            }

        } catch (final Exception e) {
            log.error("get getUserInfo api error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
        return ResultUtils.success();
    }

    @GetMapping(value = "/List")
    @OpLog(name = "订单列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_ORDER_VIEW"})
    public ResponseResult list(final DataGridPager pager
            , @RequestParam(value = "orderId", required = false) final Long orderId
            , @RequestParam(value = "buy", required = false) final Long buy
            , @RequestParam(value = "sell", required = false) final Long sell
            , @RequestParam(value = "digitalCurrencySymbol", required = false) final String digitalCurrencySymbol
            , @RequestParam(value = "legalCurrencySymbol", required = false) final String legalCurrencySymbol
            , @RequestParam(value = "paymentType", required = false) final Integer paymentType
            , @RequestParam(value = "orderStatus", required = false) final Integer orderStatus
            , @RequestParam(value = "paymentStatus", required = false) final Integer paymentStatus
            , @RequestParam(value = "freezeType", required = false) final Integer freezeType
            , @RequestParam(value = "beginTime", required = false) final String beginTime
            , @RequestParam(value = "endTime", required = false) final String endTime) {
        try {

            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newBeginTime = null;
            Date newEndTime = null;
            try {
                if (beginTime != null) {
                    newBeginTime = sdf.parse(beginTime);
                }
                if (endTime != null) {
                    newEndTime = sdf.parse(endTime);
                }
            } catch (final Exception e) {
                log.error(" beginTime or endTime type error");
            }
            final OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
            orderQueryDTO.setPublicOrderId(orderId);
            orderQueryDTO.setBuyerId(buy);
            orderQueryDTO.setSellerId(sell);
            orderQueryDTO.setDigitalCurrencySymbol(digitalCurrencySymbol);
            orderQueryDTO.setLegalCurrencySymbol(legalCurrencySymbol);
            orderQueryDTO.setPaymentType(paymentType);
            orderQueryDTO.setOrderStatus(orderStatus);
            orderQueryDTO.setPaymentStatus(paymentStatus);
            orderQueryDTO.setStartTime(newBeginTime);
            orderQueryDTO.setEndTime(newEndTime);
            if (freezeType != null) {
                if (freezeType == 1) {
                    orderQueryDTO.setIsFrozen(true);
                }
                if (freezeType == 0) {
                    orderQueryDTO.setIsFrozen(false);
                }
            }
            orderQueryDTO.setPage(pager.getPage());
            orderQueryDTO.setPageSize(pager.getRows());
            final ResponseResult<PagedList<OrderDTO>> list = this.c2cClient.getList(orderQueryDTO);
            final Map<String, Object> modelMap = new HashMap<>(2);
            if (list != null && list.getCode() == 0) {
                final List<OrderDTO> items = list.getData().getItems();
                modelMap.put("total", list.getData().getTotal());
                modelMap.put("rows", items);
                return ResultUtils.success(modelMap);
            }
            return ResultUtils.failure(list.getMsg());

        } catch (final Exception e) {
            log.error("order list error", e);
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
    @OpLog(name = "根据订单ID查询订单详情")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_ORDER_VIEW"})
    public ResponseResult getOrderDetail(@RequestParam(value = "orderId", required = false) final Long orderId) {

        try {
            final ResponseResult<OrderDetailDTO> orderDetail = this.c2cClient.getOrderDetail(orderId);
            if (orderDetail != null && orderDetail.getCode() == 0) {
                return ResultUtils.success(orderDetail.getData());
            }
            return ResultUtils.failure(BizErrorCodeEnum.QUERY_DETAILS);
        } catch (final Exception e) {
            log.error("get getOrderDetail api error ", e);
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/remark", method = RequestMethod.POST)
    @OpLog(name = "法币订单添加备注")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_ORDER_REMARK"})
    public ResponseResult addRemarks(final HttpServletRequest request) throws IOException {

        try {
            request.setCharacterEncoding("UTF-8");
            final String remarks = request.getParameter("remarks");
            final String orderId = request.getParameter("orderId");
            final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
            final ManagerInfo managerinfo = ManagerInfo.builder()
                    .note(remarks)
                    .userId(Long.valueOf(user.getId()))
                    .userName(user.getAccount()).
                            build();

            final ResponseResult<Boolean> result = this.c2cClient.orderRemark(Long.valueOf(orderId), managerinfo);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("c2c add remark is error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UPDATE_ERROR);
        }

    }

    @RequestMapping(value = "/freeze", method = RequestMethod.POST)
    @OpLog(name = "法币订单冻结")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_ORDER_FREEZE"})
    public ResponseResult freeze(final HttpServletRequest request) {

        try {
            final String orderId = request.getParameter("orderId");
            final String type = request.getParameter("type");
            final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

            final ManagerInfo managerinfo = ManagerInfo.builder()
                    .userId(Long.valueOf(user.getId()))
                    .note(BizErrorCodeEnum.REMARKS_FREEZE.getMessage())
                    .userName(user.getAccount()).
                            build();

            final boolean state = true;
            if (StringUtils.isEmpty(type)) {
                return ResultUtils.failure(BizErrorCodeEnum.ILLEGAL_OPERATION);
            }
            //2冻结
            if (!type.equalsIgnoreCase("2")) {
                return ResultUtils.failure(BizErrorCodeEnum.ILLEGAL_OPERATION);
            }

            final ResponseResult<Boolean> result = this.c2cClient.freeze(Long.valueOf(orderId), state, managerinfo);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("c2c freeze  is error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UPDATE_ERROR);
        }

    }

    @RequestMapping(value = "/unfreeze", method = RequestMethod.POST)
    @OpLog(name = "法币订单解冻")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_ORDER_UNFREEZE"})
    public ResponseResult unFreeze(final HttpServletRequest request) {

        try {
            final String orderId = request.getParameter("orderId");
            final String type = request.getParameter("type");
            final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
            final ManagerInfo managerinfo = ManagerInfo.builder()
                    .userId(Long.valueOf(user.getId()))
                    .note(BizErrorCodeEnum.REMARKS_UNFREEZE.getMessage())
                    .userName(user.getAccount()).
                            build();

            final boolean state = false;
            if (StringUtils.isEmpty(type)) {
                return ResultUtils.failure(BizErrorCodeEnum.ILLEGAL_OPERATION);
            }
            //1解冻
            if (!type.equalsIgnoreCase("1")) {
                return ResultUtils.failure(BizErrorCodeEnum.ILLEGAL_OPERATION);
            }

            final ResponseResult<Boolean> result = this.c2cClient.freeze(Long.valueOf(orderId), state, managerinfo);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("c2c unfreeze  is error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UPDATE_ERROR);
        }

    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @OpLog(name = "法币订单取消")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_ORDER_CANCEL"})
    public ResponseResult cancel(final HttpServletRequest request) {

        try {
            final String orderId = request.getParameter("orderId");
            final String type = request.getParameter("type");
            final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
            final ManagerInfo managerinfo = ManagerInfo.builder()
                    .userId(Long.valueOf(user.getId()))
                    .note(BizErrorCodeEnum.REMARKS_CANCEL.getMessage())
                    .userName(user.getAccount()).
                            build();

            if (StringUtils.isEmpty(type)) {
                return ResultUtils.failure(BizErrorCodeEnum.ILLEGAL_OPERATION);
            }
            //3 取消订单
            if (!type.equalsIgnoreCase("3")) {
                return ResultUtils.failure(BizErrorCodeEnum.ILLEGAL_OPERATION);
            }
            //后端状态为5，客服取消
            final Integer state = 5;

            final ResponseResult<Boolean> result = this.c2cClient.cancel(Long.valueOf(orderId), state, managerinfo);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("c2c cancel  is error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UPDATE_ERROR);
        }

    }

    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    @OpLog(name = "法币订单完成")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_ORDER_FINISH"})
    public ResponseResult finish(final HttpServletRequest request) {

        try {
            final String orderId = request.getParameter("orderId");
            final String type = request.getParameter("type");
            final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
            final ManagerInfo managerinfo = ManagerInfo.builder()
                    .userId(Long.valueOf(user.getId()))
                    .note(BizErrorCodeEnum.REMARKS_FINISH.getMessage())
                    .userName(user.getAccount()).
                            build();

            if (StringUtils.isEmpty(type)) {
                return ResultUtils.failure(BizErrorCodeEnum.ILLEGAL_OPERATION);
            }
            //4 完成
            if (!type.equalsIgnoreCase("4")) {
                return ResultUtils.failure(BizErrorCodeEnum.ILLEGAL_OPERATION);
            }
            //后端状态为2，客服手动完成
            final Integer state = 2;

            final ResponseResult<Boolean> result = this.c2cClient.cancel(Long.valueOf(orderId), state, managerinfo);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("c2c finish  is error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UPDATE_ERROR);
        }

    }

    /**
     * 根据ID获取用户信息
     */
    @RequestMapping(value = "/getC2cUserInfo")
    @OpLog(name = "根据ID获取用户信息")
    public ResponseResult getC2cUserInfo(@RequestParam(value = "userId", required = false) final Long userId) {
        try {
            final ResponseResult<UserDTO> userFiatRes = this.c2cUsersAdminClient.getUser(userId);
            if (userFiatRes != null && userFiatRes.getCode() == 0) {
                return ResultUtils.success(userFiatRes.getData());
            } else {
                assert userFiatRes != null;
                log.error("get getC2cUserInfo error code:" + userFiatRes.getCode() + "msg:" + userFiatRes.getMsg() + "data:" + userFiatRes.getData());
            }

        } catch (final Exception e) {
            log.error("get getC2cUserInfo api error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
        return ResultUtils.success();
    }

    /**
     * 冻结C2C功能
     */
    @RequestMapping(value = "/c2cUserFreeze", method = RequestMethod.POST)
    @OpLog(name = "冻结当前用户C2C法币功能")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_USERS_FREEZE"})
    public ResponseResult c2cUserFreeze(@RequestParam(value = "userId", required = false) @SiteUserId final Long userId,
                                        @RequestParam(value = "disabled", required = false) final String disabled) {
        try {
            final UserDTO userdto = new UserDTO();
            userdto.setUserId(userId);
            userdto.setDisabled(true);
            final ResponseResult<Boolean> booleanResponseResult = this.c2cUsersAdminClient.updateUser(userId, userdto);
            if (booleanResponseResult.getData()) {
                return ResultUtils.success();
            } else {
                log.error("get c2cUserFreeze error");
            }

        } catch (final Exception e) {
            log.error("get c2cUserFreeze api error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
        return ResultUtils.success();
    }

    /**
     * 解冻C2C功能
     */
    @RequestMapping(value = "/c2cUserUpFreeze", method = RequestMethod.POST)
    @OpLog(name = "解冻当前用户C2C法币功能")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_USERS_UPFREEZE"})
    public ResponseResult c2cUserUpFreeze(@RequestParam(value = "userId", required = false) @SiteUserId final Long userId) {
        try {
            final UserDTO userdto = new UserDTO();
            userdto.setUserId(userId);
            userdto.setDisabled(false);
            final ResponseResult<Boolean> booleanResponseResult = this.c2cUsersAdminClient.updateUser(userId, userdto);
            if (booleanResponseResult.getData()) {
                return ResultUtils.success();
            } else {
                log.error("get c2cUserFreeze error");
            }

        } catch (final Exception e) {
            log.error("get c2cUserFreeze api error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
        return ResultUtils.success();
    }

    /**
     * 开通用户C2C商户功能
     */
    @RequestMapping(value = "/c2cBusinessUpFreeze", method = RequestMethod.POST)
    @OpLog(name = "开通用户C2C商户功能")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_BUSINESS_UPFREEZE"})
    public ResponseResult c2cBusinessUpFreeze(@RequestParam(value = "userId", required = false) @SiteUserId final Long userId) {
        try {
            final UserDTO userdto = new UserDTO();
            userdto.setUserId(userId);
            userdto.setIsCertifiedUser(true);
            final ResponseResult<Boolean> booleanResponseResult = this.c2cUsersAdminClient.updateUser(userId, userdto);
            if (booleanResponseResult.getData()) {
                return ResultUtils.success();
            } else {
                log.error("get c2cUserFreeze error");
            }

        } catch (final Exception e) {
            log.error("get c2cUserFreeze api error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
        return ResultUtils.success();
    }

    /**
     * 取消用户C2C商户功能
     */
    @RequestMapping(value = "/c2cBusinessFreeze", method = RequestMethod.POST)
    @OpLog(name = "取消用户C2C商户功能")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_BUSINESS_FREEZE"})
    public ResponseResult c2cBusinessFreeze(@RequestParam(value = "userId", required = false) @SiteUserId final Long userId) {
        try {
            final UserDTO userdto = new UserDTO();
            userdto.setUserId(userId);
            userdto.setIsCertifiedUser(false);
            final ResponseResult<Boolean> booleanResponseResult = this.c2cUsersAdminClient.updateUser(userId, userdto);
            if (booleanResponseResult.getData()) {
                return ResultUtils.success();
            } else {
                log.error("get c2cBusinessFreeze error");
            }

        } catch (final Exception e) {
            log.error("get c2cBusinessFreeze api error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
        return ResultUtils.success();
    }

}
