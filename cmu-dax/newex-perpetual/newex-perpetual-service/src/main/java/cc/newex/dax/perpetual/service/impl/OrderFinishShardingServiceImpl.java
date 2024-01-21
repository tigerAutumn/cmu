package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.OrderFinishShardingExample;
import cc.newex.dax.perpetual.data.OrderFinishShardingRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderFinish;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.OrderFinishShardingService;
import cc.newex.dax.perpetual.util.ShardingUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 定单完成总表 服务实现
 *
 * @author newex-team
 * @date 2018-10-30 18:49:51
 */
@Slf4j
@Service
public class OrderFinishShardingServiceImpl extends
        AbstractCrudService<OrderFinishShardingRepository, OrderFinish, OrderFinishShardingExample, Long>
        implements OrderFinishShardingService {
    @Autowired
    private OrderFinishShardingRepository orderFinishRepository;
    @Autowired
    private ContractService contractService;

    @Override
    protected OrderFinishShardingExample getPageExample(final String fieldName,
                                                        final String keyword) {
        final OrderFinishShardingExample example = new OrderFinishShardingExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int createOrderFinishIfNotExists(final ShardTable shardTable) {
        return this.orderFinishRepository.createOrderFinishIfNotExists(shardTable);
    }

    @Override
    public int batchInsertByOrder(final List<Order> orderList, final ShardTable shardTable) {
        return this.orderFinishRepository.batchInsertByOrder(orderList, shardTable);
    }

    @Override
    public ShardTable getOrderFinishShardTable(final String contractCode) {
        final Contract contract = this.contractService.getContract(contractCode);
        return ShardingUtil.buildContractOrderFinishShardTable(contract);
    }

    @Override
    public List<OrderFinish> queryOrderList(final Long userId, final Integer brokerId,
                                            final String contractCode) {
        final OrderFinishShardingExample example = new OrderFinishShardingExample();
        final OrderFinishShardingExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId);

        example.setOrderByClause("id desc limit 200");

        if (StringUtils.isNotEmpty(contractCode)) {
            criteria.andContractCodeEqualTo(contractCode);
        } else {
            final List<OrderFinish> totalList = Lists.newArrayList();
            final List<String> contractCodeList = this.contractService.getUnExpiredContract().stream()
                    .map(Contract::getContractCode).collect(Collectors.toList());
            for (final String code : contractCodeList) {
                final OrderFinishShardingExample finishShardingExample = new OrderFinishShardingExample();
                final OrderFinishShardingExample.Criteria exampleCriteria = finishShardingExample.createCriteria();

                exampleCriteria.andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId);
                exampleCriteria.andContractCodeEqualTo(code);
                finishShardingExample.setOrderByClause("id desc limit 200");
                final List<OrderFinish> list = this.orderFinishRepository.selectByExample(finishShardingExample, this.getOrderFinishShardTable(code));
                if (CollectionUtils.isNotEmpty(list)) {
                    totalList.addAll(list);
                }
            }
            return totalList;

        }
        return this.orderFinishRepository.selectByExample(example, this.getOrderFinishShardTable(contractCode));
    }
}
