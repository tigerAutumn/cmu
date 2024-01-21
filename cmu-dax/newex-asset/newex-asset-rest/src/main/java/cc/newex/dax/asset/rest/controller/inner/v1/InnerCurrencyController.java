package cc.newex.dax.asset.rest.controller.inner.v1;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.PageBossEntity;
import cc.newex.dax.asset.dto.AssetCurrencyDTO;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.exception.UnsupportedCurrency;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @data 2018/5/4
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/asset")
public class InnerCurrencyController {

    @Autowired
    private AssetCurrencyCompressService currencyCompressService;

    @GetMapping("/currency/all")
    ResponseResult<?> getAllCurrencies(@RequestParam("biz") String biz, @RequestParam(value = "brokerId", required = false) Integer brokerId) {
        log.info("getAllCurrencies params {}", biz);
        try {
            if (StringUtils.isEmpty(biz)) {
                return ResultUtils.failure("业务类型错误");
            }
            List<AssetCurrency> currencies = this.currencyCompressService.getAllCurrency(biz, brokerId);
            currencies.parallelStream().forEach(cur -> cur.setBiz(biz));

            log.info("getAllCurrencies nums {}", currencies.size());
            return ResultUtils.success(currencies);
        } catch (final Throwable e) {
            log.error("getAllCurrencies error {} ", e);
            return ResultUtils.failure("查询所有币种错误");
        }
    }

    @RequestMapping("checkCurrencyAvailable")
    public ResponseResult<Boolean> checkCurrencyAvailable(@RequestParam("currency") String currency) {
        try {
            CurrencyEnum currencyEnum = CurrencyEnum.parseName(currency);
            return ResultUtils.success(true);
        } catch (UnsupportedCurrency e) {
            return ResultUtils.success(false);
        }
    }

    /**
     * 查詢幣種
     *
     * @param dataGridPager
     * @return
     */
    @RequestMapping("/currency/get")
    ResponseResult<?> getCurrencies(@RequestBody DataGridPager<AssetCurrencyDTO> dataGridPager) {
        log.info("getCurrencies params:{}", JSONObject.toJSON(dataGridPager.getQueryParameter()));
        try {
            AssetCurrencyDTO currencyDTO = dataGridPager.getQueryParameter();
            if (StringUtils.isEmpty(currencyDTO.getBiz())) {
                return ResultUtils.failure("业务类型错误");
            }
            BizEnum bizEnum = BizEnum.parseBiz(currencyDTO.getBiz());
            ShardTable table = ShardTable.builder().prefix(bizEnum.getName()).build();
            int pageNum = dataGridPager.getPage();
            int pageSize = dataGridPager.getRows();
            int startIndex = (pageNum - 1) * pageSize;
            startIndex = startIndex >= 0 ? startIndex : 0;
            pageSize = pageSize >= 0 ? pageSize : 10;
            PageInfo pageInfo = new PageInfo();
            pageInfo.setSortItem("id");
            pageInfo.setPageSize(pageSize);
            pageInfo.setStartIndex(startIndex);
            pageInfo.setSortType(PageInfo.SORT_TYPE_DES);
            PageBossEntity bossEntity = this.currencyCompressService.getPageByExampleCustom(currencyDTO, table, pageInfo);
            log.info("getCurrencies num {} ", bossEntity.getTotal());
            return ResultUtils.success(bossEntity);

        } catch (final Throwable e) {
            log.info("getCurrencies error {}", e);
            return ResultUtils.failure("查询币种错误");
        }
    }

    /**
     * 添加幣種到各個業務線
     *
     * @param currencyDTO
     * @return
     */
    @PostMapping("/currency/add")
    ResponseResult<?> addCurrency(@RequestBody @Valid AssetCurrencyDTO currencyDTO) {
        log.info("addCurrency begin params {}", currencyDTO);
        try {
            BizEnum bizEnum = BizEnum.parseBiz(currencyDTO.getBiz());
            AssetCurrency exist = this.currencyCompressService.getOneCurrencyByExample(currencyDTO);
            if (!ObjectUtils.isEmpty(exist)) {
                return ResultUtils.failure(currencyDTO.getSymbol() + " has existed");
            }
            CurrencyEnum currencyEnum = CurrencyEnum.parseName(currencyDTO.getSymbol());
            currencyDTO.setId(currencyEnum.getIndex());

            this.currencyCompressService.addCurrencyDto(currencyDTO);
            log.info("addCurrency ok {} ", currencyDTO);
            return ResultUtils.success(currencyDTO);

        } catch (UnsupportedCurrency e) {
            return ResultUtils.failure("币种不支持:" + currencyDTO.getSymbol());
        } catch (final Throwable e) {
            log.info("addCurrency error {}", e);

            return ResultUtils.failure("addCurrency error");
        }
    }

