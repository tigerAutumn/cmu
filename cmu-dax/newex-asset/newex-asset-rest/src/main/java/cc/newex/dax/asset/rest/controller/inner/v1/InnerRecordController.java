package cc.newex.dax.asset.rest.controller.inner.v1;

import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.dao.TransferRecordRepository;
import cc.newex.dax.asset.domain.PageBossEntity;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.dto.AssetRecordStatisticsDto;
import cc.newex.dax.asset.dto.TransferRecordReqDto;
import cc.newex.dax.asset.dto.TransferRecordResDto;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.wallet.exception.UnsupportedCurrency;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 查询用户充提币流水
 * @author: newex-team
 * @date: 2018/6/13 下午3:03
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/asset")
public class InnerRecordController {
    @Autowired
    TransferRecordService recordService;
    @Autowired
    TransferRecordRepository recordRepository;

    /**
     * @description: 查询流水
     * @param: [transferType, userId, currency, startTime, endTime, pageNum, pageSize]
     * @return: cc.newex.commons.support.model.ResponseResult
     * @author: newex-team
     * @date: 2018/6/13 下午5:08
     */
    @PostMapping("/record")
    public ResponseResult getTransferRecord(@RequestBody @Valid final TransferRecordReqDto transferRecordReqDto) {
        ResponseResult<PageBossEntity> result;
        try {

            TransferRecordExample example = TransferRecordExample.from(transferRecordReqDto);

            PageBossEntity pageBossEntity = PageBossEntity.getPage(this.recordService,
                    transferRecordReqDto.getPageNum(), transferRecordReqDto.getPageSize(), example);
            List<TransferRecord> transferRecords = pageBossEntity.getRows();
            List<TransferRecordResDto> transferRecordResDtos = transferRecords.parallelStream().map((record) -> {
                TransferRecordResDto deposit = TransferRecordResDto.builder()
                        .userId(record.getUserId())
                        .txId(record.getFrom())
                        .address(record.getTo())
                        .amount(record.getAmount().toPlainString())
                        .fee(record.getFee().toPlainString())
                        .confirmation(record.getConfirmation())
                        .currency(record.getCurrency())
                        .biz(record.getBiz())
                        .traderNo(record.getTraderNo())
                        .transferType(record.getTransferType())
                        .status(record.getStatus())
                        .createDate(record.getCreateDate())
                        .updateDate(record.getUpdateDate())
                        .confirmation(record.getConfirmation())
                        .brokerId(record.getBrokerId())
                        .build();
//                CurrencyEnum coin = CurrencyEnum.parseValue(record.getCurrency());
//                if (deposit.getConfirmation() >= coin.getConfirmNum()) {
//                    deposit.setConfirmation(Integer.MAX_VALUE);
//                }
                return deposit;
            }).collect(Collectors.toList());
            pageBossEntity.setRows(transferRecordResDtos);

            result = ResultUtils.success(pageBossEntity);

        } catch (UnsupportedCurrency e) {
            InnerRecordController.log.error("getTransferRecord error", e);
            return ResultUtils.failure("币种不支持" + transferRecordReqDto.getCurrency());
        } catch (Throwable e) {
            InnerRecordController.log.error("getTransferRecord error", e);
            return ResultUtils.failure(e.getMessage());
        }

        return result;
    }

    @GetMapping("/record/statistics")
    public ResponseResult<?> getTransferRecordStatistic() {
        ResponseResult<AssetRecordStatisticsDto> result;
        try {
            log.info("getTransferRecordStatistic begin");
            String dataStr = REDIS.get(RedisKeyCons.ASSET_DATA_KEY);
            JSONObject dataJson = JSONObject.parseObject(dataStr);

            result = ResultUtils.success(dataJson.toJavaObject(AssetRecordStatisticsDto.class));
            log.info("getTransferRecordStatistic end");

        } catch (UnsupportedCurrency e) {
            InnerRecordController.log.error("getTransferRecordStatistic error", e);
            return ResultUtils.failure("getTransferRecordStatistic error");
        } catch (Throwable e) {
            InnerRecordController.log.error("getTransferRecordStatistic error", e);
            return ResultUtils.failure(e.getMessage());
        }

        return result;
    }

}
