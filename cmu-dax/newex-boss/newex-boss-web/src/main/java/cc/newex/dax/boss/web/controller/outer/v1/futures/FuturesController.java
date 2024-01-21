package cc.newex.dax.boss.web.controller.outer.v1.futures;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author:gigi
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/futures")
public class FuturesController {

    @GetMapping(value = "/list")
    public ResponseResult list(DataGridPager pager){
         return ResultUtil.getCheckedResponseResult(null);
    }
}
