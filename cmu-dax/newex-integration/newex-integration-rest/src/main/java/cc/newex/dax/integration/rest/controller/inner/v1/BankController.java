package cc.newex.dax.integration.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.domain.bank.BankInfo;
import cc.newex.dax.integration.domain.bank.BankInfoBO;
import cc.newex.dax.integration.dto.bank.BankInfoResDTO;
import cc.newex.dax.integration.service.bank.BankInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证银行卡二,三,四要素
 *
 * @author newex-team
 * @date 2018/5/17
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/integration/bank")
public class BankController {

    @Autowired
    private BankInfoService bankinfoService;

    /**
     * 验证银行卡三要素
     */
    @GetMapping("/info")
    public ResponseResult<BankInfoResDTO> getBankInfo(@RequestParam("bankCard") final String bankCard,
                                                      @RequestParam("realName") final String realName,
                                                      @RequestParam("cardNo") final String cardNo) {
        final BankInfoBO bo = BankInfoBO.builder()
                .bankcard(bankCard)
                .realName(realName)
                .cardNo(cardNo)
                .build();

        final BankInfo bankInfo = this.bankinfoService.getBankInfo(bo);
        if (bankInfo == null) {
            return ResultUtils.success(null);
        }

        final BankInfoResDTO bankInfoResDTO = BankInfoResDTO.builder()
                .name(bankInfo.getName())
                .abbreviation(bankInfo.getAbbreviation())
                .build();

        return ResultUtils.success(bankInfoResDTO);
    }
}
