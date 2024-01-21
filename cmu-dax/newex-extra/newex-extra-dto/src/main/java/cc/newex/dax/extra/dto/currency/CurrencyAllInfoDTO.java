package cc.newex.dax.extra.dto.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 币种全部信息
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyAllInfoDTO {

    private CurrencyBaseInfoDTO baseInfo;

    private CurrencyDetailInfoDTO detailInfo;

    private CurrencyLevelInfoDTO levelInfo;

    private List<CurrencyProgressInfoDTO> progressInfos;

}
