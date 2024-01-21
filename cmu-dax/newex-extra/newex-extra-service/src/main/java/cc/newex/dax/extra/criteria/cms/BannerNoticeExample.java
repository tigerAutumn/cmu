package cc.newex.dax.extra.criteria.cms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 首页banner广告表 查询条件类
 *
 * @author mbg.generated
 * @date 2018-09-12 16:53:57
 */
public class BannerNoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BannerNoticeExample() {
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

        public Criteria andTitleIsNull() {
            this.addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            this.addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(final String value) {
            this.addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(final String value) {
            this.addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(final String value) {
            this.addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(final String value) {
            this.addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(final String value) {
            this.addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(final String value) {
            this.addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(final String value) {
            this.addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(final String value) {
            this.addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(final List<String> values) {
            this.addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(final List<String> values) {
            this.addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(final String value1, final String value2) {
            this.addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(final String value1, final String value2) {
            this.addCriterion("title not between", value1, value2, "title");
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

        public Criteria andTypeIsNull() {
            this.addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            this.addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(final Integer value) {
            this.addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(final Integer value) {
            this.addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(final Integer value) {
            this.addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(final Integer value) {
            this.addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(final List<Integer> values) {
            this.addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(final List<Integer> values) {
            this.addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("type not between", value1, value2, "type");
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

        public Criteria andTextIsNull() {
            this.addCriterion("text is null");
            return (Criteria) this;
        }

        public Criteria andTextIsNotNull() {
            this.addCriterion("text is not null");
            return (Criteria) this;
        }

        public Criteria andTextEqualTo(final String value) {
            this.addCriterion("text =", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotEqualTo(final String value) {
            this.addCriterion("text <>", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThan(final String value) {
            this.addCriterion("text >", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThanOrEqualTo(final String value) {
            this.addCriterion("text >=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThan(final String value) {
            this.addCriterion("text <", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThanOrEqualTo(final String value) {
            this.addCriterion("text <=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLike(final String value) {
            this.addCriterion("text like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotLike(final String value) {
            this.addCriterion("text not like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextIn(final List<String> values) {
            this.addCriterion("text in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotIn(final List<String> values) {
            this.addCriterion("text not in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextBetween(final String value1, final String value2) {
            this.addCriterion("text between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotBetween(final String value1, final String value2) {
            this.addCriterion("text not between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andImageUrlIsNull() {
            this.addCriterion("image_url is null");
            return (Criteria) this;
        }

        public Criteria andImageUrlIsNotNull() {
            this.addCriterion("image_url is not null");
            return (Criteria) this;
        }

        public Criteria andImageUrlEqualTo(final String value) {
            this.addCriterion("image_url =", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotEqualTo(final String value) {
            this.addCriterion("image_url <>", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlGreaterThan(final String value) {
            this.addCriterion("image_url >", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlGreaterThanOrEqualTo(final String value) {
            this.addCriterion("image_url >=", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLessThan(final String value) {
            this.addCriterion("image_url <", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLessThanOrEqualTo(final String value) {
            this.addCriterion("image_url <=", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLike(final String value) {
            this.addCriterion("image_url like", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotLike(final String value) {
            this.addCriterion("image_url not like", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlIn(final List<String> values) {
            this.addCriterion("image_url in", values, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotIn(final List<String> values) {
            this.addCriterion("image_url not in", values, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlBetween(final String value1, final String value2) {
            this.addCriterion("image_url between", value1, value2, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotBetween(final String value1, final String value2) {
            this.addCriterion("image_url not between", value1, value2, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlIsNull() {
            this.addCriterion("original_image_url is null");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlIsNotNull() {
            this.addCriterion("original_image_url is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlEqualTo(final String value) {
            this.addCriterion("original_image_url =", value, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlNotEqualTo(final String value) {
            this.addCriterion("original_image_url <>", value, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlGreaterThan(final String value) {
            this.addCriterion("original_image_url >", value, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlGreaterThanOrEqualTo(final String value) {
            this.addCriterion("original_image_url >=", value, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlLessThan(final String value) {
            this.addCriterion("original_image_url <", value, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlLessThanOrEqualTo(final String value) {
            this.addCriterion("original_image_url <=", value, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlLike(final String value) {
            this.addCriterion("original_image_url like", value, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlNotLike(final String value) {
            this.addCriterion("original_image_url not like", value, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlIn(final List<String> values) {
            this.addCriterion("original_image_url in", values, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlNotIn(final List<String> values) {
            this.addCriterion("original_image_url not in", values, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlBetween(final String value1, final String value2) {
            this.addCriterion("original_image_url between", value1, value2, "originalImageUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalImageUrlNotBetween(final String value1, final String value2) {
            this.addCriterion("original_image_url not between", value1, value2, "originalImageUrl");
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

        public Criteria andPlatformIsNull() {
            this.addCriterion("platform is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNotNull() {
            this.addCriterion("platform is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformEqualTo(final Byte value) {
            this.addCriterion("platform =", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotEqualTo(final Byte value) {
            this.addCriterion("platform <>", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThan(final Byte value) {
            this.addCriterion("platform >", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("platform >=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThan(final Byte value) {
            this.addCriterion("platform <", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThanOrEqualTo(final Byte value) {
            this.addCriterion("platform <=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformIn(final List<Byte> values) {
            this.addCriterion("platform in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotIn(final List<Byte> values) {
            this.addCriterion("platform not in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformBetween(final Byte value1, final Byte value2) {
            this.addCriterion("platform between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("platform not between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            this.addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            this.addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(final Date value) {
            this.addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(final Date value) {
            this.addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(final Date value) {
            this.addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(final Date value) {
            this.addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(final List<Date> values) {
            this.addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(final List<Date> values) {
            this.addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            this.addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            this.addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(final Date value) {
            this.addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(final Date value) {
            this.addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(final Date value) {
            this.addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(final Date value) {
            this.addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(final List<Date> values) {
            this.addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(final List<Date> values) {
            this.addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("end_time not between", value1, value2, "endTime");
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

        public Criteria andPublishUserIsNull() {
            this.addCriterion("publish_user is null");
            return (Criteria) this;
        }

        public Criteria andPublishUserIsNotNull() {
            this.addCriterion("publish_user is not null");
            return (Criteria) this;
        }

        public Criteria andPublishUserEqualTo(final String value) {
            this.addCriterion("publish_user =", value, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserNotEqualTo(final String value) {
            this.addCriterion("publish_user <>", value, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserGreaterThan(final String value) {
            this.addCriterion("publish_user >", value, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserGreaterThanOrEqualTo(final String value) {
            this.addCriterion("publish_user >=", value, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserLessThan(final String value) {
            this.addCriterion("publish_user <", value, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserLessThanOrEqualTo(final String value) {
            this.addCriterion("publish_user <=", value, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserLike(final String value) {
            this.addCriterion("publish_user like", value, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserNotLike(final String value) {
            this.addCriterion("publish_user not like", value, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserIn(final List<String> values) {
            this.addCriterion("publish_user in", values, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserNotIn(final List<String> values) {
            this.addCriterion("publish_user not in", values, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserBetween(final String value1, final String value2) {
            this.addCriterion("publish_user between", value1, value2, "publishUser");
            return (Criteria) this;
        }

        public Criteria andPublishUserNotBetween(final String value1, final String value2) {
            this.addCriterion("publish_user not between", value1, value2, "publishUser");
            return (Criteria) this;
        }

        public Criteria andRndNumIsNull() {
            this.addCriterion("rnd_num is null");
            return (Criteria) this;
        }

        public Criteria andRndNumIsNotNull() {
            this.addCriterion("rnd_num is not null");
            return (Criteria) this;
        }

        public Criteria andRndNumEqualTo(final String value) {
            this.addCriterion("rnd_num =", value, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumNotEqualTo(final String value) {
            this.addCriterion("rnd_num <>", value, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumGreaterThan(final String value) {
            this.addCriterion("rnd_num >", value, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumGreaterThanOrEqualTo(final String value) {
            this.addCriterion("rnd_num >=", value, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumLessThan(final String value) {
            this.addCriterion("rnd_num <", value, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumLessThanOrEqualTo(final String value) {
            this.addCriterion("rnd_num <=", value, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumLike(final String value) {
            this.addCriterion("rnd_num like", value, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumNotLike(final String value) {
            this.addCriterion("rnd_num not like", value, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumIn(final List<String> values) {
            this.addCriterion("rnd_num in", values, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumNotIn(final List<String> values) {
            this.addCriterion("rnd_num not in", values, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumBetween(final String value1, final String value2) {
            this.addCriterion("rnd_num between", value1, value2, "rndNum");
            return (Criteria) this;
        }

        public Criteria andRndNumNotBetween(final String value1, final String value2) {
            this.addCriterion("rnd_num not between", value1, value2, "rndNum");
            return (Criteria) this;
        }

        public Criteria andShareTitleIsNull() {
            this.addCriterion("share_title is null");
            return (Criteria) this;
        }

        public Criteria andShareTitleIsNotNull() {
            this.addCriterion("share_title is not null");
            return (Criteria) this;
        }

        public Criteria andShareTitleEqualTo(final String value) {
            this.addCriterion("share_title =", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleNotEqualTo(final String value) {
            this.addCriterion("share_title <>", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleGreaterThan(final String value) {
            this.addCriterion("share_title >", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleGreaterThanOrEqualTo(final String value) {
            this.addCriterion("share_title >=", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleLessThan(final String value) {
            this.addCriterion("share_title <", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleLessThanOrEqualTo(final String value) {
            this.addCriterion("share_title <=", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleLike(final String value) {
            this.addCriterion("share_title like", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleNotLike(final String value) {
            this.addCriterion("share_title not like", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleIn(final List<String> values) {
            this.addCriterion("share_title in", values, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleNotIn(final List<String> values) {
            this.addCriterion("share_title not in", values, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleBetween(final String value1, final String value2) {
            this.addCriterion("share_title between", value1, value2, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleNotBetween(final String value1, final String value2) {
            this.addCriterion("share_title not between", value1, value2, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTextIsNull() {
            this.addCriterion("share_text is null");
            return (Criteria) this;
        }

        public Criteria andShareTextIsNotNull() {
            this.addCriterion("share_text is not null");
            return (Criteria) this;
        }

        public Criteria andShareTextEqualTo(final String value) {
            this.addCriterion("share_text =", value, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextNotEqualTo(final String value) {
            this.addCriterion("share_text <>", value, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextGreaterThan(final String value) {
            this.addCriterion("share_text >", value, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextGreaterThanOrEqualTo(final String value) {
            this.addCriterion("share_text >=", value, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextLessThan(final String value) {
            this.addCriterion("share_text <", value, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextLessThanOrEqualTo(final String value) {
            this.addCriterion("share_text <=", value, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextLike(final String value) {
            this.addCriterion("share_text like", value, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextNotLike(final String value) {
            this.addCriterion("share_text not like", value, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextIn(final List<String> values) {
            this.addCriterion("share_text in", values, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextNotIn(final List<String> values) {
            this.addCriterion("share_text not in", values, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextBetween(final String value1, final String value2) {
            this.addCriterion("share_text between", value1, value2, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareTextNotBetween(final String value1, final String value2) {
            this.addCriterion("share_text not between", value1, value2, "shareText");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlIsNull() {
            this.addCriterion("share_image_url is null");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlIsNotNull() {
            this.addCriterion("share_image_url is not null");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlEqualTo(final String value) {
            this.addCriterion("share_image_url =", value, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlNotEqualTo(final String value) {
            this.addCriterion("share_image_url <>", value, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlGreaterThan(final String value) {
            this.addCriterion("share_image_url >", value, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlGreaterThanOrEqualTo(final String value) {
            this.addCriterion("share_image_url >=", value, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlLessThan(final String value) {
            this.addCriterion("share_image_url <", value, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlLessThanOrEqualTo(final String value) {
            this.addCriterion("share_image_url <=", value, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlLike(final String value) {
            this.addCriterion("share_image_url like", value, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlNotLike(final String value) {
            this.addCriterion("share_image_url not like", value, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlIn(final List<String> values) {
            this.addCriterion("share_image_url in", values, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlNotIn(final List<String> values) {
            this.addCriterion("share_image_url not in", values, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlBetween(final String value1, final String value2) {
            this.addCriterion("share_image_url between", value1, value2, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageUrlNotBetween(final String value1, final String value2) {
            this.addCriterion("share_image_url not between", value1, value2, "shareImageUrl");
            return (Criteria) this;
        }

        public Criteria andShareLinkIsNull() {
            this.addCriterion("share_link is null");
            return (Criteria) this;
        }

        public Criteria andShareLinkIsNotNull() {
            this.addCriterion("share_link is not null");
            return (Criteria) this;
        }

        public Criteria andShareLinkEqualTo(final String value) {
            this.addCriterion("share_link =", value, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkNotEqualTo(final String value) {
            this.addCriterion("share_link <>", value, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkGreaterThan(final String value) {
            this.addCriterion("share_link >", value, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkGreaterThanOrEqualTo(final String value) {
            this.addCriterion("share_link >=", value, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkLessThan(final String value) {
            this.addCriterion("share_link <", value, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkLessThanOrEqualTo(final String value) {
            this.addCriterion("share_link <=", value, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkLike(final String value) {
            this.addCriterion("share_link like", value, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkNotLike(final String value) {
            this.addCriterion("share_link not like", value, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkIn(final List<String> values) {
            this.addCriterion("share_link in", values, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkNotIn(final List<String> values) {
            this.addCriterion("share_link not in", values, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkBetween(final String value1, final String value2) {
            this.addCriterion("share_link between", value1, value2, "shareLink");
            return (Criteria) this;
        }

        public Criteria andShareLinkNotBetween(final String value1, final String value2) {
            this.addCriterion("share_link not between", value1, value2, "shareLink");
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

        public Criteria andUpdateDateIsNull() {
            this.addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            this.addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(final Date value) {
            this.addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(final Date value) {
            this.addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(final Date value) {
            this.addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(final Date value) {
            this.addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(final Date value) {
            this.addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(final List<Date> values) {
            this.addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(final List<Date> values) {
            this.addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(final Date value1, final Date value2) {
            this.addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(final Date value1, final Date value2) {
            this.addCriterion("update_date not between", value1, value2, "updateDate");
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