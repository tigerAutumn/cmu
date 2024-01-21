package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.spot.client.SpotScheduleTaskConfigureClient;
import cc.newex.dax.spot.client.SpotUserCurrencyBalanceClient;
import cc.newex.dax.spot.dto.ccex.ScheduleTaskConfigDTO;
import cc.newex.dax.spot.dto.ccex.UserCurrencyBalanceSnapshotDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author newex-jinlong
 * @date 2018-04-28
 */
@RestController
@RequestMapping(value = "/v1/boss/spot/snapshot-details")
@Slf4j
public class SnapShotDetailsController {
    @Autowired
    private SpotUserCurrencyBalanceClient spotClient;

    @Autowired
    private SpotScheduleTaskConfigureClient spotClientCurrency;

    @Autowired
    private AdminServiceClient assetClient;

    private static JSONArray jsonArray;

    /**
     * 根据币种ID返回币种名称
     */
    private void getcurrency(final Integer brokerId) {
        log.info("get currencies begin");
        try {
            final ResponseResult<?> allCurrencies = this.assetClient.getAllCurrencies("spot", brokerId);
            if (allCurrencies.getCode() == 0) {
                jsonArray = (JSONArray) allCurrencies.getData();
            } else {
                log.error("get currencies error,error code:{}", allCurrencies.getCode());
            }
        } catch (final Throwable e) {
            log.error("get currencies error", e);
        }

    }

    /**
     * 根据币种ID查询币种名称
     *
     * @return
     */
    private String getsymbolname(final int id) {
        for (int i = 0; i < jsonArray.size(); i++) {
            final JSONObject job = jsonArray.getJSONObject(i);
            if (job.get("id").equals(id)) {
                return (String) job.get("symbol");
            }
        }
        return "";
    }


