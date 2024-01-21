package cc.newex.dax.users.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 邀请好友记录表 查询条件类
 *
 * @author newex-team
 * @date 2018-09-11 12:02:25
 */
public class UserInviteRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserInviteRecordExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdIsNull() {
            addCriterion("invite_user_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdIsNotNull() {
            addCriterion("invite_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdEqualTo(Long value) {
            addCriterion("invite_user_id =", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdNotEqualTo(Long value) {
            addCriterion("invite_user_id <>", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdGreaterThan(Long value) {
            addCriterion("invite_user_id >", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("invite_user_id >=", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdLessThan(Long value) {
            addCriterion("invite_user_id <", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdLessThanOrEqualTo(Long value) {
            addCriterion("invite_user_id <=", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdIn(List<Long> values) {
            addCriterion("invite_user_id in", values, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdNotIn(List<Long> values) {
            addCriterion("invite_user_id not in", values, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdBetween(Long value1, Long value2) {
            addCriterion("invite_user_id between", value1, value2, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdNotBetween(Long value1, Long value2) {
            addCriterion("invite_user_id not between", value1, value2, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteCodeIsNull() {
            addCriterion("invite_code is null");
            return (Criteria) this;
        }

        public Criteria andInviteCodeIsNotNull() {
            addCriterion("invite_code is not null");
            return (Criteria) this;
        }

        public Criteria andInviteCodeEqualTo(String value) {
            addCriterion("invite_code =", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeNotEqualTo(String value) {
            addCriterion("invite_code <>", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeGreaterThan(String value) {
            addCriterion("invite_code >", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeGreaterThanOrEqualTo(String value) {
            addCriterion("invite_code >=", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeLessThan(String value) {
            addCriterion("invite_code <", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeLessThanOrEqualTo(String value) {
            addCriterion("invite_code <=", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeLike(String value) {
            addCriterion("invite_code like", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeNotLike(String value) {
            addCriterion("invite_code not like", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeIn(List<String> values) {
            addCriterion("invite_code in", values, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeNotIn(List<String> values) {
            addCriterion("invite_code not in", values, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeBetween(String value1, String value2) {
            addCriterion("invite_code between", value1, value2, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeNotBetween(String value1, String value2) {
            addCriterion("invite_code not between", value1, value2, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeIsNull() {
            addCriterion("activity_code is null");
            return (Criteria) this;
        }

        public Criteria andActivityCodeIsNotNull() {
            addCriterion("activity_code is not null");
            return (Criteria) this;
        }

        public Criteria andActivityCodeEqualTo(String value) {
            addCriterion("activity_code =", value, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeNotEqualTo(String value) {
            addCriterion("activity_code <>", value, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeGreaterThan(String value) {
            addCriterion("activity_code >", value, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeGreaterThanOrEqualTo(String value) {
            addCriterion("activity_code >=", value, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeLessThan(String value) {
            addCriterion("activity_code <", value, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeLessThanOrEqualTo(String value) {
            addCriterion("activity_code <=", value, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeLike(String value) {
            addCriterion("activity_code like", value, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeNotLike(String value) {
            addCriterion("activity_code not like", value, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeIn(List<String> values) {
            addCriterion("activity_code in", values, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeNotIn(List<String> values) {
            addCriterion("activity_code not in", values, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeBetween(String value1, String value2) {
            addCriterion("activity_code between", value1, value2, "activityCode");
            return (Criteria) this;
        }

        public Criteria andActivityCodeNotBetween(String value1, String value2) {
            addCriterion("activity_code not between", value1, value2, "activityCode");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumIsNull() {
            addCriterion("currency_num is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumIsNotNull() {
            addCriterion("currency_num is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumEqualTo(Double value) {
            addCriterion("currency_num =", value, "currencyNum");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumNotEqualTo(Double value) {
            addCriterion("currency_num <>", value, "currencyNum");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumGreaterThan(Double value) {
            addCriterion("currency_num >", value, "currencyNum");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumGreaterThanOrEqualTo(Double value) {
            addCriterion("currency_num >=", value, "currencyNum");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumLessThan(Double value) {
            addCriterion("currency_num <", value, "currencyNum");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumLessThanOrEqualTo(Double value) {
            addCriterion("currency_num <=", value, "currencyNum");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumIn(List<Double> values) {
            addCriterion("currency_num in", values, "currencyNum");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumNotIn(List<Double> values) {
            addCriterion("currency_num not in", values, "currencyNum");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumBetween(Double value1, Double value2) {
            addCriterion("currency_num between", value1, value2, "currencyNum");
            return (Criteria) this;
        }

        public Criteria andCurrencyNumNotBetween(Double value1, Double value2) {
            addCriterion("currency_num not between", value1, value2, "currencyNum");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdIsNull() {
            addCriterion("currency_id is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdIsNotNull() {
            addCriterion("currency_id is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdEqualTo(Integer value) {
            addCriterion("currency_id =", value, "currencyId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdNotEqualTo(Integer value) {
            addCriterion("currency_id <>", value, "currencyId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdGreaterThan(Integer value) {
            addCriterion("currency_id >", value, "currencyId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("currency_id >=", value, "currencyId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdLessThan(Integer value) {
            addCriterion("currency_id <", value, "currencyId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdLessThanOrEqualTo(Integer value) {
            addCriterion("currency_id <=", value, "currencyId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdIn(List<Integer> values) {
            addCriterion("currency_id in", values, "currencyId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdNotIn(List<Integer> values) {
            addCriterion("currency_id not in", values, "currencyId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdBetween(Integer value1, Integer value2) {
            addCriterion("currency_id between", value1, value2, "currencyId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("currency_id not between", value1, value2, "currencyId");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumIsNull() {
            addCriterion("invite_currency_num is null");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumIsNotNull() {
            addCriterion("invite_currency_num is not null");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumEqualTo(Double value) {
            addCriterion("invite_currency_num =", value, "inviteCurrencyNum");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumNotEqualTo(Double value) {
            addCriterion("invite_currency_num <>", value, "inviteCurrencyNum");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumGreaterThan(Double value) {
            addCriterion("invite_currency_num >", value, "inviteCurrencyNum");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumGreaterThanOrEqualTo(Double value) {
            addCriterion("invite_currency_num >=", value, "inviteCurrencyNum");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumLessThan(Double value) {
            addCriterion("invite_currency_num <", value, "inviteCurrencyNum");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumLessThanOrEqualTo(Double value) {
            addCriterion("invite_currency_num <=", value, "inviteCurrencyNum");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumIn(List<Double> values) {
            addCriterion("invite_currency_num in", values, "inviteCurrencyNum");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumNotIn(List<Double> values) {
            addCriterion("invite_currency_num not in", values, "inviteCurrencyNum");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumBetween(Double value1, Double value2) {
            addCriterion("invite_currency_num between", value1, value2, "inviteCurrencyNum");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyNumNotBetween(Double value1, Double value2) {
            addCriterion("invite_currency_num not between", value1, value2, "inviteCurrencyNum");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdIsNull() {
            addCriterion("invite_currency_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdIsNotNull() {
            addCriterion("invite_currency_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdEqualTo(Integer value) {
            addCriterion("invite_currency_id =", value, "inviteCurrencyId");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdNotEqualTo(Integer value) {
            addCriterion("invite_currency_id <>", value, "inviteCurrencyId");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdGreaterThan(Integer value) {
            addCriterion("invite_currency_id >", value, "inviteCurrencyId");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_currency_id >=", value, "inviteCurrencyId");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdLessThan(Integer value) {
            addCriterion("invite_currency_id <", value, "inviteCurrencyId");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_currency_id <=", value, "inviteCurrencyId");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdIn(List<Integer> values) {
            addCriterion("invite_currency_id in", values, "inviteCurrencyId");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdNotIn(List<Integer> values) {
            addCriterion("invite_currency_id not in", values, "inviteCurrencyId");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_currency_id between", value1, value2, "inviteCurrencyId");
            return (Criteria) this;
        }

        public Criteria andInviteCurrencyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_currency_id not between", value1, value2, "inviteCurrencyId");
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

        public Criteria andUpdatedDateIsNull() {
            addCriterion("updated_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNotNull() {
            addCriterion("updated_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateEqualTo(Date value) {
            addCriterion("updated_date =", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotEqualTo(Date value) {
            addCriterion("updated_date <>", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThan(Date value) {
            addCriterion("updated_date >", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_date >=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThan(Date value) {
            addCriterion("updated_date <", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThanOrEqualTo(Date value) {
            addCriterion("updated_date <=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIn(List<Date> values) {
            addCriterion("updated_date in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotIn(List<Date> values) {
            addCriterion("updated_date not in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateBetween(Date value1, Date value2) {
            addCriterion("updated_date between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotBetween(Date value1, Date value2) {
            addCriterion("updated_date not between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNull() {
            addCriterion("broker_id is null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNotNull() {
            addCriterion("broker_id is not null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdEqualTo(Integer value) {
            addCriterion("broker_id =", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotEqualTo(Integer value) {
            addCriterion("broker_id <>", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThan(Integer value) {
            addCriterion("broker_id >", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("broker_id >=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThan(Integer value) {
            addCriterion("broker_id <", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThanOrEqualTo(Integer value) {
            addCriterion("broker_id <=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIn(List<Integer> values) {
            addCriterion("broker_id in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotIn(List<Integer> values) {
            addCriterion("broker_id not in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdBetween(Integer value1, Integer value2) {
            addCriterion("broker_id between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("broker_id not between", value1, value2, "brokerId");
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