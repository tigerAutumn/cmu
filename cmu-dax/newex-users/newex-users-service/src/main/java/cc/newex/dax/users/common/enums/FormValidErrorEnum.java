package cc.newex.dax.users.common.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户表单验证错误码
 *
 * @author newex-team
 * @date 2018/03/18
 */
public enum FormValidErrorEnum implements ErrorCode {

    FORM_USERNAME_CODE("username"),
    FORM_PASSWORD_CODE("password"),
    FORM_AREACODE_CODE("areaCode"),
    FORM_MOBILE_CODE("mobile"),
    FORM_EMAIL_CODE("email"),
    FORM_LOGINNAME_CODE("loginName"),
    FORM_SERIALNO_CODE("serialNO"),
    FORM_BEHAVIOR_CODE("behavior"),
    FORM_GOOGLECODE_CODE("googleCode"),
    FORM_EMAILCODE_CODE("emailCode"),
    FORM_MOBILECODE_CODE("mobileCode"),
    FORM_OLDGOOGLECODE_CODE("oldGoogleCode"),
    FORM_NEWVERIFICATIONCODE_CODE("newVerificationCode"),
    FORM_OLDVERIFICATIONCODE_CODE("oldVerificationCode"),
    FORM_ORIGINPWD_CODE("originPwd"),
    FORM_NEWPWD_CODE("newPwd"),
    FORM_RENEWPWD_CODE("reNewPwd"),
    FORM_LABEL_CODE("label"),
    FORM_CONFIRMPASSWORD_CODE("confirmPassword"),
    FORM_PASSPHRASE_CODE("passphrase"),
    FORM_VERIFICATIONCODE_CODE("verificationCode");

    private final String code;

    FormValidErrorEnum(final String code) {
        this.code = code;
    }

    public static String getFormCode(final String code) {
        for (final FormValidErrorEnum em : FormValidErrorEnum.values()) {
            if (StringUtils.equals(code, em.code)) {
                return LocaleUtils.getMessage("error.code.biz." + code);
            }
        }
        return null;
    }


    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage("error.code.biz." + this.code);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }

}
