package cc.newex.dax.market.spider.jobs;

import cc.newex.dax.market.spider.common.config.UrlConfig;
import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.common.enums.CoinNameEnum;
import cc.newex.dax.market.spider.common.util.DataKey;
import cc.newex.dax.market.spider.common.util.HttpClientUtils;
import cc.newex.dax.market.spider.common.util.anxbtc.AnxbtcUtil;
import cc.newex.dax.market.spider.common.util.bibox.BiBoxUtil;
import cc.newex.dax.market.spider.common.util.binance.BinanceUtil;
import cc.newex.dax.market.spider.common.util.bitcoinde.BitcoinDeUtil;
import cc.newex.dax.market.spider.common.util.bitfinex.BtcFinexUtil;
import cc.newex.dax.market.spider.common.util.bitstamp.BitStampUtil;
import cc.newex.dax.market.spider.common.util.bittrex.BittrexUtil;
import cc.newex.dax.market.spider.common.util.btce.BtceUtil;
import cc.newex.dax.market.spider.common.util.btctrade.BtcTradeUtil;
import cc.newex.dax.market.spider.common.util.coinbase.CoinbaseUtil;
import cc.newex.dax.market.spider.common.util.coinmex.CoinMexUtil;
import cc.newex.dax.market.spider.common.util.exmo.ExmoUtil;
import cc.newex.dax.market.spider.common.util.gateio.GateIoUtil;
import cc.newex.dax.market.spider.common.util.gemini.GEMINIUtil;
import cc.newex.dax.market.spider.common.util.hitbtc.HitbtcUtil;
import cc.newex.dax.market.spider.common.util.huobi.HuobiUtil;
import cc.newex.dax.market.spider.common.util.itbit.ItbitUtil;
import cc.newex.dax.market.spider.common.util.kraken.KrakenUtil;
import cc.newex.dax.market.spider.common.util.okcoin.OKCoinUtil;
import cc.newex.dax.market.spider.common.util.poloniex.Poloniex;
import cc.newex.dax.market.spider.common.util.upbit.UpBitUtil;
import cc.newex.dax.market.spider.common.util.zb.ZbUtil;
import cc.newex.dax.market.spider.domain.FetchingDataPath;
import cc.newex.dax.market.spider.domain.LatestTicker;
import cc.newex.dax.market.spider.domain.ServerUrl;
import cc.newex.dax.market.spider.service.DataToRedisService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时执行
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
@Component
public class TickerJobs {

    @Autowired
    private DataToRedisService dataToRedisService;

    @Autowired
    private DataKey dataKey;

    @Autowired
    private ServerUrl url;

    /**
     * huobi
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 30000)
    public void huobiCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        if (CollectionUtils.isEmpty(fetchingDataPaths)) {
            log.error("fetchingDataPaths is empty");

            return;
        }

        fetchingDataPaths.stream().filter(dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.HUOBI.name
        ))
                .forEach(fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());
                    if (StringUtils.isNotEmpty(content)) {
                        final LatestTicker ticker = HuobiUtil.huoBi(content);
                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }

                });
    }

    /**
     * btce
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void bTCECurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        if (CollectionUtils.isEmpty(fetchingDataPaths)) {
            log.error("fetchingDataPaths is empty");

            return;
        }

        fetchingDataPaths.stream().filter(dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.BTCE.name))
                .forEach(fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());
                    if (StringUtils.isNotEmpty(content)) {
                        final LatestTicker ticker = BtceUtil.btceTickerByJson(content);
                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });
    }

    /**
     * bitstamp
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void bitstampCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        if (CollectionUtils.isEmpty(fetchingDataPaths)) {
            log.error("fetchingDataPaths is empty");

            return;
        }

        fetchingDataPaths.stream().filter(
                dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.BITSTAMP.name)).forEach(
                fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());

                    if (StringUtils.isNotEmpty(content)) {
                        final LatestTicker ticker = BitStampUtil.bitStampBuildTicker(content);

                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });
    }

    /**
     * btcFinex
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void btcFinexCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        if (CollectionUtils.isEmpty(fetchingDataPaths)) {
            log.error("fetchingDataPaths is empty");

            return;
        }

        fetchingDataPaths.stream().filter(
                dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.BTCFINEX.name)).forEach(
                fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());

                    if (StringUtils.isNotEmpty(content)) {
                        final LatestTicker ticker = BtcFinexUtil.btcFinexBuildTicker(content);

                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });
    }

    /**
     * oKCoinCN
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void oKCoinCNCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        if (CollectionUtils.isEmpty(fetchingDataPaths)) {
            log.error("fetchingDataPaths is empty");

            return;
        }

        fetchingDataPaths.stream().filter(
                dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.OKCOINCOM.name)).forEach(
                fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());
                    if (StringUtils.isNotEmpty(content)) {
                        final LatestTicker ticker = OKCoinUtil.oKCoinCNBuildTicker(content);
                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });
    }

    /**
     * okex
     */
    @Scheduled(initialDelay = 1000, fixedRate = 6000)
    public void OKEXCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        if (CollectionUtils.isEmpty(fetchingDataPaths)) {
            log.error("fetchingDataPaths is empty");

            return;
        }

