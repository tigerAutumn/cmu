package cc.newex.dax.users.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.dto.admin.UserIpRateLimitDTO;
import cc.newex.dax.users.dto.apisecret.UserApiSecretResDTO;
import cc.newex.dax.users.dto.common.UserLevelEnum;
import cc.newex.dax.users.dto.kyc.KycUserLevelDTO;
import cc.newex.dax.users.dto.membership.SignUpReqDTO;
import cc.newex.dax.users.dto.membership.UserActivityConfigDTO;
import cc.newex.dax.users.dto.membership.UserFiatResDTO;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import cc.newex.dax.users.dto.membership.UserInviteDTO;
import cc.newex.dax.users.dto.membership.UserInviteRecordDTO;
import cc.newex.dax.users.dto.membership.UserStatisticsInfoDTO;
import cc.newex.dax.users.dto.security.UserFiatSettingDTO;
import cc.newex.dax.users.dto.security.WithdrawCheckCodeDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@FeignClient(value = "${newex.feignClient.dax.users}", path = "/inner/v1/users")
public interface UsersClient {

    /**
     * @param userId
     * @return
     */
    @GetMapping("/membership/{userId}")
    ResponseResult<UserInfoResDTO> getUserInfo(@PathVariable(value = "userId") long userId,
                                               @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    /**
     * @param userId
     * @return
     */
    @GetMapping("/membership/{userId}/detail")
    ResponseResult<UserInfoResDTO> getUserDetailInfo(@PathVariable(value = "userId") long userId,
                                                     @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    /**
     * @param passphrase
     * @param apiKey
     * @description 根据用户apikey和密码获取用户信息
     * @date 2018/5/2 下午2:19
     */
    @GetMapping("/userInfo/{apiKey}")
    ResponseResult<UserInfoResDTO> getUserInfoByApiKey(@PathVariable("apiKey") final String apiKey, @RequestParam("passphrase") final String passphrase);

    /**
     * @param apiKey
     * @return
     */
    @GetMapping("/api-secrets/{apiKey}")
    ResponseResult<UserApiSecretResDTO> getUserApiSecret(@PathVariable("apiKey") String apiKey);

    /**
     * 按照ip 查询 user ip rate info
     *
     * @param ip
     * @return
     */
    @GetMapping("/ip-rate-limit/{ip}")
    ResponseResult<UserIpRateLimitDTO> getUserIpRateLimit(@PathVariable("ip") final Long ip);

    /**
     * @param userId
     * @description 通过userId获取用户kyc认证的最高等级
     * @date 2018/5/17 下午4:14
     */
    @GetMapping("/kyc/{userId}")
    ResponseResult<Integer> getUserKycLevel(@PathVariable("userId") final long userId);

    @GetMapping("/kyc/userLevels")
    ResponseResult<List<KycUserLevelDTO>> getUserKycLevels(@RequestParam("userIdList") final List<Long> userIdList);

    /**
     * @param userId
     * @description 通过userId获取法币设置列表
     * @date 2018/5/17 下午8:01
     */
    @GetMapping("/fiat/{userId}")
    ResponseResult<List<UserFiatSettingDTO>> getUserFiatSettingList(@PathVariable("userId") final long userId);

    /**
     * @param userId
     * @description 通过用户id获取法币交易需要的用户名, 手机号和邮箱
     * @date 2018/5/17 下午8:16
     */
    @GetMapping("/fiat-user/{userId}")
    ResponseResult<UserFiatResDTO> getUserFiatRes(@PathVariable("userId") final long userId);

    /**
     * @param id       分页id
     * @param pageSize 分页大小,默认1000
     * @description 通过分页id获取邀请好友列表
     * @date 2018/5/29 下午8:28
     */
    @GetMapping("/invite/{id}")
    ResponseResult<List<UserInviteRecordDTO>> queryUserInviteList(@PathVariable("id") final Long id,
                                                                  @RequestParam(value = "pageSize", defaultValue = "1000") final Integer pageSize);

    /**
     * 分页查询
     *
     * @param id
     * @param startDate
     * @param endDate
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/invite/page")
    ResponseResult<List<UserInviteRecordDTO>> queryUserInviteList(@RequestParam("id") final Long id,
                                                                  @RequestParam("startDate") final Date startDate,
                                                                  @RequestParam("endDate") final Date endDate,
                                                                  @RequestParam(value = "pageIndex", defaultValue = "1") final Integer pageIndex,
                                                                  @RequestParam(value = "pageSize", defaultValue = "1000") final Integer pageSize);

    /**
     * @param userId
     * @param activityCode 活动编码
     * @description 通过用户id获取邀请用户信息
     * @date 2018/5/30 下午3:57
     */
    @GetMapping("/{activityCode}/{userId}")
    ResponseResult<UserInviteDTO> queryUserInviteInfo(@PathVariable("userId") final Long userId, @PathVariable("activityCode") final String activityCode,
                                                      @RequestParam(value = "brokerId", required = false) final Integer brokerId);

    /**
     * @param idList
     * @description 通过用户id返回通过kyc2认证的用户id列表
     * @date 2018/6/1 下午4:04
     */
    @GetMapping("/kyc2/pass-list")
    ResponseResult<List<Long>> queryKyc2PassIdList(@RequestParam("idList") final List<Long> idList);

    /**
     * 活动下线
     *
     * @param activityCode 活动编码
     * @return
     */
    @PostMapping("/offline/{activityCode}")
    ResponseResult<Integer> offline(@PathVariable("activityCode") final String activityCode);

    /**
     * 获取最新活动配置
     *
     * @return
     */
    @GetMapping("/activity-config")
    ResponseResult<UserActivityConfigDTO> getActivityConfig(@RequestParam(value = "activityCode", required = false) final String activityCode);

    /**
     * 提币时验证码校验
     *
     * @param verificationCode
     * @param behavior
     * @param userId
     * @return
     */
    @PostMapping("/verification/single-code/{userId}")
    ResponseResult checkVerificationCode(@RequestParam("verificationCode") final String verificationCode,
                                         @RequestParam("behavior") final Integer behavior,
                                         @PathVariable("userId") final long userId);

    /**
     * 提币时验证码校验
     *
     * @param verificationCode
     * @param userId
     * @return
     */
    @PostMapping("/verification/asset/withdraw/{userId}")
    ResponseResult verifyUserWithdrawAsset(@RequestParam("verificationCode") final String verificationCode,
                                           @PathVariable("userId") final long userId);

    /**
     * 提现风控查询card number
     *
     * @param userId
     * @return
     */
    @GetMapping("/verification/asset/get-card-number/{userId}")
    ResponseResult<String> getCardNumber(@PathVariable("userId") final long userId);

    /**
     * 提现时验证码校验
     *
     * @param form
     * @param userId
     * @return
     */
    @PostMapping("/verification/asset/withdraw-limit/{userId}")
    ResponseResult withdrawLimit(@RequestBody final WithdrawCheckCodeDTO form, @PathVariable("userId") final long userId);

    /**
     * @param userName
     * @description 根据手机号或者邮箱获取用户信息
     * @date 2018/7/6 下午4:07
     */
    @GetMapping("/membership/user-info")
    ResponseResult<UserInfoResDTO> getUserInfoByUserName(@RequestParam("userName") final String userName);

    /**
     * 通过用户id获取用户等级枚举
     *
     * @param userId
     * @return
     */
    @GetMapping("/user-level/{userId}")
    ResponseResult<UserLevelEnum> getUserLevel(@PathVariable("userId") final long userId);

    /**
     * @param level  用户等级
     * @param userId 用户id
     * @description 修改用户的等级
     * @date 2018/7/7 下午2:09
     */
    @PostMapping("/user-level/update/{userId}")
    ResponseResult updateUserLevel(@RequestParam("level") final String level,
                                   @PathVariable("userId") final long userId);

    @GetMapping("/user/statistics")
    ResponseResult<List<UserStatisticsInfoDTO>> getUserStatistics(@RequestParam("beginTime") final Long beginTime,
                                                                  @RequestParam("endTime") final Long endTime);

    @PostMapping("/sign-up")
    ResponseResult signUp(@RequestBody @Valid final SignUpReqDTO signUpReqDTO);

    /**
     * 根据手机号或者邮箱获取用户id
     *
     * @param username
     */
    @GetMapping("/membership/user-info/user-id")
    ResponseResult<Long> getUserIdByUsername(@RequestParam("username") final String username);

    /**
     * 通过host获取brokerId
     * host:需要传域名,如www.coinmex.com,test.cmx.com
     *
     * @param host
     * @return
     */
    @GetMapping("/broker/detail")
    ResponseResult<Integer> getBrokerId(@RequestParam("host") final String host);

    /**
     * 查询用户ID对应的uid
     *
     * @param userIdList
     * @return
     */
    @GetMapping("/uid")
    ResponseResult<List<String>> getUid(@RequestParam("ids") final List<Long> userIdList);

    /**
     * 更新永续合约协议
     *
     * @param userId
     * @param perpetualProtocolFlag
     * @return
     */
    @PostMapping(value = "/settings/perpetualProtocolFlag")
    ResponseResult<Integer> updatePerpetualProtocolFlag(@RequestParam("userId") final Long userId,
                                                        @RequestParam("perpetualProtocolFlag") final Integer perpetualProtocolFlag);

    /**
     * 判断当前用户当天是否登录过
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/{userId}/logged/today")
    ResponseResult<Boolean> isLoginWithToday(@PathVariable(value = "userId") Long userId);

    /**
     * 判断是否24小时内修改过安全设置  true:是 false:否
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/withdraw24HoursLimit/{userId}")
    ResponseResult<Boolean> withdraw24HoursLimit(@PathVariable(value = "userId") final Long userId);

}