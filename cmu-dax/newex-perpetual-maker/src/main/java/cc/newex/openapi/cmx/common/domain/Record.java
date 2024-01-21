package cc.newex.openapi.cmx.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record<T, I> {
    /**
     * 分页返回 header 中 cb_before
     */
    private I before;
    /**
     * 分页返回 header 中 cb_after
     */
    private I after;
    /**
     * 分页返回实体数据
     */
    private T data;
}
