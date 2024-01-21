package cc.newex.dax.users.dto.membership;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInviteRecordDTO implements Serializable {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 注册用户id
     */
    private Long userId;
    /**
     * 邀请人id
     */
    private Long inviteUserId;
    /**
     * 用户登录名
     */
    private String userName;
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
}

