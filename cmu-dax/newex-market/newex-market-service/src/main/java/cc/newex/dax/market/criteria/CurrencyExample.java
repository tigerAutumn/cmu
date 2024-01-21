package cc.newex.dax.market.criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * 币种表 查询条件example类
 *
 * @author newex-team
 * @date 2018/03/18
 */
public class CurrencyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CurrencyExample() {
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

        public Criteria andIdEqualTo(final Integer value) {
            this.addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(final List<Integer> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(final List<Integer> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
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

        public Criteria andSymbolLike(final String value) {
            this.addCriterion("symbol like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotLike(final String value) {
            this.addCriterion("symbol not like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSignIsNull() {
            this.addCriterion("sign is null");
            return (Criteria) this;
        }

        public Criteria andSignIsNotNull() {
            this.addCriterion("sign is not null");
            return (Criteria) this;
        }

        public Criteria andSignEqualTo(final String value) {
            this.addCriterion("sign =", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotEqualTo(final String value) {
            this.addCriterion("sign <>", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignIn(final List<String> values) {
            this.addCriterion("sign in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotIn(final List<String> values) {
            this.addCriterion("sign not in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignBetween(final String value1, final String value2) {
            this.addCriterion("sign between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotBetween(final String value1, final String value2) {
            this.addCriterion("sign not between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLike(final String value) {
            this.addCriterion("sign like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotLike(final String value) {
            this.addCriterion("sign not like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andWithdrawableIsNull() {
            this.addCriterion("withdrawable is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawableIsNotNull() {
            this.addCriterion("withdrawable is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawableEqualTo(final Integer value) {
            this.addCriterion("withdrawable =", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableNotEqualTo(final Integer value) {
            this.addCriterion("withdrawable <>", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableIn(final List<Integer> values) {
            this.addCriterion("withdrawable in", values, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableNotIn(final List<Integer> values) {
            this.addCriterion("withdrawable not in", values, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableBetween(final Integer value1, final Integer value2) {
            this.addCriterion("withdrawable between", value1, value2, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("withdrawable not between", value1, value2, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableGreaterThan(final Integer value) {
            this.addCriterion("withdrawable >", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("withdrawable >=", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableLessThan(final Integer value) {
            this.addCriterion("withdrawable <", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andWithdrawableLessThanOrEqualTo(final Integer value) {
            this.addCriterion("withdrawable <=", value, "withdrawable");
            return (Criteria) this;
        }

        public Criteria andRechargeableIsNull() {
            this.addCriterion("rechargeable is null");
            return (Criteria) this;
        }

        public Criteria andRechargeableIsNotNull() {
            this.addCriterion("rechargeable is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeableEqualTo(final Integer value) {
            this.addCriterion("rechargeable =", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableNotEqualTo(final Integer value) {
            this.addCriterion("rechargeable <>", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableIn(final List<Integer> values) {
            this.addCriterion("rechargeable in", values, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableNotIn(final List<Integer> values) {
            this.addCriterion("rechargeable not in", values, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableBetween(final Integer value1, final Integer value2) {
            this.addCriterion("rechargeable between", value1, value2, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("rechargeable not between", value1, value2, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableGreaterThan(final Integer value) {
            this.addCriterion("rechargeable >", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("rechargeable >=", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableLessThan(final Integer value) {
            this.addCriterion("rechargeable <", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andRechargeableLessThanOrEqualTo(final Integer value) {
            this.addCriterion("rechargeable <=", value, "rechargeable");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeIsNull() {
            this.addCriterion("confirm_size is null");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeIsNotNull() {
            this.addCriterion("confirm_size is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeEqualTo(final Integer value) {
            this.addCriterion("confirm_size =", value, "confirmSize");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeNotEqualTo(final Integer value) {
            this.addCriterion("confirm_size <>", value, "confirmSize");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeIn(final List<Integer> values) {
            this.addCriterion("confirm_size in", values, "confirmSize");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeNotIn(final List<Integer> values) {
            this.addCriterion("confirm_size not in", values, "confirmSize");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("confirm_size between", value1, value2, "confirmSize");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("confirm_size not between", value1, value2, "confirmSize");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeGreaterThan(final Integer value) {
            this.addCriterion("confirm_size >", value, "confirmSize");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("confirm_size >=", value, "confirmSize");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeLessThan(final Integer value) {
            this.addCriterion("confirm_size <", value, "confirmSize");
            return (Criteria) this;
        }

        public Criteria andConfirmSizeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("confirm_size <=", value, "confirmSize");
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

        public Criteria andOnlineIsNull() {
            this.addCriterion("online is null");
            return (Criteria) this;
        }

        public Criteria andOnlineIsNotNull() {
            this.addCriterion("online is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineEqualTo(final Integer value) {
            this.addCriterion("online =", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotEqualTo(final Integer value) {
            this.addCriterion("online <>", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineIn(final List<Integer> values) {
            this.addCriterion("online in", values, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotIn(final List<Integer> values) {
            this.addCriterion("online not in", values, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineBetween(final Integer value1, final Integer value2) {
            this.addCriterion("online between", value1, value2, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("online not between", value1, value2, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineGreaterThan(final Integer value) {
            this.addCriterion("online >", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("online >=", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineLessThan(final Integer value) {
            this.addCriterion("online <", value, "online");
            return (Criteria) this;
        }

        public Criteria andOnlineLessThanOrEqualTo(final Integer value) {
            this.addCriterion("online <=", value, "online");
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