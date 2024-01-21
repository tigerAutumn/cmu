package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.BannerNoticeExample;
import cc.newex.dax.extra.domain.cms.BannerNotice;
import org.springframework.stereotype.Repository;

/**
 * 首页banner广告表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-09-12 16:53:57
 */
@Repository
public interface BannerNoticeRepository extends CrudRepository<BannerNotice, BannerNoticeExample, Long> {
}