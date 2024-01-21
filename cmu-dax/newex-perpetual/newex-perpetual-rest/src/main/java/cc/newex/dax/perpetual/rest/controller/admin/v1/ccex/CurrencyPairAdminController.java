package cc.newex.dax.perpetual.rest.controller.admin.v1.ccex;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.criteria.CurrencyPairExample;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.dto.CurrencyPairDTO;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.wallet.currency.CurrencyEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/10/30
 */
@Slf4j
@RestController
@RequestMapping("/admin/v1/perpetual/currencypair")
public class CurrencyPairAdminController {

    @Resource
    private CurrencyPairService currencyPairService;

    @Resource
    private CacheService cacheService;

    @PostMapping(value = "/add")
    public ResponseResult<Integer> addCurrencyPair(@RequestBody final CurrencyPairDTO currencyPairDto) {
        final CurrencyPair currencyPair = new CurrencyPair();
        new ModelMapper().map(currencyPairDto, currencyPair);
        this.checkParams(currencyPair);
        final int count = this.currencyPairService.addCurrencyPair(currencyPair);
        return ResultUtils.success(count);
    }

    private void checkParams(final CurrencyPair currencyPair) {
        currencyPair.setBase(currencyPair.getBase().toLowerCase());
        currencyPair.setQuote(currencyPair.getQuote().toLowerCase());
        if (!"perpetual".equals(currencyPair.getBiz()) && !"future".equals(currencyPair.getBiz())) {
            throw new BizException("biz illegal");
        }
        final int env = CurrencyEnum.parseName(currencyPair.getBase()).isContractFakeCurrency() ? 1 : 0;
        if (currencyPair.getEnv() != env) {
            throw new BizException("env illegal");
        }
        if (currencyPair.getInsuranceAccount() == null || currencyPair.getInsuranceAccount() <= 0L) {
            throw new BizException("insuranceAccount illegal");
        }
        if (!this.validateType(currencyPair.getBiz(), currencyPair.getType())) {
            throw new BizException("triggerBy illegal");
        }
        if (currencyPair.getPremiumDepth() == null
                || currencyPair.getPremiumDepth().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException("premiumDepth illegal");
        }
        if (currencyPair.getMaintainRate() == null
                || currencyPair.getMaintainRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new BizException("maintainRate illegal");
        }
        if (currencyPair.getDirection() == null ||
                (DirectionEnum.POSITIVE.getDirection() != currencyPair.getDirection() && DirectionEnum.REVERSE.getDirection() != currencyPair.getDirection())) {
            throw new BizException("direction illegal");
        }
        String pairCode = currencyPair.getBase() + currencyPair.getQuote();
        if (currencyPair.getBiz().equals("perpetual")) {
            pairCode = "p" + pairCode;
        } else {
            pairCode = "f" + pairCode;
        }
        if (currencyPair.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
            pairCode = pairCode + "(" + currencyPair.getQuote() + ")";
        } else {
            pairCode = pairCode + "(" + currencyPair.getBase() + ")";
        }
        currencyPair.setPairCode(pairCode.toLowerCase());
    }

    /**
     * 检查合约币对类型
     *
     * @param biz
     * @param type
     * @return
     */
    private boolean validateType(final String biz, final String type) {
        return ("perpetual".equals(biz) && "perpetual".equals(type))
                || ("future".equals(biz) && ("week".equals(type) || "nextweek".equals(type) || "month".equals(type) || "quarter".equals(type)));
    }

    @PostMapping(value = "/update")
    public ResponseResult<Integer> updateCurrencyPair(
            @RequestBody final CurrencyPairDTO currencyPairDto) {
        final CurrencyPair currencyPair = new CurrencyPair();
        new ModelMapper().map(currencyPairDto, currencyPair);
        this.checkParams(currencyPair);
        final int count = this.currencyPairService.updateCurrencyPair(currencyPair);
        return ResultUtils.success(count);
    }

    @PostMapping(value = "/remove")
    public ResponseResult<Integer> removeCurrencyPair(@RequestBody final Integer id) {
        final int count = this.currencyPairService.removeById(id);
        return ResultUtils.success(count);
    }

    @PostMapping(value = "/query")
    public ResponseResult<DataGridPagerResult<CurrencyPairDTO>> queryCurrencyPair(
            @RequestBody final DataGridPager<?> pager,
            @RequestParam(value = "biz", required = false) final String biz,
            @RequestParam(value = "indexBase", required = false) final String indexBase,
            @RequestParam(value = "quote", required = false) final String quote,
            @RequestParam(value = "pairCode", required = false) final String pairCode,
            @RequestParam(value = "online", required = false) final Integer online) {
        final PageInfo pageInfo = this.getPageInfo(pager);
        final CurrencyPairExample currencyPairExample = new CurrencyPairExample();
        final CurrencyPairExample.Criteria criteria = currencyPairExample.createCriteria();
        if (!StringUtils.isEmpty(biz)) {
            criteria.andBizEqualTo(biz);
        }
        if (!StringUtils.isEmpty(indexBase)) {
            criteria.andIndexBaseEqualTo(indexBase);
        }
        if (!StringUtils.isEmpty(quote)) {
            criteria.andQuoteEqualTo(quote);
        }
        if (!StringUtils.isEmpty(pairCode)) {
            criteria.andPairCodeEqualTo(pairCode);
        }
        if (online != null) {
            criteria.andOnlineEqualTo(online);
        }
        final List<CurrencyPair> currencyPairList =
                this.currencyPairService.getByPage(pageInfo, currencyPairExample);
        final List<CurrencyPairDTO> currencyPairDTOList = new ArrayList<>();
        final ModelMapper modelMapper = new ModelMapper();
        currencyPairList
                .forEach(vo -> currencyPairDTOList.add(modelMapper.map(vo, CurrencyPairDTO.class)));
        return ResultUtils
                .success(new DataGridPagerResult<>(pageInfo.getTotals(), currencyPairDTOList));
    }

    /**
     * 获取所有的合约币对
     *
     * @return
     */
    @GetMapping(value = "/pairCodes")
    public ResponseResult<List<String>> listCurrencyPairCode() {
        final List<String> pairCode = this.currencyPairService.listCurrencyPairCode();
        return ResultUtils.success(pairCode);
    }

    /**
     * 获取所有的交易币对
     *
     * @return
     */
    @GetMapping(value = "/baseCodes")
    public ResponseResult<List<String>> listCurrencyBaseCode() {
        final List<String> baseCode = this.currencyPairService.listCurrencyPairBaseCode();
        return ResultUtils.success(baseCode);
    }

    private PageInfo getPageInfo(final DataGridPager<?> pager) {
        if (pager == null || pager.getRows() == null || pager.getPage() == null) {
            throw new BizException("dataGridPager illegal");
        }

        final Integer page = 1;
        final Integer pageSize = 100;

        if (pager.getPage() <= 0) {
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
}
