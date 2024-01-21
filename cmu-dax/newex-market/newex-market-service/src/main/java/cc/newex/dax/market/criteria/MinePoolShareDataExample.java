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
public class MinePoolShareDataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MinePoolShareDataExample() {
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

        public Criteria andPoolModeIsNull() {
            this.addCriterion("pool_mode is null");
            return (Criteria) this;
        }

        public Criteria andPoolModeIsNotNull() {
            this.addCriterion("pool_mode is not null");
            return (Criteria) this;
        }

        public Criteria andPoolModeEqualTo(final String value) {
            this.addCriterion("pool_mode =", value, "poolMode");
            return (Criteria) this;
        }

        public Criteria andPoolModeNotEqualTo(final String value) {
            this.addCriterion("pool_mode <>", value, "poolMode");
            return (Criteria) this;
        }

        public Criteria andPoolModeIn(final List<String> values) {
            this.addCriterion("pool_mode in", values, "poolMode");
            return (Criteria) this;
        }

        public Criteria andPoolModeNotIn(final List<String> values) {
            this.addCriterion("pool_mode not in", values, "poolMode");
            return (Criteria) this;
        }

        public Criteria andPoolModeBetween(final String value1, final String value2) {
            this.addCriterion("pool_mode between", value1, value2, "poolMode");
            return (Criteria) this;
        }

        public Criteria andPoolModeNotBetween(final String value1, final String value2) {
            this.addCriterion("pool_mode not between", value1, value2, "poolMode");
            return (Criteria) this;
        }

        public Criteria andPoolModeLike(final String value) {
            this.addCriterion("pool_mode like", value, "poolMode");
            return (Criteria) this;
        }

