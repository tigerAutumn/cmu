package cc.newex.dax.extra.service.cms;

import cc.newex.dax.extra.domain.cms.BannerNotice;

import java.util.List;

/**
 * 首页banner广告表 服务接口
 *
 * @author mbg.generated
 * @date 2018-09-12 16:53:57
 */
public interface BannerNoticeService {

    List<BannerNotice> getHomePageBanners(String locale, Integer brokerId);

    List<BannerNotice> getHomePageNews(String locale, Integer brokerId);

    List<BannerNotice> getHomePageSplashes(String locale, Integer brokerId);

    List<BannerNotice> getByType(String locale, Integer type, Integer brokerId);

}