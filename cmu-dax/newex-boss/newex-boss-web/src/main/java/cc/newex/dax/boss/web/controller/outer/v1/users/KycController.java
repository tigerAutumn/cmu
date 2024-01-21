package cc.newex.dax.boss.web.controller.outer.v1.users;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.annotation.SiteUserId;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.kyc.KycReasonUtils;
import cc.newex.dax.boss.web.common.kyc.KycSender;
import cc.newex.dax.boss.web.model.users.kyc.UserKycAdminKycDetailVO;
import cc.newex.dax.boss.web.model.users.kyc.UserKycReasonVO;
import cc.newex.dax.users.client.UsersAdminClient;
import cc.newex.dax.users.dto.common.PageResultDTO;
import cc.newex.dax.users.dto.common.ParamPageDTO;
import cc.newex.dax.users.dto.kyc.AdminUserKycAuditDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycFirstDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycSecondDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminListReqDTO;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author newex-jinlong
 * @date 2018-04-28
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/users/kyc")
public class KycController {

    @Autowired
    private UsersAdminClient usersAdminClient;

    @Autowired
    private KycSender kycSender;

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取用户列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser,
                               @RequestParam(value = "userProperty", required = false) final Integer userProperty,
                               @RequestParam(value = "userValue", required = false) final String userValue,
                               @RequestParam(value = "cardNumber", required = false) String cardNumber,
                               @RequestParam(value = "country", required = false) Integer country,
                               @RequestParam(value = "status", required = false) String status,
                               @RequestParam(value = "level", required = false) String level,
                               @RequestParam(value = "dealUserId", required = false) String dealUserId,
                               @RequestParam(value = "range", required = false) final Integer range,
                               @RequestParam(value = "beginTime", required = false) String beginTime,
                               @RequestParam(value = "endTime", required = false) String endTime,
                               final DataGridPager pager) {

        try {
            Long userId = null;
            String loginName = null;

            // 1-用户ID、2-用户邮箱、3-用户手机号
            if (userProperty != null && StringUtils.isNotBlank(userValue)) {
                if (userProperty == 1) {
                    userId = Long.parseLong(userValue);
                } else if (userProperty == 2) {
                    loginName = userValue;
                } else if (userProperty == 3) {
                    loginName = userValue;
                }

            }

            if (StringUtils.isBlank(cardNumber)) {
                cardNumber = null;
            }

            // 查询条件，选择国家、地区，0：全部，1：国内，2：国际
            if (country == null || country < 0) {
                country = 0;
            }

            if (StringUtils.isBlank(status)) {
                status = null;
            }

            if (StringUtils.isBlank(level)) {
                level = null;
            }

            if (StringUtils.isBlank(dealUserId)) {
                dealUserId = null;
            }

            if (StringUtils.isBlank(beginTime)) {
                beginTime = null;
            }

            if (StringUtils.isBlank(endTime)) {
                endTime = null;
            }

            String createdDateStart = null;
            String createdDateEnd = null;
            String updatedDateStart = null;
            String updatedDateEnd = null;

            if (range != null && range.equals(0)) {
                createdDateStart = beginTime;
                createdDateEnd = endTime;
            } else {
                updatedDateStart = beginTime;
                updatedDateEnd = endTime;
            }

            final ParamPageDTO ppd = new ParamPageDTO();
            ppd.setPage(pager.getPage());
            ppd.setPageSize(pager.getRows());
            final UserKycAdminListReqDTO reqDTO = UserKycAdminListReqDTO.builder()
                    .userId(userId)
                    .loginName(loginName)
                    .cardNumber(cardNumber)
                    .country(country)
                    .status(status)
                    .level(level)
                    .dealUserId(dealUserId)
                    .createdDateStart(createdDateStart)
                    .createdDateEnd(createdDateEnd)
                    .updatedDateStart(updatedDateStart)
                    .updatedDateEnd(updatedDateEnd)
                    .paramPageDTO(ppd)
                    .brokerId(loginUser.getLoginBrokerId())
                    .build();
            final ResponseResult<PageResultDTO> responseResult = this.usersAdminClient.list(reqDTO);

            return ResultUtil.getDataGridResult(responseResult);
        } catch (final Exception e) {
            log.error("get user list api error: " + e.getMessage(), e);
        }

        return ResultUtil.getDataGridResult();
    }

    @PostMapping(value = "/getKycUser")
    @OpLog(name = "根据用户ID查看kyc用户详情")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_VIEW"})
    public ResponseResult getKycUser(@RequestParam(value = "userId", required = false) final Long userId,
                                     @RequestParam(value = "level", required = false) final Integer level) {
        UserKycAdminKycDetailVO kycDetailVO = null;

        try {
            if (level == 1) {
                final ResponseResult<UserKycAdminKycFirstDetailDTO> firstResult = this.usersAdminClient.firstDetail(userId, level);

                if (firstResult != null && firstResult.getCode() == 0) {
                    final UserKycAdminKycFirstDetailDTO kycFirstDetailDTO = firstResult.getData();

                    kycFirstDetailDTO.setLevel(1);

                    kycDetailVO = this.getKycDetatil(kycFirstDetailDTO);
                }
            } else if (level == 2) {
                final ResponseResult<UserKycAdminKycSecondDetailDTO> secondResult = this.usersAdminClient.secondDetail(userId, level);

                if (secondResult != null && secondResult.getCode() == 0) {
                    final UserKycAdminKycSecondDetailDTO kycSecondDetailDTO = secondResult.getData();

                    kycSecondDetailDTO.setLevel(2);

                    kycDetailVO = this.getKycDetatil(kycSecondDetailDTO);
                }
            }
        } catch (final Exception e) {
            log.error(userId + " get kyc user api error: " + e.getMessage(), e);

            kycDetailVO = new UserKycAdminKycDetailVO();
        }

        return ResultUtils.success(kycDetailVO);
    }

    private UserKycAdminKycDetailVO getKycDetatil(final UserKycAdminKycFirstDetailDTO kycFirstDetailDTO) {
        UserKycAdminKycDetailVO kycDetailVO = null;

        if (kycFirstDetailDTO != null) {
            final ModelMapper mapper = new ModelMapper();

            kycDetailVO = mapper.map(kycFirstDetailDTO, UserKycAdminKycDetailVO.class);
        } else {
            kycDetailVO = new UserKycAdminKycDetailVO();
        }

        return kycDetailVO;
    }

    private UserKycAdminKycDetailVO getKycDetatil(final UserKycAdminKycSecondDetailDTO kycSecondDetailDTO) {
        UserKycAdminKycDetailVO kycDetailVO = null;

        if (kycSecondDetailDTO != null) {
            final ModelMapper mapper = new ModelMapper();

            kycDetailVO = mapper.map(kycSecondDetailDTO, UserKycAdminKycDetailVO.class);
        } else {
            kycDetailVO = new UserKycAdminKycDetailVO();
        }

        return kycDetailVO;
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    private UserInfoResDTO queryUserInfo(@CurrentUser final User loginUser, final Long userId) {
        if (userId == null) {
            return null;
        }
        final ResponseResult<UserInfoResDTO> result = this.usersAdminClient.queryUserInfo(userId, loginUser.getLoginBrokerId());
        if (result == null || result.getCode() != 0) {
            return null;
        }

        return result.getData();
    }

    @PostMapping(value = "/unpass")
    @OpLog(name = "kyc 审核不通过")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_UNPASS"})
    public ResponseResult unpass(@CurrentUser final User loginUser,
                                 @RequestParam(value = "id", required = false) final Long id,
                                 @RequestParam(value = "leavingMessage", required = false) final String leavingMessage,
                                 @RequestParam(value = "remarks", required = false) final String remarks,
                                 @RequestParam(value = "userId", required = false) @SiteUserId final Long userId,
                                 @RequestParam(value = "level", required = false) final Integer level,
                                 @RequestParam(value = "countryCode", required = false) final String countryCode,
                                 @RequestParam(value = "mobile", required = false) final String mobile,
                                 @RequestParam(value = "email", required = false) final String email,
                                 final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final AdminUserKycAuditDTO adminUserKycAuditDTO = AdminUserKycAuditDTO.builder()
                .id(id)
                .status(2)
                .remarks(remarks)
                .userId(userId)
                .dealUserId(Long.valueOf(user.getId()))
                .dealUserName(user.getAccount())
                .level(level)
                .rejectReason(leavingMessage)
                .build();

        final ResponseResult result = this.usersAdminClient.auditKyc(id, adminUserKycAuditDTO);

        final UserInfoResDTO userInfo = this.queryUserInfo(user, userId);

        this.kycSender.sendMessage(userInfo, countryCode, leavingMessage, 0, result);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/pass")
    @OpLog(name = "kyc 审核通过")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_PASS"})
    public ResponseResult pass(@RequestParam(value = "id", required = false) final Long id,
                               @RequestParam(value = "leavingMessage", required = false) final String leavingMessage,
                               @RequestParam(value = "remarks", required = false) final String remarks,
                               @RequestParam(value = "userId", required = false) @SiteUserId final Long userId,
                               @RequestParam(value = "level", required = false) final Integer level,
                               @RequestParam(value = "countryCode", required = false) final String countryCode,
                               @RequestParam(value = "mobile", required = false) final String mobile,
                               @RequestParam(value = "email", required = false) final String email,
                               final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final AdminUserKycAuditDTO adminUserKycAuditDTO = AdminUserKycAuditDTO.builder()
                .id(id)
                .status(1)
                .remarks(remarks)
                .userId(userId)
                .dealUserId(Long.valueOf(user.getId()))
                .dealUserName(user.getAccount())
                .level(level)
                .rejectReason(leavingMessage)
                .build();

        final ResponseResult result = this.usersAdminClient.auditKyc(id, adminUserKycAuditDTO);

        final UserInfoResDTO userInfo = this.queryUserInfo(user, userId);

        this.kycSender.sendMessage(userInfo, countryCode, leavingMessage, 1, result);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/listReason")
    @OpLog(name = "获取理由列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_VIEW"})
    public ResponseResult listReason(final String locale, final HttpServletRequest request) {
        final List<UserKycReasonVO> userKycReasonVOList = KycReasonUtils.getKycReason(locale);

        return ResultUtils.success(userKycReasonVOList);
    }

    @GetMapping(value = "/addReason")
    @OpLog(name = "添加理由")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_VIEW"})
    public ResponseResult addReason(final String reason, final HttpServletRequest request) {
        log.info("add reason: {}", reason);

        return ResultUtils.success();
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除kyc信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_REMOVE"})
    public ResponseResult deleteKyc(final Long userId) {
        return this.usersAdminClient.deleteKycInfo(userId);
    }

}
