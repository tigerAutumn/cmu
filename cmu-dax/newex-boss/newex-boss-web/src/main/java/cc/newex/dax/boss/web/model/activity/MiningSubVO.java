package cc.newex.dax.boss.web.model.activity;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MiningSubVO {
    private Long subId;
    private Long miningId;
    private Long lastUserBillId;
    private String startDate;
    private String endDate;
    private Integer finishFlag;
    private String currencyCode;
    private List<String> productIds;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal miningReward;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal feeReward;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal totalReward;
}
