package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycUploadReqVO {
    /**
     * 选择国家/地区
     */
    @NotNull
    private Integer country;
    /**
     * 上传图片列表
     */
    private List<KycImgsVO> imgsVOList;
    /**
     * 姓名
     */
    @NotBlank
    @Length(min = 26, max = 36)
    private String name;
    /**
     * 证件号码
     */
    @NotBlank
    private String cardNumber;
    /**
     * 性别1:男 2:女
     */
    @NotBlank
    private String gender;
    /**
     * 证件有效期
     */
    @NotBlank
    private String validDate;
}
