package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AssetTransferServiceClient;
import cc.newex.dax.perpetual.common.enums.BillTypeEnum;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.common.enums.TransferTargetEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.criteria.AssetsTransferExample;
import cc.newex.dax.perpetual.data.AssetsTransferRepository;
import cc.newex.dax.perpetual.domain.AssetsTransfer;
import cc.newex.dax.perpetual.dto.enums.TransferStatusEnum;
import cc.newex.dax.perpetual.service.AssetsTransferService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资金划转交易 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:33:04
 */
@Slf4j
@Service
public class AssetsTransferServiceImpl extends
        AbstractCrudService<AssetsTransferRepository, AssetsTransfer, AssetsTransferExample, Long>
        implements AssetsTransferService {
    @Autowired
    private AssetsTransferRepository assetsTransferRepository;

    @Autowired
    private UserBalanceService userCurrencyBalanceService;
    @Autowired
    private AssetTransferServiceClient assetTransferServiceClient;

    @Override
    protected AssetsTransferExample getPageExample(final String fieldName, final String keyword) {
        final AssetsTransferExample example = new AssetsTransferExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transferIn(final String tradeNo, final long userId, final Integer brokerId, final String currencyCode,
                           final BigDecimal amount, final TransferType transferType) {
        Preconditions.checkState(StringUtils.isNotBlank(tradeNo), "tradeNo is blank");
        // 判断是否重复划转
        final AssetsTransfer assetsTransfer = this.getAssetsTransferByTradeNo(tradeNo);
        if (assetsTransfer != null) {
            return;
        }

        // 增加资金划转记录
        this.assetsTransferRepository.insert(AssetsTransfer.builder().currencyCode(currencyCode).brokerId(brokerId).userId(userId).amount(amount)
                .target(TransferTargetEnum.ASSET.getTargetId()).tradeNo(tradeNo)
                .type(transferType.getCode()).status(TransferStatusEnum.SUCCESS.getCode()).build());

        // 增加用户资产
        this.userCurrencyBalanceService.transferInUserBalance(userId, currencyCode, brokerId, amount,
                this.fromTransferType(transferType));
    }

    private AssetsTransfer getAssetsTransferByTradeNo(final String tradeNo) {
        final AssetsTransferExample example = new AssetsTransferExample();
        example.createCriteria().andTradeNoEqualTo(tradeNo);
        return this.assetsTransferRepository.selectOneByExample(example);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AssetsTransfer transferOut(final String tradeNo, final long userId, final Integer brokerId,
                                      final String currencyCode, final BigDecimal amount, final TransferType transferType) {
        Preconditions.checkState(StringUtils.isNotBlank(tradeNo), "tradeNo is blank");

        // 判断是否重复划转
        AssetsTransfer assetsTransfer = this.getAssetsTransferByTradeNo(tradeNo);
        if (assetsTransfer != null) {
            return assetsTransfer;
        }

        // 插入转账记录
        assetsTransfer = AssetsTransfer.builder().currencyCode(currencyCode).brokerId(brokerId).userId(userId).amount(amount)
                .target(TransferTargetEnum.ASSET.getTargetId()).tradeNo(tradeNo)
                .type(transferType.getCode()).status(TransferStatusEnum.NOT_YET.getCode()).build();
        this.assetsTransferRepository.insert(assetsTransfer);

        // 修改余额
        this.userCurrencyBalanceService.transferOutUserBalance(userId, currencyCode, brokerId, amount,
                this.fromTransferType(transferType));

        return assetsTransfer;
    }

    @Override
    public List<AssetsTransfer> transferList(final Integer brokerId, final Long[] userIds, final String[] currencyCodes, final String[] tradeNos, final Date startTime, final Date endTime, final PageInfo pageInfo) {
        final AssetsTransferExample example = new AssetsTransferExample();
        final AssetsTransferExample.Criteria criteria = example.createCriteria();

        if (ArrayUtils.isNotEmpty(userIds)) {
            criteria.andUserIdIn(Arrays.asList(userIds));
        }

        if (ArrayUtils.isNotEmpty(currencyCodes)) {
            criteria.andCurrencyCodeIn(Arrays.asList(currencyCodes));
        }

        if (ArrayUtils.isNotEmpty(tradeNos)) {
            criteria.andTradeNoIn(Arrays.asList(tradeNos));
        }

        if (startTime != null) {
            criteria.andModifyDateGreaterThanOrEqualTo(startTime);
        }

        if (endTime != null) {
            criteria.andModifyDateLessThan(endTime);
        }

        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }

        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<AssetsTransfer> listNotYetAssetsTransferByType(final TransferType... transferType) {
        final List<Integer> transferList =
                Arrays.asList(transferType).stream().map(l -> l.getCode()).collect(Collectors.toList());
        // 查询待处理的转出任务，并且重试次数小于10
        final AssetsTransferExample example = new AssetsTransferExample();
        example.createCriteria().andStatusEqualTo(TransferStatusEnum.NOT_YET.getCode())
                .andTypeIn(transferList);
        return this.assetsTransferRepository.selectByExample(example);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void noticeAssetsTransfer(final Long assetsTransferId) {
        final AssetsTransfer assetsTransfer = this.assetsTransferRepository.selectByIdForUpdate(assetsTransferId);
        Preconditions.checkNotNull(assetsTransfer, "assetsTransfer not found:" + assetsTransferId);

        if (assetsTransfer.getStatus() == TransferStatusEnum.SUCCESS.getCode()) {
            return;
        }

        // 只有 转出、提现需要重试回调
        final List<Integer> availableTypes = Arrays.asList(TransferType.TRANSFER_OUT.getCode(),
                TransferType.WITHDRAW.getCode(), TransferType.LOCKED_POSITION.getCode());

        if (!availableTypes.contains(assetsTransfer.getType())) {
            return;
        }

        final ResponseResult responseResult = this.assetTransferServiceClient.transferIn(assetsTransfer.getUserId(),
                -1, assetsTransfer.getAmount(), assetsTransfer.getTradeNo());

        if (responseResult.getCode() == ResultUtils.success().getCode()) {
            assetsTransfer.setStatus(TransferStatusEnum.SUCCESS.getCode());
            this.assetsTransferRepository.updateById(assetsTransfer);
        }
        AssetsTransferServiceImpl.log.info("noticeAssets finished, code : {} : tradeNo : {}", responseResult.getCode(), assetsTransfer.getTradeNo());
    }

    private BillTypeEnum fromTransferType(final TransferType transferType) {
        switch (transferType) {
            case DEPOSIT:
                return BillTypeEnum.RECHARGE;
            case WITHDRAW:
                return BillTypeEnum.WITHDRAW;
            case TRANSFER_IN:
                return BillTypeEnum.TRANSFER_IN;
            case TRANSFER_OUT:
                return BillTypeEnum.TRANSFER_OUT;
            default:
                throw new BizException(BizErrorCodeEnum.ILLEGAL_PARAM);
        }

    }
}
