package cc.newex.dax.integration.domain.bank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2018-06-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankInfo {
    /**
     * 银行名称
     */
    private String name;
    /**
     * 缩写
     */
    private String abbreviation;
}
