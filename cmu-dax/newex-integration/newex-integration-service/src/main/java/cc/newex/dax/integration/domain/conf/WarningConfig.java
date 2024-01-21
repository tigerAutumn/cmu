package cc.newex.dax.integration.domain.conf;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 报警配置表
 *
 * @author mbg.generated
 * @date 2018-07-23 16:10:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WarningConfig {
    /**
     * 配置Id
     */
    private Integer id;

    /**
     * 配置code
     */
    private String code;

    /**
     * 业务类型，spot,c2c
     */
    private String bizType;

    /**
     * 报警详情
     */
    private String content;

    /**
     * 配置备注
     */
    private String memo;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}