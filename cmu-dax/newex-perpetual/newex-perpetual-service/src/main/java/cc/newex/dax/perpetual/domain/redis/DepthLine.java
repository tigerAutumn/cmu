package cc.newex.dax.perpetual.domain.redis;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @date 2018/03/18
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepthLine {

    /**
     * 价格
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal price;
    /**
     * 总数量
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal totalAmount;
    /**
     * 该价格前的总数量
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal sumTotalAmount;

    /**
     * 用户id
     */
    private String userId;
}
