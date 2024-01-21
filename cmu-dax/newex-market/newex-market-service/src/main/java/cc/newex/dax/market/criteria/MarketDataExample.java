package cc.newex.dax.market.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询条件example类
 *
 * @author
 * @date 2018/03/18
 */
public class MarketDataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MarketDataExample() {
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

        public Criteria andMarketFromIsNull() {
            this.addCriterion("market_from is null");
            return (Criteria) this;
        }

        public Criteria andMarketFromIsNotNull() {
            this.addCriterion("market_from is not null");
            return (Criteria) this;
        }

        public Criteria andMarketFromEqualTo(final Integer value) {
            this.addCriterion("market_from =", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotEqualTo(final Integer value) {
            this.addCriterion("market_from <>", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromIn(final List<Integer> values) {
            this.addCriterion("market_from in", values, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotIn(final List<Integer> values) {
            this.addCriterion("market_from not in", values, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromBetween(final Integer value1, final Integer value2) {
            this.addCriterion("market_from between", value1, value2, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("market_from not between", value1, value2, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromGreaterThan(final Integer value) {
            this.addCriterion("market_from >", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("market_from >=", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromLessThan(final Integer value) {
            this.addCriterion("market_from <", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andMarketFromLessThanOrEqualTo(final Integer value) {
            this.addCriterion("market_from <=", value, "marketFrom");
            return (Criteria) this;
        }

        public Criteria andOpenIsNull() {
            this.addCriterion("open is null");
            return (Criteria) this;
        }

        public Criteria andOpenIsNotNull() {
            this.addCriterion("open is not null");
            return (Criteria) this;
        }

        public Criteria andOpenEqualTo(final Double value) {
            this.addCriterion("open =", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenNotEqualTo(final Double value) {
            this.addCriterion("open <>", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenIn(final List<Double> values) {
            this.addCriterion("open in", values, "open");
            return (Criteria) this;
        }

        public Criteria andOpenNotIn(final List<Double> values) {
            this.addCriterion("open not in", values, "open");
            return (Criteria) this;
        }

        public Criteria andOpenBetween(final Double value1, final Double value2) {
            this.addCriterion("open between", value1, value2, "open");
            return (Criteria) this;
        }

        public Criteria andOpenNotBetween(final Double value1, final Double value2) {
            this.addCriterion("open not between", value1, value2, "open");
            return (Criteria) this;
        }

        public Criteria andOpenGreaterThan(final Double value) {
            this.addCriterion("open >", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("open >=", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenLessThan(final Double value) {
            this.addCriterion("open <", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenLessThanOrEqualTo(final Double value) {
            this.addCriterion("open <=", value, "open");
            return (Criteria) this;
        }

        public Criteria andHighIsNull() {
            this.addCriterion("high is null");
            return (Criteria) this;
        }

        public Criteria andHighIsNotNull() {
            this.addCriterion("high is not null");
            return (Criteria) this;
        }

        public Criteria andHighEqualTo(final Double value) {
            this.addCriterion("high =", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighNotEqualTo(final Double value) {
            this.addCriterion("high <>", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighIn(final List<Double> values) {
            this.addCriterion("high in", values, "high");
            return (Criteria) this;
        }

        public Criteria andHighNotIn(final List<Double> values) {
            this.addCriterion("high not in", values, "high");
            return (Criteria) this;
        }

        public Criteria andHighBetween(final Double value1, final Double value2) {
            this.addCriterion("high between", value1, value2, "high");
            return (Criteria) this;
        }

        public Criteria andHighNotBetween(final Double value1, final Double value2) {
            this.addCriterion("high not between", value1, value2, "high");
            return (Criteria) this;
        }

        public Criteria andHighGreaterThan(final Double value) {
            this.addCriterion("high >", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("high >=", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighLessThan(final Double value) {
            this.addCriterion("high <", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighLessThanOrEqualTo(final Double value) {
            this.addCriterion("high <=", value, "high");
            return (Criteria) this;
        }

        public Criteria andLowIsNull() {
            this.addCriterion("low is null");
            return (Criteria) this;
        }

        public Criteria andLowIsNotNull() {
            this.addCriterion("low is not null");
            return (Criteria) this;
        }

        public Criteria andLowEqualTo(final Double value) {
            this.addCriterion("low =", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowNotEqualTo(final Double value) {
            this.addCriterion("low <>", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowIn(final List<Double> values) {
            this.addCriterion("low in", values, "low");
            return (Criteria) this;
        }

        public Criteria andLowNotIn(final List<Double> values) {
            this.addCriterion("low not in", values, "low");
            return (Criteria) this;
        }

        public Criteria andLowBetween(final Double value1, final Double value2) {
            this.addCriterion("low between", value1, value2, "low");
            return (Criteria) this;
        }

        public Criteria andLowNotBetween(final Double value1, final Double value2) {
            this.addCriterion("low not between", value1, value2, "low");
            return (Criteria) this;
        }

        public Criteria andLowGreaterThan(final Double value) {
            this.addCriterion("low >", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("low >=", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowLessThan(final Double value) {
            this.addCriterion("low <", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowLessThanOrEqualTo(final Double value) {
            this.addCriterion("low <=", value, "low");
            return (Criteria) this;
        }

        public Criteria andCloseIsNull() {
            this.addCriterion("close is null");
            return (Criteria) this;
        }

        public Criteria andCloseIsNotNull() {
            this.addCriterion("close is not null");
            return (Criteria) this;
        }

        public Criteria andCloseEqualTo(final Double value) {
            this.addCriterion("close =", value, "close");
            return (Criteria) this;
        }

        public Criteria andCloseNotEqualTo(final Double value) {
            this.addCriterion("close <>", value, "close");
            return (Criteria) this;
        }

        public Criteria andCloseIn(final List<Double> values) {
            this.addCriterion("close in", values, "close");
            return (Criteria) this;
        }

        public Criteria andCloseNotIn(final List<Double> values) {
            this.addCriterion("close not in", values, "close");
            return (Criteria) this;
        }

        public Criteria andCloseBetween(final Double value1, final Double value2) {
            this.addCriterion("close between", value1, value2, "close");
            return (Criteria) this;
        }

        public Criteria andCloseNotBetween(final Double value1, final Double value2) {
            this.addCriterion("close not between", value1, value2, "close");
            return (Criteria) this;
        }

        public Criteria andCloseGreaterThan(final Double value) {
            this.addCriterion("close >", value, "close");
            return (Criteria) this;
        }

        public Criteria andCloseGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("close >=", value, "close");
            return (Criteria) this;
        }

        public Criteria andCloseLessThan(final Double value) {
            this.addCriterion("close <", value, "close");
            return (Criteria) this;
        }

        public Criteria andCloseLessThanOrEqualTo(final Double value) {
            this.addCriterion("close <=", value, "close");
            return (Criteria) this;
        }

        public Criteria andVolumeIsNull() {
            this.addCriterion("volume is null");
            return (Criteria) this;
        }

        public Criteria andVolumeIsNotNull() {
            this.addCriterion("volume is not null");
            return (Criteria) this;
        }

        public Criteria andVolumeEqualTo(final Double value) {
            this.addCriterion("volume =", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotEqualTo(final Double value) {
            this.addCriterion("volume <>", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeIn(final List<Double> values) {
            this.addCriterion("volume in", values, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotIn(final List<Double> values) {
            this.addCriterion("volume not in", values, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeBetween(final Double value1, final Double value2) {
            this.addCriterion("volume between", value1, value2, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotBetween(final Double value1, final Double value2) {
            this.addCriterion("volume not between", value1, value2, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeGreaterThan(final Double value) {
            this.addCriterion("volume >", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("volume >=", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLessThan(final Double value) {
            this.addCriterion("volume <", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLessThanOrEqualTo(final Double value) {
            this.addCriterion("volume <=", value, "volume");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeIsNull() {
            this.addCriterion("coin_volume is null");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeIsNotNull() {
            this.addCriterion("coin_volume is not null");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeEqualTo(final Double value) {
            this.addCriterion("coin_volume =", value, "coinVolume");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeNotEqualTo(final Double value) {
            this.addCriterion("coin_volume <>", value, "coinVolume");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeIn(final List<Double> values) {
            this.addCriterion("coin_volume in", values, "coinVolume");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeNotIn(final List<Double> values) {
            this.addCriterion("coin_volume not in", values, "coinVolume");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeBetween(final Double value1, final Double value2) {
            this.addCriterion("coin_volume between", value1, value2, "coinVolume");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeNotBetween(final Double value1, final Double value2) {
            this.addCriterion("coin_volume not between", value1, value2, "coinVolume");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeGreaterThan(final Double value) {
            this.addCriterion("coin_volume >", value, "coinVolume");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("coin_volume >=", value, "coinVolume");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeLessThan(final Double value) {
            this.addCriterion("coin_volume <", value, "coinVolume");
            return (Criteria) this;
        }

        public Criteria andCoinVolumeLessThanOrEqualTo(final Double value) {
            this.addCriterion("coin_volume <=", value, "coinVolume");
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

        public Criteria andStartIdIsNull() {
            this.addCriterion("start_id is null");
            return (Criteria) this;
        }

        public Criteria andStartIdIsNotNull() {
            this.addCriterion("start_id is not null");
            return (Criteria) this;
        }

        public Criteria andStartIdEqualTo(final Long value) {
            this.addCriterion("start_id =", value, "startId");
            return (Criteria) this;
        }

        public Criteria andStartIdNotEqualTo(final Long value) {
            this.addCriterion("start_id <>", value, "startId");
            return (Criteria) this;
        }

        public Criteria andStartIdIn(final List<Long> values) {
            this.addCriterion("start_id in", values, "startId");
            return (Criteria) this;
        }

        public Criteria andStartIdNotIn(final List<Long> values) {
            this.addCriterion("start_id not in", values, "startId");
            return (Criteria) this;
        }

        public Criteria andStartIdBetween(final Long value1, final Long value2) {
            this.addCriterion("start_id between", value1, value2, "startId");
            return (Criteria) this;
        }

        public Criteria andStartIdNotBetween(final Long value1, final Long value2) {
            this.addCriterion("start_id not between", value1, value2, "startId");
            return (Criteria) this;
        }

        public Criteria andStartIdGreaterThan(final Long value) {
            this.addCriterion("start_id >", value, "startId");
            return (Criteria) this;
        }

        public Criteria andStartIdGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("start_id >=", value, "startId");
            return (Criteria) this;
        }

        public Criteria andStartIdLessThan(final Long value) {
            this.addCriterion("start_id <", value, "startId");
            return (Criteria) this;
        }

        public Criteria andStartIdLessThanOrEqualTo(final Long value) {
            this.addCriterion("start_id <=", value, "startId");
            return (Criteria) this;
        }

        public Criteria andEndIdIsNull() {
            this.addCriterion("end_id is null");
            return (Criteria) this;
        }

        public Criteria andEndIdIsNotNull() {
            this.addCriterion("end_id is not null");
            return (Criteria) this;
        }

        public Criteria andEndIdEqualTo(final Long value) {
            this.addCriterion("end_id =", value, "endId");
            return (Criteria) this;
        }

        public Criteria andEndIdNotEqualTo(final Long value) {
            this.addCriterion("end_id <>", value, "endId");
            return (Criteria) this;
        }

        public Criteria andEndIdIn(final List<Long> values) {
            this.addCriterion("end_id in", values, "endId");
            return (Criteria) this;
        }

        public Criteria andEndIdNotIn(final List<Long> values) {
            this.addCriterion("end_id not in", values, "endId");
            return (Criteria) this;
        }

        public Criteria andEndIdBetween(final Long value1, final Long value2) {
            this.addCriterion("end_id between", value1, value2, "endId");
            return (Criteria) this;
        }

        public Criteria andEndIdNotBetween(final Long value1, final Long value2) {
            this.addCriterion("end_id not between", value1, value2, "endId");
            return (Criteria) this;
        }

        public Criteria andEndIdGreaterThan(final Long value) {
            this.addCriterion("end_id >", value, "endId");
            return (Criteria) this;
        }

        public Criteria andEndIdGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("end_id >=", value, "endId");
            return (Criteria) this;
        }

        public Criteria andEndIdLessThan(final Long value) {
            this.addCriterion("end_id <", value, "endId");
            return (Criteria) this;
        }

        public Criteria andEndIdLessThanOrEqualTo(final Long value) {
            this.addCriterion("end_id <=", value, "endId");
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