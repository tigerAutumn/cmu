package cc.newex.dax.users.common.enums.kyc;

import com.alibaba.druid.util.StringUtils;

/**
 * @description kyc认证枚举
 * @date 2018/5/4 下午4:19
 */
public enum KycImgsEnum {

    KYC_IMGS_FRONT(1, "front_img", "身份证正面照片"),
    KYC_IMGS_BACK(2, "back_img", "身份证背面照片"),
    KYC_IMGS_CARD(3, "card_img", "护照，驾驶证，身份证照片"),
    KYC_IMGS_HANDS(4, "hands_img", "手持证件照"),
    PHONE_REG_ADDRESS(5, "address_img", "住址证明");
    /**
     * kyc认证图片类型
     */
    private final int type;
    /**
     * kyc认证图片名称
     */
    private final String value;
    /**
     * kyc认证图片描述
     */
    private final String desc;

    KycImgsEnum(final int type, final String value, final String desc) {
        this.type = type;
        this.value = value;
        this.desc = desc;
    }

    public int getType() {
        return this.type;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getValue() {
        return this.value;
    }

    public static KycImgsEnum getKycImgsEnum(final int type) {
        if (type <= 0) {
            return null;
        }
        for (final KycImgsEnum kycImgsEnum : KycImgsEnum.values()) {
            if (kycImgsEnum.type == type) {
                return kycImgsEnum;
            }
        }
        return null;
    }

    public static KycImgsEnum getKycImgsEnum(final String value) {

        for (final KycImgsEnum kycImgsEnum : KycImgsEnum.values()) {
            if (StringUtils.equals(value, kycImgsEnum.getValue())) {
                return kycImgsEnum;
            }
        }
        return null;
    }

}
