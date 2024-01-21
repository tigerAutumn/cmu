package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.criteria.WithdrawAddressExample;
import cc.newex.dax.asset.domain.WithdrawAddress;
import cc.newex.dax.asset.dto.UserAddressDto;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-04-09
 */
public interface WithdrawAddressService
        extends CrudService<WithdrawAddress, WithdrawAddressExample, Integer> {
    /**
     * @description: 校验提现验证码是否正确
     * @author: newex-team
     * @date: 2018/6/27 下午2:41
     */
    ResponseResult checkCode(String setEmailCode, String setGoogleCode, String setMobileCode, Long userId, String cardNumber);

    WithdrawAddress convertFromUserAddressDto(UserAddressDto userAddressDto);

    List<UserAddressDto> getByDomain(WithdrawAddress withdrawAddress);

    List<WithdrawAddress> getWithdrawAddress(Long userId, BizEnum bizEnum, CurrencyEnum currency);

    ResponseResult checkWithdrawLimit(Long userId, int currency, BigDecimal amount, String address, String cardNumber, String emailCode, String googleCode,
                                      String mobileCode, Integer brokerId);
}
