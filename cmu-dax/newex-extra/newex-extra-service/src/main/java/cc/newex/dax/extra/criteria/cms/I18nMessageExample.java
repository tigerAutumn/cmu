package cc.newex.dax.extra.criteria.cms;

import cc.newex.dax.extra.domain.cms.I18nMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 本地化文本表 查询条件example类
 *
 * @author newex-team
 * @date 2018-05-30
 */
public class I18nMessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public I18nMessageExample() {
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

    public static I18nMessageExample toExample(final I18nMessage i18nMessage) {
        if (i18nMessage == null) {
            return null;
        }

        final I18nMessageExample example = new I18nMessageExample();
        final I18nMessageExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(i18nMessage.getId())) {
            criteria.andIdEqualTo(i18nMessage.getId());
        }

        if (Objects.nonNull(i18nMessage.getMsgCodeId())) {
            criteria.andMsgCodeIdEqualTo(i18nMessage.getMsgCodeId());
        }

        if (StringUtils.isNotBlank(i18nMessage.getLocale())) {
            criteria.andLocaleEqualTo(i18nMessage.getLocale());
        }

        if (StringUtils.isNotBlank(i18nMessage.getMsgCode())) {
            criteria.andMsgCodeEqualTo(i18nMessage.getMsgCode());
        }

        if (StringUtils.isNotBlank(i18nMessage.getMsgText())) {
            criteria.andMsgTextLike(i18nMessage.getMsgText());
        }

        return example;
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

        public Criteria andMsgCodeIdIsNull() {
            this.addCriterion("msg_code_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdIsNotNull() {
            this.addCriterion("msg_code_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdEqualTo(final Integer value) {
            this.addCriterion("msg_code_id =", value, "msgCodeId");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdNotEqualTo(final Integer value) {
            this.addCriterion("msg_code_id <>", value, "msgCodeId");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdIn(final List<Integer> values) {
            this.addCriterion("msg_code_id in", values, "msgCodeId");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdNotIn(final List<Integer> values) {
            this.addCriterion("msg_code_id not in", values, "msgCodeId");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("msg_code_id between", value1, value2, "msgCodeId");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("msg_code_id not between", value1, value2, "msgCodeId");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdGreaterThan(final Integer value) {
            this.addCriterion("msg_code_id >", value, "msgCodeId");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("msg_code_id >=", value, "msgCodeId");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdLessThan(final Integer value) {
            this.addCriterion("msg_code_id <", value, "msgCodeId");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("msg_code_id <=", value, "msgCodeId");
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

        public Criteria andMsgCodeIsNull() {
            this.addCriterion("msg_code is null");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIsNotNull() {
            this.addCriterion("msg_code is not null");
            return (Criteria) this;
        }

        public Criteria andMsgCodeEqualTo(final String value) {
            this.addCriterion("msg_code =", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeNotEqualTo(final String value) {
            this.addCriterion("msg_code <>", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIn(final List<String> values) {
            this.addCriterion("msg_code in", values, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeNotIn(final List<String> values) {
            this.addCriterion("msg_code not in", values, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeBetween(final String value1, final String value2) {
            this.addCriterion("msg_code between", value1, value2, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeNotBetween(final String value1, final String value2) {
            this.addCriterion("msg_code not between", value1, value2, "msgCode");
            return (Criteria) this;
        }


        public Criteria andMsgCodeLike(final String value) {
            this.addCriterion("msg_code like", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeNotLike(final String value) {
            this.addCriterion("msg_code not like", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgTextIsNull() {
            this.addCriterion("msg_text is null");
            return (Criteria) this;
        }

        public Criteria andMsgTextIsNotNull() {
            this.addCriterion("msg_text is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTextEqualTo(final String value) {
            this.addCriterion("msg_text =", value, "msgText");
            return (Criteria) this;
        }

        public Criteria andMsgTextNotEqualTo(final String value) {
            this.addCriterion("msg_text <>", value, "msgText");
            return (Criteria) this;
        }

        public Criteria andMsgTextIn(final List<String> values) {
            this.addCriterion("msg_text in", values, "msgText");
            return (Criteria) this;
        }

        public Criteria andMsgTextNotIn(final List<String> values) {
            this.addCriterion("msg_text not in", values, "msgText");
            return (Criteria) this;
        }

        public Criteria andMsgTextBetween(final String value1, final String value2) {
            this.addCriterion("msg_text between", value1, value2, "msgText");
            return (Criteria) this;
        }

        public Criteria andMsgTextNotBetween(final String value1, final String value2) {
            this.addCriterion("msg_text not between", value1, value2, "msgText");
            return (Criteria) this;
        }


        public Criteria andMsgTextLike(final String value) {
            this.addCriterion("msg_text like", "%" + value + "%", "msgText");
            return (Criteria) this;
        }

        public Criteria andMsgTextNotLike(final String value) {
            this.addCriterion("msg_text not like", value, "msgText");
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
