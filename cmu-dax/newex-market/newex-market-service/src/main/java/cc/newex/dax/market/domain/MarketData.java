package cc.newex.dax.market.domain;

import cc.newex.dax.market.common.enums.CoinConfigTypeEnum;
import lombok.*;

import java.util.Date;

/**
 * @author
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketData {
    /**
     * 交易数据
     */
    private Long id;
    /**
     *
     */
    private Integer marketFrom;
    /**
     * 开盘价
     */
    private Double open;
    /**
     * 最高价
     */
    private Double high;
    /**
     * 最低价
     */
    private Double low;
    /**
     * 收盘价
     */
    private Double close;
    /**
     * 成交量
     */
    private Double volume;
    /**
     * 币总数
     */
    private Double coinVolume;
    /**
     * 0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月
     */
    private Integer type;
    /**
     * 起始id
     */
    private Long startId;
    /**
     * 结束id
     */
    private Long endId;
    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 将当前对象转换成字符串数组。以便后面推送数据
     * @return
     */
    /**
     * 将当前对象转换成字符串数组。以便后面推送数据
     *
     * @return
     */
    public String[] getArr(Integer coinConfigType) {
        final String[] arr = new String[7];
        arr[0] = String.valueOf(this.createdDate.getTime());
        arr[1] = String.valueOf(this.getOpen());
        arr[2] = String.valueOf(this.getHigh());
        arr[3] = String.valueOf(this.getLow());
        arr[4] = String.valueOf(this.getClose());
        arr[5] = String.valueOf(this.getVolume());
        arr[6] = coinConfigType.equals(0) ? CoinConfigTypeEnum.MARKET.name() : CoinConfigTypeEnum.PORTFOLIO.name();
        return arr;
    }

}