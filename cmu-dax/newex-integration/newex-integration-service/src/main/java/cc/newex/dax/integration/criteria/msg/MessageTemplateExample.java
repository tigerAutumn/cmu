package cc.newex.dax.integration.criteria.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 信息模板表 查询条件example类
 *
 * @author newex-team
 * @date 2018-06-02
 */
public class MessageTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageTemplateExample() {
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

        public Criteria andCodeIsNull() {
            this.addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            this.addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(final String value) {
            this.addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(final String value) {
            this.addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(final List<String> values) {
            this.addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(final List<String> values) {
            this.addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(final String value1, final String value2) {
            this.addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(final String value1, final String value2) {
            this.addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }


        public Criteria andCodeLike(final String value) {
            this.addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(final String value) {
            this.addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andSignIsNull() {
            this.addCriterion("sign is null");
            return (Criteria) this;
        }

        public Criteria andSignIsNotNull() {
            this.addCriterion("sign is not null");
            return (Criteria) this;
        }

        public Criteria andSignEqualTo(final String value) {
            this.addCriterion("sign =", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotEqualTo(final String value) {
            this.addCriterion("sign <>", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignIn(final List<String> values) {
            this.addCriterion("sign in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotIn(final List<String> values) {
            this.addCriterion("sign not in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignBetween(final String value1, final String value2) {
            this.addCriterion("sign between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotBetween(final String value1, final String value2) {
            this.addCriterion("sign not between", value1, value2, "sign");
            return (Criteria) this;
        }


        public Criteria andSignLike(final String value) {
            this.addCriterion("sign like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotLike(final String value) {
            this.addCriterion("sign not like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNull() {
            this.addCriterion("subject is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNotNull() {
            this.addCriterion("subject is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectEqualTo(final String value) {
            this.addCriterion("subject =", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotEqualTo(final String value) {
            this.addCriterion("subject <>", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectIn(final List<String> values) {
            this.addCriterion("subject in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotIn(final List<String> values) {
            this.addCriterion("subject not in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectBetween(final String value1, final String value2) {
            this.addCriterion("subject between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotBetween(final String value1, final String value2) {
            this.addCriterion("subject not between", value1, value2, "subject");
            return (Criteria) this;
        }


        public Criteria andSubjectLike(final String value) {
            this.addCriterion("subject like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotLike(final String value) {
            this.addCriterion("subject not like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andLocaleIsNull() {
            this.addCriterion("locale is null");
            return (Criteria) this;
        }

        public Criteria andLocaleIsNotNull() {
            this.addCriterion("locale is not null");
            return (Criteria) this;
        }

        public Criteria andLocaleEqualTo(final String value) {
            this.addCriterion("locale =", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleNotEqualTo(final String value) {
            this.addCriterion("locale <>", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleIn(final List<String> values) {
            this.addCriterion("locale in", values, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleNotIn(final List<String> values) {
            this.addCriterion("locale not in", values, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleBetween(final String value1, final String value2) {
            this.addCriterion("locale between", value1, value2, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleNotBetween(final String value1, final String value2) {
            this.addCriterion("locale not between", value1, value2, "locale");
            return (Criteria) this;
        }


        public Criteria andLocaleLike(final String value) {
            this.addCriterion("locale like", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleNotLike(final String value) {
            this.addCriterion("locale not like", value, "locale");
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

        public Criteria andTypeEqualTo(final String value) {
            this.addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(final String value) {
            this.addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(final List<String> values) {
            this.addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(final List<String> values) {
            this.addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(final String value1, final String value2) {
            this.addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(final String value1, final String value2) {
            this.addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }


        public Criteria andTypeLike(final String value) {
            this.addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(final String value) {
            this.addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            this.addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            this.addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(final String value) {
            this.addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(final String value) {
            this.addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(final List<String> values) {
            this.addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(final List<String> values) {
            this.addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(final String value1, final String value2) {
            this.addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(final String value1, final String value2) {
            this.addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }


        public Criteria andContentLike(final String value) {
            this.addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(final String value) {
            this.addCriterion("content not like", value, "content");
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
