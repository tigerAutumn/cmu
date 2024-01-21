package cc.newex.dax.users.rest.controller.admin.v1;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.domain.UserIpRateLimit;
import cc.newex.dax.users.dto.admin.UserIpRateLimitDTO;
import cc.newex.dax.users.service.admin.UserIpRateLimitAdminService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Api流量限制配置表 控制器类
 *
 * @author newex-team
 * @date 2018-06-18
 */
@RestController
@RequestMapping(value = "/admin/v1/users/api-rate-limits")
public class UserIpRateLimitAdminController {
    @Resource
    private UserIpRateLimitAdminService userApiRateLimitAdminService;

    @PostMapping(value = "/pager")
    public ResponseResult<DataGridPagerResult<UserIpRateLimitDTO>> getList(@RequestBody final DataGridPager pager,
                                                                           @RequestParam(value = "fieldName") final String fieldName,
                                                                           @RequestParam(value = "keyword") final String keyword,
                                                                           @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<UserIpRateLimit> list = this.userApiRateLimitAdminService.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        final List<UserIpRateLimitDTO> dtoList = Lists.newArrayListWithCapacity(CollectionUtils.size(list));
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(x -> {
                if (brokerId != null) {
                    if (x.getBrokerId().intValue() == brokerId.intValue()) {
                        dtoList.add(ObjectCopyUtils.map(x, UserIpRateLimitDTO.class));
                    }
                } else {
                    dtoList.add(ObjectCopyUtils.map(x, UserIpRateLimitDTO.class));
                }
            });
        }
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), dtoList));
    }

    @GetMapping(value = "/{id}")
    public ResponseResult get(@PathVariable("id") final Integer id) {
        final UserIpRateLimit po = this.userApiRateLimitAdminService.getById(id);
        final UserIpRateLimitDTO dto = ObjectCopyUtils.map(po, UserIpRateLimitDTO.class);
        return ResultUtils.success(dto);
    }

    @PostMapping(value = "")
    public ResponseResult add(@RequestBody final UserIpRateLimitDTO dto) {
        final UserIpRateLimit po = ObjectCopyUtils.map(dto, UserIpRateLimit.class);
        final int add = this.userApiRateLimitAdminService.add(po);
        return ResultUtils.success(add);
    }

    @PutMapping(value = "")
    public ResponseResult edit(@RequestBody final UserIpRateLimitDTO dto) {
        final UserIpRateLimit po = ObjectCopyUtils.map(dto, UserIpRateLimit.class);
        return ResultUtils.success(this.userApiRateLimitAdminService.editById(po));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(@PathVariable("id") final Integer id) {
        if (NumberUtil.gt(id, 0)) {
            return ResultUtils.success(this.userApiRateLimitAdminService.removeById(id));
        }
        return ResultUtils.success();
    }

    @GetMapping(value = "/refresh")
    public ResponseResult refreshCache() {
        this.userApiRateLimitAdminService.refreshCache();
        return ResultUtils.success();
    }
}