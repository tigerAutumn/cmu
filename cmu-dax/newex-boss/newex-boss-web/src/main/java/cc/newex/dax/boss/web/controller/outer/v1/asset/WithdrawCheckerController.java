package cc.newex.dax.boss.web.controller.outer.v1.asset;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.client.AssetTransferServiceClient;
import cc.newex.dax.asset.dto.TransferRecordAuditReqDto;
import cc.newex.dax.asset.dto.TransferRecordAuditResDto;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.asset.TransferRecordAuditReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author liutiejun
 * @date 2019-04-26
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/asset/withdraw/checker")
public class WithdrawCheckerController {

    @Autowired
    private AssetTransferServiceClient transferServiceClient;

    @RequestMapping(value = "/edit")
    @OpLog(name = "对提现记录进行审核")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_WITHDRAW_CHECKER_EDIT"})
    public ResponseResult edit(@Valid final TransferRecordAuditReqVO vo, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final TransferRecordAuditReqDto dto = TransferRecordAuditReqDto.builder()
                .traderNo(vo.getTraderNo())
                .msg(vo.getMsg())
                .status(vo.getStatus())
                .auditUserId(user.getId().longValue())
                .build();

        final ResponseResult result = this.transferServiceClient.withdraw(dto);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取提现审核列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_WITHDRAW_CHECKER_VIEW"})
    public ResponseResult list(@RequestParam(value = "status", required = false) final Integer status,
                               @RequestParam(value = "traderNo", required = false) final String traderNo,
                               final DataGridPager<TransferRecordAuditResDto> pager) {

        final TransferRecordAuditResDto.TransferRecordAuditResDtoBuilder builder = TransferRecordAuditResDto.builder();

        if (status != null) {
            builder.status(status);
        }

        if (StringUtils.isNotBlank(traderNo)) {
            builder.traderNo(traderNo);
        }

        final TransferRecordAuditResDto dto = builder.build();
        pager.setQueryParameter(dto);

        final ResponseResult responseResult = this.transferServiceClient.withdrawAudit(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

}
