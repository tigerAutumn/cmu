package cc.newex.dax.users.domain;

import lombok.*;

import java.util.Date;

/**
 * 用户等级变更记录表
 *
 * @author newex-team
 * @date 2018-07-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLevelRecord {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户等级(vip,angel, partner)
     */
    private String userLevel;
    /**
     * 上次用户等级
     */
    private String preUserLevel;
    /**
     * 等级变更原因
     */
    private String reason;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date modifyDate;

    public static UserLevelRecord getInstance() {
        return UserLevelRecord.builder()
                .id(0L)
                .userId(0L)
                .userLevel("")
                .preUserLevel("")
                .reason("")
                .createdDate(new Date())
                .modifyDate(new Date())
                .build();
    }
}