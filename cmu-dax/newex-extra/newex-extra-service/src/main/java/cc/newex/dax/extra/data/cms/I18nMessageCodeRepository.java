package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.I18nMessageCodeExample;
import cc.newex.dax.extra.domain.cms.I18nMessageCode;
import org.springframework.stereotype.Repository;

/**
 * 本地化文本编码表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface I18nMessageCodeRepository
        extends CrudRepository<I18nMessageCode, I18nMessageCodeExample, Integer> {
}