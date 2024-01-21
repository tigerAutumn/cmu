package cc.newex.dax.asset.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.criteria.UserAssetConfExample;
import cc.newex.dax.asset.domain.PageBossEntity;
import cc.newex.dax.asset.domain.UserAssetConf;
import cc.newex.dax.asset.service.UserAssetConfService;
import cc.newex.wallet.exception.UnsupportedCurrency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 2018/5/4
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/asset")
public class InnerUserAssetConfController {

    @Autowired
    private UserAssetConfService userAssetConfService;

    @PostMapping(value = "/user-asset-conf/edit")
    ResponseResult editUserAssetConf(@RequestParam("userId") final Long userId,
                                     @RequestParam("withdrawLimit") final BigDecimal withdrawLimit) {
        ResponseResult<PageBossEntity> result;
        try {
            final UserAssetConfExample example = new UserAssetConfExample();
            example.createCriteria()
                    .andUserIdEqualTo(userId);
            UserAssetConf userAssetConf = new UserAssetConf();
            userAssetConf.setWithdrawLimit(withdrawLimit);
            int num = userAssetConfService.editByExample(userAssetConf, example);
            Assert.isTrue(num > 0, "该币种不存在");
            result = ResultUtils.success();
        } catch (UnsupportedCurrency e) {
            InnerUserAssetConfController.log.error("editThreshold error", e);
            return ResultUtils.failure("editThreshold error");
        } catch (Throwable e) {
            InnerUserAssetConfController.log.error("editThreshold error", e);
            return ResultUtils.failure(e.getMessage());
        }

        return result;
    }

}
