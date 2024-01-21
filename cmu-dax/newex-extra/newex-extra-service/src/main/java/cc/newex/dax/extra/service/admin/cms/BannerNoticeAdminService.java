package cc.newex.dax.extra.service.admin.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.extra.domain.cms.BannerNotice;

import java.util.List;

/**
 * 首页banner广告表 服务接口
 *
 * @author newex-team
 * @date 2018-04-09
 */
public interface BannerNoticeAdminService {
    int save(BannerNotice params);

    Long update(BannerNotice params);

    List<BannerNotice> listByPage(PageInfo pageInfo, BannerNotice bannerNotice);

    List<BannerNotice> list(BannerNotice bannerNotice);

    Integer count(BannerNotice bannerNotice);

    int updateStatus(Long id, Integer status);

    BannerNotice getById(Long id);

}
