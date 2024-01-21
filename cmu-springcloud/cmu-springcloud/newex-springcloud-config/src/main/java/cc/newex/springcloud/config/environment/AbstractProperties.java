package cc.newex.springcloud.config.environment;

/**
 * @author newex-team
 * @date 2018-07-17
 */
public abstract class AbstractProperties {
    protected String endpoint;
    protected String region;
    protected String accessKeyId;
    protected String accessKeySecret;
    protected String bucketName;
    protected String basedir = "";

    /**
     * 获取具体存储的oss or s3 endpoint地址(如：http://oss or s3-cn-hangzhou.aliyuncs.com）
     *
     * @return oss or s3 endpoint
     */
    public String getEndpoint() {
        return this.endpoint;
    }

    /**
     * 获取oss or s3 endpoint地址 (ap-northeast-1)
     *
     * @param endpoint
     */
    public void setEndpoint(final String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 获取具体存储的oss or s3  Region
     *
     * @return region （如:ap-northeast-1)
     */
    public String getRegion() {
        return this.region;
    }

    /**
     * 设置具体存储的oss or s3  Region
     *
     * @param region 如:ap-northeast-1)
     */
    public void setRegion(final String region) {
        this.region = region;
    }

    /**
     * 获取具体存储的oss or s3  accessKeyId
     *
     * @return accessKeyId
     */
    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    /**
     * 设置具体存储的oss or s3  accessKeyId
     *
     * @param accessKeyId accessKeyId
     */
    public void setAccessKeyId(final String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    /**
     * 获取具体存储的oss or s3  accessKeySecret
     *
     * @return accessKeySecret
     */
    public String getAccessKeySecret() {
        return this.accessKeySecret;
    }

    /**
     * 设置具体存储的oss or s3  accessKeySecret
     *
     * @param accessKeySecret accessKeySecret
     */
    public void setAccessKeySecret(final String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    /**
     * 获取具体存储的oss or s3  bucketName
     *
     * @return bucketName 桶名称
     */
    public String getBucketName() {
        return this.bucketName;
    }

    /**
     * 设置具体存储的oss or s3  bucketName
     *
     * @param bucketName 桶名称
     */
    public void setBucketName(final String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * 获取具体存储的oss or s3  根目录(相对于bucketName)
     *
     * @return bucketName 根目录(相对于bucketName)
     */
    public String getBasedir() {
        return this.basedir;
    }

    /**
     * 设置具体存储的oss or s3  根目录(相对于bucketName)
     *
     * @param basedir 根目录(相对于bucketName)
     */
    public void setBasedir(final String basedir) {
        this.basedir = basedir;
    }
}
