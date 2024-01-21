package cc.newex.dax.push.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.portfolio.client.inner.PortfolioConfigClient;
import cc.newex.dax.portfolio.dto.PortfolioConfigDTO;

/**
 * {@link PortfolioConfigClient}
 *
 * @author xionghui
 * @date 2018/09/27
 */
@FeignClient(value = "${newex.feignClient.dax.portfolio}",
    path = "/inner/v1/portfolio/portfolioConfig")
public interface FeignPortfolioConfigClient {

  @GetMapping("/list")
  ResponseResult<List<PortfolioConfigDTO>> portfolioConfigList();
}
