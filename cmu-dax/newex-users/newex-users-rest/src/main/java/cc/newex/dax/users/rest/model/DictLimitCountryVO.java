package cc.newex.dax.users.rest.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2018/6/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DictLimitCountryVO {

    private String name;

    /**
     * 是否是当前访问的国家
     */
    private Boolean visit;

}
