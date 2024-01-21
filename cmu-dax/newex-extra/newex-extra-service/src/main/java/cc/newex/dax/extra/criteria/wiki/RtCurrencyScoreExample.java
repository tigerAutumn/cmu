package cc.newex.dax.extra.criteria.wiki;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * rt代币评分信息 查询条件类
 *
 * @author mbg.generated
 * @date 2018-12-13 16:34:00
 */
public class RtCurrencyScoreExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RtCurrencyScoreExample() {
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

        public Criteria andCidIsNull() {
            this.addCriterion("cid is null");
            return (Criteria) this;
        }

        public Criteria andCidIsNotNull() {
            this.addCriterion("cid is not null");
            return (Criteria) this;
        }

        public Criteria andCidEqualTo(final String value) {
            this.addCriterion("cid =", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotEqualTo(final String value) {
            this.addCriterion("cid <>", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidGreaterThan(final String value) {
            this.addCriterion("cid >", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidGreaterThanOrEqualTo(final String value) {
            this.addCriterion("cid >=", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidLessThan(final String value) {
            this.addCriterion("cid <", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidLessThanOrEqualTo(final String value) {
            this.addCriterion("cid <=", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidLike(final String value) {
            this.addCriterion("cid like", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotLike(final String value) {
            this.addCriterion("cid not like", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidIn(final List<String> values) {
            this.addCriterion("cid in", values, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotIn(final List<String> values) {
            this.addCriterion("cid not in", values, "cid");
            return (Criteria) this;
        }

        public Criteria andCidBetween(final String value1, final String value2) {
            this.addCriterion("cid between", value1, value2, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotBetween(final String value1, final String value2) {
            this.addCriterion("cid not between", value1, value2, "cid");
            return (Criteria) this;
        }

        public Criteria andSymbolIsNull() {
            this.addCriterion("symbol is null");
            return (Criteria) this;
        }

        public Criteria andSymbolIsNotNull() {
            this.addCriterion("symbol is not null");
            return (Criteria) this;
        }

        public Criteria andSymbolEqualTo(final String value) {
            this.addCriterion("symbol =", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotEqualTo(final String value) {
            this.addCriterion("symbol <>", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThan(final String value) {
            this.addCriterion("symbol >", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThanOrEqualTo(final String value) {
            this.addCriterion("symbol >=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThan(final String value) {
            this.addCriterion("symbol <", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThanOrEqualTo(final String value) {
            this.addCriterion("symbol <=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLike(final String value) {
            this.addCriterion("symbol like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotLike(final String value) {
            this.addCriterion("symbol not like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolIn(final List<String> values) {
            this.addCriterion("symbol in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotIn(final List<String> values) {
            this.addCriterion("symbol not in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolBetween(final String value1, final String value2) {
            this.addCriterion("symbol between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotBetween(final String value1, final String value2) {
            this.addCriterion("symbol not between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andTechScoreIsNull() {
            this.addCriterion("tech_score is null");
            return (Criteria) this;
        }

        public Criteria andTechScoreIsNotNull() {
            this.addCriterion("tech_score is not null");
            return (Criteria) this;
        }

        public Criteria andTechScoreEqualTo(final Float value) {
            this.addCriterion("tech_score =", value, "techScore");
            return (Criteria) this;
        }

        public Criteria andTechScoreNotEqualTo(final Float value) {
            this.addCriterion("tech_score <>", value, "techScore");
            return (Criteria) this;
        }

        public Criteria andTechScoreGreaterThan(final Float value) {
            this.addCriterion("tech_score >", value, "techScore");
            return (Criteria) this;
        }

        public Criteria andTechScoreGreaterThanOrEqualTo(final Float value) {
            this.addCriterion("tech_score >=", value, "techScore");
            return (Criteria) this;
        }

        public Criteria andTechScoreLessThan(final Float value) {
            this.addCriterion("tech_score <", value, "techScore");
            return (Criteria) this;
        }

        public Criteria andTechScoreLessThanOrEqualTo(final Float value) {
            this.addCriterion("tech_score <=", value, "techScore");
            return (Criteria) this;
        }

        public Criteria andTechScoreIn(final List<Float> values) {
            this.addCriterion("tech_score in", values, "techScore");
            return (Criteria) this;
        }

        public Criteria andTechScoreNotIn(final List<Float> values) {
            this.addCriterion("tech_score not in", values, "techScore");
            return (Criteria) this;
        }

        public Criteria andTechScoreBetween(final Float value1, final Float value2) {
            this.addCriterion("tech_score between", value1, value2, "techScore");
            return (Criteria) this;
        }

        public Criteria andTechScoreNotBetween(final Float value1, final Float value2) {
            this.addCriterion("tech_score not between", value1, value2, "techScore");
            return (Criteria) this;
        }

        public Criteria andTeamScoreIsNull() {
            this.addCriterion("team_score is null");
            return (Criteria) this;
        }

        public Criteria andTeamScoreIsNotNull() {
            this.addCriterion("team_score is not null");
            return (Criteria) this;
        }

        public Criteria andTeamScoreEqualTo(final Float value) {
            this.addCriterion("team_score =", value, "teamScore");
            return (Criteria) this;
        }

        public Criteria andTeamScoreNotEqualTo(final Float value) {
            this.addCriterion("team_score <>", value, "teamScore");
            return (Criteria) this;
        }

        public Criteria andTeamScoreGreaterThan(final Float value) {
            this.addCriterion("team_score >", value, "teamScore");
            return (Criteria) this;
        }

        public Criteria andTeamScoreGreaterThanOrEqualTo(final Float value) {
            this.addCriterion("team_score >=", value, "teamScore");
            return (Criteria) this;
        }

        public Criteria andTeamScoreLessThan(final Float value) {
            this.addCriterion("team_score <", value, "teamScore");
            return (Criteria) this;
        }

        public Criteria andTeamScoreLessThanOrEqualTo(final Float value) {
            this.addCriterion("team_score <=", value, "teamScore");
            return (Criteria) this;
        }

        public Criteria andTeamScoreIn(final List<Float> values) {
            this.addCriterion("team_score in", values, "teamScore");
            return (Criteria) this;
        }

        public Criteria andTeamScoreNotIn(final List<Float> values) {
            this.addCriterion("team_score not in", values, "teamScore");
            return (Criteria) this;
        }

        public Criteria andTeamScoreBetween(final Float value1, final Float value2) {
            this.addCriterion("team_score between", value1, value2, "teamScore");
            return (Criteria) this;
        }

        public Criteria andTeamScoreNotBetween(final Float value1, final Float value2) {
            this.addCriterion("team_score not between", value1, value2, "teamScore");
            return (Criteria) this;
        }

        public Criteria andHypeScoreIsNull() {
            this.addCriterion("hype_score is null");
            return (Criteria) this;
        }

        public Criteria andHypeScoreIsNotNull() {
            this.addCriterion("hype_score is not null");
            return (Criteria) this;
        }

        public Criteria andHypeScoreEqualTo(final Float value) {
            this.addCriterion("hype_score =", value, "hypeScore");
            return (Criteria) this;
        }

        public Criteria andHypeScoreNotEqualTo(final Float value) {
            this.addCriterion("hype_score <>", value, "hypeScore");
            return (Criteria) this;
        }

        public Criteria andHypeScoreGreaterThan(final Float value) {
            this.addCriterion("hype_score >", value, "hypeScore");
            return (Criteria) this;
        }

        public Criteria andHypeScoreGreaterThanOrEqualTo(final Float value) {
            this.addCriterion("hype_score >=", value, "hypeScore");
            return (Criteria) this;
        }

        public Criteria andHypeScoreLessThan(final Float value) {
            this.addCriterion("hype_score <", value, "hypeScore");
            return (Criteria) this;
        }

        public Criteria andHypeScoreLessThanOrEqualTo(final Float value) {
            this.addCriterion("hype_score <=", value, "hypeScore");
            return (Criteria) this;
        }

        public Criteria andHypeScoreIn(final List<Float> values) {
            this.addCriterion("hype_score in", values, "hypeScore");
            return (Criteria) this;
        }

        public Criteria andHypeScoreNotIn(final List<Float> values) {
            this.addCriterion("hype_score not in", values, "hypeScore");
            return (Criteria) this;
        }

        public Criteria andHypeScoreBetween(final Float value1, final Float value2) {
            this.addCriterion("hype_score between", value1, value2, "hypeScore");
            return (Criteria) this;
        }

        public Criteria andHypeScoreNotBetween(final Float value1, final Float value2) {
            this.addCriterion("hype_score not between", value1, value2, "hypeScore");
            return (Criteria) this;
        }

        public Criteria andRatingIsNull() {
            this.addCriterion("rating is null");
            return (Criteria) this;
        }

        public Criteria andRatingIsNotNull() {
            this.addCriterion("rating is not null");
            return (Criteria) this;
        }

        public Criteria andRatingEqualTo(final Float value) {
            this.addCriterion("rating =", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotEqualTo(final Float value) {
            this.addCriterion("rating <>", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingGreaterThan(final Float value) {
            this.addCriterion("rating >", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingGreaterThanOrEqualTo(final Float value) {
            this.addCriterion("rating >=", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingLessThan(final Float value) {
            this.addCriterion("rating <", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingLessThanOrEqualTo(final Float value) {
            this.addCriterion("rating <=", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingIn(final List<Float> values) {
            this.addCriterion("rating in", values, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotIn(final List<Float> values) {
            this.addCriterion("rating not in", values, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingBetween(final Float value1, final Float value2) {
            this.addCriterion("rating between", value1, value2, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotBetween(final Float value1, final Float value2) {
            this.addCriterion("rating not between", value1, value2, "rating");
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

        public Criteria andModifyDateIsNull() {
            this.addCriterion("modify_date is null");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNotNull() {
            this.addCriterion("modify_date is not null");
            return (Criteria) this;
        }

        public Criteria andModifyDateEqualTo(final Date value) {
            this.addCriterion("modify_date =", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotEqualTo(final Date value) {
            this.addCriterion("modify_date <>", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThan(final Date value) {
            this.addCriterion("modify_date >", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("modify_date >=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThan(final Date value) {
            this.addCriterion("modify_date <", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThanOrEqualTo(final Date value) {
            this.addCriterion("modify_date <=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateIn(final List<Date> values) {
            this.addCriterion("modify_date in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotIn(final List<Date> values) {
            this.addCriterion("modify_date not in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateBetween(final Date value1, final Date value2) {
            this.addCriterion("modify_date between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotBetween(final Date value1, final Date value2) {
            this.addCriterion("modify_date not between", value1, value2, "modifyDate");
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
            this.addCriterion(fieldName + " =", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <>", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " >", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " >=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLike(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " not like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldIn(final String fieldName, final List<Object> fieldValues) {
            this.addCriterion(fieldName + " in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(final String fieldName, final List<Object> fieldValues) {
            this.addCriterion(fieldName + " not in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            this.addCriterion(fieldName + " between", fieldValue1, fieldValue2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            this.addCriterion(fieldName + " not between", fieldValue1, fieldValue2, "fieldName");
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