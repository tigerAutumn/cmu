package cc.newex.dax.extra.criteria.cgm;

import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 项目信息表 查询条件类
 *
 * @author mbg.generated
 * @date 2018-08-16 15:16:03
 */
public class ProjectApplyInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProjectApplyInfoExample() {
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

    public static ProjectApplyInfoExample toExample(final ProjectApplyInfo projectApplyInfo) {
        if (projectApplyInfo == null) {
            return null;
        }

        final ProjectApplyInfoExample example = new ProjectApplyInfoExample();
        final ProjectApplyInfoExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(projectApplyInfo.getId())) {
            criteria.andIdEqualTo(projectApplyInfo.getId());
        }

        if (Objects.nonNull(projectApplyInfo.getTokenInfoId())) {
            criteria.andTokenInfoIdEqualTo(projectApplyInfo.getTokenInfoId());
        }

        if (StringUtils.isNotBlank(projectApplyInfo.getLocale())) {
            criteria.andLocaleEqualTo(projectApplyInfo.getLocale());
        }

        if (StringUtils.isNotBlank(projectApplyInfo.getCompany())) {
            criteria.andCompanyLike(projectApplyInfo.getCompany());
        }

        if (StringUtils.isNotBlank(projectApplyInfo.getCompanyPosition())) {
            criteria.andCompanyPositionLike(projectApplyInfo.getCompanyPosition());
        }

        if (StringUtils.isNotBlank(projectApplyInfo.getWebsite())) {
            criteria.andWebsiteLike(projectApplyInfo.getWebsite());
        }

