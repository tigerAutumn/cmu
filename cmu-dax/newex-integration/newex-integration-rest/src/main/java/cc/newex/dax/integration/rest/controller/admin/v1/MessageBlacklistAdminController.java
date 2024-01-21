package cc.newex.dax.integration.rest.controller.admin.v1;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.domain.msg.MessageBlacklist;
import cc.newex.dax.integration.dto.admin.MessageBlacklistDTO;
import cc.newex.dax.integration.service.admin.MessageBlacklistAdminService;
import cc.newex.dax.integration.service.cache.AppCacheService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
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
@RequestMapping(value = "/admin/v1/integration/message-blacklists")
public class MessageBlacklistAdminController {
    @Autowired
    private MessageBlacklistAdminService adminService;
    @Autowired
    private AppCacheService appCacheService;

    @PostMapping(value = "/pager")
    public ResponseResult getList(@RequestBody final DataGridPager pager,
                                  @RequestParam(value = "brokerId", required = false) final Integer brokerId,
                                  @RequestParam(value = "fieldName") final String fieldName,
                                  @RequestParam(value = "keyword") final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final ModelMapper modelMapper = new ModelMapper();
        final List<MessageBlacklist> list = this.adminService.getByPage(pageInfo, fieldName, "%" + keyword + "%", brokerId);
        final List<MessageBlacklistDTO> dtoList = Lists.newArrayListWithCapacity(CollectionUtils.size(list));
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(x -> dtoList.add(modelMapper.map(x, MessageBlacklistDTO.class)));
        }
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), dtoList));
    }

    @GetMapping(value = "/{id}")
    public ResponseResult get(@RequestParam(value = "brokerId", required = false) final Integer brokerId,
                              @PathVariable("id") final Integer id) {
        if (brokerId != null) {
            this.checkPermission(brokerId, id);
        }
        final MessageBlacklist po = this.adminService.getById(id);
        final ModelMapper modelMapper = new ModelMapper();
        final MessageBlacklistDTO dto = modelMapper.map(po, MessageBlacklistDTO.class);
        return ResultUtils.success(dto);
    }

    @PostMapping(value = "")
    public ResponseResult add(@RequestBody final MessageBlacklistDTO dto) {
        final ModelMapper modelMapper = new ModelMapper();
        final MessageBlacklist po = modelMapper.map(dto, MessageBlacklist.class);
        final int add = this.adminService.add(po);
        return ResultUtils.success(add);
    }

    @PutMapping(value = "")
    public ResponseResult edit(@RequestBody final MessageBlacklistDTO dto) {
        if (dto.getBrokerId() != null) {
            this.checkPermission(dto.getBrokerId(), dto.getId());
        }
        final ModelMapper modelMapper = new ModelMapper();
        final MessageBlacklist po = modelMapper.map(dto, MessageBlacklist.class);
        return ResultUtils.success(this.adminService.editById(po));
    }

    @DeleteMapping(value = "")
    public ResponseResult remove(@RequestBody final MessageBlacklistDTO dto) {
        if (NumberUtil.gt(dto.getId(), 0)) {
            if (dto.getBrokerId() != null) {
                this.checkPermission(dto.getBrokerId(), dto.getId());
            }
            return ResultUtils.success(this.adminService.removeById(dto.getId()));
        }
        return ResultUtils.success();
    }

    @GetMapping(value = "/refresh")
    public ResponseResult refreshCache() {
        this.appCacheService.loadAllMessageBlacklists();
        return ResultUtils.success();
    }

    private void checkPermission(final Integer brokerId, final Integer id) {
        final MessageBlacklist record = this.adminService.getById(id);
        Preconditions.checkNotNull(record, "MessageBlack record is not exist, id = " + id);
        Preconditions.checkArgument(!ObjectUtils.notEqual(record.getBrokerId(), brokerId),
                "MessageBlack record is not exist, id = " + id + ", brokerId = " + brokerId);
    }
}