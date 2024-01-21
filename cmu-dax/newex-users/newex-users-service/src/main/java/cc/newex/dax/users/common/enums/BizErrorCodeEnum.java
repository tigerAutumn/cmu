package cc.newex.dax.users.common.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;

/**
 * 业务错误码
 *
 * @author newex-team
 * @date 2018/03/18
 */
public enum BizErrorCodeEnum implements ErrorCode {
    TRANSACTION_SUCCESS(0),
    SYSTEM_ERROR(1),
    REFRESH_TOKEN_FAILURE(11001),

    /**
     * KYC相关错误码
     */
    ERROR_REJECT_NO_REASON(3000),           //KYC驳回必须填写原因和备注
    ERROR_CHECK_TEMPLATE(3001),
    ERROR_CHECK_IDCARD(3002),
    ERROR_IDCARD_USE(3003),
    ERROR_CUSER_INSERT(3004),
    ERROR_CHECK_KYC1(3005),
    ERROR_CHECK_KYC2(3006),
    ERROR_NO_IMAGE(3007),
    ERROR_USER_KYC2_NOPASS_FAILED(3008),                      //高级认证失败,请重试!
    ERROR_FACEID_SIGN(3009),                                  //签名不一致,验证失败
    ERROR_KYC_CARDNUMBER_FOUND(3010),                         //该kyc证件号码已被认证

    /**
     * KYC活体认证失败
     */
    ERROR_MEGLIVE_FAILED(3011),
    /**
     * KYC活体认证非本人
     */
    ERROR_MEGLIVE_NOT_ONESELF_FAILED(3012),
    /**
     * KYC证件不清晰
     */
    ERROR_USER_FACEID_PRECISIONLESS_FAILED(3013),
    ERROR_USER_FACEID_FRONT_FAILED(3014),        //请重新上传正面照
    ERROR_USER_FACEID_BACK_FAILED(3015),        //请重新上传国徽照
    ERROR_KYC1_SAVE_FAILED(3018),               //kyc1保存信息失败
    ERROR_KYC2_SAVE_FAILED(3019),               //kyc2保存信息失败
    ERROR_KYC_STATUS_FAILED(3020),              //KYC认证通过后不能修改
    ERROR_KYC_IMG_FAILED(3021),                 //上传证件非法,请重新上传证件照
    ERROR_UPLOAD_IMAGE_FAILED(3022),            //上传证件失败
    ERROR_KYC_INFO_NOT_FOUND(3023),             //KYC认证信息不存在
    ERROR_KYC_INFO_EXIST(3024),                 //KYC认证信息已提交
    ERROR_USER_TOKEN_TIMEOUT_FAILED(3025),      //信息填写超时，请重新进行认证
    ERROR_USER_KYC_IDCARD_FAILED(3026),         //用户身份证号不符
    ERROR_USER_KYC1_NOPASS_FAILED(3027),        //用户KYC1没有通过
    ERROR_USER_KYC2_PASS_FAILED(3028),          //用户已经完成KYC2认证
    ERROR_USER_KYC_PAYLOAD_TOO_LARGE(3029),     //KYC证件大于2M
    ERROR_USER_GET_KYC_TOKEN_TIMEOUT(3030),     //高级认证授权获取失败,请重试!
    ERROR_USER_KYC1_PASS_FAILED(3041),          //用户已经完成KYC1认证
    ERROR_USER_KYC1_AGE_ILLEGAL(3042),          //用户年龄非法

    /**
     * 登录错误码相关 3150 ～ 3149
     **/
    LOGIN_LOGINNAME_OR_PASSWORD_ERROR(3100),           //3100 = 登录名或者密码不正确
    LOGIN_COOKIE_UNOPEN(3101),                     //3101 = cookie不支持
    LOGIN_USER_ACCOUNT_FROZEN(3102),                   //3102 = 用户被冻结
    LOGIN_USER_ALREADY_LANDED(3103),                   //3103 = 用户已经登录
    LOGIN_USER_AUTHORISE_FAIL(3104),                   //3104 = 用户登录授权失败
    LOGIN_LOGINSTEP2_FAIL(3105),                    //3105 = 二次验证失败
    LOGIN_LOGINNAME_FORMAT_ERROR(3106),             //3106 =  登录名格式不正确
    LOGIN_IPINFO_NOTEXIST(3107),                     //3107 =无法识别的用户登录地址
    LOGIN_USER_DELETED(3108),                       //3108 =用户已被删除
    LOGIN_MASTER_ACCOUNT_UNSUPPORT(3109),           //3109 =主账号请使用邮箱或手机号登录
    LOGIN_LOGINSTEP2_TOKEN_ERROR(3110),           //3110 =验证已过期，请重新获取

