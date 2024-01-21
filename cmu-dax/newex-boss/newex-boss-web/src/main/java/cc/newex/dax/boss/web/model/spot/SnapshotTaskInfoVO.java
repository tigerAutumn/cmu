package cc.newex.dax.boss.web.model.spot;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SnapshotTaskInfoVO {
    private Long id;
    private int status;
    private Date startTime;
    private Date endTime;
    private String createName;
    private Date createDate;
    private Date modifyTime;

    private Integer[] currencyId;
    private Map<String, BigDecimal> latest;
}
