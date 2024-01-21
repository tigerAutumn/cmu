package cc.newex.dax.boss.web.controller.outer.v1.users;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.annotation.SiteUserId;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.users.ProductFeeVO;
import cc.newex.dax.spot.client.SpotCurrencyPairServiceClient;
import cc.newex.dax.spot.client.SpotFeesUserConfigClient;
import cc.newex.dax.spot.client.SpotUserLevelClient;
import cc.newex.dax.spot.dto.ccex.CurrencyPairDTO;
import cc.newex.dax.spot.dto.ccex.FeesUserConfigDTO;
import cc.newex.dax.spot.dto.ccex.UserLevelDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/user/fee")
public class FeeController {

    @Autowired
    private SpotUserLevelClient spotUserLevelClient;

    @Autowired
    private SpotFeesUserConfigClient spotFeesUserConfigClient;

    @Autowired
    private SpotCurrencyPairServiceClient spotCurrencyPairServiceClient;

    @GetMapping(value = "userLevel")
    @OpLog(name = "获取用户等级列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FEE_USER_LEVEL_VIEW"})
    public ResponseResult getUserLevel(@CurrentUser User loginUser, final DataGridPager<UserLevelDTO> pager, @RequestParam(value = "userId", required = false) final Long userId) {
        final ResponseResult result = this.spotUserLevelClient.getByPager(pager, userId, loginUser.getLoginBrokerId());
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/productFee")
    @OpLog(name = "获取币对手续费列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FEE_PRODUCT_FEE_VIEW"})
    public ResponseResult geProductFee(final DataGridPager<FeesUserConfigDTO> pager,
                                       @RequestParam(value = "userId", required = false) final Long userId,
                                       @RequestParam(value = "productId", required = false) final Integer productId,
                                       @RequestParam(value = "side", required = false) final Byte side) {

        final ResponseResult<DataGridPagerResult<FeesUserConfigDTO>> result = this.spotFeesUserConfigClient.getByPager(pager, productId, userId, side);

        final Long total = result.getData().getTotal();

        final List<FeesUserConfigDTO> userConfigDTOList = result.getData().getRows();

        List<ProductFeeVO> rows = new ArrayList<>();

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (CollectionUtils.isNotEmpty(userConfigDTOList)) {
            rows = userConfigDTOList.stream().map(v -> {
                final ProductFeeVO vo = ProductFeeVO.builder()
                        .userId(v.getUserId())
                        .expireDate(sdf.format(v.getExpireDate()))
                        .productDesc(this.getProductDesc(v.getProductId()))
                        .productId(v.getProductId())
                        .id(v.getId())
                        .rate(v.getRate())
                        .createdDate(sdf.format(v.getCreatedDate()))
                        .side(v.getSide().intValue()).build();

                return vo;
            }).collect(Collectors.toList());
        }

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total, rows);

        return ResultUtils.success(dataGridPagerResult);
    }

    @PostMapping(value = "/addUserLevel")
    @OpLog(name = "添加用户等级")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FEE_USER_LEVEL_ADD"})
    public ResponseResult addUserLevel(@RequestParam(value = "userId") @SiteUserId final Long userId, @RequestParam(value = "userLevel") final Integer level) {
        final UserLevelDTO dto = UserLevelDTO.builder()
                .userId(userId)
                .userLevel(level).build();
        final ResponseResult result = this.spotUserLevelClient.addConfig(dto);
        if (result.getCode() == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.failure("add user level error");
    }

    @PostMapping(value = "/addProductFee")
    @OpLog(name = "添加币对费率")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FEE_PRODUCT_FEE_ADD"})
    public ResponseResult addProductFee(final ProductFeeVO vo) {
        FeesUserConfigDTO dto = null;
        try {
            dto = FeesUserConfigDTO.builder()
                    .userId(vo.getUserId())
                    .productId(vo.getProductId())
                    .rate(vo.getRate())
                    .side(vo.getSide().byteValue())
                    .expireDate(DateUtils.parseDate(vo.getExpireDate(), "yyyy-MM-dd HH:mm:ss")).build();
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        final ResponseResult result = this.spotFeesUserConfigClient.addConfig(dto);
        if (result.getCode() == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.failure("add product fee error");
    }

    @PostMapping(value = "/editProductFee")
    @OpLog(name = "编辑币对手续费")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FEE_PRODUCT_FEE_EDIT"})
    public ResponseResult deleteProductFee(final ProductFeeVO vo) {
        FeesUserConfigDTO dto = null;
        try {
            dto = FeesUserConfigDTO.builder()
                    .id(vo.getId())
                    .userId(vo.getUserId())
                    .side(vo.getSide().byteValue())
                    .rate(vo.getRate())
                    .productId(vo.getProductId())
                    .expireDate(DateUtils.parseDate(vo.getExpireDate(), "yyyy-MM-dd HH:mm:ss")).build();
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        final ResponseResult result = this.spotFeesUserConfigClient.editConfig(dto);
        if (result.getCode() == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.failure(result.getMsg());
    }

    @PostMapping(value = "/deleteUserLevel")
    @OpLog(name = "删除用户等级")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FEE_USER_LEVEL_DELETE"})
    public ResponseResult deleteUserLevel(@RequestParam(value = "id") final Long id) {
        log.info("" + id);
        final ResponseResult result = this.spotUserLevelClient.removeConfig(id);
        if (result.getCode() == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.failure("remove error");
    }

    @PostMapping(value = "/deleteProductFee")
    @OpLog(name = "删除币对手续费")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_FEE_PRODUCT_FEE_DELETE"})
    public ResponseResult deleteProductFee(@RequestParam(value = "id") final Integer id) {
        log.info("" + id);
        final ResponseResult result = this.spotFeesUserConfigClient.removeConfig(id);
        if (result.getCode() == 0) {
            return ResultUtils.success();
        }
        return ResultUtils.failure("remove error");
    }

    private String getProductDesc(final Integer productId) {
        final ResponseResult<CurrencyPairDTO> result = this.spotCurrencyPairServiceClient.getCurrencyPair(productId);
        if (result.getCode() == 0 && result.getData() != null) {
            return result.getData().getCode().toUpperCase();
        }
        return null;
    }


}
