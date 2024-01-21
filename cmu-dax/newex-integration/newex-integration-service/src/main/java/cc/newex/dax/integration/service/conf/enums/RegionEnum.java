package cc.newex.dax.integration.service.conf.enums;

import lombok.Getter;

@Getter
public enum RegionEnum {
    /**
     * 中国大陆地区
     */
    CHINA("china"),
    /**
     * 国际(非中国大陆地区)
     */
    INTERNATIONAL("international"),
    /**
     * 全部(所有地区)
     */
    ALL("all");

    private final String name;

    RegionEnum(final String name) {
        this.name = name;
    }
}
