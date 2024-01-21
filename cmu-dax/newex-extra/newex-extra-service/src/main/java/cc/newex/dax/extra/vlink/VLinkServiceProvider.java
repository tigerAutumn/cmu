package cc.newex.dax.extra.vlink;

import cc.newex.dax.extra.common.config.VlinkConfig;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import cc.newex.dax.extra.common.util.EncryptUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author gi
 * @date 10/23/18
 */
@Slf4j
@Component
public class VLinkServiceProvider implements VlinkServer {
    @Autowired
    VlinkConfig vlinkConfig;

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private static final String ACTIVITY_KEY = "VLINK_CONTRACT_TRANSACTION";

    public Long getVlinkUserId() {
        return vlinkConfig.getUserId();
    }

    public String getActivityKey() {
        return ACTIVITY_KEY;
    }

    public String getDomain(){
        return vlinkConfig.getDomain();
    }

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static String encodeHeadInfo(String headInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

    private static Request getInstance(String url,VlinkConfig vlinkConfig) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        log.info(System.currentTimeMillis() + "---");
        return new Request.Builder()
                .url(vlinkConfig.getDomain() + url)
                .removeHeader(encodeHeadInfo("ACCESS-PASSPHRASE"))
                .header(encodeHeadInfo("ACCESS-KEY"), encodeHeadInfo(vlinkConfig.getAppKey()))
                .addHeader(encodeHeadInfo("ACCESS-TIMESTAMP"), timeStamp)
                .addHeader(encodeHeadInfo("ACCESS-SIGN"), EncryptUtils.hmacSHA256(vlinkConfig.getSecretKey(), timeStamp))
                .build();

    }

    private static Request postInstance(String url, String message, RequestBody body,VlinkConfig vlinkConfig) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        return new Request.Builder()
                .url(vlinkConfig.getDomain()  + url)
                .post(body)
                .removeHeader(encodeHeadInfo("ACCESS-PASSPHRASE"))
                .addHeader(encodeHeadInfo("ACCESS-KEY"), encodeHeadInfo(vlinkConfig.getAppKey()))
                .addHeader(encodeHeadInfo("ACCESS-TIMESTAMP"), timeStamp)
                .addHeader(encodeHeadInfo("ACCESS-SIGN"), EncryptUtils.hmacSHA256(vlinkConfig.getSecretKey(), timeStamp + message))
                .build();
    }

    @Override
    public JSONObject contractList(Map<String, Object> params) {
        Number page = (Number) params.get("page");
        Number size = (Number) params.get("size");

        String url;
        if (params.containsKey("status")) {
            String status = params.get("status").toString();
            url = encodeHeadInfo("/third-party/contract/all?status=" + status + "&page=" + page + "&size=" + size);
        } else {
            url = encodeHeadInfo("/third-party/contract/all?page=" + page + "&size=" + size);
        }
        Request request = getInstance(url,vlinkConfig);
        JSONObject jsonObject = responseJsonString(request);
        log.info(jsonObject.toJSONString());
        return jsonObject;
    }

    @Override
    public JSONObject contractInfo(Map<String, Object> params) {
        Number contractId = (Number) params.get("contractId");
        String url = encodeHeadInfo("/third-party/contract/" + contractId);
        Request request = getInstance(url,vlinkConfig);
        JSONObject jsonObject = responseJsonString(request);
        log.info(jsonObject.toJSONString());
        return jsonObject;
    }

    @Override
    public JSONObject getOrder(Map<String, Object> params) {
        String email = params.get("email").toString();
        Number contractId = (Number) params.get("contractId");
        Number page = (Number) params.get("page");
        Number size = (Number) params.get("size");
        String url = "/third-party/order/all?email=" + email + "&contractId=" + contractId + "&page=" + page + "size=" + size;
        Request request = getInstance(url,vlinkConfig);
        JSONObject jsonObject = responseJsonString(request);
        log.info(jsonObject.toJSONString());
        return jsonObject;
    }

    @Override
    public JSONObject orderPreview(Map<String, Object> params) {
        String url = "/third-party/order/action/preview";
        String jsonParams = JSON.toJSONString(params);
        log.info(jsonParams);
        RequestBody body = RequestBody.create(MEDIA_TYPE, jsonParams);
        Request request = postInstance(url, jsonParams, body,vlinkConfig);
        JSONObject jsonObject = responseJsonString(request);
        log.info(jsonObject.toJSONString());
        return jsonObject;
    }

    @Override
    public JSONObject orderSubmit(Map<String, Object> params) {
        String url = "/third-party/order/action/submit";
        String jsonParams = JSON.toJSONString(params);
        RequestBody body = RequestBody.create(MEDIA_TYPE, jsonParams);
        Request request = postInstance(url, jsonParams, body,vlinkConfig);
        JSONObject jsonObject = responseJsonString(request);
        log.info(jsonObject.toJSONString());
        return jsonObject;
    }

    @Override
    public JSONObject getEarning(Map<String, Object> params) {
        String email = params.get("email").toString();
        Number contractId = (Number) params.get("contractId");
        Number page = (Number) params.get("page");
        Number size = (Number) params.get("size");
        String url = "/third-party/earning/all?email=" + email + "&contractId=" + contractId + "&page=" + page + "size=" + size;
        Request request = getInstance(url,vlinkConfig);
        JSONObject jsonObject = responseJsonString(request);
        log.info(jsonObject.toJSONString());
        return jsonObject;
    }

    @Override
    public String getUserAuth(Map<String, Object> params) {
        String url = "/third-party/account/action/login";
        String jsonParams = JSON.toJSONString(params);
        RequestBody body = RequestBody.create(MEDIA_TYPE, jsonParams);
        Request request = postInstance(url, jsonParams, body,vlinkConfig);
        JSONObject jsonObject = responseJsonString(request);
        return jsonObject.get("authorization").toString();
    }

    @Override
    public String withDraw(Map<String, Object> params) {
        String authorization = params.get("authorization").toString();
        String url = vlinkConfig.getWalletUrl();
       return url + "authorization=" + authorization;
    }

    private static JSONObject responseJsonString(Request request) {
        log.info("----http request-------" + request.url().query());
        log.info("ACCESS-KEY:" + request.header(encodeHeadInfo("ACCESS-KEY")));
        log.info("ACCESS-TIMESTAMP:" + request.header(encodeHeadInfo("ACCESS-TIMESTAMP")));
        log.info("ACCESS-SIGN:" + request.header(encodeHeadInfo("ACCESS-SIGN")));
        Response response;
        String bodyString;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            ResponseBody body = response.body();
            bodyString = body != null ? body.string() : "";
        } catch (Exception ex) {
            log.info(ExtraBizErrorCodeEnum.VENDOR_SERVER_ERROR.toString());
            throw new RuntimeException(ex);
        }

        if (!response.isSuccessful()) {
            throw new RuntimeException(String.format("http error : code=%s,body=%s", response.code(), bodyString));
        }
        return JSON.parseObject(bodyString);
    }

}
