package cc.newex.wallet.wallet.impl;

import cc.newex.wallet.command.SSCCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.wallet.AbstractActLikeWallet;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 2018/5/10
 */
@Slf4j
@Component
public class SSCWallet extends AbstractActLikeWallet {
    @Autowired
    SSCCommand command;
    @Value("${newex.ssc.withdraw.address}")
    private String sscWithdrawAddress;

    @Value("${newex.ssc.wallet.name}")
    private String walletName;

    @Value("${newex.ssc.wallet.password}")
    private String walletPassword;

    @Value("${newex.ssc.account.name}")
    private String accountName;

    @PostConstruct
    public void init() {
        super.setCommand(this.command);
        super.setWithdrawAddress(this.sscWithdrawAddress);
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.SSC;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.SSC.getDecimal();
    }

    @Override
    public String getTxId(final WithdrawTransaction transaction) {
        final JSONObject signature = JSONObject.parseObject(transaction.getSignature());
        String raw = signature.getString("rawTransaction");
        JSONObject rawTx = JSONObject.parseObject(raw);
        return rawTx.getString("hash");
    }
}
