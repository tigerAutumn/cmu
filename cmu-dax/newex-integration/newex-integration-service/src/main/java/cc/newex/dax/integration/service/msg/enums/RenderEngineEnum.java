package cc.newex.dax.integration.service.msg.enums;

import lombok.Getter;

/**
 * RenderEngine
 */
@Getter
public enum RenderEngineEnum {
    /**
     * thymeleaf
     */
    THYMELEAF("thymeleaf"),
    /**
     * string replace
     */
    STRING_REPLACE("replace");

    private final String name;

    RenderEngineEnum(final String name) {
        this.name = name;
    }
}
