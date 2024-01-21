package cc.newex.wallet;

import cc.newex.wallet.annotation.RpcConfig;
import cc.newex.wallet.command.ActLikeCommand;
import com.alibaba.fastjson.support.retrofit.Retrofit2ConverterFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cc.newex.wallet.annotation.RpcConfig.RpcType.JSON_RPC;
import static cc.newex.wallet.annotation.RpcConfig.RpcType.REST_RPC;
import static com.googlecode.jsonrpc4j.JsonRpcBasicServer.ERROR;

/**
 * @author newex-team
 * @data 29/03/2018
 */
@Component
@Slf4j
public class RpcCommandProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private final static String RPC_LOCATION = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "cc/newex/wallet/command/**/*.class";

    private Environment environment;
    private BeanDefinitionRegistry registry;

    private final int connectionTimeoutMillis = 120 * 1000;
    private final int readTimeoutMillis = 120 * 1000;

    private String innerProxy;

    @Override
    public void postProcessBeanDefinitionRegistry(final BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            final Resource[] resources = resourcePatternResolver.getResources(RPC_LOCATION);
            final MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (final Resource e : resources) {
                final MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(e);
                final Class<?> command = ClassUtils.getClass(metadataReader.getClassMetadata().getClassName());
                if (command.isInterface()) {
                    final RpcConfig config = command.getAnnotation(RpcConfig.class);
                    if (!ObjectUtils.isEmpty(config)) {
                        final String server = this.getProperty(config.server());
                        if (!StringUtils.hasText(server)) {
                            continue;
                        }
                        final String username = this.getProperty(config.username());
                        final String password = this.getProperty(config.password());
                        final boolean needProxy = config.needProxy();
                        Object obj = null;
                        if (config.type() == JSON_RPC) {
                            final JsonRpcHttpClient client = this.buildRpcClient(command, server, username, password, needProxy);
                            obj = ProxyUtil.createClientProxy(this.getClass().getClassLoader(), command, client);
                        } else if (config.type() == REST_RPC) {
                            obj = this.buildRestClient(command, server, username, password, needProxy);
                        } else {
                            throw new RuntimeException("Not support rpc type: " + config.type().name());
                        }

                        final GenericBeanDefinition definition = new GenericBeanDefinition();
                        definition.getConstructorArgumentValues().addGenericArgumentValue(obj);
                        definition.setBeanClass(CommandFactoryBean.class);
                        this.registry.registerBeanDefinition(command.getSimpleName(), definition);

                    }
                }
            }

        } catch (final Throwable e) {
            log.error("scan rpc command error", e);
            throw new BeanCreationException("scan rpc command error");
        }

    }

    private <T> T buildRestClient(final Class<T> command, final String server, final String username, final String password, final boolean needProxy) {

        final Map<String, String> authorization = new HashMap<>(2);
        authorization.put("Content-type", "application/json");
        final String auth = username + ":" + password;

        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(this.connectionTimeoutMillis, TimeUnit.MILLISECONDS)
                .readTimeout(this.readTimeoutMillis, TimeUnit.MILLISECONDS)
                .addInterceptor((chain) -> {
                    Request.Builder builder = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Basic " + Base64Utils.encodeToString(auth.getBytes()));
                    if (needProxy) {
                        builder.addHeader("referer", server);
                    }

                    Request request = builder.build();
                    return chain.proceed(request);
                })
                .build();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(new Retrofit2ConverterFactory())
                .addCallAdapterFactory(new RestCallAdapterFactory())
                .client(client);
        if (needProxy) {
            builder.baseUrl(this.innerProxy);
        } else {
            builder.baseUrl(server);
        }

        final Retrofit retrofit = builder.build();
        final T obj = retrofit.create(command);
        return obj;

    }

    class RestCallAdapterFactory extends CallAdapter.Factory {

        @Override
        public CallAdapter<?, ?> get(final Type returnType, final Annotation[] annotations, final Retrofit retrofit) {
            return new RestCallAdapter(returnType);
        }
    }

    class RestCallAdapter<R> implements CallAdapter<R, Object> {
        private final Type responseType;

        RestCallAdapter(final Type responseType) {
            this.responseType = responseType;
        }

        @Override
        public Type responseType() {
            return this.responseType;
        }

        @Override
        public Object adapt(final Call<R> call) {
            try {
                return call.execute().body();
            } catch (final Throwable e) {
                RpcCommandProcessor.log.error("Got an error:", e);
                throw new RuntimeException(e);
            }
        }
    }

    private JsonRpcHttpClient buildRpcClient(final Class command, String server, final String username, final String password, final boolean needProxy) {
        final Map<String, String> authorization = new HashMap<>(3);
        authorization.put("Content-type", "application/json");
        final String auth = username + ":" + password;

        if (ActLikeCommand.class.isAssignableFrom(command)) {
            authorization.put(
                    "Authorization",
                    "000000" + Base64Utils.encodeToString(auth.getBytes()));
        } else {
            authorization.put(
                    "Authorization",
                    "Basic " + Base64Utils.encodeToString(auth.getBytes()));
        }
        if (needProxy) {
            authorization.put("referer", server);
            server = this.innerProxy;
        }

        JsonRpcHttpClient client = null;
        try {
            client = new JsonRpcHttpClient(new URL(server), authorization) {
                @Override
                protected boolean hasError(final ObjectNode jsonObject) {
                    if (jsonObject.has(ERROR) && jsonObject.get(ERROR) != null && !jsonObject.get(ERROR).isNull()) {
                        final JsonNode node = jsonObject.get(ERROR);
                        if (node instanceof IntNode && node.intValue() == 0) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            };
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        }
        client.setConnectionTimeoutMillis(this.connectionTimeoutMillis);
        client.setReadTimeoutMillis(this.readTimeoutMillis);
        return client;
    }

    private String getProperty(String key) {
        key = key.replaceAll("(\\$|\\{|\\})", "");
        String value = this.environment.getProperty(key);
        if (!StringUtils.hasText(value)) {
            value = "";
        }
        return value;
    }

    @Override
    public void setEnvironment(final Environment env) {
        this.environment = env;
        this.innerProxy = this.getProperty("newex.innerProxy");
    }

}
