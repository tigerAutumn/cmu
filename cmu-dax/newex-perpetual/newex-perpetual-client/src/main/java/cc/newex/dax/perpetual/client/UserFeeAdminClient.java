package cc.newex.dax.perpetual.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.UserFeeDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * The interface User fee admin client.
 * @author better
 */
@FeignClient(value = "${newex.feignClient.dax.perpetual}", path = "/admin/v1/perpetual/userFee")
public interface UserFeeAdminClient {

    /**
     * Add user fee response result.
     *
     * @param userFee the user fee
     * @return the response result
     */
    @PostMapping(value = "/")
    ResponseResult<Integer> addUserFee(@RequestBody final UserFeeDTO userFee);

    /**
     * Edit user fee response result.
     *
     * @param id      the id
     * @param userFee the user fee
     * @return the response result
     */
    @PutMapping(value = "/{id}")
    ResponseResult<Integer> editUserFee(@PathVariable(value = "id") Long id, @RequestBody final UserFeeDTO userFee);

    /**
     * Remove user fee response result.
     *
     * @param id the id
     * @return the response result
     */
    @DeleteMapping(value = "/{id}")
    ResponseResult<Integer> removeUserFee(@PathVariable(value = "id") Long id);

    /**
     * List user fee by condition response result.
     *
     * @param dataGridPager the data grid pager
     * @return the response result
     */
    @PostMapping(value = "/collections")
    ResponseResult<DataGridPagerResult<UserFeeDTO>> listUserFeeByCondition(@RequestBody final DataGridPager<UserFeeDTO> dataGridPager);
}
