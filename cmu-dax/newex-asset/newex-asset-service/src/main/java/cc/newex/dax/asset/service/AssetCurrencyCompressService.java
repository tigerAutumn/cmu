package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.criteria.AssetCurrencyCompressExample;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.AssetCurrencyCompress;
import cc.newex.dax.asset.domain.PageBossEntity;
import cc.newex.dax.asset.dto.AssetCurrencyDTO;
import cc.newex.wallet.currency.BizEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 币种表 服务接口
 *
 * @author newex-team
 * @date 2018-07-23
 */
public interface AssetCurrencyCompressService
        extends CrudService<AssetCurrencyCompress, AssetCurrencyCompressExample, Integer> {
    /**
     * 获取 AssetCurrency 币种对象
     *
     * @param currency 币种英文简写
     * @param biz      业务类型
     * @return
     */
    AssetCurrency getCurrency(String currency, String biz, Integer brokerId);


    AssetCurrency getCurrency(String symbol, Integer biz, Integer brokerId);

    /**
     * 查询所有的币种
     *
     * @param biz
     * @return
     */
    List<AssetCurrency> getAllCurrency(String biz, Integer brokerId);

    AssetCurrency convertFromCurrencyDto(AssetCurrencyDTO currencyDTO);

    List<AssetCurrency> getByExampleCustom(AssetCurrencyDTO currencyDTO, ShardTable table);

    AssetCurrency getOneCurrencyByExample(AssetCurrencyDTO currencyDTO);

    void addCurrencyDto(AssetCurrencyDTO currencyDTO);

    /**
     * 目前只能根据币种id 和brokerId更改币种信息
     *
     * @param currency
     * @param table
     * @return
     */
    ResponseResult editByIdCustom(AssetCurrency currency, ShardTable table);

    List<AssetCurrency> getAllByTable(ShardTable table, Integer brokerId);

    PageBossEntity getPageByExampleCustom(AssetCurrencyDTO currencyDTO, ShardTable table, PageInfo pageInfo);

    ResponseResult snapshot(BizEnum bizEnum, Integer type);

    Map<Integer, BigDecimal> getLatestTickerMap();

    BigDecimal coinConverseBTCRate(Integer currency, Map<Integer, BigDecimal> latestTickerMap);
}
