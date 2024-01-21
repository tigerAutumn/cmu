package cc.newex.dax.market.rest.common.config.datasource;

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
 * 现货与合约核心业务数据源配置类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Configuration
@MapperScan(basePackages = {
    MarketDataSourceConfig.MARKET_PACKAGE},
    sqlSessionFactoryRef = "marketSqlSessionFactory")
public class MarketDataSourceConfig extends AbstractDataSourceConfig {
    static final String MARKET_PACKAGE = "cc.newex.dax.market.data";

    private static final String[] MAPPER_LOCATIONS = {
            "classpath*:mybatis/mapper/dax/market/*.xml"
    };

    @Value("${newex.market.datasource.master.type}")
    private Class<? extends DataSource> dataSourceType;


    @Primary
    @ConfigurationProperties(prefix = "newex.market.datasource.master")
    @Bean(name = "marketDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .type(this.dataSourceType)
            .build();
    }

    @Primary
    @Bean(name = "marketTransactionManager")
    public DataSourceTransactionManager transactionManager(
        @Qualifier("marketDataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "marketSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("marketDataSource") final DataSource dataSource)
        throws Exception {
        return this.createSqlSessionFactory(dataSource, MarketDataSourceConfig.MAPPER_LOCATIONS);
    }

    @Primary
    @Bean(name = "marketSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("marketSqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
        throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "marketTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("marketTransactionManager") final
                                                   DataSourceTransactionManager transactionManager)
        throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }
}
