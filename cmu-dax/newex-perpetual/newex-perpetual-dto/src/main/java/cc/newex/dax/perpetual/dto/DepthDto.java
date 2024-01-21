package cc.newex.dax.perpetual.dto;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepthDto {

    /**
     * 买方深度
     */
    private List<DepthLine> bids;

    /**
     * 卖方深度
     */
    private List<DepthLine> asks;

    public DepthDto() {
        bids = new LinkedList<>();
        asks = new LinkedList<>();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DepthLine {
        /**
         * 价格，卖方深度 price asc，买方深度 price desc
         */
        @JSONField(serializeUsing = BigDecimalSerializer.class)
        private BigDecimal price;

        /**
         * 数量
         */
        private int totalSize;
    }
}
