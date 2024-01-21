package cc.newex.dax.boss.config.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.config.criteria.GlobalDictExample;
import cc.newex.dax.boss.config.domain.GlobalDict;

import java.util.List;

/**
 * 后台系统全局配置字典表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface GlobalDictService
        extends CrudService<GlobalDict, GlobalDictExample, Integer> {

    /**
     * @param pageInfo
     * @param pid
     * @return
     */
    List<GlobalDict> getByPage(PageInfo pageInfo, Integer pid);


    /**
     * @param parentId
     * @return
     */
    List<GlobalDict> getByParentId(Integer parentId);

    /**
     * @param key
     * @return
     */
    List<GlobalDict> getByParentKey(String key);
}
