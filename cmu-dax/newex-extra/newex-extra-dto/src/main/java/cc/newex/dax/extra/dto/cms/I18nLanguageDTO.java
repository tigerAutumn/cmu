package cc.newex.dax.extra.dto.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 本地化语言表
 *
 * @author liutiejun
 * @date 2018-06-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class I18nLanguageDTO {
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

    public static I18nLanguageDTO getInstance() {
        return I18nLanguageDTO.builder()
                .id(0)
                .code("")
                .name("")
                .createdDate(new Date())
                .build();
    }
}