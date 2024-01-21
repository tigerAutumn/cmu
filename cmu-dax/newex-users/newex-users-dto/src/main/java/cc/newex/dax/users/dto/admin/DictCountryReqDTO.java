package cc.newex.dax.users.dto.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 全球国家(地区)
 *
 * @author newex-team
 * @date 2018-04-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DictCountryReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 中英文
     */
    private String locale;
    /**
     * 缩写
     */
    private String abbr;
    /**
     * 名称
     */
    private String name;
    /**
     * 首都
     */
    private String capital;
    /**
     * 地区代码
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
     * 国家顶级域名
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

}
