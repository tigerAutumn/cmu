package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 合约币对 查询条件类
 *
 * @author newex-team
 * @date 2019-01-10 14:48:21
 */
public class CurrencyPairExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CurrencyPairExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBizIsNull() {
            addCriterion("biz is null");
            return (Criteria) this;
        }

        public Criteria andBizIsNotNull() {
            addCriterion("biz is not null");
            return (Criteria) this;
        }

        public Criteria andBizEqualTo(String value) {
            addCriterion("biz =", value, "biz");
            return (Criteria) this;
        }

        public Criteria andBizNotEqualTo(String value) {
            addCriterion("biz <>", value, "biz");
            return (Criteria) this;
        }

        public Criteria andBizGreaterThan(String value) {
            addCriterion("biz >", value, "biz");
            return (Criteria) this;
        }

        public Criteria andBizGreaterThanOrEqualTo(String value) {
            addCriterion("biz >=", value, "biz");
            return (Criteria) this;
        }

        public Criteria andBizLessThan(String value) {
            addCriterion("biz <", value, "biz");
            return (Criteria) this;
        }

        public Criteria andBizLessThanOrEqualTo(String value) {
            addCriterion("biz <=", value, "biz");
            return (Criteria) this;
        }

        public Criteria andBizLike(String value) {
            addCriterion("biz like", value, "biz");
            return (Criteria) this;
        }

        public Criteria andBizNotLike(String value) {
            addCriterion("biz not like", value, "biz");
            return (Criteria) this;
        }

        public Criteria andBizIn(List<String> values) {
            addCriterion("biz in", values, "biz");
            return (Criteria) this;
        }

        public Criteria andBizNotIn(List<String> values) {
            addCriterion("biz not in", values, "biz");
            return (Criteria) this;
        }

        public Criteria andBizBetween(String value1, String value2) {
            addCriterion("biz between", value1, value2, "biz");
            return (Criteria) this;
        }

        public Criteria andBizNotBetween(String value1, String value2) {
            addCriterion("biz not between", value1, value2, "biz");
            return (Criteria) this;
        }

        public Criteria andIndexBaseIsNull() {
            addCriterion("index_base is null");
            return (Criteria) this;
        }

        public Criteria andIndexBaseIsNotNull() {
            addCriterion("index_base is not null");
            return (Criteria) this;
        }

        public Criteria andIndexBaseEqualTo(String value) {
            addCriterion("index_base =", value, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseNotEqualTo(String value) {
            addCriterion("index_base <>", value, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseGreaterThan(String value) {
            addCriterion("index_base >", value, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseGreaterThanOrEqualTo(String value) {
            addCriterion("index_base >=", value, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseLessThan(String value) {
            addCriterion("index_base <", value, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseLessThanOrEqualTo(String value) {
            addCriterion("index_base <=", value, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseLike(String value) {
            addCriterion("index_base like", value, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseNotLike(String value) {
            addCriterion("index_base not like", value, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseIn(List<String> values) {
            addCriterion("index_base in", values, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseNotIn(List<String> values) {
            addCriterion("index_base not in", values, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseBetween(String value1, String value2) {
            addCriterion("index_base between", value1, value2, "indexBase");
            return (Criteria) this;
        }

        public Criteria andIndexBaseNotBetween(String value1, String value2) {
            addCriterion("index_base not between", value1, value2, "indexBase");
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

        public Criteria andDirectionIsNull() {
            addCriterion("direction is null");
            return (Criteria) this;
        }

        public Criteria andDirectionIsNotNull() {
            addCriterion("direction is not null");
            return (Criteria) this;
        }

        public Criteria andDirectionEqualTo(Integer value) {
            addCriterion("direction =", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotEqualTo(Integer value) {
            addCriterion("direction <>", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionGreaterThan(Integer value) {
            addCriterion("direction >", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionGreaterThanOrEqualTo(Integer value) {
            addCriterion("direction >=", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLessThan(Integer value) {
            addCriterion("direction <", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLessThanOrEqualTo(Integer value) {
            addCriterion("direction <=", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionIn(List<Integer> values) {
            addCriterion("direction in", values, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotIn(List<Integer> values) {
            addCriterion("direction not in", values, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionBetween(Integer value1, Integer value2) {
            addCriterion("direction between", value1, value2, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotBetween(Integer value1, Integer value2) {
            addCriterion("direction not between", value1, value2, "direction");
            return (Criteria) this;
        }

        public Criteria andPairCodeIsNull() {
            addCriterion("pair_code is null");
            return (Criteria) this;
        }

        public Criteria andPairCodeIsNotNull() {
            addCriterion("pair_code is not null");
            return (Criteria) this;
        }

        public Criteria andPairCodeEqualTo(String value) {
            addCriterion("pair_code =", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeNotEqualTo(String value) {
            addCriterion("pair_code <>", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeGreaterThan(String value) {
            addCriterion("pair_code >", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeGreaterThanOrEqualTo(String value) {
            addCriterion("pair_code >=", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeLessThan(String value) {
            addCriterion("pair_code <", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeLessThanOrEqualTo(String value) {
            addCriterion("pair_code <=", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeLike(String value) {
            addCriterion("pair_code like", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeNotLike(String value) {
            addCriterion("pair_code not like", value, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeIn(List<String> values) {
            addCriterion("pair_code in", values, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeNotIn(List<String> values) {
            addCriterion("pair_code not in", values, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeBetween(String value1, String value2) {
            addCriterion("pair_code between", value1, value2, "pairCode");
            return (Criteria) this;
        }

        public Criteria andPairCodeNotBetween(String value1, String value2) {
            addCriterion("pair_code not between", value1, value2, "pairCode");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountIsNull() {
            addCriterion("insurance_account is null");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountIsNotNull() {
            addCriterion("insurance_account is not null");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountEqualTo(Long value) {
            addCriterion("insurance_account =", value, "insuranceAccount");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountNotEqualTo(Long value) {
            addCriterion("insurance_account <>", value, "insuranceAccount");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountGreaterThan(Long value) {
            addCriterion("insurance_account >", value, "insuranceAccount");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountGreaterThanOrEqualTo(Long value) {
            addCriterion("insurance_account >=", value, "insuranceAccount");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountLessThan(Long value) {
            addCriterion("insurance_account <", value, "insuranceAccount");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountLessThanOrEqualTo(Long value) {
            addCriterion("insurance_account <=", value, "insuranceAccount");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountIn(List<Long> values) {
            addCriterion("insurance_account in", values, "insuranceAccount");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountNotIn(List<Long> values) {
            addCriterion("insurance_account not in", values, "insuranceAccount");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountBetween(Long value1, Long value2) {
            addCriterion("insurance_account between", value1, value2, "insuranceAccount");
            return (Criteria) this;
        }

        public Criteria andInsuranceAccountNotBetween(Long value1, Long value2) {
            addCriterion("insurance_account not between", value1, value2, "insuranceAccount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountIsNull() {
            addCriterion("unit_amount is null");
            return (Criteria) this;
        }

        public Criteria andUnitAmountIsNotNull() {
            addCriterion("unit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andUnitAmountEqualTo(BigDecimal value) {
            addCriterion("unit_amount =", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountNotEqualTo(BigDecimal value) {
            addCriterion("unit_amount <>", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountGreaterThan(BigDecimal value) {
            addCriterion("unit_amount >", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unit_amount >=", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountLessThan(BigDecimal value) {
            addCriterion("unit_amount <", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unit_amount <=", value, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountIn(List<BigDecimal> values) {
            addCriterion("unit_amount in", values, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountNotIn(List<BigDecimal> values) {
            addCriterion("unit_amount not in", values, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unit_amount between", value1, value2, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andUnitAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unit_amount not between", value1, value2, "unitAmount");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountIsNull() {
            addCriterion("min_order_amount is null");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountIsNotNull() {
            addCriterion("min_order_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountEqualTo(BigDecimal value) {
            addCriterion("min_order_amount =", value, "minOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountNotEqualTo(BigDecimal value) {
            addCriterion("min_order_amount <>", value, "minOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountGreaterThan(BigDecimal value) {
            addCriterion("min_order_amount >", value, "minOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_order_amount >=", value, "minOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountLessThan(BigDecimal value) {
            addCriterion("min_order_amount <", value, "minOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_order_amount <=", value, "minOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountIn(List<BigDecimal> values) {
            addCriterion("min_order_amount in", values, "minOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountNotIn(List<BigDecimal> values) {
            addCriterion("min_order_amount not in", values, "minOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_order_amount between", value1, value2, "minOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMinOrderAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_order_amount not between", value1, value2, "minOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountIsNull() {
            addCriterion("max_order_amount is null");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountIsNotNull() {
            addCriterion("max_order_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountEqualTo(BigDecimal value) {
            addCriterion("max_order_amount =", value, "maxOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountNotEqualTo(BigDecimal value) {
            addCriterion("max_order_amount <>", value, "maxOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountGreaterThan(BigDecimal value) {
            addCriterion("max_order_amount >", value, "maxOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_order_amount >=", value, "maxOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountLessThan(BigDecimal value) {
            addCriterion("max_order_amount <", value, "maxOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_order_amount <=", value, "maxOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountIn(List<BigDecimal> values) {
            addCriterion("max_order_amount in", values, "maxOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountNotIn(List<BigDecimal> values) {
            addCriterion("max_order_amount not in", values, "maxOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_order_amount between", value1, value2, "maxOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrderAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_order_amount not between", value1, value2, "maxOrderAmount");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersIsNull() {
            addCriterion("max_orders is null");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersIsNotNull() {
            addCriterion("max_orders is not null");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersEqualTo(Integer value) {
            addCriterion("max_orders =", value, "maxOrders");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersNotEqualTo(Integer value) {
            addCriterion("max_orders <>", value, "maxOrders");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersGreaterThan(Integer value) {
            addCriterion("max_orders >", value, "maxOrders");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_orders >=", value, "maxOrders");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersLessThan(Integer value) {
            addCriterion("max_orders <", value, "maxOrders");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersLessThanOrEqualTo(Integer value) {
            addCriterion("max_orders <=", value, "maxOrders");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersIn(List<Integer> values) {
            addCriterion("max_orders in", values, "maxOrders");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersNotIn(List<Integer> values) {
            addCriterion("max_orders not in", values, "maxOrders");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersBetween(Integer value1, Integer value2) {
            addCriterion("max_orders between", value1, value2, "maxOrders");
            return (Criteria) this;
        }

        public Criteria andMaxOrdersNotBetween(Integer value1, Integer value2) {
            addCriterion("max_orders not between", value1, value2, "maxOrders");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitIsNull() {
            addCriterion("min_trade_digit is null");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitIsNotNull() {
            addCriterion("min_trade_digit is not null");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitEqualTo(Integer value) {
            addCriterion("min_trade_digit =", value, "minTradeDigit");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitNotEqualTo(Integer value) {
            addCriterion("min_trade_digit <>", value, "minTradeDigit");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitGreaterThan(Integer value) {
            addCriterion("min_trade_digit >", value, "minTradeDigit");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_trade_digit >=", value, "minTradeDigit");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitLessThan(Integer value) {
            addCriterion("min_trade_digit <", value, "minTradeDigit");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitLessThanOrEqualTo(Integer value) {
            addCriterion("min_trade_digit <=", value, "minTradeDigit");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitIn(List<Integer> values) {
            addCriterion("min_trade_digit in", values, "minTradeDigit");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitNotIn(List<Integer> values) {
            addCriterion("min_trade_digit not in", values, "minTradeDigit");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitBetween(Integer value1, Integer value2) {
            addCriterion("min_trade_digit between", value1, value2, "minTradeDigit");
            return (Criteria) this;
        }

        public Criteria andMinTradeDigitNotBetween(Integer value1, Integer value2) {
            addCriterion("min_trade_digit not between", value1, value2, "minTradeDigit");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitIsNull() {
            addCriterion("min_quote_digit is null");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitIsNotNull() {
            addCriterion("min_quote_digit is not null");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitEqualTo(Integer value) {
            addCriterion("min_quote_digit =", value, "minQuoteDigit");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitNotEqualTo(Integer value) {
            addCriterion("min_quote_digit <>", value, "minQuoteDigit");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitGreaterThan(Integer value) {
            addCriterion("min_quote_digit >", value, "minQuoteDigit");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_quote_digit >=", value, "minQuoteDigit");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitLessThan(Integer value) {
            addCriterion("min_quote_digit <", value, "minQuoteDigit");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitLessThanOrEqualTo(Integer value) {
            addCriterion("min_quote_digit <=", value, "minQuoteDigit");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitIn(List<Integer> values) {
            addCriterion("min_quote_digit in", values, "minQuoteDigit");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitNotIn(List<Integer> values) {
            addCriterion("min_quote_digit not in", values, "minQuoteDigit");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitBetween(Integer value1, Integer value2) {
            addCriterion("min_quote_digit between", value1, value2, "minQuoteDigit");
            return (Criteria) this;
        }

        public Criteria andMinQuoteDigitNotBetween(Integer value1, Integer value2) {
            addCriterion("min_quote_digit not between", value1, value2, "minQuoteDigit");
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

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdIsNull() {
            addCriterion("pre_liqudate_price_threshold is null");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdIsNotNull() {
            addCriterion("pre_liqudate_price_threshold is not null");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdEqualTo(BigDecimal value) {
            addCriterion("pre_liqudate_price_threshold =", value, "preLiqudatePriceThreshold");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdNotEqualTo(BigDecimal value) {
            addCriterion("pre_liqudate_price_threshold <>", value, "preLiqudatePriceThreshold");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdGreaterThan(BigDecimal value) {
            addCriterion("pre_liqudate_price_threshold >", value, "preLiqudatePriceThreshold");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_liqudate_price_threshold >=", value, "preLiqudatePriceThreshold");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdLessThan(BigDecimal value) {
            addCriterion("pre_liqudate_price_threshold <", value, "preLiqudatePriceThreshold");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_liqudate_price_threshold <=", value, "preLiqudatePriceThreshold");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdIn(List<BigDecimal> values) {
            addCriterion("pre_liqudate_price_threshold in", values, "preLiqudatePriceThreshold");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdNotIn(List<BigDecimal> values) {
            addCriterion("pre_liqudate_price_threshold not in", values, "preLiqudatePriceThreshold");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_liqudate_price_threshold between", value1, value2, "preLiqudatePriceThreshold");
            return (Criteria) this;
        }

        public Criteria andPreLiqudatePriceThresholdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_liqudate_price_threshold not between", value1, value2, "preLiqudatePriceThreshold");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeIsNull() {
            addCriterion("premium_min_range is null");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeIsNotNull() {
            addCriterion("premium_min_range is not null");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeEqualTo(BigDecimal value) {
            addCriterion("premium_min_range =", value, "premiumMinRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeNotEqualTo(BigDecimal value) {
            addCriterion("premium_min_range <>", value, "premiumMinRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeGreaterThan(BigDecimal value) {
            addCriterion("premium_min_range >", value, "premiumMinRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("premium_min_range >=", value, "premiumMinRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeLessThan(BigDecimal value) {
            addCriterion("premium_min_range <", value, "premiumMinRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("premium_min_range <=", value, "premiumMinRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeIn(List<BigDecimal> values) {
            addCriterion("premium_min_range in", values, "premiumMinRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeNotIn(List<BigDecimal> values) {
            addCriterion("premium_min_range not in", values, "premiumMinRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("premium_min_range between", value1, value2, "premiumMinRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMinRangeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("premium_min_range not between", value1, value2, "premiumMinRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeIsNull() {
            addCriterion("premium_max_range is null");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeIsNotNull() {
            addCriterion("premium_max_range is not null");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeEqualTo(BigDecimal value) {
            addCriterion("premium_max_range =", value, "premiumMaxRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeNotEqualTo(BigDecimal value) {
            addCriterion("premium_max_range <>", value, "premiumMaxRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeGreaterThan(BigDecimal value) {
            addCriterion("premium_max_range >", value, "premiumMaxRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("premium_max_range >=", value, "premiumMaxRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeLessThan(BigDecimal value) {
            addCriterion("premium_max_range <", value, "premiumMaxRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("premium_max_range <=", value, "premiumMaxRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeIn(List<BigDecimal> values) {
            addCriterion("premium_max_range in", values, "premiumMaxRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeNotIn(List<BigDecimal> values) {
            addCriterion("premium_max_range not in", values, "premiumMaxRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("premium_max_range between", value1, value2, "premiumMaxRange");
            return (Criteria) this;
        }

        public Criteria andPremiumMaxRangeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("premium_max_range not between", value1, value2, "premiumMaxRange");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthIsNull() {
            addCriterion("premium_depth is null");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthIsNotNull() {
            addCriterion("premium_depth is not null");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthEqualTo(BigDecimal value) {
            addCriterion("premium_depth =", value, "premiumDepth");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthNotEqualTo(BigDecimal value) {
            addCriterion("premium_depth <>", value, "premiumDepth");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthGreaterThan(BigDecimal value) {
            addCriterion("premium_depth >", value, "premiumDepth");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("premium_depth >=", value, "premiumDepth");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthLessThan(BigDecimal value) {
            addCriterion("premium_depth <", value, "premiumDepth");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("premium_depth <=", value, "premiumDepth");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthIn(List<BigDecimal> values) {
            addCriterion("premium_depth in", values, "premiumDepth");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthNotIn(List<BigDecimal> values) {
            addCriterion("premium_depth not in", values, "premiumDepth");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("premium_depth between", value1, value2, "premiumDepth");
            return (Criteria) this;
        }

        public Criteria andPremiumDepthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("premium_depth not between", value1, value2, "premiumDepth");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingIsNull() {
            addCriterion("funding_ceiling is null");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingIsNotNull() {
            addCriterion("funding_ceiling is not null");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingEqualTo(BigDecimal value) {
            addCriterion("funding_ceiling =", value, "fundingCeiling");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingNotEqualTo(BigDecimal value) {
            addCriterion("funding_ceiling <>", value, "fundingCeiling");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingGreaterThan(BigDecimal value) {
            addCriterion("funding_ceiling >", value, "fundingCeiling");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("funding_ceiling >=", value, "fundingCeiling");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingLessThan(BigDecimal value) {
            addCriterion("funding_ceiling <", value, "fundingCeiling");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingLessThanOrEqualTo(BigDecimal value) {
            addCriterion("funding_ceiling <=", value, "fundingCeiling");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingIn(List<BigDecimal> values) {
            addCriterion("funding_ceiling in", values, "fundingCeiling");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingNotIn(List<BigDecimal> values) {
            addCriterion("funding_ceiling not in", values, "fundingCeiling");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("funding_ceiling between", value1, value2, "fundingCeiling");
            return (Criteria) this;
        }

        public Criteria andFundingCeilingNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("funding_ceiling not between", value1, value2, "fundingCeiling");
            return (Criteria) this;
        }

        public Criteria andMinGearIsNull() {
            addCriterion("min_gear is null");
            return (Criteria) this;
        }

        public Criteria andMinGearIsNotNull() {
            addCriterion("min_gear is not null");
            return (Criteria) this;
        }

        public Criteria andMinGearEqualTo(BigDecimal value) {
            addCriterion("min_gear =", value, "minGear");
            return (Criteria) this;
        }

        public Criteria andMinGearNotEqualTo(BigDecimal value) {
            addCriterion("min_gear <>", value, "minGear");
            return (Criteria) this;
        }

        public Criteria andMinGearGreaterThan(BigDecimal value) {
            addCriterion("min_gear >", value, "minGear");
            return (Criteria) this;
        }

        public Criteria andMinGearGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_gear >=", value, "minGear");
            return (Criteria) this;
        }

        public Criteria andMinGearLessThan(BigDecimal value) {
            addCriterion("min_gear <", value, "minGear");
            return (Criteria) this;
        }

        public Criteria andMinGearLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_gear <=", value, "minGear");
            return (Criteria) this;
        }

        public Criteria andMinGearIn(List<BigDecimal> values) {
            addCriterion("min_gear in", values, "minGear");
            return (Criteria) this;
        }

        public Criteria andMinGearNotIn(List<BigDecimal> values) {
            addCriterion("min_gear not in", values, "minGear");
            return (Criteria) this;
        }

        public Criteria andMinGearBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_gear between", value1, value2, "minGear");
            return (Criteria) this;
        }

        public Criteria andMinGearNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_gear not between", value1, value2, "minGear");
            return (Criteria) this;
        }

        public Criteria andDiffGearIsNull() {
            addCriterion("diff_gear is null");
            return (Criteria) this;
        }

        public Criteria andDiffGearIsNotNull() {
            addCriterion("diff_gear is not null");
            return (Criteria) this;
        }

        public Criteria andDiffGearEqualTo(BigDecimal value) {
            addCriterion("diff_gear =", value, "diffGear");
            return (Criteria) this;
        }

        public Criteria andDiffGearNotEqualTo(BigDecimal value) {
            addCriterion("diff_gear <>", value, "diffGear");
            return (Criteria) this;
        }

        public Criteria andDiffGearGreaterThan(BigDecimal value) {
            addCriterion("diff_gear >", value, "diffGear");
            return (Criteria) this;
        }

        public Criteria andDiffGearGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("diff_gear >=", value, "diffGear");
            return (Criteria) this;
        }

        public Criteria andDiffGearLessThan(BigDecimal value) {
            addCriterion("diff_gear <", value, "diffGear");
            return (Criteria) this;
        }

        public Criteria andDiffGearLessThanOrEqualTo(BigDecimal value) {
            addCriterion("diff_gear <=", value, "diffGear");
            return (Criteria) this;
        }

        public Criteria andDiffGearIn(List<BigDecimal> values) {
            addCriterion("diff_gear in", values, "diffGear");
            return (Criteria) this;
        }

        public Criteria andDiffGearNotIn(List<BigDecimal> values) {
            addCriterion("diff_gear not in", values, "diffGear");
            return (Criteria) this;
        }

        public Criteria andDiffGearBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("diff_gear between", value1, value2, "diffGear");
            return (Criteria) this;
        }

        public Criteria andDiffGearNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("diff_gear not between", value1, value2, "diffGear");
            return (Criteria) this;
        }

        public Criteria andMaxGearIsNull() {
            addCriterion("max_gear is null");
            return (Criteria) this;
        }

        public Criteria andMaxGearIsNotNull() {
            addCriterion("max_gear is not null");
            return (Criteria) this;
        }

        public Criteria andMaxGearEqualTo(BigDecimal value) {
            addCriterion("max_gear =", value, "maxGear");
            return (Criteria) this;
        }

        public Criteria andMaxGearNotEqualTo(BigDecimal value) {
            addCriterion("max_gear <>", value, "maxGear");
            return (Criteria) this;
        }

        public Criteria andMaxGearGreaterThan(BigDecimal value) {
            addCriterion("max_gear >", value, "maxGear");
            return (Criteria) this;
        }

        public Criteria andMaxGearGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_gear >=", value, "maxGear");
            return (Criteria) this;
        }

        public Criteria andMaxGearLessThan(BigDecimal value) {
            addCriterion("max_gear <", value, "maxGear");
            return (Criteria) this;
        }

        public Criteria andMaxGearLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_gear <=", value, "maxGear");
            return (Criteria) this;
        }

        public Criteria andMaxGearIn(List<BigDecimal> values) {
            addCriterion("max_gear in", values, "maxGear");
            return (Criteria) this;
        }

        public Criteria andMaxGearNotIn(List<BigDecimal> values) {
            addCriterion("max_gear not in", values, "maxGear");
            return (Criteria) this;
        }

        public Criteria andMaxGearBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_gear between", value1, value2, "maxGear");
            return (Criteria) this;
        }

        public Criteria andMaxGearNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_gear not between", value1, value2, "maxGear");
            return (Criteria) this;
        }

        public Criteria andMaintainRateIsNull() {
            addCriterion("maintain_rate is null");
            return (Criteria) this;
        }

        public Criteria andMaintainRateIsNotNull() {
            addCriterion("maintain_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMaintainRateEqualTo(BigDecimal value) {
            addCriterion("maintain_rate =", value, "maintainRate");
            return (Criteria) this;
        }

        public Criteria andMaintainRateNotEqualTo(BigDecimal value) {
            addCriterion("maintain_rate <>", value, "maintainRate");
            return (Criteria) this;
        }

        public Criteria andMaintainRateGreaterThan(BigDecimal value) {
            addCriterion("maintain_rate >", value, "maintainRate");
            return (Criteria) this;
        }

        public Criteria andMaintainRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("maintain_rate >=", value, "maintainRate");
            return (Criteria) this;
        }

        public Criteria andMaintainRateLessThan(BigDecimal value) {
            addCriterion("maintain_rate <", value, "maintainRate");
            return (Criteria) this;
        }

        public Criteria andMaintainRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("maintain_rate <=", value, "maintainRate");
            return (Criteria) this;
        }

        public Criteria andMaintainRateIn(List<BigDecimal> values) {
            addCriterion("maintain_rate in", values, "maintainRate");
            return (Criteria) this;
        }

        public Criteria andMaintainRateNotIn(List<BigDecimal> values) {
            addCriterion("maintain_rate not in", values, "maintainRate");
            return (Criteria) this;
        }

        public Criteria andMaintainRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("maintain_rate between", value1, value2, "maintainRate");
            return (Criteria) this;
        }

        public Criteria andMaintainRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("maintain_rate not between", value1, value2, "maintainRate");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourIsNull() {
            addCriterion("liquidation_hour is null");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourIsNotNull() {
            addCriterion("liquidation_hour is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourEqualTo(Integer value) {
            addCriterion("liquidation_hour =", value, "liquidationHour");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourNotEqualTo(Integer value) {
            addCriterion("liquidation_hour <>", value, "liquidationHour");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourGreaterThan(Integer value) {
            addCriterion("liquidation_hour >", value, "liquidationHour");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourGreaterThanOrEqualTo(Integer value) {
            addCriterion("liquidation_hour >=", value, "liquidationHour");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourLessThan(Integer value) {
            addCriterion("liquidation_hour <", value, "liquidationHour");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourLessThanOrEqualTo(Integer value) {
            addCriterion("liquidation_hour <=", value, "liquidationHour");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourIn(List<Integer> values) {
            addCriterion("liquidation_hour in", values, "liquidationHour");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourNotIn(List<Integer> values) {
            addCriterion("liquidation_hour not in", values, "liquidationHour");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourBetween(Integer value1, Integer value2) {
            addCriterion("liquidation_hour between", value1, value2, "liquidationHour");
            return (Criteria) this;
        }

        public Criteria andLiquidationHourNotBetween(Integer value1, Integer value2) {
            addCriterion("liquidation_hour not between", value1, value2, "liquidationHour");
            return (Criteria) this;
        }

        public Criteria andDkFeeIsNull() {
            addCriterion("dk_fee is null");
            return (Criteria) this;
        }

        public Criteria andDkFeeIsNotNull() {
            addCriterion("dk_fee is not null");
            return (Criteria) this;
        }

        public Criteria andDkFeeEqualTo(Integer value) {
            addCriterion("dk_fee =", value, "dkFee");
            return (Criteria) this;
        }

        public Criteria andDkFeeNotEqualTo(Integer value) {
            addCriterion("dk_fee <>", value, "dkFee");
            return (Criteria) this;
        }

        public Criteria andDkFeeGreaterThan(Integer value) {
            addCriterion("dk_fee >", value, "dkFee");
            return (Criteria) this;
        }

        public Criteria andDkFeeGreaterThanOrEqualTo(Integer value) {
            addCriterion("dk_fee >=", value, "dkFee");
            return (Criteria) this;
        }

        public Criteria andDkFeeLessThan(Integer value) {
            addCriterion("dk_fee <", value, "dkFee");
            return (Criteria) this;
        }

        public Criteria andDkFeeLessThanOrEqualTo(Integer value) {
            addCriterion("dk_fee <=", value, "dkFee");
            return (Criteria) this;
        }

        public Criteria andDkFeeIn(List<Integer> values) {
            addCriterion("dk_fee in", values, "dkFee");
            return (Criteria) this;
        }

        public Criteria andDkFeeNotIn(List<Integer> values) {
            addCriterion("dk_fee not in", values, "dkFee");
            return (Criteria) this;
        }

        public Criteria andDkFeeBetween(Integer value1, Integer value2) {
            addCriterion("dk_fee between", value1, value2, "dkFee");
            return (Criteria) this;
        }

        public Criteria andDkFeeNotBetween(Integer value1, Integer value2) {
            addCriterion("dk_fee not between", value1, value2, "dkFee");
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

        public Criteria andOnlineIsNull() {
            addCriterion("online is null");
            return (Criteria) this;
        }

        public Criteria andOnlineIsNotNull() {
            addCriterion("online is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineEqualTo(Integer value) {
            addCriterion("online =", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotEqualTo(Integer value) {
            addCriterion("online <>", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineGreaterThan(Integer value) {
            addCriterion("online >", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineGreaterThanOrEqualTo(Integer value) {
            addCriterion("online >=", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineLessThan(Integer value) {
            addCriterion("online <", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineLessThanOrEqualTo(Integer value) {
            addCriterion("online <=", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineIn(List<Integer> values) {
            addCriterion("online in", values, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotIn(List<Integer> values) {
            addCriterion("online not in", values, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineBetween(Integer value1, Integer value2) {
            addCriterion("online between", value1, value2, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotBetween(Integer value1, Integer value2) {
            addCriterion("online not between", value1, value2, "online");
            return (Criteria) this;
        }

        public Criteria andInterestRateIsNull() {
            addCriterion("interest_rate is null");
            return (Criteria) this;
        }

        public Criteria andInterestRateIsNotNull() {
            addCriterion("interest_rate is not null");
            return (Criteria) this;
        }

        public Criteria andInterestRateEqualTo(BigDecimal value) {
            addCriterion("interest_rate =", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateNotEqualTo(BigDecimal value) {
            addCriterion("interest_rate <>", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateGreaterThan(BigDecimal value) {
            addCriterion("interest_rate >", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("interest_rate >=", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateLessThan(BigDecimal value) {
            addCriterion("interest_rate <", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("interest_rate <=", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateIn(List<BigDecimal> values) {
            addCriterion("interest_rate in", values, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateNotIn(List<BigDecimal> values) {
            addCriterion("interest_rate not in", values, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest_rate between", value1, value2, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest_rate not between", value1, value2, "interestRate");
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

        public Criteria andMarketPriceDigitIsNull() {
            addCriterion("market_price_digit is null");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitIsNotNull() {
            addCriterion("market_price_digit is not null");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitEqualTo(Integer value) {
            addCriterion("market_price_digit =", value, "marketPriceDigit");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitNotEqualTo(Integer value) {
            addCriterion("market_price_digit <>", value, "marketPriceDigit");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitGreaterThan(Integer value) {
            addCriterion("market_price_digit >", value, "marketPriceDigit");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitGreaterThanOrEqualTo(Integer value) {
            addCriterion("market_price_digit >=", value, "marketPriceDigit");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitLessThan(Integer value) {
            addCriterion("market_price_digit <", value, "marketPriceDigit");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitLessThanOrEqualTo(Integer value) {
            addCriterion("market_price_digit <=", value, "marketPriceDigit");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitIn(List<Integer> values) {
            addCriterion("market_price_digit in", values, "marketPriceDigit");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitNotIn(List<Integer> values) {
            addCriterion("market_price_digit not in", values, "marketPriceDigit");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitBetween(Integer value1, Integer value2) {
            addCriterion("market_price_digit between", value1, value2, "marketPriceDigit");
            return (Criteria) this;
        }

        public Criteria andMarketPriceDigitNotBetween(Integer value1, Integer value2) {
            addCriterion("market_price_digit not between", value1, value2, "marketPriceDigit");
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