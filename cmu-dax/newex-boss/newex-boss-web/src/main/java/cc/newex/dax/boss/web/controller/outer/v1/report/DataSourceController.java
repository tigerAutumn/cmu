package cc.newex.dax.boss.web.controller.outer.v1.report;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.report.criteria.DataSourceExample;
import cc.newex.dax.boss.report.domain.DataSource;
import cc.newex.dax.boss.report.service.DataSourceService;
import cc.newex.dax.boss.web.controller.common.BaseController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 报表数据源控制器
 *
 * @author newex-team
 * @date 2017-03-18
 */
@RestController
@RequestMapping(value = "/v1/boss/report/data-sources")
public class DataSourceController
    extends BaseController<DataSourceService, DataSource, DataSourceExample, Integer> {

    @GetMapping(value = "/listAll")
    @OpLog(name = "获取所有数据源")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DS_VIEW"})
    public ResponseResult<List<DataSource>> listAll() {
        return ResultUtils.success(this.service.getAll().stream()
            .map(x -> DataSource.builder()
                .id(x.getId())
                .uid(x.getUid())
                .name(x.getName())
                .build())
            .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/listByPage")
    @OpLog(name = "分页获取数据源列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DS_VIEW"})
    public ResponseResult<Map<String,Object>> listByPage(final DataGridPager pager,
                                                         final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<DataSource> list = this.service.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增数据源")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DS_ADD"})
    public ResponseResult add(final DataSource po) {
        po.setCreatedDate(new Date());
        po.setUid(UUID.randomUUID().toString());
        this.service.add(po);
        return ResultUtils.success();
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑数据源")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DS_EDIT"})
    public ResponseResult edit(final DataSource po) {
        this.service.editById(po);
        return ResultUtils.success();
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除数据源")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DS_REMOVE"})
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResultUtils.success();
    }

    @PostMapping(value = "/testConnection")
    @OpLog(name = "测试数据源")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DS_VIEW"})
    public ResponseResult testConnection(final String driverClass, final String url, final String pass,
                                         final String user) {
        if (this.service.testConnection(driverClass, url, user, pass)) {
            return ResultUtils.success("");
        }
        return ResultUtils.failure(20000, "数据源测试失败");
    }

    @PostMapping(value = "/testConnectionById")
    @OpLog(name = "测试数据源")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_REPORT_DS_VIEW"})
    public ResponseResult testConnection(final Integer id) {
        final DataSource dsPo = this.service.getById(id);
        final boolean testResult = this.service.testConnection(
            dsPo.getDriverClass(),
            dsPo.getJdbcUrl(),
            dsPo.getUser(), dsPo.getPassword());

        if (testResult) {
            return ResultUtils.success();
        }
        return ResultUtils.failure(10005, "数据源测试失败");
    }
}