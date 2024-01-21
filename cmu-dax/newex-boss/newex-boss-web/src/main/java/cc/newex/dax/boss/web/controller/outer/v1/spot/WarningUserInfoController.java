package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.spot.WarningUserInfoVO;
import cc.newex.dax.spot.client.WarningAdminClient;
import cc.newex.dax.spot.dto.ccex.warning.WarningUserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2019-01-15
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/spot/warning/user-info")
public class WarningUserInfoController {

    @Autowired
    private WarningAdminClient warningAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增WarningUserInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WARNING_USER_INFO_ADD"})
    public ResponseResult add(@Valid final WarningUserInfoVO warningUserInfoVO, final HttpServletRequest request) {
        final WarningUserInfoDTO warningUserInfoDTO = WarningUserInfoDTO.builder()
                .email(warningUserInfoVO.getEmail())
                .mobile(warningUserInfoVO.getMobile())
                .username(warningUserInfoVO.getUsername())
                .type(warningUserInfoVO.getType())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.warningAdminClient.saveWarningUserInfo(warningUserInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改WarningUserInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WARNING_USER_INFO_EDIT"})
    public ResponseResult edit(@Valid final WarningUserInfoVO warningUserInfoVO, final Long id, final HttpServletRequest request) {
        final WarningUserInfoDTO warningUserInfoDTO = WarningUserInfoDTO.builder()
                .id(id)
                .email(warningUserInfoVO.getEmail())
                .mobile(warningUserInfoVO.getMobile())
                .username(warningUserInfoVO.getUsername())
                .type(warningUserInfoVO.getType())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.warningAdminClient.updateWarningUserInfo(warningUserInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取WarningUserInfo列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WARNING_USER_INFO_VIEW"})
    public ResponseResult list(@RequestParam(value = "email", required = false) final String email,
                               @RequestParam(value = "mobile", required = false) final String mobile,
                               final DataGridPager<WarningUserInfoDTO> pager) {
        final WarningUserInfoDTO.WarningUserInfoDTOBuilder builder = WarningUserInfoDTO.builder();

        if (email != null) {
            builder.email(email);
        }

        if (mobile != null) {
            builder.mobile(mobile);
        }

        final WarningUserInfoDTO warningUserInfoDTO = builder.build();
        pager.setQueryParameter(warningUserInfoDTO);

        final ResponseResult responseResult = this.warningAdminClient.listWarningUserInfo(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除WarningUserInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WARNING_USER_INFO_REMOVE"})
    public ResponseResult remove(@RequestParam("id") final Long id) {
        final ResponseResult result = this.warningAdminClient.removeWarningUserInfo(id);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
