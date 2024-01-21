package cc.newex.dax.users.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户收藏币种对
 *
 * @author allen
 * @date 2018/03/18
 **/
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserFavorites {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 产品ID
     */
    private String productIds;
    /**
     * 券商ID
     */
    private Integer brokerId;

}
