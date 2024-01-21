package cc.newex.openapi.bitmex.v1;

import cc.newex.maker.http.OkHttpClientUtils;
import cc.newex.maker.http.ParamPair;
import cc.newex.maker.perpetual.enums.AccountEnum;
import cc.newex.openapi.bitmex.domain.BitMexBaseInfo;
import cc.newex.openapi.bitmex.domain.BitMexOrderBook;
import cc.newex.openapi.bitmex.domain.BitMexPosition;
import cc.newex.openapi.bitmex.domain.BitMexTrade;
import cc.newex.openapi.bitmex.domain.BitMexWalletInfo;
import cc.newex.openapi.bitmex.enums.BitMexOrderTypeEnum;
import cc.newex.openapi.bitmex.enums.BitMexSideEnum;
import cc.newex.openapi.cmx.perpetual.domain.DepthOrderBook;
import cc.newex.openapi.cmx.perpetual.enums.OrderSideEnum;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liutiejun
 * @date 2019-04-24
 */
@Slf4j
public class BitMexV1RestApiImpl implements BitMexV1RestApi {

    @Override
    public DepthOrderBook depth(final String code, Integer depth) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        if (depth == null || depth < 0) {
            depth = 25;
        }

        // GET: https://testnet.bitmex.com/api/v1/orderBook/L2?symbol=XBTUSD&depth=25
        final String url = BitMexBaseInfo.BASE_URL + "/api/v1/orderBook/L2";

        final Map<String, String> paramMap = new HashMap<>();
        paramMap.put("symbol", code);
        paramMap.put("depth", String.valueOf(depth));

        final String response = OkHttpClientUtils.get(url, paramMap, BitMexBaseInfo.HEAD_MAP);

        final List<BitMexOrderBook> bitMexOrderBookList = JSON.parseArray(response, BitMexOrderBook.class);
        if (CollectionUtils.isEmpty(bitMexOrderBookList)) {
            return null;
        }

        final List<BitMexOrderBook> sellList = new ArrayList<>();
        final List<BitMexOrderBook> buyList = new ArrayList<>();

        bitMexOrderBookList.forEach(bitMexOrderBook -> {
            final String side = bitMexOrderBook.getSide();
            final BitMexSideEnum sideEnum = BitMexSideEnum.getBySideName(side);

            if (BitMexSideEnum.BUY.equals(sideEnum)) {
                buyList.add(bitMexOrderBook);
            } else if (BitMexSideEnum.SELL.equals(sideEnum)) {
                sellList.add(bitMexOrderBook);
            }
        });

        // 卖单，价格从低到高
        final List<String[]> asks = sellList.stream()
                .sorted(Comparator.comparing(BitMexOrderBook::getPrice))
                .map(bitMexOrderBook -> this.toDepth(bitMexOrderBook))
                .collect(Collectors.toList());

        // 买单，价格从高到低
        final List<String[]> bids = buyList.stream()
                .sorted(Comparator.comparing(BitMexOrderBook::getPrice).reversed())
                .map(bitMexOrderBook -> this.toDepth(bitMexOrderBook))
                .collect(Collectors.toList());

        final DepthOrderBook orderBook = new DepthOrderBook();
        orderBook.setAsks(asks);
        orderBook.setBids(bids);

