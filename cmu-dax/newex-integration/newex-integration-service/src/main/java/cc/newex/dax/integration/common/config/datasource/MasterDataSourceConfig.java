package cc.newex.dax.integration.common.config.datasource;

import cc.newex.commons.support.mybatis.AbstractDataSourceConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
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
 * newex-integration 数据源配置类
 *
 * @or newex-team
 * @date 2018/03/18
 **/
@Configuration
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "integrationSqlSessionFactory")
public class MasterDataSourceConfig extends AbstractDataSourceConfig {
    static final String PACKAGE = "cc.newex.dax.integration.data";
    private static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/dax/integration/**/*.xml";

    @Value("${newex.integration.datasource.master.type}")
    private Class<? extends DataSource> datasourceType;

    @Primary
    @ConfigurationProperties(prefix = "newex.integration.datasource.master")
    @Bean(name = "integrationDataSource")
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .type(this.datasourceType)
                .build();
    }

    @Primary
    @Bean(name = "integrationTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("integrationDataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "integrationSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("integrationDataSource") final DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = this.createSqlSessionFactoryBean(dataSource, MasterDataSourceConfig.MAPPER_LOCATION);
        return sessionFactory.getObject();
    }

    @Primary
    @Bean(name = "integrationSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("integrationSqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "integrationTransactionTemplate")
    public TransactionTemplate transactionTemplate(
            @Qualifier("integrationTransactionManager") final DataSourceTransactionManager transactionManager)
            throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }

}
