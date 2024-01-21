package cc.newex.commons.fileupload;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author newex-team
 * @date 2017/12/09
 */
public interface FileUploadService {
    /**
     * 上传文件并存储到日期格式(yyyyMMdd)目录下
     *
     * @param fileName 上传后的文件名称(可带相对路径）
     * @param file     当前上传文件对象
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFileWithDatePath(String fileName, File file);

    /**
     * 上传文件并存储到日期格式(yyyyMMdd)目录下
     *
     * @param fileName    上传后的文件名称(可带相对路径）
     * @param inputStream 当前上传的文件流
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFileWithDatePath(String fileName, InputStream inputStream);

    /**
     * 上传文件并存储到日期格式目录下
     *
     * @param fileName 上传后的文件名称(可带相对路径）
     * @param file     当前上传文件对象
     * @param pattern  日期时间模式(默认为yyyyMMdd)
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFileWithDatePath(String fileName, File file, String pattern);

    /**
     * 上传文件并存储到日期格式目录下
     *
     * @param fileName    上传后的文件名称(可带相对路径）
     * @param inputStream 当前上传的文件流
     * @param pattern     日期时间模式(默认为yyyyMMdd)
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFileWithDatePath(String fileName, InputStream inputStream, String pattern);

    /**
     * 上传文件
     *
     * @param fileName 上传后的文件名称(可带相对路径）
     * @param file     当前上传文件对象
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFile(String fileName, File file);

    /**
     * 上传文件
     *
     * @param fileName    上传后的文件名称(可带相对路径）
     * @param inputStream 当前上传的文件流
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFile(String fileName, InputStream inputStream);

    /**
     * 上传文件并可以Public Read
     *
     * @param fileName 上传后的文件名称(可带相对路径）
     * @param file     当前上传文件对象
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFileWithPublicRead(String fileName, File file);

    /**
     * 上传文件并可以Public Read
     *
     * @param fileName    上传后的文件名称(可带相对路径）
     * @param inputStream 当前上传的文件流
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFileWithPublicRead(String fileName, InputStream inputStream);

    /**
     * 上传文件
     *
     * @param fileName
     * @param in
     * @param contentLength
     * @param contentType
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFile(String fileName, InputStream in, long contentLength, String contentType);

    /**
     * 上传文件
     *
     * @param fileName   上传后的文件名称(可带相对路径）
     * @param file       当前上传的文件
     * @param objectMeta oss元数据
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFile(String fileName, File file, FileUploadMetadata objectMeta);

    /**
     * 上传文件
     *
     * @param fileName    上传后的文件名称(可带相对路径）
     * @param inputStream 当前上传的文件流
     * @param objectMeta  oss元数据
     * @return 上传后的文件私有签名url地址
     */
    FileUploadResponse uploadFile(String fileName, InputStream inputStream, FileUploadMetadata objectMeta);

    /**
     * 删除Oss文件
     *
     * @param fileName 文件名称(可带相对路径）
     * @return true|false
     */
    boolean delete(String fileName);

    /**
     * 获取上传后的文件url地址
     *
     * @param fileName 文件名称(可带相对路径）
     * @return 上传文件的url
     */
    String getUrl(String fileName);

    /**
     * 获取上传后的文件url地址
     *
     * @param fileName   文件名称(可带相对路径）
     * @param prefixPath 文件前辍路径
     * @return 上传文件的url
     */
    String getUrl(String fileName, String prefixPath);

    /**
     * @param url URL对象
     *            如（http://bucket-name.oss-cn-hongkong.aliyuncs.com/public/vod/example.jpg）
     *            其中把上述url解析成如下：
     *            endpoint=http://oss-cn-hongkong.aliyuncs.com
     *            bucketName=bucket-name
     *            fileName=public/vod/example.jpg
     * @return 带私有签名url地址ok-private-hk
     */
    String getSignedUrlByParseUrl(final URL url);

    /**
     * @param url url地址
     *            如（http://private-hk.oss-cn-hongkong.aliyuncs.com/public/vod/example.jpg）
     *            其中把上述url解析成如下：
     *            endpoint=http://oss-cn-hongkong.aliyuncs.com
     *            bucketName=bucket-name
     *            fileName=public/vod/example.jpg
     * @return 带私有签名url地址
     */
    String getSignedUrlByParseUrl(final String url);

    /**
     * 获取上传后的文件临时签名url地址
     *
     * @param fileName 文件名称(可带相对路径）
     * @return 带私有签名url地址
     */
    String getSignedUrl(String fileName);

    /**
     * 获取上传后的文件临时签名url地址
     *
     * @param fileName   文件名称(可带相对路径）
     * @param prefixPath 文件前辍路径
     * @return 带私有签名url地址
     */
    String getSignedUrl(String fileName, String prefixPath);

    /**
     * @param endpoint   endpoint url (如:http://oss-cn-hongkong.aliyuncs.com)
     * @param bucketName 桶名称
     * @param fileName   文件名称(可带相对路径）
     * @return 带私有签名url地址
     */
    String getSignedUrl(final String endpoint, final String bucketName, final String fileName);

    /**
     * @param endpoint   endpoint url (如:http://oss-cn-hongkong.aliyuncs.com)
     * @param bucketName 桶名称
     * @param fileName   文件名称(可带相对路径）
     * @param prefixPath 文件前辍路径
     * @return 带私有签名url地址
     */
    String getSignedUrl(final String endpoint, final String bucketName, final String fileName, String prefixPath);

    /**
     * 获取文件上传的bucket key
     *
     * @param fileName 上传的文件名称
     * @return bucket key
     */
    String getKey(String fileName);

    /**
     * 获取文件上传的bucket key
     *
     * @param fileName   上传的文件名称
     * @param prefixPath 文件前辍路径
     * @return bucket key
     */
    String getKey(String fileName, String prefixPath);

    /**
     * 生成（yyyyMMdd)日期格式文件目录
     */
    String generateDateFilePath();

    /**
     * 生成日期格式文件目录
     *
     * @param pattern 日期时间模式(默认为yyyyMMdd)
     */
    String generateDateFilePath(String pattern);

    /**
     * 生成yyyyMMdd)日期格式上传文件名称
     *
     * @param originalFileName 原文件名
     */
    String generateDateFileName(String originalFileName);

    /**
     * 生成yyyyMMdd)日期格式上传文件名称
     *
     * @param originalFileName 原文件名
     * @param pattern          日期时间模式(默认为yyyyMMdd)
     */
    String generateDateFileName(String originalFileName, String pattern);

    /**
     * 根据原文件名的扩展名生成新的文件名称
     *
     * @param newFileName      新文件名(不需要带扩展名）
     * @param originalFileName 原文件名
     */
    String generateFileName(String newFileName, String originalFileName);

    /**
     * 获取带日期时间格式(默认为yyyyMMdd)目录的文件名
     *
     * @param fileName 文件名称(可带相对路径）
     * @return 带日期时间格式目录的文件名
     */
    String getDatePathFileName(String fileName);

    /**
     * 获取带日期时间格式目录的文件名
     *
     * @param fileName 文件名称(可带相对路径）
     * @param pattern  日期时间模式(默认为yyyyMMdd)
     * @return 带日期时间格式目录的文件名
     */
    String getDatePathFileName(String fileName, String pattern);
}
