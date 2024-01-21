package cc.newex.dax.boss.web.controller.outer.v1.activity.hundred;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.activity.client.celebration.WheelAdminClient;
import cc.newex.dax.activity.dto.ccex.celebration.WheelPrizeDTO;
import cc.newex.dax.activity.dto.ccex.celebration.WheelWinRecordDTO;
import cc.newex.dax.boss.common.util.CsvUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 一百天活动的控制器
 *
 * @author better
 * @date create in 2018/10/11 下午3:36
 */
@RestController
@RequestMapping(value = "/v1/boss/activity/hundred")
@Slf4j
public class HundredController {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private WheelAdminClient wheelAdminClient;

    /**
     * 获取奖品信息
     *
     * @return response result
     */
    @GetMapping(value = "/prize/list")
    @OpLog(name = "获取奖品纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_PRIZE_VIEW"})
    public ResponseResult<List<WheelPrizeDTO>> listPrizes(final DataGridPager<WheelPrizeDTO> dataGridPager) {

        dataGridPager.setQueryParameter(new WheelPrizeDTO());
        return this.wheelAdminClient.listWheelPrize(dataGridPager);
    }


    /**
     * 新增奖品信息
     *
     * @param wheelPrize the wheel prize
     * @return response result
     */
    @PostMapping(value = "/prize/add")
    @OpLog(name = "新增奖品纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_PRIZE_ADD"})
    public ResponseResult<?> savePrize(final WheelPrizeDTO wheelPrize) {

        wheelPrize.setCreatedDate(new Date());
        return ResultUtil.getCheckedResponseResult(this.wheelAdminClient.saveWheelPrize(wheelPrize));
    }

    /**
     * 删除奖品信息
     *
     * @param id the id
     * @return response result
     */
    @PostMapping(value = "/prize/remove")
    @OpLog(name = "删除奖品纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_PRIZE_REMOVE"})
    public ResponseResult<?> removePrize(final Long id) {

        return ResultUtil.getCheckedResponseResult(this.wheelAdminClient.removeWheelPrize(id));
    }

    /**
     * 更新奖品信息
     *
     * @param wheelPrize the wheel prize
     * @return response result
     */
    @PostMapping(value = "/prize/edit")
    @OpLog(name = "更新奖品纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_PRIZE_EDIT"})
    public ResponseResult<?> updatePrize(final WheelPrizeDTO wheelPrize) {

        wheelPrize.setUpdatedDate(new Date());
        return ResultUtil.getCheckedResponseResult(this.wheelAdminClient.updateWheelPrize(wheelPrize));
    }


    /**
     * 获取中奖信息
     *
     * @return response result
     */
    @GetMapping(value = "/record/winning/list")
    @OpLog(name = "获取中奖纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_WIN_RECORD_VIEW"})
    public ResponseResult<List<WheelWinRecordDTO>> listRecords(final String startTime, final String endTime, final String username,
                                                               final Integer level, final DataGridPager<WheelWinRecordDTO> dataGridPager) throws ParseException {

        final WheelWinRecordDTO wheelWinRecordQuery = this.buildWinRecordQuery(startTime, endTime, username, level);
        dataGridPager.setQueryParameter(wheelWinRecordQuery);

        return this.wheelAdminClient.listWheelWinRecord(dataGridPager);
    }

    /**
     * 导出中奖信息
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @param username  the username
     * @param level     the level
     * @param response  the response
     * @return response result
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    @GetMapping(value = "/record/winning/export")
    @OpLog(name = "导出中奖纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_WIN_RECORD_EXPORT"})
    public void exportRecords(final String startTime, final String endTime, final String username,
                              final Integer level, final HttpServletResponse response) throws IOException, ParseException {

        final String[] headers = {"用户ID", "用户名", "奖品等级", "奖品描述"};

        final WheelWinRecordDTO wheelWinRecordQuery = this.buildWinRecordQuery(startTime, endTime, username, level);

        final List<String[]> data = this.searchWinRecordAndAssembleCsvData(wheelWinRecordQuery);

        log.info("query record winning param is => {} and result size => {}", wheelWinRecordQuery, data.size());

        // 设置响应头
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("中奖纪录.csv", "UTF-8"));
        CsvUtils.writeCsv(headers, response.getOutputStream(), data);
    }

    /**
     * 构建中奖纪录查询对象
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param username  用户名
     * @param level     奖品级别
     * @return
     * @throws ParseException
     */
    private WheelWinRecordDTO buildWinRecordQuery(final String startTime, final String endTime, final String username,
                                                  final Integer level) throws ParseException {

        final WheelWinRecordDTO wheelWinRecordQuery = new WheelWinRecordDTO();
        if (StringUtils.isNotBlank(startTime)) {
            wheelWinRecordQuery.setStartDate(this.format.parse(startTime));
        }
        if (StringUtils.isNotBlank(endTime)) {
            wheelWinRecordQuery.setEndDate(this.format.parse(endTime));
        }
        wheelWinRecordQuery.setUsername(username);
        wheelWinRecordQuery.setLevel(level);
        return wheelWinRecordQuery;
    }

    /**
     * 搜索中奖纪录并组装成csv所需数据
     *
     * @param wheelWinRecord 查询条件
     * @return
     */
    private List<String[]> searchWinRecordAndAssembleCsvData(final WheelWinRecordDTO wheelWinRecord) {

        return Optional.ofNullable(this.wheelAdminClient.searchWheelWinRecord(wheelWinRecord).getData())
                .map(wheelWinRecords -> wheelWinRecords.stream()
                        .map(winRecord -> {
                            String userIdCsvColumn = winRecord.getUserId().toString();
                            String usernameCsvColumn = winRecord.getUsername();
                            String levelCsvColumn = winRecord.getLevel().toString();
                            String descCsvColumn = winRecord.getDesc();
                            return new String[]{userIdCsvColumn, usernameCsvColumn, levelCsvColumn, descCsvColumn};
                        })
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }


    /**
     * 新增中奖纪录
     *
     * @param wheelWinRecord the wheel win record
     * @return response result
     */
    @PostMapping(value = "/record/winning/add")
    @OpLog(name = "新增中奖纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_WIN_RECORD_ADD"})
    public ResponseResult<?> saveWinningRecord(final WheelWinRecordDTO wheelWinRecord) {

        wheelWinRecord.setCreatedDate(new Date());

        return ResultUtil.getCheckedResponseResult(this.wheelAdminClient.saveWheelWinRecord(wheelWinRecord));
    }

    /**
     * 修改中奖纪录
     *
     * @param wheelWinRecord the wheel win record
     * @return response result
     */
    @PostMapping(value = "/record/winning/edit")
    @OpLog(name = "修改中奖纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_WIN_RECORD_EDIT"})
    public ResponseResult<?> updateWinningRecord(final WheelWinRecordDTO wheelWinRecord) {

        wheelWinRecord.setUpdatedDate(new Date());

        return ResultUtil.getCheckedResponseResult(this.wheelAdminClient.updateWheelWinRecord(wheelWinRecord));
    }

    /**
     * 删除中奖纪录
     *
     * @param id the id
     * @return response result
     */
    @PostMapping(value = "/record/winning/remove")
    @OpLog(name = "删除中奖纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_WIN_RECORD_REMOVE"})
    public ResponseResult<?> removeWinningRecord(final Long id) {

        return ResultUtil.getCheckedResponseResult(this.wheelAdminClient.removeWheelWinRecord(id));
    }
}
