package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.OrderAllExample;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderAll;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 全部订单表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:29:33
 */
@Repository
public interface OrderAllRepository extends CrudRepository<OrderAll, OrderAllExample, Long> {

    /**
     * 批量查询orderAll成交数据
     *
     * @param orderList
     * @return
     */
    int batchInsertOrderAllDealOnDuplicate(@Param("records") List<Order> orderList);

    /**
     * 按order_id批量删除
     *
     * @param orderList
     * @return
     */
    int deleteByOrderIds(@Param("records") List<Order> orderList);
}
