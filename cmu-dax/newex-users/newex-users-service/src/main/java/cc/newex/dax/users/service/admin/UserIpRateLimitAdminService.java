package cc.newex.dax.users.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserIpRateLimitExample;
import cc.newex.dax.users.domain.UserIpRateLimit;

import java.util.List;

/**
 * Api流量限制配置表 服务接口
 *
 * @author newex-team
 * @date 2018-06-18
 */
public interface UserIpRateLimitAdminService
        extends CrudService<UserIpRateLimit, UserIpRateLimitExample, Integer> {

    void refreshCache();

    /**
     * 分页查询
     *
     * @param pageInfo  分页参数
     * @param fieldName where 筛选字段名
     * @param keyword   where 筛选字段模糊匹配关键字
     * @param brokerId  券商id
     * @return 分页记录列表
     */
    List<UserIpRateLimit> getByPage(PageInfo pageInfo, String fieldName, String keyword, Integer brokerId);
}
