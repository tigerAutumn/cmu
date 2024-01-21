package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * API IP流量限流配置表
 *
 * @author newex-team
 * @date 2018-06-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserIpRateLimit {
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

    public static UserIpRateLimit getInstance() {
        return UserIpRateLimit.builder()
                .id(0)
                .ip(0L)
                .rateLimit("6/2")
                .memo("")
                .createdDate(new Date())
                .brokerId(1)
                .build();
    }
}