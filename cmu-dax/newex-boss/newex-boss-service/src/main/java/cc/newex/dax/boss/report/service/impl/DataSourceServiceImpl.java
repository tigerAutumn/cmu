package cc.newex.dax.boss.report.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.report.criteria.DataSourceExample;
import cc.newex.dax.boss.report.data.DataSourceRepository;
import cc.newex.dax.boss.report.domain.DataSource;
import cc.newex.dax.boss.report.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 报表数据源配置信息表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class DataSourceServiceImpl
        extends AbstractCrudService<DataSourceRepository, DataSource, DataSourceExample, Integer>
        implements DataSourceService {

    @Autowired
    private DataSourceRepository dataSourceRepos;

    @Override
    protected DataSourceExample getPageExample(final String fieldName, final String keyword) {
        final DataSourceExample example = new DataSourceExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * @param driverClass
     * @param url
     * @param user
     * @param password
     * @return
     */
    @Override
    public boolean testConnection(final String driverClass, final String url, final String user,
                                  final String password) {
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, user, password);
            return true;
        } catch (final Exception e) {
            log.error("testConnection", e);
            return false;
        } finally {
            this.releaseConnection(conn);
        }
    }

    private void releaseConnection(final Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (final SQLException ex) {
                log.error("测试数据库连接后释放资源失败", ex);
                throw new RuntimeException(ex);
            }
        }
    }
}