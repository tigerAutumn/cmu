package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.UserActivityRewardExample;
import cc.newex.dax.perpetual.data.UserActivityRewardRepository;
import cc.newex.dax.perpetual.domain.UserActivityReward;
import cc.newex.dax.perpetual.service.UserActivityRewardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 活动币领取表 服务实现
 *
 * @author newex-team
 * @date 2018-12-20 20:14:27
 */
@Slf4j
@Service
public class UserActivityRewardServiceImpl
        extends AbstractCrudService<UserActivityRewardRepository, UserActivityReward, UserActivityRewardExample, Long>
        implements UserActivityRewardService {

    @Autowired
    private UserActivityRewardRepository userActivityRewardRepository;

    @Override
    protected UserActivityRewardExample getPageExample(final String fieldName, final String keyword) {
        final UserActivityRewardExample example = new UserActivityRewardExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<UserActivityReward> getByPager(final Integer brokerId, final Long[] userIds, final String[] currencyCodes,
                                               final Date startTime, final Date endTime, final PageInfo pageInfo) {
        final UserActivityRewardExample example = new UserActivityRewardExample();
        final UserActivityRewardExample.Criteria criteria = example.createCriteria();

        if (ArrayUtils.isNotEmpty(userIds)) {
            criteria.andUserIdIn(Arrays.asList(userIds));
        }

        if (ArrayUtils.isNotEmpty(currencyCodes)) {
            criteria.andCurrencyCodeIn(Arrays.asList(currencyCodes));
        }

        if (startTime != null) {
            criteria.andModifyDateGreaterThanOrEqualTo(startTime);
        }

        if (endTime != null) {
            criteria.andModifyDateLessThan(endTime);
        }

        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }

        return this.getByPage(pageInfo, example);
    }

}