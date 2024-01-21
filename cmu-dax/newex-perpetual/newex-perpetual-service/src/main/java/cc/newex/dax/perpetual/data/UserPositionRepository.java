package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.UserPositionExample;
import cc.newex.dax.perpetual.domain.UserPosition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * 用户合约持仓数据表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:32:15
 */
@Repository
public interface UserPositionRepository
        extends CrudRepository<UserPosition, UserPositionExample, Long> {

    /**
     * 获取用户 id 按 base 分组且 有效持仓条数 >= count
     *
     * @param base
     * @param count
     * @return
     */
    List<Long> getUserIdGroupByBase(@Param("base") String base, @Param("brokerId") Integer brokerId,
                                    @Param("count") int count);

    /**
     * 批量查询用户持仓
     */
    List<UserPosition> selectBatchPosition(@Param("contractCode") String contractCode,
                                           @Param("brokerId") Integer brokerId, @Param("set") Set<Long> userIds);

    BigDecimal sumUserPositionAmount(String contractCode);

    Integer batchUpdateBaseInfo(@Param("records") List<UserPosition> record);

    List<UserPosition> selectBaseInfoForClearPosition(@Param("example") UserPositionExample example);
}
