package cc.newex.dax.boss.web.controller.outer.v1.perpetual;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.web.common.enums.CurrencyBizEnum;
import cc.newex.dax.boss.web.model.perpetual.PerpetualCurrencyPairVO;
import cc.newex.dax.perpetual.client.CurrencyPairAdminClient;
import cc.newex.dax.perpetual.dto.CurrencyPairDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

/**
 * 合约币对的控制器
 *
 * @author better
 * @date create in 2018/11/20 11:28 AM
 */
@RestController
@RequestMapping(value = "/v1/boss/perpetual/currency-pair")
@Slf4j
public class PerpetualCurrencyPairController {

    private final ModelMapper modelMapper = new ModelMapper();
    private final String ADD_FLAG = "save";
    private final String EDIT_FLAG = "edit";
    
    @Autowired
    private CurrencyPairAdminClient currencyPairAdminClient;
    @Autowired
    private AdminServiceClient adminServiceClient;

    /**
     * List perpetual currency pair response result.
     *
     * @param dataGridPager the data grid pager
     * @param biz           the biz
     * @param base          the base
     * @param quote         the quote
     * @param online        the online
     * @param pairCode      the pair code
     * @return the response result
     */
    @GetMapping(value = "/list")
    @OpLog(name = "查看合约币对")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_CURRENCY_PAIR_VIEW"})
    public ResponseResult<DataGridPagerResult<CurrencyPairDTO>> listPerpetualCurrencyPair(final DataGridPager<CurrencyPairDTO> dataGridPager, final String biz, final String base, final String quote,
                                                                                          final Integer online, final String pairCode) {

        return this.currencyPairAdminClient.queryCurrencyPair(dataGridPager, biz, base, quote, pairCode, online);
    }

    /**
     * Add perpetual currency pair response result.
     *
     * @param currencyPair the currency pair
     * @return the response result
     */
    @PostMapping(value = "/add")
    @OpLog(name = "添加合约币对")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_CURRENCY_PAIR_ADD"})
    public ResponseResult addPerpetualCurrencyPair(@CurrentUser final User loginUser, final PerpetualCurrencyPairVO currencyPair) {
        return this.saveOrUpdate(this.ADD_FLAG, loginUser.getLoginBrokerId(), currencyPair);
    }

    /**
     * Edit perpetual currency pair response result.
     *
     * @param currencyPair the currency pair
     * @return the response result
     */
    @PostMapping(value = "/edit")
    @OpLog(name = "修改合约币对")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_CURRENCY_PAIR_EDIT"})
    public ResponseResult editPerpetualCurrencyPair(@CurrentUser final User loginUser, final PerpetualCurrencyPairVO currencyPair) {
        return this.saveOrUpdate(this.EDIT_FLAG, loginUser.getLoginBrokerId(), currencyPair);
    }

    private ResponseResult saveOrUpdate(final String flag, final Integer brokerId, final PerpetualCurrencyPairVO currencyPair) {
        final CurrencyPairDTO clientParam = this.modelMapper.map(currencyPair, CurrencyPairDTO.class);
        if (!this.checkParam(clientParam.getIndexBase(), clientParam.getQuote(), brokerId)) {
            return ResultUtils.failure("indexBase or quote is not exists");
        }
        this.fieldToLowerCase(clientParam);
        if (StringUtils.equalsIgnoreCase(this.ADD_FLAG, flag)) {
            return this.currencyPairAdminClient.addCurrencyPair(clientParam);
        } else if (StringUtils.equalsIgnoreCase(this.EDIT_FLAG, flag)) {
            return this.currencyPairAdminClient.updateCurrencyPair(clientParam);
        }
        log.error("flag is error, flag => {}", flag);
        return ResultUtils.failure("save or update is error");
    }

    private Boolean checkParam(final String indexBase, final String quote, final Integer brokerId) {

        final ResponseResult<?> source = this.adminServiceClient.getAllCurrencies(CurrencyBizEnum.PERPETUAL.getName(), brokerId);
        if (source.getCode() == 0) {
            final JSONArray result = JSONObject.parseArray(source.getData().toString());
            final boolean indexBaseBool = result.stream().anyMatch(item -> {
                final JSONObject jsonObject = (JSONObject) item;
                return equalsIgnoreCase(jsonObject.getString("symbol"), indexBase);
            });
            final boolean quoteBool = result.stream().anyMatch(item -> {
                final JSONObject jsonObject = (JSONObject) item;
                return equalsIgnoreCase(jsonObject.getString("symbol"), quote);
            });
            return indexBaseBool && quoteBool;
        }
        log.error("get perpetual allCurrencies is error, res => {}", source);
        return false;
    }

    private void fieldToLowerCase(final CurrencyPairDTO clientParam) {
        clientParam.setBase(clientParam.getBase().toLowerCase());
        clientParam.setQuote(clientParam.getQuote().toLowerCase());
        clientParam.setPairCode(clientParam.getPairCode().toLowerCase());
        clientParam.setIndexBase(clientParam.getIndexBase().toLowerCase());
    }
}
