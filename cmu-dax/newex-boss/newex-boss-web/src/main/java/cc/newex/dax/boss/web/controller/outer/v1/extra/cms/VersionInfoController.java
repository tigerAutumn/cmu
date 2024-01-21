package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.cms.VersionInfoExtraVO;
import cc.newex.dax.extra.client.ExtraCmsVersionInfoAdminClient;
import cc.newex.dax.extra.dto.cms.VersionInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-07-03
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/cms/version-info")
public class VersionInfoController {

    @Autowired
    private ExtraCmsVersionInfoAdminClient extraCmsVersionInfoAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增VersionInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_VERSION_INFO_ADD"})
    public ResponseResult add(@Valid final VersionInfoExtraVO versionInfoExtraVO, final HttpServletRequest request) {

        try {
            final VersionInfoDTO versionInfoDTO = VersionInfoDTO.builder()
                    .platform(versionInfoExtraVO.getPlatform())
                    .newVersion(versionInfoExtraVO.getNewVersion())
                    .forceUpdate(versionInfoExtraVO.getForceUpdate())
                    .content(versionInfoExtraVO.getContent())
                    .downloadUrl(versionInfoExtraVO.getDownloadUrl())
                    .createdDate(new Date())
                    .updatedDate(new Date())
                    .brokerId(versionInfoExtraVO.getBrokerId())
                    .build();

            final ResponseResult result = this.extraCmsVersionInfoAdminClient.save(versionInfoDTO);

            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("add versionInfo api error: " + e.getMessage(), e);
        }

        return ResultUtils.success();
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改VersionInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_VERSION_INFO_EDIT"})
    public ResponseResult edit(@Valid final VersionInfoExtraVO versionInfoExtraVO, @RequestParam(value = "id", required = false) final Integer id,
                               final HttpServletRequest request) {

        try {

            final VersionInfoDTO versionInfoDTO = VersionInfoDTO.builder()
                    .id(id)
                    .platform(versionInfoExtraVO.getPlatform())
                    .newVersion(versionInfoExtraVO.getNewVersion())
                    .forceUpdate(versionInfoExtraVO.getForceUpdate())
                    .content(versionInfoExtraVO.getContent())
                    .downloadUrl(versionInfoExtraVO.getDownloadUrl())
                    .updatedDate(new Date())
                    .brokerId(versionInfoExtraVO.getBrokerId())
                    .build();

            final ResponseResult result = this.extraCmsVersionInfoAdminClient.update(versionInfoDTO);

            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("edit versionInfo api error: " + e.getMessage(), e);
        }

        return ResultUtils.success();
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取VersionInfo列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_VERSION_INFO_VIEW"})
    public ResponseResult list(@RequestParam(value = "platform", required = false) final Integer platform,
                               @CurrentUser final User loginUser,
                               final DataGridPager<VersionInfoDTO> pager) {

        try {
            final VersionInfoDTO.VersionInfoDTOBuilder builder = VersionInfoDTO.builder().brokerId(loginUser.getLoginBrokerId());

            if (platform != null && platform != -1) {
                builder.platform(platform);
            }

            final VersionInfoDTO versionInfoDTO = builder.build();
            pager.setQueryParameter(versionInfoDTO);

            final ResponseResult responseResult = this.extraCmsVersionInfoAdminClient.list(pager);

            return ResultUtil.getDataGridResult(responseResult);
        } catch (final Exception e) {
            log.error("get versionInfo list api error: " + e.getMessage(), e);
        }

        return ResultUtil.getDataGridResult();
    }

}
