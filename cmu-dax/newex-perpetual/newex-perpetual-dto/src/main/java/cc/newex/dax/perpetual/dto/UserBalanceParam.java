package cc.newex.dax.perpetual.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBalanceParam {

    private String currencyCode;
    private List<Long> userId;
    private Integer brokerId;
}
