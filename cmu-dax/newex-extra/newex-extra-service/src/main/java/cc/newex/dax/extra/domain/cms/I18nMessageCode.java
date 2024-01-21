package cc.newex.dax.extra.domain.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 本地化文本编码表
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class I18nMessageCode {
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

    public static I18nMessageCode getInstance() {
        return I18nMessageCode.builder()
                .id(0)
                .parentId(0)
                .code("")
                .name("")
                .type("")
                .createdDate(new Date())
                .build();
    }
}