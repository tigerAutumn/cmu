package cc.newex.dax.boss.admin.domain;

import lombok.*;

import java.util.Date;

/**
 * 后台系统券商表
 *
 * @author mbg.generated
 * @date 2018-09-11 14:44:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Broker {
    /**
     * id
     */
    private Integer id;

    /**
     * 券商key
     */
    private String brokerKey;

    /**
     * 券商名称
     */
    private Integer brokerName;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    private Date createdDate;
}