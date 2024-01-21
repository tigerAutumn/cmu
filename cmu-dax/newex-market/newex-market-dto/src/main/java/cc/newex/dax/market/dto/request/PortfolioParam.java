package cc.newex.dax.market.dto.request;

import cc.newex.dax.market.dto.model.IndexPortfolioConfigDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by wj on 2018/7/26.
 */
@Data
@Builder
@AllArgsConstructor
public class PortfolioParam {
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
    //币列表
    private List<IndexPortfolioConfigDTO> portfolioConfigList;
}
