package cc.newex.dax.boss.web.controller.outer.v1.admin;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.IpWhite;
import cc.newex.dax.boss.admin.service.IpWhiteService;
import cc.newex.dax.boss.web.model.admin.IpWhiteVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/admin/ip-white")
public class IpWhiteController {

    @Autowired
    private IpWhiteService ipWhiteService;

    @GetMapping(value = "/listByPage")
    @OpLog(name = "分页获取IP白名单列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_IPWHITE_VIEW", "ROLE_ADMIN_IP_WHITE_MGR"})
    public ResponseResult listByPage(final DataGridPager pager,
                                     final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<IpWhite> list = this.ipWhiteService.getByPage(pageInfo, fieldName, "%" + keyword + "%");

        final List<IpWhiteVO> voList = Lists.newArrayListWithCapacity(50);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(x -> voList.add(IpWhiteVO.builder()
                    .id(x.getId())
                    .adminUserId(x.getAdminUserId())
                    .ipAddress(IpUtil.ipLong2DotDec(x.getIpAddress()))
                    .createdDate(x.getCreatedDate())
                    .updatedDate(x.getUpdatedDate())
                    .build()));
        }

        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", voList);
        return ResultUtils.success(modelMap);
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增IP白名单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_IPWHITE_ADD", "ROLE_ADMIN_IP_WHITE_MGR"})
    public ResponseResult add(@Valid final IpWhiteVO vo) {
        final IpWhite ipWhite = IpWhite.builder()
                .adminUserId(vo.getAdminUserId())
                .ipAddress(IpUtil.ipDotDec2Long(vo.getIpAddress()))
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();
        this.ipWhiteService.add(ipWhite);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改IP白名单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_IPWHITE_EDIT", "ROLE_ADMIN_IP_WHITE_MGR"})
    public ResponseResult edit(@Valid final IpWhiteVO vo) {
        final IpWhite ipWhite = IpWhite.builder()
                .id(vo.getId())
                .adminUserId(vo.getAdminUserId())
                .ipAddress(IpUtil.ipDotDec2Long(vo.getIpAddress()))
                .updatedDate(new Date())
                .build();
        this.ipWhiteService.editById(ipWhite);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除IP白名单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_IPWHITE_REMOVE", "ROLE_ADMIN_IP_WHITE_MGR"})
    public ResponseResult remove(final Integer id) {
        this.ipWhiteService.removeById(id);
        return ResultUtils.success();
    }

}
