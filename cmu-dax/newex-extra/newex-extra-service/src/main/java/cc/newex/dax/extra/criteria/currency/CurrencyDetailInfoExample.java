package cc.newex.dax.extra.criteria.currency;

import cc.newex.dax.extra.domain.currency.CurrencyDetailInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 全球数字货币详细信息表 查询条件类
 *
 * @author mbg.generated
 * @date 2019-01-17 16:07:55
 */
public class CurrencyDetailInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CurrencyDetailInfoExample() {
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

    public static CurrencyDetailInfoExample toExample(final CurrencyDetailInfo currencyDetailInfo) {
        if (currencyDetailInfo == null) {
            return null;
        }

        final CurrencyDetailInfoExample example = new CurrencyDetailInfoExample();
        final CurrencyDetailInfoExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(currencyDetailInfo.getId())) {
            criteria.andIdEqualTo(currencyDetailInfo.getId());
        }

        if (StringUtils.isNotBlank(currencyDetailInfo.getCode())) {
            criteria.andCodeEqualTo(currencyDetailInfo.getCode());
        }

        if (StringUtils.isNotBlank(currencyDetailInfo.getLocale())) {
            criteria.andLocaleEqualTo(currencyDetailInfo.getLocale());
        }

        if (StringUtils.isNotBlank(currencyDetailInfo.getWhitePaper())) {
            criteria.andWhitePaperLike(currencyDetailInfo.getWhitePaper());
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

        public Criteria andCodeIsNull() {
            this.addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            this.addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(final String value) {
            this.addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(final String value) {
            this.addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(final String value) {
            this.addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(final String value) {
            this.addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(final String value) {
            this.addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(final String value) {
            this.addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(final String value) {
            this.addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(final List<String> values) {
            this.addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(final List<String> values) {
            this.addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(final String value1, final String value2) {
            this.addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(final String value1, final String value2) {
            this.addCriterion("code not between", value1, value2, "code");
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

        public Criteria andConceptIsNull() {
            this.addCriterion("concept is null");
            return (Criteria) this;
        }

        public Criteria andConceptIsNotNull() {
            this.addCriterion("concept is not null");
            return (Criteria) this;
        }

        public Criteria andConceptEqualTo(final String value) {
            this.addCriterion("concept =", value, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptNotEqualTo(final String value) {
            this.addCriterion("concept <>", value, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptGreaterThan(final String value) {
            this.addCriterion("concept >", value, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptGreaterThanOrEqualTo(final String value) {
            this.addCriterion("concept >=", value, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptLessThan(final String value) {
            this.addCriterion("concept <", value, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptLessThanOrEqualTo(final String value) {
            this.addCriterion("concept <=", value, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptLike(final String value) {
            this.addCriterion("concept like", value, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptNotLike(final String value) {
            this.addCriterion("concept not like", value, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptIn(final List<String> values) {
            this.addCriterion("concept in", values, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptNotIn(final List<String> values) {
            this.addCriterion("concept not in", values, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptBetween(final String value1, final String value2) {
            this.addCriterion("concept between", value1, value2, "concept");
            return (Criteria) this;
        }

        public Criteria andConceptNotBetween(final String value1, final String value2) {
            this.addCriterion("concept not between", value1, value2, "concept");
            return (Criteria) this;
        }

        public Criteria andWhitePaperIsNull() {
            this.addCriterion("white_paper is null");
            return (Criteria) this;
        }

        public Criteria andWhitePaperIsNotNull() {
            this.addCriterion("white_paper is not null");
            return (Criteria) this;
        }

        public Criteria andWhitePaperEqualTo(final String value) {
            this.addCriterion("white_paper =", value, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperNotEqualTo(final String value) {
            this.addCriterion("white_paper <>", value, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperGreaterThan(final String value) {
            this.addCriterion("white_paper >", value, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperGreaterThanOrEqualTo(final String value) {
            this.addCriterion("white_paper >=", value, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperLessThan(final String value) {
            this.addCriterion("white_paper <", value, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperLessThanOrEqualTo(final String value) {
            this.addCriterion("white_paper <=", value, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperLike(final String value) {
            this.addCriterion("white_paper like", value, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperNotLike(final String value) {
            this.addCriterion("white_paper not like", value, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperIn(final List<String> values) {
            this.addCriterion("white_paper in", values, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperNotIn(final List<String> values) {
            this.addCriterion("white_paper not in", values, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperBetween(final String value1, final String value2) {
            this.addCriterion("white_paper between", value1, value2, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andWhitePaperNotBetween(final String value1, final String value2) {
            this.addCriterion("white_paper not between", value1, value2, "whitePaper");
            return (Criteria) this;
        }

        public Criteria andIntroductionIsNull() {
            this.addCriterion("introduction is null");
            return (Criteria) this;
        }

        public Criteria andIntroductionIsNotNull() {
            this.addCriterion("introduction is not null");
            return (Criteria) this;
        }

        public Criteria andIntroductionEqualTo(final String value) {
            this.addCriterion("introduction =", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotEqualTo(final String value) {
            this.addCriterion("introduction <>", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionGreaterThan(final String value) {
            this.addCriterion("introduction >", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionGreaterThanOrEqualTo(final String value) {
            this.addCriterion("introduction >=", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLessThan(final String value) {
            this.addCriterion("introduction <", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLessThanOrEqualTo(final String value) {
            this.addCriterion("introduction <=", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLike(final String value) {
            this.addCriterion("introduction like", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotLike(final String value) {
            this.addCriterion("introduction not like", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionIn(final List<String> values) {
            this.addCriterion("introduction in", values, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotIn(final List<String> values) {
            this.addCriterion("introduction not in", values, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionBetween(final String value1, final String value2) {
            this.addCriterion("introduction between", value1, value2, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotBetween(final String value1, final String value2) {
            this.addCriterion("introduction not between", value1, value2, "introduction");
            return (Criteria) this;
        }

        public Criteria andTelegramIsNull() {
            this.addCriterion("telegram is null");
            return (Criteria) this;
        }

        public Criteria andTelegramIsNotNull() {
            this.addCriterion("telegram is not null");
            return (Criteria) this;
        }

        public Criteria andTelegramEqualTo(final String value) {
            this.addCriterion("telegram =", value, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramNotEqualTo(final String value) {
            this.addCriterion("telegram <>", value, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramGreaterThan(final String value) {
            this.addCriterion("telegram >", value, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramGreaterThanOrEqualTo(final String value) {
            this.addCriterion("telegram >=", value, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramLessThan(final String value) {
            this.addCriterion("telegram <", value, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramLessThanOrEqualTo(final String value) {
            this.addCriterion("telegram <=", value, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramLike(final String value) {
            this.addCriterion("telegram like", value, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramNotLike(final String value) {
            this.addCriterion("telegram not like", value, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramIn(final List<String> values) {
            this.addCriterion("telegram in", values, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramNotIn(final List<String> values) {
            this.addCriterion("telegram not in", values, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramBetween(final String value1, final String value2) {
            this.addCriterion("telegram between", value1, value2, "telegram");
            return (Criteria) this;
        }

        public Criteria andTelegramNotBetween(final String value1, final String value2) {
            this.addCriterion("telegram not between", value1, value2, "telegram");
            return (Criteria) this;
        }

        public Criteria andFacebookIsNull() {
            this.addCriterion("facebook is null");
            return (Criteria) this;
        }

        public Criteria andFacebookIsNotNull() {
            this.addCriterion("facebook is not null");
            return (Criteria) this;
        }

        public Criteria andFacebookEqualTo(final String value) {
            this.addCriterion("facebook =", value, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookNotEqualTo(final String value) {
            this.addCriterion("facebook <>", value, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookGreaterThan(final String value) {
            this.addCriterion("facebook >", value, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookGreaterThanOrEqualTo(final String value) {
            this.addCriterion("facebook >=", value, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookLessThan(final String value) {
            this.addCriterion("facebook <", value, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookLessThanOrEqualTo(final String value) {
            this.addCriterion("facebook <=", value, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookLike(final String value) {
            this.addCriterion("facebook like", value, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookNotLike(final String value) {
            this.addCriterion("facebook not like", value, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookIn(final List<String> values) {
            this.addCriterion("facebook in", values, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookNotIn(final List<String> values) {
            this.addCriterion("facebook not in", values, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookBetween(final String value1, final String value2) {
            this.addCriterion("facebook between", value1, value2, "facebook");
            return (Criteria) this;
        }

        public Criteria andFacebookNotBetween(final String value1, final String value2) {
            this.addCriterion("facebook not between", value1, value2, "facebook");
            return (Criteria) this;
        }

        public Criteria andTwitterIsNull() {
            this.addCriterion("twitter is null");
            return (Criteria) this;
        }

        public Criteria andTwitterIsNotNull() {
            this.addCriterion("twitter is not null");
            return (Criteria) this;
        }

        public Criteria andTwitterEqualTo(final String value) {
            this.addCriterion("twitter =", value, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterNotEqualTo(final String value) {
            this.addCriterion("twitter <>", value, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterGreaterThan(final String value) {
            this.addCriterion("twitter >", value, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterGreaterThanOrEqualTo(final String value) {
            this.addCriterion("twitter >=", value, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterLessThan(final String value) {
            this.addCriterion("twitter <", value, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterLessThanOrEqualTo(final String value) {
            this.addCriterion("twitter <=", value, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterLike(final String value) {
            this.addCriterion("twitter like", value, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterNotLike(final String value) {
            this.addCriterion("twitter not like", value, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterIn(final List<String> values) {
            this.addCriterion("twitter in", values, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterNotIn(final List<String> values) {
            this.addCriterion("twitter not in", values, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterBetween(final String value1, final String value2) {
            this.addCriterion("twitter between", value1, value2, "twitter");
            return (Criteria) this;
        }

        public Criteria andTwitterNotBetween(final String value1, final String value2) {
            this.addCriterion("twitter not between", value1, value2, "twitter");
            return (Criteria) this;
        }

        public Criteria andWeiboIsNull() {
            this.addCriterion("weibo is null");
            return (Criteria) this;
        }

        public Criteria andWeiboIsNotNull() {
            this.addCriterion("weibo is not null");
            return (Criteria) this;
        }

        public Criteria andWeiboEqualTo(final String value) {
            this.addCriterion("weibo =", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotEqualTo(final String value) {
            this.addCriterion("weibo <>", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboGreaterThan(final String value) {
            this.addCriterion("weibo >", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboGreaterThanOrEqualTo(final String value) {
            this.addCriterion("weibo >=", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboLessThan(final String value) {
            this.addCriterion("weibo <", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboLessThanOrEqualTo(final String value) {
            this.addCriterion("weibo <=", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboLike(final String value) {
            this.addCriterion("weibo like", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotLike(final String value) {
            this.addCriterion("weibo not like", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboIn(final List<String> values) {
            this.addCriterion("weibo in", values, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotIn(final List<String> values) {
            this.addCriterion("weibo not in", values, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboBetween(final String value1, final String value2) {
            this.addCriterion("weibo between", value1, value2, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotBetween(final String value1, final String value2) {
            this.addCriterion("weibo not between", value1, value2, "weibo");
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

        public Criteria andSortIsNull() {
            this.addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            this.addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(final Integer value) {
            this.addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(final Integer value) {
            this.addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(final Integer value) {
            this.addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(final Integer value) {
            this.addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(final Integer value) {
            this.addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(final List<Integer> values) {
            this.addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(final List<Integer> values) {
            this.addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(final Integer value1, final Integer value2) {
            this.addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("sort not between", value1, value2, "sort");
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

        public Criteria andCoinMarketCapUrlIsNull() {
            this.addCriterion("coin_market_cap_url is null");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlIsNotNull() {
            this.addCriterion("coin_market_cap_url is not null");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlEqualTo(final String value) {
            this.addCriterion("coin_market_cap_url =", value, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlNotEqualTo(final String value) {
            this.addCriterion("coin_market_cap_url <>", value, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlGreaterThan(final String value) {
            this.addCriterion("coin_market_cap_url >", value, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlGreaterThanOrEqualTo(final String value) {
            this.addCriterion("coin_market_cap_url >=", value, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlLessThan(final String value) {
            this.addCriterion("coin_market_cap_url <", value, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlLessThanOrEqualTo(final String value) {
            this.addCriterion("coin_market_cap_url <=", value, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlLike(final String value) {
            this.addCriterion("coin_market_cap_url like", value, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlNotLike(final String value) {
            this.addCriterion("coin_market_cap_url not like", value, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlIn(final List<String> values) {
            this.addCriterion("coin_market_cap_url in", values, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlNotIn(final List<String> values) {
            this.addCriterion("coin_market_cap_url not in", values, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlBetween(final String value1, final String value2) {
            this.addCriterion("coin_market_cap_url between", value1, value2, "coinMarketCapUrl");
            return (Criteria) this;
        }

        public Criteria andCoinMarketCapUrlNotBetween(final String value1, final String value2) {
            this.addCriterion("coin_market_cap_url not between", value1, value2, "coinMarketCapUrl");
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