    @GetMapping(value = "/list")
    @OpLog(name = "获取任务详情列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_SNAPSHOT_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager pager, @RequestParam(value = "taskid", required = false) final long taskid) throws IOException {

        final DecimalFormat formatter = new DecimalFormat();

        formatter.setMaximumFractionDigits(8);
        formatter.setGroupingSize(0);
        formatter.setRoundingMode(RoundingMode.FLOOR);
        this.getcurrency(loginUser.getLoginBrokerId());
        final BigDecimal var3 = null;

        try {
            final ResponseResult<DataGridPagerResult<UserCurrencyBalanceSnapshotDTO>> pageRecordResponseResult = this.spotClient.snapshotList(pager, loginUser.getBrokerId(),
                    taskid, var3, var3);
            final Map<String, Object> modelMap = new HashMap<>(2);
            if (pageRecordResponseResult != null && pageRecordResponseResult.getCode() == 0) {

                final List<UserCurrencyBalanceSnapshotDTO> authorities = pageRecordResponseResult.getData().getRows();
                final StringBuilder columns = new StringBuilder();

                columns.append("[[");
                columns.append("{field:'userId',title:'用户ID',width:180,},");
                columns.append("{field:'bitcoinAssets',title:'btc估值',width:180},");
                columns.append("{field:'createTime',title:'创建时间',width:180},");

                //动态生成总列数,根据任务ID查询币种个数
                int count = 0;
                try {
                    //查询币种ID和数量 前台显示很重要
                    final ResponseResult<ScheduleTaskConfigDTO<ScheduleTaskConfigDTO.SnapshotUserCurrencyBalance>> snapshotUserCurrencyBalanceTaskConfig = this
                            .spotClientCurrency.getById(taskid);
                    final List<Integer> ls = Arrays.asList(snapshotUserCurrencyBalanceTaskConfig.getData().getConfigJson().getCurrencyId());
                    count = ls.size();
                    for (int currencycount = 0; currencycount < count; currencycount++) {
                        //根据币种ID显示币种名称
                        final String currencyId = this.getsymbolname((int) ls.get(currencycount));
                        final String currencyIdtitle = currencyId;

                        final String available = currencyId + "可用余额";
                        final String availabletitle = currencyIdtitle + "可用余额";

                        final String hold = currencyId + "冻结余额";
                        final String holdtitle = currencyIdtitle + "冻结余额";

                        if (count - 1 <= currencycount) {
                            //动态生成列根据ID增加
                            columns.append("{field:'" + available + "',title:'" + availabletitle + "',width:180},");
                            columns.append("{field:'" + hold + "',title:'" + holdtitle + "',width:180}");
                        } else {
                            columns.append("{field:'" + available + "',title:'" + availabletitle + "',width:180},");
                            columns.append("{field:'" + hold + "',title:'" + holdtitle + "',width:180},");
                        }
                    }
                    final List<Map<String, Object>> newtaskDetails = new ArrayList<>();

                    for (int i = 0; i < authorities.size(); i++) {
                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final Map<String, Object> map = new HashMap<>();
                        map.put("userId", authorities.get(i).getUserId().toString());
                        map.put("bitcoinAssets", formatter.format(authorities.get(i).getBitcoinAssets()));
                        map.put("createTime", sdf.format(authorities.get(i).getCreateTime()));


                        for (final Integer ci : ls) {
                            map.put(this.getsymbolname(ci) + "可用余额", "0");
                            map.put(this.getsymbolname(ci) + "冻结余额", "0");
                        }
                        //动态生成列根据ID增加 3 1 eth 2 0  3 0
                        for (int ww = 0; ww < count; ww++) {

                            if (authorities.get(i).getObject().size() > ww) {
                                final String str = authorities.get(i).getObject().get(ww).getCurrencyId().toString();
                                map.put(this.getsymbolname(Integer.parseInt(str)) + "可用余额", formatter.format(authorities.get(i).getObject().get(ww).getAvailable()));
                                map.put(this.getsymbolname(Integer.parseInt(str)) + "冻结余额", formatter.format(authorities.get(i).getObject().get(ww).getHold()));
                            }
                        }
                        newtaskDetails.add(map);
                    }
                    columns.append("]]");

                    modelMap.put("total", pageRecordResponseResult.getData().getTotal());
                    modelMap.put("rows", newtaskDetails);
                    modelMap.put("columns", columns);
                } catch (final Exception e) {
                    log.error("get configjson api error", e);
                }
                return ResultUtils.success(modelMap);
            } else {
                log.info("get user snap shot details code" + pageRecordResponseResult.getCode() + "message" + pageRecordResponseResult.getMsg());
                return ResultUtils.failure(pageRecordResponseResult.getMsg());
            }

        } catch (final Exception e) {
            log.error("get user snap shot details api error", e);
        }
        return ResultUtils.success();
    }


    @RequestMapping("info")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_SNAPSHOT_DOWNLOAD"})
    public void info(@CurrentUser final User loginUser, final HttpServletRequest request, final HttpServletResponse response, @RequestParam(value = "taskid", required = false) final
    long taskid) {
        final Map<String, Object> map = new HashMap<>();

        final DecimalFormat formater = new DecimalFormat();

        formater.setMaximumFractionDigits(8);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);

        final Integer page = 1;
        final Integer pageSize = 1000;
        final DataGridPager pager = new DataGridPager();
        pager.setPage(page);
        pager.setRows(pageSize);
        final HSSFWorkbook wb = new HSSFWorkbook();
        final HSSFSheet sheet = wb.createSheet("任务快照");
        final HSSFRow row1 = sheet.createRow(0);
        final HSSFRow row2 = sheet.createRow(0);

