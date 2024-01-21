package cc.newex.dax.users.rest.controller.outer.v1.common.individual;

import cc.newex.commons.dictionary.consts.ApiKeyConsts;
import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.openapi.specs.config.OpenApiKeyConfig;
import cc.newex.commons.openapi.specs.consts.PermissionType;
import cc.newex.commons.openapi.specs.util.OpenApiUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.ApiSecretConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.domain.UserApiSecret;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.rest.model.UserApiSecretDeleteReqVO;
import cc.newex.dax.users.rest.model.UserApiSecretReqVO;
import cc.newex.dax.users.rest.model.UserApiSecretResVO;
import cc.newex.dax.users.service.membership.UserApiSecretService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.verification.CheckCodeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;


/**
 * 用户ApiKey信息
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/users/api-secrets")
public class UserApiSecretController extends BaseController {

    @Autowired
    private UserApiSecretService apiSecretService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private OpenApiKeyConfig openApiKeyConfig;

    @GetMapping(value = "")
    public ResponseResult list(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        final Integer brokerId = this.getBrokerId(request);

        return ResultUtils.success(this.getNonSensitiveUserApiSecrets(userId, brokerId));
    }

    @RetryLimit(type = RetryLimitTypeEnum.API_SECRETS)
    @PostMapping(value = "")
    public ResponseResult create(@RequestBody @Valid final UserApiSecretReqVO form,
                                 final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final ResponseResult verifyResult = this.checkCodeService.checkCodeByBehavior(userId, BehaviorTheme.USERS7_API_CREATE_1,
                form.getVerificationCode());
        if (verifyResult != null && verifyResult.getCode() > 0) {
            log.error("UserApiSecretController create check error! userid={},verifyResult=()", userId, verifyResult);
            return verifyResult;
        }

        final long userApiSecretNum = this.apiSecretService.count(userId);
        if (userApiSecretNum >= ApiSecretConsts.API_KEY_MAX_NUMBER) {
            log.error("user's api key is too much! apikey count is: {}", userApiSecretNum);
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_ADD_MAX_LIMIT);
        }

        if (this.apiSecretService.existLabel(form.getLabel(), userId)) {
            log.error("apiKey label is exists! label: {}, userId: {}", form.getLabel(), userId);
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_NAME_REPEAT);
        }

        final long expiredTime = DateUtils.addYears(Calendar.getInstance().getTime(), 1).getTime();
        final String apiKey = OpenApiUtil.generateKey(this.openApiKeyConfig.getKeyAlgorithm(), this.openApiKeyConfig.getPrefix());
        final String secret = OpenApiUtil.generateSecret(this.openApiKeyConfig.getSecretAlgorithm(), String.format("%s-%s-%s", apiKey, form.getPassphrase(), expiredTime));
        final String salt = OpenApiUtil.generatePassphraseSalt(apiKey, this.openApiKeyConfig.getPassphraseSaltConfig());
        final String passphrase = OpenApiUtil.encodePassphrase(this.openApiKeyConfig.getPassphraseAlgorithm(), form.getPassphrase(), salt);
        final Integer brokerId = this.getBrokerId(request);

        final UserApiSecret apiSecret = UserApiSecret.builder()
                .userId(userId)
                .apiKey(apiKey)
                .secret(secret)
                .ipAddress(IpUtil.toLong(IpUtil.getRealIPAddress(request)))
                .label(form.getLabel())
                .expiredTime(expiredTime)
                .authorities(JSON.toJSONString(this.parseAuthorities(form.getAuthorities())))
                .passphrase(passphrase)
                .ipWhiteLists(this.ipListConverter(form.getIpWhiteLists(), x -> String.valueOf(IpUtil.toLong(x))))
                .createdDate(new Date())
                .updatedDate(new Date())
                .brokerId(brokerId)
                .build();

        this.apiSecretService.save(apiSecret);
        if (apiSecret.getId() <= 0) {
            log.error("create apiSecret error: {}", JSONObject.toJSONString(apiSecret));
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_OPERATE_FAIL);
        }

        final UserApiSecretResVO vo = ObjectCopyUtils.map(apiSecret, UserApiSecretResVO.class);
        vo.setIpWhiteLists(form.getIpWhiteLists());
        vo.setPassphrase("");
        vo.setSecret("");
        return ResultUtils.success(vo);
    }

    private String ipListConverter(final String ipWhiteLists, final Function<String, String> func) {
        final String[] ipLists = StringUtils.split(ipWhiteLists, ',');
        if (ArrayUtils.isEmpty(ipLists)) {
            return ipWhiteLists;
        }
        for (int i = 0; i < ipLists.length; i++) {
            ipLists[i] = func.apply(ipLists[i]);
        }
        return StringUtils.join(ipLists, ',');
    }

    @RetryLimit(type = RetryLimitTypeEnum.API_SECRETS)
    @GetMapping(value = "/{id}")
    public ResponseResult detail(@NotNull @PathVariable("id") final Long id,
                                 @RequestParam("verificationCode") final String verificationCode,
                                 final HttpServletRequest request) {
        if (id <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final ResponseResult verifyResult = this.checkCodeService.checkCodeByBehavior(userId,
                BehaviorTheme.USERS7_API_DETAIL_2, verificationCode);
        if (verifyResult != null && verifyResult.getCode() > 0) {
            log.error("UserApiSecretController create check error! userid={},verifyResult=()", userId, verifyResult);
            return verifyResult;
        }

        final UserApiSecret apiSecret = this.apiSecretService.getApiSecret(userId, id);
        if (ObjectUtils.isEmpty(apiSecret) || apiSecret.getUserId() != userId) {
            log.error("id: {}, userId: {} apiSecret is not exist or not belong to current user", id, userId);
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_NOT_EXIST);
        }

        final UserApiSecretResVO userApiSecretResDTO = ObjectCopyUtils.map(apiSecret, UserApiSecretResVO.class);
        userApiSecretResDTO.setAuthorities(this.parseAuthorities(apiSecret.getAuthorities()));
        userApiSecretResDTO.setIpWhiteLists(this.ipListConverter(apiSecret.getIpWhiteLists(), x -> IpUtil.longToString(Long.valueOf(x))));
        return ResultUtils.success(userApiSecretResDTO);
    }

    @RetryLimit(type = RetryLimitTypeEnum.API_SECRETS)
    @PutMapping("/{id}")
    public ResponseResult update(@NotNull @PathVariable("id") final Long id,
                                 @RequestBody @Valid final UserApiSecretReqVO form,
                                 final HttpServletRequest request) {
        if (Objects.isNull(id) || id <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final ResponseResult verifyResult = this.checkCodeService.checkCodeByBehavior(userId,
                BehaviorTheme.USERS7_API_UPDATE_3, form.getVerificationCode());
        if (verifyResult != null && verifyResult.getCode() > 0) {
            log.error("UserApiSecretController create check error! userid={},verifyResult=()", userId, verifyResult);
            return verifyResult;
        }
        final UserApiSecret apiSecret = this.apiSecretService.getApiSecret(userId, id);
        if (ObjectUtils.isEmpty(apiSecret) || apiSecret.getUserId() != userId) {
            log.error("id: {}, userId: {} apiSecret is not exist or not belong to current user", id, userId);
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_NOT_EXIST);
        }

        if (this.apiSecretService.existLabel(form.getLabel(), userId, id)) {
            log.error("update, apiKey realName is repeat! label: {}, userId: {}", form.getLabel(), userId);
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_NAME_REPEAT);
        }
        final String ipAddress = IpUtil.getRealIPAddress(request);
        final String salt = OpenApiUtil.generatePassphraseSalt(apiSecret.getApiKey(), this.openApiKeyConfig.getPassphraseSaltConfig());
        final String passphrase = OpenApiUtil.encodePassphrase(this.openApiKeyConfig.getPassphraseAlgorithm(), form.getPassphrase(), salt);

        final UserApiSecret userApiSecret = UserApiSecret.builder()
                .id(id)
                .label(form.getLabel())
                .passphrase(passphrase)
                .authorities(JSON.toJSONString(this.parseAuthorities(form.getAuthorities())))
                .ipAddress(IpUtil.toLong(ipAddress))
                .ipWhiteLists(this.ipListConverter(form.getIpWhiteLists(), x -> String.valueOf(IpUtil.toLong(x))))
                .updatedDate(new Date())
                .build();
        final int optResult = this.apiSecretService.update(userApiSecret);
        if (optResult <= 0) {
            log.error("update database operate fail! apiSecret: {}", JSONObject.toJSONString(apiSecret));
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_OPERATE_FAIL);
        }
        /**
         * 修改后删除缓存中的值
         */
        this.stringRedisTemplate.delete(ApiKeyConsts.OPEN_API_KEY_PREFIX + apiSecret.getApiKey());

        final UserApiSecretResVO userApiSecretResDTO = ObjectCopyUtils.map(apiSecret, UserApiSecretResVO.class);
        userApiSecretResDTO.setLabel(form.getLabel());
        userApiSecretResDTO.setPassphrase(StringUtils.EMPTY);
        userApiSecretResDTO.setSecret(StringUtils.EMPTY);
        userApiSecretResDTO.setAuthorities(this.parseAuthorities(form.getAuthorities()));
        userApiSecretResDTO.setIpWhiteLists(this.ipListConverter(apiSecret.getIpWhiteLists(), x -> IpUtil.longToString(Long.valueOf(x))));
        return ResultUtils.success(userApiSecretResDTO);
    }

    @RetryLimit(type = RetryLimitTypeEnum.API_SECRETS)
    @DeleteMapping("/{id}")
    public ResponseResult delete(@NotNull @PathVariable("id") final Long id,
                                 @Valid final UserApiSecretDeleteReqVO form,
                                 final HttpServletRequest request) {
        if (Objects.isNull(id) || id <= 0) {
            log.error(" update, delete can't be null!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error(" delete user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        final ResponseResult verifyResult = this.checkCodeService.checkCodeByBehavior(userId,
                BehaviorTheme.USERS7_API_DELETE_4, form.getVerificationCode());

        if (verifyResult != null && verifyResult.getCode() > 0) {
            log.error("UserApiSecretController create check error! userid={},verifyResult=()", userId, verifyResult);
            return verifyResult;
        }

        final UserApiSecret apiSecret = this.apiSecretService.getApiSecret(userId, id);
        if (ObjectUtils.isEmpty(apiSecret) || apiSecret.getUserId() != userId) {
            log.error("id: {}, userId: {} apiSecret is not exist or not belong to current user", id, userId);
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_NOT_EXIST);
        }

        final int optResult = this.apiSecretService.delete(id, userId, apiSecret.getApiKey());
        if (optResult <= 0) {
            log.error("delete database operate fail! id: {}", id);
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_OPERATE_FAIL);
        }
        /**
         * 删除后删除缓存中的值
         */
        this.stringRedisTemplate.delete(ApiKeyConsts.OPEN_API_KEY_PREFIX + apiSecret.getApiKey());

        return ResultUtils.success();
    }

    /**
     * 获取脱敏过的用户api secret数据
     *
     * @param userId 用户id
     * @return 脱敏过的用户api secret数据
     */
    @NotNull
    private List<UserApiSecretResVO> getNonSensitiveUserApiSecrets(final long userId, final Integer brokerId) {
        final List<UserApiSecret> apiSecretList = this.apiSecretService.getApiSecrets(userId, brokerId);
        final List<UserApiSecretResVO> apiSecretResDTOList = Lists.newArrayListWithCapacity(CollectionUtils.size(apiSecretList));
        final ModelMapper modelMapper = new ModelMapper();

        apiSecretList.forEach(item -> {
            final UserApiSecretResVO resVO = new UserApiSecretResVO();
            item.setApiKey(StringUtil.getStarString(item.getApiKey(), 9, item.getApiKey().length()));
            item.setPassphrase(StringUtils.EMPTY);
            item.setSecret(StringUtils.EMPTY);
            item.setIpWhiteLists(this.ipListConverter(item.getIpWhiteLists(), x -> IpUtil.longToString(Long.valueOf(x))));
            resVO.setAuthorities(this.parseAuthorities(item.getAuthorities()));
            modelMapper.map(item, resVO);
            apiSecretResDTOList.add(resVO);
        });

        return apiSecretResDTOList;
    }

    private List<String> parseAuthorities(final String authorities) {
        final String readonly = "readonly";
        final Set<String> set = Sets.newHashSetWithExpectedSize(4);
        set.add(readonly);
        if (StringUtils.isNotBlank(authorities)) {
            final List<String> list = new ArrayList<>(JSONArray.parseArray(authorities, String.class));
            for (final String e : list) {
                if (PermissionType.PUBLIC_PERMISSIONS.contains(e)) {
                    set.add(e);
                }
            }
        }
        return new ArrayList<>(set);
    }
}
