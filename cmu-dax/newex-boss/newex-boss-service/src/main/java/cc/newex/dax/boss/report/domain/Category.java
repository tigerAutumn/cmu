package cc.newex.dax.boss.report.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 报表类别表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {
    /**
     * 报表ID
     */
    private Integer id;
    /**
     * 父分类
     */
    private Integer parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 树型结构路径从根id到当前id的路径
     */
    private String path;
    /**
     * 是否为子类别1为是，0为否
     */
    private Integer hasChild;
    /**
     * 状态 0表示禁用;1表示启用;默认为1
     */
    private Integer status;
    /**
     * 节点在其父节点中的顺序
     */
    private Integer sequence;
    /**
     * 说明备注
     */
    private String comment;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date updatedDate;

    public static Category getInstance() {
        return Category.builder()
                .parentId(0)
                .name("")
                .path("")
                .hasChild(0)
                .status(1)
                .sequence(10)
                .comment("")
                .updatedDate(new Date())
                .build();
    }
}