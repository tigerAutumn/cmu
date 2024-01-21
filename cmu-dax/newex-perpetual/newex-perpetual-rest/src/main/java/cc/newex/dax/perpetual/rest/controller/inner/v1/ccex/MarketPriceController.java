package cc.newex.dax.perpetual.rest.controller.inner.v1.ccex;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.service.common.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/inner/v1/perpetual/price")
public class MarketPriceController {

    @Autowired
    private MarketService marketService;

    /**
     * 获取指数价格
     *
     * @param contractCode 合约
     * @return
     */
    @GetMapping("/getMarketPrice")
    public ResponseResult<List<MarkIndexPriceDTO>> getMarketPrice(
            @RequestParam(value = "contractCode", required = false) final String[] contractCode) {
        final Map<String, MarkIndexPriceDTO> priceMap = this.marketService.allMarkIndexPrice();
        if (contractCode == null || contractCode.length == 0) {
            return ResultUtils.success(new ArrayList<>(priceMap.values()));
        }
        final Set<String> codes = new HashSet<>(Arrays.asList(contractCode));
        final List<MarkIndexPriceDTO> result = new LinkedList<>();
        for (final Map.Entry<String, MarkIndexPriceDTO> entry : priceMap.entrySet()) {
            if (codes.contains(entry.getKey())) {
                result.add(entry.getValue());
            }
        }
        return ResultUtils.success(result);
    }
}
