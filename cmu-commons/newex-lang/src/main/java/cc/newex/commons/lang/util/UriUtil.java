package cc.newex.commons.lang.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author newex-team
 * @date 2017/12/9
 */
@Slf4j
public class UriUtil {
    public static String urlDecoder(final String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        final String decUrl = urlDecoder(url, "UTF-8");
        return decUrl == null ? urlDecoder(url, "GBK") : decUrl;
    }

    public static String urlDecoder(final String url, final String enc) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            return URLDecoder.decode(url, enc);
        } catch (final UnsupportedEncodingException e) {
            log.error("url Decoder error,url:{} ", url, e);
        }
        return null;
    }
}
