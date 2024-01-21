package cc.newex.dax.asset.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 2018/6/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetRecordStatisticsDto {
    BigDecimal totalDepositAmount;
    Integer totalDepositUsers;
    Integer firstDepositUsers;
    Integer totalDepositRecords;

    BigDecimal totalWithdrawAmount;
    Integer totalWithdrawUsers;
    Integer totalWithdrawRecords;
}
