package cc.newex.dax.perpetual.rest.controller.admin.v1.ccex;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.domain.CurrencyPairBrokerRelation;
import cc.newex.dax.perpetual.dto.CurrencyPairBrokerRelationDTO;
import cc.newex.dax.perpetual.service.CurrencyPairBrokerRelationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The triggerBy Currency pair broker relation admin controller.
 *
 * @author better
 * @date create in 2018/11/22 5:40 PM
 */
@RestController
@RequestMapping(value = "/admin/v1/perpetual/currencyPairBrokerRelation")
public class CurrencyPairBrokerRelationAdminController {

    private final CurrencyPairBrokerRelationService service;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Instantiates a new Currency pair broker relation admin controller.
     *
     * @param service the service
     */
    @Autowired
    public CurrencyPairBrokerRelationAdminController(final CurrencyPairBrokerRelationService service) {
        this.service = service;
    }

    /**
     * List currency pair broker relation response result.
     *
     * @param dataGridPager the data grid pager
     * @return the response result
     */
    @PostMapping(value = "/collections")
    public ResponseResult<DataGridPagerResult<CurrencyPairBrokerRelationDTO>> listCurrencyPairBrokerRelation(@RequestBody final DataGridPager<CurrencyPairBrokerRelationDTO> dataGridPager) {

        final PageInfo pageInfo = dataGridPager.toPageInfo();
        final List<CurrencyPairBrokerRelationDTO> result = this.service.listCurrencyPairBrokerRelationByConditon(pageInfo, dataGridPager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), result));
    }

    /**
     * Add currency pair broker relation response result.
     *
     * @param currencyPairBrokerRelation the currency pair broker relation
     * @return the response result
     */
    @PostMapping(value = "/")
    public ResponseResult<Integer> addCurrencyPairBrokerRelation(@RequestBody final CurrencyPairBrokerRelationDTO currencyPairBrokerRelation) {
        final CurrencyPairBrokerRelation target = this.modelMapper.map(currencyPairBrokerRelation, CurrencyPairBrokerRelation.class);
        return ResultUtils.success(this.service.add(target));
    }

    /**
     * Edit currency pair broker relation response result.
     *
     * @param id                         the id
     * @param currencyPairBrokerRelation the currency pair broker relation
     * @return the response result
     */
    @PutMapping(value = "/{id}")
    public ResponseResult<Integer> editCurrencyPairBrokerRelation(@PathVariable(value = "id") final Long id, @RequestBody final CurrencyPairBrokerRelationDTO currencyPairBrokerRelation) {

        final CurrencyPairBrokerRelation target = this.modelMapper.map(currencyPairBrokerRelation, CurrencyPairBrokerRelation.class);
        target.setId(id);
        return ResultUtils.success(this.service.editById(target));
    }

    /**
     * Delete currency pair broker relation response result.
     *
     * @param id the id
     * @return the response result
     */
    @DeleteMapping(value = "/{id}")
    public ResponseResult<Integer> deleteCurrencyPairBrokerRelation(@PathVariable(value = "id") final Long id) {

        return ResultUtils.success(this.service.removeById(id));
    }
}
