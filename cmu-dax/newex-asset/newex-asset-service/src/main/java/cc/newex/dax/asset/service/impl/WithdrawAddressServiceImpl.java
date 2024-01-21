package cc.newex.dax.asset.service.impl;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.common.enums.AddressType;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.criteria.WithdrawAddressExample;
import cc.newex.dax.asset.dao.WithdrawAddressRepository;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.domain.WithdrawAddress;
import cc.newex.dax.asset.dto.UserAddressDto;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.asset.service.WithdrawAddressService;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.security.WithdrawCheckCodeDTO;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-04-09
 */
@Slf4j
@Service
public class WithdrawAddressServiceImpl
        extends AbstractCrudService<WithdrawAddressRepository, WithdrawAddress, WithdrawAddressExample, Integer>
        implements WithdrawAddressService {

    @Autowired
    private UsersClient usersClient;
    @Autowired
    private TransferRecordService recordService;
    @Autowired
    private AssetCurrencyCompressService assetCurrencyService;

    @Override
    protected WithdrawAddressExample getPageExample(final String fieldName, final String keyword) {
        final WithdrawAddressExample example = new WithdrawAddressExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public ResponseResult checkCode(final String emailCode, final String googleCode, final String mobileCode, final Long userId, String cardNumber) {
        WithdrawCheckCodeDTO withdrawCheckCodeDTO = WithdrawCheckCodeDTO.builder()
                .emailCode(emailCode)
                .googleCode(googleCode)
                .mobileCode(mobileCode)
                .cardNumber(cardNumber)
                .build();
        return this.usersClient.withdrawLimit(withdrawCheckCodeDTO, userId);

    }

    @Override
    public WithdrawAddress convertFromUserAddressDto(final UserAddressDto userAddressDto) {
        final WithdrawAddress withdrawAddress = new WithdrawAddress();
        BeanUtils.copyProperties(userAddressDto, withdrawAddress);
        return withdrawAddress;
    }

    @Override
    public List<UserAddressDto> getByDomain(final WithdrawAddress withdrawAddress) {
        final WithdrawAddressExample example = WithdrawAddressExample.from(withdrawAddress);
        final List<WithdrawAddress> withdrawAddressList = this.getByExample(example);
        final List<UserAddressDto> result = withdrawAddressList.parallelStream().map((address) -> {
            UserAddressDto dto = new UserAddressDto();
            BeanUtils.copyProperties(address, dto);
            dto.setType(AddressType.WITHDRAW.getCode());
            return dto;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<WithdrawAddress> getWithdrawAddress(Long userId, BizEnum bizEnum, CurrencyEnum currency) {
        WithdrawAddressExample example = new WithdrawAddressExample();
        example.createCriteria()
                .andCurrencyEqualTo(currency.getIndex())
                .andUserIdEqualTo(userId)
                .andBizEqualTo(bizEnum.getIndex())
                .andStatusEqualTo((byte) 0);
        return this.getByExample(example);

    }

    @Override
    public ResponseResult checkWithdrawLimit(Long userId, int currency, BigDecimal amount, String address, String cardNumber, String emailCode, String googleCode,
                                             String mobileCode, Integer brokerId) {
        boolean isNewAddressResult = this.checkIsNewAddress(userId, currency, address);
        if (isNewAddressResult) {
            return this.checkCardNumber(emailCode, googleCode, mobileCode, userId, cardNumber);
        }
        boolean checkIntervalResult = this.checkIntervalResult(userId);
        if (!checkIntervalResult) {
            return this.checkCardNumber(emailCode, googleCode, mobileCode, userId, cardNumber);
        }
        boolean checkFrequencyResult = this.checkFrequencyResult(userId, brokerId);
        if (!checkFrequencyResult) {
            return this.checkCardNumber(emailCode, googleCode, mobileCode, userId, cardNumber);
        }
        boolean checkAmountLimit = this.checkAmountLimit(currency, amount);
        if (!checkAmountLimit) {
            return this.checkCardNumber(emailCode, googleCode, mobileCode, userId, cardNumber);
        }
        return this.checkCode(emailCode, googleCode, mobileCode, userId, null);
    }

    private ResponseResult checkCardNumber(String emailCode, String googleCode, String mobileCode, Long userId, String cardNumber) {
        if (StringUtils.isEmpty(cardNumber)) {
            ResponseResult<String> incompleteCardNumber = this.usersClient.getCardNumber(userId);
            Map<String, String> cardPre = Maps.newHashMap();
            cardPre.put("cardPre", incompleteCardNumber.getData());
            return ResultUtils.failure(BizErrorCodeEnum.NEED_VALIDATE_IDCARD, cardPre);
        }
        return this.checkCode(emailCode, googleCode, mobileCode, userId, cardNumber);
    }

    private boolean checkIsNewAddress(Long userId, int currency, String address) {
        TransferRecordExample recordExample = new TransferRecordExample();
        recordExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andCurrencyEqualTo(currency)
                .andTransferTypeEqualTo(TransferType.WITHDRAW.getCode())
                .andToEqualTo(address);
        TransferRecord oneByExample = this.recordService.getOneByExample(recordExample);
        log.error("checkIsNewAddress user {} currency {} record ", userId, currency, oneByExample);
        return ObjectUtils.isEmpty(oneByExample);
    }

    private boolean checkIntervalResult(Long userId) {
        TransferRecordExample recordExample = new TransferRecordExample();
        recordExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andTransferTypeEqualTo(TransferType.DEPOSIT.getCode());
        recordExample.setOrderByClause("id desc");
        TransferRecord depositRecord = this.recordService.getOneByExample(recordExample);
        log.error("checkIntervalResult user {} record {}", userId, depositRecord);
        if (ObjectUtils.isEmpty(depositRecord)) {
            return true;
        }
        return System.currentTimeMillis() - depositRecord.getUpdateDate().getTime() > DateUtil.ONE_HOUR;
    }

    private boolean checkFrequencyResult(Long userId, Integer brokerId) {
        String key = MessageFormat.format(RedisKeyCons.ASSET_RECORD_WITHDRAW_KEY_PRE_NEW, userId, brokerId);
        long now = System.currentTimeMillis();
        long oneHourAgo = now - DateUtil.ONE_HOUR;
        Set<String> recordSet = REDIS.zRangeByScore(key, oneHourAgo, now);
        if (CollectionUtils.isEmpty(recordSet)) {
            return true;
        }
        //一小时允许提现次数限制阈值
        int oneHourWithdrawTimes = 3;

        //已经提现次数 + 1
        int hasWithdrawTimes = recordSet.size() + 1;
        log.error("checkFrequencyResult user {} brokerId {} recordSize {}", userId, brokerId, recordSet);

        return hasWithdrawTimes < oneHourWithdrawTimes;
    }

    private boolean checkAmountLimit(int currency, BigDecimal amount) {
        String btcLimitAmount = REDIS.get(RedisKeyCons.ASSET_WITHDRAW_BTC_AMOUNT_LIMIT);
        BigDecimal oneBtc = new BigDecimal(1);
        if (!StringUtils.isEmpty(btcLimitAmount)) {
            oneBtc = new BigDecimal(btcLimitAmount);
        } else {
            log.error("checkAmountLimit:::withdraw limit amount not set ,default is 1");
        }
        BigDecimal convertToBtcPrice = this.assetCurrencyService.coinConverseBTCRate(currency, null);
        BigDecimal multiply = convertToBtcPrice.multiply(amount);
        log.error("checkAmountLimit currency {} multiply {} onebtc {}", convertToBtcPrice, oneBtc);
        return multiply.compareTo(oneBtc) <= 0;
    }

}