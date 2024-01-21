package cc.newex.dax.boss.admin.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 后台系统权限表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Permission {
    /**
     * 系统操作标识
     */
    private Integer id;
    /**
     * 系统模块标识
     */
    private Integer moduleId;
    /**
     * 系统操作名称
     */
    private String name;
    /**
     * 系统操作唯一代号
     */
    private String code;
    /**
     * 系统操作的排序顺序
     */
    private Integer sequence;
    /**
     * 系统操作备注
     */
    private String comment;
    /**
     * 系统操作记录创建时间
     */
    private Date createdDate;
    /**
     * 系统操作记录更新时间戳
     */
    private Date updatedDate;
    /**
     * 系统操作所属模块树路径,对应admin_module表中的path字段
     */
    private String path;

    public static Permission getInstance() {
        return Permission.builder()
                .moduleId(0)
                .name("")
                .code("")
                .sequence(10)
                .comment("")
                .updatedDate(new Date())
                .build();
    }
}