package cc.newex.dax.perpetual.rest.controller.admin.v1.ccex;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.domain.Fee;
import cc.newex.dax.perpetual.dto.FeeDTO;
import cc.newex.dax.perpetual.service.FeeService;
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
 * The triggerBy Fee admin controller.
 *
 * @author better
 */
@RestController
@RequestMapping("/admin/v1/perpetual/fee")
@Slf4j
public class FeeAdminController {

    @Resource
    private FeeService service;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Add fee response result.
     *
     * @param fee the fee
     * @return the response result
     */
    @PostMapping(value = "/")
    public ResponseResult<Integer> addFee(@RequestBody final FeeDTO fee) {
        final Fee target = this.modelMapper.map(fee, Fee.class);
        this.checkParams(target);
        return ResultUtils.success(this.service.add(target));
    }

    /**
     * Edit fee response result.
     *
     * @param id  the id
     * @param fee the fee
     * @return the response result
     */
    @PutMapping(value = "/{id}")
    public ResponseResult<Integer> editFee(@PathVariable(value = "id") final Long id, @RequestBody final FeeDTO fee) {
        final Fee target = new ModelMapper().map(fee, Fee.class);
        this.checkParams(target);
        target.setId(id);
        return ResultUtils.success(this.service.editById(target));
    }

    /**
     * Remove fee response result.
     *
     * @param id the id
     * @return the response result
     */
    @DeleteMapping(value = "/{id}")
    public ResponseResult<Integer> removeFee(@PathVariable(value = "id") final Long id) {
        return ResultUtils.success(this.service.removeById(id));
    }

    /**
     * Query fee response result.
     *
     * @param dataGridPager the data grid pager
     * @return the response result
     */
    @PostMapping(value = "/collections")
    public ResponseResult<DataGridPagerResult<FeeDTO>> listFeeByCondition(@RequestBody final DataGridPager<FeeDTO> dataGridPager) {

        final PageInfo pageInfo = dataGridPager.toPageInfo();

        final List<FeeDTO> result = this.service.listFeeByCondition(pageInfo, dataGridPager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), result));
    }

    /**
     * 插件参数
     *
     * @param fee
     */
    private void checkParams(final Fee fee) {
        if (fee.getSide() == null || (fee.getSide() != 0 && fee.getSide() != 1 && fee.getSide() != 2)) {
            throw new BizException("side illegal");
        }
        if (fee.getRate() == null || fee.getRate().abs().compareTo(BigDecimal.ONE) >= 0) {
            throw new BizException("rate illegal");
        }
    }
}
