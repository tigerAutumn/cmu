package cc.newex.dax.perpetual.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.FeeDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * The interface Fee admin client.
 *
 * @author better
 */
@FeignClient(value = "${newex.feignClient.dax.perpetual}", path = "/admin/v1/perpetual/fee")
public interface FeeAdminClient {

    /**
     * Add fee response result.
     *
     * @param fee the fee
     * @return the response result
     */
    @PostMapping(value = "/")
    ResponseResult<Integer> addFee(@RequestBody final FeeDTO fee);

    /**
     * Edit fee response result.
     *
     * @param id  the id
     * @param fee the fee
     * @return the response result
     */
    @PutMapping(value = "/{id}")
    ResponseResult<Integer> editFee(@PathVariable(value = "id") Long id, @RequestBody final FeeDTO fee);

    /**
     * Remove fee response result.
     *
     * @param id the id
     * @return the response result
     */
    @DeleteMapping(value = "/{id}")
    ResponseResult<Integer> removeFee(@PathVariable(value = "id") Long id);

    /**
     * Query fee response result.
     *
     * @param dataGridPager the data grid pager
     * @return the response result
     */
    @PostMapping(value = "/collections")
    ResponseResult<DataGridPagerResult<FeeDTO>> listFeeByCondition(@RequestBody final DataGridPager<FeeDTO> dataGridPager);
}
