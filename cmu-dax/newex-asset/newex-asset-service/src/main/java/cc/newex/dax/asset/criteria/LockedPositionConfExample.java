package cc.newex.dax.asset.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 锁仓配置表 查询条件example类
 * @author newex-team
 * @date 2018-07-16
 */
public class LockedPositionConfExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LockedPositionConfExample() {
        this.oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public List<Criteria> getOredCriteria() {
        return this.oredCriteria;
    }

    public void or(Criteria criteria) {
        this.oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
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

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
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

        public Criteria andIdEqualTo(Long value) {
            this.addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            this.addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            this.addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameIsNull() {
            this.addCriterion("lock_position_name is null");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameIsNotNull() {
            this.addCriterion("lock_position_name is not null");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameEqualTo(String value) {
            this.addCriterion("lock_position_name =", value, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameNotEqualTo(String value) {
            this.addCriterion("lock_position_name <>", value, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameIn(List<String> values) {
            this.addCriterion("lock_position_name in", values, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameNotIn(List<String> values) {
            this.addCriterion("lock_position_name not in", values, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameBetween(String value1, String value2) {
            this.addCriterion("lock_position_name between", value1, value2, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameNotBetween(String value1, String value2) {
            this.addCriterion("lock_position_name not between", value1, value2, "lockPositionName");
            return (Criteria) this;
        }


        public Criteria andLockPositionNameLike(String value) {
            this.addCriterion("lock_position_name like", value, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameNotLike(String value) {
            this.addCriterion("lock_position_name not like", value, "lockPositionName");
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

        public Criteria andCurrencyEqualTo(Integer value) {
            this.addCriterion("currency =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(Integer value) {
            this.addCriterion("currency <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(List<Integer> values) {
            this.addCriterion("currency in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(List<Integer> values) {
            this.addCriterion("currency not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(Integer value1, Integer value2) {
            this.addCriterion("currency between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(Integer value1, Integer value2) {
            this.addCriterion("currency not between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(Integer value) {
            this.addCriterion("currency >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("currency >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(Integer value) {
            this.addCriterion("currency <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(Integer value) {
            this.addCriterion("currency <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            this.addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            this.addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            this.addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            this.addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            this.addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            this.addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            this.addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            this.addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andDividendIsNull() {
            this.addCriterion("dividend is null");
            return (Criteria) this;
        }

        public Criteria andDividendIsNotNull() {
            this.addCriterion("dividend is not null");
            return (Criteria) this;
        }

        public Criteria andDividendEqualTo(Integer value) {
            this.addCriterion("dividend =", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendNotEqualTo(Integer value) {
            this.addCriterion("dividend <>", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendIn(List<Integer> values) {
            this.addCriterion("dividend in", values, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendNotIn(List<Integer> values) {
            this.addCriterion("dividend not in", values, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendBetween(Integer value1, Integer value2) {
            this.addCriterion("dividend between", value1, value2, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendNotBetween(Integer value1, Integer value2) {
            this.addCriterion("dividend not between", value1, value2, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendGreaterThan(Integer value) {
            this.addCriterion("dividend >", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("dividend >=", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendLessThan(Integer value) {
            this.addCriterion("dividend <", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendLessThanOrEqualTo(Integer value) {
            this.addCriterion("dividend <=", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            this.addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            this.addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            this.addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            this.addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            this.addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            this.addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            this.addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            this.addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }


        public Criteria andRemarkLike(String value) {
            this.addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            this.addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIsNull() {
            this.addCriterion("release_date is null");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIsNotNull() {
            this.addCriterion("release_date is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseDateEqualTo(String value) {
            this.addCriterion("release_date =", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotEqualTo(String value) {
            this.addCriterion("release_date <>", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIn(List<String> values) {
            this.addCriterion("release_date in", values, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotIn(List<String> values) {
            this.addCriterion("release_date not in", values, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateBetween(String value1, String value2) {
            this.addCriterion("release_date between", value1, value2, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotBetween(String value1, String value2) {
            this.addCriterion("release_date not between", value1, value2, "releaseDate");
            return (Criteria) this;
        }


        public Criteria andReleaseDateLike(String value) {
            this.addCriterion("release_date like", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotLike(String value) {
            this.addCriterion("release_date not like", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeIsNull() {
            this.addCriterion("release_type is null");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeIsNotNull() {
            this.addCriterion("release_type is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeEqualTo(Integer value) {
            this.addCriterion("release_type =", value, "releaseType");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeNotEqualTo(Integer value) {
            this.addCriterion("release_type <>", value, "releaseType");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeIn(List<Integer> values) {
            this.addCriterion("release_type in", values, "releaseType");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeNotIn(List<Integer> values) {
            this.addCriterion("release_type not in", values, "releaseType");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeBetween(Integer value1, Integer value2) {
            this.addCriterion("release_type between", value1, value2, "releaseType");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeNotBetween(Integer value1, Integer value2) {
            this.addCriterion("release_type not between", value1, value2, "releaseType");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeGreaterThan(Integer value) {
            this.addCriterion("release_type >", value, "releaseType");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("release_type >=", value, "releaseType");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeLessThan(Integer value) {
            this.addCriterion("release_type <", value, "releaseType");
            return (Criteria) this;
        }

        public Criteria andReleaseTypeLessThanOrEqualTo(Integer value) {
            this.addCriterion("release_type <=", value, "releaseType");
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

        public Criteria andCreateDateEqualTo(Date value) {
            this.addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            this.addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            this.addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            this.addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            this.addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            this.addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            this.addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            this.addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            this.addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
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

        public Criteria andUpdateDateEqualTo(Date value) {
            this.addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            this.addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            this.addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            this.addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            this.addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            this.addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            this.addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            this.addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            this.addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            this.addCriterion("update_date <=", value, "updateDate");
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
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

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
