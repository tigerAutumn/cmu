package cc.newex.dax.boss.web.controller.outer.v1.users;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.activity.client.InviteAdminClient;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.spot.client.SpotInviteActivityClient;
import cc.newex.dax.spot.dto.ccex.ActivityRecordDTO;
import cc.newex.dax.spot.dto.ccex.UserInviteRecordDTO;
import cc.newex.dax.users.client.UsersAdminClient;
import cc.newex.dax.users.dto.admin.UserInviteReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
@RequestMapping(value = "/v1/boss/user/invite")

public class InviteController {

    @Autowired
    SpotInviteActivityClient spotInviteActivityClient;
    @Autowired
    private UsersAdminClient usersAdminClient;
    @Autowired
    private InviteAdminClient inviteAdminClient;
    @GetMapping(value = "/user/{userId}")
    @OpLog(name = "用户详情")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_INVITE_VIEW"})
    public ResponseResult getInfo(@PathVariable(value = "userId") final Long userId) {
        final ResponseResult result = this.usersAdminClient.inviteDetail(userId);
        return ResultUtils.success(result.getData());
    }

    @GetMapping(value = "/userList")
    @OpLog(name = "获取全部用户")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_INVITE_VIEW"})
    public ResponseResult getUsers(final DataGridPager pager, @RequestParam(value = "userId", required = false) final Long userId,
                                   @RequestParam(value = "email", required = false) final String email,
                                   @RequestParam(value = "mobile", required = false) final String mobile,
                                   @RequestParam(value = "title", required = false) final String title) {
        final UserInviteReqDTO dto = UserInviteReqDTO.builder()
                .pageSize(pager.getRows())
                .lastUserId(0L).build();
        if (Objects.nonNull(userId)) {
            dto.setUserId(userId);
        }
        if (StringUtils.isNotBlank(email)) {
            dto.setEmail(email);
        }
        if (StringUtils.isNotBlank(mobile)) {
            dto.setMobile(mobile);
        }
        if (StringUtils.isNotBlank(title)) {
            dto.setUserLevel(title.toLowerCase());
        }
        final ResponseResult result = this.usersAdminClient.inviteList(dto);
        if (result.getCode() == 0 && result.getData() != null) {
            return ResultUtil.getDataGridResult(result);
        }
        return ResultUtils.failure(result.getMsg());

    }

    @GetMapping(value = "/rewardList/{userId}")
    @OpLog(name = "获取已发放奖励")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_INVITE_VIEW"})
    public ResponseResult getReward(final DataGridPager pager, @PathVariable(value = "userId") final Long userId) {
        final ResponseResult<DataGridPagerResult<ActivityRecordDTO>> source = this.spotInviteActivityClient.awardList(pager, userId);
        if (source.getCode() == 0 && source.getData() != null) {
            return ResultUtil.getDataGridResult(source);
        }
        return ResultUtils.failure(source.getMsg());
    }

    @GetMapping(value = "/inviteList/{userId}")
    @OpLog(name = "获取邀请人数列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_INVITE_VIEW"})
    public ResponseResult getInvite(@CurrentUser final User loginUser, final DataGridPager pager, @PathVariable(value = "userId") final Long userId) {
        final ResponseResult<DataGridPagerResult<UserInviteRecordDTO>> source = this.spotInviteActivityClient.list(pager, userId, loginUser.getLoginBrokerId());
        if (source.getCode() == 0 && source.getData() != null) {
            return ResultUtil.getDataGridResult(source);
        }
        return ResultUtils.failure(source.getMsg());
    }

    @PostMapping(value = "/operationLevel")
    @OpLog(name = "操作用户身份")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_INVITE_EDIT"})
    public ResponseResult deleteTitle(@RequestParam(value = "userId") final Long userId) {
        if (Objects.nonNull(userId)) {
            final ResponseResult result = this.inviteAdminClient.delete(userId);
            if (result.getCode() == 0) {
                return ResultUtils.success();
            }
            return ResultUtils.failure(result.getMsg());
        }
        return ResultUtils.failure("userId is null");
    }

    @PostMapping(value = "/coWork")
    @OpLog(name = "添加合伙人")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_INVITE_ADD"})
    public ResponseResult addCoWork(@RequestParam(value = "userId") final Long userId) {
        log.info("" + userId);
        if (Objects.nonNull(userId)) {
            final ResponseResult result = this.inviteAdminClient.apply("partner", userId);
            if (result.getCode() == 0) {
                return ResultUtils.success();
            }
            return ResultUtils.failure(result.getMsg());
        }
        return ResultUtils.failure("userId is null");
    }


}
