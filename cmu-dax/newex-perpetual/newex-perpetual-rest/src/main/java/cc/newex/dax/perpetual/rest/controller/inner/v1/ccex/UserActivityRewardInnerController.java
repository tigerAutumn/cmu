package cc.newex.dax.perpetual.rest.controller.inner.v1.ccex;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.domain.UserActivityReward;
import cc.newex.dax.perpetual.dto.UserActivityRewardDTO;
import cc.newex.dax.perpetual.service.UserActivityRewardService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liutiejun
 * @date 2018-12-21
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/v1/perpetual/activity-reward")
public class UserActivityRewardInnerController {

    @Autowired
    private UserActivityRewardService userActivityRewardService;

    @GetMapping(value = "/list")
    public ResponseResult<DataGridPagerResult<UserActivityRewardDTO>> list(
            @RequestParam(value = "brokerId", required = false) final Integer brokerId,
            @RequestParam(value = "userIds", required = false) final Long[] userIds,
            @RequestParam(value = "currencyCodes", required = false) final String[] currencyCodes,
            @RequestParam(value = "startTime", required = false) final Long startTimeMills,
            @RequestParam(value = "endTime", required = false) final Long endTimeMills,
            @RequestParam(value = "page", required = false, defaultValue = "1") final Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "100") final Integer pageSize) {

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
        pageInfo.setStartIndex((page - 1) * pageSize);
        pageInfo.setPageSize(pageSize);

        Date startTime = null;
        Date endTime = null;

        if (startTimeMills != null && startTimeMills > 0L) {
            startTime = new Date(startTimeMills);
        }

        if (endTimeMills != null && endTimeMills > 0L) {
            endTime = new Date(endTimeMills);
        }

        final List<UserActivityReward> userActivityRewardList = this.userActivityRewardService.getByPager(brokerId, userIds, currencyCodes, startTime, endTime, pageInfo);

        final List<UserActivityRewardDTO> userActivityRewardDTOList = convert(userActivityRewardList);

        return ResultUtils.success(new DataGridPagerResult(pageInfo.getTotals(), userActivityRewardDTOList));
    }

    private static List<UserActivityRewardDTO> convert(final List<UserActivityReward> userActivityRewardList) {
        if (CollectionUtils.isEmpty(userActivityRewardList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        final ModelMapper mapper = new ModelMapper();

        return userActivityRewardList.stream().map(userActivityReward -> mapper.map(userActivityReward, UserActivityRewardDTO.class)).collect(Collectors.toList());
    }

}
