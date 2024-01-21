package cc.newex.dax.boss.web.controller.outer.v1.extra.activity;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.spot.client.SpotActivityClient;
import cc.newex.dax.spot.dto.ccex.ActivityDTO;
import cc.newex.dax.spot.dto.ccex.ActivityStatisticsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author newex-jinlong
 * @date 2018-04-28
 */
@RestController
@RequestMapping(value = "/v1/boss/activity/giveCandy")
@Slf4j

public class GiveCandyController {

    @Autowired
    private SpotActivityClient spotClient;

    /**
     * 文件上传解析
     *
     * @return
     */
    @RequestMapping(value = "/file-upload")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_UPLOAD"})
    public ResponseResult ossFile(final HttpServletRequest request) throws IOException {

        //创建一个通用的多部分解析器
        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //设置编码方式
        multipartResolver.setDefaultEncoding("utf-8");
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            final List<MultipartFile> file = multiRequest.getFiles("excelfile");
            //处理后的file
            if (file.get(0) == null || file.get(0).getSize() <= 0) {
                return ResultUtils.failure(BizErrorCodeEnum.Parsing_ERROR.getMessage());
            }
            InputStream inputStream = null;
            try {
                // 取得输入流
                inputStream = file.get(0).getInputStream();
            } catch (final IOException e) {
                log.error("importData----------->InputStream IOException", e);
            }

            Workbook workbook = null;
            try {
                // 解析2007版本
                workbook = new XSSFWorkbook(inputStream);
            } catch (final Exception ex) {
                log.error("importData-------2007------>InputStream", ex);
                try {
                    // 解析2003版本
                    final POIFSFileSystem pfs = new POIFSFileSystem(file.get(0).getInputStream());
                    workbook = new HSSFWorkbook(pfs);
                } catch (final IOException e) {
                    log.error("importData--------2003------>HSSFWorkbook Read InputStream Exception", e);
                }
            }

            //3.得到Excel工作表对象
            final Sheet sheet = workbook.getSheetAt(0);

            final int rowNum = sheet.getPhysicalNumberOfRows();
            try {
                for (int i = 0; i < rowNum; i++) {
                    final ActivityDTO activitedto = new ActivityDTO();
                    if (i != 0) {
                        //判断数据不全
                        if (sheet.getRow(i).getCell(1) == null || ("").equals(sheet.getRow(i).getCell(1).toString())) {
                            break;
                        }
                        activitedto.setActiveId(Double.valueOf(sheet.getRow(i).getCell(0).toString()).longValue());
                        activitedto.setActiveName(sheet.getRow(i).getCell(1).toString());
                        activitedto.setRaffleId(Double.valueOf(sheet.getRow(i).getCell(2).toString()).longValue());
                        activitedto.setUserId(Double.valueOf(sheet.getRow(i).getCell(3).toString()).longValue());
                        activitedto.setAmount(new BigDecimal(Double.valueOf(sheet.getRow(i).getCell(4).toString()).toString()));
                        activitedto.setCurrencyId(Double.valueOf(sheet.getRow(i).getCell(5).toString()).intValue());
                        try {
                            final ResponseResult<Boolean> booleanResponseResult = this.spotClient.addActivity(Arrays.asList(activitedto));
                            if (booleanResponseResult == null) {
                                return ResultUtils.failure(BizErrorCodeEnum.API_ERROR.getMessage());
                            }
                        } catch (final Exception e) {
                            log.error("analysis excel api error ", e);
                            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR.getMessage());
                        }
                    }

                }
                return ResultUtils.success(BizErrorCodeEnum.Parsing_SUCCESS.getMessage());

            } catch (final Exception e) {
                log.info("analysis excel error", e);
                return ResultUtils.failure(BizErrorCodeEnum.Parsing_ERROR.getMessage());
            }
        }
        return ResultUtils.success(BizErrorCodeEnum.Parsing_SUCCESS.getMessage());

    }

    @GetMapping(value = "/searchActivity")
    @OpLog(name = "活动ID搜索")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_VIEW"})
    public ResponseResult list(@RequestParam(value = "activityid", required = false) final Long activityid) {

        try {
            final ResponseResult<ActivityStatisticsDTO> result = this.spotClient.getInfo(activityid, null, null, null,null);
            if (result == null) {
                return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
            }
            if (result.getCode() == 0) {
                return ResultUtils.success(result);
            }
            return ResultUtils.failure(result.getMsg());
        } catch (final Exception e) {
            log.error("search activity api error", e);
        }
        return ResultUtils.success();
    }
}
