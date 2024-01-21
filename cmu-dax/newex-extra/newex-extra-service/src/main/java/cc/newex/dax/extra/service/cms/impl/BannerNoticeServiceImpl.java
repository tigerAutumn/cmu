package cc.newex.dax.extra.service.cms.impl;

import cc.newex.dax.extra.common.enums.BannerNoticeTypeEnum;
import cc.newex.dax.extra.criteria.cms.BannerNoticeExample;
import cc.newex.dax.extra.data.cms.BannerNoticeRepository;
import cc.newex.dax.extra.domain.cms.BannerNotice;
import cc.newex.dax.extra.service.cms.BannerNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页banner广告表 服务实现
 *
 * @author newex-team
 * @date 2018-04-09
 */
@Slf4j
@Service
public class BannerNoticeServiceImpl implements BannerNoticeService {

    @Autowired
    private BannerNoticeRepository bannerNoticeRepository;

    @Override
    public List<BannerNotice> getHomePageBanners(String locale, Integer brokerId) {
        return this.getByType(locale, BannerNoticeTypeEnum.BANNER.getCode(), brokerId);
    }

    @Override
    public List<BannerNotice> getHomePageNews(String locale, Integer brokerId) {
        return this.getByType(locale, BannerNoticeTypeEnum.NOTICE.getCode(), brokerId);
    }

    @Override
    public List<BannerNotice> getHomePageSplashes(String locale, Integer brokerId) {
        return this.getByType(locale, BannerNoticeTypeEnum.SPLASH.getCode(), brokerId);
    }

    @Override
    public List<BannerNotice> getByType(String locale, Integer type, Integer brokerId) {
        BannerNoticeExample bannerNoticeExample = new BannerNoticeExample();

        BannerNoticeExample.Criteria criteria = bannerNoticeExample.createCriteria();

        if (StringUtils.isNotBlank(locale)) {
            criteria.andLocaleEqualTo(locale);
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        criteria.andStatusEqualTo(1);
        criteria.andBrokerIdEqualTo(brokerId);

        bannerNoticeExample.setOrderByClause("sort desc");

        return this.bannerNoticeRepository.selectByExample(bannerNoticeExample);
    }

}