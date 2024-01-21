package cc.newex.dax.boss.web.controller.outer.v1.integration;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.integration.client.WarningConfigClient;
import cc.newex.dax.integration.dto.admin.WarningConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 报警配置
 */
@Slf4j
@RestController
@RequestMapping("/v1/boss/integration/warning-config")
public class WarningConfigController {

    @Autowired
    private WarningConfigClient configClient;

    @GetMapping(value = "/list")
    @OpLog(name = "查询配置信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_WARNING_CONFIG_VIEW"})
    public ResponseResult list(final DataGridPager pager, @RequestParam(value = "code", required = false) String code, @RequestParam(value = "content", required = false) String content) {
        DataGridPager<WarningConfigDTO> item = new DataGridPager<>();
        item.setPage(pager.getPage());
        item.setRows(pager.getRows());
        WarningConfigDTO dto = WarningConfigDTO.builder().build();
        if (StringUtils.isNotEmpty(code)) {
            dto.setCode(code);
        }
        if (StringUtils.isNotEmpty(content)) {
            dto.setContent(content);
        }
        item.setQueryParameter(dto);
        ResponseResult result = configClient.getWarningConfig(item);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "添加报警配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_WARNING_CONFIG_ADD"})
    public ResponseResult add(@Valid final WarningConfigDTO dto) {
        final ResponseResult result = configClient.addConfig(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除报警配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_WARNING_CONFIG_REMOVE"})
    public ResponseResult remove(final Long id) {
        final ResponseResult result = configClient.deleteConfig(id);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑报警配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INTEGRATION_WARNING_CONFIG_EDIT"})
    public ResponseResult edit(@Valid final WarningConfigDTO dto) {
        final ResponseResult result = configClient.editConfig(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }
}
