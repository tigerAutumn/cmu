package cc.newex.dax.integration.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.conf.ProviderConfExample;
import cc.newex.dax.integration.domain.conf.ProviderConf;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-02
 */
public interface ProviderConfAdminService
        extends CrudService<ProviderConf, ProviderConfExample, Integer> {

    List<ProviderConf> getByPage(PageInfo pageInfo, String fieldName, String keyword, Integer brokerId);
}
