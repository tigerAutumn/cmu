package cc.newex.dax.perpetual.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.CurrencyPairDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The interface Currency pair admin client.
 */
@FeignClient(value = "${newex.feignClient.dax.perpetual}",
        path = "/admin/v1/perpetual/currencypair")
public interface CurrencyPairAdminClient {

    /**
     * Add currency pair response result.
     *
     * @param currencyPairDto the currency pair dto
     * @return the response result
     */
    @PostMapping(value = "/add")
    ResponseResult<Integer> addCurrencyPair(@RequestBody final CurrencyPairDTO currencyPairDto);

    /**
     * Update currency pair response result.
     *
     * @param currencyPairDto the currency pair dto
     * @return the response result
     */
    @PostMapping(value = "/update")
    ResponseResult<Integer> updateCurrencyPair(@RequestBody final CurrencyPairDTO currencyPairDto);

    /**
     * Remove currency pair response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/remove")
    ResponseResult<Integer> removeCurrencyPair(@RequestBody final Integer id);

    /**
     * Query currency pair response result.
     *
     * @param pager     the pager
     * @param biz       the biz
     * @param indexBase the indexBase
     * @param quote     the quote
     * @param pairCode  the pair code
     * @param online    the online
     * @return the response result
     */
    @PostMapping(value = "/query")
    ResponseResult<DataGridPagerResult<CurrencyPairDTO>> queryCurrencyPair(
            @RequestBody final DataGridPager<?> pager,
            @RequestParam(value = "biz", required = false) final String biz,
            @RequestParam(value = "indexBase", required = false) final String indexBase,
            @RequestParam(value = "quote", required = false) final String quote,
            @RequestParam(value = "pairCode", required = false) final String pairCode,
            @RequestParam(value = "online", required = false) final Integer online);

    /**
     * List currency pair code response result.
     *
     * @return the response result
     */
    @GetMapping(value = "/pairCodes")
    ResponseResult<List<String>> listCurrencyPairCode();

    /**
     * List currency base code response result.
     *
     * @return the response result
     */
    @GetMapping(value = "/baseCodes")
    ResponseResult<List<String>> listCurrencyBaseCode();
}
