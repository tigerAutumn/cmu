package cc.newex.dax.integration.rest.controller.admin.v1;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.dto.admin.MessageDTO;
import cc.newex.dax.integration.service.admin.MessageAdminService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 信息发送状态明细表 控制器类
 *
 * @author newex-team
 * @date 2018-05-10
 */
@RestController
@RequestMapping(value = "/admin/v1/integration/messages")
public class MessageAdminController {
    @Autowired
    private MessageAdminService messageAdminService;

    @PostMapping("/pager")
    public ResponseResult getList(@RequestBody final DataGridPager<MessageDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Message> list = this.messageAdminService.listByPage(pageInfo, pager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), list));
    }

    @GetMapping(value = "/{id}")
    public ResponseResult get(@RequestParam(value = "brokerId", required = false) final Integer brokerId,
                              @PathVariable("id") final Long id) {
        if (brokerId != null) {
            this.checkPermission(brokerId, id);
        }
        return ResultUtils.success(this.messageAdminService.getById(id));
    }

    @DeleteMapping(value = "")
    public ResponseResult remove(@RequestBody final MessageDTO dto) {
        if (dto.getBrokerId() != null) {
            this.checkPermission(dto.getBrokerId(), dto.getId());
        }
        if (NumberUtil.gt(dto.getId(), 0)) {
            return ResultUtils.success(this.messageAdminService.removeById(dto.getId()));
        }
        return ResultUtils.success(null);
    }

    private void checkPermission(final Integer brokerId, final Long id) {
        final Message record = this.messageAdminService.getById(id);
        Preconditions.checkNotNull(record, "MessageBlack record is not exist, id = " + id);
        Preconditions.checkArgument(!ObjectUtils.notEqual(record.getBrokerId(), brokerId),
                "MessageBlack record is not exist, id = " + id + ", brokerId = " + brokerId);
    }
}