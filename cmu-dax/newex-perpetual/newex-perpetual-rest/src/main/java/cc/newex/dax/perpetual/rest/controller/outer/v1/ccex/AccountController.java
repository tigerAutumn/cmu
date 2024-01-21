package cc.newex.dax.perpetual.rest.controller.outer.v1.ccex;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.commons.ucenter.model.SessionInfo;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.PositionClear;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.domain.bean.AssetsBalanceBean;
import cc.newex.dax.perpetual.domain.bean.ContractDetail;
import cc.newex.dax.perpetual.domain.bean.Currency;
import cc.newex.dax.perpetual.domain.bean.PositionClearBean;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.rest.controller.base.BaseController;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.PositionClearService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.common.CurrencyService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import cc.newex.dax.perpetual.util.ObjectCopyUtil;
import cc.newex.dax.users.client.UsersClient;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/v1/perpetual/account")
public class AccountController extends BaseController {
    @Resource
    private UserBalanceService userBalanceService;
    @Resource
    private UserPositionService userPositionService;
    @Resource
    private ContractService contractService;
    @Resource
    private MarketService marketService;
    @Resource
    private CurrencyPairService currencyPairService;
    @Resource
    private UsersClient usersClient;
    @Resource
    private CurrencyService currencyService;
    @Resource
    private PositionClearService positionClearService;

    /**
     * 我的资产
     *
     * @param contractCode
     * @return
     */
    @GetMapping("/assets/{contractCode}")
    public ResponseResult account(@PathVariable("contractCode") final String contractCode) {
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        this.userBalanceService.checkUserAccountByContract(userJwt.getUserId(), brokerId);

        final Contract contract = this.contractService.getContract(contractCode);
        final int digit = 8;
//        if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
//            digit = contract.getMinQuoteDigit();
//        } else {
//            digit = contract.getMinTradeDigit();
//        }
        final UserBalance userBalance =
                this.userBalanceService.get(contract.getBase(), userJwt.getUserId(), brokerId);
        final Map result = Maps.newConcurrentMap();
        result.put("currencyCode", userBalance.getCurrencyCode());// 币种
        result.put("env", userBalance.getEnv());// 是否测试币 0:线上币,1:测试币
        result.put("availableMargin", BigDecimalUtil.setDigit(userBalance.getAvailableBalance(), digit));// 可用保证金
        result.put("realizedSurplus", BigDecimalUtil.setDigit(userBalance.getRealizedSurplus(), digit));// 已实现盈亏
        result.put("orderMargin", BigDecimalUtil.setDigit(userBalance.getOrderMargin().add(userBalance.getOrderFee()), digit));// 委托保证金
        result.put("positionMargin", BigDecimalUtil.setDigit(userBalance.getPositionMargin().add(userBalance.getPositionFee()), digit));// 仓位保证金
        return ResultUtils.success(result);
    }

