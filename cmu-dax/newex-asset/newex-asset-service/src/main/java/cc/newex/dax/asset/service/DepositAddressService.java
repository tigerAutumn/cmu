package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.asset.criteria.DepositAddressExample;
import cc.newex.dax.asset.domain.DepositAddress;
import cc.newex.dax.asset.dto.UserAddressDto;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;

import java.util.List;

/**
 * 服务接口
 *
 * @author
 * @date 2018-04-05
 */
public interface DepositAddressService
        extends CrudService<DepositAddress, DepositAddressExample, Integer> {
    DepositAddress convertFromUserAddressDto(UserAddressDto userAddressDto);

    List<UserAddressDto> getByDomain(DepositAddress depositAddress);

    DepositAddress getDepositAddress(Long userId, BizEnum bizEnum, CurrencyEnum currency, Integer brokerId);
    DepositAddress getByAddress(String address);

    DepositAddress getDepositAddressFromWallet(Long userId, BizEnum bizEnum, CurrencyEnum currency, Integer brokerId);

}
