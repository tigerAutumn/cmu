package cc.newex.dax.asset.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 币种表 查询条件example类
 * @author newex-team
 * @date 2018-04-17
 */
public class CurrencyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CurrencyExample() {
        this.oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(final String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setDistinct(final boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public List<Criteria> getOredCriteria() {
        return this.oredCriteria;
    }

    public void or(final Criteria criteria) {
        this.oredCriteria.add(criteria);
    }

    public Criteria or() {
        final Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        final Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        final Criteria criteria = new Criteria();
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

        protected void addCriterion(final String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.criteria.add(new Criterion(condition));
        }

        protected void addCriterion(final String condition, final Object value, final String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(final String condition, final Object value1, final Object value2, final String property) {
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

        public Criteria andIdEqualTo(final Integer value) {
            this.addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(final List<Integer> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(final List<Integer> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
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

        public Criteria andSymbolEqualTo(final String value) {
            this.addCriterion("symbol =", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotEqualTo(final String value) {
            this.addCriterion("symbol <>", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolIn(final List<String> values) {
            this.addCriterion("symbol in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotIn(final List<String> values) {
            this.addCriterion("symbol not in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolBetween(final String value1, final String value2) {
            this.addCriterion("symbol between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotBetween(final String value1, final String value2) {
            this.addCriterion("symbol not between", value1, value2, "symbol");
            return (Criteria) this;
        }


        public Criteria andSymbolLike(final String value) {
            this.addCriterion("symbol like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotLike(final String value) {
            this.addCriterion("symbol not like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSignIsNull() {
            this.addCriterion("sign is null");
            return (Criteria) this;
        }

        public Criteria andSignIsNotNull() {
            this.addCriterion("sign is not null");
            return (Criteria) this;
        }

        public Criteria andSignEqualTo(final String value) {
            this.addCriterion("sign =", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotEqualTo(final String value) {
            this.addCriterion("sign <>", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignIn(final List<String> values) {
            this.addCriterion("sign in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotIn(final List<String> values) {
            this.addCriterion("sign not in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignBetween(final String value1, final String value2) {
            this.addCriterion("sign between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotBetween(final String value1, final String value2) {
            this.addCriterion("sign not between", value1, value2, "sign");
            return (Criteria) this;
        }


        public Criteria andSignLike(final String value) {
            this.addCriterion("sign like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotLike(final String value) {
            this.addCriterion("sign not like", value, "sign");
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

        public Criteria andWithdrawableEqualTo(final Byte value) {
            this.addCriterion("withdrawable =", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableNotEqualTo(final Byte value) {
            this.addCriterion("withdrawable <>", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableIn(final List<Byte> values) {
            this.addCriterion("withdrawable in", values, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableNotIn(final List<Byte> values) {
            this.addCriterion("withdrawable not in", values, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableBetween(final Byte value1, final Byte value2) {
            this.addCriterion("withdrawable between", value1, value2, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("withdrawable not between", value1, value2, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableGreaterThan(final Byte value) {
            this.addCriterion("withdrawable >", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("withdrawable >=", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableLessThan(final Byte value) {
            this.addCriterion("withdrawable <", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableLessThanOrEqualTo(final Byte value) {
            this.addCriterion("withdrawable <=", value, "withdrawable");
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

        public Criteria andRechargeableEqualTo(final Byte value) {
            this.addCriterion("rechargeable =", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableNotEqualTo(final Byte value) {
            this.addCriterion("rechargeable <>", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableIn(final List<Byte> values) {
            this.addCriterion("rechargeable in", values, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableNotIn(final List<Byte> values) {
            this.addCriterion("rechargeable not in", values, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableBetween(final Byte value1, final Byte value2) {
            this.addCriterion("rechargeable between", value1, value2, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("rechargeable not between", value1, value2, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableGreaterThan(final Byte value) {
            this.addCriterion("rechargeable >", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("rechargeable >=", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableLessThan(final Byte value) {
            this.addCriterion("rechargeable <", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableLessThanOrEqualTo(final Byte value) {
            this.addCriterion("rechargeable <=", value, "rechargeable");
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

        public Criteria andSortEqualTo(final Integer value) {
            this.addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(final Integer value) {
            this.addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(final List<Integer> values) {
            this.addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(final List<Integer> values) {
            this.addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(final Integer value1, final Integer value2) {
            this.addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(final Integer value) {
            this.addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(final Integer value) {
            this.addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(final Integer value) {
            this.addCriterion("sort <=", value, "sort");
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

        public Criteria andOnlineEqualTo(final Byte value) {
            this.addCriterion("online =", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotEqualTo(final Byte value) {
            this.addCriterion("online <>", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineIn(final List<Byte> values) {
            this.addCriterion("online in", values, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotIn(final List<Byte> values) {
            this.addCriterion("online not in", values, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineBetween(final Byte value1, final Byte value2) {
            this.addCriterion("online between", value1, value2, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("online not between", value1, value2, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineGreaterThan(final Byte value) {
            this.addCriterion("online >", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("online >=", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineLessThan(final Byte value) {
            this.addCriterion("online <", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineLessThanOrEqualTo(final Byte value) {
            this.addCriterion("online <=", value, "online");
            return (Criteria) this;
        }

        public Criteria andCanExchangeIsNull() {
            this.addCriterion("can_exchange is null");
            return (Criteria) this;
        }

        public Criteria andCanExchangeIsNotNull() {
            this.addCriterion("can_exchange is not null");
            return (Criteria) this;
        }

        public Criteria andCanExchangeEqualTo(final Byte value) {
            this.addCriterion("can_exchange =", value, "canExchange");
            return (Criteria) this;
        }

        public Criteria andCanExchangeNotEqualTo(final Byte value) {
            this.addCriterion("can_exchange <>", value, "canExchange");
            return (Criteria) this;
        }

        public Criteria andCanExchangeIn(final List<Byte> values) {
            this.addCriterion("can_exchange in", values, "canExchange");
            return (Criteria) this;
        }

        public Criteria andCanExchangeNotIn(final List<Byte> values) {
            this.addCriterion("can_exchange not in", values, "canExchange");
            return (Criteria) this;
        }

        public Criteria andCanExchangeBetween(final Byte value1, final Byte value2) {
            this.addCriterion("can_exchange between", value1, value2, "canExchange");
            return (Criteria) this;
        }

        public Criteria andCanExchangeNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("can_exchange not between", value1, value2, "canExchange");
            return (Criteria) this;
        }

        public Criteria andCanExchangeGreaterThan(final Byte value) {
            this.addCriterion("can_exchange >", value, "canExchange");
            return (Criteria) this;
        }

        public Criteria andCanExchangeGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("can_exchange >=", value, "canExchange");
            return (Criteria) this;
        }

        public Criteria andCanExchangeLessThan(final Byte value) {
            this.addCriterion("can_exchange <", value, "canExchange");
            return (Criteria) this;
        }

        public Criteria andCanExchangeLessThanOrEqualTo(final Byte value) {
            this.addCriterion("can_exchange <=", value, "canExchange");
            return (Criteria) this;
        }

        public Criteria andCanReceiveIsNull() {
            this.addCriterion("can_receive is null");
            return (Criteria) this;
        }

        public Criteria andCanReceiveIsNotNull() {
            this.addCriterion("can_receive is not null");
            return (Criteria) this;
        }

        public Criteria andCanReceiveEqualTo(final Byte value) {
            this.addCriterion("can_receive =", value, "canReceive");
            return (Criteria) this;
        }

        public Criteria andCanReceiveNotEqualTo(final Byte value) {
            this.addCriterion("can_receive <>", value, "canReceive");
            return (Criteria) this;
        }

        public Criteria andCanReceiveIn(final List<Byte> values) {
            this.addCriterion("can_receive in", values, "canReceive");
            return (Criteria) this;
        }

        public Criteria andCanReceiveNotIn(final List<Byte> values) {
            this.addCriterion("can_receive not in", values, "canReceive");
            return (Criteria) this;
        }

        public Criteria andCanReceiveBetween(final Byte value1, final Byte value2) {
            this.addCriterion("can_receive between", value1, value2, "canReceive");
            return (Criteria) this;
        }

        public Criteria andCanReceiveNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("can_receive not between", value1, value2, "canReceive");
            return (Criteria) this;
        }

        public Criteria andCanReceiveGreaterThan(final Byte value) {
            this.addCriterion("can_receive >", value, "canReceive");
            return (Criteria) this;
        }

        public Criteria andCanReceiveGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("can_receive >=", value, "canReceive");
            return (Criteria) this;
        }

        public Criteria andCanReceiveLessThan(final Byte value) {
            this.addCriterion("can_receive <", value, "canReceive");
            return (Criteria) this;
        }

        public Criteria andCanReceiveLessThanOrEqualTo(final Byte value) {
            this.addCriterion("can_receive <=", value, "canReceive");
            return (Criteria) this;
        }

        public Criteria andCanTransferIsNull() {
            this.addCriterion("can_transfer is null");
            return (Criteria) this;
        }

        public Criteria andCanTransferIsNotNull() {
            this.addCriterion("can_transfer is not null");
            return (Criteria) this;
        }

        public Criteria andCanTransferEqualTo(final Byte value) {
            this.addCriterion("can_transfer =", value, "canTransfer");
            return (Criteria) this;
        }

        public Criteria andCanTransferNotEqualTo(final Byte value) {
            this.addCriterion("can_transfer <>", value, "canTransfer");
            return (Criteria) this;
        }

        public Criteria andCanTransferIn(final List<Byte> values) {
            this.addCriterion("can_transfer in", values, "canTransfer");
            return (Criteria) this;
        }

        public Criteria andCanTransferNotIn(final List<Byte> values) {
            this.addCriterion("can_transfer not in", values, "canTransfer");
            return (Criteria) this;
        }

        public Criteria andCanTransferBetween(final Byte value1, final Byte value2) {
            this.addCriterion("can_transfer between", value1, value2, "canTransfer");
            return (Criteria) this;
        }

        public Criteria andCanTransferNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("can_transfer not between", value1, value2, "canTransfer");
            return (Criteria) this;
        }

        public Criteria andCanTransferGreaterThan(final Byte value) {
            this.addCriterion("can_transfer >", value, "canTransfer");
            return (Criteria) this;
        }

        public Criteria andCanTransferGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("can_transfer >=", value, "canTransfer");
            return (Criteria) this;
        }

        public Criteria andCanTransferLessThan(final Byte value) {
            this.addCriterion("can_transfer <", value, "canTransfer");
            return (Criteria) this;
        }

        public Criteria andCanTransferLessThanOrEqualTo(final Byte value) {
            this.addCriterion("can_transfer <=", value, "canTransfer");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateIsNull() {
            this.addCriterion("margin_interest_rate is null");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateIsNotNull() {
            this.addCriterion("margin_interest_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateEqualTo(final Double value) {
            this.addCriterion("margin_interest_rate =", value, "marginInterestRate");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateNotEqualTo(final Double value) {
            this.addCriterion("margin_interest_rate <>", value, "marginInterestRate");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateIn(final List<Double> values) {
            this.addCriterion("margin_interest_rate in", values, "marginInterestRate");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateNotIn(final List<Double> values) {
            this.addCriterion("margin_interest_rate not in", values, "marginInterestRate");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateBetween(final Double value1, final Double value2) {
            this.addCriterion("margin_interest_rate between", value1, value2, "marginInterestRate");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateNotBetween(final Double value1, final Double value2) {
            this.addCriterion("margin_interest_rate not between", value1, value2, "marginInterestRate");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateGreaterThan(final Double value) {
            this.addCriterion("margin_interest_rate >", value, "marginInterestRate");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("margin_interest_rate >=", value, "marginInterestRate");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateLessThan(final Double value) {
            this.addCriterion("margin_interest_rate <", value, "marginInterestRate");
            return (Criteria) this;
        }

        public Criteria andMarginInterestRateLessThanOrEqualTo(final Double value) {
            this.addCriterion("margin_interest_rate <=", value, "marginInterestRate");
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

        public Criteria andWithdrawFeeEqualTo(final BigDecimal value) {
            this.addCriterion("withdraw_fee =", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeNotEqualTo(final BigDecimal value) {
            this.addCriterion("withdraw_fee <>", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeIn(final List<BigDecimal> values) {
            this.addCriterion("withdraw_fee in", values, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeNotIn(final List<BigDecimal> values) {
            this.addCriterion("withdraw_fee not in", values, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("withdraw_fee between", value1, value2, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("withdraw_fee not between", value1, value2, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeGreaterThan(final BigDecimal value) {
            this.addCriterion("withdraw_fee >", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("withdraw_fee >=", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeLessThan(final BigDecimal value) {
            this.addCriterion("withdraw_fee <", value, "withdrawFee");
            return (Criteria) this;
        }

        public Criteria andWithdrawFeeLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("withdraw_fee <=", value, "withdrawFee");
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

        public Criteria andDepositConfirmEqualTo(final Integer value) {
            this.addCriterion("deposit_confirm =", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmNotEqualTo(final Integer value) {
            this.addCriterion("deposit_confirm <>", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmIn(final List<Integer> values) {
            this.addCriterion("deposit_confirm in", values, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmNotIn(final List<Integer> values) {
            this.addCriterion("deposit_confirm not in", values, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmBetween(final Integer value1, final Integer value2) {
            this.addCriterion("deposit_confirm between", value1, value2, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("deposit_confirm not between", value1, value2, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmGreaterThan(final Integer value) {
            this.addCriterion("deposit_confirm >", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("deposit_confirm >=", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmLessThan(final Integer value) {
            this.addCriterion("deposit_confirm <", value, "depositConfirm");
            return (Criteria) this;
        }

        public Criteria andDepositConfirmLessThanOrEqualTo(final Integer value) {
            this.addCriterion("deposit_confirm <=", value, "depositConfirm");
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

        public Criteria andWithdrawConfirmEqualTo(final Integer value) {
            this.addCriterion("withdraw_confirm =", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmNotEqualTo(final Integer value) {
            this.addCriterion("withdraw_confirm <>", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmIn(final List<Integer> values) {
            this.addCriterion("withdraw_confirm in", values, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmNotIn(final List<Integer> values) {
            this.addCriterion("withdraw_confirm not in", values, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmBetween(final Integer value1, final Integer value2) {
            this.addCriterion("withdraw_confirm between", value1, value2, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("withdraw_confirm not between", value1, value2, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmGreaterThan(final Integer value) {
            this.addCriterion("withdraw_confirm >", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("withdraw_confirm >=", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmLessThan(final Integer value) {
            this.addCriterion("withdraw_confirm <", value, "withdrawConfirm");
            return (Criteria) this;
        }

        public Criteria andWithdrawConfirmLessThanOrEqualTo(final Integer value) {
            this.addCriterion("withdraw_confirm <=", value, "withdrawConfirm");
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

        public Criteria andMinDepositAmountEqualTo(final BigDecimal value) {
            this.addCriterion("min_deposit_amount =", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountNotEqualTo(final BigDecimal value) {
            this.addCriterion("min_deposit_amount <>", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountIn(final List<BigDecimal> values) {
            this.addCriterion("min_deposit_amount in", values, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountNotIn(final List<BigDecimal> values) {
            this.addCriterion("min_deposit_amount not in", values, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("min_deposit_amount between", value1, value2, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("min_deposit_amount not between", value1, value2, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountGreaterThan(final BigDecimal value) {
            this.addCriterion("min_deposit_amount >", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("min_deposit_amount >=", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountLessThan(final BigDecimal value) {
            this.addCriterion("min_deposit_amount <", value, "minDepositAmount");
            return (Criteria) this;
        }

        public Criteria andMinDepositAmountLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("min_deposit_amount <=", value, "minDepositAmount");
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

        public Criteria andMinWithdrawAmountEqualTo(final BigDecimal value) {
            this.addCriterion("min_withdraw_amount =", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountNotEqualTo(final BigDecimal value) {
            this.addCriterion("min_withdraw_amount <>", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountIn(final List<BigDecimal> values) {
            this.addCriterion("min_withdraw_amount in", values, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountNotIn(final List<BigDecimal> values) {
            this.addCriterion("min_withdraw_amount not in", values, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("min_withdraw_amount between", value1, value2, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("min_withdraw_amount not between", value1, value2, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountGreaterThan(final BigDecimal value) {
            this.addCriterion("min_withdraw_amount >", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("min_withdraw_amount >=", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountLessThan(final BigDecimal value) {
            this.addCriterion("min_withdraw_amount <", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andMinWithdrawAmountLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("min_withdraw_amount <=", value, "minWithdrawAmount");
            return (Criteria) this;
        }

        public Criteria andFullNameIsNull() {
            this.addCriterion("full_name is null");
            return (Criteria) this;
        }

        public Criteria andFullNameIsNotNull() {
            this.addCriterion("full_name is not null");
            return (Criteria) this;
        }

        public Criteria andFullNameEqualTo(final String value) {
            this.addCriterion("full_name =", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameNotEqualTo(final String value) {
            this.addCriterion("full_name <>", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameIn(final List<String> values) {
            this.addCriterion("full_name in", values, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameNotIn(final List<String> values) {
            this.addCriterion("full_name not in", values, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameBetween(final String value1, final String value2) {
            this.addCriterion("full_name between", value1, value2, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameNotBetween(final String value1, final String value2) {
            this.addCriterion("full_name not between", value1, value2, "fullName");
            return (Criteria) this;
        }


        public Criteria andFullNameLike(final String value) {
            this.addCriterion("full_name like", value, "fullName");
            return (Criteria) this;
        }

        public Criteria andFullNameNotLike(final String value) {
            this.addCriterion("full_name not like", value, "fullName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andFieldLike(final String fieldName, final String keyword) {
            this.addCriterion(fieldName + " like ", keyword, fieldName);
            return this;
        }
    }

    public static class Criterion {
        private final String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private final String typeHandler;

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

        protected Criterion(final String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(final String condition, final Object value, final String typeHandler) {
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

        protected Criterion(final String condition, final Object value) {
            this(condition, value, null);
        }

        protected Criterion(final String condition, final Object value, final Object secondValue, final String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(final String condition, final Object value, final Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}