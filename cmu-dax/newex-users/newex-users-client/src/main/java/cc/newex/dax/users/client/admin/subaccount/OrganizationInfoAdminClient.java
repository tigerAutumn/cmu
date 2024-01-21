package cc.newex.dax.users.client.admin.subaccount;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.dto.subaccount.OrganizationInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liutiejun
 * @date 2018-11-01
 */
@FeignClient(value = "${newex.feignClient.dax.users}", path = "/admin/v1/users/subaccount/organization")
public interface OrganizationInfoAdminClient {

    /**
     * 保存OrganizationInfo
     *
     * @param organizationInfoDTO
     * @return
     */
    @PostMapping(value = "/saveOrganizationInfo")
    ResponseResult saveOrganizationInfo(@RequestBody final OrganizationInfoDTO organizationInfoDTO);

    /**
     * 更新OrganizationInfo
     *
     * @param organizationInfoDTO
     * @return
     */
    @PostMapping(value = "/updateOrganizationInfo")
    ResponseResult updateOrganizationInfo(@RequestBody final OrganizationInfoDTO organizationInfoDTO);

    /**
     * List OrganizationInfo
     *
     * @param pager
     * @return
     */
    @PostMapping(value = "/listOrganizationInfo")
    ResponseResult listOrganizationInfo(@RequestBody final DataGridPager<OrganizationInfoDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getOrganizationInfoById")
    ResponseResult getOrganizationInfoById(@RequestParam("id") final Long id);

    /**
     * 删除OrganizationInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeOrganizationInfo")
    ResponseResult removeOrganizationInfo(@RequestParam("id") final Long id);

}
