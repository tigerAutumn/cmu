package cc.newex.dax.extra.dto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用的树型节点类
 *
 * @author liutiejun
 * @date 2018-09-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeNodeDTO {

    private String id;
    private String pid;
    private String text;
    private String state;
    private String iconCls;
    private Boolean checked;
    private List<TreeNodeDTO> children;

}