    /**
     * 用户资产列表
     *
     * @return
     */
    @GetMapping("assets")
    public ResponseResult accounts() {
        final JwtUserDetails userJwt = this.getUserDetails();
        final Long userId = userJwt.getUserId();
        final Integer brokerId = this.getBrokerId();
        this.userBalanceService.checkUserAccountByContract(userJwt.getUserId(), brokerId);
        final List<Currency> currencyList = this.currencyService.listCurrencies();
        final List<UserBalance> userBalanceList = this.userBalanceService.get(userJwt.getUserId(), brokerId);
        if (CollectionUtils.isEmpty(userBalanceList)) {
            return ResultUtils.success();
        }
        final List<AssetsBalanceBean> resultList = Lists.newArrayList();
        for (final Currency currency : currencyList) {
            final AssetsBalanceBean result = new AssetsBalanceBean();
            resultList.add(result);
            result.setCurrencyCode(currency.getSymbol());
            List<Contract> contractList = this.contractService.getUnExpiredContract();
            contractList = contractList.stream().filter(x -> x.getBase().equalsIgnoreCase(currency.getSymbol())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(contractList)) {
                for (final Contract contract : contractList) {
                    final int digit = 8;
                    final UserBalance userBalance = this.userBalanceService.get(contract.getBase().toLowerCase(), userId, brokerId);
                    result.setContractCode(contract.getContractCode());
                    result.setEnv(userBalance.getEnv());// 是否测试币 0:线上币,1:测试币
                    result.setAvailableBalance(BigDecimalUtil.setDigit(userBalance.getAvailableBalance(), digit));
                    result.setRealizedSurplus(BigDecimalUtil.setDigit(userBalance.getRealizedSurplus(), digit));// 已实现盈亏
                    result.setOrderMargin(BigDecimalUtil.setDigit(userBalance.getOrderMargin().add(userBalance.getOrderFee()), digit));// 委托保证金
                    result.setPositionMargin(BigDecimalUtil.setDigit(userBalance.getPositionMargin().add(userBalance.getPositionFee()), digit));// 仓位保证金
                    result.setWithdrawable(currency.getWithdrawable());//提现：false：不是提现货币，true:提现货币
                    result.setRechargeable(currency.getRechargeable());//充值：false：不是充值货币，true：充值货币
                    result.setExchange(currency.getExchange());//可兑换：false：不可兑换，true：可以兑换
                    result.setTransfer(currency.getTransfer());//可划转 0:不可划转，1 可划转
                    break;
                }
            } else {
                final int digit = 8;
                final UserBalance userBalance = this.userBalanceService.get(currency.getSymbol().toLowerCase(), userId, brokerId);
                result.setContractCode("");
                result.setEnv(userBalance.getEnv());// 是否测试币 0:线上币,1:测试币
                result.setAvailableBalance(BigDecimalUtil.setDigit(userBalance.getAvailableBalance(), digit));
                result.setRealizedSurplus(BigDecimalUtil.setDigit(userBalance.getRealizedSurplus(), digit));// 已实现盈亏
                result.setOrderMargin(BigDecimalUtil.setDigit(userBalance.getOrderMargin().add(userBalance.getOrderFee()), digit));// 委托保证金
                result.setPositionMargin(BigDecimalUtil.setDigit(userBalance.getPositionMargin().add(userBalance.getPositionFee()), digit));// 仓位保证金
                result.setWithdrawable(currency.getWithdrawable());//提现：false：不是提现货币，true:提现货币
                result.setRechargeable(currency.getRechargeable());//充值：false：不是充值货币，true：充值货币
                result.setExchange(currency.getExchange());//可兑换：false：不可兑换，true：可以兑换
                result.setTransfer(currency.getTransfer());//可划转 0:不可划转，1 可划转
            }
        }
        return ResultUtils.success(resultList);
    }

    /**
     * 合约明细
     */
    @PostMapping("/{contractCode}/detail")
    public ResponseResult contractDetail(@PathVariable("contractCode") final String contractCode) {
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        final UserPosition userPosition =
                this.userPositionService.getUserPosition(userJwt.getUserId(), brokerId, contractCode);
        final List<UserPositionService.UserRank> userRankList;
        if (OrderSideEnum.LONG.getSide().equalsIgnoreCase(userPosition.getSide())) {
            userRankList = this.userPositionService.getUserRank(contractCode, OrderSideEnum.LONG);
        } else {
            userRankList = this.userPositionService.getUserRank(contractCode, OrderSideEnum.SHORT);
        }
        final Contract contract = this.contractService.getContract(contractCode);
        final CurrencyPair currencyPair =
                this.currencyPairService.getByPairCode(contract.getPairCode());
        final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
        final ContractDetail contractDetail = new ContractDetail();
        contractDetail.setUserRankList(userRankList);
        contractDetail.setContractCode(contractCode);
        contractDetail.setIndexPrice(markIndexPriceDTO.getIndexPrice());
        contractDetail.setMarkPrice(markIndexPriceDTO.getMarkPrice());
        contractDetail.setRiskLimit(userPosition.getGear());
        contractDetail
                .setUnPositionAmount(userPosition.getAmount().subtract(userPosition.getClosingAmount()));
        contractDetail.setMaxPrice(PerpetualConstants.MAX_PRICE);
        contractDetail.setMaxAmount(currencyPair.getMaxOrderAmount());
        contractDetail.setReasonablePrice(
                markIndexPriceDTO.getMarkPrice().subtract(markIndexPriceDTO.getIndexPrice()));

        return ResultUtils.success(contractDetail);
    }

