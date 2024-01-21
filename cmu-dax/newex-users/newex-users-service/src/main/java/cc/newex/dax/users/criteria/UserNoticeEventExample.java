package cc.newex.dax.users.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户通知事件表 查询条件example类
 *
 * @author newex-team
 * @date 2018-04-15
 */
public class UserNoticeEventExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserNoticeEventExample() {
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

        public Criteria andTypeIsNull() {
            this.addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            this.addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(final Integer value) {
            this.addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(final Integer value) {
            this.addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(final List<Integer> values) {
            this.addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(final List<Integer> values) {
            this.addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(final Integer value) {
            this.addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(final Integer value) {
            this.addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeIsNull() {
            this.addCriterion("template_code is null");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeIsNotNull() {
            this.addCriterion("template_code is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeEqualTo(final String value) {
            this.addCriterion("template_code =", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotEqualTo(final String value) {
            this.addCriterion("template_code <>", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeIn(final List<String> values) {
            this.addCriterion("template_code in", values, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotIn(final List<String> values) {
            this.addCriterion("template_code not in", values, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeBetween(final String value1, final String value2) {
            this.addCriterion("template_code between", value1, value2, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotBetween(final String value1, final String value2) {
            this.addCriterion("template_code not between", value1, value2, "templateCode");
            return (Criteria) this;
        }


        public Criteria andTemplateCodeLike(final String value) {
            this.addCriterion("template_code like", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotLike(final String value) {
            this.addCriterion("template_code not like", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTargetIsNull() {
            this.addCriterion("target is null");
            return (Criteria) this;
        }

        public Criteria andTargetIsNotNull() {
            this.addCriterion("target is not null");
            return (Criteria) this;
        }

        public Criteria andTargetEqualTo(final String value) {
            this.addCriterion("target =", value, "target");
            return (Criteria) this;
        }

        public Criteria andTargetNotEqualTo(final String value) {
            this.addCriterion("target <>", value, "target");
            return (Criteria) this;
        }

        public Criteria andTargetIn(final List<String> values) {
            this.addCriterion("target in", values, "target");
            return (Criteria) this;
        }

        public Criteria andTargetNotIn(final List<String> values) {
            this.addCriterion("target not in", values, "target");
            return (Criteria) this;
        }

        public Criteria andTargetBetween(final String value1, final String value2) {
            this.addCriterion("target between", value1, value2, "target");
            return (Criteria) this;
        }

        public Criteria andTargetNotBetween(final String value1, final String value2) {
            this.addCriterion("target not between", value1, value2, "target");
            return (Criteria) this;
        }


        public Criteria andTargetLike(final String value) {
            this.addCriterion("target like", value, "target");
            return (Criteria) this;
        }

        public Criteria andTargetNotLike(final String value) {
            this.addCriterion("target not like", value, "target");
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

        public Criteria andChannelIsNull() {
            this.addCriterion("channel is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            this.addCriterion("channel is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(final Integer value) {
            this.addCriterion("channel =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(final Integer value) {
            this.addCriterion("channel <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(final List<Integer> values) {
            this.addCriterion("channel in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(final List<Integer> values) {
            this.addCriterion("channel not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(final Integer value1, final Integer value2) {
            this.addCriterion("channel between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("channel not between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(final Integer value) {
            this.addCriterion("channel >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("channel >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(final Integer value) {
            this.addCriterion("channel <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(final Integer value) {
            this.addCriterion("channel <=", value, "channel");
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