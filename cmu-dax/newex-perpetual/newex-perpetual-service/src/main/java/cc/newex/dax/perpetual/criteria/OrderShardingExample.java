package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单表 查询条件类
 *
 * @author newex-team
 * @date 2019-01-10 19:58:00
 */
public class OrderShardingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderShardingExample() {
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

        public Criteria andClazzIsNull() {
            addCriterion("clazz is null");
            return (Criteria) this;
        }

        public Criteria andClazzIsNotNull() {
            addCriterion("clazz is not null");
            return (Criteria) this;
        }

        public Criteria andClazzEqualTo(Integer value) {
            addCriterion("clazz =", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzNotEqualTo(Integer value) {
            addCriterion("clazz <>", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzGreaterThan(Integer value) {
            addCriterion("clazz >", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzGreaterThanOrEqualTo(Integer value) {
            addCriterion("clazz >=", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzLessThan(Integer value) {
            addCriterion("clazz <", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzLessThanOrEqualTo(Integer value) {
            addCriterion("clazz <=", value, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzIn(List<Integer> values) {
            addCriterion("clazz in", values, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzNotIn(List<Integer> values) {
            addCriterion("clazz not in", values, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzBetween(Integer value1, Integer value2) {
            addCriterion("clazz between", value1, value2, "clazz");
            return (Criteria) this;
        }

        public Criteria andClazzNotBetween(Integer value1, Integer value2) {
            addCriterion("clazz not between", value1, value2, "clazz");
            return (Criteria) this;
        }

        public Criteria andMustMakerIsNull() {
            addCriterion("must_maker is null");
            return (Criteria) this;
        }

        public Criteria andMustMakerIsNotNull() {
            addCriterion("must_maker is not null");
            return (Criteria) this;
        }

        public Criteria andMustMakerEqualTo(Integer value) {
            addCriterion("must_maker =", value, "mustMaker");
            return (Criteria) this;
        }

        public Criteria andMustMakerNotEqualTo(Integer value) {
            addCriterion("must_maker <>", value, "mustMaker");
            return (Criteria) this;
        }

        public Criteria andMustMakerGreaterThan(Integer value) {
            addCriterion("must_maker >", value, "mustMaker");
            return (Criteria) this;
        }

        public Criteria andMustMakerGreaterThanOrEqualTo(Integer value) {
            addCriterion("must_maker >=", value, "mustMaker");
            return (Criteria) this;
        }

        public Criteria andMustMakerLessThan(Integer value) {
            addCriterion("must_maker <", value, "mustMaker");
            return (Criteria) this;
        }

        public Criteria andMustMakerLessThanOrEqualTo(Integer value) {
            addCriterion("must_maker <=", value, "mustMaker");
            return (Criteria) this;
        }

        public Criteria andMustMakerIn(List<Integer> values) {
            addCriterion("must_maker in", values, "mustMaker");
            return (Criteria) this;
        }

        public Criteria andMustMakerNotIn(List<Integer> values) {
            addCriterion("must_maker not in", values, "mustMaker");
            return (Criteria) this;
        }

        public Criteria andMustMakerBetween(Integer value1, Integer value2) {
            addCriterion("must_maker between", value1, value2, "mustMaker");
            return (Criteria) this;
        }

        public Criteria andMustMakerNotBetween(Integer value1, Integer value2) {
            addCriterion("must_maker not between", value1, value2, "mustMaker");
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

        public Criteria andDetailSideIsNull() {
            addCriterion("detail_side is null");
            return (Criteria) this;
        }

        public Criteria andDetailSideIsNotNull() {
            addCriterion("detail_side is not null");
            return (Criteria) this;
        }

        public Criteria andDetailSideEqualTo(String value) {
            addCriterion("detail_side =", value, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideNotEqualTo(String value) {
            addCriterion("detail_side <>", value, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideGreaterThan(String value) {
            addCriterion("detail_side >", value, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideGreaterThanOrEqualTo(String value) {
            addCriterion("detail_side >=", value, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideLessThan(String value) {
            addCriterion("detail_side <", value, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideLessThanOrEqualTo(String value) {
            addCriterion("detail_side <=", value, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideLike(String value) {
            addCriterion("detail_side like", value, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideNotLike(String value) {
            addCriterion("detail_side not like", value, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideIn(List<String> values) {
            addCriterion("detail_side in", values, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideNotIn(List<String> values) {
            addCriterion("detail_side not in", values, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideBetween(String value1, String value2) {
            addCriterion("detail_side between", value1, value2, "detailSide");
            return (Criteria) this;
        }

        public Criteria andDetailSideNotBetween(String value1, String value2) {
            addCriterion("detail_side not between", value1, value2, "detailSide");
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

        public Criteria andShowAmountIsNull() {
            addCriterion("show_amount is null");
            return (Criteria) this;
        }

        public Criteria andShowAmountIsNotNull() {
            addCriterion("show_amount is not null");
            return (Criteria) this;
        }

        public Criteria andShowAmountEqualTo(BigDecimal value) {
            addCriterion("show_amount =", value, "showAmount");
            return (Criteria) this;
        }

        public Criteria andShowAmountNotEqualTo(BigDecimal value) {
            addCriterion("show_amount <>", value, "showAmount");
            return (Criteria) this;
        }

        public Criteria andShowAmountGreaterThan(BigDecimal value) {
            addCriterion("show_amount >", value, "showAmount");
            return (Criteria) this;
        }

        public Criteria andShowAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("show_amount >=", value, "showAmount");
            return (Criteria) this;
        }

        public Criteria andShowAmountLessThan(BigDecimal value) {
            addCriterion("show_amount <", value, "showAmount");
            return (Criteria) this;
        }

        public Criteria andShowAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("show_amount <=", value, "showAmount");
            return (Criteria) this;
        }

        public Criteria andShowAmountIn(List<BigDecimal> values) {
            addCriterion("show_amount in", values, "showAmount");
            return (Criteria) this;
        }

        public Criteria andShowAmountNotIn(List<BigDecimal> values) {
            addCriterion("show_amount not in", values, "showAmount");
            return (Criteria) this;
        }

        public Criteria andShowAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("show_amount between", value1, value2, "showAmount");
            return (Criteria) this;
        }

        public Criteria andShowAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("show_amount not between", value1, value2, "showAmount");
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

        public Criteria andAvgPriceIsNull() {
            addCriterion("avg_price is null");
            return (Criteria) this;
        }

        public Criteria andAvgPriceIsNotNull() {
            addCriterion("avg_price is not null");
            return (Criteria) this;
        }

        public Criteria andAvgPriceEqualTo(BigDecimal value) {
            addCriterion("avg_price =", value, "avgPrice");
            return (Criteria) this;
        }

        public Criteria andAvgPriceNotEqualTo(BigDecimal value) {
            addCriterion("avg_price <>", value, "avgPrice");
            return (Criteria) this;
        }

        public Criteria andAvgPriceGreaterThan(BigDecimal value) {
            addCriterion("avg_price >", value, "avgPrice");
            return (Criteria) this;
        }

        public Criteria andAvgPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("avg_price >=", value, "avgPrice");
            return (Criteria) this;
        }

        public Criteria andAvgPriceLessThan(BigDecimal value) {
            addCriterion("avg_price <", value, "avgPrice");
            return (Criteria) this;
        }

        public Criteria andAvgPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("avg_price <=", value, "avgPrice");
            return (Criteria) this;
        }

        public Criteria andAvgPriceIn(List<BigDecimal> values) {
            addCriterion("avg_price in", values, "avgPrice");
            return (Criteria) this;
        }

        public Criteria andAvgPriceNotIn(List<BigDecimal> values) {
            addCriterion("avg_price not in", values, "avgPrice");
            return (Criteria) this;
        }

        public Criteria andAvgPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("avg_price between", value1, value2, "avgPrice");
            return (Criteria) this;
        }

        public Criteria andAvgPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("avg_price not between", value1, value2, "avgPrice");
            return (Criteria) this;
        }

        public Criteria andDealAmountIsNull() {
            addCriterion("deal_amount is null");
            return (Criteria) this;
        }

        public Criteria andDealAmountIsNotNull() {
            addCriterion("deal_amount is not null");
            return (Criteria) this;
        }

        public Criteria andDealAmountEqualTo(BigDecimal value) {
            addCriterion("deal_amount =", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountNotEqualTo(BigDecimal value) {
            addCriterion("deal_amount <>", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountGreaterThan(BigDecimal value) {
            addCriterion("deal_amount >", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("deal_amount >=", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountLessThan(BigDecimal value) {
            addCriterion("deal_amount <", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("deal_amount <=", value, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountIn(List<BigDecimal> values) {
            addCriterion("deal_amount in", values, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountNotIn(List<BigDecimal> values) {
            addCriterion("deal_amount not in", values, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deal_amount between", value1, value2, "dealAmount");
            return (Criteria) this;
        }

        public Criteria andDealAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deal_amount not between", value1, value2, "dealAmount");
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

        public Criteria andBrokerSizeIsNull() {
            addCriterion("broker_size is null");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeIsNotNull() {
            addCriterion("broker_size is not null");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeEqualTo(BigDecimal value) {
            addCriterion("broker_size =", value, "brokerSize");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeNotEqualTo(BigDecimal value) {
            addCriterion("broker_size <>", value, "brokerSize");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeGreaterThan(BigDecimal value) {
            addCriterion("broker_size >", value, "brokerSize");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("broker_size >=", value, "brokerSize");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeLessThan(BigDecimal value) {
            addCriterion("broker_size <", value, "brokerSize");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("broker_size <=", value, "brokerSize");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeIn(List<BigDecimal> values) {
            addCriterion("broker_size in", values, "brokerSize");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeNotIn(List<BigDecimal> values) {
            addCriterion("broker_size not in", values, "brokerSize");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("broker_size between", value1, value2, "brokerSize");
            return (Criteria) this;
        }

        public Criteria andBrokerSizeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("broker_size not between", value1, value2, "brokerSize");
            return (Criteria) this;
        }

        public Criteria andDealSizeIsNull() {
            addCriterion("deal_size is null");
            return (Criteria) this;
        }

        public Criteria andDealSizeIsNotNull() {
            addCriterion("deal_size is not null");
            return (Criteria) this;
        }

        public Criteria andDealSizeEqualTo(BigDecimal value) {
            addCriterion("deal_size =", value, "dealSize");
            return (Criteria) this;
        }

        public Criteria andDealSizeNotEqualTo(BigDecimal value) {
            addCriterion("deal_size <>", value, "dealSize");
            return (Criteria) this;
        }

        public Criteria andDealSizeGreaterThan(BigDecimal value) {
            addCriterion("deal_size >", value, "dealSize");
            return (Criteria) this;
        }

        public Criteria andDealSizeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("deal_size >=", value, "dealSize");
            return (Criteria) this;
        }

        public Criteria andDealSizeLessThan(BigDecimal value) {
            addCriterion("deal_size <", value, "dealSize");
            return (Criteria) this;
        }

        public Criteria andDealSizeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("deal_size <=", value, "dealSize");
            return (Criteria) this;
        }

        public Criteria andDealSizeIn(List<BigDecimal> values) {
            addCriterion("deal_size in", values, "dealSize");
            return (Criteria) this;
        }

        public Criteria andDealSizeNotIn(List<BigDecimal> values) {
            addCriterion("deal_size not in", values, "dealSize");
            return (Criteria) this;
        }

        public Criteria andDealSizeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deal_size between", value1, value2, "dealSize");
            return (Criteria) this;
        }

        public Criteria andDealSizeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deal_size not between", value1, value2, "dealSize");
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

        public Criteria andExtraMarginIsNull() {
            addCriterion("extra_margin is null");
            return (Criteria) this;
        }

        public Criteria andExtraMarginIsNotNull() {
            addCriterion("extra_margin is not null");
            return (Criteria) this;
        }

        public Criteria andExtraMarginEqualTo(BigDecimal value) {
            addCriterion("extra_margin =", value, "extraMargin");
            return (Criteria) this;
        }

        public Criteria andExtraMarginNotEqualTo(BigDecimal value) {
            addCriterion("extra_margin <>", value, "extraMargin");
            return (Criteria) this;
        }

        public Criteria andExtraMarginGreaterThan(BigDecimal value) {
            addCriterion("extra_margin >", value, "extraMargin");
            return (Criteria) this;
        }

        public Criteria andExtraMarginGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("extra_margin >=", value, "extraMargin");
            return (Criteria) this;
        }

        public Criteria andExtraMarginLessThan(BigDecimal value) {
            addCriterion("extra_margin <", value, "extraMargin");
            return (Criteria) this;
        }

        public Criteria andExtraMarginLessThanOrEqualTo(BigDecimal value) {
            addCriterion("extra_margin <=", value, "extraMargin");
            return (Criteria) this;
        }

        public Criteria andExtraMarginIn(List<BigDecimal> values) {
            addCriterion("extra_margin in", values, "extraMargin");
            return (Criteria) this;
        }

        public Criteria andExtraMarginNotIn(List<BigDecimal> values) {
            addCriterion("extra_margin not in", values, "extraMargin");
            return (Criteria) this;
        }

        public Criteria andExtraMarginBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("extra_margin between", value1, value2, "extraMargin");
            return (Criteria) this;
        }

        public Criteria andExtraMarginNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("extra_margin not between", value1, value2, "extraMargin");
            return (Criteria) this;
        }

        public Criteria andAvgMarginIsNull() {
            addCriterion("avg_margin is null");
            return (Criteria) this;
        }

        public Criteria andAvgMarginIsNotNull() {
            addCriterion("avg_margin is not null");
            return (Criteria) this;
        }

        public Criteria andAvgMarginEqualTo(BigDecimal value) {
            addCriterion("avg_margin =", value, "avgMargin");
            return (Criteria) this;
        }

        public Criteria andAvgMarginNotEqualTo(BigDecimal value) {
            addCriterion("avg_margin <>", value, "avgMargin");
            return (Criteria) this;
        }

        public Criteria andAvgMarginGreaterThan(BigDecimal value) {
            addCriterion("avg_margin >", value, "avgMargin");
            return (Criteria) this;
        }

        public Criteria andAvgMarginGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("avg_margin >=", value, "avgMargin");
            return (Criteria) this;
        }

        public Criteria andAvgMarginLessThan(BigDecimal value) {
            addCriterion("avg_margin <", value, "avgMargin");
            return (Criteria) this;
        }

        public Criteria andAvgMarginLessThanOrEqualTo(BigDecimal value) {
            addCriterion("avg_margin <=", value, "avgMargin");
            return (Criteria) this;
        }

        public Criteria andAvgMarginIn(List<BigDecimal> values) {
            addCriterion("avg_margin in", values, "avgMargin");
            return (Criteria) this;
        }

        public Criteria andAvgMarginNotIn(List<BigDecimal> values) {
            addCriterion("avg_margin not in", values, "avgMargin");
            return (Criteria) this;
        }

        public Criteria andAvgMarginBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("avg_margin between", value1, value2, "avgMargin");
            return (Criteria) this;
        }

        public Criteria andAvgMarginNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("avg_margin not between", value1, value2, "avgMargin");
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

        public Criteria andOrderFromIsNull() {
            addCriterion("order_from is null");
            return (Criteria) this;
        }

        public Criteria andOrderFromIsNotNull() {
            addCriterion("order_from is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFromEqualTo(Integer value) {
            addCriterion("order_from =", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromNotEqualTo(Integer value) {
            addCriterion("order_from <>", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromGreaterThan(Integer value) {
            addCriterion("order_from >", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_from >=", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromLessThan(Integer value) {
            addCriterion("order_from <", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromLessThanOrEqualTo(Integer value) {
            addCriterion("order_from <=", value, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromIn(List<Integer> values) {
            addCriterion("order_from in", values, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromNotIn(List<Integer> values) {
            addCriterion("order_from not in", values, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromBetween(Integer value1, Integer value2) {
            addCriterion("order_from between", value1, value2, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andOrderFromNotBetween(Integer value1, Integer value2) {
            addCriterion("order_from not between", value1, value2, "orderFrom");
            return (Criteria) this;
        }

        public Criteria andSystemTypeIsNull() {
            addCriterion("system_type is null");
            return (Criteria) this;
        }

        public Criteria andSystemTypeIsNotNull() {
            addCriterion("system_type is not null");
            return (Criteria) this;
        }

        public Criteria andSystemTypeEqualTo(Integer value) {
            addCriterion("system_type =", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeNotEqualTo(Integer value) {
            addCriterion("system_type <>", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeGreaterThan(Integer value) {
            addCriterion("system_type >", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("system_type >=", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeLessThan(Integer value) {
            addCriterion("system_type <", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeLessThanOrEqualTo(Integer value) {
            addCriterion("system_type <=", value, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeIn(List<Integer> values) {
            addCriterion("system_type in", values, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeNotIn(List<Integer> values) {
            addCriterion("system_type not in", values, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeBetween(Integer value1, Integer value2) {
            addCriterion("system_type between", value1, value2, "systemType");
            return (Criteria) this;
        }

        public Criteria andSystemTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("system_type not between", value1, value2, "systemType");
            return (Criteria) this;
        }

        public Criteria andMatchStatusIsNull() {
            addCriterion("match_status is null");
            return (Criteria) this;
        }

        public Criteria andMatchStatusIsNotNull() {
            addCriterion("match_status is not null");
            return (Criteria) this;
        }

        public Criteria andMatchStatusEqualTo(Integer value) {
            addCriterion("match_status =", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusNotEqualTo(Integer value) {
            addCriterion("match_status <>", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusGreaterThan(Integer value) {
            addCriterion("match_status >", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("match_status >=", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusLessThan(Integer value) {
            addCriterion("match_status <", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusLessThanOrEqualTo(Integer value) {
            addCriterion("match_status <=", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusIn(List<Integer> values) {
            addCriterion("match_status in", values, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusNotIn(List<Integer> values) {
            addCriterion("match_status not in", values, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusBetween(Integer value1, Integer value2) {
            addCriterion("match_status between", value1, value2, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("match_status not between", value1, value2, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdIsNull() {
            addCriterion("relation_order_id is null");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdIsNotNull() {
            addCriterion("relation_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdEqualTo(Long value) {
            addCriterion("relation_order_id =", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdNotEqualTo(Long value) {
            addCriterion("relation_order_id <>", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdGreaterThan(Long value) {
            addCriterion("relation_order_id >", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("relation_order_id >=", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdLessThan(Long value) {
            addCriterion("relation_order_id <", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("relation_order_id <=", value, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdIn(List<Long> values) {
            addCriterion("relation_order_id in", values, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdNotIn(List<Long> values) {
            addCriterion("relation_order_id not in", values, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdBetween(Long value1, Long value2) {
            addCriterion("relation_order_id between", value1, value2, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andRelationOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("relation_order_id not between", value1, value2, "relationOrderId");
            return (Criteria) this;
        }

        public Criteria andProfitIsNull() {
            addCriterion("profit is null");
            return (Criteria) this;
        }

        public Criteria andProfitIsNotNull() {
            addCriterion("profit is not null");
            return (Criteria) this;
        }

        public Criteria andProfitEqualTo(BigDecimal value) {
            addCriterion("profit =", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitNotEqualTo(BigDecimal value) {
            addCriterion("profit <>", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitGreaterThan(BigDecimal value) {
            addCriterion("profit >", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profit >=", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitLessThan(BigDecimal value) {
            addCriterion("profit <", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profit <=", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitIn(List<BigDecimal> values) {
            addCriterion("profit in", values, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitNotIn(List<BigDecimal> values) {
            addCriterion("profit not in", values, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit between", value1, value2, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit not between", value1, value2, "profit");
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

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(Integer value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(Integer value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(Integer value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(Integer value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(Integer value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(Integer value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<Integer> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<Integer> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(Integer value1, Integer value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(Integer value1, Integer value2) {
            addCriterion("reason not between", value1, value2, "reason");
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