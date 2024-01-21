package cc.newex.dax.boss.web.controller.outer.v1.asset;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.asset.dto.LockedPositionRecordReqDto;
import cc.newex.dax.asset.dto.UserLockedPositionDto;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.enums.CurrencyBizEnum;
import cc.newex.dax.boss.web.model.asset.LockedPositionConfVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

/**
 * 锁仓管理
 *
 * @author liutiejun
 * @date 2018-07-04
 */
@RestController
@RequestMapping(value = "/v1/boss/asset/lockup")
@Slf4j
public class LockupController {

    @Autowired
    private AdminServiceClient adminServiceClient;

    @GetMapping(value = "/listAllCurrency")
    @OpLog(name = "获取币种列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_LOCKUP_VIEW"})
    public ResponseResult listAllCurrency(final HttpServletRequest request, @CurrentUser final User loginUser) {
        ResponseResult result = null;

        try {
            final Integer brokerId = loginUser.getBrokerId();
            result = this.adminServiceClient.getAllCurrencies(CurrencyBizEnum.SPOT.getName(), brokerId == 0 ? null : brokerId);
        } catch (final Exception e) {
            log.error("get currencies error: " + e.getMessage(), e);
        }

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "添加锁仓")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_LOCKUP_ADD"})
    public ResponseResult add(@Valid final LockedPositionConfVO lockedPositionConfVO, final HttpServletRequest request) {
        if (lockedPositionConfVO == null) {
            return ResultUtils.failure(BizErrorCodeEnum.ADD_ERROR);
        }

        try {
            // 锁仓用户账户，保护手机号、邮箱，最多20个，用多好隔开
            final String accounts = lockedPositionConfVO.getAccounts();
            String[] accountList = null;
            if (StringUtils.isNotBlank(accounts)) {
                accountList = accounts.split("\\s*[,|，]\\s*");
            }

            final Set<String> accountSet = new HashSet<>();

            if (ArrayUtils.isNotEmpty(accountList)) {
                for (final String account : accountList) {
                    accountSet.add(account);
                }
            }

            if (CollectionUtils.isEmpty(accountSet) || accountSet.size() > 20) {
                log.error("锁仓用户账户数据格式不正确，{}", accounts);

                return ResultUtils.failure(BizErrorCodeEnum.ADD_ERROR);
            }

            final List<String> users = new ArrayList<>(accountSet);

            // 释放时间，精确到日，格式为：yyyy-MM-dd
            String unlockTime = lockedPositionConfVO.getUnlockTime();
            if (StringUtils.isBlank(unlockTime)) {
                unlockTime = "2099-12-31";
            }

            final Date releaseDate = DateUtils.parseDate(unlockTime, "yyyy-MM-dd");

            // 释放规则，2-每月，5-每天
            Integer unlockCycle = lockedPositionConfVO.getUnlockCycle();
            if (unlockCycle == null || unlockCycle <= 0) {
                unlockCycle = 2;
            }

            // 释放的百分比，取值0-100，相对于锁仓数量
            Integer unlockAmount = lockedPositionConfVO.getUnlockAmount();
            if (unlockAmount == null || unlockAmount < 0) {
                unlockAmount = 0;
            }

            final BigDecimal releaseProportion = new BigDecimal(new Double(unlockAmount / 100.0).toString());

            final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

            // 操作者名字
            final String operator = user.getName();

            final UserLockedPositionDto userLockedPositionDto = UserLockedPositionDto.builder()
                    .users(users)
                    .lockedPositionName(lockedPositionConfVO.getLockPositionName())
                    .currency(lockedPositionConfVO.getCurrency())
                    .amount(lockedPositionConfVO.getAmount())
                    .dividend(lockedPositionConfVO.getDividend())
                    .releaseDate(releaseDate)
                    .releaseFrequency(unlockCycle)
                    .releaseProportion(releaseProportion)
                    .remark(lockedPositionConfVO.getRemark())
                    .operator(operator)
                    .brokerId(lockedPositionConfVO.getBrokerId())
                    .build();

            log.info("add user lock position dto => {}", userLockedPositionDto);
            final ResponseResult result = this.adminServiceClient.lockUserPositionBatch(userLockedPositionDto);

            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("add Lock api error: " + e.getMessage(), e);

            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改锁仓")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_LOCKUP_EDIT"})
    public ResponseResult edit(@RequestParam(value = "id", required = true) @NotNull final Long id,
                               @Valid final LockedPositionConfVO lockedPositionConfVO, final HttpServletRequest request) {
        return null;
    }

    /**
     * 获取锁仓列表
     *
     * @param userProperty     1-用户ID、2-用户邮箱、3-用户手机号
     * @param userValue
     * @param currencyId       币种
     * @param lockPositionName 锁仓名称
     * @param startTime
     * @param endTime
     * @param pager
     * @return
     */
    @GetMapping(value = "/list")
    @OpLog(name = "获取锁仓列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_LOCKUP_VIEW"})
    public ResponseResult list(@RequestParam(value = "userProperty", required = false) final Integer userProperty,
                               @RequestParam(value = "userValue", required = false) final String userValue,
                               @RequestParam(value = "currencyId", required = false) Integer currencyId,
                               @RequestParam(value = "lockPositionName", required = false) String lockPositionName,
                               @RequestParam(value = "startTime", required = false) String startTime,
                               @RequestParam(value = "endTime", required = false) String endTime,
                               final DataGridPager pager, @CurrentUser final User loginUser) {

        if (currencyId == null || currencyId <= 0) {
            currencyId = null;
        }

        if (StringUtils.isBlank(lockPositionName)) {
            lockPositionName = null;
        }

        if (StringUtils.isBlank(startTime)) {
            startTime = null;
        }

        if (StringUtils.isBlank(endTime)) {
            endTime = null;
        }

        ResponseResult result = null;

        try {
            Long userId = null;
            String email = null;
            String phone = null;

            // 1-用户ID、2-用户邮箱、3-用户手机号
            if (userProperty != null && StringUtils.isNotBlank(userValue)) {
                if (userProperty == 1) {
                    userId = Long.parseLong(userValue);
                } else if (userProperty == 2) {
                    email = userValue;
                } else if (userProperty == 3) {
                    phone = userValue;
                }

            }

            final Integer brokerId = loginUser.getBrokerId();
            result = this.adminServiceClient.
                    getLockedPositionList(pager, userId, phone, email, currencyId, lockPositionName, startTime, endTime, brokerId == 0 ? null : brokerId);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }

        return ResultUtil.getDataGridResult(result);
    }

    @RequestMapping(value = "/unlock")
    @OpLog(name = "解锁锁仓")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_LOCKUP_UNLOCK"})
    public ResponseResult unlock(@RequestParam(value = "unlock-Id", required = false) final Long unlockId,
                                 @RequestParam(value = "unlock-amount", required = false) final String unlockAmount, final HttpServletRequest request) {
        try {
            final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

            // 操作者名字
            final String operator = user.getName();

            final ResponseResult result = this.adminServiceClient.unlockUserPosition(unlockId, unlockAmount, operator);

            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("unlock api error: " + e.getMessage(), e);

            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

    }

    @GetMapping(value = "/getLockRecodeList")
    @OpLog(name = "获取锁仓操作记录列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_LOCKUP_REC_VIEW"})
    public ResponseResult getLockRecodeList(final DataGridPager<LockedPositionRecordReqDto> pager, @CurrentUser final User loginUser,
                                            @RequestParam(value = "field", required = false) final Integer field,
                                            @RequestParam(value = "keyword", required = false) final String keyword,
                                            @RequestParam(value = "beginTime", required = false) final String beginTimeStr,
                                            @RequestParam(value = "endTime", required = false) final String endTimeStr,
                                            @RequestParam(value = "currencyId", required = false) Integer currencyId,
                                            @RequestParam(value = "action", required = false) Integer action) {

        Long userId = null;
        String email = null;
        String mobile = null;

        //  field取值：1-用户ID、2-用户邮箱、3-用户手机号
        if (field != null && StringUtils.isNotBlank(keyword)) {
            if (field == 1) {
                userId = Long.parseLong(keyword);
            } else if (field == 2) {
                email = keyword;
            } else if (field == 3) {
                mobile = keyword;
            }
        }

        if (currencyId == null || currencyId <= 0) {
            currencyId = null;
        }

        Date startTime = null;
        Date endTime = null;

        if (StringUtils.isNotBlank(beginTimeStr)) {
            try {
                startTime = DateUtils.parseDate(beginTimeStr, "yyyy-MM-dd");
            } catch (final Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        if (StringUtils.isNotBlank(endTimeStr)) {
            try {
                endTime = DateUtils.parseDate(endTimeStr, "yyyy-MM-dd");
            } catch (final Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        if (action == null || action < 0) {
            action = null;
        }

        final LockedPositionRecordReqDto lockedPositionRecordReqDto = LockedPositionRecordReqDto.builder()
                .userId(userId)
                .mobile(mobile)
                .email(email)
                .currency(currencyId)
                .transferType(action)
                .startTime(startTime)
                .brokerId(loginUser.getLoginBrokerId())
                .endTime(endTime)
                .build();

        pager.setQueryParameter(lockedPositionRecordReqDto);

        try {
            final ResponseResult result = this.adminServiceClient.getLockedPositionRecord(pager);

            return ResultUtil.getDataGridResult(result);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);

            return ResultUtil.getDataGridResult();
        }

    }

}
