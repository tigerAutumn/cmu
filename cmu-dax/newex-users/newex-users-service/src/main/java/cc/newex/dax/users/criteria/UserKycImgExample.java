package cc.newex.dax.users.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户kyc证件照片信息 查询条件类
 *
 * @author newex-team
 * @date 2018-09-13 14:29:31
 */
public class UserKycImgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserKycImgExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNull() {
            addCriterion("card_type is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("card_type is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(Integer value) {
            addCriterion("card_type =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(Integer value) {
            addCriterion("card_type <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(Integer value) {
            addCriterion("card_type >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("card_type >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(Integer value) {
            addCriterion("card_type <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(Integer value) {
            addCriterion("card_type <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<Integer> values) {
            addCriterion("card_type in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<Integer> values) {
            addCriterion("card_type not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(Integer value1, Integer value2) {
            addCriterion("card_type between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("card_type not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardImgIsNull() {
            addCriterion("card_img is null");
            return (Criteria) this;
        }

        public Criteria andCardImgIsNotNull() {
            addCriterion("card_img is not null");
            return (Criteria) this;
        }

        public Criteria andCardImgEqualTo(String value) {
            addCriterion("card_img =", value, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgNotEqualTo(String value) {
            addCriterion("card_img <>", value, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgGreaterThan(String value) {
            addCriterion("card_img >", value, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgGreaterThanOrEqualTo(String value) {
            addCriterion("card_img >=", value, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgLessThan(String value) {
            addCriterion("card_img <", value, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgLessThanOrEqualTo(String value) {
            addCriterion("card_img <=", value, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgLike(String value) {
            addCriterion("card_img like", value, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgNotLike(String value) {
            addCriterion("card_img not like", value, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgIn(List<String> values) {
            addCriterion("card_img in", values, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgNotIn(List<String> values) {
            addCriterion("card_img not in", values, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgBetween(String value1, String value2) {
            addCriterion("card_img between", value1, value2, "cardImg");
            return (Criteria) this;
        }

        public Criteria andCardImgNotBetween(String value1, String value2) {
            addCriterion("card_img not between", value1, value2, "cardImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgIsNull() {
            addCriterion("front_img is null");
            return (Criteria) this;
        }

        public Criteria andFrontImgIsNotNull() {
            addCriterion("front_img is not null");
            return (Criteria) this;
        }

        public Criteria andFrontImgEqualTo(String value) {
            addCriterion("front_img =", value, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgNotEqualTo(String value) {
            addCriterion("front_img <>", value, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgGreaterThan(String value) {
            addCriterion("front_img >", value, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgGreaterThanOrEqualTo(String value) {
            addCriterion("front_img >=", value, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgLessThan(String value) {
            addCriterion("front_img <", value, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgLessThanOrEqualTo(String value) {
            addCriterion("front_img <=", value, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgLike(String value) {
            addCriterion("front_img like", value, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgNotLike(String value) {
            addCriterion("front_img not like", value, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgIn(List<String> values) {
            addCriterion("front_img in", values, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgNotIn(List<String> values) {
            addCriterion("front_img not in", values, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgBetween(String value1, String value2) {
            addCriterion("front_img between", value1, value2, "frontImg");
            return (Criteria) this;
        }

        public Criteria andFrontImgNotBetween(String value1, String value2) {
            addCriterion("front_img not between", value1, value2, "frontImg");
            return (Criteria) this;
        }

        public Criteria andBackImgIsNull() {
            addCriterion("back_img is null");
            return (Criteria) this;
        }

        public Criteria andBackImgIsNotNull() {
            addCriterion("back_img is not null");
            return (Criteria) this;
        }

        public Criteria andBackImgEqualTo(String value) {
            addCriterion("back_img =", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgNotEqualTo(String value) {
            addCriterion("back_img <>", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgGreaterThan(String value) {
            addCriterion("back_img >", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgGreaterThanOrEqualTo(String value) {
            addCriterion("back_img >=", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgLessThan(String value) {
            addCriterion("back_img <", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgLessThanOrEqualTo(String value) {
            addCriterion("back_img <=", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgLike(String value) {
            addCriterion("back_img like", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgNotLike(String value) {
            addCriterion("back_img not like", value, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgIn(List<String> values) {
            addCriterion("back_img in", values, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgNotIn(List<String> values) {
            addCriterion("back_img not in", values, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgBetween(String value1, String value2) {
            addCriterion("back_img between", value1, value2, "backImg");
            return (Criteria) this;
        }

        public Criteria andBackImgNotBetween(String value1, String value2) {
            addCriterion("back_img not between", value1, value2, "backImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgIsNull() {
            addCriterion("hands_img is null");
            return (Criteria) this;
        }

        public Criteria andHandsImgIsNotNull() {
            addCriterion("hands_img is not null");
            return (Criteria) this;
        }

        public Criteria andHandsImgEqualTo(String value) {
            addCriterion("hands_img =", value, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgNotEqualTo(String value) {
            addCriterion("hands_img <>", value, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgGreaterThan(String value) {
            addCriterion("hands_img >", value, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgGreaterThanOrEqualTo(String value) {
            addCriterion("hands_img >=", value, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgLessThan(String value) {
            addCriterion("hands_img <", value, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgLessThanOrEqualTo(String value) {
            addCriterion("hands_img <=", value, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgLike(String value) {
            addCriterion("hands_img like", value, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgNotLike(String value) {
            addCriterion("hands_img not like", value, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgIn(List<String> values) {
            addCriterion("hands_img in", values, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgNotIn(List<String> values) {
            addCriterion("hands_img not in", values, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgBetween(String value1, String value2) {
            addCriterion("hands_img between", value1, value2, "handsImg");
            return (Criteria) this;
        }

        public Criteria andHandsImgNotBetween(String value1, String value2) {
            addCriterion("hands_img not between", value1, value2, "handsImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgIsNull() {
            addCriterion("address_img is null");
            return (Criteria) this;
        }

        public Criteria andAddressImgIsNotNull() {
            addCriterion("address_img is not null");
            return (Criteria) this;
        }

        public Criteria andAddressImgEqualTo(String value) {
            addCriterion("address_img =", value, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgNotEqualTo(String value) {
            addCriterion("address_img <>", value, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgGreaterThan(String value) {
            addCriterion("address_img >", value, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgGreaterThanOrEqualTo(String value) {
            addCriterion("address_img >=", value, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgLessThan(String value) {
            addCriterion("address_img <", value, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgLessThanOrEqualTo(String value) {
            addCriterion("address_img <=", value, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgLike(String value) {
            addCriterion("address_img like", value, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgNotLike(String value) {
            addCriterion("address_img not like", value, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgIn(List<String> values) {
            addCriterion("address_img in", values, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgNotIn(List<String> values) {
            addCriterion("address_img not in", values, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgBetween(String value1, String value2) {
            addCriterion("address_img between", value1, value2, "addressImg");
            return (Criteria) this;
        }

        public Criteria andAddressImgNotBetween(String value1, String value2) {
            addCriterion("address_img not between", value1, value2, "addressImg");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            addCriterion("created_date is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            addCriterion("created_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(Date value) {
            addCriterion("created_date =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(Date value) {
            addCriterion("created_date <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(Date value) {
            addCriterion("created_date >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("created_date >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(Date value) {
            addCriterion("created_date <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(Date value) {
            addCriterion("created_date <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(List<Date> values) {
            addCriterion("created_date in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(List<Date> values) {
            addCriterion("created_date not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(Date value1, Date value2) {
            addCriterion("created_date between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(Date value1, Date value2) {
            addCriterion("created_date not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNull() {
            addCriterion("updated_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNotNull() {
            addCriterion("updated_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateEqualTo(Date value) {
            addCriterion("updated_date =", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotEqualTo(Date value) {
            addCriterion("updated_date <>", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThan(Date value) {
            addCriterion("updated_date >", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_date >=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThan(Date value) {
            addCriterion("updated_date <", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThanOrEqualTo(Date value) {
            addCriterion("updated_date <=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIn(List<Date> values) {
            addCriterion("updated_date in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotIn(List<Date> values) {
            addCriterion("updated_date not in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateBetween(Date value1, Date value2) {
            addCriterion("updated_date between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotBetween(Date value1, Date value2) {
            addCriterion("updated_date not between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNull() {
            addCriterion("broker_id is null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNotNull() {
            addCriterion("broker_id is not null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdEqualTo(Integer value) {
            addCriterion("broker_id =", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotEqualTo(Integer value) {
            addCriterion("broker_id <>", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThan(Integer value) {
            addCriterion("broker_id >", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("broker_id >=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThan(Integer value) {
            addCriterion("broker_id <", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThanOrEqualTo(Integer value) {
            addCriterion("broker_id <=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIn(List<Integer> values) {
            addCriterion("broker_id in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotIn(List<Integer> values) {
            addCriterion("broker_id not in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdBetween(Integer value1, Integer value2) {
            addCriterion("broker_id between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("broker_id not between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andFieldIsNull(String fieldName) {
            addCriterion(fieldName + " is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull(String fieldName) {
            addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " =", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " <>", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " >", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " >=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " <", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " <=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLike(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(String fieldName, Object fieldValue) {
            addCriterion(fieldName + " not like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldIn(String fieldName, List<Object> fieldValues) {
            addCriterion(fieldName + " in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(String fieldName, List<Object> fieldValues) {
            addCriterion(fieldName + " not in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldBetween(String fieldName, Object fieldValue1, Object fieldValue2) {
            addCriterion(fieldName + " between", fieldValue1, fieldValue2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(String fieldName, Object fieldValue1, Object fieldValue2) {
            addCriterion(fieldName + " not between", fieldValue1, fieldValue2, "fieldName");
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