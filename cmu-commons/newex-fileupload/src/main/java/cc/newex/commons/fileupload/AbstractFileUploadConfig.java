package cc.newex.commons.fileupload;

import org.apache.commons.lang3.StringUtils;

/**
 * @author newex-team
 * @date 2017/12/09
 */
public abstract class AbstractFileUploadConfig {
    protected final static String HTTP_SCHEME = "https";
    protected String vpcEndpoint;
    protected String endpoint;
    protected String bucketName;
    protected String path;
    protected String urlScheme = HTTP_SCHEME;
    protected long expiredTime = 1000 * 60 * 60 * 5L + 1000 * 60 * 30L;
    protected long cacheControlMaxAge = 60 * 60 * 24;

    protected String referer;

    /**
     * 获取vpc oss endpoint地址
     * (如：http://vpc-100.oss-cn-hangzhou.aliyuncs.com)
     *
     * @return vpc endpoint
     */
    public String getVpcEndpoint() {
        return this.vpcEndpoint;
    }

    /**
     * 设置vpc oss endpoint地址
     *
     * @param vpcEndpoint vpc endpoint
     */
    public void setVpcEndpoint(final String vpcEndpoint) {
        this.vpcEndpoint = vpcEndpoint;
    }

    /**
     * 获取具体上传的oss endpoint地址(如：http://oss-cn-hangzhou.aliyuncs.com）
     *
     * @return oss endpoint
     */
    public String getEndpoint() {
        return this.endpoint;
    }

    /**
     * 获取oss endpoint地址
     *
     * @param endpoint
     */
    public void setEndpoint(final String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * @return
     */
    public String getBucketName() {
        return this.bucketName;
    }

    /**
     * @param bucketName
     */
    public void setBucketName(final String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * @return
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @param path
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * 获取图片链接的有效期
     *
     * @return
     */
    public long getExpiredTime() {
        return this.expiredTime;
    }

    /**
     * 设置图片链接的有效期
     *
     * @param expiredTime
     */
    public void setExpiredTime(final long expiredTime) {
        this.expiredTime = expiredTime;
    }

    /**
     * 设置图片缓存时间(默认24小时)
     *
     * @return
     */
    public long getCacheControlMaxAge() {
        return this.cacheControlMaxAge;
    }

    /**
     * 获取图片缓存时间(默认24小时)
     *
     * @param cacheControlMaxAge
     */
    public void setCacheControlMaxAge(final long cacheControlMaxAge) {
        this.cacheControlMaxAge = cacheControlMaxAge;
    }

    /**
     * @return
     */
    public String getUrlScheme() {
        if (StringUtils.isBlank(this.urlScheme)) {
            return HTTP_SCHEME;
        }
        return this.urlScheme;
    }

    /**
     * @param urlScheme
     */
    public void setUrlScheme(final String urlScheme) {
        this.urlScheme = urlScheme;
    }

    public String getReferer() {
        return this.referer;
    }

    public void setReferer(final String referer) {
        this.referer = referer;
    }

}
