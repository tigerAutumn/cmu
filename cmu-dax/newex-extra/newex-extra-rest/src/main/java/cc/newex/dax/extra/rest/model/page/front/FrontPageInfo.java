package cc.newex.dax.extra.rest.model.page.front;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liutiejun
 * @date 2018-08-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FrontPageInfo {

    /**
     * 当前页码
     */
    private Integer pageIndex;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pageCount;

    /**
     * 总记录数
     */
    private Long totalItemCount;

}
