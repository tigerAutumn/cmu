package cc.newex.wallet;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.transaction.TransactionUtils;
import cc.newex.commons.support.web.CommonErrorController;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"cc.newex.wallet", "cc.newex.commons"})
@ComponentScan(basePackages = {
        "cc.newex.commons",
        "cc.newex.wallet"},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {CommonErrorController.class, LocaleUtils.class, TransactionUtils.class}))
public class Client {
    public static void main(final String[] args) {
        SpringApplication.run(Client.class, args);
    }

    @Bean
    public RestTemplate createRestTemplate()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        final TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        final SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        final SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        final CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        final HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(15000);
        requestFactory.setConnectionRequestTimeout(30000);
        requestFactory.setReadTimeout(60000);
        requestFactory.setHttpClient(httpClient);

        final RestTemplate restTemplate = new RestTemplate(requestFactory);

        for (HttpMessageConverter converter : restTemplate.getMessageConverters()) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("utf-8"));
                break;
            }
        }
        return restTemplate;
    }
}
