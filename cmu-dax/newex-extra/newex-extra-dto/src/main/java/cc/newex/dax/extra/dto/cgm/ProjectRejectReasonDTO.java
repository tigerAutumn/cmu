package cc.newex.dax.extra.dto.cgm;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-08-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectRejectReasonDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目id
     */
    @NotNull
    private Long tokenInfoId;

    /**
     * 拒绝原因
     */
    @NotBlank
    private String reason;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

}
