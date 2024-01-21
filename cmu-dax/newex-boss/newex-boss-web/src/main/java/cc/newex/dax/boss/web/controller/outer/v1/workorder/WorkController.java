package cc.newex.dax.boss.web.controller.outer.v1.workorder;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.criteria.UserExample;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.UserService;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.controller.common.BaseController;
import cc.newex.dax.extra.client.ExtraWorkOrderCustomerClient;
import cc.newex.dax.extra.client.ExtraWorkOrderReplyClient;
import cc.newex.dax.extra.client.ExtraWorkOrderUploadClient;
import cc.newex.dax.extra.dto.customer.WorkOrderAttachmentDTO;
import cc.newex.dax.extra.dto.customer.WorkOrderCustomerDTO;
import cc.newex.dax.extra.dto.customer.WorkOrderDTO;
import cc.newex.dax.extra.dto.customer.WorkOrderOpLogDTO;
import cc.newex.dax.extra.dto.customer.WorkOrderReplyDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author jinlong
 * @date 2018-06-15
 */
@RestController
@RequestMapping(value = "/v1/boss/workorder/work")
@Slf4j
public class WorkController extends BaseController<UserService, User, UserExample, Integer> {

    @Autowired
    ExtraWorkOrderCustomerClient extraWorkOrderCustomerClient;


    @Autowired
    ExtraWorkOrderReplyClient extraWorkOrderReplyClient;

    @Autowired
    ExtraWorkOrderUploadClient extraWorkOrderUploadClient;

