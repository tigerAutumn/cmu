package cc.newex.dax.users.common.enums.security;

/**
 * @author newex-team
 * @description 重设错误次数枚举类
 * @return
 * @date 2018/03/18
 */
public enum BehaviorErrorEnum {
    EMAIL_REG_ERROR(3011, "邮箱注册失败"),
    PHONE_REG_ERROR(3012, "手机注册失败"),
    REG_INCR_ERROR(3013, "注册累加失败"),
    USER_LOGIN_ERROR(3021, "用户登录失败"),
    MNODIFY_LOGIN_ERROR(3032, "手机修改失败"),
    GOOGLE_BIND_ERROR(3051, "谷歌绑定失败"),
    GOOGLE_RESET_ERROR(3052, "谷歌重置失败"),
    GOOGLE_TRADE_VALIDATE_ERROR(3053, "交易时，谷歌验证开关"),
    GOOGLE_LOGIN_VALIDATE_ERROR(3054, "登录时，谷歌验证开关"),
    TRADE_PWD_ERROR(3061, "资金密码设置失败"),
    TRADE_PWD_RESET_ERROR(3062, "资金密码找回（重置）失败"),
    TRADE_PWD_MODIFY_ERROR(3063, "资金密码修改失败"),
    TRADE_PWD_DEAL_ERROR(3064, "资金密码输入频次失败"),
    API_ADD_ERROR(3071, "api创建"),
    API_DETAIL_ERROR(3072, "api查看详情"),
    API_UPDATE_ERROR(3073, "api修改"),
    API_DEAL_ERROR(3074, "api删除"),
    CAPTCHA_QUERY_ERROR(3081, "请求图片验证码"),
    SUBACCOUNT_ADD_ERROR(3101, "添加子账户");
    /**
     * 错误类型
     */
    private final int type;
    /**
     * 错误描述
     */
    private final String desc;

    BehaviorErrorEnum(final int type, final String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static boolean isContain(final int type) {
        if (type <= 0) {
            return false;
        }
        for (final BehaviorErrorEnum bee : BehaviorErrorEnum.values()) {
            if (bee.type == type) {
                return true;
            }
        }
        return false;
    }

    public int getType() {
        return this.type;
    }

    public String getDesc() {
        return this.desc;
    }

}
