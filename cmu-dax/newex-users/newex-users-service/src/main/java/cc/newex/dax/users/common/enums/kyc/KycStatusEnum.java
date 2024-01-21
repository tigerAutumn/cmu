package cc.newex.dax.users.common.enums.kyc;

import com.alibaba.druid.util.StringUtils;

/**
 * @description kyc 状态枚举
 * @date 2018/5/23 下午5:19
 */
public enum KycStatusEnum {
    //    认证状态0：初始值，1：通过，2：驳回，3：其他异常，4：审核中
    KYC_STATUS_0(0, "init", "初始值"),
    KYC_STATUS_1(1, "pass", "通过"),
    KYC_STATUS_2(2, "reject", "驳回"),
    KYC_STATUS_3(3, "other_exception", "其他异常"),
    KYC_STATUS_4(4, "audit", "审核中");
    private final int type;

    private final String value;

    private final String desc;


    KycStatusEnum(final int type, final String value, final String desc) {
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

    public static KycStatusEnum getKycStatusEnum(final int type) {
        if (type <= 0) {
            return null;
        }
        for (final KycStatusEnum kycStatusEnum : KycStatusEnum.values()) {
            if (kycStatusEnum.type == type) {
                return kycStatusEnum;
            }
        }
        return null;
    }

    public static KycStatusEnum getKycStatusEnum(final String value) {

        for (final KycStatusEnum kycStatusEnum : KycStatusEnum.values()) {
            if (StringUtils.equals(value, kycStatusEnum.getValue())) {
                return kycStatusEnum;
            }
        }
        return null;
    }

}
