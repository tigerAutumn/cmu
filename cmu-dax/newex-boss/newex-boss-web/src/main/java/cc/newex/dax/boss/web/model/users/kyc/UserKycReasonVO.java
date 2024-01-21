package cc.newex.dax.boss.web.model.users.kyc;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 理由
 *
 * @author liutiejun
 * @date 2018-07-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserKycReasonVO {

    /**
     * 自增id
     */
    private Integer id;

    private String value;

    /**
     * 语言，zh-cn、en-us
     */
    private String locale;

}
