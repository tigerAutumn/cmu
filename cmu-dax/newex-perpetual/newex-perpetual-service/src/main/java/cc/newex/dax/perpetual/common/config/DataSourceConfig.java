package cc.newex.dax.perpetual.common.config;

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
 * 数据源配置类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Configuration("dataSourceBizConfig")
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig extends AbstractDataSourceConfig {
    static final String PACKAGE = "cc.newex.dax.perpetual.data";
    private static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/dax/perpetual/**/*.xml";

    @Value("${newex.perpetual.datasource.biz.type}")
    private Class<? extends DataSource> datasourceType;

    @Primary
    @ConfigurationProperties(prefix = "newex.perpetual.datasource.biz")
    @Bean(name = "dataSource")
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .type(this.datasourceType)
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") final DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = this.createSqlSessionFactoryBean(dataSource,
                DataSourceConfig.MAPPER_LOCATION);
        return sessionFactory.getObject();
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
    public TransactionTemplate transactionTemplate(
            @Qualifier("transactionManager") final DataSourceTransactionManager transactionManager)
            throws Exception {

        return this.createTransactionTemplate(transactionManager);
    }

}
