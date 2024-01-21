package cc.newex.dax.perpetual.openapi.controller;

import cc.newex.commons.openapi.specs.annotation.OpenApi;
import cc.newex.commons.openapi.specs.annotation.OpenApiAuthValidator;
import cc.newex.commons.openapi.specs.annotation.OpenApiRateLimit;
import cc.newex.commons.openapi.specs.consts.PermissionType;
import cc.newex.commons.openapi.specs.model.OpenApiUserInfo;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.common.enums.V1ErrorCodeEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.dto.OrderBookDTO;
import cc.newex.dax.perpetual.dto.OrderDTO;
import cc.newex.dax.perpetual.openapi.controller.base.BaseController;
import cc.newex.dax.perpetual.openapi.support.aop.PerpetualOpenApiException;
import cc.newex.dax.perpetual.openapi.support.common.AuthenticationUtils;
import cc.newex.dax.perpetual.openapi.support.common.CheckPermission;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.util.ContractSizeUtil;
import cc.newex.dax.perpetual.util.ObjectCopyUtil;
import cc.newex.dax.perpetual.util.WebUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * V1 open api order 相关接口
 *
 * @author newex-team
 * @date 2018/11/01
 */
@Slf4j
@OpenApi
@RestController
@RequestMapping("/api/v1/perpetual/products")
public class OrderController extends BaseController {

    @Autowired
    private OrderShardingService orderShardingService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private AuthenticationUtils authenticationUtils;

    @Autowired
    private UserPositionService userPositionService;

    /**
     * 获取订单列表
     *
     * @param contractCode
     * @return
     */
    @GetMapping("/{contractCode}/list")
    @OpenApiRateLimit(value = "10/1")
    @OpenApiAuthValidator(method = HttpMethod.GET)
    public List<OrderBookDTO> list(@PathVariable("contractCode") final String contractCode,
                                   final HttpServletRequest request, final HttpServletResponse response) {

        final OpenApiUserInfo openApiUserInfo = this.authenticationUtils.getUserInfo(request);

        final Long userId = openApiUserInfo.getId();
        final Integer brokerId = this.getBrokerId();
        /**
         * 订单列表
         */
        final List<Order> list = this.orderShardingService.queryOrderTypeList(userId,
                brokerId, contractCode, null);
        final List<OrderBookDTO> bookList = Lists.newArrayList();
        final Contract contract = this.contractService.getContract(contractCode);
        list.stream().forEach(x -> {
            final OrderBookDTO bookDTO = ObjectCopyUtil.map(x, OrderBookDTO.class);
            bookDTO.setId(x.getOrderId());
            bookDTO.setOrderSize(this.serializer(ContractSizeUtil.countSize(contract, x.getAmount(), x.getPrice())));
            bookList.add(bookDTO);
        });

        return bookList;
    }

