package cc.newex.dax.perpetual.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author newex-team
 * @date 2018/11/01
 */
@Data
@Builder
@Validated
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParamPageDTO {

    public static final Integer DEFAULT_PAGE_INDEX = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 300;
    public static final Integer AMOUNT_PAGE_SIZE = 10;

    /**
     * 当前页数
     */
    @Min(1)
    private Integer page;

    /**
     * 每页显示条数
     */
    @Min(1)
    @Max(300)
    private Integer pageSize;

    private Integer total;

    public ParamPageDTO() {
        if (this.page == null) {
            this.page = DEFAULT_PAGE_INDEX;
        }

        if (this.pageSize == null) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }

    }

    public static ParamPageDTO toParamPage(Integer page, Integer pageSize) {
        if (page == null || page <= 0) {
            page = DEFAULT_PAGE_INDEX;
        }

        if (pageSize == null || pageSize <= 0) {
            pageSize = AMOUNT_PAGE_SIZE;
        }

        final ParamPageDTO paramPageDTO = ParamPageDTO.builder()
                .page(page)
                .pageSize(pageSize)
                .build();

        return paramPageDTO;
    }

}
