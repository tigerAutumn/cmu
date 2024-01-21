package cc.newex.dax.perpetual.rest.controller.outer.v1.ccex;

import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.common.enums.OrderConditionStatusEnum;
import cc.newex.dax.perpetual.criteria.OrderConditionExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.OrderAll;
import cc.newex.dax.perpetual.domain.OrderCondition;
import cc.newex.dax.perpetual.domain.OrderFinish;
import cc.newex.dax.perpetual.dto.OrderBookDTO;
import cc.newex.dax.perpetual.dto.OrderDTO;
import cc.newex.dax.perpetual.rest.controller.base.BaseController;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.OrderAllService;
import cc.newex.dax.perpetual.service.OrderConditionService;
import cc.newex.dax.perpetual.service.OrderFinishShardingService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.util.ObjectCopyUtil;
import cc.newex.dax.perpetual.util.WebUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/v1/perpetual/products")
public class OrderController extends BaseController {
    @Resource
    private OrderShardingService orderShardingService;
    @Autowired
    private HttpServletRequest request;
    @Resource
    private OrderAllService orderAllService;
    @Resource
    private OrderFinishShardingService orderFinishShardingService;
    @Resource
    private UserPositionService userPositionService;
    @Resource
    private ContractService contractService;
    @Resource
    private OrderConditionService orderConditionService;

