package cc.newex.dax.boss.report.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 报表元数据配置字典表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Conf {
    /**
     * 配置字典ID
     */
    private Integer id;
    /**
     * 父ID
     */
    private Integer parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 配置code
     */
    private String code;
    /**
     * 配置值
     */
    private String value;
    /**
     * 显示顺序
     */
    private Integer sequence;
    /**
     * 配置说明
     */
    private String comment;
    /**
     * 记录创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;
    /**
     * 是否有子配置项
     */
    private boolean hasChild;

    public static Conf getInstance() {
        return Conf.builder()
                .parentId(0)
                .name("")
                .code("")
                .value("")
                .sequence(10)
                .comment("")
                .updatedDate(new Date())
                .build();
    }
}