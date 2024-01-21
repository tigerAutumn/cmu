package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.ImgInfoExample;
import cc.newex.dax.extra.domain.cms.ImgInfo;
import org.springframework.stereotype.Repository;

/**
 * cms图片表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-08-08 19:47:55
 */
@Repository
public interface ImgInfoRepository extends CrudRepository<ImgInfo, ImgInfoExample, Long> {
}