package cc.newex.dax.users.common.enums;

import lombok.Getter;

@Getter
public enum SiteDomainEnum {
    /**
     * 站点
     */
    BITMORE("bitmore"),
    COINMEX("coinmex"),
    COINTOBE("cointobe");
    /**
     * 类型
     */
    private final String domain;

    SiteDomainEnum(final String domain) {
        this.domain = domain;
    }
}
