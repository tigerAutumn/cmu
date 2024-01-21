package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.extra.criteria.cms.BannerNoticeExample;
import cc.newex.dax.extra.data.cms.BannerNoticeRepository;
import cc.newex.dax.extra.domain.cms.BannerNotice;
import cc.newex.dax.extra.service.admin.cms.BannerNoticeAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 首页banner广告表 服务实现
 *
 * @author newex-team
 * @date 2018-04-09
 */
@Slf4j
@Service
public class BannerNoticeAdminServiceImpl implements BannerNoticeAdminService {

    @Autowired
    private BannerNoticeRepository bannerNoticeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(final BannerNotice bannerNotice) {
        return this.bannerNoticeRepository.insert(bannerNotice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long update(final BannerNotice bannerNotice) {
        this.bannerNoticeRepository.updateById(bannerNotice);
        return 1L;
    }

    @Override
    public List<BannerNotice> listByPage(final PageInfo pageInfo, final BannerNotice bannerNotice) {
        final BannerNoticeExample example = new BannerNoticeExample();
        final BannerNoticeExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(bannerNotice.getType())) {
            criteria.andTypeEqualTo(bannerNotice.getType());
        }

        if (Objects.nonNull(bannerNotice.getPlatform())) {
            criteria.andPlatformEqualTo(bannerNotice.getPlatform());
        }

        if (Objects.nonNull(bannerNotice.getStatus())) {
            criteria.andStatusEqualTo(bannerNotice.getStatus());
        }

        if (StringUtils.isNotBlank(bannerNotice.getLocale())) {
            criteria.andLocaleEqualTo(bannerNotice.getLocale());
        }

        if (Objects.nonNull(bannerNotice.getBrokerId())) {
            criteria.andBrokerIdEqualTo(bannerNotice.getBrokerId());
        }
        return this.bannerNoticeRepository.selectByPager(pageInfo, example);
    }

    @Override
    public List<BannerNotice> list(final BannerNotice bannerNotice) {
        final BannerNoticeExample bannerNoticeExample = new BannerNoticeExample();
        return this.bannerNoticeRepository.selectByExample(bannerNoticeExample);
    }

    @Override
    public Integer count(final BannerNotice bannerNotice) {
        final BannerNoticeExample bannerNoticeExample = new BannerNoticeExample();
        return this.bannerNoticeRepository.countByExample(bannerNoticeExample);
    }

    @Override
    public int updateStatus(final Long id, final Integer status) {
        final BannerNotice bannerNotice = this.getById(id);
        bannerNotice.setStatus(status);
        return this.bannerNoticeRepository.updateById(bannerNotice);
    }

    @Override
    public BannerNotice getById(final Long id) {
        return this.bannerNoticeRepository.selectById(id);
    }
}
