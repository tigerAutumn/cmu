package cc.newex.dax.boss.admin.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.admin.criteria.UserExample;
import cc.newex.dax.boss.admin.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台系统用户表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface UserRepository
        extends CrudRepository<User, UserExample, Integer> {
    /**
     * 查询用户所有非敏感数据列信息
     *
     * @param example 条件
     * @return {@link User}
     */
    User selectUserWithoutSensitiveInfo(@Param("example") UserExample example);

    /**
     * 根据groupid 查询用户信息
     *
     * @param groupIds
     * @return
     */
    List<User> selectUserInfoByGroupIds(@Param("groupIds") String groupIds);
}