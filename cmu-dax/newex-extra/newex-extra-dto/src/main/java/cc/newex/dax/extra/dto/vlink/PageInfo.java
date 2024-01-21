package cc.newex.dax.extra.dto.vlink;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gi
 * @date 10/29/18
 */

@Data
@NoArgsConstructor
public class PageInfo {
    private Integer page = 0;
    private Integer size = 50;
}
