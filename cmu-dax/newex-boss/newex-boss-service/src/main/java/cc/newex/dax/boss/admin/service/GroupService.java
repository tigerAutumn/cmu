package cc.newex.dax.boss.admin.service;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.admin.criteria.GroupExample;
import cc.newex.dax.boss.admin.domain.Group;
import cc.newex.dax.boss.admin.domain.GroupUser;

import java.util.List;
import java.util.function.Predicate;

/**
 * 后台系统企业组织机构表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface GroupService
        extends CrudService<Group, GroupExample, Integer> {

    /**
     * 获取当前组织机构下的所有后台管理用户
     *
     * @param id 组织机构id
     * @return {@link List[GroupUser]}
     */
    List<GroupUser> getAdminUsers(final Integer id);

    /**
     * @param pageInfo
     * @param pid
     * @return
     */
    List<Group> getByPage(PageInfo pageInfo, Integer pid, Integer brokerId);

    /**
     * @param moduleId
     * @return
     */
    List<Group> getChildren(int moduleId);

    /**
     * get info by id
     *
     * @param id
     * @return
     */
    Group getInfoById(int id);


    /**
     * @param moduleId
     * @return
     */
    boolean hasChildren(int moduleId);

    /**
     * @param id
     * @param pid
     * @return
     */
    boolean remove(int id, int pid);

    /**
     * @param id
     */
    void rebuildPathById(int id);

    /**
     *
     */
    void rebuildAllPath();

    /**
     * @param modules
     * @param predicate
     * @return
     */
    List<TreeNode<Group>> getGroupTree(List<Group> modules, Predicate<Group> predicate);

    /**
     * 根据券商Id获取组织结果
     *
     * @param id
     * @return
     */
    List<Group> getAllByBrokerId(Integer id);
}
