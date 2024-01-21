package cc.newex.dax.extra.criteria.cgm;

import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 项目基础信息表 查询条件类
 *
 * @author mbg.generated
 * @date 2018-08-17 09:56:18
 */
public class ProjectTokenInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProjectTokenInfoExample() {
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

    public static ProjectTokenInfoExample toExample(final ProjectTokenInfo projectTokenInfo) {
        if (projectTokenInfo == null) {
            return null;
        }

        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        final ProjectTokenInfoExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(projectTokenInfo.getId())) {
            criteria.andIdEqualTo(projectTokenInfo.getId());
        }

        if (Objects.nonNull(projectTokenInfo.getUserId())) {
            criteria.andUserIdEqualTo(projectTokenInfo.getUserId());
        }

        if (StringUtils.isNotBlank(projectTokenInfo.getName())) {
            criteria.andNameLike(projectTokenInfo.getName());
        }

        if (StringUtils.isNotBlank(projectTokenInfo.getMobile())) {
            criteria.andMobileLike(projectTokenInfo.getMobile());
        }

        if (StringUtils.isNotBlank(projectTokenInfo.getEmail())) {
            criteria.andEmailLike(projectTokenInfo.getEmail());
        }

        if (StringUtils.isNotBlank(projectTokenInfo.getToken())) {
            criteria.andTokenLike(projectTokenInfo.getToken());
        }

        if (StringUtils.isNotBlank(projectTokenInfo.getTokenSymbol())) {
            criteria.andTokenSymbolLike(projectTokenInfo.getTokenSymbol());
        }

        if (Objects.nonNull(projectTokenInfo.getStatus())) {
            criteria.andStatusEqualTo(projectTokenInfo.getStatus());
        }

        if (StringUtils.isNotBlank(projectTokenInfo.getContacts())) {
            criteria.andContactsLike(projectTokenInfo.getContacts());
        }

        if (StringUtils.isNotBlank(projectTokenInfo.getWechat())) {
            criteria.andWechatLike(projectTokenInfo.getWechat());
        }

