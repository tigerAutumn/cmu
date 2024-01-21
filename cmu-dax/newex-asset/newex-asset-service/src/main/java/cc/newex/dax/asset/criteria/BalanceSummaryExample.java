package cc.newex.dax.asset.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询条件example类
 *
 * @author newex-team
 * @date 2018-06-26
 */
public class BalanceSummaryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BalanceSummaryExample() {
        this.oredCriteria = new ArrayList<>();
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
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

        public Criteria andIdEqualTo(Long value) {
            this.addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            this.addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            this.addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNull() {
            this.addCriterion("currency is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNotNull() {
            this.addCriterion("currency is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyEqualTo(Integer value) {
            this.addCriterion("currency =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(Integer value) {
            this.addCriterion("currency <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(List<Integer> values) {
            this.addCriterion("currency in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(List<Integer> values) {
            this.addCriterion("currency not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(Integer value1, Integer value2) {
            this.addCriterion("currency between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(Integer value1, Integer value2) {
            this.addCriterion("currency not between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(Integer value) {
            this.addCriterion("currency >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("currency >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(Integer value) {
            this.addCriterion("currency <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(Integer value) {
            this.addCriterion("currency <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceIsNull() {
            this.addCriterion("wallet_balance is null");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceIsNotNull() {
            this.addCriterion("wallet_balance is not null");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceEqualTo(BigDecimal value) {
            this.addCriterion("wallet_balance =", value, "walletBalance");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceNotEqualTo(BigDecimal value) {
            this.addCriterion("wallet_balance <>", value, "walletBalance");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceIn(List<BigDecimal> values) {
            this.addCriterion("wallet_balance in", values, "walletBalance");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceNotIn(List<BigDecimal> values) {
            this.addCriterion("wallet_balance not in", values, "walletBalance");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("wallet_balance between", value1, value2, "walletBalance");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("wallet_balance not between", value1, value2, "walletBalance");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceGreaterThan(BigDecimal value) {
            this.addCriterion("wallet_balance >", value, "walletBalance");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("wallet_balance >=", value, "walletBalance");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceLessThan(BigDecimal value) {
            this.addCriterion("wallet_balance <", value, "walletBalance");
            return (Criteria) this;
        }

        public Criteria andWalletBalanceLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("wallet_balance <=", value, "walletBalance");
            return (Criteria) this;
        }

        public Criteria andBizBalanceIsNull() {
            this.addCriterion("biz_balance is null");
            return (Criteria) this;
        }

        public Criteria andBizBalanceIsNotNull() {
            this.addCriterion("biz_balance is not null");
            return (Criteria) this;
        }

        public Criteria andBizBalanceEqualTo(String value) {
            this.addCriterion("biz_balance =", value, "bizBalance");
            return (Criteria) this;
        }

        public Criteria andBizBalanceNotEqualTo(String value) {
            this.addCriterion("biz_balance <>", value, "bizBalance");
            return (Criteria) this;
        }

        public Criteria andBizBalanceIn(List<String> values) {
            this.addCriterion("biz_balance in", values, "bizBalance");
            return (Criteria) this;
        }

        public Criteria andBizBalanceNotIn(List<String> values) {
            this.addCriterion("biz_balance not in", values, "bizBalance");
            return (Criteria) this;
        }

        public Criteria andBizBalanceBetween(String value1, String value2) {
            this.addCriterion("biz_balance between", value1, value2, "bizBalance");
            return (Criteria) this;
        }

        public Criteria andBizBalanceNotBetween(String value1, String value2) {
            this.addCriterion("biz_balance not between", value1, value2, "bizBalance");
            return (Criteria) this;
        }


        public Criteria andBizBalanceLike(String value) {
            this.addCriterion("biz_balance like", value, "bizBalance");
            return (Criteria) this;
        }

        public Criteria andBizBalanceNotLike(String value) {
            this.addCriterion("biz_balance not like", value, "bizBalance");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedIsNull() {
            this.addCriterion("deposit_unconfirmed is null");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedIsNotNull() {
            this.addCriterion("deposit_unconfirmed is not null");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedEqualTo(BigDecimal value) {
            this.addCriterion("deposit_unconfirmed =", value, "depositUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedNotEqualTo(BigDecimal value) {
            this.addCriterion("deposit_unconfirmed <>", value, "depositUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedIn(List<BigDecimal> values) {
            this.addCriterion("deposit_unconfirmed in", values, "depositUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedNotIn(List<BigDecimal> values) {
            this.addCriterion("deposit_unconfirmed not in", values, "depositUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("deposit_unconfirmed between", value1, value2, "depositUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("deposit_unconfirmed not between", value1, value2, "depositUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedGreaterThan(BigDecimal value) {
            this.addCriterion("deposit_unconfirmed >", value, "depositUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("deposit_unconfirmed >=", value, "depositUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedLessThan(BigDecimal value) {
            this.addCriterion("deposit_unconfirmed <", value, "depositUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andDepositUnconfirmedLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("deposit_unconfirmed <=", value, "depositUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedIsNull() {
            this.addCriterion("withdraw_unconfirmed is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedIsNotNull() {
            this.addCriterion("withdraw_unconfirmed is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedEqualTo(BigDecimal value) {
            this.addCriterion("withdraw_unconfirmed =", value, "withdrawUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedNotEqualTo(BigDecimal value) {
            this.addCriterion("withdraw_unconfirmed <>", value, "withdrawUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedIn(List<BigDecimal> values) {
            this.addCriterion("withdraw_unconfirmed in", values, "withdrawUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedNotIn(List<BigDecimal> values) {
            this.addCriterion("withdraw_unconfirmed not in", values, "withdrawUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("withdraw_unconfirmed between", value1, value2, "withdrawUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("withdraw_unconfirmed not between", value1, value2, "withdrawUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedGreaterThan(BigDecimal value) {
            this.addCriterion("withdraw_unconfirmed >", value, "withdrawUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("withdraw_unconfirmed >=", value, "withdrawUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedLessThan(BigDecimal value) {
            this.addCriterion("withdraw_unconfirmed <", value, "withdrawUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andWithdrawUnconfirmedLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("withdraw_unconfirmed <=", value, "withdrawUnconfirmed");
            return (Criteria) this;
        }

        public Criteria andDifferenceIsNull() {
            this.addCriterion("difference is null");
            return (Criteria) this;
        }

        public Criteria andDifferenceIsNotNull() {
            this.addCriterion("difference is not null");
            return (Criteria) this;
        }

        public Criteria andDifferenceEqualTo(BigDecimal value) {
            this.addCriterion("difference =", value, "difference");
            return (Criteria) this;
        }

        public Criteria andDifferenceNotEqualTo(BigDecimal value) {
            this.addCriterion("difference <>", value, "difference");
            return (Criteria) this;
        }

        public Criteria andDifferenceIn(List<BigDecimal> values) {
            this.addCriterion("difference in", values, "difference");
            return (Criteria) this;
        }

        public Criteria andDifferenceNotIn(List<BigDecimal> values) {
            this.addCriterion("difference not in", values, "difference");
            return (Criteria) this;
        }

        public Criteria andDifferenceBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("difference between", value1, value2, "difference");
            return (Criteria) this;
        }

        public Criteria andDifferenceNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("difference not between", value1, value2, "difference");
            return (Criteria) this;
        }

        public Criteria andDifferenceGreaterThan(BigDecimal value) {
            this.addCriterion("difference >", value, "difference");
            return (Criteria) this;
        }

        public Criteria andDifferenceGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("difference >=", value, "difference");
            return (Criteria) this;
        }

        public Criteria andDifferenceLessThan(BigDecimal value) {
            this.addCriterion("difference <", value, "difference");
            return (Criteria) this;
        }

        public Criteria andDifferenceLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("difference <=", value, "difference");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            this.addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            this.addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            this.addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            this.addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            this.addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            this.addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            this.addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            this.addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            this.addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            this.addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            this.addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            this.addCriterion("create_date <=", value, "createDate");
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
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

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
    }
}