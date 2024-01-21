package cc.newex.dax.boss.web.controller.outer.v1.users;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.annotation.SiteUserId;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.users.client.UsersAdminClient;
import cc.newex.dax.users.dto.admin.GlobalFrozenConfigDTO;
import cc.newex.dax.users.dto.common.BizTypeEnum;
import cc.newex.dax.users.dto.common.GlobalFrozenEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author newex-team
 * @date 2018-07-10
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/users/frozen")
public class UsersFrozenController {
    @Autowired
    private UsersAdminClient usersAdminClient;

    @PostMapping("/{userId}/freeze")
    @OpLog(name = "用户冻结")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FREEZE"})
    public ResponseResult frozenUser(@PathVariable("userId") @SiteUserId final Long userId,
                                     @RequestParam("bizType") final String bizType,
                                     @RequestParam("remark") final String remark) {
        //冻结状态 0:解冻,1:冻结
        final BizTypeEnum bizTypeEnum = BizTypeEnum.forName(bizType);
        final ResponseResult result = this.usersAdminClient.frozen(bizTypeEnum.getName(), userId, 1, remark);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping("/{userId}/unfreeze")
    @OpLog(name = "用户解冻")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_UNFREEZE"})
    public ResponseResult unfrozenUser(@PathVariable("userId") @SiteUserId final Long userId,
                                       @RequestParam("bizType") final String bizType,
                                       @RequestParam("remark") final String remark) {
        //冻结状态 0:解冻,1:冻结
        final BizTypeEnum bizTypeEnum = BizTypeEnum.forName(bizType);
        final ResponseResult result = this.usersAdminClient.frozen(bizTypeEnum.getName(), userId, 0, remark);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/{userId}/freeze-email-24hours")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_UNFREEZE"})
    @OpLog(name = "24小时修改解冻")
    public ResponseResult editFreeze(@PathVariable("userId") @SiteUserId final Long userId) {
        final ResponseResult result = this.usersAdminClient.freeWithdrawLimit(userId);
        if (result.getCode() == 0) {
            return ResultUtils.success();
        } else {
            return ResultUtils.failure(result.getMsg());
        }
    }

    @GetMapping(value = "/global/list")
    @OpLog(name = "获取全局冻结状态列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FROZEN_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager pager) {
        try {
            final ResponseResult list = this.usersAdminClient.listGlobalFrozenStatus(loginUser.getLoginBrokerId());
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            log.error("list global frozen error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/global/add")
    @OpLog(name = "添加全局冻结状态")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FROZEN_ADD"})
    public ResponseResult add(final GlobalFrozenConfigDTO dto) {
        try {
            dto.setStatus(0);
            dto.setCreatedDate(new Date());
            final ResponseResult list = this.usersAdminClient.addGlobalFrozenStatus(dto);
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            log.error("add global frozen error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/global/edit")
    @OpLog(name = "编辑全局冻结状态")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FROZEN_EDIT"})
    public ResponseResult edit(final GlobalFrozenConfigDTO dto) {
        try {
            final ResponseResult list = this.usersAdminClient.editGlobalFrozenStatus(dto);
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            log.error("edit global frozen error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/global/remove")
    @OpLog(name = "删除全局冻结状态")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FROZEN_DELETE"})
    public ResponseResult remove(final Integer id) {
        try {
            final ResponseResult list = this.usersAdminClient.deleteGlobalFrozenStatus(id);
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            log.error("remove global frozen error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping("/global/freeze")
    @OpLog(name = "全局冻结")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FREEZE"})
    public ResponseResult frozenGlobal(@RequestParam("name") final String name) {
        //冻结状态 0:解冻,1:冻结
        final GlobalFrozenEnum globalFrozenEnum = GlobalFrozenEnum.forName(name);
        final ResponseResult result = this.usersAdminClient.editGlobalFrozenStatus(globalFrozenEnum.getName(), 1);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping("/global/unfreeze")
    @OpLog(name = "全局解冻")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_UNFREEZE"})
    public ResponseResult unfrozenGlobal(@RequestParam("name") final String name) {
        //冻结状态 0:解冻,1:冻结
        final GlobalFrozenEnum globalFrozenEnum = GlobalFrozenEnum.forName(name);
        final ResponseResult result = this.usersAdminClient.editGlobalFrozenStatus(globalFrozenEnum.getName(), 0);
        return ResultUtil.getCheckedResponseResult(result);
    }
}
