package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.common.exception.AssetBizException;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.dto.LuckWinExchangeVO;
import cc.newex.dax.asset.dto.PayTokenReqDto;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-04-06
 */
public interface TransferRecordService
        extends CrudService<TransferRecord, TransferRecordExample, Long> {

    ResponseResult createTransferRecord(TransferRecord record);

    ResponseResult retryTransferRecord(TransferRecord record);

    boolean sendWithdraw(TransferRecord record);

    TransferRecord getAndLockOneByExample(TransferRecordExample example);

    List<Map<String, Object>> getUnconfirmedRecordSum(Integer transferType);

    void analyseTransferData();

    void confirmLockPositionRecord(String tradeNo, long userId, int currencyId, TransferRecord oneByExample, BigDecimal amount);

    TransferRecord getByTradeNo(String tradeNo);

    ResponseResult payToken(PayTokenReqDto payTokenReqDto, String biz, Long userId, Integer brokerId);

    ResponseResult winExchange(CurrencyEnum currencyEnum, BizEnum fromBiz, BizEnum toBiz, Long userId, LuckWinExchangeVO luckWinExchangeVO, Integer brokerId) throws AssetBizException;

    /**
     * 获取充值未到业务账的列表
     *
     * @return
     */
    List<TransferRecord> getDepositNotBizList(Long userId, int currencyId, int brokerId, String address);
}
