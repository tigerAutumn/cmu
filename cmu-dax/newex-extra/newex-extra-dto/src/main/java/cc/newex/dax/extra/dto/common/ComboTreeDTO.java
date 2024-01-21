package cc.newex.dax.extra.dto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liutiejun
 * @date 2018-06-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ComboTreeDTO {

    private Integer id;

    private String text;

    private String text2;

    private ComboTreeDTO[] children;

}
