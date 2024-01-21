package cc.newex.wallet.jobs.deposit.listener;

import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.service.TransactionService;
import cc.newex.wallet.wallet.impl.BtcWallet;
import cc.newex.wallet.wallet.impl.UsdtWallet;
import com.google.common.net.InetAddresses;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.core.listeners.OnTransactionBroadcastListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 监听btc和usdt的未确认交易
 *
 * @author newex-team
 * @data 30/03/2018
 */
@Slf4j
@Component
public class ListenBtcUnconfirmedTxs {
//    static {
//        Security.addProvider(new BouncyCastleProvider());
//    }

    @Autowired
    BtcWallet btcWallet;

    @Autowired
    UsdtWallet usdtWallet;

    @Autowired
    TransactionService txService;
    @Value("${newex.app.env.name}")
    private String env;

    @Value("${newex.btc.server}")
    private String btcServer;

    @PostConstruct
    public void init() {
        try {
            if ("dev".equals(this.env)) {
                return;
            }
            ListenBtcUnconfirmedTxs.log.info("start listen btc unconfirmed tx");
            NetworkParameters params = this.btcWallet.getNetworkParameters();
            PeerGroup peer = new PeerGroup(params);
            peer.addOnTransactionBroadcastListener(new UnconfirmedBtcTxListener());
            //peer.addPeerDiscovery(new DnsDiscovery(params));
            String addr = this.btcServer.split("//")[1];
            peer.addAddress(new PeerAddress(params, InetAddresses.forString(addr.split(":")[0]), params.getPort()));
            peer.start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                ListenBtcUnconfirmedTxs.log.info("listener quit begin");
                peer.stop();
                ListenBtcUnconfirmedTxs.log.info("listener quit");
            }));
        } catch (Exception e) {
            ListenBtcUnconfirmedTxs.log.error("start listen btc unconfirmed tx error", e);
        }

    }


    class UnconfirmedBtcTxListener implements OnTransactionBroadcastListener {
        @Override
        public void onTransaction(Peer peer, Transaction tx) {

            //ListenBtcUnconfirmedTxs.log.info("receive btc unconfirmed tx:{}", tx.getHashAsString());
            try {

//                final TransactionDTO omniTx = ListenBtcUnconfirmedTxs.this.usdtWallet.checkOmniTransaction(tx, 0L);
//                if (!ObjectUtils.isEmpty(omniTx)) {
//                    ListenBtcUnconfirmedTxs.log.info("receive usdt unconfirmed tx:{}", tx.getHashAsString());
//
//                    ListenBtcUnconfirmedTxs.this.txService.saveTransaction(omniTx);
//                }

                List<TransactionDTO> transactionDTOS = ListenBtcUnconfirmedTxs.this.btcWallet.convertFromBjTx(tx);
                if (CollectionUtils.isEmpty(transactionDTOS)) {
                    return;
                }
                ListenBtcUnconfirmedTxs.log.info("receive btc unconfirmed tx:{}", tx.getHashAsString());

                ListenBtcUnconfirmedTxs.this.txService.saveTransaction(transactionDTOS);

            } catch (Throwable e) {
                ListenBtcUnconfirmedTxs.log.error("receive unconfirmed tx error, tx:{}", tx.getHashAsString(), e);
            }


        }
    }

}
