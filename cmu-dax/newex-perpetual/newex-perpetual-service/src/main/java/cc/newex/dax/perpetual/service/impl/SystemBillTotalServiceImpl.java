package cc.newex.dax.perpetual.service.impl;


import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.ConfigNameEnum;
import cc.newex.dax.perpetual.criteria.SystemBillExample;
import cc.newex.dax.perpetual.criteria.SystemBillTotalExample;
import cc.newex.dax.perpetual.criteria.SystemBillTotalExample.Criteria;
import cc.newex.dax.perpetual.data.SystemBillRepository;
import cc.newex.dax.perpetual.data.SystemBillTotalRepository;
import cc.newex.dax.perpetual.data.UserBalanceRepository;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.SystemBill;
import cc.newex.dax.perpetual.domain.SystemBillTotal;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.dto.SystemBillTotalDTO;
import cc.newex.dax.perpetual.service.CommonPropService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.SystemBillService;
import cc.newex.dax.perpetual.service.SystemBillTotalService;
import cc.newex.dax.perpetual.util.ObjectCopyUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 对账汇总表 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:32:47
 */
@Slf4j
@Service
public class SystemBillTotalServiceImpl extends
        AbstractCrudService<SystemBillTotalRepository, SystemBillTotal, SystemBillTotalExample, Long>
        implements SystemBillTotalService {
    /**
     * 记录币种对账不平次数，超过阈值后报警
     */
    private static final Map<String, Integer> CACHE_MAP = new ConcurrentHashMap<>();

    @Autowired
    private SystemBillTotalRepository systemBillTotalRepository;
    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private SystemBillRepository systemBillRepository;
    @Autowired
    private CommonPropService commonPropService;
    @Autowired
    private PerpetualConfig perpetualConfig;
    @Autowired
    private MessageClient messageClient;
    @Autowired
    private SystemBillService systemBillService;

    @Override
    protected SystemBillTotalExample getPageExample(final String fieldName, final String keyword) {
        final SystemBillTotalExample example = new SystemBillTotalExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public SystemBillTotal getSystemBillTotal(final String currencyCode, final Long endDateMillis) {
        final SystemBillTotalExample systemBillTotalExample = new SystemBillTotalExample();
        final Criteria criteria = systemBillTotalExample.createCriteria();
        criteria.andCurrencyCodeEqualTo(currencyCode);
        if (endDateMillis != null) {
            criteria.andCreatedDateLessThanOrEqualTo(new Date(endDateMillis));
        }
        return this.systemBillTotalRepository.selectOneByExample(systemBillTotalExample);
    }

    @Override
    public void buildSystemBillTotal() {
        log.info("SystemBillTotalServiceImpl buildSystemBillTotal begin");
        final Set<String> baseSet = new HashSet<>();
        this.currencyPairService.getOnlineCurrencyPair().forEach(currencyPair -> {
            log.info("SystemBillTotalServiceImpl build currencyPair: {}", currencyPair);
            if (!baseSet.add(currencyPair.getBase())) {
                log.info("SystemBillTotalServiceImpl buildSystemBillTotal base repeat: {}", currencyPair);
                return;
            }
            final SystemBillTotalExample systemBillTotalExample = new SystemBillTotalExample();
            systemBillTotalExample.createCriteria().andCurrencyCodeEqualTo(currencyPair.getBase());
            systemBillTotalExample.setOrderByClause("id desc");
            SystemBillTotal lastSystemBillTotal =
                    this.systemBillTotalRepository.selectOneByExample(systemBillTotalExample);
            if (lastSystemBillTotal == null) {
                lastSystemBillTotal = SystemBillTotal.builder().systemBillId(0L)
                        .currencyCode(currencyPair.getBase()).fee(BigDecimal.ZERO).profit(BigDecimal.ZERO)
                        .userBalance(BigDecimal.ZERO).totalFee(BigDecimal.ZERO).totalProfit(BigDecimal.ZERO)
                        .baseAdjust(BigDecimal.ZERO).createdDate(new Date()).build();
            }
            lastSystemBillTotal.setId(null);
            lastSystemBillTotal.setFee(BigDecimal.ZERO);
            lastSystemBillTotal.setProfit(BigDecimal.ZERO);
            lastSystemBillTotal.setBaseAdjust(BigDecimal.ZERO);
            // 汇总当前币种的用户总资产
            UserBalance sumUserBalance =
                    this.userBalanceRepository.selectSumBalanceByCurrencyCode(currencyPair.getBase());
            if (sumUserBalance == null) {
                sumUserBalance = new UserBalance();
            }
            if (sumUserBalance.getAvailableBalance() == null) {
                sumUserBalance.setAvailableBalance(BigDecimal.ZERO);
            }
            if (sumUserBalance.getPositionSize() == null) {
                sumUserBalance.setPositionSize(BigDecimal.ZERO);
            }
            // 按照最大的id 和 币种id 查询 system bill的数据
            final SystemBillExample systemBillExample = new SystemBillExample();
            systemBillExample.createCriteria().andIdGreaterThan(lastSystemBillTotal.getSystemBillId());
            systemBillExample.setOrderByClause("id asc");
            final List<SystemBill> systemBillList =
                    this.systemBillRepository.selectByExample(systemBillExample);

            final BigDecimal userBalance = ObjectUtils.defaultIfNull(lastSystemBillTotal.getUserBalance(), BigDecimal.ZERO);
            final BigDecimal positionSize = ObjectUtils.defaultIfNull(lastSystemBillTotal.getPositionSize(), BigDecimal.ZERO);

            // 数据没变化直接返回
            if (systemBillList.size() == 0
                    && sumUserBalance.getAvailableBalance().compareTo(userBalance) == 0
                    && sumUserBalance.getPositionSize().compareTo(positionSize) == 0) {
                log.info("SystemBillTotalServiceImpl buildSystemBillTotal no data change");
                return;
            }
            lastSystemBillTotal.setUserBalance(sumUserBalance.getAvailableBalance());
            lastSystemBillTotal.setPositionSize(sumUserBalance.getPositionSize());
            lastSystemBillTotal.setCreatedDate(new Date());
            for (final SystemBill systemBill : systemBillList) {
                if (!systemBill.getCurrencyCode().equals(currencyPair.getBase())
                        && !systemBill.getFeeCurrencyCode().equals(currencyPair.getBase())) {
                    continue;
                }
                if (systemBill.getCurrencyCode().equals(currencyPair.getBase())) {
                    lastSystemBillTotal
                            .setProfit(lastSystemBillTotal.getProfit().add(systemBill.getProfit()));
                }
                if (systemBill.getFeeCurrencyCode().equals(currencyPair.getBase())) {
                    lastSystemBillTotal.setFee(lastSystemBillTotal.getFee().add(systemBill.getFee()));
                }
                lastSystemBillTotal.setSystemBillId(systemBill.getId());
                lastSystemBillTotal.setCreatedDate(systemBill.getCreatedDate());
            }
            lastSystemBillTotal.setTotalProfit(
                    lastSystemBillTotal.getTotalProfit().add(lastSystemBillTotal.getProfit()));
            lastSystemBillTotal
                    .setTotalFee(lastSystemBillTotal.getTotalFee().add(lastSystemBillTotal.getFee()));
            this.systemBillTotalRepository.insert(lastSystemBillTotal);
            // 判断账是否平，是否需要发送短信
            this.warning(currencyPair, sumUserBalance, lastSystemBillTotal, currencyPair.getBase().toLowerCase());
        });
        log.info("SystemBillTotalServiceImpl buildSystemBillTotal end");
    }

    /**
     * 短信提醒账不平
     */
    private void warning(final CurrencyPair currencyPair, final UserBalance sumUserBalance, final SystemBillTotal systemBillTotal,
                         final String currencyCode) {
        // 用户资产和公司账单之间相差大于阈值发送预警短信
        final BigDecimal diff = sumUserBalance.getAvailableBalance().subtract(sumUserBalance.getPositionSize())
                .subtract(systemBillTotal.getTotalProfit().add(systemBillTotal.getTotalFee())).abs();
        JSONObject warning = this.commonPropService.getConfigObject(PerpetualConstants.DEFAULT_BROKERID,
                ConfigNameEnum.SYSTEM_BILL_TOTAL_WARNING_JSON.getName(), JSONObject.class);
        warning = warning == null ? new JSONObject() : warning;
        BigDecimal threshold = warning.getBigDecimal(currencyCode);
        if (threshold == null) {
            threshold = warning.getBigDecimal("other");
            if (threshold == null) {
                threshold = BigDecimal.ZERO;
            }
        }
        if (diff.compareTo(threshold) > 0) {
            int time = CACHE_MAP.getOrDefault(currencyCode, 0);
            log.warn("SystemBillTotalServiceImpl warning CACHE_MAP cache key: {}, time: {}", currencyCode,
                    time);
            time++;
            CACHE_MAP.put(currencyCode, time);
            final Integer warningTime =
                    this.commonPropService.getConfigObject(PerpetualConstants.DEFAULT_BROKERID,
                            ConfigNameEnum.BILL_WARNING_TIMES.getName(), Integer.class);
            // 默认3次为阈值
            if (time < (warningTime == null ? 3 : warningTime)) {
                return;
            }
            CACHE_MAP.remove(currencyCode);
            try {
                final Map<String, Object> map = new HashMap<>(16);
                map.put("dbBalance",
                        sumUserBalance.getAvailableBalance().setScale(PerpetualConstants.SCALE, BigDecimal.ROUND_HALF_UP)
                                + "-" + sumUserBalance.getPositionSize().setScale(PerpetualConstants.SCALE, BigDecimal.ROUND_HALF_UP));
                map.put("currency", currencyCode);
                map.put("type", currencyPair.getPairCode());
                map.put("billBalance", systemBillTotal.getTotalProfit().add(systemBillTotal.getTotalFee())
                        .setScale(PerpetualConstants.SCALE, BigDecimal.ROUND_HALF_UP));
                map.put("time", DateUtil.getFormatDate(System.currentTimeMillis()));
                // 多个手机号用逗号分隔
                String mobiles = this.commonPropService.getConfigObject(PerpetualConstants.DEFAULT_BROKERID,
                        ConfigNameEnum.BILL_WARNING_PHONES.getName(), String.class);
                mobiles = mobiles == null ? this.perpetualConfig.getSystemBillPhones() : mobiles;
                // 发送短信
                final MessageReqDTO messageReqDTO = MessageReqDTO.builder().countryCode("86")
                        .locale(Locale.SIMPLIFIED_CHINESE.toLanguageTag()).mobile(mobiles)
                        .templateCode("SMS_SYSTEM_USER_BILL_WARN1").brokerId(1)
                        .params(JSON.toJSONString(map)).build();
                final ResponseResult<String> smsSendResultApiResult =
                        this.messageClient.send(messageReqDTO);
                log.info("SystemBillTotalServiceImpl warning sendMsg code:{} msg:{} ",
                        smsSendResultApiResult.getCode(), smsSendResultApiResult.getMsg());
            } catch (final Exception e) {
                log.error("SystemBillTotalServiceImpl warning sendMsg error: ", e);
            }
        } else {
            final Integer time = CACHE_MAP.remove(currencyCode);
            if (time != null) {
                log.warn("SystemBillTotalServiceImpl warning remove cache key: {}, time: {}", currencyCode,
                        time);
            }
        }
    }

    /**
     * Save systemo bill total response result.
     *
     * @param target the target
     * @return the response result
     */
    @Override
    public ResponseResult<?> saveSystemBillTotal(final SystemBillTotal target) {
        final Long id = this.systemBillService.getMaxId();
        if (Objects.isNull(id)) {
            return ResultUtils.failure("max systemBill id is not exists");
        }
        target.setSystemBillId(id);
        return ResultUtils.success(this.add(target));
    }

    /**
     * List by condition list.
     *
     * @param currencyCode the currency code
     * @param start        the start
     * @param end          the end
     * @return the list
     */
    @Override
    public List<SystemBillTotalDTO> listByCondition(final String currencyCode, final Long start, final Long end) {

        if (StringUtils.isNotEmpty(currencyCode)) {
            final SystemBillTotalDTO buildResult = this.buildByCurrencyCodeAndStartAndEnd(currencyCode, start, end);
            return Lists.newArrayList(buildResult);
        }
        // 获取所有币对的交易货币Code
        final List<String> queryBaseCodeData =
                Optional.ofNullable(this.currencyPairService.getOnlineCurrencyPair())
                        .map(itemList -> itemList.stream()
                                .map(CurrencyPair::getBase).collect(Collectors.toList()))
                        .orElse(Collections.emptyList());

        return queryBaseCodeData.stream()
                .distinct()
                .map(itemCode -> this.buildByCurrencyCodeAndStartAndEnd(itemCode, start, end))
                .collect(Collectors.toList());
    }

    /**
     * 构建账单DTO
     *
     * @param currencyCode
     * @param start
     * @param end
     * @return
     */
    private SystemBillTotalDTO buildByCurrencyCodeAndStartAndEnd(final String currencyCode, final Long start, final Long end) {
        final SystemBillTotal endQueryData = this.getSystemBillTotal(currencyCode, end);

        final SystemBillTotalDTO result = ObjectCopyUtil.map(endQueryData, SystemBillTotalDTO.class);
        if (Objects.nonNull(start)) {
            final SystemBillTotal startQueryData = this.getSystemBillTotal(currencyCode, start);
            this.copyDiff(result, startQueryData);
        }
        return result;
    }

    /**
     * 复制不同的数值
     *
     * @param target
     * @param source
     */
    private void copyDiff(final SystemBillTotalDTO target, final SystemBillTotal source) {
        if (source != null) {
            target.setFee(target.getFee().subtract(source.getFee()));
            target.setProfit(target.getProfit().subtract(source.getProfit()));
            target.setUserBalance(target.getUserBalance().subtract(source.getUserBalance()));
            target.setTotalFee(target.getTotalFee().subtract(source.getTotalFee()));
            target.setTotalProfit(target.getTotalProfit().subtract(source.getTotalProfit()));
            target.setBaseAdjust(target.getBaseAdjust().subtract(source.getBaseAdjust()));
        }
    }
}
