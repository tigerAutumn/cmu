package cc.newex.dax.integration.dto.admin;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WarningConfigDTO {
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
}