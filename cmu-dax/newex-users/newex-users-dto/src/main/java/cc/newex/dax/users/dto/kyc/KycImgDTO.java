package cc.newex.dax.users.dto.kyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycImgDTO {
    /**
     * 图片类型
     */
    private Integer type;
    /**
     * 图片的相对路径
     */
    private String imgPath;
}
