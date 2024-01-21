package cc.newex.dax.users.service.subaccount.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.users.criteria.UserInfoExample;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.dto.subaccount.SubAccountInfoDTO;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.subaccount.SubAccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 子账户绑定、解锁的操作记录表 服务实现
 *
 * @author newex-team
 * @date 2018-11-05 17:21:06
 */
@Slf4j
@Service
public class SubAccountInfoServiceImpl implements SubAccountInfoService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public List<SubAccountInfoDTO> getByParentId(final PageInfo pageInfo, final Long parentId, final Integer brokerId) {
        if (parentId == null || parentId < 0) {
            return null;
        }

        final UserInfoExample example = new UserInfoExample();
        final UserInfoExample.Criteria criteria = example.createCriteria();

        criteria.andParentIdEqualTo(parentId);

        final List<UserInfo> userInfoList = this.userInfoService.getByPage(pageInfo, example);

        if (CollectionUtils.isEmpty(userInfoList)) {
            return null;
        }

        final List<SubAccountInfoDTO> subAccountInfoDTOList = new ArrayList<>();

        userInfoList.stream().forEach(userInfo -> {
            final SubAccountInfoDTO subAccountInfoDTO = this.createSubAccountInfo(userInfo, brokerId);

            subAccountInfoDTOList.add(subAccountInfoDTO);
        });

        return subAccountInfoDTOList;
    }

    private SubAccountInfoDTO createSubAccountInfo(final UserInfo userInfo, final Integer brokerId) {
        if (Objects.isNull(userInfo)) {
            return null;
        }

        final SubAccountInfoDTO subAccountInfoDTO = SubAccountInfoDTO.builder()
                .parentId(userInfo.getParentId())
                .userId(userInfo.getId())
                .realName(userInfo.getRealName())
                .baseBTC(this.getTotalBalance(userInfo.getId(), brokerId))
                .build();

        return subAccountInfoDTO;
    }

    /**
     * 查看用户的资产估值
     *
     * @param userId
     * @param brokerId
     * @return
     */
    private BigDecimal getTotalBalance(final Long userId, final Integer brokerId) {
        return BigDecimal.ZERO;
    }
}