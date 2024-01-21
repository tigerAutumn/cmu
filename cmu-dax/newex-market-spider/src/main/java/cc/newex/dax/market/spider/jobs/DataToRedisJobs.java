package cc.newex.dax.market.spider.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 定时刷新redis
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Component
@Slf4j
public class DataToRedisJobs {

//    @Autowired
//    private DataToRedisService dataToRedisService;
//
//
//    /**
//     * 路径存到缓存
//     */
//    @Scheduled(initialDelay = 10, fixedDelay = 60000)
//    public void pathToRedis() {
//        this.dataToRedisService.pathToRedis();
//    }
//
//    /**
//     * ticker放到redis
//     */
//    @Scheduled(initialDelay = 10, fixedDelay = 60000)
//    public void tickerToRedis() {
//        this.dataToRedisService.tickerToRedis();
//    }
//
//    /**
//     * rate放到redis
//     */
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void rateToRedis() {
//        this.dataToRedisService.rateToRedis();
//    }
//
//    /**
//     * 矿池数据放到redis
//     */
//    @Scheduled(initialDelay = 10, fixedDelay = 60000)
//    public void orePoolToRedis() {
//        this.dataToRedisService.orePoolToRedis();
//    }

}
