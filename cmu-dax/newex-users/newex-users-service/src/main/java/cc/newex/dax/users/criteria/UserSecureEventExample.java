package cc.newex.dax.users.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户安全事件表 查询条件example类
 *
 * @author newex-team
 * @date 2018-04-15
 */
public class UserSecureEventExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserSecureEventExample() {
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

        public Criteria andBehaviorIdIsNull() {
            this.addCriterion("behavior_id is null");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdIsNotNull() {
            this.addCriterion("behavior_id is not null");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdEqualTo(final Integer value) {
            this.addCriterion("behavior_id =", value, "behaviorId");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdNotEqualTo(final Integer value) {
            this.addCriterion("behavior_id <>", value, "behaviorId");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdIn(final List<Integer> values) {
            this.addCriterion("behavior_id in", values, "behaviorId");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdNotIn(final List<Integer> values) {
            this.addCriterion("behavior_id not in", values, "behaviorId");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("behavior_id between", value1, value2, "behaviorId");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("behavior_id not between", value1, value2, "behaviorId");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdGreaterThan(final Integer value) {
            this.addCriterion("behavior_id >", value, "behaviorId");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("behavior_id >=", value, "behaviorId");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdLessThan(final Integer value) {
            this.addCriterion("behavior_id <", value, "behaviorId");
            return (Criteria) this;
        }

        public Criteria andBehaviorIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("behavior_id <=", value, "behaviorId");
            return (Criteria) this;
        }

        public Criteria andBehaviorNameIsNull() {
            this.addCriterion("behavior_name is null");
            return (Criteria) this;
        }

        public Criteria andBehaviorNameIsNotNull() {
            this.addCriterion("behavior_name is not null");
            return (Criteria) this;
        }

        public Criteria andBehaviorNameEqualTo(final String value) {
            this.addCriterion("behavior_name =", value, "behaviorName");
            return (Criteria) this;
        }

        public Criteria andBehaviorNameNotEqualTo(final String value) {
            this.addCriterion("behavior_name <>", value, "behaviorName");
            return (Criteria) this;
        }

        public Criteria andBehaviorNameIn(final List<String> values) {
            this.addCriterion("behavior_name in", values, "behaviorName");
            return (Criteria) this;
        }

        public Criteria andBehaviorNameNotIn(final List<String> values) {
            this.addCriterion("behavior_name not in", values, "behaviorName");
            return (Criteria) this;
        }

        public Criteria andBehaviorNameBetween(final String value1, final String value2) {
            this.addCriterion("behavior_name between", value1, value2, "behaviorName");
            return (Criteria) this;
        }

        public Criteria andBehaviorNameNotBetween(final String value1, final String value2) {
            this.addCriterion("behavior_name not between", value1, value2, "behaviorName");
            return (Criteria) this;
        }


        public Criteria andBehaviorNameLike(final String value) {
            this.addCriterion("behavior_name like", value, "behaviorName");
            return (Criteria) this;
        }

        public Criteria andBehaviorNameNotLike(final String value) {
            this.addCriterion("behavior_name not like", value, "behaviorName");
            return (Criteria) this;
        }

        public Criteria andParamsIsNull() {
            this.addCriterion("params is null");
            return (Criteria) this;
        }

        public Criteria andParamsIsNotNull() {
            this.addCriterion("params is not null");
            return (Criteria) this;
        }

        public Criteria andParamsEqualTo(final String value) {
            this.addCriterion("params =", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotEqualTo(final String value) {
            this.addCriterion("params <>", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsIn(final List<String> values) {
            this.addCriterion("params in", values, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotIn(final List<String> values) {
            this.addCriterion("params not in", values, "params");
            return (Criteria) this;
        }

        public Criteria andParamsBetween(final String value1, final String value2) {
            this.addCriterion("params between", value1, value2, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotBetween(final String value1, final String value2) {
            this.addCriterion("params not between", value1, value2, "params");
            return (Criteria) this;
        }


        public Criteria andParamsLike(final String value) {
            this.addCriterion("params like", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotLike(final String value) {
            this.addCriterion("params not like", value, "params");
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

        public Criteria andMessageIsNull() {
            this.addCriterion("message is null");
            return (Criteria) this;
        }

        public Criteria andMessageIsNotNull() {
            this.addCriterion("message is not null");
            return (Criteria) this;
        }

        public Criteria andMessageEqualTo(final String value) {
            this.addCriterion("message =", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotEqualTo(final String value) {
            this.addCriterion("message <>", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageIn(final List<String> values) {
            this.addCriterion("message in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotIn(final List<String> values) {
            this.addCriterion("message not in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageBetween(final String value1, final String value2) {
            this.addCriterion("message between", value1, value2, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotBetween(final String value1, final String value2) {
            this.addCriterion("message not between", value1, value2, "message");
            return (Criteria) this;
        }


        public Criteria andMessageLike(final String value) {
            this.addCriterion("message like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotLike(final String value) {
            this.addCriterion("message not like", value, "message");
            return (Criteria) this;
        }

        public Criteria andIpAddressIsNull() {
            this.addCriterion("ip_address is null");
            return (Criteria) this;
        }

        public Criteria andIpAddressIsNotNull() {
            this.addCriterion("ip_address is not null");
            return (Criteria) this;
        }

        public Criteria andIpAddressEqualTo(final Long value) {
            this.addCriterion("ip_address =", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotEqualTo(final Long value) {
            this.addCriterion("ip_address <>", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressIn(final List<Long> values) {
            this.addCriterion("ip_address in", values, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotIn(final List<Long> values) {
            this.addCriterion("ip_address not in", values, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressBetween(final Long value1, final Long value2) {
            this.addCriterion("ip_address between", value1, value2, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotBetween(final Long value1, final Long value2) {
            this.addCriterion("ip_address not between", value1, value2, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressGreaterThan(final Long value) {
            this.addCriterion("ip_address >", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("ip_address >=", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLessThan(final Long value) {
            this.addCriterion("ip_address <", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLessThanOrEqualTo(final Long value) {
            this.addCriterion("ip_address <=", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andRegionIsNull() {
            this.addCriterion("region is null");
            return (Criteria) this;
        }

        public Criteria andRegionIsNotNull() {
            this.addCriterion("region is not null");
            return (Criteria) this;
        }

        public Criteria andRegionEqualTo(final String value) {
            this.addCriterion("region =", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotEqualTo(final String value) {
            this.addCriterion("region <>", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionIn(final List<String> values) {
            this.addCriterion("region in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotIn(final List<String> values) {
            this.addCriterion("region not in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionBetween(final String value1, final String value2) {
            this.addCriterion("region between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotBetween(final String value1, final String value2) {
            this.addCriterion("region not between", value1, value2, "region");
            return (Criteria) this;
        }


        public Criteria andRegionLike(final String value) {
            this.addCriterion("region like", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotLike(final String value) {
            this.addCriterion("region not like", value, "region");
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