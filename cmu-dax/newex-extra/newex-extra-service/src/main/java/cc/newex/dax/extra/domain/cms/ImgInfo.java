package cc.newex.dax.extra.domain.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * cms图片表
 *
 * @author mbg.generated
 * @date 2018-08-08 19:47:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImgInfo {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片
     */
    private String imgUrl;

    /**
     * 发布用户id对应boss系统的管理员id
     */
    private Integer publisherId;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}