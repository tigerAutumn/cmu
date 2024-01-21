package cc.newex.dax.boss.web.controller.outer.v1.integration;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.integration.client.IntegrationAdminClient;
import cc.newex.dax.integration.dto.admin.MessageSendStatusDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author allen
 * @date 2018/5/28
 * @des 短信发送状态包括渠道
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/integration/message-send")
public class MessageSendController {

    @Autowired
    private IntegrationAdminClient messageAdminClient;

    @GetMapping(value = "/list")
    @OpLog(name = "查询短信发送状态列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_SEND_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager pager, final String channel, final Integer status, final String type) {
        final MessageSendStatusDetailDTO messageSendStatusDetailDTO = MessageSendStatusDetailDTO.builder()
                .channel(channel)
                .status(status)
                .type(type)
                .brokerId(loginUser.getLoginBrokerId())
                .build();
        pager.setQueryParameter(messageSendStatusDetailDTO);

        final ResponseResult result = this.messageAdminClient.getMessageSendStatusDetails(pager);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/detail")
    @OpLog(name = "查询短信发送状态详情")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_SEND_VIEW"})
    public ResponseResult info(final Long id) {
        final ResponseResult result = this.messageAdminClient.getMessageSendStatusDetail(id);
        return ResultUtil.getCheckedResponseResult(result);

    }
}
