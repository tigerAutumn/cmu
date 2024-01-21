package cc.newex.dax.market.job.config.datasource;

import cc.newex.commons.support.mybatis.AbstractDataSourceConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * 业务数据源配置类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Configuration
@MapperScan(basePackages = {DataSourceConfig.CC_NEWEX_DAX_FUTURES_DATA},
        sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig extends AbstractDataSourceConfig {
    static final String CC_NEWEX_DAX_FUTURES_DATA = "cc.newex.dax.market.data";

    private static final String[] MAPPER_LOCATIONS = {
            "classpath*:mybatis/mapper/dax/market/*.xml"
    };

    @Value("${newex.futures.datasource.master.type}")
    private Class<? extends DataSource> dataSourceType;

    @Primary
    @ConfigurationProperties(prefix = "newex.futures.datasource.master")
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(this.dataSourceType)
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(
            @Qualifier("dataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") final DataSource dataSource)
            throws Exception {
        return this.createSqlSessionFactory(dataSource, DataSourceConfig.MAPPER_LOCATIONS);
    }

    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "transactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("transactionManager") final
                                                   DataSourceTransactionManager transactionManager)
            throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }
}
