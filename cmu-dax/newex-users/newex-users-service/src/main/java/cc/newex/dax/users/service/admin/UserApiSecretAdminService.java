package cc.newex.dax.users.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserApiSecretExample;
import cc.newex.dax.users.domain.UserApiSecret;

import java.util.List;

/**
 * apikey表 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface UserApiSecretAdminService
        extends CrudService<UserApiSecret, UserApiSecretExample, Long> {

    /**
     * 获取api secret list
     *
     * @param pageInfo
     * @param userId
     * @param apiKey
     * @param brokerId
     * @return
     */
    List<UserApiSecret> listByPage(PageInfo pageInfo, Long userId, String apiKey, Integer brokerId);
}
