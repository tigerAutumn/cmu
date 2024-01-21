package cc.newex.dax.integration.dto.message;

import lombok.*;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerSignConfigDTO {
    /**
     * 主键
     */
    private transient Long id;

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
