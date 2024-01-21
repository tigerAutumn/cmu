package cc.newex.dax.users.enums.subaccount;

import lombok.Getter;

/**
 * 组织类型，11-团队，12-企业，19-其他组织，和UserInfo中的type字段对应
 *
 * @author liutiejun
 * @date 2018-11-01
 */
@Getter
public enum OrgTypeEnum {

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

    OrgTypeEnum(final Integer code, final String name) {
        this.code = code;
        this.name = name;
    }

}
