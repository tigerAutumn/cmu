package cc.newex.dax.extra.service.vlink;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.vlink.ContractInfoExample;
import cc.newex.dax.extra.domain.vlink.ContractInfo;
import cc.newex.dax.spot.dto.ccex.ActRecordDTO;
import cc.newex.dax.spot.dto.result.model.Currency;

import java.util.List;

/**
 * vlink合约购买记录 服务接口
 *
 * @author mbg.generated
 * @date 2018-10-27 22:42:17
 */
public interface ContractInfoService extends CrudService<ContractInfo, ContractInfoExample, Long> {
    /**
     * 获取用户邮箱
     *
     * @param userId
     * @return
     */
    String getEmail(Long userId);

    /**
     * 根据币种简称获取币种信息
     *
     * @param currencyCode
     * @return
     */
    Currency getCurrency(String currencyCode);

    /**
     * 划转
     *
     * @param actRecordDTOList
     * @return
     */
    Boolean transfer(List<ActRecordDTO> actRecordDTOList, ContractInfo info);

    /**
     * 转账退回操作
     * @return
     */
    Boolean transferBack(List<ActRecordDTO> actRecordDTOList);

}