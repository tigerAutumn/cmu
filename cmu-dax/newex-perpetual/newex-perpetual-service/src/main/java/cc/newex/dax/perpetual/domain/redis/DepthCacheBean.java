package cc.newex.dax.perpetual.domain.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepthCacheBean {
    /**
     * 买方深度
     */
    private List<DepthLine> bids;
    /**
     * 卖方深度
     */
    private List<DepthLine> asks;
    private String symbol;

    public DepthCacheBean() {
        this.bids = new ArrayList<>();
        this.asks = new ArrayList<>();
    }
}
