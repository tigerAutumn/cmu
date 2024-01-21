package cc.newex.commons.mybatis.pager.front;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 提供给前端的分页对象
 *
 * @author liutiejun
 * @date 2018-11-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FrontPagerResult<T> {

    /**
     * 当前页数据项
     */
    private List<T> items;

    /**
     * 分页数据
     */
    private FrontPageInfo pageInfo;

    public FrontPagerResult(final List<T> items, final Integer pageIndex, final Integer pageSize, final Long totalItemCount) {
        this.items = items;

        this.pageInfo = FrontPageInfo.builder()
                .pageIndex(pageIndex)
                .pageSize(pageSize)
                .pageCount(calPageCount(pageSize, totalItemCount))
                .totalItemCount(totalItemCount)
                .build();
    }

    private static Integer calPageCount(final Integer pageSize, final Long totalItemCount) {
        if (totalItemCount == null || totalItemCount <= 0) {
            return 0;
        }

        final Integer pageCount = (int) Math.ceil(totalItemCount * 1.0 / pageSize);

        return pageCount;
    }

}
