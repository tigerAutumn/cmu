package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.cgm.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liutiejun
 * @date 2018-08-08
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/project")
public interface ExtraProjectAdminClient {

    /**
     * 保存ProjectInfoDTO
     *
     * @param projectInfoDTO
     * @return
     */
    @PostMapping("/info/saveProjectInfo")
    ResponseResult saveProjectInfo(@RequestBody final ProjectInfoDTO projectInfoDTO);

    /**
     * 更新ProjectInfoDTO
     *
     * @param projectInfo2DTO
     * @return
     */
    @PostMapping("/info/updateProjectInfo")
    ResponseResult updateProjectInfo(@RequestBody final ProjectInfo2DTO projectInfo2DTO);

    /**
     * 保存ProjectApplyInfo
     *
     * @param projectApplyInfoDTO
     * @return
     */
    @PostMapping(value = "/apply-info/saveProjectApplyInfo")
    ResponseResult saveProjectApplyInfo(@RequestBody final ProjectApplyInfoDTO projectApplyInfoDTO);

    /**
     * 更新ProjectApplyInfo
     *
     * @param projectApplyInfoDTO
     * @return
     */
    @PostMapping(value = "/apply-info/updateProjectApplyInfo")
    ResponseResult updateProjectApplyInfo(@RequestBody final ProjectApplyInfoDTO projectApplyInfoDTO);

    /**
     * 删除ProjectApplyInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/apply-info/removeProjectApplyInfo")
    ResponseResult removeProjectApplyInfo(@RequestParam("id") final Long id);

    /**
     * List ProjectApplyInfo
     *
     * @return
     */
    @PostMapping(value = "/apply-info/listProjectApplyInfo")
    ResponseResult listProjectApplyInfo(@RequestBody final DataGridPager<ProjectApplyInfoDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/apply-info/getProjectApplyInfoById")
    ResponseResult getProjectApplyInfoById(@RequestParam("id") final Long id);

    /**
     * 保存ProjectRejectReason
     *
     * @param projectRejectReasonDTO
     * @return
     */
    @PostMapping(value = "/reject-reason/saveProjectRejectReason")
    ResponseResult saveProjectRejectReason(@RequestBody final ProjectRejectReasonDTO projectRejectReasonDTO);

    /**
     * 更新ProjectRejectReason
     *
     * @param projectRejectReasonDTO
     * @return
     */
    @PostMapping(value = "/reject-reason/updateProjectRejectReason")
    ResponseResult updateProjectRejectReason(@RequestBody final ProjectRejectReasonDTO projectRejectReasonDTO);

    /**
     * 删除ProjectRejectReason
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/reject-reason/removeProjectRejectReason")
    ResponseResult removeProjectRejectReason(@RequestParam("id") final Long id);

    /**
     * List ProjectRejectReason
     *
     * @return
     */
    @PostMapping(value = "/reject-reason/listProjectRejectReason")
    ResponseResult listProjectRejectReason(@RequestBody final DataGridPager<ProjectRejectReasonDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/reject-reason/getProjectRejectReasonById")
    ResponseResult getProjectRejectReasonById(@RequestParam("id") final Long id);

    /**
     * 查询拒绝理由
     *
     * @param tokenInfoId
     * @return
     */
    @GetMapping(value = "/reject-reason/getProjectRejectReasonByTokenInfoId")
    ResponseResult getProjectRejectReasonByTokenInfoId(@RequestParam("tokenInfoId") final Long tokenInfoId);

    /**
     * 保存ProjectTokenInfo
     *
     * @param projectTokenInfoDTO
     * @return
     */
    @PostMapping(value = "/token-info/saveProjectTokenInfo")
    ResponseResult saveProjectTokenInfo(@RequestBody final ProjectTokenInfoDTO projectTokenInfoDTO);

    /**
     * 更新ProjectTokenInfo
     *
     * @param projectTokenInfoDTO
     * @return
     */
    @PostMapping(value = "/token-info/updateProjectTokenInfo")
    ResponseResult updateProjectTokenInfo(@RequestBody final ProjectTokenInfoDTO projectTokenInfoDTO);

    /**
     * 删除ProjectTokenInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/token-info/removeProjectTokenInfo")
    ResponseResult removeProjectTokenInfo(@RequestParam("id") final Long id);

    /**
     * List ProjectTokenInfo
     *
     * @return
     */
    @PostMapping(value = "/token-info/listProjectTokenInfo")
    ResponseResult listProjectTokenInfo(@RequestBody final DataGridPager<ProjectTokenInfoDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/token-info/getProjectTokenInfoById")
    ResponseResult getProjectTokenInfoById(@RequestParam("id") final Long id);

    /**
     * 查看上币申请详情
     *
     * @param tokenInfoId
     * @return
     */
    @GetMapping(value = "/token-info/getDetailInfo")
    ResponseResult getDetailInfo(@RequestParam("tokenInfoId") final Long tokenInfoId);

    /**
     * 通过初始审核
     *
     * @param projectTokenInfoDTO
     * @return
     */
    @PostMapping(value = "/token-info/pass")
    ResponseResult pass(@RequestBody final ProjectTokenInfoDTO projectTokenInfoDTO);

    /**
     * 排期：给初始审核状态的数据设置上线时间
     *
     * @param projectTokenInfoDTO
     * @return
     */
    @PostMapping(value = "/token-info/schedule")
    ResponseResult schedule(@RequestBody final ProjectTokenInfoDTO projectTokenInfoDTO);

    /**
     * 确认完成上线
     *
     * @param projectTokenInfoDTO
     * @return
     */
    @PostMapping(value = "/token-info/online")
    ResponseResult online(@RequestBody final ProjectTokenInfoDTO projectTokenInfoDTO);

    /**
     * 保存ProjectPaymentRecord
     *
     * @param projectPaymentRecordDTO
     * @return
     */
    @PostMapping(value = "/payment-record/saveProjectPaymentRecord")
    ResponseResult saveProjectPaymentRecord(@RequestBody final ProjectPaymentRecordDTO projectPaymentRecordDTO);

    /**
     * 更新ProjectPaymentRecord
     *
     * @param projectPaymentRecordDTO
     * @return
     */
    @PostMapping(value = "/payment-record/updateProjectPaymentRecord")
    ResponseResult updateProjectPaymentRecord(@RequestBody final ProjectPaymentRecordDTO projectPaymentRecordDTO);

    /**
     * 删除ProjectPaymentRecord
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/payment-record/removeProjectPaymentRecord")
    ResponseResult removeProjectPaymentRecord(@RequestParam("id") final Long id);

    /**
     * List ProjectPaymentRecord
     *
     * @return
     */
    @PostMapping(value = "/payment-record/listProjectPaymentRecord")
    ResponseResult listProjectPaymentRecord(@RequestBody final DataGridPager<ProjectPaymentRecordDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/payment-record/getProjectPaymentRecordById")
    ResponseResult getProjectPaymentRecordById(@RequestParam("id") final Long id);

    /**
     * 用于查询保证金支付记录
     *
     * @param tokenInfoId
     * @param currencyType
     * @return
     */
    @GetMapping(value = "/payment-record/getProjectPaymentRecordByTokenInfoId")
    ResponseResult getProjectPaymentRecordByTokenInfoId(@RequestParam("tokenInfoId") final Long tokenInfoId,
                                                        @RequestParam("currencyType") final Byte currencyType);

}
