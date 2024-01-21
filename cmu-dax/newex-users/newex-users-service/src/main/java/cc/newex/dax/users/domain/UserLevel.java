package cc.newex.dax.users.domain;

import cc.newex.dax.users.dto.common.UserLevelEnum;
import lombok.*;

import java.util.Date;

/**
 * 用户等级表
 *
 * @author newex-team
 * @date 2018-07-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLevel {
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
     * 用户等级权重
     */
    private Integer weight;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date modifyDate;

    public static UserLevel getInstance() {
        return UserLevel.builder()
                .id(0L)
                .userId(0L)
                .userLevel(UserLevelEnum.VIP.getCode())
                .weight(UserLevelEnum.VIP.getWeight())
                .createdDate(new Date())
                .modifyDate(new Date())
                .build();
    }
}