    /**
     * 注册错误码相关3150 ～ 3199
     **/
    REGISTER_BAD_MOBILE_FORMAT(3150),  //3150 = 错误的手机格式
    REGISTER_BAD_EMAIL_FORMAT(3151),   //3151 = 错误的邮箱格式
    REGISTER_PASSWORD_NOT_EQUAL(3152), //3152 = 两次输入的密码不一致
    REGISTER_USER_EXIST(3153),         //3153 = 用户已经存在
    REGISTER_NOT_SUPPORT_CHINA(3154),  //3154 = 中国地区不支持
    REGISTER_PARAMS_ILLEGAL(3155),     //3155 = 参数非法
    REGISTER_COUNTRY_UNSUPPORT(3156),  //3156 = 不支持的国家地区
    REGISTER_SAVE_FAILED(3157),        //3157 = 注册保存失败
    REGISTER_PASSWORD_SIMPLE(3158),    //3158 = 密码必须字母加数字

    /**
     * APIKEY 错误码相关3200 ～ 3249
     **/
    APIKEY_OPERATE_FAIL(3200),          //3200 = 操作失败
    APIKEY_NUMBER_TOO_MUCH(3201),       //3201 = 数字太大
    APIKEY_NAME_REPEAT(3202),           //3202 = API的备注已存在
    APIKEY_NOT_EXIST(3203),             //3203 = 查询不到Apikey
    APIKEY_ADD_MAX_LIMIT(3204),         //3204 = API超过添加的最大限制
    API_IP_RATE_LIMIT_NOT_EXIST(3205),  //3205= 查询不到限流ip

    //开关验证码相关
    SWITCH_OPEN_GOOGLE_FAIL(3220), //3220=开启谷歌验证失败
    SWITCH_CLOSED_GOOGLE_FAIL(3221), //3221=关闭谷歌验证失败
    SWITCH_OPEN_SMS_FAIL(3222), //3222=开启短信验证失败
    SWITCH_CLOSED_SMS_FAIL(3223), //3223=关闭短信验证失败
    SWITCH_CANTNOT_CLOSED_GOOGLESMS(3224), //3224=不能同时关闭谷歌和短信
    SWITCH_GOOGLESMS_ALL_CLOSED(3225), //3225 = 谷歌和短信验证都没有开启
    SWITCH_CORPORATE_CANNOT_GOOGLE_CLOSED(3226), //3226 = 企业用户不可关闭谷歌验证码

    //谷歌验证码  3310 -3319
    GOOGLE_KEY_GENERATE_FAILED(3310),         //3310 = 谷歌密钥生成失败
    GOOGLE_KEY_EMPTY(3311),                   //3311 = 谷歌密钥为空
    GOOGLE_CODE_TIMES_LIMIT(3312),            //3312 = 谷歌验证码使用太频繁
    GOOGLE_CODE_VERIFY_GOOGLE_TWICE(3313),     //  3313 = 谷歌验证码每个只能使用一次。
    GOOGLE_CODE_VERIFY_ERROR(3314),           //  3314 = 谷歌验证错误
    GOOGLE_CODE_VERIFY_OVERDUE(3315),         // 3315 =  谷歌验证码过期
    GOOGLE_CODE_FORMAT_ERROR(3316),            //3316 = 谷歌验证码格式错误
    GOOGLE_CODE_ORIGIN_ERROR(3317),           //  3317 = 旧谷歌验证错误

    //短信验证码 3320 - 3329
    SMS_CODE_TIMES_LIMIT(3321),              //3321 = 短信验证码使用太频繁
    SMS_CODE_VERIFY_GOOGLE_TWICE(3322),     //  3322 = 短信验证码每个只能使用一次。
    SMS_CODE_VERIFY_ERROR(3323),           //  3323 = 短信验证错误
    SMS_CODE_SEND_FAIL(3324),           //  3324 = 短信验证码发送失败
    SMS_CODE_FORMAT_ERROR(3325),       //  3325 = 短信验证码格式错误
    SMS_CODE_VERIFY_OVERDUE(3326),           // 3326 =  短信验证码过期
    SMS_NEW_CODE_VERIFY_ERROR(3327),       //  3327 = 新手机短信验证错误
    SMS_OLD_CODE_VERIFY_ERROR(3328),       //  3328 = 旧手机短信验证错误

    //邮箱验证码
    EMAIL_CODE_TIMES_LIMIT(3331),              //3331 = 邮箱验证码使用太频繁
    EMAIL_CODE_VERIFY_GOOGLE_TWICE(3332),     //  3332 = 邮箱验证码每个只能使用一次。
    EMAIL_CODE_VERIFY_ERROR(3333),           //  3333 = 邮箱验证错误
    EMAIL_CODE_SEND_FAIL(3334),             //3334 = 邮箱验证码发送失败
    EMAIL_CODE_FORMAT_ERROR(3335),       //  3335 =  邮箱验证码格式错误
    EMAIL_CODE_VERIFY_OVERDUE(3336),           // 3336 =  邮箱验证码过期

