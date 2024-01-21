package cc.newex.dax.users.dto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int pageSize = 10;

    private Long total;

    private List<T> rows;
}