    /**
     * 获取所有合约下的订单列表,包括已完成和未完成订单
     *
     * @return
     */
    @GetMapping("list")
    public ResponseResult list(@RequestParam(value = "contractCode", required = false) final String contractCode,
                               @RequestParam(value = "detailSide", required = false) final String detailSide,
                               @RequestParam(value = "status", required = false) final Integer status,
                               @RequestParam(value = "systemType", required = false) final Integer systemType,
                               @RequestParam(value = "startDate", required = false) final String startDate,
                               @RequestParam(value = "endDate", required = false) final String endDate,
                               @RequestParam(value = "page", required = false, defaultValue = "1") final Integer page,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") final Integer pageSize) {
        /**
         * 1 用户状态与合约状态验证`
         */
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        List<OrderBookDTO> allList = Lists.newArrayList();
        /**
         * 订单列表
         */
        final List<OrderAll> list = this.orderAllService.queryOrderTypeList(userJwt.getUserId(), brokerId, null);

        final List<OrderBookDTO> orderList = Lists.newArrayList();
        list.stream().forEach(x -> {
            final Contract contract = this.contractService.getContract(x.getContractCode());
            final OrderBookDTO bookDTO = ObjectCopyUtil.map(x, OrderBookDTO.class);
            this.dealOrderBookDTO(bookDTO, x, contract);
            bookDTO.setId(x.getOrderId());
            bookDTO.setContractDirection(contract.getDirection());
            bookDTO.setBase(contract.getBase());
            bookDTO.setQuote(contract.getQuote());
            orderList.add(bookDTO);
        });
        allList.addAll(orderList);

        /**
         * 完成订单列表
         */
        final List<OrderFinish> orderFinishList = this.orderFinishShardingService.queryOrderList(userJwt.getUserId(),
                brokerId, null);

        final List<OrderBookDTO> finishList = Lists.newArrayList();
        orderFinishList.stream().forEach(x -> {
            final Contract contract = this.contractService.getContract(x.getContractCode());
            final OrderBookDTO bookDTO = ObjectCopyUtil.map(x, OrderBookDTO.class);
            this.dealOrderBookDTO(bookDTO, x, contract);
            bookDTO.setId(x.getOrderId());
            bookDTO.setContractDirection(contract.getDirection());
            bookDTO.setBase(contract.getBase());
            bookDTO.setQuote(contract.getQuote());
            finishList.add(bookDTO);
        });
        allList.addAll(finishList);

        /**
         * 条件单列表
         */
        final OrderConditionExample orderConditionExample = new OrderConditionExample();
        orderConditionExample.createCriteria().andUserIdEqualTo(userJwt.getUserId()).andStatusEqualTo(OrderConditionStatusEnum.NODEAL.getStatus())
                .andBrokerIdEqualTo(brokerId);
        orderConditionExample.setOrderByClause("id desc limit 200");

        final List<OrderCondition> orderConditionList = this.orderConditionService.getByExample(orderConditionExample);
        final List<OrderBookDTO> conditionList = Lists.newArrayList();

        orderConditionList.stream().forEach(vo -> {
            final OrderBookDTO dto = OrderBookDTO.builder().price(this.serializer(vo.getPrice())).triggerPrice(this.serializer(vo.getConditionPrice()))
                    .amount(this.serializer(vo.getAmount())).contractCode(vo.getContractCode()).id(vo.getOrderId()).side(vo.getSide())
                    .systemType(vo.getSystemType()).status(OrderConditionStatusEnum.NODEAL.getStatus()).direction(vo.getDirection())
                    .profit("0").fee("0").reason(0).detailSide(vo.getDetailSide())
                    .triggerBy(vo.getType()).createdDate(vo.getCreatedDate()).build();
            conditionList.add(dto);
        });

        allList.addAll(conditionList);

        /**
         * 过滤符合条件的记录
         */
        if (StringUtils.isNotEmpty(contractCode)) {
            allList = allList.stream().filter(x -> x.getContractCode().equalsIgnoreCase(contractCode)).collect(Collectors.toList());
        }

        if (StringUtils.isNotEmpty(detailSide)) {
            allList = allList.stream().filter(x -> x.getDetailSide().equalsIgnoreCase(detailSide)).collect(Collectors.toList());
        }

        if (status != null) {
            allList = allList.stream().filter(x -> x.getStatus().equals(status)).collect(Collectors.toList());
        }
        if (systemType != null) {
            allList = allList.stream().filter(x -> x.getSystemType().equals(systemType)).collect(Collectors.toList());
        }

        if (StringUtils.isNotEmpty(startDate)) {
            allList = allList.stream().filter(x -> x.getCreatedDate().getTime() > new Date(Long.valueOf(startDate)).getTime()).collect(Collectors.toList());
        }

        if (StringUtils.isNotEmpty(endDate)) {
            allList = allList.stream().filter(x -> x.getCreatedDate().getTime() < new Date(Long.valueOf(endDate)).getTime()).collect(Collectors.toList());
        }

        allList = allList.stream().sorted(Comparator.comparing(OrderBookDTO::getCreatedDate).reversed()).collect(Collectors.toList());

        final long totalCount = allList.size(); // 总记录数

        final int fromIndex = (page - 1) * pageSize;
        int toIndex = page * pageSize;
        if (toIndex > totalCount) {
            toIndex = (int) totalCount;
        }
        allList = allList.subList(fromIndex, toIndex);

        return ResultUtils.success(new DataGridPagerResult<>(totalCount, allList));
    }

