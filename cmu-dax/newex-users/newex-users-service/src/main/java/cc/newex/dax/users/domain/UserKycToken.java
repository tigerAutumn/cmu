package cc.newex.dax.users.domain;

import lombok.*;

import java.util.Date;

/**
 * 用户kyc等级
 *
 * @author newex-team
 * @date 2018-05-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserKycToken {
    /**
     *
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * face++请求token
     */
    private String token;
    /**
     * faceId活体业务编号
     */
    private String bizId;
    /**
     * kyc2返回结果
     */
    private String returnResult;
    /**
     * 认证备注信息
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static UserKycToken getInstance() {
        return UserKycToken.builder()
                .id(0L)
                .userId(0L)
                .token("")
                .bizId("")
                .returnResult("")
                .remarks("")
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();
    }
}