package cc.newex.dax.integration.rest.controller.admin.v1;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.domain.conf.BrokerSignConfig;
import cc.newex.dax.integration.dto.message.BrokerSignConfigDTO;
import cc.newex.dax.integration.service.conf.BrokerSignConfigService;
import com.google.common.base.Preconditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * broker sign config
 *
 * @author newex-team
 * @date 2018-9-12
 */
@RestController
@RequestMapping(value = "/admin/v1/integration/broker")
public class BrokerSignConfigController {

    @Autowired
    private BrokerSignConfigService brokerSignConfigService;


    /**
     * 添加 broker 配置信息
     *
     * @param config
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(
            @RequestBody final BrokerSignConfigDTO config) {

        Preconditions.checkNotNull(config.getSign(), "sign is null");
        Preconditions.checkNotNull(config.getBrokerId(), "broker id is null");

        final Date nowTime = new Date();
        final BrokerSignConfig record = BrokerSignConfig.builder()
                .createTime(nowTime)
                .modifyTime(nowTime)
                .sign(config.getSign())
                .brokerId(config.getBrokerId())
                .build();
        this.brokerSignConfigService.add(record);
        return ResultUtils.success();
    }

    /**
     * 通过 broker 删除配置信息
     *
     * @param brokerId
     * @return
     */
    @DeleteMapping("/{brokerId}")
    public ResponseResult remove(@PathVariable("brokerId") final Integer brokerId) {

        Preconditions.checkNotNull(brokerId, "sign is null");
        this.brokerSignConfigService.removeByBrokerId(brokerId);

        return ResultUtils.success();
    }

    /**
     * 通过 broker 更新 sign
     *
     * @param config
     * @return
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody final BrokerSignConfigDTO config) {

        Preconditions.checkNotNull(config.getSign(), "sign is null");
        Preconditions.checkNotNull(config.getBrokerId(), "broker id is null");

        this.brokerSignConfigService.updateByBrokerId(config.getBrokerId(), config.getSign());
        return ResultUtils.success();
    }

    /**
     * 通过 brokerId 获取配置信息
     *
     * @param brokerId
     * @return
     */
    @GetMapping("/{brokerId}")
    public ResponseResult<BrokerSignConfigDTO> getByBrokerId(@PathVariable("brokerId") final Integer brokerId) {

        final BrokerSignConfig record = this.brokerSignConfigService.getByBrokerId(brokerId);

        return ResultUtils.success(this.convert(record));
    }

    /**
     * 通过 brokerId 数组获取配置信息
     *
     * @param brokerId
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<List<BrokerSignConfigDTO>> getByBrokerIdList(@RequestParam("brokerId") final Integer[] brokerId) {
        final List<BrokerSignConfig> list = this.brokerSignConfigService.getByBrokerId(brokerId);
        final List<BrokerSignConfigDTO> dtoList = list.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return ResultUtils.success(dtoList);
    }

    /**
     * 分页获取配置信息
     *
     * @param pager
     * @return
     */
    @PostMapping("/pager")
    public ResponseResult<DataGridPagerResult<BrokerSignConfigDTO>> pageList(
            @RequestBody final DataGridPager pager) {

        final Integer page = pager.getPage() == null || pager.getPage() < 1 ? 1 : pager.getPage();
        final Integer pageSize = pager.getRows() == null || pager.getRows() <= 0 ? 10 : pager.getRows();

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setStartIndex((page - 1) * pageSize);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_DES);

        final List<BrokerSignConfig> list = this.brokerSignConfigService.getByPage(pageInfo);

        final List<BrokerSignConfigDTO> dtoList = list.stream()
                .map(this::convert)
                .collect(Collectors.toList());

        return ResultUtils.success(new DataGridPagerResult(pageInfo.getTotals(), dtoList));
    }

    private BrokerSignConfigDTO convert(final BrokerSignConfig record) {
        if (record == null) {
            return null;
        }
        final ModelMapper mapper = new ModelMapper();
        return mapper.map(record, BrokerSignConfigDTO.class);
    }
}