    //未绑定 3340 - 3349
    UNBIND_PHONE(3340), // 3340 = 用户没绑定手机
    UNBIND_GOOGLE(3341), // 3341 = 用户没绑定谷歌
    UNBIND_EMAIL(3342), // 3342 = 用户没绑定邮箱
    UNBIND_PHONE_AND_GOOGLE(3344), //3344 = 没有绑定谷歌验证码和手机
    UNBIND_PHONE_AND_EMAIL(3345), //3345 = 没有绑定手机和邮箱
    UNBIND_GOOGLE_AND_EMAIL(3346), //3346 = 没有绑定谷歌和邮箱
    UNBIND_GOOGLE_ERROR(3347), //3347 = 解绑谷歌失败

    //邮箱相关 3350 -3379
    EMAIL_CANNOT_MODIFY(3350),  //3350 = 邮箱不可更改
    EMAIL_HAS_USED(3351),    //3351 = 邮箱已经被使用
    EMAIL_FORMAT_ERROR(3354), // 3354 = 邮箱格式错误
    EMAIL_VERTIFY_BAD(3355),     // 3355 = 设置防钓鱼码失败

    // 子账号相关3360-3369
    SUBACCOUNT_HAS_ASSET(3360),       //该子账户有未转出的资产，暂不能删除该子账户
    SUBACCOUNT_CLOSE_FAILED(3361),    //删除子账户失败
    SUBACCOUNT_LOCKED_FAILED(3362),   //子账户冻结成功
    SUBACCOUNT_UNLOCKED_FAILED(3363), //子账户解冻成功
    SUBACCOUNT_ADD_MAX_LIMIT(3364),   //子账号超过添加的最大限制

    //手机相关 3380 - 3419
    PHONE_CANNOT_MODIFY(3380),  //3380 = 手机不可更改
    PHONE_HAS_USED(3381),    //3381 = 手机已经被使用
    PHONE_EQUAL_OLD(3382),   //3382 = 新老手机相同
    PHONE_MODIFY_FAILED(3383), //3383 = 手机修改失败
    PHONE_FORMAT_ERROR(3384), //3384 =  手机格式错误
    PHONE_EMPTY(3385), //3385 = 手机为空
    EMAIL_EMPTY(3386), //3386 = 邮箱为空

    //交易密码
    TRADE_PWD_EMPTY(3410),                     //3410=资金密码为空
    TRADE_PWD_ERROR(3411),                     //3411=资金密码输入错误
    TRADE_PWD_FORMAT_ERROR(3412),              //3412=资金密码输入错误

    //登录密码
    LOGIN_PWD_EMPTY(3420),                     //3420=登录密码为空
    LOGIN_PWD_ERROR(3421),                     //3421=登录密码输入错误
    LOGIN_PWD_FORMAT_ERROR(3422),              //3422=登录密码输入错误
    LOGIN_PWD_ONLY_FIND_BY_MASTER(3423),       //3423=子账户无法重置密码，请联系您的母账号进行重置

    //密码公用
    PWD_CONFIRM_NOT_EQUAL(3430),              //3430=二次密码输入错误
    PWD_ORIGINAL_ERROR(3431),                  //3431=原密码错误
    PWD_LOGINPWD_AND_TRADEPWD_EQUAL(3432),     //3432=登录密码和资金密码相等
    PWD_SETTING_ERROR(3433),                   //3433= 密码设置失败
    PWD_NOT_EQUALS_ORIGINAL(3434),             //3434= 新密码不能与旧密码相同

    //法币设置错误码
    ERROR_FIAT_EXISTS_FAILED(3500),             //法币设置已经存在
    ERROR_FIAT_SAVE_FAILED(3501),               //法币设置保存失败
    ERROR_FIAT_SAVEALIPAY_FAILED(3502),         //请先设置银行卡
    ERROR_FIAT_PAYLOAD_TOO_LARGE(3503),         //收款码大小大于5M
    ERROR_FIAT_SETTING_FAILED(3504),            //开关设置失败
    ERROR_FIAT_CHECK_BANK_FAILED(3505),            //银行卡验证失败
    ERROR_FIAT_NOT_BIND_BANK_FAILED(3506),            //请先绑定银行卡
    ERROR_FIAT_ALIPAY_EXISTS_FAILED(3507),            //支付宝收款码已经存在
    ERROR_FIAT_ALIPAY_NOT_EXISTS_FAILED(3508),        //支付宝收款码不存在
    ERROR_FIAT_WECHATPAY_EXISTS_FAILED(3509),         //微信收款码已经存在
    ERROR_FIAT_WECHATPAY_NOT_EXISTS_FAILED(3510),     //微信收款码不存在
    BOSS_USER_FIAT_FREEZE_FAILED(3511),               //法币出入金冻结失败
    ERROR_FIAT_PAYMENT_LIMIT_ONE(3512),               //至少绑定一种支付方式

