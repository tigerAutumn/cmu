package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.UserActivityRewardExample;
import cc.newex.dax.perpetual.domain.UserActivityReward;

import java.util.Date;
import java.util.List;

/**
 * 活动币领取表 服务接口
 *
 * @author newex-team
 * @date 2018-12-20 20:14:27
 */
public interface UserActivityRewardService extends CrudService<UserActivityReward, UserActivityRewardExample, Long> {

    List<UserActivityReward> getByPager(Integer brokerId, Long[] userIds, String[] currencyCodes,
                                        Date startTime, Date endTime, PageInfo pageInfo);

}