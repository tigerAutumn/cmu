package cc.newex.dax.extra.rest.controller.outer.v1.common.cms;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cms.VersionInfo;
import cc.newex.dax.extra.dto.cms.VersionInfoDTO;
import cc.newex.dax.extra.rest.common.util.BrokerUtils;
import cc.newex.dax.extra.service.admin.cms.VersionInfoAdminService;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 版本信息 控制器类
 *
 * @author liutiejun
 * @date 2018-07-03
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/extra/cms/version-info")
public class VersionInfoController {

    @Autowired
    private VersionInfoAdminService versionInfoAdminService;

    /**
     * 根据platform查询
     *
     * @param platform
     * @param request
     * @return
     */
    @GetMapping(value = "/getByPlatform")
    public ResponseResult getByPlatform(@RequestParam("platform") final Integer platform, final HttpServletRequest request) {
        final Integer brokerId = BrokerUtils.getBrokerId(request);

        final VersionInfo versionInfo = this.getByPlatform(platform, brokerId);
        if (versionInfo == null) {
            return ResultUtils.success(null);
        }

        final ModelMapper mapper = new ModelMapper();
        final VersionInfoDTO versionInfoDTO = mapper.map(versionInfo, VersionInfoDTO.class);

        return ResultUtils.success(versionInfoDTO);
    }

    private VersionInfo getByPlatform(final Integer platform, final Integer brokerId) {
        final List<VersionInfo> versionInfoList = this.versionInfoAdminService.getByPlatform(platform, brokerId);

        if (CollectionUtils.isEmpty(versionInfoList)) {
            return null;
        }

        return versionInfoList.get(0);
    }

    /**
     * 根据user-agent下载对应的APP
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/download")
    public void downloadApp(final HttpServletRequest request, final HttpServletResponse response) {
        final String userAgentStr = request.getHeader("User-Agent");

        log.info("User-Agent: {}", userAgentStr);

        final UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);

        final OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        final OperatingSystem group = operatingSystem.getGroup();

        log.info("OperatingSystem: {}", group);

        final Integer brokerId = BrokerUtils.getBrokerId(request);

        VersionInfo versionInfo = null;

        if (group.equals(OperatingSystem.IOS)) {
            // 11 - iOS APP下载
            versionInfo = this.getByPlatform(11, brokerId);
        } else {
            // 22 - Android APP下载
            versionInfo = this.getByPlatform(22, brokerId);
        }

        if (versionInfo == null) {
            return;
        }

        try {
            response.setStatus(302);
            response.sendRedirect(versionInfo.getDownloadUrl());
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
