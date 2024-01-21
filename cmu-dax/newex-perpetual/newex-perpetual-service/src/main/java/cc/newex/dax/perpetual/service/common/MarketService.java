package cc.newex.dax.perpetual.service.common;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Deal;
import cc.newex.dax.perpetual.domain.MarketData;
import cc.newex.dax.perpetual.domain.bean.Currency;
import cc.newex.dax.perpetual.dto.DepthDataDTO;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.MarkIndexReasonablePriceDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MarketService {

    /**
     * 获取某币对的交易货币的标记价格和现货价格
     *
     * @param contract 合约对象 使用 contract.base 获取
     * @return
     */
    MarkIndexPriceDTO getMarkIndex(Contract contract);

    void removeNotInContractMarkIndex(List<Contract> contracts);

    /**
     * 获取指数价格队列
     *
     * @param contract
     * @return
     */
    List<MarkIndexPriceDTO> getMarkIndexList(List<Contract> contract);

    /**
     * 获取所有币对的标记价格
     *
     * @return
     */
    Map<String, MarkIndexPriceDTO> allMarkIndexPrice();

    /**
     * 获取深度的买一卖一价
     *
     * @param contract
     */
    DepthDataDTO getFirstDepthData(Contract contract);

    /**
     * 计算标记价格
     *
     * @param contract 使用 contract.base
     */
    void scheduleMarketPrice(Contract contract);

    /**
     * 获取合理价格
     *
     * @param contract   币对
     * @param diffMargin
     * @return
     */
    MarkIndexReasonablePriceDTO getReasonablePrice(Contract contract, BigDecimal diffMargin);

    /**
     * 构建KlineKey
     */
    String buildKlineKey(String contractCode, KlineEnum klineEnum);

    /**
     * 取得K线数据，默认从redis中取数据，如果Redis中不存在。则从DB中加载
     * 如果数据库中加载开关打开，直接从DB中加载
     */
    List<MarketData> getKlineData(Contract contract, KlineEnum klineEnum);

    /**
     * 直接从DB中加载Kline
     */
    List<MarketData> getMarketDatas(Contract contract, KlineEnum klineEnum);

    /**
     * 获取kline对象
     *
     * @param klineStr
     * @return
     */
    KlineEnum getKlineEnum(final String klineStr);

    /**
     * 返回合约的最新成交记录
     *
     * @param contract
     * @return
     */
    List<Deal> fills(Contract contract);

    /**
     * 获取现货币种
     *
     * @return
     */
    List<Currency> getCurrencies();
}
