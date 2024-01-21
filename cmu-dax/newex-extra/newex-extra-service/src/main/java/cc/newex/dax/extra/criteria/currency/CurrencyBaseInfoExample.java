package cc.newex.dax.extra.criteria.currency;

import cc.newex.dax.extra.domain.currency.CurrencyBaseInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 全球数字货币基本信息表 查询条件类
 *
 * @author mbg.generated
 * @date 2018-08-27 16:50:56
 */
public class CurrencyBaseInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CurrencyBaseInfoExample() {
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

    public static CurrencyBaseInfoExample toExample(final CurrencyBaseInfo currencyInfo) {
        if (currencyInfo == null) {
            return null;
        }

        final CurrencyBaseInfoExample example = new CurrencyBaseInfoExample();
        final CurrencyBaseInfoExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(currencyInfo.getId())) {
            criteria.andIdEqualTo(currencyInfo.getId());
        }

        if (StringUtils.isNotBlank(currencyInfo.getCode())) {
            criteria.andCodeLike(currencyInfo.getCode());
        }

        if (StringUtils.isNotBlank(currencyInfo.getName())) {
            criteria.andNameLike(currencyInfo.getName());
        }

        if (StringUtils.isNotBlank(currencyInfo.getSymbol())) {
            criteria.andSymbolEqualTo(currencyInfo.getSymbol());
        }

        if (StringUtils.isNotBlank(currencyInfo.getIssueDate())) {
            criteria.andIssueDateEqualTo(currencyInfo.getIssueDate());
        }

        if (Objects.nonNull(currencyInfo.getMaxSupply())) {
            criteria.andMaxSupplyEqualTo(currencyInfo.getMaxSupply());
        }

        if (Objects.nonNull(currencyInfo.getCirculatingSupply())) {
            criteria.andCirculatingSupplyEqualTo(currencyInfo.getCirculatingSupply());
        }

        if (StringUtils.isNotBlank(currencyInfo.getOfficalWebsite())) {
            criteria.andOfficalWebsiteLike(currencyInfo.getOfficalWebsite());
        }

