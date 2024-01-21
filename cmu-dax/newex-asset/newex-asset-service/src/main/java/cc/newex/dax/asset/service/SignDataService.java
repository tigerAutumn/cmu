package cc.newex.dax.asset.service;

import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author newex-team
 * @data 16/04/2018
 */
@Component
@Slf4j
public class SignDataService {
    private final int count = 20;


    public List<String> getWithdrawTx() {
        final String key = Constants.WALLET_WITHDRAW_SIG_SECOND_KEY;
        final String tmp = Constants.WALLET_WITHDRAW_SIG_SECOND_TMP_KEY;
        List<String> result = new LinkedList<>();
        int tmpCount = this.count;
        //把tmp队列中的数据转移
        while (REDIS.lLen(tmp) > 0) {
            String tx = REDIS.lPop(tmp);
            REDIS.rPush(key, tx);
        }

        while (tmpCount > 0) {
            String tx = REDIS.rPoplPush(key, tmp);

            if (StringUtils.isEmpty(tx)) {
                break;
            }
            result.add(tx);
            tmpCount = tmpCount - 1;
        }
        return result;
    }

    public boolean postWithdrawTx(final List<WithdrawTransaction> withdrawTransactions) {
        final String rKey = Constants.WALLET_WITHDRAW_SIG_DONE_KEY;
        final String tmp = Constants.WALLET_WITHDRAW_SIG_SECOND_TMP_KEY;

        List<String> tmps = REDIS.lRange(tmp, 0, this.count);
        if (tmps.size() != withdrawTransactions.size()) {
            SignDataService.log.error("postWithdrawTx error, size is not equal");
            return false;
        }
        REDIS.del(tmp);
        withdrawTransactions.parallelStream().forEach((tx) -> {
            REDIS.rPush(rKey, JSONObject.toJSONString(tx));
        });

        return true;
    }
}
