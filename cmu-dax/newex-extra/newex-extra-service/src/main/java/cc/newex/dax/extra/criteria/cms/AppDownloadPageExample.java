package cc.newex.dax.extra.criteria.cms;

import cc.newex.dax.extra.domain.cms.AppDownloadPage;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * App下载页表 查询条件类
 *
 * @author mbg.generated
 * @date 2018-09-13 17:25:24
 */
public class AppDownloadPageExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppDownloadPageExample() {
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

    public static AppDownloadPageExample toExample(final AppDownloadPage appDownloadPage) {
        if (appDownloadPage == null) {
            return null;
        }

        final AppDownloadPageExample example = new AppDownloadPageExample();
        final AppDownloadPageExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(appDownloadPage.getId())) {
            criteria.andIdEqualTo(appDownloadPage.getId());
        }

        if (StringUtils.isNotBlank(appDownloadPage.getName())) {
            criteria.andNameLike(appDownloadPage.getName());
        }

        if (StringUtils.isNotBlank(appDownloadPage.getLocale())) {
            criteria.andLocaleEqualTo(appDownloadPage.getLocale());
        }

        if (StringUtils.isNotBlank(appDownloadPage.getUid())) {
            criteria.andUidEqualTo(appDownloadPage.getUid());
        }

        if (Objects.nonNull(appDownloadPage.getStatus())) {
            criteria.andStatusEqualTo(appDownloadPage.getStatus());
        }

