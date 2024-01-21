package cc.newex.dax.extra.dto.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyRequestDTO {

    @Valid
    private CurrencyBaseInfoDTO baseInfo;

    @Valid
    private List<CurrencyDetailInfoDTO> detailInfos;

}
