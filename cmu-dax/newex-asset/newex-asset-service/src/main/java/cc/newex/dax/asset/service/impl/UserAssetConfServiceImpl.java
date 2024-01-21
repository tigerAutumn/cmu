package cc.newex.dax.asset.service.impl;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.criteria.UserAssetConfExample;
import cc.newex.dax.asset.dao.UserAssetConfRepository;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.domain.UserAssetConf;
import cc.newex.dax.asset.dto.WithDrawInfoDto;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.asset.service.UserAssetConfService;
import cc.newex.dax.perpetual.client.inner.TransferInnerClient;
import cc.newex.dax.perpetual.dto.UserAssetsBalanceDTO;
import cc.newex.dax.spot.client.inner.SpotUserCurrencyBalanceClient;
import cc.newex.dax.spot.dto.ccex.UserCurrencyBalanceDTO;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-06-26
 */
@Slf4j
@Service
public class UserAssetConfServiceImpl
        extends AbstractCrudService<UserAssetConfRepository, UserAssetConf, UserAssetConfExample, Long>
        implements UserAssetConfService {

    private static final Map<Integer, BigDecimal> KYC_WITHDRAW_LIMIT_BTC = new HashMap<Integer, BigDecimal>() {{
        this.put(0, BigDecimal.valueOf(0));
        this.put(1, BigDecimal.valueOf(2));
        this.put(2, BigDecimal.valueOf(100));
    }};

    @Autowired
    TransferRecordService recordService;
    @Autowired
    UsersClient usersClient;
    @Autowired
    SpotUserCurrencyBalanceClient spotUserCurrencyBalanceClient;
    @Autowired
    AssetCurrencyCompressService currencyService;
    @Autowired
    private UserAssetConfRepository userAssetConfRepos;
    @Autowired
    private TransferInnerClient perpetualTransferClient;

    @Override
    protected UserAssetConfExample getPageExample(String fieldName, String keyword) {
        final UserAssetConfExample example = new UserAssetConfExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public UserAssetConf getByUserId(Long userId) {
        final UserAssetConfExample example = new UserAssetConfExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return this.userAssetConfRepos.selectOneByExample(example);
    }

    @Override
    public WithDrawInfoDto getTransferMsg(BizEnum bizEnum, Long userId, String currency, Integer brokerId) {
        WithDrawInfoDto withDrawInfoDto = WithDrawInfoDto.builder().build();
        CurrencyEnum currencyEnum = CurrencyEnum.parseName(currency);

        Map<Integer, BigDecimal> latestTickerMap = this.currencyService.getLatestTickerMap();
        BigDecimal rate = this.currencyService.coinConverseBTCRate(currencyEnum.getIndex(), latestTickerMap);
        //1、24小时提现限额
        BigDecimal withdrawLimitBTC = this.getWithdrawLimitBTC(userId);

        //2、24小时已提现额度
        BigDecimal withdrawBTC = this.getWithdrawBTC(userId, brokerId);

        //3、可用提现额度= 1 - 2
        BigDecimal canUsedWithdrawBTC = withdrawLimitBTC.subtract(withdrawBTC);

        //4、所有币种未确认资产
        BigDecimal allUnconfirmedBTC = this.getAllUnconfirmedBTC(userId, brokerId);

        //5、当前币种可用余额
        Map<String, List<BigDecimal>> allCanUsedBTCMap = this.getAllCanUsedBTC(bizEnum, userId, brokerId);
        BigDecimal canUsed = allCanUsedBTCMap.get(currencyEnum.name()) == null ?
                BigDecimal.ZERO : allCanUsedBTCMap.get(currencyEnum.name()).get(0);
        BigDecimal canUsedBTC = allCanUsedBTCMap.get(currencyEnum.name()) == null ?
                BigDecimal.ZERO : allCanUsedBTCMap.get(currencyEnum.name()).get(1);

        //6、所有币种可用余额
        BigDecimal allCanUsedBTC = allCanUsedBTCMap.keySet().stream()
                .map(a -> allCanUsedBTCMap.get(a).get(1))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //7、所有币种已确认(由于汇率波动 当＜0时候，取0)
        BigDecimal allConfirmedBTC = allCanUsedBTC.subtract(allUnconfirmedBTC);
        allConfirmedBTC = allConfirmedBTC.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : allConfirmedBTC;

        //8、*可提现额度 = min{3,5,7}
        BigDecimal canWithdrawBTC = canUsedBTC.compareTo(allConfirmedBTC) < 0 ? canUsedBTC : allConfirmedBTC;
        canWithdrawBTC = canWithdrawBTC.compareTo(canUsedWithdrawBTC) < 0 ? canWithdrawBTC : canUsedWithdrawBTC;

        //9、*暂不可提现 = 5 - 8，当＜0时候，取0
        BigDecimal canNotWithdrawBTC = canUsedBTC.subtract(canWithdrawBTC);
        canNotWithdrawBTC = canNotWithdrawBTC.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : canNotWithdrawBTC;
        withDrawInfoDto.setWithdrawLimitBTC(withdrawLimitBTC.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros());
        withDrawInfoDto.setWithdrawBTC(withdrawBTC.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros());
        if (rate.compareTo(BigDecimal.ZERO) == 0) {
            withDrawInfoDto.setCanNotWithdraw(BigDecimal.ZERO);
            BigDecimal canUsedBalance = allCanUsedBTCMap.get(currencyEnum.name()) == null ?
                    BigDecimal.ZERO : allCanUsedBTCMap.get(currencyEnum.name()).get(0);
            withDrawInfoDto.setCanWithdraw(canUsedBalance.setScale(8, BigDecimal.ROUND_DOWN));
        } else {
            BigDecimal canWithdraw = canWithdrawBTC.divide(rate, 8, BigDecimal.ROUND_DOWN).stripTrailingZeros();
            withDrawInfoDto.setCanWithdraw(canWithdraw);
            withDrawInfoDto.setCanNotWithdraw(canUsed.subtract(canWithdraw).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros());
        }
        withDrawInfoDto.setCanUsedWithdrawBTC(canUsedWithdrawBTC.stripTrailingZeros());
        withDrawInfoDto.setAllConfirmedBTC(allConfirmedBTC.stripTrailingZeros());
        withDrawInfoDto.setAllCanUsedBTC(allCanUsedBTC.stripTrailingZeros());
        withDrawInfoDto.setCanUsedBTC(canUsedBTC.stripTrailingZeros());
        withDrawInfoDto.setAllUnconfirmedBTC(allUnconfirmedBTC.stripTrailingZeros());
        return withDrawInfoDto;
    }


    /**
     * @description: 24小时提现限额：读取KYC信息配置。初始值为未过KYC，提现额度为0;通过KYC1，提现额度为2BTC;通过KYC2，提现额度为100BTC;
     * 更多额度，可通过boss后台，个人信息页面配置（boss后台增加提现额度调整功能）
     * @param: [userId]
     * @return: java.math.BigDecimal
     * @author: newex-team
     * @date: 2018/6/27 下午6:20
     */
    private BigDecimal getWithdrawLimitBTC(Long userId) {
        UserAssetConf conf = this.getByUserId(userId);
        if (conf != null) {
            return conf.getWithdrawLimit();
        } else {
            Integer kycLevel = this.usersClient.getUserKycLevel(userId).getData();
            log.info("用户kyc等级：kycLevel:{}, WithdrawLimit:{}", kycLevel, KYC_WITHDRAW_LIMIT_BTC.get(kycLevel));
            return KYC_WITHDRAW_LIMIT_BTC.get(kycLevel);
        }
    }

    /**
     * @description: 24小时已提现额度
     * @param: [userId]
     * @return: java.math.BigDecimal
     * @author: newex-team
     * @date: 2018/6/27 下午6:19
     */
    private BigDecimal getWithdrawBTC(Long userId, Integer brokerId) {
        Date now = new Date();
        Date yesterday = DateUtil.addDays(now, -1);
        BigDecimal withdrawBTC = BigDecimal.ZERO;
        String key = MessageFormat.format(RedisKeyCons.ASSET_RECORD_WITHDRAW_KEY_PRE_NEW, userId, brokerId);
        Set<String> recordSet = REDIS.zRangeByScore(key, yesterday.getTime(), now.getTime());
        if (!CollectionUtils.isEmpty(recordSet)) {
            withdrawBTC = recordSet.stream()
                    .filter((a) -> (Integer) JSONObject.parseObject(a).get("transferType") == TransferType.WITHDRAW.getCode())
                    .map((a) -> new BigDecimal(JSONObject.parseObject(a).get("amountBTC").toString()))
                    .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        }
        return withdrawBTC;
    }

    /**
     * @description: 所有币种未确认资产：指的已经达到确认到账区块数，可交易，但是未达到全确认区块数的资产。
     * @param: [userId]
     * @return: java.math.BigDecimal
     * @author: newex-team
     * @date: 2018/6/27 下午6:19
     */
    private BigDecimal getAllUnconfirmedBTC(Long userId, Integer brokerId) {
        BigDecimal allUnconfirmedBTC = BigDecimal.ZERO;
        TransferRecordExample example = new TransferRecordExample();

        // 未达到提现确认交易的id列表
        Set<String> ids = REDIS.zRange(RedisKeyCons.ASSET_RECORD_WITHDRAW_UNCONFIRM_KEY, 0, 0);
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andTransferTypeEqualTo(TransferType.DEPOSIT.getCode())
                .andStatusEqualTo((byte) TransferStatus.CONFIRMED.getCode())
                .andBrokerIdEqualTo(brokerId)
                .andIdGreaterThan(CollectionUtils.isEmpty(ids) ? 0L : Long.valueOf(ids.iterator().next()));
        List<TransferRecord> depositRecordList = this.recordService.getByExample(example);
        Map<Integer, BigDecimal> latestTickerMap = this.currencyService.getLatestTickerMap();
        if (!CollectionUtils.isEmpty(depositRecordList)) {
            allUnconfirmedBTC = depositRecordList.stream()
                    .filter(a -> a.getConfirmation() < CurrencyEnum.parseValue(a.getCurrency()).getWithdrawConfirmNum())
                    .map(a -> a.getAmount().multiply(this.currencyService.coinConverseBTCRate(a.getCurrency(), latestTickerMap)))
                    .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        }
        return allUnconfirmedBTC;
    }

    /**
     * @description: 所有币种可用余额
     * @param: [userId]
     * @return: java.math.BigDecimal
     * @author: newex-team
     * @date: 2018/6/27 下午6:19
     */
    private Map<String, List<BigDecimal>> getAllCanUsedBTC(BizEnum bizEnum, Long userId, Integer brokerId) {
        Map<Integer, BigDecimal> latestTickerMap = this.currencyService.getLatestTickerMap();
        Map<String, List<BigDecimal>> allCanUsedBTC = new HashMap<>();
        if (bizEnum.getIndex() != BizEnum.PERPETUAL.getIndex()) {
            List<UserCurrencyBalanceDTO> currencyBalance = this.spotUserCurrencyBalanceClient.balance(null, userId, brokerId).getData();
            allCanUsedBTC = new HashMap<>(currencyBalance.size());
            if (!CollectionUtils.isEmpty(currencyBalance)) {
                allCanUsedBTC = currencyBalance.stream()
                        .filter(a -> a.getBalance().compareTo(BigDecimal.ZERO) > 0)
                        .collect(Collectors.toMap(a -> a.getCurrency().toUpperCase(), (a) -> {
                            List<BigDecimal> balances = new LinkedList<>();
                            balances.add(a.getAvailable());
                            balances.add(a.getAvailable().multiply(this.currencyService.coinConverseBTCRate(a.getCurrencyId(), latestTickerMap)));
                            return balances;
                        }));
            }
        }
        if (bizEnum.getIndex() == BizEnum.PERPETUAL.getIndex()) {
            ResponseResult<List<UserAssetsBalanceDTO>> listResponseResult = perpetualTransferClient.queryUserBalance(userId, brokerId);
            List<UserAssetsBalanceDTO> currencyBalance = listResponseResult.getData();
            allCanUsedBTC = new HashMap<>(currencyBalance.size());
            if (!CollectionUtils.isEmpty(currencyBalance)) {
                allCanUsedBTC = currencyBalance.stream()
                        .filter(a -> a.getAvailableBalance().compareTo(BigDecimal.ZERO) > 0)
                        .collect(Collectors.toMap(a -> a.getCurrencyCode().toUpperCase(), (a) -> {
                            List<BigDecimal> balances = new LinkedList<>();
                            balances.add(a.getAvailableBalance());
                            balances.add(a.getAvailableBalance().multiply(this.currencyService.coinConverseBTCRate(CurrencyEnum.parseName(a.getCurrencyCode()).getIndex(), latestTickerMap)));
                            return balances;
                        }));
            }
        }
        return allCanUsedBTC;
    }

}