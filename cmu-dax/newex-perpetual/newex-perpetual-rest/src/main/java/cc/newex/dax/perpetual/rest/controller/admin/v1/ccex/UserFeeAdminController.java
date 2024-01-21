package cc.newex.dax.perpetual.rest.controller.admin.v1.ccex;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.domain.UserFee;
import cc.newex.dax.perpetual.dto.UserFeeDTO;
import cc.newex.dax.perpetual.service.UserFeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * The triggerBy User fee admin controller.
 *
 * @author better
 */
@RestController
@RequestMapping("/admin/v1/perpetual/userFee")
@Slf4j
public class UserFeeAdminController {

    @Resource
    private UserFeeService service;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Add user fee response result.
     *
     * @param userFee the user fee
     * @return the response result
     */
    @PostMapping(value = "/")
    public ResponseResult<Integer> addUserFee(@RequestBody final UserFeeDTO userFee) {

        final UserFee target = this.modelMapper.map(userFee, UserFee.class);
        this.checkParams(target);
        return ResultUtils.success(this.service.add(target));
    }


    /**
     * Edit user fee response result.
     *
     * @param id      the id
     * @param userFee the user fee
     * @return the response result
     */
    @PutMapping(value = "/{id}")
    public ResponseResult<Integer> editUserFee(@PathVariable(value = "id") final Long id, @RequestBody final UserFeeDTO userFee) {

        final UserFee target = this.modelMapper.map(userFee, UserFee.class);
        this.checkParams(target);
        target.setId(id);
        return ResultUtils.success(this.service.editById(target));
    }

    /**
     * Remove user fee response result.
     *
     * @param id the id
     * @return the response result
     */
    @DeleteMapping(value = "/{id}")
    public ResponseResult<Integer> removeUserFee(@PathVariable(value = "id") final Long id) {
        return ResultUtils.success(this.service.removeById(id));
    }

    /**
     * List user fee by condition response result.
     *
     * @param dataGridPager the data grid pager
     * @return the response result
     */
    @PostMapping(value = "/collections")
    public ResponseResult<DataGridPagerResult<UserFeeDTO>> listUserFeeByCondition(@RequestBody final DataGridPager<UserFeeDTO> dataGridPager) {

        final PageInfo pageInfo = dataGridPager.toPageInfo();
        final List<UserFeeDTO> result = this.service.listUserFeeByCondition(pageInfo, dataGridPager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), result));
    }

    /**
     * 检查参数
     *
     * @param userFee
     */
    private void checkParams(final UserFee userFee) {
        if (userFee.getSide() == null
                || (userFee.getSide() != 0 && userFee.getSide() != 1 && userFee.getSide() != 2)) {
            throw new BizException("side illegal");
        }
        if (userFee.getRate() == null || userFee.getRate().abs().compareTo(BigDecimal.ONE) >= 0) {
            throw new BizException("rate illegal");
        }
    }
}
