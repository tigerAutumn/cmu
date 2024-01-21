package cc.newex.dax.market.spider.common.util.orepool;

import cc.newex.dax.market.spider.domain.MinePoolShareData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 矿池
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
public class OrePoolShareUtil {

    private static final String[] POOL_MODEL_DATE = {"week", "day3", "day"};

    private static final String PROPERTY
            = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 "
            + "Safari/537.36";

    /**
     * 找出想要的数据
     *
     * @param htmlUrl
     * @return
     */
    public static String getHtmlContent(final String htmlUrl) {
        HttpURLConnection connection = null;
        String content = null;
        try {
            final URL url = new URL(htmlUrl);
            HttpsURLConnection.setDefaultHostnameVerifier(new CustomHostNameVerifier());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", OrePoolShareUtil.PROPERTY);
            connection.setConnectTimeout(25000);
            connection.setReadTimeout(35000);
            final InputStream inS = connection.getInputStream();
            if (inS != null && inS.available() > 0) {
                final BufferedReader in = new BufferedReader(new InputStreamReader(inS));
                String line;
                final StringBuilder stringBuilder = new StringBuilder(500);
                synchronized (stringBuilder) {
                    while ((line = in.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    content = stringBuilder.toString();
                }
            }
        } catch (final IOException e) {
            log.error("msg ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return content;
    }

    /**
     * 数据处理
     *
     * @param content
     * @param poolMode
     * @param list
     */
    public static void dataProcess(final String content, final String poolMode, final List<MinePoolShareData> list) {
        final Document document = Jsoup.parse(content);
        final Elements items = document.select("table[class=table table-hover pool-panel-share-table]");
        final Elements prevs = items.select("tr");
        for (int i = 1; i < prevs.size(); i++) {
            final Element p = prevs.get(i);
            final Elements elements = p.children();
            final MinePoolShareData minePoolShareData = new MinePoolShareData();
            minePoolShareData.setPoolMode(poolMode);
            minePoolShareData.setRank(MathUtils.toInteger(elements.get(0).text()));
            minePoolShareData.setName(elements.get(1).text());
            minePoolShareData.setMarketShareOfPool(MathUtils.toDouble(elements.get(2).text()));
            if (ArrayUtils.contains(OrePoolShareUtil.POOL_MODEL_DATE, poolMode)) {
                minePoolShareData.setComputingPower(elements.get(3).text().trim());
                minePoolShareData.setBlockAmount(MathUtils.toInteger(elements.get(4).text()));
                minePoolShareData.setEmptyBlockProportion(MathUtils.toDouble(elements.get(5).text()));
                minePoolShareData.setAvgBlockSize(MathUtils.toLong(elements.get(6).text()));
                minePoolShareData.setAvgBlockMinerFee(MathUtils.toDouble(elements.get(7).text()));
                minePoolShareData.setMinerFeeAndBlockBonusProportion(MathUtils.toDouble(elements.get(8).text()));

            } else {
                minePoolShareData.setBlockAmount(MathUtils.toInteger(elements.get(3).text()));
                minePoolShareData.setEmptyBlockProportion(MathUtils.toDouble(elements.get(4).text()));
                minePoolShareData.setAvgBlockSize(MathUtils.toLong(elements.get(5).text()));
                minePoolShareData.setAvgBlockMinerFee(MathUtils.toDouble(elements.get(6).text()));
                minePoolShareData.setMinerFeeAndBlockBonusProportion(MathUtils.toDouble(elements.get(7).text()));
            }
            list.add(minePoolShareData);
        }
    }

}
