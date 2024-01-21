package cc.newex.commons.mybatis.pager.front;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liutiejun
 * @date 2018-11-06
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
