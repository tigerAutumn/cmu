package cc.newex.dax.asset.service.impl;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.common.consts.UserInfoCons;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.enums.TransferAuditStatus;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.common.exception.AssetBizException;
import cc.newex.dax.asset.common.util.BeanConvertUtil;
import cc.newex.dax.asset.common.util.BizClientUtil;
import cc.newex.dax.asset.common.util.TradeNoUtil;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.dao.LockedPositionRepository;
import cc.newex.dax.asset.dao.TransferRecordRepository;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.DepositAddress;
import cc.newex.dax.asset.domain.LockedPosition;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.domain.TransferRecordAudit;
import cc.newex.dax.asset.dto.LuckWinExchangeVO;
import cc.newex.dax.asset.dto.PayTokenReqDto;
import cc.newex.dax.asset.dto.TransferRecordResDto;
import cc.newex.dax.asset.service.AccountService;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.DepositAddressService;
import cc.newex.dax.asset.service.LockedPositionService;
import cc.newex.dax.asset.service.TransferRecordAuditService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.extra.client.inner.ProjectInnerClient;
import cc.newex.dax.extra.dto.cgm.ProjectPaymentRecordDTO;
import cc.newex.dax.extra.dto.cgm.ProjectTokenInfoDTO;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.spot.client.inner.SpotLastTickerClient;
import cc.newex.dax.spot.client.inner.SpotUserCurrencyBalanceClient;
import cc.newex.dax.spot.dto.ccex.LatestTickerDTO;
import cc.newex.dax.spot.dto.ccex.UserCurrencyBalanceDTO;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.security.WithdrawCheckCodeDTO;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.exception.UnsupportedBiz;
import cc.newex.wallet.exception.UnsupportedCurrency;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static cc.newex.commons.dictionary.enums.TransferType.DEPOSIT;
import static cc.newex.dax.asset.common.consts.RedisKeyCons.ASSET_DATA_KEY;
import static cc.newex.dax.asset.common.consts.RedisKeyCons.LAST_GET_RECORD_ID;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-04-06
 */
