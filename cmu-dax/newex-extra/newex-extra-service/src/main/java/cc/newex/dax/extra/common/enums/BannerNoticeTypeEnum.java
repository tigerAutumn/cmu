package cc.newex.dax.extra.common.enums;

/**
 * 首页banner与notice类型
 *
 * @author newex-team
 * @date 2018-04-09
 */
public enum BannerNoticeTypeEnum {
    /**
     * 广告banner
     */
    BANNER("banner", 1),
    /**
     * 新闻公告
     */
    NOTICE("notice", 2),
    /**
     * 轮播图
     */
    WHEEL("wheel", 3),
    /**
     * APP启动页
     */
    SPLASH("splash", 4);

    private final String name;
    private final int code;

    BannerNoticeTypeEnum(final String name, final int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public int getCode() {
        return this.code;
    }

    public static BannerNoticeTypeEnum forName(final String name) {
        for (final BannerNoticeTypeEnum e : values()) {
            if (e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return BannerNoticeTypeEnum.BANNER;
    }

    public static BannerNoticeTypeEnum forCode(final int code) {
        for (final BannerNoticeTypeEnum e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return BannerNoticeTypeEnum.BANNER;
    }
}