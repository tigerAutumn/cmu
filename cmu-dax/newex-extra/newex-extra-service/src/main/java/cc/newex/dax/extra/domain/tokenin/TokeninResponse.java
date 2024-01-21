package cc.newex.dax.extra.domain.tokenin;

import lombok.*;

/**
 * TokenInsight API返回数据封装
 *
 * @author liutiejun
 * @date 2018-07-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokeninResponse<T> {

    private Integer code;

    private T data;

}
