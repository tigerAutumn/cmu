package cc.newex;

import cc.newex.maker.conf.Config;
import cc.newex.maker.perpetual.core.DoDepth;
import cc.newex.maker.perpetual.core.DoTrade;
import cc.newex.maker.perpetual.core.FetchDepth;
import cc.newex.maker.perpetual.core.RobotTrade;
import cc.newex.maker.perpetual.enums.AccountEnum;
import cc.newex.maker.perpetual.enums.MakerEnum;
import cc.newex.maker.perpetual.enums.PlatformEnum;
import cc.newex.maker.perpetual.enums.RobotTradeEnum;
import cc.newex.openapi.bitmex.v1.BitMexV1RestApi;
import cc.newex.openapi.bitmex.v1.BitMexV1RestApiImpl;
import cc.newex.openapi.cmx.client.CmxClient;
import cc.newex.openapi.cmx.common.domain.ClientParameter;
import cc.newex.openapi.cmx.common.domain.CmxBaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cmx-sdk-team
 * @date 2018/04/28
 */
@Slf4j
@Component
public class Bootstrapper {

    @Autowired
    private Config config;

    @Resource(name = "commonTaskExecutor")
    private TaskExecutor commonTaskExecutor;

    @Resource(name = "robotTaskExecutor")
    private TaskExecutor robotTaskExecutor;

    @PostConstruct
    public synchronized void start() {
        log.info("init marker maker");

        final Map<AccountEnum, CmxClient> cmxClientMap = this.initCmxClient();

        final Set<String> pairs = new HashSet<>();
        if (CollectionUtils.isNotEmpty(this.config.getPairs())) {
            pairs.addAll(this.config.getPairs());
        }

        this.initFetchDepth(pairs, cmxClientMap);

        this.initRobotTrade(cmxClientMap);
    }

    private Map<AccountEnum, CmxClient> initCmxClient() {
        final Map<AccountEnum, CmxClient> cmxClientMap = new HashMap<>();

        final List<AccountEnum> accountEnumList = AccountEnum.getByPlatform(PlatformEnum.CMX);

        for (final AccountEnum accountEnum : accountEnumList) {
            final ClientParameter clientParameter = ClientParameter.builder()
                    .apiKey(accountEnum.getApiKey())
                    .secretKey(accountEnum.getSecretKey())
                    .passphrase(accountEnum.getPassphrase())
                    .baseUrl(CmxBaseInfo.BASE_URL)
                    .timeout(CmxBaseInfo.TIME_OUT)
                    .build();

            final CmxClient cmxClient = CmxClient.builder().configuration(clientParameter).build();

            cmxClientMap.put(accountEnum, cmxClient);
        }

        return cmxClientMap;
    }

    private void initRobotTrade(final Map<AccountEnum, CmxClient> cmxClientMap) {
        if (MapUtils.isEmpty(cmxClientMap)) {
            log.error("CmxClient is null ");

            return;
        }

        for (final RobotTradeEnum robotTradeEnum : RobotTradeEnum.values()) {
            final AccountEnum accountEnum = robotTradeEnum.getAccountEnum();

            final CmxClient cmxClient = cmxClientMap.get(accountEnum);

            final RobotTrade robotTrade = new RobotTrade(robotTradeEnum, cmxClient);

            this.robotTaskExecutor.execute(robotTrade::trade);
        }
    }

    private void initFetchDepth(final Set<String> pairs, final Map<AccountEnum, CmxClient> cmxClientMap) {
        if (CollectionUtils.isEmpty(pairs)) {
            log.error("pairs is empty");

            return;
        }

        if (MapUtils.isEmpty(cmxClientMap)) {
            log.error("CmxClient is null");

            return;
        }

        for (final String pair : pairs) {
            this.initFetchDepth(pair, cmxClientMap);
        }
    }

    private void initFetchDepth(final String pair, final Map<AccountEnum, CmxClient> cmxClientMap) {
        log.info("init marker maker, pair code: {}", pair);

        final MakerEnum makerEnum = MakerEnum.valueOf(pair.toUpperCase());
        if (makerEnum == null) {
            log.info("pair code not exist: {}", pair);
            return;
        }

        final AccountEnum cmxDepthAccount = makerEnum.getAccountGroupEnum().getCmxDepthAccount();
        final AccountEnum cmxTradeAccount = makerEnum.getAccountGroupEnum().getCmxTradeAccount();

        final CmxClient cmxClient = cmxClientMap.get(cmxDepthAccount);
        final CmxClient robotCmxClient = cmxClientMap.get(cmxTradeAccount);

        final BitMexV1RestApi bitMexV1RestApi = new BitMexV1RestApiImpl();

        final DoTrade doTrade = new DoTrade(makerEnum, robotCmxClient, bitMexV1RestApi);
        final DoDepth doDepth = new DoDepth(makerEnum, cmxClient, bitMexV1RestApi);

        final FetchDepth fetchDepth = new FetchDepth(this.commonTaskExecutor, makerEnum, doTrade, doDepth,
                robotCmxClient, bitMexV1RestApi);

        fetchDepth.execute();
    }

}
