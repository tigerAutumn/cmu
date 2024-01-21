package cc.newex.dax.users.rest.controller.admin.v1;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.domain.DictCountry;
import cc.newex.dax.users.domain.UserBroker;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.dto.admin.DictCountryReqDTO;
import cc.newex.dax.users.dto.admin.UserInviteDetailDTO;
import cc.newex.dax.users.dto.admin.UserInviteReqDTO;
import cc.newex.dax.users.dto.kyc.UserInfoStatDTO;
import cc.newex.dax.users.dto.membership.UserBrokerDTO;
import cc.newex.dax.users.dto.membership.UserDetailInfoResDTO;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import cc.newex.dax.users.dto.membership.UserSettingsDTO;
import cc.newex.dax.users.service.admin.UserInfoAdminService;
import cc.newex.dax.users.service.admin.UserKycAdminService;
import cc.newex.dax.users.service.membership.DictCountryService;
import cc.newex.dax.users.service.membership.UserBrokerService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserInviteRecordService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserSecureEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 提供给boss后台管理系统使用的接口服务
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/users/individual")
public class UserInfoAdminController {
    @Autowired
    private UserInfoAdminService userInfoAdminService;
    @Autowired
    private DictCountryService dictCountryService;
    @Autowired
    private UserKycAdminService userKycAdminService;
    @Autowired
    private UserSecureEventService userSecureEventService;
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInviteRecordService userInviteRecordService;
    @Autowired
    private UserBrokerService userBrokerService;

