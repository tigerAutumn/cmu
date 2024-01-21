package cc.newex.dax.users.rest.controller.outer.v1.common.individual;

import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.domain.behavior.enums.BehaviorCategoryEnum;
import cc.newex.dax.users.dto.membership.FavoritesParamDTO;
import cc.newex.dax.users.dto.membership.FavoritesResultDTO;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.service.security.UserFavoritesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/users/favorites")
public class UserFavoritesController extends BaseController {
    @Autowired
    private UserFavoritesService userFavoritesService;

    /**
     * 收藏币对
     *
     * @param request
     * @return
     */
    @PostMapping("")
    public ResponseResult addUserFavorites(final HttpServletRequest request, @RequestBody final FavoritesParamDTO favorites) {
        final JwtUserDetails userJwt = JwtTokenUtils.getCurrentLoginUser(request);
        if (userJwt.getStatus() != 0) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (!favorites.getType().equalsIgnoreCase(BehaviorCategoryEnum.SPOT.getName()) && !favorites.getType().equalsIgnoreCase(BehaviorCategoryEnum.FUTURES.getName())) {
            return ResultUtils.failure(BizErrorCodeEnum.FAVORITES_TYPE_ERROR);
        }
        final Integer brokerId = this.getBrokerId(request);
        final int i = this.userFavoritesService.addAndUpdateUserFavorites(favorites, userJwt.getUserId(), "add", brokerId);
        if (i == -1) {
            return ResultUtils.failure(BizErrorCodeEnum.FAVORITES_ADD_ERROR);
        }
        return ResultUtils.success();
    }

    /**
     * 取消收藏
     *
     * @param request
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteUserFavorites(@PathVariable final Integer id, @RequestParam final String type, final HttpServletRequest request) {
        final JwtUserDetails userJwt = JwtTokenUtils.getCurrentLoginUser(request);
        if (userJwt.getStatus() != 0) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (!type.equalsIgnoreCase(BehaviorCategoryEnum.SPOT.getName()) && !type.equalsIgnoreCase(BehaviorCategoryEnum.FUTURES.getName())) {
            return ResultUtils.failure(BizErrorCodeEnum.FAVORITES_TYPE_ERROR);
        }
        final Integer brokerId = this.getBrokerId(request);

        final List<Integer> list = new ArrayList<>();
        list.add(id);
        final int i = this.userFavoritesService.addAndUpdateUserFavorites(FavoritesParamDTO.builder()
                .ids(list)
                .type(type)
                .build(), userJwt.getUserId(), "delete", brokerId);
        if (i == -1) {
            return ResultUtils.failure(BizErrorCodeEnum.FAVORITES_UPDATE_ERROR);
        }
        return ResultUtils.success();
    }

    /**
     * 获取用户收藏的列表
     *
     * @param request
     * @return
     */
    @GetMapping("")
    public ResponseResult getUserFavoritesList(final HttpServletRequest request) {
        final JwtUserDetails userJwt = JwtTokenUtils.getCurrentLoginUser(request);
        if (userJwt.getStatus() != 0) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        final Integer brokerId = this.getBrokerId(request);
        final FavoritesResultDTO result = this.userFavoritesService.getFavoritesList(userJwt.getUserId(), brokerId);
        if (ObjectUtils.isEmpty(result)) {
            return ResultUtils.success();
        }
        return ResultUtils.success(result);
    }
}