        public Criteria andPoolModeNotLike(final String value) {
            this.addCriterion("pool_mode not like", value, "poolMode");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolIsNull() {
            this.addCriterion("market_share_of_pool is null");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolIsNotNull() {
            this.addCriterion("market_share_of_pool is not null");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolEqualTo(final Double value) {
            this.addCriterion("market_share_of_pool =", value, "marketShareOfPool");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolNotEqualTo(final Double value) {
            this.addCriterion("market_share_of_pool <>", value, "marketShareOfPool");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolIn(final List<Double> values) {
            this.addCriterion("market_share_of_pool in", values, "marketShareOfPool");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolNotIn(final List<Double> values) {
            this.addCriterion("market_share_of_pool not in", values, "marketShareOfPool");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolBetween(final Double value1, final Double value2) {
            this.addCriterion("market_share_of_pool between", value1, value2, "marketShareOfPool");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolNotBetween(final Double value1, final Double value2) {
            this.addCriterion("market_share_of_pool not between", value1, value2, "marketShareOfPool");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolGreaterThan(final Double value) {
            this.addCriterion("market_share_of_pool >", value, "marketShareOfPool");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("market_share_of_pool >=", value, "marketShareOfPool");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolLessThan(final Double value) {
            this.addCriterion("market_share_of_pool <", value, "marketShareOfPool");
            return (Criteria) this;
        }

        public Criteria andMarketShareOfPoolLessThanOrEqualTo(final Double value) {
            this.addCriterion("market_share_of_pool <=", value, "marketShareOfPool");
            return (Criteria) this;
        }

        public Criteria andBlockAmountIsNull() {
            this.addCriterion("block_amount is null");
            return (Criteria) this;
        }

        public Criteria andBlockAmountIsNotNull() {
            this.addCriterion("block_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBlockAmountEqualTo(final Integer value) {
            this.addCriterion("block_amount =", value, "blockAmount");
            return (Criteria) this;
        }

        public Criteria andBlockAmountNotEqualTo(final Integer value) {
            this.addCriterion("block_amount <>", value, "blockAmount");
            return (Criteria) this;
        }

        public Criteria andBlockAmountIn(final List<Integer> values) {
            this.addCriterion("block_amount in", values, "blockAmount");
            return (Criteria) this;
        }

        public Criteria andBlockAmountNotIn(final List<Integer> values) {
            this.addCriterion("block_amount not in", values, "blockAmount");
            return (Criteria) this;
        }

        public Criteria andBlockAmountBetween(final Integer value1, final Integer value2) {
            this.addCriterion("block_amount between", value1, value2, "blockAmount");
            return (Criteria) this;
        }

        public Criteria andBlockAmountNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("block_amount not between", value1, value2, "blockAmount");
            return (Criteria) this;
        }

        public Criteria andBlockAmountGreaterThan(final Integer value) {
            this.addCriterion("block_amount >", value, "blockAmount");
            return (Criteria) this;
        }

        public Criteria andBlockAmountGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("block_amount >=", value, "blockAmount");
            return (Criteria) this;
        }

        public Criteria andBlockAmountLessThan(final Integer value) {
            this.addCriterion("block_amount <", value, "blockAmount");
            return (Criteria) this;
        }

        public Criteria andBlockAmountLessThanOrEqualTo(final Integer value) {
            this.addCriterion("block_amount <=", value, "blockAmount");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionIsNull() {
            this.addCriterion("empty_block_proportion is null");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionIsNotNull() {
            this.addCriterion("empty_block_proportion is not null");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionEqualTo(final Double value) {
            this.addCriterion("empty_block_proportion =", value, "emptyBlockProportion");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionNotEqualTo(final Double value) {
            this.addCriterion("empty_block_proportion <>", value, "emptyBlockProportion");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionIn(final List<Double> values) {
            this.addCriterion("empty_block_proportion in", values, "emptyBlockProportion");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionNotIn(final List<Double> values) {
            this.addCriterion("empty_block_proportion not in", values, "emptyBlockProportion");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionBetween(final Double value1, final Double value2) {
            this.addCriterion("empty_block_proportion between", value1, value2, "emptyBlockProportion");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionNotBetween(final Double value1, final Double value2) {
            this.addCriterion("empty_block_proportion not between", value1, value2, "emptyBlockProportion");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionGreaterThan(final Double value) {
            this.addCriterion("empty_block_proportion >", value, "emptyBlockProportion");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("empty_block_proportion >=", value, "emptyBlockProportion");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionLessThan(final Double value) {
            this.addCriterion("empty_block_proportion <", value, "emptyBlockProportion");
            return (Criteria) this;
        }

        public Criteria andEmptyBlockProportionLessThanOrEqualTo(final Double value) {
            this.addCriterion("empty_block_proportion <=", value, "emptyBlockProportion");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeIsNull() {
            this.addCriterion("avg_block_size is null");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeIsNotNull() {
            this.addCriterion("avg_block_size is not null");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeEqualTo(final Long value) {
            this.addCriterion("avg_block_size =", value, "avgBlockSize");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeNotEqualTo(final Long value) {
            this.addCriterion("avg_block_size <>", value, "avgBlockSize");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeIn(final List<Long> values) {
            this.addCriterion("avg_block_size in", values, "avgBlockSize");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeNotIn(final List<Long> values) {
            this.addCriterion("avg_block_size not in", values, "avgBlockSize");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeBetween(final Long value1, final Long value2) {
            this.addCriterion("avg_block_size between", value1, value2, "avgBlockSize");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeNotBetween(final Long value1, final Long value2) {
            this.addCriterion("avg_block_size not between", value1, value2, "avgBlockSize");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeGreaterThan(final Long value) {
            this.addCriterion("avg_block_size >", value, "avgBlockSize");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeGreaterThanOrEqualTo(final Long value) {
            this.addCriterion("avg_block_size >=", value, "avgBlockSize");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeLessThan(final Long value) {
            this.addCriterion("avg_block_size <", value, "avgBlockSize");
            return (Criteria) this;
        }

        public Criteria andAvgBlockSizeLessThanOrEqualTo(final Long value) {
            this.addCriterion("avg_block_size <=", value, "avgBlockSize");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeIsNull() {
            this.addCriterion("avg_block_miner_fee is null");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeIsNotNull() {
            this.addCriterion("avg_block_miner_fee is not null");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeEqualTo(final Double value) {
            this.addCriterion("avg_block_miner_fee =", value, "avgBlockMinerFee");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeNotEqualTo(final Double value) {
            this.addCriterion("avg_block_miner_fee <>", value, "avgBlockMinerFee");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeIn(final List<Double> values) {
            this.addCriterion("avg_block_miner_fee in", values, "avgBlockMinerFee");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeNotIn(final List<Double> values) {
            this.addCriterion("avg_block_miner_fee not in", values, "avgBlockMinerFee");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeBetween(final Double value1, final Double value2) {
            this.addCriterion("avg_block_miner_fee between", value1, value2, "avgBlockMinerFee");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeNotBetween(final Double value1, final Double value2) {
            this.addCriterion("avg_block_miner_fee not between", value1, value2, "avgBlockMinerFee");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeGreaterThan(final Double value) {
            this.addCriterion("avg_block_miner_fee >", value, "avgBlockMinerFee");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("avg_block_miner_fee >=", value, "avgBlockMinerFee");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeLessThan(final Double value) {
            this.addCriterion("avg_block_miner_fee <", value, "avgBlockMinerFee");
            return (Criteria) this;
        }

        public Criteria andAvgBlockMinerFeeLessThanOrEqualTo(final Double value) {
            this.addCriterion("avg_block_miner_fee <=", value, "avgBlockMinerFee");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionIsNull() {
            this.addCriterion("miner_fee_and_block_bonus_proportion is null");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionIsNotNull() {
            this.addCriterion("miner_fee_and_block_bonus_proportion is not null");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionEqualTo(final Double value) {
            this.addCriterion("miner_fee_and_block_bonus_proportion =", value, "minerFeeAndBlockBonusProportion");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionNotEqualTo(final Double value) {
            this.addCriterion("miner_fee_and_block_bonus_proportion <>", value, "minerFeeAndBlockBonusProportion");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionIn(final List<Double> values) {
            this.addCriterion("miner_fee_and_block_bonus_proportion in", values, "minerFeeAndBlockBonusProportion");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionNotIn(final List<Double> values) {
            this.addCriterion("miner_fee_and_block_bonus_proportion not in", values, "minerFeeAndBlockBonusProportion");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionBetween(final Double value1, final Double value2) {
            this.addCriterion("miner_fee_and_block_bonus_proportion between", value1, value2,
                    "minerFeeAndBlockBonusProportion");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionNotBetween(final Double value1, final Double value2) {
            this.addCriterion("miner_fee_and_block_bonus_proportion not between", value1, value2,
                    "minerFeeAndBlockBonusProportion");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionGreaterThan(final Double value) {
            this.addCriterion("miner_fee_and_block_bonus_proportion >", value, "minerFeeAndBlockBonusProportion");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionGreaterThanOrEqualTo(final Double value) {
            this.addCriterion("miner_fee_and_block_bonus_proportion >=", value, "minerFeeAndBlockBonusProportion");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionLessThan(final Double value) {
            this.addCriterion("miner_fee_and_block_bonus_proportion <", value, "minerFeeAndBlockBonusProportion");
            return (Criteria) this;
        }

        public Criteria andMinerFeeAndBlockBonusProportionLessThanOrEqualTo(final Double value) {
            this.addCriterion("miner_fee_and_block_bonus_proportion <=", value, "minerFeeAndBlockBonusProportion");
            return (Criteria) this;
        }

        public Criteria andRankIsNull() {
            this.addCriterion("rank is null");
            return (Criteria) this;
        }

        public Criteria andRankIsNotNull() {
            this.addCriterion("rank is not null");
            return (Criteria) this;
        }

        public Criteria andRankEqualTo(final Integer value) {
            this.addCriterion("rank =", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotEqualTo(final Integer value) {
            this.addCriterion("rank <>", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankIn(final List<Integer> values) {
            this.addCriterion("rank in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotIn(final List<Integer> values) {
            this.addCriterion("rank not in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankBetween(final Integer value1, final Integer value2) {
            this.addCriterion("rank between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("rank not between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThan(final Integer value) {
            this.addCriterion("rank >", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("rank >=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThan(final Integer value) {
            this.addCriterion("rank <", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThanOrEqualTo(final Integer value) {
            this.addCriterion("rank <=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andComputingPowerIsNull() {
            this.addCriterion("computing_power is null");
            return (Criteria) this;
        }

        public Criteria andComputingPowerIsNotNull() {
            this.addCriterion("computing_power is not null");
            return (Criteria) this;
        }

        public Criteria andComputingPowerEqualTo(final String value) {
            this.addCriterion("computing_power =", value, "computingPower");
            return (Criteria) this;
        }

        public Criteria andComputingPowerNotEqualTo(final String value) {
            this.addCriterion("computing_power <>", value, "computingPower");
            return (Criteria) this;
        }

        public Criteria andComputingPowerIn(final List<String> values) {
            this.addCriterion("computing_power in", values, "computingPower");
            return (Criteria) this;
        }

        public Criteria andComputingPowerNotIn(final List<String> values) {
            this.addCriterion("computing_power not in", values, "computingPower");
            return (Criteria) this;
        }

        public Criteria andComputingPowerBetween(final String value1, final String value2) {
            this.addCriterion("computing_power between", value1, value2, "computingPower");
            return (Criteria) this;
        }

        public Criteria andComputingPowerNotBetween(final String value1, final String value2) {
            this.addCriterion("computing_power not between", value1, value2, "computingPower");
            return (Criteria) this;
        }

        public Criteria andComputingPowerLike(final String value) {
            this.addCriterion("computing_power like", value, "computingPower");
            return (Criteria) this;
        }

        public Criteria andComputingPowerNotLike(final String value) {
            this.addCriterion("computing_power not like", value, "computingPower");
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