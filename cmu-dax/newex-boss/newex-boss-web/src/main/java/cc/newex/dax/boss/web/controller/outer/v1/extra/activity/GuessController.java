package cc.newex.dax.boss.web.controller.outer.v1.extra.activity;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.activity.client.luckdraw.LuckDrawExpandClient;
import cc.newex.dax.boss.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Slf4j
@RequestMapping("/v1/boss/activity/guess")
public class GuessController {

    @Autowired
    private LuckDrawExpandClient luckDrawExpandClient;

    @GetMapping(value = "/list")
    @OpLog(name = "数据列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_GUESS_VIEW"})
    public ResponseResult list(DataGridPager pager) {
        return ResultUtil.getDataGridResult();
    }

    @PostMapping(value = "/add")
    @OpLog(name = "添加数据")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_GUESS_ADD"})
    public ResponseResult list(@RequestParam(value = "minPrice") BigDecimal minPrice,
                               @RequestParam(value = "maxPrice") BigDecimal maxPrice,
                               @RequestParam(value = "count") Integer count) {
        log.info(minPrice + "--" + maxPrice + "--" + count);
        if (minPrice.compareTo(BigDecimal.ZERO) > 0 && maxPrice.compareTo(minPrice) > 0 && count < 100) {
            ResponseResult result = luckDrawExpandClient.hashLuckDraw(minPrice, maxPrice, count);
            return ResultUtil.getCheckedResponseResult(result);
        }
        return ResultUtils.failure("input error");
    }


}
