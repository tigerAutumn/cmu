package cc.newex.dax.perpetual.client.inner;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = "${newex.feignClient.dax.perpetual}", path = "/inner/v1/perpetual/price")
public interface MarketPriceClient {

    /**
     * 获取标记价格
     *
     * @param contractCode 合约 code
     * @return
     */
    @GetMapping("/getMarketPrice")
    ResponseResult<List<MarkIndexPriceDTO>> getMarketPrice(
            @RequestParam(value = "contractCode", required = false) final String[] contractCode);
}
