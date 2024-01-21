package cc.newex.dax.users.service.security.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserFavoritesExample;
import cc.newex.dax.users.data.UserFavoritesRepository;
import cc.newex.dax.users.domain.UserFavorites;
import cc.newex.dax.users.domain.behavior.enums.BehaviorCategoryEnum;
import cc.newex.dax.users.dto.membership.FavoritesParamDTO;
import cc.newex.dax.users.dto.membership.FavoritesResultDTO;
import cc.newex.dax.users.service.security.UserFavoritesService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserFavoritesServiceImpl extends AbstractCrudService<UserFavoritesRepository, UserFavorites, UserFavoritesExample, Long>
        implements UserFavoritesService {

    @Autowired
    private UserFavoritesRepository userFavoritesRepository;

    @Override
    public UserFavorites getByUserId(final Long userId, final Integer brokerId) {
        final UserFavoritesExample example = new UserFavoritesExample();
        final UserFavoritesExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }
        return this.userFavoritesRepository.selectOneByExample(example);
    }

    /**
     * 在用户注册成功之后，默认收藏币对CT/USDT、GEN/ETH、PVB/ETH
     *
     * @param userId
     * @param brokerId
     * @return
     */
    @Override
    public Integer addDefaultUserFavorites(final Long userId, final Integer brokerId) {
        return -1;
    }

    private UserFavorites initUserFavorites(final Long userId, final Integer brokerId) {
        final FavoritesResultDTO result = new FavoritesResultDTO();
        final UserFavorites userFavorites = UserFavorites.builder()
                .userId(userId)
                .brokerId(brokerId)
                .productIds(JSONObject.toJSONString(result))
                .build();

        this.userFavoritesRepository.insert(userFavorites);

        return userFavorites;
    }

    /**
     * add update 收藏币对信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addAndUpdateUserFavorites(final FavoritesParamDTO favoritesParam, final Long userId, final String operateType, final Integer brokerId) {
        UserFavorites userFavorites = this.getByUserId(userId, brokerId);

        if (userFavorites == null) {
            userFavorites = this.initUserFavorites(userId, brokerId);
        }

        final String favorites = userFavorites.getProductIds();

        switch (operateType.toLowerCase()) {
            case "add":
                final FavoritesResultDTO result = new FavoritesResultDTO();
                if (StringUtils.isBlank(favorites) || favorites.length() <= 2) {
                    if (favoritesParam.getType().equalsIgnoreCase(BehaviorCategoryEnum.SPOT.getName())) {
                        result.setSpot(favoritesParam.getIds());
                    } else {
                        result.setFutures(favoritesParam.getIds());
                    }
                    userFavorites = UserFavorites.builder()
                            .productIds(JSONObject.toJSONString(result))
                            .userId(userId).brokerId(brokerId).build();
                } else {
                    final FavoritesResultDTO favoritesResult = JSONObject.parseObject(favorites, FavoritesResultDTO.class);
                    if (favoritesParam.getType().equalsIgnoreCase(BehaviorCategoryEnum.SPOT.getName())) {
                        if (favoritesParam.getIds() != null) {
                            favoritesParam.getIds().stream().forEach(id -> {
                                if (id != null) {
                                    if (favoritesResult.getSpot() != null && !favoritesResult.getSpot().contains(id)) {
                                        favoritesResult.getSpot().add(id);
                                    }
                                }
                            });
                        }

                    } else {
                        if (favoritesParam.getIds() != null) {
                            favoritesParam.getIds().stream().forEach(id -> {
                                if (id != null) {
                                    if (favoritesResult.getFutures() != null && !favoritesResult.getFutures().contains(id)) {
                                        favoritesResult.getFutures().add(id);
                                    }
                                }
                            });
                        }

                    }
                    userFavorites = UserFavorites.builder()
                            .productIds(JSONObject.toJSONString(favoritesResult))
                            .userId(userId).brokerId(brokerId).build();
                }

                break;
            case "delete":
                final FavoritesResultDTO favoritesResult = JSONObject.parseObject(favorites, FavoritesResultDTO.class);
                final List<Integer> listSpot = new ArrayList<>();
                final List<Integer> listFuture = new ArrayList<>();
                if (favoritesParam.getType().equalsIgnoreCase(BehaviorCategoryEnum.SPOT.getName())) {
                    favoritesResult.getSpot().stream().forEach(s -> {
                        if (!s.equals(favoritesParam.getIds().get(0))) {
                            listSpot.add(s);
                        }
                    });
                } else {
                    favoritesResult.getSpot().stream().forEach(s -> {
                        if (!s.equals(favoritesParam.getIds().get(0))) {
                            listFuture.add(s);
                        }
                    });
                }

                userFavorites = UserFavorites.builder()
                        .productIds(JSONObject.toJSONString(FavoritesResultDTO.builder()
                                .futures(listFuture)
                                .spot(listSpot)
                                .build()))
                        .userId(userId).brokerId(brokerId)
                        .build();
                break;
            default:
                break;
        }
        return this.userFavoritesRepository.updateById(userFavorites);
    }

    @Override
    public FavoritesResultDTO getFavoritesList(final Long userId, final Integer brokerId) {
        final UserFavorites userFavorites = this.getByUserId(userId, brokerId);
        if (ObjectUtils.isEmpty(userFavorites)) {
            return null;
        }
        return JSONObject.parseObject(userFavorites.getProductIds(), FavoritesResultDTO.class);
    }

    @Override
    protected UserFavoritesExample getPageExample(final String fieldName, final String keyword) {
        return null;
    }
}
