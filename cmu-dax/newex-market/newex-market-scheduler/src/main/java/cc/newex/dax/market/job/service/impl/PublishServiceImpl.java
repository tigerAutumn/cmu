package cc.newex.dax.market.job.service.impl;

import cc.newex.dax.market.common.enums.PublishTypeEnum;
import cc.newex.dax.market.job.bean.PublishInfo;
import cc.newex.dax.market.job.service.PublishService;
import cc.newex.dax.market.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 合约业务数据推送
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    RedisService redisService;
    private static ExecutorService executorService;

    @Override
    public void publish(final String channel, final PublishTypeEnum typeEnum, final String symbolName, final int binary, final Object data, final String contract,
                        final String period) {
        final PublishInfo publishInfo = new PublishInfo();
        publishInfo.setType(typeEnum.getName());
        publishInfo.setBase(symbolName);
        publishInfo.setZip(false);
        publishInfo.setData(data);
        if (StringUtils.isNotBlank(contract)) {
            publishInfo.setContract(contract);
        }
        if (StringUtils.isNotBlank(period)) {
            publishInfo.setGranularity(period);
        }
        final String publishMsg = publishInfo.toString();

        PublishServiceImpl.executorService.execute(() -> {
            this.redisService.publish(channel + symbolName.toUpperCase(), publishMsg);
        });
    }

    @Override
    public void setInfo(final String key, final String data) {
        this.redisService.setInfo(key, data);
    }

    static {
        PublishServiceImpl.executorService = new ThreadPoolExecutor(5, 5, 5L, TimeUnit.SECONDS, new ArrayBlockingQueue(5000),
                (r, executor) -> log.error("futures publish msg is too many ,discard it."));
    }
}
