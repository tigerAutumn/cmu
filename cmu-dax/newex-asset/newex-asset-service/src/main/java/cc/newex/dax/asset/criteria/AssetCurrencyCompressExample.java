package cc.newex.dax.asset.criteria;

import cc.newex.dax.asset.domain.AssetCurrency;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 币种表 查询条件类
 *
 * @author newex-team
 * @date 2018-09-17 17:20:24
 */
public class AssetCurrencyCompressExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public static AssetCurrencyCompressExample from(AssetCurrency currency) {
        AssetCurrencyCompressExample example = new AssetCurrencyCompressExample();
        if (ObjectUtils.isEmpty(currency)) {
            return example;
        }
        AssetCurrencyCompressExample.Criteria criteria = example.createCriteria();

        if (!ObjectUtils.isEmpty(currency.getId())) {
            criteria.andIdEqualTo(currency.getId());
        }
        if (!ObjectUtils.isEmpty(currency.getExchange())) {
            criteria.andExchangeEqualTo(currency.getExchange());
        }

        if (!ObjectUtils.isEmpty(currency.getOnline())) {
            criteria.andOnlineEqualTo(currency.getOnline());
        }
        if (!ObjectUtils.isEmpty(currency.getReceive())) {
            criteria.andReceiveEqualTo(currency.getReceive());
        }
        if (!ObjectUtils.isEmpty(currency.getRechargeable())) {
            criteria.andRechargeableEqualTo(currency.getRechargeable());
        }
        if (!ObjectUtils.isEmpty(currency.getSymbol())) {
            criteria.andSymbolEqualTo(currency.getSymbol());
        }
        if (!ObjectUtils.isEmpty(currency.getTransfer())) {
            criteria.andTransferEqualTo(currency.getTransfer());
        }
        if (!ObjectUtils.isEmpty(currency.getWithdrawable())) {
            criteria.andWithdrawableEqualTo(currency.getWithdrawable());
        }
        if (!ObjectUtils.isEmpty(currency.getBrokerId())) {
            criteria.andBrokerIdEqualTo(currency.getBrokerId());
        }
        return example;
    }

    public AssetCurrencyCompressExample() {
        this.oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public List<Criteria> getOredCriteria() {
        return this.oredCriteria;
    }

    public void or(Criteria criteria) {
        this.oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            this.criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return this.criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return this.criteria;
        }

        public List<Criterion> getCriteria() {
            return this.criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            this.addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            this.addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            this.addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSymbolIsNull() {
            this.addCriterion("symbol is null");
            return (Criteria) this;
        }

        public Criteria andSymbolIsNotNull() {
            this.addCriterion("symbol is not null");
            return (Criteria) this;
        }

        public Criteria andSymbolEqualTo(String value) {
            this.addCriterion("symbol =", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotEqualTo(String value) {
            this.addCriterion("symbol <>", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThan(String value) {
            this.addCriterion("symbol >", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThanOrEqualTo(String value) {
            this.addCriterion("symbol >=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThan(String value) {
            this.addCriterion("symbol <", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThanOrEqualTo(String value) {
            this.addCriterion("symbol <=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLike(String value) {
            this.addCriterion("symbol like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotLike(String value) {
            this.addCriterion("symbol not like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolIn(List<String> values) {
            this.addCriterion("symbol in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotIn(List<String> values) {
            this.addCriterion("symbol not in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolBetween(String value1, String value2) {
            this.addCriterion("symbol between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotBetween(String value1, String value2) {
            this.addCriterion("symbol not between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsIsNull() {
            this.addCriterion("extend_attrs is null");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsIsNotNull() {
            this.addCriterion("extend_attrs is not null");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsEqualTo(String value) {
            this.addCriterion("extend_attrs =", value, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsNotEqualTo(String value) {
            this.addCriterion("extend_attrs <>", value, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsGreaterThan(String value) {
            this.addCriterion("extend_attrs >", value, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsGreaterThanOrEqualTo(String value) {
            this.addCriterion("extend_attrs >=", value, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsLessThan(String value) {
            this.addCriterion("extend_attrs <", value, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsLessThanOrEqualTo(String value) {
            this.addCriterion("extend_attrs <=", value, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsLike(String value) {
            this.addCriterion("extend_attrs like", value, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsNotLike(String value) {
            this.addCriterion("extend_attrs not like", value, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsIn(List<String> values) {
            this.addCriterion("extend_attrs in", values, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsNotIn(List<String> values) {
            this.addCriterion("extend_attrs not in", values, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsBetween(String value1, String value2) {
            this.addCriterion("extend_attrs between", value1, value2, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andExtendAttrsNotBetween(String value1, String value2) {
            this.addCriterion("extend_attrs not between", value1, value2, "extendAttrs");
            return (Criteria) this;
        }

        public Criteria andWithdrawableIsNull() {
            this.addCriterion("withdrawable is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawableIsNotNull() {
            this.addCriterion("withdrawable is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawableEqualTo(Integer value) {
            this.addCriterion("withdrawable =", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableNotEqualTo(Integer value) {
            this.addCriterion("withdrawable <>", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableGreaterThan(Integer value) {
            this.addCriterion("withdrawable >", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("withdrawable >=", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableLessThan(Integer value) {
            this.addCriterion("withdrawable <", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableLessThanOrEqualTo(Integer value) {
            this.addCriterion("withdrawable <=", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableIn(List<Integer> values) {
            this.addCriterion("withdrawable in", values, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableNotIn(List<Integer> values) {
            this.addCriterion("withdrawable not in", values, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableBetween(Integer value1, Integer value2) {
            this.addCriterion("withdrawable between", value1, value2, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableNotBetween(Integer value1, Integer value2) {
            this.addCriterion("withdrawable not between", value1, value2, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andRechargeableIsNull() {
            this.addCriterion("rechargeable is null");
            return (Criteria) this;
        }

        public Criteria andRechargeableIsNotNull() {
            this.addCriterion("rechargeable is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeableEqualTo(Integer value) {
            this.addCriterion("rechargeable =", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableNotEqualTo(Integer value) {
            this.addCriterion("rechargeable <>", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableGreaterThan(Integer value) {
            this.addCriterion("rechargeable >", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("rechargeable >=", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableLessThan(Integer value) {
            this.addCriterion("rechargeable <", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableLessThanOrEqualTo(Integer value) {
            this.addCriterion("rechargeable <=", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableIn(List<Integer> values) {
            this.addCriterion("rechargeable in", values, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableNotIn(List<Integer> values) {
            this.addCriterion("rechargeable not in", values, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableBetween(Integer value1, Integer value2) {
            this.addCriterion("rechargeable between", value1, value2, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableNotBetween(Integer value1, Integer value2) {
            this.addCriterion("rechargeable not between", value1, value2, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            this.addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            this.addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            this.addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            this.addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            this.addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            this.addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            this.addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            this.addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            this.addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            this.addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            this.addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andOnlineIsNull() {
            this.addCriterion("online is null");
            return (Criteria) this;
        }

        public Criteria andOnlineIsNotNull() {
            this.addCriterion("online is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineEqualTo(Integer value) {
            this.addCriterion("online =", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotEqualTo(Integer value) {
            this.addCriterion("online <>", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineGreaterThan(Integer value) {
            this.addCriterion("online >", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("online >=", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineLessThan(Integer value) {
            this.addCriterion("online <", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineLessThanOrEqualTo(Integer value) {
            this.addCriterion("online <=", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineIn(List<Integer> values) {
            this.addCriterion("online in", values, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotIn(List<Integer> values) {
            this.addCriterion("online not in", values, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineBetween(Integer value1, Integer value2) {
            this.addCriterion("online between", value1, value2, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotBetween(Integer value1, Integer value2) {
            this.addCriterion("online not between", value1, value2, "online");
            return (Criteria) this;
        }

        public Criteria andExchangeIsNull() {
            this.addCriterion("exchange is null");
            return (Criteria) this;
        }

        public Criteria andExchangeIsNotNull() {
            this.addCriterion("exchange is not null");
            return (Criteria) this;
        }

        public Criteria andExchangeEqualTo(Integer value) {
            this.addCriterion("exchange =", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeNotEqualTo(Integer value) {
            this.addCriterion("exchange <>", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeGreaterThan(Integer value) {
            this.addCriterion("exchange >", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("exchange >=", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeLessThan(Integer value) {
            this.addCriterion("exchange <", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeLessThanOrEqualTo(Integer value) {
            this.addCriterion("exchange <=", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeIn(List<Integer> values) {
            this.addCriterion("exchange in", values, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeNotIn(List<Integer> values) {
            this.addCriterion("exchange not in", values, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeBetween(Integer value1, Integer value2) {
            this.addCriterion("exchange between", value1, value2, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeNotBetween(Integer value1, Integer value2) {
            this.addCriterion("exchange not between", value1, value2, "exchange");
            return (Criteria) this;
        }

        public Criteria andReceiveIsNull() {
            this.addCriterion("receive is null");
            return (Criteria) this;
        }

        public Criteria andReceiveIsNotNull() {
            this.addCriterion("receive is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveEqualTo(Integer value) {
            this.addCriterion("receive =", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotEqualTo(Integer value) {
            this.addCriterion("receive <>", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveGreaterThan(Integer value) {
            this.addCriterion("receive >", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("receive >=", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveLessThan(Integer value) {
            this.addCriterion("receive <", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveLessThanOrEqualTo(Integer value) {
            this.addCriterion("receive <=", value, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveIn(List<Integer> values) {
            this.addCriterion("receive in", values, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotIn(List<Integer> values) {
            this.addCriterion("receive not in", values, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveBetween(Integer value1, Integer value2) {
            this.addCriterion("receive between", value1, value2, "receive");
            return (Criteria) this;
        }

        public Criteria andReceiveNotBetween(Integer value1, Integer value2) {
            this.addCriterion("receive not between", value1, value2, "receive");
            return (Criteria) this;
        }

        public Criteria andTransferIsNull() {
            this.addCriterion("transfer is null");
            return (Criteria) this;
        }

        public Criteria andTransferIsNotNull() {
            this.addCriterion("transfer is not null");
            return (Criteria) this;
        }

        public Criteria andTransferEqualTo(Integer value) {
            this.addCriterion("transfer =", value, "transfer");
            return (Criteria) this;
        }

        public Criteria andTransferNotEqualTo(Integer value) {
            this.addCriterion("transfer <>", value, "transfer");
            return (Criteria) this;
        }

        public Criteria andTransferGreaterThan(Integer value) {
            this.addCriterion("transfer >", value, "transfer");
            return (Criteria) this;
        }

        public Criteria andTransferGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("transfer >=", value, "transfer");
            return (Criteria) this;
        }

        public Criteria andTransferLessThan(Integer value) {
            this.addCriterion("transfer <", value, "transfer");
            return (Criteria) this;
        }

        public Criteria andTransferLessThanOrEqualTo(Integer value) {
            this.addCriterion("transfer <=", value, "transfer");
            return (Criteria) this;
        }

        public Criteria andTransferIn(List<Integer> values) {
            this.addCriterion("transfer in", values, "transfer");
            return (Criteria) this;
        }

        public Criteria andTransferNotIn(List<Integer> values) {
            this.addCriterion("transfer not in", values, "transfer");
            return (Criteria) this;
        }

        public Criteria andTransferBetween(Integer value1, Integer value2) {
            this.addCriterion("transfer between", value1, value2, "transfer");
            return (Criteria) this;
        }

        public Criteria andTransferNotBetween(Integer value1, Integer value2) {
            this.addCriterion("transfer not between", value1, value2, "transfer");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeIsNull() {
            this.addCriterion("withdraw_fee is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeIsNotNull() {
            this.addCriterion("withdraw_fee is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeEqualTo(BigDecimal value) {
            this.addCriterion("withdraw_fee =", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeNotEqualTo(BigDecimal value) {
            this.addCriterion("withdraw_fee <>", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeGreaterThan(BigDecimal value) {
            this.addCriterion("withdraw_fee >", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("withdraw_fee >=", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeLessThan(BigDecimal value) {
            this.addCriterion("withdraw_fee <", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("withdraw_fee <=", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeIn(List<BigDecimal> values) {
            this.addCriterion("withdraw_fee in", values, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeNotIn(List<BigDecimal> values) {
            this.addCriterion("withdraw_fee not in", values, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("withdraw_fee between", value1, value2, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("withdraw_fee not between", value1, value2, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmIsNull() {
            this.addCriterion("deposit_confirm is null");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmIsNotNull() {
            this.addCriterion("deposit_confirm is not null");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmEqualTo(Integer value) {
            this.addCriterion("deposit_confirm =", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmNotEqualTo(Integer value) {
            this.addCriterion("deposit_confirm <>", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmGreaterThan(Integer value) {
            this.addCriterion("deposit_confirm >", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("deposit_confirm >=", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmLessThan(Integer value) {
            this.addCriterion("deposit_confirm <", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmLessThanOrEqualTo(Integer value) {
            this.addCriterion("deposit_confirm <=", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmIn(List<Integer> values) {
            this.addCriterion("deposit_confirm in", values, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmNotIn(List<Integer> values) {
            this.addCriterion("deposit_confirm not in", values, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmBetween(Integer value1, Integer value2) {
            this.addCriterion("deposit_confirm between", value1, value2, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmNotBetween(Integer value1, Integer value2) {
            this.addCriterion("deposit_confirm not between", value1, value2, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmIsNull() {
            this.addCriterion("withdraw_confirm is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmIsNotNull() {
            this.addCriterion("withdraw_confirm is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmEqualTo(Integer value) {
            this.addCriterion("withdraw_confirm =", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmNotEqualTo(Integer value) {
            this.addCriterion("withdraw_confirm <>", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmGreaterThan(Integer value) {
            this.addCriterion("withdraw_confirm >", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("withdraw_confirm >=", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmLessThan(Integer value) {
            this.addCriterion("withdraw_confirm <", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmLessThanOrEqualTo(Integer value) {
            this.addCriterion("withdraw_confirm <=", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmIn(List<Integer> values) {
            this.addCriterion("withdraw_confirm in", values, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmNotIn(List<Integer> values) {
            this.addCriterion("withdraw_confirm not in", values, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmBetween(Integer value1, Integer value2) {
            this.addCriterion("withdraw_confirm between", value1, value2, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmNotBetween(Integer value1, Integer value2) {
            this.addCriterion("withdraw_confirm not between", value1, value2, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountIsNull() {
            this.addCriterion("min_deposit_amount is null");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountIsNotNull() {
            this.addCriterion("min_deposit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountEqualTo(BigDecimal value) {
            this.addCriterion("min_deposit_amount =", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountNotEqualTo(BigDecimal value) {
            this.addCriterion("min_deposit_amount <>", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountGreaterThan(BigDecimal value) {
            this.addCriterion("min_deposit_amount >", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("min_deposit_amount >=", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountLessThan(BigDecimal value) {
            this.addCriterion("min_deposit_amount <", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("min_deposit_amount <=", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountIn(List<BigDecimal> values) {
            this.addCriterion("min_deposit_amount in", values, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountNotIn(List<BigDecimal> values) {
            this.addCriterion("min_deposit_amount not in", values, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("min_deposit_amount between", value1, value2, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("min_deposit_amount not between", value1, value2, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountIsNull() {
            this.addCriterion("min_withdraw_amount is null");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountIsNotNull() {
            this.addCriterion("min_withdraw_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountEqualTo(BigDecimal value) {
            this.addCriterion("min_withdraw_amount =", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountNotEqualTo(BigDecimal value) {
            this.addCriterion("min_withdraw_amount <>", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountGreaterThan(BigDecimal value) {
            this.addCriterion("min_withdraw_amount >", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("min_withdraw_amount >=", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountLessThan(BigDecimal value) {
            this.addCriterion("min_withdraw_amount <", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("min_withdraw_amount <=", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountIn(List<BigDecimal> values) {
            this.addCriterion("min_withdraw_amount in", values, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountNotIn(List<BigDecimal> values) {
            this.addCriterion("min_withdraw_amount not in", values, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("min_withdraw_amount between", value1, value2, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("min_withdraw_amount not between", value1, value2, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountIsNull() {
            this.addCriterion("max_withdraw_amount is null");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountIsNotNull() {
            this.addCriterion("max_withdraw_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountEqualTo(BigDecimal value) {
            this.addCriterion("max_withdraw_amount =", value, "maxWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountNotEqualTo(BigDecimal value) {
            this.addCriterion("max_withdraw_amount <>", value, "maxWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountGreaterThan(BigDecimal value) {
            this.addCriterion("max_withdraw_amount >", value, "maxWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("max_withdraw_amount >=", value, "maxWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountLessThan(BigDecimal value) {
            this.addCriterion("max_withdraw_amount <", value, "maxWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("max_withdraw_amount <=", value, "maxWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountIn(List<BigDecimal> values) {
            this.addCriterion("max_withdraw_amount in", values, "maxWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountNotIn(List<BigDecimal> values) {
            this.addCriterion("max_withdraw_amount not in", values, "maxWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("max_withdraw_amount between", value1, value2, "maxWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMaxWithdrawAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("max_withdraw_amount not between", value1, value2, "maxWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            this.addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            this.addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            this.addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            this.addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            this.addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            this.addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            this.addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            this.addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            this.addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            this.addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            this.addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            this.addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNull() {
            this.addCriterion("broker_id is null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNotNull() {
            this.addCriterion("broker_id is not null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdEqualTo(Integer value) {
            this.addCriterion("broker_id =", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotEqualTo(Integer value) {
            this.addCriterion("broker_id <>", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThan(Integer value) {
            this.addCriterion("broker_id >", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("broker_id >=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThan(Integer value) {
            this.addCriterion("broker_id <", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThanOrEqualTo(Integer value) {
            this.addCriterion("broker_id <=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIn(List<Integer> values) {
            this.addCriterion("broker_id in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotIn(List<Integer> values) {
            this.addCriterion("broker_id not in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdBetween(Integer value1, Integer value2) {
            this.addCriterion("broker_id between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotBetween(Integer value1, Integer value2) {
            this.addCriterion("broker_id not between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andFieldIsNull(String fieldName) {
            this.addCriterion(fieldName + " is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull(String fieldName) {
            this.addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " =", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " <>", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " >", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " >=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " <", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " <=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLike(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " not like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldIn(String fieldName, List<Object> fieldValues) {
            this.addCriterion(fieldName + " in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(String fieldName, List<Object> fieldValues) {
            this.addCriterion(fieldName + " not in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldBetween(String fieldName, Object fieldValue1, Object fieldValue2) {
            this.addCriterion(fieldName + " between", fieldValue1, fieldValue2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(String fieldName, Object fieldValue1, Object fieldValue2) {
            this.addCriterion(fieldName + " not between", fieldValue1, fieldValue2, "fieldName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return this.condition;
        }

        public Object getValue() {
            return this.value;
        }

        public Object getSecondValue() {
            return this.secondValue;
        }

        public boolean isNoValue() {
            return this.noValue;
        }

        public boolean isSingleValue() {
            return this.singleValue;
        }

        public boolean isBetweenValue() {
            return this.betweenValue;
        }

        public boolean isListValue() {
            return this.listValue;
        }

        public String getTypeHandler() {
            return this.typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}