package cc.newex.dax.boss.web.model.spot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author liutiejun
 * @date 2019-05-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotBatchTransferVO {

    /**
     * 批量划转的数据，行之间用"\r\n"分割，字段之间用","分割；
     * 字段之间的顺序：fromUserId, toUserId, currencyId, amount；
     */
    @NotBlank
    private String batchTransferData;

    @NotNull
    private Integer batchBrokerId;

    @NotBlank
    private String batchRemark;

}
