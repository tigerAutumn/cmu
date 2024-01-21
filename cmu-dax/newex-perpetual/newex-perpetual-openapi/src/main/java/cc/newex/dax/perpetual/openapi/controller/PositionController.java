package cc.newex.dax.perpetual.openapi.controller;

import cc.newex.commons.openapi.specs.annotation.OpenApi;
import cc.newex.commons.openapi.specs.annotation.OpenApiAuthValidator;
import cc.newex.commons.openapi.specs.annotation.OpenApiRateLimit;
import cc.newex.commons.openapi.specs.consts.PermissionType;
import cc.newex.commons.openapi.specs.model.OpenApiUserInfo;
import cc.newex.dax.perpetual.common.enums.V1ErrorCodeEnum;
import cc.newex.dax.perpetual.domain.bean.CurrentPosition;
import cc.newex.dax.perpetual.domain.bean.PositionPubConfig;
import cc.newex.dax.perpetual.dto.CurrentPositionDTO;
import cc.newex.dax.perpetual.openapi.controller.base.BaseController;
import cc.newex.dax.perpetual.openapi.support.aop.PerpetualOpenApiException;
import cc.newex.dax.perpetual.openapi.support.common.AuthenticationUtils;
import cc.newex.dax.perpetual.openapi.support.common.CheckPermission;
import cc.newex.dax.perpetual.service.UserPositionService;
import com.google.common.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Slf4j
@OpenApi
@RestController
@RequestMapping(value = "/api/v1/perpetual/position")
public class PositionController extends BaseController {

    @Autowired
    private AuthenticationUtils authenticationUtils;

    @Autowired
    private UserPositionService userPositionService;

    /**
     * 获取用户仓位列表
     */
    @GetMapping("/{contractCode}/list")
    @OpenApiRateLimit(value = "10/1")
    @OpenApiAuthValidator(method = HttpMethod.GET)
    public List<CurrentPositionDTO> list(@PathVariable("contractCode") final String contractCode, final HttpServletRequest request) {
        final OpenApiUserInfo openApiUserInfo = this.authenticationUtils.getUserInfo(request);

        if (Objects.isNull(openApiUserInfo)) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Partner_not_exist);
        }

        if (!CheckPermission.checkPermission(openApiUserInfo.getAuthorities(), PermissionType.TRADE)) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.API_KEY_NOT_PREMISSION_TRADE);
        }

        final Long userId = openApiUserInfo.getId();
        final Integer brokerId = this.getBrokerId();

        final List<CurrentPosition> currentPositionList = this.userPositionService.getUserPositionByType(userId, brokerId, contractCode);

        final ModelMapper mapper = new ModelMapper();

        final List<CurrentPositionDTO> dtoList = mapper.map(currentPositionList, new TypeToken<List<CurrentPositionDTO>>() {
        }.getType());

        return dtoList;
    }

    /**
     * 仓位和限额设置
     */
    @GetMapping("/{contractCode}/configs")
    @OpenApiRateLimit(value = "10/1")
    @OpenApiAuthValidator(method = HttpMethod.GET)
    public PositionPubConfig getPositionConfig(@PathVariable("contractCode") final String contractCode, final HttpServletRequest request) {
        final OpenApiUserInfo openApiUserInfo = this.authenticationUtils.getUserInfo(request);

        if (Objects.isNull(openApiUserInfo)) {
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Partner_not_exist);
        }

        final Long userId = openApiUserInfo.getId();
        final Integer brokerId = this.getBrokerId();

        final PositionPubConfig config = this.userPositionService.getPositionConfig(userId, brokerId, contractCode);

        return config;
    }

}
