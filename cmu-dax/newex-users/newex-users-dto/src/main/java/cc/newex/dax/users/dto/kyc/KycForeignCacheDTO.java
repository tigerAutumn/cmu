package cc.newex.dax.users.dto.kyc;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KycForeignCacheDTO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 证件类型
     */
    private Integer cardType;
    /**
     * 证件照片
     */
    private String cardImg;
    /**
     * 手持照片
     */
    private String handsImg;
    /**
     * 住址证明照片
     */
    private String addressImg;
}
