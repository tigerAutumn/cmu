package cc.newex.wallet.task;

import cc.newex.wallet.annotation.StartThread;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.service.SignDataService;
import cc.newex.wallet.signature.api.ITransactionSignService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

import static cc.newex.wallet.currency.CurrencyEnum.IOTA;

/**
 * 主要针对一些没有公钥或者不能通过公钥生成地址的币种（比如iota）
 * 检查一些币种是否缺少地址。
 *
 * @author newex-team
 * @data 01/04/2018
 */
@Slf4j
@Component
@StartThread
public class CheckNeedAddressJob implements Runnable {

    private final long FIVE_MINUTES = 5 * 60 * 1000L;

    @Autowired
    private ITransactionSignService signService;

    @Autowired
    private SignDataService dataService;

    private final Set<CurrencyEnum> needCheckAddress = Sets.immutableEnumSet(IOTA);

    @Override
    public void run() {

        while (true) {
            try {
                log.info("CheckNeedAddressJob begin");

                for (final CurrencyEnum currency : this.needCheckAddress) {
                    final JSONObject res = this.dataService.getNeedAddress(currency.getName());

                    if (!CollectionUtils.isEmpty(res)) {
                        final int count = res.getIntValue("count");
                        if (count > 1000) {
                            continue;
                        }
                        final List<String> addresses = this.signService.generateNeedAddress(res);
                        this.dataService.postNeedAddress(currency.getName(), addresses);
                    }
                }

                log.info("CheckNeedAddressJob end");
            } catch (final Throwable e) {
                log.error("CheckNeedAddressJob error", e);
            }

            try {
                Thread.sleep(this.FIVE_MINUTES);
            } catch (final InterruptedException e) {
                log.error("CheckNeedAddressJob quit", e);
                break;
            }

        }

    }
}
