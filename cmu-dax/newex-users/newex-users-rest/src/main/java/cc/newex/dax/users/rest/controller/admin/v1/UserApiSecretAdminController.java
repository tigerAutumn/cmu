package cc.newex.dax.users.rest.controller.admin.v1;

import cc.newex.commons.dictionary.consts.ApiKeyConsts;
import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.criteria.UserApiSecretExample;
import cc.newex.dax.users.domain.UserApiSecret;
import cc.newex.dax.users.dto.admin.UserApiSecretResDTO;
import cc.newex.dax.users.service.admin.UserApiSecretAdminService;
import cc.newex.dax.users.service.membership.UserApiSecretService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/v1/users/api-secrets")
public class UserApiSecretAdminController {
    @Autowired
    private UserApiSecretService apiSecretService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserApiSecretAdminService userApiSecretAdminService;

    /**
     * 提供根据userId的查询接口
     */
    @RequestMapping("/list/{userId}")
    public ResponseResult<DataGridPagerResult<UserApiSecretResDTO>> getUserSecret(
            @RequestBody final DataGridPager pager,
            @PathVariable("userId") final long userId) {
        if (userId <= 0) {
            ResultUtils.failure(BizErrorCodeEnum.APIKEY_NOT_EXIST);
        }

        final PageInfo pageInfo = pager.toPageInfo();
        final UserApiSecretExample example = new UserApiSecretExample();
        example.createCriteria()
                .andUserIdEqualTo(userId);

        final List<UserApiSecret> apiSecretList = this.userApiSecretAdminService.getByPage(pageInfo, example);
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), this.replaceSenstiveInfo(apiSecretList)));
    }

    /**
     * 提供查询所有的api列表接口
     */
    @RequestMapping("/list")
    public ResponseResult<DataGridPagerResult<UserApiSecretResDTO>> getUserSecret(
            @RequestBody final DataGridPager pager,
            @RequestParam(value = "userId", required = false) final Long userId,
            @RequestParam(value = "apiKey", required = false) final String apiKey,
            @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<UserApiSecret> apiSecretList = this.userApiSecretAdminService.listByPage(pageInfo, userId, apiKey, brokerId);
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), this.replaceSenstiveInfo(apiSecretList)));
    }

    /**
     * 提供根据apikey的查询接口
     *
     * @param apiKey
     * @return
     */
    @GetMapping("/keys/{apiKey}")
    public ResponseResult<UserApiSecretResDTO> getApiSecretByApiKey(@NotBlank @PathVariable final String apiKey) {
        if (StringUtils.isBlank(apiKey)) {
            ResultUtils.failure(BizErrorCodeEnum.APIKEY_NOT_EXIST);
        }

        final UserApiSecret userApiSecret = this.apiSecretService.getApiSecret(apiKey);
        final UserApiSecretResDTO resDTO = ObjectCopyUtils.map(userApiSecret, UserApiSecretResDTO.class);

        return ResultUtils.success(resDTO);
    }

    /**
     * @param id 主键id
     * @description 通过id查询apikey对象
     * @date 2018/6/4 下午4:06
     */
    @GetMapping(value = "/{id}")
    public ResponseResult<UserApiSecretResDTO> detail(@NotNull @PathVariable("id") final Long id) {
        if (id <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY, null);
        }

        final UserApiSecret apiSecret = this.apiSecretService.getApiSecretById(id);
        if (ObjectUtils.isEmpty(apiSecret)) {
            log.error("id: {} apiSecret is not exist ", id);
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_NOT_EXIST, null);
        }

        final UserApiSecretResDTO userApiSecretResDTO = ObjectCopyUtils.map(apiSecret, UserApiSecretResDTO.class);
        userApiSecretResDTO.setIpWhiteLists(this.ipListConverter(userApiSecretResDTO.getIpWhiteLists(), x -> IpUtil.longToString(Long.valueOf(x))));
        return ResultUtils.success(userApiSecretResDTO);
    }

    /**
     * @param id           主键id
     * @param rateLimit    限流设置
     * @param ipWhiteLists ip白名单
     * @description 通过主键id修改限流配置
     * @date 2018/6/4 下午4:12
     */
    @PutMapping("/{id}")
    public ResponseResult updateRateLimit(@NotNull @PathVariable("id") final Long id,
                                          @RequestParam("rateLimit") final String rateLimit,
                                          @RequestParam(value = "ipWhiteLists", defaultValue = "") final String ipWhiteLists,
                                          @RequestParam(value = "authorities", defaultValue = "[]") final String authorities) {
        final UserApiSecret apiSecret = this.apiSecretService.getApiSecretById(id);
        if (ObjectUtils.isEmpty(apiSecret)) {
            log.error("id: {} apiSecret is not exist ", id);
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_NOT_EXIST);
        }

        final UserApiSecret po = UserApiSecret.builder()
                .id(apiSecret.getId())
                .rateLimit(rateLimit)
                .authorities(authorities)
                .ipWhiteLists(this.ipListConverter(ipWhiteLists, x -> String.valueOf(IpUtil.toLong(x))))
                .build();
        final int optResult = this.apiSecretService.update(po);
        if (optResult <= 0) {
            log.error("update database operate fail! apiSecret: {}", JSONObject.toJSONString(po));
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_OPERATE_FAIL);
        }

        //修改后删除缓存中的值
        this.stringRedisTemplate.delete(ApiKeyConsts.OPEN_API_KEY_PREFIX + apiSecret.getApiKey());
        return ResultUtils.success();
    }

    private List<UserApiSecretResDTO> replaceSenstiveInfo(final List<UserApiSecret> apiSecretList) {
        final List<UserApiSecretResDTO> apiSecretResDTOList = Lists.newArrayListWithCapacity(CollectionUtils.size(apiSecretList));
        apiSecretList.forEach(item -> {
            item.setApiKey(StringUtil.getStarString(item.getApiKey(), 9, item.getApiKey().length()));
            item.setPassphrase(StringUtils.EMPTY);
            item.setSecret(StringUtils.EMPTY);
            item.setIpWhiteLists(this.ipListConverter(item.getIpWhiteLists(), x -> IpUtil.longToString(Long.valueOf(x))));
            final UserApiSecretResDTO resVO = ObjectCopyUtils.map(item, UserApiSecretResDTO.class);
            apiSecretResDTOList.add(resVO);
        });
        return apiSecretResDTOList;
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
}
