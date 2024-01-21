package cc.newex.commons.support.consts;

import org.apache.commons.lang3.StringUtils;

/**
 * 应用环境常量
 *
 * @author newex-team
 * @date 2017/12/09
 **/
public class AppEnvConsts {
    /**
     * http context 路径境配置项
     */
    public final static String CONTEXT_PATH = "ctxPath";

    /**
     * 系统运行环境配置项
     */
    public final static String ENV_NAME_ITEM = "env";

    /**
     * 应用名称配置项
     */
    public final static String APP_NAME_ITEM = "appName";

    /**
     * 应用服务的网站主域名配置项
     */
    public final static String DOMAIN_ITEM = "domain";

    /**
     * 系统统一版本名称境配置项
     */
    public final static String VERSION_ITEM = "version";

    /**
     * 系统版本随机数配置项
     */
    public final static String RANDOM_ITEM = "rnd";

    /**
     * 系统应用名称
     */
    public static String APP_NAME = "Default";

    /**
     * 设置系统应用服务的网站主域名
     */
    public static String DOMAIN = "www.newex.cc";

    /**
     * 系统统一版本名称境配置项默认值
     */
    public static String VERSION = "2.0";

    /**
     * 系统运行环境配置项默认值
     */
    public static String ENV_NAME = "prod";

    /**
     * 系统版本随机数配置项默认值
     */
    public static float RANDOM = 0.0f;

    /**
     * 系统支持的语言类型(如:zh-cn/zh-tw/en-us等)默认为en-US
     */
    public static String[] SUPPORTED_LOCALES = {"en", "en-US", "zh-HANS", "zh-HANT", "zh-CN", "zh-TW", "zh-HK"};

    /**
     * 设置系统应用名称
     *
     * @param appName 默认为newex.app.name配置项的值
     */
    public static void setAppName(final String appName) {
        AppEnvConsts.APP_NAME = appName;
    }

    /**
     * 设置应用发布版本
     *
     * @param version
     */
    public static void setVersion(final String version) {
        AppEnvConsts.VERSION = version;
    }

    /**
     * 设置应用部署环境名，取值范围为[dev,test,pre,test]
     *
     * @param envName
     */
    public static void setEnvName(final String envName) {
        AppEnvConsts.ENV_NAME = envName;
    }

    /**
     * 设置系统应用服务的网站主域名
     *
     * @param domain 默认为newex.app.domain配置项的值
     */
    public static void setDomain(final String domain) {
        AppEnvConsts.DOMAIN = domain;
    }

    /**
     * 系统版本随机数配置项值
     *
     * @param rnd
     */
    public static void setRandom(final float rnd) {
        AppEnvConsts.RANDOM = rnd;
    }

    /**
     * 设置系统支持的语言类型(如:zh-cn/zh-tw/en-us等)默认为en-US
     *
     * @param supportedLocales
     */
    public static void setSupportedLocales(final String[] supportedLocales) {
        AppEnvConsts.SUPPORTED_LOCALES = supportedLocales;
    }

    /**
     * 应用部署环境名是否为生产环境
     *
     * @return true|false
     */
    public static boolean isProductionMode() {
        return StringUtils.equalsAnyIgnoreCase(ENV_NAME, "prod");
    }

    /**
     * 应用部署环境名是否为预发环境
     *
     * @return true|false
     */
    public static boolean isPreMode() {
        return StringUtils.equalsAnyIgnoreCase(ENV_NAME, "pre");
    }

    /**
     * 应用部署环境名是否为日常环境
     *
     * @return true|false
     */
    public static boolean isDailyMode() {
        return StringUtils.equalsAnyIgnoreCase(ENV_NAME, "daily");
    }

    /**
     * 应用部署环境名是否为开发环境
     *
     * @return true|false
     */
    public static boolean isDevelopmentMode() {
        return StringUtils.equalsAnyIgnoreCase(ENV_NAME, "dev");
    }
}
