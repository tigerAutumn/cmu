package cc.newex.dax.extra.rest.controller.outer.v1.common.cgm;

import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import cc.newex.dax.extra.common.exception.ExtraBizException;
import cc.newex.dax.extra.common.util.DateUtils;
import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import cc.newex.dax.extra.domain.cgm.ProjectRejectReason;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.dto.cgm.ProjectInfoDTO;
import cc.newex.dax.extra.dto.cgm.ProjectRejectReasonDTO;
import cc.newex.dax.extra.dto.cgm.ProjectTokenInfoDTO;
import cc.newex.dax.extra.dto.error.FieldErrorMessage;
import cc.newex.dax.extra.enums.cgm.ProjectStatusEnum;
import cc.newex.dax.extra.rest.common.util.BrokerUtils;
import cc.newex.dax.extra.service.cgm.ProjectRejectReasonService;
import cc.newex.dax.extra.service.cgm.ProjectService;
import cc.newex.dax.extra.service.cgm.ProjectTokenInfoService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author liutiejun
 * @date 2018-08-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/extra/cgm")
public class ProjectInfoController {

    @Autowired
    private AdminServiceClient adminServiceClient;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRejectReasonService projectRejectReasonService;

    @Autowired
    private ProjectTokenInfoService projectTokenInfoService;

    /**
     * 提交上币申请
     *
     * @param projectInfoDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/apply")
    public ResponseResult save(@RequestBody final ProjectInfoDTO projectInfoDTO, final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }
        final Integer brokerId = BrokerUtils.getBrokerId(request);

        final Long userId = userDetails.getUserId();
//        final Long userId = 21889575L;

        projectInfoDTO.setUserId(userId);

        this.validate(projectInfoDTO);

        final ProjectApplyInfo projectApplyInfo = new ModelMapper().map(projectInfoDTO, ProjectApplyInfo.class);
        final ProjectTokenInfo projectTokenInfo = new ModelMapper().map(projectInfoDTO, ProjectTokenInfo.class);

        this.setDefaultValue(projectApplyInfo);
        this.setDefaultValue(projectTokenInfo);

        projectApplyInfo.setBrokerId(brokerId);
        projectTokenInfo.setBrokerId(brokerId);

        final int save = this.projectService.save(projectTokenInfo, projectApplyInfo);

        if (save <= 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.ADD_ERROR);
        }

        return ResultUtils.success(save);
    }

    private void setDefaultValue(final ProjectApplyInfo projectApplyInfo) {
        //项目简介
        final String projectInfo = StringUtils.defaultString(projectApplyInfo.getProjectInfo(), "");

        // 项目阶段
        final String projectStage = StringUtils.defaultString(projectApplyInfo.getProjectStage(), "");

        // 项目目标
        final String projectObjective = StringUtils.defaultString(projectApplyInfo.getProjectObjective(), "");

        //项目进展
        final String projectProgress = StringUtils.defaultString(projectApplyInfo.getProjectProgress(), "");

        // 团队成员
        final String team = StringUtils.defaultString(projectApplyInfo.getTeam(), "");

        // 团队顾问
        final String teamCounselor = StringUtils.defaultString(projectApplyInfo.getTeamCounselor(), "");

        // 私募规则
        final String raiseRule = StringUtils.defaultString(projectApplyInfo.getRaiseRule(), "");

        // ico规则
        final String icoRule = StringUtils.defaultString(projectApplyInfo.getIcoRule(), "");

        // 项目钱包的说明
        final String wallet = StringUtils.defaultString(projectApplyInfo.getWallet(), "");

        projectApplyInfo.setProjectInfo(projectInfo);
        projectApplyInfo.setProjectStage(projectStage);
        projectApplyInfo.setProjectObjective(projectObjective);
        projectApplyInfo.setProjectProgress(projectProgress);
        projectApplyInfo.setTeam(team);
        projectApplyInfo.setTeamCounselor(teamCounselor);
        projectApplyInfo.setRaiseRule(raiseRule);
        projectApplyInfo.setIcoRule(icoRule);
        projectApplyInfo.setWallet(wallet);
    }

    private void setDefaultValue(final ProjectTokenInfo projectTokenInfo) {
        // 发币时间
        Date startTime = projectTokenInfo.getStartTime();
        if (startTime == null) {
            // TIMESTAMP has a range of '1970-01-01 00:00:01' UTC to '2038-01-19 03:14:07' UTC.
            startTime = DateUtils.getADate(2037, 12, 31, 0, 0, 0);
            projectTokenInfo.setStartTime(startTime);
        }

        // 上线时间
        Date onlineTime = projectTokenInfo.getOnlineTime();
        if (onlineTime == null) {
            // TIMESTAMP has a range of '1970-01-01 00:00:01' UTC to '2038-01-19 03:14:07' UTC.
            onlineTime = DateUtils.getADate(2037, 12, 31, 0, 0, 0);
            projectTokenInfo.setOnlineTime(onlineTime);
        }

        // 设置待审核状态
        projectTokenInfo.setStatus(ProjectStatusEnum.CHECK.getCode().byteValue());
    }

    private void validate(final ProjectInfoDTO projectInfoDTO) {
        log.info("dto: {}", new Gson().toJson(projectInfoDTO));

        final String tokenSymbol = projectInfoDTO.getTokenSymbol();
        final String token = projectInfoDTO.getToken();

        this.validateTokenSymbol(tokenSymbol);
        this.validateToken(token);
    }

    /**
     * 对币种的全称进行唯一性校验
     *
     * @param token
     */
    private void validateToken(final String token) {
        final ProjectTokenInfo projectTokenInfo = this.projectTokenInfoService.getByTokenAndNotStatus(token, ProjectStatusEnum.REJECT);

        if (projectTokenInfo != null) {
            // 已存在
            throw this.validateException("token", ExtraBizErrorCodeEnum.TOKEN_EXIST);
        }
    }

