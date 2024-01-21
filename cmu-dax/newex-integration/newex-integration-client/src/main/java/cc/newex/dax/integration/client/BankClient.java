package cc.newex.dax.integration.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.dto.bank.BankInfoResDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("${newex.feignClient.dax.integration}")
public interface BankClient {
    /**
     * 验证银行卡三要素
     *
     * @param bankCard 银行卡号码
     * @param realName 姓名
     * @param cardNo   身份证号码
     */
    @GetMapping("/inner/v1/integration/bank/info")
    ResponseResult<BankInfoResDTO> getBankInfo(@RequestParam("bankCard") final String bankCard,
                                               @RequestParam("realName") final String realName,
                                               @RequestParam("cardNo") final String cardNo);
}