package cc.newex.dax.asset.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.dto.TransferRecordAuditReqDto;
import cc.newex.dax.asset.dto.TransferRecordAuditResDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author newex-team
 * @data 06/04/2018
 */
@FeignClient(name = "${newex.feignClient.dax.asset}", path = "/inner/v1/asset")
public interface AssetTransferServiceClient {
    @PostMapping(value = "/transfer-in")
    ResponseResult transferIn(@RequestParam("userId") final long userId,
                              @RequestParam("currencyId") final int currencyId,
                              @RequestParam("amount") final BigDecimal amount,
                              @RequestParam("tradeNo") final String tradeNo);

    @PostMapping(value = "/transfer-out")
    ResponseResult transferOut(@RequestParam("currency") final String symbol,
                               @RequestParam("fromUser") final Long fromUser,
                               @RequestParam("toUser") final Long toUser,
                               @RequestParam("amount") final BigDecimal amount,
                               @RequestParam("tradeNo") final String tradeNo,
                               @RequestParam("brokerId") Integer brokerId);

    @PostMapping(value = "/add-token")
    ResponseResult addTokenToUser(@RequestParam("userId") final long userId,
                                  @RequestParam("currency") final String symbol,
                                  @RequestParam("fromBiz") final String fromBiz,
                                  @RequestParam("toBiz") final String toBiz,
                                  @RequestParam("amount") final BigDecimal amount,
                                  @RequestParam("brokerId") Integer brokerId);

    @PostMapping(value = "/withdraw")
    ResponseResult withdraw(@RequestParam("userId") final long userId,
                            @RequestParam("biz") String biz,
                            @RequestParam("currencyCode") final String currencyCode,
                            @RequestParam("address") final String address,
                            @RequestParam("amount") final BigDecimal amount,
                            @RequestParam("brokerId") Integer brokerId);

    @RequestMapping("/lockedPosition/lockUser/activity")
    ResponseResult lockUserPositionActivity(@RequestParam(value = "tradeNo") String tradeNo,
                                            @RequestParam(value = "lockPositionName") String lockPositionName,
                                            @RequestParam(value = "userId") Long userId,
                                            @RequestParam(value = "currency") Integer currency,
                                            @RequestParam(value = "amount") BigDecimal amount,
                                            @RequestParam(value = "dividend") Integer dividend,
                                            @RequestParam(value = "unlockDate") Date unlockDate,
                                            @RequestParam("brokerId") Integer brokerId);

    @RequestMapping("/lockedPosition/unlockUser/activity")
    ResponseResult unlockUserPositionActivity(@RequestParam("tradeNo") String tradeNo,
                                              @RequestParam("amount") String amount,
                                              @RequestParam("operator") String operator);


    @RequestMapping(value = "/withdraw/audit")
    ResponseResult<DataGridPagerResult<TransferRecordAuditResDto>> withdrawAudit(@RequestBody final DataGridPager<TransferRecordAuditResDto> pager);

    @PostMapping(value = "/withdraw/audit/oper")
    ResponseResult withdraw(@RequestBody TransferRecordAuditReqDto transferRecordAuditReqDto);
}
