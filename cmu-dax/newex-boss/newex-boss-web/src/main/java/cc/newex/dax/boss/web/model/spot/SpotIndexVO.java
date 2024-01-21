package cc.newex.dax.boss.web.model.spot;

import cc.newex.dax.market.dto.enums.CoinConfigStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 指数管理
 *
 * @author liutiejun
 * @date 2018-08-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotIndexVO {

    private Long id;

    @NotNull
    private Integer symbol;

    private String symbolName;

    private Long initialDate;

    @NotNull
    private Integer status;

    private CoinConfigStatusEnum statusEnum;

}
