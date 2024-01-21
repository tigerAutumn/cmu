package cc.newex.dax.extra.criteria.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工单表 查询条件example类
 *
 * @author newex-team
 * @date 2018-05-30
 */
public class WorkOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WorkOrderExample() {
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

        public Criteria andMenuIdIsNull() {
            this.addCriterion("menu_id is null");
            return (Criteria) this;
        }

        public Criteria andMenuIdIsNotNull() {
            this.addCriterion("menu_id is not null");
            return (Criteria) this;
        }

        public Criteria andMenuIdEqualTo(final Integer value) {
            this.addCriterion("menu_id =", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdNotEqualTo(final Integer value) {
            this.addCriterion("menu_id <>", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdIn(final List<Integer> values) {
            this.addCriterion("menu_id in", values, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdNotIn(final List<Integer> values) {
            this.addCriterion("menu_id not in", values, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("menu_id between", value1, value2, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("menu_id not between", value1, value2, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdGreaterThan(final Integer value) {
            this.addCriterion("menu_id >", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("menu_id >=", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdLessThan(final Integer value) {
            this.addCriterion("menu_id <", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andMenuIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("menu_id <=", value, "menuId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNull() {
            this.addCriterion("group_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            this.addCriterion("group_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(final Integer value) {
            this.addCriterion("group_id =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(final Integer value) {
            this.addCriterion("group_id <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(final List<Integer> values) {
            this.addCriterion("group_id in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(final List<Integer> values) {
            this.addCriterion("group_id not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("group_id between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("group_id not between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(final Integer value) {
            this.addCriterion("group_id >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("group_id >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(final Integer value) {
            this.addCriterion("group_id <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("group_id <=", value, "groupId");
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

        public Criteria andUnfoldCountIsNull() {
            this.addCriterion("unfold_count is null");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountIsNotNull() {
            this.addCriterion("unfold_count is not null");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountEqualTo(final Integer value) {
            this.addCriterion("unfold_count =", value, "unfoldCount");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountNotEqualTo(final Integer value) {
            this.addCriterion("unfold_count <>", value, "unfoldCount");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountIn(final List<Integer> values) {
            this.addCriterion("unfold_count in", values, "unfoldCount");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountNotIn(final List<Integer> values) {
            this.addCriterion("unfold_count not in", values, "unfoldCount");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountBetween(final Integer value1, final Integer value2) {
            this.addCriterion("unfold_count between", value1, value2, "unfoldCount");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("unfold_count not between", value1, value2, "unfoldCount");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountGreaterThan(final Integer value) {
            this.addCriterion("unfold_count >", value, "unfoldCount");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("unfold_count >=", value, "unfoldCount");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountLessThan(final Integer value) {
            this.addCriterion("unfold_count <", value, "unfoldCount");
            return (Criteria) this;
        }

        public Criteria andUnfoldCountLessThanOrEqualTo(final Integer value) {
            this.addCriterion("unfold_count <=", value, "unfoldCount");
            return (Criteria) this;
        }

        public Criteria andUrgentIsNull() {
            this.addCriterion("urgent is null");
            return (Criteria) this;
        }

        public Criteria andUrgentIsNotNull() {
            this.addCriterion("urgent is not null");
            return (Criteria) this;
        }

        public Criteria andUrgentEqualTo(final Integer value) {
            this.addCriterion("urgent =", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentNotEqualTo(final Integer value) {
            this.addCriterion("urgent <>", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentIn(final List<Integer> values) {
            this.addCriterion("urgent in", values, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentNotIn(final List<Integer> values) {
            this.addCriterion("urgent not in", values, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentBetween(final Integer value1, final Integer value2) {
            this.addCriterion("urgent between", value1, value2, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("urgent not between", value1, value2, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentGreaterThan(final Integer value) {
            this.addCriterion("urgent >", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("urgent >=", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentLessThan(final Integer value) {
            this.addCriterion("urgent <", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andUrgentLessThanOrEqualTo(final Integer value) {
            this.addCriterion("urgent <=", value, "urgent");
            return (Criteria) this;
        }

        public Criteria andSiteTypeIsNull() {
            this.addCriterion("site_type is null");
            return (Criteria) this;
        }

        public Criteria andSiteTypeIsNotNull() {
            this.addCriterion("site_type is not null");
            return (Criteria) this;
        }

        public Criteria andSiteTypeEqualTo(final Integer value) {
            this.addCriterion("site_type =", value, "siteType");
            return (Criteria) this;
        }

        public Criteria andSiteTypeNotEqualTo(final Integer value) {
            this.addCriterion("site_type <>", value, "siteType");
            return (Criteria) this;
        }

        public Criteria andSiteTypeIn(final List<Integer> values) {
            this.addCriterion("site_type in", values, "siteType");
            return (Criteria) this;
        }

        public Criteria andSiteTypeNotIn(final List<Integer> values) {
            this.addCriterion("site_type not in", values, "siteType");
            return (Criteria) this;
        }

        public Criteria andSiteTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("site_type between", value1, value2, "siteType");
            return (Criteria) this;
        }

        public Criteria andSiteTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("site_type not between", value1, value2, "siteType");
            return (Criteria) this;
        }

        public Criteria andSiteTypeGreaterThan(final Integer value) {
            this.addCriterion("site_type >", value, "siteType");
            return (Criteria) this;
        }

        public Criteria andSiteTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("site_type >=", value, "siteType");
            return (Criteria) this;
        }

        public Criteria andSiteTypeLessThan(final Integer value) {
            this.addCriterion("site_type <", value, "siteType");
            return (Criteria) this;
        }

        public Criteria andSiteTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("site_type <=", value, "siteType");
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

        public Criteria andFromTypeIsNull() {
            this.addCriterion("from_type is null");
            return (Criteria) this;
        }

        public Criteria andFromTypeIsNotNull() {
            this.addCriterion("from_type is not null");
            return (Criteria) this;
        }

        public Criteria andFromTypeEqualTo(final Integer value) {
            this.addCriterion("from_type =", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeNotEqualTo(final Integer value) {
            this.addCriterion("from_type <>", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeIn(final List<Integer> values) {
            this.addCriterion("from_type in", values, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeNotIn(final List<Integer> values) {
            this.addCriterion("from_type not in", values, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("from_type between", value1, value2, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("from_type not between", value1, value2, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeGreaterThan(final Integer value) {
            this.addCriterion("from_type >", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("from_type >=", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeLessThan(final Integer value) {
            this.addCriterion("from_type <", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andFromTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("from_type <=", value, "fromType");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNull() {
            this.addCriterion("is_show is null");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNotNull() {
            this.addCriterion("is_show is not null");
            return (Criteria) this;
        }

        public Criteria andIsShowEqualTo(final Integer value) {
            this.addCriterion("is_show =", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotEqualTo(final Integer value) {
            this.addCriterion("is_show <>", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowIn(final List<Integer> values) {
            this.addCriterion("is_show in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotIn(final List<Integer> values) {
            this.addCriterion("is_show not in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowBetween(final Integer value1, final Integer value2) {
            this.addCriterion("is_show between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("is_show not between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThan(final Integer value) {
            this.addCriterion("is_show >", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("is_show >=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThan(final Integer value) {
            this.addCriterion("is_show <", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThanOrEqualTo(final Integer value) {
            this.addCriterion("is_show <=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            this.addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            this.addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(final Integer value) {
            this.addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(final Integer value) {
            this.addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(final List<Integer> values) {
            this.addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(final List<Integer> values) {
            this.addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(final Integer value1, final Integer value2) {
            this.addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(final Integer value) {
            this.addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(final Integer value) {
            this.addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(final Integer value) {
            this.addCriterion("source <=", value, "source");
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

        public Criteria andRemitNameIsNull() {
            this.addCriterion("remit_name is null");
            return (Criteria) this;
        }

        public Criteria andRemitNameIsNotNull() {
            this.addCriterion("remit_name is not null");
            return (Criteria) this;
        }

        public Criteria andRemitNameEqualTo(final String value) {
            this.addCriterion("remit_name =", value, "remitName");
            return (Criteria) this;
        }

        public Criteria andRemitNameNotEqualTo(final String value) {
            this.addCriterion("remit_name <>", value, "remitName");
            return (Criteria) this;
        }

        public Criteria andRemitNameIn(final List<String> values) {
            this.addCriterion("remit_name in", values, "remitName");
            return (Criteria) this;
        }

        public Criteria andRemitNameNotIn(final List<String> values) {
            this.addCriterion("remit_name not in", values, "remitName");
            return (Criteria) this;
        }

        public Criteria andRemitNameBetween(final String value1, final String value2) {
            this.addCriterion("remit_name between", value1, value2, "remitName");
            return (Criteria) this;
        }

        public Criteria andRemitNameNotBetween(final String value1, final String value2) {
            this.addCriterion("remit_name not between", value1, value2, "remitName");
            return (Criteria) this;
        }


        public Criteria andRemitNameLike(final String value) {
            this.addCriterion("remit_name like", value, "remitName");
            return (Criteria) this;
        }

        public Criteria andRemitNameNotLike(final String value) {
            this.addCriterion("remit_name not like", value, "remitName");
            return (Criteria) this;
        }

        public Criteria andRemitCardNumberIsNull() {
            this.addCriterion("remit_card_number is null");
            return (Criteria) this;
        }

        public Criteria andRemitCardNumberIsNotNull() {
            this.addCriterion("remit_card_number is not null");
            return (Criteria) this;
        }

        public Criteria andRemitCardNumberEqualTo(final String value) {
            this.addCriterion("remit_card_number =", value, "remitCardNumber");
            return (Criteria) this;
        }

        public Criteria andRemitCardNumberNotEqualTo(final String value) {
            this.addCriterion("remit_card_number <>", value, "remitCardNumber");
            return (Criteria) this;
        }

        public Criteria andRemitCardNumberIn(final List<String> values) {
            this.addCriterion("remit_card_number in", values, "remitCardNumber");
            return (Criteria) this;
        }

        public Criteria andRemitCardNumberNotIn(final List<String> values) {
            this.addCriterion("remit_card_number not in", values, "remitCardNumber");
            return (Criteria) this;
        }

        public Criteria andRemitCardNumberBetween(final String value1, final String value2) {
            this.addCriterion("remit_card_number between", value1, value2, "remitCardNumber");
            return (Criteria) this;
        }

        public Criteria andRemitCardNumberNotBetween(final String value1, final String value2) {
            this.addCriterion("remit_card_number not between", value1, value2, "remitCardNumber");
            return (Criteria) this;
        }


        public Criteria andRemitCardNumberLike(final String value) {
            this.addCriterion("remit_card_number like", value, "remitCardNumber");
            return (Criteria) this;
        }

        public Criteria andRemitCardNumberNotLike(final String value) {
            this.addCriterion("remit_card_number not like", value, "remitCardNumber");
            return (Criteria) this;
        }

        public Criteria andRemitAlipayIsNull() {
            this.addCriterion("remit_alipay is null");
            return (Criteria) this;
        }

        public Criteria andRemitAlipayIsNotNull() {
            this.addCriterion("remit_alipay is not null");
            return (Criteria) this;
        }

        public Criteria andRemitAlipayEqualTo(final String value) {
            this.addCriterion("remit_alipay =", value, "remitAlipay");
            return (Criteria) this;
        }

        public Criteria andRemitAlipayNotEqualTo(final String value) {
            this.addCriterion("remit_alipay <>", value, "remitAlipay");
            return (Criteria) this;
        }

        public Criteria andRemitAlipayIn(final List<String> values) {
            this.addCriterion("remit_alipay in", values, "remitAlipay");
            return (Criteria) this;
        }

        public Criteria andRemitAlipayNotIn(final List<String> values) {
            this.addCriterion("remit_alipay not in", values, "remitAlipay");
            return (Criteria) this;
        }

        public Criteria andRemitAlipayBetween(final String value1, final String value2) {
            this.addCriterion("remit_alipay between", value1, value2, "remitAlipay");
            return (Criteria) this;
        }

        public Criteria andRemitAlipayNotBetween(final String value1, final String value2) {
            this.addCriterion("remit_alipay not between", value1, value2, "remitAlipay");
            return (Criteria) this;
        }


        public Criteria andRemitAlipayLike(final String value) {
            this.addCriterion("remit_alipay like", value, "remitAlipay");
            return (Criteria) this;
        }

        public Criteria andRemitAlipayNotLike(final String value) {
            this.addCriterion("remit_alipay not like", value, "remitAlipay");
            return (Criteria) this;
        }

        public Criteria andRemitAmountIsNull() {
            this.addCriterion("remit_amount is null");
            return (Criteria) this;
        }

        public Criteria andRemitAmountIsNotNull() {
            this.addCriterion("remit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRemitAmountEqualTo(final String value) {
            this.addCriterion("remit_amount =", value, "remitAmount");
            return (Criteria) this;
        }

        public Criteria andRemitAmountNotEqualTo(final String value) {
            this.addCriterion("remit_amount <>", value, "remitAmount");
            return (Criteria) this;
        }

        public Criteria andRemitAmountIn(final List<String> values) {
            this.addCriterion("remit_amount in", values, "remitAmount");
            return (Criteria) this;
        }

        public Criteria andRemitAmountNotIn(final List<String> values) {
            this.addCriterion("remit_amount not in", values, "remitAmount");
            return (Criteria) this;
        }

        public Criteria andRemitAmountBetween(final String value1, final String value2) {
            this.addCriterion("remit_amount between", value1, value2, "remitAmount");
            return (Criteria) this;
        }

        public Criteria andRemitAmountNotBetween(final String value1, final String value2) {
            this.addCriterion("remit_amount not between", value1, value2, "remitAmount");
            return (Criteria) this;
        }


        public Criteria andRemitAmountLike(final String value) {
            this.addCriterion("remit_amount like", value, "remitAmount");
            return (Criteria) this;
        }

        public Criteria andRemitAmountNotLike(final String value) {
            this.addCriterion("remit_amount not like", value, "remitAmount");
            return (Criteria) this;
        }

        public Criteria andCoinTypeIsNull() {
            this.addCriterion("coin_type is null");
            return (Criteria) this;
        }

        public Criteria andCoinTypeIsNotNull() {
            this.addCriterion("coin_type is not null");
            return (Criteria) this;
        }

        public Criteria andCoinTypeEqualTo(final String value) {
            this.addCriterion("coin_type =", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeNotEqualTo(final String value) {
            this.addCriterion("coin_type <>", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeIn(final List<String> values) {
            this.addCriterion("coin_type in", values, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeNotIn(final List<String> values) {
            this.addCriterion("coin_type not in", values, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeBetween(final String value1, final String value2) {
            this.addCriterion("coin_type between", value1, value2, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeNotBetween(final String value1, final String value2) {
            this.addCriterion("coin_type not between", value1, value2, "coinType");
            return (Criteria) this;
        }


        public Criteria andCoinTypeLike(final String value) {
            this.addCriterion("coin_type like", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeNotLike(final String value) {
            this.addCriterion("coin_type not like", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andWithdrawAddressIsNull() {
            this.addCriterion("withdraw_address is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawAddressIsNotNull() {
            this.addCriterion("withdraw_address is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawAddressEqualTo(final String value) {
            this.addCriterion("withdraw_address =", value, "withdrawAddress");
            return (Criteria) this;
        }

        public Criteria andWithdrawAddressNotEqualTo(final String value) {
            this.addCriterion("withdraw_address <>", value, "withdrawAddress");
            return (Criteria) this;
        }

        public Criteria andWithdrawAddressIn(final List<String> values) {
            this.addCriterion("withdraw_address in", values, "withdrawAddress");
            return (Criteria) this;
        }

        public Criteria andWithdrawAddressNotIn(final List<String> values) {
            this.addCriterion("withdraw_address not in", values, "withdrawAddress");
            return (Criteria) this;
        }

        public Criteria andWithdrawAddressBetween(final String value1, final String value2) {
            this.addCriterion("withdraw_address between", value1, value2, "withdrawAddress");
            return (Criteria) this;
        }

        public Criteria andWithdrawAddressNotBetween(final String value1, final String value2) {
            this.addCriterion("withdraw_address not between", value1, value2, "withdrawAddress");
            return (Criteria) this;
        }


        public Criteria andWithdrawAddressLike(final String value) {
            this.addCriterion("withdraw_address like", value, "withdrawAddress");
            return (Criteria) this;
        }

        public Criteria andWithdrawAddressNotLike(final String value) {
            this.addCriterion("withdraw_address not like", value, "withdrawAddress");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberIsNull() {
            this.addCriterion("withdraw_number is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberIsNotNull() {
            this.addCriterion("withdraw_number is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberEqualTo(final BigDecimal value) {
            this.addCriterion("withdraw_number =", value, "withdrawNumber");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberNotEqualTo(final BigDecimal value) {
            this.addCriterion("withdraw_number <>", value, "withdrawNumber");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberIn(final List<BigDecimal> values) {
            this.addCriterion("withdraw_number in", values, "withdrawNumber");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberNotIn(final List<BigDecimal> values) {
            this.addCriterion("withdraw_number not in", values, "withdrawNumber");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("withdraw_number between", value1, value2, "withdrawNumber");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("withdraw_number not between", value1, value2, "withdrawNumber");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberGreaterThan(final BigDecimal value) {
            this.addCriterion("withdraw_number >", value, "withdrawNumber");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("withdraw_number >=", value, "withdrawNumber");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberLessThan(final BigDecimal value) {
            this.addCriterion("withdraw_number <", value, "withdrawNumber");
            return (Criteria) this;
        }

        public Criteria andWithdrawNumberLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("withdraw_number <=", value, "withdrawNumber");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusIsNull() {
            this.addCriterion("answer_status is null");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusIsNotNull() {
            this.addCriterion("answer_status is not null");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusEqualTo(final Integer value) {
            this.addCriterion("answer_status =", value, "answerStatus");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusNotEqualTo(final Integer value) {
            this.addCriterion("answer_status <>", value, "answerStatus");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusIn(final List<Integer> values) {
            this.addCriterion("answer_status in", values, "answerStatus");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusNotIn(final List<Integer> values) {
            this.addCriterion("answer_status not in", values, "answerStatus");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusBetween(final Integer value1, final Integer value2) {
            this.addCriterion("answer_status between", value1, value2, "answerStatus");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("answer_status not between", value1, value2, "answerStatus");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusGreaterThan(final Integer value) {
            this.addCriterion("answer_status >", value, "answerStatus");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("answer_status >=", value, "answerStatus");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusLessThan(final Integer value) {
            this.addCriterion("answer_status <", value, "answerStatus");
            return (Criteria) this;
        }

        public Criteria andAnswerStatusLessThanOrEqualTo(final Integer value) {
            this.addCriterion("answer_status <=", value, "answerStatus");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            this.addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            this.addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(final Long value) {
            this.addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(final Long value) {
            this.addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(final List<Long> values) {
            this.addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(final List<Long> values) {
            this.addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(final Long value1, final Long value2) {
            this.addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(final Long value1, final Long value2) {
            this.addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(final Long value) {
            this.addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(final Long value) {
            this.addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(final Long value) {
            this.addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            this.addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            this.addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(final String value) {
            this.addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(final String value) {
            this.addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(final List<String> values) {
            this.addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(final List<String> values) {
            this.addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(final String value1, final String value2) {
            this.addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(final String value1, final String value2) {
            this.addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }


        public Criteria andUserNameLike(final String value) {
            this.addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(final String value) {
            this.addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserEmailIsNull() {
            this.addCriterion("user_email is null");
            return (Criteria) this;
        }

        public Criteria andUserEmailIsNotNull() {
            this.addCriterion("user_email is not null");
            return (Criteria) this;
        }

        public Criteria andUserEmailEqualTo(final String value) {
            this.addCriterion("user_email =", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotEqualTo(final String value) {
            this.addCriterion("user_email <>", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailIn(final List<String> values) {
            this.addCriterion("user_email in", values, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotIn(final List<String> values) {
            this.addCriterion("user_email not in", values, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailBetween(final String value1, final String value2) {
            this.addCriterion("user_email between", value1, value2, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotBetween(final String value1, final String value2) {
            this.addCriterion("user_email not between", value1, value2, "userEmail");
            return (Criteria) this;
        }


        public Criteria andUserEmailLike(final String value) {
            this.addCriterion("user_email like", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotLike(final String value) {
            this.addCriterion("user_email not like", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserPhoneIsNull() {
            this.addCriterion("user_phone is null");
            return (Criteria) this;
        }

        public Criteria andUserPhoneIsNotNull() {
            this.addCriterion("user_phone is not null");
            return (Criteria) this;
        }

        public Criteria andUserPhoneEqualTo(final String value) {
            this.addCriterion("user_phone =", value, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneNotEqualTo(final String value) {
            this.addCriterion("user_phone <>", value, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneIn(final List<String> values) {
            this.addCriterion("user_phone in", values, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneNotIn(final List<String> values) {
            this.addCriterion("user_phone not in", values, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneBetween(final String value1, final String value2) {
            this.addCriterion("user_phone between", value1, value2, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneNotBetween(final String value1, final String value2) {
            this.addCriterion("user_phone not between", value1, value2, "userPhone");
            return (Criteria) this;
        }


        public Criteria andUserPhoneLike(final String value) {
            this.addCriterion("user_phone like", value, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneNotLike(final String value) {
            this.addCriterion("user_phone not like", value, "userPhone");
            return (Criteria) this;
        }

        public Criteria andSatisfactionIsNull() {
            this.addCriterion("satisfaction is null");
            return (Criteria) this;
        }

        public Criteria andSatisfactionIsNotNull() {
            this.addCriterion("satisfaction is not null");
            return (Criteria) this;
        }

        public Criteria andSatisfactionEqualTo(final Integer value) {
            this.addCriterion("satisfaction =", value, "satisfaction");
            return (Criteria) this;
        }

        public Criteria andSatisfactionNotEqualTo(final Integer value) {
            this.addCriterion("satisfaction <>", value, "satisfaction");
            return (Criteria) this;
        }

        public Criteria andSatisfactionIn(final List<Integer> values) {
            this.addCriterion("satisfaction in", values, "satisfaction");
            return (Criteria) this;
        }

        public Criteria andSatisfactionNotIn(final List<Integer> values) {
            this.addCriterion("satisfaction not in", values, "satisfaction");
            return (Criteria) this;
        }

        public Criteria andSatisfactionBetween(final Integer value1, final Integer value2) {
            this.addCriterion("satisfaction between", value1, value2, "satisfaction");
            return (Criteria) this;
        }

        public Criteria andSatisfactionNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("satisfaction not between", value1, value2, "satisfaction");
            return (Criteria) this;
        }

        public Criteria andSatisfactionGreaterThan(final Integer value) {
            this.addCriterion("satisfaction >", value, "satisfaction");
            return (Criteria) this;
        }

        public Criteria andSatisfactionGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("satisfaction >=", value, "satisfaction");
            return (Criteria) this;
        }

        public Criteria andSatisfactionLessThan(final Integer value) {
            this.addCriterion("satisfaction <", value, "satisfaction");
            return (Criteria) this;
        }

        public Criteria andSatisfactionLessThanOrEqualTo(final Integer value) {
            this.addCriterion("satisfaction <=", value, "satisfaction");
            return (Criteria) this;
        }

        public Criteria andResponseTimeIsNull() {
            this.addCriterion("response_time is null");
            return (Criteria) this;
        }

        public Criteria andResponseTimeIsNotNull() {
            this.addCriterion("response_time is not null");
            return (Criteria) this;
        }

        public Criteria andResponseTimeEqualTo(final Date value) {
            this.addCriterion("response_time =", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeNotEqualTo(final Date value) {
            this.addCriterion("response_time <>", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeIn(final List<Date> values) {
            this.addCriterion("response_time in", values, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeNotIn(final List<Date> values) {
            this.addCriterion("response_time not in", values, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("response_time between", value1, value2, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("response_time not between", value1, value2, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeGreaterThan(final Date value) {
            this.addCriterion("response_time >", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("response_time >=", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeLessThan(final Date value) {
            this.addCriterion("response_time <", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("response_time <=", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andSolveTimeIsNull() {
            this.addCriterion("solve_time is null");
            return (Criteria) this;
        }

        public Criteria andSolveTimeIsNotNull() {
            this.addCriterion("solve_time is not null");
            return (Criteria) this;
        }

        public Criteria andSolveTimeEqualTo(final Date value) {
            this.addCriterion("solve_time =", value, "solveTime");
            return (Criteria) this;
        }

        public Criteria andSolveTimeNotEqualTo(final Date value) {
            this.addCriterion("solve_time <>", value, "solveTime");
            return (Criteria) this;
        }

        public Criteria andSolveTimeIn(final List<Date> values) {
            this.addCriterion("solve_time in", values, "solveTime");
            return (Criteria) this;
        }

        public Criteria andSolveTimeNotIn(final List<Date> values) {
            this.addCriterion("solve_time not in", values, "solveTime");
            return (Criteria) this;
        }

        public Criteria andSolveTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("solve_time between", value1, value2, "solveTime");
            return (Criteria) this;
        }

        public Criteria andSolveTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("solve_time not between", value1, value2, "solveTime");
            return (Criteria) this;
        }

        public Criteria andSolveTimeGreaterThan(final Date value) {
            this.addCriterion("solve_time >", value, "solveTime");
            return (Criteria) this;
        }

        public Criteria andSolveTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("solve_time >=", value, "solveTime");
            return (Criteria) this;
        }

        public Criteria andSolveTimeLessThan(final Date value) {
            this.addCriterion("solve_time <", value, "solveTime");
            return (Criteria) this;
        }

        public Criteria andSolveTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("solve_time <=", value, "solveTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIsNull() {
            this.addCriterion("handle_time is null");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIsNotNull() {
            this.addCriterion("handle_time is not null");
            return (Criteria) this;
        }

        public Criteria andHandleTimeEqualTo(final Long value) {
            this.addCriterion("handle_time =", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotEqualTo(final Long value) {
            this.addCriterion("handle_time <>", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIn(final List<Long> values) {
            this.addCriterion("handle_time in", values, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotIn(final List<Long> values) {
            this.addCriterion("handle_time not in", values, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeBetween(final Long value1, final Long value2) {
            this.addCriterion("handle_time between", value1, value2, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotBetween(final Long value1, final Long value2) {
            this.addCriterion("handle_time not between", value1, value2, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeGreaterThan(final Long value) {
            this.addCriterion("handle_time >", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("handle_time >=", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLessThan(final Long value) {
            this.addCriterion("handle_time <", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLessThanOrEqualTo(final Long value) {
            this.addCriterion("handle_time <=", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeIsNull() {
            this.addCriterion("dispose_time is null");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeIsNotNull() {
            this.addCriterion("dispose_time is not null");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeEqualTo(final Long value) {
            this.addCriterion("dispose_time =", value, "disposeTime");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeNotEqualTo(final Long value) {
            this.addCriterion("dispose_time <>", value, "disposeTime");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeIn(final List<Long> values) {
            this.addCriterion("dispose_time in", values, "disposeTime");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeNotIn(final List<Long> values) {
            this.addCriterion("dispose_time not in", values, "disposeTime");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeBetween(final Long value1, final Long value2) {
            this.addCriterion("dispose_time between", value1, value2, "disposeTime");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeNotBetween(final Long value1, final Long value2) {
            this.addCriterion("dispose_time not between", value1, value2, "disposeTime");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeGreaterThan(final Long value) {
            this.addCriterion("dispose_time >", value, "disposeTime");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("dispose_time >=", value, "disposeTime");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeLessThan(final Long value) {
            this.addCriterion("dispose_time <", value, "disposeTime");
            return (Criteria) this;
        }

        public Criteria andDisposeTimeLessThanOrEqualTo(final Long value) {
            this.addCriterion("dispose_time <=", value, "disposeTime");
            return (Criteria) this;
        }

        public Criteria andCommentIsNull() {
            this.addCriterion("comment is null");
            return (Criteria) this;
        }

        public Criteria andCommentIsNotNull() {
            this.addCriterion("comment is not null");
            return (Criteria) this;
        }

        public Criteria andCommentEqualTo(final String value) {
            this.addCriterion("comment =", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotEqualTo(final String value) {
            this.addCriterion("comment <>", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentIn(final List<String> values) {
            this.addCriterion("comment in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotIn(final List<String> values) {
            this.addCriterion("comment not in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentBetween(final String value1, final String value2) {
            this.addCriterion("comment between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotBetween(final String value1, final String value2) {
            this.addCriterion("comment not between", value1, value2, "comment");
            return (Criteria) this;
        }


        public Criteria andCommentLike(final String value) {
            this.addCriterion("comment like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotLike(final String value) {
            this.addCriterion("comment not like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeIsNull() {
            this.addCriterion("accept_time is null");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeIsNotNull() {
            this.addCriterion("accept_time is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeEqualTo(final Date value) {
            this.addCriterion("accept_time =", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeNotEqualTo(final Date value) {
            this.addCriterion("accept_time <>", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeIn(final List<Date> values) {
            this.addCriterion("accept_time in", values, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeNotIn(final List<Date> values) {
            this.addCriterion("accept_time not in", values, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("accept_time between", value1, value2, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("accept_time not between", value1, value2, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeGreaterThan(final Date value) {
            this.addCriterion("accept_time >", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("accept_time >=", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeLessThan(final Date value) {
            this.addCriterion("accept_time <", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("accept_time <=", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeIsNull() {
            this.addCriterion("last_reply_time is null");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeIsNotNull() {
            this.addCriterion("last_reply_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeEqualTo(final Date value) {
            this.addCriterion("last_reply_time =", value, "lastReplyTime");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeNotEqualTo(final Date value) {
            this.addCriterion("last_reply_time <>", value, "lastReplyTime");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeIn(final List<Date> values) {
            this.addCriterion("last_reply_time in", values, "lastReplyTime");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeNotIn(final List<Date> values) {
            this.addCriterion("last_reply_time not in", values, "lastReplyTime");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("last_reply_time between", value1, value2, "lastReplyTime");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("last_reply_time not between", value1, value2, "lastReplyTime");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeGreaterThan(final Date value) {
            this.addCriterion("last_reply_time >", value, "lastReplyTime");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("last_reply_time >=", value, "lastReplyTime");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeLessThan(final Date value) {
            this.addCriterion("last_reply_time <", value, "lastReplyTime");
            return (Criteria) this;
        }

        public Criteria andLastReplyTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("last_reply_time <=", value, "lastReplyTime");
            return (Criteria) this;
        }

        public Criteria andFreshIsNull() {
            this.addCriterion("fresh is null");
            return (Criteria) this;
        }

        public Criteria andFreshIsNotNull() {
            this.addCriterion("fresh is not null");
            return (Criteria) this;
        }

        public Criteria andFreshEqualTo(final Integer value) {
            this.addCriterion("fresh =", value, "fresh");
            return (Criteria) this;
        }

        public Criteria andFreshNotEqualTo(final Integer value) {
            this.addCriterion("fresh <>", value, "fresh");
            return (Criteria) this;
        }

        public Criteria andFreshIn(final List<Integer> values) {
            this.addCriterion("fresh in", values, "fresh");
            return (Criteria) this;
        }

        public Criteria andFreshNotIn(final List<Integer> values) {
            this.addCriterion("fresh not in", values, "fresh");
            return (Criteria) this;
        }

        public Criteria andFreshBetween(final Integer value1, final Integer value2) {
            this.addCriterion("fresh between", value1, value2, "fresh");
            return (Criteria) this;
        }

        public Criteria andFreshNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("fresh not between", value1, value2, "fresh");
            return (Criteria) this;
        }

        public Criteria andFreshGreaterThan(final Integer value) {
            this.addCriterion("fresh >", value, "fresh");
            return (Criteria) this;
        }

        public Criteria andFreshGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("fresh >=", value, "fresh");
            return (Criteria) this;
        }

        public Criteria andFreshLessThan(final Integer value) {
            this.addCriterion("fresh <", value, "fresh");
            return (Criteria) this;
        }

        public Criteria andFreshLessThanOrEqualTo(final Integer value) {
            this.addCriterion("fresh <=", value, "fresh");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNull() {
            this.addCriterion("admin_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNotNull() {
            this.addCriterion("admin_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdEqualTo(final Integer value) {
            this.addCriterion("admin_user_id =", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotEqualTo(final Integer value) {
            this.addCriterion("admin_user_id <>", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIn(final List<Integer> values) {
            this.addCriterion("admin_user_id in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotIn(final List<Integer> values) {
            this.addCriterion("admin_user_id not in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("admin_user_id between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("admin_user_id not between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThan(final Integer value) {
            this.addCriterion("admin_user_id >", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("admin_user_id >=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThan(final Integer value) {
            this.addCriterion("admin_user_id <", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("admin_user_id <=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminAccountIsNull() {
            this.addCriterion("admin_account is null");
            return (Criteria) this;
        }

        public Criteria andAdminAccountIsNotNull() {
            this.addCriterion("admin_account is not null");
            return (Criteria) this;
        }

        public Criteria andAdminAccountEqualTo(final String value) {
            this.addCriterion("admin_account =", value, "adminAccount");
            return (Criteria) this;
        }

        public Criteria andAdminAccountNotEqualTo(final String value) {
            this.addCriterion("admin_account <>", value, "adminAccount");
            return (Criteria) this;
        }

        public Criteria andAdminAccountIn(final List<String> values) {
            this.addCriterion("admin_account in", values, "adminAccount");
            return (Criteria) this;
        }

        public Criteria andAdminAccountNotIn(final List<String> values) {
            this.addCriterion("admin_account not in", values, "adminAccount");
            return (Criteria) this;
        }

        public Criteria andAdminAccountBetween(final String value1, final String value2) {
            this.addCriterion("admin_account between", value1, value2, "adminAccount");
            return (Criteria) this;
        }

        public Criteria andAdminAccountNotBetween(final String value1, final String value2) {
            this.addCriterion("admin_account not between", value1, value2, "adminAccount");
            return (Criteria) this;
        }


        public Criteria andAdminAccountLike(final String value) {
            this.addCriterion("admin_account like", value, "adminAccount");
            return (Criteria) this;
        }

        public Criteria andAdminAccountNotLike(final String value) {
            this.addCriterion("admin_account not like", value, "adminAccount");
            return (Criteria) this;
        }

        public Criteria andAdminNameIsNull() {
            this.addCriterion("admin_name is null");
            return (Criteria) this;
        }

        public Criteria andAdminNameIsNotNull() {
            this.addCriterion("admin_name is not null");
            return (Criteria) this;
        }

        public Criteria andAdminNameEqualTo(final String value) {
            this.addCriterion("admin_name =", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameNotEqualTo(final String value) {
            this.addCriterion("admin_name <>", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameIn(final List<String> values) {
            this.addCriterion("admin_name in", values, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameNotIn(final List<String> values) {
            this.addCriterion("admin_name not in", values, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameBetween(final String value1, final String value2) {
            this.addCriterion("admin_name between", value1, value2, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameNotBetween(final String value1, final String value2) {
            this.addCriterion("admin_name not between", value1, value2, "adminName");
            return (Criteria) this;
        }


        public Criteria andAdminNameLike(final String value) {
            this.addCriterion("admin_name like", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andAdminNameNotLike(final String value) {
            this.addCriterion("admin_name not like", value, "adminName");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdIsNull() {
            this.addCriterion("create_admin_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdIsNotNull() {
            this.addCriterion("create_admin_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdEqualTo(final Integer value) {
            this.addCriterion("create_admin_user_id =", value, "createAdminUserId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdNotEqualTo(final Integer value) {
            this.addCriterion("create_admin_user_id <>", value, "createAdminUserId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdIn(final List<Integer> values) {
            this.addCriterion("create_admin_user_id in", values, "createAdminUserId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdNotIn(final List<Integer> values) {
            this.addCriterion("create_admin_user_id not in", values, "createAdminUserId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("create_admin_user_id between", value1, value2, "createAdminUserId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("create_admin_user_id not between", value1, value2, "createAdminUserId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdGreaterThan(final Integer value) {
            this.addCriterion("create_admin_user_id >", value, "createAdminUserId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("create_admin_user_id >=", value, "createAdminUserId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdLessThan(final Integer value) {
            this.addCriterion("create_admin_user_id <", value, "createAdminUserId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminUserIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("create_admin_user_id <=", value, "createAdminUserId");
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
