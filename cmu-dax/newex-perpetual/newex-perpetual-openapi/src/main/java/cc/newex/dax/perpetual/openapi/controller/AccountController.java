package cc.newex.dax.perpetual.openapi.controller;

import cc.newex.commons.openapi.specs.annotation.OpenApi;
import cc.newex.commons.openapi.specs.annotation.OpenApiAuthValidator;
import cc.newex.commons.openapi.specs.annotation.OpenApiRateLimit;
import cc.newex.commons.openapi.specs.model.OpenApiUserInfo;
import cc.newex.dax.perpetual.common.converter.UserBillConverter;
import cc.newex.dax.perpetual.common.enums.BillTypeEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserBill;
import cc.newex.dax.perpetual.domain.bean.AssetsBalanceBean;
import cc.newex.dax.perpetual.domain.bean.Currency;
import cc.newex.dax.perpetual.dto.BillResultDTO;
import cc.newex.dax.perpetual.dto.ParamPageDTO;
import cc.newex.dax.perpetual.dto.UserBillDTO;
import cc.newex.dax.perpetual.openapi.controller.base.BaseController;
import cc.newex.dax.perpetual.openapi.support.common.AuthenticationUtils;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.UserBillService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.common.CurrencyService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import cc.newex.dax.users.client.UsersClient;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * V1 open api account 相关接口
 *
 * @author newex-team
 * @date 2018/11/01
 */

@Slf4j
@OpenApi
@RestController
@RequestMapping("/api/v1/perpetual/account")
public class AccountController extends BaseController {

    @Autowired
    private UsersClient usersClient;

    @Autowired
    private MarketService marketService;

    @Autowired
    private UserBillService userBillService;
    @Autowired
    private AuthenticationUtils authenticationUtils;
    @Autowired
    private UserBalanceService userBalanceService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private UserPositionService userPositionService;
    @Resource
    private CurrencyService currencyService;

    /**
     * 我的资产
     */
    @GetMapping("/assets/{contractCode}")
    @OpenApiRateLimit(value = "10/1")
    @OpenApiAuthValidator(method = HttpMethod.GET)
    public Map account(@PathVariable("contractCode") final String contractCode, final HttpServletRequest request) {
        final OpenApiUserInfo openApiUserInfo = this.authenticationUtils.getUserInfo(request);

        final Long userId = openApiUserInfo.getId();
        final Integer brokerId = this.getBrokerId();
        this.userBalanceService.checkUserAccountByContract(userId, brokerId);

        final Contract contract = this.contractService.getContract(contractCode);
        final int digit = 8;
        final UserBalance userBalance = this.userBalanceService.get(contract.getBase(), userId, brokerId);

        final Map result = Maps.newHashMap();
        result.put("currencyCode", userBalance.getCurrencyCode());// 币种
        result.put("env", userBalance.getEnv());// 是否测试币 0:线上币,1:测试币
        result.put("availableMargin", BigDecimalUtil.setDigit(userBalance.getAvailableBalance(), digit));// 可用保证金
        result.put("realizedSurplus", BigDecimalUtil.setDigit(userBalance.getRealizedSurplus(), digit));// 已实现盈亏
        result.put("orderMargin", BigDecimalUtil.setDigit(userBalance.getOrderMargin().add(userBalance.getOrderFee()), digit));// 委托保证金
        result.put("positionMargin", BigDecimalUtil.setDigit(userBalance.getPositionMargin().add(userBalance.getPositionFee()), digit));// 仓位保证金

        return result;
    }

    /**
     * 用户资产列表
     */
    @GetMapping("/assets")
    @OpenApiRateLimit(value = "10/1")
    @OpenApiAuthValidator(method = HttpMethod.GET)
    public List<AssetsBalanceBean> accountInfo(final HttpServletRequest request) {
        final OpenApiUserInfo openApiUserInfo = this.authenticationUtils.getUserInfo(request);
        final Long userId = openApiUserInfo.getId();
        final Integer brokerId = this.getBrokerId();
        this.userBalanceService.checkUserAccountByContract(userId, brokerId);
        final List<Currency> currencyList = this.currencyService.listCurrencies();

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
        return resultList;
    }

    /**
     * 用户账单
     *
     * @param currencyCode
     * @param startDate
     * @param endDate
     * @param page
     * @param limit
     * @param request
     */
    @GetMapping("/{currencyCode}/ledger")
    @OpenApiRateLimit(value = "10/1")
    @OpenApiAuthValidator(method = HttpMethod.GET)
    public BillResultDTO bills(@PathVariable("currencyCode") final String currencyCode,
                               @RequestParam(value = "startDate", required = false) final Long startDate,
                               @RequestParam(value = "endDate", required = false) final Long endDate,
                               @RequestParam(value = "page", required = false, defaultValue = "1") final Integer page,
                               @RequestParam(value = "limit", required = false, defaultValue = "100") final Integer limit,
                               final HttpServletRequest request) {
        final Long userId = this.authenticationUtils.getUserInfo(request).getId();
        final Integer brokerId = this.getBrokerId();
        final ParamPageDTO paramPageDTO = ParamPageDTO.toParamPage(page, limit);

        //获取账单类型
        final List<Integer> allTypes = BillTypeEnum.getList();

        final List<UserBill> userBillList = this.userBillService.getBillList(paramPageDTO, userId,
                currencyCode, allTypes, startDate, endDate, brokerId);

        final List<UserBillDTO> billInfoList = UserBillConverter.convertToBillInfo(userBillList, 8);

        final BillResultDTO billResultDTO = BillResultDTO.builder()
                .bills(billInfoList)
                .paginate(paramPageDTO)
                .build();
        return billResultDTO;
    }

}
