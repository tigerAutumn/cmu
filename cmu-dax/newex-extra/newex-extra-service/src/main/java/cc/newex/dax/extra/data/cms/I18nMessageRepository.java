package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.I18nMessageExample;
import cc.newex.dax.extra.domain.cms.I18nMessage;
import org.springframework.stereotype.Repository;

/**
 * 本地化文本表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface I18nMessageRepository
        extends CrudRepository<I18nMessage, I18nMessageExample, Integer> {
}