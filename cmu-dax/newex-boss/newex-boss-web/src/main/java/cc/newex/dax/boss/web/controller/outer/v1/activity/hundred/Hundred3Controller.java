package cc.newex.dax.boss.web.controller.outer.v1.activity.hundred;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.activity.client.celebration.RushAdminClient;
import cc.newex.dax.activity.dto.ccex.celebration.RushPrizeDTO;
import cc.newex.dax.activity.dto.ccex.celebration.RushProgressDTO;
import cc.newex.dax.activity.dto.ccex.celebration.RushRecordDTO;
import cc.newex.dax.boss.common.util.CsvUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author better
 * @date create in 2018/10/19 下午2:05
 */
@RestController
@RequestMapping(value = "/v1/boss/activity/hundred3")
@Slf4j
public class Hundred3Controller {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 抢购活动纪录成功状态
     */
    private final Map<Integer, String> statusMap;
    @Autowired
    private RushAdminClient rushAdminClient;

    public Hundred3Controller() {
        this.statusMap = new HashMap<>(3);
        this.statusMap.put(1, "处理中");
        this.statusMap.put(2, "成功");
        this.statusMap.put(3, "失败");
    }

    // 查询相关api =================>

    @GetMapping(value = "/rushPrize/list")
    @OpLog(name = "获取抢购活动奖品信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_PRIZE_VIEW"})
    public ResponseResult<RushPrizeDTO> listRushPrize(final DataGridPager<RushPrizeDTO> dataGridPager) {

        dataGridPager.setQueryParameter(new RushPrizeDTO());
        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.listRushPrize(dataGridPager));
    }

    @GetMapping(value = "/rushProgress/list")
    @OpLog(name = "获取抢购活动进程信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_PROGRESS_VIEW"})
    public ResponseResult<RushProgressDTO> listRushProgress(final DataGridPager<RushProgressDTO> dataGridPager) {

        dataGridPager.setQueryParameter(new RushProgressDTO());
        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.listRushProgress(dataGridPager));
    }

    @GetMapping(value = "/rushRecord/list")
    @OpLog(name = "获取抢购活动纪录信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_RECORD_VIEW"})
    public ResponseResult<RushRecordDTO> listRushRecord(final DataGridPager<RushRecordDTO> dataGridPager,
                                                        final Integer periodNo, final String startTime, final String endTime, final Integer status) throws ParseException {

        final RushRecordDTO rushRecordQuery = this.buildRushRecordQuery(periodNo, startTime, endTime, status);
        dataGridPager.setQueryParameter(rushRecordQuery);
        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.listRushRecord(dataGridPager));
    }

    // 新增相关api =================>

    @PostMapping(value = "/rushPrize/add")
    @OpLog(name = "新增抢购活动奖品信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_PRIZE_ADD"})
    public ResponseResult addRushPrize(final RushPrizeDTO rushPrize) {

        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.saveRushPrize(rushPrize));
    }

    @PostMapping(value = "/rushProgress/add")
    @OpLog(name = "新增抢购活动进程信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_PROGRESS_ADD"})
    public ResponseResult addRushProgress(final RushProgressDTO rushProgress) {

        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.saveRushProgress(rushProgress));
    }


    @PostMapping(value = "/rushRecord/add")
    @OpLog(name = "新增抢购活动纪录信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_RECORD_ADD"})
    public ResponseResult addRushRecord(final RushRecordDTO rushRecord) {

        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.saveRushRecord(rushRecord));
    }


    // 更新相关api =================>

    @PostMapping(value = "/rushPrize/edit")
    @OpLog(name = "更新抢购活动奖品信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_PRIZE_EDIT"})
    public ResponseResult updateRushPrize(final RushPrizeDTO rushPrize) {

        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.updateRushPrize(rushPrize));
    }

    @PostMapping(value = "/rushProgress/edit")
    @OpLog(name = "更新抢购活动进程信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_PROGRESS_EDIT"})
    public ResponseResult updateRushProgress(final RushProgressDTO rushProgress) {

        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.updateRushProgress(rushProgress));
    }


    @PostMapping(value = "/rushRecord/edit")
    @OpLog(name = "更新抢购活动纪录信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_RECORD_EDIT"})
    public ResponseResult updateRushRecord(final RushRecordDTO rushRecord) {

        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.updateRushRecord(rushRecord));
    }

    // 删除相关api =================>

    @PostMapping(value = "/rushPrize/remove")
    @OpLog(name = "删除抢购活动奖品信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_PRIZE_REMOVE"})
    public ResponseResult removeRushPrize(final Long id) {

        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.removeRushPrize(id));
    }

    @PostMapping(value = "/rushProgress/remove")
    @OpLog(name = "删除抢购活动进程信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_PROGRESS_REMOVE"})
    public ResponseResult removeRushProgress(final Long id) {

        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.removeRushProgress(id));
    }


    @PostMapping(value = "/rushRecord/remove")
    @OpLog(name = "删除抢购活动纪录信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_RECORD_REMOVE"})
    public ResponseResult removeRushRecord(final Long id) {

        return ResultUtil.getCheckedResponseResult(this.rushAdminClient.removeRushRecord(id));
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder, final WebRequest webRequest) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    // 导出的api =================>

    @GetMapping(value = "/rushRecord/export")
    @OpLog(name = "导出抢购活动纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RUSH_RECORD_EXPORT"})
    public void exportRecords(final Integer periodNo, final String startTime, final String endTime, final Integer status,
                              final HttpServletResponse response) throws IOException, ParseException {

        final String[] headers = {"用户ID", "期数", "币种ID", "币种简称", "币种数量", "状态"};

        final RushRecordDTO rushRecordQuery = this.buildRushRecordQuery(periodNo, startTime, endTime, status);
        final List<String[]> data = this.searchRushRecordAndAssembleCsvData(rushRecordQuery);

        log.info("query rush record param is => {} and result size => {}", rushRecordQuery, data.size());

        // 设置响应头
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("抢购活动纪录.csv", "UTF-8"));
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/octet-stream");
        CsvUtils.writeCsv(headers, response.getOutputStream(), data);
    }

    /**
     * 构建rushRecord查询对象
     *
     * @param periodNo  期数
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @throws ParseException
     */
    private RushRecordDTO buildRushRecordQuery(final Integer periodNo, final String startTime, final String endTime, final Integer status) throws ParseException {
        final RushRecordDTO rushRecordQuery = new RushRecordDTO();

        if (StringUtils.isNotBlank(startTime)) {
            rushRecordQuery.setStartDate(this.format.parse(startTime));
        }
        if (StringUtils.isNotBlank(endTime)) {
            rushRecordQuery.setEndDate(this.format.parse(endTime));
        }
        if (Objects.nonNull(status)) {
            rushRecordQuery.setStatus(status);
        }
        if (Objects.nonNull(periodNo)) {
            rushRecordQuery.setPeriodNo(periodNo);
        }
        return rushRecordQuery;
    }

    /**
     * 搜索rushRecord和组装csv数据
     *
     * @param rushRecordQuery
     * @return
     */
    private List<String[]> searchRushRecordAndAssembleCsvData(final RushRecordDTO rushRecordQuery) {

        return Optional.ofNullable(this.rushAdminClient.searchRushRecord(rushRecordQuery).getData())
                .map(rushRecords -> rushRecords.stream()
                        .map(rushRecord -> {
                            String userIdCsvColumn = rushRecord.getUserId().toString();
                            String periodNoCsvColumn = rushRecord.getPeriodNo().toString();
                            String currencyIdCsvColumn = rushRecord.getCurrencyId().toString();
                            String currencyCodeCsvColumn = rushRecord.getCurrencyCode();
                            String currencyAmountCsvColumn = rushRecord.getCurrencyAmount().toString();
                            String statusCsvColumn = null;
                            if (this.statusMap.containsKey(rushRecord.getStatus())) {
                                statusCsvColumn = this.statusMap.get(rushRecord.getStatus());
                            }

                            return new String[]{userIdCsvColumn, periodNoCsvColumn, currencyIdCsvColumn,
                                    currencyCodeCsvColumn, currencyAmountCsvColumn, statusCsvColumn};
                        })
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }
}
