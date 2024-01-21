package cc.newex.dax.perpetual.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 爆仓流水扩展表 查询条件类
 *
 * @author newex-team
 * @date 2018-11-20 18:26:43
 */
public class HistoryExplosionExpandExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HistoryExplosionExpandExample() {
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

        public Criteria andHistoryExplosionIdIsNull() {
            addCriterion("history_explosion_id is null");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdIsNotNull() {
            addCriterion("history_explosion_id is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdEqualTo(Long value) {
            addCriterion("history_explosion_id =", value, "historyExplosionId");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdNotEqualTo(Long value) {
            addCriterion("history_explosion_id <>", value, "historyExplosionId");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdGreaterThan(Long value) {
            addCriterion("history_explosion_id >", value, "historyExplosionId");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdGreaterThanOrEqualTo(Long value) {
            addCriterion("history_explosion_id >=", value, "historyExplosionId");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdLessThan(Long value) {
            addCriterion("history_explosion_id <", value, "historyExplosionId");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdLessThanOrEqualTo(Long value) {
            addCriterion("history_explosion_id <=", value, "historyExplosionId");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdIn(List<Long> values) {
            addCriterion("history_explosion_id in", values, "historyExplosionId");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdNotIn(List<Long> values) {
            addCriterion("history_explosion_id not in", values, "historyExplosionId");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdBetween(Long value1, Long value2) {
            addCriterion("history_explosion_id between", value1, value2, "historyExplosionId");
            return (Criteria) this;
        }

        public Criteria andHistoryExplosionIdNotBetween(Long value1, Long value2) {
            addCriterion("history_explosion_id not between", value1, value2, "historyExplosionId");
            return (Criteria) this;
        }

        public Criteria andReferIdIsNull() {
            addCriterion("refer_id is null");
            return (Criteria) this;
        }

        public Criteria andReferIdIsNotNull() {
            addCriterion("refer_id is not null");
            return (Criteria) this;
        }

        public Criteria andReferIdEqualTo(Long value) {
            addCriterion("refer_id =", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdNotEqualTo(Long value) {
            addCriterion("refer_id <>", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdGreaterThan(Long value) {
            addCriterion("refer_id >", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdGreaterThanOrEqualTo(Long value) {
            addCriterion("refer_id >=", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdLessThan(Long value) {
            addCriterion("refer_id <", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdLessThanOrEqualTo(Long value) {
            addCriterion("refer_id <=", value, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdIn(List<Long> values) {
            addCriterion("refer_id in", values, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdNotIn(List<Long> values) {
            addCriterion("refer_id not in", values, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdBetween(Long value1, Long value2) {
            addCriterion("refer_id between", value1, value2, "referId");
            return (Criteria) this;
        }

        public Criteria andReferIdNotBetween(Long value1, Long value2) {
            addCriterion("refer_id not between", value1, value2, "referId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityIsNull() {
            addCriterion("before_position_quantity is null");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityIsNotNull() {
            addCriterion("before_position_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityEqualTo(BigDecimal value) {
            addCriterion("before_position_quantity =", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityNotEqualTo(BigDecimal value) {
            addCriterion("before_position_quantity <>", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityGreaterThan(BigDecimal value) {
            addCriterion("before_position_quantity >", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("before_position_quantity >=", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityLessThan(BigDecimal value) {
            addCriterion("before_position_quantity <", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("before_position_quantity <=", value, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityIn(List<BigDecimal> values) {
            addCriterion("before_position_quantity in", values, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityNotIn(List<BigDecimal> values) {
            addCriterion("before_position_quantity not in", values, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_position_quantity between", value1, value2, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andBeforePositionQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_position_quantity not between", value1, value2, "beforePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityIsNull() {
            addCriterion("after_position_quantity is null");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityIsNotNull() {
            addCriterion("after_position_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityEqualTo(BigDecimal value) {
            addCriterion("after_position_quantity =", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityNotEqualTo(BigDecimal value) {
            addCriterion("after_position_quantity <>", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityGreaterThan(BigDecimal value) {
            addCriterion("after_position_quantity >", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("after_position_quantity >=", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityLessThan(BigDecimal value) {
            addCriterion("after_position_quantity <", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("after_position_quantity <=", value, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityIn(List<BigDecimal> values) {
            addCriterion("after_position_quantity in", values, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityNotIn(List<BigDecimal> values) {
            addCriterion("after_position_quantity not in", values, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_position_quantity between", value1, value2, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andAfterPositionQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_position_quantity not between", value1, value2, "afterPositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityIsNull() {
            addCriterion("close_position_quantity is null");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityIsNotNull() {
            addCriterion("close_position_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityEqualTo(BigDecimal value) {
            addCriterion("close_position_quantity =", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityNotEqualTo(BigDecimal value) {
            addCriterion("close_position_quantity <>", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityGreaterThan(BigDecimal value) {
            addCriterion("close_position_quantity >", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("close_position_quantity >=", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityLessThan(BigDecimal value) {
            addCriterion("close_position_quantity <", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("close_position_quantity <=", value, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityIn(List<BigDecimal> values) {
            addCriterion("close_position_quantity in", values, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityNotIn(List<BigDecimal> values) {
            addCriterion("close_position_quantity not in", values, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_position_quantity between", value1, value2, "closePositionQuantity");
            return (Criteria) this;
        }

        public Criteria andClosePositionQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_position_quantity not between", value1, value2, "closePositionQuantity");
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