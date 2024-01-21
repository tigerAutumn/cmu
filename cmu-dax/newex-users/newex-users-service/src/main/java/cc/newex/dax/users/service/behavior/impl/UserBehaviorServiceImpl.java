package cc.newex.dax.users.service.behavior.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserBehaviorExample;
import cc.newex.dax.users.data.UserBehaviorRepository;
import cc.newex.dax.users.domain.UserBehavior;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.domain.behavior.enums.BehaviorCheckRuleEnum;
import cc.newex.dax.users.domain.behavior.enums.BehaviorItemEnum;
import cc.newex.dax.users.domain.behavior.model.CheckBehaviorItem;
import cc.newex.dax.users.domain.behavior.model.UserBehaviorResult;
import cc.newex.dax.users.service.behavior.UserBehaviorService;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 行为操作配置表 服务实现
 *
 * @author newex-team
 * @date 2018-04-13
 */
@Slf4j
@Service
public class UserBehaviorServiceImpl
        extends AbstractCrudService<UserBehaviorRepository, UserBehavior, UserBehaviorExample, Integer>
        implements UserBehaviorService {

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private UserBehaviorService userBehaviorConfService;

    @Autowired
    private AppCacheService appCacheService;

    private static final Map<String, CheckBehaviorItem> BEHAVIOR_ITEM_MAP = Maps.newHashMapWithExpectedSize(3);

    static {
        //初始化行为项权重，主要为了行为规则检查
        //如果将来行为项组合比较多,可考虑使用一些简单的规则引擎
        BEHAVIOR_ITEM_MAP.put(
                BehaviorItemEnum.GOOGLE.getName(),
                CheckBehaviorItem.builder()
                        .name(BehaviorItemEnum.GOOGLE.getName())
                        .weight(100).build()
        );

        BEHAVIOR_ITEM_MAP.put(
                BehaviorItemEnum.EMAIL.getName(),
                CheckBehaviorItem.builder()
                        .name(BehaviorItemEnum.EMAIL.getName())
                        .weight(98).build()
        );

        BEHAVIOR_ITEM_MAP.put(
                BehaviorItemEnum.MOBILE.getName(),
                CheckBehaviorItem.builder()
                        .name(BehaviorItemEnum.MOBILE.getName())
                        .weight(99).build()
        );

    }

    @Override
    protected UserBehaviorExample getPageExample(final String fieldName, final String keyword) {
        final UserBehaviorExample example = new UserBehaviorExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @PostConstruct
    @Override
    public void loadUserBehaviorToCache() {
        try {
            final List<UserBehavior> list = this.userBehaviorConfService.getAll();

            list.forEach(x -> this.appCacheService.setUserBehaviorConf(x.getName(), x));
        } catch (final Exception e) {
            log.error("behaviorConfCache error ", e);
        }

    }

    @Override
    public UserBehavior getUserBehaviorFromCache(final String name) {
        return this.appCacheService.getUserBehaviorConf(name);
    }

    @Override
    public UserBehaviorResult getUserCheckRuleBehavior(final String name, final long userId) {
        final UserBehavior userBehavior = this.getUserBehaviorFromCache(name);
        return this.getUserCheckRuleBehavior(userBehavior, userId);
    }

    @Override
    public UserBehaviorResult getUserCheckRuleBehavior(final UserBehavior userBehavior, final long userId) {
        final UserBehaviorResult result = UserBehaviorResult.builder()
                .name(userBehavior.getName())
                .checkItems(Lists.newArrayList())
                .totalCheckItems(Lists.newArrayList())
                .build();

        final UserSettings userSettings = this.userSettingsService.getById(userId);
        if (Objects.isNull(userSettings)) {
            return result;
        }

        result.setCheckItems(this.getUserCheckBehaviorItems(userSettings, userBehavior));
        result.setTotalCheckItems(this.getUserCheckRuleBehaviorItems(userSettings));

        return result;
    }

    private List<CheckBehaviorItem> getUserCheckBehaviorItems(final UserSettings settings,
                                                              final UserBehavior userBehavior) {
        final List<CheckBehaviorItem> behaviorItems = this.getUserCheckRuleBehaviorItems(settings);
        if (CollectionUtils.isEmpty(behaviorItems)) {
            log.warn("google、mobile、email校验已关闭：{}", settings);

            return behaviorItems;
        }

        //需要所有项验证规则
        if (StringUtils.equalsIgnoreCase(BehaviorCheckRuleEnum.ALL.getName(), userBehavior.getCheckRule())) {
            return behaviorItems;
        }

        //需要单项验证规则，直接取最大权重
        return behaviorItems.subList(behaviorItems.size() - 1, behaviorItems.size());
    }

    private List<CheckBehaviorItem> getUserCheckRuleBehaviorItems(final UserSettings settings) {
        final List<CheckBehaviorItem> behaviorItems = Lists.newArrayListWithCapacity(3);

        if (BooleanUtils.toBoolean(settings.getGoogleAuthFlag())) {
            behaviorItems.add(BEHAVIOR_ITEM_MAP.get(BehaviorItemEnum.GOOGLE.getName()));
        }

        if (BooleanUtils.toBoolean(settings.getMobileAuthFlag())) {
            behaviorItems.add(BEHAVIOR_ITEM_MAP.get(BehaviorItemEnum.MOBILE.getName()));
        }

        if (BooleanUtils.toBoolean(settings.getEmailAuthFlag())) {
            behaviorItems.add(BEHAVIOR_ITEM_MAP.get(BehaviorItemEnum.EMAIL.getName()));
        }

        if (CollectionUtils.isNotEmpty(behaviorItems)) {
            behaviorItems.sort(Comparator.comparingInt(CheckBehaviorItem::getWeight));
        }

        return behaviorItems;
    }

}