package cc.newex.dax.extra.dto.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 本地化文本表
 *
 * @author liutiejun
 * @date 2018-06-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class I18nMessageDTO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 本地化文本Code ID
     */
    private Integer msgCodeId;
    /**
     * 本地化语言代号(zh-cn/en-us等)
     */
    private String locale;
    /**
     * 本地化文本Code
     */
    private String msgCode;
    /**
     * 本地化文本内容
     */
    private String msgText;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static I18nMessageDTO getInstance() {
        return I18nMessageDTO.builder()
                .id(0)
                .msgCodeId(0)
                .locale("")
                .msgCode("")
                .msgText("")
                .createdDate(new Date())
                .build();
    }
}