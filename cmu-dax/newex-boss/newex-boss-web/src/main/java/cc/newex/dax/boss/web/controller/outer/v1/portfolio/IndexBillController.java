package cc.newex.dax.boss.web.controller.outer.v1.portfolio;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.portfolio.client.admin.AdminSystemBillTotalClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/v1/boss/portfolio")
@Slf4j
public class IndexBillController {
    @Autowired
    private AdminSystemBillTotalClient adminSystemBillTotalClient;

    @RequestMapping(value = "/bill")
    public ResponseResult list(@RequestParam(value = "currency", required = false) String currencyCode,
                               @RequestParam(value = "start", required = false) String start,
                               @RequestParam(value = "end", required = false) String end) {

        Long endTime = null;
        Long startTime = null;
        if (StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)) {
            try {
                startTime = DateUtils.parseDate(start, "yyyy-MM-dd HH:mm:ss").getTime();
                endTime = DateUtils.parseDate(end, "yyyy-MM-dd HH:mm:ss").getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        ResponseResult result = adminSystemBillTotalClient.querySystemBillTotal(currencyCode, startTime, endTime);
        return ResultUtil.getCheckedResponseResult(result);
    }
}
