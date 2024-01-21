package cc.newex.dax.boss.web.model.portfolio;

import java.util.List;

/**
 * 主动管理组合
 *
 * @author liutiejun
 * @date 2018-07-23
 */
public class ActivePortfolioVO {

    /**
     * 产品ID
     */
    private Long id;

    /**
     * 产品类型，1-指数组合，2-看涨组合，3-主动组合
     */
    private Integer type;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品副标题
     */
    private String subtitle;

    /**
     * 产品介绍
     */
    private String intro;

    /**
     * 发行方介绍
     */
    private String issuerIntro;

    /**
     * 发行方ID
     */
    private String issuerId;

    /**
     * 发行规模，以USDT计，即最大可募集资金
     */
    private Long issueAmount;

    /**
     * 可操盘币种
     */
    private List<String> operateCurrencyList;

    /**
     * 可操盘时间 - 开始时间
     */
    private String operateStartTime;

    /**
     * 可操盘时间 - 结束时间
     */
    private String operateEndTime;

    /**
     * 初始申购时间 - 开始时间
     */
    private String firstPurchStartTime;

    /**
     * 初始申购时间 - 结束时间
     */
    private String firstPurchEndtime;

    /**
     * 初始申购总额，以USDT计
     */
    private Long firstPurchAmount;

    /**
     * 中途申购，0-关，1-开
     */
    private Integer halfwayPurch;

    /**
     * 中途申购时间，1-每日
     */
    private Integer halfwayPurchTime;

    /**
     * 中途申购 - 每次申购总额
     */
    private Long halfwayPurchAmount;

    /**
     * 中途申购时间 - 开始时间
     */
    private String halfwayPurchStartTime;

    /**
     * 中途申购时间 - 结束时间
     */
    private String halfwayPurchEndtime;

    /**
     * 个人最大可申请份额
     */
    private Long personalMaxShares;

    /**
     * 赎回成分，0-关，1-开
     */
    private Integer redeemComponent;

    /**
     * 赎回USDT，0-关，1-开
     */
    private Integer redeemUsdt;

    /**
     * 是否可划转至币币账户，0-关（不可以），1-开（可以）
     */
    private Integer transferSpot;

    /**
     * 持仓人数，实时计算
     */
    private Integer holdNumber;

    /**
     * 平台发行份额，实时计算
     */
    private Long issueTotalShares;

    /**
     * 上线状态，1-未上线，2-预发上线，3-上线
     */
    private Integer onlineState;

}
