package cc.newex.dax.market.dto.model;

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
public class PortfolioInfo {
    //组合指数配置列表
    private List<IndexPortfolioConfigDTO> portfolioConfigList;
    //初始化时间
    private Long initialDate;
    //初始完成
    private Boolean initialCompleted = false;
}
