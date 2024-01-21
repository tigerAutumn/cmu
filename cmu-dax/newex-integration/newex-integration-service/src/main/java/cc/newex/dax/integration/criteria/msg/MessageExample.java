package cc.newex.dax.integration.criteria.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发送的消息(邮件/短信等)表 查询条件example类
 *
 * @author newex-team
 * @date 2018-05-10
 */
public class MessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageExample() {
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

        public Criteria andCountryCodeIsNull() {
            this.addCriterion("country_code is null");
            return (Criteria) this;
        }

        public Criteria andCountryCodeIsNotNull() {
            this.addCriterion("country_code is not null");
            return (Criteria) this;
        }

        public Criteria andCountryCodeEqualTo(final String value) {
            this.addCriterion("country_code =", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotEqualTo(final String value) {
            this.addCriterion("country_code <>", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeIn(final List<String> values) {
            this.addCriterion("country_code in", values, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotIn(final List<String> values) {
            this.addCriterion("country_code not in", values, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeBetween(final String value1, final String value2) {
            this.addCriterion("country_code between", value1, value2, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotBetween(final String value1, final String value2) {
            this.addCriterion("country_code not between", value1, value2, "countryCode");
            return (Criteria) this;
        }


        public Criteria andCountryCodeLike(final String value) {
            this.addCriterion("country_code like", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotLike(final String value) {
            this.addCriterion("country_code not like", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIsNull() {
            this.addCriterion("phone_number is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIsNotNull() {
            this.addCriterion("phone_number is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberEqualTo(final String value) {
            this.addCriterion("phone_number =", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotEqualTo(final String value) {
            this.addCriterion("phone_number <>", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIn(final List<String> values) {
            this.addCriterion("phone_number in", values, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotIn(final List<String> values) {
            this.addCriterion("phone_number not in", values, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberBetween(final String value1, final String value2) {
            this.addCriterion("phone_number between", value1, value2, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotBetween(final String value1, final String value2) {
            this.addCriterion("phone_number not between", value1, value2, "phoneNumber");
            return (Criteria) this;
        }


        public Criteria andPhoneNumberLike(final String value) {
            this.addCriterion("phone_number like", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotLike(final String value) {
            this.addCriterion("phone_number not like", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIsNull() {
            this.addCriterion("email_address is null");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIsNotNull() {
            this.addCriterion("email_address is not null");
            return (Criteria) this;
        }

        public Criteria andEmailAddressEqualTo(final String value) {
            this.addCriterion("email_address =", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressNotEqualTo(final String value) {
            this.addCriterion("email_address <>", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIn(final List<String> values) {
            this.addCriterion("email_address in", values, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressNotIn(final List<String> values) {
            this.addCriterion("email_address not in", values, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressBetween(final String value1, final String value2) {
            this.addCriterion("email_address between", value1, value2, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressNotBetween(final String value1, final String value2) {
            this.addCriterion("email_address not between", value1, value2, "emailAddress");
            return (Criteria) this;
        }


        public Criteria andEmailAddressLike(final String value) {
            this.addCriterion("email_address like", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressNotLike(final String value) {
            this.addCriterion("email_address not like", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andFromAliasIsNull() {
            this.addCriterion("from_alias is null");
            return (Criteria) this;
        }

        public Criteria andFromAliasIsNotNull() {
            this.addCriterion("from_alias is not null");
            return (Criteria) this;
        }

        public Criteria andFromAliasEqualTo(final String value) {
            this.addCriterion("from_alias =", value, "fromAlias");
            return (Criteria) this;
        }

        public Criteria andFromAliasNotEqualTo(final String value) {
            this.addCriterion("from_alias <>", value, "fromAlias");
            return (Criteria) this;
        }

        public Criteria andFromAliasIn(final List<String> values) {
            this.addCriterion("from_alias in", values, "fromAlias");
            return (Criteria) this;
        }

        public Criteria andFromAliasNotIn(final List<String> values) {
            this.addCriterion("from_alias not in", values, "fromAlias");
            return (Criteria) this;
        }

        public Criteria andFromAliasBetween(final String value1, final String value2) {
            this.addCriterion("from_alias between", value1, value2, "fromAlias");
            return (Criteria) this;
        }

        public Criteria andFromAliasNotBetween(final String value1, final String value2) {
            this.addCriterion("from_alias not between", value1, value2, "fromAlias");
            return (Criteria) this;
        }


        public Criteria andFromAliasLike(final String value) {
            this.addCriterion("from_alias like", value, "fromAlias");
            return (Criteria) this;
        }

        public Criteria andFromAliasNotLike(final String value) {
            this.addCriterion("from_alias not like", value, "fromAlias");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeIsNull() {
            this.addCriterion("template_code is null");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeIsNotNull() {
            this.addCriterion("template_code is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeEqualTo(final String value) {
            this.addCriterion("template_code =", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotEqualTo(final String value) {
            this.addCriterion("template_code <>", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeIn(final List<String> values) {
            this.addCriterion("template_code in", values, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotIn(final List<String> values) {
            this.addCriterion("template_code not in", values, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeBetween(final String value1, final String value2) {
            this.addCriterion("template_code between", value1, value2, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotBetween(final String value1, final String value2) {
            this.addCriterion("template_code not between", value1, value2, "templateCode");
            return (Criteria) this;
        }


        public Criteria andTemplateCodeLike(final String value) {
            this.addCriterion("template_code like", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotLike(final String value) {
            this.addCriterion("template_code not like", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateParamsIsNull() {
            this.addCriterion("template_params is null");
            return (Criteria) this;
        }

        public Criteria andTemplateParamsIsNotNull() {
            this.addCriterion("template_params is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateParamsEqualTo(final String value) {
            this.addCriterion("template_params =", value, "templateParams");
            return (Criteria) this;
        }

        public Criteria andTemplateParamsNotEqualTo(final String value) {
            this.addCriterion("template_params <>", value, "templateParams");
            return (Criteria) this;
        }

        public Criteria andTemplateParamsIn(final List<String> values) {
            this.addCriterion("template_params in", values, "templateParams");
            return (Criteria) this;
        }

        public Criteria andTemplateParamsNotIn(final List<String> values) {
            this.addCriterion("template_params not in", values, "templateParams");
            return (Criteria) this;
        }

        public Criteria andTemplateParamsBetween(final String value1, final String value2) {
            this.addCriterion("template_params between", value1, value2, "templateParams");
            return (Criteria) this;
        }

        public Criteria andTemplateParamsNotBetween(final String value1, final String value2) {
            this.addCriterion("template_params not between", value1, value2, "templateParams");
            return (Criteria) this;
        }


        public Criteria andTemplateParamsLike(final String value) {
            this.addCriterion("template_params like", value, "templateParams");
            return (Criteria) this;
        }

        public Criteria andTemplateParamsNotLike(final String value) {
            this.addCriterion("template_params not like", value, "templateParams");
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

        public Criteria andIsSentIsNull() {
            this.addCriterion("is_sent is null");
            return (Criteria) this;
        }

        public Criteria andIsSentIsNotNull() {
            this.addCriterion("is_sent is not null");
            return (Criteria) this;
        }

        public Criteria andIsSentEqualTo(final Byte value) {
            this.addCriterion("is_sent =", value, "isSent");
            return (Criteria) this;
        }

        public Criteria andIsSentNotEqualTo(final Byte value) {
            this.addCriterion("is_sent <>", value, "isSent");
            return (Criteria) this;
        }

        public Criteria andIsSentIn(final List<Byte> values) {
            this.addCriterion("is_sent in", values, "isSent");
            return (Criteria) this;
        }

        public Criteria andIsSentNotIn(final List<Byte> values) {
            this.addCriterion("is_sent not in", values, "isSent");
            return (Criteria) this;
        }

        public Criteria andIsSentBetween(final Byte value1, final Byte value2) {
            this.addCriterion("is_sent between", value1, value2, "isSent");
            return (Criteria) this;
        }

        public Criteria andIsSentNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("is_sent not between", value1, value2, "isSent");
            return (Criteria) this;
        }

        public Criteria andIsSentGreaterThan(final Byte value) {
            this.addCriterion("is_sent >", value, "isSent");
            return (Criteria) this;
        }

        public Criteria andIsSentGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("is_sent >=", value, "isSent");
            return (Criteria) this;
        }

        public Criteria andIsSentLessThan(final Byte value) {
            this.addCriterion("is_sent <", value, "isSent");
            return (Criteria) this;
        }

        public Criteria andIsSentLessThanOrEqualTo(final Byte value) {
            this.addCriterion("is_sent <=", value, "isSent");
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

        public Criteria andRetryTimesIsNull() {
            this.addCriterion("retry_times is null");
            return (Criteria) this;
        }

        public Criteria andRetryTimesIsNotNull() {
            this.addCriterion("retry_times is not null");
            return (Criteria) this;
        }

        public Criteria andRetryTimesEqualTo(final Integer value) {
            this.addCriterion("retry_times =", value, "retryTimes");
            return (Criteria) this;
        }

        public Criteria andRetryTimesNotEqualTo(final Integer value) {
            this.addCriterion("retry_times <>", value, "retryTimes");
            return (Criteria) this;
        }

        public Criteria andRetryTimesIn(final List<Integer> values) {
            this.addCriterion("retry_times in", values, "retryTimes");
            return (Criteria) this;
        }

        public Criteria andRetryTimesNotIn(final List<Integer> values) {
            this.addCriterion("retry_times not in", values, "retryTimes");
            return (Criteria) this;
        }

        public Criteria andRetryTimesBetween(final Integer value1, final Integer value2) {
            this.addCriterion("retry_times between", value1, value2, "retryTimes");
            return (Criteria) this;
        }

        public Criteria andRetryTimesNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("retry_times not between", value1, value2, "retryTimes");
            return (Criteria) this;
        }

        public Criteria andRetryTimesGreaterThan(final Integer value) {
            this.addCriterion("retry_times >", value, "retryTimes");
            return (Criteria) this;
        }

        public Criteria andRetryTimesGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("retry_times >=", value, "retryTimes");
            return (Criteria) this;
        }

        public Criteria andRetryTimesLessThan(final Integer value) {
            this.addCriterion("retry_times <", value, "retryTimes");
            return (Criteria) this;
        }

        public Criteria andRetryTimesLessThanOrEqualTo(final Integer value) {
            this.addCriterion("retry_times <=", value, "retryTimes");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeIsNull() {
            this.addCriterion("next_retry_time is null");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeIsNotNull() {
            this.addCriterion("next_retry_time is not null");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeEqualTo(final Date value) {
            this.addCriterion("next_retry_time =", value, "nextRetryTime");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeNotEqualTo(final Date value) {
            this.addCriterion("next_retry_time <>", value, "nextRetryTime");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeIn(final List<Date> values) {
            this.addCriterion("next_retry_time in", values, "nextRetryTime");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeNotIn(final List<Date> values) {
            this.addCriterion("next_retry_time not in", values, "nextRetryTime");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("next_retry_time between", value1, value2, "nextRetryTime");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("next_retry_time not between", value1, value2, "nextRetryTime");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeGreaterThan(final Date value) {
            this.addCriterion("next_retry_time >", value, "nextRetryTime");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("next_retry_time >=", value, "nextRetryTime");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeLessThan(final Date value) {
            this.addCriterion("next_retry_time <", value, "nextRetryTime");
            return (Criteria) this;
        }

        public Criteria andNextRetryTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("next_retry_time <=", value, "nextRetryTime");
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

        public Criteria andBrokerIdIsNull() {
            this.addCriterion("broker_id is null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNotNull() {
            this.addCriterion("broker_id is not null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdEqualTo(final Integer value) {
            this.addCriterion("broker_id =", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotEqualTo(final Integer value) {
            this.addCriterion("broker_id <>", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThan(final Integer value) {
            this.addCriterion("broker_id >", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("broker_id >=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThan(final Integer value) {
            this.addCriterion("broker_id <", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("broker_id <=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIn(final List<Integer> values) {
            this.addCriterion("broker_id in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotIn(final List<Integer> values) {
            this.addCriterion("broker_id not in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("broker_id between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("broker_id not between", value1, value2, "brokerId");
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
