package cc.newex.dax.boss.admin.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.admin.criteria.UserExample;
import cc.newex.dax.boss.admin.domain.User;

import java.util.List;

/**
 * 后台系统用户表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface UserService
        extends CrudService<User, UserExample, Integer> {
    /**
     * 根据账号获取用户信息
     *
     * @param account 账号
     * @return {@link User}
     */
    User getUserByAccount(String account);

    /**
     * 加密用户的密码字段
     *
     * @param user {@link User}
     */
    void encryptPassword(User user);

    /**
     * 查询用户所有非敏感数据列信息
     *
     * @param account
     * @return {@link User}
     */
    User getUserWithoutSensitiveInfo(String account);

    /**
     * 按照用户groupids 查询用户信息
     *
     * @param groupIds
     * @return
     */
    List<User> selectUserInfoByGroupIds(String groupIds);
}
