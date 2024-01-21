package cc.newex.dax.boss.web.controller.outer.v1.integration;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.integration.client.IntegrationAdminClient;
import cc.newex.dax.integration.dto.admin.MessageTemplateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author allen
 * @date 2018/5/28
 * @des 短信邮件模板
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/integration/message-templates")
public class MessageTemplateController {

    @Autowired
    private IntegrationAdminClient messageAdminClient;

    @GetMapping(value = "/list")
    @OpLog(name = "查询短信模板列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_TEMPLATES_VIEW"})
    public ResponseResult list(final DataGridPager<MessageTemplateDTO> pager, final String subject, final String locale, final String type, final String code) {
        final MessageTemplateDTO messageTemplateDTO = MessageTemplateDTO.builder()
                .subject(subject)
                .locale(locale)
                .type(type)
                .code(code)
                .build();
        pager.setQueryParameter(messageTemplateDTO);
        final ResponseResult result = this.messageAdminClient.getMessageTemplates(pager);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "添加短信模板")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_TEMPLATES_ADD"})
    public ResponseResult add(final MessageTemplateDTO messageTemplateDTO) {
        final ResponseResult result = this.messageAdminClient.addMessageTemplate(messageTemplateDTO);
        this.messageAdminClient.refreshMessageTemplateCache();
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除短信模板")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_TEMPLATES_REMOVE"})
    public ResponseResult remove(final Integer id) {
        final ResponseResult result = this.messageAdminClient.removeMessageTemplate(id);
        this.messageAdminClient.refreshMessageTemplateCache();
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑短信模板")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_TEMPLATES_EDIT"})
    public ResponseResult edit(final MessageTemplateDTO messageTemplateDTO) {
        final ResponseResult result = this.messageAdminClient.editMessageTemplate(messageTemplateDTO);
        this.messageAdminClient.refreshMessageTemplateCache();
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/detail")
    @OpLog(name = "查看短信模板详情")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_TEMPLATES_VIEW"})
    public ResponseResult info(final Integer id) {
        final ResponseResult result = this.messageAdminClient.getMessageTemplate(id);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/refresh")
    @OpLog(name = "刷新短信模板缓存")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_TEMPLATES_REFRESH"})
    public ResponseResult refresh() {
        final ResponseResult result = this.messageAdminClient.refreshMessageTemplateCache();
        return ResultUtil.getCheckedResponseResult(result);
    }
}