@Slf4j
@Service
public class TransferRecordServiceImpl
        extends AbstractCrudService<TransferRecordRepository, TransferRecord, TransferRecordExample, Long>
        implements TransferRecordService {

    private static final long ONE_DAY = 24 * 60 * 60;
    private static final int NOT_ENOUGH_BALANCE = 1223;
    private static final long DEFAULT_OFFICIAL_USER_ID = 10L;

    /**
     * win官方划转账户
     */
    @Value("${newex.exchange.luckywin.account}")
    public Long luckyWinOfficialAccountId;

    @Autowired
    TransferRecordRepository recordRepository;
    @Autowired
    SpotLastTickerClient spotLastTickerClient;
    @Autowired
    private AssetCurrencyCompressService currencyCompressService;
    @Autowired
    private TransferRecordAuditService transferRecordAuditService;
    @Autowired
    private LockedPositionService positionService;
    @Autowired
    private LockedPositionRepository positionRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProjectInnerClient projectInnerClient;
    @Value("${newex.payToken.defaultUser}")
    private Long targetUserId;
    @Autowired
    private UsersClient usersClient;
    @Autowired
    private SpotUserCurrencyBalanceClient spotUserCurrencyBalanceClient;

    @Autowired
    private DepositAddressService depositAddressService;
    @Autowired
    private MessageClient messageClient;

    @Override
    protected TransferRecordExample getPageExample(final String fieldName, final String keyword) {
        final TransferRecordExample example = new TransferRecordExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public TransferRecord getByTradeNo(final String tradeNo) {
        final TransferRecordExample example = new TransferRecordExample();
        example.createCriteria().andTraderNoEqualTo(tradeNo);
        return this.getOneByExample(example);
    }

    /**
     * payToken
     *
     * @param payTokenReqDto project
     * @param biz            spot
     * @param userId         uid
     * @return
     */
    @Override
    public ResponseResult payToken(final PayTokenReqDto payTokenReqDto, final String biz, final Long userId, final Integer brokerId) {
        try {
            final ResponseResult<ProjectTokenInfoDTO> tokenInfo = this.projectInnerClient.getTokenInfo(payTokenReqDto.getProjectId());
            final ProjectTokenInfoDTO data = tokenInfo.getData();
            final BigDecimal amount = data.getDeposit();
            if (ObjectUtils.isEmpty(data)) {
                return ResultUtils.failure(BizErrorCodeEnum.PROJECT_QUERY_ERROR);
            }
            final CurrencyEnum currencyEnum = CurrencyEnum.CT;
            if (currencyEnum.getIndex() != CurrencyEnum.CT.getIndex()) {
                return ResultUtils.failure(BizErrorCodeEnum.ONLY_SUPPORT_CT);
            }
            final BizEnum bizEnum = BizEnum.parseBiz(biz);
            if (this.targetUserId != DEFAULT_OFFICIAL_USER_ID) {
                log.error("payToken targetUserId is not 10  targetId={}", this.targetUserId);
            }

            final TransferRecordExample example = new TransferRecordExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andTransferTypeEqualTo(TransferType.PAY_TOKEN.getCode())
                    .andStatusEqualTo((byte) TransferStatus.CONFIRMED.getCode())
                    .andRemarkEqualTo(String.valueOf(data.getId()))
                    .andBrokerIdEqualTo(brokerId);
            final TransferRecord oneByExample = this.getOneByExample(example);
            if (!ObjectUtils.isEmpty(oneByExample)) {
                return ResultUtils.failure(BizErrorCodeEnum.PROJECT_HAS_PAY_TOKEN);
            }

            String tradeNo = TradeNoUtil.getTradeNo();
            final TransferRecord transferRecord = TransferRecord.builder()
                    .status((byte) TransferStatus.WAITING.getCode())
                    .amount(amount)
                    .biz(bizEnum.getIndex())
                    .currency(currencyEnum.getIndex())
                    .transferType(TransferType.PAY_TOKEN.getCode())
                    .fee(BigDecimal.valueOf(0))
                    .to(String.valueOf(this.targetUserId))
                    .from(String.valueOf(userId))
                    .traderNo(tradeNo)
                    .confirmation(0)
                    .createDate(new Date())
                    .retryTimes(0)
                    .userId(userId)
                    .updateDate(new Date())
                    .remark(String.valueOf(data.getId()))
                    .brokerId(brokerId)
                    .build();
            final int add = this.add(transferRecord);
            if (add <= 0) {
                log.error("payToken marginFromUser error {}", add);
                return ResultUtils.failure(BizErrorCodeEnum.PAY_TOKEN_FAILURE);
            }
            final ResponseResult payResult = BizClientUtil.payToken(false, bizEnum.getIndex(), userId, currencyEnum, amount, tradeNo, brokerId);
            if (payResult.getCode() == 0) {
                log.info("payToken success userId={} targetId={} amount={} result={}", userId, this.targetUserId, amount,
                        payResult.getMsg());
                //第一次扣款成功以后 设置状态=1
                transferRecord.setStatus((byte) TransferStatus.PREPARED.getCode());
                this.editById(transferRecord);
            } else {
                if (payResult.getCode() == NOT_ENOUGH_BALANCE) {
                    this.removeById(transferRecord.getId());
                    log.error("payToken error:balance is not enough userId={}", userId);
                }
                log.error("payToken failure userId={} targetId={} amount={} result={}", userId, this.targetUserId, amount, payResult.getMsg());
                return payResult;
            }
            tradeNo = tradeNo + "@add";
            final ResponseResult addOfficialResult = BizClientUtil.payToken(true, bizEnum.getIndex(), this.targetUserId, currencyEnum, amount, tradeNo, brokerId);
            if (addOfficialResult.getCode() == 0) {
                log.info("addOfficial success userId={} targetId={} amount={} result={}", userId, this.targetUserId, amount,
                        addOfficialResult.getMsg());
                //给官方账户加钱成功以后 设置状态=3
                transferRecord.setStatus((byte) TransferStatus.CONFIRMED.getCode());
                this.editById(transferRecord);

                final ProjectPaymentRecordDTO dto = ProjectPaymentRecordDTO.builder()
                        .amount(amount)
                        .createdDate(new Date())
                        .currencyType((byte) 1)
                        .tokenInfoId(data.getId())
                        .userId(userId)
                        .tradeNo(tradeNo)
                        .updatedDate(new Date())
                        .brokerId(brokerId)
                        .build();
                this.projectInnerClient.saveProjectPaymentRecord(dto);
            } else {
                log.error("addOfficial failure userId={} targetId={} amount={} result={}", userId, this.targetUserId, amount,
                        addOfficialResult.getMsg());
                return addOfficialResult;
            }

            return ResultUtils.success();
        } catch (final UnsupportedCurrency e) {
            log.error("payToken currency error {}", e);
            return ResultUtils.failure(BizErrorCodeEnum.INVALID_CURRENCY);
        } catch (final UnsupportedBiz e1) {
            log.error("payToken biz error {}", biz);
            return ResultUtils.failure(BizErrorCodeEnum.INVALID_BIZ);
        } catch (final Throwable e) {
            log.error("payToken error {}", e);
            throw e;
        }
    }

    @Override
    public ResponseResult winExchange(final CurrencyEnum currencyEnum, final BizEnum fromBiz, final BizEnum toBiz, final Long userId, final LuckWinExchangeVO luckWinExchangeVO,
                                      final Integer brokerId) throws AssetBizException {
        String tradeNo = TradeNoUtil.getTradeNo();
        final BigDecimal amount = luckWinExchangeVO.getAmount();

        //此操作需要先校验短信和邮箱，如果在最后校验失败 ，划转会有问题
        final WithdrawCheckCodeDTO withdrawCheckCodeDTO = WithdrawCheckCodeDTO.builder()
                .emailCode(luckWinExchangeVO.getEmailCode())
                .googleCode(luckWinExchangeVO.getGoogleCode())
                .mobileCode(luckWinExchangeVO.getSmsCode())
                .behavior(3093)
                .build();
        final ResponseResult codeCheck = this.usersClient.withdrawLimit(withdrawCheckCodeDTO, userId);
        if (codeCheck.getCode() != 0) {
            return codeCheck;
        }

        final ResponseResult<UserCurrencyBalanceDTO> userBalance = this.spotUserCurrencyBalanceClient.getUserBalance(brokerId, this.luckyWinOfficialAccountId, CurrencyEnum.LUCKYWIN.getIndex());
        if (userBalance.getCode() != 0) {
            return ResultUtils.failure(BizErrorCodeEnum.TRANSFER_FAIL);
        }
        final UserCurrencyBalanceDTO data = userBalance.getData();
        if (data.getAvailable().compareTo(luckWinExchangeVO.getAmount()) < 0) {
            return ResultUtils.failure(BizErrorCodeEnum.LUCKYWIN_WITHDRAW_ACOUNT_AMOUNT_NOT_ENOUGH);
        }

        final TransferRecord transferRecord = TransferRecord.builder()
                .status((byte) TransferStatus.WAITING.getCode())
                .amount(amount)
                .biz(fromBiz.getIndex())
                .currency(currencyEnum.getIndex())
                .transferType(TransferType.PAY_TOKEN.getCode())
                .fee(BigDecimal.valueOf(0))
                .to(String.valueOf(this.luckyWinOfficialAccountId))
                .from(String.valueOf(userId))
                .traderNo(tradeNo)
                .confirmation(0)
                .createDate(new Date())
                .retryTimes(0)
                .userId(userId)
                .updateDate(new Date())
                .remark("win exchange")
                .brokerId(brokerId)
                .build();
        final int add = this.add(transferRecord);
        if (add <= 0) {
            log.error("win exchange add transferRecord error {}", transferRecord);
            throw new AssetBizException(BizErrorCodeEnum.TRANSFER_FAIL);
        }
        //用户端扣钱
        final ResponseResult result = BizClientUtil.payToken(false, fromBiz.getIndex(), userId, currencyEnum, amount, tradeNo, brokerId);
        if (result.getCode() == 0) {
            log.info("win exchange reduce money success userId={} targetId={} amount={}", userId, this.luckyWinOfficialAccountId, amount);
            //第一次扣款成功以后 设置状态=1
            transferRecord.setStatus((byte) TransferStatus.PREPARED.getCode());
            this.editById(transferRecord);
        } else {
            if (result.getCode() == NOT_ENOUGH_BALANCE) {
                this.removeById(transferRecord.getId());
                log.error("result error:balance is not enough userId={}", userId);
            }
            log.error("unknown error {} message={}", result.getCode(), result.getMsg());
            return result;
        }
        //官方账号加钱
        tradeNo = tradeNo + "@add";

        final ResponseResult officialResult = BizClientUtil.payToken(true, fromBiz.getIndex(), this.luckyWinOfficialAccountId, currencyEnum, amount, tradeNo, brokerId);
        if (officialResult.getCode() == 0) {
            //给官方账户加钱成功以后 设置状态=3
            transferRecord.setStatus((byte) TransferStatus.CONFIRMED.getCode());
            this.editById(transferRecord);
        } else {
            //抛出异常
            throw new AssetBizException(BizErrorCodeEnum.TRANSFER_FAIL);
        }
        //发起一笔提现操作 从luckywin官方账号
        final TransferRecord record = TransferRecord.builder()
                .to(luckWinExchangeVO.getLuckyWinAddress())
                .biz(fromBiz.getIndex())
                .currency(CurrencyEnum.LUCKYWIN.getIndex())
                .userId(this.luckyWinOfficialAccountId)
                .amount(amount)
                .fee(BigDecimal.valueOf(0))
                .transferType(TransferType.WITHDRAW.getCode())
                .status((byte) TransferStatus.WAITING.getCode())
                .brokerId(brokerId)
                .remark("winExchange#" + userId)
                .build();
        final ResponseResult withdrawResult = this.createTransferRecord(record);
        if (withdrawResult.getCode() != 0) {
            if (withdrawResult.getCode() == NOT_ENOUGH_BALANCE) {
                log.error("luckywin official account amount not enough officialId={}", this.luckyWinOfficialAccountId);
                throw new AssetBizException(BizErrorCodeEnum.LUCKYWIN_WITHDRAW_ACOUNT_AMOUNT_NOT_ENOUGH);
            }
            return withdrawResult;
        }
        return ResultUtils.success();
    }

    @Override
    public ResponseResult createTransferRecord(final TransferRecord record) {

        try {
            ResponseResult result = null;
            final Date createDate = new Date();

            final CurrencyEnum currencyEnum = CurrencyEnum.parseValue(record.getCurrency());
            final AssetCurrency info = this.currencyCompressService.getCurrency(currencyEnum.getName(), BizEnum.SPOT.getIndex(), record.getBrokerId());
            if (TransferType.WITHDRAW.getCode() == record.getTransferType()) {
                if (info.getWithdrawable() == 0) {
                    return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_CLOSE);
                }
            }
            if (TransferType.TRANSFER.getCode() == record.getTransferType()) {
                //划转判断币种是否上下线
                if (info.getOnline() == 0) {
                    return ResultUtils.failure(BizErrorCodeEnum.TRANSFER_CLOSE);
                }

                if (info.getTransfer() == 0) {
                    return ResultUtils.failure(BizErrorCodeEnum.TRANSFER_CLOSE);
                } else if (BizEnum.parseBiz(record.getTo()) != BizEnum.INTERNAL) {
                    //划转时查询业务线是否支持
                    final AssetCurrency exist = this.currencyCompressService.getCurrency(currencyEnum.getName().toUpperCase(), record.getTo(), record.getBrokerId());
                    if (ObjectUtils.isEmpty(exist)) {
                        return ResultUtils.failure(BizErrorCodeEnum.BIZ_NOT_SUPPORT);
                    }
                    if (exist.getTransfer() == 0 || exist.getOnline() == 0) {
                        return ResultUtils.failure(BizErrorCodeEnum.BIZ_NOT_SUPPORT);
                    }
                }
            }

            final String tradeNo;

            //先判断是否已经存在
            if (StringUtils.hasText(record.getTraderNo())) {
                tradeNo = record.getTraderNo();
                final String rex = "[a-zA-Z0-9-]{32,120}$";
                if (!tradeNo.matches(rex)) {
                    return ResultUtils.failure(BizErrorCodeEnum.INVALID_TRADE_NO);
                }
                final TransferRecord exist = this.getByTradeNo(tradeNo);
                if (!ObjectUtils.isEmpty(exist)) {
                    if (exist.getUserId().equals(record.getUserId())) {
                        final TransferRecordResDto transferRecordDto = new TransferRecordResDto();
                        BeanConvertUtil.convert(transferRecordDto, exist);
                        result = ResultUtils.success(transferRecordDto);
                        return result;
                    } else {
                        return ResultUtils.failure(BizErrorCodeEnum.INVALID_TRADE_NO);
                    }
                }
            } else {
                tradeNo = TradeNoUtil.getTradeNo();
            }
            record.setTraderNo(tradeNo);
            record.setCreateDate(createDate);

            //请求各个业务线
            final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());

            if (TransferType.WITHDRAW.getCode() == record.getTransferType()) {
                //判断提现是否需要审核
                final boolean needAudit = this.needAuditAmountLimit(currency.getIndex(), record.getAmount());
                if (needAudit) {
                    //发送短信与邮件通知相关人员
                    this.sendWithdrawAuditEmailOrSMS(record, UserInfoCons.WITHDRAW_AUDIT_ALERT_TEMPLATE_SMS);
                    this.sendWithdrawAuditEmailOrSMS(record, UserInfoCons.WITHDRAW_AUDIT_ALERT_TEMPLATE_EMAIL);
                    final TransferRecordResDto transferRecordDto = new TransferRecordResDto();
                    record.setStatus((byte) TransferStatus.AUDIT.getCode());
                    BeanConvertUtil.convert(transferRecordDto, record);
                    result = ResultUtils.success(transferRecordDto);
                    log.warn("createTransferRecord need audit,TraderNo:{}", record.getTraderNo());
                    //提现记录入库
                    this.add(record);
                    //将需审核的记录入库
                    final TransferRecordAudit addTransferRecordAudit = TransferRecordAudit.builder()
                            .traderNo(record.getTraderNo())
                            .status((byte) TransferAuditStatus.WAITING.getCode())
                            .brokerId(record.getBrokerId()).build();
                    this.transferRecordAuditService.add(addTransferRecordAudit);
                    return result;
                }
            }

            this.add(record);

            if (TransferType.WITHDRAW.getCode() == record.getTransferType()) {
                result = BizClientUtil.withdraw(record.getBiz(), record.getUserId(), currency, record.getAmount().add(record.getFee()), record.getTraderNo(), record.getBrokerId());

            } else if (TransferType.TRANSFER.getCode() == record.getTransferType()
                    || TransferType.TRANSFER_OUT.getCode() == record.getTransferType()) {
                result = BizClientUtil.transferOut(record.getBiz(), record.getUserId(), currency, record.getAmount().add(record.getFee()), record.getTraderNo(), record.getBrokerId());
            }

            if (result.getCode() == NOT_ENOUGH_BALANCE) {
                this.removeById(record.getId());
                log.error("user={} currency={} createTransferRecord error:balance is not enough", record.getUserId(), record.getCurrency());
            } else {
                if (TransferType.WITHDRAW.getCode() == record.getTransferType()) {
                    final String key = MessageFormat.format(RedisKeyCons.ASSET_RECORD_WITHDRAW_KEY_PRE_NEW, record.getUserId(), record.getBrokerId());
                    final JSONObject content = new JSONObject();
                    content.put("transferType", record.getTransferType());
                    content.put("currency", record.getCurrency());
                    content.put("amountBTC", record.getAmount().multiply(this.currencyCompressService.coinConverseBTCRate(record.getCurrency(), null)));
                    content.put("traderNo", record.getTraderNo());
                    REDIS.zAdd(key, createDate.getTime(), content.toJSONString());
                    REDIS.expire(key, ONE_DAY);
                }
                if (result.getCode() == 0) {
                    final TransferRecordResDto transferRecordDto = new TransferRecordResDto();
                    BeanConvertUtil.convert(transferRecordDto, record);
                    result = ResultUtils.success(transferRecordDto);
                } else {
                    log.error("createTransferRecord error:{}", result.getMsg());
                }
            }
            return result;
        } catch (final Throwable e) {
            log.error("createTransferRecord error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }

    }

    private void sendWithdrawAuditEmailOrSMS(final TransferRecord record, final String messageTemplate) {
        try {
            if (ObjectUtils.isEmpty(record)) {
                log.info("sendWithdrawAuditEmailOrSMS sendEmail error,TransferRecord is null");
                return;
            }
            final CurrencyEnum currencyEnum = CurrencyEnum.parseValue(record.getCurrency());

            final Map<String, String> params = new HashMap<>();
            params.put("userid", record.getUserId().toString());
            params.put("amount", record.getAmount().stripTrailingZeros().toPlainString());
            params.put("coin", currencyEnum.name());

            final MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                    .countryCode("86")
                    .locale(Locale.CHINA.toLanguageTag())
                    .email(UserInfoCons.WITHDRAW_AUDIT_ALERT_CONTACT_EMAIL)
                    .mobile(UserInfoCons.WITHDRAW_AUDIT_ALERT_CONTACT_SMS)
                    .templateCode(messageTemplate)
                    .params(JSON.toJSONString(params))
                    .brokerId(record.getBrokerId())
                    .build();

            this.messageClient.send(messageReqDTO);
        } catch (final Throwable e) {
            log.error("sendWithdrawAuditEmailOrSMS error,txid:{}, userId:{},", record.getFrom(), record.getUserId(), e);
        }
    }

    @Override
    public ResponseResult retryTransferRecord(final TransferRecord record) {
        log.info("retryTransferRecord trade_no:{}", record.getTraderNo());
        ResponseResult result = null;
        try {

            //请求各个业务线
            final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());

            BizEnum bizEnum = BizEnum.parseBiz(record.getBiz());
            if (TransferType.TRANSFER.getCode() == record.getTransferType()) {
                bizEnum = BizEnum.parseBiz(record.getFrom());
                result = BizClientUtil.transferOut(bizEnum.getIndex(), record.getUserId(), currency, record.getAmount(), record.getTraderNo(),
                        record.getBrokerId());

            } else if (TransferType.TRANSFER_OUT.getCode() == record.getTransferType()) {
                bizEnum = BizEnum.parseBiz(record.getBiz());
                final Long fromUser = Long.valueOf(record.getFrom());
                result = BizClientUtil.transferOut(bizEnum.getIndex(), fromUser, currency, record.getAmount(), record.getTraderNo(), record.getBrokerId());

            } else if (TransferType.WITHDRAW.getCode() == record.getTransferType()) {
                result = BizClientUtil.withdraw(bizEnum.getIndex(), record.getUserId(), currency, record.getAmount().add(record.getFee()), record.getTraderNo(), record.getBrokerId());
            }

            if (result.getCode() == NOT_ENOUGH_BALANCE) {
                this.removeById(record.getId());
                log.error("createTransferRecord error:balance is not enough");
            } else if (result.getCode() != 0) {
                log.error("retryTransferRecord error:{}", result.getMsg());
            }
        } catch (final Throwable e) {
            log.error("retryTransferRecord error", e);
            result = ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = READ_UNCOMMITTED)
    public boolean sendWithdraw(final TransferRecord record) {

        final TransferRecordExample example = new TransferRecordExample();
        example.createCriteria().andTraderNoEqualTo(record.getTraderNo()).andStatusEqualTo((byte) Constants.WAITING);

        final TransferRecord exist = this.getAndLockOneByExample(example);
        if (!ObjectUtils.isEmpty(exist)) {
            record.setStatus((byte) TransferStatus.SENDING.getCode());
            record.setUpdateDate(Date.from(Instant.now()));
            this.editById(record);

            final String addressDst = record.getTo();
            final DepositAddress address = this.depositAddressService.getByAddress(addressDst);
            final CurrencyEnum currencyEnum = CurrencyEnum.parseValue(record.getCurrency());
            if (!ObjectUtils.isEmpty(address) && currencyEnum.isContractFakeCurrency()) {
                final String inTradeNo = record.getTraderNo() + "-deposit";
                BizClientUtil.deposit(record.getBiz(), address.getUserId(), currencyEnum, record.getAmount(), inTradeNo, record.getBrokerId());
                record.setStatus((byte) TransferStatus.CONFIRMED.getCode());
                record.setUpdateDate(Date.from(Instant.now()));
                record.setFrom(record.getTraderNo() + "fast-withdraw");
                this.editById(record);
                record.setId(null);
                record.setFrom(inTradeNo);
                record.setTraderNo(inTradeNo);
                record.setUserId(address.getUserId());
                record.setTransferType(DEPOSIT.getCode());
                final Long confirm = currencyEnum.getConfirmNum();
                record.setConfirmation(confirm.intValue());
                this.add(record);

            } else {
                //把提现记录推到待提现队列，等待wallet消费
                final WithdrawRecord withdrawRecord = WithdrawRecord.builder()
                        .userId(record.getUserId())
                        .fee(record.getFee())
                        .currency(record.getCurrency())
                        .biz(record.getBiz())
                        .balance(record.getAmount())
                        .address(record.getTo())
                        .withdrawId(record.getTraderNo())
                        .status((byte) Constants.WAITING)
                        .build();

                REDIS.rPush(Constants.WALLET_WITHDRAW_WAIT_KEY, JSONObject.toJSONString(withdrawRecord));
            }
        }

        return true;

    }

    @Override
    public TransferRecord getAndLockOneByExample(final TransferRecordExample example) {
        return this.recordRepository.selectAndLockOneByExample(example);
    }

    @Override
    public List<Map<String, Object>> getUnconfirmedRecordSum(final Integer transferType) {
        return this.recordRepository.getUnconfirmedRecordSum(transferType);
    }

    @Override
    public void analyseTransferData() {
        final ResponseResult<List<LatestTickerDTO>> responseResult = this.spotLastTickerClient.queryLastTicker(null,
                CurrencyEnum.BTC.getIndex());
        final List<LatestTickerDTO> tickers = responseResult.getData();
        final Map<CurrencyEnum, BigDecimal> currencyTicker = new LinkedHashMap<>();
        currencyTicker.put(CurrencyEnum.BTC, BigDecimal.ONE);
        if (!CollectionUtils.isEmpty(tickers)) {
            //首先获得每个币种对BTC的价格
            tickers.forEach((ticker) -> {
                final String pair = ticker.getSymbol();
                if (StringUtils.hasText(pair)) {
                    final CurrencyEnum currencyEnum = CurrencyEnum.parseName(pair.split("_")[0]);
                    if (StringUtils.hasText(ticker.getLast())) {
                        currencyTicker.put(currencyEnum, new BigDecimal(ticker.getLast()));
                    }
                }
            });

            //获取最近一小时内所有的充值和提现记录
            final Date preHour = Date.from(Instant.now().minus(1, ChronoUnit.HOURS).truncatedTo(ChronoUnit.HOURS));
            final Date curHour = Date.from(Instant.now().truncatedTo(ChronoUnit.HOURS));
            final String lastIdStr = REDIS.get(LAST_GET_RECORD_ID);
            final Long lastId = Long.valueOf(StringUtils.hasText(lastIdStr) ? lastIdStr : "0");

            final TransferRecordExample example = new TransferRecordExample();
            example.createCriteria().andIdGreaterThan(lastId).andTransferTypeBetween(DEPOSIT.getCode(),
                    TransferType.WITHDRAW
                            .getCode()).andCreateDateGreaterThan(preHour).andCreateDateLessThan(curHour);
            final PageInfo page = new PageInfo();
            final int size = 200;
            page.setPageSize(size);
            page.setSortItem("id");
            page.setStartIndex(0);

            //初始化数据
            final JSONObject assetData = new JSONObject();
            assetData.put("totalDepositAmount", BigDecimal.ZERO);
            assetData.put("totalDepositUsers", new HashSet<Long>());
            assetData.put("firstDepositUsers", new HashSet<Long>());
            assetData.put("totalDepositRecords", 0);

            assetData.put("totalWithdrawAmount", BigDecimal.ZERO);
            assetData.put("totalWithdrawUsers", new HashSet<Long>());
            assetData.put("totalWithdrawRecords", 0);

            while (true) {
                final List<TransferRecord> transferRecords = this.getByPage(page, example);
                transferRecords.forEach((record -> {
                    if (record.getTransferType() == DEPOSIT.getCode()) {
                        this.analyseDepositRecord(record, assetData, currencyTicker);

                    } else if (record.getTransferType() == TransferType.WITHDRAW.getCode()) {
                        this.analyseWithdrawRecord(record, assetData, currencyTicker);
                    }
                }));

                if (!CollectionUtils.isEmpty(transferRecords)) {
                    //缓存最后一条数据的id，下次直接从此id开始查询
                    final TransferRecord record = transferRecords.get(transferRecords.size() - 1);
                    REDIS.set(LAST_GET_RECORD_ID, record.getId().toString());
                }

                if (transferRecords.size() < size) {
                    break;
                }
                page.setStartIndex(page.getStartIndex() + size);
            }

            //把集合转变成大小
            assetData.put("totalDepositUsers", assetData.getObject("totalDepositUsers", Set.class).size());
            assetData.put("firstDepositUsers", assetData.getObject("firstDepositUsers", Set.class).size());
            assetData.put("totalWithdrawUsers", assetData.getObject("totalWithdrawUsers", Set.class).size());
            final String statistics = assetData.toJSONString();
            REDIS.set(ASSET_DATA_KEY, statistics);

        } else {
            log.error("analyseTransferData error, ticker data is null");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirmLockPositionRecord(final String tradeNo, final long userId, final int currencyId, final TransferRecord oneByExample, final BigDecimal amount) {
        this.recordRepository.updateById(oneByExample);
        final LockedPosition position = this.positionService.getById(Long.valueOf(oneByExample.getTo()));
        position.setStatus(1);
        this.positionRepository.updateById(position);
        log.info("this.accountService.accountAdd userId={}", userId);
        this.accountService.accountAdd(userId, currencyId, amount, tradeNo, oneByExample.getBrokerId());
    }

    private void analyseDepositRecord(final TransferRecord record, final JSONObject assetData,
                                      final Map<CurrencyEnum, BigDecimal> tickerMap) {
        BigDecimal totalAmount = assetData.getBigDecimal("totalDepositAmount");
        final BigDecimal ticker = tickerMap.getOrDefault(CurrencyEnum.parseValue(record.getCurrency()), BigDecimal.ZERO);
        totalAmount = totalAmount.add(record.getAmount().multiply(ticker));
        assetData.put("totalDepositAmount", totalAmount);
        final Set<Long> totalDepositUsers = assetData.getObject("totalDepositUsers", Set.class);
        totalDepositUsers.add(record.getUserId());

        final Set<Long> firstDepositUsers = assetData.getObject("firstDepositUsers", Set.class);

        final TransferRecordExample example = new TransferRecordExample();
        final Date preHour = Date.from(Instant.now().minus(1, ChronoUnit.HOURS).truncatedTo(ChronoUnit.HOURS));

        example.createCriteria().andUserIdEqualTo(record.getUserId()).
                andTransferTypeEqualTo(DEPOSIT.getCode()).andCreateDateLessThan(preHour);

        final TransferRecord exist = this.getOneByExample(example);
        if (ObjectUtils.isEmpty(exist)) {
            firstDepositUsers.add(record.getUserId());
        }

        int records = assetData.getIntValue("totalDepositRecords");
        records += 1;
        assetData.put("totalDepositRecords", records);

    }

    private void analyseWithdrawRecord(final TransferRecord record, final JSONObject assetData,
                                       final Map<CurrencyEnum, BigDecimal> tickerMap) {
        BigDecimal totalAmount = assetData.getBigDecimal("totalWithdrawAmount");
        final BigDecimal ticker = tickerMap.getOrDefault(CurrencyEnum.parseValue(record.getCurrency()), BigDecimal.ZERO);

        totalAmount = totalAmount.add(record.getAmount().multiply(ticker));
        assetData.put("totalWithdrawAmount", totalAmount);
        final Set<Long> totalWithdrawUsers = assetData.getObject("totalWithdrawUsers", Set.class);
        totalWithdrawUsers.add(record.getUserId());

        int records = assetData.getIntValue("totalWithdrawRecords");
        records += 1;
        assetData.put("totalWithdrawRecords", records);

    }

    /**
     * 获取充值未到业务账的列表
     *
     * @return
     */
    @Override
    public List<TransferRecord> getDepositNotBizList(final Long userId, final int currencyId, final int brokerId, final String address) {

        final TransferRecordExample example = new TransferRecordExample();
        final TransferRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo((byte) TransferStatus.DEPOSIT_NOT_BIZ.getCode());
        criteria.andTransferTypeEqualTo(DEPOSIT.getCode());
        criteria.andCurrencyEqualTo(currencyId);
        criteria.andBrokerIdEqualTo(brokerId);
        criteria.andToEqualTo(address);
        return this.recordRepository.selectByExample(example);
    }

    //是否需要审核
    private boolean needAuditAmountLimit(final int currency, final BigDecimal amount) {
        final String btcLimitAmount = REDIS.get(RedisKeyCons.ASSET_WITHDRAW_BTC_AUDIT_AMOUNT_LIMIT);
        //审核数量
        BigDecimal auditBtc = new BigDecimal(1);
        if (!StringUtils.isEmpty(btcLimitAmount)) {
            auditBtc = new BigDecimal(btcLimitAmount);
        } else {
            log.error("needAuditAmountLimit:::withdraw audit    limit amount not set ,default is 1");
        }
        final BigDecimal convertToBtcPrice = this.currencyCompressService.coinConverseBTCRate(currency, null);
        //用户提现金额
        final BigDecimal withdrawAmount = convertToBtcPrice.multiply(amount);
        //提现金额为0的全部进审核
        if (withdrawAmount.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        log.error("checkAmountLimit currency {} withdrawAmount {} onebtc {}", convertToBtcPrice, auditBtc);

        return auditBtc.compareTo(withdrawAmount) <= 0;
    }

}