package cc.newex.wallet.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询条件example类
 *
 * @author newex-team
 * @date 2018-05-02
 */
public class BestBlockHeightExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BestBlockHeightExample() {
        this.oredCriteria = new ArrayList<>();
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setOrderByClause(final String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public void setDistinct(final boolean distinct) {
        this.distinct = distinct;
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

        public Criteria andIdEqualTo(final Integer value) {
            this.addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(final List<Integer> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(final List<Integer> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNull() {
            this.addCriterion("currency is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNotNull() {
            this.addCriterion("currency is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyEqualTo(final Integer value) {
            this.addCriterion("currency =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(final Integer value) {
            this.addCriterion("currency <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(final List<Integer> values) {
            this.addCriterion("currency in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(final List<Integer> values) {
            this.addCriterion("currency not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(final Integer value1, final Integer value2) {
            this.addCriterion("currency between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("currency not between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(final Integer value) {
            this.addCriterion("currency >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("currency >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(final Integer value) {
            this.addCriterion("currency <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(final Integer value) {
            this.addCriterion("currency <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andHeightIsNull() {
            this.addCriterion("height is null");
            return (Criteria) this;
        }

        public Criteria andHeightIsNotNull() {
            this.addCriterion("height is not null");
            return (Criteria) this;
        }

        public Criteria andHeightEqualTo(final Long value) {
            this.addCriterion("height =", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotEqualTo(final Long value) {
            this.addCriterion("height <>", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightIn(final List<Long> values) {
            this.addCriterion("height in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotIn(final List<Long> values) {
            this.addCriterion("height not in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightBetween(final Long value1, final Long value2) {
            this.addCriterion("height between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotBetween(final Long value1, final Long value2) {
            this.addCriterion("height not between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThan(final Long value) {
            this.addCriterion("height >", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("height >=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThan(final Long value) {
            this.addCriterion("height <", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThanOrEqualTo(final Long value) {
            this.addCriterion("height <=", value, "height");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeIsNull() {
            this.addCriterion("interval_time is null");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeIsNotNull() {
            this.addCriterion("interval_time is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeEqualTo(final Long value) {
            this.addCriterion("interval_time =", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeNotEqualTo(final Long value) {
            this.addCriterion("interval_time <>", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeIn(final List<Long> values) {
            this.addCriterion("interval_time in", values, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeNotIn(final List<Long> values) {
            this.addCriterion("interval_time not in", values, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeBetween(final Long value1, final Long value2) {
            this.addCriterion("interval_time between", value1, value2, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeNotBetween(final Long value1, final Long value2) {
            this.addCriterion("interval_time not between", value1, value2, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeGreaterThan(final Long value) {
            this.addCriterion("interval_time >", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("interval_time >=", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeLessThan(final Long value) {
            this.addCriterion("interval_time <", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeLessThanOrEqualTo(final Long value) {
            this.addCriterion("interval_time <=", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            this.addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            this.addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(final Date value) {
            this.addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(final Date value) {
            this.addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(final List<Date> values) {
            this.addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(final List<Date> values) {
            this.addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(final Date value1, final Date value2) {
            this.addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(final Date value1, final Date value2) {
            this.addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(final Date value) {
            this.addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(final Date value) {
            this.addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(final Date value) {
            this.addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            this.addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            this.addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(final Date value) {
            this.addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(final Date value) {
            this.addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(final List<Date> values) {
            this.addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(final List<Date> values) {
            this.addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(final Date value1, final Date value2) {
            this.addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(final Date value1, final Date value2) {
            this.addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(final Date value) {
            this.addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(final Date value) {
            this.addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(final Date value) {
            this.addCriterion("update_date <=", value, "updateDate");
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
        private final String typeHandler;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;

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
    }
}