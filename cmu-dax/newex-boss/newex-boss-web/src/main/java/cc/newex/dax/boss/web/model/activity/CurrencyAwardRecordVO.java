package cc.newex.dax.boss.web.model.activity;

import lombok.Data;

@Data
public class CurrencyAwardRecordVO {

    /**
     * 活动编号
     */
    private String code;

    /**
     * 活动数据
     */
    private String area;

//    /**
//     * 用户编号
//     */
//    private List<Long> userId;
//
//    /**
//     * 币种编号
//     */
//    private List<Integer> currencyId;
//
//    /**
//     * 发币数量
//     */
//    private List<BigDecimal> amount;
}
