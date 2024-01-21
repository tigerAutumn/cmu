package cc.newex.dax.extra.service.admin.currency;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.currency.CurrencyBaseInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyBaseInfo;

import java.util.List;

/**
 * 全球数字货币基本信息表 服务接口
 *
 * @author newex-team
 * @date 2018-06-29
 */
public interface CurrencyBaseInfoAdminService extends CrudService<CurrencyBaseInfo, CurrencyBaseInfoExample, Long> {

    /**
     * 按币种编码删除数据
     *
     * @param code
     * @return
     */
    int removeByCode(String code);

    /**
     * 按币种编码查询
     *
     * @param code
     * @return
     */
    List<CurrencyBaseInfo> getByCode(String code);

}
