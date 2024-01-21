package cc.newex.dax.market.dto.model;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author allen
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonInfo implements Serializable {
    private static final long serialVersionUID = 2991912354217062158L;
    /**
     * 交易所名称
     */
    private String name;
    /**
     * 价格
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal price;
}
