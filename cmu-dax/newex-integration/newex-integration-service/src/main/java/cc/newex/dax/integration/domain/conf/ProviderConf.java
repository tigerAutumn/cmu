package cc.newex.dax.integration.domain.conf;

import lombok.*;

import java.util.Date;

/**
 * @author newex-team
 * @date 2018-06-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProviderConf {
    /**
     * 自增Id
     */
    private Integer id;
    /**
     * 信息提供者唯一名称
     */
    private String name;
    /**
     * 在同类所有提供者的权重
     */
    private Double weight;
    /**
     * 类型(sms|mail)
     */
    private String type;
    /**
     * 服务地区(china|international)
     */
    private String region;
    /**
     * 信息提供者配置选项(JSON格式）
     */
    private String options;
    /**
     * 备注
     */
    private String memo;
    /**
     * 状态0禁用,1启用其他保留
     */
    private Integer status;
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

    public static ProviderConf getInstance() {
        return ProviderConf.builder()
                .id(0)
                .name("")
                .weight(5.0)
                .type("")
                .region("")
                .options("")
                .memo("")
                .status(1)
                .createdDate(new Date())
                .build();
    }
}