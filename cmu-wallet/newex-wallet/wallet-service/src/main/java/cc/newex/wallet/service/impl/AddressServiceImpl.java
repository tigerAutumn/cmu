package cc.newex.wallet.service.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dao.AddressRepository;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-03-27
 */
@Slf4j
@Service
public class AddressServiceImpl
        extends AbstractCrudService<AddressRepository, Address, AddressExample, Integer>
        implements AddressService {

    @Autowired
    private AddressRepository addressRepos;

    @Override
    protected AddressExample getPageExample(final String fieldName, final String keyword) {
        final AddressExample example = new AddressExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public Address getAndLockOneByExample(final AddressExample example, final ShardTable table) {
        final Address address = this.addressRepos.selectAndLockOneByExample(example, table);
        if (!ObjectUtils.isEmpty(address)) {
            address.setCurrency(table.getPrefix());
        }
        return address;
    }

    @Override
    public Address getAddress(final String addressStr, ShardTable table) {
        if (!StringUtils.hasText(addressStr)) {
            return null;
        }

        CurrencyEnum currency = CurrencyEnum.parseName(table.getPrefix());
        currency = CurrencyEnum.toMainCurrency(currency);

        table = ShardTable.builder().prefix(currency.getName()).build();

        final AddressExample example = new AddressExample();
        example.createCriteria().andAddressEqualTo(addressStr);

        final Address address = this.getOneByExample(example, table);

        if (!ObjectUtils.isEmpty(address)) {
            address.setCurrency(currency.getName());
        }

        return address;
    }

    @Override
    public Address getAddress(final String addressStr, final CurrencyEnum currency) {
        if (!StringUtils.hasText(addressStr)) {
            return null;
        }

        final ShardTable table = ShardTable.builder().prefix(currency.getName()).build();

        return this.getAddress(addressStr, table);
    }

    @Override
    public Address getAddress(final Long userId, final CurrencyEnum currencyEnum) {
        if (userId == null || userId < 0L || currencyEnum == null) {
            return null;
        }

        final CurrencyEnum mainCurrency = CurrencyEnum.toMainCurrency(currencyEnum);
        final ShardTable table = ShardTable.builder().prefix(mainCurrency.getName()).build();

        final AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(userId);

        final Address address = this.getOneByExample(example, table);

        if (!ObjectUtils.isEmpty(address)) {
            address.setCurrency(mainCurrency.getName());
        }

        return address;
    }

    @Override
    public int countByExam(final AddressExample example, final ShardTable table) {
        return this.dao.countByExample(example, table);
    }

    @Override
    public BigDecimal getTotalBalance(final AddressExample example, final ShardTable table) {
        return this.dao.getTotalBalance(example, table);
    }

}