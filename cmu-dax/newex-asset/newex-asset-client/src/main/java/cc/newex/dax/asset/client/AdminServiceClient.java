package cc.newex.dax.asset.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.client.config.ClientConfig;
import cc.newex.dax.asset.dto.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@FeignClient(value = "${newex.feignClient.dax.asset}", path = "/inner/v1/asset", configuration = ClientConfig.class)
public interface AdminServiceClient {

    /**
     * 添加币种
     *
     * @param currencyDTO
     * @return
     */
    @PostMapping("/currency/add")
    ResponseResult<?> addCurrency(@RequestBody AssetCurrencyDTO currencyDTO);

    /**
     * 查询币种
     *
     * @param dataGridPager
     * @return
     */
    @PostMapping("/currency/get")
    ResponseResult<?> getCurrencies(@RequestBody DataGridPager<AssetCurrencyDTO> dataGridPager);

    /**
     * 缴纳保证金业务 检查币种是否可用
     *
     * @param currency 币种简写
     * @return
     */
    @RequestMapping("checkCurrencyAvailable")
    ResponseResult<Boolean> checkCurrencyAvailable(@RequestParam("currency") String currency);

    /**
     * 查詢全部幣種
     *
     * @param biz      业务线
     * @param brokerId 券商id 默认1 代表cmx
     * @return
     */
    @GetMapping("/currency/all")
    ResponseResult<?> getAllCurrencies(@RequestParam("biz") String biz, @RequestParam(value = "brokerId", required = false) Integer brokerId);

    /**
     * 編輯幣種
     *
     * @param currencyDTO
     * @return
     */
    @PutMapping("/currency/edit")
    ResponseResult<?> editCurrency(@RequestBody AssetCurrencyDTO currencyDTO);

    @PostMapping("/user-address")
    ResponseResult<?> queryUserAddress(@RequestBody UserAddressDto userAddressDto);

    /**
     * 查询转账记录
     *
     * @param transferRecordReqDto
     * @return
     */
    @PostMapping("/record")
    ResponseResult getTransferRecord(@RequestBody @Valid final TransferRecordReqDto transferRecordReqDto);

    @GetMapping(value = "/transfer-record/{traderNo}")
    ResponseResult queryTransferRecord(@PathVariable("traderNo") final String traderNo);

    @GetMapping(value = "/balanceSummary/threshold/edit")
    ResponseResult editThreshold(@RequestParam("currency") final Integer currency,
                                 @RequestParam("threshold") final BigDecimal threshold);

    @GetMapping(value = "/balanceSummary/get")
    ResponseResult getBalanceSummary(@RequestParam(value = "currency", required = false) String currency,
                                     @RequestParam(value = "timeNode", required = false) Date timeNode,
                                     @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize);

    @GetMapping(value = "/record/statistics")
    ResponseResult<AssetRecordStatisticsDto> getRecordStatistics();


