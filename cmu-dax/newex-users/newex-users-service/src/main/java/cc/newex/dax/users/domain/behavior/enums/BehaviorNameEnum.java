package cc.newex.dax.users.domain.behavior.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户中心行为名称
 *
 * @author newex-team
 * @date 2018/04/12
 */
public enum BehaviorNameEnum {
    /**
     * 邮箱注册
     */
    REGISTER_EMAIL(1001, "register_email"),
    /**
     * 手机注册
     */
    REGISTER_MOBILE(1002, "register_mobile"),
    /**
     * 用户名检查
     */
    REGISTER_CHECK_USERNAME(1004, "register_check_username"),
    /**
     * 用户登录
     */
    LOGIN(1101, "login"),
    /**
     * 登录二次验证
     */
    LOGIN_STEP2_AUTH(1102, "login_step2_auth"),
    /**
     * 登录密码修改
     */
    LOGIN_PWD_MODIFY(1103, "login_pwd_modify"),
    /**
     * 登录密码找回(重置)
     */
    LOGIN_PWD_RESET(1104, "login_pwd_reset"),
    /**
     * 手机绑定
     */
    MOBILE_BIND(1201, "mobile_bind"),
    /**
     * 手机修改
     */
    MOBILE_MODIFY(1202, "mobile_modify"),
    /**
     * 关闭手机验证
     */
    MOBILE_VERFICATION_CLOSE(1203, "mobile_verification_close"),
    /**
     * 邮箱绑定
     */
    EMAIL_BIND(1301, "email_bind"),
    /**
     * 邮箱激活
     */
    EMAIL_ACTIVE(1302, "email_active"),
    /**
     * 邮箱修改
     */
    EMAIL_MODIFY(1303, "email_modify"),
    /**
     * 邮箱防钓鱼码
     */
    EMAIL_ANTI_CODE(1304, "email_anti_code"),
    /**
     * 谷歌码绑定
     */
    GOOGLE_CODE_BIND(1401, "google_code_bind"),
    /**
     * 谷歌码重置绑定
     */
    GOOGLE_CODE_RESET(1402, "google_code_reset"),
    /**
     * 关闭谷歌验证
     */
    GOOGLE_VERFICATION_CLOSE(1403, "google_verification_close"),
    /**
     * 资金密码设置
     */
    TRADE_PWD_SET(1501, "trade_pwd_set"),
    /**
     * 资金密码找回（重置）
     */
    TRADE_PWD_RESET(1502, "trade_reset_pwd"),
    /**
     * 资金密码修改
     */
    TRADE_PWD_MODIFY(1503, "trade_pwd_modify"),
    /**
     * 资金密码输入频次
     */
    TRADE_PWD_FREQUENCY(1504, "trade_pwd_frequency"),
    /**
     * api创建
     */
    API_KEY_APPLY(1601, "api_key_apply"),
    /**
     * api查看详情
     */
    API_KEY_DETAIL(1602, "api_key_detail"),
    /**
     * api修改
     */
    API_KEY_MODIFY(1603, "api_key_modify"),
    /**
     * api删除
     */
    API_KEY_DELETE(1604, "api_key_delete"),
    /**
     * 子账户添加
     */
    SUB_ACCOUNT_ADD(1801, "sub_account_add"),
    /**
     * 子账户删除
     */
    SUB_ACCOUNT_DELETE(1802, "sub_account_delete"),
    /**
     * 子账户重置密码
     */
    SUB_ACCOUNT_PWD_MODIFY(1803, "sub_account_pwd_modify"),
    /**
     * 子账户冻结
     */
    SUB_ACCOUNT_FREEZE(1804, "sub_account_freeze"),
    /**
     * 子账修改备注
     */
    SUB_ACCOUNT_REMARK(1805, "sub_account_remark"),
    /**
     * 子账户资金划转
     */
    SUB_ACCOUNT_TRANSFER(1806, "sub_account_transfer"),

    /**
     * 提币
     */
    WITHDRAW_ASSET(1807, "withdraw_asset"),
    /**
     * 登录二次验证开关
     */
    LOGIN_STEP2_AUTH_SWITCH(1808, "login_step2_auth_switch"),
    /**
     * 法币交易-设置银行卡
     */
    FIAT_BANK_SETTING(1809, "fiat_bank_setting"),
    /**
     * 法币交易-设置alipay
     */
    FIAT_ALIPAY_SETTING(1810, "fiat_alipay_setting"),
    /**
     * 法币交易-设置微信支付
     */
    FIAT_WEPAY_SETTING(1811, "fiat_wepay_setting"),

    /**
     * 点卡转让
     */
    POINTCARD_TRANSFER(3092, "pointcard_transfer");

    /**
     * 操作类别标识
     */
    private final int id;

    /**
     * 操作类别名称
     */
    private final String name;

    BehaviorNameEnum(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @param name
     * @description 通过获取用户中心行为
     */
    public static Integer getBehaviorByName(final String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (final BehaviorNameEnum nameEnum : BehaviorNameEnum.values()) {
            if (StringUtils.equalsIgnoreCase(name, nameEnum.name)) {
                return nameEnum.getId();
            }
        }
        return null;
    }

    /**
     * @param id
     * @description 通过id获取用户中心行为
     */
    public static String getBehaviorById(final Integer id) {
        if (id <= 0) {
            return null;
        }
        for (final BehaviorNameEnum nameEnum : BehaviorNameEnum.values()) {
            if (id == nameEnum.getId()) {
                return nameEnum.name;
            }
        }
        return null;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

}
