package cc.newex.wallet.pojo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyBalance implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer currencyIndex;
    /**
     *
     */
    private BigDecimal balance;
    /**
     *
     */
    private Date createDate;
    /**
     *
     */
    private Date updateDate;
}