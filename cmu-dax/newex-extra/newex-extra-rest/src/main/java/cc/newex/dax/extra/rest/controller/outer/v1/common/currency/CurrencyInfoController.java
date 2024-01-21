package cc.newex.dax.extra.rest.controller.outer.v1.common.currency;

import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import cc.newex.dax.extra.domain.currency.CurrencyBaseInfo;
import cc.newex.dax.extra.domain.currency.CurrencyDetailInfo;
import cc.newex.dax.extra.domain.currency.CurrencyPermission;
import cc.newex.dax.extra.domain.currency.CurrencyProgressInfo;
import cc.newex.dax.extra.domain.currency.CurrencyTrendInfo;
import cc.newex.dax.extra.dto.currency.CurrencyBaseInfoDTO;
import cc.newex.dax.extra.dto.currency.CurrencyDetailInfoDTO;
import cc.newex.dax.extra.dto.currency.CurrencyPermissionDTO;
import cc.newex.dax.extra.dto.currency.CurrencyProgressInfoDTO;
import cc.newex.dax.extra.dto.currency.CurrencyRequestDTO;
import cc.newex.dax.extra.dto.currency.CurrencyTrendInfoDTO;
import cc.newex.dax.extra.service.currency.CurrencyBaseInfoService;
import cc.newex.dax.extra.service.currency.CurrencyDetailInfoService;
import cc.newex.dax.extra.service.currency.CurrencyPermissionService;
import cc.newex.dax.extra.service.currency.CurrencyProgressInfoService;
import cc.newex.dax.extra.service.currency.CurrencyTrendInfoService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 币种wiki
 *
 * @author liutiejun
 * @date 2018-08-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/extra/currency")
public class CurrencyInfoController {

    @Autowired
    private CurrencyBaseInfoService currencyBaseInfoService;

    @Autowired
    private CurrencyDetailInfoService currencyDetailInfoService;

    @Autowired
    private CurrencyProgressInfoService currencyProgressInfoService;

    @Autowired
    private CurrencyTrendInfoService currencyTrendInfoService;

    @Autowired
    private CurrencyPermissionService currencyPermissionService;

    /**
     * 查看用户权限
     *
     * @param code
     * @param request
     * @return
     */
    @GetMapping(value = "/permission/get")
    public ResponseResult getPermission(@RequestParam(value = "code", required = false) final String code, final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final Long userId = userDetails.getUserId();

        final List<CurrencyPermission> currencyPermissionList = this.currencyPermissionService.getByUserId(userId.intValue(), code);

        final ModelMapper mapper = new ModelMapper();

        List<CurrencyPermissionDTO> currencyPermissionDTOS = null;
        if (currencyPermissionList != null) {
            currencyPermissionDTOS = mapper.map(
                    currencyPermissionList, new TypeToken<List<CurrencyPermissionDTO>>() {
                    }.getType()
            );
        }

        return ResultUtils.success(currencyPermissionDTOS);
    }

    /**
     * 查看项目信息进行编辑
     *
     * @param code    币种简称
     * @param request
     * @return
     */
    @GetMapping(value = "/info/get")
    public ResponseResult getInfo(@RequestParam("code") @NotBlank final String code, final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final ModelMapper mapper = new ModelMapper();

        final CurrencyBaseInfo currencyBaseInfo = this.currencyBaseInfoService.getByCode(code);
        final List<CurrencyDetailInfo> currencyDetailInfoList = this.currencyDetailInfoService.getByCode(code);

        CurrencyBaseInfoDTO currencyBaseInfoDTO = null;
        if (currencyBaseInfo != null) {
            currencyBaseInfoDTO = mapper.map(currencyBaseInfo, CurrencyBaseInfoDTO.class);
        }

        List<CurrencyDetailInfoDTO> currencyDetailInfoDTOS = null;
        if (currencyDetailInfoList != null) {
            currencyDetailInfoDTOS = mapper.map(
                    currencyDetailInfoList, new TypeToken<List<CurrencyDetailInfoDTO>>() {
                    }.getType()
            );
        }

        final CurrencyRequestDTO currencyRequestDTO = CurrencyRequestDTO.builder()
                .baseInfo(currencyBaseInfoDTO)
                .detailInfos(currencyDetailInfoDTOS)
                .build();

        return ResultUtils.success(currencyRequestDTO);
    }

