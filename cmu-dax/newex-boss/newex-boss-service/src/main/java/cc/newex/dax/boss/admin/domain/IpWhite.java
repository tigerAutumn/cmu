package cc.newex.dax.boss.admin.domain;

import lombok.*;

import java.util.Date;

/**
 * 后台系统ip白名单表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IpWhite {
    /**
     * 标识
     */
    private Integer id;
    /**
     * 创建管理员用户id
     */
    private Integer adminUserId;
    /**
     * ip地址
     */
    private Long ipAddress;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    /**
     * brokerId
     */
    private Integer brokerId;

    public static IpWhite getInstance() {
        return IpWhite.builder()
                .adminUserId(-1)
                .ipAddress(0L)
                .updatedDate(new Date())
                .brokerId(0)
                .build();
    }
}