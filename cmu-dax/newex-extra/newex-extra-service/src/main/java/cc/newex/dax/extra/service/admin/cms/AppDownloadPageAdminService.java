package cc.newex.dax.extra.service.admin.cms;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cms.AppDownloadPageExample;
import cc.newex.dax.extra.domain.cms.AppDownloadPage;
import cc.newex.dax.extra.dto.cms.AppDownloadPageDTO;

import java.util.List;

/**
 * App下载页表 服务接口
 *
 * @author liutiejun
 * @date 2018-08-08
 */
public interface AppDownloadPageAdminService extends CrudService<AppDownloadPage, AppDownloadPageExample, Long> {

    /**
     * 获取app download 的分页信息
     *
     * @param pager
     * @return
     */
    DataGridPagerResult<AppDownloadPageDTO> getAppDownloadPagerInfo(DataGridPager<AppDownloadPageDTO> pager);

    /**
     * 条件查询
     *
     * @param locale
     * @param uid
     * @return
     */
    List<AppDownloadPage> getByUid(String locale, String uid);

}
