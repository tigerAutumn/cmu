package cc.newex.dax.boss.web.controller.outer.v1.extra.cgm;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.asset.dto.TransferRecordReqDto;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.util.JavaBeanUtil;
import cc.newex.dax.extra.client.ExtraProjectAdminClient;
import cc.newex.dax.extra.dto.cgm.*;
import cc.newex.dax.extra.enums.cgm.CurrencyTypeEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author gi
 * @date 8/9/18
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/cgm/project/check")
public class ProjectCheckController {

    @Autowired
    private ExtraProjectAdminClient extraProjectAdminClient;

    @Autowired
    private AdminServiceClient assetClient;

    @GetMapping(value = "/{status}/list")
    @OpLog(name = "按状态获取项目审核列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_LIST"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager<ProjectTokenInfoDTO> pager, @PathVariable(value = "status") final Byte status) {
        final ProjectTokenInfoDTO projectTokenInfoDTO = ProjectTokenInfoDTO.builder().status(status).brokerId(loginUser.getLoginBrokerId()).build();
        pager.setQueryParameter(projectTokenInfoDTO);

        final ResponseResult result = this.extraProjectAdminClient.listProjectTokenInfo(pager);

        return ResultUtil.getDataGridResult(result);
    }

    @GetMapping(value = "/info")
    @OpLog(name = "详情")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_INFO"})
    public ResponseResult info(@RequestParam(value = "id") final Long id) {
        final ResponseResult result = this.extraProjectAdminClient.getDetailInfo(id);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/pass")
    @OpLog(name = "初始通过")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_PASS"})
    public ResponseResult pass(final ProjectTokenInfoDTO dto) {
        final ResponseResult result = this.extraProjectAdminClient.pass(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/reject")
    @OpLog(name = "拒绝")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_REJECT"})
    public ResponseResult reject(final ProjectRejectReasonDTO dto) {
        final ResponseResult result = this.extraProjectAdminClient.saveProjectRejectReason(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/schedule")
    @OpLog(name = "排期")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_SCHEDULE"})
    public ResponseResult schedule(@RequestParam(value = "id") final Long id, @RequestParam(value = "onlineTime") final String time) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ProjectTokenInfoDTO dto = null;
        try {
            dto = ProjectTokenInfoDTO.builder()
                    .id(id)
                    .onlineTime(sdf.parse(time)).build();
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        final ResponseResult result = this.extraProjectAdminClient.schedule(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/online")
    @OpLog(name = "上线")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_ONLINE"})
    public ResponseResult online(final ProjectTokenInfoDTO dto) {
        final ResponseResult result = this.extraProjectAdminClient.online(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/getRejectReason")
    @OpLog(name = "查看拒绝理由")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_REASON"})
    public ResponseResult getRejectReason(@RequestParam(value = "id") final Long id) {
        final ResponseResult result = this.extraProjectAdminClient.getProjectRejectReasonByTokenInfoId(id);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/getDepositResult")
    @OpLog(name = "保证金结果")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_DEPOSIT"})
    public ResponseResult getDepositResult(@RequestParam(value = "id") final Long id) {
        final ResponseResult result = this.extraProjectAdminClient.getProjectPaymentRecordByTokenInfoId(id, CurrencyTypeEnum.DEPOSIT.getCode().byteValue());
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/getTokenResult")
    @OpLog(name = "糖果记录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_TOKEN"})
    public ResponseResult getTokenResult(@RequestParam(value = "userId") final Long userId,
                                         @RequestParam(value = "tokenUrl") final String tokenUrl,
                                         @RequestParam(value = "tokenSymbol") final String tokenSymbol) {
        final TransferRecordReqDto dto = TransferRecordReqDto.builder()
                .userId(userId)
                .address(tokenUrl)
                .currency(tokenSymbol)
                .status((byte) 3)
                .transferType("deposit").build();
        final ResponseResult result = this.assetClient.getTransferRecord(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping("/saveProjectInfo")
    @OpLog(name = "保存ProjectInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_ADD"})
    public ResponseResult saveProjectInfo(final ProjectInfoDTO projectInfoDTO) {
        final ResponseResult result = this.extraProjectAdminClient.saveProjectInfo(projectInfoDTO);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping("/updateProjectInfo")
    @OpLog(name = "更新ProjectInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_EDIT"})
    public ResponseResult updateProject(final ProjectInfo2DTO projectInfo2DTO) {
        final ResponseResult result = this.extraProjectAdminClient.updateProjectInfo(projectInfo2DTO);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/getProjectInfo")
    @OpLog(name = "获取编辑信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CGM_INFO"})
    public ResponseResult getProjectInfo(@RequestParam(value = "id") final Long id) throws Exception {
        final ResponseResult result = this.extraProjectAdminClient.getDetailInfo(id);
        final JSONObject jsonObject = (JSONObject) result.getData();
        final ProjectDetailInfoDTO detailInfoDTO = jsonObject.toJavaObject(ProjectDetailInfoDTO.class);
        final Map map1 = JavaBeanUtil.convertBeanToMap(detailInfoDTO.getProjectTokenInfoDTO());
        final Map map2 = JavaBeanUtil.convertBeanToMap(detailInfoDTO.getProjectApplyInfoDTO());
        map1.putAll(map2);
        final ProjectInfo2DTO projectInfo2DTO = (ProjectInfo2DTO) JavaBeanUtil.convertMap(ProjectInfo2DTO.class, map1);
        projectInfo2DTO.setTokenInfoId(detailInfoDTO.getProjectTokenInfoDTO().getId());
        projectInfo2DTO.setApplyInfoId(detailInfoDTO.getProjectApplyInfoDTO().getId());
        final String imgName = projectInfo2DTO.getImgName();
        final String whitePaper = projectInfo2DTO.getWhitePaper();
        projectInfo2DTO.setImgName(StringUtils.substring(imgName, imgName.lastIndexOf("/") + 1, imgName.lastIndexOf("?")));
        projectInfo2DTO.setWhitePaper(StringUtils.substring(whitePaper, whitePaper.lastIndexOf("/") + 1, whitePaper.lastIndexOf("?")));
        return ResultUtils.success(projectInfo2DTO);
    }


    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
