package cc.newex.dax.boss.web.controller.outer.v1.perpetual;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.web.model.perpetual.UserFeeVO;
import cc.newex.dax.perpetual.client.UserFeeAdminClient;
import cc.newex.dax.perpetual.dto.UserFeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 合约币对的控制器
 *
 * @author better
 * @date create in 2018/11/20 11:28 AM
 */
@RestController
@RequestMapping(value = "/v1/boss/perpetual/user-fee")
public class UserFeeController {

    private final UserFeeAdminClient client;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Instantiates a new User fee controller.
     *
     * @param client the client
     */
    @Autowired
    public UserFeeController(final UserFeeAdminClient client) {
        this.client = client;
    }

    /**
     * List perpetual user fee response result.
     *
     * @param dataGridPager the data grid pager
     * @param pairCode      the pair code
     * @param side          the side
     * @param brokerId      the broker id
     * @return the response result
     */
    @GetMapping(value = "/list")
    @OpLog(name = "查看合约用户手续费配置")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_USER_FEE_VIEW"})
    public ResponseResult<DataGridPagerResult<UserFeeDTO>> listPerpetualUserFee(final DataGridPager<UserFeeDTO> dataGridPager,
                                                                                final String pairCode, final Integer side, final Integer brokerId) {

        final UserFeeDTO queryParam = UserFeeDTO.builder().brokerId(brokerId).side(side).pairCode(pairCode).build();
        dataGridPager.setQueryParameter(queryParam);
        return this.client.listUserFeeByCondition(dataGridPager);
    }

    /**
     * Add perpetual user fee response result.
     *
     * @param userFee the user fee
     * @return the response result
     */
    @PostMapping(value = "/add")
    @OpLog(name = "添加合约用户手续费配置")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_USER_FEE_ADD"})
    public ResponseResult<Integer> addPerpetualUserFee(final UserFeeVO userFee) {
        final UserFeeDTO clientParam = this.modelMapper.map(userFee, UserFeeDTO.class);
        return this.client.addUserFee(clientParam);
    }

    /**
     * Edit perpetual user fee response result.
     *
     * @param userFee the user fee
     * @return the response result
     */
    @PostMapping(value = "/edit")
    @OpLog(name = "修改合约用户手续费配置")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_USER_FEE_EDIT"})
    public ResponseResult<Integer> editPerpetualUserFee(final UserFeeVO userFee) {
        final UserFeeDTO clientParam = this.modelMapper.map(userFee, UserFeeDTO.class);
        return this.client.editUserFee(userFee.getId(), clientParam);
    }

    /**
     * Remove perpetual user fee response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/remove")
    @OpLog(name = "删除¬合约用户手续费配置")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_USER_FEE_DELETE"})
    public ResponseResult<Integer> removePerpetualUserFee(final Long id) {
        return this.client.removeUserFee(id);
    }
}
