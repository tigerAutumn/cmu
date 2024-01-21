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
public class DictCountryResDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String locale;
    /**
     *
     */
    private String abbr;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String capital;
    /**
     *
     */
    private Integer areaCode;
    /**
     *
     */
    private Integer countryCode;
    /**
     *
     */
    private String currencyCode;
    /**
     *
     */
    private String currencyName;
    /**
     *
     */
    private String domainCode;
    /**
     *
     */
    private String letterCode2;
    /**
     *
     */
    private String letterCode3;
    /**
     *
     */
    private Integer status;
    /**
     *
     */
    private Date createdDate;
    /**
     *
     */
    private Date updatedDate;
}
