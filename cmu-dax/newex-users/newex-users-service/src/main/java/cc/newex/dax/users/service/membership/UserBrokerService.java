package cc.newex.dax.users.service.membership;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserBrokerExample;
import cc.newex.dax.users.domain.UserBroker;
import cc.newex.dax.users.dto.membership.UserBrokerDTO;

import java.util.List;

/**
 * 券商表 服务接口
 *
 * @author newex-team
 * @date 2018-09-11 10:57:27
 */
public interface UserBrokerService extends CrudService<UserBroker, UserBrokerExample, Integer> {
    /**
     * 通过host获取brokerId
     *
     * @param host
     * @return
     */
    Integer getBrokerId(String host);

    /**
     * 通过host获取brokerId
     *
     * @param host
     * @return
     */
    Integer getBrokerIdFromCache(String host);

    Integer save(UserBroker userBroker);

    /**
     * 通过host获取券商对象
     *
     * @param host
     * @return
     */
    UserBroker getUserBrokerByHost(String host);

    /**
     * 获取券商列表
     *
     * @return
     */
    List<UserBrokerDTO> selectBrokerList();

    List<UserBrokerDTO> listByPage(PageInfo pageInfo, UserBrokerDTO queryParameter);

    boolean updateBroker(UserBrokerDTO dto);
}