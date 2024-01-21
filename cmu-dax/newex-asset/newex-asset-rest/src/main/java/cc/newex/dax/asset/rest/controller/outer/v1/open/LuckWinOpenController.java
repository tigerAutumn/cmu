package cc.newex.dax.asset.rest.controller.outer.v1.open;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author newex
 */
@Slf4j
@RestController
@RequestMapping("/v1/asset/open/lucky-win")
public class LuckWinOpenController {

    @RequestMapping("/callback")
    public ResponseResult callback(@RequestBody String data) {

        return ResultUtils.success();
    }
}
