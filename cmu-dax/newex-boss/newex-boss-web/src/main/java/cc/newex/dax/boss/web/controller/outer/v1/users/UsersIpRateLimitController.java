package cc.newex.dax.boss.web.controller.outer.v1.users;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.users.UserIprateLimitVo;
import cc.newex.dax.users.client.UsersAdminClient;
import cc.newex.dax.users.dto.admin.UserIpRateLimitDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 短信邮件提供者配置
 *
 * @author newex-team
 * @date 2018/5/28
 */
@Slf4j
@RestController
@RequestMapping("/v1/boss/users/ip-rate-limits")
public class UsersIpRateLimitController {
    @Autowired
    private UsersAdminClient usersAdminClient;

    @GetMapping(value = "/list")
    @OpLog(name = "查询OPEN API IP限流配置列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_IP_RATE_LIMIT_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, final DataGridPager pager, final String fieldName, final String keyword) {
        final String field = StringUtils.defaultIfBlank(fieldName, "");
        String word = StringUtils.defaultIfBlank(keyword, "");
        try {
            if ("ip".equals(field)) {
                word = String.valueOf(IpUtil.ipDotDec2Long(word));
            }
            final ResponseResult list = this.usersAdminClient.getUserIpRateLimitList(pager, field, word, loginUser.getLoginBrokerId());
            final JSONArray rows = ((JSONObject) list.getData()).getJSONArray("rows");
            for (final Object obj : rows) {
                final JSONObject json = (JSONObject) obj;
                final long ip = json.getLong("ip");
                final String sip = IpUtil.longToString(ip);
                json.put("ip", sip);
            }
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            log.error("list IP_RATE_LIMIT error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/add")
    @OpLog(name = "添加OPEN API IP限流配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_IP_RATE_LIMIT_ADD"})
    public ResponseResult add(@Valid final UserIprateLimitVo iprateLimitVo) {
        try {
            final Long ip = IpUtil.ipDotDec2Long(iprateLimitVo.getIp());
            final UserIpRateLimitDTO dto = UserIpRateLimitDTO.builder()
                    .ip(ip)
                    .rateLimit(iprateLimitVo.getRateLimit())
                    .memo(iprateLimitVo.getMemo())
                    .brokerId(iprateLimitVo.getBrokerId())
                    .build();
            final ResponseResult result = this.usersAdminClient.addUserIpRateLimit(dto);
            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("IP_RATE_LIMIT_ADD error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除OPEN API IP限流配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_IP_RATE_LIMIT_REMOVE"})
    public ResponseResult remove(final Integer id) {
        try {
            final ResponseResult result = this.usersAdminClient.removeUserIpRateLimit(id);
            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("IP_RATE_LIMIT_REMOVE error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑OPEN API IP限流配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_IP_RATE_LIMIT_EDIT"})
    public ResponseResult edit(@Valid final UserIprateLimitVo vo) {
        try {
            final Long ip = IpUtil.ipDotDec2Long(vo.getIp());
            final UserIpRateLimitDTO dto = UserIpRateLimitDTO.builder()
                    .id(vo.getId())
                    .ip(ip)
                    .rateLimit(vo.getRateLimit())
                    .memo(vo.getMemo())
                    .brokerId(vo.getBrokerId())
                    .build();
            final ResponseResult result = this.usersAdminClient.editUserIpRateLimit(dto);
            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("IP_RATE_LIMIT_EDIT error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    @OpLog(name = "查看OPEN API IP限流配置详情")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_IP_RATE_LIMIT_VIEW"})
    public ResponseResult getById(@PathVariable("id") final Integer id) {
        final ResponseResult result = this.usersAdminClient.getUserIpRateLimit(id);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/refresh")
    @OpLog(name = "刷新OPEN API IP限流配置缓存")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_IP_RATE_LIMIT_EDIT"})
    public ResponseResult refresh() {
        final ResponseResult result = this.usersAdminClient.refreshUserIpRateLimitCache();
        return ResultUtil.getCheckedResponseResult(result);
    }
}
