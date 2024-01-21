package cc.newex.dax.boss.web.controller.outer.v1.perpetual;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.web.model.perpetual.GlobalFeeVO;
import cc.newex.dax.perpetual.client.FeeAdminClient;
import cc.newex.dax.perpetual.dto.FeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 合约全局手续费的控制器
 *
 * @author better
 * @date create in 2018/11/20 11:28 AM
 */
@RestController
@RequestMapping(value = "/v1/boss/perpetual/global-fee")
public class GlobalFeeController {

    private final FeeAdminClient client;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Instantiates a new Global fee controller.
     *
     * @param client the client
     */
    @Autowired
    public GlobalFeeController(final FeeAdminClient client) {
        this.client = client;
    }

    /**
     * List perpetual global fee response result.
     *
     * @param dataGridPager the data grid pager
     * @param pairCode      the pair code
     * @param side          the side
     * @param brokerId      the broker id
     * @return the response result
     */
    @GetMapping(value = "/list")
    @OpLog(name = "查看合约全局手续费配置")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_GLOBAL_FEE_VIEW"})
    public ResponseResult<DataGridPagerResult<FeeDTO>> listPerpetualGlobalFee(final DataGridPager<FeeDTO> dataGridPager,
                                                                              final String pairCode, final Integer side, final Integer brokerId) {

        final FeeDTO queryParam = FeeDTO.builder().brokerId(brokerId).side(side).pairCode(pairCode).build();
        dataGridPager.setQueryParameter(queryParam);
        return this.client.listFeeByCondition(dataGridPager);
    }

    /**
     * Add perpetual global fee response result.
     *
     * @param globalFee the global fee
     * @return the response result
     */
    @PostMapping(value = "/add")
    @OpLog(name = "添加合约全局手续费配置")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_GLOBAL_FEE_ADD"})
    public ResponseResult<Integer> addPerpetualGlobalFee(final GlobalFeeVO globalFee) {
        final FeeDTO fee = this.modelMapper.map(globalFee, FeeDTO.class);
        return this.client.addFee(fee);
    }

    /**
     * Edit perpetual global fee response result.
     *
     * @param globalFee the global fee
     * @return the response result
     */
    @PostMapping(value = "/edit")
    @OpLog(name = "修改合约全局手续费配置")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_GLOBAL_FEE_EDIT"})
    public ResponseResult<Integer> editPerpetualGlobalFee(final GlobalFeeVO globalFee) {
        final FeeDTO fee = this.modelMapper.map(globalFee, FeeDTO.class);
        return this.client.editFee(globalFee.getId(), fee);
    }

    /**
     * Remove perpetual global fee response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/remove")
    @OpLog(name = "删除合约全局手续费配置")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_GLOBAL_FEE_DELETE"})
    public ResponseResult<Integer> removePerpetualGlobalFee(final Long id) {
        return this.client.removeFee(id);
    }
}
