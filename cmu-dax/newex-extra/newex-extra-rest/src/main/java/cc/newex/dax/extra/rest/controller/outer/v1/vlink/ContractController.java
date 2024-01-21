package cc.newex.dax.extra.rest.controller.outer.v1.vlink;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.dto.vlink.PageInfo;
import cc.newex.dax.extra.service.vlink.ContractInfoService;
import cc.newex.dax.extra.vlink.VLinkServiceProvider;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gi
 * @date 10/19/18
 */

/**
 * 合约信息相关服务
 */
@RestController
@RequestMapping(value = "/v1/extra/public/vlink/contract")
public class ContractController {

    @Autowired
    VLinkServiceProvider provider;

    @Autowired
    ContractInfoService contractInfoService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @OpLog(name = "获取合约列表")
    public ResponseResult getList(PageInfo pageInfo, @RequestParam(value = "status", required = false) String status) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("page", pageInfo.getPage());
        params.put("size", pageInfo.getSize());
        if (!StringUtils.isBlank(status)) {
            params.put("status", "ACTIVE");
        }
        JSONObject result = provider.contractList(params);
        return ResultUtils.success(result);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @OpLog(name = "获取详情")
    public ResponseResult getList(@PathVariable(value = "id") int id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("contractId", id);
        JSONObject result = provider.contractInfo(params);
        return ResultUtils.success(result);
    }

}
