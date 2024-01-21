package cc.newex.dax.users.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.dto.admin.DictCountryReqDTO;
import cc.newex.dax.users.dto.admin.GlobalFrozenConfigDTO;
import cc.newex.dax.users.dto.admin.UserApiSecretResDTO;
import cc.newex.dax.users.dto.admin.UserFileInfoDTO;
import cc.newex.dax.users.dto.admin.UserInvitationChannelDTO;
import cc.newex.dax.users.dto.admin.UserInviteDetailDTO;
import cc.newex.dax.users.dto.admin.UserInviteReqDTO;
import cc.newex.dax.users.dto.admin.UserIpRateLimitDTO;
import cc.newex.dax.users.dto.common.PageResultDTO;
import cc.newex.dax.users.dto.kyc.AdminUserKycAuditDTO;
import cc.newex.dax.users.dto.kyc.UserInfoStatDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycFirstDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycSecondDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminListReqDTO;
import cc.newex.dax.users.dto.membership.UserBrokerDTO;
import cc.newex.dax.users.dto.membership.UserDetailInfoResDTO;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import cc.newex.dax.users.dto.membership.UserSettingsDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@FeignClient(value = "${newex.feignClient.dax.users}", path = "/admin/v1/users")
public interface UsersAdminClient {

    /**
     * @param userId
     * @description 查询用户信息
     * @date 2018/4/27 上午10:32
     */
    @GetMapping(value = "/individual/{userId}")
    ResponseResult<UserInfoResDTO> queryUserInfo(@PathVariable("userId") final long userId,
                                                 @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    /**
     * 查询用户信息 支持 uid, email,mobile,realname 查询
     *
     * @param pager
     * @return
     */
    @PostMapping(value = "/individual/list")
    ResponseResult list(@RequestBody final DataGridPager<UserInfoResDTO> pager);

    /**
     * 解绑用户google
     *
     * @param userId
     * @return
     */
    @PutMapping(value = "/individual/google-code-bind/{userId}")
    ResponseResult unbindGoogle(@PathVariable("userId") final long userId,
                                @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    /**
     * 解绑手机
     *
     * @param userId
     * @return
     */
    @PutMapping(value = "/individual/mobile-bind/{userId}")
    ResponseResult unbindMobile(@PathVariable("userId") final long userId,
                                @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    /**
     * 更换用户邮箱
     *
     * @param userId
     * @param newEmail
     * @param brokerId
     * @return
     */
    @PutMapping(value = "/individual/email/{userId}")
    ResponseResult updateUserEmail(@PathVariable("userId") final Long userId,
                                   @RequestParam("newEmail") final String newEmail,
                                   @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    /**
     * 更换用户手机号
     *
     * @param userId
     * @param newMobile
     * @param brokerId
     * @return
     */
    @PutMapping(value = "/individual/mobile/{userId}")
    ResponseResult updateUserMobile(@PathVariable("userId") final Long userId,
                                    @RequestParam("newMobile") final String newMobile,
                                    @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    /**
     * @description 查询kyc列表信息
     * @date 2018/4/27 上午10:32
     */
    @PostMapping(value = "/kyc/list")
    ResponseResult<PageResultDTO> list(@RequestBody UserKycAdminListReqDTO userKycAdminListReqDTO);

    /**
     * 删除kyc信息
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/kyc/delete/{userId}")
    ResponseResult deleteKycInfo(@PathVariable("userId") final Long userId);

    /**
     * @param userId
     * @param level
     * @description 获取kyc等级1详情信息
     * @date 2018/5/21 下午4:44
     */
    @GetMapping(value = "/kyc/first/{userId}")
    ResponseResult<UserKycAdminKycFirstDetailDTO> firstDetail(@PathVariable("userId") final long userId,
                                                              @RequestParam("level") final Integer level);

    /**
     * @param userId
     * @param level
     * @description 获取 kyc 等级2的审核信息
     * @date 2018/5/23 上午11:00
     */
    @GetMapping(value = "/kyc/second/{userId}")
    ResponseResult<UserKycAdminKycSecondDetailDTO> secondDetail(@PathVariable("userId") final long userId,
                                                                @RequestParam("level") final Integer level);

    /**
     * @param id
     * @param audit
     * @description kyc审核
     * @date 2018/5/22 下午3:19
     */
    @PostMapping(value = "/kyc/audit/{id}")
    ResponseResult auditKyc(@PathVariable("id") final long id,
                            @RequestBody final AdminUserKycAuditDTO audit);

    /**
     * 提供根据userId的查询接口
     */
    @PostMapping("/api-secrets/list/{userId}")
    ResponseResult getUserSecret(@RequestBody final DataGridPager pager,
                                 @PathVariable("userId") final long userId);

    /**
     * 查询所有的api secrets
     *
     * @param userId
     * @return
     */
    @PostMapping("/api-secrets/list")
    ResponseResult getUserSecret(@RequestBody final DataGridPager pager,
                                 @RequestParam(value = "userId", required = false) final long userId,
                                 @RequestParam(value = "apiKey", required = false) final String apiKey,
                                 @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    /**
     * @param id 主键id
     * @description 通过id查询apikey对象
     * @date 2018/6/4 下午4:06
     */
    @GetMapping(value = "/api-secrets/{id}")
    ResponseResult<UserApiSecretResDTO> detail(@NotNull @PathVariable("id") final Long id);

    /**
     * @param id           主键id
     * @param rateLimit    限流设置
     * @param ipWhiteLists ip白名单
     * @description 通过主键id修改限流配置
     * @date 2018/6/4 下午4:12
     */
    @PutMapping("/api-secrets/{id}")
    ResponseResult updateRateLimit(@NotNull @PathVariable("id") final Long id,
                                   @RequestParam("rateLimit") final String rateLimit,
                                   @RequestParam(value = "ipWhiteLists", defaultValue = "") final String ipWhiteLists,
                                   @RequestParam(value = "authorities", defaultValue = "[]") final String authorities);

    //----------------------- IpRateLimit ---------------------------------------------------------------------------//

    @PostMapping(value = "/api-rate-limits")
    ResponseResult<Integer> addUserIpRateLimit(@RequestBody UserIpRateLimitDTO dto);

    @PutMapping(value = "/api-rate-limits")
    ResponseResult<Integer> editUserIpRateLimit(@RequestBody UserIpRateLimitDTO dto);

    @PostMapping("/api-rate-limits/pager")
    ResponseResult getUserIpRateLimitList(@RequestBody final DataGridPager pager,
                                          @RequestParam("fieldName") final String fieldName,
                                          @RequestParam("keyword") final String keyword,
                                          @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    @GetMapping(value = "/api-rate-limits/{id}")
    ResponseResult<UserIpRateLimitDTO> getUserIpRateLimit(@PathVariable("id") Integer id);

    @DeleteMapping(value = "/api-rate-limits/{id}")
    ResponseResult<Integer> removeUserIpRateLimit(@PathVariable("id") Integer id);

    @GetMapping(value = "/api-rate-limits/refresh")
    ResponseResult refreshUserIpRateLimitCache();

    //--------countries---------------//

    /**
     * 获取国家列表
     *
     * @param pager
     * @return
     */
    @PostMapping("/individual/countries/list")
    ResponseResult getDictCountry(@RequestBody final DataGridPager<DictCountryReqDTO> pager);

    /**
     * 添加国家信息
     *
     * @param dictCountryReqDTO
     * @return
     */
    @PostMapping("/individual/countries")
    ResponseResult addDictCountry(@RequestBody final DictCountryReqDTO dictCountryReqDTO);

    /**
     * 编辑国家信息
     *
     * @param id
     * @param dictCountryReqDTO
     * @return
     */
    @PutMapping("/individual/countries/{id}")
    ResponseResult updateDictCountry(@PathVariable("id") final Integer id,
                                     @RequestBody final DictCountryReqDTO dictCountryReqDTO);

    /**
     * 删除国家信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/individual/countries/{id}")
    ResponseResult remove(@PathVariable("id") final Integer id);

    /**
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @description 根据开始时间和结束时间查询kyc1, kyc2这段时间的认证人数
     * @date 2018/6/25 下午1:45
     */
    @GetMapping("/individual/user-stat")
    ResponseResult<UserInfoStatDTO> getUserInfoStat(@RequestParam("startTime") final Long startTime,
                                                    @RequestParam("endTime") final Long endTime);

    /**
     * 查看用户的当前登录session信息
     *
     * @param userId 用户id
     * @return cc.newex.commons.ucenter.model.SessionInfo
     */
    @GetMapping(value = "/individual/{userId}/session")
    ResponseResult getSession(@PathVariable("userId") final long userId);

    //--------frozen start---------------//

    @GetMapping("/frozen/global/list")
    ResponseResult listGlobalFrozenStatus(@RequestParam(value = "brokerId", required = false) final Integer brokerId);

    @PostMapping(value = "/frozen/global")
    ResponseResult addGlobalFrozenStatus(@RequestBody GlobalFrozenConfigDTO dto);

    @PutMapping(value = "/frozen/global")
    ResponseResult editGlobalFrozenStatus(@RequestBody GlobalFrozenConfigDTO dto);

    /**
     * 设置全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     *
     * @param name   {@link cc.newex.dax.users.dto.common.GlobalFrozenEnum}
     * @param status 0：解冻 1：冻结
     * @return
     */
    @PutMapping(value = "/frozen/global/{name}")
    ResponseResult editGlobalFrozenStatus(
            @PathVariable("name") final String name,
            @RequestParam("status") final Integer status);

    /**
     * 获取全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     *
     * @param name {@link cc.newex.dax.users.dto.common.GlobalFrozenEnum}
     */
    @GetMapping(value = "/frozen/global/{name}")
    ResponseResult getGlobalFrozenStatus(@PathVariable("name") final String name);

    @DeleteMapping(value = "/frozen/global/{id}")
    ResponseResult deleteGlobalFrozenStatus(@PathVariable("id") final Integer id);

    /**
     * 冻结用户所有交易业务(spot,c2c,contracts等)
     *
     * @param bizType {@link cc.newex.dax.users.dto.common.BizTypeEnum}
     * @param userId  用户idlist
     * @param status  冻结状态 0:解冻,1:冻结
     * @param remark  原因
     */
    @PutMapping(value = "/frozen/{bizType}/{userId}")
    ResponseResult frozen(
            @PathVariable("bizType") final String bizType,
            @PathVariable("userId") final long userId,
            @RequestParam("status") final Integer status,
            @RequestParam("remark") final String remark);

    /**
     * 冻结用户所有交易业务(spot,c2c,contracts等)
     *
     * @param bizType {@link cc.newex.dax.users.dto.common.BizTypeEnum}
     * @param userId  用户id
     */
    @GetMapping(value = "/frozen/{bizType}/{userId}")
    ResponseResult getFrozenStatus(
            @PathVariable("bizType") final String bizType,
            @PathVariable("userId") final long userId);

    /**
     * @param userId
     * @description 解除用户24小时提现限制
     * @date 2018/7/7 下午4:43
     */
    @PostMapping("/individual/free-withdraw-limit/{userId}")
    ResponseResult freeWithdrawLimit(@PathVariable("userId") final long userId);
    //--------frozen end ---------------//

    @GetMapping("/individual/{userId}/settings")
    ResponseResult<UserSettingsDTO> getUserSettings(@PathVariable("userId") final long userId);

    @GetMapping("/individual/{userId}/detail")
    ResponseResult<UserDetailInfoResDTO> getUserDetailInfo(@PathVariable("userId") final long userId,
                                                           @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    /**
     * 查询BM用户邀请列表
     *
     * @return
     */
    @RequestMapping("/individual/invite-list")
    ResponseResult inviteList(@RequestBody final UserInviteReqDTO dto);

    /**
     * 用户身份和邀请用户身份信息
     *
     * @return
     */
    @GetMapping("/individual/invite-detail/{userId}")
    ResponseResult<UserInviteDetailDTO> inviteDetail(@PathVariable("userId") final long userId);

    /**
     * 分页查询channel信息
     *
     * @param pager
     * @param channelCode
     * @param channelName
     * @param channelFullName
     * @return
     */
    @PostMapping(value = "/channel/pager")
    ResponseResult<DataGridPagerResult<UserInvitationChannelDTO>> getUserInvitationChannelDTO(@RequestBody final DataGridPager pager,
                                                                                              @RequestParam(value = "channelCode", required = false) final String channelCode,
                                                                                              @RequestParam(value = "channelName", required = false) final String channelName,
                                                                                              @RequestParam(value = "channelFullName", required = false) final String channelFullName);

    /**
     * 生成channelCode
     *
     * @return
     */
    @GetMapping(value = "/channel/generate")
    ResponseResult<UserInvitationChannelDTO> generateChannelCode();

    /**
     * 增加channel
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/channel")
    ResponseResult<Integer> addUserInvitationChannelDTO(@RequestBody final UserInvitationChannelDTO dto);

    /**
     * 修改channel
     *
     * @param dto
     * @return
     */
    @PutMapping(value = "/channel")
    ResponseResult<Integer> editUserInvitationChannelDTO(@RequestBody final UserInvitationChannelDTO dto);

    /**
     * 删除channel
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/channel/{id}")
    ResponseResult<Integer> remove(@PathVariable("id") final Long id);

    /**
     * 文件上传
     *
     * @param file
     * @param userId
     * @return
     */
    @PostMapping("/file/info/upload")
    ResponseResult fileInfoUploadFile(@RequestParam("file") final MultipartFile file, @RequestParam(value = "userId") final Long userId);

    /**
     * 文件新增
     */
    @PostMapping(value = "/file/info")
    ResponseResult<Integer> fileInfoAdd(@RequestBody final UserFileInfoDTO dto);

    /**
     * 文件删除
     */
    @DeleteMapping(value = "/file/info/{id}")
    ResponseResult<Integer> fileInfoRemove(@PathVariable("id") final Long id);

    /**
     * 文件修改
     */
    @PutMapping(value = "/file/info")
    ResponseResult<Integer> fileInfoEdit(@RequestBody final UserFileInfoDTO dto);

    /**
     * 文件分页查询
     */
    @PostMapping(value = "/file/info/pager")
    ResponseResult<DataGridPagerResult<UserFileInfoDTO>> fileInfoGetPagerList(@RequestBody final DataGridPager pager,
                                                                              @RequestParam(value = "userId", required = false) final Long userId,
                                                                              @RequestParam(value = "fileType", required = false) final String fileType,
                                                                              @RequestParam(value = "fileName", required = false) final String fileName,
                                                                              @RequestParam(value = "filePath", required = false) final String filePath,
                                                                              @RequestParam(value = "note", required = false) final String note);

    /**
     * 文件根据id查询
     */
    @GetMapping(value = "/file/info/{id}")
    ResponseResult<UserFileInfoDTO> fileInfoGet(@PathVariable("id") final Long id);

    /**
     * 分页查询record接口
     *
     * @param pager
     * @return
     */
    @PostMapping("/security/records/pager")
    ResponseResult getSecureEvent(@RequestBody final DataGridPager pager,
                                  @RequestParam(value = "userId") final long userId);

    /**
     * 获取券商列表
     */
    @PostMapping("/individual/broker/list")
    ResponseResult selectBrokerList(@RequestBody final DataGridPager<UserBrokerDTO> pager);

    /**
     * 新增券商
     *
     * @param dto
     * @return
     */
    @PostMapping("/individual/broker")
    ResponseResult create(@RequestBody final UserBrokerDTO dto);

    /**
     * 更新券商
     *
     * @param id
     * @param dto
     * @return
     */
    @PostMapping("/individual/broker/{id}")
    ResponseResult updateBroker(@PathVariable("id") final long id, @RequestBody final UserBrokerDTO dto);

    /**
     * 获取券商列表
     */
    @GetMapping("/individual/broker/all")
    ResponseResult<List<UserBrokerDTO>> brokerList();
}
