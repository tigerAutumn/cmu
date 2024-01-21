package cc.newex.dax.users.service.membership;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserLevelExample;
import cc.newex.dax.users.domain.UserLevel;
import cc.newex.dax.users.dto.common.UserLevelEnum;

import java.util.List;

/**
 * 用户等级表 服务接口
 *
 * @author newex-team
 * @date 2018-07-07
 */
public interface UserLevelService
        extends CrudService<UserLevel, UserLevelExample, Long> {
    /**
     * @param userId
     * @description 通过用户id获取用户等级
     * @date 2018/7/7 下午1:33
     */
    UserLevel getByUserId(Long userId);

    /**
     * 分页查询用户等级
     *
     * @param level
     * @param lastUserId
     * @param limit
     * @return
     */
    List<UserLevel> getListByLevel(String level, Long lastUserId, Integer limit);

    /**
     * @param userId
     * @description 通过用户id获取用户等级枚举
     * @date 2018/7/7 下午1:33
     */
    UserLevelEnum getUserLevelEnum(Long userId);

    /**
     * @param userId 用户id
     * @param level  用户等级
     * @description 修改用户的等级
     * @date 2018/7/7 下午2:03
     */
    int updateUserLevel(Long userId, String level);
}
