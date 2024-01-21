package cc.newex.dax.push.bean;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author newex-team
 * @date 2018/7/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PortfolioShareData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 基准币种id
     */
    private Integer baseId;
    /**
     * 币种编码
     */
    private String base;
    /**
     * 计价币种id
     */
    private Integer quoteId;
    /**
     * 币种编码
     */
    private String quote;
    /**
     * 当前份数
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal current;
    /**
     * 剩余份数
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal remaining;
    /**
     * 冻结份数
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal hold;
}
