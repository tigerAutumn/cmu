package cc.newex.dax.boss.admin.service;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.admin.criteria.ModuleExample;
import cc.newex.dax.boss.admin.domain.Module;

import java.util.List;
import java.util.function.Predicate;

/**
 * 后台系统模块表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface ModuleService
        extends CrudService<Module, ModuleExample, Integer> {
    /**
     * @param pageInfo
     * @param pid
     * @return
     */
    List<Module> getByPage(PageInfo pageInfo, Integer pid);

    /**
     * @param moduleIds
     * @return
     */
    List<Module> getModules(String moduleIds);

    /**
     * @param moduleId
     * @return
     */
    List<Module> getChildren(int moduleId);

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
     * @param sourceId
     * @param targetId
     * @param sourcePid
     * @param sourcePath
     */
    void move(int sourceId, int targetId, int sourcePid, String sourcePath);

    /**
     * @param sourceId
     * @param targetId
     * @return
     */
    Module paste(int sourceId, int targetId);

    /**
     * @param sourceId
     * @param targetId
     */
    void clone(int sourceId, int targetId);

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
    List<TreeNode<Module>> getModuleTree(List<Module> modules, Predicate<Module> predicate);

    int updatePath(Integer id, String path);

    String getPath(int pid, int id);

}
