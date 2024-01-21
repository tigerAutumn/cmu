package cc.newex.dax.integration.rest.controller.admin.v1;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.domain.msg.MessageSuccessRatio;
import cc.newex.dax.integration.dto.admin.MessageSuccessRatioDTO;
import cc.newex.dax.integration.service.admin.MessageSuccessRatioAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 信息息发送成功率统计表 控制器类
 *
 * @author newex-team
 * @date 2018-05-10
 */
@RestController
@RequestMapping(value = "/admin/v1/integration/message-success-ratios")
public class MessageSuccessRatioAdminController {
    @Autowired
    private MessageSuccessRatioAdminService messageSuccessRatioAdminService;

    @PostMapping(value = "/pager")
    public ResponseResult getList(@RequestBody final DataGridPager<MessageSuccessRatioDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<MessageSuccessRatio> list = this.messageSuccessRatioAdminService.listByPage(pageInfo, pager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), list));
    }

    @GetMapping(value = "/{id}")
    public ResponseResult get(@PathVariable("id") final Integer id) {
        return ResultUtils.success(this.messageSuccessRatioAdminService.getById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(@PathVariable("id") final Integer id) {
        if (NumberUtil.gt(id, 0)) {
            return ResultUtils.success(this.messageSuccessRatioAdminService.removeById(id));
        }
        return ResultUtils.success();
    }
}