package cc.newex.dax.boss.web.controller.outer.v1.integration;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.integration.client.IntegrationAdminClient;
import cc.newex.dax.integration.dto.admin.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * @author allen
 * @date 2018/5/28
 * @des 消息信息明细
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/integration/messages")
public class MessagesController {

    @Autowired
    private IntegrationAdminClient messageAdminClient;

    @GetMapping(value = "/list")
    @OpLog(name = "查询短信信息列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGES_VIEW"})
    public ResponseResult list(@CurrentUser final User user, final DataGridPager pager, @RequestParam(value = "phoneNumber", required = false) final String phoneNumber,
                               @RequestParam(value = "emailaddress", required = false) final String emailaddress,
                               @RequestParam(value = "sent", required = false) final Boolean sent,
                               @RequestParam(value = "locale", required = false) final String locale) {

        final MessageDTO messageDTO = MessageDTO.builder()
                .phoneNumber(phoneNumber)
                .emailAddress(emailaddress)
                .sent(sent)
                .locale(locale)
                .brokerId(user.getLoginBrokerId())
                .build();
        pager.setQueryParameter(messageDTO);
        final ResponseResult result = this.messageAdminClient.getMessages(pager);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping("/remove")
    @OpLog(name = "删除短信信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGES_REMOVE"})
    public ResponseResult remove(@CurrentUser final User user, final Long id) {
        final MessageDTO dto = MessageDTO.builder()
                .id(id)
                .brokerId(user.getLoginBrokerId()).build();
        final ResponseResult result = this.messageAdminClient.removeMessage(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping("/getmessage")
    @OpLog(name = "获取详情信息")
    public ResponseResult getMessage(@CurrentUser final User user, final Long id) {
        final ResponseResult result = this.messageAdminClient.getMessage(user.getLoginBrokerId(), id);
        return ResultUtil.getCheckedResponseResult(result);
    }

}
