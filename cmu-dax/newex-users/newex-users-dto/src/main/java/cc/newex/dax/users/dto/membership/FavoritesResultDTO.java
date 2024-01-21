package cc.newex.dax.users.dto.membership;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author allen
 * @date 2018/4/5
 * @des favorites list
 */
@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FavoritesResultDTO implements Serializable {
    private static final long serialVersionUID = 8796106333891878480L;

    /**
     * spot
     */
    private List<Integer> spot;

    /**
     * futures
     */
    private List<Integer> futures;
}
