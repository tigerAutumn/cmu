package cc.newex.dax.asset.service.impl;

import cc.newex.commons.dictionary.consts.BrokerIdConsts;
import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.common.consts.UserInfoCons;
import cc.newex.dax.asset.common.enums.CurrencyBizTypeEnum;
import cc.newex.dax.asset.common.util.BizClientUtil;
import cc.newex.dax.asset.criteria.BalanceSummaryExample;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.dao.BalanceSummaryRepository;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.BalanceSummary;
import cc.newex.dax.asset.domain.ReconciliationConf;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.dto.BizCurrencyBalance;
import cc.newex.dax.asset.service.*;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.wallet.client.WalletClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.CurrencyBalance;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static cc.newex.dax.asset.common.consts.RedisKeyCons.ALERT_TIMES;
import static cc.newex.dax.asset.common.consts.RedisKeyCons.LAST_RECONCILIATION_RECORD_ID;
import static cc.newex.dax.integration.dto.message.MessageTemplateConsts.SMS_WALLET_BALANCE_SUMMARY_ALERT;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-06-15
 */
@Slf4j
@Service
public class BalanceSummaryServiceImpl
        extends AbstractCrudService<BalanceSummaryRepository, BalanceSummary, BalanceSummaryExample, Long>
        implements BalanceSummaryService {

    @Value("${newex.asset.reconciliation.biz}")
    private String[] reconciliationBiz;

    @Autowired
    private BalanceSummaryService balanceSummaryService;
    @Autowired
    private TransferRecordService transferRecordService;
    @Autowired
    private AssetCurrencyCompressService currencyCompressService;

    @Autowired
    private ReconciliationConfService reconciliationConfService;
    @Autowired
    private WalletClient walletClient;
    @Autowired
    private MessageClient messageClient;

    @Autowired
    private AccountService accountService;

    private String EMPTY_JSON_STRING = "{}";


    @Override
    protected BalanceSummaryExample getPageExample(final String fieldName, final String keyword) {
        final BalanceSummaryExample example = new BalanceSummaryExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public BalanceSummaryExample getBalanceSummaryExample(final String currency, final Date time) {
        final BalanceSummaryExample example = new BalanceSummaryExample();
        final BalanceSummaryExample.Criteria criteria = example.createCriteria();
        if (currency != null) {
            CurrencyEnum currencyEnum = CurrencyEnum.parseName(currency);
            criteria.andCurrencyEqualTo(currencyEnum.getIndex());
        }
        if (time != null) {
            criteria.andCreateDateEqualTo(DateUtil.dateTimeMinuteTrim(time));
        }

        return example;
    }

    @Override
    public void getAndCheckBalanceSummary() {
        Date currentTime = Date.from(Instant.now().truncatedTo(ChronoUnit.MINUTES));
        List<String> needAlert = new ArrayList<>();

        // 0、获取币种列表(spot_asset_currency为准)
        try {
            List<AssetCurrency> currencyList = this.currencyCompressService.getAllCurrency(BizEnum.SPOT.getName(), null);

            // 1、获取阈值
            List<ReconciliationConf> confs = this.reconciliationConfService.getAll();
            Map<Integer, ReconciliationConf> confMap = confs.stream()
                    .collect(Collectors.toMap(ReconciliationConf::getCurrency, ReconciliationConf::selfReference));

            //从划转记录计算各个业务线应有的余额
            this.solveTransferRecord(confMap);

            Map<CurrencyEnum, BalanceSummary> balanceSummaryMap = new LinkedHashMap<>();

            //查询各个业务线的余额
            for (String biz : this.reconciliationBiz) {
                Map<CurrencyEnum, BizCurrencyBalance> currencyBalanceMap = BizClientUtil.querySystemBillTotal(biz);
                if (!CollectionUtils.isEmpty(currencyBalanceMap)) {
                    this.arrangeBalanceSummary(balanceSummaryMap, currencyBalanceMap, currentTime);
                }
            }
            //查询锁仓的余额
            Map<CurrencyEnum, BizCurrencyBalance> currencyBalanceMap = this.accountService.getLockBalanceSumMap();
            if (!CollectionUtils.isEmpty(currencyBalanceMap)) {
                this.arrangeBalanceSummary(balanceSummaryMap, currencyBalanceMap, currentTime);
            }


            log.info("get wallet balance");
            List<CurrencyBalance> walletBalance = this.walletClient.genAllBalance().getData();

            Map<CurrencyEnum, BigDecimal> walletBalanceMap = walletBalance.stream()
                    .collect(Collectors.toMap(balance -> CurrencyEnum.parseValue(balance.getCurrencyIndex()), CurrencyBalance::getBalance));

            //3、充值未确认、提现未确认
            List<Map<String, Object>> unconfirmedDepositRecordSum = this.transferRecordService.getUnconfirmedRecordSum(TransferType.DEPOSIT.getCode());
            Map<Integer, BigDecimal> depositUnconfirmedMap = unconfirmedDepositRecordSum.stream().collect(Collectors
                    .toMap(m -> (Integer) m.get("currency"), m -> (BigDecimal) m.get("amount")));

            List<Map<String, Object>> unconfirmedWithdrawRecordSum = this.transferRecordService.getUnconfirmedRecordSum(TransferType.WITHDRAW.getCode());
            Map<Integer, BigDecimal> withdrawUnconfirmedMap = unconfirmedWithdrawRecordSum.stream().collect(Collectors
                    .toMap(m -> (Integer) m.get("currency"), m -> (BigDecimal) m.get("amount")));

            List<BalanceSummary> balanceSummaryList = new LinkedList<>();
            balanceSummaryMap.forEach((currencyEnum, balanceSummary) -> {
                balanceSummary.setDepositUnconfirmed(depositUnconfirmedMap.getOrDefault(currencyEnum.getIndex(), BigDecimal.ZERO));
                balanceSummary.setWithdrawUnconfirmed(withdrawUnconfirmedMap.getOrDefault(currencyEnum.getIndex(), BigDecimal.ZERO));
                JSONObject bizBalanceJson = JSONObject.parseObject(balanceSummary.getBizBalance());
                BigDecimal bizTotal = bizBalanceJson.getBigDecimal("total");
                BigDecimal walletTotal = walletBalanceMap.getOrDefault(currencyEnum, BigDecimal.ZERO);
                balanceSummary.setWalletBalance(walletTotal);
                BigDecimal difference = walletTotal.subtract(bizTotal);
                ReconciliationConf conf = confMap.get(currencyEnum.getIndex());
                BigDecimal threshHold = ObjectUtils.isEmpty(conf) ? BigDecimal.ZERO : conf.getTotalThreshold();
                if (difference.compareTo(threshHold) < 0) {
                    needAlert.add(currencyEnum
                            + " [" + threshHold.toPlainString() + " > " + difference.toPlainString()
                            + "]");
                }
                balanceSummary.setDifference(difference);
                balanceSummaryList.add(balanceSummary);
            });

            int num = this.balanceSummaryService.batchAdd(balanceSummaryList);
            log.info("插入余额表{}", num);

            if (!CollectionUtils.isEmpty(needAlert)) {
                long maxTimes = 3;
                //long threeMinutes = 3 * 60;
                long alertTimes = REDIS.incr(ALERT_TIMES);
                //REDIS.expire(ALERT_TIMES, threeMinutes);
                if (alertTimes >= maxTimes) {
                    //5、报警
                    this.sendBalanceAlertSMSOrMail(org.apache.commons.lang3.StringUtils.join(needAlert, ","), currentTime, SMS_WALLET_BALANCE_SUMMARY_ALERT);

                    //6、停提现划转
                    this.stopAllWithdrawAndTransfer(currentTime, currencyList);
                }
            } else {
                REDIS.set(ALERT_TIMES, 0);
            }
        } catch (Throwable e) {
            log.error("getAndCheckBalanceSummary error", e);
            this.sendBalanceAlertSMSOrMail("getAndCheckBalanceSummary error" + e.toString(), currentTime, SMS_WALLET_BALANCE_SUMMARY_ALERT);
        }
    }

    private void arrangeBalanceSummary(Map<CurrencyEnum, BalanceSummary> balanceSummaryMap,
                                       Map<CurrencyEnum, BizCurrencyBalance> currencyBalanceMap, Date createTime) {
        currencyBalanceMap.forEach((currencyEnum, balance) -> {
            BalanceSummary balanceSummary = balanceSummaryMap.getOrDefault(currencyEnum, new BalanceSummary());

            if (!balanceSummaryMap.containsKey(currencyEnum)) {
                balanceSummary.setCreateDate(createTime);
                balanceSummary.setCurrency(currencyEnum.getIndex());
                balanceSummary.setBizBalance(this.EMPTY_JSON_STRING);
                balanceSummaryMap.put(currencyEnum, balanceSummary);
            }
            JSONObject bizBalanceJson = JSONObject.parseObject(balanceSummary.getBizBalance());
            BizEnum bizEnum = BizEnum.parseBiz(balance.getBiz());
            bizBalanceJson.put(bizEnum.getName(), balance.getUserBalance());
            BigDecimal total = bizBalanceJson.getBigDecimal("total");
            total = total == null ? BigDecimal.ZERO : total;
            total = total.add(balance.getUserBalance());
            bizBalanceJson.put("total", total);
            balanceSummary.setBizBalance(bizBalanceJson.toJSONString());
        });

    }

    //增量更新各个业务线应有的余额（从充值、提现、划转记录 推算）
    private Map<CurrencyEnum, Map<BizEnum, BigDecimal>> solveTransferRecord(Map<Integer, ReconciliationConf> confMap) {
        log.info("solveTransferRecord begin");
        String lastIdStr = REDIS.get(LAST_RECONCILIATION_RECORD_ID);
        Long lastId = Long.valueOf(StringUtils.hasText(lastIdStr) ? lastIdStr : "0");
        final TransferRecordExample example = new TransferRecordExample();
        example.createCriteria().andIdGreaterThan(lastId);
        final PageInfo page = new PageInfo();
        final int size = 200;
        page.setPageSize(size);
        page.setSortItem("id");
        page.setStartIndex(0);

        Map<CurrencyEnum, Map<BizEnum, BigDecimal>> currencyBizBalance = new LinkedHashMap<>();

        while (true) {
            final List<TransferRecord> transferRecords = this.transferRecordService.getByPage(page, example);
            transferRecords.forEach((record -> {
                CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
                Map<BizEnum, BigDecimal> bizBalance;
                if (currencyBizBalance.containsKey(currency)) {
                    bizBalance = currencyBizBalance.get(currency);
                } else {
                    bizBalance = new LinkedHashMap<>();
                    currencyBizBalance.put(currency, bizBalance);
                }
                this.solveBizBalance(bizBalance, record);

            }));

            if (!CollectionUtils.isEmpty(transferRecords)) {
                //缓存最后一条数据的id，下次直接从此id开始查询
                TransferRecord record = transferRecords.get(transferRecords.size() - 1);
                REDIS.set(LAST_RECONCILIATION_RECORD_ID, record.getId().toString());
            }

            if (transferRecords.size() < size) {
                break;
            }
            page.setStartIndex(page.getStartIndex() + size);
        }

        currencyBizBalance.forEach((currencyEnum, bizBalance) -> {
            ReconciliationConf conf = confMap.get(currencyEnum.getIndex());
            if (ObjectUtils.isEmpty(conf)) {
                conf = ReconciliationConf.builder()
                        .currency(currencyEnum.getIndex())
                        .totalThreshold(BigDecimal.ZERO)
                        .bizBalance(this.EMPTY_JSON_STRING)
                        .build();
                confMap.put(currencyEnum.getIndex(), conf);
                this.reconciliationConfService.add(conf);
            }

            JSONObject bizBalanceJson = JSONObject.parseObject(StringUtils.hasText(conf.getBizBalance()) ?
                    conf.getBizBalance() : this.EMPTY_JSON_STRING);
            bizBalance.forEach((bizEnum, balance) -> {
                BigDecimal oldBalance = (BigDecimal) bizBalanceJson.getOrDefault(bizEnum.getName(), BigDecimal.ZERO);
                BigDecimal newBalance = oldBalance.add(balance);
                bizBalanceJson.put(bizEnum.getName(), newBalance);
            });
            conf.setBizBalance(bizBalanceJson.toJSONString());
            this.reconciliationConfService.editById(conf);

        });


        log.info("solveTransferRecord end");
        return currencyBizBalance;
    }

    private void solveBizBalance(Map<BizEnum, BigDecimal> bizBalance, TransferRecord record) {
        TransferType type = TransferType.convert(record.getTransferType());
        BizEnum fromBiz = BizEnum.parseBiz(record.getBiz());
        switch (type) {
            case DEPOSIT: {
                BigDecimal balance = bizBalance.getOrDefault(fromBiz, BigDecimal.ZERO);
                balance = balance.add(record.getAmount());
                bizBalance.put(fromBiz, balance);
                break;
            }
            case WITHDRAW: {
                BigDecimal balance = bizBalance.getOrDefault(fromBiz, BigDecimal.ZERO);
                balance = balance.subtract(record.getAmount().add(record.getFee()));
                bizBalance.put(fromBiz, balance);
                break;
            }
            case TRANSFER: {
                fromBiz = BizEnum.parseBiz(record.getFrom());
                BizEnum toBiz = BizEnum.parseBiz(record.getTo());

                BigDecimal fromBalance = bizBalance.getOrDefault(fromBiz, BigDecimal.ZERO);
                fromBalance = fromBalance.subtract(record.getAmount());
                bizBalance.put(fromBiz, fromBalance);

                BigDecimal toBalance = bizBalance.getOrDefault(toBiz, BigDecimal.ZERO);
                toBalance = toBalance.add(record.getAmount());
                bizBalance.put(toBiz, toBalance);

                break;
            }
            case LOCKED_POSITION: {
                fromBiz = BizEnum.parseBiz(record.getFrom());
                BizEnum toBiz = BizEnum.ASSET;

                BigDecimal fromBalance = bizBalance.getOrDefault(fromBiz, BigDecimal.ZERO);
                fromBalance = fromBalance.subtract(record.getAmount());
                bizBalance.put(fromBiz, fromBalance);

                BigDecimal toBalance = bizBalance.getOrDefault(toBiz, BigDecimal.ZERO);
                toBalance = toBalance.add(record.getAmount());
                bizBalance.put(toBiz, toBalance);

                break;
            }
            case UNLOCKED_POSITION: {
                fromBiz = BizEnum.ASSET;
                BizEnum toBiz = BizEnum.parseBiz(record.getTo());

                BigDecimal fromBalance = bizBalance.getOrDefault(fromBiz, BigDecimal.ZERO);
                fromBalance = fromBalance.subtract(record.getAmount());
                bizBalance.put(fromBiz, fromBalance);

                BigDecimal toBalance = bizBalance.getOrDefault(toBiz, BigDecimal.ZERO);
                toBalance = toBalance.add(record.getAmount());
                bizBalance.put(toBiz, toBalance);

                break;
            }
            case ADD_TOKEN: {
                BizEnum toBiz = BizEnum.parseBiz(record.getTo());
                BigDecimal toBalance = bizBalance.getOrDefault(toBiz, BigDecimal.ZERO);
                toBalance = toBalance.add(record.getAmount());
                bizBalance.put(toBiz, toBalance);

                break;
            }
            default:
                log.info("solveBizBalance do not support {}", type);
                break;

        }

    }

    private int stopAllWithdrawAndTransfer(Date time, List<AssetCurrency> currencyList) {
        //停提现(以spot_asset_currency为准)
        ResponseResult snapshot = this.currencyCompressService.snapshot(BizEnum.SPOT, CurrencyBizTypeEnum.CURRENCY_WITHDRAW_TRANSFER_FROZEN.getType());

        if (snapshot.getCode() > 0) {
            //发邮件
            StringBuilder sb = new StringBuilder();
            for (AssetCurrency currency : currencyList) {
                sb.append(CurrencyEnum.parseValue(currency.getId()).getName());
                sb.append(":");
                sb.append(currency.getWithdrawable());
                sb.append("，");
            }
            this.sendBalanceAlertSMSOrMail(sb.toString(), time, MessageTemplateConsts.MAIL_WALLET_BALANCE_WITHDRABLE_STATUS);
        }
        return snapshot.getCode();
    }

    private void sendBalanceAlertSMSOrMail(String content, Date threshold, String template) {
        if (!StringUtils.isEmpty(content)) {
            final JSONObject params = new JSONObject();
            params.put("threshold", threshold.toString());
            params.put("currency", content);

            MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                    .countryCode(UserInfoCons.USER_AREA_CODE_CN_KEY)
                    .locale("zh-cn")
                    .templateCode(template)
                    .params(params.toJSONString())
                    .brokerId(BrokerIdConsts.COIN_MEX)
                    .build();

            if (SMS_WALLET_BALANCE_SUMMARY_ALERT.equals(template)) {
                messageReqDTO.setMobile(UserInfoCons.BALANCE_SUMMARY_ALERT_CONTACT);
                BalanceSummaryServiceImpl.log.info("对账发送告警短信，内容为：{}，发送状态{}", params);
            } else if (MessageTemplateConsts.MAIL_WALLET_BALANCE_WITHDRABLE_STATUS.equals(template)) {
                messageReqDTO.setEmail(UserInfoCons.BALANCE_SUMMARY_WITHDRABLE_STATUS);
                BalanceSummaryServiceImpl.log.info("停止提现发送邮件，内容为：{}，发送状态{}", params);
            }

            ResponseResult<String> send = this.messageClient.send(messageReqDTO);
            BalanceSummaryServiceImpl.log.info("发送状态{}", send.getMsg());
        }
    }

    /**
     * 清理一周之前的数据
     */
    @Override
    public void removeDataBeforeTime(Date time) {

        BalanceSummaryExample example = new BalanceSummaryExample();
        example.createCriteria().andCreateDateLessThan(time);
        example.setOrderByClause("id desc");
        BalanceSummary summary = this.getOneByExample(example);
        example = new BalanceSummaryExample();
        example.createCriteria().andIdLessThan(summary.getId());
        this.removeByExample(example);
    }

}