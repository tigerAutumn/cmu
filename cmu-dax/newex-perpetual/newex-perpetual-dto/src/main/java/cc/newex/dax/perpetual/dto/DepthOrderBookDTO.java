package cc.newex.dax.perpetual.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 深度dto
 *
 * @author newex-team
 * @date 2018-11-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepthOrderBookDTO {

    /**
     * 卖单
     */
    private List<String[]> asks;

    /**
     * 买单
     */
    private List<String[]> bids;

}
