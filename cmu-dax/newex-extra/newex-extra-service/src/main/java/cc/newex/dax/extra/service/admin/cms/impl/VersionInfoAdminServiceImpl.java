package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cms.VersionInfoExample;
import cc.newex.dax.extra.data.cms.VersionInfoRepository;
import cc.newex.dax.extra.domain.cms.VersionInfo;
import cc.newex.dax.extra.dto.cms.VersionInfoDTO;
import cc.newex.dax.extra.service.admin.cms.VersionInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 版本信息 服务实现
 *
 * @author liutiejun
 * @date 2018-07-03
 */
@Slf4j
@Service
public class VersionInfoAdminServiceImpl
        extends AbstractCrudService<VersionInfoRepository, VersionInfo, VersionInfoExample, Integer>
        implements VersionInfoAdminService {

    @Autowired
    private VersionInfoRepository versionInfoRepository;

    @Override
    protected VersionInfoExample getPageExample(String fieldName, String keyword) {
        VersionInfoExample example = new VersionInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public int countByPager(PageInfo pager, VersionInfoExample example) {
        if (example == null) {
            return 0;
        }

        return this.versionInfoRepository.countByPager(pager, example);
    }

    @Override
    public int countByExample(VersionInfoExample example) {
        if (example == null) {
            return 0;
        }

        return this.versionInfoRepository.countByExample(example);
    }

    @Override
    public int count(VersionInfo news) {
        if (news == null) {
            return 0;
        }

        VersionInfoExample example = VersionInfoExample.toExample(news);

        return this.countByExample(example);
    }

    @Override
    public List<VersionInfo> getByPlatform(Integer platform, Integer brokerId) {
        if (platform == null) {
            return null;
        }

        VersionInfoExample example = new VersionInfoExample();
        VersionInfoExample.Criteria criteria = example.createCriteria();

        criteria.andPlatformEqualTo(platform);
        criteria.andBrokerIdEqualTo(brokerId);

        return this.versionInfoRepository.selectByExample(example);
    }

    @Override
    public DataGridPagerResult<VersionInfoDTO> getVersionInfoPagerInfo(DataGridPager<VersionInfoDTO> pager) {

        PageInfo pageInfo = pager.toPageInfo();
        ModelMapper mapper = new ModelMapper();
        VersionInfo versionInfo = mapper.map(pager.getQueryParameter(), VersionInfo.class);

        VersionInfoExample versionInfoExample = new VersionInfoExample();
        VersionInfoExample.Criteria criteria = versionInfoExample.createCriteria();

        if (Objects.nonNull(versionInfo.getBrokerId())) {
            criteria.andBrokerIdEqualTo(versionInfo.getBrokerId());
        }

        List<VersionInfo> versionInfos = this.getByPage(pageInfo, versionInfoExample);

        List<VersionInfoDTO> result = mapper.map(
                versionInfos, new TypeToken<List<VersionInfoDTO>>() {
                }.getType()
        );
        return new DataGridPagerResult<>(pageInfo.getTotals(), result);
    }
}
