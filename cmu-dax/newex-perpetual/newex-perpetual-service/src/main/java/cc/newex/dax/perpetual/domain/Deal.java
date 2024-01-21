package cc.newex.dax.perpetual.domain;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易数据
 *
 * @author xionghui
 * @date 2018/10/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Deal {
    private long id;
    /**
     * 价格
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal price;

    /**
     * 数量
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal amount;
    /**
     * 买卖方向
     */
    private String side;
    /**
     * 创建时间 sort desc
     */
    private Date createdDate;
}
