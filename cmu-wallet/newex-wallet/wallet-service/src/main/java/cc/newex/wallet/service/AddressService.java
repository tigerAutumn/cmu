package cc.newex.wallet.service;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;

import java.math.BigDecimal;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-03-27
 */
public interface AddressService
        extends CrudService<Address, AddressExample, Integer> {

    Address getAndLockOneByExample(AddressExample example, ShardTable table);

    Address getAddress(String addressStr, ShardTable table);

    Address getAddress(String addressStr, CurrencyEnum currencyEnum);

    Address getAddress(Long userId, CurrencyEnum currencyEnum);

    int countByExam(final AddressExample example, final ShardTable table);

    BigDecimal getTotalBalance(AddressExample example, ShardTable table);

}
