package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserFavoritesExample;
import cc.newex.dax.users.domain.UserFavorites;
import org.springframework.stereotype.Repository;

/**
 * @author allen
 */
@Repository
public interface UserFavoritesRepository extends CrudRepository<UserFavorites, UserFavoritesExample, Long> {

}
