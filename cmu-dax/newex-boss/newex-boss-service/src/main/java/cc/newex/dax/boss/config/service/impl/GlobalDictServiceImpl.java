package cc.newex.dax.boss.config.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.config.criteria.GlobalDictExample;
import cc.newex.dax.boss.config.data.GlobalDictRepository;
import cc.newex.dax.boss.config.domain.GlobalDict;
import cc.newex.dax.boss.config.service.GlobalDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台系统全局配置字典表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class GlobalDictServiceImpl
        extends AbstractCrudService<GlobalDictRepository, GlobalDict, GlobalDictExample, Integer>
        implements GlobalDictService {

    @Autowired
    private GlobalDictRepository globalDictRepos;

    @Override
    protected GlobalDictExample getPageExample(final String fieldName, final String keyword) {
        final GlobalDictExample example = new GlobalDictExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int add(final GlobalDict record) {
        return this.dao.insert(record);
    }

    @Override
    public List<GlobalDict> getByPage(final PageInfo pageInfo, final Integer pid) {
        final GlobalDictExample example = new GlobalDictExample();
        example.or().andParentIdEqualTo(pid);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<GlobalDict> getByParentId(final Integer parentId) {
        return this.globalDictRepos.selectByParentId(parentId);
    }

    @Override
    public List<GlobalDict> getByParentKey(final String key) {
        return this.globalDictRepos.selectByParentKey(key);
    }
}