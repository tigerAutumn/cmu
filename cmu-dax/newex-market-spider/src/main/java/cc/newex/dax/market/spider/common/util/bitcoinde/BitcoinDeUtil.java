package cc.newex.dax.market.spider.common.util.bitcoinde;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;

/**
 * bit
 */
@Slf4j
public class BitcoinDeUtil {

    private static final String NAME = "http.protocol.single-cookie-header";

    public static String getJsonContent(final String ticketUrl) {
        String result = null;
        if (ticketUrl != null || !"".equals(ticketUrl.trim())) {
            try {
                final HttpClient client = new HttpClient();
                client.getHttpConnectionManager().getParams().setConnectionTimeout(20 * 1000);
                client.getHttpConnectionManager().getParams().setSoTimeout(20 * 1000);
                client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
                client.getParams().setParameter(NAME, true);
                final GetMethod method = new GetMethod(ticketUrl);
                method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
                method.setRequestHeader("Accept-Language", "zh-cn");
                method.setRequestHeader("User-Agent",
                        "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
                method.setRequestHeader("Keep-Alive", "300");
                method.setRequestHeader("Connection", "Keep-Alive");
                method.setRequestHeader("Cache-Control", "no-cache");

                final int status = client.executeMethod(method);
                if (status == 200) {
                    result = getString(result, method);
                } else {
                    log.error("msg {}", status);
                }
            } catch (final Exception e) {
                log.error("发送请求", e);
            }
        }
        return result;
    }

    private static String getString(String result, final GetMethod method) throws IOException {
        final InputStream resStream = method.getResponseBodyAsStream();
        final BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
        final StringBuffer resBuffer = new StringBuffer();
        String resTemp = "";
        while ((resTemp = br.readLine()) != null) {
            if (resTemp.indexOf("ticker_price") != -1) {
                resBuffer.append(resTemp).append("\n");
                break;
            }
        }
        result = resBuffer.toString();
        if (StringUtils.isNotEmpty(result)) {
            result = new String(result.substring(result.indexOf("€") + 1, result.length() - 10));
        }
        return result.replace(",", "");
    }
}