        if (Objects.nonNull(appDownloadPage.getBrokerId())) {
            criteria.andBrokerIdEqualTo(appDownloadPage.getBrokerId());
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

        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(final String value) {
            this.addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(final String value) {
            this.addCriterion("name like", "%" + value + "%", "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(final String value) {
            this.addCriterion("name not like", value, "name");
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

        public Criteria andLogoImgIsNull() {
            this.addCriterion("logo_img is null");
            return (Criteria) this;
        }

        public Criteria andLogoImgIsNotNull() {
            this.addCriterion("logo_img is not null");
            return (Criteria) this;
        }

        public Criteria andLogoImgEqualTo(final String value) {
            this.addCriterion("logo_img =", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotEqualTo(final String value) {
            this.addCriterion("logo_img <>", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgGreaterThan(final String value) {
            this.addCriterion("logo_img >", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgGreaterThanOrEqualTo(final String value) {
            this.addCriterion("logo_img >=", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgLessThan(final String value) {
            this.addCriterion("logo_img <", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgLessThanOrEqualTo(final String value) {
            this.addCriterion("logo_img <=", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgLike(final String value) {
            this.addCriterion("logo_img like", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotLike(final String value) {
            this.addCriterion("logo_img not like", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgIn(final List<String> values) {
            this.addCriterion("logo_img in", values, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotIn(final List<String> values) {
            this.addCriterion("logo_img not in", values, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgBetween(final String value1, final String value2) {
            this.addCriterion("logo_img between", value1, value2, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotBetween(final String value1, final String value2) {
            this.addCriterion("logo_img not between", value1, value2, "logoImg");
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

        public Criteria andLocaleGreaterThan(final String value) {
            this.addCriterion("locale >", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleGreaterThanOrEqualTo(final String value) {
            this.addCriterion("locale >=", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleLessThan(final String value) {
            this.addCriterion("locale <", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleLessThanOrEqualTo(final String value) {
            this.addCriterion("locale <=", value, "locale");
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

        public Criteria andUidIsNull() {
            this.addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            this.addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(final String value) {
            this.addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(final String value) {
            this.addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(final String value) {
            this.addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(final String value) {
            this.addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(final String value) {
            this.addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(final String value) {
            this.addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLike(final String value) {
            this.addCriterion("uid like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotLike(final String value) {
            this.addCriterion("uid not like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(final List<String> values) {
            this.addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(final List<String> values) {
            this.addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(final String value1, final String value2) {
            this.addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(final String value1, final String value2) {
            this.addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andLinkIsNull() {
            this.addCriterion("link is null");
            return (Criteria) this;
        }

        public Criteria andLinkIsNotNull() {
            this.addCriterion("link is not null");
            return (Criteria) this;
        }

        public Criteria andLinkEqualTo(final String value) {
            this.addCriterion("link =", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotEqualTo(final String value) {
            this.addCriterion("link <>", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThan(final String value) {
            this.addCriterion("link >", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThanOrEqualTo(final String value) {
            this.addCriterion("link >=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThan(final String value) {
            this.addCriterion("link <", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThanOrEqualTo(final String value) {
            this.addCriterion("link <=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLike(final String value) {
            this.addCriterion("link like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotLike(final String value) {
            this.addCriterion("link not like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkIn(final List<String> values) {
            this.addCriterion("link in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotIn(final List<String> values) {
            this.addCriterion("link not in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkBetween(final String value1, final String value2) {
            this.addCriterion("link between", value1, value2, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotBetween(final String value1, final String value2) {
            this.addCriterion("link not between", value1, value2, "link");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlIsNull() {
            this.addCriterion("android_url is null");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlIsNotNull() {
            this.addCriterion("android_url is not null");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlEqualTo(final String value) {
            this.addCriterion("android_url =", value, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlNotEqualTo(final String value) {
            this.addCriterion("android_url <>", value, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlGreaterThan(final String value) {
            this.addCriterion("android_url >", value, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlGreaterThanOrEqualTo(final String value) {
            this.addCriterion("android_url >=", value, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlLessThan(final String value) {
            this.addCriterion("android_url <", value, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlLessThanOrEqualTo(final String value) {
            this.addCriterion("android_url <=", value, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlLike(final String value) {
            this.addCriterion("android_url like", value, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlNotLike(final String value) {
            this.addCriterion("android_url not like", value, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlIn(final List<String> values) {
            this.addCriterion("android_url in", values, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlNotIn(final List<String> values) {
            this.addCriterion("android_url not in", values, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlBetween(final String value1, final String value2) {
            this.addCriterion("android_url between", value1, value2, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andAndroidUrlNotBetween(final String value1, final String value2) {
            this.addCriterion("android_url not between", value1, value2, "androidUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlIsNull() {
            this.addCriterion("ios_url is null");
            return (Criteria) this;
        }

        public Criteria andIosUrlIsNotNull() {
            this.addCriterion("ios_url is not null");
            return (Criteria) this;
        }

        public Criteria andIosUrlEqualTo(final String value) {
            this.addCriterion("ios_url =", value, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlNotEqualTo(final String value) {
            this.addCriterion("ios_url <>", value, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlGreaterThan(final String value) {
            this.addCriterion("ios_url >", value, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlGreaterThanOrEqualTo(final String value) {
            this.addCriterion("ios_url >=", value, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlLessThan(final String value) {
            this.addCriterion("ios_url <", value, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlLessThanOrEqualTo(final String value) {
            this.addCriterion("ios_url <=", value, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlLike(final String value) {
            this.addCriterion("ios_url like", value, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlNotLike(final String value) {
            this.addCriterion("ios_url not like", value, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlIn(final List<String> values) {
            this.addCriterion("ios_url in", values, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlNotIn(final List<String> values) {
            this.addCriterion("ios_url not in", values, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlBetween(final String value1, final String value2) {
            this.addCriterion("ios_url between", value1, value2, "iosUrl");
            return (Criteria) this;
        }

        public Criteria andIosUrlNotBetween(final String value1, final String value2) {
            this.addCriterion("ios_url not between", value1, value2, "iosUrl");
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

        public Criteria andTemplateIdIsNull() {
            this.addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            this.addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(final Integer value) {
            this.addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(final Integer value) {
            this.addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(final Integer value) {
            this.addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(final Integer value) {
            this.addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(final List<Integer> values) {
            this.addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(final List<Integer> values) {
            this.addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andFirstTitleIsNull() {
            this.addCriterion("first_title is null");
            return (Criteria) this;
        }

        public Criteria andFirstTitleIsNotNull() {
            this.addCriterion("first_title is not null");
            return (Criteria) this;
        }

        public Criteria andFirstTitleEqualTo(final String value) {
            this.addCriterion("first_title =", value, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleNotEqualTo(final String value) {
            this.addCriterion("first_title <>", value, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleGreaterThan(final String value) {
            this.addCriterion("first_title >", value, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleGreaterThanOrEqualTo(final String value) {
            this.addCriterion("first_title >=", value, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleLessThan(final String value) {
            this.addCriterion("first_title <", value, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleLessThanOrEqualTo(final String value) {
            this.addCriterion("first_title <=", value, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleLike(final String value) {
            this.addCriterion("first_title like", value, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleNotLike(final String value) {
            this.addCriterion("first_title not like", value, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleIn(final List<String> values) {
            this.addCriterion("first_title in", values, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleNotIn(final List<String> values) {
            this.addCriterion("first_title not in", values, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleBetween(final String value1, final String value2) {
            this.addCriterion("first_title between", value1, value2, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstTitleNotBetween(final String value1, final String value2) {
            this.addCriterion("first_title not between", value1, value2, "firstTitle");
            return (Criteria) this;
        }

        public Criteria andFirstIntroIsNull() {
            this.addCriterion("first_intro is null");
            return (Criteria) this;
        }

        public Criteria andFirstIntroIsNotNull() {
            this.addCriterion("first_intro is not null");
            return (Criteria) this;
        }

        public Criteria andFirstIntroEqualTo(final String value) {
            this.addCriterion("first_intro =", value, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroNotEqualTo(final String value) {
            this.addCriterion("first_intro <>", value, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroGreaterThan(final String value) {
            this.addCriterion("first_intro >", value, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroGreaterThanOrEqualTo(final String value) {
            this.addCriterion("first_intro >=", value, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroLessThan(final String value) {
            this.addCriterion("first_intro <", value, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroLessThanOrEqualTo(final String value) {
            this.addCriterion("first_intro <=", value, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroLike(final String value) {
            this.addCriterion("first_intro like", value, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroNotLike(final String value) {
            this.addCriterion("first_intro not like", value, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroIn(final List<String> values) {
            this.addCriterion("first_intro in", values, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroNotIn(final List<String> values) {
            this.addCriterion("first_intro not in", values, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroBetween(final String value1, final String value2) {
            this.addCriterion("first_intro between", value1, value2, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstIntroNotBetween(final String value1, final String value2) {
            this.addCriterion("first_intro not between", value1, value2, "firstIntro");
            return (Criteria) this;
        }

        public Criteria andFirstImgIsNull() {
            this.addCriterion("first_img is null");
            return (Criteria) this;
        }

        public Criteria andFirstImgIsNotNull() {
            this.addCriterion("first_img is not null");
            return (Criteria) this;
        }

        public Criteria andFirstImgEqualTo(final String value) {
            this.addCriterion("first_img =", value, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgNotEqualTo(final String value) {
            this.addCriterion("first_img <>", value, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgGreaterThan(final String value) {
            this.addCriterion("first_img >", value, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgGreaterThanOrEqualTo(final String value) {
            this.addCriterion("first_img >=", value, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgLessThan(final String value) {
            this.addCriterion("first_img <", value, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgLessThanOrEqualTo(final String value) {
            this.addCriterion("first_img <=", value, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgLike(final String value) {
            this.addCriterion("first_img like", value, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgNotLike(final String value) {
            this.addCriterion("first_img not like", value, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgIn(final List<String> values) {
            this.addCriterion("first_img in", values, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgNotIn(final List<String> values) {
            this.addCriterion("first_img not in", values, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgBetween(final String value1, final String value2) {
            this.addCriterion("first_img between", value1, value2, "firstImg");
            return (Criteria) this;
        }

        public Criteria andFirstImgNotBetween(final String value1, final String value2) {
            this.addCriterion("first_img not between", value1, value2, "firstImg");
            return (Criteria) this;
        }

        public Criteria andSecondTitleIsNull() {
            this.addCriterion("second_title is null");
            return (Criteria) this;
        }

        public Criteria andSecondTitleIsNotNull() {
            this.addCriterion("second_title is not null");
            return (Criteria) this;
        }

        public Criteria andSecondTitleEqualTo(final String value) {
            this.addCriterion("second_title =", value, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleNotEqualTo(final String value) {
            this.addCriterion("second_title <>", value, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleGreaterThan(final String value) {
            this.addCriterion("second_title >", value, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleGreaterThanOrEqualTo(final String value) {
            this.addCriterion("second_title >=", value, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleLessThan(final String value) {
            this.addCriterion("second_title <", value, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleLessThanOrEqualTo(final String value) {
            this.addCriterion("second_title <=", value, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleLike(final String value) {
            this.addCriterion("second_title like", value, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleNotLike(final String value) {
            this.addCriterion("second_title not like", value, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleIn(final List<String> values) {
            this.addCriterion("second_title in", values, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleNotIn(final List<String> values) {
            this.addCriterion("second_title not in", values, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleBetween(final String value1, final String value2) {
            this.addCriterion("second_title between", value1, value2, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondTitleNotBetween(final String value1, final String value2) {
            this.addCriterion("second_title not between", value1, value2, "secondTitle");
            return (Criteria) this;
        }

        public Criteria andSecondIntroIsNull() {
            this.addCriterion("second_intro is null");
            return (Criteria) this;
        }

        public Criteria andSecondIntroIsNotNull() {
            this.addCriterion("second_intro is not null");
            return (Criteria) this;
        }

        public Criteria andSecondIntroEqualTo(final String value) {
            this.addCriterion("second_intro =", value, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroNotEqualTo(final String value) {
            this.addCriterion("second_intro <>", value, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroGreaterThan(final String value) {
            this.addCriterion("second_intro >", value, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroGreaterThanOrEqualTo(final String value) {
            this.addCriterion("second_intro >=", value, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroLessThan(final String value) {
            this.addCriterion("second_intro <", value, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroLessThanOrEqualTo(final String value) {
            this.addCriterion("second_intro <=", value, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroLike(final String value) {
            this.addCriterion("second_intro like", value, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroNotLike(final String value) {
            this.addCriterion("second_intro not like", value, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroIn(final List<String> values) {
            this.addCriterion("second_intro in", values, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroNotIn(final List<String> values) {
            this.addCriterion("second_intro not in", values, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroBetween(final String value1, final String value2) {
            this.addCriterion("second_intro between", value1, value2, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondIntroNotBetween(final String value1, final String value2) {
            this.addCriterion("second_intro not between", value1, value2, "secondIntro");
            return (Criteria) this;
        }

        public Criteria andSecondImgIsNull() {
            this.addCriterion("second_img is null");
            return (Criteria) this;
        }

        public Criteria andSecondImgIsNotNull() {
            this.addCriterion("second_img is not null");
            return (Criteria) this;
        }

        public Criteria andSecondImgEqualTo(final String value) {
            this.addCriterion("second_img =", value, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgNotEqualTo(final String value) {
            this.addCriterion("second_img <>", value, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgGreaterThan(final String value) {
            this.addCriterion("second_img >", value, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgGreaterThanOrEqualTo(final String value) {
            this.addCriterion("second_img >=", value, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgLessThan(final String value) {
            this.addCriterion("second_img <", value, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgLessThanOrEqualTo(final String value) {
            this.addCriterion("second_img <=", value, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgLike(final String value) {
            this.addCriterion("second_img like", value, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgNotLike(final String value) {
            this.addCriterion("second_img not like", value, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgIn(final List<String> values) {
            this.addCriterion("second_img in", values, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgNotIn(final List<String> values) {
            this.addCriterion("second_img not in", values, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgBetween(final String value1, final String value2) {
            this.addCriterion("second_img between", value1, value2, "secondImg");
            return (Criteria) this;
        }

        public Criteria andSecondImgNotBetween(final String value1, final String value2) {
            this.addCriterion("second_img not between", value1, value2, "secondImg");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1IsNull() {
            this.addCriterion("third_feature_1 is null");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1IsNotNull() {
            this.addCriterion("third_feature_1 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1EqualTo(final String value) {
            this.addCriterion("third_feature_1 =", value, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1NotEqualTo(final String value) {
            this.addCriterion("third_feature_1 <>", value, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1GreaterThan(final String value) {
            this.addCriterion("third_feature_1 >", value, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_feature_1 >=", value, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1LessThan(final String value) {
            this.addCriterion("third_feature_1 <", value, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1LessThanOrEqualTo(final String value) {
            this.addCriterion("third_feature_1 <=", value, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1Like(final String value) {
            this.addCriterion("third_feature_1 like", value, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1NotLike(final String value) {
            this.addCriterion("third_feature_1 not like", value, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1In(final List<String> values) {
            this.addCriterion("third_feature_1 in", values, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1NotIn(final List<String> values) {
            this.addCriterion("third_feature_1 not in", values, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1Between(final String value1, final String value2) {
            this.addCriterion("third_feature_1 between", value1, value2, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature1NotBetween(final String value1, final String value2) {
            this.addCriterion("third_feature_1 not between", value1, value2, "thirdFeature1");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2IsNull() {
            this.addCriterion("third_feature_2 is null");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2IsNotNull() {
            this.addCriterion("third_feature_2 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2EqualTo(final String value) {
            this.addCriterion("third_feature_2 =", value, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2NotEqualTo(final String value) {
            this.addCriterion("third_feature_2 <>", value, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2GreaterThan(final String value) {
            this.addCriterion("third_feature_2 >", value, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_feature_2 >=", value, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2LessThan(final String value) {
            this.addCriterion("third_feature_2 <", value, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2LessThanOrEqualTo(final String value) {
            this.addCriterion("third_feature_2 <=", value, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2Like(final String value) {
            this.addCriterion("third_feature_2 like", value, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2NotLike(final String value) {
            this.addCriterion("third_feature_2 not like", value, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2In(final List<String> values) {
            this.addCriterion("third_feature_2 in", values, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2NotIn(final List<String> values) {
            this.addCriterion("third_feature_2 not in", values, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2Between(final String value1, final String value2) {
            this.addCriterion("third_feature_2 between", value1, value2, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature2NotBetween(final String value1, final String value2) {
            this.addCriterion("third_feature_2 not between", value1, value2, "thirdFeature2");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3IsNull() {
            this.addCriterion("third_feature_3 is null");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3IsNotNull() {
            this.addCriterion("third_feature_3 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3EqualTo(final String value) {
            this.addCriterion("third_feature_3 =", value, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3NotEqualTo(final String value) {
            this.addCriterion("third_feature_3 <>", value, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3GreaterThan(final String value) {
            this.addCriterion("third_feature_3 >", value, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_feature_3 >=", value, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3LessThan(final String value) {
            this.addCriterion("third_feature_3 <", value, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3LessThanOrEqualTo(final String value) {
            this.addCriterion("third_feature_3 <=", value, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3Like(final String value) {
            this.addCriterion("third_feature_3 like", value, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3NotLike(final String value) {
            this.addCriterion("third_feature_3 not like", value, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3In(final List<String> values) {
            this.addCriterion("third_feature_3 in", values, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3NotIn(final List<String> values) {
            this.addCriterion("third_feature_3 not in", values, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3Between(final String value1, final String value2) {
            this.addCriterion("third_feature_3 between", value1, value2, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature3NotBetween(final String value1, final String value2) {
            this.addCriterion("third_feature_3 not between", value1, value2, "thirdFeature3");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4IsNull() {
            this.addCriterion("third_feature_4 is null");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4IsNotNull() {
            this.addCriterion("third_feature_4 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4EqualTo(final String value) {
            this.addCriterion("third_feature_4 =", value, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4NotEqualTo(final String value) {
            this.addCriterion("third_feature_4 <>", value, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4GreaterThan(final String value) {
            this.addCriterion("third_feature_4 >", value, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_feature_4 >=", value, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4LessThan(final String value) {
            this.addCriterion("third_feature_4 <", value, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4LessThanOrEqualTo(final String value) {
            this.addCriterion("third_feature_4 <=", value, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4Like(final String value) {
            this.addCriterion("third_feature_4 like", value, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4NotLike(final String value) {
            this.addCriterion("third_feature_4 not like", value, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4In(final List<String> values) {
            this.addCriterion("third_feature_4 in", values, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4NotIn(final List<String> values) {
            this.addCriterion("third_feature_4 not in", values, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4Between(final String value1, final String value2) {
            this.addCriterion("third_feature_4 between", value1, value2, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdFeature4NotBetween(final String value1, final String value2) {
            this.addCriterion("third_feature_4 not between", value1, value2, "thirdFeature4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1IsNull() {
            this.addCriterion("third_intro_1 is null");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1IsNotNull() {
            this.addCriterion("third_intro_1 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1EqualTo(final String value) {
            this.addCriterion("third_intro_1 =", value, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1NotEqualTo(final String value) {
            this.addCriterion("third_intro_1 <>", value, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1GreaterThan(final String value) {
            this.addCriterion("third_intro_1 >", value, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_intro_1 >=", value, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1LessThan(final String value) {
            this.addCriterion("third_intro_1 <", value, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1LessThanOrEqualTo(final String value) {
            this.addCriterion("third_intro_1 <=", value, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1Like(final String value) {
            this.addCriterion("third_intro_1 like", value, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1NotLike(final String value) {
            this.addCriterion("third_intro_1 not like", value, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1In(final List<String> values) {
            this.addCriterion("third_intro_1 in", values, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1NotIn(final List<String> values) {
            this.addCriterion("third_intro_1 not in", values, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1Between(final String value1, final String value2) {
            this.addCriterion("third_intro_1 between", value1, value2, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro1NotBetween(final String value1, final String value2) {
            this.addCriterion("third_intro_1 not between", value1, value2, "thirdIntro1");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2IsNull() {
            this.addCriterion("third_intro_2 is null");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2IsNotNull() {
            this.addCriterion("third_intro_2 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2EqualTo(final String value) {
            this.addCriterion("third_intro_2 =", value, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2NotEqualTo(final String value) {
            this.addCriterion("third_intro_2 <>", value, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2GreaterThan(final String value) {
            this.addCriterion("third_intro_2 >", value, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_intro_2 >=", value, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2LessThan(final String value) {
            this.addCriterion("third_intro_2 <", value, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2LessThanOrEqualTo(final String value) {
            this.addCriterion("third_intro_2 <=", value, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2Like(final String value) {
            this.addCriterion("third_intro_2 like", value, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2NotLike(final String value) {
            this.addCriterion("third_intro_2 not like", value, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2In(final List<String> values) {
            this.addCriterion("third_intro_2 in", values, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2NotIn(final List<String> values) {
            this.addCriterion("third_intro_2 not in", values, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2Between(final String value1, final String value2) {
            this.addCriterion("third_intro_2 between", value1, value2, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro2NotBetween(final String value1, final String value2) {
            this.addCriterion("third_intro_2 not between", value1, value2, "thirdIntro2");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3IsNull() {
            this.addCriterion("third_intro_3 is null");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3IsNotNull() {
            this.addCriterion("third_intro_3 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3EqualTo(final String value) {
            this.addCriterion("third_intro_3 =", value, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3NotEqualTo(final String value) {
            this.addCriterion("third_intro_3 <>", value, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3GreaterThan(final String value) {
            this.addCriterion("third_intro_3 >", value, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_intro_3 >=", value, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3LessThan(final String value) {
            this.addCriterion("third_intro_3 <", value, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3LessThanOrEqualTo(final String value) {
            this.addCriterion("third_intro_3 <=", value, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3Like(final String value) {
            this.addCriterion("third_intro_3 like", value, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3NotLike(final String value) {
            this.addCriterion("third_intro_3 not like", value, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3In(final List<String> values) {
            this.addCriterion("third_intro_3 in", values, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3NotIn(final List<String> values) {
            this.addCriterion("third_intro_3 not in", values, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3Between(final String value1, final String value2) {
            this.addCriterion("third_intro_3 between", value1, value2, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro3NotBetween(final String value1, final String value2) {
            this.addCriterion("third_intro_3 not between", value1, value2, "thirdIntro3");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4IsNull() {
            this.addCriterion("third_intro_4 is null");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4IsNotNull() {
            this.addCriterion("third_intro_4 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4EqualTo(final String value) {
            this.addCriterion("third_intro_4 =", value, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4NotEqualTo(final String value) {
            this.addCriterion("third_intro_4 <>", value, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4GreaterThan(final String value) {
            this.addCriterion("third_intro_4 >", value, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_intro_4 >=", value, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4LessThan(final String value) {
            this.addCriterion("third_intro_4 <", value, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4LessThanOrEqualTo(final String value) {
            this.addCriterion("third_intro_4 <=", value, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4Like(final String value) {
            this.addCriterion("third_intro_4 like", value, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4NotLike(final String value) {
            this.addCriterion("third_intro_4 not like", value, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4In(final List<String> values) {
            this.addCriterion("third_intro_4 in", values, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4NotIn(final List<String> values) {
            this.addCriterion("third_intro_4 not in", values, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4Between(final String value1, final String value2) {
            this.addCriterion("third_intro_4 between", value1, value2, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdIntro4NotBetween(final String value1, final String value2) {
            this.addCriterion("third_intro_4 not between", value1, value2, "thirdIntro4");
            return (Criteria) this;
        }

        public Criteria andThirdImg1IsNull() {
            this.addCriterion("third_img_1 is null");
            return (Criteria) this;
        }

        public Criteria andThirdImg1IsNotNull() {
            this.addCriterion("third_img_1 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdImg1EqualTo(final String value) {
            this.addCriterion("third_img_1 =", value, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1NotEqualTo(final String value) {
            this.addCriterion("third_img_1 <>", value, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1GreaterThan(final String value) {
            this.addCriterion("third_img_1 >", value, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_img_1 >=", value, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1LessThan(final String value) {
            this.addCriterion("third_img_1 <", value, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1LessThanOrEqualTo(final String value) {
            this.addCriterion("third_img_1 <=", value, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1Like(final String value) {
            this.addCriterion("third_img_1 like", value, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1NotLike(final String value) {
            this.addCriterion("third_img_1 not like", value, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1In(final List<String> values) {
            this.addCriterion("third_img_1 in", values, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1NotIn(final List<String> values) {
            this.addCriterion("third_img_1 not in", values, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1Between(final String value1, final String value2) {
            this.addCriterion("third_img_1 between", value1, value2, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg1NotBetween(final String value1, final String value2) {
            this.addCriterion("third_img_1 not between", value1, value2, "thirdImg1");
            return (Criteria) this;
        }

        public Criteria andThirdImg2IsNull() {
            this.addCriterion("third_img_2 is null");
            return (Criteria) this;
        }

        public Criteria andThirdImg2IsNotNull() {
            this.addCriterion("third_img_2 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdImg2EqualTo(final String value) {
            this.addCriterion("third_img_2 =", value, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2NotEqualTo(final String value) {
            this.addCriterion("third_img_2 <>", value, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2GreaterThan(final String value) {
            this.addCriterion("third_img_2 >", value, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_img_2 >=", value, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2LessThan(final String value) {
            this.addCriterion("third_img_2 <", value, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2LessThanOrEqualTo(final String value) {
            this.addCriterion("third_img_2 <=", value, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2Like(final String value) {
            this.addCriterion("third_img_2 like", value, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2NotLike(final String value) {
            this.addCriterion("third_img_2 not like", value, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2In(final List<String> values) {
            this.addCriterion("third_img_2 in", values, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2NotIn(final List<String> values) {
            this.addCriterion("third_img_2 not in", values, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2Between(final String value1, final String value2) {
            this.addCriterion("third_img_2 between", value1, value2, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg2NotBetween(final String value1, final String value2) {
            this.addCriterion("third_img_2 not between", value1, value2, "thirdImg2");
            return (Criteria) this;
        }

        public Criteria andThirdImg3IsNull() {
            this.addCriterion("third_img_3 is null");
            return (Criteria) this;
        }

        public Criteria andThirdImg3IsNotNull() {
            this.addCriterion("third_img_3 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdImg3EqualTo(final String value) {
            this.addCriterion("third_img_3 =", value, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3NotEqualTo(final String value) {
            this.addCriterion("third_img_3 <>", value, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3GreaterThan(final String value) {
            this.addCriterion("third_img_3 >", value, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_img_3 >=", value, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3LessThan(final String value) {
            this.addCriterion("third_img_3 <", value, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3LessThanOrEqualTo(final String value) {
            this.addCriterion("third_img_3 <=", value, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3Like(final String value) {
            this.addCriterion("third_img_3 like", value, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3NotLike(final String value) {
            this.addCriterion("third_img_3 not like", value, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3In(final List<String> values) {
            this.addCriterion("third_img_3 in", values, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3NotIn(final List<String> values) {
            this.addCriterion("third_img_3 not in", values, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3Between(final String value1, final String value2) {
            this.addCriterion("third_img_3 between", value1, value2, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg3NotBetween(final String value1, final String value2) {
            this.addCriterion("third_img_3 not between", value1, value2, "thirdImg3");
            return (Criteria) this;
        }

        public Criteria andThirdImg4IsNull() {
            this.addCriterion("third_img_4 is null");
            return (Criteria) this;
        }

        public Criteria andThirdImg4IsNotNull() {
            this.addCriterion("third_img_4 is not null");
            return (Criteria) this;
        }

        public Criteria andThirdImg4EqualTo(final String value) {
            this.addCriterion("third_img_4 =", value, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4NotEqualTo(final String value) {
            this.addCriterion("third_img_4 <>", value, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4GreaterThan(final String value) {
            this.addCriterion("third_img_4 >", value, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4GreaterThanOrEqualTo(final String value) {
            this.addCriterion("third_img_4 >=", value, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4LessThan(final String value) {
            this.addCriterion("third_img_4 <", value, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4LessThanOrEqualTo(final String value) {
            this.addCriterion("third_img_4 <=", value, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4Like(final String value) {
            this.addCriterion("third_img_4 like", value, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4NotLike(final String value) {
            this.addCriterion("third_img_4 not like", value, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4In(final List<String> values) {
            this.addCriterion("third_img_4 in", values, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4NotIn(final List<String> values) {
            this.addCriterion("third_img_4 not in", values, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4Between(final String value1, final String value2) {
            this.addCriterion("third_img_4 between", value1, value2, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andThirdImg4NotBetween(final String value1, final String value2) {
            this.addCriterion("third_img_4 not between", value1, value2, "thirdImg4");
            return (Criteria) this;
        }

        public Criteria andPublisherIdIsNull() {
            this.addCriterion("publisher_id is null");
            return (Criteria) this;
        }

        public Criteria andPublisherIdIsNotNull() {
            this.addCriterion("publisher_id is not null");
            return (Criteria) this;
        }

        public Criteria andPublisherIdEqualTo(final Integer value) {
            this.addCriterion("publisher_id =", value, "publisherId");
            return (Criteria) this;
        }

        public Criteria andPublisherIdNotEqualTo(final Integer value) {
            this.addCriterion("publisher_id <>", value, "publisherId");
            return (Criteria) this;
        }

        public Criteria andPublisherIdGreaterThan(final Integer value) {
            this.addCriterion("publisher_id >", value, "publisherId");
            return (Criteria) this;
        }

        public Criteria andPublisherIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("publisher_id >=", value, "publisherId");
            return (Criteria) this;
        }

        public Criteria andPublisherIdLessThan(final Integer value) {
            this.addCriterion("publisher_id <", value, "publisherId");
            return (Criteria) this;
        }

        public Criteria andPublisherIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("publisher_id <=", value, "publisherId");
            return (Criteria) this;
        }

        public Criteria andPublisherIdIn(final List<Integer> values) {
            this.addCriterion("publisher_id in", values, "publisherId");
            return (Criteria) this;
        }

        public Criteria andPublisherIdNotIn(final List<Integer> values) {
            this.addCriterion("publisher_id not in", values, "publisherId");
            return (Criteria) this;
        }

        public Criteria andPublisherIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("publisher_id between", value1, value2, "publisherId");
            return (Criteria) this;
        }

        public Criteria andPublisherIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("publisher_id not between", value1, value2, "publisherId");
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