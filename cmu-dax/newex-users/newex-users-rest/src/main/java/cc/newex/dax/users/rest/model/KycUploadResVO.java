package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycUploadResVO {
    /**
     * 存储到oss或s3上的文件名(带相对路径)
     */
    private String fileName;
    /**
     * 上传后在oss或s3上可访问的url地址
     */
    private String url;
    /**
     * 图片类型
     *
     * @see cc.newex.dax.users.common.enums.kyc.KycImgsEnum
     */
    private Integer type;
}
