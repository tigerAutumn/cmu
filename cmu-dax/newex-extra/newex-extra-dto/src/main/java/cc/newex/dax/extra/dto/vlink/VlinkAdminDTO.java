package cc.newex.dax.extra.dto.vlink;

import lombok.*;

import java.io.Serializable;

/**
 * @author gi
 * @date 11/16/18
 */

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VlinkAdminDTO implements Serializable {

    /**
     * 转账记录
     */
    private String transactionId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * vlink账号email
     */
    private String email;

    /**
     * 币种id
     */
    private Integer currencyId;
}