    /**
     * 对币种的简称进行唯一性校验
     *
     * @param tokenSymbol
     */
    private void validateTokenSymbol(final String tokenSymbol) {
        final ResponseResult<Boolean> result = this.adminServiceClient.checkCurrencyAvailable(tokenSymbol);

        Boolean exist = false;

        if (result != null && result.getCode() == 0) {
            exist = result.getData();
        }

        if (exist) {
            // 已存在
            throw this.validateException("tokenSymbol", ExtraBizErrorCodeEnum.TOKEN_SYMBOL_EXIST);
        }

        final ProjectTokenInfo projectTokenInfo = this.projectTokenInfoService.getByTokenSymbolAndNotStatus(tokenSymbol, ProjectStatusEnum.REJECT);
        if (projectTokenInfo != null) {
            // 已存在
            throw this.validateException("tokenSymbol", ExtraBizErrorCodeEnum.TOKEN_SYMBOL_EXIST);
        }
    }

    /**
     * 字段校验失败异常信息
     *
     * @param field
     * @param errorCodeEnum
     * @return
     */
    private ExtraBizException validateException(final String field, final ExtraBizErrorCodeEnum errorCodeEnum) {
        final ExtraBizException extraBizException = new ExtraBizException();

        final FieldErrorMessage fieldErrorMessage = FieldErrorMessage.builder()
                .field(field)
                .defaultMessage(errorCodeEnum.getMessage())
                .build();

        extraBizException.addFieldError(fieldErrorMessage);

        return extraBizException;
    }

    /**
     * 查看某一个用户的申请记录
     *
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseResult getProjectByUserId(final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        final Integer brokerId = BrokerUtils.getBrokerId(request);


        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final Long userId = userDetails.getUserId();

        final List<ProjectTokenInfo> list = this.projectTokenInfoService.listProjectByUserId(userId, brokerId);

        final ModelMapper mapper = new ModelMapper();

        final List<ProjectTokenInfoDTO> projectTokenInfoDTOS = mapper.map(
                list, new TypeToken<List<ProjectTokenInfoDTO>>() {
                }.getType()
        );

        return ResultUtils.success(projectTokenInfoDTOS);
    }

    /**
     * 查询拒绝理由
     *
     * @param tokenInfoId
     * @return
     */
    @GetMapping(value = "/getReason/{tokenInfoId}")
    public ResponseResult getProjectRejectReason(@PathVariable("tokenInfoId") @NotNull final Long tokenInfoId, final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final ProjectRejectReason projectRejectReason = this.projectRejectReasonService.getByTokenInfoId(tokenInfoId);

        final ModelMapper mapper = new ModelMapper();

        ProjectRejectReasonDTO projectRejectReasonDTO = null;
        if (Objects.nonNull(projectRejectReason)) {
            projectRejectReasonDTO = mapper.map(projectRejectReason, ProjectRejectReasonDTO.class);
        }

        return ResultUtils.success(projectRejectReasonDTO);
    }

    /**
     * 糖果支付
     *
     * @param tokenInfoId
     * @param request
     * @return
     */
    @PostMapping(value = "/sweets/{tokenInfoId}")
    public ResponseResult updateSweetsStatus(@PathVariable("tokenInfoId") @NotNull final Long tokenInfoId, final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final Long userId = userDetails.getUserId();

        final int update = this.projectTokenInfoService.updateSweetsStatus(tokenInfoId, userId);

        if (update <= 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.UPDATE_ERROR);
        }

        return ResultUtils.success(update);
    }

}