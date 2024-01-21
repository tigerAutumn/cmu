package cc.newex.dax.boss.report.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.report.criteria.CategoryExample;
import cc.newex.dax.boss.report.domain.Category;

import java.util.List;

/**
 * 报表类别表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface CategoryService
        extends CrudService<Category, CategoryExample, Integer> {
    /**
     * @param id
     * @return
     */
    List<Category> getChildren(int id);

    /**
     * @param id
     * @return
     */
    boolean hasChildren(int id);

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
    Category paste(int sourceId, int targetId);

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
}
