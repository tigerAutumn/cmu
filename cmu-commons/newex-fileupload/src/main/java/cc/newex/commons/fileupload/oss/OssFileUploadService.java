package cc.newex.commons.fileupload.oss;

import cc.newex.commons.fileupload.AbstractFileUploadConfig;
import cc.newex.commons.fileupload.AbstractFileUploadService;
import cc.newex.commons.fileupload.FileUploadMetadata;
import cc.newex.commons.fileupload.FileUploadResponse;
import cc.newex.commons.fileupload.FileUploadService;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * Aliyun OSS 文件上传服务类
 *
 * @author newex-team
 * @date 2017/12/09
 */
@Slf4j
public class OssFileUploadService extends AbstractFileUploadService
        implements FileUploadService {
    private final OSSClient ossClient;

    public OssFileUploadService(final AbstractFileUploadConfig config, final OSSClient ossClient) {
        super(config);
        this.ossClient = ossClient;
    }

    @Override
    public FileUploadResponse uploadFileWithPublicRead(final String fileName, final File file) {
        return this.uploadFile(fileName, file, null);
    }

    @Override
    public FileUploadResponse uploadFileWithPublicRead(final String fileName, final InputStream inputStream) {
        return this.uploadFile(fileName, inputStream, null);
    }

    @Override
    public FileUploadResponse uploadFile(final String fileName, final File file,
                                         final FileUploadMetadata uploadMetadata) {
        final String key = this.getKey(fileName);
        final ObjectMetadata objectMetadata = this.createObjectMetadata(uploadMetadata);
        this.ossClient.putObject(this.config.getBucketName(), key, file, objectMetadata);
        return FileUploadResponse.builder()
                .fileName(fileName)
                .key(key)
                .url(this.getSignedUrl(fileName))
                .build();
    }

    @Override
    public FileUploadResponse uploadFile(final String fileName, final InputStream inputStream,
                                         final FileUploadMetadata uploadMetadata) {
        final String key = this.getKey(fileName);
        final ObjectMetadata objectMetadata = this.createObjectMetadata(uploadMetadata);
        this.ossClient.putObject(this.config.getBucketName(), key, inputStream, objectMetadata);
        return FileUploadResponse.builder()
                .fileName(fileName)
                .key(key)
                .url(this.getSignedUrl(fileName))
                .build();
    }

    @Override
    public boolean delete(final String fileName) {
        try {
            this.ossClient.deleteObject(this.config.getBucketName(), this.getKey(fileName));
            return true;
        } catch (final Exception ex) {
            log.error("delete oss object failure", ex);
            return false;
        }
    }

    @Override
    protected String generatePresignedUrl(final String bucketName, final String key, final Date expiration) {
        final URL url = this.ossClient.generatePresignedUrl(bucketName, key, expiration);
        return url.toString();
    }

    /**
     * @param uploadMetadata
     * @return
     */
    private ObjectMetadata createObjectMetadata(final FileUploadMetadata uploadMetadata) {
        if (uploadMetadata == null) {
            return null;
        }

        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(uploadMetadata.getContentType());
        objectMetadata.setContentLength(uploadMetadata.getContentLength());
        objectMetadata.setCacheControl("max-age=" + this.config.getCacheControlMaxAge());

        final String referer = this.config.getReferer();
        if (StringUtils.isNotBlank(referer)) {
            log.info("oss upload referer: {}", referer);

            objectMetadata.setHeader("referer", referer);
        }

        return objectMetadata;
    }
}
