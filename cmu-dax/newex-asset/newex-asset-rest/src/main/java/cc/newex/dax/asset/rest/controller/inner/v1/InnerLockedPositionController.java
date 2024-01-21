package cc.newex.dax.asset.rest.controller.inner.v1;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.consts.UserInfoCons;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.enums.LockPositionStatus;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.criteria.LockedPositionExample;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.domain.LockedPosition;
import cc.newex.dax.asset.domain.PageBossEntity;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.dto.LockedPositionPageDto;
import cc.newex.dax.asset.dto.LockedPositionRecordReqDto;
import cc.newex.dax.asset.dto.LockedPositionRecordResDto;
import cc.newex.dax.asset.dto.UserLockedPositionDto;
import cc.newex.dax.asset.service.LockedPositionConfService;
import cc.newex.dax.asset.service.LockedPositionService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.exception.UnsupportedCurrency;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static cc.newex.dax.asset.common.consts.UserInfoCons.LOCK_OPERATOR_ACTIVITY;

/**
 * @author newex-team
 * @data 2018/5/4
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/asset")
public class InnerLockedPositionController {

    @Autowired
    private LockedPositionService lockedPositionService;

    @Autowired
    private LockedPositionConfService lockedPositionConfService;

    @Autowired
    private TransferRecordService recordService;

    @Autowired
    private UsersClient usersClient;


    /**
     * 通过tradeNo查询锁仓
     *
     * @param tradeNo
     * @return
     */
    @RequestMapping("queryLockedPositionByTradeNo")
    public ResponseResult<LockedPositionPageDto> queryLockedPositionByTradeNo(@RequestParam("tradeNo") String tradeNo) {
        TransferRecord record = recordService.getByTradeNo(tradeNo);
        if (ObjectUtils.isEmpty(record)) {
            return ResultUtils.failure(BizErrorCodeEnum.RECORD_NOT_FOUND);
        }
        if (record.getTransferType() != TransferType.LOCKED_POSITION.getCode()) {
            return ResultUtils.failure(BizErrorCodeEnum.RECORD_TYPE_ERROR);
        }
        LockedPosition lockedPosition = lockedPositionService.getById(Long.valueOf(record.getTo()));
        if (ObjectUtils.isEmpty(lockedPosition)) {
            return ResultUtils.failure(BizErrorCodeEnum.RECORD_NOT_FOUND);
        }
        LockedPositionPageDto dto = getLockedPositionPageDto(lockedPosition);
        return ResultUtils.success(dto);
    }

    /**
     * 单次锁定 单个时间解锁全部
     */
    @RequestMapping("/lockedPosition/lockUser/activity")
    public ResponseResult lockUserPositionActivity(@RequestParam(value = "tradeNo") String tradeNo,
                                                   @RequestParam(value = "lockPositionName") String lockPositionName,
                                                   @RequestParam(value = "userId") Long userId,
                                                   @RequestParam(value = "currency") Integer currency,
                                                   @RequestParam(value = "amount") BigDecimal amount,
                                                   @RequestParam(value = "dividend") Integer dividend,
                                                   @RequestParam(value = "unlockDate") Date unlockDate,
                                                   @RequestParam("brokerId") Integer brokerId) {
        log.info("activity lock position param: tradeNo={} lockPositionName={} userId={} currency={} amount={} unlockDate={} ", tradeNo, lockPositionName, userId, currency, amount, unlockDate);
        try {
            unlockDate = DateUtil.dateTimeDayTrim(unlockDate);
            LockedPosition lockedPosition = LockedPosition.builder()
                    .userId(userId)
                    .currency(currency)
                    .amount(amount)
                    .lockAmount(amount)
                    .dividend(dividend)
                    .remark(StringUtil.EMPTY)
                    .lockPositionName(lockPositionName)
                    .releaseDate(unlockDate)
                    .nextReleaseDate(unlockDate)
                    .releaseContent(StringUtil.EMPTY)
                    .status(LockPositionStatus.WAITING.getCode())
                    .brokerId(brokerId)
                    .build();
            // 2. 锁仓
            ResponseResult responseResult = lockedPositionService.lockUserPosition(lockedPosition, tradeNo, false, LOCK_OPERATOR_ACTIVITY);
            if (responseResult.getCode() != 0) {
                log.info("activity lock position lockUserPosition resp code not 0, responseResult={}", responseResult);
            }
            return responseResult;
        } catch (Throwable e) {
            log.error("activity lock position failure {}", e);
            return ResultUtils.failure(e.getMessage());
        }
    }

    /**
     * 批量锁仓
     *
     * @param userLockedPositionDto
     * @return
     */
    @PostMapping("/lockedPosition/lockUser/batch")
    public ResponseResult lockUserPositionBatch(@RequestBody UserLockedPositionDto userLockedPositionDto) {
        log.info("lockUserPositionBatch userLockedPositionDto={}", userLockedPositionDto);
        try {
            return lockedPositionService.lockUserPositionBatch(userLockedPositionDto);
        } catch (Exception e) {
            log.error("batch lock position error userLockedPositionDto={} , e={}", userLockedPositionDto, e);
            return ResultUtils.failure(BizErrorCodeEnum.USER_LOCK_POSITION_ERROR);
        }
    }

