package cc.newex.dax.users.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户法币交易设置 查询条件example类
 *
 * @author newex-team
 * @date 2018-05-18
 */
public class UserFiatSettingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserFiatSettingExample() {
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

        public Criteria andUserNameIsNull() {
            this.addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            this.addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(final String value) {
            this.addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(final String value) {
            this.addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(final List<String> values) {
            this.addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(final List<String> values) {
            this.addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(final String value1, final String value2) {
            this.addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(final String value1, final String value2) {
            this.addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }


        public Criteria andUserNameLike(final String value) {
            this.addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(final String value) {
            this.addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andBankCardIsNull() {
            this.addCriterion("bank_card is null");
            return (Criteria) this;
        }

        public Criteria andBankCardIsNotNull() {
            this.addCriterion("bank_card is not null");
            return (Criteria) this;
        }

        public Criteria andBankCardEqualTo(final String value) {
            this.addCriterion("bank_card =", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotEqualTo(final String value) {
            this.addCriterion("bank_card <>", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardIn(final List<String> values) {
            this.addCriterion("bank_card in", values, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotIn(final List<String> values) {
            this.addCriterion("bank_card not in", values, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardBetween(final String value1, final String value2) {
            this.addCriterion("bank_card between", value1, value2, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotBetween(final String value1, final String value2) {
            this.addCriterion("bank_card not between", value1, value2, "bankCard");
            return (Criteria) this;
        }


        public Criteria andBankCardLike(final String value) {
            this.addCriterion("bank_card like", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotLike(final String value) {
            this.addCriterion("bank_card not like", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankAddressIsNull() {
            this.addCriterion("bank_address is null");
            return (Criteria) this;
        }

        public Criteria andBankAddressIsNotNull() {
            this.addCriterion("bank_address is not null");
            return (Criteria) this;
        }

        public Criteria andBankAddressEqualTo(final String value) {
            this.addCriterion("bank_address =", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressNotEqualTo(final String value) {
            this.addCriterion("bank_address <>", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressIn(final List<String> values) {
            this.addCriterion("bank_address in", values, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressNotIn(final List<String> values) {
            this.addCriterion("bank_address not in", values, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressBetween(final String value1, final String value2) {
            this.addCriterion("bank_address between", value1, value2, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressNotBetween(final String value1, final String value2) {
            this.addCriterion("bank_address not between", value1, value2, "bankAddress");
            return (Criteria) this;
        }


        public Criteria andBankAddressLike(final String value) {
            this.addCriterion("bank_address like", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressNotLike(final String value) {
            this.addCriterion("bank_address not like", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNull() {
            this.addCriterion("bank_name is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            this.addCriterion("bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(final String value) {
            this.addCriterion("bank_name =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(final String value) {
            this.addCriterion("bank_name <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(final List<String> values) {
            this.addCriterion("bank_name in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(final List<String> values) {
            this.addCriterion("bank_name not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(final String value1, final String value2) {
            this.addCriterion("bank_name between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(final String value1, final String value2) {
            this.addCriterion("bank_name not between", value1, value2, "bankName");
            return (Criteria) this;
        }


        public Criteria andBankNameLike(final String value) {
            this.addCriterion("bank_name like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(final String value) {
            this.addCriterion("bank_name not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNoIsNull() {
            this.addCriterion("bank_no is null");
            return (Criteria) this;
        }

        public Criteria andBankNoIsNotNull() {
            this.addCriterion("bank_no is not null");
            return (Criteria) this;
        }

        public Criteria andBankNoEqualTo(final String value) {
            this.addCriterion("bank_no =", value, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoNotEqualTo(final String value) {
            this.addCriterion("bank_no <>", value, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoIn(final List<String> values) {
            this.addCriterion("bank_no in", values, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoNotIn(final List<String> values) {
            this.addCriterion("bank_no not in", values, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoBetween(final String value1, final String value2) {
            this.addCriterion("bank_no between", value1, value2, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoNotBetween(final String value1, final String value2) {
            this.addCriterion("bank_no not between", value1, value2, "bankNo");
            return (Criteria) this;
        }


        public Criteria andBankNoLike(final String value) {
            this.addCriterion("bank_no like", value, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoNotLike(final String value) {
            this.addCriterion("bank_no not like", value, "bankNo");
            return (Criteria) this;
        }

        public Criteria andAlipayNameIsNull() {
            this.addCriterion("alipay_name is null");
            return (Criteria) this;
        }

        public Criteria andAlipayNameIsNotNull() {
            this.addCriterion("alipay_name is not null");
            return (Criteria) this;
        }

        public Criteria andAlipayNameEqualTo(final String value) {
            this.addCriterion("alipay_name =", value, "alipayName");
            return (Criteria) this;
        }

        public Criteria andAlipayNameNotEqualTo(final String value) {
            this.addCriterion("alipay_name <>", value, "alipayName");
            return (Criteria) this;
        }

        public Criteria andAlipayNameIn(final List<String> values) {
            this.addCriterion("alipay_name in", values, "alipayName");
            return (Criteria) this;
        }

        public Criteria andAlipayNameNotIn(final List<String> values) {
            this.addCriterion("alipay_name not in", values, "alipayName");
            return (Criteria) this;
        }

        public Criteria andAlipayNameBetween(final String value1, final String value2) {
            this.addCriterion("alipay_name between", value1, value2, "alipayName");
            return (Criteria) this;
        }

        public Criteria andAlipayNameNotBetween(final String value1, final String value2) {
            this.addCriterion("alipay_name not between", value1, value2, "alipayName");
            return (Criteria) this;
        }


        public Criteria andAlipayNameLike(final String value) {
            this.addCriterion("alipay_name like", value, "alipayName");
            return (Criteria) this;
        }

        public Criteria andAlipayNameNotLike(final String value) {
            this.addCriterion("alipay_name not like", value, "alipayName");
            return (Criteria) this;
        }

        public Criteria andAlipayCardIsNull() {
            this.addCriterion("alipay_card is null");
            return (Criteria) this;
        }

        public Criteria andAlipayCardIsNotNull() {
            this.addCriterion("alipay_card is not null");
            return (Criteria) this;
        }

        public Criteria andAlipayCardEqualTo(final String value) {
            this.addCriterion("alipay_card =", value, "alipayCard");
            return (Criteria) this;
        }

        public Criteria andAlipayCardNotEqualTo(final String value) {
            this.addCriterion("alipay_card <>", value, "alipayCard");
            return (Criteria) this;
        }

        public Criteria andAlipayCardIn(final List<String> values) {
            this.addCriterion("alipay_card in", values, "alipayCard");
            return (Criteria) this;
        }

        public Criteria andAlipayCardNotIn(final List<String> values) {
            this.addCriterion("alipay_card not in", values, "alipayCard");
            return (Criteria) this;
        }

        public Criteria andAlipayCardBetween(final String value1, final String value2) {
            this.addCriterion("alipay_card between", value1, value2, "alipayCard");
            return (Criteria) this;
        }

        public Criteria andAlipayCardNotBetween(final String value1, final String value2) {
            this.addCriterion("alipay_card not between", value1, value2, "alipayCard");
            return (Criteria) this;
        }


        public Criteria andAlipayCardLike(final String value) {
            this.addCriterion("alipay_card like", value, "alipayCard");
            return (Criteria) this;
        }

        public Criteria andAlipayCardNotLike(final String value) {
            this.addCriterion("alipay_card not like", value, "alipayCard");
            return (Criteria) this;
        }

        public Criteria andAlipayReceivingImgIsNull() {
            this.addCriterion("alipay_receiving_img is null");
            return (Criteria) this;
        }

        public Criteria andAlipayReceivingImgIsNotNull() {
            this.addCriterion("alipay_receiving_img is not null");
            return (Criteria) this;
        }

        public Criteria andAlipayReceivingImgEqualTo(final String value) {
            this.addCriterion("alipay_receiving_img =", value, "alipayReceivingImg");
            return (Criteria) this;
        }

        public Criteria andAlipayReceivingImgNotEqualTo(final String value) {
            this.addCriterion("alipay_receiving_img <>", value, "alipayReceivingImg");
            return (Criteria) this;
        }

        public Criteria andAlipayReceivingImgIn(final List<String> values) {
            this.addCriterion("alipay_receiving_img in", values, "alipayReceivingImg");
            return (Criteria) this;
        }

        public Criteria andAlipayReceivingImgNotIn(final List<String> values) {
            this.addCriterion("alipay_receiving_img not in", values, "alipayReceivingImg");
            return (Criteria) this;
        }

        public Criteria andAlipayReceivingImgBetween(final String value1, final String value2) {
            this.addCriterion("alipay_receiving_img between", value1, value2, "alipayReceivingImg");
            return (Criteria) this;
        }

        public Criteria andAlipayReceivingImgNotBetween(final String value1, final String value2) {
            this.addCriterion("alipay_receiving_img not between", value1, value2, "alipayReceivingImg");
            return (Criteria) this;
        }


        public Criteria andAlipayReceivingImgLike(final String value) {
            this.addCriterion("alipay_receiving_img like", value, "alipayReceivingImg");
            return (Criteria) this;
        }

        public Criteria andAlipayReceivingImgNotLike(final String value) {
            this.addCriterion("alipay_receiving_img not like", value, "alipayReceivingImg");
            return (Criteria) this;
        }

        public Criteria andWechatPayNameIsNull() {
            this.addCriterion("wechat_pay_name is null");
            return (Criteria) this;
        }

        public Criteria andWechatPayNameIsNotNull() {
            this.addCriterion("wechat_pay_name is not null");
            return (Criteria) this;
        }

        public Criteria andWechatPayNameEqualTo(final String value) {
            this.addCriterion("wechat_pay_name =", value, "wechatPayName");
            return (Criteria) this;
        }

        public Criteria andWechatPayNameNotEqualTo(final String value) {
            this.addCriterion("wechat_pay_name <>", value, "wechatPayName");
            return (Criteria) this;
        }

        public Criteria andWechatPayNameIn(final List<String> values) {
            this.addCriterion("wechat_pay_name in", values, "wechatPayName");
            return (Criteria) this;
        }

        public Criteria andWechatPayNameNotIn(final List<String> values) {
            this.addCriterion("wechat_pay_name not in", values, "wechatPayName");
            return (Criteria) this;
        }

        public Criteria andWechatPayNameBetween(final String value1, final String value2) {
            this.addCriterion("wechat_pay_name between", value1, value2, "wechatPayName");
            return (Criteria) this;
        }

        public Criteria andWechatPayNameNotBetween(final String value1, final String value2) {
            this.addCriterion("wechat_pay_name not between", value1, value2, "wechatPayName");
            return (Criteria) this;
        }


        public Criteria andWechatPayNameLike(final String value) {
            this.addCriterion("wechat_pay_name like", value, "wechatPayName");
            return (Criteria) this;
        }

        public Criteria andWechatPayNameNotLike(final String value) {
            this.addCriterion("wechat_pay_name not like", value, "wechatPayName");
            return (Criteria) this;
        }

        public Criteria andWechatCardIsNull() {
            this.addCriterion("wechat_card is null");
            return (Criteria) this;
        }

        public Criteria andWechatCardIsNotNull() {
            this.addCriterion("wechat_card is not null");
            return (Criteria) this;
        }

        public Criteria andWechatCardEqualTo(final String value) {
            this.addCriterion("wechat_card =", value, "wechatCard");
            return (Criteria) this;
        }

        public Criteria andWechatCardNotEqualTo(final String value) {
            this.addCriterion("wechat_card <>", value, "wechatCard");
            return (Criteria) this;
        }

        public Criteria andWechatCardIn(final List<String> values) {
            this.addCriterion("wechat_card in", values, "wechatCard");
            return (Criteria) this;
        }

        public Criteria andWechatCardNotIn(final List<String> values) {
            this.addCriterion("wechat_card not in", values, "wechatCard");
            return (Criteria) this;
        }

        public Criteria andWechatCardBetween(final String value1, final String value2) {
            this.addCriterion("wechat_card between", value1, value2, "wechatCard");
            return (Criteria) this;
        }

        public Criteria andWechatCardNotBetween(final String value1, final String value2) {
            this.addCriterion("wechat_card not between", value1, value2, "wechatCard");
            return (Criteria) this;
        }


        public Criteria andWechatCardLike(final String value) {
            this.addCriterion("wechat_card like", value, "wechatCard");
            return (Criteria) this;
        }

        public Criteria andWechatCardNotLike(final String value) {
            this.addCriterion("wechat_card not like", value, "wechatCard");
            return (Criteria) this;
        }

        public Criteria andWechatReceivingImgIsNull() {
            this.addCriterion("wechat_receiving_img is null");
            return (Criteria) this;
        }

        public Criteria andWechatReceivingImgIsNotNull() {
            this.addCriterion("wechat_receiving_img is not null");
            return (Criteria) this;
        }

        public Criteria andWechatReceivingImgEqualTo(final String value) {
            this.addCriterion("wechat_receiving_img =", value, "wechatReceivingImg");
            return (Criteria) this;
        }

        public Criteria andWechatReceivingImgNotEqualTo(final String value) {
            this.addCriterion("wechat_receiving_img <>", value, "wechatReceivingImg");
            return (Criteria) this;
        }

        public Criteria andWechatReceivingImgIn(final List<String> values) {
            this.addCriterion("wechat_receiving_img in", values, "wechatReceivingImg");
            return (Criteria) this;
        }

        public Criteria andWechatReceivingImgNotIn(final List<String> values) {
            this.addCriterion("wechat_receiving_img not in", values, "wechatReceivingImg");
            return (Criteria) this;
        }

        public Criteria andWechatReceivingImgBetween(final String value1, final String value2) {
            this.addCriterion("wechat_receiving_img between", value1, value2, "wechatReceivingImg");
            return (Criteria) this;
        }

        public Criteria andWechatReceivingImgNotBetween(final String value1, final String value2) {
            this.addCriterion("wechat_receiving_img not between", value1, value2, "wechatReceivingImg");
            return (Criteria) this;
        }


        public Criteria andWechatReceivingImgLike(final String value) {
            this.addCriterion("wechat_receiving_img like", value, "wechatReceivingImg");
            return (Criteria) this;
        }

        public Criteria andWechatReceivingImgNotLike(final String value) {
            this.addCriterion("wechat_receiving_img not like", value, "wechatReceivingImg");
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

        public Criteria andRemarksIsNull() {
            this.addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            this.addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(final String value) {
            this.addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(final String value) {
            this.addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(final List<String> values) {
            this.addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(final List<String> values) {
            this.addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(final String value1, final String value2) {
            this.addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(final String value1, final String value2) {
            this.addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }


        public Criteria andRemarksLike(final String value) {
            this.addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(final String value) {
            this.addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            this.addCriterion("pay_type is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            this.addCriterion("pay_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(final Integer value) {
            this.addCriterion("pay_type =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(final Integer value) {
            this.addCriterion("pay_type <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(final List<Integer> values) {
            this.addCriterion("pay_type in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(final List<Integer> values) {
            this.addCriterion("pay_type not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("pay_type between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("pay_type not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(final Integer value) {
            this.addCriterion("pay_type >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("pay_type >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(final Integer value) {
            this.addCriterion("pay_type <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("pay_type <=", value, "payType");
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

        public Criteria andFieldIsNull(final String fieldName) {
            this.addCriterion(fieldName + " is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull(final String fieldName) {
            this.addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " = ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <> ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldIn(final String fieldName, final List<Object> values) {
            this.addCriterion(fieldName + " in ", values, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(final String fieldName, final List<Object> values) {
            this.addCriterion(fieldName + " not in ", values, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            this.addCriterion(fieldName + " between ", fieldValue1, fieldValue2, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            this.addCriterion(fieldName + "  not between ", fieldValue1, fieldValue2, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " > ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " >= ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " < ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <= ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldLike(final String fieldName, final String fieldValue) {
            this.addCriterion(fieldName + " like ", fieldValue, fieldName);
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(final String fieldName, final String fieldValue) {
            this.addCriterion(fieldName + "  not like ", fieldValue, fieldName);
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
