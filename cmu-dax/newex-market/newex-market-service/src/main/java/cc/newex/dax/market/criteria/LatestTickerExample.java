package cc.newex.dax.market.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询条件example类
 *
 * @author newex-team
 * @date 2018/03/18
 */
public class LatestTickerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LatestTickerExample() {
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

        protected void addCriterion(final String condition, final Object value1, final Object value2,
                                    final String property) {
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

        public Criteria andVolume22IsNull() {
            this.addCriterion("volume_22 is null");
            return (Criteria) this;
        }

        public Criteria andVolume22IsNotNull() {
            this.addCriterion("volume_22 is not null");
            return (Criteria) this;
        }

        public Criteria andVolume22EqualTo(final Double value) {
            this.addCriterion("volume_22 =", value, "volume22");
            return (Criteria) this;
        }

        public Criteria andVolume22NotEqualTo(final Double value) {
            this.addCriterion("volume_22 <>", value, "volume22");
            return (Criteria) this;
        }

        public Criteria andVolume22In(final List<Double> values) {
            this.addCriterion("volume_22 in", values, "volume22");
            return (Criteria) this;
        }

        public Criteria andVolume22NotIn(final List<Double> values) {
            this.addCriterion("volume_22 not in", values, "volume22");
            return (Criteria) this;
        }

        public Criteria andVolume22Between(final Double value1, final Double value2) {
            this.addCriterion("volume_22 between", value1, value2, "volume22");
            return (Criteria) this;
        }

        public Criteria andVolume22NotBetween(final Double value1, final Double value2) {
            this.addCriterion("volume_22 not between", value1, value2, "volume22");
            return (Criteria) this;
        }

        public Criteria andVolume22GreaterThan(final Double value) {
            this.addCriterion("volume_22 >", value, "volume22");
            return (Criteria) this;
        }

        public Criteria andVolume22GreaterThanOrEqualTo(final Double value) {
            this.addCriterion("volume_22 >=", value, "volume22");
            return (Criteria) this;
        }

        public Criteria andVolume22LessThan(final Double value) {
            this.addCriterion("volume_22 <", value, "volume22");
            return (Criteria) this;
        }

        public Criteria andVolume22LessThanOrEqualTo(final Double value) {
            this.addCriterion("volume_22 <=", value, "volume22");
            return (Criteria) this;
        }

        public Criteria andChange24IsNull() {
            this.addCriterion("change_24 is null");
            return (Criteria) this;
        }

        public Criteria andChange24IsNotNull() {
            this.addCriterion("change_24 is not null");
            return (Criteria) this;
        }

        public Criteria andChange24EqualTo(final Double value) {
            this.addCriterion("change_24 =", value, "change24");
            return (Criteria) this;
        }

        public Criteria andChange24NotEqualTo(final Double value) {
            this.addCriterion("change_24 <>", value, "change24");
            return (Criteria) this;
        }

        public Criteria andChange24In(final List<Double> values) {
            this.addCriterion("change_24 in", values, "change24");
            return (Criteria) this;
        }

        public Criteria andChange24NotIn(final List<Double> values) {
            this.addCriterion("change_24 not in", values, "change24");
            return (Criteria) this;
        }

        public Criteria andChange24Between(final Double value1, final Double value2) {
            this.addCriterion("change_24 between", value1, value2, "change24");
            return (Criteria) this;
        }

        public Criteria andChange24NotBetween(final Double value1, final Double value2) {
            this.addCriterion("change_24 not between", value1, value2, "change24");
            return (Criteria) this;
        }

        public Criteria andChange24GreaterThan(final Double value) {
            this.addCriterion("change_24 >", value, "change24");
            return (Criteria) this;
        }

        public Criteria andChange24GreaterThanOrEqualTo(final Double value) {
            this.addCriterion("change_24 >=", value, "change24");
            return (Criteria) this;
        }

        public Criteria andChange24LessThan(final Double value) {
            this.addCriterion("change_24 <", value, "change24");
            return (Criteria) this;
        }

        public Criteria andChange24LessThanOrEqualTo(final Double value) {
            this.addCriterion("change_24 <=", value, "change24");
            return (Criteria) this;
        }

        public Criteria andLastIsNull() {
            this.addCriterion("last is null");
            return (Criteria) this;
        }

        public Criteria andLastIsNotNull() {
            this.addCriterion("last is not null");
            return (Criteria) this;
        }

        public Criteria andLastEqualTo(final Double value) {
            this.addCriterion("last =", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastNotEqualTo(final Double value) {
            this.addCriterion("last <>", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastIn(final List<Double> values) {
            this.addCriterion("last in", values, "last");
            return (Criteria) this;
        }

        public Criteria andLastNotIn(final List<Double> values) {
            this.addCriterion("last not in", values, "last");
            return (Criteria) this;
        }

        public Criteria andLastBetween(final Double value1, final Double value2) {
            this.addCriterion("last between", value1, value2, "last");
            return (Criteria) this;
        }

        public Criteria andLastNotBetween(final Double value1, final Double value2) {
            this.addCriterion("last not between", value1, value2, "last");
            return (Criteria) this;
        }

        public Criteria andLastGreaterThan(final Double value) {
            this.addCriterion("last >", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("last >=", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastLessThan(final Double value) {
            this.addCriterion("last <", value, "last");
            return (Criteria) this;
        }

        public Criteria andLastLessThanOrEqualTo(final Double value) {
            this.addCriterion("last <=", value, "last");
            return (Criteria) this;
        }

        public Criteria andBuyIsNull() {
            this.addCriterion("buy is null");
            return (Criteria) this;
        }

        public Criteria andBuyIsNotNull() {
            this.addCriterion("buy is not null");
            return (Criteria) this;
        }

        public Criteria andBuyEqualTo(final Double value) {
            this.addCriterion("buy =", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyNotEqualTo(final Double value) {
            this.addCriterion("buy <>", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyIn(final List<Double> values) {
            this.addCriterion("buy in", values, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyNotIn(final List<Double> values) {
            this.addCriterion("buy not in", values, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyBetween(final Double value1, final Double value2) {
            this.addCriterion("buy between", value1, value2, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyNotBetween(final Double value1, final Double value2) {
            this.addCriterion("buy not between", value1, value2, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyGreaterThan(final Double value) {
            this.addCriterion("buy >", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("buy >=", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyLessThan(final Double value) {
            this.addCriterion("buy <", value, "buy");
            return (Criteria) this;
        }

        public Criteria andBuyLessThanOrEqualTo(final Double value) {
            this.addCriterion("buy <=", value, "buy");
            return (Criteria) this;
        }

        public Criteria andSellIsNull() {
            this.addCriterion("sell is null");
            return (Criteria) this;
        }

        public Criteria andSellIsNotNull() {
            this.addCriterion("sell is not null");
            return (Criteria) this;
        }

        public Criteria andSellEqualTo(final Double value) {
            this.addCriterion("sell =", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellNotEqualTo(final Double value) {
            this.addCriterion("sell <>", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellIn(final List<Double> values) {
            this.addCriterion("sell in", values, "sell");
            return (Criteria) this;
        }

        public Criteria andSellNotIn(final List<Double> values) {
            this.addCriterion("sell not in", values, "sell");
            return (Criteria) this;
        }

        public Criteria andSellBetween(final Double value1, final Double value2) {
            this.addCriterion("sell between", value1, value2, "sell");
            return (Criteria) this;
        }

        public Criteria andSellNotBetween(final Double value1, final Double value2) {
            this.addCriterion("sell not between", value1, value2, "sell");
            return (Criteria) this;
        }

        public Criteria andSellGreaterThan(final Double value) {
            this.addCriterion("sell >", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("sell >=", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellLessThan(final Double value) {
            this.addCriterion("sell <", value, "sell");
            return (Criteria) this;
        }

        public Criteria andSellLessThanOrEqualTo(final Double value) {
            this.addCriterion("sell <=", value, "sell");
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

        public Criteria andOffsetBuyIsNull() {
            this.addCriterion("offset_buy is null");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyIsNotNull() {
            this.addCriterion("offset_buy is not null");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyEqualTo(final Double value) {
            this.addCriterion("offset_buy =", value, "offsetBuy");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyNotEqualTo(final Double value) {
            this.addCriterion("offset_buy <>", value, "offsetBuy");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyIn(final List<Double> values) {
            this.addCriterion("offset_buy in", values, "offsetBuy");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyNotIn(final List<Double> values) {
            this.addCriterion("offset_buy not in", values, "offsetBuy");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyBetween(final Double value1, final Double value2) {
            this.addCriterion("offset_buy between", value1, value2, "offsetBuy");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyNotBetween(final Double value1, final Double value2) {
            this.addCriterion("offset_buy not between", value1, value2, "offsetBuy");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyGreaterThan(final Double value) {
            this.addCriterion("offset_buy >", value, "offsetBuy");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("offset_buy >=", value, "offsetBuy");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyLessThan(final Double value) {
            this.addCriterion("offset_buy <", value, "offsetBuy");
            return (Criteria) this;
        }

        public Criteria andOffsetBuyLessThanOrEqualTo(final Double value) {
            this.addCriterion("offset_buy <=", value, "offsetBuy");
            return (Criteria) this;
        }

        public Criteria andOffsetSellIsNull() {
            this.addCriterion("offset_sell is null");
            return (Criteria) this;
        }

        public Criteria andOffsetSellIsNotNull() {
            this.addCriterion("offset_sell is not null");
            return (Criteria) this;
        }

        public Criteria andOffsetSellEqualTo(final Double value) {
            this.addCriterion("offset_sell =", value, "offsetSell");
            return (Criteria) this;
        }

        public Criteria andOffsetSellNotEqualTo(final Double value) {
            this.addCriterion("offset_sell <>", value, "offsetSell");
            return (Criteria) this;
        }

        public Criteria andOffsetSellIn(final List<Double> values) {
            this.addCriterion("offset_sell in", values, "offsetSell");
            return (Criteria) this;
        }

        public Criteria andOffsetSellNotIn(final List<Double> values) {
            this.addCriterion("offset_sell not in", values, "offsetSell");
            return (Criteria) this;
        }

        public Criteria andOffsetSellBetween(final Double value1, final Double value2) {
            this.addCriterion("offset_sell between", value1, value2, "offsetSell");
            return (Criteria) this;
        }

        public Criteria andOffsetSellNotBetween(final Double value1, final Double value2) {
            this.addCriterion("offset_sell not between", value1, value2, "offsetSell");
            return (Criteria) this;
        }

        public Criteria andOffsetSellGreaterThan(final Double value) {
            this.addCriterion("offset_sell >", value, "offsetSell");
            return (Criteria) this;
        }

        public Criteria andOffsetSellGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("offset_sell >=", value, "offsetSell");
            return (Criteria) this;
        }

        public Criteria andOffsetSellLessThan(final Double value) {
            this.addCriterion("offset_sell <", value, "offsetSell");
            return (Criteria) this;
        }

        public Criteria andOffsetSellLessThanOrEqualTo(final Double value) {
            this.addCriterion("offset_sell <=", value, "offsetSell");
            return (Criteria) this;
        }

        public Criteria andAutoIsNull() {
            this.addCriterion("auto is null");
            return (Criteria) this;
        }

        public Criteria andAutoIsNotNull() {
            this.addCriterion("auto is not null");
            return (Criteria) this;
        }

        public Criteria andAutoEqualTo(final Integer value) {
            this.addCriterion("auto =", value, "auto");
            return (Criteria) this;
        }

        public Criteria andAutoNotEqualTo(final Integer value) {
            this.addCriterion("auto <>", value, "auto");
            return (Criteria) this;
        }

        public Criteria andAutoIn(final List<Integer> values) {
            this.addCriterion("auto in", values, "auto");
            return (Criteria) this;
        }

        public Criteria andAutoNotIn(final List<Integer> values) {
            this.addCriterion("auto not in", values, "auto");
            return (Criteria) this;
        }

        public Criteria andAutoBetween(final Integer value1, final Integer value2) {
            this.addCriterion("auto between", value1, value2, "auto");
            return (Criteria) this;
        }

        public Criteria andAutoNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("auto not between", value1, value2, "auto");
            return (Criteria) this;
        }

        public Criteria andAutoGreaterThan(final Integer value) {
            this.addCriterion("auto >", value, "auto");
            return (Criteria) this;
        }

        public Criteria andAutoGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("auto >=", value, "auto");
            return (Criteria) this;
        }

        public Criteria andAutoLessThan(final Integer value) {
            this.addCriterion("auto <", value, "auto");
            return (Criteria) this;
        }

        public Criteria andAutoLessThanOrEqualTo(final Integer value) {
            this.addCriterion("auto <=", value, "auto");
            return (Criteria) this;
        }

        public Criteria andDelayIsNull() {
            this.addCriterion("delay is null");
            return (Criteria) this;
        }

        public Criteria andDelayIsNotNull() {
            this.addCriterion("delay is not null");
            return (Criteria) this;
        }

        public Criteria andDelayEqualTo(final Integer value) {
            this.addCriterion("delay =", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayNotEqualTo(final Integer value) {
            this.addCriterion("delay <>", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayIn(final List<Integer> values) {
            this.addCriterion("delay in", values, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayNotIn(final List<Integer> values) {
            this.addCriterion("delay not in", values, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayBetween(final Integer value1, final Integer value2) {
            this.addCriterion("delay between", value1, value2, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("delay not between", value1, value2, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayGreaterThan(final Integer value) {
            this.addCriterion("delay >", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("delay >=", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayLessThan(final Integer value) {
            this.addCriterion("delay <", value, "delay");
            return (Criteria) this;
        }

        public Criteria andDelayLessThanOrEqualTo(final Integer value) {
            this.addCriterion("delay <=", value, "delay");
            return (Criteria) this;
        }

        public Criteria andOrderIndexIsNull() {
            this.addCriterion("order_index is null");
            return (Criteria) this;
        }

        public Criteria andOrderIndexIsNotNull() {
            this.addCriterion("order_index is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIndexEqualTo(final Integer value) {
            this.addCriterion("order_index =", value, "orderIndex");
            return (Criteria) this;
        }

        public Criteria andOrderIndexNotEqualTo(final Integer value) {
            this.addCriterion("order_index <>", value, "orderIndex");
            return (Criteria) this;
        }

        public Criteria andOrderIndexIn(final List<Integer> values) {
            this.addCriterion("order_index in", values, "orderIndex");
            return (Criteria) this;
        }

        public Criteria andOrderIndexNotIn(final List<Integer> values) {
            this.addCriterion("order_index not in", values, "orderIndex");
            return (Criteria) this;
        }

        public Criteria andOrderIndexBetween(final Integer value1, final Integer value2) {
            this.addCriterion("order_index between", value1, value2, "orderIndex");
            return (Criteria) this;
        }

        public Criteria andOrderIndexNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("order_index not between", value1, value2, "orderIndex");
            return (Criteria) this;
        }

        public Criteria andOrderIndexGreaterThan(final Integer value) {
            this.addCriterion("order_index >", value, "orderIndex");
            return (Criteria) this;
        }

        public Criteria andOrderIndexGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("order_index >=", value, "orderIndex");
            return (Criteria) this;
        }

        public Criteria andOrderIndexLessThan(final Integer value) {
            this.addCriterion("order_index <", value, "orderIndex");
            return (Criteria) this;
        }

        public Criteria andOrderIndexLessThanOrEqualTo(final Integer value) {
            this.addCriterion("order_index <=", value, "orderIndex");
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

        public Criteria andNameLike(final String value) {
            this.addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(final String value) {
            this.addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andMoneytypeIsNull() {
            this.addCriterion("moneytype is null");
            return (Criteria) this;
        }

        public Criteria andMoneytypeIsNotNull() {
            this.addCriterion("moneytype is not null");
            return (Criteria) this;
        }

        public Criteria andMoneytypeEqualTo(final Integer value) {
            this.addCriterion("moneytype =", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeNotEqualTo(final Integer value) {
            this.addCriterion("moneytype <>", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeIn(final List<Integer> values) {
            this.addCriterion("moneytype in", values, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeNotIn(final List<Integer> values) {
            this.addCriterion("moneytype not in", values, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("moneytype between", value1, value2, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("moneytype not between", value1, value2, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeGreaterThan(final Integer value) {
            this.addCriterion("moneytype >", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("moneytype >=", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeLessThan(final Integer value) {
            this.addCriterion("moneytype <", value, "moneytype");
            return (Criteria) this;
        }

        public Criteria andMoneytypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("moneytype <=", value, "moneytype");
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

        public Criteria andValidEqualTo(final int value) {
            this.addCriterion("valid =", value, "valid");
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

        protected Criterion(final String condition, final Object value, final Object secondValue,
                            final String typeHandler) {
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