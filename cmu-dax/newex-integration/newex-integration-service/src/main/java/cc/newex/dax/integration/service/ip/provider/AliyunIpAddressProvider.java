package cc.newex.dax.integration.service.ip.provider;

import cc.newex.dax.integration.domain.ip.IpLocation;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author newex-team
 * @date 2018-06-05
 */
@Slf4j
@Service
public class AliyunIpAddressProvider extends AbstractIpAddressProvider implements IpAddressProvider {

    private String url;
    private String appCode;
    private Map<String, Object> options;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public String getName() {
        return "aliyun-ip";
    }

    @Override
    public void setOptions(final Map<String, Object> options) {
        this.options = options;
        this.url = MapUtils.getString(this.options, "url", "");
        this.appCode = MapUtils.getString(this.options, "appCode", "");
    }

    @Override
    public IpLocation getIpLocation(final String ipAddress) {
        final Request request = new Request.Builder()
                .url(this.url + "?ip=" + ipAddress)
                .header("Authorization", "APPCODE " + this.appCode)
                .build();

        int code = -1;
        String bodyString = null;
        JSONObject jsonObject = null;

        try (final Response response = this.okHttpClient.newCall(request).execute()) {
            code = response.code();

            if (response == null || code != HttpStatus.SC_OK) {
                throw new RuntimeException(String.format("http error : code=%s, body=%s", response.code(), bodyString));
            }

            bodyString = response.body().string();

            if (StringUtils.isNotBlank(bodyString)) {
                jsonObject = JSON.parseObject(bodyString);
            }
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }

        if (jsonObject == null) {
            throw new RuntimeException(String.format("http error : code=%s, body=%s", code, bodyString));
        }

        if (jsonObject.getInteger("code") != 0) {
            throw new RuntimeException(String.format("http error : code=%s, body=%s", code, bodyString));
        }

        return jsonObject.getObject("data", IpLocation.class);
    }
}
