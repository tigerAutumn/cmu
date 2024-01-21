package cc.newex.dax.users.service.security;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserFavoritesExample;
import cc.newex.dax.users.domain.UserFavorites;
import cc.newex.dax.users.dto.membership.FavoritesParamDTO;
import cc.newex.dax.users.dto.membership.FavoritesResultDTO;

/**
 * 收藏币种对
 *
 * @author allen
 * @date 2018/03/18
 **/

public interface UserFavoritesService extends CrudService<UserFavorites, UserFavoritesExample, Long> {

    UserFavorites getByUserId(Long userId, final Integer brokerId);

    /**
     * 在用户注册成功之后，默认收藏币对CT/USDT、GEN/ETH、PVB/ETH
     *
     * @param userId
     * @param brokerId
     * @return
     */
    Integer addDefaultUserFavorites(Long userId, Integer brokerId);

    /**
     * 添加，取消删除
     */
    Integer addAndUpdateUserFavorites(FavoritesParamDTO request, Long userId, String operateType, Integer brokerId);

    /**
     * 获取收藏列表
     */
    FavoritesResultDTO getFavoritesList(Long userId, Integer brokerId);

}
