package cc.newex.dax.boss.common.util;

import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author allen
 * @date 2018/5/31
 * @des
 */
public class ResultUtil {

    /**
     * 统一返回处理
     *
     * @param result
     * @return
     */
    public static ResponseResult getCheckedResponseResult(final ResponseResult result) {
        if (result == null) {
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

        if (result.getCode() != 0) {
            return ResultUtils.failure(result.getMsg());
        }

        return ResultUtils.success(result.getData());
    }

    /**
     * 封装DataGrid前端显示数据，以避免数据为空、服务不可用等异常情况时前端js报异常
     *
     * @return
     */
    public static ResponseResult getDataGridResult() {
        return getDataGridResult(0L, null);
    }

    /**
     * 封装DataGrid前端显示数据，以避免数据为空、服务不可用等异常情况时前端js报异常
     *
     * @param total
     * @param dataList
     * @param <T>
     * @return
     */
    public static <T> ResponseResult getDataGridResult(Long total, List<T> dataList) {
        if (total <= 0L || dataList == null) {
            total = 0L;
            dataList = new ArrayList<>();
        }

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total, dataList);

        return ResultUtils.success(dataGridPagerResult);
    }

    /**
     * 封装DataGrid前端显示数据，以避免数据为空、服务不可用等异常情况时前端js报异常
     *
     * @param result data里面封装的是分页对象，包含total、rows两个属性
     * @param <T>
     * @return
     */
    public static <T> ResponseResult getDataGridResult(final ResponseResult result) {
        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return getDataGridResult();
        }

        return result;
    }

}
