package cc.newex.dax.extra.rest.controller.outer.v1.common.customer;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.customer.WorkOrderMenuExample;
import cc.newex.dax.extra.domain.customer.WorkOrderMenu;
import cc.newex.dax.extra.service.customer.WorkOrderMenuService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 工单菜单表 控制器类
 *
 * @author newex-team
 * @date 2018-06-08
 */

@Slf4j
@RestController
@RequestMapping(value = "/v1/extra/customer/work-order-menus")
public class WorkOrderMenuController {
    @Autowired
    private WorkOrderMenuService workOrderMenuService;

    @GetMapping(value = "/menu")
    public ResponseResult get(@RequestParam(value = "parentId") Integer parentId) {
        WorkOrderMenuExample workOrderMenuExample = new WorkOrderMenuExample();
        WorkOrderMenuExample.Criteria criteria = workOrderMenuExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<WorkOrderMenu> worKOrderMenuList = this.workOrderMenuService.getByExample(workOrderMenuExample);
        JSONArray jsonArray = new JSONArray();
        worKOrderMenuList.forEach(menu -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", menu.getId());
            jsonObject.put("name", menu.getName());
            jsonArray.add(jsonObject);
        });
        return ResultUtils.success(jsonArray);
    }

}