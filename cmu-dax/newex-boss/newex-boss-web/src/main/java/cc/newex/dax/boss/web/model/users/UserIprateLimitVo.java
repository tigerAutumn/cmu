package cc.newex.dax.boss.web.model.users;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserIprateLimitVo {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * IP地址十进制表示
     */
    private String ip;
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

    private Integer brokerId;
}
