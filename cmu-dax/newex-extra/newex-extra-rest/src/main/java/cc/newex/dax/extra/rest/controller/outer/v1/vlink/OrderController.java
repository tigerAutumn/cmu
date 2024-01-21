package cc.newex.dax.extra.rest.controller.outer.v1.vlink;

import cc.newex.commons.dictionary.consts.BrokerIdConsts;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import cc.newex.dax.extra.domain.vlink.ContractInfo;
import cc.newex.dax.extra.dto.vlink.OrderReqDTO;
import cc.newex.dax.extra.dto.vlink.PageInfo;
import cc.newex.dax.extra.enums.vlink.ContractStatusEnum;
import cc.newex.dax.extra.service.vlink.ContractInfoService;
import cc.newex.dax.extra.vlink.VLinkServiceProvider;
import cc.newex.dax.spot.client.inner.SpotCurrencyClient;
import cc.newex.dax.spot.client.inner.SpotTransferServiceClient;
import cc.newex.dax.spot.dto.ccex.ActRecordDTO;
import cc.newex.dax.spot.dto.ccex.SpotRecordTypeEnum;
import cc.newex.dax.spot.dto.result.model.Currency;
import cc.newex.dax.users.client.UsersAdminClient;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author gi
 * @date 10/19/18
 * 用户订单购买
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/extra/vlink/order")
public class OrderController {
    @Autowired
    UsersAdminClient usersAdminClient;

    @Autowired
    VLinkServiceProvider provider;

    @Autowired
    SpotCurrencyClient spotCurrencyClient;

    @Autowired
    SpotTransferServiceClient spotTransferServiceClient;

    @Autowired
    ContractInfoService contractInfoService;

