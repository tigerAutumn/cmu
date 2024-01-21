package cc.newex.dax.integration.rest.controller.admin.v1;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.domain.msg.MessageTemplate;
import cc.newex.dax.integration.dto.admin.MessageTemplateDTO;
import cc.newex.dax.integration.service.admin.MessageTemplateAdminService;
import cc.newex.dax.integration.service.cache.AppCacheService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 信息发送状态明细表 控制器类
 *
 * @author newex-team
 * @date 2018-05-10
 */
@RestController
@RequestMapping(value = "/admin/v1/integration/message-templates")
public class MessageTemplateAdminController {
    @Autowired
    private MessageTemplateAdminService messageTemplateAdminService;
    @Autowired
    private AppCacheService appCacheService;

    @PostMapping(value = "/pager")
    public ResponseResult getList(@RequestBody final DataGridPager<MessageTemplateDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<MessageTemplate> list = this.messageTemplateAdminService.listByPage(pageInfo, pager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), list));
    }

    @GetMapping(value = "/{id}")
    public ResponseResult get(@PathVariable("id") final Integer id) {
        return ResultUtils.success(this.messageTemplateAdminService.getById(id));
    }

    @PostMapping(value = "")
    public ResponseResult add(@RequestBody final MessageTemplateDTO messageTemplateDTO) {
        final ModelMapper modelMapper = new ModelMapper();
        final MessageTemplate po = modelMapper.map(messageTemplateDTO, MessageTemplate.class);
        final int add = this.messageTemplateAdminService.add(po);
        return ResultUtils.success(add);
    }

    @PutMapping(value = "")
    public ResponseResult edit(@RequestBody final MessageTemplateDTO messageTemplateDTO) {
        final ModelMapper modelMapper = new ModelMapper();
        final MessageTemplate po = modelMapper.map(messageTemplateDTO, MessageTemplate.class);
        return ResultUtils.success(this.messageTemplateAdminService.editById(po));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(@PathVariable("id") final Integer id) {
        if (NumberUtil.gt(id, 0)) {
            return ResultUtils.success(this.messageTemplateAdminService.removeById(id));
        }
        return ResultUtils.success();
    }

    @GetMapping(value = "/refresh")
    public ResponseResult refreshCache() {
        this.appCacheService.loadAllMessageTemplates();
        return ResultUtils.success();
    }
}