package cc.newex.dax.boss.web.controller.outer.v1.users;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.users.client.UsersAdminClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * @author allen
 * @date 2018/6/4
 */
@RestController
@RequestMapping(value = "/v1/boss/users/api-secrets")
@Slf4j
public class UsersApiSecretController {

    @Autowired
    private UsersAdminClient usersAdminClient;

    @GetMapping("/{userId}")
    @OpLog(name = "获取api secrets 列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_API_SECRETS_VIEW"})
    public ResponseResult listByUser(final DataGridPager pager, @PathVariable("userId") final Long userId) {
        return this.usersAdminClient.getUserSecret(pager, userId);
    }

    @PostMapping(value = "/{id}")
    @OpLog(name = "编辑限流次数")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_API_SECRETS_EDIT"})
    public ResponseResult updateRateLimit(@PathVariable final Long id,
                                          @RequestParam final String rateLimit,
                                          @RequestParam(value = "ipWhiteLists", defaultValue = "") final String ipWhiteLists,
                                          @RequestParam(value = "authorities", defaultValue = "[]") final String authorities) {
        final String ips = StringUtils.remove(StringUtils.remove(ipWhiteLists, '\n'), '\r');
        final ResponseResult result = this.usersAdminClient.updateRateLimit(id, rateLimit, ips, authorities);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping("/list")
    @OpLog(name = "获取api secrets 列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_API_SECRETS_VIEW"})
    public ResponseResult listByUser(@CurrentUser final User loginUser, final DataGridPager pager, Long userId, String apiKey) {
        userId = NumberUtil.lte(userId, 0) ? 0L : userId;
        apiKey = StringUtils.defaultIfBlank(apiKey, StringUtils.EMPTY);
        final ResponseResult responseResult = this.usersAdminClient.getUserSecret(pager, userId, apiKey, loginUser.getLoginBrokerId());
        return ResultUtil.getDataGridResult(responseResult);
    }

}
