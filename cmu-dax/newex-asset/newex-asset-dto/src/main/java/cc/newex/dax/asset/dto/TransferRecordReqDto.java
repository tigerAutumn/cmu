package cc.newex.dax.asset.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018-04-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRecordReqDto {

    @NotNull
    @Range(min = 0)
    private Long userId;

    @NotNull
    private String transferType;

    private String currency;

    private Integer biz;

    private Byte status;

    private Date startTime;

    private Date endTime;

    private String address;

    @Min(1)
    private Integer pageNum = 1;

    @Min(1)
    @Max(100)
    private Integer pageSize = 10;

    private Integer brokerId;
}
