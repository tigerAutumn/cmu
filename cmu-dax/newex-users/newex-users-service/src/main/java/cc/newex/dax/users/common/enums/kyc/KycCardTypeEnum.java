package cc.newex.dax.users.common.enums.kyc;

import com.alibaba.druid.util.StringUtils;

public enum KycCardTypeEnum {
    KYC_IMGS_FRONT(1, "id_card", "身份证"),
    KYC_IMGS_BACK(2, "passport", "护照"),
    KYC_IMGS_CARD(3, "driver", "驾驶证");
    /**
     * kyc认证图片类型
     */
    private final int type;
    /**
     * kyc认证图片名称
     */
    private final String value;
    /**
     * kyc认证描述
     */
    private final String desc;

    KycCardTypeEnum(final int type, final String value, final String desc) {
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

    public static KycCardTypeEnum getKycCardTypeEnum(final int type) {
        if (type <= 0) {
            return null;
        }
        for (final KycCardTypeEnum kycCardTypeEnum : KycCardTypeEnum.values()) {
            if (kycCardTypeEnum.type == type) {
                return kycCardTypeEnum;
            }
        }
        return null;
    }

    public static KycCardTypeEnum getKycCardTypeEnum(final String value) {
        for (final KycCardTypeEnum kycCardTypeEnum : KycCardTypeEnum.values()) {
            if (StringUtils.equals(value, kycCardTypeEnum.getValue())) {
                return kycCardTypeEnum;
            }
        }
        return null;
    }

}
