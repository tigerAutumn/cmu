package cc.newex.dax.extra.dto.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字段校验异常信息
 *
 * @author liutiejun
 * @date 2018-08-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErrorMessage {

    private String field;

    private String defaultMessage;

}
