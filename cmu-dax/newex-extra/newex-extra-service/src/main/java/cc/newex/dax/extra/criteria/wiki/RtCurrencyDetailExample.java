package cc.newex.dax.extra.criteria.wiki;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * rt代币详情信息 查询条件类
 *
 * @author mbg.generated
 * @date 2018-12-19 17:00:43
 */
public class RtCurrencyDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RtCurrencyDetailExample() {
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

        public Criteria andCirculatingSupplyIsNull() {
            this.addCriterion("circulating_supply is null");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyIsNotNull() {
            this.addCriterion("circulating_supply is not null");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyEqualTo(final Long value) {
            this.addCriterion("circulating_supply =", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyNotEqualTo(final Long value) {
            this.addCriterion("circulating_supply <>", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyGreaterThan(final Long value) {
            this.addCriterion("circulating_supply >", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("circulating_supply >=", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyLessThan(final Long value) {
            this.addCriterion("circulating_supply <", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyLessThanOrEqualTo(final Long value) {
            this.addCriterion("circulating_supply <=", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyIn(final List<Long> values) {
            this.addCriterion("circulating_supply in", values, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyNotIn(final List<Long> values) {
            this.addCriterion("circulating_supply not in", values, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyBetween(final Long value1, final Long value2) {
            this.addCriterion("circulating_supply between", value1, value2, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyNotBetween(final Long value1, final Long value2) {
            this.addCriterion("circulating_supply not between", value1, value2, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andIssueTimeIsNull() {
            this.addCriterion("issue_time is null");
            return (Criteria) this;
        }

        public Criteria andIssueTimeIsNotNull() {
            this.addCriterion("issue_time is not null");
            return (Criteria) this;
        }

        public Criteria andIssueTimeEqualTo(final String value) {
            this.addCriterion("issue_time =", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeNotEqualTo(final String value) {
            this.addCriterion("issue_time <>", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeGreaterThan(final String value) {
            this.addCriterion("issue_time >", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("issue_time >=", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeLessThan(final String value) {
            this.addCriterion("issue_time <", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeLessThanOrEqualTo(final String value) {
            this.addCriterion("issue_time <=", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeLike(final String value) {
            this.addCriterion("issue_time like", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeNotLike(final String value) {
            this.addCriterion("issue_time not like", value, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeIn(final List<String> values) {
            this.addCriterion("issue_time in", values, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeNotIn(final List<String> values) {
            this.addCriterion("issue_time not in", values, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeBetween(final String value1, final String value2) {
            this.addCriterion("issue_time between", value1, value2, "issueTime");
            return (Criteria) this;
        }

        public Criteria andIssueTimeNotBetween(final String value1, final String value2) {
            this.addCriterion("issue_time not between", value1, value2, "issueTime");
            return (Criteria) this;
        }

        public Criteria andExchangeNumIsNull() {
            this.addCriterion("exchange_num is null");
            return (Criteria) this;
        }

        public Criteria andExchangeNumIsNotNull() {
            this.addCriterion("exchange_num is not null");
            return (Criteria) this;
        }

        public Criteria andExchangeNumEqualTo(final Short value) {
            this.addCriterion("exchange_num =", value, "exchangeNum");
            return (Criteria) this;
        }

        public Criteria andExchangeNumNotEqualTo(final Short value) {
            this.addCriterion("exchange_num <>", value, "exchangeNum");
            return (Criteria) this;
        }

        public Criteria andExchangeNumGreaterThan(final Short value) {
            this.addCriterion("exchange_num >", value, "exchangeNum");
            return (Criteria) this;
        }

        public Criteria andExchangeNumGreaterThanOrEqualTo(final Short value) {
            this.addCriterion("exchange_num >=", value, "exchangeNum");
            return (Criteria) this;
        }

        public Criteria andExchangeNumLessThan(final Short value) {
            this.addCriterion("exchange_num <", value, "exchangeNum");
            return (Criteria) this;
        }

        public Criteria andExchangeNumLessThanOrEqualTo(final Short value) {
            this.addCriterion("exchange_num <=", value, "exchangeNum");
            return (Criteria) this;
        }

        public Criteria andExchangeNumIn(final List<Short> values) {
            this.addCriterion("exchange_num in", values, "exchangeNum");
            return (Criteria) this;
        }

        public Criteria andExchangeNumNotIn(final List<Short> values) {
            this.addCriterion("exchange_num not in", values, "exchangeNum");
            return (Criteria) this;
        }

        public Criteria andExchangeNumBetween(final Short value1, final Short value2) {
            this.addCriterion("exchange_num between", value1, value2, "exchangeNum");
            return (Criteria) this;
        }

        public Criteria andExchangeNumNotBetween(final Short value1, final Short value2) {
            this.addCriterion("exchange_num not between", value1, value2, "exchangeNum");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationIsNull() {
            this.addCriterion("total_circulation is null");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationIsNotNull() {
            this.addCriterion("total_circulation is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationEqualTo(final String value) {
            this.addCriterion("total_circulation =", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationNotEqualTo(final String value) {
            this.addCriterion("total_circulation <>", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationGreaterThan(final String value) {
            this.addCriterion("total_circulation >", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationGreaterThanOrEqualTo(final String value) {
            this.addCriterion("total_circulation >=", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationLessThan(final String value) {
            this.addCriterion("total_circulation <", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationLessThanOrEqualTo(final String value) {
            this.addCriterion("total_circulation <=", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationLike(final String value) {
            this.addCriterion("total_circulation like", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationNotLike(final String value) {
            this.addCriterion("total_circulation not like", value, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationIn(final List<String> values) {
            this.addCriterion("total_circulation in", values, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationNotIn(final List<String> values) {
            this.addCriterion("total_circulation not in", values, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationBetween(final String value1, final String value2) {
            this.addCriterion("total_circulation between", value1, value2, "totalCirculation");
            return (Criteria) this;
        }

        public Criteria andTotalCirculationNotBetween(final String value1, final String value2) {
            this.addCriterion("total_circulation not between", value1, value2, "totalCirculation");
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

        public Criteria andIssuePriceIsNull() {
            this.addCriterion("issue_price is null");
            return (Criteria) this;
        }

        public Criteria andIssuePriceIsNotNull() {
            this.addCriterion("issue_price is not null");
            return (Criteria) this;
        }

        public Criteria andIssuePriceEqualTo(final String value) {
            this.addCriterion("issue_price =", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceNotEqualTo(final String value) {
            this.addCriterion("issue_price <>", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceGreaterThan(final String value) {
            this.addCriterion("issue_price >", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceGreaterThanOrEqualTo(final String value) {
            this.addCriterion("issue_price >=", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceLessThan(final String value) {
            this.addCriterion("issue_price <", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceLessThanOrEqualTo(final String value) {
            this.addCriterion("issue_price <=", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceLike(final String value) {
            this.addCriterion("issue_price like", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceNotLike(final String value) {
            this.addCriterion("issue_price not like", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceIn(final List<String> values) {
            this.addCriterion("issue_price in", values, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceNotIn(final List<String> values) {
            this.addCriterion("issue_price not in", values, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceBetween(final String value1, final String value2) {
            this.addCriterion("issue_price between", value1, value2, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceNotBetween(final String value1, final String value2) {
            this.addCriterion("issue_price not between", value1, value2, "issuePrice");
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