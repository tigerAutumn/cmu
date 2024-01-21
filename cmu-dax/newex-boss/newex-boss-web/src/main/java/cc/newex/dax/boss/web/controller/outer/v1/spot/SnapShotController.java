package cc.newex.dax.boss.web.controller.outer.v1.spot;


import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.spot.TaskJobConfigVO;
import cc.newex.dax.spot.client.SpotScheduleTaskConfigureClient;
import cc.newex.dax.spot.dto.ccex.ScheduleTaskConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author newex-jinlong
 * @date 2018-04-28
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/spot/snapshot")
public class SnapShotController {

    @Autowired
    private SpotScheduleTaskConfigureClient spotClient;

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取任务列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_SNAPSHOT_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager pager,
                               @RequestParam(value = "status", required = false) final Integer status) {
        final ResponseResult<DataGridPagerResult<ScheduleTaskConfigDTO<ScheduleTaskConfigDTO.SnapshotUserCurrencyBalance>>> taskJobList = this.spotClient
                .getByPager(pager, status, loginUser.getLoginBrokerId());
        if (taskJobList.getCode() != 0 && Objects.isNull(taskJobList.getData())) {
            return ResultUtils.failure("get task list error");
        }
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final List<TaskJobConfigVO> vos = taskJobList.getData().getRows().stream().map(v -> TaskJobConfigVO.builder()
                    .id(v.getId())
                    .currency(v.getConfigJson().getCurrencyId())
                    .taskJobName(v.getCreateName())
                    .status(v.getStatus())
                    .currency(v.getConfigJson().getCurrencyId())
                    .calender(v.getConfigJson().getCalendar())
                    .startTime(sdf.format(v.getStartTime()))
                    .period(v.getConfigJson().getPeriod())
                    .taskType(v.getType())
                    .endTime(sdf.format(v.getEndTime()))
                    .build()).collect(Collectors.toList());
            final HashMap<String, Object> result = new HashMap<>(2);
            result.put("rows", vos);
            result.put("total", taskJobList.getData().getTotal());
            return ResultUtils.success(result);
        } catch (final NullPointerException e) {
            return ResultUtils.failure("null point");
        }
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "根据任务ID删除任务")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_SNAPSHOT_REMOVE"})
    public ResponseResult remove(final Long[] id) {

        try {
            final ResponseResult result = this.spotClient.remove(id);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("delete task api error " + e);
        }
        return ResultUtils.success();
    }


    @RequestMapping(value = "/add")
    @OpLog(name = "新增快照任务")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_SNAPSHOT_ADD"})
    public ResponseResult add(@CurrentUser final User loginUser, final TaskJobConfigVO vo) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            final ScheduleTaskConfigDTO<ScheduleTaskConfigDTO.SnapshotUserCurrencyBalance> dto = ScheduleTaskConfigDTO.<ScheduleTaskConfigDTO.SnapshotUserCurrencyBalance>builder()
                    .createName(vo.getTaskJobName())
                    .startTime(sdf.parse(vo.getStartTime()))
                    .type(vo.getTaskType())
                    .brokerId(vo.getBrokerId())
                    .build();
            final ScheduleTaskConfigDTO.SnapshotUserCurrencyBalance cdto = ScheduleTaskConfigDTO.SnapshotUserCurrencyBalance.builder()
                    .currencyId(vo.getCurrency()).build();
            if (vo.getTaskType() == 1) {
                final Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(vo.getStartTime()));
                cal.add(Calendar.HOUR, 1);
                dto.setEndTime(cal.getTime());
            }
            if (vo.getTaskType() == 2) {
                cdto.setCalendar(vo.getCalender());
                cdto.setPeriod(vo.getPeriod());
                dto.setEndTime(sdf.parse(vo.getEndTime()));
            }
            dto.setConfigJson(cdto);
            final ResponseResult result = this.spotClient.add(dto);
            return ResultUtil.getCheckedResponseResult(result);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return ResultUtils.failure("add task error");
    }

    @GetMapping(value = "/subList")
    @OpLog(name = "分页获取任务列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_SNAPSHOT_SUB_VIEW"})
    public ResponseResult subList(@CurrentUser final User loginUser, final DataGridPager pager, @RequestParam(value = "parentId", required = false) final Integer parentId,
                                  @RequestParam(value = "status", required = false) final Integer status) {
        final ResponseResult<DataGridPagerResult<ScheduleTaskConfigDTO<ScheduleTaskConfigDTO.SnapshotUserCurrencyBalance>>> subTaskJobList = this.spotClient
                .getPeriodByPager(pager, parentId, status, loginUser.getLoginBrokerId());
        if (subTaskJobList.getCode() != 0 && Objects.isNull(subTaskJobList.getData())) {
            return ResultUtils.failure("get task list error");
        }
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final List<TaskJobConfigVO> vos = subTaskJobList.getData().getRows().stream().map(v -> TaskJobConfigVO.builder()
                    .id(v.getId())
                    .taskJobName(v.getCreateName())
                    .status(v.getStatus())
                    .startTime(sdf.format(v.getStartTime()))
                    .taskType(v.getType())
                    .endTime(sdf.format(v.getEndTime()))
                    .build()).collect(Collectors.toList());
            final HashMap<String, Object> result = new HashMap<>(2);
            result.put("rows", vos);
            result.put("total", subTaskJobList.getData().getTotal());
            return ResultUtils.success(result);
        } catch (final NullPointerException e) {
            return ResultUtils.failure("null point");
        }
    }

    @PostMapping(value = "/pause")
    @OpLog(name = "暂停")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_SNAPSHOT_PAUSE"})
    public ResponseResult pause(@RequestParam(value = "ids") final Long[] ids, @RequestParam(value = "status", required = false) final Integer status) {
        final ResponseResult result = this.spotClient.updateStatus(ids, status);
        return ResultUtil.getCheckedResponseResult(result);
    }

}
