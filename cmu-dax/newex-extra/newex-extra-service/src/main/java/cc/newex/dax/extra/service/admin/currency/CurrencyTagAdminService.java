package cc.newex.dax.extra.service.admin.currency;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.currency.CurrencyTagExample;
import cc.newex.dax.extra.domain.currency.CurrencyTag;
import cc.newex.dax.extra.dto.currency.CurrencyTagDTO;

import java.util.List;

/**
 * 币种标签表 服务接口
 *
 * @author liutiejun
 * @date 2018-07-19
 */
public interface CurrencyTagAdminService extends CrudService<CurrencyTag, CurrencyTagExample, Long> {

    int batchSaveOrUpdateCurrencyTag(final List<CurrencyTagDTO> currencyTagDTOList);

    int removeByCode(String code);

    List<CurrencyTag> getByCode(String code);

    int countByTagInfo(String tagInfoCode);

}

