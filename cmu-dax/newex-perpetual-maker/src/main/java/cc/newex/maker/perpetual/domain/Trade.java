package cc.newex.maker.perpetual.domain;

import lombok.Data;

@Data
public class Trade {
    /**
     * 成交时间
     */
    private String timestamp;
    /**
     * 成交数量
     */
    private String size;
    /**
     * 成交价格
     */
    private String price;
    /**
     * 成交方向：sell，buy
     */
    private String side;
}
