package cc.newex.dax.extra.dto.vlink;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gi
 * @date 10/23/18
 */

@Data
@NoArgsConstructor
public class ResultPageData<T> {
    /**
     * 返回数据
     */
    T content;
    /**
     * 总数
     */
    public Number totalElements;
    /**
     * 总页数
     */
    public Number totalPages;
    /**
     * 当前页数
     */
    public Number number;
    /**
     * 当前页的内容数
     */
    public Number numberOfElements;


}
