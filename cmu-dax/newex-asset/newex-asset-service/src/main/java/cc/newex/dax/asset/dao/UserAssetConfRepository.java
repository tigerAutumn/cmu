package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.asset.criteria.UserAssetConfExample;
import cc.newex.dax.asset.domain.UserAssetConf;
import org.springframework.stereotype.Repository;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-06-26
 */
@Repository
public interface UserAssetConfRepository
        extends CrudRepository<UserAssetConf, UserAssetConfExample, Long> {
}