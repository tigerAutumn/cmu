package cc.newex.dax.users.enums.membership;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserTypeEnum {
    /**
     * 个人用户
     */
    INDIVIDUAL(0, "individual"),

    /**
     * 组织类型，11-团队，12-企业，19-其他组织
     */
    TEAM(11, "team"),

    /**
     * 组织类型，11-团队，12-企业，19-其他组织
     */
    COMPANY(12, "company"),

    /**
     * 组织类型，11-团队，12-企业，19-其他组织
     */
    OTHER_ORG(19, "other_org");

    private final Integer code;
    private final String name;

    UserTypeEnum(final Integer code, final String name) {
        this.code = code;
        this.name = name;
    }

    public static UserTypeEnum parse(final Integer code) {
        return Arrays.stream(UserTypeEnum.values())
                .filter(x -> x.code.equals(code))
                .findFirst()
                .orElse(null);
    }

}
