package cc.newex.dax.integration.rest.controller.inner.v1;

import cc.newex.commons.dictionary.consts.BrokerIdConsts;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.integration.service.msg.MessageService;
import cc.newex.dax.integration.service.msg.enums.MessageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/inner/v1/integration/messages")
public class MessageController {
    @Resource
    private MessageService messageService;

    @PostMapping("")
    public ResponseResult<String> send(@RequestBody final MessageReqDTO dto) {
        Integer brokerId = dto.getBrokerId();

        if (brokerId == null || brokerId <= 0) {
            // 默认使用 cmx 的 brokerId
            brokerId = BrokerIdConsts.COIN_MEX;
        }

        if (StringUtils.startsWithIgnoreCase(dto.getTemplateCode(), MessageTypeEnum.MAIL.getName())) {
            this.messageService.sendEmailAsync(
                    dto.getTemplateCode(), dto.getLocale(), dto.getCountryCode(), dto.getEmail(), dto.getParams(), brokerId);
        } else {
            this.messageService.sendSmsAsync(
                    dto.getTemplateCode(), dto.getLocale(), dto.getCountryCode(), dto.getMobile(), dto.getParams(), brokerId);
        }

        return ResultUtils.success("OK");
    }
}
