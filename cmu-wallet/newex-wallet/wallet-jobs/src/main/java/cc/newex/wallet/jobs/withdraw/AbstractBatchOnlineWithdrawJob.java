package cc.newex.wallet.jobs.withdraw;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.command.OnLineBtcCommand;
import cc.newex.wallet.criteria.WithdrawRecordExample;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.pojo.rpc.TxInput;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @data 01/04/2018
 */
@Slf4j
abstract public class AbstractBatchOnlineWithdrawJob extends AbstractBatchWithdrawJob {

    OnLineBtcCommand command;

    public void setCommand(OnLineBtcCommand com) {
        this.command = com;
    }

    final String rKey = Constants.WALLET_WITHDRAW_SIG_DONE_KEY;

    @Override
    public void execute(final ShardingContext shardingContext) {
        log.info("Batch {} WithdrawJob begin", this.currency.getName());

        try {
            final ShardTable table = ShardTable.builder().prefix(this.currency.getName()).build();
            final WithdrawRecordExample example = new WithdrawRecordExample();
            example.createCriteria().andStatusEqualTo((byte) Constants.WAITING);
            final PageInfo pageInfo = new PageInfo();
            pageInfo.setPageSize(this.COUNT);
            pageInfo.setStartIndex(0);
            pageInfo.setSortItem("id");
            pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);

            while (true) {
                final List<WithdrawRecord> records = this.recordService.getByPage(pageInfo, example, table);
                if (CollectionUtils.isEmpty(records)) {
                    break;
                }
                //构建交易原始信息
                WithdrawTransaction withdrawTransaction = this.buildTransaction(records);
                if (withdrawTransaction == null) {
                    log.error("Batch {} WithdrawJob error, buildTransaction failed", this.currency.getName());
                    break;
                }
                JSONObject signature = JSONObject.parseObject(withdrawTransaction.getSignature());

                //获取input信息
                List<TxInput> txInputs = getTxinputsByTransaction(signature);
                Map<String, BigDecimal> assets = getAssetsByTransaction(signature, withdrawTransaction.getBalance());

                //直接RPC签名，不走离线签名
                String hex = this.command.createrawtransaction(txInputs, assets);
                //签名
                JSONObject res = this.command.signrawtransaction(hex);
                boolean isComplete = res.getBoolean("complete");
                if (!isComplete) {
                    log.error("{} signrawtransaction fail,isComplete:{}, id:{},txid:{}", this.currency.getName(), isComplete, withdrawTransaction.getId(), res.getString("txid"));
                    break;
                }
                String signedhex = res.getString("hex");

                signature.put("valid", true);
                signature.put("rawTransaction", signedhex);
                withdrawTransaction.setSignature(signature.toJSONString());
                //直接放入签完名的key中
                REDIS.rPush(this.rKey, JSONObject.toJSONString(withdrawTransaction));
                log.info("{} buildTransaction success, id:{},txid:{}", this.currency.getName(), withdrawTransaction.getId(), res.getString("txid"));

                //说明数据库中没有等待签名的交易了
                if (records.size() < this.COUNT) {
                    break;
                }
            }
        } catch (final Throwable e) {
            log.error("Batch {} WithdrawJob error", this.currency.getName(), e);
        }


        log.info("Batch {} WithdrawJob end", this.currency.getName());

    }

    public List<TxInput> getTxinputsByTransaction(JSONObject signature) {
        List<TxInput> txInputs = new ArrayList<TxInput>();
        List<UtxoTransaction> utxos = signature.getJSONArray("utxos").toJavaList(UtxoTransaction.class);
        for (UtxoTransaction utxoTransaction : utxos) {
            TxInput txInput = new TxInput();
            txInput.setTxid(utxoTransaction.getTxId());
            txInput.setVout(utxoTransaction.getSeq());
            txInputs.add(txInput);
        }
        return txInputs;
    }

    public Map<String, BigDecimal> getAssetsByTransaction(JSONObject signature, BigDecimal totalAmount) {
        Map<String, BigDecimal> assets = new HashMap<>();
        List<WithdrawRecord> records = signature.getJSONArray("withdraw").toJavaList(WithdrawRecord.class);
        BigDecimal sentAmount = BigDecimal.ZERO;
        for (WithdrawRecord record : records) {
            assets.put(record.getAddress(), record.getBalance());
            sentAmount = sentAmount.add(record.getBalance());
        }
        BigDecimal fee = new BigDecimal("0.0001");
        //计算找零数据
        BigDecimal changeAmount = totalAmount.subtract(sentAmount).subtract(fee);
        String changeAddress = signature.getString("changeAddress");
        assets.put(changeAddress, changeAmount);
        return assets;
    }
}
















