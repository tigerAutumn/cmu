package cc.newex.dax.users.dto.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Api流量限制配置表
 *
 * @author newex-team
 * @date 2018-06-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserIpRateLimitDTO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * IP地址十进制表示
     */
    private Long ip;
    /**
     * 请求次数限制(次数/每几秒 如:6/2表示2秒内不超过6次)默认为6/2
     */
    private String rateLimit;
    /**
     * 备注
     */
    private String memo;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date updatedDate;
    /**
     * 券商ID
     */
    private Integer brokerId;
}