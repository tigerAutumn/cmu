package cc.newex.commons.fileupload;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author newex-team
 * @date 2017/12/09
 */
public abstract class AbstractFileUploadService implements FileUploadService {
    protected final AbstractFileUploadConfig config;

    protected AbstractFileUploadService(final AbstractFileUploadConfig config) {
        this.config = config;
    }

    @Override
    public FileUploadResponse uploadFileWithDatePath(final String fileName, final File file) {
        return this.uploadFileWithDatePath(fileName, file, "");
    }

    @Override
    public FileUploadResponse uploadFileWithDatePath(final String fileName, final InputStream inputStream) {
        return this.uploadFileWithDatePath(fileName, inputStream, "");
    }

    @Override
    public FileUploadResponse uploadFileWithDatePath(final String fileName, final File file, final String pattern) {
        return this.uploadFile(this.getDatePathFileName(fileName, pattern), file);
    }

    @Override
    public FileUploadResponse uploadFileWithDatePath(final String fileName, final InputStream inputStream,
                                                     final String pattern) {
        return this.uploadFile(this.getDatePathFileName(fileName, pattern), inputStream);
    }

    @Override
    public FileUploadResponse uploadFile(final String fileName, final File file) {
        return this.uploadFile(fileName, file, null);
    }

    @Override
    public FileUploadResponse uploadFile(final String fileName, final InputStream inputStream) {
        return this.uploadFile(fileName, inputStream, null);
    }

    @Override
    public FileUploadResponse uploadFile(final String fileName, final InputStream in, final long contentLength,
                                         final String contentType) {
        final FileUploadMetadata uploadMetadata = new FileUploadMetadata();
        uploadMetadata.setContentLength(contentLength);
        uploadMetadata.setContentType(contentType);
        return this.uploadFile(fileName, in, uploadMetadata);
    }

    @Override
    public String getUrl(final String fileName) {
        final String endpoint = this.appendBucketNameBefore(this.config.getEndpoint(), this.config.getBucketName());
        return StringUtils.join(StringUtils.appendIfMissing(endpoint, "/"), this.getKey(fileName));
    }

    @Override
    public String getUrl(final String fileName, final String prefixPath) {
        final String endpoint = this.appendBucketNameBefore(this.config.getEndpoint(), this.config.getBucketName());
        return StringUtils.join(StringUtils.appendIfMissing(endpoint, "/"), this.getKey(fileName, prefixPath));
    }

    @Override
    public String getSignedUrlByParseUrl(final URL url) {
        return this.getSignedUrlByParseUrl(url.toString());
    }

    @Override
    public String getSignedUrlByParseUrl(final String url) {
        final String[] segment = StringUtils.splitByWholeSeparator(url, "//");
        Validate.notEmpty(segment);

        final String endpointSegment = StringUtils.substringBefore(segment[1], "/");
        final String endpoint = "http://" + StringUtils.substringAfter(endpointSegment, ".");
        final String bucketName = StringUtils.substringBefore(endpointSegment, ".");
        final String fileName = StringUtils.substringAfter(segment[1], "/");
        return this.getSignedUrl(endpoint, bucketName, fileName);
    }

    @Override
    public String getSignedUrl(final String fileName) {
        return this.getSignedUrl(fileName, this.config.getPath());
    }

    @Override
    public String getSignedUrl(final String fileName, final String prefixPath) {
        return this.getSignedUrl(this.config.getEndpoint(), this.config.getBucketName(), fileName, prefixPath);
    }

    @Override
    public String getSignedUrl(final String endpoint, final String bucketName, final String fileName, final String prefixPath) {
        return this.getSignedUrl(this.config.getEndpoint(), this.config.getBucketName(), this.getKey(fileName, prefixPath));
    }

    @Override
    public String getSignedUrl(final String endpoint, final String bucketName, final String key) {
        final Date expiration = new Date(System.currentTimeMillis() + this.config.getExpiredTime());
        final String signedUrl = this.generatePresignedUrl(key, expiration);
        final String newUrl = this.replaceUrlScheme(signedUrl);
        final String newVpcEndpoint = this.appendBucketNameBefore(this.config.getVpcEndpoint(), bucketName);
        final String newEndpoint = this.appendBucketNameBefore(endpoint, bucketName);
        return StringUtils.replace(newUrl, newVpcEndpoint, newEndpoint);
    }

    @Override
    public String getKey(final String fileName) {
        return this.getKey(fileName, this.config.getPath());
    }

    @Override
    public String getKey(final String fileName, final String prefixPath) {
        final String newFileName = StringUtils.stripStart(fileName, "/");
        final String path = StringUtils.stripStart(prefixPath, "/");
        return StringUtils.join(StringUtils.appendIfMissing(path, "/"), newFileName);
    }

    @Override
    public String generateDateFilePath() {
        return this.generateDateFilePath("");
    }

    @Override
    public String generateDateFilePath(String pattern) {
        pattern = StringUtils.defaultIfBlank(pattern, "yyyyMMdd");
        final SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    @Override
    public String generateDateFileName(final String originalFileName) {
        return this.generateFileName(originalFileName, "yyyyMMdd");
    }

    @Override
    public String generateDateFileName(final String originalFileName, String pattern) {
        pattern = StringUtils.defaultIfBlank(pattern, "yyyyMMdd");
        final SimpleDateFormat format = new SimpleDateFormat(pattern);
        final String newFileName = format.format(new Date());
        return this.generateFileName(newFileName, originalFileName);
    }

    @Override
    public String generateFileName(final String newFileName, final String originalFileName) {
        final String ext = StringUtils.substringAfterLast(originalFileName, ".");
        return String.join(".", newFileName, ext);
    }

    @Override
    public String getDatePathFileName(final String fileName) {
        return this.getDatePathFileName(fileName, "");
    }

    @Override
    public String getDatePathFileName(final String fileName, final String pattern) {
        return String.join("/", this.generateDateFilePath(pattern), fileName);
    }

    /**
     * url scheme后加入bucketname
     *
     * @param url        url地址
     * @param bucketName 桶名
     * @return 替换后的url
     */
    protected String appendBucketNameBefore(final String url, final String bucketName) {
        final String newUrl = this.replaceUrlScheme(url);
        final String regex = "^(" + this.config.getUrlScheme() + "://)";
        return StringUtils.replacePattern(newUrl, regex, "$1" + bucketName + ".");
    }

    /**
     * 替换url scheme
     *
     * @param url url地址
     * @return 替换后的url
     */
    protected String replaceUrlScheme(final String url) {
        return StringUtils.replacePattern(url, "^(http://|https://)",
                this.config.getUrlScheme() + "://");
    }

    /**
     * 生成带私有签名文件url地址
     *
     * @param key        存储key
     * @param expiration 过期时间
     * @return 私有签名文件url地址
     */
    protected String generatePresignedUrl(final String key, final Date expiration) {
        return this.generatePresignedUrl(this.config.getBucketName(), key, expiration);
    }

    /**
     * 生成带私有签名文件url地址
     *
     * @param bucketName 桶名称
     * @param key        存储key
     * @param expiration 过期时间
     * @return 私有签名文件url地址
     */
    protected abstract String generatePresignedUrl(String bucketName, String key, Date expiration);

}
