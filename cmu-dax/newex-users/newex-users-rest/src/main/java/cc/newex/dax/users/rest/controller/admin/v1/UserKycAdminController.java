package cc.newex.dax.users.rest.controller.admin.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.dto.common.PageResultDTO;
import cc.newex.dax.users.dto.kyc.AdminUserKycAuditDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycFirstDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycSecondDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminListReqDTO;
import cc.newex.dax.users.service.admin.UserKycAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供给boss后台管理系统使用的接口服务
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/users/kyc")
public class UserKycAdminController {
    @Autowired
    private UserKycAdminService userKycAdminService;

    /**
     * 查询kyc列表信息
     *
     * @param userKycAdminListReqDTO
     * @return
     */
    @PostMapping(value = "/list")
    ResponseResult<PageResultDTO> list(@RequestBody final UserKycAdminListReqDTO userKycAdminListReqDTO) {
        final PageResultDTO list = this.userKycAdminService.list(userKycAdminListReqDTO);
        return ResultUtils.success(list);
    }

    /**
     * 获取kyc等级1详情信息
     *
     * @param userId
     * @param level
     * @return
     */
    @GetMapping(value = "/first/{userId}")
    ResponseResult<UserKycAdminKycFirstDetailDTO> firstDetail(@PathVariable("userId") final long userId, @RequestParam("level") final Integer level) {
        final UserKycAdminKycFirstDetailDTO detailDTO = this.userKycAdminService.selectKycAdminFirstDetail(userId, level);
        return ResultUtils.success(detailDTO);
    }

    /**
     * 获取 kyc 等级2的审核信息
     *
     * @param userId
     * @param level
     * @return
     */
    @GetMapping(value = "/second/{userId}")
    ResponseResult<UserKycAdminKycSecondDetailDTO> secondDetail(@PathVariable("userId") final long userId, @RequestParam("level") final Integer level) {
        final UserKycAdminKycSecondDetailDTO detailDTO = this.userKycAdminService.selectKycAdminSecondDetail(userId, level);
        return ResultUtils.success(detailDTO);
    }

    /**
     * kyc审核
     *
     * @param id
     * @param audit
     * @return
     */
    @PostMapping(value = "/audit/{id}")
    ResponseResult auditKyc(@PathVariable("id") final long id, @RequestBody final AdminUserKycAuditDTO audit) {
        audit.setId(id);
        final ResponseResult result = this.userKycAdminService.auditKyc(audit);
        return result;
    }

    /**
     * 法币出入金冻结与解冻 0:解冻,1:冻结
     *
     * @param userId
     * @param status
     * @return
     */
    @PostMapping(value = "/kyc/audit/{userId}")
    ResponseResult enableFiatStatus(@PathVariable("userId") final long userId, @RequestParam("status") final int status) {
        final Boolean result = this.userKycAdminService.enableFiatStatus(userId, status);
        if (BooleanUtils.isFalse(result)) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_FIAT_FREEZE_FAILED);
        }
        return ResultUtils.success();
    }

    /**
     * 删除kyc信息
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/delete/{userId}")
    public ResponseResult deleteKycInfo(@PathVariable("userId") final Long userId) {
        this.userKycAdminService.deleteKycInfo(userId);

        return ResultUtils.success();
    }
}