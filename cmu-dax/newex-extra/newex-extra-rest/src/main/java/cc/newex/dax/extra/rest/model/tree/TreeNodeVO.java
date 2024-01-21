package cc.newex.dax.extra.rest.model.tree;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liutiejun
 * @date 2018-09-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeNodeVO {

    private String id;
    private String pid;
    private String text;
    private List<TreeNodeVO> children;

    private Integer total;

}
