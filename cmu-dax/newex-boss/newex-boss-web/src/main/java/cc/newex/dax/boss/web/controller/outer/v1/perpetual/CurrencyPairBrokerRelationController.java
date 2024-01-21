package cc.newex.dax.boss.web.controller.outer.v1.perpetual;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.web.model.perpetual.CurrencyPairBrokerRelationVO;
import cc.newex.dax.perpetual.client.CurrencyPairBrokerRelationAdminClient;
import cc.newex.dax.perpetual.dto.CurrencyPairBrokerRelationDTO;
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
@RequestMapping(value = "/v1/boss/perpetual/currencyPairBrokerRelation")
public class CurrencyPairBrokerRelationController {

    private final CurrencyPairBrokerRelationAdminClient client;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Instantiates a new Global fee controller.
     *
     * @param client the client
     */
    @Autowired
    public CurrencyPairBrokerRelationController(final CurrencyPairBrokerRelationAdminClient client) {
        this.client = client;
    }

    /**
     * List perpetual global fee response result.
     *
     * @param dataGridPager the data grid pager
     * @param pairCode      the pair code
     * @param brokerId      the broker id
     * @return the response result
     */
    @GetMapping(value = "/list")
    @OpLog(name = "查看合约币对券商关联纪录")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_CURRENCY_PAIR_BROKER_RELATION_VIEW"})
    public ResponseResult<DataGridPagerResult<CurrencyPairBrokerRelationDTO>> listPerpetualCurrencyPairBrokerRelation(final DataGridPager<CurrencyPairBrokerRelationDTO> dataGridPager,
                                                                                                     final String pairCode, final Integer brokerId) {

        final CurrencyPairBrokerRelationDTO queryParam = new CurrencyPairBrokerRelationDTO();
        queryParam.setBrokerId(brokerId);
        queryParam.setPairCode(pairCode);
        dataGridPager.setQueryParameter(queryParam);
        return this.client.listCurrencyPairBrokerRelation(dataGridPager);
    }

    /**
     * Add perpetual global fee response result.
     *
     * @param currencyPairBrokerRelation the currency pair broker relation
     * @return the response result
     */
    @PostMapping(value = "/add")
    @OpLog(name = "添加合约币对券商关联纪录")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_CURRENCY_PAIR_BROKER_RELATION_ADD"})
    public ResponseResult<Integer> addPerpetualCurrencyPairBrokerRelation(final CurrencyPairBrokerRelationVO currencyPairBrokerRelation) {
        final CurrencyPairBrokerRelationDTO target = this.modelMapper.map(currencyPairBrokerRelation, CurrencyPairBrokerRelationDTO.class);
        return this.client.addCurrencyPairBrokerRelation(target);
    }

    /**
     * Edit perpetual global fee response result.
     *
     * @param currencyPairBrokerRelation the currency pair broker relation
     * @return the response result
     */
    @PostMapping(value = "/edit")
    @OpLog(name = "修改合约币对券商关联纪录")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_CURRENCY_PAIR_BROKER_RELATION_EDIT"})
    public ResponseResult<Integer> editPerpetualCurrencyPairBrokerRelation(final CurrencyPairBrokerRelationVO currencyPairBrokerRelation) {
        final CurrencyPairBrokerRelationDTO target = this.modelMapper.map(currencyPairBrokerRelation, CurrencyPairBrokerRelationDTO.class);
        return this.client.editCurrencyPairBrokerRelation(currencyPairBrokerRelation.getId(), target);
    }
}
