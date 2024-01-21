package cc.newex.dax.boss.web.model.extra.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 本地化文本编码表
 *
 * @author liutiejun
 * @date 2018-06-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class I18nMessageCodeExtraVO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 父节点id
     */
    private Integer parentId;
    /**
     * Message唯一编号
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 节点类型(cate类别节点|leaf叶子节点)
     */
    private String type;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static cc.newex.dax.extra.dto.cms.I18nMessageCodeDTO getInstance() {
        return cc.newex.dax.extra.dto.cms.I18nMessageCodeDTO.builder()
                .id(0)
                .parentId(0)
                .code("")
                .name("")
                .type("")
                .createdDate(new Date())
                .build();
    }
}