    /**
     * 下单
     *
     * @param contractCode
     * @param orderDTO
     * @param request
     * @return
     */
    @PostMapping("/{contractCode}/order")
    @OpenApiRateLimit(value = "10/1")
    @OpenApiAuthValidator(method = HttpMethod.POST, availableAtFrozen = false)
    public Map order(@PathVariable("contractCode") final String contractCode,
                     @RequestBody final OrderDTO orderDTO, final HttpServletRequest request) {
        final OpenApiUserInfo openApiUserInfo = this.authenticationUtils.getUserInfo(request);
        final Map resultMap = Maps.newHashMap();
        if (Objects.isNull(openApiUserInfo)) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Partner_not_exist);
        }
        if (openApiUserInfo.getPerpetualProtocolFlag() == null || openApiUserInfo.getPerpetualProtocolFlag() != 1) {
            throw new BizException(BizErrorCodeEnum.NEEDED_CONFIRM_FUTURES_EXCHANGE);
        }
        if (!CheckPermission.checkPermission(openApiUserInfo.getAuthorities(), PermissionType.TRADE)) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.API_KEY_NOT_PREMISSION_TRADE);
        }

        // 获取用户 id
        final Long userId = openApiUserInfo.getId();
        final Integer brokerId = this.getBrokerId();

        orderDTO.setUserId(userId);
        orderDTO.setBrokerId(brokerId);
        orderDTO.setContractCode(contractCode);

        this.userPositionService.initAccount(contractCode, userId, brokerId);

        final ResponseResult responseResult;
        if (orderDTO.getTriggerBy() == null) {
            /**
             * 下单
             */
            responseResult = this.orderShardingService.dealOrder(orderDTO, WebUtil.getWebFrom(request));
        } else {
            /**
             * 计划委托
             */
            responseResult = this.orderShardingService.dealConditionOrder(orderDTO, WebUtil.getWebFrom(request));
        }
        Map result = (Map) responseResult.getData();
        if (responseResult.getCode() != 0) {
            result = Maps.newHashMap();
            result.put("code", responseResult.getCode());
            result.put("msg", responseResult.getMsg());

            log.error("dealOrder dealOrder is error ,userId={},brokerId={},responseResult={}",
                    userId, brokerId, JSON.toJSON(responseResult));

            return result;
        }
        resultMap.put("id", result.get("orderId"));
        return resultMap;
    }

    /**
     * 撤单
     *
     * @param contractCode 合约code
     * @param id           主键id
     * @param request
     */
    @DeleteMapping("/{contractCode}/order/{id}")
    @OpenApiRateLimit(value = "10/1")
    @OpenApiAuthValidator(method = HttpMethod.DELETE)
    public ResponseResult cancelOrder(@PathVariable("contractCode") final String contractCode,
                                      @PathVariable("id") final Long id,
                                      final HttpServletRequest request) {

        final OpenApiUserInfo openApiUserInfo = this.authenticationUtils.getUserInfo(request);

        if (!CheckPermission.checkPermission(openApiUserInfo.getAuthorities(), PermissionType.TRADE)) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.API_KEY_NOT_PREMISSION_TRADE);
        }
        // 获取用户 id
        final Long userId = openApiUserInfo.getId();
        final Integer brokerId = this.getBrokerId();

        return this.orderShardingService.cancelOrder(userId, brokerId, id, contractCode);
    }

    /**
     * 批量撤单
     *
     * @param contractCode
     * @param request
     */
    @DeleteMapping("/{contractCode}/orders")
    @OpenApiAuthValidator(method = HttpMethod.DELETE)
    public ResponseResult cancelOrders(@PathVariable("contractCode") final String contractCode, final HttpServletRequest request) {

        final OpenApiUserInfo openApiUserInfo = this.authenticationUtils.getUserInfo(request);

        if (!CheckPermission.checkPermission(openApiUserInfo.getAuthorities(), PermissionType.TRADE)) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.API_KEY_NOT_PREMISSION_TRADE);
        }
        final Long userId = openApiUserInfo.getId();
        final Integer brokerId = this.getBrokerId();

        this.orderShardingService.cancelAll(brokerId, userId, contractCode);
        return ResultUtils.success();
    }

    /**
     * 根据订单id获取订单信息
     *
     * @param contractCode 合约
     * @param id           订单主键id
     */
    @GetMapping("/{contractCode}/{id}")
    @OpenApiRateLimit(value = "10/1")
    @OpenApiAuthValidator(method = HttpMethod.GET)
    public OrderBookDTO getOrderInfo(@PathVariable("contractCode") final String contractCode, @PathVariable("id") final Long id,
                                     final HttpServletRequest request) {

        final OpenApiUserInfo openApiUserInfo = this.authenticationUtils.getUserInfo(request);

        final Long userId = openApiUserInfo.getId();

        final Contract contract = this.contractService.getContract(contractCode);
        if (Objects.isNull(contract)) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Illegal_parameter);
        }

        final Order order = this.orderShardingService.getByOrderId(contractCode, id);
        if (Objects.isNull(order)) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Order_not_exist);
        }

        /**
         * 是否本人订单判断
         */
        if (!order.getUserId().equals(userId)) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.OTC_Order_UserFrom_Error);

        }

        final OrderBookDTO bookDTO = ObjectCopyUtil.map(order, OrderBookDTO.class);
        bookDTO.setOrderSize(this.serializer(ContractSizeUtil.countSize(contract, order.getAmount(), order.getPrice())));

        return bookDTO;
    }

    private String serializer(final BigDecimal value) {
        if (value == null) {
            return "0";
        }
        return value.stripTrailingZeros().toPlainString();
    }
}
