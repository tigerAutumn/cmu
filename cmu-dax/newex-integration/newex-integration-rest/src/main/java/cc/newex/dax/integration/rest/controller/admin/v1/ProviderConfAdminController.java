package cc.newex.dax.integration.rest.controller.admin.v1;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.domain.conf.ProviderConf;
import cc.newex.dax.integration.dto.admin.ProviderConfDTO;
import cc.newex.dax.integration.service.admin.ProviderConfAdminService;
import cc.newex.dax.integration.service.cache.AppCacheService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 控制器类
 *
 * @author newex-team
 * @date 2018-06-02
 */
@RestController
@RequestMapping(value = "/admin/v1/integration/conf/providers")
public class ProviderConfAdminController {
    @Resource
    private ProviderConfAdminService providerConfService;
    @Autowired
    private AppCacheService appCacheService;

    @PostMapping(value = "/pager")
    public ResponseResult getList(@RequestBody final DataGridPager pager,
                                  @RequestParam(value = "brokerId", required = false) final Integer brokerId,
                                  @RequestParam(value = "fieldName") final String fieldName,
                                  @RequestParam(value = "keyword") final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final ModelMapper modelMapper = new ModelMapper();
        final List<ProviderConf> list = this.providerConfService.getByPage(pageInfo, fieldName, "%" + keyword + "%", brokerId);
        final List<ProviderConfDTO> dtoList = Lists.newArrayListWithCapacity(CollectionUtils.size(list));
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(x -> dtoList.add(modelMapper.map(x, ProviderConfDTO.class)));
        }
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), dtoList));
    }

    @GetMapping(value = "/{id}")
    public ResponseResult get(@RequestParam final Integer brokerId,
                              @PathVariable("id") final Integer id) {
        if (brokerId != null) {
            this.checkPermission(brokerId, id);
        }
        final ProviderConf po = this.providerConfService.getById(id);
        final ModelMapper modelMapper = new ModelMapper();
        final ProviderConfDTO dto = modelMapper.map(po, ProviderConfDTO.class);
        return ResultUtils.success(dto);
    }

    @PostMapping(value = "")
    public ResponseResult add(@RequestBody final ProviderConfDTO dto) {
        final ModelMapper modelMapper = new ModelMapper();
        final ProviderConf po = modelMapper.map(dto, ProviderConf.class);
        final int add = this.providerConfService.add(po);
        return ResultUtils.success(add);
    }

    @PutMapping(value = "")
    public ResponseResult edit(@RequestBody final ProviderConfDTO dto) {

        if (dto.getBrokerId() != null) {
            this.checkPermission(dto.getBrokerId(), dto.getId());
        }

        final ModelMapper modelMapper = new ModelMapper();
        final ProviderConf po = modelMapper.map(dto, ProviderConf.class);
        return ResultUtils.success(this.providerConfService.editById(po));
    }

    @DeleteMapping(value = "")
    public ResponseResult remove(@RequestBody final ProviderConfDTO dto) {
        if (NumberUtil.gt(dto.getId(), 0)) {
            if (dto.getBrokerId() != null) {
                this.checkPermission(dto.getBrokerId(), dto.getId());
            }
            return ResultUtils.success(this.providerConfService.removeById(dto.getId()));
        }
        return ResultUtils.success();
    }

    private void checkPermission(final Integer brokerId, final Integer id) {
        final ProviderConf record = this.providerConfService.getById(id);
        Preconditions.checkNotNull(record, "messageSendStatusDetail record is not exist, id = " + id);
        Preconditions.checkArgument(!ObjectUtils.notEqual(record.getBrokerId(), brokerId),
                "messageSendStatusDetail record is not exist, id = " + id + ", brokerId = " + brokerId);
    }
}