    /**
     * 开通合约协议
     *
     * @param request
     */
    @PostMapping("/open-protocol")
    public ResponseResult openPerpetualProtocol(final HttpServletRequest request) {
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        this.userBalanceService.checkUserAccountByContract(userJwt.getUserId(), this.getBrokerId());
        final ResponseResult<Integer> responseResult = this.usersClient.updatePerpetualProtocolFlag(userJwt.getUserId(), 1);
        if (responseResult == null || responseResult.getData() != 1) {
            AccountController.log.error("开通合约协议失败");
            return ResultUtils.failure(BizErrorCodeEnum.OPEN_PERPETUAL_PROTOCOL_ERROR);
        }
        final SessionInfo sessionInfo = SessionInfo.builder().userId(userJwt.getUserId()).perpetualProtocolFlag(1).build();
        JwtTokenUtils.updateSession(sessionInfo);
        final BigDecimal reward = this.userBalanceService.rewardFbtc(userJwt.getUserId(), brokerId);
        final JSONObject data = new JSONObject();
        data.put("reward", reward);
        return ResultUtils.success(data);
    }


    /**
     * 测试币领取资产
     *
     * @return
     */
    @PostMapping("/{currencyCode}/assets/reward")
    public ResponseResult accountReward(
            @PathVariable("currencyCode") final String currencyCode) {
        final JwtUserDetails userJwt = this.getUserDetails();
        final Integer brokerId = this.getBrokerId();
        this.userBalanceService.checkUserAccountByContract(userJwt.getUserId(), brokerId);
        final UserBalance userBalance =
                this.userBalanceService.get(currencyCode, userJwt.getUserId(), brokerId);
        // 非测试币不能转钱
        if (userBalance == null || userBalance.getEnv() == 0) {
            return ResultUtils.failure(BizErrorCodeEnum.ILLEGAL_PARAM);
        }
        final BigDecimal reward = this.userBalanceService.reward(currencyCode, userJwt.getUserId(), brokerId);
        final JSONObject data = new JSONObject();
        data.put("reward", reward);
        return ResultUtils.success(data);
    }

    /**
     * 清算记录
     *
     * @param contractCode
     * @return
     */
    @GetMapping("/{contractCode}/settlement-list")
    public ResponseResult<DataGridPagerResult<PositionClearBean>> settlementList(@PathVariable("contractCode") final String contractCode,
                                                                                 @RequestParam(value = "page", required = false, defaultValue = "1") final Integer page,
                                                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "100") final Integer pageSize) {
        final JwtUserDetails userJwt = this.getUserDetails();
        final Long userId = userJwt.getUserId();
        final Integer brokerId = this.getBrokerId();
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize((pageSize == null || pageSize <= 0 || pageSize >= 100) ? 100 : pageSize);
        pageInfo.setStartIndex((page == null || page <= 1) ? 0 : (page - 1) * pageInfo.getPageSize());
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_DES);
        final List<PositionClear> positionClearList = this.positionClearService.getPositionClear(brokerId, userId, contractCode, pageInfo);
        final List<PositionClearBean> resultList = ObjectCopyUtil.mapList(positionClearList, PositionClearBean.class);
        final DataGridPagerResult<PositionClearBean> result = new DataGridPagerResult();
        result.setRows(resultList);
        result.setTotal(pageInfo.getTotals());
        return ResultUtils.success(result);
    }
}
