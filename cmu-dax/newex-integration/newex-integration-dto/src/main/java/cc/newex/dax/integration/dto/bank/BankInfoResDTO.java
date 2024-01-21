package cc.newex.dax.integration.dto.bank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankInfoResDTO {
    /**
     * 银行名称
     */
    private String name;
    /**
     * 缩写
     */
    private String abbreviation;
}
