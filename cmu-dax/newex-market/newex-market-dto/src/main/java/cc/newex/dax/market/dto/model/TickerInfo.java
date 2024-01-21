package cc.newex.dax.market.dto.model;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author allen
 * @date 2018/03/18
 */
@Setter
@Getter
public class TickerInfo implements Serializable {
    private static final long serialVersionUID = -73571155750361589L;

    /**
     * 价格
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal last;
    /**
     * 名称
     */
    private String name;
}
