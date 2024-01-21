package cc.newex.dax.market.dto.result;

import cc.newex.dax.market.dto.enums.CoinConfigStatusEnum;
import cc.newex.dax.market.dto.model.IndexPortfolioConfigDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by wj on 2018/7/26.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinConfigDto {
    //Id
    private Long id;
    //指数ID
    private Integer symbol;
    //指数名称
    private String symbolName;
    //初始化时间
    private Long initialDate;
    //状态
    private Integer status;
    //状态枚举
    private CoinConfigStatusEnum statusEnum;
    //币列表
    private List<IndexPortfolioConfigDTO> portfolioConfigList;
}
