package cc.newex.dax.integration.rest.controller.admin.v1;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.domain.msg.MessageSendStatusDetail;
import cc.newex.dax.integration.dto.admin.MessageSendStatusDetailDTO;
import cc.newex.dax.integration.service.admin.MessageSendStatusDetailAdminService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 信息发送状态明细表 控制器类
 *
 * @author newex-team
 * @date 2018-05-10
 */
@RestController
@RequestMapping(value = "/admin/v1/integration/message-send-status-details")
public class MessageSendStatusDetailAdminController {
    @Resource
    private MessageSendStatusDetailAdminService messageSendStatusDetailAdminService;

    @PostMapping(value = "/pager")
    public ResponseResult getList(@RequestBody final DataGridPager<MessageSendStatusDetailDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<MessageSendStatusDetail> list = this.messageSendStatusDetailAdminService.listByPage(pageInfo, pager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), list));
    }

    @GetMapping(value = "/{id}")
    public ResponseResult get(@RequestParam(value = "brokerId", required = false) final Integer brokerId,
                              @PathVariable("id") final Long id) {
        if (brokerId != null) {
            this.checkPermission(brokerId, id);
        }
        return ResultUtils.success(this.messageSendStatusDetailAdminService.getById(id));
    }


    @DeleteMapping(value = "")
    public ResponseResult remove(@RequestBody final MessageSendStatusDetailDTO dto) {
        if (NumberUtil.gt(dto.getId(), 0)) {
            if (dto.getBrokerId() != null) {
                this.checkPermission(dto.getBrokerId(), dto.getId());
            }
            return ResultUtils.success(this.messageSendStatusDetailAdminService.removeById(dto.getId()));
        }
        return ResultUtils.success();
    }

    private void checkPermission(final Integer brokerId, final Long id) {
        final MessageSendStatusDetail record = this.messageSendStatusDetailAdminService.getById(id);
        Preconditions.checkNotNull(record, "messageSendStatusDetail record is not exist, id = " + id);
        Preconditions.checkArgument(!ObjectUtils.notEqual(record.getBrokerId(), brokerId),
                "messageSendStatusDetail record is not exist, id = " + id + ", brokerId = " + brokerId);
    }
}