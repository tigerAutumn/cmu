package cc.newex.dax.perpetual.domain.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepthOrderInfo {
    /**
     * 卖单
     */
    private List<String[]> asks;

    /**
     * 买单
     */
    private List<String[]> bids;

}
