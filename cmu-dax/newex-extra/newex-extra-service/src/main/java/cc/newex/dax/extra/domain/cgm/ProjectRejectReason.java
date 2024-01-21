package cc.newex.dax.extra.domain.cgm;

import lombok.*;

import java.util.Date;

/**
 * 项目拒绝原因表
 *
 * @author mbg.generated
 * @date 2018-08-08 16:21:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectRejectReason {
    /**
     * 主键
     */
    private Long id;

    /**
     * 项目id
     */
    private Long tokenInfoId;

    /**
     * 拒绝原因
     */
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