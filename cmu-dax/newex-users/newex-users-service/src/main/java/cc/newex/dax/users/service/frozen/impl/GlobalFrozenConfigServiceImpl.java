package cc.newex.dax.users.service.frozen.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.GlobalFrozenConfigExample;
import cc.newex.dax.users.data.GlobalFrozenConfigRepository;
import cc.newex.dax.users.domain.GlobalFrozenConfig;
import cc.newex.dax.users.service.frozen.GlobalFrozenConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统业务全局冻结配置表 服务实现
 *
 * @author newex-team
 * @date 2018-07-08
 */
@Slf4j
@Service
public class GlobalFrozenConfigServiceImpl
        extends AbstractCrudService<GlobalFrozenConfigRepository, GlobalFrozenConfig, GlobalFrozenConfigExample, Integer>
        implements GlobalFrozenConfigService {

    @Autowired
    private GlobalFrozenConfigRepository globalFrozenConfigRepos;

    @Override
    protected GlobalFrozenConfigExample getPageExample(final String fieldName, final String keyword) {
        final GlobalFrozenConfigExample example = new GlobalFrozenConfigExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int edit(final String name, final Integer status) {
        final GlobalFrozenConfigExample example = new GlobalFrozenConfigExample();
        example.createCriteria().andCodeEqualTo(name);
        final GlobalFrozenConfig config = GlobalFrozenConfig.builder()
                .status(status)
                .build();
        return this.editByExample(config, example);
    }

    @Override
    public List<GlobalFrozenConfig> listAll(final Integer brokerId) {
        final GlobalFrozenConfigExample example = new GlobalFrozenConfigExample();
        if (brokerId != null && brokerId > 0) {
            example.createCriteria().andBrokerIdEqualTo(brokerId);
        }
        return this.globalFrozenConfigRepos.selectByExample(example);
    }

    @Override
    public int insert(final GlobalFrozenConfig globalFrozenConfig) {
        return this.globalFrozenConfigRepos.insert(globalFrozenConfig);
    }
}