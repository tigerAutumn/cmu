package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账户资产 查询条件类
 *
 * @author newex-team
 * @date 2019-01-11 16:50:22
 */
public class UserBalanceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserBalanceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeIsNull() {
            addCriterion("currency_code is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeIsNotNull() {
            addCriterion("currency_code is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeEqualTo(String value) {
            addCriterion("currency_code =", value, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeNotEqualTo(String value) {
            addCriterion("currency_code <>", value, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeGreaterThan(String value) {
            addCriterion("currency_code >", value, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("currency_code >=", value, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeLessThan(String value) {
            addCriterion("currency_code <", value, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeLessThanOrEqualTo(String value) {
            addCriterion("currency_code <=", value, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeLike(String value) {
            addCriterion("currency_code like", value, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeNotLike(String value) {
            addCriterion("currency_code not like", value, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeIn(List<String> values) {
            addCriterion("currency_code in", values, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeNotIn(List<String> values) {
            addCriterion("currency_code not in", values, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeBetween(String value1, String value2) {
            addCriterion("currency_code between", value1, value2, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyCodeNotBetween(String value1, String value2) {
            addCriterion("currency_code not between", value1, value2, "currencyCode");
            return (Criteria) this;
        }

        public Criteria andEnvIsNull() {
            addCriterion("env is null");
            return (Criteria) this;
        }

        public Criteria andEnvIsNotNull() {
            addCriterion("env is not null");
            return (Criteria) this;
        }

        public Criteria andEnvEqualTo(Integer value) {
            addCriterion("env =", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotEqualTo(Integer value) {
            addCriterion("env <>", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvGreaterThan(Integer value) {
            addCriterion("env >", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvGreaterThanOrEqualTo(Integer value) {
            addCriterion("env >=", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvLessThan(Integer value) {
            addCriterion("env <", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvLessThanOrEqualTo(Integer value) {
            addCriterion("env <=", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvIn(List<Integer> values) {
            addCriterion("env in", values, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotIn(List<Integer> values) {
            addCriterion("env not in", values, "env");
            return (Criteria) this;
        }

        public Criteria andEnvBetween(Integer value1, Integer value2) {
            addCriterion("env between", value1, value2, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotBetween(Integer value1, Integer value2) {
            addCriterion("env not between", value1, value2, "env");
            return (Criteria) this;
        }

        public Criteria andRewardTimeIsNull() {
            addCriterion("reward_time is null");
            return (Criteria) this;
        }

        public Criteria andRewardTimeIsNotNull() {
            addCriterion("reward_time is not null");
            return (Criteria) this;
        }

        public Criteria andRewardTimeEqualTo(Long value) {
            addCriterion("reward_time =", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeNotEqualTo(Long value) {
            addCriterion("reward_time <>", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeGreaterThan(Long value) {
            addCriterion("reward_time >", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("reward_time >=", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeLessThan(Long value) {
            addCriterion("reward_time <", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeLessThanOrEqualTo(Long value) {
            addCriterion("reward_time <=", value, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeIn(List<Long> values) {
            addCriterion("reward_time in", values, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeNotIn(List<Long> values) {
            addCriterion("reward_time not in", values, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeBetween(Long value1, Long value2) {
            addCriterion("reward_time between", value1, value2, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andRewardTimeNotBetween(Long value1, Long value2) {
            addCriterion("reward_time not between", value1, value2, "rewardTime");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusIsNull() {
            addCriterion("frozen_status is null");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusIsNotNull() {
            addCriterion("frozen_status is not null");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusEqualTo(Integer value) {
            addCriterion("frozen_status =", value, "frozenStatus");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusNotEqualTo(Integer value) {
            addCriterion("frozen_status <>", value, "frozenStatus");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusGreaterThan(Integer value) {
            addCriterion("frozen_status >", value, "frozenStatus");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("frozen_status >=", value, "frozenStatus");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusLessThan(Integer value) {
            addCriterion("frozen_status <", value, "frozenStatus");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusLessThanOrEqualTo(Integer value) {
            addCriterion("frozen_status <=", value, "frozenStatus");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusIn(List<Integer> values) {
            addCriterion("frozen_status in", values, "frozenStatus");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusNotIn(List<Integer> values) {
            addCriterion("frozen_status not in", values, "frozenStatus");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusBetween(Integer value1, Integer value2) {
            addCriterion("frozen_status between", value1, value2, "frozenStatus");
            return (Criteria) this;
        }

        public Criteria andFrozenStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("frozen_status not between", value1, value2, "frozenStatus");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceIsNull() {
            addCriterion("available_balance is null");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceIsNotNull() {
            addCriterion("available_balance is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceEqualTo(BigDecimal value) {
            addCriterion("available_balance =", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceNotEqualTo(BigDecimal value) {
            addCriterion("available_balance <>", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceGreaterThan(BigDecimal value) {
            addCriterion("available_balance >", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("available_balance >=", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceLessThan(BigDecimal value) {
            addCriterion("available_balance <", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("available_balance <=", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceIn(List<BigDecimal> values) {
            addCriterion("available_balance in", values, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceNotIn(List<BigDecimal> values) {
            addCriterion("available_balance not in", values, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_balance between", value1, value2, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_balance not between", value1, value2, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceIsNull() {
            addCriterion("frozen_balance is null");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceIsNotNull() {
            addCriterion("frozen_balance is not null");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceEqualTo(BigDecimal value) {
            addCriterion("frozen_balance =", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceNotEqualTo(BigDecimal value) {
            addCriterion("frozen_balance <>", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceGreaterThan(BigDecimal value) {
            addCriterion("frozen_balance >", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frozen_balance >=", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceLessThan(BigDecimal value) {
            addCriterion("frozen_balance <", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frozen_balance <=", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceIn(List<BigDecimal> values) {
            addCriterion("frozen_balance in", values, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceNotIn(List<BigDecimal> values) {
            addCriterion("frozen_balance not in", values, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frozen_balance between", value1, value2, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frozen_balance not between", value1, value2, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andPositionSizeIsNull() {
            addCriterion("position_size is null");
            return (Criteria) this;
        }

        public Criteria andPositionSizeIsNotNull() {
            addCriterion("position_size is not null");
            return (Criteria) this;
        }

        public Criteria andPositionSizeEqualTo(BigDecimal value) {
            addCriterion("position_size =", value, "positionSize");
            return (Criteria) this;
        }

        public Criteria andPositionSizeNotEqualTo(BigDecimal value) {
            addCriterion("position_size <>", value, "positionSize");
            return (Criteria) this;
        }

        public Criteria andPositionSizeGreaterThan(BigDecimal value) {
            addCriterion("position_size >", value, "positionSize");
            return (Criteria) this;
        }

        public Criteria andPositionSizeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("position_size >=", value, "positionSize");
            return (Criteria) this;
        }

        public Criteria andPositionSizeLessThan(BigDecimal value) {
            addCriterion("position_size <", value, "positionSize");
            return (Criteria) this;
        }

        public Criteria andPositionSizeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("position_size <=", value, "positionSize");
            return (Criteria) this;
        }

        public Criteria andPositionSizeIn(List<BigDecimal> values) {
            addCriterion("position_size in", values, "positionSize");
            return (Criteria) this;
        }

        public Criteria andPositionSizeNotIn(List<BigDecimal> values) {
            addCriterion("position_size not in", values, "positionSize");
            return (Criteria) this;
        }

        public Criteria andPositionSizeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("position_size between", value1, value2, "positionSize");
            return (Criteria) this;
        }

        public Criteria andPositionSizeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("position_size not between", value1, value2, "positionSize");
            return (Criteria) this;
        }

        public Criteria andPositionMarginIsNull() {
            addCriterion("position_margin is null");
            return (Criteria) this;
        }

        public Criteria andPositionMarginIsNotNull() {
            addCriterion("position_margin is not null");
            return (Criteria) this;
        }

        public Criteria andPositionMarginEqualTo(BigDecimal value) {
            addCriterion("position_margin =", value, "positionMargin");
            return (Criteria) this;
        }

        public Criteria andPositionMarginNotEqualTo(BigDecimal value) {
            addCriterion("position_margin <>", value, "positionMargin");
            return (Criteria) this;
        }

        public Criteria andPositionMarginGreaterThan(BigDecimal value) {
            addCriterion("position_margin >", value, "positionMargin");
            return (Criteria) this;
        }

        public Criteria andPositionMarginGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("position_margin >=", value, "positionMargin");
            return (Criteria) this;
        }

        public Criteria andPositionMarginLessThan(BigDecimal value) {
            addCriterion("position_margin <", value, "positionMargin");
            return (Criteria) this;
        }

        public Criteria andPositionMarginLessThanOrEqualTo(BigDecimal value) {
            addCriterion("position_margin <=", value, "positionMargin");
            return (Criteria) this;
        }

        public Criteria andPositionMarginIn(List<BigDecimal> values) {
            addCriterion("position_margin in", values, "positionMargin");
            return (Criteria) this;
        }

        public Criteria andPositionMarginNotIn(List<BigDecimal> values) {
            addCriterion("position_margin not in", values, "positionMargin");
            return (Criteria) this;
        }

        public Criteria andPositionMarginBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("position_margin between", value1, value2, "positionMargin");
            return (Criteria) this;
        }

        public Criteria andPositionMarginNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("position_margin not between", value1, value2, "positionMargin");
            return (Criteria) this;
        }

        public Criteria andPositionFeeIsNull() {
            addCriterion("position_fee is null");
            return (Criteria) this;
        }

        public Criteria andPositionFeeIsNotNull() {
            addCriterion("position_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPositionFeeEqualTo(BigDecimal value) {
            addCriterion("position_fee =", value, "positionFee");
            return (Criteria) this;
        }

        public Criteria andPositionFeeNotEqualTo(BigDecimal value) {
            addCriterion("position_fee <>", value, "positionFee");
            return (Criteria) this;
        }

        public Criteria andPositionFeeGreaterThan(BigDecimal value) {
            addCriterion("position_fee >", value, "positionFee");
            return (Criteria) this;
        }

        public Criteria andPositionFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("position_fee >=", value, "positionFee");
            return (Criteria) this;
        }

        public Criteria andPositionFeeLessThan(BigDecimal value) {
            addCriterion("position_fee <", value, "positionFee");
            return (Criteria) this;
        }

        public Criteria andPositionFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("position_fee <=", value, "positionFee");
            return (Criteria) this;
        }

        public Criteria andPositionFeeIn(List<BigDecimal> values) {
            addCriterion("position_fee in", values, "positionFee");
            return (Criteria) this;
        }

        public Criteria andPositionFeeNotIn(List<BigDecimal> values) {
            addCriterion("position_fee not in", values, "positionFee");
            return (Criteria) this;
        }

        public Criteria andPositionFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("position_fee between", value1, value2, "positionFee");
            return (Criteria) this;
        }

        public Criteria andPositionFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("position_fee not between", value1, value2, "positionFee");
            return (Criteria) this;
        }

        public Criteria andOrderMarginIsNull() {
            addCriterion("order_margin is null");
            return (Criteria) this;
        }

        public Criteria andOrderMarginIsNotNull() {
            addCriterion("order_margin is not null");
            return (Criteria) this;
        }

        public Criteria andOrderMarginEqualTo(BigDecimal value) {
            addCriterion("order_margin =", value, "orderMargin");
            return (Criteria) this;
        }

        public Criteria andOrderMarginNotEqualTo(BigDecimal value) {
            addCriterion("order_margin <>", value, "orderMargin");
            return (Criteria) this;
        }

        public Criteria andOrderMarginGreaterThan(BigDecimal value) {
            addCriterion("order_margin >", value, "orderMargin");
            return (Criteria) this;
        }

        public Criteria andOrderMarginGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_margin >=", value, "orderMargin");
            return (Criteria) this;
        }

        public Criteria andOrderMarginLessThan(BigDecimal value) {
            addCriterion("order_margin <", value, "orderMargin");
            return (Criteria) this;
        }

        public Criteria andOrderMarginLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_margin <=", value, "orderMargin");
            return (Criteria) this;
        }

        public Criteria andOrderMarginIn(List<BigDecimal> values) {
            addCriterion("order_margin in", values, "orderMargin");
            return (Criteria) this;
        }

        public Criteria andOrderMarginNotIn(List<BigDecimal> values) {
            addCriterion("order_margin not in", values, "orderMargin");
            return (Criteria) this;
        }

        public Criteria andOrderMarginBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_margin between", value1, value2, "orderMargin");
            return (Criteria) this;
        }

        public Criteria andOrderMarginNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_margin not between", value1, value2, "orderMargin");
            return (Criteria) this;
        }

        public Criteria andOrderFeeIsNull() {
            addCriterion("order_fee is null");
            return (Criteria) this;
        }

        public Criteria andOrderFeeIsNotNull() {
            addCriterion("order_fee is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFeeEqualTo(BigDecimal value) {
            addCriterion("order_fee =", value, "orderFee");
            return (Criteria) this;
        }

        public Criteria andOrderFeeNotEqualTo(BigDecimal value) {
            addCriterion("order_fee <>", value, "orderFee");
            return (Criteria) this;
        }

        public Criteria andOrderFeeGreaterThan(BigDecimal value) {
            addCriterion("order_fee >", value, "orderFee");
            return (Criteria) this;
        }

        public Criteria andOrderFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_fee >=", value, "orderFee");
            return (Criteria) this;
        }

        public Criteria andOrderFeeLessThan(BigDecimal value) {
            addCriterion("order_fee <", value, "orderFee");
            return (Criteria) this;
        }

        public Criteria andOrderFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_fee <=", value, "orderFee");
            return (Criteria) this;
        }

        public Criteria andOrderFeeIn(List<BigDecimal> values) {
            addCriterion("order_fee in", values, "orderFee");
            return (Criteria) this;
        }

        public Criteria andOrderFeeNotIn(List<BigDecimal> values) {
            addCriterion("order_fee not in", values, "orderFee");
            return (Criteria) this;
        }

        public Criteria andOrderFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_fee between", value1, value2, "orderFee");
            return (Criteria) this;
        }

        public Criteria andOrderFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_fee not between", value1, value2, "orderFee");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusIsNull() {
            addCriterion("realized_surplus is null");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusIsNotNull() {
            addCriterion("realized_surplus is not null");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusEqualTo(BigDecimal value) {
            addCriterion("realized_surplus =", value, "realizedSurplus");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusNotEqualTo(BigDecimal value) {
            addCriterion("realized_surplus <>", value, "realizedSurplus");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusGreaterThan(BigDecimal value) {
            addCriterion("realized_surplus >", value, "realizedSurplus");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("realized_surplus >=", value, "realizedSurplus");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusLessThan(BigDecimal value) {
            addCriterion("realized_surplus <", value, "realizedSurplus");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("realized_surplus <=", value, "realizedSurplus");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusIn(List<BigDecimal> values) {
            addCriterion("realized_surplus in", values, "realizedSurplus");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusNotIn(List<BigDecimal> values) {
            addCriterion("realized_surplus not in", values, "realizedSurplus");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("realized_surplus between", value1, value2, "realizedSurplus");
            return (Criteria) this;
        }

        public Criteria andRealizedSurplusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("realized_surplus not between", value1, value2, "realizedSurplus");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNull() {
            addCriterion("broker_id is null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNotNull() {
            addCriterion("broker_id is not null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdEqualTo(Integer value) {
            addCriterion("broker_id =", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotEqualTo(Integer value) {
            addCriterion("broker_id <>", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThan(Integer value) {
            addCriterion("broker_id >", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("broker_id >=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThan(Integer value) {
            addCriterion("broker_id <", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThanOrEqualTo(Integer value) {
            addCriterion("broker_id <=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIn(List<Integer> values) {
            addCriterion("broker_id in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotIn(List<Integer> values) {
            addCriterion("broker_id not in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdBetween(Integer value1, Integer value2) {
            addCriterion("broker_id between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("broker_id not between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            addCriterion("created_date is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            addCriterion("created_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(Date value) {
            addCriterion("created_date =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(Date value) {
            addCriterion("created_date <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(Date value) {
            addCriterion("created_date >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("created_date >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(Date value) {
            addCriterion("created_date <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(Date value) {
            addCriterion("created_date <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(List<Date> values) {
            addCriterion("created_date in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(List<Date> values) {
            addCriterion("created_date not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(Date value1, Date value2) {
            addCriterion("created_date between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(Date value1, Date value2) {
            addCriterion("created_date not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNull() {
            addCriterion("modify_date is null");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNotNull() {
            addCriterion("modify_date is not null");
            return (Criteria) this;
        }

        public Criteria andModifyDateEqualTo(Date value) {
            addCriterion("modify_date =", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotEqualTo(Date value) {
            addCriterion("modify_date <>", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThan(Date value) {
            addCriterion("modify_date >", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_date >=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThan(Date value) {
            addCriterion("modify_date <", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThanOrEqualTo(Date value) {
            addCriterion("modify_date <=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateIn(List<Date> values) {
            addCriterion("modify_date in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotIn(List<Date> values) {
            addCriterion("modify_date not in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateBetween(Date value1, Date value2) {
            addCriterion("modify_date between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotBetween(Date value1, Date value2) {
            addCriterion("modify_date not between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andFieldIsNull(String fieldName) {
            addCriterion(fieldName + " is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull(String fieldName) {
            addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " =", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " <>", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " >", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " >=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " <", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " <=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLike(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " not like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldIn(String fieldName, List<Object> fieldValues) {
            addCriterion(fieldName + " in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(String fieldName, List<Object> fieldValues) {
            addCriterion(fieldName + " not in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldBetween(String fieldName, Object fieldValue1, Object fieldValue2) {
            addCriterion(fieldName + " between", fieldValue1, fieldValue2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(String fieldName, Object fieldValue1, Object fieldValue2) {
            addCriterion(fieldName + " not between", fieldValue1, fieldValue2, "fieldName");
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
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
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