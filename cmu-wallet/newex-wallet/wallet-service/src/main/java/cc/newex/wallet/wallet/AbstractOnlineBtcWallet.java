package cc.newex.wallet.wallet;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.OnLineBtcCommand;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.pojo.Address;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


/**
 * @author newex-team
 * @data 27/03/2018
 */
@Slf4j
public abstract class AbstractOnlineBtcWallet extends AbstractBtcLikeWallet implements IWallet {

    OnLineBtcCommand command;

    public void setCommand(OnLineBtcCommand com) {
        super.setCommand(com);
        this.command = com;
    }

    @Override
    public Address genNewAddress(Long userId, Integer biz) {
        AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(userId).andBizEqualTo(biz);

        ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        List<Address> addressList = this.addressService.getByExample(example, table);
        int index = 0;

        //获取该userId在biz业务线下面已经生成了多少地址
        if (!CollectionUtils.isEmpty(addressList)) {

            Optional<Address> maxAddress = addressList.stream().max(Comparator.comparing(Address::getIndex));
            index = maxAddress.get().getIndex() + 1;
        }
        String newAddress = this.command.getnewaddress();
        Address address = Address.builder()
                .address(newAddress)
                .biz(biz)
                .currency(this.getCurrency().getName())
                .userId(userId)
                .index(index).build();
        this.addressService.add(address, table);
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} end", userId, biz, this.getCurrency().name());
        return address;
    }

    @Override
    public boolean checkAddress(String addressStr) {
        try {
            JSONObject res = this.command.validateaddress(addressStr);
            if (res == null) {
                AbstractOnlineBtcWallet.log.error("validateaddress {} is not valid", addressStr);
                return false;
            }
            return res.getBoolean("isvalid");
        } catch (final Exception e) {
            AbstractOnlineBtcWallet.log.error("{} is not valid", addressStr, e);
        }
        return false;
    }


}
