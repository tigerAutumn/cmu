package cc.newex.wallet.jobs.deposit.listener;

import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.service.TransactionService;
import cc.newex.wallet.wallet.impl.LtcWallet;
import com.google.common.net.InetAddresses;
import lombok.extern.slf4j.Slf4j;
import org.litecoinj.core.*;
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
public class ListenLtcUnconfirmedTxs {
    @Autowired
    LtcWallet ltcWallet;

    @Autowired
    TransactionService txService;
    @Value("${newex.app.env.name}")
    private String env;

    @Value("${newex.ltc.server}")
    private String ltcServer;
    @PostConstruct
    public void init() {
        try {
            if ("dev".equals(this.env)) {
                return;
            }
            ListenLtcUnconfirmedTxs.log.info("start listen ltc unconfirmed tx");
            NetworkParameters params = this.ltcWallet.getLiteNetworkParameters();
            PeerGroup peer = new PeerGroup(params);
            peer.addEventListener(new UnconfirmedLtcTxListener());
            //peer.addPeerDiscovery(new DnsDiscovery(params));
            String addr = this.ltcServer.split("//")[1];

            peer.addAddress(new PeerAddress(InetAddresses.forString(addr.split(":")[0]), params.getPort()));

            peer.startAsync();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                ListenLtcUnconfirmedTxs.log.info("listener quit begin");
                peer.stopAsync();
                ListenLtcUnconfirmedTxs.log.info("listener quit");
            }));
        } catch (Exception e) {
            ListenLtcUnconfirmedTxs.log.error("start listen ltc unconfirmed tx error", e);
        }

    }


    class UnconfirmedLtcTxListener extends AbstractPeerEventListener {
        @Override
        public void onTransaction(Peer peer, Transaction tx) {

            //ListenLtcUnconfirmedTxs.log.info("receive ltc unconfirmed tx:{}", tx.getHashAsString());
            try {


                List<TransactionDTO> transactionDTOS = ListenLtcUnconfirmedTxs.this.ltcWallet.convertFromLtcBjTx(tx);
                if (CollectionUtils.isEmpty(transactionDTOS)) {
                    return;
                }
                ListenLtcUnconfirmedTxs.log.info("receive ltc unconfirmed tx:{}", tx.getHashAsString());
                ListenLtcUnconfirmedTxs.this.txService.saveTransaction(transactionDTOS);

            } catch (Throwable e) {
                ListenLtcUnconfirmedTxs.log.error("receive unconfirmed tx error, tx:{}", tx.getHashAsString(), e);
            }


        }
    }
}
