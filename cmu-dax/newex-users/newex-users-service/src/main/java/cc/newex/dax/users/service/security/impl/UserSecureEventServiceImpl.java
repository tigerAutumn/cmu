package cc.newex.dax.users.service.security.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.common.util.DateUtils;
import cc.newex.dax.users.criteria.UserSecureEventExample;
import cc.newex.dax.users.data.UserSecureEventRepository;
import cc.newex.dax.users.domain.UserSecureEvent;
import cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum;
import cc.newex.dax.users.service.security.UserSecureEventService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户操作记录表 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class UserSecureEventServiceImpl
        extends AbstractCrudService<UserSecureEventRepository, UserSecureEvent, UserSecureEventExample, Long>
        implements UserSecureEventService {
    @Autowired
    private UserSecureEventRepository userSecureEventRepository;

    @Override
    protected UserSecureEventExample getPageExample(final String fieldName, final String keyword) {
        final UserSecureEventExample example = new UserSecureEventExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<UserSecureEvent> getLast10SecureEvents(final long userId) {
        final UserSecureEventExample usExample = new UserSecureEventExample();
        usExample.createCriteria()
                .andUserIdEqualTo(userId);
        usExample.setOrderByClause(" id desc limit 10");
        return this.dao.selectByExample(usExample);
    }

    @Override
    public List<UserSecureEvent> listByPage(final PageInfo pageInfo, final long userId) {
        final UserSecureEventExample example = new UserSecureEventExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause(" id desc limit " + pageInfo.getPageSize());
        return this.getByPage(pageInfo, example);
    }

    @Override
    public boolean free24HoursLimit(final Long userId) {

        final List<String> list = Lists.newArrayList();
        /**
         *
         1、安全记录类型：
         修改登录密码
         修改手机绑定
         关闭手机验证
         关闭谷歌验证
         修改谷歌验证
         */
        list.add(BehaviorNameEnum.LOGIN_PWD_MODIFY.getName());
        list.add(BehaviorNameEnum.MOBILE_MODIFY.getName());
        list.add(BehaviorNameEnum.MOBILE_VERFICATION_CLOSE.getName());
        list.add(BehaviorNameEnum.GOOGLE_VERFICATION_CLOSE.getName());
        list.add(BehaviorNameEnum.GOOGLE_CODE_RESET.getName());

        final UserSecureEventExample example = new UserSecureEventExample();
        example.createCriteria().andUserIdEqualTo(userId).andBehaviorNameIn(list).andStatusEqualTo(0).
                andCreatedDateGreaterThan(DateUtils.addDays(new Date(), -1));
        final UserSecureEvent userSecureEvent = UserSecureEvent.builder().userId(userId).status(1).build();
        return this.userSecureEventRepository.updateByExample(userSecureEvent, example) > 0 ? true : false;

    }

    @Override
    public boolean withdraw24HoursLimit(final Long userId) {
        final List<String> list = Lists.newArrayList();
        /**
         *
         1、安全记录类型：
         修改登录密码
         修改手机绑定
         关闭手机验证
         关闭谷歌验证
         修改谷歌验证
         */
        list.add(BehaviorNameEnum.LOGIN_PWD_MODIFY.getName());
        list.add(BehaviorNameEnum.MOBILE_MODIFY.getName());
        list.add(BehaviorNameEnum.MOBILE_VERFICATION_CLOSE.getName());
        list.add(BehaviorNameEnum.GOOGLE_VERFICATION_CLOSE.getName());
        list.add(BehaviorNameEnum.GOOGLE_CODE_RESET.getName());

        final UserSecureEventExample example = new UserSecureEventExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andBehaviorNameIn(list)
                .andStatusEqualTo(0)
                .andCreatedDateGreaterThan(DateUtils.addDays(new Date(), -1));

        return this.userSecureEventRepository.countByExample(example) > 0 ? true : false;
    }

}