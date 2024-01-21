package cc.newex.dax.market.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询条件example类
 *
 * @author newex-team
 * @date 2018/03/18
 */
public class MarketAllRateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MarketAllRateExample() {
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

        protected void addCriterion(final String condition, final Object value1, final Object value2,
                                    final String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIDIsNull() {
            this.addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIDIsNotNull() {
            this.addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIDEqualTo(final Long value) {
            this.addCriterion("ID =", value, "iD");
            return (Criteria) this;
        }

        public Criteria andIDNotEqualTo(final Long value) {
            this.addCriterion("ID <>", value, "iD");
            return (Criteria) this;
        }

        public Criteria andIDIn(final List<Long> values) {
            this.addCriterion("ID in", values, "iD");
            return (Criteria) this;
        }

        public Criteria andIDNotIn(final List<Long> values) {
            this.addCriterion("ID not in", values, "iD");
            return (Criteria) this;
        }

        public Criteria andIDBetween(final Long value1, final Long value2) {
            this.addCriterion("ID between", value1, value2, "iD");
            return (Criteria) this;
        }

        public Criteria andIDNotBetween(final Long value1, final Long value2) {
            this.addCriterion("ID not between", value1, value2, "iD");
            return (Criteria) this;
        }

        public Criteria andIDGreaterThan(final Long value) {
            this.addCriterion("ID >", value, "iD");
            return (Criteria) this;
        }

        public Criteria andIDGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("ID >=", value, "iD");
            return (Criteria) this;
        }

        public Criteria andIDLessThan(final Long value) {
            this.addCriterion("ID <", value, "iD");
            return (Criteria) this;
        }

        public Criteria andIDLessThanOrEqualTo(final Long value) {
            this.addCriterion("ID <=", value, "iD");
            return (Criteria) this;
        }

        public Criteria andRateNameIsNull() {
            this.addCriterion("rate_name is null");
            return (Criteria) this;
        }

        public Criteria andRateNameIsNotNull() {
            this.addCriterion("rate_name is not null");
            return (Criteria) this;
        }

        public Criteria andRateNameEqualTo(final String value) {
            this.addCriterion("rate_name =", value, "rateName");
            return (Criteria) this;
        }

        public Criteria andRateNameNotEqualTo(final String value) {
            this.addCriterion("rate_name <>", value, "rateName");
            return (Criteria) this;
        }

        public Criteria andRateNameIn(final List<String> values) {
            this.addCriterion("rate_name in", values, "rateName");
            return (Criteria) this;
        }

        public Criteria andRateNameNotIn(final List<String> values) {
            this.addCriterion("rate_name not in", values, "rateName");
            return (Criteria) this;
        }

        public Criteria andRateNameBetween(final String value1, final String value2) {
            this.addCriterion("rate_name between", value1, value2, "rateName");
            return (Criteria) this;
        }

        public Criteria andRateNameNotBetween(final String value1, final String value2) {
            this.addCriterion("rate_name not between", value1, value2, "rateName");
            return (Criteria) this;
        }

        public Criteria andRateNameLike(final String value) {
            this.addCriterion("rate_name like", value, "rateName");
            return (Criteria) this;
        }

        public Criteria andRateNameNotLike(final String value) {
            this.addCriterion("rate_name not like", value, "rateName");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            this.addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            this.addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(final Date value) {
            this.addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(final Date value) {
            this.addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(final List<Date> values) {
            this.addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(final List<Date> values) {
            this.addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(final Date value) {
            this.addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(final Date value) {
            this.addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("modify_time <=", value, "modifyTime");
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

        public Criteria andCreateTimeIsNull() {
            this.addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            this.addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(final Date value) {
            this.addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(final Date value) {
            this.addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(final List<Date> values) {
            this.addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(final List<Date> values) {
            this.addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(final Date value) {
            this.addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(final Date value) {
            this.addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andRateParitiesIsNull() {
            this.addCriterion("rate_ parities is null");
            return (Criteria) this;
        }

        public Criteria andRateParitiesIsNotNull() {
            this.addCriterion("rate_ parities is not null");
            return (Criteria) this;
        }

        public Criteria andRateParitiesEqualTo(final Double value) {
            this.addCriterion("rate_ parities =", value, "rateParities");
            return (Criteria) this;
        }

        public Criteria andRateParitiesNotEqualTo(final Double value) {
            this.addCriterion("rate_ parities <>", value, "rateParities");
            return (Criteria) this;
        }

        public Criteria andRateParitiesIn(final List<Double> values) {
            this.addCriterion("rate_ parities in", values, "rateParities");
            return (Criteria) this;
        }

        public Criteria andRateParitiesNotIn(final List<Double> values) {
            this.addCriterion("rate_ parities not in", values, "rateParities");
            return (Criteria) this;
        }

        public Criteria andRateParitiesBetween(final Double value1, final Double value2) {
            this.addCriterion("rate_ parities between", value1, value2, "rateParities");
            return (Criteria) this;
        }

        public Criteria andRateParitiesNotBetween(final Double value1, final Double value2) {
            this.addCriterion("rate_ parities not between", value1, value2, "rateParities");
            return (Criteria) this;
        }

        public Criteria andRateParitiesGreaterThan(final Double value) {
            this.addCriterion("rate_ parities >", value, "rateParities");
            return (Criteria) this;
        }

        public Criteria andRateParitiesGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("rate_ parities >=", value, "rateParities");
            return (Criteria) this;
        }

        public Criteria andRateParitiesLessThan(final Double value) {
            this.addCriterion("rate_ parities <", value, "rateParities");
            return (Criteria) this;
        }

        public Criteria andRateParitiesLessThanOrEqualTo(final Double value) {
            this.addCriterion("rate_ parities <=", value, "rateParities");
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

        protected Criterion(final String condition, final Object value, final Object secondValue,
                            final String typeHandler) {
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