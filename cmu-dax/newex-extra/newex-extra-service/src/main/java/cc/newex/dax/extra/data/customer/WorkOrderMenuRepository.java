package cc.newex.dax.extra.data.customer;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.customer.WorkOrderMenuExample;
import cc.newex.dax.extra.domain.customer.WorkOrderMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 工单菜单表 数据访问类
 *
 * @author newex-team
 * @date 2018-06-14
 */
@Repository
public interface WorkOrderMenuRepository
        extends CrudRepository<WorkOrderMenu, WorkOrderMenuExample, Integer> {
    /**
     * @param id
     * @return
     */
    String getMenuDesc(@Param("id") Integer id);
}