        if (Objects.nonNull(projectApplyInfo.getBrokerId())) {
            criteria.andBrokerIdEqualTo(projectApplyInfo.getBrokerId());
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

        public Criteria andTokenInfoIdIsNull() {
            this.addCriterion("token_info_id is null");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdIsNotNull() {
            this.addCriterion("token_info_id is not null");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdEqualTo(final Long value) {
            this.addCriterion("token_info_id =", value, "tokenInfoId");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdNotEqualTo(final Long value) {
            this.addCriterion("token_info_id <>", value, "tokenInfoId");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdGreaterThan(final Long value) {
            this.addCriterion("token_info_id >", value, "tokenInfoId");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("token_info_id >=", value, "tokenInfoId");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdLessThan(final Long value) {
            this.addCriterion("token_info_id <", value, "tokenInfoId");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdLessThanOrEqualTo(final Long value) {
            this.addCriterion("token_info_id <=", value, "tokenInfoId");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdIn(final List<Long> values) {
            this.addCriterion("token_info_id in", values, "tokenInfoId");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdNotIn(final List<Long> values) {
            this.addCriterion("token_info_id not in", values, "tokenInfoId");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdBetween(final Long value1, final Long value2) {
            this.addCriterion("token_info_id between", value1, value2, "tokenInfoId");
            return (Criteria) this;
        }

        public Criteria andTokenInfoIdNotBetween(final Long value1, final Long value2) {
            this.addCriterion("token_info_id not between", value1, value2, "tokenInfoId");
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

        public Criteria andCompanyIsNull() {
            this.addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            this.addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(final String value) {
            this.addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(final String value) {
            this.addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(final String value) {
            this.addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(final String value) {
            this.addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(final String value) {
            this.addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(final String value) {
            this.addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(final String value) {
            this.addCriterion("company like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(final String value) {
            this.addCriterion("company not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(final List<String> values) {
            this.addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(final List<String> values) {
            this.addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(final String value1, final String value2) {
            this.addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(final String value1, final String value2) {
            this.addCriterion("company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andWebsiteIsNull() {
            this.addCriterion("website is null");
            return (Criteria) this;
        }

        public Criteria andWebsiteIsNotNull() {
            this.addCriterion("website is not null");
            return (Criteria) this;
        }

        public Criteria andWebsiteEqualTo(final String value) {
            this.addCriterion("website =", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotEqualTo(final String value) {
            this.addCriterion("website <>", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteGreaterThan(final String value) {
            this.addCriterion("website >", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteGreaterThanOrEqualTo(final String value) {
            this.addCriterion("website >=", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteLessThan(final String value) {
            this.addCriterion("website <", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteLessThanOrEqualTo(final String value) {
            this.addCriterion("website <=", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteLike(final String value) {
            this.addCriterion("website like", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotLike(final String value) {
            this.addCriterion("website not like", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteIn(final List<String> values) {
            this.addCriterion("website in", values, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotIn(final List<String> values) {
            this.addCriterion("website not in", values, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteBetween(final String value1, final String value2) {
            this.addCriterion("website between", value1, value2, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotBetween(final String value1, final String value2) {
            this.addCriterion("website not between", value1, value2, "website");
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

        public Criteria andProjectInfoIsNull() {
            this.addCriterion("project_info is null");
            return (Criteria) this;
        }

        public Criteria andProjectInfoIsNotNull() {
            this.addCriterion("project_info is not null");
            return (Criteria) this;
        }

        public Criteria andProjectInfoEqualTo(final String value) {
            this.addCriterion("project_info =", value, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoNotEqualTo(final String value) {
            this.addCriterion("project_info <>", value, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoGreaterThan(final String value) {
            this.addCriterion("project_info >", value, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoGreaterThanOrEqualTo(final String value) {
            this.addCriterion("project_info >=", value, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoLessThan(final String value) {
            this.addCriterion("project_info <", value, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoLessThanOrEqualTo(final String value) {
            this.addCriterion("project_info <=", value, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoLike(final String value) {
            this.addCriterion("project_info like", value, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoNotLike(final String value) {
            this.addCriterion("project_info not like", value, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoIn(final List<String> values) {
            this.addCriterion("project_info in", values, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoNotIn(final List<String> values) {
            this.addCriterion("project_info not in", values, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoBetween(final String value1, final String value2) {
            this.addCriterion("project_info between", value1, value2, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectInfoNotBetween(final String value1, final String value2) {
            this.addCriterion("project_info not between", value1, value2, "projectInfo");
            return (Criteria) this;
        }

        public Criteria andProjectStageIsNull() {
            this.addCriterion("project_stage is null");
            return (Criteria) this;
        }

        public Criteria andProjectStageIsNotNull() {
            this.addCriterion("project_stage is not null");
            return (Criteria) this;
        }

        public Criteria andProjectStageEqualTo(final String value) {
            this.addCriterion("project_stage =", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageNotEqualTo(final String value) {
            this.addCriterion("project_stage <>", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageGreaterThan(final String value) {
            this.addCriterion("project_stage >", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageGreaterThanOrEqualTo(final String value) {
            this.addCriterion("project_stage >=", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageLessThan(final String value) {
            this.addCriterion("project_stage <", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageLessThanOrEqualTo(final String value) {
            this.addCriterion("project_stage <=", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageLike(final String value) {
            this.addCriterion("project_stage like", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageNotLike(final String value) {
            this.addCriterion("project_stage not like", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageIn(final List<String> values) {
            this.addCriterion("project_stage in", values, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageNotIn(final List<String> values) {
            this.addCriterion("project_stage not in", values, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageBetween(final String value1, final String value2) {
            this.addCriterion("project_stage between", value1, value2, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageNotBetween(final String value1, final String value2) {
            this.addCriterion("project_stage not between", value1, value2, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveIsNull() {
            this.addCriterion("project_objective is null");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveIsNotNull() {
            this.addCriterion("project_objective is not null");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveEqualTo(final String value) {
            this.addCriterion("project_objective =", value, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveNotEqualTo(final String value) {
            this.addCriterion("project_objective <>", value, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveGreaterThan(final String value) {
            this.addCriterion("project_objective >", value, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveGreaterThanOrEqualTo(final String value) {
            this.addCriterion("project_objective >=", value, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveLessThan(final String value) {
            this.addCriterion("project_objective <", value, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveLessThanOrEqualTo(final String value) {
            this.addCriterion("project_objective <=", value, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveLike(final String value) {
            this.addCriterion("project_objective like", value, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveNotLike(final String value) {
            this.addCriterion("project_objective not like", value, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveIn(final List<String> values) {
            this.addCriterion("project_objective in", values, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveNotIn(final List<String> values) {
            this.addCriterion("project_objective not in", values, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveBetween(final String value1, final String value2) {
            this.addCriterion("project_objective between", value1, value2, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectObjectiveNotBetween(final String value1, final String value2) {
            this.addCriterion("project_objective not between", value1, value2, "projectObjective");
            return (Criteria) this;
        }

        public Criteria andProjectProgressIsNull() {
            this.addCriterion("project_progress is null");
            return (Criteria) this;
        }

        public Criteria andProjectProgressIsNotNull() {
            this.addCriterion("project_progress is not null");
            return (Criteria) this;
        }

        public Criteria andProjectProgressEqualTo(final String value) {
            this.addCriterion("project_progress =", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressNotEqualTo(final String value) {
            this.addCriterion("project_progress <>", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressGreaterThan(final String value) {
            this.addCriterion("project_progress >", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressGreaterThanOrEqualTo(final String value) {
            this.addCriterion("project_progress >=", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressLessThan(final String value) {
            this.addCriterion("project_progress <", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressLessThanOrEqualTo(final String value) {
            this.addCriterion("project_progress <=", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressLike(final String value) {
            this.addCriterion("project_progress like", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressNotLike(final String value) {
            this.addCriterion("project_progress not like", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressIn(final List<String> values) {
            this.addCriterion("project_progress in", values, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressNotIn(final List<String> values) {
            this.addCriterion("project_progress not in", values, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressBetween(final String value1, final String value2) {
            this.addCriterion("project_progress between", value1, value2, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressNotBetween(final String value1, final String value2) {
            this.addCriterion("project_progress not between", value1, value2, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andSeeIsNull() {
            this.addCriterion("see is null");
            return (Criteria) this;
        }

        public Criteria andSeeIsNotNull() {
            this.addCriterion("see is not null");
            return (Criteria) this;
        }

        public Criteria andSeeEqualTo(final Byte value) {
            this.addCriterion("see =", value, "see");
            return (Criteria) this;
        }

        public Criteria andSeeNotEqualTo(final Byte value) {
            this.addCriterion("see <>", value, "see");
            return (Criteria) this;
        }

        public Criteria andSeeGreaterThan(final Byte value) {
            this.addCriterion("see >", value, "see");
            return (Criteria) this;
        }

        public Criteria andSeeGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("see >=", value, "see");
            return (Criteria) this;
        }

        public Criteria andSeeLessThan(final Byte value) {
            this.addCriterion("see <", value, "see");
            return (Criteria) this;
        }

        public Criteria andSeeLessThanOrEqualTo(final Byte value) {
            this.addCriterion("see <=", value, "see");
            return (Criteria) this;
        }

        public Criteria andSeeIn(final List<Byte> values) {
            this.addCriterion("see in", values, "see");
            return (Criteria) this;
        }

        public Criteria andSeeNotIn(final List<Byte> values) {
            this.addCriterion("see not in", values, "see");
            return (Criteria) this;
        }

        public Criteria andSeeBetween(final Byte value1, final Byte value2) {
            this.addCriterion("see between", value1, value2, "see");
            return (Criteria) this;
        }

        public Criteria andSeeNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("see not between", value1, value2, "see");
            return (Criteria) this;
        }

        public Criteria andGithubUrlIsNull() {
            this.addCriterion("github_url is null");
            return (Criteria) this;
        }

        public Criteria andGithubUrlIsNotNull() {
            this.addCriterion("github_url is not null");
            return (Criteria) this;
        }

        public Criteria andGithubUrlEqualTo(final String value) {
            this.addCriterion("github_url =", value, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlNotEqualTo(final String value) {
            this.addCriterion("github_url <>", value, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlGreaterThan(final String value) {
            this.addCriterion("github_url >", value, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlGreaterThanOrEqualTo(final String value) {
            this.addCriterion("github_url >=", value, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlLessThan(final String value) {
            this.addCriterion("github_url <", value, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlLessThanOrEqualTo(final String value) {
            this.addCriterion("github_url <=", value, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlLike(final String value) {
            this.addCriterion("github_url like", value, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlNotLike(final String value) {
            this.addCriterion("github_url not like", value, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlIn(final List<String> values) {
            this.addCriterion("github_url in", values, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlNotIn(final List<String> values) {
            this.addCriterion("github_url not in", values, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlBetween(final String value1, final String value2) {
            this.addCriterion("github_url between", value1, value2, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andGithubUrlNotBetween(final String value1, final String value2) {
            this.addCriterion("github_url not between", value1, value2, "githubUrl");
            return (Criteria) this;
        }

        public Criteria andTeamIsNull() {
            this.addCriterion("team is null");
            return (Criteria) this;
        }

        public Criteria andTeamIsNotNull() {
            this.addCriterion("team is not null");
            return (Criteria) this;
        }

        public Criteria andTeamEqualTo(final String value) {
            this.addCriterion("team =", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamNotEqualTo(final String value) {
            this.addCriterion("team <>", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamGreaterThan(final String value) {
            this.addCriterion("team >", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamGreaterThanOrEqualTo(final String value) {
            this.addCriterion("team >=", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamLessThan(final String value) {
            this.addCriterion("team <", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamLessThanOrEqualTo(final String value) {
            this.addCriterion("team <=", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamLike(final String value) {
            this.addCriterion("team like", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamNotLike(final String value) {
            this.addCriterion("team not like", value, "team");
            return (Criteria) this;
        }

        public Criteria andTeamIn(final List<String> values) {
            this.addCriterion("team in", values, "team");
            return (Criteria) this;
        }

        public Criteria andTeamNotIn(final List<String> values) {
            this.addCriterion("team not in", values, "team");
            return (Criteria) this;
        }

        public Criteria andTeamBetween(final String value1, final String value2) {
            this.addCriterion("team between", value1, value2, "team");
            return (Criteria) this;
        }

        public Criteria andTeamNotBetween(final String value1, final String value2) {
            this.addCriterion("team not between", value1, value2, "team");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorIsNull() {
            this.addCriterion("team_counselor is null");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorIsNotNull() {
            this.addCriterion("team_counselor is not null");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorEqualTo(final String value) {
            this.addCriterion("team_counselor =", value, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorNotEqualTo(final String value) {
            this.addCriterion("team_counselor <>", value, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorGreaterThan(final String value) {
            this.addCriterion("team_counselor >", value, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorGreaterThanOrEqualTo(final String value) {
            this.addCriterion("team_counselor >=", value, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorLessThan(final String value) {
            this.addCriterion("team_counselor <", value, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorLessThanOrEqualTo(final String value) {
            this.addCriterion("team_counselor <=", value, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorLike(final String value) {
            this.addCriterion("team_counselor like", value, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorNotLike(final String value) {
            this.addCriterion("team_counselor not like", value, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorIn(final List<String> values) {
            this.addCriterion("team_counselor in", values, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorNotIn(final List<String> values) {
            this.addCriterion("team_counselor not in", values, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorBetween(final String value1, final String value2) {
            this.addCriterion("team_counselor between", value1, value2, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andTeamCounselorNotBetween(final String value1, final String value2) {
            this.addCriterion("team_counselor not between", value1, value2, "teamCounselor");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberIsNull() {
            this.addCriterion("fulltime_number is null");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberIsNotNull() {
            this.addCriterion("fulltime_number is not null");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberEqualTo(final Integer value) {
            this.addCriterion("fulltime_number =", value, "fulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberNotEqualTo(final Integer value) {
            this.addCriterion("fulltime_number <>", value, "fulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberGreaterThan(final Integer value) {
            this.addCriterion("fulltime_number >", value, "fulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("fulltime_number >=", value, "fulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberLessThan(final Integer value) {
            this.addCriterion("fulltime_number <", value, "fulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberLessThanOrEqualTo(final Integer value) {
            this.addCriterion("fulltime_number <=", value, "fulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberIn(final List<Integer> values) {
            this.addCriterion("fulltime_number in", values, "fulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberNotIn(final List<Integer> values) {
            this.addCriterion("fulltime_number not in", values, "fulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberBetween(final Integer value1, final Integer value2) {
            this.addCriterion("fulltime_number between", value1, value2, "fulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andFulltimeNumberNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("fulltime_number not between", value1, value2, "fulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberIsNull() {
            this.addCriterion("no_fulltime_number is null");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberIsNotNull() {
            this.addCriterion("no_fulltime_number is not null");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberEqualTo(final Integer value) {
            this.addCriterion("no_fulltime_number =", value, "noFulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberNotEqualTo(final Integer value) {
            this.addCriterion("no_fulltime_number <>", value, "noFulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberGreaterThan(final Integer value) {
            this.addCriterion("no_fulltime_number >", value, "noFulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("no_fulltime_number >=", value, "noFulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberLessThan(final Integer value) {
            this.addCriterion("no_fulltime_number <", value, "noFulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberLessThanOrEqualTo(final Integer value) {
            this.addCriterion("no_fulltime_number <=", value, "noFulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberIn(final List<Integer> values) {
            this.addCriterion("no_fulltime_number in", values, "noFulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberNotIn(final List<Integer> values) {
            this.addCriterion("no_fulltime_number not in", values, "noFulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberBetween(final Integer value1, final Integer value2) {
            this.addCriterion("no_fulltime_number between", value1, value2, "noFulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andNoFulltimeNumberNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("no_fulltime_number not between", value1, value2, "noFulltimeNumber");
            return (Criteria) this;
        }

        public Criteria andRaiseIsNull() {
            this.addCriterion("raise is null");
            return (Criteria) this;
        }

        public Criteria andRaiseIsNotNull() {
            this.addCriterion("raise is not null");
            return (Criteria) this;
        }

        public Criteria andRaiseEqualTo(final Byte value) {
            this.addCriterion("raise =", value, "raise");
            return (Criteria) this;
        }

        public Criteria andRaiseNotEqualTo(final Byte value) {
            this.addCriterion("raise <>", value, "raise");
            return (Criteria) this;
        }

        public Criteria andRaiseGreaterThan(final Byte value) {
            this.addCriterion("raise >", value, "raise");
            return (Criteria) this;
        }

        public Criteria andRaiseGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("raise >=", value, "raise");
            return (Criteria) this;
        }

        public Criteria andRaiseLessThan(final Byte value) {
            this.addCriterion("raise <", value, "raise");
            return (Criteria) this;
        }

        public Criteria andRaiseLessThanOrEqualTo(final Byte value) {
            this.addCriterion("raise <=", value, "raise");
            return (Criteria) this;
        }

        public Criteria andRaiseIn(final List<Byte> values) {
            this.addCriterion("raise in", values, "raise");
            return (Criteria) this;
        }

        public Criteria andRaiseNotIn(final List<Byte> values) {
            this.addCriterion("raise not in", values, "raise");
            return (Criteria) this;
        }

        public Criteria andRaiseBetween(final Byte value1, final Byte value2) {
            this.addCriterion("raise between", value1, value2, "raise");
            return (Criteria) this;
        }

        public Criteria andRaiseNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("raise not between", value1, value2, "raise");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalIsNull() {
            this.addCriterion("raise_total is null");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalIsNotNull() {
            this.addCriterion("raise_total is not null");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalEqualTo(final String value) {
            this.addCriterion("raise_total =", value, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalNotEqualTo(final String value) {
            this.addCriterion("raise_total <>", value, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalGreaterThan(final String value) {
            this.addCriterion("raise_total >", value, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalGreaterThanOrEqualTo(final String value) {
            this.addCriterion("raise_total >=", value, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalLessThan(final String value) {
            this.addCriterion("raise_total <", value, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalLessThanOrEqualTo(final String value) {
            this.addCriterion("raise_total <=", value, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalLike(final String value) {
            this.addCriterion("raise_total like", value, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalNotLike(final String value) {
            this.addCriterion("raise_total not like", value, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalIn(final List<String> values) {
            this.addCriterion("raise_total in", values, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalNotIn(final List<String> values) {
            this.addCriterion("raise_total not in", values, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalBetween(final String value1, final String value2) {
            this.addCriterion("raise_total between", value1, value2, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaiseTotalNotBetween(final String value1, final String value2) {
            this.addCriterion("raise_total not between", value1, value2, "raiseTotal");
            return (Criteria) this;
        }

        public Criteria andRaisePriceIsNull() {
            this.addCriterion("raise_price is null");
            return (Criteria) this;
        }

        public Criteria andRaisePriceIsNotNull() {
            this.addCriterion("raise_price is not null");
            return (Criteria) this;
        }

        public Criteria andRaisePriceEqualTo(final String value) {
            this.addCriterion("raise_price =", value, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceNotEqualTo(final String value) {
            this.addCriterion("raise_price <>", value, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceGreaterThan(final String value) {
            this.addCriterion("raise_price >", value, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceGreaterThanOrEqualTo(final String value) {
            this.addCriterion("raise_price >=", value, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceLessThan(final String value) {
            this.addCriterion("raise_price <", value, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceLessThanOrEqualTo(final String value) {
            this.addCriterion("raise_price <=", value, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceLike(final String value) {
            this.addCriterion("raise_price like", value, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceNotLike(final String value) {
            this.addCriterion("raise_price not like", value, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceIn(final List<String> values) {
            this.addCriterion("raise_price in", values, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceNotIn(final List<String> values) {
            this.addCriterion("raise_price not in", values, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceBetween(final String value1, final String value2) {
            this.addCriterion("raise_price between", value1, value2, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaisePriceNotBetween(final String value1, final String value2) {
            this.addCriterion("raise_price not between", value1, value2, "raisePrice");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestIsNull() {
            this.addCriterion("raise_invest is null");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestIsNotNull() {
            this.addCriterion("raise_invest is not null");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestEqualTo(final Integer value) {
            this.addCriterion("raise_invest =", value, "raiseInvest");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestNotEqualTo(final Integer value) {
            this.addCriterion("raise_invest <>", value, "raiseInvest");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestGreaterThan(final Integer value) {
            this.addCriterion("raise_invest >", value, "raiseInvest");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("raise_invest >=", value, "raiseInvest");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestLessThan(final Integer value) {
            this.addCriterion("raise_invest <", value, "raiseInvest");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestLessThanOrEqualTo(final Integer value) {
            this.addCriterion("raise_invest <=", value, "raiseInvest");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestIn(final List<Integer> values) {
            this.addCriterion("raise_invest in", values, "raiseInvest");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestNotIn(final List<Integer> values) {
            this.addCriterion("raise_invest not in", values, "raiseInvest");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestBetween(final Integer value1, final Integer value2) {
            this.addCriterion("raise_invest between", value1, value2, "raiseInvest");
            return (Criteria) this;
        }

        public Criteria andRaiseInvestNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("raise_invest not between", value1, value2, "raiseInvest");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioIsNull() {
            this.addCriterion("raise_ratio is null");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioIsNotNull() {
            this.addCriterion("raise_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioEqualTo(final String value) {
            this.addCriterion("raise_ratio =", value, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioNotEqualTo(final String value) {
            this.addCriterion("raise_ratio <>", value, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioGreaterThan(final String value) {
            this.addCriterion("raise_ratio >", value, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioGreaterThanOrEqualTo(final String value) {
            this.addCriterion("raise_ratio >=", value, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioLessThan(final String value) {
            this.addCriterion("raise_ratio <", value, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioLessThanOrEqualTo(final String value) {
            this.addCriterion("raise_ratio <=", value, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioLike(final String value) {
            this.addCriterion("raise_ratio like", value, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioNotLike(final String value) {
            this.addCriterion("raise_ratio not like", value, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioIn(final List<String> values) {
            this.addCriterion("raise_ratio in", values, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioNotIn(final List<String> values) {
            this.addCriterion("raise_ratio not in", values, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioBetween(final String value1, final String value2) {
            this.addCriterion("raise_ratio between", value1, value2, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseRatioNotBetween(final String value1, final String value2) {
            this.addCriterion("raise_ratio not between", value1, value2, "raiseRatio");
            return (Criteria) this;
        }

        public Criteria andRaiseDateIsNull() {
            this.addCriterion("raise_date is null");
            return (Criteria) this;
        }

        public Criteria andRaiseDateIsNotNull() {
            this.addCriterion("raise_date is not null");
            return (Criteria) this;
        }

        public Criteria andRaiseDateEqualTo(final String value) {
            this.addCriterion("raise_date =", value, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateNotEqualTo(final String value) {
            this.addCriterion("raise_date <>", value, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateGreaterThan(final String value) {
            this.addCriterion("raise_date >", value, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateGreaterThanOrEqualTo(final String value) {
            this.addCriterion("raise_date >=", value, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateLessThan(final String value) {
            this.addCriterion("raise_date <", value, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateLessThanOrEqualTo(final String value) {
            this.addCriterion("raise_date <=", value, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateLike(final String value) {
            this.addCriterion("raise_date like", value, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateNotLike(final String value) {
            this.addCriterion("raise_date not like", value, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateIn(final List<String> values) {
            this.addCriterion("raise_date in", values, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateNotIn(final List<String> values) {
            this.addCriterion("raise_date not in", values, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateBetween(final String value1, final String value2) {
            this.addCriterion("raise_date between", value1, value2, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseDateNotBetween(final String value1, final String value2) {
            this.addCriterion("raise_date not between", value1, value2, "raiseDate");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleIsNull() {
            this.addCriterion("raise_rule is null");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleIsNotNull() {
            this.addCriterion("raise_rule is not null");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleEqualTo(final String value) {
            this.addCriterion("raise_rule =", value, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleNotEqualTo(final String value) {
            this.addCriterion("raise_rule <>", value, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleGreaterThan(final String value) {
            this.addCriterion("raise_rule >", value, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleGreaterThanOrEqualTo(final String value) {
            this.addCriterion("raise_rule >=", value, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleLessThan(final String value) {
            this.addCriterion("raise_rule <", value, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleLessThanOrEqualTo(final String value) {
            this.addCriterion("raise_rule <=", value, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleLike(final String value) {
            this.addCriterion("raise_rule like", value, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleNotLike(final String value) {
            this.addCriterion("raise_rule not like", value, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleIn(final List<String> values) {
            this.addCriterion("raise_rule in", values, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleNotIn(final List<String> values) {
            this.addCriterion("raise_rule not in", values, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleBetween(final String value1, final String value2) {
            this.addCriterion("raise_rule between", value1, value2, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andRaiseRuleNotBetween(final String value1, final String value2) {
            this.addCriterion("raise_rule not between", value1, value2, "raiseRule");
            return (Criteria) this;
        }

        public Criteria andIcoIsNull() {
            this.addCriterion("ico is null");
            return (Criteria) this;
        }

        public Criteria andIcoIsNotNull() {
            this.addCriterion("ico is not null");
            return (Criteria) this;
        }

        public Criteria andIcoEqualTo(final Byte value) {
            this.addCriterion("ico =", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoNotEqualTo(final Byte value) {
            this.addCriterion("ico <>", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoGreaterThan(final Byte value) {
            this.addCriterion("ico >", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("ico >=", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoLessThan(final Byte value) {
            this.addCriterion("ico <", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoLessThanOrEqualTo(final Byte value) {
            this.addCriterion("ico <=", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoIn(final List<Byte> values) {
            this.addCriterion("ico in", values, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoNotIn(final List<Byte> values) {
            this.addCriterion("ico not in", values, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoBetween(final Byte value1, final Byte value2) {
            this.addCriterion("ico between", value1, value2, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("ico not between", value1, value2, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoTotalIsNull() {
            this.addCriterion("ico_total is null");
            return (Criteria) this;
        }

        public Criteria andIcoTotalIsNotNull() {
            this.addCriterion("ico_total is not null");
            return (Criteria) this;
        }

        public Criteria andIcoTotalEqualTo(final String value) {
            this.addCriterion("ico_total =", value, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalNotEqualTo(final String value) {
            this.addCriterion("ico_total <>", value, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalGreaterThan(final String value) {
            this.addCriterion("ico_total >", value, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalGreaterThanOrEqualTo(final String value) {
            this.addCriterion("ico_total >=", value, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalLessThan(final String value) {
            this.addCriterion("ico_total <", value, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalLessThanOrEqualTo(final String value) {
            this.addCriterion("ico_total <=", value, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalLike(final String value) {
            this.addCriterion("ico_total like", value, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalNotLike(final String value) {
            this.addCriterion("ico_total not like", value, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalIn(final List<String> values) {
            this.addCriterion("ico_total in", values, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalNotIn(final List<String> values) {
            this.addCriterion("ico_total not in", values, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalBetween(final String value1, final String value2) {
            this.addCriterion("ico_total between", value1, value2, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoTotalNotBetween(final String value1, final String value2) {
            this.addCriterion("ico_total not between", value1, value2, "icoTotal");
            return (Criteria) this;
        }

        public Criteria andIcoPriceIsNull() {
            this.addCriterion("ico_price is null");
            return (Criteria) this;
        }

        public Criteria andIcoPriceIsNotNull() {
            this.addCriterion("ico_price is not null");
            return (Criteria) this;
        }

        public Criteria andIcoPriceEqualTo(final String value) {
            this.addCriterion("ico_price =", value, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceNotEqualTo(final String value) {
            this.addCriterion("ico_price <>", value, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceGreaterThan(final String value) {
            this.addCriterion("ico_price >", value, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceGreaterThanOrEqualTo(final String value) {
            this.addCriterion("ico_price >=", value, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceLessThan(final String value) {
            this.addCriterion("ico_price <", value, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceLessThanOrEqualTo(final String value) {
            this.addCriterion("ico_price <=", value, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceLike(final String value) {
            this.addCriterion("ico_price like", value, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceNotLike(final String value) {
            this.addCriterion("ico_price not like", value, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceIn(final List<String> values) {
            this.addCriterion("ico_price in", values, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceNotIn(final List<String> values) {
            this.addCriterion("ico_price not in", values, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceBetween(final String value1, final String value2) {
            this.addCriterion("ico_price between", value1, value2, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoPriceNotBetween(final String value1, final String value2) {
            this.addCriterion("ico_price not between", value1, value2, "icoPrice");
            return (Criteria) this;
        }

        public Criteria andIcoInvestIsNull() {
            this.addCriterion("ico_invest is null");
            return (Criteria) this;
        }

        public Criteria andIcoInvestIsNotNull() {
            this.addCriterion("ico_invest is not null");
            return (Criteria) this;
        }

        public Criteria andIcoInvestEqualTo(final Integer value) {
            this.addCriterion("ico_invest =", value, "icoInvest");
            return (Criteria) this;
        }

        public Criteria andIcoInvestNotEqualTo(final Integer value) {
            this.addCriterion("ico_invest <>", value, "icoInvest");
            return (Criteria) this;
        }

        public Criteria andIcoInvestGreaterThan(final Integer value) {
            this.addCriterion("ico_invest >", value, "icoInvest");
            return (Criteria) this;
        }

        public Criteria andIcoInvestGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("ico_invest >=", value, "icoInvest");
            return (Criteria) this;
        }

        public Criteria andIcoInvestLessThan(final Integer value) {
            this.addCriterion("ico_invest <", value, "icoInvest");
            return (Criteria) this;
        }

        public Criteria andIcoInvestLessThanOrEqualTo(final Integer value) {
            this.addCriterion("ico_invest <=", value, "icoInvest");
            return (Criteria) this;
        }

        public Criteria andIcoInvestIn(final List<Integer> values) {
            this.addCriterion("ico_invest in", values, "icoInvest");
            return (Criteria) this;
        }

        public Criteria andIcoInvestNotIn(final List<Integer> values) {
            this.addCriterion("ico_invest not in", values, "icoInvest");
            return (Criteria) this;
        }

        public Criteria andIcoInvestBetween(final Integer value1, final Integer value2) {
            this.addCriterion("ico_invest between", value1, value2, "icoInvest");
            return (Criteria) this;
        }

        public Criteria andIcoInvestNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("ico_invest not between", value1, value2, "icoInvest");
            return (Criteria) this;
        }

        public Criteria andIcoRatioIsNull() {
            this.addCriterion("ico_ratio is null");
            return (Criteria) this;
        }

        public Criteria andIcoRatioIsNotNull() {
            this.addCriterion("ico_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andIcoRatioEqualTo(final String value) {
            this.addCriterion("ico_ratio =", value, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioNotEqualTo(final String value) {
            this.addCriterion("ico_ratio <>", value, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioGreaterThan(final String value) {
            this.addCriterion("ico_ratio >", value, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioGreaterThanOrEqualTo(final String value) {
            this.addCriterion("ico_ratio >=", value, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioLessThan(final String value) {
            this.addCriterion("ico_ratio <", value, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioLessThanOrEqualTo(final String value) {
            this.addCriterion("ico_ratio <=", value, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioLike(final String value) {
            this.addCriterion("ico_ratio like", value, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioNotLike(final String value) {
            this.addCriterion("ico_ratio not like", value, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioIn(final List<String> values) {
            this.addCriterion("ico_ratio in", values, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioNotIn(final List<String> values) {
            this.addCriterion("ico_ratio not in", values, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioBetween(final String value1, final String value2) {
            this.addCriterion("ico_ratio between", value1, value2, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoRatioNotBetween(final String value1, final String value2) {
            this.addCriterion("ico_ratio not between", value1, value2, "icoRatio");
            return (Criteria) this;
        }

        public Criteria andIcoDateIsNull() {
            this.addCriterion("ico_date is null");
            return (Criteria) this;
        }

        public Criteria andIcoDateIsNotNull() {
            this.addCriterion("ico_date is not null");
            return (Criteria) this;
        }

        public Criteria andIcoDateEqualTo(final String value) {
            this.addCriterion("ico_date =", value, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateNotEqualTo(final String value) {
            this.addCriterion("ico_date <>", value, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateGreaterThan(final String value) {
            this.addCriterion("ico_date >", value, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateGreaterThanOrEqualTo(final String value) {
            this.addCriterion("ico_date >=", value, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateLessThan(final String value) {
            this.addCriterion("ico_date <", value, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateLessThanOrEqualTo(final String value) {
            this.addCriterion("ico_date <=", value, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateLike(final String value) {
            this.addCriterion("ico_date like", value, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateNotLike(final String value) {
            this.addCriterion("ico_date not like", value, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateIn(final List<String> values) {
            this.addCriterion("ico_date in", values, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateNotIn(final List<String> values) {
            this.addCriterion("ico_date not in", values, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateBetween(final String value1, final String value2) {
            this.addCriterion("ico_date between", value1, value2, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoDateNotBetween(final String value1, final String value2) {
            this.addCriterion("ico_date not between", value1, value2, "icoDate");
            return (Criteria) this;
        }

        public Criteria andIcoRuleIsNull() {
            this.addCriterion("ico_rule is null");
            return (Criteria) this;
        }

        public Criteria andIcoRuleIsNotNull() {
            this.addCriterion("ico_rule is not null");
            return (Criteria) this;
        }

        public Criteria andIcoRuleEqualTo(final String value) {
            this.addCriterion("ico_rule =", value, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleNotEqualTo(final String value) {
            this.addCriterion("ico_rule <>", value, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleGreaterThan(final String value) {
            this.addCriterion("ico_rule >", value, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleGreaterThanOrEqualTo(final String value) {
            this.addCriterion("ico_rule >=", value, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleLessThan(final String value) {
            this.addCriterion("ico_rule <", value, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleLessThanOrEqualTo(final String value) {
            this.addCriterion("ico_rule <=", value, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleLike(final String value) {
            this.addCriterion("ico_rule like", value, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleNotLike(final String value) {
            this.addCriterion("ico_rule not like", value, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleIn(final List<String> values) {
            this.addCriterion("ico_rule in", values, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleNotIn(final List<String> values) {
            this.addCriterion("ico_rule not in", values, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleBetween(final String value1, final String value2) {
            this.addCriterion("ico_rule between", value1, value2, "icoRule");
            return (Criteria) this;
        }

        public Criteria andIcoRuleNotBetween(final String value1, final String value2) {
            this.addCriterion("ico_rule not between", value1, value2, "icoRule");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkIsNull() {
            this.addCriterion("telegram_link is null");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkIsNotNull() {
            this.addCriterion("telegram_link is not null");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkEqualTo(final String value) {
            this.addCriterion("telegram_link =", value, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkNotEqualTo(final String value) {
            this.addCriterion("telegram_link <>", value, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkGreaterThan(final String value) {
            this.addCriterion("telegram_link >", value, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkGreaterThanOrEqualTo(final String value) {
            this.addCriterion("telegram_link >=", value, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkLessThan(final String value) {
            this.addCriterion("telegram_link <", value, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkLessThanOrEqualTo(final String value) {
            this.addCriterion("telegram_link <=", value, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkLike(final String value) {
            this.addCriterion("telegram_link like", value, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkNotLike(final String value) {
            this.addCriterion("telegram_link not like", value, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkIn(final List<String> values) {
            this.addCriterion("telegram_link in", values, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkNotIn(final List<String> values) {
            this.addCriterion("telegram_link not in", values, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkBetween(final String value1, final String value2) {
            this.addCriterion("telegram_link between", value1, value2, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkNotBetween(final String value1, final String value2) {
            this.addCriterion("telegram_link not between", value1, value2, "telegramLink");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersIsNull() {
            this.addCriterion("telegram_link_members is null");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersIsNotNull() {
            this.addCriterion("telegram_link_members is not null");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersEqualTo(final String value) {
            this.addCriterion("telegram_link_members =", value, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersNotEqualTo(final String value) {
            this.addCriterion("telegram_link_members <>", value, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersGreaterThan(final String value) {
            this.addCriterion("telegram_link_members >", value, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersGreaterThanOrEqualTo(final String value) {
            this.addCriterion("telegram_link_members >=", value, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersLessThan(final String value) {
            this.addCriterion("telegram_link_members <", value, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersLessThanOrEqualTo(final String value) {
            this.addCriterion("telegram_link_members <=", value, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersLike(final String value) {
            this.addCriterion("telegram_link_members like", value, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersNotLike(final String value) {
            this.addCriterion("telegram_link_members not like", value, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersIn(final List<String> values) {
            this.addCriterion("telegram_link_members in", values, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersNotIn(final List<String> values) {
            this.addCriterion("telegram_link_members not in", values, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersBetween(final String value1, final String value2) {
            this.addCriterion("telegram_link_members between", value1, value2, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andTelegramLinkMembersNotBetween(final String value1, final String value2) {
            this.addCriterion("telegram_link_members not between", value1, value2, "telegramLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkIsNull() {
            this.addCriterion("wechat_link is null");
            return (Criteria) this;
        }

        public Criteria andWechatLinkIsNotNull() {
            this.addCriterion("wechat_link is not null");
            return (Criteria) this;
        }

        public Criteria andWechatLinkEqualTo(final String value) {
            this.addCriterion("wechat_link =", value, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkNotEqualTo(final String value) {
            this.addCriterion("wechat_link <>", value, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkGreaterThan(final String value) {
            this.addCriterion("wechat_link >", value, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkGreaterThanOrEqualTo(final String value) {
            this.addCriterion("wechat_link >=", value, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkLessThan(final String value) {
            this.addCriterion("wechat_link <", value, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkLessThanOrEqualTo(final String value) {
            this.addCriterion("wechat_link <=", value, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkLike(final String value) {
            this.addCriterion("wechat_link like", value, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkNotLike(final String value) {
            this.addCriterion("wechat_link not like", value, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkIn(final List<String> values) {
            this.addCriterion("wechat_link in", values, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkNotIn(final List<String> values) {
            this.addCriterion("wechat_link not in", values, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkBetween(final String value1, final String value2) {
            this.addCriterion("wechat_link between", value1, value2, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkNotBetween(final String value1, final String value2) {
            this.addCriterion("wechat_link not between", value1, value2, "wechatLink");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersIsNull() {
            this.addCriterion("wechat_link_members is null");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersIsNotNull() {
            this.addCriterion("wechat_link_members is not null");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersEqualTo(final String value) {
            this.addCriterion("wechat_link_members =", value, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersNotEqualTo(final String value) {
            this.addCriterion("wechat_link_members <>", value, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersGreaterThan(final String value) {
            this.addCriterion("wechat_link_members >", value, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersGreaterThanOrEqualTo(final String value) {
            this.addCriterion("wechat_link_members >=", value, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersLessThan(final String value) {
            this.addCriterion("wechat_link_members <", value, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersLessThanOrEqualTo(final String value) {
            this.addCriterion("wechat_link_members <=", value, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersLike(final String value) {
            this.addCriterion("wechat_link_members like", value, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersNotLike(final String value) {
            this.addCriterion("wechat_link_members not like", value, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersIn(final List<String> values) {
            this.addCriterion("wechat_link_members in", values, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersNotIn(final List<String> values) {
            this.addCriterion("wechat_link_members not in", values, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersBetween(final String value1, final String value2) {
            this.addCriterion("wechat_link_members between", value1, value2, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andWechatLinkMembersNotBetween(final String value1, final String value2) {
            this.addCriterion("wechat_link_members not between", value1, value2, "wechatLinkMembers");
            return (Criteria) this;
        }

        public Criteria andQqIsNull() {
            this.addCriterion("qq is null");
            return (Criteria) this;
        }

        public Criteria andQqIsNotNull() {
            this.addCriterion("qq is not null");
            return (Criteria) this;
        }

        public Criteria andQqEqualTo(final String value) {
            this.addCriterion("qq =", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotEqualTo(final String value) {
            this.addCriterion("qq <>", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThan(final String value) {
            this.addCriterion("qq >", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThanOrEqualTo(final String value) {
            this.addCriterion("qq >=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThan(final String value) {
            this.addCriterion("qq <", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThanOrEqualTo(final String value) {
            this.addCriterion("qq <=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLike(final String value) {
            this.addCriterion("qq like", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotLike(final String value) {
            this.addCriterion("qq not like", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqIn(final List<String> values) {
            this.addCriterion("qq in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotIn(final List<String> values) {
            this.addCriterion("qq not in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqBetween(final String value1, final String value2) {
            this.addCriterion("qq between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotBetween(final String value1, final String value2) {
            this.addCriterion("qq not between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkIsNull() {
            this.addCriterion("kakao_talk is null");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkIsNotNull() {
            this.addCriterion("kakao_talk is not null");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkEqualTo(final String value) {
            this.addCriterion("kakao_talk =", value, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkNotEqualTo(final String value) {
            this.addCriterion("kakao_talk <>", value, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkGreaterThan(final String value) {
            this.addCriterion("kakao_talk >", value, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkGreaterThanOrEqualTo(final String value) {
            this.addCriterion("kakao_talk >=", value, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkLessThan(final String value) {
            this.addCriterion("kakao_talk <", value, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkLessThanOrEqualTo(final String value) {
            this.addCriterion("kakao_talk <=", value, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkLike(final String value) {
            this.addCriterion("kakao_talk like", value, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkNotLike(final String value) {
            this.addCriterion("kakao_talk not like", value, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkIn(final List<String> values) {
            this.addCriterion("kakao_talk in", values, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkNotIn(final List<String> values) {
            this.addCriterion("kakao_talk not in", values, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkBetween(final String value1, final String value2) {
            this.addCriterion("kakao_talk between", value1, value2, "kakaoTalk");
            return (Criteria) this;
        }

        public Criteria andKakaoTalkNotBetween(final String value1, final String value2) {
            this.addCriterion("kakao_talk not between", value1, value2, "kakaoTalk");
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

        public Criteria andRedditIsNull() {
            this.addCriterion("reddit is null");
            return (Criteria) this;
        }

        public Criteria andRedditIsNotNull() {
            this.addCriterion("reddit is not null");
            return (Criteria) this;
        }

        public Criteria andRedditEqualTo(final String value) {
            this.addCriterion("reddit =", value, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditNotEqualTo(final String value) {
            this.addCriterion("reddit <>", value, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditGreaterThan(final String value) {
            this.addCriterion("reddit >", value, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditGreaterThanOrEqualTo(final String value) {
            this.addCriterion("reddit >=", value, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditLessThan(final String value) {
            this.addCriterion("reddit <", value, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditLessThanOrEqualTo(final String value) {
            this.addCriterion("reddit <=", value, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditLike(final String value) {
            this.addCriterion("reddit like", value, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditNotLike(final String value) {
            this.addCriterion("reddit not like", value, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditIn(final List<String> values) {
            this.addCriterion("reddit in", values, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditNotIn(final List<String> values) {
            this.addCriterion("reddit not in", values, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditBetween(final String value1, final String value2) {
            this.addCriterion("reddit between", value1, value2, "reddit");
            return (Criteria) this;
        }

        public Criteria andRedditNotBetween(final String value1, final String value2) {
            this.addCriterion("reddit not between", value1, value2, "reddit");
            return (Criteria) this;
        }

        public Criteria andOthersIsNull() {
            this.addCriterion("others is null");
            return (Criteria) this;
        }

        public Criteria andOthersIsNotNull() {
            this.addCriterion("others is not null");
            return (Criteria) this;
        }

        public Criteria andOthersEqualTo(final String value) {
            this.addCriterion("others =", value, "others");
            return (Criteria) this;
        }

        public Criteria andOthersNotEqualTo(final String value) {
            this.addCriterion("others <>", value, "others");
            return (Criteria) this;
        }

        public Criteria andOthersGreaterThan(final String value) {
            this.addCriterion("others >", value, "others");
            return (Criteria) this;
        }

        public Criteria andOthersGreaterThanOrEqualTo(final String value) {
            this.addCriterion("others >=", value, "others");
            return (Criteria) this;
        }

        public Criteria andOthersLessThan(final String value) {
            this.addCriterion("others <", value, "others");
            return (Criteria) this;
        }

        public Criteria andOthersLessThanOrEqualTo(final String value) {
            this.addCriterion("others <=", value, "others");
            return (Criteria) this;
        }

        public Criteria andOthersLike(final String value) {
            this.addCriterion("others like", value, "others");
            return (Criteria) this;
        }

        public Criteria andOthersNotLike(final String value) {
            this.addCriterion("others not like", value, "others");
            return (Criteria) this;
        }

        public Criteria andOthersIn(final List<String> values) {
            this.addCriterion("others in", values, "others");
            return (Criteria) this;
        }

        public Criteria andOthersNotIn(final List<String> values) {
            this.addCriterion("others not in", values, "others");
            return (Criteria) this;
        }

        public Criteria andOthersBetween(final String value1, final String value2) {
            this.addCriterion("others between", value1, value2, "others");
            return (Criteria) this;
        }

        public Criteria andOthersNotBetween(final String value1, final String value2) {
            this.addCriterion("others not between", value1, value2, "others");
            return (Criteria) this;
        }

        public Criteria andWalletTypeIsNull() {
            this.addCriterion("wallet_type is null");
            return (Criteria) this;
        }

        public Criteria andWalletTypeIsNotNull() {
            this.addCriterion("wallet_type is not null");
            return (Criteria) this;
        }

        public Criteria andWalletTypeEqualTo(final Integer value) {
            this.addCriterion("wallet_type =", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeNotEqualTo(final Integer value) {
            this.addCriterion("wallet_type <>", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeGreaterThan(final Integer value) {
            this.addCriterion("wallet_type >", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("wallet_type >=", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeLessThan(final Integer value) {
            this.addCriterion("wallet_type <", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("wallet_type <=", value, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeIn(final List<Integer> values) {
            this.addCriterion("wallet_type in", values, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeNotIn(final List<Integer> values) {
            this.addCriterion("wallet_type not in", values, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("wallet_type between", value1, value2, "walletType");
            return (Criteria) this;
        }

        public Criteria andWalletTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("wallet_type not between", value1, value2, "walletType");
            return (Criteria) this;
        }

        public Criteria andContractIsNull() {
            this.addCriterion("contract is null");
            return (Criteria) this;
        }

        public Criteria andContractIsNotNull() {
            this.addCriterion("contract is not null");
            return (Criteria) this;
        }

        public Criteria andContractEqualTo(final String value) {
            this.addCriterion("contract =", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractNotEqualTo(final String value) {
            this.addCriterion("contract <>", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractGreaterThan(final String value) {
            this.addCriterion("contract >", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractGreaterThanOrEqualTo(final String value) {
            this.addCriterion("contract >=", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractLessThan(final String value) {
            this.addCriterion("contract <", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractLessThanOrEqualTo(final String value) {
            this.addCriterion("contract <=", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractLike(final String value) {
            this.addCriterion("contract like", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractNotLike(final String value) {
            this.addCriterion("contract not like", value, "contract");
            return (Criteria) this;
        }

        public Criteria andContractIn(final List<String> values) {
            this.addCriterion("contract in", values, "contract");
            return (Criteria) this;
        }

        public Criteria andContractNotIn(final List<String> values) {
            this.addCriterion("contract not in", values, "contract");
            return (Criteria) this;
        }

        public Criteria andContractBetween(final String value1, final String value2) {
            this.addCriterion("contract between", value1, value2, "contract");
            return (Criteria) this;
        }

        public Criteria andContractNotBetween(final String value1, final String value2) {
            this.addCriterion("contract not between", value1, value2, "contract");
            return (Criteria) this;
        }

        public Criteria andWalletIsNull() {
            this.addCriterion("wallet is null");
            return (Criteria) this;
        }

        public Criteria andWalletIsNotNull() {
            this.addCriterion("wallet is not null");
            return (Criteria) this;
        }

        public Criteria andWalletEqualTo(final String value) {
            this.addCriterion("wallet =", value, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletNotEqualTo(final String value) {
            this.addCriterion("wallet <>", value, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletGreaterThan(final String value) {
            this.addCriterion("wallet >", value, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletGreaterThanOrEqualTo(final String value) {
            this.addCriterion("wallet >=", value, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletLessThan(final String value) {
            this.addCriterion("wallet <", value, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletLessThanOrEqualTo(final String value) {
            this.addCriterion("wallet <=", value, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletLike(final String value) {
            this.addCriterion("wallet like", value, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletNotLike(final String value) {
            this.addCriterion("wallet not like", value, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletIn(final List<String> values) {
            this.addCriterion("wallet in", values, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletNotIn(final List<String> values) {
            this.addCriterion("wallet not in", values, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletBetween(final String value1, final String value2) {
            this.addCriterion("wallet between", value1, value2, "wallet");
            return (Criteria) this;
        }

        public Criteria andWalletNotBetween(final String value1, final String value2) {
            this.addCriterion("wallet not between", value1, value2, "wallet");
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

        public Criteria andCompanyPositionIsNull() {
            this.addCriterion("company_position is null");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionIsNotNull() {
            this.addCriterion("company_position is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionEqualTo(final String value) {
            this.addCriterion("company_position =", value, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionNotEqualTo(final String value) {
            this.addCriterion("company_position <>", value, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionGreaterThan(final String value) {
            this.addCriterion("company_position >", value, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionGreaterThanOrEqualTo(final String value) {
            this.addCriterion("company_position >=", value, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionLessThan(final String value) {
            this.addCriterion("company_position <", value, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionLessThanOrEqualTo(final String value) {
            this.addCriterion("company_position <=", value, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionLike(final String value) {
            this.addCriterion("company_position like", value, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionNotLike(final String value) {
            this.addCriterion("company_position not like", value, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionIn(final List<String> values) {
            this.addCriterion("company_position in", values, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionNotIn(final List<String> values) {
            this.addCriterion("company_position not in", values, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionBetween(final String value1, final String value2) {
            this.addCriterion("company_position between", value1, value2, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andCompanyPositionNotBetween(final String value1, final String value2) {
            this.addCriterion("company_position not between", value1, value2, "companyPosition");
            return (Criteria) this;
        }

        public Criteria andProductAddressIsNull() {
            this.addCriterion("product_address is null");
            return (Criteria) this;
        }

        public Criteria andProductAddressIsNotNull() {
            this.addCriterion("product_address is not null");
            return (Criteria) this;
        }

        public Criteria andProductAddressEqualTo(final String value) {
            this.addCriterion("product_address =", value, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressNotEqualTo(final String value) {
            this.addCriterion("product_address <>", value, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressGreaterThan(final String value) {
            this.addCriterion("product_address >", value, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressGreaterThanOrEqualTo(final String value) {
            this.addCriterion("product_address >=", value, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressLessThan(final String value) {
            this.addCriterion("product_address <", value, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressLessThanOrEqualTo(final String value) {
            this.addCriterion("product_address <=", value, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressLike(final String value) {
            this.addCriterion("product_address like", value, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressNotLike(final String value) {
            this.addCriterion("product_address not like", value, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressIn(final List<String> values) {
            this.addCriterion("product_address in", values, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressNotIn(final List<String> values) {
            this.addCriterion("product_address not in", values, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressBetween(final String value1, final String value2) {
            this.addCriterion("product_address between", value1, value2, "productAddress");
            return (Criteria) this;
        }

        public Criteria andProductAddressNotBetween(final String value1, final String value2) {
            this.addCriterion("product_address not between", value1, value2, "productAddress");
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