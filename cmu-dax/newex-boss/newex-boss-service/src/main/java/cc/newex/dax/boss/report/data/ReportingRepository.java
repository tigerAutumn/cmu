package cc.newex.dax.boss.report.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.boss.report.criteria.ReportingExample;
import cc.newex.dax.boss.report.domain.Reporting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报表信息表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface ReportingRepository
        extends CrudRepository<Reporting, ReportingExample, Integer> {
    /**
     * 获取当前分页查询的总记录数
     *
     * @param pager
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByPagerWithCategory(@Param("pager") PageInfo pager, @Param("example") ReportingExample example);

    /**
     * 分页查询
     *
     * @param pager
     * @param example 查询条件参数
     * @return 分页记录列表
     */
    List<Reporting> selectByPagerWithCategory(@Param("pager") PageInfo pager, @Param("example") ReportingExample example);
}