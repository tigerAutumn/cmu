package cc.newex.dax.perpetual.service;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.AssetsTransferExample;
import cc.newex.dax.perpetual.domain.AssetsTransfer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资金划转交易 服务接口
 *
 * @author newex-team
 * @date 2018-11-01 09:33:04
 */
public interface AssetsTransferService
        extends CrudService<AssetsTransfer, AssetsTransferExample, Long> {

    /**
     * 资金转入
     */
    void transferIn(String tradeNo, long userId, Integer brokerId, String currencyCode, BigDecimal amount,
                    TransferType transferType);

    /**
     * 资金转出
     */
    AssetsTransfer transferOut(String tradeNo, long userId, Integer brokerId, String currencyCode, BigDecimal amount,
                               TransferType transferType);

    List<AssetsTransfer> transferList(Integer brokerId, Long[] userIds, String[] currencyCodes, String[] tradeNos, Date startTime, Date endTime,
                                      PageInfo pageInfo);

    /**
     * 通过转账类型查询未处理的 转账记录
     */
    List<AssetsTransfer> listNotYetAssetsTransferByType(TransferType... transferTypeEnum);

    void noticeAssetsTransfer(Long assetsTransferId);
}