    private User currentUser;

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "/getUserInfo")
    @OpLog(name = "获取当前用户信息")
    public User getUserInfo(final HttpServletRequest request) {
        return (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
    }


    @RequestMapping(value = "/updateType")
    @OpLog(name = "更新工作状态")
    public ResponseResult setUpdateType(@RequestParam(value = "type", required = false) final Integer type, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
        user.setDutyStatus(type);
        this.service.editById(user);
        return ResultUtils.success();
    }

    @GetMapping(value = "/list")
    @OpLog(name = "工单菜单列表")
    public ResponseResult list(final DataGridPager pager,
                               @RequestParam(value = "filedName", required = false) final String filedName,
                               @RequestParam(value = "keyword", required = false) final String keyword,
                               @RequestParam(value = "status") final Integer status, final HttpServletRequest request) {
        try {
            this.currentUser = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
            final ResponseResult result = this.extraWorkOrderCustomerClient.getListByStatus(pager, filedName, keyword, this.currentUser.getId(), status);
            //重组数据格式
            final Map<String, Object> modelMap = new HashMap<>(2);
            if (result == null) {
                return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
            }
            if (result.getCode() == 0) {
                final JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                final List<WorkOrderDTO> workOrderDTOS = JSON.parseArray(jsonObject.get("rows").toString(), WorkOrderDTO.class);
                final List<WorkOrderDTO> newList = workOrderDTOS.stream().map(l -> {
                    final ResponseResult data = this.extraWorkOrderCustomerClient.status();

                    String StatusTxt = "";
                    final JSONArray statusJson = (JSONArray) data.getData();
                    if (statusJson.size() > 0) {
                        for (int ii = 0; ii < statusJson.size(); ii++) {
                            JSONObject job = statusJson.getJSONObject(ii);
                            if (l.getStatus().equals(job.get("code"))) {
                                StatusTxt = job.get("name").toString();
                            }
                        }
                    }
                    return WorkOrderDTO.builder()
                            .id(l.getId())
                            .userId(l.getUserId())
                            .MenuDesc(l.getMenuDesc())
                            .userName(l.getUserName())
                            .content("<div style='padding: 10px;'><div style='float: left'>" + l.getAdminName() + "</div><div style='float: right'>" + l.getId() + "</div><div class='clear'></div><div style='padding-top: 10px;'><div style='float: left;'>" + l.getMenuDesc() + "</div><div style='float: right;color: #00bbee;'>" + StatusTxt + "</div><div class='clear'></div></div><div style='padding-top: 10px;'>" + l.getContent() + "</div></div>")
                            .build();
                }).collect(Collectors.toList());

                modelMap.put("total", jsonObject.get("total").toString());
                modelMap.put("rows", newList);
                return ResultUtils.success(modelMap);
            }

            return ResultUtils.failure(result.getMsg());

        } catch (final Exception e) {
            log.error("get work list api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

    }


    @GetMapping(value = "/workDetails")
    @OpLog(name = "根据ID获取工单详情")
    public ResponseResult workDetails(final Long workOrderId) {
        try {
            final ResponseResult byWorkOrderId = this.extraWorkOrderReplyClient.get(workOrderId);
            if (byWorkOrderId == null) {
                return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
            }
            if (byWorkOrderId.getCode() == 0) {
                return ResultUtil.getCheckedResponseResult(byWorkOrderId);
            }
            return ResultUtils.failure(byWorkOrderId.getMsg());

        } catch (final Exception e) {
            log.error("get workDetails api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @RequestMapping(value = "/transmit", method = RequestMethod.POST)
    @OpLog(name = "转发工单")
    public ResponseResult transmit(@RequestParam(value = "id") final Long workOrderId,
                                   @RequestParam(value = "userId") final Integer userId) {
        try {
            final User user = this.service.getById(userId);
            final WorkOrderCustomerDTO workOrderCustomerDTO = new WorkOrderCustomerDTO();
            workOrderCustomerDTO.setAdminName(user.getName());
            workOrderCustomerDTO.setAdminAccount(user.getAccount());
            workOrderCustomerDTO.setAdminUserId(user.getId());

            final ResponseResult transfer = this.extraWorkOrderCustomerClient.transfer(workOrderId, workOrderCustomerDTO);
            if (transfer == null) {
                return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
            }
            if (transfer.getCode() == 0) {
                final WorkOrderOpLogDTO workOrderOpLogDTO = WorkOrderOpLogDTO.builder()
                        .currentAdminAccount(this.currentUser.getAccount())
                        .currentAdminUserId(this.currentUser.getId())
                        .groupId(this.currentUser.getGroupId())
                        .workOrderId(workOrderId)
                        .build();
                //opType=0,转发工单日志
                this.extraWorkOrderCustomerClient.log(0, workOrderOpLogDTO);
                return ResultUtil.getCheckedResponseResult(transfer);
            }
            return ResultUtils.failure(transfer.getMsg());

        } catch (final Exception e) {
            log.error("get transmit api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @OpLog(name = "回复问题")
    public ResponseResult add(final Long workOrderId, final HttpServletRequest request,
                              final String remarks) {
        try {
            final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
            final WorkOrderReplyDTO workOrderReplyDTO = new WorkOrderReplyDTO();
            workOrderReplyDTO.setAdminUserId(user.getId());
            workOrderReplyDTO.setWorkOrderId(workOrderId);
            workOrderReplyDTO.setType(1);
            workOrderReplyDTO.setReply(remarks);
            final ResponseResult transfer = this.extraWorkOrderReplyClient.add(workOrderId, workOrderReplyDTO);
            if (transfer == null) {
                return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
            }
            if (transfer.getCode() == 0) {
                return ResultUtil.getCheckedResponseResult(transfer);
            }
            return ResultUtils.failure(transfer.getMsg());

        } catch (final Exception e) {
            log.error("get  workRemark api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

    }

    @RequestMapping(value = "/Solve", method = RequestMethod.POST)
    @OpLog(name = "设置问题解决")
    public ResponseResult Solve(final Long workOrderId) {
        try {
            final ResponseResult responseResult = this.extraWorkOrderCustomerClient.setComplete(workOrderId);
            if (responseResult == null) {
                return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
            }
            if (responseResult.getCode() == 0) {
                final WorkOrderOpLogDTO workOrderOpLogDTO = WorkOrderOpLogDTO.builder()
                        .currentAdminAccount(this.currentUser.getAccount())
                        .currentAdminUserId(this.currentUser.getId())
                        .groupId(this.currentUser.getGroupId())
                        .workOrderId(workOrderId)
                        .build();
                //opType=2,工单已解决
                this.extraWorkOrderCustomerClient.log(2, workOrderOpLogDTO);
                return ResultUtil.getCheckedResponseResult(responseResult);
            }
            return ResultUtils.failure(responseResult.getMsg());

        } catch (final Exception e) {
            log.error("get Solve api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

    }

    @PostMapping(value = "/attachFile")
    @OpLog(name = "发送附件")
    public ResponseResult attach(@RequestParam(value = "workOrderId") final Long workOrderId,
                                 @RequestParam(value = "originalName") final String originalName,
                                 @RequestParam(value = "path") final String path,
                                 @RequestParam(value = "desc", required = false) final String desc) {

        final WorkOrderAttachmentDTO workOrderAttachmentDTO = WorkOrderAttachmentDTO.builder()
                .workOrderId(workOrderId)
                .originalName(originalName)
                .path(path)
                .type(1)
                .desc(desc).build();
        return ResultUtil.getCheckedResponseResult(this.extraWorkOrderUploadClient.save(workOrderAttachmentDTO));
    }

    @PostMapping(value = "/wait")
    @OpLog(name = "工单置为等待")
    public ResponseResult wait(@RequestParam(value = "workOrderId") final Long workOrderId) {
        if (!Objects.nonNull(workOrderId)) {
            return ResultUtils.failure("");
        }
        final ResponseResult result = this.extraWorkOrderCustomerClient.setWait(workOrderId);
        if (result.getCode() == 0) {
            final WorkOrderOpLogDTO workOrderOpLogDTO = WorkOrderOpLogDTO.builder()
                    .currentAdminAccount(this.currentUser.getAccount())
                    .currentAdminUserId(this.currentUser.getId())
                    .groupId(this.currentUser.getGroupId())
                    .workOrderId(workOrderId)
                    .build();
            //opType=1,工单置为等待
            this.extraWorkOrderCustomerClient.log(1, workOrderOpLogDTO);
        }
        return ResultUtil.getCheckedResponseResult(result);
    }

}
