package cc.newex.dax.boss.report.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 报表信息表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reporting {
    /**
     * 报表ID
     */
    private Integer id;
    /**
     * 创建管理用户id
     */
    private Integer adminUserId;
    /**
     * 报表唯一ID,由接口调用方传入
     */
    private String uid;
    /**
     * 报表分类id
     */
    private Integer categoryId;
    /**
     * 数据源ID
     */
    private Integer dsId;
    /**
     * 报表名称
     */
    private String name;
    /**
     * 报表SQL语句
     */
    private String sqlText;
    /**
     * 报表列集合元数据(JSON格式)
     */
    private String metaColumns;
    /**
     * 查询条件列属性集合(JSON格式)
     */
    private String queryParams;
    /**
     * 报表配置选项(JSON格式)
     */
    private String options;
    /**
     * 报表状态（1表示锁定，0表示编辑)
     */
    private Integer status;
    /**
     * 报表节点在其父节点中的顺序
     */
    private Integer sequence;
    /**
     * 创建管理员用户名
     */
    private String adminUsername;
    /**
     * 说明备注
     */
    private String comment;
    /**
     * 记录创建时间
     */
    private Date createdDate;
    /**
     * 记录修改时间
     */
    private Date updatedDate;
    /**
     * 报表分类名称
     */
    private String categoryName;
    /**
     * 报表数据源名称
     */
    private String dsName;

    public static Reporting getInstance() {
        return Reporting.builder()
                .adminUserId(-1)
                .uid("")
                .categoryId(0)
                .dsId(0)
                .name("")
                .sqlText("")
                .metaColumns("")
                .queryParams("")
                .options("")
                .status(0)
                .sequence(10)
                .adminUsername("")
                .comment("")
                .updatedDate(new Date())
                .build();
    }
}