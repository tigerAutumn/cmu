package cc.newex.dax.market.spider.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 矿池定时器
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
@Component
public class OrePoolShareJobs {

//    @Autowired
//    private DataToRedisService dataToRedisService;
//
//    @Autowired
//    private DataKey dataKey;
//
//    @Autowired
//    private ServerUrl url;
//
//    /**
//     * 矿池
//     */
//    @Scheduled(initialDelay = 1000, fixedDelay = 60 * 10000 * 60)
//    public void ratePush() {
//        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();
//        fetchingDataPaths.stream().filter(fetchingDataPath -> fetchingDataPath.getWebName().equalsIgnoreCase(
//                CoinNameEnum.OREPOOL.name))
//                .forEach((FetchingDataPath fetchingDataPath) -> {
//                    final String content = OrePoolShareUtil.getHtmlContent(fetchingDataPath.getPath());
//                    if (content != null) {
//                        final List<MinePoolShareData> list = new ArrayList<>();
//                        OrePoolShareUtil.dataProcess(content, fetchingDataPath.getUrlKey(), list);
//                        this.addPoolToDB(UrlConfig.POOL_URL, list);
//                    } else {
//                        log.error("msg: ratePush return data is null ");
//                    }
//
//                });
//    }
//
//    private void addPoolToDB(final String path, final List<MinePoolShareData> poolShareDatas) {
//        final Map<String, Object> map = new HashMap<>();
//        map.put("poolShareData", JSONObject.toJSONString(poolShareDatas));
//        map.put("key", this.dataKey.getDataKey());
//        try {
//            final HttpClientUtils.Response post = HttpClientUtils.post(this.url.getServerLocation() + path, map);
//        } catch (final IOException e) {
//            log.error("msg : {}  Ticket path【 httpclient connection exception ！！！ ", this.url.getServerLocation() + path);
//        }
//    }
}