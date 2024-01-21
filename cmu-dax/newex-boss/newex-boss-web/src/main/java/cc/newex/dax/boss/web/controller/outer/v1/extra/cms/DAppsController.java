package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.extra.client.ExtraCmsDappsAdminClient;
import cc.newex.dax.extra.dto.cms.DAppsAdminDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/boss/extra/cms/dApps")
@Slf4j
public class DAppsController {

    @Autowired
    private ExtraCmsDappsAdminClient extraCmsDappsAdminClient;

    @GetMapping(value = "/info")
    @OpLog(name = "查看列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_DAPPS_VIEW"})
    public ResponseResult getApps(@CurrentUser final User loginUser, final DataGridPager<DAppsAdminDTO> pager) {
        final DAppsAdminDTO appsAdminDTO = new DAppsAdminDTO();
        appsAdminDTO.setBrokerId(loginUser.getLoginBrokerId());
        pager.setQueryParameter(appsAdminDTO);
        final ResponseResult result = this.extraCmsDappsAdminClient.listApps(pager);
        return ResultUtil.getDataGridResult(result);
    }

    @PostMapping(value = "")
    public ResponseResult delete(@RequestParam(value = "appId") final Long appId) {
        return ResultUtils.success();
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_DAPPS_EDIT"})
    public ResponseResult edit(final DAppsAdminDTO dto) {
        final ResponseResult result = this.extraCmsDappsAdminClient.edit(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "添加列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_DAPPS_ADD"})
    public ResponseResult add(final DAppsAdminDTO dto) {
        final ResponseResult result = this.extraCmsDappsAdminClient.add(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

}
