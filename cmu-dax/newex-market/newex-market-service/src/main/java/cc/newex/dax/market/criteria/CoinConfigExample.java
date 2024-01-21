package cc.newex.dax.market.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询条件example类
 *
 * @author newex-team
 * @date 2018-07-26
 */
public class CoinConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CoinConfigExample() {
        oredCriteria = new ArrayList<>();
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

        public Criteria andSymbolIsNull() {
            addCriterion("symbol is null");
            return (Criteria) this;
        }

        public Criteria andSymbolIsNotNull() {
            addCriterion("symbol is not null");
            return (Criteria) this;
        }

        public Criteria andSymbolEqualTo(Integer value) {
            addCriterion("symbol =", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotEqualTo(Integer value) {
            addCriterion("symbol <>", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolIn(List<Integer> values) {
            addCriterion("symbol in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotIn(List<Integer> values) {
            addCriterion("symbol not in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolBetween(Integer value1, Integer value2) {
            addCriterion("symbol between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotBetween(Integer value1, Integer value2) {
            addCriterion("symbol not between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThan(Integer value) {
            addCriterion("symbol >", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThanOrEqualTo(Integer value) {
            addCriterion("symbol >=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThan(Integer value) {
            addCriterion("symbol <", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThanOrEqualTo(Integer value) {
            addCriterion("symbol <=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNameIsNull() {
            addCriterion("symbol_name is null");
            return (Criteria) this;
        }

        public Criteria andSymbolNameIsNotNull() {
            addCriterion("symbol_name is not null");
            return (Criteria) this;
        }

        public Criteria andSymbolNameEqualTo(String value) {
            addCriterion("symbol_name =", value, "symbolName");
            return (Criteria) this;
        }

        public Criteria andSymbolNameNotEqualTo(String value) {
            addCriterion("symbol_name <>", value, "symbolName");
            return (Criteria) this;
        }

        public Criteria andSymbolNameIn(List<String> values) {
            addCriterion("symbol_name in", values, "symbolName");
            return (Criteria) this;
        }

        public Criteria andSymbolNameNotIn(List<String> values) {
            addCriterion("symbol_name not in", values, "symbolName");
            return (Criteria) this;
        }

        public Criteria andSymbolNameBetween(String value1, String value2) {
            addCriterion("symbol_name between", value1, value2, "symbolName");
            return (Criteria) this;
        }

        public Criteria andSymbolNameNotBetween(String value1, String value2) {
            addCriterion("symbol_name not between", value1, value2, "symbolName");
            return (Criteria) this;
        }


        public Criteria andSymbolNameLike(String value) {
            addCriterion("symbol_name like", value, "symbolName");
            return (Criteria) this;
        }

        public Criteria andSymbolNameNotLike(String value) {
            addCriterion("symbol_name not like", value, "symbolName");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromIsNull() {
            addCriterion("index_market_from is null");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromIsNotNull() {
            addCriterion("index_market_from is not null");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromEqualTo(Integer value) {
            addCriterion("index_market_from =", value, "indexMarketFrom");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromNotEqualTo(Integer value) {
            addCriterion("index_market_from <>", value, "indexMarketFrom");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromIn(List<Integer> values) {
            addCriterion("index_market_from in", values, "indexMarketFrom");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromNotIn(List<Integer> values) {
            addCriterion("index_market_from not in", values, "indexMarketFrom");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromBetween(Integer value1, Integer value2) {
            addCriterion("index_market_from between", value1, value2, "indexMarketFrom");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromNotBetween(Integer value1, Integer value2) {
            addCriterion("index_market_from not between", value1, value2, "indexMarketFrom");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromGreaterThan(Integer value) {
            addCriterion("index_market_from >", value, "indexMarketFrom");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromGreaterThanOrEqualTo(Integer value) {
            addCriterion("index_market_from >=", value, "indexMarketFrom");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromLessThan(Integer value) {
            addCriterion("index_market_from <", value, "indexMarketFrom");
            return (Criteria) this;
        }

        public Criteria andIndexMarketFromLessThanOrEqualTo(Integer value) {
            addCriterion("index_market_from <=", value, "indexMarketFrom");
            return (Criteria) this;
        }

        public Criteria andSymbolMarkIsNull() {
            addCriterion("symbol_mark is null");
            return (Criteria) this;
        }

        public Criteria andSymbolMarkIsNotNull() {
            addCriterion("symbol_mark is not null");
            return (Criteria) this;
        }

        public Criteria andSymbolMarkEqualTo(String value) {
            addCriterion("symbol_mark =", value, "symbolMark");
            return (Criteria) this;
        }

        public Criteria andSymbolMarkNotEqualTo(String value) {
            addCriterion("symbol_mark <>", value, "symbolMark");
            return (Criteria) this;
        }

        public Criteria andSymbolMarkIn(List<String> values) {
            addCriterion("symbol_mark in", values, "symbolMark");
            return (Criteria) this;
        }

        public Criteria andSymbolMarkNotIn(List<String> values) {
            addCriterion("symbol_mark not in", values, "symbolMark");
            return (Criteria) this;
        }

        public Criteria andSymbolMarkBetween(String value1, String value2) {
            addCriterion("symbol_mark between", value1, value2, "symbolMark");
            return (Criteria) this;
        }

        public Criteria andSymbolMarkNotBetween(String value1, String value2) {
            addCriterion("symbol_mark not between", value1, value2, "symbolMark");
            return (Criteria) this;
        }


        public Criteria andSymbolMarkLike(String value) {
            addCriterion("symbol_mark like", value, "symbolMark");
            return (Criteria) this;
        }

        public Criteria andSymbolMarkNotLike(String value) {
            addCriterion("symbol_mark not like", value, "symbolMark");
            return (Criteria) this;
        }

        public Criteria andMarketFromIsNull() {
            addCriterion("market_from is null");
            return (Criteria) this;
        }

        public Criteria andMarketFromIsNotNull() {
            addCriterion("market_from is not null");
            return (Criteria) this;
        }

        public Criteria andMarketFromEqualTo(String value) {
            addCriterion("market_from =", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotEqualTo(String value) {
            addCriterion("market_from <>", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromIn(List<String> values) {
            addCriterion("market_from in", values, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotIn(List<String> values) {
            addCriterion("market_from not in", values, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromBetween(String value1, String value2) {
            addCriterion("market_from between", value1, value2, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotBetween(String value1, String value2) {
            addCriterion("market_from not between", value1, value2, "marketFrom");
            return (Criteria) this;
        }


        public Criteria andMarketFromLike(String value) {
            addCriterion("market_from like", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotLike(String value) {
            addCriterion("market_from not like", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andPricePointIsNull() {
            addCriterion("pricePoint is null");
            return (Criteria) this;
        }

        public Criteria andPricePointIsNotNull() {
            addCriterion("pricePoint is not null");
            return (Criteria) this;
        }

        public Criteria andPricePointEqualTo(Integer value) {
            addCriterion("pricePoint =", value, "pricePoint");
            return (Criteria) this;
        }

        public Criteria andPricePointNotEqualTo(Integer value) {
            addCriterion("pricePoint <>", value, "pricePoint");
            return (Criteria) this;
        }

        public Criteria andPricePointIn(List<Integer> values) {
            addCriterion("pricePoint in", values, "pricePoint");
            return (Criteria) this;
        }

        public Criteria andPricePointNotIn(List<Integer> values) {
            addCriterion("pricePoint not in", values, "pricePoint");
            return (Criteria) this;
        }

        public Criteria andPricePointBetween(Integer value1, Integer value2) {
            addCriterion("pricePoint between", value1, value2, "pricePoint");
            return (Criteria) this;
        }

        public Criteria andPricePointNotBetween(Integer value1, Integer value2) {
            addCriterion("pricePoint not between", value1, value2, "pricePoint");
            return (Criteria) this;
        }

        public Criteria andPricePointGreaterThan(Integer value) {
            addCriterion("pricePoint >", value, "pricePoint");
            return (Criteria) this;
        }

        public Criteria andPricePointGreaterThanOrEqualTo(Integer value) {
            addCriterion("pricePoint >=", value, "pricePoint");
            return (Criteria) this;
        }

        public Criteria andPricePointLessThan(Integer value) {
            addCriterion("pricePoint <", value, "pricePoint");
            return (Criteria) this;
        }

        public Criteria andPricePointLessThanOrEqualTo(Integer value) {
            addCriterion("pricePoint <=", value, "pricePoint");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchIsNull() {
            addCriterion("invalid_switch is null");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchIsNotNull() {
            addCriterion("invalid_switch is not null");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchEqualTo(Byte value) {
            addCriterion("invalid_switch =", value, "invalidSwitch");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchNotEqualTo(Byte value) {
            addCriterion("invalid_switch <>", value, "invalidSwitch");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchIn(List<Byte> values) {
            addCriterion("invalid_switch in", values, "invalidSwitch");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchNotIn(List<Byte> values) {
            addCriterion("invalid_switch not in", values, "invalidSwitch");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchBetween(Byte value1, Byte value2) {
            addCriterion("invalid_switch between", value1, value2, "invalidSwitch");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchNotBetween(Byte value1, Byte value2) {
            addCriterion("invalid_switch not between", value1, value2, "invalidSwitch");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchGreaterThan(Byte value) {
            addCriterion("invalid_switch >", value, "invalidSwitch");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchGreaterThanOrEqualTo(Byte value) {
            addCriterion("invalid_switch >=", value, "invalidSwitch");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchLessThan(Byte value) {
            addCriterion("invalid_switch <", value, "invalidSwitch");
            return (Criteria) this;
        }

        public Criteria andInvalidSwitchLessThanOrEqualTo(Byte value) {
            addCriterion("invalid_switch <=", value, "invalidSwitch");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
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

        public Criteria andFieldIsNull(final String fieldName) {
            addCriterion(fieldName + " is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull(final String fieldName) {
            addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " = ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " <> ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldIn(final String fieldName, final List<Object> values) {
            addCriterion(fieldName + " in ", values, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(final String fieldName, final List<Object> values) {
            addCriterion(fieldName + " not in ", values, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            addCriterion(fieldName + " between ", fieldValue1, fieldValue2, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            addCriterion(fieldName + "  not between ", fieldValue1, fieldValue2, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " > ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " >= ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " < ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " <= ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldLike(final String fieldName, final String fieldValue) {
            addCriterion(fieldName + " like ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(final String fieldName, final String fieldValue) {
            addCriterion(fieldName + "  not like ", fieldValue, fieldName);
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
