package cc.newex.dax.users.service.admin;

/**
 * 用户受限制行为 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface UserLimitedBehaviorAdminService {
    /**
     * @param ipAddress
     * @param type
     * @return
     */
    boolean resetUserLimitedBehavior(long ipAddress, int type);
}
