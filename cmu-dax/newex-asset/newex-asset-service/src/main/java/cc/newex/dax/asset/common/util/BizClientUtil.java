package cc.newex.dax.asset.common.util;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.dto.BizCurrencyBalance;
import cc.newex.dax.c2c.client.C2CSystemBillTotalAdminClient;
import cc.newex.dax.c2c.client.C2CTransferClient;
import cc.newex.dax.c2c.client.inner.C2CCurrencyClient;
import cc.newex.dax.perpetual.client.inner.SystemBillTotalInnerClient;
import cc.newex.dax.perpetual.client.inner.TransferInnerClient;
import cc.newex.dax.portfolio.client.admin.AdminSystemBillTotalClient;
import cc.newex.dax.portfolio.client.inner.AssetsTransferClient;
import cc.newex.dax.spot.client.SpotSystemBillTotalClient;
import cc.newex.dax.spot.client.inner.SpotCurrencyClient;
import cc.newex.dax.spot.client.inner.SpotTransferServiceClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cc.newex.commons.dictionary.enums.TransferType.*;

/**
 * @author newex-team
 * @data 06/04/2018
 */
@Component
@Slf4j
public class BizClientUtil {

    private static SpotTransferServiceClient SPOT_TRANSFER_CLIENT;
    private static C2CTransferClient C2C_TRANSFER_CLIENT;
    private static SpotCurrencyClient SPOT_CURRENCY_CLIENT;
    private static C2CCurrencyClient C2C_CURRENCY_CLIENT;
    private static TransferInnerClient PERPETUAL_TRANSFER_CLIENT;
    private static SpotSystemBillTotalClient SPOT_BILL_TOTAL_CLIENT;

    private static AdminSystemBillTotalClient PORT_BILL_TOTAL_CLIENT;
    private static C2CSystemBillTotalAdminClient C2C_BILL_TOTAL_CLIENT;
    private static AssetsTransferClient FOLIO_TRANSFER_CLIENT;
    private static SystemBillTotalInnerClient PERPETUAL_BILL_TOTAL_CLIENT;

    @Autowired
    private SpotTransferServiceClient spotTransferClient;

    @Autowired
    private AssetsTransferClient portFolioTransferClient;
    @Autowired
    private C2CTransferClient c2cTransferClient;
    @Autowired
    private TransferInnerClient perpetualTransferClient;
    @Autowired
    private SpotCurrencyClient spotCurrencyClient;

    @Autowired
    private C2CCurrencyClient c2cCurrencyClient;
    @Autowired
    private SpotSystemBillTotalClient spotSystemBillTotalClient;

    @Autowired
    private AdminSystemBillTotalClient portBillTotalClient;

    @Autowired
    private C2CSystemBillTotalAdminClient c2cSystemBillTotalAdminClient;

    @Autowired
    private SystemBillTotalInnerClient perpetualSystemBillTotalAdminClient;

    public static ResponseResult transferIn(final int biz, final Long userId, final CurrencyEnum currency, final BigDecimal amount, final String tradeNo, Integer brokerId) {

        return transferIn(biz, userId, currency, amount, tradeNo, TRANSFER_IN.getCode(), brokerId);
    }

    public static ResponseResult transferIn(final int biz, final Long userId, final CurrencyEnum currency, final BigDecimal amount, final String tradeNo, int transferType,
                                            Integer brokerId) {
        log.error("find unlocked npe biz={} , userId={} ,currency={},amount={},tradeNo={},transferType={},brokerId={}",
                biz, userId, currency, amount, tradeNo, transferType, brokerId);
        final Object client = BizClientUtil.getTransferClient(biz);
        Object[] args = new Object[]{userId, currency.getName(), brokerId, amount, tradeNo, transferType};
        Class[] classes = new Class[]{long.class, String.class, Integer.class, BigDecimal.class, String.class, Integer.class};
        final Method method = ReflectionUtils.findMethod(client.getClass(), "transferIn", classes);
        return (ResponseResult) ReflectionUtils.invokeMethod(method, client, args);
    }

