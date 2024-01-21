package cc.newex.dax.market.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询条件example类
 *
 * @author newex-team
 * @date 2018/03/18
 */
public class FetchingDataPathExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FetchingDataPathExample() {
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

        protected void addCriterion(final String condition, final Object value1, final Object value2,
                                    final String property) {
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

        public Criteria andMarketFromIsNull() {
            this.addCriterion("market_from is null");
            return (Criteria) this;
        }

        public Criteria andMarketFromIsNotNull() {
            this.addCriterion("market_from is not null");
            return (Criteria) this;
        }

        public Criteria andMarketFromEqualTo(final Integer value) {
            this.addCriterion("market_from =", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotEqualTo(final Integer value) {
            this.addCriterion("market_from <>", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromIn(final List<Integer> values) {
            this.addCriterion("market_from in", values, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotIn(final List<Integer> values) {
            this.addCriterion("market_from not in", values, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromBetween(final Integer value1, final Integer value2) {
            this.addCriterion("market_from between", value1, value2, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("market_from not between", value1, value2, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromGreaterThan(final Integer value) {
            this.addCriterion("market_from >", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("market_from >=", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromLessThan(final Integer value) {
            this.addCriterion("market_from <", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromLessThanOrEqualTo(final Integer value) {
            this.addCriterion("market_from <=", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            this.addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            this.addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(final Date value) {
            this.addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(final Date value) {
            this.addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(final List<Date> values) {
            this.addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(final List<Date> values) {
            this.addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(final Date value) {
            this.addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(final Date value) {
            this.addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("create_time <=", value, "createTime");
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

        public Criteria andSymbolLike(final String value) {
            this.addCriterion("symbol like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotLike(final String value) {
            this.addCriterion("symbol not like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andWebNameIsNull() {
            this.addCriterion("web_name is null");
            return (Criteria) this;
        }

        public Criteria andWebNameIsNotNull() {
            this.addCriterion("web_name is not null");
            return (Criteria) this;
        }

        public Criteria andWebNameEqualTo(final String value) {
            this.addCriterion("web_name =", value, "webName");
            return (Criteria) this;
        }

        public Criteria andWebNameNotEqualTo(final String value) {
            this.addCriterion("web_name <>", value, "webName");
            return (Criteria) this;
        }

        public Criteria andWebNameIn(final List<String> values) {
            this.addCriterion("web_name in", values, "webName");
            return (Criteria) this;
        }

        public Criteria andWebNameNotIn(final List<String> values) {
            this.addCriterion("web_name not in", values, "webName");
            return (Criteria) this;
        }

        public Criteria andWebNameBetween(final String value1, final String value2) {
            this.addCriterion("web_name between", value1, value2, "webName");
            return (Criteria) this;
        }

        public Criteria andWebNameNotBetween(final String value1, final String value2) {
            this.addCriterion("web_name not between", value1, value2, "webName");
            return (Criteria) this;
        }

        public Criteria andWebNameLike(final String value) {
            this.addCriterion("web_name like", value, "webName");
            return (Criteria) this;
        }

        public Criteria andWebNameNotLike(final String value) {
            this.addCriterion("web_name not like", value, "webName");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            this.addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            this.addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(final Date value) {
            this.addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(final Date value) {
            this.addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(final List<Date> values) {
            this.addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(final List<Date> values) {
            this.addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(final Date value) {
            this.addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(final Date value) {
            this.addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andKeyIsNull() {
            this.addCriterion("url_key is null");
            return (Criteria) this;
        }

        public Criteria andKeyIsNotNull() {
            this.addCriterion("url_key is not null");
            return (Criteria) this;
        }

        public Criteria andKeyEqualTo(final String value) {
            this.addCriterion("url_key =", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotEqualTo(final String value) {
            this.addCriterion("url_key <>", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyIn(final List<String> values) {
            this.addCriterion("url_key in", values, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotIn(final List<String> values) {
            this.addCriterion("url_key not in", values, "key");
            return (Criteria) this;
        }

        public Criteria andKeyBetween(final String value1, final String value2) {
            this.addCriterion("url_key between", value1, value2, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotBetween(final String value1, final String value2) {
            this.addCriterion("url_key not between", value1, value2, "key");
            return (Criteria) this;
        }

        public Criteria andKeyLike(final String value) {
            this.addCriterion("url_key like", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotLike(final String value) {
            this.addCriterion("url_key not like", value, "key");
            return (Criteria) this;
        }

        public Criteria andOvmIsNull() {
            this.addCriterion("ovm is null");
            return (Criteria) this;
        }

        public Criteria andOvmIsNotNull() {
            this.addCriterion("ovm is not null");
            return (Criteria) this;
        }

        public Criteria andOvmEqualTo(final String value) {
            this.addCriterion("ovm =", value, "ovm");
            return (Criteria) this;
        }

        public Criteria andOvmNotEqualTo(final String value) {
            this.addCriterion("ovm <>", value, "ovm");
            return (Criteria) this;
        }

        public Criteria andOvmIn(final List<String> values) {
            this.addCriterion("ovm in", values, "ovm");
            return (Criteria) this;
        }

        public Criteria andOvmNotIn(final List<String> values) {
            this.addCriterion("ovm not in", values, "ovm");
            return (Criteria) this;
        }

        public Criteria andOvmBetween(final String value1, final String value2) {
            this.addCriterion("ovm between", value1, value2, "ovm");
            return (Criteria) this;
        }

        public Criteria andOvmNotBetween(final String value1, final String value2) {
            this.addCriterion("ovm not between", value1, value2, "ovm");
            return (Criteria) this;
        }

        public Criteria andOvmLike(final String value) {
            this.addCriterion("ovm like", value, "ovm");
            return (Criteria) this;
        }

        public Criteria andOvmNotLike(final String value) {
            this.addCriterion("ovm not like", value, "ovm");
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

        protected Criterion(final String condition, final Object value, final Object secondValue,
                            final String typeHandler) {
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