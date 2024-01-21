package cc.newex.dax.extra.domain.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 本地化语言表
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class I18nLanguage {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 语言代号参考BCP47标准
     */
    private String code;
    /**
     * 语言名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static I18nLanguage getInstance() {
        return I18nLanguage.builder()
                .id(0)
                .code("")
                .name("")
                .createdDate(new Date())
                .build();
    }
}