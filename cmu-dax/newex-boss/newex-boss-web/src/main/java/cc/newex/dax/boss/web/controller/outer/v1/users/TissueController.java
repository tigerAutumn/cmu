package cc.newex.dax.boss.web.controller.outer.v1.users;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.users.client.admin.subaccount.OrganizationInfoAdminClient;
import cc.newex.dax.users.dto.subaccount.OrganizationInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Tissue controller.
 *
 * @author better
 * @date create in 2018/10/31 10:45 AM
 */
@RestController
@RequestMapping(value = "/v1/boss/users/tissue")
@Slf4j
public class TissueController {

    @Autowired
    private OrganizationInfoAdminClient organizationInfoAdminClient;

    /**
     * 查询组织
     *
     * @param userId        the user id
     * @param orgName       the org name
     * @param contactName   the contact name
     * @param orgType       the org type
     * @param dataGridPager the data grid pager
     * @return the response result
     */
    @GetMapping(value = "/list")
    @OpLog(name = "查询组织列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USER_TISSUE_VIEW"})
    public ResponseResult list(final Long userId, final String orgName, final String contactName, final Integer orgType,
                               final DataGridPager<OrganizationInfoDTO> dataGridPager) {

        final OrganizationInfoDTO organizationInfoDTO = OrganizationInfoDTO.builder()
                .userId(userId)
                .orgName(orgName)
                .contactName(contactName)
                .orgType(orgType)
                .orgType(orgType).build();

        dataGridPager.setQueryParameter(organizationInfoDTO);

        return ResultUtil.getCheckedResponseResult(this.organizationInfoAdminClient.listOrganizationInfo(dataGridPager));
    }

    /**
     * 更新组织
     *
     * @param organizationInfo the organization info
     * @return the response result
     */
    @PostMapping(value = "/edit")
    @OpLog(name = "修改组织")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USER_TISSUE_EDIT"})
    public ResponseResult edit(final OrganizationInfoDTO organizationInfo) {
        organizationInfo.setUpdatedDate(new Date());
        return ResultUtil.getCheckedResponseResult(this.organizationInfoAdminClient.updateOrganizationInfo(organizationInfo));
    }

    /**
     * 创建组织
     *
     * @param organizationInfo the organization info
     * @return the response result
     */
    @PostMapping(value = "/save")
    @OpLog(name = "新增组织")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USER_TISSUE_ADD"})
    public ResponseResult save(final OrganizationInfoDTO organizationInfo) {
        organizationInfo.setCreatedDate(new Date());
        return ResultUtil.getCheckedResponseResult(this.organizationInfoAdminClient.saveOrganizationInfo(organizationInfo));
    }

    /**
     * 删除组织
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/remove")
    @OpLog(name = "删除组织")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USER_TISSUE_REMOVE"})
    public ResponseResult remove(final Long id) {
        return ResultUtil.getCheckedResponseResult(this.organizationInfoAdminClient.removeOrganizationInfo(id));
    }

    /**
     * Look child account response result.
     *
     * @return the response result
     */
    @GetMapping(value = "/childAccount")
    public ResponseResult lookChildAccount() {
        final List<ChildAccount> childAccounts = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            final ChildAccount childAccount = new ChildAccount("childAccount" + i, "mark" + i, "valuation" + i);
            childAccounts.add(childAccount);
        }
        return ResultUtils.success(childAccounts);
    }

    /**
     * The type Child account.
     */
    @ToString
    @Data
    @AllArgsConstructor
    class ChildAccount {
        private String childAccount;

        private String mark;

        private String valuation;
    }
}
