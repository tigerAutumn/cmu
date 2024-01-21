package cc.newex.dax.integration.service.bank.provider;

import cc.newex.dax.integration.domain.bank.BankInfo;
import cc.newex.dax.integration.domain.bank.BankInfoBO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author newex-team
 * @date 2018-06-05
 */
@Slf4j
@Service
public class AliyunBankInfoProvider extends AbstractBankInfoProvider implements BankInfoProvider {

    private String url;
    private String appCode;
    private String referer;
    private Map<String, Object> options;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public String getName() {
        return "aliyun-bank";
    }

    @Override
    public void setOptions(final Map<String, Object> options) {
        this.options = options;
        this.url = MapUtils.getString(this.options, "url", "");
        this.appCode = MapUtils.getString(this.options, "appCode", "");
        this.referer = MapUtils.getString(this.options, "referer", "");
    }

    @Override
    public BankInfo getBankInfo(final BankInfoBO bo) {
        final StringBuilder url2 = new StringBuilder(this.url);
        url2.append("?")
                .append("Mobile").append("=").append(bo.getMobile() == null ? "" : bo.getMobile()).append("&")
                .append("bankcard").append("=").append(bo.getBankcard()).append("&")
                .append("cardNo").append("=").append(bo.getCardNo()).append("&")
                .append("realName").append("=").append(bo.getRealName());

        Request request = null;

        if (StringUtils.isBlank(this.referer)) {
            request = new Request.Builder().url(url2.toString())
                    .header("Authorization", "APPCODE " + this.appCode)
                    .get().build();
        } else {
            request = new Request.Builder().url(url2.toString())
                    .header("Authorization", "APPCODE " + this.appCode)
                    .header("referer", this.referer)
                    .get().build();
        }

        JSONObject object = null;

        try (final Response response = this.okHttpClient.newCall(request).execute()) {
            if (response == null) {
                log.error("getBankInfo failed, response is empty, bankInfo : {}", bo);

                return null;
            }

            object = JSON.parseObject(response.body().string());
        } catch (final Exception e) {
            log.error("getBankInfo is error: " + e.getMessage(), e);
        }

        if (object == null) {
            log.error("getBankInfo failed, not match, bankInfo : {}, responseBody : {}", bo, object);

            return null;
        }

        final int errorCode = object.getInteger("error_code");
        if (errorCode != 0) {
            log.error("getBankInfo failed, not match, bankInfo : {}, responseBody : {}", bo, object);

            return null;
        }

        log.debug("getBankInfo object={}", object);

        final JSONObject result = object.getJSONObject("result");
        final JSONObject information = result.getJSONObject("information");

        return BankInfo.builder()
                .name(information.getString("bankname"))
                .abbreviation(information.getString("abbreviation"))
                .build();
    }

}
