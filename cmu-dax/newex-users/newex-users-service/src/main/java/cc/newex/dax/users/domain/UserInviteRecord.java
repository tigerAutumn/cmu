package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 邀请好友记录表
 *
 * @author newex-team
 * @date 2018-06-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInviteRecord {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 注册用户id
     */
    private Long userId;
    /**
     * 注册人姓名
     */
    private String userName;
    /**
     * 邀请人id
     */
    private Long inviteUserId;
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 活动编码
     */
    private String activityCode;
    /**
     * 送币数量
     */
    private Double currencyNum;
    /**
     * 活动送币
     */
    private Integer currencyId;
    /**
     * 邀请人送币数量
     */
    private Double inviteCurrencyNum;
    /**
     * 邀请人活动送币
     */
    private Integer inviteCurrencyId;
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

    public static UserInviteRecord getInstance() {
        return UserInviteRecord.builder()
                .id(0L)
                .userId(0L)
                .userName("")
                .inviteUserId(0L)
                .inviteCode("")
                .activityCode("")
                .currencyNum(0D)
                .currencyId(0)
                .inviteCurrencyNum(0D)
                .inviteCurrencyId(0)
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();
    }
}