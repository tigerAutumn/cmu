package cc.newex.dax.users.dto.kyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (类描述：身份证正面上传返回数据)
 *
 * @create 2018/5/7 下午5:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycUploadFrontDTO {
    /**
     * 数据库中图片相对路径
     */
    private String imagePath;
    /**
     * 姓名
     */
    private String name;
    /**
     * 证件号码
     */
    private String cardNumber;
    /**
     * 性别
     */
    private String gender;

}
