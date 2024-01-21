package cc.newex.wallet.jobs.deposit.listener;

import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.service.TransactionService;
import cc.newex.wallet.wallet.impl.BchWallet;
import com.google.common.net.InetAddresses;
import lombok.extern.slf4j.Slf4j;
import org.bitcoincashj.core.*;
import org.bitcoincashj.core.listeners.OnTransactionBroadcastListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@Slf4j
@Component
public class ListenBchUnconfirmedTxs {
    @Autowired
    BchWallet bchWallet;

    @Autowired
    TransactionService txService;

    @Value("${newex.app.env.name}")
    private String env;
    @Value("${newex.bch.server}")
    private String bchServer;

    @PostConstruct
    public void init() {
        try {
            if ("dev".equals(this.env)) {
                return;
            }
            ListenBchUnconfirmedTxs.log.info("start listen bch unconfirmed tx");
            NetworkParameters params = this.bchWallet.getBchNetworkParameters();
            PeerGroup peer = new PeerGroup(params);
            peer.addOnTransactionBroadcastListener(new UnconfirmedBchTxListener());
            //peer.addPeerDiscovery(new DnsDiscovery(params));
            String addr = this.bchServer.split("//")[1];
            peer.addAddress(new PeerAddress(params, InetAddresses.forString(addr.split(":")[0]), params.getPort()));

            peer.start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                ListenBchUnconfirmedTxs.log.info("listener quit begin");
                peer.stop();
                ListenBchUnconfirmedTxs.log.info("listener quit");
            }));
        } catch (Exception e) {
            ListenBchUnconfirmedTxs.log.error("start listen bch unconfirmed tx error", e);
        }

    }


    class UnconfirmedBchTxListener implements OnTransactionBroadcastListener {
        @Override
        public void onTransaction(Peer peer, Transaction tx) {

            //ListenBchUnconfirmedTxs.log.info("receive bch unconfirmed tx:{}", tx.getHashAsString());
            try {

                List<TransactionDTO> transactionDTOS = ListenBchUnconfirmedTxs.this.bchWallet.convertFromBchBjTx(tx);
                if (CollectionUtils.isEmpty(transactionDTOS)) {
                    return;
                }
                ListenBchUnconfirmedTxs.log.info("receive bch unconfirmed tx:{}", tx.getHashAsString());
                ListenBchUnconfirmedTxs.this.txService.saveTransaction(transactionDTOS);

            } catch (Throwable e) {
                ListenBchUnconfirmedTxs.log.error("receive unconfirmed tx error, tx:{}", tx.getHashAsString(), e);
            }


        }
    }
}
