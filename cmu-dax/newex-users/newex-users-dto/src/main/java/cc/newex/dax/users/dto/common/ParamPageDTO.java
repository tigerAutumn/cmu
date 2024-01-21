package cc.newex.dax.users.dto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParamPageDTO implements Serializable {
    public static final Integer DEFAULT_PAGE_INDEX = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    /**
     * 当前页数
     */
    @Builder.Default
    private Integer page = 1;
    /**
     * 每页显示条数
     */
    @Builder.Default
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    private Integer total;

    public ParamPageDTO() {
        if (this.page == null) {
            this.page = ParamPageDTO.DEFAULT_PAGE_INDEX;
        }
        if (this.pageSize == null) {
            this.pageSize = ParamPageDTO.DEFAULT_PAGE_SIZE;
        }
    }

}
