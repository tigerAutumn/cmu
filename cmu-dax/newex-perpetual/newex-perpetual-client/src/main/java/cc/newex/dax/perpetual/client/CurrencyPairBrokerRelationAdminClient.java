package cc.newex.dax.perpetual.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.CurrencyPairBrokerRelationDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * The interface Currency pair broker relation admin client.
 *
 * @author better
 * @date create in 2018/11/22 5:39 PM
 */
@FeignClient(value = "${newex.feignClient.dax.perpetual}", path = "/admin/v1/perpetual/currencyPairBrokerRelation")
public interface CurrencyPairBrokerRelationAdminClient {


    /**
     * List currency pair broker relation response result.
     *
     * @param dataGridPager the data grid pager
     * @return the response result
     */
    @PostMapping(value = "/collections")
    ResponseResult<DataGridPagerResult<CurrencyPairBrokerRelationDTO>> listCurrencyPairBrokerRelation(@RequestBody DataGridPager<CurrencyPairBrokerRelationDTO> dataGridPager);

    /**
     * Add currency pair broker relation response result.
     *
     * @param currencyPairBrokerRelation the currency pair broker relation
     * @return the response result
     */
    @PostMapping(value = "/")
    ResponseResult<Integer> addCurrencyPairBrokerRelation(@RequestBody CurrencyPairBrokerRelationDTO currencyPairBrokerRelation);

    /**
     * Edit currency pair broker relation response result.
     *
     * @param id                         the id
     * @param currencyPairBrokerRelation the currency pair broker relation
     * @return the response result
     */
    @PutMapping(value = "/{id}")
    ResponseResult<Integer> editCurrencyPairBrokerRelation(@PathVariable(value = "id") Long id, @RequestBody CurrencyPairBrokerRelationDTO currencyPairBrokerRelation);

    /**
     * Delete currency pair broker relation response result.
     *
     * @param id the id
     * @return the response result
     */
    @DeleteMapping(value = "/{id}")
    ResponseResult<Integer> deleteCurrencyPairBrokerRelation(@PathVariable(value = "id") Long id);
}
