package cc.newex.dax.boss.web.controller.outer.v1.perpetual;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.perpetual.client.AccountAdminClient;
import cc.newex.dax.perpetual.dto.UserBalanceDTO;
import cc.newex.dax.perpetual.dto.UserBalanceParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * The type Perpetual controller.
 *
 * @author better
 * @date create in 2019-01-16 11:41
 */
@RestController
@RequestMapping(value = "/v1/boss/perpetual")
public class PerpetualController {

    private final AccountAdminClient accountAdminClient;

    /**
     * Instantiates a new Perpetual controller.
     *
     * @param accountAdminClient the account admin client
     */
    @Autowired
    public PerpetualController(final AccountAdminClient accountAdminClient) {
        this.accountAdminClient = accountAdminClient;
    }

    /**
     * 获取合约资产信息
     *
     * @param userId the user id
     * @return response result
     */
    @GetMapping(value = "/assert/{userId}")
    public ResponseResult<DataGridPagerResult<UserBalanceDTO>> assertInfo(@PathVariable(value = "userId") final Long userId,
                                                                          @CurrentUser final User user,
                                                                          final DataGridPager<UserBalanceParam> pager) {

        final UserBalanceParam queryParam =
                UserBalanceParam.builder().userId(Collections.singletonList(userId)).brokerId(user.getLoginBrokerId()).build();

        pager.setQueryParameter(queryParam);
        return this.accountAdminClient.account(pager);
    }
}
