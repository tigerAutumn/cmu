package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.AppDownloadPageExample;
import cc.newex.dax.extra.domain.cms.AppDownloadPage;
import org.springframework.stereotype.Repository;

/**
 * App下载页表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-08-08 11:00:26
 */
@Repository
public interface AppDownloadPageRepository extends CrudRepository<AppDownloadPage, AppDownloadPageExample, Long> {
}