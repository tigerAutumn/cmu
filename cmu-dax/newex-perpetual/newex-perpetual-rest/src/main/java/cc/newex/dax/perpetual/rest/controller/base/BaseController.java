package cc.newex.dax.perpetual.rest.controller.base;

import cc.newex.commons.broker.consts.BrokerConsts;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.CurrencyPairBrokerRelation;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.CurrencyPairBrokerRelationService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 公共Action控制器类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
public class BaseController {

    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private PerpetualConfig perpetualConfig;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private CurrencyPairBrokerRelationService currencyPairBrokerRelationService;

    public static PageInfo getPageInfo(final DataGridPager<?> pager) {
        if (pager == null || pager.getRows() == null || pager.getPage() == null) {
            throw new BizException("dataGridPager illegal");
        }

        final Integer page = 1;
        final Integer pageSize = 100;

        if (pager.getPage() <= 0 || pager.getPage() == null) {
            pager.setPage(page);
        }

        if (pager.getRows() <= 0 || pager.getRows() > pageSize) {
            pager.setRows(pageSize);
        }

        final PageInfo pageInfo = pager.toPageInfo();
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_DES);

        return pageInfo;
    }

    protected Contract checkContract(final String contractCode,
                                     final boolean checkLiquidationStatus) {
        return this.checkContract(contractCode, null, checkLiquidationStatus);
    }

    protected Contract checkContract(final String contractCode, final BigDecimal gear,
                                     final boolean checkLiquidationStatus) {

        if (StringUtils.isBlank(contractCode)) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }
        // 检测合约是否存在
        final Contract contract = this.contractService.getContract(contractCode);
        if (contract == null) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }

        if (gear != null) {
            // 检测币对是否存在,并检查gear是否合法
            final boolean legal = this.currencyPairService.checkGear(contract.getPairCode(), gear);
            if (!legal) {
                throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
            }
        }

        // 检测当前 broker 是否有对应的 币对
        final CurrencyPairBrokerRelation relation =
                this.currencyPairBrokerRelationService.getPairCodeByCache(contract.getPairCode(), this.getBrokerId());
        if (relation == null) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }

        final CurrencyPair pairCode = this.currencyPairService.getByPairCode(contract.getPairCode());

        // 检测当前币对在当前环境上是否有效
        final List<Integer> onlineValue = this.perpetualConfig.availableOnlineValue();
        if (CollectionUtils.isEmpty(onlineValue) || !onlineValue.contains(pairCode.getOnline())) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }

        // 判断当前合约是否 正在清算
        if (checkLiquidationStatus && this.contractService.isLockedContract(contractCode)) {
            throw new BizException(BizErrorCodeEnum.CONTRACT_ARE_BEING_SETTLED);
        }

        return contract;
    }

    protected JwtUserDetails getUserDetails() {
        return this.getUserDetails(false);
    }

    protected JwtUserDetails getUserDetails(final boolean checkPerpetualFlag) {
        final JwtUserDetails userJwt = JwtTokenUtils.getCurrentLoginUser(this.httpServletRequest);
        if (userJwt.getStatus() != 0) {
            throw new BizException(BizErrorCodeEnum.NOT_LOGIN);
        }
        if (checkPerpetualFlag && (userJwt.getPerpetualProtocolFlag() == null || userJwt.getPerpetualProtocolFlag() != 1)) {
            throw new BizException(BizErrorCodeEnum.NEEDED_CONFIRM_FUTURES_EXCHANGE);
        }
        return userJwt;
    }

    protected Integer getBrokerId() {
        return (Integer) this.httpServletRequest.getAttribute(BrokerConsts.BROKER_ID);
    }
}
