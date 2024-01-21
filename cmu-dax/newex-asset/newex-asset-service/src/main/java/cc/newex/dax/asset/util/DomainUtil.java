package cc.newex.dax.asset.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class DomainUtil {

    public static String getDomain(HttpServletRequest request) {
        URL url = null;
        try {
            url = new URL(request.getRequestURL().toString());
        } catch (MalformedURLException e) {
            return null;
        }
        String domain = url.getHost();
        log.info("domain={}", domain);
        return domain;
    }
}
