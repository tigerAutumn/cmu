package cc.newex.dax.market.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RateInfo {


    /**
     * 汇率
     */
    private double rate;

    /**
     * 汇率类型
     */
    private String rateType;


}
