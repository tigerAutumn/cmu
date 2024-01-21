package cc.newex.dax.users.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户行为表 查询条件example类
 *
 * @author newex-team
 * @date 2018-04-15
 */
public class UserBehaviorExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserBehaviorExample() {
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

        public Criteria andCategoryIsNull() {
            this.addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            this.addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(final String value) {
            this.addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(final String value) {
            this.addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(final List<String> values) {
            this.addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(final List<String> values) {
            this.addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(final String value1, final String value2) {
            this.addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(final String value1, final String value2) {
            this.addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }


        public Criteria andCategoryLike(final String value) {
            this.addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(final String value) {
            this.addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            this.addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            this.addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(final String value) {
            this.addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(final List<String> values) {
            this.addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(final List<String> values) {
            this.addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(final String value1, final String value2) {
            this.addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(final String value1, final String value2) {
            this.addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }


        public Criteria andNameLike(final String value) {
            this.addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(final String value) {
            this.addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            this.addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            this.addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(final String value) {
            this.addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(final String value) {
            this.addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(final List<String> values) {
            this.addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(final List<String> values) {
            this.addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(final String value1, final String value2) {
            this.addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(final String value1, final String value2) {
            this.addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }


        public Criteria andDescriptionLike(final String value) {
            this.addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(final String value) {
            this.addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorIsNull() {
            this.addCriterion("has_success_monitor is null");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorIsNotNull() {
            this.addCriterion("has_success_monitor is not null");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorEqualTo(final Byte value) {
            this.addCriterion("has_success_monitor =", value, "hasSuccessMonitor");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorNotEqualTo(final Byte value) {
            this.addCriterion("has_success_monitor <>", value, "hasSuccessMonitor");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorIn(final List<Byte> values) {
            this.addCriterion("has_success_monitor in", values, "hasSuccessMonitor");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorNotIn(final List<Byte> values) {
            this.addCriterion("has_success_monitor not in", values, "hasSuccessMonitor");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorBetween(final Byte value1, final Byte value2) {
            this.addCriterion("has_success_monitor between", value1, value2, "hasSuccessMonitor");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("has_success_monitor not between", value1, value2, "hasSuccessMonitor");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorGreaterThan(final Byte value) {
            this.addCriterion("has_success_monitor >", value, "hasSuccessMonitor");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("has_success_monitor >=", value, "hasSuccessMonitor");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorLessThan(final Byte value) {
            this.addCriterion("has_success_monitor <", value, "hasSuccessMonitor");
            return (Criteria) this;
        }

        public Criteria andHasSuccessMonitorLessThanOrEqualTo(final Byte value) {
            this.addCriterion("has_success_monitor <=", value, "hasSuccessMonitor");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorIsNull() {
            this.addCriterion("has_failure_monitor is null");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorIsNotNull() {
            this.addCriterion("has_failure_monitor is not null");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorEqualTo(final Byte value) {
            this.addCriterion("has_failure_monitor =", value, "hasFailureMonitor");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorNotEqualTo(final Byte value) {
            this.addCriterion("has_failure_monitor <>", value, "hasFailureMonitor");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorIn(final List<Byte> values) {
            this.addCriterion("has_failure_monitor in", values, "hasFailureMonitor");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorNotIn(final List<Byte> values) {
            this.addCriterion("has_failure_monitor not in", values, "hasFailureMonitor");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorBetween(final Byte value1, final Byte value2) {
            this.addCriterion("has_failure_monitor between", value1, value2, "hasFailureMonitor");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("has_failure_monitor not between", value1, value2, "hasFailureMonitor");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorGreaterThan(final Byte value) {
            this.addCriterion("has_failure_monitor >", value, "hasFailureMonitor");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("has_failure_monitor >=", value, "hasFailureMonitor");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorLessThan(final Byte value) {
            this.addCriterion("has_failure_monitor <", value, "hasFailureMonitor");
            return (Criteria) this;
        }

        public Criteria andHasFailureMonitorLessThanOrEqualTo(final Byte value) {
            this.addCriterion("has_failure_monitor <=", value, "hasFailureMonitor");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitIsNull() {
            this.addCriterion("max_retry_limit is null");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitIsNotNull() {
            this.addCriterion("max_retry_limit is not null");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitEqualTo(final Integer value) {
            this.addCriterion("max_retry_limit =", value, "maxRetryLimit");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitNotEqualTo(final Integer value) {
            this.addCriterion("max_retry_limit <>", value, "maxRetryLimit");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitIn(final List<Integer> values) {
            this.addCriterion("max_retry_limit in", values, "maxRetryLimit");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitNotIn(final List<Integer> values) {
            this.addCriterion("max_retry_limit not in", values, "maxRetryLimit");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitBetween(final Integer value1, final Integer value2) {
            this.addCriterion("max_retry_limit between", value1, value2, "maxRetryLimit");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("max_retry_limit not between", value1, value2, "maxRetryLimit");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitGreaterThan(final Integer value) {
            this.addCriterion("max_retry_limit >", value, "maxRetryLimit");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("max_retry_limit >=", value, "maxRetryLimit");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitLessThan(final Integer value) {
            this.addCriterion("max_retry_limit <", value, "maxRetryLimit");
            return (Criteria) this;
        }

        public Criteria andMaxRetryLimitLessThanOrEqualTo(final Integer value) {
            this.addCriterion("max_retry_limit <=", value, "maxRetryLimit");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingIsNull() {
            this.addCriterion("duration_of_freezing is null");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingIsNotNull() {
            this.addCriterion("duration_of_freezing is not null");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingEqualTo(final Integer value) {
            this.addCriterion("duration_of_freezing =", value, "durationOfFreezing");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingNotEqualTo(final Integer value) {
            this.addCriterion("duration_of_freezing <>", value, "durationOfFreezing");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingIn(final List<Integer> values) {
            this.addCriterion("duration_of_freezing in", values, "durationOfFreezing");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingNotIn(final List<Integer> values) {
            this.addCriterion("duration_of_freezing not in", values, "durationOfFreezing");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingBetween(final Integer value1, final Integer value2) {
            this.addCriterion("duration_of_freezing between", value1, value2, "durationOfFreezing");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("duration_of_freezing not between", value1, value2, "durationOfFreezing");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingGreaterThan(final Integer value) {
            this.addCriterion("duration_of_freezing >", value, "durationOfFreezing");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("duration_of_freezing >=", value, "durationOfFreezing");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingLessThan(final Integer value) {
            this.addCriterion("duration_of_freezing <", value, "durationOfFreezing");
            return (Criteria) this;
        }

        public Criteria andDurationOfFreezingLessThanOrEqualTo(final Integer value) {
            this.addCriterion("duration_of_freezing <=", value, "durationOfFreezing");
            return (Criteria) this;
        }

        public Criteria andNoticeTemplateCodeIsNull() {
            this.addCriterion("notice_template_code is null");
            return (Criteria) this;
        }

        public Criteria andNoticeTemplateCodeIsNotNull() {
            this.addCriterion("notice_template_code is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeTemplateCodeEqualTo(final String value) {
            this.addCriterion("notice_template_code =", value, "noticeTemplateCode");
            return (Criteria) this;
        }

        public Criteria andNoticeTemplateCodeNotEqualTo(final String value) {
            this.addCriterion("notice_template_code <>", value, "noticeTemplateCode");
            return (Criteria) this;
        }

        public Criteria andNoticeTemplateCodeIn(final List<String> values) {
            this.addCriterion("notice_template_code in", values, "noticeTemplateCode");
            return (Criteria) this;
        }

        public Criteria andNoticeTemplateCodeNotIn(final List<String> values) {
            this.addCriterion("notice_template_code not in", values, "noticeTemplateCode");
            return (Criteria) this;
        }

        public Criteria andNoticeTemplateCodeBetween(final String value1, final String value2) {
            this.addCriterion("notice_template_code between", value1, value2, "noticeTemplateCode");
            return (Criteria) this;
        }

        public Criteria andNoticeTemplateCodeNotBetween(final String value1, final String value2) {
            this.addCriterion("notice_template_code not between", value1, value2, "noticeTemplateCode");
            return (Criteria) this;
        }


        public Criteria andNoticeTemplateCodeLike(final String value) {
            this.addCriterion("notice_template_code like", value, "noticeTemplateCode");
            return (Criteria) this;
        }

        public Criteria andNoticeTemplateCodeNotLike(final String value) {
            this.addCriterion("notice_template_code not like", value, "noticeTemplateCode");
            return (Criteria) this;
        }

        public Criteria andNeedLoginIsNull() {
            this.addCriterion("need_login is null");
            return (Criteria) this;
        }

        public Criteria andNeedLoginIsNotNull() {
            this.addCriterion("need_login is not null");
            return (Criteria) this;
        }

        public Criteria andNeedLoginEqualTo(final Byte value) {
            this.addCriterion("need_login =", value, "needLogin");
            return (Criteria) this;
        }

        public Criteria andNeedLoginNotEqualTo(final Byte value) {
            this.addCriterion("need_login <>", value, "needLogin");
            return (Criteria) this;
        }

        public Criteria andNeedLoginIn(final List<Byte> values) {
            this.addCriterion("need_login in", values, "needLogin");
            return (Criteria) this;
        }

        public Criteria andNeedLoginNotIn(final List<Byte> values) {
            this.addCriterion("need_login not in", values, "needLogin");
            return (Criteria) this;
        }

        public Criteria andNeedLoginBetween(final Byte value1, final Byte value2) {
            this.addCriterion("need_login between", value1, value2, "needLogin");
            return (Criteria) this;
        }

        public Criteria andNeedLoginNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("need_login not between", value1, value2, "needLogin");
            return (Criteria) this;
        }

        public Criteria andNeedLoginGreaterThan(final Byte value) {
            this.addCriterion("need_login >", value, "needLogin");
            return (Criteria) this;
        }

        public Criteria andNeedLoginGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("need_login >=", value, "needLogin");
            return (Criteria) this;
        }

        public Criteria andNeedLoginLessThan(final Byte value) {
            this.addCriterion("need_login <", value, "needLogin");
            return (Criteria) this;
        }

        public Criteria andNeedLoginLessThanOrEqualTo(final Byte value) {
            this.addCriterion("need_login <=", value, "needLogin");
            return (Criteria) this;
        }

        public Criteria andCheckRuleIsNull() {
            this.addCriterion("check_rule is null");
            return (Criteria) this;
        }

        public Criteria andCheckRuleIsNotNull() {
            this.addCriterion("check_rule is not null");
            return (Criteria) this;
        }

        public Criteria andCheckRuleEqualTo(final String value) {
            this.addCriterion("check_rule =", value, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleNotEqualTo(final String value) {
            this.addCriterion("check_rule <>", value, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleIn(final List<String> values) {
            this.addCriterion("check_rule in", values, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleNotIn(final List<String> values) {
            this.addCriterion("check_rule not in", values, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleBetween(final String value1, final String value2) {
            this.addCriterion("check_rule between", value1, value2, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleNotBetween(final String value1, final String value2) {
            this.addCriterion("check_rule not between", value1, value2, "checkRule");
            return (Criteria) this;
        }


        public Criteria andCheckRuleLike(final String value) {
            this.addCriterion("check_rule like", value, "checkRule");
            return (Criteria) this;
        }

        public Criteria andCheckRuleNotLike(final String value) {
            this.addCriterion("check_rule not like", value, "checkRule");
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

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private final String typeHandler;

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