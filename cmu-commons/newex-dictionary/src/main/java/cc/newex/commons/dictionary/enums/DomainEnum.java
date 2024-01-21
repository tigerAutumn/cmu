package cc.newex.commons.dictionary.enums;

/**
 * newex公司应用部署的域名枚举
 *
 * @author newex-team
 * @date 2017/12/09
 */
public enum DomainEnum {
    /**
     * newex.cc
     */
    NEWEX_CC("newex.cc"),;

    private final String name;

    DomainEnum(final String name) {
        this.name = name;
    }

    /**
     * 枚举名称
     *
     * @return 枚举名称
     */
    public String getName() {
        return this.name;
    }
}
