package cc.newex.dax.users.rest.controller.outer.v1.common.subaccount;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.pager.front.FrontPagerResult;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.dto.subaccount.SubAccountInfoDTO;
import cc.newex.dax.users.dto.subaccount.SubAccountInfoRequestDTO;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.service.subaccount.SubAccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liutiejun
 * @date 2018-11-05
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/subaccount/info")
public class SubAccountInfoController extends BaseController {

    @Autowired
    private SubAccountInfoService subAccountInfoService;

    /**
     * 查看子账户列表
     *
     * @param pageIndex
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseResult list(@RequestParam("pageIndex") @NotNull @Min(1) final Integer pageIndex,
                               @RequestParam("pageSize") @NotNull @Min(1) final Integer pageSize,
                               final HttpServletRequest request) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);

        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final Long userId = userDetails.getUserId();
        final Integer brokerId = this.getBrokerId(request);

        final PageInfo pageInfo = new PageInfo((pageIndex - 1) * pageSize, pageSize, "id", PageInfo.SORT_TYPE_DES);

        final List<SubAccountInfoDTO> subAccountInfoDTOList = this.subAccountInfoService.getByParentId(pageInfo, userId, brokerId);

        if (CollectionUtils.isNotEmpty(subAccountInfoDTOList)) {
            subAccountInfoDTOList.stream().forEach(subAccountInfoDTO -> {
                subAccountInfoDTO.setRealName(StringUtil.getStarRealName(subAccountInfoDTO.getRealName()));
            });
        }

        final Long totalItemCount = pageInfo.getTotals();

        final FrontPagerResult<SubAccountInfoDTO> frontPagerResult = new FrontPagerResult<>(subAccountInfoDTOList, pageIndex, pageSize, totalItemCount);

        return ResultUtils.success(frontPagerResult);
    }

    /**
     * 新增子账户
     *
     * @param subAccountInfoRequestDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/add")
    public ResponseResult add(@RequestBody final SubAccountInfoRequestDTO subAccountInfoRequestDTO, final HttpServletRequest request) {
        return null;
    }

    /**
     * 解绑子账户
     *
     * @param userId
     * @param request
     * @return
     */
    @PostMapping(value = "/unbind/{userId}")
    public ResponseResult unbind(@PathVariable final Long userId, final HttpServletRequest request) {
        return null;
    }



}
