package cc.newex.dax.market.spider.common.util.orepool;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
public class CustomHostNameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(final String s, final SSLSession sslSession) {
        return false;
    }
}
