package cc.newex.commons.security;

import java.util.Set;

/**
 * 用户权限服务外观接口
 *
 * @param <User> 用户信息对象
 * @author newex-team
 * @date 2017/12/09
 */
public interface MembershipFacade<User> {

    /**
     * 获取登录用户信息不包含密码等敏感数据
     *
     * @param account a {@link java.lang.String} object.
     * @return a User object.
     */
    User getUserNonSensitiveInfo(String account);

    /**
     * 获取登录用户信息、包含密码等敏感数据
     *
     * @param account a {@link java.lang.String} object.
     * @return a User object.
     */
    User getUser(String account);

    /**
     * <p>getRoleNames.</p>
     *
     * @param roleIds a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String getRoleNames(String roleIds);

    /**
     * <p>getRoleSet.</p>
     *
     * @param roleIds a {@link java.lang.String} object.
     * @return a {@link java.util.Set} object.
     */
    Set<String> getRoleSet(String roleIds);

    /**
     * <p>getPermissionSet.</p>
     *
     * @param roleIds a {@link java.lang.String} object.
     * @return a {@link java.util.Set} object.
     */
    Set<String> getPermissionSet(String roleIds);

    /**
     * <p>hasPermission.</p>
     *
     * @param roleIds a {@link java.lang.String} object.
     * @param codes   a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean hasPermission(String roleIds, String... codes);

    /**
     * <p>isAdministrator.</p>
     *
     * @param roleIds a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean isAdministrator(String roleIds);
}