    /**
     * 购买记录查询
     *
     * @param request
     * @param pageInfo
     * @return
     */
    @RequestMapping(value = "/list/{contractId}", method = RequestMethod.GET)
    public ResponseResult getList(@PathVariable(value = "contractId") @NotNull final Integer contractId, final HttpServletRequest request,
                                  final PageInfo pageInfo) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final String email = this.contractInfoService.getEmail(userDetails.getUserId());
        if (StringUtils.isBlank(email)) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.NO_EMAIL);
        }
        final Map<String, Object> params = new HashMap<>(3);
        params.put("email", email);
        params.put("contractId", contractId);
        params.put("page", pageInfo.getPage());
        params.put("size", pageInfo.getSize());
        final JSONObject result = this.provider.getOrder(params);
        return ResultUtils.success(result);
    }

    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public ResponseResult preview(final HttpServletRequest request, @RequestBody final OrderReqDTO dto) {

        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }
        String email = contractInfoService.getEmail(userDetails.getUserId());
        if (StringUtils.isBlank(email)) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.NO_EMAIL);
        }
        if(Objects.isNull(dto.getQuantity())||dto.getQuantity()<1){
            return ResultUtils.failure(ExtraBizErrorCodeEnum.INCORRECT_PARAMETER);
        }
        if(Objects.isNull(dto.getContractId())){
            return ResultUtils.failure(ExtraBizErrorCodeEnum.INCORRECT_PARAMETER);
        }
        Map<String, Object> params = new HashMap<>(3);
        params.put("email", email);
        params.put("contractId", dto.getContractId());
        params.put("quantity", dto.getQuantity());
        final JSONObject result = provider.orderPreview(params);
        return ResultUtils.success(result);
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ResponseResult confirm(final HttpServletRequest request,
                                  @RequestBody final OrderReqDTO dto) {

        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }
        final String email = this.contractInfoService.getEmail(userDetails.getUserId());
        if (StringUtils.isBlank(email)) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.NO_EMAIL);
        }
        dto.setEmail(email);
        if(StringUtils.isBlank(dto.getCurrency())||Objects.isNull(dto.getContractId())||
                Objects.isNull(dto.getQuantity())||Objects.isNull(dto.getTotal())||
                dto.getQuantity()<1||dto.getTotal().compareTo(BigDecimal.ZERO)<=0){
            return ResultUtils.failure(ExtraBizErrorCodeEnum.INCORRECT_PARAMETER);
        }
        //1.查询vlink合约币种在coinmex对应币种信息
        final Currency currency = contractInfoService.getCurrency(dto.getCurrency());
        if (Objects.isNull(currency)) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.CONTRACT_CURRENCY_NULL);
        }

        //转账和添加购买记录的事务
        final String segment = UUID.randomUUID().toString();
        final List<ActRecordDTO> actRecordDTOList = new ArrayList<>();
        final ActRecordDTO transferIn = ActRecordDTO.builder()
                .activityKey(provider.getActivityKey())
                .currencyId(currency.getId())
                .currencyCode(currency.getSymbol())
                .amount(dto.getTotal())
                .brokerId(BrokerIdConsts.COIN_MEX)
                .segment(segment)
                .userId(provider.getVlinkUserId())
                .type(SpotRecordTypeEnum.TRANSFER_IN.getCode())
                .raffleId(0L)
                .build();
        actRecordDTOList.add(transferIn);
        final ActRecordDTO transferOut = ActRecordDTO.builder()
                .activityKey(this.provider.getActivityKey())
                .currencyId(currency.getId())
                .currencyCode(currency.getSymbol())
                .amount(dto.getTotal())
                .brokerId(BrokerIdConsts.COIN_MEX)
                .segment(segment)
                .userId(userDetails.getUserId())
                .type(SpotRecordTypeEnum.TRANSFER_OUT.getCode())
                .raffleId(1L)
                .build();
        actRecordDTOList.add(transferOut);

        final ContractInfo info = ContractInfo.builder()
                .userId(userDetails.getUserId())
                .transactionId(segment)
                .email(dto.getEmail())
                .contractId(dto.getContractId())
                .quantity(dto.getQuantity().longValue())
                .total(dto.getTotal().longValue())
                .currencyId(currency.getId())
                .build();
        //2.用户转账给vlink,币种一定选择currency.getId(),而不是currencyId（）
        if (!contractInfoService.transfer(actRecordDTOList, info)) {
            info.setStatus(ContractStatusEnum.TRANSFER_FAIL.getCode());
            this.contractInfoService.add(info);
            return ResultUtils.failure(ExtraBizErrorCodeEnum.TRANSACTION_FAIL);
        }
        //3.转账成功之后提交购买信息到vlink
        final Map<String, Object> params = new HashMap<>(3);
        params.put("email", email);
        params.put("contractId", dto.getContractId());
        params.put("quantity", dto.getQuantity());
        params.put("transactionId", segment);
        final JSONObject resultDTO = this.provider.orderSubmit(params);
        final String serialNumber = resultDTO.get("serialNumber").toString();
        final Date activateDate = (Date) resultDTO.get("activateDate");
        //4.根据vlink的返回信息，更新用户购买合约信息；
        //tip,之后试验是否需要重新查询，还是用上面对象
        ContractInfo contractInfo = contractInfoService.getById(info.getId());
        if(StringUtils.isNotEmpty(serialNumber)){
            contractInfo.setActivateDate(activateDate);
            contractInfo.setStatus(ContractStatusEnum.ORDER_SUCCESS.getCode());
            contractInfo.setSerialId(serialNumber);
        }else {
            //如果购买失败，把转账退回用户
            String segment2 = UUID.randomUUID().toString();
            transferIn.setUserId(userDetails.getUserId());
            transferIn.setSegment(segment2);
            transferOut.setUserId(provider.getVlinkUserId());
            transferOut.setSegment(segment2);
            List<ActRecordDTO> actRecordBackDTOList = new ArrayList<>();
            actRecordBackDTOList.add(transferIn);
            actRecordBackDTOList.add(transferOut);
            if(contractInfoService.transferBack(actRecordBackDTOList)){
                contractInfo.setStatus(ContractStatusEnum.ORDER_FAIL.getCode());
            }else {
                contractInfo.setStatus(ContractStatusEnum.TRANSFER_BACK_FAIL.getCode());
            }
        }
        contractInfoService.editById(contractInfo);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/earning/{contractId}", method = RequestMethod.GET)
    @OpLog(name = "收益情况")
    public ResponseResult getEarning(@PathVariable(value = "contractId") @NotNull final Integer contractId, final HttpServletRequest request,
                                     final PageInfo pageInfo) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);
        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }
        final String email = this.contractInfoService.getEmail(userDetails.getUserId());
        if (StringUtils.isBlank(email)) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.NO_EMAIL);
        }
        final Map<String, Object> params = new HashMap<>(3);
        params.put("email", email);
        params.put("contractId", contractId);
        params.put("page", pageInfo.getPage());
        params.put("size", pageInfo.getSize());
        final JSONObject result = this.provider.getEarning(params);
        return ResultUtils.success(result);
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public ResponseResult withdraw(final HttpServletRequest request) {
        log.info("withdraw");
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);
        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final String email = this.contractInfoService.getEmail(userDetails.getUserId());
        if (StringUtils.isBlank(email)) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.NO_EMAIL);
        }
        //调用vlink登录接口，获取auth
        final Map<String, Object> loginParams = new HashMap<>(1);
        loginParams.put("email", email);
        final String result = this.provider.getUserAuth(loginParams);
        final Map<String, Object> directParams = new HashMap<>(1);
        directParams.put("authorization", result);
        final String directUrl = this.provider.withDraw(directParams);
        return ResultUtils.success(directUrl);
    }
}
