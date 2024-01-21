package cc.newex.dax.users.service.security;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserSecureEventExample;
import cc.newex.dax.users.domain.UserSecureEvent;

import java.util.List;

/**
 * 用户操作记录表 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface UserSecureEventService
        extends CrudService<UserSecureEvent, UserSecureEventExample, Long> {

    /**
     * @param userId
     * @return
     */
    List<UserSecureEvent> getLast10SecureEvents(final long userId);

    /**
     * 获取分页的安全记录
     *
     * @param pageInfo
     * @param userId
     * @return
     */
    List<UserSecureEvent> listByPage(PageInfo pageInfo, final long userId);

    /**
     * @param userId
     * @description 解除24小时限制
     * @date 2018/7/7 下午4:41
     */
    boolean free24HoursLimit(Long userId);

    /**
     * 判断是否24小时内修改过安全设置  true:是 false:否
     *
     * @param userId
     * @return
     */
    boolean withdraw24HoursLimit(Long userId);
}