        if (StringUtils.isNotBlank(currencyInfo.getExplorer())) {
            criteria.andExplorerLike(currencyInfo.getExplorer());
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
            this.addCriterion("code like", "%" + value + "%", "code");
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

        public Criteria andSymbolIsNull() {
            this.addCriterion("symbol is null");
            return (Criteria) this;
        }

        public Criteria andSymbolIsNotNull() {
            this.addCriterion("symbol is not null");
            return (Criteria) this;
        }

        public Criteria andSymbolEqualTo(final String value) {
            this.addCriterion("symbol =", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotEqualTo(final String value) {
            this.addCriterion("symbol <>", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThan(final String value) {
            this.addCriterion("symbol >", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThanOrEqualTo(final String value) {
            this.addCriterion("symbol >=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThan(final String value) {
            this.addCriterion("symbol <", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThanOrEqualTo(final String value) {
            this.addCriterion("symbol <=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLike(final String value) {
            this.addCriterion("symbol like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotLike(final String value) {
            this.addCriterion("symbol not like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolIn(final List<String> values) {
            this.addCriterion("symbol in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotIn(final List<String> values) {
            this.addCriterion("symbol not in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolBetween(final String value1, final String value2) {
            this.addCriterion("symbol between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotBetween(final String value1, final String value2) {
            this.addCriterion("symbol not between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andImgUrlIsNull() {
            this.addCriterion("img_url is null");
            return (Criteria) this;
        }

        public Criteria andImgUrlIsNotNull() {
            this.addCriterion("img_url is not null");
            return (Criteria) this;
        }

        public Criteria andImgUrlEqualTo(final String value) {
            this.addCriterion("img_url =", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotEqualTo(final String value) {
            this.addCriterion("img_url <>", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlGreaterThan(final String value) {
            this.addCriterion("img_url >", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlGreaterThanOrEqualTo(final String value) {
            this.addCriterion("img_url >=", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLessThan(final String value) {
            this.addCriterion("img_url <", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLessThanOrEqualTo(final String value) {
            this.addCriterion("img_url <=", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLike(final String value) {
            this.addCriterion("img_url like", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotLike(final String value) {
            this.addCriterion("img_url not like", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlIn(final List<String> values) {
            this.addCriterion("img_url in", values, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotIn(final List<String> values) {
            this.addCriterion("img_url not in", values, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlBetween(final String value1, final String value2) {
            this.addCriterion("img_url between", value1, value2, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotBetween(final String value1, final String value2) {
            this.addCriterion("img_url not between", value1, value2, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andIssueDateIsNull() {
            this.addCriterion("issue_date is null");
            return (Criteria) this;
        }

        public Criteria andIssueDateIsNotNull() {
            this.addCriterion("issue_date is not null");
            return (Criteria) this;
        }

        public Criteria andIssueDateEqualTo(final String value) {
            this.addCriterion("issue_date =", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateNotEqualTo(final String value) {
            this.addCriterion("issue_date <>", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateGreaterThan(final String value) {
            this.addCriterion("issue_date >", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateGreaterThanOrEqualTo(final String value) {
            this.addCriterion("issue_date >=", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateLessThan(final String value) {
            this.addCriterion("issue_date <", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateLessThanOrEqualTo(final String value) {
            this.addCriterion("issue_date <=", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateLike(final String value) {
            this.addCriterion("issue_date like", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateNotLike(final String value) {
            this.addCriterion("issue_date not like", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateIn(final List<String> values) {
            this.addCriterion("issue_date in", values, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateNotIn(final List<String> values) {
            this.addCriterion("issue_date not in", values, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateBetween(final String value1, final String value2) {
            this.addCriterion("issue_date between", value1, value2, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateNotBetween(final String value1, final String value2) {
            this.addCriterion("issue_date not between", value1, value2, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssuePriceIsNull() {
            this.addCriterion("issue_price is null");
            return (Criteria) this;
        }

        public Criteria andIssuePriceIsNotNull() {
            this.addCriterion("issue_price is not null");
            return (Criteria) this;
        }

        public Criteria andIssuePriceEqualTo(final String value) {
            this.addCriterion("issue_price =", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceNotEqualTo(final String value) {
            this.addCriterion("issue_price <>", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceGreaterThan(final String value) {
            this.addCriterion("issue_price >", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceGreaterThanOrEqualTo(final String value) {
            this.addCriterion("issue_price >=", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceLessThan(final String value) {
            this.addCriterion("issue_price <", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceLessThanOrEqualTo(final String value) {
            this.addCriterion("issue_price <=", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceLike(final String value) {
            this.addCriterion("issue_price like", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceNotLike(final String value) {
            this.addCriterion("issue_price not like", value, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceIn(final List<String> values) {
            this.addCriterion("issue_price in", values, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceNotIn(final List<String> values) {
            this.addCriterion("issue_price not in", values, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceBetween(final String value1, final String value2) {
            this.addCriterion("issue_price between", value1, value2, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andIssuePriceNotBetween(final String value1, final String value2) {
            this.addCriterion("issue_price not between", value1, value2, "issuePrice");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyIsNull() {
            this.addCriterion("max_supply is null");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyIsNotNull() {
            this.addCriterion("max_supply is not null");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyEqualTo(final Long value) {
            this.addCriterion("max_supply =", value, "maxSupply");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyNotEqualTo(final Long value) {
            this.addCriterion("max_supply <>", value, "maxSupply");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyGreaterThan(final Long value) {
            this.addCriterion("max_supply >", value, "maxSupply");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("max_supply >=", value, "maxSupply");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyLessThan(final Long value) {
            this.addCriterion("max_supply <", value, "maxSupply");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyLessThanOrEqualTo(final Long value) {
            this.addCriterion("max_supply <=", value, "maxSupply");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyIn(final List<Long> values) {
            this.addCriterion("max_supply in", values, "maxSupply");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyNotIn(final List<Long> values) {
            this.addCriterion("max_supply not in", values, "maxSupply");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyBetween(final Long value1, final Long value2) {
            this.addCriterion("max_supply between", value1, value2, "maxSupply");
            return (Criteria) this;
        }

        public Criteria andMaxSupplyNotBetween(final Long value1, final Long value2) {
            this.addCriterion("max_supply not between", value1, value2, "maxSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyIsNull() {
            this.addCriterion("circulating_supply is null");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyIsNotNull() {
            this.addCriterion("circulating_supply is not null");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyEqualTo(final Long value) {
            this.addCriterion("circulating_supply =", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyNotEqualTo(final Long value) {
            this.addCriterion("circulating_supply <>", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyGreaterThan(final Long value) {
            this.addCriterion("circulating_supply >", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("circulating_supply >=", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyLessThan(final Long value) {
            this.addCriterion("circulating_supply <", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyLessThanOrEqualTo(final Long value) {
            this.addCriterion("circulating_supply <=", value, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyIn(final List<Long> values) {
            this.addCriterion("circulating_supply in", values, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyNotIn(final List<Long> values) {
            this.addCriterion("circulating_supply not in", values, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyBetween(final Long value1, final Long value2) {
            this.addCriterion("circulating_supply between", value1, value2, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andCirculatingSupplyNotBetween(final Long value1, final Long value2) {
            this.addCriterion("circulating_supply not between", value1, value2, "circulatingSupply");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteIsNull() {
            this.addCriterion("offical_website is null");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteIsNotNull() {
            this.addCriterion("offical_website is not null");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteEqualTo(final String value) {
            this.addCriterion("offical_website =", value, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteNotEqualTo(final String value) {
            this.addCriterion("offical_website <>", value, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteGreaterThan(final String value) {
            this.addCriterion("offical_website >", value, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteGreaterThanOrEqualTo(final String value) {
            this.addCriterion("offical_website >=", value, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteLessThan(final String value) {
            this.addCriterion("offical_website <", value, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteLessThanOrEqualTo(final String value) {
            this.addCriterion("offical_website <=", value, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteLike(final String value) {
            this.addCriterion("offical_website like", "%" + value + "%", "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteNotLike(final String value) {
            this.addCriterion("offical_website not like", value, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteIn(final List<String> values) {
            this.addCriterion("offical_website in", values, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteNotIn(final List<String> values) {
            this.addCriterion("offical_website not in", values, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteBetween(final String value1, final String value2) {
            this.addCriterion("offical_website between", value1, value2, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andOfficalWebsiteNotBetween(final String value1, final String value2) {
            this.addCriterion("offical_website not between", value1, value2, "officalWebsite");
            return (Criteria) this;
        }

        public Criteria andExplorerIsNull() {
            this.addCriterion("explorer is null");
            return (Criteria) this;
        }

        public Criteria andExplorerIsNotNull() {
            this.addCriterion("explorer is not null");
            return (Criteria) this;
        }

        public Criteria andExplorerEqualTo(final String value) {
            this.addCriterion("explorer =", value, "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerNotEqualTo(final String value) {
            this.addCriterion("explorer <>", value, "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerGreaterThan(final String value) {
            this.addCriterion("explorer >", value, "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerGreaterThanOrEqualTo(final String value) {
            this.addCriterion("explorer >=", value, "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerLessThan(final String value) {
            this.addCriterion("explorer <", value, "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerLessThanOrEqualTo(final String value) {
            this.addCriterion("explorer <=", value, "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerLike(final String value) {
            this.addCriterion("explorer like", "%" + value + "%", "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerNotLike(final String value) {
            this.addCriterion("explorer not like", value, "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerIn(final List<String> values) {
            this.addCriterion("explorer in", values, "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerNotIn(final List<String> values) {
            this.addCriterion("explorer not in", values, "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerBetween(final String value1, final String value2) {
            this.addCriterion("explorer between", value1, value2, "explorer");
            return (Criteria) this;
        }

        public Criteria andExplorerNotBetween(final String value1, final String value2) {
            this.addCriterion("explorer not between", value1, value2, "explorer");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlIsNull() {
            this.addCriterion("source_code_url is null");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlIsNotNull() {
            this.addCriterion("source_code_url is not null");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlEqualTo(final String value) {
            this.addCriterion("source_code_url =", value, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlNotEqualTo(final String value) {
            this.addCriterion("source_code_url <>", value, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlGreaterThan(final String value) {
            this.addCriterion("source_code_url >", value, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlGreaterThanOrEqualTo(final String value) {
            this.addCriterion("source_code_url >=", value, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlLessThan(final String value) {
            this.addCriterion("source_code_url <", value, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlLessThanOrEqualTo(final String value) {
            this.addCriterion("source_code_url <=", value, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlLike(final String value) {
            this.addCriterion("source_code_url like", value, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlNotLike(final String value) {
            this.addCriterion("source_code_url not like", value, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlIn(final List<String> values) {
            this.addCriterion("source_code_url in", values, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlNotIn(final List<String> values) {
            this.addCriterion("source_code_url not in", values, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlBetween(final String value1, final String value2) {
            this.addCriterion("source_code_url between", value1, value2, "sourceCodeUrl");
            return (Criteria) this;
        }

        public Criteria andSourceCodeUrlNotBetween(final String value1, final String value2) {
            this.addCriterion("source_code_url not between", value1, value2, "sourceCodeUrl");
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