package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cms.VersionInfo;
import cc.newex.dax.extra.dto.cms.VersionInfoDTO;
import cc.newex.dax.extra.service.admin.cms.VersionInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 版本信息 控制器类
 *
 * @author liutiejun
 * @date 2018-07-03
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/version-info")
public class VersionInfoAdminController {

    @Autowired
    private VersionInfoAdminService versionInfoAdminService;

    /**
     * 保存VersionInfo
     *
     * @param versionInfoDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/save")
    public ResponseResult save(@RequestBody final VersionInfoDTO versionInfoDTO, final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final VersionInfo versionInfo = mapper.map(versionInfoDTO, VersionInfo.class);

        final int save = this.versionInfoAdminService.add(versionInfo);

        return ResultUtils.success(save);
    }

    /**
     * 更新VersionInfo
     *
     * @param versionInfoDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/update")
    public ResponseResult update(@RequestBody final VersionInfoDTO versionInfoDTO, final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final VersionInfo versionInfo = mapper.map(versionInfoDTO, VersionInfo.class);

        final int update = this.versionInfoAdminService.editById(versionInfo);

        return ResultUtils.success(update);
    }

    /**
     * List VersionInfo
     *
     * @return
     */
    @PostMapping(value = "/list")
    public ResponseResult list(@RequestBody final DataGridPager<VersionInfoDTO> pager) {

        final DataGridPagerResult<VersionInfoDTO> result = this.versionInfoAdminService.getVersionInfoPagerInfo(pager);
        return ResultUtils.success(result);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/getById")
    public ResponseResult getVersionInfoById(@RequestParam("id") final Integer id, final HttpServletRequest request) {
        final VersionInfo versionInfo = this.versionInfoAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final VersionInfoDTO versionInfoDTO = mapper.map(versionInfo, VersionInfoDTO.class);

        return ResultUtils.success(versionInfoDTO);
    }

}
