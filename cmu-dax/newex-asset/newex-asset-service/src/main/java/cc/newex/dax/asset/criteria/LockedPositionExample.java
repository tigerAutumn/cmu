package cc.newex.dax.asset.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 锁仓记录表 查询条件类
 *
 * @author newex-team
 * @date 2018-09-17 19:35:04
 */
public class LockedPositionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LockedPositionExample() {
        this.oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public List<Criteria> getOredCriteria() {
        return this.oredCriteria;
    }

    public void or(Criteria criteria) {
        this.oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
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

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
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

        public Criteria andIdEqualTo(Long value) {
            this.addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            this.addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            this.addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
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

        public Criteria andUserIdEqualTo(Long value) {
            this.addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            this.addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            this.addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            this.addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            this.addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            this.addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            this.addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            this.addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            this.addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            this.addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNull() {
            this.addCriterion("currency is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNotNull() {
            this.addCriterion("currency is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyEqualTo(Integer value) {
            this.addCriterion("currency =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(Integer value) {
            this.addCriterion("currency <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(Integer value) {
            this.addCriterion("currency >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("currency >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(Integer value) {
            this.addCriterion("currency <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(Integer value) {
            this.addCriterion("currency <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(List<Integer> values) {
            this.addCriterion("currency in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(List<Integer> values) {
            this.addCriterion("currency not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(Integer value1, Integer value2) {
            this.addCriterion("currency between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(Integer value1, Integer value2) {
            this.addCriterion("currency not between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            this.addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            this.addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            this.addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            this.addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            this.addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            this.addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            this.addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            this.addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andLockAmountIsNull() {
            this.addCriterion("lock_amount is null");
            return (Criteria) this;
        }

        public Criteria andLockAmountIsNotNull() {
            this.addCriterion("lock_amount is not null");
            return (Criteria) this;
        }

        public Criteria andLockAmountEqualTo(BigDecimal value) {
            this.addCriterion("lock_amount =", value, "lockAmount");
            return (Criteria) this;
        }

        public Criteria andLockAmountNotEqualTo(BigDecimal value) {
            this.addCriterion("lock_amount <>", value, "lockAmount");
            return (Criteria) this;
        }

        public Criteria andLockAmountGreaterThan(BigDecimal value) {
            this.addCriterion("lock_amount >", value, "lockAmount");
            return (Criteria) this;
        }

        public Criteria andLockAmountGreaterThanOrEqualTo(BigDecimal value) {
            this.addCriterion("lock_amount >=", value, "lockAmount");
            return (Criteria) this;
        }

        public Criteria andLockAmountLessThan(BigDecimal value) {
            this.addCriterion("lock_amount <", value, "lockAmount");
            return (Criteria) this;
        }

        public Criteria andLockAmountLessThanOrEqualTo(BigDecimal value) {
            this.addCriterion("lock_amount <=", value, "lockAmount");
            return (Criteria) this;
        }

        public Criteria andLockAmountIn(List<BigDecimal> values) {
            this.addCriterion("lock_amount in", values, "lockAmount");
            return (Criteria) this;
        }

        public Criteria andLockAmountNotIn(List<BigDecimal> values) {
            this.addCriterion("lock_amount not in", values, "lockAmount");
            return (Criteria) this;
        }

        public Criteria andLockAmountBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("lock_amount between", value1, value2, "lockAmount");
            return (Criteria) this;
        }

        public Criteria andLockAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            this.addCriterion("lock_amount not between", value1, value2, "lockAmount");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameIsNull() {
            this.addCriterion("lock_position_name is null");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameIsNotNull() {
            this.addCriterion("lock_position_name is not null");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameEqualTo(String value) {
            this.addCriterion("lock_position_name =", value, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameNotEqualTo(String value) {
            this.addCriterion("lock_position_name <>", value, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameGreaterThan(String value) {
            this.addCriterion("lock_position_name >", value, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameGreaterThanOrEqualTo(String value) {
            this.addCriterion("lock_position_name >=", value, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameLessThan(String value) {
            this.addCriterion("lock_position_name <", value, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameLessThanOrEqualTo(String value) {
            this.addCriterion("lock_position_name <=", value, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameLike(String value) {
            this.addCriterion("lock_position_name like", "%" + value + "%", "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameNotLike(String value) {
            this.addCriterion("lock_position_name not like", "%" + value + "%", "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameIn(List<String> values) {
            this.addCriterion("lock_position_name in", values, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameNotIn(List<String> values) {
            this.addCriterion("lock_position_name not in", values, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameBetween(String value1, String value2) {
            this.addCriterion("lock_position_name between", value1, value2, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andLockPositionNameNotBetween(String value1, String value2) {
            this.addCriterion("lock_position_name not between", value1, value2, "lockPositionName");
            return (Criteria) this;
        }

        public Criteria andDividendIsNull() {
            this.addCriterion("dividend is null");
            return (Criteria) this;
        }

        public Criteria andDividendIsNotNull() {
            this.addCriterion("dividend is not null");
            return (Criteria) this;
        }

        public Criteria andDividendEqualTo(Integer value) {
            this.addCriterion("dividend =", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendNotEqualTo(Integer value) {
            this.addCriterion("dividend <>", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendGreaterThan(Integer value) {
            this.addCriterion("dividend >", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("dividend >=", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendLessThan(Integer value) {
            this.addCriterion("dividend <", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendLessThanOrEqualTo(Integer value) {
            this.addCriterion("dividend <=", value, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendIn(List<Integer> values) {
            this.addCriterion("dividend in", values, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendNotIn(List<Integer> values) {
            this.addCriterion("dividend not in", values, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendBetween(Integer value1, Integer value2) {
            this.addCriterion("dividend between", value1, value2, "dividend");
            return (Criteria) this;
        }

        public Criteria andDividendNotBetween(Integer value1, Integer value2) {
            this.addCriterion("dividend not between", value1, value2, "dividend");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIsNull() {
            this.addCriterion("release_date is null");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIsNotNull() {
            this.addCriterion("release_date is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseDateEqualTo(Date value) {
            this.addCriterion("release_date =", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotEqualTo(Date value) {
            this.addCriterion("release_date <>", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateGreaterThan(Date value) {
            this.addCriterion("release_date >", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateGreaterThanOrEqualTo(Date value) {
            this.addCriterion("release_date >=", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateLessThan(Date value) {
            this.addCriterion("release_date <", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateLessThanOrEqualTo(Date value) {
            this.addCriterion("release_date <=", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIn(List<Date> values) {
            this.addCriterion("release_date in", values, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotIn(List<Date> values) {
            this.addCriterion("release_date not in", values, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateBetween(Date value1, Date value2) {
            this.addCriterion("release_date between", value1, value2, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotBetween(Date value1, Date value2) {
            this.addCriterion("release_date not between", value1, value2, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateIsNull() {
            this.addCriterion("next_release_date is null");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateIsNotNull() {
            this.addCriterion("next_release_date is not null");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateEqualTo(Date value) {
            this.addCriterion("next_release_date =", value, "nextReleaseDate");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateNotEqualTo(Date value) {
            this.addCriterion("next_release_date <>", value, "nextReleaseDate");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateGreaterThan(Date value) {
            this.addCriterion("next_release_date >", value, "nextReleaseDate");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateGreaterThanOrEqualTo(Date value) {
            this.addCriterion("next_release_date >=", value, "nextReleaseDate");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateLessThan(Date value) {
            this.addCriterion("next_release_date <", value, "nextReleaseDate");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateLessThanOrEqualTo(Date value) {
            this.addCriterion("next_release_date <=", value, "nextReleaseDate");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateIn(List<Date> values) {
            this.addCriterion("next_release_date in", values, "nextReleaseDate");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateNotIn(List<Date> values) {
            this.addCriterion("next_release_date not in", values, "nextReleaseDate");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateBetween(Date value1, Date value2) {
            this.addCriterion("next_release_date between", value1, value2, "nextReleaseDate");
            return (Criteria) this;
        }

        public Criteria andNextReleaseDateNotBetween(Date value1, Date value2) {
            this.addCriterion("next_release_date not between", value1, value2, "nextReleaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseContentIsNull() {
            this.addCriterion("release_content is null");
            return (Criteria) this;
        }

        public Criteria andReleaseContentIsNotNull() {
            this.addCriterion("release_content is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseContentEqualTo(String value) {
            this.addCriterion("release_content =", value, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentNotEqualTo(String value) {
            this.addCriterion("release_content <>", value, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentGreaterThan(String value) {
            this.addCriterion("release_content >", value, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentGreaterThanOrEqualTo(String value) {
            this.addCriterion("release_content >=", value, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentLessThan(String value) {
            this.addCriterion("release_content <", value, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentLessThanOrEqualTo(String value) {
            this.addCriterion("release_content <=", value, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentLike(String value) {
            this.addCriterion("release_content like", value, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentNotLike(String value) {
            this.addCriterion("release_content not like", value, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentIn(List<String> values) {
            this.addCriterion("release_content in", values, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentNotIn(List<String> values) {
            this.addCriterion("release_content not in", values, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentBetween(String value1, String value2) {
            this.addCriterion("release_content between", value1, value2, "releaseContent");
            return (Criteria) this;
        }

        public Criteria andReleaseContentNotBetween(String value1, String value2) {
            this.addCriterion("release_content not between", value1, value2, "releaseContent");
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

        public Criteria andStatusEqualTo(Integer value) {
            this.addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            this.addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            this.addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            this.addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            this.addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            this.addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            this.addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            this.addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            this.addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            this.addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            this.addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            this.addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            this.addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            this.addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            this.addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            this.addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            this.addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            this.addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            this.addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            this.addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            this.addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            this.addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            this.addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            this.addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            this.addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            this.addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            this.addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            this.addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            this.addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            this.addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            this.addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            this.addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            this.addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            this.addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            this.addCriterion("create_date not between", value1, value2, "createDate");
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

        public Criteria andUpdateDateEqualTo(Date value) {
            this.addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            this.addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            this.addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            this.addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            this.addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            this.addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            this.addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            this.addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            this.addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
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

        public Criteria andBrokerIdEqualTo(Integer value) {
            this.addCriterion("broker_id =", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotEqualTo(Integer value) {
            this.addCriterion("broker_id <>", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThan(Integer value) {
            this.addCriterion("broker_id >", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThanOrEqualTo(Integer value) {
            this.addCriterion("broker_id >=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThan(Integer value) {
            this.addCriterion("broker_id <", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThanOrEqualTo(Integer value) {
            this.addCriterion("broker_id <=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIn(List<Integer> values) {
            this.addCriterion("broker_id in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotIn(List<Integer> values) {
            this.addCriterion("broker_id not in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdBetween(Integer value1, Integer value2) {
            this.addCriterion("broker_id between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotBetween(Integer value1, Integer value2) {
            this.addCriterion("broker_id not between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andFieldIsNull(String fieldName) {
            this.addCriterion(fieldName + " is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull(String fieldName) {
            this.addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " =", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " <>", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " >", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " >=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " <", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " <=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLike(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(String fieldName, Object fieldValue) {
            this.addCriterion(fieldName + " not like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldIn(String fieldName, List<Object> fieldValues) {
            this.addCriterion(fieldName + " in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(String fieldName, List<Object> fieldValues) {
            this.addCriterion(fieldName + " not in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldBetween(String fieldName, Object fieldValue1, Object fieldValue2) {
            this.addCriterion(fieldName + " between", fieldValue1, fieldValue2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(String fieldName, Object fieldValue1, Object fieldValue2) {
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
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

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