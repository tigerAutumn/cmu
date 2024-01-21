package cc.newex.dax.extra.enums.cgm;

import java.util.Objects;

/**
 * 项目审核状态 0:待审核 1:初始审核 2:待上线 3:已上线  -1:拒绝
 *
 * @author liutiejun
 * @date 2018-08-10
 */
public enum ProjectStatusEnum {
    REJECT(-1, "reject"),
    CHECK(0, "check"),
    PASS(1, "pass"),
    WAIT(2, "wait"),
    ONLINE(3, "online");

    private final Integer code;
    private final String name;

    ProjectStatusEnum(final Integer code, final String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static ProjectStatusEnum getByCode(final Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }

        switch (code) {
            case -1:
                return REJECT;
            case 0:
                return CHECK;
            case 1:
                return PASS;
            case 2:
                return WAIT;
            case 3:
                return ONLINE;
            default:
                return null;
        }
    }

}
