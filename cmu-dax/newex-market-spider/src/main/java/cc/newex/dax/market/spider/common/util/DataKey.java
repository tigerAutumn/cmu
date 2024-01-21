package cc.newex.dax.market.spider.common.util;

import cc.newex.commons.lang.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;


/**
 * 获取内部访问key配置。
 */
@Repository
@Slf4j
@ConfigurationProperties(prefix = "newex.market")
public class DataKey {

    private String dataKey;

    public String getDataKey() {
        return MD5Util.getMD5String(this.dataKey);
    }

    public void setDataKey(final String dataKey) {
        this.dataKey = dataKey;
    }

    /**
     * 校验传入的key是否正确。
     *
     * @param key
     * @return
     */
    public boolean validateKey(final String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }

        return MD5Util.getMD5String(this.dataKey).equals(key);
    }

}
