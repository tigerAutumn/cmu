package cc.newex.dax.boss.web.controller.outer.v1.c2c;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.c2c.UserVO;
import cc.newex.dax.c2c.client.C2CUserAdminClient;
import cc.newex.dax.c2c.dto.admin.UserDTO;
import cc.newex.dax.c2c.dto.admin.UserQueryDTO;
import cc.newex.dax.c2c.dto.common.PagedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author newex-jinlong
 * @date 2018-04-28
 */
@RestController
@RequestMapping(value = "/v1/boss/c2c/vendors")
@Slf4j
public class VendorsController {

    @Autowired
    private C2CUserAdminClient c2CUserAdminClient;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_VENDORS_VIEW"})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @OpLog(name = "查看商家列表")
    public ResponseResult list(final DataGridPager pager,
                               @RequestParam(value = "userId", required = false) final Long userId,
                               @RequestParam(value = "isCertifiedUser", required = false) final Integer isCertifiedUser) {
        final UserQueryDTO userQueryDTO = UserQueryDTO.builder()
                .page(pager.getPage())
                .pageSize(pager.getRows())
                .build();
        if (userId != null) {
            userQueryDTO.setUserId(userId);
        }
        if (isCertifiedUser != null && isCertifiedUser == 0) {
            userQueryDTO.setIsCertifiedUser(false);
        }
        if (isCertifiedUser != null && isCertifiedUser == 1) {
            userQueryDTO.setIsCertifiedUser(true);
        }
        final ResponseResult<PagedList<UserDTO>> result = this.c2CUserAdminClient.getUserList(userQueryDTO);
        log.info(result.getData().getItems().toString() + "pageall" + result.getData().getTotal());
        final Map<String, Object> modelMap = new HashMap<>(2);
        if (result != null && result.getCode() == 0) {
            modelMap.put("total", result.getData().getTotal());
            modelMap.put("rows", result.getData().getItems());
            return ResultUtils.success(modelMap);
        }
        return ResultUtils.success();

    }

    @PostMapping(value = "/updateUser")
    @OpLog(name = "升级用户为商家")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_VENDORS_UPGRADE"})
    public ResponseResult update(@Valid final UserVO user) {
        final UserDTO userDTO = UserDTO.builder()
                .userId(user.getUserId())
                .tags(user.getTags())
                .remark(user.getRemark())
                .build();
        if (user.getUpgrade() != null && user.getUpgrade()) {
            userDTO.setIsCertifiedUser(true);
        }
        final ResponseResult<Boolean> responseResult = this.c2CUserAdminClient.updateUser(user.getUserId(), userDTO);
        return ResultUtil.getCheckedResponseResult(responseResult);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @OpLog(name = "冻结商家")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_VENDORS_FREEZE"})
    public ResponseResult freeze(@RequestParam(value = "userId") final long userId, @RequestParam(value = "status") final Boolean status) {
        try {
            final UserDTO userDTO = UserDTO.builder()
                    .userId(userId)
                    .disabled(!status).build();
            final ResponseResult responseResult = this.c2CUserAdminClient.updateUser(userId, userDTO);
            return ResultUtil.getCheckedResponseResult(responseResult);
        } catch (final Exception e) {
            log.error("error", e);
        }
        return ResultUtils.success();
    }


}
