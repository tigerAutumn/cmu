package cc.newex.dax.boss.web.controller.outer.v1.users;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.annotation.SiteUserId;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.users.client.UsersAdminClient;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

/**
 * @author allen
 * @date 2018/5/30
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/users/usersInfo")
public class UsersInfoController {
    @Autowired
    private UsersAdminClient usersAdminClient;

    @GetMapping("/list")
    @OpLog(name = "分页获取用户列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser,
                               @RequestParam(value = "id", required = false) final Long id,
                               @RequestParam(value = "email", required = false) final String email,
                               @RequestParam(value = "mobile", required = false) final String mobile,
                               @RequestParam(value = "realName", required = false) final String realName,
                               @RequestParam(value = "beginTime", required = false) final String beginTimeStr,
                               @RequestParam(value = "endTime", required = false) final String endTimeStr,
                               @RequestParam(value = "uid", required = false) final String uid,
                               @RequestParam(value = "channel", required = false) final Integer channel,
                               final DataGridPager<UserInfoResDTO> pager) {

        Date startTime = null;
        Date endTime = null;

        if (StringUtils.isNotBlank(beginTimeStr)) {
            try {
                startTime = DateUtils.parseDate(beginTimeStr, "yyyy-MM-dd HH:mm:ss");
            } catch (final ParseException e) {
                log.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotBlank(endTimeStr)) {
            try {
                endTime = DateUtils.parseDate(endTimeStr, "yyyy-MM-dd HH:mm:ss");
            } catch (final ParseException e) {
                log.error(e.getMessage(), e);
            }
        }

        final UserInfoResDTO userInfoResDTO = UserInfoResDTO.builder()
                .id(id)
                .email(email)
                .mobile(mobile)
                .realName(realName)
                .startTime(startTime)
                .endTime(endTime)
                .uid(uid)
                .brokerId(loginUser.getLoginBrokerId())
                .channel(channel)
                .build();
        pager.setQueryParameter(userInfoResDTO);

        final ResponseResult result = this.usersAdminClient.list(pager);

        return ResultUtil.getDataGridResult(result);
    }

    @PutMapping("/{userId}/google-code/unbind")
    @OpLog(name = "解绑用户google码")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_UNBIND_GOOGLE"})
    public ResponseResult unbindGoogle(@CurrentUser final User loginUser,
                                       @PathVariable("userId") @SiteUserId final long userId) {
        final ResponseResult result = this.usersAdminClient.unbindGoogle(userId, loginUser.getLoginBrokerId());
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PutMapping("/{userId}/mobile/unbind")
    @OpLog(name = "解绑用户mobile")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_UNBIND_MOBILE"})
    public ResponseResult unbindMobile(@CurrentUser final User loginUser,
                                       @PathVariable("userId") @SiteUserId final long userId) {
        final ResponseResult result = this.usersAdminClient.unbindMobile(userId, loginUser.getLoginBrokerId());
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PutMapping(value = "/{userId}/email")
    @OpLog(name = "更换用户邮箱")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_UPDATE_EMAIL"})
    public ResponseResult updateUserEmail(@CurrentUser final User loginUser,
                                          @PathVariable("userId") @SiteUserId final long userId,
                                          @RequestParam("newEmail") final String newEmail) {
        final ResponseResult result = this.usersAdminClient.updateUserEmail(userId, newEmail, loginUser.getLoginBrokerId());
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/{userId}")
    @OpLog(name = "查看用户信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_VIEW"})
    public ResponseResult getUserInfo(@CurrentUser final User loginUser,
                                      @PathVariable(value = "userId") final Long userId) {
        final ResponseResult result = this.usersAdminClient.queryUserInfo(userId, loginUser.getLoginBrokerId());
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/{userId}/settings")
    @OpLog(name = "查看用户设置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_VIEW"})
    public ResponseResult getUserSettings(@PathVariable(value = "userId") final Long userId) {
        final ResponseResult result = this.usersAdminClient.getUserSettings(userId);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/{userId}/detail")
    @OpLog(name = "查看用户详细信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_VIEW"})
    public ResponseResult getUserDetailInfo(@CurrentUser final User loginUser, @PathVariable(value = "userId") final Long userId) {
        final ResponseResult result = this.usersAdminClient.getUserDetailInfo(userId, loginUser.getLoginBrokerId());
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/replacePhone")
    @OpLog(name = "换绑用户手机号")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_REPLACE_CELL_PHONE"})
    public ResponseResult replacePhone(final Long id, final String newPhone, final Integer brokerId) {

        return this.usersAdminClient.updateUserMobile(id, newPhone, brokerId);
    }
}
