package cc.newex.openapi.bitmex.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author liutiejun
 * @date 2019-04-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BitMexOrderBook {

    private Long id;

    private String symbol;

    private String side;

    private BigDecimal size;

    private BigDecimal price;

}
