package cc.newex.dax.integration.rest.controller.admin.v1;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.domain.conf.WarningConfig;
import cc.newex.dax.integration.dto.admin.WarningConfigDTO;
import cc.newex.dax.integration.service.admin.WarningConfigAdminService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author gigi
 */

@RestController
@RequestMapping(value = "/admin/v1/integration/warning/config")

public class WarningConfigController {

    @Autowired
    private WarningConfigAdminService warningConfigService;
    @PostMapping(value = "/list")
    public ResponseResult getConfig(@RequestBody DataGridPager<WarningConfigDTO> pager){
        PageInfo pageInfo = pager.toPageInfo();
        List<WarningConfig> list= this.warningConfigService.listByPage(pageInfo,pager.getQueryParameter());
        HashMap<String,Object> result = new HashMap<>(2);
        result.put("rows",list);
        result.put("total",pageInfo.getTotals());
        return ResultUtils.success(result);
    }

    @PostMapping(value = "/addConfig")
    public ResponseResult addConfig(@RequestBody WarningConfigDTO dto){
        WarningConfig po = WarningConfig.builder().build();
        if(StringUtils.isNotEmpty(dto.getCode())&&StringUtils.isNotEmpty(dto.getContent())){
            po.setCode(dto.getCode());
            po.setContent(dto.getContent());
        }else{
            return ResultUtils.failure("code error");
        }
        if(StringUtils.isNotEmpty(dto.getBizType())){
            po.setBizType(dto.getBizType());
        }
        if(StringUtils.isNotEmpty(dto.getMemo())){
            po.setMemo(dto.getMemo());
        }
        return ResultUtils.success(this.warningConfigService.add(po));

    }

    @PostMapping(value = "/editConfig")
    public ResponseResult editConfig(@RequestBody WarningConfigDTO dto){
        if(Objects.nonNull(dto.getId())) {
            ModelMapper mapper = new ModelMapper();
            WarningConfig po = mapper.map(dto, WarningConfig.class);
            return ResultUtils.success(this.warningConfigService.editById(po));
        }else{
            return ResultUtils.failure("edit error");
        }
    }

    @PostMapping(value = "/deleteConfig")
    public ResponseResult deleteConfig(@RequestParam(value = "id") Long id){
        return ResultUtils.success(this.warningConfigService.removeById(id));
    }

}