    public static ResponseResult transferOut(final int biz, final long userId, final CurrencyEnum currency, final BigDecimal amount, final String tradeNo,
                                             Integer brokerId) {// TRANSFER_OUT.getCode()
        return transferOut(biz, userId, currency, amount, tradeNo, TRANSFER_OUT.getCode(), brokerId);
    }

    public static ResponseResult transferOut(final int biz, final long userId, final CurrencyEnum currency, final BigDecimal amount, final String tradeNo, int transferType,
                                             Integer brokerId) {
        final Object client = BizClientUtil.getTransferClient(biz);
        log.error("biz={},userId={},currency={},amount={},tradeNo={},transferType={},brokerId={}",
                biz, userId, currency.getName(), amount, tradeNo, transferType, brokerId);
        Object[] args = new Object[]{userId, currency.getName(), brokerId, amount, tradeNo, transferType};
        Class[] classes = new Class[]{long.class, String.class, Integer.class, BigDecimal.class, String.class, Integer.class};
        final Method method = ReflectionUtils.findMethod(client.getClass(), "transferOut", classes);
        return (ResponseResult) ReflectionUtils.invokeMethod(method, client, args);
    }


    public static ResponseResult deposit(final int biz, final Long userId, final CurrencyEnum currency, final BigDecimal amount, final String tradeNo, Integer brokerId) {

        if(amount.compareTo(BigDecimal.ZERO) > 0){
            return transferIn(biz, userId, currency, amount, tradeNo, DEPOSIT.getCode(), brokerId);
        }else{
            return ResultUtils.success();
        }
    }

    public static ResponseResult addToken(final int biz, final Long userId, final CurrencyEnum currency, final BigDecimal amount, final String tradeNo, Integer brokerId) {

        return transferIn(biz, userId, currency, amount, tradeNo, ADD_TOKEN.getCode(), brokerId);

    }

    public static ResponseResult withdraw(final int biz, final long userId, final CurrencyEnum currency, final BigDecimal amount, final String tradeNo, Integer brokerId) {

        return transferOut(biz, userId, currency, amount, tradeNo, WITHDRAW.getCode(), brokerId);

    }

    public static ResponseResult lockedPosition(final int biz, final long userId, final CurrencyEnum currency, final BigDecimal amount, final String tradeNo, Integer brokerId) {

        return transferOut(biz, userId, currency, amount, tradeNo, LOCKED_POSITION.getCode(), brokerId);

    }

    public static ResponseResult unlockedPosition(final int biz, final long userId, final CurrencyEnum currency, final BigDecimal amount, final String tradeNo, Integer brokerId) {

        return transferIn(biz, userId, currency, amount, tradeNo, UNLOCKED_POSITION.getCode(), brokerId);
    }

    @Retryable(value = {FeignException.class},
            maxAttempts = 2,
            backoff = @Backoff(value = 1000))
    public static ResponseResult payToken(boolean isAddOfficial, final int biz, final long userId, final CurrencyEnum currency, final BigDecimal amount, final String tradeNo,
                                          Integer brokerId) {
        if (isAddOfficial) {
            return transferIn(biz, userId, currency, amount, tradeNo, TRANSFER_IN.getCode(), brokerId);
        }
        return transferOut(biz, userId, currency, amount, tradeNo, TRANSFER_OUT.getCode(), brokerId);
    }

    public static ResponseResult addCurrencies(final int biz, final String currencies) {
        final Object client = BizClientUtil.getCurrencyClient(biz);
        if (ObjectUtils.isEmpty(client)) {
            log.error("addCurrencies can not find biz :{}", biz);
            return ResultUtils.failure("can not find biz :" + biz);
        }
        final Object[] args = new Object[]{currencies};
        final Class[] classes = new Class[]{String.class};
        final Method method = ReflectionUtils.findMethod(client.getClass(), "addCurrencies", classes);
        final ResponseResult result = (ResponseResult) ReflectionUtils.invokeMethod(method, client, args);
        return result;
    }

