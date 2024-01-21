package cc.newex.commons.broker.service;

import cc.newex.commons.broker.consts.BrokerConsts;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class BrokerService {

    private LoadingCache<String, Integer> cache = CacheBuilder.newBuilder()
            .refreshAfterWrite(BrokerConsts.BROKER_EXPIRE_TIME, TimeUnit.SECONDS)
            .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(final String key) {
                    return getBrokerIdFromUserClient(key);
                }
            });

    @Autowired
    private HttpServletRequest request;

    public Integer getBrokerId() throws ExecutionException {
        final String host = this.request.getServerName();
        return this.getBrokerId(host);
    }

    public Integer getBrokerId(String host) throws ExecutionException {
        return cache.get(host);
    }

    public abstract Integer getBrokerIdFromUserClient(String key);
}
