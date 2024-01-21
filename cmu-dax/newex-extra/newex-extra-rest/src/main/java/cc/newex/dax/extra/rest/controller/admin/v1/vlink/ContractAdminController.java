package cc.newex.dax.extra.rest.controller.admin.v1.vlink;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.vlink.ContractInfoExample;
import cc.newex.dax.extra.domain.vlink.ContractInfo;
import cc.newex.dax.extra.dto.vlink.VlinkAdminDTO;
import cc.newex.dax.extra.service.vlink.ContractInfoService;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author gi
 * @date 10/29/18
 */

@RequestMapping("/admin/v1/extra/vlink/contract")
@RestController
public class ContractAdminController {

    @Autowired
    private ContractInfoService contractInfoService;

    @PostMapping(value = "/list")
    public ResponseResult list(@RequestBody DataGridPager<VlinkAdminDTO> pager) {
        PageInfo pageInfo = pager.toPageInfo();
        ContractInfoExample example = new ContractInfoExample();
        ContractInfoExample.Criteria criteria = example.createCriteria();
        if(Objects.nonNull(pager.getQueryParameter())){
            VlinkAdminDTO paramInfo = pager.getQueryParameter();
            if(Objects.nonNull(paramInfo.getUserId())){
                criteria.andUserIdEqualTo(paramInfo.getUserId());
            }
             if(Objects.nonNull(paramInfo.getCurrencyId())){
                criteria.andCurrencyIdEqualTo(paramInfo.getCurrencyId());
            }
            if(StringUtils.isNotBlank(paramInfo.getEmail())){
                criteria.andEmailEqualTo(paramInfo.getEmail());
            }
        }
        List<ContractInfo> result = this.contractInfoService.getByPage(pageInfo, example);
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(),result));
    }
}