    @PutMapping("/currency/edit")
    ResponseResult<?> editCurrency(@RequestBody AssetCurrencyDTO currencyDTO) {
        log.info("editCurrency begin {} ", currencyDTO);
        try {
            if (currencyDTO.getBrokerId() == null){
                currencyDTO.setBrokerId(1);
            }
            BizEnum bizEnum = BizEnum.parseBiz(currencyDTO.getBiz());
            ShardTable table = ShardTable.builder().prefix(bizEnum.getName()).build();
            AssetCurrency currency = this.currencyCompressService.convertFromCurrencyDto(currencyDTO);
            return this.currencyCompressService.editByIdCustom(currency, table);
        } catch (final Throwable e) {
            log.info("edit currency error {}", e);
            return ResultUtils.failure("编辑币种失败");
        }
    }

    /**
     * 一键冻结  划转和提现
     *
     * @param biz  业务类型 默认传spot
     * @param type 1是冻结 2是解冻
     * @return
     */
    @RequestMapping("/currency/frozen")
    public ResponseResult oneStepFrozen(@RequestParam("biz") String biz,
                                        @RequestParam("type") Integer type) {
        BizEnum bizEnum = BizEnum.parseBiz(biz);
        try {
            //保存快照
            return this.currencyCompressService.snapshot(bizEnum, type);
        } catch (Exception e) {
            log.error("one step frozen withdraw and transfer error {}", e);
            return ResultUtils.failure("一键关停提现划转失败");
        }
    }

    @GetMapping("maxBtcWithdrawAmount")
    public ResponseResult maxBtcWithdrawAmount() {
        String btcAmountLimit = REDIS.get(RedisKeyCons.ASSET_WITHDRAW_BTC_AMOUNT_LIMIT);
        Map<String, String> data = new HashMap<>(2);
        if (StringUtils.isEmpty(btcAmountLimit)) {
            String maxAmount = "1";
            data.put("currency", maxAmount);
        } else {
            data.put("currency", btcAmountLimit);
        }
        return ResultUtils.success(data);
    }

    @PostMapping("setMaxBtcWithdrawAmount")
    public ResponseResult setMaxBtcWithdrawAmount(@RequestParam("maxAmount") String maxAmount) {
        REDIS.set(RedisKeyCons.ASSET_WITHDRAW_BTC_AMOUNT_LIMIT, maxAmount);
        Map<String, String> data = new HashMap<>(2);
        data.put("currency", maxAmount);
        return ResultUtils.success(data);
    }



    @GetMapping("auditBtcWithdrawAmount")
    public ResponseResult auditBtcWithdrawAmount() {
        String btcAmountLimit = REDIS.get(RedisKeyCons.ASSET_WITHDRAW_BTC_AUDIT_AMOUNT_LIMIT);
        Map<String, String> data = new HashMap<>(2);
        if (StringUtils.isEmpty(btcAmountLimit)) {
            String maxAmount = "1";
            data.put("currency", maxAmount);
        } else {
            data.put("currency", btcAmountLimit);
        }
        return ResultUtils.success(data);
    }

    /**
     * 设置提现审核额度阈值（btc）
     *
     * @param auditAmount 阈值 超过多少额度需要人工审核
     * @return
     */
    @PostMapping("setAuditAmount")
    public ResponseResult setAuditBtcWithdrawAmount(@RequestParam("auditAmount") String auditAmount) {
        REDIS.set(RedisKeyCons.ASSET_WITHDRAW_BTC_AUDIT_AMOUNT_LIMIT, auditAmount);
        Map<String, String> data = new HashMap<>(2);
        data.put("currency", auditAmount);
        return ResultUtils.success(data);
    }

}
