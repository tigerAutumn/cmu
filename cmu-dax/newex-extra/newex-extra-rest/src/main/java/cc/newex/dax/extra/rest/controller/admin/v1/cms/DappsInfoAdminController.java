package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cms.DappsInfo;
import cc.newex.dax.extra.dto.cms.DAppsAdminDTO;
import cc.newex.dax.extra.service.cms.DappsInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "/admin/v1/extra/cms/DApps")
public class DappsInfoAdminController {

    @Autowired
    private DappsInfoService dappsInfoService;

    @PostMapping(value = "/getApps")
    public ResponseResult<DataGridPagerResult<DAppsAdminDTO>> getAppsInfo(@RequestBody final DataGridPager<DAppsAdminDTO> pager) {

        final DataGridPagerResult<DAppsAdminDTO> result = this.dappsInfoService.getDAppPagerInfo(pager);
        return ResultUtils.success(result);
    }

    @PostMapping(value = "/add")
    public ResponseResult add(@RequestBody final DAppsAdminDTO dto) {
        final ModelMapper mapper = new ModelMapper();
        final DappsInfo vo = mapper.map(dto, DappsInfo.class);
        final int result = this.dappsInfoService.add(vo);
        if (result > 0) {
            return ResultUtils.success();
        } else {
            return ResultUtils.failure("save failure");
        }
    }

    @PostMapping(value = "/edit")
    public ResponseResult edit(@RequestBody final DAppsAdminDTO dto) {
        if (!Objects.nonNull(dto.getId())) {
            return ResultUtils.failure("Id is null");
        }
        final ModelMapper mapper = new ModelMapper();
        final DappsInfo vo = mapper.map(dto, DappsInfo.class);
        final int result = this.dappsInfoService.editById(vo);
        if (result > 0) {
            return ResultUtils.success();
        } else {
            return ResultUtils.failure("edit failure");
        }
    }
}
