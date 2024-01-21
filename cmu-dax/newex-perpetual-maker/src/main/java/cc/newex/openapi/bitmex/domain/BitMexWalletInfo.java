package cc.newex.openapi.bitmex.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liutiejun
 * @date 2019-04-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BitMexWalletInfo {

    private Long account;

    private String currency;

    private Long amount;

    private String timestamp;

    private String addr;
}
