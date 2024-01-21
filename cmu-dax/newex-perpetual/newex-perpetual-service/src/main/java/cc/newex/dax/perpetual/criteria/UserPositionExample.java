package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户合约持仓数据表 查询条件类
 *
 * @author newex-team
 * @date 2018-11-20 21:08:27
 */
public class UserPositionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserPositionExample() {
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

        public Criteria andQuoteIsNull() {
            addCriterion("quote is null");
            return (Criteria) this;
        }

        public Criteria andQuoteIsNotNull() {
            addCriterion("quote is not null");
            return (Criteria) this;
        }

        public Criteria andQuoteEqualTo(String value) {
            addCriterion("quote =", value, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteNotEqualTo(String value) {
            addCriterion("quote <>", value, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteGreaterThan(String value) {
            addCriterion("quote >", value, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteGreaterThanOrEqualTo(String value) {
            addCriterion("quote >=", value, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteLessThan(String value) {
            addCriterion("quote <", value, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteLessThanOrEqualTo(String value) {
            addCriterion("quote <=", value, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteLike(String value) {
            addCriterion("quote like", value, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteNotLike(String value) {
            addCriterion("quote not like", value, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteIn(List<String> values) {
            addCriterion("quote in", values, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteNotIn(List<String> values) {
            addCriterion("quote not in", values, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteBetween(String value1, String value2) {
            addCriterion("quote between", value1, value2, "quote");
            return (Criteria) this;
        }

        public Criteria andQuoteNotBetween(String value1, String value2) {
            addCriterion("quote not between", value1, value2, "quote");
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

        public Criteria andLeverIsNull() {
            addCriterion("lever is null");
            return (Criteria) this;
        }

        public Criteria andLeverIsNotNull() {
            addCriterion("lever is not null");
            return (Criteria) this;
        }

        public Criteria andLeverEqualTo(BigDecimal value) {
            addCriterion("lever =", value, "lever");
            return (Criteria) this;
        }

        public Criteria andLeverNotEqualTo(BigDecimal value) {
            addCriterion("lever <>", value, "lever");
            return (Criteria) this;
        }

        public Criteria andLeverGreaterThan(BigDecimal value) {
            addCriterion("lever >", value, "lever");
            return (Criteria) this;
        }

        public Criteria andLeverGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lever >=", value, "lever");
            return (Criteria) this;
        }

        public Criteria andLeverLessThan(BigDecimal value) {
            addCriterion("lever <", value, "lever");
            return (Criteria) this;
        }

        public Criteria andLeverLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lever <=", value, "lever");
            return (Criteria) this;
        }

        public Criteria andLeverIn(List<BigDecimal> values) {
            addCriterion("lever in", values, "lever");
            return (Criteria) this;
        }

        public Criteria andLeverNotIn(List<BigDecimal> values) {
            addCriterion("lever not in", values, "lever");
            return (Criteria) this;
        }

        public Criteria andLeverBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lever between", value1, value2, "lever");
            return (Criteria) this;
        }

        public Criteria andLeverNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lever not between", value1, value2, "lever");
            return (Criteria) this;
        }

        public Criteria andGearIsNull() {
            addCriterion("gear is null");
            return (Criteria) this;
        }

        public Criteria andGearIsNotNull() {
            addCriterion("gear is not null");
            return (Criteria) this;
        }

        public Criteria andGearEqualTo(BigDecimal value) {
            addCriterion("gear =", value, "gear");
            return (Criteria) this;
        }

        public Criteria andGearNotEqualTo(BigDecimal value) {
            addCriterion("gear <>", value, "gear");
            return (Criteria) this;
        }

        public Criteria andGearGreaterThan(BigDecimal value) {
            addCriterion("gear >", value, "gear");
            return (Criteria) this;
        }

        public Criteria andGearGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gear >=", value, "gear");
            return (Criteria) this;
        }

        public Criteria andGearLessThan(BigDecimal value) {
            addCriterion("gear <", value, "gear");
            return (Criteria) this;
        }

        public Criteria andGearLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gear <=", value, "gear");
            return (Criteria) this;
        }

        public Criteria andGearIn(List<BigDecimal> values) {
            addCriterion("gear in", values, "gear");
            return (Criteria) this;
        }

        public Criteria andGearNotIn(List<BigDecimal> values) {
            addCriterion("gear not in", values, "gear");
            return (Criteria) this;
        }

        public Criteria andGearBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gear between", value1, value2, "gear");
            return (Criteria) this;
        }

        public Criteria andGearNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gear not between", value1, value2, "gear");
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

        public Criteria andClosingAmountIsNull() {
            addCriterion("closing_amount is null");
            return (Criteria) this;
        }

        public Criteria andClosingAmountIsNotNull() {
            addCriterion("closing_amount is not null");
            return (Criteria) this;
        }

        public Criteria andClosingAmountEqualTo(BigDecimal value) {
            addCriterion("closing_amount =", value, "closingAmount");
            return (Criteria) this;
        }

        public Criteria andClosingAmountNotEqualTo(BigDecimal value) {
            addCriterion("closing_amount <>", value, "closingAmount");
            return (Criteria) this;
        }

        public Criteria andClosingAmountGreaterThan(BigDecimal value) {
            addCriterion("closing_amount >", value, "closingAmount");
            return (Criteria) this;
        }

        public Criteria andClosingAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("closing_amount >=", value, "closingAmount");
            return (Criteria) this;
        }

        public Criteria andClosingAmountLessThan(BigDecimal value) {
            addCriterion("closing_amount <", value, "closingAmount");
            return (Criteria) this;
        }

        public Criteria andClosingAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("closing_amount <=", value, "closingAmount");
            return (Criteria) this;
        }

        public Criteria andClosingAmountIn(List<BigDecimal> values) {
            addCriterion("closing_amount in", values, "closingAmount");
            return (Criteria) this;
        }

        public Criteria andClosingAmountNotIn(List<BigDecimal> values) {
            addCriterion("closing_amount not in", values, "closingAmount");
            return (Criteria) this;
        }

        public Criteria andClosingAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("closing_amount between", value1, value2, "closingAmount");
            return (Criteria) this;
        }

        public Criteria andClosingAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("closing_amount not between", value1, value2, "closingAmount");
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

        public Criteria andMaintenanceMarginIsNull() {
            addCriterion("maintenance_margin is null");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginIsNotNull() {
            addCriterion("maintenance_margin is not null");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginEqualTo(BigDecimal value) {
            addCriterion("maintenance_margin =", value, "maintenanceMargin");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginNotEqualTo(BigDecimal value) {
            addCriterion("maintenance_margin <>", value, "maintenanceMargin");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginGreaterThan(BigDecimal value) {
            addCriterion("maintenance_margin >", value, "maintenanceMargin");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("maintenance_margin >=", value, "maintenanceMargin");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginLessThan(BigDecimal value) {
            addCriterion("maintenance_margin <", value, "maintenanceMargin");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginLessThanOrEqualTo(BigDecimal value) {
            addCriterion("maintenance_margin <=", value, "maintenanceMargin");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginIn(List<BigDecimal> values) {
            addCriterion("maintenance_margin in", values, "maintenanceMargin");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginNotIn(List<BigDecimal> values) {
            addCriterion("maintenance_margin not in", values, "maintenanceMargin");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("maintenance_margin between", value1, value2, "maintenanceMargin");
            return (Criteria) this;
        }

        public Criteria andMaintenanceMarginNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("maintenance_margin not between", value1, value2, "maintenanceMargin");
            return (Criteria) this;
        }

        public Criteria andFeeIsNull() {
            addCriterion("fee is null");
            return (Criteria) this;
        }

        public Criteria andFeeIsNotNull() {
            addCriterion("fee is not null");
            return (Criteria) this;
        }

        public Criteria andFeeEqualTo(BigDecimal value) {
            addCriterion("fee =", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotEqualTo(BigDecimal value) {
            addCriterion("fee <>", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThan(BigDecimal value) {
            addCriterion("fee >", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fee >=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThan(BigDecimal value) {
            addCriterion("fee <", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fee <=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeIn(List<BigDecimal> values) {
            addCriterion("fee in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotIn(List<BigDecimal> values) {
            addCriterion("fee not in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee not between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andSizeIsNull() {
            addCriterion("size is null");
            return (Criteria) this;
        }

        public Criteria andSizeIsNotNull() {
            addCriterion("size is not null");
            return (Criteria) this;
        }

        public Criteria andSizeEqualTo(BigDecimal value) {
            addCriterion("size =", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotEqualTo(BigDecimal value) {
            addCriterion("size <>", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThan(BigDecimal value) {
            addCriterion("size >", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("size >=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThan(BigDecimal value) {
            addCriterion("size <", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("size <=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeIn(List<BigDecimal> values) {
            addCriterion("size in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotIn(List<BigDecimal> values) {
            addCriterion("size not in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("size between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("size not between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceIsNull() {
            addCriterion("pre_liqudate_price is null");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceIsNotNull() {
            addCriterion("pre_liqudate_price is not null");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceEqualTo(BigDecimal value) {
            addCriterion("pre_liqudate_price =", value, "preLiqudatePrice");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceNotEqualTo(BigDecimal value) {
            addCriterion("pre_liqudate_price <>", value, "preLiqudatePrice");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceGreaterThan(BigDecimal value) {
            addCriterion("pre_liqudate_price >", value, "preLiqudatePrice");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_liqudate_price >=", value, "preLiqudatePrice");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceLessThan(BigDecimal value) {
            addCriterion("pre_liqudate_price <", value, "preLiqudatePrice");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_liqudate_price <=", value, "preLiqudatePrice");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceIn(List<BigDecimal> values) {
            addCriterion("pre_liqudate_price in", values, "preLiqudatePrice");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceNotIn(List<BigDecimal> values) {
            addCriterion("pre_liqudate_price not in", values, "preLiqudatePrice");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_liqudate_price between", value1, value2, "preLiqudatePrice");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_liqudate_price not between", value1, value2, "preLiqudatePrice");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceIsNull() {
            addCriterion("liqudate_price is null");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceIsNotNull() {
            addCriterion("liqudate_price is not null");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceEqualTo(BigDecimal value) {
            addCriterion("liqudate_price =", value, "liqudatePrice");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceNotEqualTo(BigDecimal value) {
            addCriterion("liqudate_price <>", value, "liqudatePrice");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceGreaterThan(BigDecimal value) {
            addCriterion("liqudate_price >", value, "liqudatePrice");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("liqudate_price >=", value, "liqudatePrice");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceLessThan(BigDecimal value) {
            addCriterion("liqudate_price <", value, "liqudatePrice");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("liqudate_price <=", value, "liqudatePrice");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceIn(List<BigDecimal> values) {
            addCriterion("liqudate_price in", values, "liqudatePrice");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceNotIn(List<BigDecimal> values) {
            addCriterion("liqudate_price not in", values, "liqudatePrice");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liqudate_price between", value1, value2, "liqudatePrice");
            return (Criteria) this;
        }

        public Criteria andLiqudatePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liqudate_price not between", value1, value2, "liqudatePrice");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceIsNull() {
            addCriterion("broker_price is null");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceIsNotNull() {
            addCriterion("broker_price is not null");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceEqualTo(BigDecimal value) {
            addCriterion("broker_price =", value, "brokerPrice");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceNotEqualTo(BigDecimal value) {
            addCriterion("broker_price <>", value, "brokerPrice");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceGreaterThan(BigDecimal value) {
            addCriterion("broker_price >", value, "brokerPrice");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("broker_price >=", value, "brokerPrice");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceLessThan(BigDecimal value) {
            addCriterion("broker_price <", value, "brokerPrice");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("broker_price <=", value, "brokerPrice");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceIn(List<BigDecimal> values) {
            addCriterion("broker_price in", values, "brokerPrice");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceNotIn(List<BigDecimal> values) {
            addCriterion("broker_price not in", values, "brokerPrice");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("broker_price between", value1, value2, "brokerPrice");
            return (Criteria) this;
        }

        public Criteria andBrokerPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("broker_price not between", value1, value2, "brokerPrice");
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

        public Criteria andOrderLongAmountIsNull() {
            addCriterion("order_long_amount is null");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountIsNotNull() {
            addCriterion("order_long_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountEqualTo(BigDecimal value) {
            addCriterion("order_long_amount =", value, "orderLongAmount");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountNotEqualTo(BigDecimal value) {
            addCriterion("order_long_amount <>", value, "orderLongAmount");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountGreaterThan(BigDecimal value) {
            addCriterion("order_long_amount >", value, "orderLongAmount");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_long_amount >=", value, "orderLongAmount");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountLessThan(BigDecimal value) {
            addCriterion("order_long_amount <", value, "orderLongAmount");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_long_amount <=", value, "orderLongAmount");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountIn(List<BigDecimal> values) {
            addCriterion("order_long_amount in", values, "orderLongAmount");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountNotIn(List<BigDecimal> values) {
            addCriterion("order_long_amount not in", values, "orderLongAmount");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_long_amount between", value1, value2, "orderLongAmount");
            return (Criteria) this;
        }

        public Criteria andOrderLongAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_long_amount not between", value1, value2, "orderLongAmount");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountIsNull() {
            addCriterion("order_short_amount is null");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountIsNotNull() {
            addCriterion("order_short_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountEqualTo(BigDecimal value) {
            addCriterion("order_short_amount =", value, "orderShortAmount");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountNotEqualTo(BigDecimal value) {
            addCriterion("order_short_amount <>", value, "orderShortAmount");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountGreaterThan(BigDecimal value) {
            addCriterion("order_short_amount >", value, "orderShortAmount");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_short_amount >=", value, "orderShortAmount");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountLessThan(BigDecimal value) {
            addCriterion("order_short_amount <", value, "orderShortAmount");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_short_amount <=", value, "orderShortAmount");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountIn(List<BigDecimal> values) {
            addCriterion("order_short_amount in", values, "orderShortAmount");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountNotIn(List<BigDecimal> values) {
            addCriterion("order_short_amount not in", values, "orderShortAmount");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_short_amount between", value1, value2, "orderShortAmount");
            return (Criteria) this;
        }

        public Criteria andOrderShortAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_short_amount not between", value1, value2, "orderShortAmount");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeIsNull() {
            addCriterion("order_long_size is null");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeIsNotNull() {
            addCriterion("order_long_size is not null");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeEqualTo(BigDecimal value) {
            addCriterion("order_long_size =", value, "orderLongSize");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeNotEqualTo(BigDecimal value) {
            addCriterion("order_long_size <>", value, "orderLongSize");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeGreaterThan(BigDecimal value) {
            addCriterion("order_long_size >", value, "orderLongSize");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_long_size >=", value, "orderLongSize");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeLessThan(BigDecimal value) {
            addCriterion("order_long_size <", value, "orderLongSize");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_long_size <=", value, "orderLongSize");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeIn(List<BigDecimal> values) {
            addCriterion("order_long_size in", values, "orderLongSize");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeNotIn(List<BigDecimal> values) {
            addCriterion("order_long_size not in", values, "orderLongSize");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_long_size between", value1, value2, "orderLongSize");
            return (Criteria) this;
        }

        public Criteria andOrderLongSizeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_long_size not between", value1, value2, "orderLongSize");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeIsNull() {
            addCriterion("order_short_size is null");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeIsNotNull() {
            addCriterion("order_short_size is not null");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeEqualTo(BigDecimal value) {
            addCriterion("order_short_size =", value, "orderShortSize");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeNotEqualTo(BigDecimal value) {
            addCriterion("order_short_size <>", value, "orderShortSize");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeGreaterThan(BigDecimal value) {
            addCriterion("order_short_size >", value, "orderShortSize");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_short_size >=", value, "orderShortSize");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeLessThan(BigDecimal value) {
            addCriterion("order_short_size <", value, "orderShortSize");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_short_size <=", value, "orderShortSize");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeIn(List<BigDecimal> values) {
            addCriterion("order_short_size in", values, "orderShortSize");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeNotIn(List<BigDecimal> values) {
            addCriterion("order_short_size not in", values, "orderShortSize");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_short_size between", value1, value2, "orderShortSize");
            return (Criteria) this;
        }

        public Criteria andOrderShortSizeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_short_size not between", value1, value2, "orderShortSize");
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