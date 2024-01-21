package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.asset.criteria.TransferRecordAuditExample;
import cc.newex.dax.asset.domain.TransferRecordAudit;
import cc.newex.dax.asset.dto.TransferRecordAuditResDto;

import java.util.List;

/**
 *
 * @author newex-team
 * @date 2019-04-23 18:58:22
 */
public interface TransferRecordAuditService extends CrudService<TransferRecordAudit, TransferRecordAuditExample, Long> {
    TransferRecordAudit getByTradeNo(String tradeNo);
    //查询审核成功的提现记录
    List<TransferRecordAudit> getAuditSuccess();
    /**
     * 获取api secret list
     *
     * @param pageInfo
     * @param stauts
     * @param tradeNo
     * @param brokerId
     * @return
     */
    List<TransferRecordAudit> listByPage(PageInfo pageInfo,Integer stauts,String tradeNo, Integer brokerId);

}