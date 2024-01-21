package cc.newex.dax.perpetual.rest.controller.inner.v1.ccex;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.domain.AssetsTransfer;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.dto.AssetsTransferDTO;
import cc.newex.dax.perpetual.dto.UserAssetsBalanceDTO;
import cc.newex.dax.perpetual.service.AssetsTransferService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.util.ObjectCopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资金划转
 *
 * @author newex-team
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/v1/perpetual/asset")
public class TransferInnerController {
    @Resource
    private AssetsTransferService transferService;

    @Resource
    private UserBalanceService userBalanceService;

    @PostMapping(value = "/transfer-in")
    public ResponseResult<?> transferIn(@RequestParam final long userId,
                                        @RequestParam final String currencyCode,
                                        @RequestParam final Integer brokerId,
                                        @RequestParam final BigDecimal amount,
                                        @RequestParam final String tradeNo,
                                        @RequestParam(required = false) final Integer transferType) {
        final TransferType type = this.takeTransferType(transferType, TransferType.DEPOSIT);
        this.transferService.transferIn(tradeNo, userId, brokerId, currencyCode, amount, type);
        return ResultUtils.success();
    }

    @PostMapping(value = "/transfer-out")
    public ResponseResult<?> transferOut(@RequestParam final long userId,
                                         @RequestParam final String currencyCode,
                                         @RequestParam final Integer brokerId,
                                         @RequestParam final BigDecimal amount,
                                         @RequestParam final String tradeNo,
                                         @RequestParam(required = false) final Integer transferType) {
        final TransferType type = this.takeTransferType(transferType, TransferType.WITHDRAW);
        final AssetsTransfer assetsTransfer = this.transferService.transferOut(tradeNo, userId, brokerId, currencyCode, amount, type);
        this.transferService.noticeAssetsTransfer(assetsTransfer.getId());
        return ResultUtils.success();
    }

    @GetMapping(value = "/transfer-list")
    public ResponseResult<DataGridPagerResult<AssetsTransferDTO>> transferList(
            @RequestParam(value = "brokerId", required = false) final Integer brokerId,
            @RequestParam(value = "userIds", required = false) final Long[] userIds,
            @RequestParam(value = "currencyCodes", required = false) final String[] currencyCodes,
            @RequestParam(value = "tradeNos", required = false) final String[] tradeNos,
            @RequestParam(value = "startTime", required = false) final Long startTimeMills,
            @RequestParam(value = "endTime", required = false) final Long endTimeMills,
            @RequestParam(value = "page", required = false, defaultValue = "1") final Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "100") final Integer pageSize) {

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
        pageInfo.setStartIndex((page - 1) * pageSize);
        pageInfo.setPageSize(pageSize);

        Date startTime = null;
        Date endTime = null;

        if (startTimeMills != null && startTimeMills > 0L) {
            startTime = new Date(startTimeMills);
        }

        if (endTimeMills != null && endTimeMills > 0L) {
            endTime = new Date(endTimeMills);
        }

        final List<AssetsTransfer> list = this.transferService.transferList(brokerId, userIds, currencyCodes, tradeNos, startTime, endTime, pageInfo);

        final ModelMapper mapper = new ModelMapper();
        final List<AssetsTransferDTO> rows = list.stream().map(x -> mapper.map(x, AssetsTransferDTO.class)).collect(Collectors.toList());

        return ResultUtils.success(new DataGridPagerResult(pageInfo.getTotals(), rows));
    }

    private TransferType takeTransferType(final Integer code, final TransferType defaultType) {
        if (code == null) {
            return defaultType;
        }

        final TransferType transferType = TransferType.convert(code);
        if (transferType == null) {
            throw new BizException(BizErrorCodeEnum.ILLEGAL_PARAM);
        }

        if (transferType == TransferType.TRANSFER) {
            throw new BizException(BizErrorCodeEnum.ILLEGAL_PARAM);
        }

        return transferType;
    }

    /**
     * 返回给asset用户合约资产列表
     *
     * @param userId
     * @param brokerId
     */
    @GetMapping(value = "/balance")
    public ResponseResult<List<UserAssetsBalanceDTO>> queryUserBalance(
            @RequestParam(value = "userId") final Long userId,
            @RequestParam(value = "brokerId") final Integer brokerId) {
        final List<UserBalance> userBalanceList = this.userBalanceService.get(userId, brokerId);
        final List<UserAssetsBalanceDTO> resultList = ObjectCopyUtil.mapList(userBalanceList, UserAssetsBalanceDTO.class);
        return ResultUtils.success(resultList);
    }
}
