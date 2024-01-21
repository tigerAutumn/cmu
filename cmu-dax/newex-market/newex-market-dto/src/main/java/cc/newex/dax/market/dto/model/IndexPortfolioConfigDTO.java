package cc.newex.dax.market.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wj on 2018/7/24.
 */
@Data
@Builder
@AllArgsConstructor
public class IndexPortfolioConfigDTO {
    //指数
    private Integer symbol;
    //名称
    private String symbolName;
    //比列
    private Double ratio;
    //初始价格
    private Double price;
    //最新价格
    private Double lastPrice;
    //份数
    private Double amount;
}
