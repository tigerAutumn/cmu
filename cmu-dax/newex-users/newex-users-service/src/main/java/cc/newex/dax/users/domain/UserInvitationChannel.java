package cc.newex.dax.users.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 渠道推广表
 *
 * @author mbg.generated
 * @date 2018-07-25 15:34:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInvitationChannel {
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