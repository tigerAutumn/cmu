package cc.newex.dax.boss.report.service;


import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.report.criteria.DataSourceExample;
import cc.newex.dax.boss.report.domain.DataSource;

/**
 * 报表数据源配置信息表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface DataSourceService
        extends CrudService<DataSource, DataSourceExample, Integer> {
    /**
     * 测试当前数据库连接
     *
     * @param driverClass
     * @param url
     * @param user
     * @param password
     */
    boolean testConnection(String driverClass, String url, String user, String password);
}
