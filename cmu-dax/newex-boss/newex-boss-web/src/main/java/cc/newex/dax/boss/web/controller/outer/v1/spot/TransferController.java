package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.common.util.UUIDUtils;
import cc.newex.dax.boss.web.model.spot.SpotBatchTransferVO;
import cc.newex.dax.boss.web.model.spot.SpotTransferVO;
import cc.newex.dax.spot.client.SpotAdminTransferClient;
import cc.newex.dax.spot.dto.ccex.ActRecordDTO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 币币 - 资金划转
 *
 * @author liutiejun
 * @date 2018-08-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/boss/spot/transfer")
public class TransferController {

    private static final String ACTIVITY_KEY = "inner_transfer";

    @Autowired
    private SpotAdminTransferClient spotAdminTransferClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "币币 - 资金划转")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_TRANSFER_ADD"})
    public ResponseResult add(@Valid final SpotTransferVO spotTransferVO,
                              @CurrentUser final User loginUser) {

        if (loginUser.getBrokerId().intValue() > 0 && !loginUser.getBrokerId().equals(spotTransferVO.getBrokerId())) {
            return ResultUtil.getCheckedResponseResult(null);
        }

        final List<ActRecordDTO> actRecordDTOList = this.toActRecord(spotTransferVO);

        final ResponseResult result = this.spotAdminTransferClient.transfer(actRecordDTOList);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/batch-add")
    @OpLog(name = "币币 - 批量划转")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_TRANSFER_BATCH_ADD"})
    public ResponseResult batchAdd(@Valid final SpotBatchTransferVO spotBatchTransferVO,
                                   @CurrentUser final User loginUser) {

        if (loginUser.getBrokerId().intValue() > 0 && !loginUser.getBrokerId().equals(spotBatchTransferVO.getBatchBrokerId())) {
            log.info("batch transfer error, login brokerId: {}, request brokerId: {}", loginUser.getBrokerId(), spotBatchTransferVO.getBatchBrokerId());

            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

        final List<SpotTransferVO> spotTransferVOList = this.toSpotTransfer(spotBatchTransferVO);
        if (CollectionUtils.isEmpty(spotTransferVOList)) {
            log.info("batch transfer error, no transfer data");

            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

        // 划转失败数据
        final List<String> failureList = new ArrayList<>();

        final String failureMsg = "from: %s, to: %s, currency: %s, amount: %s, msg: %s";

        for (final SpotTransferVO spotTransferVO : spotTransferVOList) {
            final List<ActRecordDTO> actRecordDTOList = this.toActRecord(spotTransferVO);

            final ResponseResult<Boolean> result = this.spotAdminTransferClient.transfer(actRecordDTOList);

            if (result == null || result.getCode() != 0 || !result.getData()) {
                log.info("batch transfer error: {}, result: {}", JSON.toJSONString(spotTransferVO), JSON.toJSONString(result));

                final String msg = String.format(failureMsg, spotTransferVO.getFromUserId(), spotTransferVO.getToUserId(),
                        spotTransferVO.getCurrencyId(), spotTransferVO.getAmount(), result.getMsg());

                failureList.add(msg);
            }
        }

        if (CollectionUtils.isEmpty(failureList)) {
            return ResultUtils.success();
        }

        log.info("batch transfer error, size: {}, msg: {}", failureList.size(), JSON.toJSONString(failureList));

        final String allFailureMsg = JSON.toJSONString(failureList);

        return ResultUtils.failure(allFailureMsg);
    }

    private List<SpotTransferVO> toSpotTransfer(final SpotBatchTransferVO spotBatchTransferVO) {
        final Integer brokerId = spotBatchTransferVO.getBatchBrokerId();
        final String remark = spotBatchTransferVO.getBatchRemark();

        final String batchTransferData = spotBatchTransferVO.getBatchTransferData();
        final String[] transferDataArray = batchTransferData.split("\\s*\r\n\\s*");

        if (ArrayUtils.isEmpty(transferDataArray)) {
            log.error("batch transfer failure, no new line character");

            return null;
        }

        final List<SpotTransferVO> spotTransferVOList = Arrays.stream(transferDataArray)
                .map(transferData -> this.toSpotTransfer(transferData, brokerId, remark))
                .collect(Collectors.toList());

        return spotTransferVOList;
    }

    private SpotTransferVO toSpotTransfer(final String transferData, final Integer brokerId, final String remark) {
        // 字段之间的顺序：fromUserId, toUserId, currencyId, amount；
        final String[] dataArray = transferData.split("\\s*[,|，]\\s*");

        if (ArrayUtils.isEmpty(dataArray)) {
            log.error("batch transfer failure, no comma, msg: {}", transferData);

            return null;
        }

        final SpotTransferVO spotTransferVO = SpotTransferVO.builder()
                .fromUserId(Long.valueOf(dataArray[0]))
                .toUserId(Long.valueOf(dataArray[1]))
                .currencyId(Integer.valueOf(dataArray[2]))
                .amount(new BigDecimal(dataArray[3]))
                .brokerId(brokerId)
                .remark(remark)
                .build();

        return spotTransferVO;
    }

    private List<ActRecordDTO> toActRecord(final SpotTransferVO spotTransferVO) {
        final String segment = UUIDUtils.getUUID32();

        final ActRecordDTO actRecordDTO_1 = ActRecordDTO.builder()
                .userId(spotTransferVO.getFromUserId())
                // 转出
                .type(3)
                .currencyId(spotTransferVO.getCurrencyId())
                .amount(spotTransferVO.getAmount())
                .remark(spotTransferVO.getRemark())
                .activityKey(ACTIVITY_KEY)
                .segment(segment)
                .raffleId(0L)
                .status(1)
                .brokerId(spotTransferVO.getBrokerId())
                .build();

        final ActRecordDTO actRecordDTO_2 = ActRecordDTO.builder()
                .userId(spotTransferVO.getToUserId())
                // 转入
                .type(4)
                .currencyId(spotTransferVO.getCurrencyId())
                .amount(spotTransferVO.getAmount())
                .remark(spotTransferVO.getRemark())
                .activityKey(ACTIVITY_KEY)
                .segment(segment)
                .raffleId(1L)
                .status(1)
                .brokerId(spotTransferVO.getBrokerId())
                .build();

        final List<ActRecordDTO> actRecordDTOList = new ArrayList<>();
        actRecordDTOList.add(actRecordDTO_1);
        actRecordDTOList.add(actRecordDTO_2);

        return actRecordDTOList;
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取资金划转记录列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_TRANSFER_VIEW"})
    public ResponseResult list(@RequestParam(value = "beginTime", required = false) final String beginTimeStr,
                               @RequestParam(value = "endTime", required = false) final String endTimeStr,
                               @RequestParam(value = "userId", required = false) final Long userId,
                               @CurrentUser final User loginUser,
                               final DataGridPager pager) {

        final Date beginTime = DateFormater.parse(beginTimeStr);
        final Date endTime = DateFormater.parse(endTimeStr);

        Long beginTimeMills = null;
        Long endTimeMills = null;

        if (beginTime != null) {
            beginTimeMills = beginTime.getTime();
        }

        if (endTime != null) {
            endTimeMills = endTime.getTime();
        }

        final ResponseResult responseResult = this.spotAdminTransferClient.list(pager, ACTIVITY_KEY, beginTimeMills, endTimeMills, userId, loginUser.getLoginBrokerId());

        return ResultUtil.getDataGridResult(responseResult);
    }

}
