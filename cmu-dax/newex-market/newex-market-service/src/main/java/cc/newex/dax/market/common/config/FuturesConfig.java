package cc.newex.dax.market.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "newex.futures.config")
public class FuturesConfig {
    private int ifOnline;
    private String httpSetting;
    private String siteFlag;
    /**
     * 短信签名
     */
    private String signName;
    /**
     * 报警短信号码
     */
    private String phone;

    private int showSql;
    private int showLog;

    public static boolean SHOW_SQL;

    public static boolean SHOW_LOG;

    public static String PRE_URL;

    public static String INNER_URL;

    public static boolean IS_ONLINE;


    /**
     * 判断是否线上环境
     *
     * @return false：t true:线上
     */
    public boolean isOnline() {
        final boolean returnFlag = this.getIfOnline() == 1;
        if (!returnFlag) {
            log.error("returnFlag is:{}", returnFlag);
        }
        return returnFlag;
    }
}
