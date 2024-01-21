package cc.newex.dax.users.dto.membership;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author allen
 * @date 2018/03/18
 * @des 收藏信息
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoritesParamDTO implements Serializable {

    private static final long serialVersionUID = 6391583909065877904L;

    /**
     * 产品IDs
     */
    @NotNull
    private List<Integer> ids;

    /**
     * 类型
     */
    @NonNull
    private String type;

}
