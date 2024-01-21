package cc.newex.dax.users.service.faceid.impl;

import cc.newex.commons.lang.util.MD5Util;
import cc.newex.dax.users.common.config.FaceidProperties;
import cc.newex.dax.users.domain.UserKycInfo;
import cc.newex.dax.users.domain.faceid.FaceToken;
import cc.newex.dax.users.service.faceid.FaceidService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liutiejun
 * @date 2018-12-05
 */
@Slf4j
@Service
public class FaceidServiceImpl implements FaceidService {

    @Autowired
    private FaceidProperties faceidProperties;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private final String accept = ContentType.APPLICATION_JSON.getMimeType();

    /**
     * 获得一个用于实名验证的 token（token唯一且只能使用一次）
     *
     * @param userKycInfo
     * @return
     */
    @Override
    public FaceToken getToken(final UserKycInfo userKycInfo) {
        final String bizNo = MD5Util.getMD5String(userKycInfo.getUserId() + userKycInfo.getCardNumber() + System.currentTimeMillis());

        final String returnUrl = this.faceidProperties.getReturnUrl();
        final String notifyUrl = this.faceidProperties.getNotifyUrl();

        log.info("get token, bizNo: {}, returnUrl: {}, notifyUrl: {}", bizNo, returnUrl, notifyUrl);

        final FormBody formBody = new FormBody.Builder()
                .add("api_key", this.faceidProperties.getApiKey())
                .add("api_secret", this.faceidProperties.getApiSecret())
                .add("return_url", returnUrl)
                .add("notify_url", notifyUrl)
                // 有源比对
                .add("comparison_type", "1")
                .add("biz_no", bizNo)
                .add("idcard_mode", "0")
                // 名称
                .add("idcard_name", userKycInfo.getFirstName())
                // 证件号码
                .add("idcard_number", userKycInfo.getCardNumber())
                .build();

        final String url = this.faceidProperties.getTokenUrl();

        final String responseStr = this.post(url, formBody);
        if (StringUtils.isBlank(responseStr)) {
            log.error("get face token error");

            return null;
        }

        final JSONObject jsonObject = JSON.parseObject(responseStr);

        final String token = jsonObject.getString("token");
        if (StringUtils.isBlank(token)) {
            log.error("get face token error: {}", responseStr);

            return null;
        }

        final Long timeUsed = jsonObject.getLong("time_used");
        final String bizId = jsonObject.getString("biz_id");
        final String requestId = jsonObject.getString("request_id");
        final Long expiredTime = jsonObject.getLong("expired_time");

        final FaceToken faceToken = FaceToken.builder()
                .bizNo(bizNo)
                .timeUsed(timeUsed)
                .token(token)
                .bizId(bizId)
                .requestId(requestId)
                .expiredTime(expiredTime)
                .build();

        return faceToken;
    }

    private String post(final String url, final RequestBody body) {
        log.info("post url: {}", url);

        final Request request = new Request.Builder()
                .url(url)
                .header("accept", this.accept)
                .post(body)
                .build();

        try (final Response response = this.okHttpClient.newCall(request).execute()) {
            return response.body().string();
        } catch (final Exception e) {
            log.error("调用Face++ API 异常：" + e.getMessage(), e);
        }

        return null;
    }

}
