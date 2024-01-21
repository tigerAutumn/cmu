package cc.newex.dax.asset.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.common.enums.AddressType;
import cc.newex.dax.asset.criteria.DepositAddressExample;
import cc.newex.dax.asset.dao.DepositAddressRepository;
import cc.newex.dax.asset.domain.DepositAddress;
import cc.newex.dax.asset.dto.UserAddressDto;
import cc.newex.dax.asset.service.DepositAddressService;
import cc.newex.wallet.client.WalletClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.AddressDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现
 *
 * @author
 * @date 2018-04-05
 */
@Slf4j
@Service
public class DepositAddressServiceImpl
        extends AbstractCrudService<DepositAddressRepository, DepositAddress, DepositAddressExample, Integer>
        implements DepositAddressService {

    @Autowired
    WalletClient walletClient;

    @Override
    protected DepositAddressExample getPageExample(final String fieldName, final String keyword) {
        final DepositAddressExample example = new DepositAddressExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public DepositAddress convertFromUserAddressDto(final UserAddressDto userAddressDto) {
        final DepositAddress depositAddress = new DepositAddress();
        BeanUtils.copyProperties(userAddressDto, depositAddress);
        return depositAddress;
    }

    @Override
    public List<UserAddressDto> getByDomain(final DepositAddress depositAddress) {
        final DepositAddressExample example = DepositAddressExample.from(depositAddress);
        final List<DepositAddress> depositAddressList = this.getByExample(example);
        final List<UserAddressDto> result = depositAddressList.parallelStream().map((address) -> {
            UserAddressDto dto = new UserAddressDto();
            BeanUtils.copyProperties(address, dto);
            dto.setType(AddressType.DEPOSIT.getCode());
            return dto;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public DepositAddress getDepositAddress(Long userId, BizEnum bizEnum, CurrencyEnum currency, Integer brokerId) {
        DepositAddressExample example = new DepositAddressExample();
        example.createCriteria()
                .andCurrencyEqualTo(currency.getIndex())
                .andUserIdEqualTo(userId)
                .andBizEqualTo(bizEnum.getIndex())
                .andStatusEqualTo((byte) 0)
                .andBrokerIdEqualTo(brokerId);
        DepositAddress address = this.getOneByExample(example);
        if (ObjectUtils.isEmpty(address)) {
            address = this.getDepositAddressFromWallet(userId, bizEnum, currency, brokerId);
        }
        return address;
    }

    @Override
    public DepositAddress getByAddress(String addressStr){
        DepositAddressExample example = new DepositAddressExample();
        example.createCriteria()
                .andAddressEqualTo(addressStr)
                .andStatusEqualTo((byte) 0);
        List<DepositAddress> address = this.getByExample(example);
        if(CollectionUtils.isEmpty(address)){
            return null;
        }else if(address.size() > 1){
            log.error("There are one more address equal :{}",addressStr);
            throw new RuntimeException("There are one more address equal "+addressStr);
        }else {
            return address.get(0);
        }
    }

    /**
     * 不检查数据中是否已经存在地址，直接请求钱包生成新的地址
     *
     * @param userId
     * @param bizEnum
     * @param currency
     * @return
     */
    @Override
    public DepositAddress getDepositAddressFromWallet(Long userId, BizEnum bizEnum, CurrencyEnum currency, Integer brokerId) {
        ResponseResult<AddressDto> result = this.walletClient.generateNewAddress(currency.getIndex(), userId, bizEnum.getIndex());
        AddressDto addressDto = result.getData();
        DepositAddress address = DepositAddress.builder()
                .biz(bizEnum.getIndex())
                .address(addressDto.getAddress())
                .currency(currency.getIndex())
                .userId(userId).status((byte) 0)
                .brokerId(brokerId).build();
        this.add(address);
        return address;
    }
}