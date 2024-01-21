package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 溢价指数流水表 查询条件类
 *
 * @author newex-team
 * @date 2018-11-20 18:26:48
 */
public class HistoryMarkPriceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HistoryMarkPriceExample() {
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

        public Criteria andTimeIndexIsNull() {
            addCriterion("time_index is null");
            return (Criteria) this;
        }

        public Criteria andTimeIndexIsNotNull() {
            addCriterion("time_index is not null");
            return (Criteria) this;
        }

        public Criteria andTimeIndexEqualTo(Date value) {
            addCriterion("time_index =", value, "timeIndex");
            return (Criteria) this;
        }

        public Criteria andTimeIndexNotEqualTo(Date value) {
            addCriterion("time_index <>", value, "timeIndex");
            return (Criteria) this;
        }

        public Criteria andTimeIndexGreaterThan(Date value) {
            addCriterion("time_index >", value, "timeIndex");
            return (Criteria) this;
        }

        public Criteria andTimeIndexGreaterThanOrEqualTo(Date value) {
            addCriterion("time_index >=", value, "timeIndex");
            return (Criteria) this;
        }

        public Criteria andTimeIndexLessThan(Date value) {
            addCriterion("time_index <", value, "timeIndex");
            return (Criteria) this;
        }

        public Criteria andTimeIndexLessThanOrEqualTo(Date value) {
            addCriterion("time_index <=", value, "timeIndex");
            return (Criteria) this;
        }

        public Criteria andTimeIndexIn(List<Date> values) {
            addCriterion("time_index in", values, "timeIndex");
            return (Criteria) this;
        }

        public Criteria andTimeIndexNotIn(List<Date> values) {
            addCriterion("time_index not in", values, "timeIndex");
            return (Criteria) this;
        }

        public Criteria andTimeIndexBetween(Date value1, Date value2) {
            addCriterion("time_index between", value1, value2, "timeIndex");
            return (Criteria) this;
        }

        public Criteria andTimeIndexNotBetween(Date value1, Date value2) {
            addCriterion("time_index not between", value1, value2, "timeIndex");
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

        public Criteria andPremiumIndexIsNull() {
            addCriterion("premium_index is null");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexIsNotNull() {
            addCriterion("premium_index is not null");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexEqualTo(BigDecimal value) {
            addCriterion("premium_index =", value, "premiumIndex");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexNotEqualTo(BigDecimal value) {
            addCriterion("premium_index <>", value, "premiumIndex");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexGreaterThan(BigDecimal value) {
            addCriterion("premium_index >", value, "premiumIndex");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("premium_index >=", value, "premiumIndex");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexLessThan(BigDecimal value) {
            addCriterion("premium_index <", value, "premiumIndex");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexLessThanOrEqualTo(BigDecimal value) {
            addCriterion("premium_index <=", value, "premiumIndex");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexIn(List<BigDecimal> values) {
            addCriterion("premium_index in", values, "premiumIndex");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexNotIn(List<BigDecimal> values) {
            addCriterion("premium_index not in", values, "premiumIndex");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("premium_index between", value1, value2, "premiumIndex");
            return (Criteria) this;
        }

        public Criteria andPremiumIndexNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("premium_index not between", value1, value2, "premiumIndex");
            return (Criteria) this;
        }

        public Criteria andAskPriceIsNull() {
            addCriterion("ask_price is null");
            return (Criteria) this;
        }

        public Criteria andAskPriceIsNotNull() {
            addCriterion("ask_price is not null");
            return (Criteria) this;
        }

        public Criteria andAskPriceEqualTo(BigDecimal value) {
            addCriterion("ask_price =", value, "askPrice");
            return (Criteria) this;
        }

        public Criteria andAskPriceNotEqualTo(BigDecimal value) {
            addCriterion("ask_price <>", value, "askPrice");
            return (Criteria) this;
        }

        public Criteria andAskPriceGreaterThan(BigDecimal value) {
            addCriterion("ask_price >", value, "askPrice");
            return (Criteria) this;
        }

        public Criteria andAskPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ask_price >=", value, "askPrice");
            return (Criteria) this;
        }

        public Criteria andAskPriceLessThan(BigDecimal value) {
            addCriterion("ask_price <", value, "askPrice");
            return (Criteria) this;
        }

        public Criteria andAskPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ask_price <=", value, "askPrice");
            return (Criteria) this;
        }

        public Criteria andAskPriceIn(List<BigDecimal> values) {
            addCriterion("ask_price in", values, "askPrice");
            return (Criteria) this;
        }

        public Criteria andAskPriceNotIn(List<BigDecimal> values) {
            addCriterion("ask_price not in", values, "askPrice");
            return (Criteria) this;
        }

        public Criteria andAskPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ask_price between", value1, value2, "askPrice");
            return (Criteria) this;
        }

        public Criteria andAskPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ask_price not between", value1, value2, "askPrice");
            return (Criteria) this;
        }

        public Criteria andBidPriceIsNull() {
            addCriterion("bid_price is null");
            return (Criteria) this;
        }

        public Criteria andBidPriceIsNotNull() {
            addCriterion("bid_price is not null");
            return (Criteria) this;
        }

        public Criteria andBidPriceEqualTo(BigDecimal value) {
            addCriterion("bid_price =", value, "bidPrice");
            return (Criteria) this;
        }

        public Criteria andBidPriceNotEqualTo(BigDecimal value) {
            addCriterion("bid_price <>", value, "bidPrice");
            return (Criteria) this;
        }

        public Criteria andBidPriceGreaterThan(BigDecimal value) {
            addCriterion("bid_price >", value, "bidPrice");
            return (Criteria) this;
        }

        public Criteria andBidPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bid_price >=", value, "bidPrice");
            return (Criteria) this;
        }

        public Criteria andBidPriceLessThan(BigDecimal value) {
            addCriterion("bid_price <", value, "bidPrice");
            return (Criteria) this;
        }

        public Criteria andBidPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bid_price <=", value, "bidPrice");
            return (Criteria) this;
        }

        public Criteria andBidPriceIn(List<BigDecimal> values) {
            addCriterion("bid_price in", values, "bidPrice");
            return (Criteria) this;
        }

        public Criteria andBidPriceNotIn(List<BigDecimal> values) {
            addCriterion("bid_price not in", values, "bidPrice");
            return (Criteria) this;
        }

        public Criteria andBidPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bid_price between", value1, value2, "bidPrice");
            return (Criteria) this;
        }

        public Criteria andBidPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bid_price not between", value1, value2, "bidPrice");
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