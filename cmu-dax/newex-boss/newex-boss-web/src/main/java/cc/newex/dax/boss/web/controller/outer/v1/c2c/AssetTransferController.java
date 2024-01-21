package cc.newex.dax.boss.web.controller.outer.v1.c2c;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.asset.dto.BossTransferDto;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.c2c.C2cTransferVO;
import cc.newex.dax.c2c.client.C2CUserBillAdminClient;
import cc.newex.dax.c2c.dto.admin.UserBillDTO;
import cc.newex.dax.c2c.dto.admin.UserBillQueryDTO;
import cc.newex.dax.c2c.dto.common.PagedList;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author newex-jinlong
 * @date 2018-04-28
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/c2c/asset-transfer")
public class AssetTransferController {

    @Autowired
    private C2CUserBillAdminClient c2CUserBillAdminClient;

    @Autowired
    private AdminServiceClient adminServiceClient;

    @GetMapping(value = "type")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_ASSET_VIEW"})
    public JSONArray typeList() {
        final ResponseResult<JSONArray> result = this.c2CUserBillAdminClient.billTypes();

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return new JSONArray();
        }

        return result.getData();
    }

    @GetMapping(value = "/searchList")
    @OpLog(name = "资金划转记录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_ASSET_VIEW"})
    public ResponseResult list(final DataGridPager pager,
                               @RequestParam(value = "userId", required = false) final Long userId,
                               @RequestParam(value = "currency", required = false) final Long currencyId,
                               @RequestParam(value = "legalTender", required = false) final Integer legalTender) {
        final UserBillQueryDTO userBillQueryDTO = UserBillQueryDTO.builder()
                .page(pager.getPage())
                .pageSize(pager.getRows()).build();

        if (userId != null) {
            userBillQueryDTO.setUserId(userId);
        }

        if (currencyId != null && currencyId > 0) {
            userBillQueryDTO.setCurrencyId(currencyId);
        }

        if (legalTender != null && legalTender > 0) {
            userBillQueryDTO.setType(legalTender);
        }

        final ResponseResult<PagedList<UserBillDTO>> result = this.c2CUserBillAdminClient.getUserList(userBillQueryDTO);

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final Long total = result.getData().getTotal();
        final List<UserBillDTO> rows = result.getData().getItems();

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total, rows);

        return ResultUtils.success(dataGridPagerResult);
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "法币资金划转")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_ASSET_ADD"})
    public ResponseResult add(@Valid final C2cTransferVO c2cTransferVO, final HttpServletRequest request, @CurrentUser final User loginUser) {
        final ModelMapper mapper = new ModelMapper();
        final BossTransferDto bossTransferDto = mapper.map(c2cTransferVO, BossTransferDto.class);
        bossTransferDto.setBrokerId(loginUser.getBrokerId()==0?1:loginUser.getBrokerId());
        final ResponseResult result = this.adminServiceClient.bossTransfer(bossTransferDto);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
