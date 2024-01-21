package cc.newex.dax.integration.dto.admin;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018-06-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProviderConfDTO {
    /**
     * 自增Id
     */
    private Integer id;
    /**
     * 信息提供者唯一名称
     */
    @Length(min = 3, max = 20)
    private String name;
    /**
     * 在同类所有提供者的权重
     */
    @NotNull
    private Double weight;
    /**
     * 类型(sms|mail|ip|bank等)
     */
    @NotNull
    @Length(min = 2, max = 20)
    private String type;
    /**
     * 服务地区(china|international|all)
     */
    @NotNull
    @Length(min = 3, max = 20)
    private String region;
    /**
     * 信息提供者配置选项(JSON格式）
     */
    @Length(min = 5, max = 1000)
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
}