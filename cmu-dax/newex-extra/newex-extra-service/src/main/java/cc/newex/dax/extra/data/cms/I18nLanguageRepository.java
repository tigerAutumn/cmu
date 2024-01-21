package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.I18nLanguageExample;
import cc.newex.dax.extra.domain.cms.I18nLanguage;
import org.springframework.stereotype.Repository;

/**
 * 本地化语言表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface I18nLanguageRepository
        extends CrudRepository<I18nLanguage, I18nLanguageExample, Integer> {
}