    @RequestMapping("/lockedPosition/list")
    ResponseResult getLockedPositionList(
            @RequestBody DataGridPager pager,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "currencyId", required = false) Integer currencyId,
            @RequestParam(value = "lockPositionName", required = false) String lockPositionName,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "brokerId", required = false) Integer brokerId);

    /**
     * 指定用户锁仓
     * @param tradeNo
     * @param lockPositionName
     * @param userId
     * @param currency
     * @param amount
     * @param dividend
     * @param unlockDate
     * @param brokerId
     * @return
     */
    @RequestMapping("/lockedPosition/lockUser/activity")
    ResponseResult lockUserPositionActivity(@RequestParam(value = "tradeNo") String tradeNo,
                                                   @RequestParam(value = "lockPositionName") String lockPositionName,
                                                   @RequestParam(value = "userId") Long userId,
                                                   @RequestParam(value = "currency") Integer currency,
                                                   @RequestParam(value = "amount") BigDecimal amount,
                                                   @RequestParam(value = "dividend") Integer dividend,
                                                   @RequestParam(value = "unlockDate") Date unlockDate,
                                                   @RequestParam("brokerId") Integer brokerId);

    @PostMapping(value = "/lockedPosition/record")
    ResponseResult getLockedPositionRecord(@RequestBody DataGridPager<LockedPositionRecordReqDto> request);

    @PostMapping("/lockedPosition/lockUser/batch")
    ResponseResult lockUserPositionBatch(@RequestBody UserLockedPositionDto userLockedPositionDto);

    @RequestMapping("/lockedPosition/unlockUser")
    ResponseResult unlockUserPosition(@RequestParam("lockedId") Long lockedId,
                                      @RequestParam("amount") String amount,
                                      @RequestParam("operator") String operator);

    @RequestMapping("/lockedPosition/batchUnlockUser")
    ResponseResult batchUnlockUserPosition(@RequestParam("lockedIds") List<Long> lockedIds,
                                           @RequestParam("amount") String amount,
                                           @RequestParam("operator") String operator);


    @PostMapping(value = "/user-asset-conf/edit")
    ResponseResult editUserAssetConf(@RequestParam("userId") final Long userId,
                                     @RequestParam("withdrawLimit") final BigDecimal withdrawLimit);

    /**
     * 查询提现校验btc额度阈值
     *
     * @return
     */
    @GetMapping("maxBtcWithdrawAmount")
    ResponseResult maxBtcWithdrawAmount();

    /**
     * 设置提现校验btc额度阈值
     *
     * @param maxAmount
     * @return
     */
    @PostMapping("setMaxBtcWithdrawAmount")
    ResponseResult setMaxBtcWithdrawAmount(@RequestParam("maxAmount") String maxAmount);

    /**
     * 查询提现审核额度阈值（btc）
     *
     * @return
     */
    @GetMapping("auditBtcWithdrawAmount")
    ResponseResult auditBtcWithdrawAmount();

    /**
     * 设置提现审核额度阈值（btc）
     *
     * @param auditAmount 阈值 超过多少额度需要人工审核
     * @return
     */
    @PostMapping("setAuditAmount")
    ResponseResult setAuditBtcWithdrawAmount(@RequestParam("auditAmount") String auditAmount);

    /**
     * 一键冻结  划转和提现
     *
     * @param biz  业务类型 默认传spot
     * @param type 1是冻结 2是解冻
     * @return
     */
    @RequestMapping("/currency/frozen")
    ResponseResult oneStepFrozen(@RequestParam("biz") String biz,
                                 @RequestParam("type") Integer type);

    /**
     * boss划转功能
     *
     * @param bossTransferDto
     * @return
     */
    @PostMapping("bossTransfer")
    ResponseResult bossTransfer(@RequestBody BossTransferDto bossTransferDto);

    /**
     * 查询用户提现额度
     *
     * @param userId
     * @param currencyId
     * @param brokerId
     * @return
     */
    @RequestMapping("getWithdrawLimit")
    ResponseResult<WithDrawInfoDto> getWithdrawLimit(@RequestParam("userId") Long userId,
                                                     @RequestParam("currencyId") Integer currencyId,
                                                     @RequestParam("brokerId") Integer brokerId,
                                                     @RequestParam(value = "biz", defaultValue = "spot", required = false) String biz);

    /**
     * 更新用户提现额度
     *
     * @param userId
     * @param currencyId
     * @param brokerId
     * @param amount
     * @return
     */
    @RequestMapping("updateCanWithdrawNumber")
    ResponseResult updateCanWithdrawNumber(@RequestParam("userId") Long userId,
                                           @RequestParam("currencyId") Integer currencyId,
                                           @RequestParam("brokerId") Integer brokerId,
                                           @RequestParam("amount") BigDecimal amount);

    /**
     * 通过tradeNo查询锁仓记录
     *
     * @param tradeNo
     * @return
     */
    @RequestMapping("queryLockedPositionByTradeNo")
    ResponseResult<LockedPositionPageDto> queryLockedPositionByTradeNo(@RequestParam("tradeNo") String tradeNo);

}