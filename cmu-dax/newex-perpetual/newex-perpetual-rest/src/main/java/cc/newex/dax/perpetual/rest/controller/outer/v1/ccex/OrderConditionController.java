package cc.newex.dax.perpetual.rest.controller.outer.v1.ccex;

import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.common.enums.OrderConditionStatusEnum;
import cc.newex.dax.perpetual.criteria.OrderConditionExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.OrderCondition;
import cc.newex.dax.perpetual.dto.OrderConditionDTO;
import cc.newex.dax.perpetual.rest.controller.base.BaseController;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.OrderConditionService;
import cc.newex.dax.perpetual.util.ObjectCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 条件委托订单
 *
 * @author newex-team
 * @date 2018-11-27
 */
@RestController
@RequestMapping(value = "/v1/perpetual/condition")
public class OrderConditionController extends BaseController {
    @Autowired
    OrderConditionService orderConditionService;
    @Autowired
    ContractService contractService;

    /**
     * 获取所有条件单列表
     *
     * @return
     */
    @GetMapping("/list-all")
    public ResponseResult list() {
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        final OrderConditionExample orderConditionExample = new OrderConditionExample();
        orderConditionExample.createCriteria().andUserIdEqualTo(userJwt.getUserId())
                .andStatusEqualTo(OrderConditionStatusEnum.NODEAL.getStatus()).andBrokerIdEqualTo(brokerId);
        final List<OrderCondition> orderConditionList = this.orderConditionService.getByExample(orderConditionExample);

        final List<OrderConditionDTO> orderConditionDTOList = new ArrayList<>();
        orderConditionList.stream().forEach(vo -> {
            final OrderConditionDTO dto = ObjectCopyUtil.map(vo, OrderConditionDTO.class);
            dto.setId(vo.getOrderId());
            final Contract contract = this.contractService.getContract(vo.getContractCode());
            this.dealOrderConditionDTO(dto, vo, contract);
            dto.setBase(contract.getBase());
            dto.setQuote(contract.getQuote());
            dto.setContractDirection(contract.getDirection());
            orderConditionDTOList.add(dto);
        });
        return ResultUtils.success(orderConditionDTOList);
    }

    private void dealOrderConditionDTO(final OrderConditionDTO dto, final OrderCondition vo, final Contract contract) {
        dto.setPrice(this.serializer(vo.getPrice()));
        dto.setAmount(this.serializer(vo.getAmount()));
        dto.setAvgPrice(this.serializer(vo.getAvgPrice()));
        dto.setDealAmount(this.serializer(vo.getDealAmount()));
        dto.setDealSize(this.serializer(vo.getDealSize()));
        dto.setTriggerPrice(this.serializer(vo.getConditionPrice()));
        if (vo.getSize() != null) {
            if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
                dto.setOrderSize(this.serializer(vo.getSize().setScale(contract.getMinQuoteDigit(), BigDecimal.ROUND_DOWN)));
            } else {
                dto.setOrderSize(this.serializer(vo.getSize().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN)));
            }
        }
    }

    /**
     * 获取列表
     *
     * @return
     */
    @GetMapping("/{contractCode}/list")
    public ResponseResult list(@PathVariable("contractCode") final String contractCode) {
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        final OrderConditionExample orderConditionExample = new OrderConditionExample();
        orderConditionExample.createCriteria().andUserIdEqualTo(userJwt.getUserId()).andContractCodeEqualTo(contractCode)
                .andStatusEqualTo(OrderConditionStatusEnum.NODEAL.getStatus()).andBrokerIdEqualTo(brokerId);
        final List<OrderCondition> orderConditionList =
                this.orderConditionService.getByExample(orderConditionExample);

        final Contract contract = this.contractService.getContract(contractCode);
        final List<OrderConditionDTO> orderConditionDTOList = new ArrayList<>();
        orderConditionList
                .forEach(vo -> {
                    final OrderConditionDTO dto = ObjectCopyUtil.map(vo, OrderConditionDTO.class);
                    dto.setId(vo.getOrderId());
                    this.dealOrderConditionDTO(dto, vo, contract);
                    dto.setBase(contract.getBase());
                    dto.setQuote(contract.getQuote());
                    dto.setSide(vo.getSide());
                    dto.setContractDirection(contract.getDirection());
                    orderConditionDTOList.add(dto);
                });
        return ResultUtils.success(orderConditionDTOList);
    }

    private String serializer(final BigDecimal value) {
        if (value == null) {
            return "0";
        }
        return value.stripTrailingZeros().toPlainString();
    }

    /**
     * 撤单
     *
     * @param contractCode
     * @param id
     * @return
     */
    @DeleteMapping("/{contractCode}/order/{id}")
    public ResponseResult<?> cancel(@PathVariable("contractCode") final String contractCode,
                                    @PathVariable final Long id) {
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        this.checkContract(contractCode, true);
        this.orderConditionService.cancelOrder(userJwt.getUserId(), brokerId, id, contractCode);
        return ResultUtils.success();
    }
}