        if (Objects.nonNull(projectTokenInfo.getBrokerId())) {
            criteria.andBrokerIdEqualTo(projectTokenInfo.getBrokerId());
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
            this.addCriterion("name like", value, "name");
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

        public Criteria andEmailIsNull() {
            this.addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            this.addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(final String value) {
            this.addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(final String value) {
            this.addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(final String value) {
            this.addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(final String value) {
            this.addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(final String value) {
            this.addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(final String value) {
            this.addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(final String value) {
            this.addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(final String value) {
            this.addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(final List<String> values) {
            this.addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(final List<String> values) {
            this.addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(final String value1, final String value2) {
            this.addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(final String value1, final String value2) {
            this.addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andTokenIsNull() {
            this.addCriterion("token is null");
            return (Criteria) this;
        }

        public Criteria andTokenIsNotNull() {
            this.addCriterion("token is not null");
            return (Criteria) this;
        }

        public Criteria andTokenEqualTo(final String value) {
            this.addCriterion("token =", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotEqualTo(final String value) {
            this.addCriterion("token <>", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThan(final String value) {
            this.addCriterion("token >", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThanOrEqualTo(final String value) {
            this.addCriterion("token >=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThan(final String value) {
            this.addCriterion("token <", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThanOrEqualTo(final String value) {
            this.addCriterion("token <=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLike(final String value) {
            this.addCriterion("token like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotLike(final String value) {
            this.addCriterion("token not like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenIn(final List<String> values) {
            this.addCriterion("token in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotIn(final List<String> values) {
            this.addCriterion("token not in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenBetween(final String value1, final String value2) {
            this.addCriterion("token between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotBetween(final String value1, final String value2) {
            this.addCriterion("token not between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolIsNull() {
            this.addCriterion("token_symbol is null");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolIsNotNull() {
            this.addCriterion("token_symbol is not null");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolEqualTo(final String value) {
            this.addCriterion("token_symbol =", value, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolNotEqualTo(final String value) {
            this.addCriterion("token_symbol <>", value, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolGreaterThan(final String value) {
            this.addCriterion("token_symbol >", value, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolGreaterThanOrEqualTo(final String value) {
            this.addCriterion("token_symbol >=", value, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolLessThan(final String value) {
            this.addCriterion("token_symbol <", value, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolLessThanOrEqualTo(final String value) {
            this.addCriterion("token_symbol <=", value, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolLike(final String value) {
            this.addCriterion("token_symbol like", value, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolNotLike(final String value) {
            this.addCriterion("token_symbol not like", value, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolIn(final List<String> values) {
            this.addCriterion("token_symbol in", values, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolNotIn(final List<String> values) {
            this.addCriterion("token_symbol not in", values, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolBetween(final String value1, final String value2) {
            this.addCriterion("token_symbol between", value1, value2, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andTokenSymbolNotBetween(final String value1, final String value2) {
            this.addCriterion("token_symbol not between", value1, value2, "tokenSymbol");
            return (Criteria) this;
        }

        public Criteria andImgNameIsNull() {
            this.addCriterion("img_name is null");
            return (Criteria) this;
        }

        public Criteria andImgNameIsNotNull() {
            this.addCriterion("img_name is not null");
            return (Criteria) this;
        }

        public Criteria andImgNameEqualTo(final String value) {
            this.addCriterion("img_name =", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameNotEqualTo(final String value) {
            this.addCriterion("img_name <>", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameGreaterThan(final String value) {
            this.addCriterion("img_name >", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("img_name >=", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameLessThan(final String value) {
            this.addCriterion("img_name <", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameLessThanOrEqualTo(final String value) {
            this.addCriterion("img_name <=", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameLike(final String value) {
            this.addCriterion("img_name like", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameNotLike(final String value) {
            this.addCriterion("img_name not like", value, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameIn(final List<String> values) {
            this.addCriterion("img_name in", values, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameNotIn(final List<String> values) {
            this.addCriterion("img_name not in", values, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameBetween(final String value1, final String value2) {
            this.addCriterion("img_name between", value1, value2, "imgName");
            return (Criteria) this;
        }

        public Criteria andImgNameNotBetween(final String value1, final String value2) {
            this.addCriterion("img_name not between", value1, value2, "imgName");
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

        public Criteria andTypeEqualTo(final String value) {
            this.addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(final String value) {
            this.addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(final String value) {
            this.addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(final String value) {
            this.addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(final String value) {
            this.addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(final String value) {
            this.addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(final String value) {
            this.addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(final List<String> values) {
            this.addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(final List<String> values) {
            this.addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(final String value1, final String value2) {
            this.addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(final String value1, final String value2) {
            this.addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andIssueIsNull() {
            this.addCriterion("issue is null");
            return (Criteria) this;
        }

        public Criteria andIssueIsNotNull() {
            this.addCriterion("issue is not null");
            return (Criteria) this;
        }

        public Criteria andIssueEqualTo(final String value) {
            this.addCriterion("issue =", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueNotEqualTo(final String value) {
            this.addCriterion("issue <>", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueGreaterThan(final String value) {
            this.addCriterion("issue >", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueGreaterThanOrEqualTo(final String value) {
            this.addCriterion("issue >=", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueLessThan(final String value) {
            this.addCriterion("issue <", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueLessThanOrEqualTo(final String value) {
            this.addCriterion("issue <=", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueLike(final String value) {
            this.addCriterion("issue like", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueNotLike(final String value) {
            this.addCriterion("issue not like", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueIn(final List<String> values) {
            this.addCriterion("issue in", values, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueNotIn(final List<String> values) {
            this.addCriterion("issue not in", values, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueBetween(final String value1, final String value2) {
            this.addCriterion("issue between", value1, value2, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueNotBetween(final String value1, final String value2) {
            this.addCriterion("issue not between", value1, value2, "issue");
            return (Criteria) this;
        }

        public Criteria andCirculatingIsNull() {
            this.addCriterion("circulating is null");
            return (Criteria) this;
        }

        public Criteria andCirculatingIsNotNull() {
            this.addCriterion("circulating is not null");
            return (Criteria) this;
        }

        public Criteria andCirculatingEqualTo(final String value) {
            this.addCriterion("circulating =", value, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingNotEqualTo(final String value) {
            this.addCriterion("circulating <>", value, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingGreaterThan(final String value) {
            this.addCriterion("circulating >", value, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingGreaterThanOrEqualTo(final String value) {
            this.addCriterion("circulating >=", value, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingLessThan(final String value) {
            this.addCriterion("circulating <", value, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingLessThanOrEqualTo(final String value) {
            this.addCriterion("circulating <=", value, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingLike(final String value) {
            this.addCriterion("circulating like", value, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingNotLike(final String value) {
            this.addCriterion("circulating not like", value, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingIn(final List<String> values) {
            this.addCriterion("circulating in", values, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingNotIn(final List<String> values) {
            this.addCriterion("circulating not in", values, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingBetween(final String value1, final String value2) {
            this.addCriterion("circulating between", value1, value2, "circulating");
            return (Criteria) this;
        }

        public Criteria andCirculatingNotBetween(final String value1, final String value2) {
            this.addCriterion("circulating not between", value1, value2, "circulating");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapIsNull() {
            this.addCriterion("coinmarketcap is null");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapIsNotNull() {
            this.addCriterion("coinmarketcap is not null");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapEqualTo(final String value) {
            this.addCriterion("coinmarketcap =", value, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapNotEqualTo(final String value) {
            this.addCriterion("coinmarketcap <>", value, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapGreaterThan(final String value) {
            this.addCriterion("coinmarketcap >", value, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapGreaterThanOrEqualTo(final String value) {
            this.addCriterion("coinmarketcap >=", value, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapLessThan(final String value) {
            this.addCriterion("coinmarketcap <", value, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapLessThanOrEqualTo(final String value) {
            this.addCriterion("coinmarketcap <=", value, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapLike(final String value) {
            this.addCriterion("coinmarketcap like", value, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapNotLike(final String value) {
            this.addCriterion("coinmarketcap not like", value, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapIn(final List<String> values) {
            this.addCriterion("coinmarketcap in", values, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapNotIn(final List<String> values) {
            this.addCriterion("coinmarketcap not in", values, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapBetween(final String value1, final String value2) {
            this.addCriterion("coinmarketcap between", value1, value2, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andCoinmarketcapNotBetween(final String value1, final String value2) {
            this.addCriterion("coinmarketcap not between", value1, value2, "coinmarketcap");
            return (Criteria) this;
        }

        public Criteria andOnlineIsNull() {
            this.addCriterion("online is null");
            return (Criteria) this;
        }

        public Criteria andOnlineIsNotNull() {
            this.addCriterion("online is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineEqualTo(final Byte value) {
            this.addCriterion("online =", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotEqualTo(final Byte value) {
            this.addCriterion("online <>", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineGreaterThan(final Byte value) {
            this.addCriterion("online >", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("online >=", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineLessThan(final Byte value) {
            this.addCriterion("online <", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineLessThanOrEqualTo(final Byte value) {
            this.addCriterion("online <=", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineIn(final List<Byte> values) {
            this.addCriterion("online in", values, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotIn(final List<Byte> values) {
            this.addCriterion("online not in", values, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineBetween(final Byte value1, final Byte value2) {
            this.addCriterion("online between", value1, value2, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("online not between", value1, value2, "online");
            return (Criteria) this;
        }

        public Criteria andExchangeNameIsNull() {
            this.addCriterion("exchange_name is null");
            return (Criteria) this;
        }

        public Criteria andExchangeNameIsNotNull() {
            this.addCriterion("exchange_name is not null");
            return (Criteria) this;
        }

        public Criteria andExchangeNameEqualTo(final String value) {
            this.addCriterion("exchange_name =", value, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameNotEqualTo(final String value) {
            this.addCriterion("exchange_name <>", value, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameGreaterThan(final String value) {
            this.addCriterion("exchange_name >", value, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("exchange_name >=", value, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameLessThan(final String value) {
            this.addCriterion("exchange_name <", value, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameLessThanOrEqualTo(final String value) {
            this.addCriterion("exchange_name <=", value, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameLike(final String value) {
            this.addCriterion("exchange_name like", value, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameNotLike(final String value) {
            this.addCriterion("exchange_name not like", value, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameIn(final List<String> values) {
            this.addCriterion("exchange_name in", values, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameNotIn(final List<String> values) {
            this.addCriterion("exchange_name not in", values, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameBetween(final String value1, final String value2) {
            this.addCriterion("exchange_name between", value1, value2, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andExchangeNameNotBetween(final String value1, final String value2) {
            this.addCriterion("exchange_name not between", value1, value2, "exchangeName");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeIsNull() {
            this.addCriterion("trade_volume is null");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeIsNotNull() {
            this.addCriterion("trade_volume is not null");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeEqualTo(final String value) {
            this.addCriterion("trade_volume =", value, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeNotEqualTo(final String value) {
            this.addCriterion("trade_volume <>", value, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeGreaterThan(final String value) {
            this.addCriterion("trade_volume >", value, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("trade_volume >=", value, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeLessThan(final String value) {
            this.addCriterion("trade_volume <", value, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeLessThanOrEqualTo(final String value) {
            this.addCriterion("trade_volume <=", value, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeLike(final String value) {
            this.addCriterion("trade_volume like", value, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeNotLike(final String value) {
            this.addCriterion("trade_volume not like", value, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeIn(final List<String> values) {
            this.addCriterion("trade_volume in", values, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeNotIn(final List<String> values) {
            this.addCriterion("trade_volume not in", values, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeBetween(final String value1, final String value2) {
            this.addCriterion("trade_volume between", value1, value2, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andTradeVolumeNotBetween(final String value1, final String value2) {
            this.addCriterion("trade_volume not between", value1, value2, "tradeVolume");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            this.addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            this.addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(final BigDecimal value) {
            this.addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(final BigDecimal value) {
            this.addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(final BigDecimal value) {
            this.addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(final BigDecimal value) {
            this.addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(final List<BigDecimal> values) {
            this.addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(final List<BigDecimal> values) {
            this.addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPossessUsersIsNull() {
            this.addCriterion("possess_users is null");
            return (Criteria) this;
        }

        public Criteria andPossessUsersIsNotNull() {
            this.addCriterion("possess_users is not null");
            return (Criteria) this;
        }

        public Criteria andPossessUsersEqualTo(final Integer value) {
            this.addCriterion("possess_users =", value, "possessUsers");
            return (Criteria) this;
        }

        public Criteria andPossessUsersNotEqualTo(final Integer value) {
            this.addCriterion("possess_users <>", value, "possessUsers");
            return (Criteria) this;
        }

        public Criteria andPossessUsersGreaterThan(final Integer value) {
            this.addCriterion("possess_users >", value, "possessUsers");
            return (Criteria) this;
        }

        public Criteria andPossessUsersGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("possess_users >=", value, "possessUsers");
            return (Criteria) this;
        }

        public Criteria andPossessUsersLessThan(final Integer value) {
            this.addCriterion("possess_users <", value, "possessUsers");
            return (Criteria) this;
        }

        public Criteria andPossessUsersLessThanOrEqualTo(final Integer value) {
            this.addCriterion("possess_users <=", value, "possessUsers");
            return (Criteria) this;
        }

        public Criteria andPossessUsersIn(final List<Integer> values) {
            this.addCriterion("possess_users in", values, "possessUsers");
            return (Criteria) this;
        }

        public Criteria andPossessUsersNotIn(final List<Integer> values) {
            this.addCriterion("possess_users not in", values, "possessUsers");
            return (Criteria) this;
        }

        public Criteria andPossessUsersBetween(final Integer value1, final Integer value2) {
            this.addCriterion("possess_users between", value1, value2, "possessUsers");
            return (Criteria) this;
        }

        public Criteria andPossessUsersNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("possess_users not between", value1, value2, "possessUsers");
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

        public Criteria andStatusEqualTo(final Byte value) {
            this.addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(final Byte value) {
            this.addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(final Byte value) {
            this.addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(final Byte value) {
            this.addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(final Byte value) {
            this.addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(final List<Byte> values) {
            this.addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(final List<Byte> values) {
            this.addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(final Byte value1, final Byte value2) {
            this.addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDepositIsNull() {
            this.addCriterion("deposit is null");
            return (Criteria) this;
        }

        public Criteria andDepositIsNotNull() {
            this.addCriterion("deposit is not null");
            return (Criteria) this;
        }

        public Criteria andDepositEqualTo(final BigDecimal value) {
            this.addCriterion("deposit =", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotEqualTo(final BigDecimal value) {
            this.addCriterion("deposit <>", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThan(final BigDecimal value) {
            this.addCriterion("deposit >", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("deposit >=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThan(final BigDecimal value) {
            this.addCriterion("deposit <", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("deposit <=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositIn(final List<BigDecimal> values) {
            this.addCriterion("deposit in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotIn(final List<BigDecimal> values) {
            this.addCriterion("deposit not in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("deposit between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("deposit not between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andTokenNumberIsNull() {
            this.addCriterion("token_number is null");
            return (Criteria) this;
        }

        public Criteria andTokenNumberIsNotNull() {
            this.addCriterion("token_number is not null");
            return (Criteria) this;
        }

        public Criteria andTokenNumberEqualTo(final BigDecimal value) {
            this.addCriterion("token_number =", value, "tokenNumber");
            return (Criteria) this;
        }

        public Criteria andTokenNumberNotEqualTo(final BigDecimal value) {
            this.addCriterion("token_number <>", value, "tokenNumber");
            return (Criteria) this;
        }

        public Criteria andTokenNumberGreaterThan(final BigDecimal value) {
            this.addCriterion("token_number >", value, "tokenNumber");
            return (Criteria) this;
        }

        public Criteria andTokenNumberGreaterThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("token_number >=", value, "tokenNumber");
            return (Criteria) this;
        }

        public Criteria andTokenNumberLessThan(final BigDecimal value) {
            this.addCriterion("token_number <", value, "tokenNumber");
            return (Criteria) this;
        }

        public Criteria andTokenNumberLessThanOrEqualTo(final BigDecimal value) {
            this.addCriterion("token_number <=", value, "tokenNumber");
            return (Criteria) this;
        }

        public Criteria andTokenNumberIn(final List<BigDecimal> values) {
            this.addCriterion("token_number in", values, "tokenNumber");
            return (Criteria) this;
        }

        public Criteria andTokenNumberNotIn(final List<BigDecimal> values) {
            this.addCriterion("token_number not in", values, "tokenNumber");
            return (Criteria) this;
        }

        public Criteria andTokenNumberBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("token_number between", value1, value2, "tokenNumber");
            return (Criteria) this;
        }

        public Criteria andTokenNumberNotBetween(final BigDecimal value1, final BigDecimal value2) {
            this.addCriterion("token_number not between", value1, value2, "tokenNumber");
            return (Criteria) this;
        }

        public Criteria andTokenUrlIsNull() {
            this.addCriterion("token_url is null");
            return (Criteria) this;
        }

        public Criteria andTokenUrlIsNotNull() {
            this.addCriterion("token_url is not null");
            return (Criteria) this;
        }

        public Criteria andTokenUrlEqualTo(final String value) {
            this.addCriterion("token_url =", value, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlNotEqualTo(final String value) {
            this.addCriterion("token_url <>", value, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlGreaterThan(final String value) {
            this.addCriterion("token_url >", value, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlGreaterThanOrEqualTo(final String value) {
            this.addCriterion("token_url >=", value, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlLessThan(final String value) {
            this.addCriterion("token_url <", value, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlLessThanOrEqualTo(final String value) {
            this.addCriterion("token_url <=", value, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlLike(final String value) {
            this.addCriterion("token_url like", value, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlNotLike(final String value) {
            this.addCriterion("token_url not like", value, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlIn(final List<String> values) {
            this.addCriterion("token_url in", values, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlNotIn(final List<String> values) {
            this.addCriterion("token_url not in", values, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlBetween(final String value1, final String value2) {
            this.addCriterion("token_url between", value1, value2, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andTokenUrlNotBetween(final String value1, final String value2) {
            this.addCriterion("token_url not between", value1, value2, "tokenUrl");
            return (Criteria) this;
        }

        public Criteria andContactsIsNull() {
            this.addCriterion("contacts is null");
            return (Criteria) this;
        }

        public Criteria andContactsIsNotNull() {
            this.addCriterion("contacts is not null");
            return (Criteria) this;
        }

        public Criteria andContactsEqualTo(final String value) {
            this.addCriterion("contacts =", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsNotEqualTo(final String value) {
            this.addCriterion("contacts <>", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsGreaterThan(final String value) {
            this.addCriterion("contacts >", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsGreaterThanOrEqualTo(final String value) {
            this.addCriterion("contacts >=", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsLessThan(final String value) {
            this.addCriterion("contacts <", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsLessThanOrEqualTo(final String value) {
            this.addCriterion("contacts <=", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsLike(final String value) {
            this.addCriterion("contacts like", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsNotLike(final String value) {
            this.addCriterion("contacts not like", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsIn(final List<String> values) {
            this.addCriterion("contacts in", values, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsNotIn(final List<String> values) {
            this.addCriterion("contacts not in", values, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsBetween(final String value1, final String value2) {
            this.addCriterion("contacts between", value1, value2, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsNotBetween(final String value1, final String value2) {
            this.addCriterion("contacts not between", value1, value2, "contacts");
            return (Criteria) this;
        }

        public Criteria andWechatIsNull() {
            this.addCriterion("wechat is null");
            return (Criteria) this;
        }

        public Criteria andWechatIsNotNull() {
            this.addCriterion("wechat is not null");
            return (Criteria) this;
        }

        public Criteria andWechatEqualTo(final String value) {
            this.addCriterion("wechat =", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatNotEqualTo(final String value) {
            this.addCriterion("wechat <>", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatGreaterThan(final String value) {
            this.addCriterion("wechat >", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatGreaterThanOrEqualTo(final String value) {
            this.addCriterion("wechat >=", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatLessThan(final String value) {
            this.addCriterion("wechat <", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatLessThanOrEqualTo(final String value) {
            this.addCriterion("wechat <=", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatLike(final String value) {
            this.addCriterion("wechat like", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatNotLike(final String value) {
            this.addCriterion("wechat not like", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatIn(final List<String> values) {
            this.addCriterion("wechat in", values, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatNotIn(final List<String> values) {
            this.addCriterion("wechat not in", values, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatBetween(final String value1, final String value2) {
            this.addCriterion("wechat between", value1, value2, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatNotBetween(final String value1, final String value2) {
            this.addCriterion("wechat not between", value1, value2, "wechat");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeIsNull() {
            this.addCriterion("online_time is null");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeIsNotNull() {
            this.addCriterion("online_time is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeEqualTo(final Date value) {
            this.addCriterion("online_time =", value, "onlineTime");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeNotEqualTo(final Date value) {
            this.addCriterion("online_time <>", value, "onlineTime");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeGreaterThan(final Date value) {
            this.addCriterion("online_time >", value, "onlineTime");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("online_time >=", value, "onlineTime");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeLessThan(final Date value) {
            this.addCriterion("online_time <", value, "onlineTime");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeLessThanOrEqualTo(final Date value) {
            this.addCriterion("online_time <=", value, "onlineTime");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeIn(final List<Date> values) {
            this.addCriterion("online_time in", values, "onlineTime");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeNotIn(final List<Date> values) {
            this.addCriterion("online_time not in", values, "onlineTime");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeBetween(final Date value1, final Date value2) {
            this.addCriterion("online_time between", value1, value2, "onlineTime");
            return (Criteria) this;
        }

        public Criteria andOnlineTimeNotBetween(final Date value1, final Date value2) {
            this.addCriterion("online_time not between", value1, value2, "onlineTime");
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

        public Criteria andMobileIsNull() {
            this.addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            this.addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(final String value) {
            this.addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(final String value) {
            this.addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(final String value) {
            this.addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(final String value) {
            this.addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(final String value) {
            this.addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(final String value) {
            this.addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(final String value) {
            this.addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(final String value) {
            this.addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(final List<String> values) {
            this.addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(final List<String> values) {
            this.addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(final String value1, final String value2) {
            this.addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(final String value1, final String value2) {
            this.addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andDepositStatusIsNull() {
            this.addCriterion("deposit_status is null");
            return (Criteria) this;
        }

        public Criteria andDepositStatusIsNotNull() {
            this.addCriterion("deposit_status is not null");
            return (Criteria) this;
        }

        public Criteria andDepositStatusEqualTo(final Byte value) {
            this.addCriterion("deposit_status =", value, "depositStatus");
            return (Criteria) this;
        }

        public Criteria andDepositStatusNotEqualTo(final Byte value) {
            this.addCriterion("deposit_status <>", value, "depositStatus");
            return (Criteria) this;
        }

        public Criteria andDepositStatusGreaterThan(final Byte value) {
            this.addCriterion("deposit_status >", value, "depositStatus");
            return (Criteria) this;
        }

        public Criteria andDepositStatusGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("deposit_status >=", value, "depositStatus");
            return (Criteria) this;
        }

        public Criteria andDepositStatusLessThan(final Byte value) {
            this.addCriterion("deposit_status <", value, "depositStatus");
            return (Criteria) this;
        }

        public Criteria andDepositStatusLessThanOrEqualTo(final Byte value) {
            this.addCriterion("deposit_status <=", value, "depositStatus");
            return (Criteria) this;
        }

        public Criteria andDepositStatusIn(final List<Byte> values) {
            this.addCriterion("deposit_status in", values, "depositStatus");
            return (Criteria) this;
        }

        public Criteria andDepositStatusNotIn(final List<Byte> values) {
            this.addCriterion("deposit_status not in", values, "depositStatus");
            return (Criteria) this;
        }

        public Criteria andDepositStatusBetween(final Byte value1, final Byte value2) {
            this.addCriterion("deposit_status between", value1, value2, "depositStatus");
            return (Criteria) this;
        }

        public Criteria andDepositStatusNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("deposit_status not between", value1, value2, "depositStatus");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusIsNull() {
            this.addCriterion("sweets_status is null");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusIsNotNull() {
            this.addCriterion("sweets_status is not null");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusEqualTo(final Byte value) {
            this.addCriterion("sweets_status =", value, "sweetsStatus");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusNotEqualTo(final Byte value) {
            this.addCriterion("sweets_status <>", value, "sweetsStatus");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusGreaterThan(final Byte value) {
            this.addCriterion("sweets_status >", value, "sweetsStatus");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusGreaterThanOrEqualTo(final Byte value) {
            this.addCriterion("sweets_status >=", value, "sweetsStatus");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusLessThan(final Byte value) {
            this.addCriterion("sweets_status <", value, "sweetsStatus");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusLessThanOrEqualTo(final Byte value) {
            this.addCriterion("sweets_status <=", value, "sweetsStatus");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusIn(final List<Byte> values) {
            this.addCriterion("sweets_status in", values, "sweetsStatus");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusNotIn(final List<Byte> values) {
            this.addCriterion("sweets_status not in", values, "sweetsStatus");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusBetween(final Byte value1, final Byte value2) {
            this.addCriterion("sweets_status between", value1, value2, "sweetsStatus");
            return (Criteria) this;
        }

        public Criteria andSweetsStatusNotBetween(final Byte value1, final Byte value2) {
            this.addCriterion("sweets_status not between", value1, value2, "sweetsStatus");
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