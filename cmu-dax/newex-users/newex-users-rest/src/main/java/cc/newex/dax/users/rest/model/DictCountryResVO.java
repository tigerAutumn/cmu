package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictCountryResVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer areaCode;
    private Integer countryCode;
    private String name;
    private Integer status;
}
