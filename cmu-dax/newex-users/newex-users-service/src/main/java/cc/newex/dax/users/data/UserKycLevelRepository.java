package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserKycLevelExample;
import cc.newex.dax.users.domain.UserKycLevel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户kyc等级 数据访问类
 * @author newex-team
 * @date 2018-05-03
 */
@Repository
public interface UserKycLevelRepository extends CrudRepository<UserKycLevel, UserKycLevelExample, Long> {
    /**
     * 插入一条数据，如果存在则替换
     *
     * @param record
     * @return 影响的记录数
     */
    int replace(@Param("record") UserKycLevel record);
}