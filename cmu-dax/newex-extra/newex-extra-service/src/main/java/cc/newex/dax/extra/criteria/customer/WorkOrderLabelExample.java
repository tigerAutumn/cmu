package cc.newex.dax.extra.criteria.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工单标签记录表 查询条件example类
 *
 * @author newex-team
 * @date 2018-05-30
 */
public class WorkOrderLabelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WorkOrderLabelExample() {
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

        public Criteria andWorkOrderIdIsNull() {
            this.addCriterion("work_order_id is null");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdIsNotNull() {
            this.addCriterion("work_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdEqualTo(final Long value) {
            this.addCriterion("work_order_id =", value, "workOrderId");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdNotEqualTo(final Long value) {
            this.addCriterion("work_order_id <>", value, "workOrderId");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdIn(final List<Long> values) {
            this.addCriterion("work_order_id in", values, "workOrderId");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdNotIn(final List<Long> values) {
            this.addCriterion("work_order_id not in", values, "workOrderId");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdBetween(final Long value1, final Long value2) {
            this.addCriterion("work_order_id between", value1, value2, "workOrderId");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdNotBetween(final Long value1, final Long value2) {
            this.addCriterion("work_order_id not between", value1, value2, "workOrderId");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdGreaterThan(final Long value) {
            this.addCriterion("work_order_id >", value, "workOrderId");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("work_order_id >=", value, "workOrderId");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdLessThan(final Long value) {
            this.addCriterion("work_order_id <", value, "workOrderId");
            return (Criteria) this;
        }

        public Criteria andWorkOrderIdLessThanOrEqualTo(final Long value) {
            this.addCriterion("work_order_id <=", value, "workOrderId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNull() {
            this.addCriterion("admin_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNotNull() {
            this.addCriterion("admin_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdEqualTo(final Integer value) {
            this.addCriterion("admin_user_id =", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotEqualTo(final Integer value) {
            this.addCriterion("admin_user_id <>", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIn(final List<Integer> values) {
            this.addCriterion("admin_user_id in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotIn(final List<Integer> values) {
            this.addCriterion("admin_user_id not in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("admin_user_id between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("admin_user_id not between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThan(final Integer value) {
            this.addCriterion("admin_user_id >", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("admin_user_id >=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThan(final Integer value) {
            this.addCriterion("admin_user_id <", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("admin_user_id <=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andLabelIsNull() {
            this.addCriterion("label is null");
            return (Criteria) this;
        }

        public Criteria andLabelIsNotNull() {
            this.addCriterion("label is not null");
            return (Criteria) this;
        }

        public Criteria andLabelEqualTo(final String value) {
            this.addCriterion("label =", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotEqualTo(final String value) {
            this.addCriterion("label <>", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelIn(final List<String> values) {
            this.addCriterion("label in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotIn(final List<String> values) {
            this.addCriterion("label not in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelBetween(final String value1, final String value2) {
            this.addCriterion("label between", value1, value2, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotBetween(final String value1, final String value2) {
            this.addCriterion("label not between", value1, value2, "label");
            return (Criteria) this;
        }


        public Criteria andLabelLike(final String value) {
            this.addCriterion("label like", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotLike(final String value) {
            this.addCriterion("label not like", value, "label");
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
