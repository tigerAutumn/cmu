package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预爆仓备份表(不会被任务删除) 查询条件类
 *
 * @author newex-team
 * @date 2018-11-20 18:26:46
 */
public class HistoryLiquidateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HistoryLiquidateExample() {
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

        public Criteria andBeforePositionQuantityIsNull() {
            addCriterion("before_position_quantity is null");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityIsNotNull() {
            addCriterion("before_position_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityEqualTo(BigDecimal value) {
            addCriterion("before_position_quantity =", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityNotEqualTo(BigDecimal value) {
            addCriterion("before_position_quantity <>", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityGreaterThan(BigDecimal value) {
            addCriterion("before_position_quantity >", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("before_position_quantity >=", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityLessThan(BigDecimal value) {
            addCriterion("before_position_quantity <", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("before_position_quantity <=", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityIn(List<BigDecimal> values) {
            addCriterion("before_position_quantity in", values, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityNotIn(List<BigDecimal> values) {
            addCriterion("before_position_quantity not in", values, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_position_quantity between", value1, value2, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_position_quantity not between", value1, value2, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityIsNull() {
            addCriterion("after_position_quantity is null");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityIsNotNull() {
            addCriterion("after_position_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityEqualTo(BigDecimal value) {
            addCriterion("after_position_quantity =", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityNotEqualTo(BigDecimal value) {
            addCriterion("after_position_quantity <>", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityGreaterThan(BigDecimal value) {
            addCriterion("after_position_quantity >", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("after_position_quantity >=", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityLessThan(BigDecimal value) {
            addCriterion("after_position_quantity <", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("after_position_quantity <=", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityIn(List<BigDecimal> values) {
            addCriterion("after_position_quantity in", values, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityNotIn(List<BigDecimal> values) {
            addCriterion("after_position_quantity not in", values, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_position_quantity between", value1, value2, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_position_quantity not between", value1, value2, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityIsNull() {
            addCriterion("close_position_quantity is null");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityIsNotNull() {
            addCriterion("close_position_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityEqualTo(BigDecimal value) {
            addCriterion("close_position_quantity =", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityNotEqualTo(BigDecimal value) {
            addCriterion("close_position_quantity <>", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityGreaterThan(BigDecimal value) {
            addCriterion("close_position_quantity >", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("close_position_quantity >=", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityLessThan(BigDecimal value) {
            addCriterion("close_position_quantity <", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("close_position_quantity <=", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityIn(List<BigDecimal> values) {
            addCriterion("close_position_quantity in", values, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityNotIn(List<BigDecimal> values) {
            addCriterion("close_position_quantity not in", values, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_position_quantity between", value1, value2, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_position_quantity not between", value1, value2, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andMarketPriceIsNull() {
            addCriterion("market_price is null");
            return (Criteria) this;
        }

        public Criteria andMarketPriceIsNotNull() {
            addCriterion("market_price is not null");
            return (Criteria) this;
        }

        public Criteria andMarketPriceEqualTo(BigDecimal value) {
            addCriterion("market_price =", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceNotEqualTo(BigDecimal value) {
            addCriterion("market_price <>", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceGreaterThan(BigDecimal value) {
            addCriterion("market_price >", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("market_price >=", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceLessThan(BigDecimal value) {
            addCriterion("market_price <", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("market_price <=", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceIn(List<BigDecimal> values) {
            addCriterion("market_price in", values, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceNotIn(List<BigDecimal> values) {
            addCriterion("market_price not in", values, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("market_price between", value1, value2, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("market_price not between", value1, value2, "marketPrice");
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Long value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Long value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Long value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Long value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Long> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Long> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Long value1, Long value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
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