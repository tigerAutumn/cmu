package cc.newex.dax.integration.domain.conf;

import lombok.*;

import java.util.Date;

/**
 *
 * @author mbg.generated
 * @date 2018-09-12 15:04:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerSignConfig {
    /**
     * 主键
     */
    private Long id;

    /**
     * 商家 broker id
     */
    private Integer brokerId;

    /**
     * 签名
     */
    private String sign;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date modifyTime;
}