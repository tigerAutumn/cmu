package cc.newex.dax.perpetual.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共配置表
 *
 * @author newex-team
 * @date 2018-11-20 18:26:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonProp {
    /**
     */
    private Long id;

    /**
     * 查询KEY
     */
    private String propKey;

    /**
     * 配置信息为JSON数据
     */
    private String propValue;

    /**
     * 业务方ID
     */
    private Integer brokerId;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;
}