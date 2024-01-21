package cc.newex.dax.extra.rest.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author newex-team
 * @date 2018/8/14 上午11:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectTokenInfoVO implements Serializable {

    private static final long serialVersionUID = -7941905893701371388L;

    /**
     * 币种名称(全称)
     */
    private String token;

    /**
     * 币种名称（简称）
     */
    private String tokenSymbol;

    /**
     * 发行总量
     */
    private String issue;

    /**
     * 流通总量
     */
    private String circulating;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 时间
     */
    private Long onlineTime;

    /**
     * 币种介绍
     */
    private String url;

}
