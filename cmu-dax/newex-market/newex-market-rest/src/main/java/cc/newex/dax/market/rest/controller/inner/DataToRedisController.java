package cc.newex.dax.market.rest.controller.inner;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.domain.FetchingDataPath;
import cc.newex.dax.market.rest.common.util.DataKey;
import cc.newex.dax.market.rest.common.util.WebUtil;
import cc.newex.dax.market.service.impl.FetchingDataPathServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author
 * @date 2018/03/18
 **/
@Slf4j
@RestController
@RequestMapping("/inner/v1/market/data")
public class DataToRedisController {

    @Autowired
    private FetchingDataPathServiceImpl fetchingDataPathService;
    @Autowired
    private DataKey dataKey;

    @GetMapping("/getAllPath")
    public ResponseResult selectAllPath(@RequestParam(value = "key", required = false) final String key,
                                        final HttpServletRequest request) {
        if (StringUtils.isEmpty(key)) {
            log.error(" key is empty! ");
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_PARAM_EMPTY);
        }
        if (!this.dataKey.validateKey(key)) {
            final String ip = WebUtil.getClientAddress(request);
            log.error("datakey:{},key is {} !! illegal user! IP: {}", this.dataKey, key, ip);
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_AUTH_FAIL);
        }
        final List<FetchingDataPath> allPathRedis = this.fetchingDataPathService.getAllPathRedis();
        return ResultUtils.success(allPathRedis);
    }

}
