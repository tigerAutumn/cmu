package cc.newex.dax.users.rest.controller.admin.v1.subaccount;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.criteria.subaccount.OrganizationInfoExample;
import cc.newex.dax.users.domain.subaccount.OrganizationInfo;
import cc.newex.dax.users.dto.subaccount.OrganizationInfoDTO;
import cc.newex.dax.users.service.subaccount.OrganizationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 组织详情表 Admin Controller
 *
 * @author newex-team
 * @date 2018-10-31 16:16:36
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/users/subaccount/organization")
public class OrganizationInfoAdminController {

    @Autowired
    private OrganizationInfoService organizationInfoService;

    /**
     * 保存OrganizationInfo
     *
     * @param organizationInfoDTO
     * @return
     */
    @PostMapping(value = "/saveOrganizationInfo")
    public ResponseResult saveOrganizationInfo(@RequestBody final OrganizationInfoDTO organizationInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final OrganizationInfo organizationInfo = mapper.map(organizationInfoDTO, OrganizationInfo.class);

        final int save = this.organizationInfoService.add(organizationInfo);

        if (save > 0) {
            return ResultUtils.success(save);
        } else {
            return ResultUtils.failure(BizErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 更新OrganizationInfo
     *
     * @param organizationInfoDTO
     * @return
     */
    @PostMapping(value = "/updateOrganizationInfo")
    public ResponseResult updateOrganizationInfo(@RequestBody final OrganizationInfoDTO organizationInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final OrganizationInfo organizationInfo = mapper.map(organizationInfoDTO, OrganizationInfo.class);

        final int update = this.organizationInfoService.editById(organizationInfo);

        if (update > 0) {
            return ResultUtils.success(update);
        } else {
            return ResultUtils.failure(BizErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * List OrganizationInfo
     *
     * @param pager
     * @return
     */
    @PostMapping(value = "/listOrganizationInfo")
    public ResponseResult listOrganizationInfo(@RequestBody final DataGridPager<OrganizationInfoDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final ModelMapper mapper = new ModelMapper();

        OrganizationInfo organizationInfo = null;

        final OrganizationInfoDTO queryParameter = pager.getQueryParameter();
        if (Objects.nonNull(queryParameter)) {
            organizationInfo = mapper.map(queryParameter, OrganizationInfo.class);
        }

        final OrganizationInfoExample example = OrganizationInfoExample.toExample(organizationInfo);
        final List<OrganizationInfo> list = this.organizationInfoService.getByPage(pageInfo, example);
        final List<OrganizationInfoDTO> organizationInfoDTOS = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(entity -> {
                final OrganizationInfoDTO organizationInfoDTO = mapper.map(entity, OrganizationInfoDTO.class);

                organizationInfoDTOS.add(organizationInfoDTO);
            });
        }

        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), organizationInfoDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getOrganizationInfoById")
    public ResponseResult getOrganizationInfoById(@RequestParam("id") final Long id) {
        final OrganizationInfo organizationInfo = this.organizationInfoService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final OrganizationInfoDTO organizationInfoDTO = mapper.map(organizationInfo, OrganizationInfoDTO.class);

        return ResultUtils.success(organizationInfoDTO);
    }

    /**
     * 删除OrganizationInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeOrganizationInfo")
    public ResponseResult removeOrganizationInfo(@RequestParam("id") final Long id) {
        final int remove = this.organizationInfoService.removeById(id);

        return ResultUtils.success(remove);
    }

}