    /**
     * @param userId
     * @description 查询用户信息
     * @date 2018/4/27 上午10:32
     */
    @GetMapping(value = "/{userId}")
    public ResponseResult<UserInfoResDTO> queryUserInfo(@PathVariable("userId") final long userId,
                                                        @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        if (userId <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST, null);
        }
        final UserInfo userInfo = this.userInfoAdminService.getUserInfoById(userId);
        if (userInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST, null);
        }

        if (brokerId != null && brokerId.intValue() != userInfo.getBrokerId().intValue()) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS, null);
        }
        userInfo.setPassword(null);
        userInfo.setCreatedDate(null);
        final UserInfoResDTO userInfoResDTO = ObjectCopyUtils.map(userInfo, UserInfoResDTO.class);
        return ResultUtils.success(userInfoResDTO);
    }

    /**
     * 更换用户邮箱
     *
     * @param userId
     * @param newEmail
     * @param brokerId
     * @return
     */
    @PutMapping(value = "/email/{userId}")
    public ResponseResult updateUserEmail(@PathVariable("userId") final Long userId,
                                          @RequestParam("newEmail") final String newEmail,
                                          @RequestParam(value = "brokerId", required = false) final Integer brokerId) {

        final long result = this.userInfoAdminService.updateUserEmail(userId, newEmail);

        if (result <= 0) {
            return ResultUtils.success(BizErrorCodeEnum.BOSS_USER_MODIFY_IDENTITYNO_ERROR);
        }

        return ResultUtils.success();
    }

    /**
     * 更换用户手机号
     *
     * @param userId
     * @param newMobile
     * @param brokerId
     * @return
     */
    @PutMapping(value = "/mobile/{userId}")
    public ResponseResult updateUserMobile(@PathVariable("userId") final Long userId,
                                           @RequestParam("newMobile") final String newMobile,
                                           @RequestParam(value = "brokerId", required = false) final Integer brokerId) {

        final long result = this.userInfoAdminService.updateUserMobile(userId, newMobile, -1);

        if (result <= 0) {
            return ResultUtils.success(BizErrorCodeEnum.BOSS_USER_MODIFY_IDENTITYNO_ERROR);
        }

        return ResultUtils.success();
    }

    /**
     * @param userId
     * @description 解绑谷歌
     * @date 2018/5/30 上午10:57
     */
    @PutMapping(value = "/google-code-bind/{userId}")
    public ResponseResult unbindGoogle(@PathVariable("userId") final long userId,
                                       @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        if (userId <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST);
        }
        final UserInfo userInfo = this.userInfoAdminService.getUserInfoById(userId);
        if (userInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST);
        }
        if (brokerId != null && brokerId.intValue() != userInfo.getBrokerId().intValue()) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS, null);
        }
        final boolean result = this.userInfoAdminService.unbindGoogle(userId);
        if (BooleanUtils.isFalse(result)) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_UNBIND_GOOGLE_FAILED);
        }
        return ResultUtils.success();
    }

    /**
     * @param userId
     * @description 解绑手机
     * @date 2018/5/30 上午10:58
     */
    @PutMapping(value = "/mobile-bind/{userId}")
    public ResponseResult unbindMobile(@PathVariable("userId") final long userId,
                                       @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        if (userId <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST);
        }
        final UserInfo userInfo = this.userInfoAdminService.getUserInfoById(userId);
        if (userInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST);
        }
        if (brokerId != null && brokerId.intValue() != userInfo.getBrokerId().intValue()) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS, null);
        }
        final boolean result = this.userInfoAdminService.unbindMobile(userId);
        if (BooleanUtils.isFalse(result)) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_UNBIND_GOOGLE_FAILED);
        }
        return ResultUtils.success();
    }

    /**
     * 查询用户列表
     *
     * @return
     */
    @PostMapping("/list")
    public ResponseResult list(@RequestBody final DataGridPager<UserInfoResDTO> pager) {
        if (StringUtils.isNotEmpty(pager.getQueryParameter().getUid())) {
            //固定按id排序
            pager.setSort("id");
        }
        final PageInfo pageInfo = pager.toPageInfo();
        final List<UserInfo> list = this.userInfoAdminService.listByPage(pageInfo, pager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), list));
    }

    /**
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @description 根据开始时间和结束时间查询kyc1, kyc2这段时间的认证人数
     * @date 2018/6/25 下午1:45
     */
    @GetMapping("/user-stat")
    public ResponseResult<UserInfoStatDTO> getUserInfoStat(@RequestParam("startTime") final Long startTime,
                                                           @RequestParam("endTime") final Long endTime) {
        return ResultUtils.success(this.userKycAdminService.getUserInfoStat(startTime, endTime));
    }

    /**
     * 查看用户的当前登录session信息
     *
     * @param userId 用户id
     * @return {@link cc.newex.commons.ucenter.model.SessionInfo}
     */
    @GetMapping(value = "/{userId}/session")
    public ResponseResult getSession(@PathVariable("userId") final long userId) {
        if (userId <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST);
        }
        return ResultUtils.success(this.userInfoAdminService.getSession(userId));
    }

    //========================== country start =============================

    /**
     * 添加国家信息
     */
    @PostMapping("/countries")
    public ResponseResult addDictCountry(@RequestBody final DictCountryReqDTO dictCountryReqDTO) {
        final DictCountry dictCountry = ObjectCopyUtils.map(dictCountryReqDTO, DictCountry.class);
        final int add = this.dictCountryService.add(dictCountry);
        return ResultUtils.success(add);
    }

    /**
     * 修改国家信息
     */
    @PutMapping("/countries/{id}")
    public ResponseResult updateDictCountry(@PathVariable("id") final Integer id,
                                            @RequestBody final DictCountryReqDTO dictCountryReqDTO) {
        final DictCountry dictCountry = this.dictCountryService.getById(id);
        if (dictCountry == null) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_PARAMS_ILLEGAL);
        }
        final DictCountry country = ObjectCopyUtils.map(dictCountryReqDTO, DictCountry.class);
        country.setId(id);
        final int errfect = this.dictCountryService.editById(country);
        if (errfect > 0) {
            return ResultUtils.success();
        }
        return ResultUtils.failure(BizErrorCodeEnum.COMMON_UPDATE_FAIL);
    }

    /**
     * 获取国家列表
     */
    @PostMapping("/countries/list")
    public ResponseResult getDictCountry(@RequestBody final DataGridPager<DictCountryReqDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<DictCountry> list = this.dictCountryService.listByPage(pageInfo, pager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), list));

    }

    /**
     * 删除国家信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/countries/{id}")
    public ResponseResult remove(@PathVariable("id") final Integer id) {
        if (NumberUtil.gt(id, 0)) {
            return ResultUtils.success(this.dictCountryService.removeById(id));
        }
        return ResultUtils.success();
    }

    //========================== country end =============================

    /**
     * @param userId
     * @description 解除用户24小时提现限制
     * @date 2018/7/7 下午4:43
     */
    @PostMapping("/free-withdraw-limit/{userId}")
    public ResponseResult freeWithdrawLimit(@PathVariable("userId") final long userId) {
        final Boolean result = this.userSecureEventService.free24HoursLimit(userId);
        return ResultUtils.success(result);
    }

    @GetMapping("/{userId}/settings")
    public ResponseResult<UserSettingsDTO> getUserSettings(@PathVariable("userId") final long userId) {
        final UserSettings settings = this.userSettingsService.getByUserId(userId);
        if (settings == null) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST, null);
        }
        return ResultUtils.success(ObjectCopyUtils.map(settings, UserSettingsDTO.class));
    }

    @GetMapping("/{userId}/detail")
    public ResponseResult<UserDetailInfoResDTO> getUserDetailInfo(@PathVariable("userId") final long userId,
                                                                  @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        final UserInfo userInfo = this.userInfoAdminService.getUserInfoById(userId);
        if (userInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST);
        }
        if (brokerId != null && brokerId.intValue() != userInfo.getBrokerId().intValue()) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS, null);
        }
        final UserDetailInfo detailInfo = this.userInfoService.getUserDetailInfo(userId);
        if (detailInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST, null);
        }
        return ResultUtils.success(ObjectCopyUtils.map(detailInfo, UserDetailInfoResDTO.class));
    }

    /**
     * 查询BM用户邀请列表
     *
     * @return
     */
    @RequestMapping("/invite-list")
    public ResponseResult inviteList(@RequestBody final UserInviteReqDTO dto) {
        final List<UserInviteReqDTO> list = this.userInviteRecordService.queryUserInviteList(dto);
        return ResultUtils.success(list);
    }

    /**
     * 用户身份和邀请用户身份信息
     *
     * @return
     */
    @GetMapping("/invite-detail/{userId}")
    public ResponseResult<UserInviteDetailDTO> inviteDetail(@PathVariable("userId") final long userId,
                                                            @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        final UserInviteDetailDTO userInviteDetailDTO = this.userInviteRecordService.queryUserInviteDetail(userId);
        if (brokerId != null && brokerId.intValue() != userInviteDetailDTO.getBrokerId().intValue()) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS, null);
        }
        return ResultUtils.success(userInviteDetailDTO);
    }

    /**
     * 获取券商列表
     */
    @PostMapping("/broker/list")
    public ResponseResult selectBrokerList(@RequestBody final DataGridPager<UserBrokerDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<UserBrokerDTO> list = this.userBrokerService.listByPage(pageInfo, pager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult(pageInfo.getTotals(), list));
    }

    /**
     * 获取券商列表
     */
    @GetMapping("/broker/all")
    public ResponseResult<List<UserBrokerDTO>> brokerList() {
        final List<UserBrokerDTO> list = this.userBrokerService.selectBrokerList();
        return ResultUtils.success(list);
    }

    /**
     * 新增券商
     *
     * @param dto
     * @return
     */
    @PostMapping("/broker")
    public ResponseResult create(@RequestBody final UserBrokerDTO dto) {
        final boolean result = this.userBrokerService.save(ObjectCopyUtils.map(dto, UserBroker.class)) > 0 ? true : false;
        return ResultUtils.success(result);
    }

    /**
     * 更新券商
     *
     * @param id
     * @param dto
     * @return
     */
    @PostMapping("/broker/{id}")
    public ResponseResult updateBroker(@PathVariable("id") final long id, @RequestBody final UserBrokerDTO dto) {
        final boolean result = this.userBrokerService.updateBroker(dto);
        return ResultUtils.success(result);
    }
}