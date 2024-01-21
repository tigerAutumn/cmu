package cc.newex.openapi.cmx.client;

import cc.newex.openapi.cmx.common.domain.ClientParameter;
import cc.newex.openapi.cmx.common.enums.HttpHeader;
import cc.newex.openapi.cmx.common.utils.SignatureUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Base Api Client
 *
 * @author cointobe-sdk-team
 * @date 2018/04/28
 */
public class ApiClient {
    private final Retrofit retrofit;
    /**
     * 超时时间
     */
    private final ClientParameter parameter;

    public ApiClient(final ClientParameter parameter) {
        this.parameter = parameter;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(parameter.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(this.httpClient())
                .build();
    }

    public <T> T create(final Class<T> clazz) {
        return this.retrofit.create(clazz);
    }

    private OkHttpClient httpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new SignInterceptor(this.parameter.getApiKey(), this.parameter.getSecretKey(), this.parameter.getPassphrase()))
                .addInterceptor(new HttpStatusInterceptor())
                .connectTimeout(this.parameter.getTimeout(), TimeUnit.SECONDS)
                .readTimeout(this.parameter.getTimeout(), TimeUnit.SECONDS)
                .build();
    }

    /**
     * 签名过滤器
     */
    private class SignInterceptor implements Interceptor {

        private final String apiKey;
        private final String secretKey;
        private final String passphrase;

        public SignInterceptor(final String apiKey, final String secretKey, final String passphrase) {
            this.apiKey = apiKey;
            this.secretKey = secretKey;
            this.passphrase = passphrase;
        }

        @Override
        public Response intercept(final Chain chain) throws IOException {
            final Request original = chain.request();
            final String timestamp = String.valueOf(System.currentTimeMillis());
            final String contentType = "application/json";
            String sign = null;
            try {
                sign = SignatureUtils.generate(timestamp.toString(),
                        original.method().toUpperCase(),
                        original.url().url().getPath(),
                        original.url().encodedQuery(),
                        original.body() == null ? null : this.bodyToString(original),
                        this.secretKey);
            } catch (final Exception e) {
                e.printStackTrace();
            }

            final Request.Builder requestBuilder = original.newBuilder()
                    .addHeader(HttpHeader.ACCESS_KEY, this.apiKey)
                    .addHeader(HttpHeader.ACCESS_PASSPHRASE, this.passphrase)
                    .addHeader(HttpHeader.ACCESS_SIGN, sign)
                    .addHeader(HttpHeader.CONTENT_TYPE, contentType)
                    .addHeader(HttpHeader.ACCESS_TIMESTAMP, timestamp);

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        }

        private String bodyToString(final Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * http 请求返回状态过滤器
     */
    private class HttpStatusInterceptor implements Interceptor {
        @Override
        public Response intercept(final Chain chain) throws IOException {
            final Response response = chain.proceed(chain.request());
            if (response.isSuccessful()) {
                return response;
            }

            throw new RuntimeException(" call " + chain.request().url().url().getPath()
                    + " failed, status : " + response.code() + ", body : " + response.body().string());
        }
    }
}
