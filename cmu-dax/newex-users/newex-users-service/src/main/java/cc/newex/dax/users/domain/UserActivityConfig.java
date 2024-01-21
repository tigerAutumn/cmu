package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 活动配置表
 *
 * @author newex-team
 * @date 2018-06-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserActivityConfig {
    /**
     * 主键id
     */
    private Long id;
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
     * 活动币code
     */
    private String currencyCode;
    /**
     * 是否上线 0:下线,1:上线
     */
    private Integer online;
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

    public static UserActivityConfig getInstance() {
        return UserActivityConfig.builder()
                .id(0L)
                .activityCode("")
                .currencyNum(0D)
                .currencyId(0)
                .currencyCode("")
                .online(1)
                .inviteCurrencyId(0)
                .inviteCurrencyNum(0D)
                .inviteCurrencyCode("")
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();
    }
}