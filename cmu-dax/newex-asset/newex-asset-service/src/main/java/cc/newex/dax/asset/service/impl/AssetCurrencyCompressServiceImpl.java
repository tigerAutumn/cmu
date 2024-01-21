package cc.newex.dax.asset.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.enums.CurrencyBizTypeEnum;
import cc.newex.dax.asset.common.util.BizClientUtil;
import cc.newex.dax.asset.criteria.AssetCurrencyCompressExample;
import cc.newex.dax.asset.dao.AssetCurrencyCompressRepository;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.AssetCurrencyCompress;
import cc.newex.dax.asset.domain.CurrencyExtendAttributes;
import cc.newex.dax.asset.domain.PageBossEntity;
import cc.newex.dax.asset.dto.AssetCurrencyDTO;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.spot.client.inner.SpotLastTickerClient;
import cc.newex.dax.spot.dto.ccex.CurrencyPriceDTO;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 币种表 服务实现
 *
 * @author newex-team
 * @date 2018-07-23
 */
@Slf4j
@Service
public class AssetCurrencyCompressServiceImpl
        extends AbstractCrudService<AssetCurrencyCompressRepository, AssetCurrencyCompress, AssetCurrencyCompressExample, Integer>
        implements AssetCurrencyCompressService {

    @Autowired
    SpotLastTickerClient spotLastTickerClient;

    @Autowired
    private AssetCurrencyCompressRepository compressRepository;

    @Override
    protected AssetCurrencyCompressExample getPageExample(final String fieldName, final String keyword) {
        final AssetCurrencyCompressExample example = new AssetCurrencyCompressExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public AssetCurrency getCurrency(String currency, String biz, Integer brokerId) {
        AssetCurrencyCompressExample example = new AssetCurrencyCompressExample();
        example.createCriteria().andSymbolEqualTo(currency).andBrokerIdEqualTo(brokerId);
        BizEnum bizEnum = BizEnum.parseBiz(biz);
        ShardTable table = ShardTable.builder().prefix(bizEnum.getName()).build();
        AssetCurrencyCompress currencyCompress = this.getOneByExample(example, table);
        if (ObjectUtils.isEmpty(currencyCompress)) {
            return null;
        }
        CurrencyExtendAttributes extendAttributes = JSONObject.parseObject(currencyCompress.getExtendAttrs(), CurrencyExtendAttributes.class);
        AssetCurrency assetCurrency = AssetCurrency.builder().build();
        BeanUtils.copyProperties(currencyCompress, assetCurrency);
        BeanUtils.copyProperties(extendAttributes, assetCurrency);
        return assetCurrency;
    }

    @Override
    public AssetCurrency getCurrency(String symbol, Integer biz, Integer brokerId) {
        BizEnum bizEnum = BizEnum.parseBiz(biz);
        return this.getCurrency(symbol, bizEnum.getName(), brokerId);
    }

    @Override
    public List<AssetCurrency> getAllCurrency(String biz, Integer brokerId) {
        BizEnum bizEnum = BizEnum.parseBiz(biz);
        ShardTable table = ShardTable.builder().prefix(bizEnum.getName()).build();
        AssetCurrencyCompressExample example = new AssetCurrencyCompressExample();
        if (!ObjectUtils.isEmpty(brokerId)) {
            example.createCriteria().andBrokerIdEqualTo(brokerId);
        }
        return this.getAllByTable(table, brokerId);
    }

    @Override
    public AssetCurrency convertFromCurrencyDto(AssetCurrencyDTO currencyDTO) {
        AssetCurrency currency = new AssetCurrency();
        BeanUtils.copyProperties(currencyDTO, currency);
        return currency;
    }

    /**
     * if (!ObjectUtils.isEmpty(currency.getFullName())) {
     * criteria.andFullNameEqualTo(currency.getFullName());
     * }
     * if (!ObjectUtils.isEmpty(currency.getNeedTag())) {
     * criteria.andNeedTagEqualTo(currency.getNeedTag());
     * }
     *
     * @param table
     * @return
     */
    @Override
    public List<AssetCurrency> getByExampleCustom(AssetCurrencyDTO currencyDTO, ShardTable table) {
        AssetCurrency assetCurrency = this.convertFromCurrencyDto(currencyDTO);
        AssetCurrencyCompressExample example = AssetCurrencyCompressExample.from(assetCurrency);
        List<AssetCurrencyCompress> currencyCompresses = this.getByExample(example, table);
        List<AssetCurrency> assetCurrencies = new ArrayList<>();
        this.compressToAssetCurrency(currencyDTO, currencyCompresses, assetCurrencies);
        return assetCurrencies;
    }

    @Override
    public AssetCurrency getOneCurrencyByExample(AssetCurrencyDTO currencyDTO) {
        BizEnum bizEnum = BizEnum.parseBiz(currencyDTO.getBiz());
        ShardTable table = ShardTable.builder().prefix(bizEnum.getName()).build();
        AssetCurrencyCompressExample example = new AssetCurrencyCompressExample();
        example.createCriteria().andSymbolEqualTo(currencyDTO.getSymbol()).andBrokerIdEqualTo(currencyDTO.getBrokerId());
        AssetCurrencyCompress compress = this.getOneByExample(example, table);
        if (ObjectUtils.isEmpty(compress)) {
            return null;
        }
        CurrencyExtendAttributes extendAttributes = JSONObject.parseObject(compress.getExtendAttrs(), CurrencyExtendAttributes.class);
        AssetCurrency assetCurrency = new AssetCurrency();
        BeanUtils.copyProperties(compress, assetCurrency);
        BeanUtils.copyProperties(extendAttributes, assetCurrency);
        return assetCurrency;
    }

    @Override
    public void addCurrencyDto(AssetCurrencyDTO currencyDTO) {
        BizEnum bizEnum = BizEnum.parseBiz(currencyDTO.getBiz());
        AssetCurrency currency = this.convertFromCurrencyDto(currencyDTO);

        ShardTable table = ShardTable.builder().prefix(bizEnum.getName()).build();
        AssetCurrencyCompress compress = new AssetCurrencyCompress();
        BeanUtils.copyProperties(currency, compress);
        CurrencyExtendAttributes extendAttributes = new CurrencyExtendAttributes();
        BeanUtils.copyProperties(currencyDTO, extendAttributes);
        compress.setExtendAttrs(JSONObject.toJSONString(extendAttributes));
        this.add(compress, table);
        currencyDTO.setId(currency.getId());
        this.notifyBizCurrencies(bizEnum.getName());
    }

    @Override
    public ResponseResult editByIdCustom(AssetCurrency currency, ShardTable table) {
        AssetCurrencyCompressExample example = new AssetCurrencyCompressExample();
        example.createCriteria().andBrokerIdEqualTo(currency.getBrokerId())
                .andIdEqualTo(currency.getId());
        AssetCurrencyCompress compress = this.getOneByExample(example, table);
        if (!currency.getWithdrawable().equals(compress.getWithdrawable())
                || !currency.getTransfer().equals(compress.getTransfer())) {
            String cacheData = REDIS.get(RedisKeyCons.ASSET_CURRENCY_ONE_STEP_FROZEN_PRE);
            if (!StringUtils.isEmpty(cacheData)) {
                return ResultUtils.failure(BizErrorCodeEnum.SNAPING_CANT_EDIT_TRANSFER);
            }
        }
        BeanUtils.copyProperties(currency, compress);
        CurrencyExtendAttributes extendAttributes = JSONObject.parseObject(compress.getExtendAttrs(), CurrencyExtendAttributes.class);
        BeanUtils.copyProperties(currency, extendAttributes);
        compress.setExtendAttrs(JSONObject.toJSONString(extendAttributes));
        this.editByExample(compress, example, table);
        this.notifyBizCurrencies(table.getPrefix());
        return ResultUtils.success();
    }

    @Override
    public List<AssetCurrency> getAllByTable(ShardTable table, Integer brokerId) {
        AssetCurrencyCompressExample example = new AssetCurrencyCompressExample();
        if (!ObjectUtils.isEmpty(brokerId)) {
            example.createCriteria().andBrokerIdEqualTo(brokerId);
        }
        List<AssetCurrencyCompress> all = this.getByExample(example, table);
        List<AssetCurrency> assetCurrencies = new ArrayList<>();
        all.forEach(currencyCompress -> {
            AssetCurrency assetCurrency = AssetCurrency.builder().build();
            CurrencyExtendAttributes extendAttributes = JSONObject.parseObject(currencyCompress.getExtendAttrs(), CurrencyExtendAttributes.class);
            BeanUtils.copyProperties(currencyCompress, assetCurrency);
            BeanUtils.copyProperties(extendAttributes, assetCurrency);
            assetCurrencies.add(assetCurrency);
        });
        return assetCurrencies;
    }

    @Override
    public PageBossEntity getPageByExampleCustom(AssetCurrencyDTO currencyDTO, ShardTable table, PageInfo pageInfo) {
        AssetCurrency assetCurrency = this.convertFromCurrencyDto(currencyDTO);
        AssetCurrencyCompressExample example = AssetCurrencyCompressExample.from(assetCurrency);
        List<AssetCurrencyCompress> currencyCompresses = this.getByPage(pageInfo, example, table);
        List<AssetCurrency> assetCurrencies = new ArrayList<>();
        this.compressToAssetCurrency(currencyDTO, currencyCompresses, assetCurrencies);
        PageBossEntity pageEntity = new PageBossEntity();
        pageEntity.setRows(assetCurrencies);
        pageEntity.setTotal(this.compressRepository.countByPager(pageInfo, example, table));
        return pageEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult snapshot(BizEnum bizEnum, Integer type) {
        String cacheData = REDIS.get(RedisKeyCons.ASSET_CURRENCY_ONE_STEP_FROZEN_PRE);
        ShardTable table = ShardTable.builder().prefix(bizEnum.getName()).build();
        if (type == CurrencyBizTypeEnum.CURRENCY_WITHDRAW_TRANSFER_FROZEN.getType()) {
            if (!StringUtils.isEmpty(cacheData)) {
                return ResultUtils.failure("上次快照未释放");
            }
            List<AssetCurrencyCompress> allCurrency = this.getAll(table);
            REDIS.set(RedisKeyCons.ASSET_CURRENCY_ONE_STEP_FROZEN_PRE, JSONObject.toJSONString(allCurrency));

            AssetCurrencyCompressExample example = new AssetCurrencyCompressExample();
            AssetCurrencyCompress assetCurrency = new AssetCurrencyCompress();
            assetCurrency.setWithdrawable(CurrencyBizTypeEnum.CURRENCY_WITHDRAW_CLOSE.getType());
            assetCurrency.setTransfer(CurrencyBizTypeEnum.CURRENCY_TRANSFER_CLOSE.getType());
            int num = this.editByExample(assetCurrency, example, table);
            log.error("snapshot currency all status update num {}", num);
            this.notifyBizCurrencies(bizEnum.getName());
            return ResultUtils.success();
        } else {
            if (StringUtils.isEmpty(cacheData)) {
                return ResultUtils.failure("未查询到快照信息");
            }
            List<AssetCurrencyCompress> currencyCompresses = JSONArray.parseArray(cacheData, AssetCurrencyCompress.class);
            currencyCompresses.forEach(currency -> {
                        AssetCurrencyCompress update = new AssetCurrencyCompress();
                        update.setId(currency.getId());
                        update.setWithdrawable(currency.getWithdrawable());
                        update.setTransfer(currency.getTransfer());
                        this.editById(update, table);
                    }
            );
            REDIS.del(RedisKeyCons.ASSET_CURRENCY_ONE_STEP_FROZEN_PRE);
            this.notifyBizCurrencies(bizEnum.getName());
            return ResultUtils.success();
        }
    }

    private void compressToAssetCurrency(AssetCurrencyDTO currencyDTO, List<AssetCurrencyCompress> currencyCompresses, List<AssetCurrency> assetCurrencies) {
        currencyCompresses.forEach(assetCurrencyCompress -> {
            AssetCurrency currency = new AssetCurrency();
            BeanUtils.copyProperties(assetCurrencyCompress, currency);
            CurrencyExtendAttributes extendAttributes = JSONObject.parseObject(assetCurrencyCompress.getExtendAttrs(), CurrencyExtendAttributes.class);
            //filter
            if (!ObjectUtils.isEmpty(currencyDTO.getFullName())) {
                if (!currencyDTO.getFullName().equals(extendAttributes.getFullName())) {
                    return;
                }
            }
            if (!ObjectUtils.isEmpty(currencyDTO.getNeedTag())) {
                if (!currencyDTO.getNeedTag().equals(extendAttributes.getNeedTag())) {
                    return;
                }
            }
            BeanUtils.copyProperties(extendAttributes, currency);
            currency.setBiz(currencyDTO.getBiz());
            assetCurrencies.add(currency);
        });
    }

    //更新业务线的currency，更新自身的currency
    private void notifyBizCurrencies(String biz) {
        BizEnum bizEnum = BizEnum.parseBiz(biz);
        ShardTable table = ShardTable.builder().prefix(biz).build();
        List<AssetCurrency> currencies = this.getAllByTable(table, null);
        BizClientUtil.addCurrencies(bizEnum.getIndex(), JSONArray.toJSONString(currencies));
        //BizClientUtil.addCurrencies(BizEnum.C2C.getIndex(), JSONArray.toJSONString(currencies));
    }

    /**
     * @description: 获得交易区汇率列表（BTH、ETH）
     * @param: []
     * @return: java.util.Map<java.lang.Integer,java.math.BigDecimal>>
     * @author: newex-team
     * @date: 2018/6/28 上午10:29
     */
    @Override
    public Map<Integer, BigDecimal> getLatestTickerMap() {
        List<CurrencyPriceDTO> currencyPriceList = this.spotLastTickerClient.currencyPriceOfBTC().getData();
        return currencyPriceList.stream().collect(Collectors.toMap(CurrencyPriceDTO::getCurrencyId, CurrencyPriceDTO::getPrice));
    }

    /**
     * @description: 兑比特币汇率
     * @param: [currency, latestTickerMap]
     * @return: java.math.BigDecimal
     * @author: newex-team
     * @date: 2018/6/28 上午10:27
     */
    @Override
    public BigDecimal coinConverseBTCRate(Integer currency, Map<Integer, BigDecimal> latestTickerMap) {
        if (CurrencyEnum.BTC.getIndex() == currency) {
            return new BigDecimal("1");
        }

        BigDecimal result;
        try {
            if (latestTickerMap == null) {
                latestTickerMap = this.getLatestTickerMap();
            }

            result = latestTickerMap.get(currency);
            Assert.isTrue(result != null, "Exception: coinConverseBTCRate error, currencyId=" + currency
                    + ", latestTickerMap=" + JSONObject.toJSONString(latestTickerMap));
        } catch (Throwable e) {
            log.error(e.getMessage());
            return BigDecimal.ZERO;
        }
        return result;
    }
}