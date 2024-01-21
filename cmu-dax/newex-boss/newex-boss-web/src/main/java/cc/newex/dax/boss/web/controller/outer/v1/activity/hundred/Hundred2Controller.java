package cc.newex.dax.boss.web.controller.outer.v1.activity.hundred;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.activity.client.celebration.ConfigAdminClient;
import cc.newex.dax.activity.client.celebration.RankAdminClient;
import cc.newex.dax.activity.dto.ccex.celebration.CelebrationConfigDTO;
import cc.newex.dax.activity.dto.ccex.celebration.RankPrizeDTO;
import cc.newex.dax.activity.dto.ccex.celebration.TradeBlocklistDTO;
import cc.newex.dax.activity.dto.ccex.celebration.TradeRankDTO;
import cc.newex.dax.boss.common.util.CsvUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Hundred 2 controller.
 *
 * @author better
 * @date create in 2018/10/16 上午10:26
 */
@RestController
@RequestMapping(value = "/v1/boss/activity/hundred2")
@Slf4j
public class Hundred2Controller {

    @Autowired
    private RankAdminClient rankAdminClient;

    @Autowired
    private ConfigAdminClient configAdminClient;

    // 查询 ===================>

    /**
     * List rank prize response result.
     *
     * @return the response result
     */
    @GetMapping(value = "/rankPrize/list")
    @OpLog(name = "获取排行榜奖品信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RANK_PRIZE_VIEW"})
    public ResponseResult<RankPrizeDTO> listRankPrize(final DataGridPager<RankPrizeDTO> dataGridPager) {

        dataGridPager.setQueryParameter(new RankPrizeDTO());
        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.listRankPrize(dataGridPager));
    }

    /**
     * List trade rank response result.
     *
     * @return the response result
     */
    @GetMapping(value = "/tradeRank/list")
    @OpLog(name = "获取交易排行榜信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TRADE_RANK_VIEW"})
    public ResponseResult<TradeRankDTO> listTradeRank(final String username, final DataGridPager<TradeRankDTO> dataGridPager) {

        final TradeRankDTO tradeRankQuery = TradeRankDTO.builder()
                .username(username).build();
        dataGridPager.setQueryParameter(tradeRankQuery);

        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.listTradeRank(dataGridPager));
    }

    /**
     * List trade block response result.
     *
     * @return the response result
     */
    @GetMapping(value = "/tradeBlock/list")
    @OpLog(name = "获取交易排行榜信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TRADE_BLOCK_VIEW"})
    public ResponseResult<TradeBlocklistDTO> listTradeBlock(final DataGridPager<TradeBlocklistDTO> dataGridPager) {

        dataGridPager.setQueryParameter(new TradeBlocklistDTO());
        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.listTradeBlocklist(dataGridPager));
    }


    // 新增 ===================>


    /**
     * Add ran prize response result.
     *
     * @param rankPrize the rank prize
     * @return the response result
     */
    @PostMapping(value = "/rankPrize/add")
    @OpLog(name = "新增排行榜奖品信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RANK_PRIZE_ADD"})
    public ResponseResult addRankPrize(final RankPrizeDTO rankPrize) {

        rankPrize.setCreatedDate(new Date());

        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.saveRankPrize(rankPrize));
    }

    /**
     * Add trade rank response result.
     *
     * @param tradeRank the trade rank
     * @return the response result
     */
    @PostMapping(value = "/tradeRank/add")
    @OpLog(name = "新增排行榜信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TRADE_RANK_ADD"})
    public ResponseResult addTradeRank(final TradeRankDTO tradeRank) {

        tradeRank.setCreatedDate(new Date());

        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.saveTradeRank(tradeRank));
    }

    /**
     * Add trade block response result.
     *
     * @param tradeBlock the trade block
     * @return the response result
     */
    @PostMapping(value = "/tradeBlock/add")
    @OpLog(name = "新增黑名单信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TRADE_BLOCK_ADD"})
    public ResponseResult addTradeBlock(final TradeBlocklistDTO tradeBlock) {

        tradeBlock.setCreatedDate(new Date());

        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.saveTradeBlocklist(tradeBlock));
    }


    // 更新 ===================>


    /**
     * Update ran prize response result.
     *
     * @param rankPrize the rank prize
     * @return the response result
     */
    @PostMapping(value = "/rankPrize/edit")
    @OpLog(name = "更新排行榜奖品信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RANK_PRIZE_EDIT"})
    public ResponseResult updateRankPrize(final RankPrizeDTO rankPrize) {

        rankPrize.setUpdatedDate(new Date());

        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.updateRankPrize(rankPrize));
    }

    /**
     * Update trade rank response result.
     *
     * @param tradeRank the trade rank
     * @return the response result
     */
    @PostMapping(value = "/tradeRank/edit")
    @OpLog(name = "更新排行榜信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TRADE_RANK_EDIT"})
    public ResponseResult updateTradeRank(final TradeRankDTO tradeRank) {

        tradeRank.setUpdatedDate(new Date());

        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.updateTradeRank(tradeRank));
    }

    /**
     * Update trade block response result.
     *
     * @param tradeBlock the trade block
     * @return the response result
     */
    @PostMapping(value = "/tradeBlock/edit")
    @OpLog(name = "更新黑名单信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TRADE_BLOCK_EDIT"})
    public ResponseResult updateTradeBlock(final TradeBlocklistDTO tradeBlock) {

        tradeBlock.setUpdatedDate(new Date());

        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.updateTradeBlocklist(tradeBlock));
    }


    // 删除 ===================>

    /**
     * Remove ran prize response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/rankPrize/remove")
    @OpLog(name = "删除排行榜奖品信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_RANK_PRIZE_REMOVE"})
    public ResponseResult removeRanPrize(final Long id) {

        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.removeRankPrize(id));
    }

    /**
     * Remove trade rank response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/tradeRank/remove")
    @OpLog(name = "删除排行榜信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TRADE_RANK_REMOVE"})
    public ResponseResult removeTradeRank(final Long id) {

        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.removeTradeRank(id));
    }

    /**
     * Remove trade block response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/tradeBlock/remove")
    @OpLog(name = "删除黑名单信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TRADE_BLOCK_REMOVE"})
    public ResponseResult removeTradeBlock(final Long id) {

        return ResultUtil.getCheckedResponseResult(this.rankAdminClient.removeTradeBlocklist(id));
    }

    // 时间相关的api  ===============

    @GetMapping(value = "/timeConfig/list")
    @OpLog(name = "查询时间配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TIME_CONFIG_VIEW"})
    public ResponseResult listTimeConfig() {
        final ResponseResult result = this.configAdminClient.getConfig();
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/timeConfig/saveOrUpdate")
    @OpLog(name = "保存和修改时间配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TIME_CONFIG_EDIT"})
    public ResponseResult updateTimeConfig(final CelebrationConfigDTO celebrationConfig) {
        final ResponseResult result = this.configAdminClient.updateConfig(celebrationConfig);
        return ResultUtil.getCheckedResponseResult(result);
    }

    // 导出数据的api

    /**
     * 导出中奖信息
     *
     * @return response result
     */
    @GetMapping(value = "/tradeRank/export")
    @OpLog(name = "导出排行榜信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_HUNDRED_ONE_TRADE_RANK_EXPORT"})
    public void exportRecords(final String username, final HttpServletResponse response) throws IOException {

        final String[] headers = {"用户ID", "用户名", "折合USDT交易额"};

        final Integer limit = 100;
        final TradeRankDTO tradeRankQuery = TradeRankDTO.builder().username(username).build();
        final List<String[]> data = this.searchTradeTankAndAssembleCsvData(limit, tradeRankQuery);

        log.info("query trade rank param is => {} and result size => {}", tradeRankQuery, data.size());

        // 设置响应头
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("排行榜.csv", "UTF-8"));
        CsvUtils.writeCsv(headers, response.getOutputStream(), data);
    }

    /**
     * 查询排行榜数据并组装csvuuju
     *
     * @param limit
     * @param tradeRankQuery
     * @return
     */
    private List<String[]> searchTradeTankAndAssembleCsvData(final Integer limit, final TradeRankDTO tradeRankQuery) {
        return Optional.ofNullable(this.rankAdminClient.searchTradeRank(tradeRankQuery, limit).getData())
                .map(tradeRanks -> tradeRanks.stream()
                        .map(tradeRank -> {
                            String userIdCsvColumn = tradeRank.getUserId().toString();
                            String usernameCsvColumn = tradeRank.getUsername();
                            String amountCsvColumn = tradeRank.getAmount().toString();
                            return new String[]{userIdCsvColumn, usernameCsvColumn, amountCsvColumn};
                        })
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder, final WebRequest webRequest) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
