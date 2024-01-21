package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户持仓清算流水表 查询条件类
 *
 * @author newex-team
 * @date 2019-01-09 14:12:48
 */
public class PositionClearExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PositionClearExample() {
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

        public Criteria andFeeRateIsNull() {
            addCriterion("fee_rate is null");
            return (Criteria) this;
        }

        public Criteria andFeeRateIsNotNull() {
            addCriterion("fee_rate is not null");
            return (Criteria) this;
        }

        public Criteria andFeeRateEqualTo(BigDecimal value) {
            addCriterion("fee_rate =", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateNotEqualTo(BigDecimal value) {
            addCriterion("fee_rate <>", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateGreaterThan(BigDecimal value) {
            addCriterion("fee_rate >", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_rate >=", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateLessThan(BigDecimal value) {
            addCriterion("fee_rate <", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_rate <=", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateIn(List<BigDecimal> values) {
            addCriterion("fee_rate in", values, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateNotIn(List<BigDecimal> values) {
            addCriterion("fee_rate not in", values, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_rate between", value1, value2, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_rate not between", value1, value2, "feeRate");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andSideIsNull() {
            addCriterion("side is null");
            return (Criteria) this;
        }

        public Criteria andSideIsNotNull() {
            addCriterion("side is not null");
            return (Criteria) this;
        }

        public Criteria andSideEqualTo(String value) {
            addCriterion("side =", value, "side");
            return (Criteria) this;
        }

        public Criteria andSideNotEqualTo(String value) {
            addCriterion("side <>", value, "side");
            return (Criteria) this;
        }

        public Criteria andSideGreaterThan(String value) {
            addCriterion("side >", value, "side");
            return (Criteria) this;
        }

        public Criteria andSideGreaterThanOrEqualTo(String value) {
            addCriterion("side >=", value, "side");
            return (Criteria) this;
        }

        public Criteria andSideLessThan(String value) {
            addCriterion("side <", value, "side");
            return (Criteria) this;
        }

        public Criteria andSideLessThanOrEqualTo(String value) {
            addCriterion("side <=", value, "side");
            return (Criteria) this;
        }

        public Criteria andSideLike(String value) {
            addCriterion("side like", value, "side");
            return (Criteria) this;
        }

        public Criteria andSideNotLike(String value) {
            addCriterion("side not like", value, "side");
            return (Criteria) this;
        }

        public Criteria andSideIn(List<String> values) {
            addCriterion("side in", values, "side");
            return (Criteria) this;
        }

        public Criteria andSideNotIn(List<String> values) {
            addCriterion("side not in", values, "side");
            return (Criteria) this;
        }

        public Criteria andSideBetween(String value1, String value2) {
            addCriterion("side between", value1, value2, "side");
            return (Criteria) this;
        }

        public Criteria andSideNotBetween(String value1, String value2) {
            addCriterion("side not between", value1, value2, "side");
            return (Criteria) this;
        }

        public Criteria andBaseIsNull() {
            addCriterion("base is null");
            return (Criteria) this;
        }

        public Criteria andBaseIsNotNull() {
            addCriterion("base is not null");
            return (Criteria) this;
        }

        public Criteria andBaseEqualTo(String value) {
            addCriterion("base =", value, "base");
            return (Criteria) this;
        }

        public Criteria andBaseNotEqualTo(String value) {
            addCriterion("base <>", value, "base");
            return (Criteria) this;
        }

        public Criteria andBaseGreaterThan(String value) {
            addCriterion("base >", value, "base");
            return (Criteria) this;
        }

        public Criteria andBaseGreaterThanOrEqualTo(String value) {
            addCriterion("base >=", value, "base");
            return (Criteria) this;
        }

        public Criteria andBaseLessThan(String value) {
            addCriterion("base <", value, "base");
            return (Criteria) this;
        }

        public Criteria andBaseLessThanOrEqualTo(String value) {
            addCriterion("base <=", value, "base");
            return (Criteria) this;
        }

        public Criteria andBaseLike(String value) {
            addCriterion("base like", value, "base");
            return (Criteria) this;
        }

        public Criteria andBaseNotLike(String value) {
            addCriterion("base not like", value, "base");
            return (Criteria) this;
        }

        public Criteria andBaseIn(List<String> values) {
            addCriterion("base in", values, "base");
            return (Criteria) this;
        }

        public Criteria andBaseNotIn(List<String> values) {
            addCriterion("base not in", values, "base");
            return (Criteria) this;
        }

        public Criteria andBaseBetween(String value1, String value2) {
            addCriterion("base between", value1, value2, "base");
            return (Criteria) this;
        }

        public Criteria andBaseNotBetween(String value1, String value2) {
            addCriterion("base not between", value1, value2, "base");
            return (Criteria) this;
        }

        public Criteria andContractCodeIsNull() {
            addCriterion("contract_code is null");
            return (Criteria) this;
        }

        public Criteria andContractCodeIsNotNull() {
            addCriterion("contract_code is not null");
            return (Criteria) this;
        }

        public Criteria andContractCodeEqualTo(String value) {
            addCriterion("contract_code =", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotEqualTo(String value) {
            addCriterion("contract_code <>", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeGreaterThan(String value) {
            addCriterion("contract_code >", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeGreaterThanOrEqualTo(String value) {
            addCriterion("contract_code >=", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLessThan(String value) {
            addCriterion("contract_code <", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLessThanOrEqualTo(String value) {
            addCriterion("contract_code <=", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLike(String value) {
            addCriterion("contract_code like", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotLike(String value) {
            addCriterion("contract_code not like", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeIn(List<String> values) {
            addCriterion("contract_code in", values, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotIn(List<String> values) {
            addCriterion("contract_code not in", values, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeBetween(String value1, String value2) {
            addCriterion("contract_code between", value1, value2, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotBetween(String value1, String value2) {
            addCriterion("contract_code not between", value1, value2, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractIdIsNull() {
            addCriterion("contract_id is null");
            return (Criteria) this;
        }

        public Criteria andContractIdIsNotNull() {
            addCriterion("contract_id is not null");
            return (Criteria) this;
        }

        public Criteria andContractIdEqualTo(Long value) {
            addCriterion("contract_id =", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotEqualTo(Long value) {
            addCriterion("contract_id <>", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdGreaterThan(Long value) {
            addCriterion("contract_id >", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdGreaterThanOrEqualTo(Long value) {
            addCriterion("contract_id >=", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdLessThan(Long value) {
            addCriterion("contract_id <", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdLessThanOrEqualTo(Long value) {
            addCriterion("contract_id <=", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdIn(List<Long> values) {
            addCriterion("contract_id in", values, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotIn(List<Long> values) {
            addCriterion("contract_id not in", values, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdBetween(Long value1, Long value2) {
            addCriterion("contract_id between", value1, value2, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotBetween(Long value1, Long value2) {
            addCriterion("contract_id not between", value1, value2, "contractId");
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

        public Criteria andOpenMarginIsNull() {
            addCriterion("open_margin is null");
            return (Criteria) this;
        }

        public Criteria andOpenMarginIsNotNull() {
            addCriterion("open_margin is not null");
            return (Criteria) this;
        }

        public Criteria andOpenMarginEqualTo(BigDecimal value) {
            addCriterion("open_margin =", value, "openMargin");
            return (Criteria) this;
        }

        public Criteria andOpenMarginNotEqualTo(BigDecimal value) {
            addCriterion("open_margin <>", value, "openMargin");
            return (Criteria) this;
        }

        public Criteria andOpenMarginGreaterThan(BigDecimal value) {
            addCriterion("open_margin >", value, "openMargin");
            return (Criteria) this;
        }

        public Criteria andOpenMarginGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("open_margin >=", value, "openMargin");
            return (Criteria) this;
        }

        public Criteria andOpenMarginLessThan(BigDecimal value) {
            addCriterion("open_margin <", value, "openMargin");
            return (Criteria) this;
        }

        public Criteria andOpenMarginLessThanOrEqualTo(BigDecimal value) {
            addCriterion("open_margin <=", value, "openMargin");
            return (Criteria) this;
        }

        public Criteria andOpenMarginIn(List<BigDecimal> values) {
            addCriterion("open_margin in", values, "openMargin");
            return (Criteria) this;
        }

        public Criteria andOpenMarginNotIn(List<BigDecimal> values) {
            addCriterion("open_margin not in", values, "openMargin");
            return (Criteria) this;
        }

        public Criteria andOpenMarginBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("open_margin between", value1, value2, "openMargin");
            return (Criteria) this;
        }

        public Criteria andOpenMarginNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("open_margin not between", value1, value2, "openMargin");
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

        public Criteria andBeforeBalanceIsNull() {
            addCriterion("before_balance is null");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceIsNotNull() {
            addCriterion("before_balance is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceEqualTo(BigDecimal value) {
            addCriterion("before_balance =", value, "beforeBalance");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceNotEqualTo(BigDecimal value) {
            addCriterion("before_balance <>", value, "beforeBalance");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceGreaterThan(BigDecimal value) {
            addCriterion("before_balance >", value, "beforeBalance");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("before_balance >=", value, "beforeBalance");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceLessThan(BigDecimal value) {
            addCriterion("before_balance <", value, "beforeBalance");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("before_balance <=", value, "beforeBalance");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceIn(List<BigDecimal> values) {
            addCriterion("before_balance in", values, "beforeBalance");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceNotIn(List<BigDecimal> values) {
            addCriterion("before_balance not in", values, "beforeBalance");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_balance between", value1, value2, "beforeBalance");
            return (Criteria) this;
        }

        public Criteria andBeforeBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_balance not between", value1, value2, "beforeBalance");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceIsNull() {
            addCriterion("after_balance is null");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceIsNotNull() {
            addCriterion("after_balance is not null");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceEqualTo(BigDecimal value) {
            addCriterion("after_balance =", value, "afterBalance");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceNotEqualTo(BigDecimal value) {
            addCriterion("after_balance <>", value, "afterBalance");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceGreaterThan(BigDecimal value) {
            addCriterion("after_balance >", value, "afterBalance");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("after_balance >=", value, "afterBalance");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceLessThan(BigDecimal value) {
            addCriterion("after_balance <", value, "afterBalance");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("after_balance <=", value, "afterBalance");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceIn(List<BigDecimal> values) {
            addCriterion("after_balance in", values, "afterBalance");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceNotIn(List<BigDecimal> values) {
            addCriterion("after_balance not in", values, "afterBalance");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_balance between", value1, value2, "afterBalance");
            return (Criteria) this;
        }

        public Criteria andAfterBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_balance not between", value1, value2, "afterBalance");
            return (Criteria) this;
        }

        public Criteria andLastPriceIsNull() {
            addCriterion("last_price is null");
            return (Criteria) this;
        }

        public Criteria andLastPriceIsNotNull() {
            addCriterion("last_price is not null");
            return (Criteria) this;
        }

        public Criteria andLastPriceEqualTo(BigDecimal value) {
            addCriterion("last_price =", value, "lastPrice");
            return (Criteria) this;
        }

        public Criteria andLastPriceNotEqualTo(BigDecimal value) {
            addCriterion("last_price <>", value, "lastPrice");
            return (Criteria) this;
        }

        public Criteria andLastPriceGreaterThan(BigDecimal value) {
            addCriterion("last_price >", value, "lastPrice");
            return (Criteria) this;
        }

        public Criteria andLastPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("last_price >=", value, "lastPrice");
            return (Criteria) this;
        }

        public Criteria andLastPriceLessThan(BigDecimal value) {
            addCriterion("last_price <", value, "lastPrice");
            return (Criteria) this;
        }

        public Criteria andLastPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("last_price <=", value, "lastPrice");
            return (Criteria) this;
        }

        public Criteria andLastPriceIn(List<BigDecimal> values) {
            addCriterion("last_price in", values, "lastPrice");
            return (Criteria) this;
        }

        public Criteria andLastPriceNotIn(List<BigDecimal> values) {
            addCriterion("last_price not in", values, "lastPrice");
            return (Criteria) this;
        }

        public Criteria andLastPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("last_price between", value1, value2, "lastPrice");
            return (Criteria) this;
        }

        public Criteria andLastPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("last_price not between", value1, value2, "lastPrice");
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

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andSumSizeIsNull() {
            addCriterion("sum_size is null");
            return (Criteria) this;
        }

        public Criteria andSumSizeIsNotNull() {
            addCriterion("sum_size is not null");
            return (Criteria) this;
        }

        public Criteria andSumSizeEqualTo(BigDecimal value) {
            addCriterion("sum_size =", value, "sumSize");
            return (Criteria) this;
        }

        public Criteria andSumSizeNotEqualTo(BigDecimal value) {
            addCriterion("sum_size <>", value, "sumSize");
            return (Criteria) this;
        }

        public Criteria andSumSizeGreaterThan(BigDecimal value) {
            addCriterion("sum_size >", value, "sumSize");
            return (Criteria) this;
        }

        public Criteria andSumSizeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_size >=", value, "sumSize");
            return (Criteria) this;
        }

        public Criteria andSumSizeLessThan(BigDecimal value) {
            addCriterion("sum_size <", value, "sumSize");
            return (Criteria) this;
        }

        public Criteria andSumSizeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_size <=", value, "sumSize");
            return (Criteria) this;
        }

        public Criteria andSumSizeIn(List<BigDecimal> values) {
            addCriterion("sum_size in", values, "sumSize");
            return (Criteria) this;
        }

        public Criteria andSumSizeNotIn(List<BigDecimal> values) {
            addCriterion("sum_size not in", values, "sumSize");
            return (Criteria) this;
        }

        public Criteria andSumSizeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_size between", value1, value2, "sumSize");
            return (Criteria) this;
        }

        public Criteria andSumSizeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_size not between", value1, value2, "sumSize");
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

        public Criteria andMarkPriceIsNull() {
            addCriterion("mark_price is null");
            return (Criteria) this;
        }

        public Criteria andMarkPriceIsNotNull() {
            addCriterion("mark_price is not null");
            return (Criteria) this;
        }

        public Criteria andMarkPriceEqualTo(BigDecimal value) {
            addCriterion("mark_price =", value, "markPrice");
            return (Criteria) this;
        }

        public Criteria andMarkPriceNotEqualTo(BigDecimal value) {
            addCriterion("mark_price <>", value, "markPrice");
            return (Criteria) this;
        }

        public Criteria andMarkPriceGreaterThan(BigDecimal value) {
            addCriterion("mark_price >", value, "markPrice");
            return (Criteria) this;
        }

        public Criteria andMarkPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("mark_price >=", value, "markPrice");
            return (Criteria) this;
        }

        public Criteria andMarkPriceLessThan(BigDecimal value) {
            addCriterion("mark_price <", value, "markPrice");
            return (Criteria) this;
        }

        public Criteria andMarkPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("mark_price <=", value, "markPrice");
            return (Criteria) this;
        }

        public Criteria andMarkPriceIn(List<BigDecimal> values) {
            addCriterion("mark_price in", values, "markPrice");
            return (Criteria) this;
        }

        public Criteria andMarkPriceNotIn(List<BigDecimal> values) {
            addCriterion("mark_price not in", values, "markPrice");
            return (Criteria) this;
        }

        public Criteria andMarkPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("mark_price between", value1, value2, "markPrice");
            return (Criteria) this;
        }

        public Criteria andMarkPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("mark_price not between", value1, value2, "markPrice");
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