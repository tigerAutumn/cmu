package cc.newex.dax.market.job.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
public class PublishInfo {
    /**
     * 产品 spot/future
     */
    private String biz = "indexes";
    /**
     * 类型 depth/balance
     */
    private String type;
    /**
     * 计价币种 合约现在默认usd
     */
    private String quote = "usd";
    /**
     * 交易币种 btc/ltc/eth
     */
    private String base;
    /**
     * 是否压缩 默认压缩 false 不压缩，true 压缩
     */
    private Boolean zip;
    /**
     * 数据
     */
    private Object data;

    /**
     * 合约类型、可选
     */
    private String contract;

    /**
     * 时间周期 1min/ 5min 等     可选
     */
    private String granularity;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
