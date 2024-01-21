package cc.newex.dax.extra.service.admin.currency;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.currency.CurrencyDetailInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyDetailInfo;

import java.util.List;

/**
 * 全球数字货币详细信息表 服务接口
 *
 * @author newex-team
 * @date 2018-06-29
 */
public interface CurrencyDetailInfoAdminService extends CrudService<CurrencyDetailInfo, CurrencyDetailInfoExample, Long> {

    /**
     * 按照币种编码和语言查询，支持多语言，如果当前语言不支持，默认为en-us
     *
     * @param code
     * @param locale
     * @return
     */
    List<CurrencyDetailInfo> getByCode(String code, String locale);

    List<CurrencyDetailInfo> getAllByCode(String code, String locale);

}
