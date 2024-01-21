package cc.newex.dax.users.dto.admin;

import lombok.*;

import java.util.Date;

/**
 * 渠道邀请信息
 *
 * @author newex-team
 * @date 2018/7/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInvitationChannelDTO {
    /**
     */
    private Long id;

    /**
     * 渠道简称
     */
    private String channelName;

    /**
     */
    private String channelFullName;

    /**
     */
    private String channelCode;

    /**
     * 用户状态0为开启，1为禁用，其他保留，默认为0
     */
    private Integer channeStatus;

    /**
     * 渠道链接
     */
    private String channeLink;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}