        return orderBook;
    }

    private String[] toDepth(final BitMexOrderBook bitMexOrderBook) {
        final String size = bitMexOrderBook.getSize().stripTrailingZeros().toPlainString();
        final String price = bitMexOrderBook.getPrice().stripTrailingZeros().toPlainString();

        return new String[]{price, size};
    }

    @Override
    public List<BitMexTrade> historyTrade(final String code, Integer count) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        if (count == null || count < 0) {
            count = 100;
        }

        // GET: https://testnet.bitmex.com/api/v1/trade?symbol=XBTUSD&count=100&reverse=true
        final String url = BitMexBaseInfo.BASE_URL + "/api/v1/trade";

        final Map<String, String> paramMap = new HashMap<>();
        paramMap.put("symbol", code);
        paramMap.put("count", String.valueOf(count));
        paramMap.put("reverse", Boolean.TRUE.toString());

        final String response = OkHttpClientUtils.get(url, paramMap, BitMexBaseInfo.HEAD_MAP);

        return JSON.parseArray(response, BitMexTrade.class);
    }

    @Override
    public String createOrder(final AccountEnum account, final String code, final OrderSideEnum side, final Integer amount, final Double price) {
        final List<ParamPair> queryParams = new ArrayList<>();
        queryParams.add(new ParamPair("symbol", code));
        queryParams.add(new ParamPair("side", BitMexSideEnum.parseCmxSide(side).getSideName()));
        queryParams.add(new ParamPair("orderQty", String.valueOf(amount)));

        if (price == null || price <= 0) {
            // 市价单
            queryParams.add(new ParamPair("ordType", BitMexOrderTypeEnum.MARKET.getType()));
        } else {
            // 限价单
            queryParams.add(new ParamPair("price", String.valueOf(price)));
            queryParams.add(new ParamPair("ordType", BitMexOrderTypeEnum.LIMIT.getType()));
        }

        final String apiKey = account.getApiKey();
        final String secretKey = account.getSecretKey();

        final String method = "POST";
        final String path = "/api/v1/order";

        final Map<String, String> signHeaders = this.buildSignHeaders(apiKey, secretKey, path, method, queryParams);

        final String url = BitMexBaseInfo.BASE_URL + path;

        final String response = OkHttpClientUtils.post(url, queryParams, signHeaders);

        log.info("create bitmex order success, code: {}, amount: {}, price: {}, side: {}, response: {}", code, amount, price, side, response);

        return response;
    }

    /**
     * 生成签名参数
     *
     * @param apiKey
     * @param secretKey
     * @param path
     * @return
     */
    private Map<String, String> buildSignHeaders(final String apiKey, final String secretKey, final String path, final String method, final List<ParamPair> queryParams) {
        final Map<String, String> siganParamMap = new HashMap<>();

        final Long expires = this.getExpires();

        // A UNIX timestamp after which the request is no longer valid. This is to prevent replay attacks.
        siganParamMap.put("api-expires", String.valueOf(expires));

        siganParamMap.put("api-key", apiKey);

        // A signature of the request you are making.
        // It is calculated as hex(HMAC_SHA256(apiSecret, verb + path + expires + data)).
        final String sign = this.createSign(secretKey, method, path, expires, queryParams);
        siganParamMap.put("api-signature", sign);

        return siganParamMap;
    }

    private Long getExpires() {
        final Date expires = DateUtils.addMinutes(new Date(), 5);

        return expires.getTime() / 1000;
    }

    /**
     * A signature of the request you are making.
     * It is calculated as hex(HMAC_SHA256(apiSecret, verb + path + expires + data)).
     *
     * @param secretKey
     * @param path
     * @param method
     * @param queryParams
     * @return
     */
    private String createSign(final String secretKey, final String method, final String path, final Long expires, final List<ParamPair> queryParams) {
        final String data = OkHttpClientUtils.buildQueryString(queryParams);

        return createSign(secretKey, method, path, expires, data);
    }

    public static String createSign(final String secretKey, final String method, final String path, final Long expires, String data) {
        try {
            final Mac sha256 = Mac.getInstance("HmacSHA256");
            final SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            sha256.init(keySpec);

            if (StringUtils.isBlank(data)) {
                data = "";
            }

            final String toSign = method + path + expires + data;

            return Hex.encodeHexString(sha256.doFinal(toSign.getBytes("UTF-8")));
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public String getUserInfo(final AccountEnum account) {
        final List<ParamPair> queryParams = new ArrayList<>();

        final String apiKey = account.getApiKey();
        final String secretKey = account.getSecretKey();

        final String method = "GET";
        final String path = "/api/v1/user";

        final Map<String, String> signHeaders = this.buildSignHeaders(apiKey, secretKey, path, method, queryParams);

        final String url = BitMexBaseInfo.BASE_URL + path;

        final String response = OkHttpClientUtils.get(url, queryParams, signHeaders);

        log.info("getUserInfo success, response: {}", response);

        return response;
    }

    @Override
    public BitMexWalletInfo getWalletInfo(final AccountEnum account, final String currency) {
        if (StringUtils.isBlank(currency)) {
            return null;
        }

        final List<ParamPair> queryParams = new ArrayList<>();
        queryParams.add(new ParamPair("currency", currency));

        final String queryString = OkHttpClientUtils.buildQueryString(queryParams);

        final String apiKey = account.getApiKey();
        final String secretKey = account.getSecretKey();

        final String method = "GET";
        String path = "/api/v1/user/wallet";
        if (StringUtils.isNotBlank(queryString)) {
            path = path + "?" + queryString;
        }

        final Map<String, String> signHeaders = this.buildSignHeaders(apiKey, secretKey, path, method, null);

        final String url = BitMexBaseInfo.BASE_URL + path;

        final String response = OkHttpClientUtils.get(url, signHeaders);

        log.info("getWalletInfo success, response: {}", response);

        return JSON.parseObject(response, BitMexWalletInfo.class);
    }

    @Override
    public List<BitMexPosition> getPositionInfo(final AccountEnum account, final String code) {
        final List<ParamPair> queryParams = this.generateFilter(code);
        final String queryString = OkHttpClientUtils.buildQueryString(queryParams);

        final String apiKey = account.getApiKey();
        final String secretKey = account.getSecretKey();

        final String method = "GET";
        String path = "/api/v1/position";
        if (StringUtils.isNotBlank(queryString)) {
            path = path + "?" + queryString;
        }

        final Map<String, String> signHeaders = this.buildSignHeaders(apiKey, secretKey, path, method, null);

        final String url = BitMexBaseInfo.BASE_URL + path;

        final String response = OkHttpClientUtils.get(url, signHeaders);

        log.info("getPositionInfo success, response: {}", response);

        return JSON.parseArray(response, BitMexPosition.class);
    }

    private List<ParamPair> generateFilter(final String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        final Map<String, String> filter = Maps.newHashMap();

        // {"symbol": "XBTUSD"}
        filter.put("symbol", code);

        final List<ParamPair> queryParams = new ArrayList<>();
        queryParams.add(new ParamPair("filter", JSON.toJSONString(filter)));

        return queryParams;
    }

    @Override
    public String getOrderInfo(final AccountEnum account, final String code, final Integer count) {
        final List<ParamPair> queryParams = new ArrayList<>();
        queryParams.add(new ParamPair("symbol", code));
        queryParams.add(new ParamPair("count", String.valueOf(count)));
        queryParams.add(new ParamPair("reverse", Boolean.TRUE.toString()));

        final String queryString = OkHttpClientUtils.buildQueryString(queryParams);

        final String apiKey = account.getApiKey();
        final String secretKey = account.getSecretKey();

        final String method = "GET";
        String path = "/api/v1/order";
        if (StringUtils.isNotBlank(queryString)) {
            path = path + "?" + queryString;
        }

        final Map<String, String> signHeaders = this.buildSignHeaders(apiKey, secretKey, path, method, null);

        final String url = BitMexBaseInfo.BASE_URL + path;

        final String response = OkHttpClientUtils.get(url, signHeaders);

        log.info("getOrderInfo success, response: {}", response);

        return response;
    }
}
