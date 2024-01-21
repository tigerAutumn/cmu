package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.UserFeeExample;
import cc.newex.dax.perpetual.domain.UserFee;
import cc.newex.dax.perpetual.dto.UserFeeDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户手续费配置表 服务接口
 *
 * @author newex -team
 * @date 2018 -11-01 09:30:43
 */
public interface UserFeeService extends CrudService<UserFee, UserFeeExample, Long> {
    /**
     * 获取用户手续费列表
     *
     * @param pairCode the pair code
     * @return the list
     */
    List<UserFee> getList(String pairCode);

    /**
     * 获取手续费
     *
     * @param pairCode the pair code
     * @return the rate
     */
    BigDecimal getRate(String pairCode);


    /**
     * List user fee by condition list.
     *
     * @param pageInfo       the page info
     * @param queryParameter the query parameter
     * @return the list
     */
    List<UserFeeDTO> listUserFeeByCondition(PageInfo pageInfo, UserFeeDTO queryParameter);
}