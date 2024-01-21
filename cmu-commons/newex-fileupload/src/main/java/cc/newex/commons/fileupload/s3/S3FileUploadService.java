package cc.newex.commons.fileupload.s3;

import cc.newex.commons.fileupload.AbstractFileUploadConfig;
import cc.newex.commons.fileupload.AbstractFileUploadService;
import cc.newex.commons.fileupload.FileUploadMetadata;
import cc.newex.commons.fileupload.FileUploadResponse;
import cc.newex.commons.fileupload.FileUploadService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * AWS S3 文件上传服务类
 *
 * @author newex-team
 * @date 2017/12/09
 */
@Slf4j
public class S3FileUploadService extends AbstractFileUploadService implements FileUploadService {
    private final AmazonS3 s3Client;

    public S3FileUploadService(final AbstractFileUploadConfig config, final AmazonS3 s3Client) {
        super(config);
        this.s3Client = s3Client;
    }

    @Override
    public FileUploadResponse uploadFileWithPublicRead(final String fileName, final File file) {
        final String key = this.getKey(fileName);
        this.s3Client.putObject(new PutObjectRequest(this.config.getBucketName(), key, file)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return FileUploadResponse.builder()
                .fileName(fileName)
                .key(key)
                .url(this.getUrl(fileName))
                .build();
    }

    @Override
    public FileUploadResponse uploadFileWithPublicRead(final String fileName, final InputStream inputStream) {
        final String key = this.getKey(fileName);
        this.s3Client.putObject(new PutObjectRequest(this.config.getBucketName(), key, inputStream, null)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return FileUploadResponse.builder()
                .fileName(fileName)
                .key(key)
                .url(this.getUrl(fileName))
                .build();
    }

    @Override
    public FileUploadResponse uploadFile(final String fileName, final File file,
                                         final FileUploadMetadata uploadMetadata) {
        final String key = this.getKey(fileName);
        final ObjectMetadata objectMetadata = this.createObjectMetadata(uploadMetadata);
        this.s3Client.putObject(new PutObjectRequest(this.config.getBucketName(), key, file).withMetadata(objectMetadata));
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
        this.s3Client.putObject(this.config.getBucketName(), key, inputStream, objectMetadata);
        return FileUploadResponse.builder()
                .fileName(fileName)
                .key(key)
                .url(this.getSignedUrl(fileName))
                .build();
    }

    @Override
    public boolean delete(final String fileName) {
        try {
            this.s3Client.deleteObject(this.config.getBucketName(), this.getKey(fileName));
            return true;
        } catch (final Exception ex) {
            log.error("delete s3 object failure", ex);
            return false;
        }
    }

    @Override
    public String getUrl(final String fileName) {
        final URL url = this.s3Client.getUrl(this.config.getBucketName(), this.getKey(fileName));
        final String newUrl = this.replaceUrlScheme(url.toString());
        final String newVpcEndpoint = this.appendBucketNameBefore(this.config.getVpcEndpoint(), this.config.getBucketName());
        final String newEndpoint = this.appendBucketNameBefore(this.config.getEndpoint(), this.config.getBucketName());
        return StringUtils.replace(newUrl, newVpcEndpoint, newEndpoint);
    }

    @Override
    protected String generatePresignedUrl(final String bucketName, final String key, final Date expiration) {
        final URL url = this.s3Client.generatePresignedUrl(bucketName, key, expiration);
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
        objectMetadata.setContentLength(uploadMetadata.getContentLength());
        objectMetadata.setContentType(uploadMetadata.getContentType());
        objectMetadata.setCacheControl("max-age=" + this.config.getCacheControlMaxAge());
        return objectMetadata;
    }
}
