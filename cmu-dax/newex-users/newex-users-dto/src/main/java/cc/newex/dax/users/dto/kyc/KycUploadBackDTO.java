package cc.newex.dax.users.dto.kyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (类描述：身份证背面上传返回数据)
 *
 * @create 2018/5/7 下午5:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycUploadBackDTO {
    /**
     * 数据库中图片相对路径
     */
    private String imagePath;
    /**
     * 证件有效期
     */
    private String validDate;
}
