package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author newex-team
 * @date 2019-01-07 21:55:01
 */
public class MarkPriceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MarkPriceExample() {
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

        public Criteria andQuoteCurrencyIsNull() {
            addCriterion("quote_currency is null");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyIsNotNull() {
            addCriterion("quote_currency is not null");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyEqualTo(String value) {
            addCriterion("quote_currency =", value, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyNotEqualTo(String value) {
            addCriterion("quote_currency <>", value, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyGreaterThan(String value) {
            addCriterion("quote_currency >", value, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("quote_currency >=", value, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyLessThan(String value) {
            addCriterion("quote_currency <", value, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyLessThanOrEqualTo(String value) {
            addCriterion("quote_currency <=", value, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyLike(String value) {
            addCriterion("quote_currency like", value, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyNotLike(String value) {
            addCriterion("quote_currency not like", value, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyIn(List<String> values) {
            addCriterion("quote_currency in", values, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyNotIn(List<String> values) {
            addCriterion("quote_currency not in", values, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyBetween(String value1, String value2) {
            addCriterion("quote_currency between", value1, value2, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andQuoteCurrencyNotBetween(String value1, String value2) {
            addCriterion("quote_currency not between", value1, value2, "quoteCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyIsNull() {
            addCriterion("base_currency is null");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyIsNotNull() {
            addCriterion("base_currency is not null");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyEqualTo(String value) {
            addCriterion("base_currency =", value, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyNotEqualTo(String value) {
            addCriterion("base_currency <>", value, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyGreaterThan(String value) {
            addCriterion("base_currency >", value, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("base_currency >=", value, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyLessThan(String value) {
            addCriterion("base_currency <", value, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyLessThanOrEqualTo(String value) {
            addCriterion("base_currency <=", value, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyLike(String value) {
            addCriterion("base_currency like", value, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyNotLike(String value) {
            addCriterion("base_currency not like", value, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyIn(List<String> values) {
            addCriterion("base_currency in", values, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyNotIn(List<String> values) {
            addCriterion("base_currency not in", values, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyBetween(String value1, String value2) {
            addCriterion("base_currency between", value1, value2, "baseCurrency");
            return (Criteria) this;
        }

        public Criteria andBaseCurrencyNotBetween(String value1, String value2) {
            addCriterion("base_currency not between", value1, value2, "baseCurrency");
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

        public Criteria andIndexPriceIsNull() {
            addCriterion("index_price is null");
            return (Criteria) this;
        }

        public Criteria andIndexPriceIsNotNull() {
            addCriterion("index_price is not null");
            return (Criteria) this;
        }

        public Criteria andIndexPriceEqualTo(BigDecimal value) {
            addCriterion("index_price =", value, "indexPrice");
            return (Criteria) this;
        }

        public Criteria andIndexPriceNotEqualTo(BigDecimal value) {
            addCriterion("index_price <>", value, "indexPrice");
            return (Criteria) this;
        }

        public Criteria andIndexPriceGreaterThan(BigDecimal value) {
            addCriterion("index_price >", value, "indexPrice");
            return (Criteria) this;
        }

        public Criteria andIndexPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("index_price >=", value, "indexPrice");
            return (Criteria) this;
        }

        public Criteria andIndexPriceLessThan(BigDecimal value) {
            addCriterion("index_price <", value, "indexPrice");
            return (Criteria) this;
        }

        public Criteria andIndexPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("index_price <=", value, "indexPrice");
            return (Criteria) this;
        }

        public Criteria andIndexPriceIn(List<BigDecimal> values) {
            addCriterion("index_price in", values, "indexPrice");
            return (Criteria) this;
        }

        public Criteria andIndexPriceNotIn(List<BigDecimal> values) {
            addCriterion("index_price not in", values, "indexPrice");
            return (Criteria) this;
        }

        public Criteria andIndexPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("index_price between", value1, value2, "indexPrice");
            return (Criteria) this;
        }

        public Criteria andIndexPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("index_price not between", value1, value2, "indexPrice");
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

        public Criteria andEstimateFeeRateIsNull() {
            addCriterion("estimate_fee_rate is null");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateIsNotNull() {
            addCriterion("estimate_fee_rate is not null");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateEqualTo(BigDecimal value) {
            addCriterion("estimate_fee_rate =", value, "estimateFeeRate");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateNotEqualTo(BigDecimal value) {
            addCriterion("estimate_fee_rate <>", value, "estimateFeeRate");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateGreaterThan(BigDecimal value) {
            addCriterion("estimate_fee_rate >", value, "estimateFeeRate");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("estimate_fee_rate >=", value, "estimateFeeRate");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateLessThan(BigDecimal value) {
            addCriterion("estimate_fee_rate <", value, "estimateFeeRate");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("estimate_fee_rate <=", value, "estimateFeeRate");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateIn(List<BigDecimal> values) {
            addCriterion("estimate_fee_rate in", values, "estimateFeeRate");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateNotIn(List<BigDecimal> values) {
            addCriterion("estimate_fee_rate not in", values, "estimateFeeRate");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("estimate_fee_rate between", value1, value2, "estimateFeeRate");
            return (Criteria) this;
        }

        public Criteria andEstimateFeeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("estimate_fee_rate not between", value1, value2, "estimateFeeRate");
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