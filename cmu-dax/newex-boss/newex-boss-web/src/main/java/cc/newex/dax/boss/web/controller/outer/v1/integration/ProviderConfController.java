package cc.newex.dax.boss.web.controller.outer.v1.integration;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.integration.client.IntegrationAdminClient;
import cc.newex.dax.integration.dto.admin.ProviderConfDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 短信邮件提供者配置
 *
 * @author newex-team
 * @date 2018/5/28
 */
@Slf4j
@RestController
@RequestMapping("/v1/boss/integration/provider-conf")
public class ProviderConfController {

    @Autowired
    private IntegrationAdminClient messageAdminClient;

    @GetMapping(value = "/list")
    @OpLog(name = "查询信息提供者配置列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_PROVIDER_CONF_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager pager, final String fieldName, final String keyword) {
        final String field = StringUtils.defaultIfBlank(fieldName, "");
        final String word = StringUtils.defaultIfBlank(keyword, "");
        return this.messageAdminClient.getProviderConfList(pager, loginUser.getLoginBrokerId(), field, word);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "添加信息提供者配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_PROVIDER_CONF_ADD"})
    public ResponseResult add(@Valid final ProviderConfDTO dto) {
        final ResponseResult result = this.messageAdminClient.addProviderConf(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除信息提供者配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_PROVIDER_CONF_REMOVE"})
    public ResponseResult remove(final Integer id) {
        final ProviderConfDTO dto = ProviderConfDTO.builder()
                .id(id).build();
        final ResponseResult result = this.messageAdminClient.removeProviderConf(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑信息提供者配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_PROVIDER_CONF_EDIT"})
    public ResponseResult edit(@Valid final ProviderConfDTO dto) {
        final ResponseResult result = this.messageAdminClient.editProviderConf(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/{id}")
    @OpLog(name = "查看信息提供者配置详情")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_PROVIDER_CONF_DETAIL_VIEW"})
    public ResponseResult getById(@PathVariable("id") final Integer id) {
        final ResponseResult result = this.messageAdminClient.getProviderConf(null, id);
        return ResultUtil.getCheckedResponseResult(result);
    }
}
