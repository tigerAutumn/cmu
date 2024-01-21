package cc.newex.dax.asset.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  查询条件example类
 * @author newex-team
 * @date 2018-06-26
 */
public class UserAssetConfExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserAssetConfExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

	        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria)this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria)this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria)this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria)this;
        }
		
		public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
			
	            public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria)this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria)this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria)this;
        }
		
		public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria)this;
        }
			
	            public Criteria andWithdrawLimitIsNull() {
            addCriterion("withdraw_limit is null");
            return (Criteria)this;
        }

        public Criteria andWithdrawLimitIsNotNull() {
            addCriterion("withdraw_limit is not null");
            return (Criteria)this;
        }

        public Criteria andWithdrawLimitEqualTo(BigDecimal value) {
            addCriterion("withdraw_limit =", value, "withdrawLimit");
            return (Criteria)this;
        }

        public Criteria andWithdrawLimitNotEqualTo(BigDecimal value) {
            addCriterion("withdraw_limit <>", value, "withdrawLimit");
            return (Criteria)this;
        }

        public Criteria andWithdrawLimitIn(List<BigDecimal> values) {
            addCriterion("withdraw_limit in", values, "withdrawLimit");
            return (Criteria)this;
        }

        public Criteria andWithdrawLimitNotIn(List<BigDecimal> values) {
            addCriterion("withdraw_limit not in", values, "withdrawLimit");
            return (Criteria)this;
        }

        public Criteria andWithdrawLimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_limit between", value1, value2, "withdrawLimit");
            return (Criteria)this;
        }

        public Criteria andWithdrawLimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_limit not between", value1, value2, "withdrawLimit");
            return (Criteria)this;
        }
		
		public Criteria andWithdrawLimitGreaterThan(BigDecimal value) {
            addCriterion("withdraw_limit >", value, "withdrawLimit");
            return (Criteria)this;
        }

        public Criteria andWithdrawLimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_limit >=", value, "withdrawLimit");
            return (Criteria)this;
        }

        public Criteria andWithdrawLimitLessThan(BigDecimal value) {
            addCriterion("withdraw_limit <", value, "withdrawLimit");
            return (Criteria)this;
        }

        public Criteria andWithdrawLimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_limit <=", value, "withdrawLimit");
            return (Criteria)this;
        }
			
	            public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria)this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria)this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria)this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria)this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria)this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria)this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria)this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria)this;
        }
		
		public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria)this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria)this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria)this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria)this;
        }
			
	            public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria)this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria)this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria)this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria)this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria)this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria)this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria)this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria)this;
        }
		
		public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria)this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria)this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria)this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria)this;
        }
			
	    	public Criteria andFieldIsNull(final String fieldName) {
            addCriterion(fieldName + " is null");
            return (Criteria) this;
        }
		
	public Criteria andFieldIsNotNull(final String fieldName) {
            addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }
		
	public Criteria andFieldEqualTo(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " = ", fieldValue, fieldName);
            return (Criteria) this;
        }
		
	public Criteria andFieldNotEqualTo(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " <> ", fieldValue, fieldName);
            return (Criteria) this;
        }
		
		public Criteria andFieldIn(final String fieldName, final List<Object> values) {
            addCriterion(fieldName + " in ", values, fieldName);
           return (Criteria) this;
        }
		
	public Criteria andFieldNotIn(final String fieldName, final List<Object> values) {
            addCriterion(fieldName + " not in ", values, fieldName);
            return (Criteria) this;
        }
		
	public Criteria andFieldBetween(final String fieldName, final Object fieldValue1,final Object fieldValue2) {
            addCriterion(fieldName + " between ", fieldValue1, fieldValue2, fieldName);
            return (Criteria) this;
        }
		
	public Criteria andFieldNotBetween(final String fieldName, final Object fieldValue1,final Object fieldValue2) {
            addCriterion(fieldName + "  not between ", fieldValue1, fieldValue2, fieldName);
            return (Criteria) this;
        }
		
		public Criteria andFieldGreaterThan(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " > ", fieldValue, fieldName);
            return (Criteria) this;
        }

	public Criteria andFieldGreaterThanOrEqualTo(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " >= ", fieldValue, fieldName);
            return (Criteria) this;
        }
		
	public Criteria andFieldLessThan(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " < ", fieldValue, fieldName);
            return (Criteria) this;
        }
		
		public Criteria andFieldLessThanOrEqualTo(final String fieldName, final Object fieldValue) {
            addCriterion(fieldName + " <= ", fieldValue, fieldName);
            return (Criteria) this;
        }
		
	public Criteria andFieldLike(final String fieldName, final String fieldValue) {
            addCriterion(fieldName + " like ", fieldValue, fieldName);
            return (Criteria) this;
        }
		
	public Criteria andFieldNotLike(final String fieldName, final String fieldValue) {
            addCriterion(fieldName + "  not like ", fieldValue, fieldName);
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
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
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
