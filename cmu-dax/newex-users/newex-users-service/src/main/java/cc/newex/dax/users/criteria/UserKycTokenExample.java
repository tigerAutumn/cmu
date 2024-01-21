package cc.newex.dax.users.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户kyc等级 查询条件example类
 *
 * @author newex-team
 * @date 2018-05-11
 */
public class UserKycTokenExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserKycTokenExample() {
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

        public Criteria andTokenIsNull() {
            this.addCriterion("token is null");
            return (Criteria) this;
        }

        public Criteria andTokenIsNotNull() {
            this.addCriterion("token is not null");
            return (Criteria) this;
        }

        public Criteria andTokenEqualTo(final String value) {
            this.addCriterion("token =", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotEqualTo(final String value) {
            this.addCriterion("token <>", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenIn(final List<String> values) {
            this.addCriterion("token in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotIn(final List<String> values) {
            this.addCriterion("token not in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenBetween(final String value1, final String value2) {
            this.addCriterion("token between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotBetween(final String value1, final String value2) {
            this.addCriterion("token not between", value1, value2, "token");
            return (Criteria) this;
        }


        public Criteria andTokenLike(final String value) {
            this.addCriterion("token like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotLike(final String value) {
            this.addCriterion("token not like", value, "token");
            return (Criteria) this;
        }

        public Criteria andBizIdIsNull() {
            this.addCriterion("biz_id is null");
            return (Criteria) this;
        }

        public Criteria andBizIdIsNotNull() {
            this.addCriterion("biz_id is not null");
            return (Criteria) this;
        }

        public Criteria andBizIdEqualTo(final String value) {
            this.addCriterion("biz_id =", value, "bizId");
            return (Criteria) this;
        }

        public Criteria andBizIdNotEqualTo(final String value) {
            this.addCriterion("biz_id <>", value, "bizId");
            return (Criteria) this;
        }

        public Criteria andBizIdIn(final List<String> values) {
            this.addCriterion("biz_id in", values, "bizId");
            return (Criteria) this;
        }

        public Criteria andBizIdNotIn(final List<String> values) {
            this.addCriterion("biz_id not in", values, "bizId");
            return (Criteria) this;
        }

        public Criteria andBizIdBetween(final String value1, final String value2) {
            this.addCriterion("biz_id between", value1, value2, "bizId");
            return (Criteria) this;
        }

        public Criteria andBizIdNotBetween(final String value1, final String value2) {
            this.addCriterion("biz_id not between", value1, value2, "bizId");
            return (Criteria) this;
        }


        public Criteria andBizIdLike(final String value) {
            this.addCriterion("biz_id like", value, "bizId");
            return (Criteria) this;
        }

        public Criteria andBizIdNotLike(final String value) {
            this.addCriterion("biz_id not like", value, "bizId");
            return (Criteria) this;
        }

        public Criteria andReturnResultIsNull() {
            this.addCriterion("return_result is null");
            return (Criteria) this;
        }

        public Criteria andReturnResultIsNotNull() {
            this.addCriterion("return_result is not null");
            return (Criteria) this;
        }

        public Criteria andReturnResultEqualTo(final String value) {
            this.addCriterion("return_result =", value, "returnResult");
            return (Criteria) this;
        }

        public Criteria andReturnResultNotEqualTo(final String value) {
            this.addCriterion("return_result <>", value, "returnResult");
            return (Criteria) this;
        }

        public Criteria andReturnResultIn(final List<String> values) {
            this.addCriterion("return_result in", values, "returnResult");
            return (Criteria) this;
        }

        public Criteria andReturnResultNotIn(final List<String> values) {
            this.addCriterion("return_result not in", values, "returnResult");
            return (Criteria) this;
        }

        public Criteria andReturnResultBetween(final String value1, final String value2) {
            this.addCriterion("return_result between", value1, value2, "returnResult");
            return (Criteria) this;
        }

        public Criteria andReturnResultNotBetween(final String value1, final String value2) {
            this.addCriterion("return_result not between", value1, value2, "returnResult");
            return (Criteria) this;
        }


        public Criteria andReturnResultLike(final String value) {
            this.addCriterion("return_result like", value, "returnResult");
            return (Criteria) this;
        }

        public Criteria andReturnResultNotLike(final String value) {
            this.addCriterion("return_result not like", value, "returnResult");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            this.addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            this.addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(final String value) {
            this.addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(final String value) {
            this.addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(final List<String> values) {
            this.addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(final List<String> values) {
            this.addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(final String value1, final String value2) {
            this.addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(final String value1, final String value2) {
            this.addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }


        public Criteria andRemarksLike(final String value) {
            this.addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(final String value) {
            this.addCriterion("remarks not like", value, "remarks");
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

        public Criteria andUpdatedDateIsNull() {
            this.addCriterion("updated_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNotNull() {
            this.addCriterion("updated_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateEqualTo(final Date value) {
            this.addCriterion("updated_date =", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotEqualTo(final Date value) {
            this.addCriterion("updated_date <>", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIn(final List<Date> values) {
            this.addCriterion("updated_date in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotIn(final List<Date> values) {
            this.addCriterion("updated_date not in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateBetween(final Date value1, final Date value2) {
            this.addCriterion("updated_date between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotBetween(final Date value1, final Date value2) {
            this.addCriterion("updated_date not between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThan(final Date value) {
            this.addCriterion("updated_date >", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("updated_date >=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThan(final Date value) {
            this.addCriterion("updated_date <", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThanOrEqualTo(final Date value) {
            this.addCriterion("updated_date <=", value, "updatedDate");
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
            this.addCriterion(fieldName + " = ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <> ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldIn(final String fieldName, final List<Object> values) {
            this.addCriterion(fieldName + " in ", values, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(final String fieldName, final List<Object> values) {
            this.addCriterion(fieldName + " not in ", values, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            this.addCriterion(fieldName + " between ", fieldValue1, fieldValue2, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            this.addCriterion(fieldName + "  not between ", fieldValue1, fieldValue2, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " > ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " >= ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " < ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <= ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldLike(final String fieldName, final String fieldValue) {
            this.addCriterion(fieldName + " like ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(final String fieldName, final String fieldValue) {
            this.addCriterion(fieldName + "  not like ", fieldValue, fieldName);
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