        final BigDecimal var3 = null;
        try {
            final ResponseResult<DataGridPagerResult<UserCurrencyBalanceSnapshotDTO>> pageRecordResponseResultpage = this.spotClient.snapshotList(pager, loginUser.getLoginBrokerId(),
                    taskid, var3, var3);

            if (pageRecordResponseResultpage != null && pageRecordResponseResultpage.getCode() == 0) {
                final Long pagecount = pageRecordResponseResultpage.getData().getTotal();

                final Long sum = (pagecount - 1) / pageSize + 1;

                final List<UserCurrencyBalanceSnapshotDTO> pagelist = new ArrayList<>();
                for (int p = 1; p <= sum; p++) {
                    try {

                        final ResponseResult<DataGridPagerResult<UserCurrencyBalanceSnapshotDTO>> pageRecordResponseResult = this.spotClient.snapshotList(pager,
                                loginUser.getLoginBrokerId(), taskid, var3, var3);
                        final List<UserCurrencyBalanceSnapshotDTO> data = pageRecordResponseResult.getData().getRows();
                        for (int i = 0; i < data.size(); i++) {
                            pagelist.add(data.get(i));
                        }

                    } catch (final Exception e) {
                        log.error("get user snap shot details pagecount api error ", e);
                    }
                }

                final List<UserCurrencyBalanceSnapshotDTO> authorities = pagelist;

                //创建单元格并设置单元格内容
                row2.createCell(0).setCellValue("用户ID");
                row2.createCell(1).setCellValue("BTC估值");
                row2.createCell(2).setCellValue("创建时间");

                //动态生成总列数,根据任务ID查询币种个数
                int count = 0;
                try {
                    final ResponseResult<ScheduleTaskConfigDTO<ScheduleTaskConfigDTO.SnapshotUserCurrencyBalance>> snapshotUserCurrencyBalanceTaskConfig =
                            this.spotClientCurrency.getById(taskid);
                    final List<Integer> ls = Arrays.asList(snapshotUserCurrencyBalanceTaskConfig.getData().getConfigJson().getCurrencyId());
                    count = ls.size();
                    final List rowlist = new ArrayList();
                    //解析json行数
                    for (int w = 0; w < authorities.size(); w++) {

                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final HSSFRow row3 = sheet.createRow(w + 1);

                        row3.createCell(0).setCellValue(authorities.get(w).getUserId().toString());
                        row3.createCell(1).setCellValue(formater.format(authorities.get(w).getBitcoinAssets()).toString());
                        row3.createCell(2).setCellValue(sdf.format(authorities.get(w).getCreateTime()));

                        int columncount = 0;
                        for (int www = 0; www < count; www++) {
                            final int column = columncount + www + 3;
                            row2.createCell(column).setCellValue(this.getsymbolname(ls.get(www)) + "可用余额");
                            rowlist.add(ls.get(www) + "可用余额");
                            row2.createCell(column + 1).setCellValue(this.getsymbolname(ls.get(www)) + "冻结余额");
                            rowlist.add(ls.get(www) + "冻结余额");

                            final int row = columncount + www + 3;
                            //存储生成的列数
                            row3.createCell(row).setCellValue("0");
                            row3.createCell(row + 1).setCellValue("0");

                            columncount++;

                        }
                        for (int ww = 0; ww < rowlist.size(); ww++) {

                            if (authorities.get(w).getObject().size() > ww) {
                                final String currency = authorities.get(w).getObject().get(ww).getCurrencyId().toString() + "可用余额";

                                final int rowcount = rowlist.indexOf(currency) + 3;
                                row3.createCell(rowcount).setCellValue(formater.format(authorities.get(w).getObject().get(ww).getAvailable()).toString());
                                row3.createCell(rowcount + 1).setCellValue(formater.format(authorities.get(w).getObject().get(ww).getHold()).toString());
                            }

                        }

                    }
                } catch (final Exception e) {
                    log.error("get configjson api error", e);
                }
            } else {
                log.info("down excel code" + pageRecordResponseResultpage.getCode() + "message" + pageRecordResponseResultpage.getMsg());
            }
        } catch (final Exception e) {
            log.error("down excel error ", e);
        }

        OutputStream fos = null;
        try {
            fos = response.getOutputStream();
            final String userAgent = request.getHeader("USER-AGENT");
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            final String dateStr = format.format(new Date());
            //文件名
            String fileName = dateStr;
            try {
                if (StringUtils.contains(userAgent, "Mozilla")) {
                    fileName = new String(fileName.getBytes(), "ISO8859-1");
                } else {
                    fileName = URLEncoder.encode(fileName, "utf8");
                }
            } catch (final UnsupportedEncodingException e) {
                log.error("", e);
            }

            response.setCharacterEncoding("UTF-8");
            // 设置contentType为excel格式
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "Attachment;Filename=" + fileName + ".xls");
            wb.write(fos);
            fos.close();
        } catch (final IOException e) {
            log.error("", e);
        }
    }
}
