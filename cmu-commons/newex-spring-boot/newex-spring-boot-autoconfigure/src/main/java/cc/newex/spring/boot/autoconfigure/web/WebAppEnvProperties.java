package cc.newex.spring.boot.autoconfigure.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@ConfigurationProperties(prefix = "newex.app")
public class WebAppEnvProperties {
    private String name = "";
    private String version = "2.0";
    private String domain = "test";
    private String supportedLocales = "en|en-US|zh-HANS|zh-HANT|zh-CN|zh-TW|zh-HK|zh-SG|ja-JP|ko-KR|fr-FR|de-DE|it-IT";
    private Env env = new Env();

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置应用名称
     *
     * @param name 应用名称
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * 获取应用版本
     *
     * @return 版本
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * 设置应用版本
     *
     * @param version 版本
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * 获取应用当前运行的站点域名(newex.cc等）
     *
     * @return newex.cc
     */
    public String getDomain() {
        return this.domain;
    }

    /**
     * 设置应用当前运行的站点域名(newex.cc等）
     *
     * @param domain (newex.cc等）
     */
    public void setDomain(final String domain) {
        this.domain = domain;
    }

    /**
     * 获取应用当前支持的语言类型(如:zh-CN|zh-TW|en-US等)
     *
     * @return zh-CN|zh-TW|en-US等
     */
    public String getSupportedLocales() {
        return this.supportedLocales;
    }

    /**
     * 设置应用当前支持的语言类型(如:zh-CN|zh-TW|en-US等)
     *
     * @param supportedLocales zh-CN|zh-TW|en-US等
     */
    public void setSupportedLocales(final String supportedLocales) {
        this.supportedLocales = supportedLocales;
    }

    /**
     * 获取应用当前运行的相关环境属性
     *
     * @return 环境属性对象
     */
    public Env getEnv() {
        return this.env;
    }

    /**
     * 设置应用当前运行的相关环境属性
     *
     * @param env 环境属性对象
     */
    public void setEnv(final Env env) {
        this.env = env;
    }

    public static class Env {
        private String name = "prod";

        /**
         * 获取应用环境名称[dev,test,pre,prod]
         *
         * @return 环境名称[dev, test, pre, prod]
         */
        public String getName() {
            return this.name;
        }

        /**
         * 设置应用环境名称[dev,test,pre,prod]
         *
         * @param name 应用环境名称[dev,test,pre,prod]
         */
        public void setName(final String name) {
            this.name = name;
        }
    }
}