//    @RequestMapping("/lockedPosition/lockUser")
//    public ResponseResult lockUserPosition(@RequestParam(value = "phone", required = false) String phone,
//                                           @RequestParam(value = "email", required = false) String email,
//                                           @RequestParam(value = "userId", required = false) Long userId,
//                                           @RequestParam(value = "lockPositionConfId") Long lockPositionConfId,
//                                           @RequestParam(value = "operator") String operator) {
//        try {
//            if (ObjectUtils.isEmpty(userId) && StringUtils.isEmpty(phone) && StringUtils.isEmpty(email)) {
//                return ResultUtils.failure(BizErrorCodeEnum.USER_MARK_ERROR);
//            }
//            userId = getUserIdByLoginName(phone, email, userId);
//            if (ObjectUtils.isEmpty(userId)) {
//                return ResultUtils.failure("未查询到用户信息");
//            }
//            ResponseResult<UserInfoResDTO> userInfo = usersClient.getUserInfo(userId);
//            if (userInfo.getCode() != 0) {
//                return ResultUtils.failure("未查询到用户信息");
//            }
//            /**
//             * 判断一个用户一种锁仓 只锁一次
//             */
//            if (checkExistLockRecord(userId, lockPositionConfId)) {
//                return ResultUtils.failure("此用户已经加入此锁仓计划");
//            }
//            try {
//                ResponseResult responseResult = lockedPositionService.lockUserPosition(userId, lockPositionConfId, null, false, operator);
//                if (responseResult.getCode() == 0) {
//                    return ResultUtils.success();
//                }
//                if (responseResult.getCode() == 1223) {
//                    return ResultUtils.failure("锁仓金额大于可用余额");
//                } else {
//                    return ResultUtils.failure(responseResult.getMsg());
//                }
//            } catch (Exception e) {
//                log.error("锁仓失败 用户 {} {}", userId, e);
//                return ResultUtils.failure("用户锁仓失败");
//            }
//        } catch (Exception e) {
//            log.error("lockUserPosition lockUser error", e);
//            return ResultUtils.failure("用户锁仓失败");
//        }
//    }

    @RequestMapping("/lockedPosition/unlockUser/activity")
    public ResponseResult unlockUserPositionActivity(@RequestParam("tradeNo") String tradeNo,
                                                     @RequestParam("amount") String amount,
                                                     @RequestParam("operator") String operator) {
        log.info("unlockUserPositionActivity tradeNo={} amount={} operator={}", tradeNo, amount, operator);
        try {
            TransferRecord transferRecord = recordService.getByTradeNo(tradeNo);
            if (transferRecord == null) {
                return ResultUtils.failure(BizErrorCodeEnum.HAND_UNLOCK_AMOUNT_ERROR);
            }

            LockedPosition lockedPosition = lockedPositionService.getById(Long.valueOf(transferRecord.getTo()));
            if (lockedPosition.getLockAmount().equals(BigDecimal.ZERO)
                    || lockedPosition.getStatus() == LockPositionStatus.SENDING.getCode()) {
                return ResultUtils.success();
            }

            ResponseResult responseResult = lockedPositionService.unlockUserPosition(new BigDecimal(amount),
                    lockedPosition, operator, null);
            if (responseResult.getCode() != 0) {
                log.info("unlockUserPosition error responseResult={}", responseResult);
            }
            return responseResult;
        } catch (final UnsupportedCurrency e) {
            log.error("unlockUserPositionActivity error", e);
            return ResultUtils.failure("unlockUserPositionActivity error");
        } catch (final Throwable e) {
            log.error("unlockUserPositionActivity error", e);
            return ResultUtils.failure(e.getMessage());
        }
    }

    @RequestMapping("/lockedPosition/unlockUser")
    public ResponseResult unlockUserPosition(@RequestParam("lockedId") Long lockedId,
                                             @RequestParam("amount") String amount,
                                             @RequestParam("operator") String operator) {
        log.info("unlockUserPosition lockedId={} amount={} operator={}", lockedId, amount, operator);
        try {
            if (new BigDecimal(amount).compareTo(BigDecimal.ZERO) <= 0) {
                return ResultUtils.failure("解锁数量必须大于0");
            }
            ResponseResult responseResult = lockedPositionService.unlockUserPosition(new BigDecimal(amount), lockedPositionService.getById(lockedId), operator, null);
            if (responseResult.getCode() != 0) {
                log.error("user unlock fail {} number {} operator {} fail msg {}", lockedId, amount, operator, responseResult.getMsg());
                return ResultUtils.failure(responseResult.getMsg());
            }
            return ResultUtils.success();
        } catch (IllegalArgumentException e) {
            return ResultUtils.failure(e.getMessage());
        } catch (Exception e) {
            log.error("unlockUserPosition error", e);
            return ResultUtils.failure(e.getMessage());
        }
    }

    /**
     * 手动批量解锁用户
     *
     * @param lockedIds 锁仓id列表
     * @param amount    数量
     * @param operator  操作人
     * @return
     */
    @RequestMapping("/lockedPosition/batchUnlockUser")
    public ResponseResult batchUnlockUserPosition(@RequestParam("lockedIds") List<Long> lockedIds,
                                                  @RequestParam("amount") String amount,
                                                  @RequestParam("operator") String operator) {
        log.info("batchUnlockUserPosition lockedIds={} amount={} operator={}", lockedIds, amount, operator);
        try {
            if (new BigDecimal(amount).compareTo(BigDecimal.valueOf(0)) <= 0) {
                return ResultUtils.failure("解锁数量必须大于0");
            }
            return lockedPositionService.batchUnlockUser(lockedIds, new BigDecimal(amount), operator);
        } catch (IllegalArgumentException e) {
            return ResultUtils.failure(e.getMessage());
        } catch (Exception e) {
            log.error("batchUnlockUser fail {}", e);
            return ResultUtils.failure(e.getMessage());
        }
    }

    /**
     * 查询所有的锁仓配置列表 供锁仓列表的查询条件使用
     *
     * @return ResponseResult
     */
    @RequestMapping("/lockedPosition/conf-list")
    public ResponseResult getLockedPositionConf(@RequestBody DataGridPager pager) {
        try {
            return lockedPositionConfService.getConfigListPage(pager);
        } catch (final Throwable e) {
            log.error("get conf list by page fail {}", e);
            return ResultUtils.failure(e.getMessage());
        }
    }

    /**
     * 后台查询用户锁仓列表
     *
     * @param userId           用户id
     * @param phone            手机号
     * @param email            邮箱
     * @param currencyId       币种id
     * @param lockPositionName 锁仓方案名称
     * @return
     */
    @RequestMapping("/lockedPosition/list")
    public ResponseResult<PageBossEntity> getLockedPositionList(
            @RequestBody final DataGridPager pager,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "currencyId", required = false) Integer currencyId,
            @RequestParam(value = "lockPositionName", required = false) String lockPositionName,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "brokerId", required = false) Integer brokerId) {
        try {
            log.info("userId={} phone={} email={} currencyId={} lockPositionName={} startTime={} endTime={} brokerId={}", userId, phone, email, currencyId, lockPositionName, startTime, email, brokerId);
            userId = getUserIdByLoginName(phone, email, userId);
            LockedPositionExample example = new LockedPositionExample();
            LockedPositionExample.Criteria criteria = example.createCriteria();
            if (!ObjectUtils.isEmpty(userId)) {
                criteria.andUserIdEqualTo(userId);
            }
            if (!ObjectUtils.isEmpty(currencyId)) {
                CurrencyEnum currencyEnum = CurrencyEnum.parseValue(currencyId);
                criteria.andCurrencyEqualTo(currencyEnum.getIndex());
            }
            if (!ObjectUtils.isEmpty(lockPositionName)) {
                criteria.andLockPositionNameLike(lockPositionName);
            }
            if (!StringUtils.isEmpty(startTime)) {
                criteria.andCreateDateGreaterThanOrEqualTo(DateUtils.parseDate(startTime, "yyyy-MM-dd"));
            }
            if (!StringUtils.isEmpty(endTime)) {
                criteria.andCreateDateLessThanOrEqualTo(DateUtils.parseDate(endTime, "yyyy-MM-dd"));
            }
            if (!ObjectUtils.isEmpty(brokerId)) {
                criteria.andBrokerIdEqualTo(brokerId);
            }
            List<Integer> status = new ArrayList<>();
            status.add(LockPositionStatus.PREPARED.getCode());
            status.add(LockPositionStatus.SENDING.getCode());
            criteria.andStatusIn(status);

            PageBossEntity pageBossEntity = PageBossEntity.getPage(lockedPositionService, pager.getPage(), pager.getRows(), example);

            List<LockedPosition> lockedPositions = pageBossEntity.getRows();
            List<LockedPositionPageDto> newData = lockedPositions.stream().map((lockedPosition) -> {
                LockedPositionPageDto dto = getLockedPositionPageDto(lockedPosition);
                return dto;
            }).collect(Collectors.toList());
            pageBossEntity.setRows(newData);
            return ResultUtils.success(pageBossEntity);
        } catch (Exception e) {
            log.error("query lock position list fail {}", e);
            return ResultUtils.failure("查询锁仓列表失败");
        }
    }

    @PostMapping(value = "/lockedPosition/record")
    ResponseResult<PageBossEntity> getLockedPositionRecord(@RequestBody DataGridPager<LockedPositionRecordReqDto> request) {
        ResponseResult<PageBossEntity> result;

        LockedPositionRecordReqDto lockedPositionRecordReqDto = request.getQueryParameter();
        try {
            Long userId = getUserIdByLoginName(lockedPositionRecordReqDto.getMobile(), lockedPositionRecordReqDto.getEmail(), lockedPositionRecordReqDto.getUserId());
            TransferRecordExample example = new TransferRecordExample();
            TransferRecordExample.Criteria criteria = example.createCriteria();
            if (userId != null) {
                criteria.andUserIdEqualTo(userId);
            }
            if (!StringUtils.isEmpty(lockedPositionRecordReqDto.getCurrency())) {
                criteria.andCurrencyEqualTo(lockedPositionRecordReqDto.getCurrency());
            }
            if (lockedPositionRecordReqDto.getTransferType() != null) {
                criteria.andTransferTypeEqualTo(lockedPositionRecordReqDto.getTransferType());
            } else {
                criteria.andTransferTypeIn(Arrays.asList(TransferType.LOCKED_POSITION.getCode(), TransferType.UNLOCKED_POSITION.getCode()));
            }
            if (lockedPositionRecordReqDto.getStartTime() != null) {
                criteria.andCreateDateGreaterThan(lockedPositionRecordReqDto.getStartTime());
            }
            if (lockedPositionRecordReqDto.getEndTime() != null) {
                criteria.andCreateDateLessThan(lockedPositionRecordReqDto.getEndTime());
            }
            if (lockedPositionRecordReqDto.getBrokerId() != null) {
                criteria.andBrokerIdEqualTo(lockedPositionRecordReqDto.getBrokerId());
            }
            criteria.andRemarkNotEqualTo(UserInfoCons.LOCK_OPERATOR_ACTIVITY);
            criteria.andStatusEqualTo((byte) TransferStatus.PREPARED.getCode());

            PageBossEntity pageBossEntity = PageBossEntity.getPage(recordService, request.getPage(), request.getRows(), example);
            List<TransferRecord> recordList = pageBossEntity.getRows();
            List<LockedPositionRecordResDto> lockedPositionRecordResDtos = recordList.stream().map(
                    (transferRecord) -> {
                        long lockedPositionId = transferRecord.getTransferType() == TransferType.LOCKED_POSITION.getCode() ?
                                Long.parseLong(transferRecord.getTo()) : Long.parseLong(transferRecord.getFrom());
                        LockedPosition lockedPosition = lockedPositionService.getById(lockedPositionId);
                        return LockedPositionRecordResDto.builder()
                                .userId(transferRecord.getUserId())
                                .amount(transferRecord.getAmount().toPlainString())
                                .currency(CurrencyEnum.parseValue(transferRecord.getCurrency()).name())
                                .lockPosition(lockedPosition == null ? "" : lockedPosition.getLockPositionName())
                                .operator(transferRecord.getRemark())
                                .transferType(transferRecord.getTransferType())
                                .createTime(transferRecord.getCreateDate())
                                .build();
                    }).collect(Collectors.toList());
            pageBossEntity.setRows(lockedPositionRecordResDtos);
            result = ResultUtils.success(pageBossEntity);
        } catch (Exception e) {
            log.error("getLockedPositionRecord error", e);
            return ResultUtils.failure("查询锁仓记录失败");
        }
        return result;
    }

    private Long getUserIdByLoginName(String phone, String email, Long userId) {
        if (ObjectUtils.isEmpty(userId)) {
            try {
                if (!StringUtils.isEmpty(phone)) {
                    return usersClient.getUserInfoByUserName(phone).getData().getId();
                }
                if (!StringUtils.isEmpty(email)) {
                    return usersClient.getUserInfoByUserName(email).getData().getId();
                }
            } catch (Exception e) {
                return null;
            }
        }
        return userId;
    }

    private LockedPositionPageDto getLockedPositionPageDto(LockedPosition lockedPosition) {
        LockedPositionPageDto dto = new LockedPositionPageDto();
        BeanUtils.copyProperties(lockedPosition, dto);
        dto.setCurrencyName(CurrencyEnum.parseValue(dto.getCurrency()).getName().toUpperCase());
        dto.setAmount(lockedPosition.getAmount().toPlainString());
        dto.setLockAmount(lockedPosition.getLockAmount().toPlainString());
        dto.setUnlockAmount(lockedPosition.getAmount().subtract(lockedPosition.getLockAmount()).toPlainString());
        dto.setReleaseRule(lockedPosition.getRemark());
        dto.setBrokerId(lockedPosition.getBrokerId());
        dto.setNextReleaseDate(lockedPosition.getStatus() ==
                LockPositionStatus.PREPARED.getCode() ? lockedPosition.getNextReleaseDate() : null);
        return dto;
    }
}