    /**
     * 编辑币种信息
     *
     * @param currencyRequestDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/info/update")
    public ResponseResult updateInfo(@RequestBody @Valid final CurrencyRequestDTO currencyRequestDTO, final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final Long userId = userDetails.getUserId();

        final CurrencyBaseInfoDTO currencyBaseInfoDTO = currencyRequestDTO.getBaseInfo();
        final List<CurrencyDetailInfoDTO> currencyDetailInfoDTOS = currencyRequestDTO.getDetailInfos();

        final ModelMapper mapper = new ModelMapper();

        CurrencyBaseInfo currencyBaseInfo = mapper.map(currencyBaseInfoDTO, CurrencyBaseInfo.class);
        if (currencyBaseInfoDTO != null) {
            currencyBaseInfo = mapper.map(currencyBaseInfoDTO, CurrencyBaseInfo.class);

            currencyBaseInfo.setPublisherId(userId.intValue());
        }

        List<CurrencyDetailInfo> currencyDetailInfoList = null;
        if (currencyDetailInfoDTOS != null) {
            currencyDetailInfoList = mapper.map(currencyDetailInfoDTOS, new TypeToken<List<CurrencyDetailInfo>>() {
            }.getType());

            currencyDetailInfoList.stream().forEach(currencyDetailInfo -> currencyDetailInfo.setPublisherId(userId.intValue()));
        }

        final int update = this.currencyBaseInfoService.saveOrUpdate(currencyBaseInfo);
        this.currencyDetailInfoService.saveOrUpdate(currencyDetailInfoList);

        return ResultUtils.success(update);
    }

    /**
     * 编辑币种wiki信息
     *
     * @param currencyBaseInfoDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/base/update")
    public ResponseResult updateBaseInfo(@RequestBody @Valid final CurrencyBaseInfoDTO currencyBaseInfoDTO, final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final Long userId = userDetails.getUserId();

        final ModelMapper mapper = new ModelMapper();

        final CurrencyBaseInfo currencyBaseInfo = mapper.map(currencyBaseInfoDTO, CurrencyBaseInfo.class);
        currencyBaseInfo.setPublisherId(userId.intValue());

        final int update = this.currencyBaseInfoService.saveOrUpdate(currencyBaseInfo);

        return ResultUtils.success(update);
    }

    /**
     * 编辑币种wiki信息
     *
     * @param currencyDetailInfoDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/detail/update")
    public ResponseResult updateDetailInfo(@RequestBody @Valid final CurrencyDetailInfoDTO currencyDetailInfoDTO, final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final Long userId = userDetails.getUserId();

        final ModelMapper mapper = new ModelMapper();

        final CurrencyDetailInfo currencyDetailInfo = mapper.map(currencyDetailInfoDTO, CurrencyDetailInfo.class);
        currencyDetailInfo.setPublisherId(userId.intValue());

        final int update = this.currencyDetailInfoService.saveOrUpdate(currencyDetailInfo);

        return ResultUtils.success(update);
    }

    /**
     * 添加项目进展信息
     *
     * @param currencyProgressInfoDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/progress/update")
    public ResponseResult updateProgressInfo(@RequestBody @Valid final CurrencyProgressInfoDTO currencyProgressInfoDTO, final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final Long userId = userDetails.getUserId();

        final ModelMapper mapper = new ModelMapper();

        final CurrencyProgressInfo currencyProgressInfo = mapper.map(currencyProgressInfoDTO, CurrencyProgressInfo.class);
        currencyProgressInfo.setPublisherId(userId.intValue());

        final int save = this.currencyProgressInfoService.save(currencyProgressInfo);

        return ResultUtils.success(save);
    }

    /**
     * 添加项目进展信息
     *
     * @param currencyTrendInfoDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/trend/update")
    public ResponseResult updateTrendInfo(@RequestBody @Valid final CurrencyTrendInfoDTO currencyTrendInfoDTO, final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.USER_NOT_LOGIN);
        }

        final Long userId = userDetails.getUserId();

        final ModelMapper mapper = new ModelMapper();

        final CurrencyTrendInfo currencyTrendInfo = mapper.map(currencyTrendInfoDTO, CurrencyTrendInfo.class);
        currencyTrendInfo.setPublisherId(userId.intValue());

        final int save = this.currencyTrendInfoService.save(currencyTrendInfo);

        return ResultUtils.success(save);
    }

}