    /**
     * 3700+ ～ 3800+ BOSS新后台错误码相关
     **/
    BOSS_USER_NOT_EXIST(3700),
    BOSS_PARAMS_ILLEGAL(3701),
    BOSS_BAD_DATA(3702),
    BOSS_USER_MODIFY_REALNAME_ERROR(3703),
    BOSS_USER_MODIFY_IDENTITYNO_ERROR(3704),
    BOSS_USER_UNBIND_GOOGLE_FAILED(3705),
    BOSS_USER_RESET_ERROR_COUNT_FAILED(3706),
    BOSS_USER_EMAIL_CANNOT_MODIFIED(3707),
    BOSS_USER_MODIFY_REALNAME_EQUAL(3708),//修改用户名称相同

    /**
     * 3900+  公共错误
     **/
    COMMON_PARAMS_EMPTY(3900), //3900 = 必填参数为空
    COMMON_BAD_DATA(3901),     //3901 = 错误的参数
    COMMON_PARAMS_ILLEGAL(3902), // 3902 = 参数非法
    COMMON_USER_NOT_LOGIN(3904), // 3904 = 用户没登录
    COMMON_USER_NOT_EXIST(3905), // 3905  = 用户不存在
    COMMON_ILLEGALITY_ACCESS(3906), // 3906 = 非法访问
    COMMON_IP_LIMIT(3907),  // 3907 = IP地址被限制
    COMMON_LOGINNAME_LIMIT(3908), //3908 = 登录名被限制
    COMMON_OPERATING_FREQUENCY(3909),//3909 = 操作太频繁
    COMMON_UPDATE_FAIL(3910), //3910 = 更新失败
    COMMON_CREATE_FAIL(3911), //3911 = 创建失败
    COMMON_DELETE_FAIL(3912), //3912 = 删除失败
    COMMON_READ_FAIL(3913),   //3913 = 读取失败
    COMMON_BINDING_FAIL(3914), //3914 = 绑定失败
    COMMON_IMAGE_VERIFICATION_CODE_ERROR(3915), //3915= 图片验证码错误
    COMMON_UNSUPPORT_OPERATE(3916), //3916=不支持的操作
    COMMON_USER_DEVICE_LIMIT(3917),// 设备被限制
    COMMON_VERIFICATION_CODE_EXPIRED(3918), // 图片验证码已失效，请刷新重试
    COMMON_VERIFICATION_CODE_ERROR(3919), // 图片验证码填写错误

    // user behavior
    NOT_FOUND_BEHAVIOR_NAME(3921),//没有找到对应的behavior
    NOT_CONTAINS_BEHAVIOR_NAME(3922),//请求的behavior不完整

    // 收藏夹
    FAVORITES_TYPE_ERROR(4000),
    FAVORITES_ADD_ERROR(4001),
    FAVORITES_UPDATE_ERROR(4002),

    //activity
    ACTIVITY_REGISTER_INVITE_ERROR(4010),       //邀请码无效
    ACTIVITY_TYPE_INVITE_ERROR(4011),           //活动已结束

    //withdraw
    WITHDRAW_LIMIT_EMAIL_ERROR(4030),   //为了您的资金安全，请先绑定邮箱，才可发起提现
    WITHDRAW_LIMIT_MOBILE_GOOGLE_ERROR(4031), //为了您的资金安全，请先绑定移动设备（手机短信验证或谷歌验证）
    WITHDRAW_LIMIT_KYC1_ERROR(4032), //提现必须完成基本信息认证
    WITHDRAW_LIMIT_TODAY_ERROR(4033), //修改过安全设置内24小时，不可提现
    WITHDRAW_LIMIT_TOADDRESS_EMPTY_ERROR(4034), //提现地址不能为空
    WITHDRAW_CHECK_CARDNUMBER_ERROR(4035), //提现验证身份证号码错误

    USER_LEVEL_NOT_EXISTS(4100), //用户等级不存在
    USER_LEVEL_UPDATE_ERROR(4101), //用户等级更新失败

    //请求太频繁已被限流
    REQUEST_TOO_FREQUENTLY_HAS_BEEN_RESTRICTED(4036),

    LOGON_FAILURE_YOU_STILL_HAVE_CHANCES(4037),

    LOGON_FAILURE_EXCEED_MAX_TIMES(4038),

    //充值地址不能为空
    EXCHANGE_TOADDRESS_EMPTY_ERROR(4039);

    private final int code;

    BizErrorCodeEnum(final int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage("error.code.biz." + this.code);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }

    public String getMessage(final Object[] args) {
        return LocaleUtils.getMessage("error.code.biz." + this.code, args);
    }
}
