package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单归档表 查询条件类
 *
 * @author newex-team
 * @date 2018-09-26 17:27:27
 */
public class HistoryOrdersExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HistoryOrdersExample() {
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

        public Criteria andIdEqualTo(final Long value) {
            this.addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(final Long value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(final Long value) {
            this.addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(final Long value) {
            this.addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(final Long value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(final List<Long> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(final List<Long> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(final Long value1, final Long value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(final Long value1, final Long value2) {
            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            this.addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            this.addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(final Long value) {
            this.addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(final Long value) {
            this.addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(final Long value) {
            this.addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(final Long value) {
            this.addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(final Long value) {
            this.addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(final List<Long> values) {
            this.addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(final List<Long> values) {
            this.addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(final Long value1, final Long value2) {
            this.addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(final Long value1, final Long value2) {
            this.addCriterion("user_id not between", value1, value2, "userId");
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

        public Criteria andBrokerIdEqualTo(final Integer value) {
            this.addCriterion("broker_id =", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotEqualTo(final Integer value) {
            this.addCriterion("broker_id <>", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThan(final Integer value) {
            this.addCriterion("broker_id >", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("broker_id >=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThan(final Integer value) {
            this.addCriterion("broker_id <", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("broker_id <=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIn(final List<Integer> values) {
            this.addCriterion("broker_id in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotIn(final List<Integer> values) {
            this.addCriterion("broker_id not in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("broker_id between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("broker_id not between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andPairCodeIsNull() {
            this.addCriterion("pair_code is null");
            return (Criteria) this;
        }

        public Criteria andPairCodeIsNotNull() {
            this.addCriterion("pair_code is not null");
            return (Criteria) this;
        }

        public Criteria andPairCodeEqualTo(final String value) {
            this.addCriterion("pair_code =", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeNotEqualTo(final String value) {
            this.addCriterion("pair_code <>", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeGreaterThan(final String value) {
            this.addCriterion("pair_code >", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("pair_code >=", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeLessThan(final String value) {
            this.addCriterion("pair_code <", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeLessThanOrEqualTo(final String value) {
            this.addCriterion("pair_code <=", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeLike(final String value) {
            this.addCriterion("pair_code like", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeNotLike(final String value) {
            this.addCriterion("pair_code not like", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeIn(final List<String> values) {
            this.addCriterion("pair_code in", values, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeNotIn(final List<String> values) {
            this.addCriterion("pair_code not in", values, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeBetween(final String value1, final String value2) {
            this.addCriterion("pair_code between", value1, value2, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeNotBetween(final String value1, final String value2) {
            this.addCriterion("pair_code not between", value1, value2, "pairCode");
            return (Criteria) this;
        }

        public Criteria andClazzIsNull() {
            this.addCriterion("clazz is null");
            return (Criteria) this;
        }

        public Criteria andClazzIsNotNull() {
            this.addCriterion("clazz is not null");
            return (Criteria) this;
        }

        public Criteria andClazzEqualTo(final Integer value) {
            this.addCriterion("clazz =", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzNotEqualTo(final Integer value) {
            this.addCriterion("clazz <>", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzGreaterThan(final Integer value) {
            this.addCriterion("clazz >", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("clazz >=", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzLessThan(final Integer value) {
            this.addCriterion("clazz <", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzLessThanOrEqualTo(final Integer value) {
            this.addCriterion("clazz <=", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzIn(final List<Integer> values) {
            this.addCriterion("clazz in", values, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzNotIn(final List<Integer> values) {
            this.addCriterion("clazz not in", values, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzBetween(final Integer value1, final Integer value2) {
            this.addCriterion("clazz between", value1, value2, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("clazz not between", value1, value2, "clazz");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            this.addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            this.addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(final Integer value) {
            this.addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(final Integer value) {
            this.addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(final Integer value) {
            this.addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(final Integer value) {
            this.addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(final List<Integer> values) {
            this.addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(final List<Integer> values) {
            this.addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            this.addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            this.addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(final BigDecimal value) {
            this.addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(final BigDecimal value) {
            this.addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(final BigDecimal value) {
            this.addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(final BigDecimal value) {
            this.addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(final List<BigDecimal> values) {
            this.addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(final List<BigDecimal> values) {
            this.addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountIsNull() {
            this.addCriterion("unit_amount is null");
            return (Criteria) this;
        }

        public Criteria andUnitAmountIsNotNull() {
            this.addCriterion("unit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andUnitAmountEqualTo(final BigDecimal value) {
            this.addCriterion("unit_amount =", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountNotEqualTo(final BigDecimal value) {
            this.addCriterion("unit_amount <>", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountGreaterThan(final BigDecimal value) {
            this.addCriterion("unit_amount >", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("unit_amount >=", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountLessThan(final BigDecimal value) {
            this.addCriterion("unit_amount <", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("unit_amount <=", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountIn(final List<BigDecimal> values) {
            this.addCriterion("unit_amount in", values, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountNotIn(final List<BigDecimal> values) {
            this.addCriterion("unit_amount not in", values, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("unit_amount between", value1, value2, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("unit_amount not between", value1, value2, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIsNull() {
            this.addCriterion("order_price is null");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIsNotNull() {
            this.addCriterion("order_price is not null");
            return (Criteria) this;
        }

        public Criteria andOrderPriceEqualTo(final BigDecimal value) {
            this.addCriterion("order_price =", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotEqualTo(final BigDecimal value) {
            this.addCriterion("order_price <>", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceGreaterThan(final BigDecimal value) {
            this.addCriterion("order_price >", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("order_price >=", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceLessThan(final BigDecimal value) {
            this.addCriterion("order_price <", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("order_price <=", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIn(final List<BigDecimal> values) {
            this.addCriterion("order_price in", values, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotIn(final List<BigDecimal> values) {
            this.addCriterion("order_price not in", values, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("order_price between", value1, value2, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("order_price not between", value1, value2, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgIsNull() {
            this.addCriterion("deal_price_avg is null");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgIsNotNull() {
            this.addCriterion("deal_price_avg is not null");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgEqualTo(final BigDecimal value) {
            this.addCriterion("deal_price_avg =", value, "dealPriceAvg");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgNotEqualTo(final BigDecimal value) {
            this.addCriterion("deal_price_avg <>", value, "dealPriceAvg");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgGreaterThan(final BigDecimal value) {
            this.addCriterion("deal_price_avg >", value, "dealPriceAvg");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("deal_price_avg >=", value, "dealPriceAvg");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgLessThan(final BigDecimal value) {
            this.addCriterion("deal_price_avg <", value, "dealPriceAvg");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("deal_price_avg <=", value, "dealPriceAvg");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgIn(final List<BigDecimal> values) {
            this.addCriterion("deal_price_avg in", values, "dealPriceAvg");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgNotIn(final List<BigDecimal> values) {
            this.addCriterion("deal_price_avg not in", values, "dealPriceAvg");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("deal_price_avg between", value1, value2, "dealPriceAvg");
            return (Criteria) this;
        }

        public Criteria andDealPriceAvgNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("deal_price_avg not between", value1, value2, "dealPriceAvg");
            return (Criteria) this;
        }

        public Criteria andDealAmountIsNull() {
            this.addCriterion("deal_amount is null");
            return (Criteria) this;
        }

        public Criteria andDealAmountIsNotNull() {
            this.addCriterion("deal_amount is not null");
            return (Criteria) this;
        }

        public Criteria andDealAmountEqualTo(final BigDecimal value) {
            this.addCriterion("deal_amount =", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountNotEqualTo(final BigDecimal value) {
            this.addCriterion("deal_amount <>", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountGreaterThan(final BigDecimal value) {
            this.addCriterion("deal_amount >", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("deal_amount >=", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountLessThan(final BigDecimal value) {
            this.addCriterion("deal_amount <", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("deal_amount <=", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountIn(final List<BigDecimal> values) {
            this.addCriterion("deal_amount in", values, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountNotIn(final List<BigDecimal> values) {
            this.addCriterion("deal_amount not in", values, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("deal_amount between", value1, value2, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("deal_amount not between", value1, value2, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andFeeRateIsNull() {
            this.addCriterion("fee_rate is null");
            return (Criteria) this;
        }

        public Criteria andFeeRateIsNotNull() {
            this.addCriterion("fee_rate is not null");
            return (Criteria) this;
        }

        public Criteria andFeeRateEqualTo(final BigDecimal value) {
            this.addCriterion("fee_rate =", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateNotEqualTo(final BigDecimal value) {
            this.addCriterion("fee_rate <>", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateGreaterThan(final BigDecimal value) {
            this.addCriterion("fee_rate >", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("fee_rate >=", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateLessThan(final BigDecimal value) {
            this.addCriterion("fee_rate <", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("fee_rate <=", value, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateIn(final List<BigDecimal> values) {
            this.addCriterion("fee_rate in", values, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateNotIn(final List<BigDecimal> values) {
            this.addCriterion("fee_rate not in", values, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("fee_rate between", value1, value2, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeRateNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("fee_rate not between", value1, value2, "feeRate");
            return (Criteria) this;
        }

        public Criteria andFeeIsNull() {
            this.addCriterion("fee is null");
            return (Criteria) this;
        }

        public Criteria andFeeIsNotNull() {
            this.addCriterion("fee is not null");
            return (Criteria) this;
        }

        public Criteria andFeeEqualTo(final BigDecimal value) {
            this.addCriterion("fee =", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotEqualTo(final BigDecimal value) {
            this.addCriterion("fee <>", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThan(final BigDecimal value) {
            this.addCriterion("fee >", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("fee >=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThan(final BigDecimal value) {
            this.addCriterion("fee <", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("fee <=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeIn(final List<BigDecimal> values) {
            this.addCriterion("fee in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotIn(final List<BigDecimal> values) {
            this.addCriterion("fee not in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("fee between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("fee not between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andLeverageIsNull() {
            this.addCriterion("leverage is null");
            return (Criteria) this;
        }

        public Criteria andLeverageIsNotNull() {
            this.addCriterion("leverage is not null");
            return (Criteria) this;
        }

        public Criteria andLeverageEqualTo(final Integer value) {
            this.addCriterion("leverage =", value, "leverage");
            return (Criteria) this;
        }

        public Criteria andLeverageNotEqualTo(final Integer value) {
            this.addCriterion("leverage <>", value, "leverage");
            return (Criteria) this;
        }

        public Criteria andLeverageGreaterThan(final Integer value) {
            this.addCriterion("leverage >", value, "leverage");
            return (Criteria) this;
        }

        public Criteria andLeverageGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("leverage >=", value, "leverage");
            return (Criteria) this;
        }

        public Criteria andLeverageLessThan(final Integer value) {
            this.addCriterion("leverage <", value, "leverage");
            return (Criteria) this;
        }

        public Criteria andLeverageLessThanOrEqualTo(final Integer value) {
            this.addCriterion("leverage <=", value, "leverage");
            return (Criteria) this;
        }

        public Criteria andLeverageIn(final List<Integer> values) {
            this.addCriterion("leverage in", values, "leverage");
            return (Criteria) this;
        }

        public Criteria andLeverageNotIn(final List<Integer> values) {
            this.addCriterion("leverage not in", values, "leverage");
            return (Criteria) this;
        }

        public Criteria andLeverageBetween(final Integer value1, final Integer value2) {
            this.addCriterion("leverage between", value1, value2, "leverage");
            return (Criteria) this;
        }

        public Criteria andLeverageNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("leverage not between", value1, value2, "leverage");
            return (Criteria) this;
        }

        public Criteria andOperateStatusIsNull() {
            this.addCriterion("operate_status is null");
            return (Criteria) this;
        }

        public Criteria andOperateStatusIsNotNull() {
            this.addCriterion("operate_status is not null");
            return (Criteria) this;
        }

        public Criteria andOperateStatusEqualTo(final Integer value) {
            this.addCriterion("operate_status =", value, "operateStatus");
            return (Criteria) this;
        }

        public Criteria andOperateStatusNotEqualTo(final Integer value) {
            this.addCriterion("operate_status <>", value, "operateStatus");
            return (Criteria) this;
        }

        public Criteria andOperateStatusGreaterThan(final Integer value) {
            this.addCriterion("operate_status >", value, "operateStatus");
            return (Criteria) this;
        }

        public Criteria andOperateStatusGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("operate_status >=", value, "operateStatus");
            return (Criteria) this;
        }

        public Criteria andOperateStatusLessThan(final Integer value) {
            this.addCriterion("operate_status <", value, "operateStatus");
            return (Criteria) this;
        }

        public Criteria andOperateStatusLessThanOrEqualTo(final Integer value) {
            this.addCriterion("operate_status <=", value, "operateStatus");
            return (Criteria) this;
        }

        public Criteria andOperateStatusIn(final List<Integer> values) {
            this.addCriterion("operate_status in", values, "operateStatus");
            return (Criteria) this;
        }

        public Criteria andOperateStatusNotIn(final List<Integer> values) {
            this.addCriterion("operate_status not in", values, "operateStatus");
            return (Criteria) this;
        }

        public Criteria andOperateStatusBetween(final Integer value1, final Integer value2) {
            this.addCriterion("operate_status between", value1, value2, "operateStatus");
            return (Criteria) this;
        }

        public Criteria andOperateStatusNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("operate_status not between", value1, value2, "operateStatus");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            this.addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            this.addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(final Integer value) {
            this.addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(final Integer value) {
            this.addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(final Integer value) {
            this.addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(final Integer value) {
            this.addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(final Integer value) {
            this.addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(final List<Integer> values) {
            this.addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(final List<Integer> values) {
            this.addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(final Integer value1, final Integer value2) {
            this.addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andOrderFromIsNull() {
            this.addCriterion("order_from is null");
            return (Criteria) this;
        }

        public Criteria andOrderFromIsNotNull() {
            this.addCriterion("order_from is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFromEqualTo(final Integer value) {
            this.addCriterion("order_from =", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromNotEqualTo(final Integer value) {
            this.addCriterion("order_from <>", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromGreaterThan(final Integer value) {
            this.addCriterion("order_from >", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("order_from >=", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromLessThan(final Integer value) {
            this.addCriterion("order_from <", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromLessThanOrEqualTo(final Integer value) {
            this.addCriterion("order_from <=", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromIn(final List<Integer> values) {
            this.addCriterion("order_from in", values, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromNotIn(final List<Integer> values) {
            this.addCriterion("order_from not in", values, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromBetween(final Integer value1, final Integer value2) {
            this.addCriterion("order_from between", value1, value2, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("order_from not between", value1, value2, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andSystemTypeIsNull() {
            this.addCriterion("system_type is null");
            return (Criteria) this;
        }

        public Criteria andSystemTypeIsNotNull() {
            this.addCriterion("system_type is not null");
            return (Criteria) this;
        }

        public Criteria andSystemTypeEqualTo(final Integer value) {
            this.addCriterion("system_type =", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeNotEqualTo(final Integer value) {
            this.addCriterion("system_type <>", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeGreaterThan(final Integer value) {
            this.addCriterion("system_type >", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("system_type >=", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeLessThan(final Integer value) {
            this.addCriterion("system_type <", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("system_type <=", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeIn(final List<Integer> values) {
            this.addCriterion("system_type in", values, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeNotIn(final List<Integer> values) {
            this.addCriterion("system_type not in", values, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("system_type between", value1, value2, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("system_type not between", value1, value2, "systemType");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdIsNull() {
            this.addCriterion("relation_order_id is null");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdIsNotNull() {
            this.addCriterion("relation_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdEqualTo(final Long value) {
            this.addCriterion("relation_order_id =", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdNotEqualTo(final Long value) {
            this.addCriterion("relation_order_id <>", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdGreaterThan(final Long value) {
            this.addCriterion("relation_order_id >", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("relation_order_id >=", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdLessThan(final Long value) {
            this.addCriterion("relation_order_id <", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdLessThanOrEqualTo(final Long value) {
            this.addCriterion("relation_order_id <=", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdIn(final List<Long> values) {
            this.addCriterion("relation_order_id in", values, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdNotIn(final List<Long> values) {
            this.addCriterion("relation_order_id not in", values, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdBetween(final Long value1, final Long value2) {
            this.addCriterion("relation_order_id between", value1, value2, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdNotBetween(final Long value1, final Long value2) {
            this.addCriterion("relation_order_id not between", value1, value2, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            this.addCriterion("created_date is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            this.addCriterion("created_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(final Date value) {
            this.addCriterion("created_date =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(final Date value) {
            this.addCriterion("created_date <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(final Date value) {
            this.addCriterion("created_date >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("created_date >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(final Date value) {
            this.addCriterion("created_date <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(final Date value) {
            this.addCriterion("created_date <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(final List<Date> values) {
            this.addCriterion("created_date in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(final List<Date> values) {
            this.addCriterion("created_date not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(final Date value1, final Date value2) {
            this.addCriterion("created_date between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(final Date value1, final Date value2) {
            this.addCriterion("created_date not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNull() {
            this.addCriterion("modify_date is null");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNotNull() {
            this.addCriterion("modify_date is not null");
            return (Criteria) this;
        }

        public Criteria andModifyDateEqualTo(final Date value) {
            this.addCriterion("modify_date =", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotEqualTo(final Date value) {
            this.addCriterion("modify_date <>", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThan(final Date value) {
            this.addCriterion("modify_date >", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("modify_date >=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThan(final Date value) {
            this.addCriterion("modify_date <", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThanOrEqualTo(final Date value) {
            this.addCriterion("modify_date <=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateIn(final List<Date> values) {
            this.addCriterion("modify_date in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotIn(final List<Date> values) {
            this.addCriterion("modify_date not in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateBetween(final Date value1, final Date value2) {
            this.addCriterion("modify_date between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotBetween(final Date value1, final Date value2) {
            this.addCriterion("modify_date not between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andFieldIsNull(final String fieldName) {
            this.addCriterion(fieldName + " is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull(final String fieldName) {
            this.addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " =", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <>", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " >", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " >=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLike(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " not like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldIn(final String fieldName, final List<Object> fieldValues) {
            this.addCriterion(fieldName + " in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(final String fieldName, final List<Object> fieldValues) {
            this.addCriterion(fieldName + " not in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            this.addCriterion(fieldName + " between", fieldValue1, fieldValue2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
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