package cc.newex.dax.asset.config.datasource;

import cc.newex.commons.mybatis.readwrite.DynamicDataSourcePlugin;
import cc.newex.commons.support.mybatis.AbstractDataSourceConfig;
import org.apache.ibatis.plugin.Interceptor;
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
 * newex-asset 数据源配置类
 *
 * @or newex-team
 * @date 2018/03/18
 **/
@Configuration
@MapperScan(basePackages = MainDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "assetSqlSessionFactory")
public class MainDataSourceConfig extends AbstractDataSourceConfig {
    static final String PACKAGE = "cc.newex.dax.asset.dao";
    private static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/dax/asset/**/*.xml";

    @Value("${newex.asset.datasource.master.type}")
    private Class<? extends DataSource> datasourceType;

    @Primary
    @ConfigurationProperties(prefix = "newex.asset.datasource.master")
    @Bean(name = "assetDataSource")
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .type(this.datasourceType)
                .build();
    }

    @Primary
    @Bean(name = "assetTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("assetDataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "assetSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("assetDataSource") final DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = this.createSqlSessionFactoryBean(dataSource, MainDataSourceConfig.MAPPER_LOCATION);
        sessionFactory.setPlugins(new Interceptor[]{new DynamicDataSourcePlugin()});
        return sessionFactory.getObject();
    }

    @Primary
    @Bean(name = "assetSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("assetSqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "assetTransactionTemplate")
    public TransactionTemplate transactionTemplate(
            @Qualifier("assetTransactionManager") final DataSourceTransactionManager transactionManager)
            throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }

}
