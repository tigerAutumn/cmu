package cc.newex.dax.market.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MinePoolShareData {

    /**
     * 矿池
     */
    private String name;
    /**
     * year:年, month: 月, month3: 3个月, day: 天, day3: 3天, all: 所有
     */
    private String poolMode;
    /**
     * 算力占比
     */
    private Double marketShareOfPool;
    /**
     * 块数量
     */
    private Integer blockAmount;
    /**
     * 空块占比
     */
    private Double emptyBlockProportion;
    /**
     * 平均块大小(Bytes)
     */
    private Long avgBlockSize;
    /**
     * 平均块矿工费(BTC)
     */
    private Double avgBlockMinerFee;
    /**
     * 矿工费与块奖励占比
     */
    private Double minerFeeAndBlockBonusProportion;
    /**
     * 排名
     */
    private Integer rank;
    /**
     * 算力
     */
    private String computingPower;
    /**
     * 创建时间
     */
    private Date createdDate;
}