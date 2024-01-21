package cc.newex.dax.extra.data.currency;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.currency.TagInfoExample;
import cc.newex.dax.extra.domain.currency.TagInfo;
import org.springframework.stereotype.Repository;

/**
 * 标签信息表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-26 12:07:27
 */
@Repository
public interface TagInfoRepository extends CrudRepository<TagInfo, TagInfoExample, Long> {
}