package cc.newex.dax.perpetual.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 撮合缓存表 查询条件类
 *
 * @author newex-team
 * @date 2018-12-26 15:31:39
 */
public class MatchingCacheExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MatchingCacheExample() {
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

        public Criteria andContractCodeIsNull() {
            addCriterion("contract_code is null");
            return (Criteria) this;
        }

        public Criteria andContractCodeIsNotNull() {
            addCriterion("contract_code is not null");
            return (Criteria) this;
        }

        public Criteria andContractCodeEqualTo(String value) {
            addCriterion("contract_code =", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotEqualTo(String value) {
            addCriterion("contract_code <>", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeGreaterThan(String value) {
            addCriterion("contract_code >", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeGreaterThanOrEqualTo(String value) {
            addCriterion("contract_code >=", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLessThan(String value) {
            addCriterion("contract_code <", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLessThanOrEqualTo(String value) {
            addCriterion("contract_code <=", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLike(String value) {
            addCriterion("contract_code like", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotLike(String value) {
            addCriterion("contract_code not like", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeIn(List<String> values) {
            addCriterion("contract_code in", values, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotIn(List<String> values) {
            addCriterion("contract_code not in", values, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeBetween(String value1, String value2) {
            addCriterion("contract_code between", value1, value2, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotBetween(String value1, String value2) {
            addCriterion("contract_code not between", value1, value2, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractInfoIsNull() {
            addCriterion("contract_info is null");
            return (Criteria) this;
        }

        public Criteria andContractInfoIsNotNull() {
            addCriterion("contract_info is not null");
            return (Criteria) this;
        }

        public Criteria andContractInfoEqualTo(String value) {
            addCriterion("contract_info =", value, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoNotEqualTo(String value) {
            addCriterion("contract_info <>", value, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoGreaterThan(String value) {
            addCriterion("contract_info >", value, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoGreaterThanOrEqualTo(String value) {
            addCriterion("contract_info >=", value, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoLessThan(String value) {
            addCriterion("contract_info <", value, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoLessThanOrEqualTo(String value) {
            addCriterion("contract_info <=", value, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoLike(String value) {
            addCriterion("contract_info like", value, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoNotLike(String value) {
            addCriterion("contract_info not like", value, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoIn(List<String> values) {
            addCriterion("contract_info in", values, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoNotIn(List<String> values) {
            addCriterion("contract_info not in", values, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoBetween(String value1, String value2) {
            addCriterion("contract_info between", value1, value2, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andContractInfoNotBetween(String value1, String value2) {
            addCriterion("contract_info not between", value1, value2, "contractInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoIsNull() {
            addCriterion("pending_info is null");
            return (Criteria) this;
        }

        public Criteria andPendingInfoIsNotNull() {
            addCriterion("pending_info is not null");
            return (Criteria) this;
        }

        public Criteria andPendingInfoEqualTo(String value) {
            addCriterion("pending_info =", value, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoNotEqualTo(String value) {
            addCriterion("pending_info <>", value, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoGreaterThan(String value) {
            addCriterion("pending_info >", value, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoGreaterThanOrEqualTo(String value) {
            addCriterion("pending_info >=", value, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoLessThan(String value) {
            addCriterion("pending_info <", value, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoLessThanOrEqualTo(String value) {
            addCriterion("pending_info <=", value, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoLike(String value) {
            addCriterion("pending_info like", value, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoNotLike(String value) {
            addCriterion("pending_info not like", value, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoIn(List<String> values) {
            addCriterion("pending_info in", values, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoNotIn(List<String> values) {
            addCriterion("pending_info not in", values, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoBetween(String value1, String value2) {
            addCriterion("pending_info between", value1, value2, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andPendingInfoNotBetween(String value1, String value2) {
            addCriterion("pending_info not between", value1, value2, "pendingInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoIsNull() {
            addCriterion("order_all_update_info is null");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoIsNotNull() {
            addCriterion("order_all_update_info is not null");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoEqualTo(String value) {
            addCriterion("order_all_update_info =", value, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoNotEqualTo(String value) {
            addCriterion("order_all_update_info <>", value, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoGreaterThan(String value) {
            addCriterion("order_all_update_info >", value, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoGreaterThanOrEqualTo(String value) {
            addCriterion("order_all_update_info >=", value, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoLessThan(String value) {
            addCriterion("order_all_update_info <", value, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoLessThanOrEqualTo(String value) {
            addCriterion("order_all_update_info <=", value, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoLike(String value) {
            addCriterion("order_all_update_info like", value, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoNotLike(String value) {
            addCriterion("order_all_update_info not like", value, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoIn(List<String> values) {
            addCriterion("order_all_update_info in", values, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoNotIn(List<String> values) {
            addCriterion("order_all_update_info not in", values, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoBetween(String value1, String value2) {
            addCriterion("order_all_update_info between", value1, value2, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllUpdateInfoNotBetween(String value1, String value2) {
            addCriterion("order_all_update_info not between", value1, value2, "orderAllUpdateInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoIsNull() {
            addCriterion("order_all_delete_info is null");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoIsNotNull() {
            addCriterion("order_all_delete_info is not null");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoEqualTo(String value) {
            addCriterion("order_all_delete_info =", value, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoNotEqualTo(String value) {
            addCriterion("order_all_delete_info <>", value, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoGreaterThan(String value) {
            addCriterion("order_all_delete_info >", value, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoGreaterThanOrEqualTo(String value) {
            addCriterion("order_all_delete_info >=", value, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoLessThan(String value) {
            addCriterion("order_all_delete_info <", value, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoLessThanOrEqualTo(String value) {
            addCriterion("order_all_delete_info <=", value, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoLike(String value) {
            addCriterion("order_all_delete_info like", value, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoNotLike(String value) {
            addCriterion("order_all_delete_info not like", value, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoIn(List<String> values) {
            addCriterion("order_all_delete_info in", values, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoNotIn(List<String> values) {
            addCriterion("order_all_delete_info not in", values, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoBetween(String value1, String value2) {
            addCriterion("order_all_delete_info between", value1, value2, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderAllDeleteInfoNotBetween(String value1, String value2) {
            addCriterion("order_all_delete_info not between", value1, value2, "orderAllDeleteInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoIsNull() {
            addCriterion("order_finish_info is null");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoIsNotNull() {
            addCriterion("order_finish_info is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoEqualTo(String value) {
            addCriterion("order_finish_info =", value, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoNotEqualTo(String value) {
            addCriterion("order_finish_info <>", value, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoGreaterThan(String value) {
            addCriterion("order_finish_info >", value, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoGreaterThanOrEqualTo(String value) {
            addCriterion("order_finish_info >=", value, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoLessThan(String value) {
            addCriterion("order_finish_info <", value, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoLessThanOrEqualTo(String value) {
            addCriterion("order_finish_info <=", value, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoLike(String value) {
            addCriterion("order_finish_info like", value, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoNotLike(String value) {
            addCriterion("order_finish_info not like", value, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoIn(List<String> values) {
            addCriterion("order_finish_info in", values, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoNotIn(List<String> values) {
            addCriterion("order_finish_info not in", values, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoBetween(String value1, String value2) {
            addCriterion("order_finish_info between", value1, value2, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andOrderFinishInfoNotBetween(String value1, String value2) {
            addCriterion("order_finish_info not between", value1, value2, "orderFinishInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoIsNull() {
            addCriterion("user_bill_info is null");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoIsNotNull() {
            addCriterion("user_bill_info is not null");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoEqualTo(String value) {
            addCriterion("user_bill_info =", value, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoNotEqualTo(String value) {
            addCriterion("user_bill_info <>", value, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoGreaterThan(String value) {
            addCriterion("user_bill_info >", value, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoGreaterThanOrEqualTo(String value) {
            addCriterion("user_bill_info >=", value, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoLessThan(String value) {
            addCriterion("user_bill_info <", value, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoLessThanOrEqualTo(String value) {
            addCriterion("user_bill_info <=", value, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoLike(String value) {
            addCriterion("user_bill_info like", value, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoNotLike(String value) {
            addCriterion("user_bill_info not like", value, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoIn(List<String> values) {
            addCriterion("user_bill_info in", values, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoNotIn(List<String> values) {
            addCriterion("user_bill_info not in", values, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoBetween(String value1, String value2) {
            addCriterion("user_bill_info between", value1, value2, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andUserBillInfoNotBetween(String value1, String value2) {
            addCriterion("user_bill_info not between", value1, value2, "userBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoIsNull() {
            addCriterion("system_bill_info is null");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoIsNotNull() {
            addCriterion("system_bill_info is not null");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoEqualTo(String value) {
            addCriterion("system_bill_info =", value, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoNotEqualTo(String value) {
            addCriterion("system_bill_info <>", value, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoGreaterThan(String value) {
            addCriterion("system_bill_info >", value, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoGreaterThanOrEqualTo(String value) {
            addCriterion("system_bill_info >=", value, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoLessThan(String value) {
            addCriterion("system_bill_info <", value, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoLessThanOrEqualTo(String value) {
            addCriterion("system_bill_info <=", value, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoLike(String value) {
            addCriterion("system_bill_info like", value, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoNotLike(String value) {
            addCriterion("system_bill_info not like", value, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoIn(List<String> values) {
            addCriterion("system_bill_info in", values, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoNotIn(List<String> values) {
            addCriterion("system_bill_info not in", values, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoBetween(String value1, String value2) {
            addCriterion("system_bill_info between", value1, value2, "systemBillInfo");
            return (Criteria) this;
        }

        public Criteria andSystemBillInfoNotBetween(String value1, String value2) {
            addCriterion("system_bill_info not between", value1, value2, "systemBillInfo");
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