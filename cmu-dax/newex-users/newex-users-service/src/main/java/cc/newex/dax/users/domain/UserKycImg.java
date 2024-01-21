package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户kyc证件照片信息
 *
 * @author newex-team
 * @date 2018-09-13 14:29:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserKycImg {
    /**
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 证件类型1：身份证，2：护照，3：驾驶证
     */
    private Integer cardType;

    /**
     * 证件信息照片
     */
    private String cardImg;

    /**
     * 身份证正面照片
     */
    private String frontImg;

    /**
     * 身份证背面照片
     */
    private String backImg;

    /**
     * 手持证件照片
     */
    private String handsImg;

    /**
     * 住址证明照片
     */
    private String addressImg;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

    /**
     * 券商ID
     */
    private Integer brokerId;

    public static UserKycImg getInstance() {
        return UserKycImg.builder()
                .id(0L)
                .userId(0L)
                .cardType(1)
                .cardImg("")
                .frontImg("")
                .backImg("")
                .handsImg("")
                .addressImg("")
                .createdDate(new Date())
                .updatedDate(new Date())
                .brokerId(1)
                .build();
    }
}