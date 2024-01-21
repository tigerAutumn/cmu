package cc.newex.dax.perpetual.service;

import java.util.List;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.FeeExample;
import cc.newex.dax.perpetual.domain.Fee;
import cc.newex.dax.perpetual.dto.FeeDTO;

/**
 * 平台手续费配置表 服务接口
 *
 * @author newex -team
 * @date 2018 -11-01 09:30:37
 */
public interface FeeService extends CrudService<Fee, FeeExample, Long> {

  /**
   * Gets list.
   *
   * @param pairCode the pair code
   * @return the list
   */
  List<Fee> getList(String pairCode);

  /**
   * List fee by condition list.
   *
   * @param pageInfo       the page info
   * @param queryParameter the query parameter
   * @return the list
   */
  List<FeeDTO> listFeeByCondition(PageInfo pageInfo, FeeDTO queryParameter);
}
