package cc.newex.dax.extra.common.config;

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
 * newex-extra 数据源配置类
 *
 * @or newex-team
 * @date 2018/03/18
 **/
@Configuration
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "extraSqlSessionFactory")
public class MasterDataSourceConfig extends AbstractDataSourceConfig {
    static final String PACKAGE = "cc.newex.dax.extra.data";
    private static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/dax/extra/**/*.xml";

    @Value("${newex.extra.datasource.master.type}")
    private Class<? extends DataSource> datasourceType;

    @Primary
    @ConfigurationProperties(prefix = "newex.extra.datasource.master")
    @Bean(name = "extraDataSource")
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .type(this.datasourceType)
                .build();
    }

    @Primary
    @Bean(name = "extraTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("extraDataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "extraSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("extraDataSource") final DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = this.createSqlSessionFactoryBean(dataSource, MAPPER_LOCATION);
        sessionFactory.setPlugins(new Interceptor[]{new DynamicDataSourcePlugin()});
        return sessionFactory.getObject();
    }

    @Primary
    @Bean(name = "extraSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("extraSqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "extraTransactionTemplate")
    public TransactionTemplate transactionTemplate(
            @Qualifier("extraTransactionManager") final DataSourceTransactionManager transactionManager)
            throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }

}
