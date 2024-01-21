package cc.newex.dax.users.rest.controller.admin.v1.subaccount;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.dto.subaccount.SubAccountInfoDTO;
import cc.newex.dax.users.service.subaccount.SubAccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 子账户绑定、解锁的操作记录表 Admin Controller
 *
 * @author newex-team
 * @date 2018-11-05 17:21:06
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/users/subaccount/info")
public class SubAccountInfoAdminController {

    @Autowired
    private SubAccountInfoService subAccountInfoService;

    /**
     * 保存SubAccountInfo
     *
     * @param subAccountInfoDTO
     * @return
     */
    @PostMapping(value = "/saveSubAccountInfo")
    public ResponseResult saveSubAccountInfo(@RequestBody final SubAccountInfoDTO subAccountInfoDTO) {
        return ResultUtils.success();
    }

    /**
     * 更新SubAccountInfo
     *
     * @param subAccountInfoDTO
     * @return
     */
    @PostMapping(value = "/updateSubAccountInfo")
    public ResponseResult updateSubAccountInfo(@RequestBody final SubAccountInfoDTO subAccountInfoDTO) {
        return ResultUtils.success();
    }

    /**
     * List SubAccountInfo
     *
     * @param pager
     * @return
     */
    @PostMapping(value = "/listSubAccountInfo")
    public ResponseResult listSubAccountInfo(@RequestBody final DataGridPager<SubAccountInfoDTO> pager) {
        return ResultUtils.success();
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getSubAccountInfoById")
    public ResponseResult getSubAccountInfoById(@RequestParam("id") final Long id) {
        return ResultUtils.success();
    }

    /**
     * 删除SubAccountInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeSubAccountInfo")
    public ResponseResult removeSubAccountInfo(@RequestParam("id") final Long id) {
        return ResultUtils.success();
    }

}