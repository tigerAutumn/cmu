package cc.newex.commons.fileupload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    /**
     * 存储到oss或s3上的文件名(带相对路径)
     */
    private String fileName;

    /**
     * 存储到oss或s3上的key
     */
    private String key;

    /**
     * 上传后在oss或s3上可访问的url地址
     */
    private String url;
}
