package cc.newex.dax.boss.security.service;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.security.MembershipFacade;
import cc.newex.dax.boss.admin.domain.Module;
import cc.newex.dax.boss.admin.domain.User;

import java.util.List;
import java.util.function.Predicate;

/**
 * 用户权限服务外观接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface AdminUserFacadeService extends MembershipFacade<User> {
    void loadCache();

    List<TreeNode<Module>> getModuleTree(final List<Module> modules, final Predicate<Module> predicate);

    List<Module> getModules(final String roleIds);
}
