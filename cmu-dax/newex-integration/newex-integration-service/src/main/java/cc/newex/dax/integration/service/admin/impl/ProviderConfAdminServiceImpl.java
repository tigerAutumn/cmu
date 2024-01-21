package cc.newex.dax.integration.service.admin.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.conf.ProviderConfExample;
import cc.newex.dax.integration.data.conf.ProviderConfRepository;
import cc.newex.dax.integration.domain.conf.ProviderConf;
import cc.newex.dax.integration.service.admin.ProviderConfAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
@Slf4j
@Service
public class ProviderConfAdminServiceImpl
        extends AbstractCrudService<ProviderConfRepository, ProviderConf, ProviderConfExample, Integer>
        implements ProviderConfAdminService {

    @Autowired
    private ProviderConfRepository providerConfRepos;

    @Override
    protected ProviderConfExample getPageExample(final String fieldName, final String keyword) {
        final ProviderConfExample example = new ProviderConfExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<ProviderConf> getByPage(final PageInfo pageInfo, final String fieldName, final String keyword, final Integer brokerId) {

        final ProviderConfExample example = new ProviderConfExample();
        final ProviderConfExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(fieldName)) {
            criteria.andFieldLike(fieldName, keyword);
        }
        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }


        return this.getByPage(pageInfo, example);
    }
}