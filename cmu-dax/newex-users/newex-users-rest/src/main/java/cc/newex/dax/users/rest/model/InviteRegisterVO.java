package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteRegisterVO implements Serializable {
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 邀请人
     */
    private String inviteName;
    /**
     * 活动类型
     */
    private String activityCode;
    /**
     * 送币数量
     */
    private Double currencyNum;
    /**
     * 活动币code
     */
    private String currencyCode;
    /**
     * 邀请人送的币id
     */
    private Integer inviteCurrencyId;
    /**
     * 邀请人送的币数
     */
    private Double inviteCurrencyNum;
    /**
     * 邀请人送的币名称
     */
    private String inviteCurrencyCode;
    /**
     * 是否上线 0:下线,1:上线
     */
    private Integer online;
    /**
     * 券商ID
     */
    private Integer brokerId;
}