    public static Map<CurrencyEnum, BizCurrencyBalance> querySystemBillTotal(final String biz) {
        final BizEnum bizEnum = BizEnum.parseBiz(biz);
        final Object client = BizClientUtil.getBillClient(bizEnum.getIndex());
        final Object[] args = new Object[]{null, null, null};
        final Class[] classes = new Class[]{String.class, Long.class, Long.class};
        final Method method = ReflectionUtils.findMethod(client.getClass(), "querySystemBillTotal", classes);
        final ResponseResult<List<?>> result = (ResponseResult) ReflectionUtils.invokeMethod(method, client, args);
        final Map<CurrencyEnum, BizCurrencyBalance> currencyBalanceMap =
                result.getData().stream().map((bill) -> {
                    BizCurrencyBalance currencyBalance = new BizCurrencyBalance();
                    BeanUtils.copyProperties(bill, currencyBalance);
                    currencyBalance.setBiz(bizEnum.getIndex());
                    return currencyBalance;
                }).collect(Collectors.toMap(balance -> CurrencyEnum.parseValue(balance.getCurrency()), BizCurrencyBalance::self));

        return currencyBalanceMap;
    }


    private static Object getTransferClient(final int biz) {
        if (biz == BizEnum.SPOT.getIndex()) {
            return BizClientUtil.SPOT_TRANSFER_CLIENT;
        } else if (biz == BizEnum.C2C.getIndex()) {
            return BizClientUtil.C2C_TRANSFER_CLIENT;
        } else if (biz == BizEnum.PORTFOLIO.getIndex()) {
            return BizClientUtil.FOLIO_TRANSFER_CLIENT;
        } else if (biz == BizEnum.PERPETUAL.getIndex()) {
            return BizClientUtil.PERPETUAL_TRANSFER_CLIENT;
        }
        return null;
    }

    private static Object getCurrencyClient(final int biz) {
        if (biz == BizEnum.SPOT.getIndex()) {
            return BizClientUtil.SPOT_CURRENCY_CLIENT;
        } else if (biz == BizEnum.C2C.getIndex()) {
            return BizClientUtil.C2C_CURRENCY_CLIENT;
        }
        return null;
    }

    private static Object getBillClient(final int biz) {
        if (biz == BizEnum.SPOT.getIndex()) {
            return BizClientUtil.SPOT_BILL_TOTAL_CLIENT;
        } else if (biz == BizEnum.C2C.getIndex()) {
            return BizClientUtil.C2C_BILL_TOTAL_CLIENT;
        } else if (biz == BizEnum.PORTFOLIO.getIndex()) {
            return BizClientUtil.PORT_BILL_TOTAL_CLIENT;
        } else if (biz == BizEnum.PERPETUAL.getIndex()) {
            return BizClientUtil.PERPETUAL_BILL_TOTAL_CLIENT;
        }
        return null;
    }


    @PostConstruct
    public void init() {
        BizClientUtil.SPOT_TRANSFER_CLIENT = this.spotTransferClient;
        BizClientUtil.SPOT_CURRENCY_CLIENT = this.spotCurrencyClient;
        BizClientUtil.C2C_CURRENCY_CLIENT = this.c2cCurrencyClient;
        BizClientUtil.C2C_TRANSFER_CLIENT = this.c2cTransferClient;
        BizClientUtil.FOLIO_TRANSFER_CLIENT = this.portFolioTransferClient;
        BizClientUtil.PERPETUAL_TRANSFER_CLIENT = this.perpetualTransferClient;
        BizClientUtil.C2C_BILL_TOTAL_CLIENT = this.c2cSystemBillTotalAdminClient;
        BizClientUtil.SPOT_BILL_TOTAL_CLIENT = this.spotSystemBillTotalClient;
        BizClientUtil.PORT_BILL_TOTAL_CLIENT = this.portBillTotalClient;
        BizClientUtil.PERPETUAL_BILL_TOTAL_CLIENT = perpetualSystemBillTotalAdminClient;
    }


}
