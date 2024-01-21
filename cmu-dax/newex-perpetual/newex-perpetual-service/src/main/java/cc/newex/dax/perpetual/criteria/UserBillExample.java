package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账单 查询条件类
 *
 * @author newex-team
 * @date 2018-11-20 18:27:52
 */
public class UserBillExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserBillExample() {
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

        public Criteria andUIdIsNull() {
            addCriterion("u_id is null");
            return (Criteria) this;
        }

        public Criteria andUIdIsNotNull() {
            addCriterion("u_id is not null");
            return (Criteria) this;
        }

        public Criteria andUIdEqualTo(String value) {
            addCriterion("u_id =", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotEqualTo(String value) {
            addCriterion("u_id <>", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdGreaterThan(String value) {
            addCriterion("u_id >", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdGreaterThanOrEqualTo(String value) {
            addCriterion("u_id >=", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdLessThan(String value) {
            addCriterion("u_id <", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdLessThanOrEqualTo(String value) {
            addCriterion("u_id <=", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdLike(String value) {
            addCriterion("u_id like", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotLike(String value) {
            addCriterion("u_id not like", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdIn(List<String> values) {
            addCriterion("u_id in", values, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotIn(List<String> values) {
            addCriterion("u_id not in", values, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdBetween(String value1, String value2) {
            addCriterion("u_id between", value1, value2, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotBetween(String value1, String value2) {
            addCriterion("u_id not between", value1, value2, "uId");
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

        public Criteria andFeeCurrencyCodeIsNull() {
            addCriterion("fee_currency_code is null");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeIsNotNull() {
            addCriterion("fee_currency_code is not null");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeEqualTo(String value) {
            addCriterion("fee_currency_code =", value, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeNotEqualTo(String value) {
            addCriterion("fee_currency_code <>", value, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeGreaterThan(String value) {
            addCriterion("fee_currency_code >", value, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("fee_currency_code >=", value, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeLessThan(String value) {
            addCriterion("fee_currency_code <", value, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeLessThanOrEqualTo(String value) {
            addCriterion("fee_currency_code <=", value, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeLike(String value) {
            addCriterion("fee_currency_code like", value, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeNotLike(String value) {
            addCriterion("fee_currency_code not like", value, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeIn(List<String> values) {
            addCriterion("fee_currency_code in", values, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeNotIn(List<String> values) {
            addCriterion("fee_currency_code not in", values, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeBetween(String value1, String value2) {
            addCriterion("fee_currency_code between", value1, value2, "feeCurrencyCode");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyCodeNotBetween(String value1, String value2) {
            addCriterion("fee_currency_code not between", value1, value2, "feeCurrencyCode");
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

        public Criteria andBeforePositionIsNull() {
            addCriterion("before_position is null");
            return (Criteria) this;
        }

        public Criteria andBeforePositionIsNotNull() {
            addCriterion("before_position is not null");
            return (Criteria) this;
        }

        public Criteria andBeforePositionEqualTo(BigDecimal value) {
            addCriterion("before_position =", value, "beforePosition");
            return (Criteria) this;
        }

        public Criteria andBeforePositionNotEqualTo(BigDecimal value) {
            addCriterion("before_position <>", value, "beforePosition");
            return (Criteria) this;
        }

        public Criteria andBeforePositionGreaterThan(BigDecimal value) {
            addCriterion("before_position >", value, "beforePosition");
            return (Criteria) this;
        }

        public Criteria andBeforePositionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("before_position >=", value, "beforePosition");
            return (Criteria) this;
        }

        public Criteria andBeforePositionLessThan(BigDecimal value) {
            addCriterion("before_position <", value, "beforePosition");
            return (Criteria) this;
        }

        public Criteria andBeforePositionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("before_position <=", value, "beforePosition");
            return (Criteria) this;
        }

        public Criteria andBeforePositionIn(List<BigDecimal> values) {
            addCriterion("before_position in", values, "beforePosition");
            return (Criteria) this;
        }

        public Criteria andBeforePositionNotIn(List<BigDecimal> values) {
            addCriterion("before_position not in", values, "beforePosition");
            return (Criteria) this;
        }

        public Criteria andBeforePositionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_position between", value1, value2, "beforePosition");
            return (Criteria) this;
        }

        public Criteria andBeforePositionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_position not between", value1, value2, "beforePosition");
            return (Criteria) this;
        }

        public Criteria andAfterPositionIsNull() {
            addCriterion("after_position is null");
            return (Criteria) this;
        }

        public Criteria andAfterPositionIsNotNull() {
            addCriterion("after_position is not null");
            return (Criteria) this;
        }

        public Criteria andAfterPositionEqualTo(BigDecimal value) {
            addCriterion("after_position =", value, "afterPosition");
            return (Criteria) this;
        }

        public Criteria andAfterPositionNotEqualTo(BigDecimal value) {
            addCriterion("after_position <>", value, "afterPosition");
            return (Criteria) this;
        }

        public Criteria andAfterPositionGreaterThan(BigDecimal value) {
            addCriterion("after_position >", value, "afterPosition");
            return (Criteria) this;
        }

        public Criteria andAfterPositionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("after_position >=", value, "afterPosition");
            return (Criteria) this;
        }

        public Criteria andAfterPositionLessThan(BigDecimal value) {
            addCriterion("after_position <", value, "afterPosition");
            return (Criteria) this;
        }

        public Criteria andAfterPositionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("after_position <=", value, "afterPosition");
            return (Criteria) this;
        }

        public Criteria andAfterPositionIn(List<BigDecimal> values) {
            addCriterion("after_position in", values, "afterPosition");
            return (Criteria) this;
        }

        public Criteria andAfterPositionNotIn(List<BigDecimal> values) {
            addCriterion("after_position not in", values, "afterPosition");
            return (Criteria) this;
        }

        public Criteria andAfterPositionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_position between", value1, value2, "afterPosition");
            return (Criteria) this;
        }

        public Criteria andAfterPositionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_position not between", value1, value2, "afterPosition");
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

        public Criteria andMakerTakerIsNull() {
            addCriterion("maker_taker is null");
            return (Criteria) this;
        }

        public Criteria andMakerTakerIsNotNull() {
            addCriterion("maker_taker is not null");
            return (Criteria) this;
        }

        public Criteria andMakerTakerEqualTo(Integer value) {
            addCriterion("maker_taker =", value, "makerTaker");
            return (Criteria) this;
        }

        public Criteria andMakerTakerNotEqualTo(Integer value) {
            addCriterion("maker_taker <>", value, "makerTaker");
            return (Criteria) this;
        }

        public Criteria andMakerTakerGreaterThan(Integer value) {
            addCriterion("maker_taker >", value, "makerTaker");
            return (Criteria) this;
        }

        public Criteria andMakerTakerGreaterThanOrEqualTo(Integer value) {
            addCriterion("maker_taker >=", value, "makerTaker");
            return (Criteria) this;
        }

        public Criteria andMakerTakerLessThan(Integer value) {
            addCriterion("maker_taker <", value, "makerTaker");
            return (Criteria) this;
        }

        public Criteria andMakerTakerLessThanOrEqualTo(Integer value) {
            addCriterion("maker_taker <=", value, "makerTaker");
            return (Criteria) this;
        }

        public Criteria andMakerTakerIn(List<Integer> values) {
            addCriterion("maker_taker in", values, "makerTaker");
            return (Criteria) this;
        }

        public Criteria andMakerTakerNotIn(List<Integer> values) {
            addCriterion("maker_taker not in", values, "makerTaker");
            return (Criteria) this;
        }

        public Criteria andMakerTakerBetween(Integer value1, Integer value2) {
            addCriterion("maker_taker between", value1, value2, "makerTaker");
            return (Criteria) this;
        }

        public Criteria andMakerTakerNotBetween(Integer value1, Integer value2) {
            addCriterion("maker_taker not between", value1, value2, "makerTaker");
            return (Criteria) this;
        }

        public Criteria andReferIdIsNull() {
            addCriterion("refer_id is null");
            return (Criteria) this;
        }

        public Criteria andReferIdIsNotNull() {
            addCriterion("refer_id is not null");
            return (Criteria) this;
        }

        public Criteria andReferIdEqualTo(Long value) {
            addCriterion("refer_id =", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdNotEqualTo(Long value) {
            addCriterion("refer_id <>", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdGreaterThan(Long value) {
            addCriterion("refer_id >", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdGreaterThanOrEqualTo(Long value) {
            addCriterion("refer_id >=", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdLessThan(Long value) {
            addCriterion("refer_id <", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdLessThanOrEqualTo(Long value) {
            addCriterion("refer_id <=", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdIn(List<Long> values) {
            addCriterion("refer_id in", values, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdNotIn(List<Long> values) {
            addCriterion("refer_id not in", values, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdBetween(Long value1, Long value2) {
            addCriterion("refer_id between", value1, value2, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdNotBetween(Long value1, Long value2) {
            addCriterion("refer_id not between", value1, value2, "referId");
            return (Criteria) this;
        }

        public Criteria andTradeNoIsNull() {
            addCriterion("trade_no is null");
            return (Criteria) this;
        }

        public Criteria andTradeNoIsNotNull() {
            addCriterion("trade_no is not null");
            return (Criteria) this;
        }

        public Criteria andTradeNoEqualTo(String value) {
            addCriterion("trade_no =", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotEqualTo(String value) {
            addCriterion("trade_no <>", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoGreaterThan(String value) {
            addCriterion("trade_no >", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoGreaterThanOrEqualTo(String value) {
            addCriterion("trade_no >=", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLessThan(String value) {
            addCriterion("trade_no <", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLessThanOrEqualTo(String value) {
            addCriterion("trade_no <=", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLike(String value) {
            addCriterion("trade_no like", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotLike(String value) {
            addCriterion("trade_no not like", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoIn(List<String> values) {
            addCriterion("trade_no in", values, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotIn(List<String> values) {
            addCriterion("trade_no not in", values, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoBetween(String value1, String value2) {
            addCriterion("trade_no between", value1, value2, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotBetween(String value1, String value2) {
            addCriterion("trade_no not between", value1, value2, "tradeNo");
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