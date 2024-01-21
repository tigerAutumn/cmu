package cc.newex.dax.users.criteria;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户开关设置表 查询条件类
 *
 * @author mbg.generated
 * @date 2018-11-02 18:28:19
 */
public class UserSettingsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserSettingsExample() {
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

        public Criteria andLoginAuthFlagIsNull() {
            addCriterion("login_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagIsNotNull() {
            addCriterion("login_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagEqualTo(Integer value) {
            addCriterion("login_auth_flag =", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagNotEqualTo(Integer value) {
            addCriterion("login_auth_flag <>", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagGreaterThan(Integer value) {
            addCriterion("login_auth_flag >", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("login_auth_flag >=", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagLessThan(Integer value) {
            addCriterion("login_auth_flag <", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("login_auth_flag <=", value, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagIn(List<Integer> values) {
            addCriterion("login_auth_flag in", values, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagNotIn(List<Integer> values) {
            addCriterion("login_auth_flag not in", values, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("login_auth_flag between", value1, value2, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("login_auth_flag not between", value1, value2, "loginAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagIsNull() {
            addCriterion("google_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagIsNotNull() {
            addCriterion("google_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagEqualTo(Integer value) {
            addCriterion("google_auth_flag =", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagNotEqualTo(Integer value) {
            addCriterion("google_auth_flag <>", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagGreaterThan(Integer value) {
            addCriterion("google_auth_flag >", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("google_auth_flag >=", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagLessThan(Integer value) {
            addCriterion("google_auth_flag <", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("google_auth_flag <=", value, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagIn(List<Integer> values) {
            addCriterion("google_auth_flag in", values, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagNotIn(List<Integer> values) {
            addCriterion("google_auth_flag not in", values, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("google_auth_flag between", value1, value2, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andGoogleAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("google_auth_flag not between", value1, value2, "googleAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagIsNull() {
            addCriterion("email_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagIsNotNull() {
            addCriterion("email_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagEqualTo(Integer value) {
            addCriterion("email_auth_flag =", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagNotEqualTo(Integer value) {
            addCriterion("email_auth_flag <>", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagGreaterThan(Integer value) {
            addCriterion("email_auth_flag >", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("email_auth_flag >=", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagLessThan(Integer value) {
            addCriterion("email_auth_flag <", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("email_auth_flag <=", value, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagIn(List<Integer> values) {
            addCriterion("email_auth_flag in", values, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagNotIn(List<Integer> values) {
            addCriterion("email_auth_flag not in", values, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("email_auth_flag between", value1, value2, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andEmailAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("email_auth_flag not between", value1, value2, "emailAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagIsNull() {
            addCriterion("mobile_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagIsNotNull() {
            addCriterion("mobile_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagEqualTo(Integer value) {
            addCriterion("mobile_auth_flag =", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagNotEqualTo(Integer value) {
            addCriterion("mobile_auth_flag <>", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagGreaterThan(Integer value) {
            addCriterion("mobile_auth_flag >", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("mobile_auth_flag >=", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagLessThan(Integer value) {
            addCriterion("mobile_auth_flag <", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("mobile_auth_flag <=", value, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagIn(List<Integer> values) {
            addCriterion("mobile_auth_flag in", values, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagNotIn(List<Integer> values) {
            addCriterion("mobile_auth_flag not in", values, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("mobile_auth_flag between", value1, value2, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andMobileAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("mobile_auth_flag not between", value1, value2, "mobileAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagIsNull() {
            addCriterion("trade_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagIsNotNull() {
            addCriterion("trade_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagEqualTo(Integer value) {
            addCriterion("trade_auth_flag =", value, "tradeAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagNotEqualTo(Integer value) {
            addCriterion("trade_auth_flag <>", value, "tradeAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagGreaterThan(Integer value) {
            addCriterion("trade_auth_flag >", value, "tradeAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("trade_auth_flag >=", value, "tradeAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagLessThan(Integer value) {
            addCriterion("trade_auth_flag <", value, "tradeAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("trade_auth_flag <=", value, "tradeAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagIn(List<Integer> values) {
            addCriterion("trade_auth_flag in", values, "tradeAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagNotIn(List<Integer> values) {
            addCriterion("trade_auth_flag not in", values, "tradeAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("trade_auth_flag between", value1, value2, "tradeAuthFlag");
            return (Criteria) this;
        }

        public Criteria andTradeAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("trade_auth_flag not between", value1, value2, "tradeAuthFlag");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthIsNull() {
            addCriterion("login_pwd_strength is null");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthIsNotNull() {
            addCriterion("login_pwd_strength is not null");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthEqualTo(Integer value) {
            addCriterion("login_pwd_strength =", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthNotEqualTo(Integer value) {
            addCriterion("login_pwd_strength <>", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthGreaterThan(Integer value) {
            addCriterion("login_pwd_strength >", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("login_pwd_strength >=", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthLessThan(Integer value) {
            addCriterion("login_pwd_strength <", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthLessThanOrEqualTo(Integer value) {
            addCriterion("login_pwd_strength <=", value, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthIn(List<Integer> values) {
            addCriterion("login_pwd_strength in", values, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthNotIn(List<Integer> values) {
            addCriterion("login_pwd_strength not in", values, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthBetween(Integer value1, Integer value2) {
            addCriterion("login_pwd_strength between", value1, value2, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andLoginPwdStrengthNotBetween(Integer value1, Integer value2) {
            addCriterion("login_pwd_strength not between", value1, value2, "loginPwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthIsNull() {
            addCriterion("trade_pwd_strength is null");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthIsNotNull() {
            addCriterion("trade_pwd_strength is not null");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthEqualTo(Integer value) {
            addCriterion("trade_pwd_strength =", value, "tradePwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthNotEqualTo(Integer value) {
            addCriterion("trade_pwd_strength <>", value, "tradePwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthGreaterThan(Integer value) {
            addCriterion("trade_pwd_strength >", value, "tradePwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("trade_pwd_strength >=", value, "tradePwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthLessThan(Integer value) {
            addCriterion("trade_pwd_strength <", value, "tradePwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthLessThanOrEqualTo(Integer value) {
            addCriterion("trade_pwd_strength <=", value, "tradePwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthIn(List<Integer> values) {
            addCriterion("trade_pwd_strength in", values, "tradePwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthNotIn(List<Integer> values) {
            addCriterion("trade_pwd_strength not in", values, "tradePwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthBetween(Integer value1, Integer value2) {
            addCriterion("trade_pwd_strength between", value1, value2, "tradePwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdStrengthNotBetween(Integer value1, Integer value2) {
            addCriterion("trade_pwd_strength not between", value1, value2, "tradePwdStrength");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputIsNull() {
            addCriterion("trade_pwd_input is null");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputIsNotNull() {
            addCriterion("trade_pwd_input is not null");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputEqualTo(Integer value) {
            addCriterion("trade_pwd_input =", value, "tradePwdInput");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputNotEqualTo(Integer value) {
            addCriterion("trade_pwd_input <>", value, "tradePwdInput");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputGreaterThan(Integer value) {
            addCriterion("trade_pwd_input >", value, "tradePwdInput");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputGreaterThanOrEqualTo(Integer value) {
            addCriterion("trade_pwd_input >=", value, "tradePwdInput");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputLessThan(Integer value) {
            addCriterion("trade_pwd_input <", value, "tradePwdInput");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputLessThanOrEqualTo(Integer value) {
            addCriterion("trade_pwd_input <=", value, "tradePwdInput");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputIn(List<Integer> values) {
            addCriterion("trade_pwd_input in", values, "tradePwdInput");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputNotIn(List<Integer> values) {
            addCriterion("trade_pwd_input not in", values, "tradePwdInput");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputBetween(Integer value1, Integer value2) {
            addCriterion("trade_pwd_input between", value1, value2, "tradePwdInput");
            return (Criteria) this;
        }

        public Criteria andTradePwdInputNotBetween(Integer value1, Integer value2) {
            addCriterion("trade_pwd_input not between", value1, value2, "tradePwdInput");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagIsNull() {
            addCriterion("alipay_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagIsNotNull() {
            addCriterion("alipay_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagEqualTo(Integer value) {
            addCriterion("alipay_auth_flag =", value, "alipayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagNotEqualTo(Integer value) {
            addCriterion("alipay_auth_flag <>", value, "alipayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagGreaterThan(Integer value) {
            addCriterion("alipay_auth_flag >", value, "alipayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("alipay_auth_flag >=", value, "alipayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagLessThan(Integer value) {
            addCriterion("alipay_auth_flag <", value, "alipayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("alipay_auth_flag <=", value, "alipayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagIn(List<Integer> values) {
            addCriterion("alipay_auth_flag in", values, "alipayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagNotIn(List<Integer> values) {
            addCriterion("alipay_auth_flag not in", values, "alipayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("alipay_auth_flag between", value1, value2, "alipayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andAlipayAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("alipay_auth_flag not between", value1, value2, "alipayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagIsNull() {
            addCriterion("wechat_pay_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagIsNotNull() {
            addCriterion("wechat_pay_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagEqualTo(Integer value) {
            addCriterion("wechat_pay_auth_flag =", value, "wechatPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagNotEqualTo(Integer value) {
            addCriterion("wechat_pay_auth_flag <>", value, "wechatPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagGreaterThan(Integer value) {
            addCriterion("wechat_pay_auth_flag >", value, "wechatPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("wechat_pay_auth_flag >=", value, "wechatPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagLessThan(Integer value) {
            addCriterion("wechat_pay_auth_flag <", value, "wechatPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("wechat_pay_auth_flag <=", value, "wechatPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagIn(List<Integer> values) {
            addCriterion("wechat_pay_auth_flag in", values, "wechatPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagNotIn(List<Integer> values) {
            addCriterion("wechat_pay_auth_flag not in", values, "wechatPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("wechat_pay_auth_flag between", value1, value2, "wechatPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andWechatPayAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("wechat_pay_auth_flag not between", value1, value2, "wechatPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagIsNull() {
            addCriterion("bank_pay_auth_flag is null");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagIsNotNull() {
            addCriterion("bank_pay_auth_flag is not null");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagEqualTo(Integer value) {
            addCriterion("bank_pay_auth_flag =", value, "bankPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagNotEqualTo(Integer value) {
            addCriterion("bank_pay_auth_flag <>", value, "bankPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagGreaterThan(Integer value) {
            addCriterion("bank_pay_auth_flag >", value, "bankPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_pay_auth_flag >=", value, "bankPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagLessThan(Integer value) {
            addCriterion("bank_pay_auth_flag <", value, "bankPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagLessThanOrEqualTo(Integer value) {
            addCriterion("bank_pay_auth_flag <=", value, "bankPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagIn(List<Integer> values) {
            addCriterion("bank_pay_auth_flag in", values, "bankPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagNotIn(List<Integer> values) {
            addCriterion("bank_pay_auth_flag not in", values, "bankPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagBetween(Integer value1, Integer value2) {
            addCriterion("bank_pay_auth_flag between", value1, value2, "bankPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andBankPayAuthFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_pay_auth_flag not between", value1, value2, "bankPayAuthFlag");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagIsNull() {
            addCriterion("spot_frozen_flag is null");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagIsNotNull() {
            addCriterion("spot_frozen_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagEqualTo(Integer value) {
            addCriterion("spot_frozen_flag =", value, "spotFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagNotEqualTo(Integer value) {
            addCriterion("spot_frozen_flag <>", value, "spotFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagGreaterThan(Integer value) {
            addCriterion("spot_frozen_flag >", value, "spotFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("spot_frozen_flag >=", value, "spotFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagLessThan(Integer value) {
            addCriterion("spot_frozen_flag <", value, "spotFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagLessThanOrEqualTo(Integer value) {
            addCriterion("spot_frozen_flag <=", value, "spotFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagIn(List<Integer> values) {
            addCriterion("spot_frozen_flag in", values, "spotFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagNotIn(List<Integer> values) {
            addCriterion("spot_frozen_flag not in", values, "spotFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagBetween(Integer value1, Integer value2) {
            addCriterion("spot_frozen_flag between", value1, value2, "spotFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andSpotFrozenFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("spot_frozen_flag not between", value1, value2, "spotFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagIsNull() {
            addCriterion("c2c_frozen_flag is null");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagIsNotNull() {
            addCriterion("c2c_frozen_flag is not null");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagEqualTo(Integer value) {
            addCriterion("c2c_frozen_flag =", value, "c2cFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagNotEqualTo(Integer value) {
            addCriterion("c2c_frozen_flag <>", value, "c2cFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagGreaterThan(Integer value) {
            addCriterion("c2c_frozen_flag >", value, "c2cFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("c2c_frozen_flag >=", value, "c2cFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagLessThan(Integer value) {
            addCriterion("c2c_frozen_flag <", value, "c2cFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagLessThanOrEqualTo(Integer value) {
            addCriterion("c2c_frozen_flag <=", value, "c2cFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagIn(List<Integer> values) {
            addCriterion("c2c_frozen_flag in", values, "c2cFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagNotIn(List<Integer> values) {
            addCriterion("c2c_frozen_flag not in", values, "c2cFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagBetween(Integer value1, Integer value2) {
            addCriterion("c2c_frozen_flag between", value1, value2, "c2cFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andC2cFrozenFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("c2c_frozen_flag not between", value1, value2, "c2cFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagIsNull() {
            addCriterion("contracts_frozen_flag is null");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagIsNotNull() {
            addCriterion("contracts_frozen_flag is not null");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagEqualTo(Integer value) {
            addCriterion("contracts_frozen_flag =", value, "contractsFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagNotEqualTo(Integer value) {
            addCriterion("contracts_frozen_flag <>", value, "contractsFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagGreaterThan(Integer value) {
            addCriterion("contracts_frozen_flag >", value, "contractsFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("contracts_frozen_flag >=", value, "contractsFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagLessThan(Integer value) {
            addCriterion("contracts_frozen_flag <", value, "contractsFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagLessThanOrEqualTo(Integer value) {
            addCriterion("contracts_frozen_flag <=", value, "contractsFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagIn(List<Integer> values) {
            addCriterion("contracts_frozen_flag in", values, "contractsFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagNotIn(List<Integer> values) {
            addCriterion("contracts_frozen_flag not in", values, "contractsFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagBetween(Integer value1, Integer value2) {
            addCriterion("contracts_frozen_flag between", value1, value2, "contractsFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andContractsFrozenFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("contracts_frozen_flag not between", value1, value2, "contractsFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagIsNull() {
            addCriterion("asset_frozen_flag is null");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagIsNotNull() {
            addCriterion("asset_frozen_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagEqualTo(Integer value) {
            addCriterion("asset_frozen_flag =", value, "assetFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagNotEqualTo(Integer value) {
            addCriterion("asset_frozen_flag <>", value, "assetFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagGreaterThan(Integer value) {
            addCriterion("asset_frozen_flag >", value, "assetFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("asset_frozen_flag >=", value, "assetFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagLessThan(Integer value) {
            addCriterion("asset_frozen_flag <", value, "assetFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagLessThanOrEqualTo(Integer value) {
            addCriterion("asset_frozen_flag <=", value, "assetFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagIn(List<Integer> values) {
            addCriterion("asset_frozen_flag in", values, "assetFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagNotIn(List<Integer> values) {
            addCriterion("asset_frozen_flag not in", values, "assetFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagBetween(Integer value1, Integer value2) {
            addCriterion("asset_frozen_flag between", value1, value2, "assetFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andAssetFrozenFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("asset_frozen_flag not between", value1, value2, "assetFrozenFlag");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagIsNull() {
            addCriterion("spot_protocol_flag is null");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagIsNotNull() {
            addCriterion("spot_protocol_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagEqualTo(Integer value) {
            addCriterion("spot_protocol_flag =", value, "spotProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagNotEqualTo(Integer value) {
            addCriterion("spot_protocol_flag <>", value, "spotProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagGreaterThan(Integer value) {
            addCriterion("spot_protocol_flag >", value, "spotProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("spot_protocol_flag >=", value, "spotProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagLessThan(Integer value) {
            addCriterion("spot_protocol_flag <", value, "spotProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagLessThanOrEqualTo(Integer value) {
            addCriterion("spot_protocol_flag <=", value, "spotProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagIn(List<Integer> values) {
            addCriterion("spot_protocol_flag in", values, "spotProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagNotIn(List<Integer> values) {
            addCriterion("spot_protocol_flag not in", values, "spotProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagBetween(Integer value1, Integer value2) {
            addCriterion("spot_protocol_flag between", value1, value2, "spotProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andSpotProtocolFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("spot_protocol_flag not between", value1, value2, "spotProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagIsNull() {
            addCriterion("c2c_protocol_flag is null");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagIsNotNull() {
            addCriterion("c2c_protocol_flag is not null");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagEqualTo(Integer value) {
            addCriterion("c2c_protocol_flag =", value, "c2cProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagNotEqualTo(Integer value) {
            addCriterion("c2c_protocol_flag <>", value, "c2cProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagGreaterThan(Integer value) {
            addCriterion("c2c_protocol_flag >", value, "c2cProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("c2c_protocol_flag >=", value, "c2cProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagLessThan(Integer value) {
            addCriterion("c2c_protocol_flag <", value, "c2cProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagLessThanOrEqualTo(Integer value) {
            addCriterion("c2c_protocol_flag <=", value, "c2cProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagIn(List<Integer> values) {
            addCriterion("c2c_protocol_flag in", values, "c2cProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagNotIn(List<Integer> values) {
            addCriterion("c2c_protocol_flag not in", values, "c2cProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagBetween(Integer value1, Integer value2) {
            addCriterion("c2c_protocol_flag between", value1, value2, "c2cProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andC2cProtocolFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("c2c_protocol_flag not between", value1, value2, "c2cProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagIsNull() {
            addCriterion("portfolio_protocol_flag is null");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagIsNotNull() {
            addCriterion("portfolio_protocol_flag is not null");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagEqualTo(Integer value) {
            addCriterion("portfolio_protocol_flag =", value, "portfolioProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagNotEqualTo(Integer value) {
            addCriterion("portfolio_protocol_flag <>", value, "portfolioProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagGreaterThan(Integer value) {
            addCriterion("portfolio_protocol_flag >", value, "portfolioProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("portfolio_protocol_flag >=", value, "portfolioProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagLessThan(Integer value) {
            addCriterion("portfolio_protocol_flag <", value, "portfolioProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagLessThanOrEqualTo(Integer value) {
            addCriterion("portfolio_protocol_flag <=", value, "portfolioProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagIn(List<Integer> values) {
            addCriterion("portfolio_protocol_flag in", values, "portfolioProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagNotIn(List<Integer> values) {
            addCriterion("portfolio_protocol_flag not in", values, "portfolioProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagBetween(Integer value1, Integer value2) {
            addCriterion("portfolio_protocol_flag between", value1, value2, "portfolioProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPortfolioProtocolFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("portfolio_protocol_flag not between", value1, value2, "portfolioProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagIsNull() {
            addCriterion("perpetual_protocol_flag is null");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagIsNotNull() {
            addCriterion("perpetual_protocol_flag is not null");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagEqualTo(Integer value) {
            addCriterion("perpetual_protocol_flag =", value, "perpetualProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagNotEqualTo(Integer value) {
            addCriterion("perpetual_protocol_flag <>", value, "perpetualProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagGreaterThan(Integer value) {
            addCriterion("perpetual_protocol_flag >", value, "perpetualProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("perpetual_protocol_flag >=", value, "perpetualProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagLessThan(Integer value) {
            addCriterion("perpetual_protocol_flag <", value, "perpetualProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagLessThanOrEqualTo(Integer value) {
            addCriterion("perpetual_protocol_flag <=", value, "perpetualProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagIn(List<Integer> values) {
            addCriterion("perpetual_protocol_flag in", values, "perpetualProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagNotIn(List<Integer> values) {
            addCriterion("perpetual_protocol_flag not in", values, "perpetualProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagBetween(Integer value1, Integer value2) {
            addCriterion("perpetual_protocol_flag between", value1, value2, "perpetualProtocolFlag");
            return (Criteria) this;
        }

        public Criteria andPerpetualProtocolFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("perpetual_protocol_flag not between", value1, value2, "perpetualProtocolFlag");
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