    private void dealOrderBookDTO(final OrderBookDTO bookDTO, final OrderFinish vo, final Contract contract) {
        bookDTO.setAmount(this.serializer(vo.getAmount()));
        bookDTO.setDealAmount(this.serializer(vo.getDealAmount()));
        bookDTO.setAvgPrice(this.serializer(vo.getAvgPrice()));
        bookDTO.setPrice(this.serializer(vo.getPrice()));
        bookDTO.setProfit(this.serializer(vo.getProfit()));
        bookDTO.setFee(this.serializer(vo.getFee()));
        bookDTO.setReason(vo.getReason());
        if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
            bookDTO.setOrderSize(this.serializer(vo.getSize().setScale(contract.getMinQuoteDigit(), BigDecimal.ROUND_DOWN)));
        } else {
            bookDTO.setOrderSize(this.serializer(vo.getSize().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN)));
        }
    }

    private String serializer(final BigDecimal value) {
        if (value == null) {
            return "0";
        }
        return value.stripTrailingZeros().toPlainString();
    }

    /**
     * 获取所有合约下的订单列表,包括已完成和未完成订单
     *
     * @return
     */
    @GetMapping("list-all")
    public ResponseResult<List<OrderBookDTO>> listAll() {
        /**
         * 1 用户状态与合约状态验证`
         */
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        List<OrderBookDTO> allList = Lists.newArrayList();
        /**
         * 订单列表
         */
        final List<OrderAll> list = this.orderAllService.queryOrderTypeList(userJwt.getUserId(), brokerId, null);

        final List<OrderBookDTO> orderList = Lists.newArrayList();
        list.stream().forEach(x -> {
            final Contract contract = this.contractService.getContract(x.getContractCode());
            final OrderBookDTO bookDTO = ObjectCopyUtil.map(x, OrderBookDTO.class);
            this.dealOrderBookDTO(bookDTO, x, contract);
            bookDTO.setId(x.getOrderId());
            bookDTO.setContractDirection(contract.getDirection());
            bookDTO.setBase(contract.getBase());
            bookDTO.setQuote(contract.getQuote());
            orderList.add(bookDTO);
        });
        allList.addAll(orderList);

        /**
         * 完成订单列表
         */
        final List<OrderFinish> orderFinishList = this.orderFinishShardingService.queryOrderList(userJwt.getUserId(),
                brokerId, null);

        final List<OrderBookDTO> finishList = Lists.newArrayList();
        orderFinishList.stream().forEach(x -> {
            final Contract contract = this.contractService.getContract(x.getContractCode());
            final OrderBookDTO bookDTO = ObjectCopyUtil.map(x, OrderBookDTO.class);
            this.dealOrderBookDTO(bookDTO, x, contract);
            bookDTO.setId(x.getOrderId());
            bookDTO.setContractDirection(contract.getDirection());
            bookDTO.setBase(contract.getBase());
            bookDTO.setQuote(contract.getQuote());
            finishList.add(bookDTO);
        });
        allList.addAll(finishList);

        /**
         * 条件单列表
         */
        final OrderConditionExample orderConditionExample = new OrderConditionExample();
        orderConditionExample.createCriteria().andUserIdEqualTo(userJwt.getUserId()).andStatusEqualTo(OrderConditionStatusEnum.NODEAL.getStatus())
                .andBrokerIdEqualTo(brokerId);
        orderConditionExample.setOrderByClause("id desc limit 200");

        final List<OrderCondition> orderConditionList = this.orderConditionService.getByExample(orderConditionExample);
        final List<OrderBookDTO> conditionList = Lists.newArrayList();

        orderConditionList.stream().forEach(vo -> {
            final OrderBookDTO dto = OrderBookDTO.builder().price(this.serializer(vo.getPrice())).triggerPrice(this.serializer(vo.getConditionPrice()))
                    .amount(this.serializer(vo.getAmount())).contractCode(vo.getContractCode()).id(vo.getOrderId()).side(vo.getSide())
                    .systemType(vo.getSystemType()).status(OrderConditionStatusEnum.NODEAL.getStatus()).direction(vo.getDirection())
                    .profit("0").fee("0").reason(0)
                    .triggerBy(vo.getType()).createdDate(vo.getCreatedDate()).build();
            conditionList.add(dto);
        });

        allList.addAll(conditionList);
        allList.stream().sorted(Comparator.comparing(OrderBookDTO::getCreatedDate).reversed()).collect(Collectors.toList());

        if (allList.size() > 200) {
            allList = allList.subList(0, 200);
        }
        return ResultUtils.success(allList);
    }

    private void dealOrderBookDTO(final OrderBookDTO bookDTO, final OrderAll vo, final Contract contract) {
        bookDTO.setAmount(this.serializer(vo.getAmount()));
        bookDTO.setDealAmount(this.serializer(vo.getDealAmount()));
        bookDTO.setAvgPrice(this.serializer(vo.getAvgPrice()));
        bookDTO.setPrice(this.serializer(vo.getPrice()));
        bookDTO.setProfit(this.serializer(vo.getProfit()));
        bookDTO.setFee(this.serializer(vo.getFee()));
        bookDTO.setReason(vo.getReason());
        if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
            bookDTO.setOrderSize(this.serializer(vo.getSize().setScale(contract.getMinQuoteDigit(), BigDecimal.ROUND_DOWN)));
        } else {
            bookDTO.setOrderSize(this.serializer(vo.getSize().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN)));
        }
    }

    /**
     * 计算保证金
     *
     * @param contractCode
     * @param orderDTO
     * @return
     */
    @PostMapping("/{contractCode}/calculateMargin")
    public ResponseResult calculateMargin(@PathVariable("contractCode") final String contractCode,
                                          @RequestBody final OrderDTO orderDTO) {
        /**
         * 1 用户状态与合约状态验证
         */
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        this.checkContract(contractCode, true);

        orderDTO.setUserId(userJwt.getUserId());
        orderDTO.setBrokerId(brokerId);
        orderDTO.setContractCode(contractCode);
        this.userPositionService.initAccount(contractCode, orderDTO.getUserId(), orderDTO.getBrokerId());

        final JSONObject data;
        if (orderDTO.getAmount() == null || orderDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            data = new JSONObject();
            data.put("longOrderMargin", "0");
            data.put("shortOrderMargin", "0");
        } else {
            data = this.orderShardingService.calculateMargin(orderDTO);
        }
        return ResultUtils.success(data);
    }

    /**
     * 下单
     *
     * @param contractCode
     * @param orderDTO
     * @return
     */
    @PostMapping("/{contractCode}/order")
    public ResponseResult order(@PathVariable("contractCode") final String contractCode,
                                @RequestBody final OrderDTO orderDTO) {

        /**
         * 1 用户状态与合约状态验证
         */
        final JwtUserDetails userJwt = this.getUserDetails(true);

        final Integer brokerId = this.getBrokerId();

        this.checkContract(contractCode, true);

        orderDTO.setUserId(userJwt.getUserId());
        orderDTO.setBrokerId(brokerId);
        orderDTO.setContractCode(contractCode);
        this.userPositionService.initAccount(contractCode, orderDTO.getUserId(), orderDTO.getBrokerId());

        final ResponseResult responseResult;

        if (orderDTO.getTriggerBy() == null) {
            /**
             * 下单
             */
            responseResult = this.orderShardingService.dealOrder(orderDTO, WebUtil.getWebFrom(this.request));
        } else {
            /**
             * 计划委托
             */
            responseResult =
                    this.orderShardingService.dealConditionOrder(orderDTO, WebUtil.getWebFrom(this.request));
        }

        if (responseResult.getCode() != 0) {
            log.error("dealOrder dealOrder is error ,userId={},brokerId={},responseResult={}",
                    userJwt.getUserId(), brokerId, JSON.toJSON(responseResult));
            return responseResult;
        }

        return ResultUtils.success(new HashMap<String, Object>() {
            {
                final Map result = (Map) responseResult.getData();
                this.put("id", result.get("orderId"));
            }
        });
    }

    /**
     * 撤单
     *
     * @param contractCode
     * @param id
     * @return
     */
    @DeleteMapping("/{contractCode}/order/{id}")
    public ResponseResult cancel(@PathVariable("contractCode") final String contractCode,
                                 @PathVariable final Long id) {
        // 参数不能小于0
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        this.checkContract(contractCode, true);
        return this.orderShardingService.cancelOrder(userJwt.getUserId(), brokerId, id, contractCode);
    }

    /**
     * 取消所有订单
     *
     * @param contractCode
     */
    @DeleteMapping("/{contractCode}/orders")
    public ResponseResult cancelAll(@PathVariable("contractCode") final String contractCode) {
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        this.checkContract(contractCode, true);
        this.orderShardingService.cancelAll(brokerId, userJwt.getUserId(), contractCode);
        return ResultUtils.success();
    }
}
