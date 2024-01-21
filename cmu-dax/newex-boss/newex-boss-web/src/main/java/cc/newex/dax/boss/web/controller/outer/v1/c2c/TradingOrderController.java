package cc.newex.dax.boss.web.controller.outer.v1.c2c;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.c2c.client.C2CTradingOrderAdminClient;
import cc.newex.dax.c2c.dto.admin.TradingOrderQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liutiejun
 * @date 2019-03-25
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/c2c/trading-order")
public class TradingOrderController {

    @Autowired
    private C2CTradingOrderAdminClient c2CTradingOrderAdminClient;

    @GetMapping(value = "/list")
    @OpLog(name = "法币广告单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_USER_AD_VIEW", "ROLE_C2C_ALL_AD_VIEW"})
    public ResponseResult list(final DataGridPager<TradingOrderQueryDTO> pager,
                               @RequestParam(value = "userId", required = false) final Long userId,
                               @RequestParam(value = "postType", required = false) final Integer postType,
                               @RequestParam(value = "status", required = false) final Integer status,
                               @RequestParam(value = "postId", required = false) final Long postId) {

        final TradingOrderQueryDTO.TradingOrderQueryDTOBuilder builder = TradingOrderQueryDTO.builder();

        if (userId != null && userId > 0) {
            builder.userId(userId);
        }

        if (postType != null && postType >= 0) {
            builder.isBuy(postType);
        }

        if (status != null && status >= 0) {
            builder.status(status);
        }

        if (postId != null) {
            builder.publicTradingOrderId(postId);
        }

        final TradingOrderQueryDTO tradingOrderQueryDTO = builder.build();

        pager.setQueryParameter(tradingOrderQueryDTO);

        final ResponseResult responseResult = this.c2CTradingOrderAdminClient.getUserTradingList(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

}
