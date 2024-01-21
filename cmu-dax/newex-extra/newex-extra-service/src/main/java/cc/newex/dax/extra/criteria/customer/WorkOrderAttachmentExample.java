package cc.newex.dax.extra.criteria.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工单附件表 查询条件example类
 *
 * @author newex-team
 * @date 2018-05-30
 */
public class WorkOrderAttachmentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WorkOrderAttachmentExample() {
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

        public Criteria andOriginalNameIsNull() {
            this.addCriterion("original_name is null");
            return (Criteria) this;
        }

        public Criteria andOriginalNameIsNotNull() {
            this.addCriterion("original_name is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalNameEqualTo(final String value) {
            this.addCriterion("original_name =", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameNotEqualTo(final String value) {
            this.addCriterion("original_name <>", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameIn(final List<String> values) {
            this.addCriterion("original_name in", values, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameNotIn(final List<String> values) {
            this.addCriterion("original_name not in", values, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameBetween(final String value1, final String value2) {
            this.addCriterion("original_name between", value1, value2, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameNotBetween(final String value1, final String value2) {
            this.addCriterion("original_name not between", value1, value2, "originalName");
            return (Criteria) this;
        }


        public Criteria andOriginalNameLike(final String value) {
            this.addCriterion("original_name like", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameNotLike(final String value) {
            this.addCriterion("original_name not like", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andPathIsNull() {
            this.addCriterion("path is null");
            return (Criteria) this;
        }

        public Criteria andPathIsNotNull() {
            this.addCriterion("path is not null");
            return (Criteria) this;
        }

        public Criteria andPathEqualTo(final String value) {
            this.addCriterion("path =", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotEqualTo(final String value) {
            this.addCriterion("path <>", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathIn(final List<String> values) {
            this.addCriterion("path in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotIn(final List<String> values) {
            this.addCriterion("path not in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathBetween(final String value1, final String value2) {
            this.addCriterion("path between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotBetween(final String value1, final String value2) {
            this.addCriterion("path not between", value1, value2, "path");
            return (Criteria) this;
        }


        public Criteria andPathLike(final String value) {
            this.addCriterion("path like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotLike(final String value) {
            this.addCriterion("path not like", value, "path");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            this.addCriterion("desc is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            this.addCriterion("desc is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(final String value) {
            this.addCriterion("desc =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(final String value) {
            this.addCriterion("desc <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(final List<String> values) {
            this.addCriterion("desc in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(final List<String> values) {
            this.addCriterion("desc not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(final String value1, final String value2) {
            this.addCriterion("desc between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(final String value1, final String value2) {
            this.addCriterion("desc not between", value1, value2, "desc");
            return (Criteria) this;
        }


        public Criteria andDescLike(final String value) {
            this.addCriterion("desc like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(final String value) {
            this.addCriterion("desc not like", value, "desc");
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
