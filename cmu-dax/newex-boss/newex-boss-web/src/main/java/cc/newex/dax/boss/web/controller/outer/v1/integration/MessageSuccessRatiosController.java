package cc.newex.dax.boss.web.controller.outer.v1.integration;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.integration.client.IntegrationAdminClient;
import cc.newex.dax.integration.dto.admin.MessageSuccessRatioDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author allen
 * @date 2018/5/28
 * @des 短信发送成功率统计
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/integration/message-success-ratios")
public class MessageSuccessRatiosController {

    @Autowired
    private IntegrationAdminClient messageAdminClient;

    @GetMapping(value = "/list")
    @OpLog(name = "查询短信成功到达率列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_MESSAGE_RATIOS_VIEW"})
    public ResponseResult list(final DataGridPager pager, final String type) {
        final MessageSuccessRatioDTO messageSuccessRatioDTO = MessageSuccessRatioDTO.builder()
                .type(type)
                .build();

        pager.setQueryParameter(messageSuccessRatioDTO);

        final ResponseResult result = this.messageAdminClient.getMessageSuccessRatios(pager);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
