package cc.newex.maker.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wj on 2018/4/27.
 */
@Slf4j
public class SignUtil {
    public static String getOkSign(final Map<String, String> params, final String secretKey) {
        String paramStr = getParamString(params);
        if (StringUtils.isNotBlank(paramStr)) {
            paramStr = paramStr + "&secret_key=" + secretKey;
        }
        return MD5Util.getMD5String(paramStr);
    }

    public static String getParamString(final Map<String, String> params) {
        final List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        final StringBuilder kvs = new StringBuilder();
        if (!keys.isEmpty()) {
            for (final String key : keys) {
                kvs.append(key).append("=").append(params.get(key)).append("&");
            }
            kvs.setLength(kvs.length() - 1);
        }
        return kvs.toString();
    }

    public static String createSign(final Map<String, String> mapParams, final String method, final String host, final String requestPath, final String secretKey) {
        final TreeMap<String, String> sortedParams = mapSortByKey(mapParams);
        final TreeMap<String, String> encodeParams = mapValueEncodeURI(sortedParams);
        final String strParams = getParamString(encodeParams);
        final String strPayload = method + "\n" + host + "\n" + requestPath + "\n" + strParams;
        return hmac256(secretKey, strPayload);
    }

    private static TreeMap<String, String> mapSortByKey(final Map<String, String> mapParams) {
        final TreeMap<String, String> finalMap = new TreeMap<>();
        mapParams.entrySet().stream()
                .sorted(Map.Entry.<String, String>comparingByKey()
                        .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        return finalMap;
    }

    public static TreeMap<String, String> mapValueEncodeURI(final TreeMap<String, String> mapParams) {
        final TreeMap<String, String> finalMap = new TreeMap<>();
        for (final Map.Entry<String, String> entry : mapParams.entrySet()) {
            try {
                finalMap.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (final UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return finalMap;
    }

    public static String hmac256(final String secretKey, final String message) {
        try {
            final Mac sha256 = Mac.getInstance("HmacSHA256");

            final SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");

            sha256.init(keySpec);

            return Base64.encodeBase64String(sha256.doFinal(message.getBytes()));
        } catch (final NoSuchAlgorithmException | InvalidKeyException e) {
            log.info(e.getMessage(), e);
        }

        return null;
    }
}
