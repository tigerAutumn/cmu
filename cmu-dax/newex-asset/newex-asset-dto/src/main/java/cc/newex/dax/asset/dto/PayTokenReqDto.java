package cc.newex.dax.asset.dto;

import lombok.*;

/**
 * @author lilaizhen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PayTokenReqDto {
    private Long projectId;
}
