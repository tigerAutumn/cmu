package cc.newex.dax.boss.web.controller.outer.v1.extra.vlink;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.extra.client.ExtraVlinkCmsAdminClient;
import cc.newex.dax.extra.dto.vlink.VlinkAdminDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author gi
 * @date 11/16/18
 */
@RestController
@RequestMapping(value = "/v1/boss/extra/vlink")
public class VlinkController {

    @Autowired
    ExtraVlinkCmsAdminClient extraVlinkCmsAdminClient;

    @RequestMapping(value = "/list")
    @OpLog(name = "查看vlink合约购买转账记录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_VLINK_VIEW"})
    public ResponseResult list(DataGridPager<VlinkAdminDTO> pager,
                               @RequestParam(value = "userId",required = false)Long userId,
                               @RequestParam(value = "currencyId",required = false)Integer currencyId,
                               @RequestParam(value = "email",required = false)String email){
        VlinkAdminDTO dto = VlinkAdminDTO.builder().build();
        if(Objects.nonNull(userId)) {
            dto.setUserId(userId);
        }
        if(Objects.nonNull(email)) {
            dto.setEmail(email);
        }
        if(Objects.nonNull(currencyId)){
            dto.setCurrencyId(currencyId);
        }
        pager.setQueryParameter(dto);
        ResponseResult result = extraVlinkCmsAdminClient.list(pager);
        return ResultUtil.getCheckedResponseResult(result);
    }
}
