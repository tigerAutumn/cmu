package cc.newex.dax.users.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 全球国家(地区)表
 *
 * @author newex-team
 * @date 2018-04-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DictCountry implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 语言
     */
    private String locale;
    /**
     * 国家简称
     */
    private String abbr;
    /**
     * 国家名称
     */
    private String name;
    /**
     * 首都
     */
    private String capital;
    /**
     * 区域编码
     */
    private Integer areaCode;
    /**
     * 国家编码
     */
    private Integer countryCode;
    /**
     * 货币代码
     */
    private String currencyCode;
    /**
     * 货币名称
     */
    private String currencyName;
    /**
     * 域名后缀 如:.cn
     */
    private String domainCode;
    /**
     * ISO 3166-1 2 Letter Code
     */
    private String letterCode2;
    /**
     * ISO 3166-1 3 Letter Code
     */
    private String letterCode3;
    /**
     * 状态 1：可注册，0：不可注册
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date updatedDate;

    public static DictCountry getInstance() {
        return DictCountry.builder()
                .id(0)
                .locale("")
                .abbr("")
                .name("")
                .capital("")
                .areaCode(0)
                .countryCode(0)
                .currencyCode("")
                .currencyName("")
                .domainCode("")
                .letterCode2("")
                .letterCode3("")
                .status(1)
                .updatedDate(new Date())
                .build();
    }
}