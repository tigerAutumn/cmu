package cc.newex.dax.extra.service.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cms.DappsInfoExample;
import cc.newex.dax.extra.data.cms.DappsInfoRepository;
import cc.newex.dax.extra.domain.cms.DappsInfo;
import cc.newex.dax.extra.dto.cms.DAppsAdminDTO;
import cc.newex.dax.extra.service.cms.DappsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * dapps应用程序表 服务实现
 *
 * @author mbg.generated
 * @date 2018-09-12 17:14:02
 */
@Slf4j
@Service
public class DappsInfoServiceImpl extends AbstractCrudService<DappsInfoRepository, DappsInfo, DappsInfoExample, Long> implements DappsInfoService {
    @Autowired
    private DappsInfoRepository cmsDappsInfoRepos;

    @Override
    protected DappsInfoExample getPageExample(final String fieldName, final String keyword) {
        final DappsInfoExample example = new DappsInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public DataGridPagerResult<DAppsAdminDTO> getDAppPagerInfo(final DataGridPager<DAppsAdminDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final DappsInfo dappsInfo = mapper.map(pager.getQueryParameter(), DappsInfo.class);

        final DappsInfoExample example = new DappsInfoExample();
        final DappsInfoExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(dappsInfo.getBrokerId())) {
            criteria.andBrokerIdEqualTo(dappsInfo.getBrokerId());
        }

        final List<DappsInfo> dappsInfos = this.getByPage(pageInfo, example);

        final List<DAppsAdminDTO> result = mapper.map(
                dappsInfos, new TypeToken<List<DAppsAdminDTO>>() {
                }.getType()
        );
        return new DataGridPagerResult<>(pageInfo.getTotals(), result);
    }
}