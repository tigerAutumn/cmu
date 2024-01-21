package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.spot.client.SpotUserCurrencyBalanceClient;
import cc.newex.dax.spot.dto.ccex.UserCurrencyBalanceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 丁昆
 * @date 2018/6/4
 * @des 资产管理
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/spot/assetcmanage")
public class AssetcManageController {

    @Autowired
    private SpotUserCurrencyBalanceClient spotUserCurrencyBalanceClient;

    @GetMapping(value = "/list")
    @OpLog(name = "资产列表查询")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_USER_ASSET_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager pager, final Long userid) {
        final ResponseResult<List<UserCurrencyBalanceDTO>> result = this.spotUserCurrencyBalanceClient.all(userid, loginUser.getLoginBrokerId());
        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final List<UserCurrencyBalanceDTO> rows = result.getData();
        final Integer total = rows.size();

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total.longValue(), rows);

        return ResultUtils.success(dataGridPagerResult);
    }

}
