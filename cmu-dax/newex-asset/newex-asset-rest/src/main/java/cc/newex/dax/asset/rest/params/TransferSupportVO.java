package cc.newex.dax.asset.rest.params;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferSupportVO {
    private String currency;
}