        fetchingDataPaths.stream().filter(
                dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.OKEX.name)).forEach(
                fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());
                    if (StringUtils.isNotEmpty(content)) {
                        final LatestTicker ticker = OKCoinUtil.OKEXBuildTicker(content);
                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });
    }

    /**
     * itBit
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void itBitCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        if (CollectionUtils.isEmpty(fetchingDataPaths)) {
            log.error("fetchingDataPaths is empty");

            return;
        }

        fetchingDataPaths.stream().filter(
                dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.ITBIT.name)).forEach(
                fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());
                    if (StringUtils.isNotEmpty(content)) {
                        final LatestTicker ticker = ItbitUtil.itbitBuildTicker(content);
                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });
    }

    /**
     * kraken
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void krakenCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        if (CollectionUtils.isEmpty(fetchingDataPaths)) {
            log.error("fetchingDataPaths is empty");

            return;
        }

        fetchingDataPaths.stream().filter(
                dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.KRAKEN.name)).forEach(
                fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());
                    if (StringUtils.isNotEmpty(content)) {
                        final LatestTicker ticker = KrakenUtil.krakenBuildTicker(content, fetchingDataPath.getUrlKey());
                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });
    }

    /**
     * CoinBase
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void CoinBaseCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        if (CollectionUtils.isEmpty(fetchingDataPaths)) {
            log.error("fetchingDataPaths is empty");

            return;
        }

        fetchingDataPaths.stream().filter(
                dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.COINBASE.name)).forEach(
                fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());
                    final String keyContent = this.getResult(fetchingDataPath.getUrlKey());
                    final String ovmContent = this.getResult(fetchingDataPath.getOvm());
                    if (StringUtils.isNoneEmpty(content, keyContent, ovmContent)) {
                        final LatestTicker ticker = CoinbaseUtil.coinBaseBuildTicker(content, keyContent, ovmContent);
                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });

    }

    /**
     * Anxbtc
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void anxbtcCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
        fetchingDataPaths.stream().filter(
                dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.ANXBTC.name)).forEach(
                fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());
                    if (StringUtils.isNotEmpty(content)) {
                        final LatestTicker ticker = AnxbtcUtil.anxbtcBuildTicker(content);
                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });

    }

    /**
     * btcTrade
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void btcTradeCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
        fetchingDataPaths.stream().filter(
                dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.BTCTRADE.name)).forEach(
                fetchingDataPath -> {
                    final String content = this.getResult(fetchingDataPath.getPath());
                    if (StringUtils.isNotEmpty(content)) {
                        final LatestTicker ticker = BtcTradeUtil.btcTradeBuildTicker(content);
                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });

    }

    /**
     * bitCoin
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void bitCoinCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
        fetchingDataPaths.stream().filter(
                dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.BITCOIN.name)).forEach(
                fetchingDataPath -> {
                    final String last = BitcoinDeUtil.getJsonContent(fetchingDataPath.getPath());
                    if (StringUtils.isNotEmpty(last)) {
                        final LatestTicker ticker = new LatestTicker();
                        ticker.setLast(new BigDecimal(last));
                        this.setLastTickerInfo(fetchingDataPath, ticker);
                    }
                });
    }

    /**
     * gemini
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void GEMINICurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        fetchingDataPaths.stream()
                .filter(dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.GEMINI.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final LatestTicker ticker = GEMINIUtil.getTicker(result);
                    this.setLastTickerInfo(fetchingDataPath, ticker);
                });
    }

    /**
     * bittrex
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void bittrexCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        fetchingDataPaths.stream()
                .filter(dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.BITTREX.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final LatestTicker ticker = BittrexUtil.getTicker(result);
                    this.setLastTickerInfo(fetchingDataPath, ticker);
                });
    }

    /**
     * poloniex
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 6000)
    public void poloniexCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        fetchingDataPaths.stream()
                .filter(dataPath -> dataPath.getWebName().equalsIgnoreCase(CoinNameEnum.POLONIEX.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final Map<String, LatestTicker> tickers = Poloniex.getTicker(result);
                    fetchingDataPath.setSymbol(15);
                    this.setLastTickerInfo(fetchingDataPath, tickers.get("BTC_XRP"));
                    fetchingDataPath.setSymbol(17);
                    fetchingDataPath.setMarketFrom(146);
                    this.setLastTickerInfo(fetchingDataPath, tickers.get("BTC_DASH"));
                    fetchingDataPath.setSymbol(4);
                    fetchingDataPath.setMarketFrom(147);
                    this.setLastTickerInfo(fetchingDataPath, tickers.get("BTC_ETC"));
                });
    }

    /**
     * hitbtc
     */
    @Scheduled(initialDelay = 1000, fixedRate = 6000)
    public void hitbtcCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        if (CollectionUtils.isEmpty(fetchingDataPaths)) {
            log.error("fetchingDataPaths is empty");

            return;
        }

        fetchingDataPaths.stream()
                .filter(path -> path.getWebName().equalsIgnoreCase(CoinNameEnum.HITBTC.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final LatestTicker ticker = HitbtcUtil.hitbtcBuildTicker(result);
                    this.setLastTickerInfo(fetchingDataPath, ticker);
                });
    }

    /**
     * binance
     */
    @Scheduled(initialDelay = 1000, fixedRate = 6000)
    public void binanceCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
        fetchingDataPaths.stream()
                .filter(path -> path.getWebName().equalsIgnoreCase(CoinNameEnum.BINANCE.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final LatestTicker ticker = BinanceUtil.binanceBuildTicker(result);
                    this.setLastTickerInfo(fetchingDataPath, ticker);
                });
    }

    /**
     * upbit
     */
    @Scheduled(initialDelay = 1000, fixedRate = 6000)
    public void upBitCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
        fetchingDataPaths.stream()
                .filter(path -> path.getWebName().equalsIgnoreCase(CoinNameEnum.UPBIT.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final LatestTicker ticker = UpBitUtil.upBitBuildTicker(result);
                    this.setLastTickerInfo(fetchingDataPath, ticker);
                });
    }

    /**
     * zb
     */
    @Scheduled(initialDelay = 1000, fixedRate = 6000)
    public void zbCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
        fetchingDataPaths.stream()
                .filter(path -> path.getWebName().equalsIgnoreCase(CoinNameEnum.ZB.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final LatestTicker ticker = ZbUtil.zbBuildTicker(result);
                    this.setLastTickerInfo(fetchingDataPath, ticker);
                });
    }

    /**
     * bibox
     */
    @Scheduled(initialDelay = 1000, fixedRate = 6000)
    public void biBoxCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
        fetchingDataPaths.stream()
                .filter(path -> path.getWebName().equalsIgnoreCase(CoinNameEnum.BIBOX.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final LatestTicker ticker = BiBoxUtil.biBoxBuildTicker(result);
                    this.setLastTickerInfo(fetchingDataPath, ticker);
                });
    }

    /**
     * gateio
     */
    @Scheduled(initialDelay = 1000, fixedRate = 6000)
    public void gateioCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
        fetchingDataPaths.stream()
                .filter(path -> path.getWebName().equalsIgnoreCase(CoinNameEnum.GATEIO.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final LatestTicker ticker = GateIoUtil.gateioBuildTicker(result);
                    this.setLastTickerInfo(fetchingDataPath, ticker);
                });
    }

    /**
     * coinmex
     */
    @Scheduled(initialDelay = 1000, fixedRate = 6000)
    public void coinMexCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
        fetchingDataPaths.stream()
                .filter(path -> path.getWebName().equalsIgnoreCase(CoinNameEnum.COINMEX.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final LatestTicker ticker = CoinMexUtil.coinMexBuildTicker(result);
                    this.setLastTickerInfo(fetchingDataPath, ticker);
                });
    }

    /**
     * exmo
     */
    @Scheduled(initialDelay = 1000, fixedRate = 6000)
    public void exmoCurrency() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
        fetchingDataPaths.stream()
                .filter(path -> path.getWebName().equalsIgnoreCase(CoinNameEnum.EXMO.name))
                .forEach(fetchingDataPath -> {
                    final String result = this.getResult(fetchingDataPath.getPath());
                    final LatestTicker ticker = ExmoUtil.buildTicker(fetchingDataPath.getOvm(), result);
                    this.setLastTickerInfo(fetchingDataPath, ticker);
                });
    }

    private String getResult(final String url) {
        String result = "";

        try {
            final HttpClientUtils.Response response = HttpClientUtils.get(url, new HashMap<>());
            result = response.getBody();

            if (response.getCode() == 200) {
                log.info("get result success: {}", url);
            } else {
                log.error("get result error, code: {}, body: {}, url: {}", response.getCode(), result, url);
                result = "";
            }

        } catch (final Exception e) {
            log.error("get result error, url: {}, msg: {}", url, e.getMessage(), e);
        }

        return result;
    }

    private void addTickerToDB(final String path, final LatestTicker ticker) {
        final Map<String, Object> map = new HashMap<>();
        map.put("ticker", JSONObject.toJSONString(ticker));
        map.put("key", this.dataKey.getDataKey());

        try {
            final HttpClientUtils.Response post = HttpClientUtils.post(path, map);

            if (post.getCode() == 200) {
                log.info("addTickerToDB, path: {}, success", path);
            } else {
                log.error("addTickerToDB, path: {}, status: {}, response: {}", path, post.getCode(), post.getBody());
            }

        } catch (final Exception e) {
            log.error("addTickerToDB error, path: {}, msg: {}", path, e.getMessage(), e);
        }
    }

    private void setLastTickerInfo(final FetchingDataPath fetchingDataPath, final LatestTicker ticker) {
        log.info("setLastTickerInfo, fetchingDataPath: {}, ticker: {}", fetchingDataPath, ticker);

        if (ticker != null && ticker.getLast().doubleValue() > 0) {
            ticker.setName(fetchingDataPath.getWebName());
            ticker.setMarketFrom(fetchingDataPath.getMarketFrom());
            ticker.setBaseCurrency(fetchingDataPath.getSymbol());

            this.addTickerToDB(this.url.getServerLocation() + UrlConfig.TICKER_URL,
                    LatestTickerConverter.convertTicker(ticker));
        } else {
            log.info("error ticker: {}, path: {}", ticker, fetchingDataPath.getPath());
        }
    }
}
