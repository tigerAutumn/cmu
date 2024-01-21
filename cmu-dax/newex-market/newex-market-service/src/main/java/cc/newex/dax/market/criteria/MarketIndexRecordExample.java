package cc.newex.dax.market.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询条件example类
 *
 * @author
 * @date 2018/03/18
 */
public class MarketIndexRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MarketIndexRecordExample() {
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

        public Criteria andMarketFromIsNull() {
            this.addCriterion("market_from is null");
            return (Criteria) this;
        }

        public Criteria andMarketFromIsNotNull() {
            this.addCriterion("market_from is not null");
            return (Criteria) this;
        }

        public Criteria andMarketFromEqualTo(final Integer value) {
            this.addCriterion("market_from =", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotEqualTo(final Integer value) {
            this.addCriterion("market_from <>", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromIn(final List<Integer> values) {
            this.addCriterion("market_from in", values, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotIn(final List<Integer> values) {
            this.addCriterion("market_from not in", values, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromBetween(final Integer value1, final Integer value2) {
            this.addCriterion("market_from between", value1, value2, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("market_from not between", value1, value2, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromGreaterThan(final Integer value) {
            this.addCriterion("market_from >", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("market_from >=", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromLessThan(final Integer value) {
            this.addCriterion("market_from <", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromLessThanOrEqualTo(final Integer value) {
            this.addCriterion("market_from <=", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            this.addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            this.addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(final BigDecimal value) {
            this.addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(final BigDecimal value) {
            this.addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(final List<BigDecimal> values) {
            this.addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(final List<BigDecimal> values) {
            this.addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(final BigDecimal value) {
            this.addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(final BigDecimal value) {
            this.addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceIsNull() {
            this.addCriterion("middle_price is null");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceIsNotNull() {
            this.addCriterion("middle_price is not null");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceEqualTo(final BigDecimal value) {
            this.addCriterion("middle_price =", value, "middlePrice");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceNotEqualTo(final BigDecimal value) {
            this.addCriterion("middle_price <>", value, "middlePrice");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceIn(final List<BigDecimal> values) {
            this.addCriterion("middle_price in", values, "middlePrice");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceNotIn(final List<BigDecimal> values) {
            this.addCriterion("middle_price not in", values, "middlePrice");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("middle_price between", value1, value2, "middlePrice");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("middle_price not between", value1, value2, "middlePrice");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceGreaterThan(final BigDecimal value) {
            this.addCriterion("middle_price >", value, "middlePrice");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("middle_price >=", value, "middlePrice");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceLessThan(final BigDecimal value) {
            this.addCriterion("middle_price <", value, "middlePrice");
            return (Criteria) this;
        }

        public Criteria andMiddlePriceLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("middle_price <=", value, "middlePrice");
            return (Criteria) this;
        }

        public Criteria andInvalidIsNull() {
            this.addCriterion("invalid is null");
            return (Criteria) this;
        }

        public Criteria andInvalidIsNotNull() {
            this.addCriterion("invalid is not null");
            return (Criteria) this;
        }

        public Criteria andInvalidEqualTo(final Integer value) {
            this.addCriterion("invalid =", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidNotEqualTo(final Integer value) {
            this.addCriterion("invalid <>", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidIn(final List<Integer> values) {
            this.addCriterion("invalid in", values, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidNotIn(final List<Integer> values) {
            this.addCriterion("invalid not in", values, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidBetween(final Integer value1, final Integer value2) {
            this.addCriterion("invalid between", value1, value2, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("invalid not between", value1, value2, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidGreaterThan(final Integer value) {
            this.addCriterion("invalid >", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("invalid >=", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidLessThan(final Integer value) {
            this.addCriterion("invalid <", value, "invalid");
            return (Criteria) this;
        }

        public Criteria andInvalidLessThanOrEqualTo(final Integer value) {
            this.addCriterion("invalid <=", value, "invalid");
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

    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andFieldLike(final String fieldName, final String keyword) {
            this.addCriterion(fieldName + " like ", keyword, fieldName);
            return this;
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