package cc.newex.dax.integration.service.admin.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.conf.WarningConfigExample;
import cc.newex.dax.integration.data.conf.WarningConfigRepository;
import cc.newex.dax.integration.domain.conf.WarningConfig;
import cc.newex.dax.integration.dto.admin.WarningConfigDTO;
import cc.newex.dax.integration.service.admin.WarningConfigAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报警配置表 服务实现
 *
 * @author mbg.generated
 * @date 2018-07-23 16:10:04
 */
@Slf4j
@Service
public class WarningConfigAdminServiceImpl
        extends AbstractCrudService<WarningConfigRepository, WarningConfig, WarningConfigExample, Long>
        implements WarningConfigAdminService {

    @Autowired
    private WarningConfigRepository warningConfigRepos;

    @Override
    protected WarningConfigExample getPageExample(final String fieldName, final String keyword) {
        final WarningConfigExample example = new WarningConfigExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<WarningConfig> listByPage(final PageInfo pageInfo, final WarningConfigDTO dto) {
        final WarningConfigExample example = new WarningConfigExample();
        final WarningConfigExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(dto.getCode())) {
            criteria.andCodeLike(dto.getCode());
        }
        if (StringUtils.isNotEmpty(dto.getBizType())) {
            criteria.andBizTypeLike(dto.getBizType());
        }
        return this.getByPage(pageInfo, example);

    }
}