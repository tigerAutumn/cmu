package cc.newex.dax.asset.service.impl;

import cc.newex.commons.dictionary.consts.BrokerIdConsts;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.LockPositionStatus;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.common.util.BizClientUtil;
import cc.newex.dax.asset.common.util.TradeNoUtil;
import cc.newex.dax.asset.criteria.LockedPositionExample;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.dao.LockedPositionRepository;
import cc.newex.dax.asset.domain.LockedPosition;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.dto.UserLockedPositionDto;
import cc.newex.dax.asset.service.LockedPositionService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.asset.service.general.SMSService;
import cc.newex.dax.asset.service.impl.transaction.LockedPositionTran;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static cc.newex.dax.integration.dto.message.MessageTemplateConsts.SMS_ASSET_UNLOCK_POSITION_NOTIFY;

/**
 * 锁仓记录表 服务实现
 *
 * @author newex-team
 * @date 2018-07-04
 */
@Slf4j
@Service
public class LockedPositionServiceImpl
        extends AbstractCrudService<LockedPositionRepository, LockedPosition, LockedPositionExample, Long>
        implements LockedPositionService {

    private static final Long GIVE_ID = 10L;
    @Autowired
    private LockedPositionRepository lockedPositionRepository;
    @Autowired
    private LockedPositionTran lockedPositionTran;
    @Autowired
    private TransferRecordService transferRecordService;
    @Autowired
    private SMSService smsService;
    @Autowired
    private UsersClient usersClient;

    @Override
    protected LockedPositionExample getPageExample(final String fieldName, final String keyword) {
        final LockedPositionExample example = new LockedPositionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public ResponseResult lockUserPositionBatch(UserLockedPositionDto userLockedPositionDto) {
        List<String> failList = new ArrayList<>();
        List<String> successList = new ArrayList<>();
        Date releaseDate = DateUtil.dateTimeDayTrim(userLockedPositionDto.getReleaseDate());
        if (userLockedPositionDto.getBrokerId() == null) {
            userLockedPositionDto.setBrokerId(BrokerIdConsts.COIN_MEX);
        }

        userLockedPositionDto.getUsers().stream().forEach(a -> {
            try {
                UserInfoResDTO user = this.usersClient.getUserInfoByUserName(a).getData();
                if (user != null) {
                    // 1. 构造锁仓配置
                    LockedPosition lockedPosition = LockedPosition.builder()
                            .userId(user.getId())
                            .currency(userLockedPositionDto.getCurrency())
                            .amount(userLockedPositionDto.getAmount())
                            .lockAmount(userLockedPositionDto.getAmount())
                            .dividend(userLockedPositionDto.getDividend())
                            .remark(this.parseRemark(userLockedPositionDto.getReleaseFrequency(), userLockedPositionDto.getReleaseProportion()))
                            .lockPositionName(userLockedPositionDto.getLockedPositionName())
                            .releaseDate(releaseDate)
                            .nextReleaseDate(releaseDate)
                            .releaseContent(this.parseReleaseContent(
                                    userLockedPositionDto.getReleaseFrequency(), userLockedPositionDto.getReleaseProportion()))
                            .status(LockPositionStatus.WAITING.getCode())
                            .brokerId(userLockedPositionDto.getBrokerId())
                            .build();
                    ResponseResult responseResult = this.lockUserPosition(lockedPosition, null, false, userLockedPositionDto.getOperator());
                    if (responseResult.getCode() != 0) {
                        failList.add(a + ":" + responseResult.getMsg());
                    } else {
                        successList.add(JSONObject.parseObject(JSONObject.toJSONString(responseResult.getData())).getString("tradeNo"));
                    }
                } else {
                    failList.add(a + ":用户不存在");
                }
            } catch (Throwable e) {
                log.error("lockUserPositionBatch error, user={}", a, e);
                failList.add(a + ":系统异常");
            }
        });
        if (CollectionUtils.isEmpty(failList)) {
            return ResultUtils.success(successList);
        }
        return ResultUtils.failure(String.join(",", failList));
    }

    private String parseReleaseContent(Integer releaseFrequency, BigDecimal releaseProportion) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(releaseDate);
//
//        JSONArray releaseDateJson = new JSONArray();
//        BigDecimal releaseAmount = amount.multiply(releaseProportion);
//        for (BigDecimal i = releaseProportion; i.compareTo(new BigDecimal("100")) < 0; i.add(releaseProportion)) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("unlockAmount", releaseAmount);
//            map.put("releaseDate", DateUtil.getFormatDate(calendar.getTime(), DATE_FORMAT_TYPE));
//            releaseDateJson.add(map);
//            calendar.add(releaseFrequency, 1);
//        }
        JSONObject content = new JSONObject();
        content.put("frequency", releaseFrequency);
        content.put("proportion", releaseProportion);
        return content.toJSONString();
    }

    private String parseRemark(Integer releaseFrequency, BigDecimal releaseProportion) {
        if (releaseFrequency == null || releaseFrequency.intValue() == 0
                || releaseProportion == null || releaseProportion.equals(BigDecimal.ZERO)) {
            return "";
        }

        BigDecimal multiply = releaseProportion.multiply(new BigDecimal("100"));
        String frequency = "";
        if (Calendar.MONTH == releaseFrequency) {
            frequency = "月";
        } else if (Calendar.DAY_OF_MONTH == releaseFrequency) {
            frequency = "天";
        }
        return "每" + frequency + "释放" + multiply.stripTrailingZeros().toPlainString() + "%";
    }

    @Override
    public ResponseResult lockUserPosition(LockedPosition lockedPosition, String tradeNo, boolean isGive, String operator) {
        JSONObject result = new JSONObject();
        if (StringUtils.hasText(tradeNo)) {
            TransferRecord transferRecord = this.transferRecordService.getByTradeNo(tradeNo);
            if (transferRecord != null) {
                result.put("tradeNo",tradeNo);
                return ResultUtils.success(result);
            }
        } else {
            tradeNo = TradeNoUtil.getTradeNo();
        }

        // 1. 创建锁仓记录
        this.lockedPositionTran.lockUserPositionTran(lockedPosition, tradeNo, isGive ? "spot_10" : "spot", operator);

        // 2. 扣减用户资产
        ResponseResult responseResult = BizClientUtil.lockedPosition(BizEnum.SPOT.getIndex(), isGive ? GIVE_ID : lockedPosition.getUserId(),
                CurrencyEnum.parseValue(lockedPosition.getCurrency()), lockedPosition.getAmount(), tradeNo, lockedPosition.getBrokerId());
        if ( responseResult.getCode() == 0 ) {
            result.put("tradeNo",tradeNo);
            return ResultUtils.success(result);
        }
        return responseResult;
    }

    @Override
    public ResponseResult unlockUserPosition(BigDecimal amount, LockedPosition lockedPosition, String operator, String tradeNo) {
        if (StringUtils.hasText(tradeNo)) {
            TransferRecord transferRecord = this.transferRecordService.getByTradeNo(tradeNo);
            if (transferRecord != null) {
                return ResultUtils.success();
            }
        } else {
            tradeNo = TradeNoUtil.getTradeNo();
        }

        // 1. 修改账户
        this.lockedPositionTran.unlockUserPositionTran(amount, lockedPosition, tradeNo, operator);

        return this.notifyBizUnlockPosition(amount, lockedPosition, tradeNo);
    }

    @Override
    public ResponseResult notifyBizUnlockPosition(BigDecimal amount, LockedPosition lockedPosition, String tradeNo) {
        // 2. 释放锁仓
        ResponseResult responseResult = BizClientUtil.unlockedPosition(BizEnum.SPOT.getIndex(),
                lockedPosition.getUserId(), CurrencyEnum.parseValue(lockedPosition.getCurrency()), amount, tradeNo, lockedPosition.getBrokerId());
        if (responseResult.getCode() != 0) {
            return responseResult;
        } else {
            TransferRecordExample example = new TransferRecordExample();
            example.createCriteria().andTraderNoEqualTo(tradeNo);
            TransferRecord record = TransferRecord.builder().status((byte) TransferStatus.CONFIRMED.getCode()).build();
            this.transferRecordService.editByExample(record, example);
        }

        // 3. 更新解锁时间及状态
        // 重新查询余额
        LockedPosition newPosition = this.getById(lockedPosition.getId());
        LockedPosition lockedPosition4Update = new LockedPosition();
        lockedPosition4Update.setId(lockedPosition.getId());
        if (newPosition.getLockAmount().compareTo(BigDecimal.ZERO) == 0) {
            lockedPosition4Update.setStatus(2);
            this.editById(lockedPosition4Update);
        }

        // 4. 给用户发短信
        this.sendUnlockNotifyToUser(amount, lockedPosition);
        return responseResult;
    }

    /**
     * 发送短信或邮箱给用户，通知用户解仓
     * 此过程发送失败不影响主流程
     * TODO 线上添加锁仓短信模块--上线前需要确认 //亲爱的CoinMex用户，您的锁仓资产${amount}枚${currency}已经解锁，您可以前往官网资产管理-币币账户-锁仓管理中进行提取。
     *
     * @param amount         解锁数量
     * @param lockedPosition 解锁内容
     */
    private void sendUnlockNotifyToUser(BigDecimal amount, LockedPosition lockedPosition) {

        JSONObject content = new JSONObject();
        content.put("amount", amount.toPlainString());
        content.put("currency", CurrencyEnum.parseValue(lockedPosition.getCurrency()).name());
        try {
            this.smsService.sendSMSOrMail(content, SMS_ASSET_UNLOCK_POSITION_NOTIFY, lockedPosition.getUserId(), lockedPosition.getBrokerId());
        } catch (Exception e) {
            log.error("unlock position send sms fail {}", lockedPosition);
        }
    }

    /**
     * 批量锁仓
     *
     * @param lockedIds 批量解锁id 逗号隔开
     * @param amount    解锁数量
     * @param operator  操作人
     * @return
     */
    @Override
    public synchronized ResponseResult batchUnlockUser(List<Long> lockedIds, BigDecimal amount, String operator) {
        AtomicInteger failNum = new AtomicInteger(0);
        StringBuffer errorMessage = new StringBuffer();
        lockedIds.forEach(id -> {
            try {
                LockedPosition lockedPosition = this.lockedPositionRepository.selectById(id);
                if (lockedPosition != null) {
                    if (lockedPosition.getLockAmount().compareTo(BigDecimal.valueOf(0)) == 0) {
                        failNum.getAndIncrement();
                        errorMessage.append("用户 ").append(lockedPosition.getUserId()).append(" 可解锁为0;\n");
                        return;
                    }
                    if (lockedPosition.getLockAmount().compareTo(amount) < 0) {
                        failNum.getAndIncrement();
                        errorMessage.append("用户 ").append(lockedPosition.getUserId()).append(" 可解锁数量小于解锁数量;\n");
                        return;
                    }
                    if (lockedPosition.getStatus() == LockPositionStatus.WAITING.getCode()) {
                        failNum.getAndIncrement();
                        errorMessage.append("用户 ").append(lockedPosition.getUserId()).append(" 解锁记录状态错误;\n");
                        return;
                    }
                    ResponseResult responseResult = this.unlockUserPosition(amount, lockedPosition, operator, null);
                    if (responseResult.getCode() != 0) {
                        failNum.getAndIncrement();
                        errorMessage.append("用户 ").append(lockedPosition.getUserId()).append(" 锁仓失败-;").append(responseResult.getMsg()).append(";\n");
                        log.error("have one unlock fail {} {}", lockedPosition, amount);
                    }
                }
            } catch (Exception e) {
                failNum.getAndIncrement();
                log.error("批量解锁遍历异常 {}", e.getMessage());
            }
        });
        if (failNum.get() > 0) {
            return ResultUtils.failure("失败列表：" + errorMessage.toString());
        }

        return ResultUtils.success();
    }

}