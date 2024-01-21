package cc.newex.dax.boss.web.controller.outer.v1.integration;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.integration.client.IntegrationAdminClient;
import cc.newex.dax.integration.dto.admin.MessageWhitelistDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * @author allen
 * @date 2018/5/28
 * @des 短信邮件模板
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/integration/message-whitelist")
public class MessageWhitelistController {

    @Autowired
    private IntegrationAdminClient messageAdminClient;

    @GetMapping(value = "/list")
    @OpLog(name = "查询短信与邮件白名单列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_WHITELIST_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager pager, final String fieldName, final String keyword) {
        final String field = StringUtils.defaultIfBlank(fieldName, "");
        final String word = StringUtils.defaultIfBlank(keyword, "");
        return this.messageAdminClient.getMessageWhitelists(pager, loginUser.getLoginBrokerId(), field, word);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "添加短信与邮件白名单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_WHITELIST_ADD"})
    public ResponseResult add(@CurrentUser final User loginUser, final MessageWhitelistDTO dto) {
        final String nameStr = StringUtils.remove(dto.getName(), '\r');
        final String[] names = StringUtils.split(nameStr, '\n');
        if (ArrayUtils.getLength(names) >= 50) {
            return ResultUtils.failure("记录条数不能超过50行");
        }
        for (final String name : names) {
            dto.setName(name);
            this.messageAdminClient.addMessageWhitelist(dto);
        }
        return ResultUtils.success();
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除短信与邮件白名单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_WHITELIST_REMOVE"})
    public ResponseResult remove(@CurrentUser final User loginUser, final Integer id) {
        final MessageWhitelistDTO dto = MessageWhitelistDTO.builder()
                .id(id)
                .brokerId(loginUser.getLoginBrokerId())
                .build();
        final ResponseResult result = this.messageAdminClient.removeMessageWhitelist(dto);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑短信与邮件白名单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_WHITELIST_EDIT"})
    public ResponseResult edit(@CurrentUser final User loginUser, final MessageWhitelistDTO dto) {
        dto.setBrokerId(loginUser.getLoginBrokerId());
        final ResponseResult result = this.messageAdminClient.editMessageWhitelist(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/{id}")
    @OpLog(name = "查看短信与邮件白名单详情")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_WHITELIST_VIEW"})
    public ResponseResult getById(@CurrentUser final User loginUser, @PathVariable("id") final Integer id) {
        final ResponseResult result = this.messageAdminClient.getMessageWhitelist(loginUser.getLoginBrokerId(), id);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/refresh")
    @OpLog(name = "刷新短信与邮件白名单缓存")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_WHITELIST_REFRESH"})
    public ResponseResult refresh() {
        final ResponseResult result = this.messageAdminClient.refreshMessageWhitelistCache();
        return ResultUtil.getCheckedResponseResult(result);
    }

}
