package cc.newex.dax.users.common.config;

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
 * newex-users 数据源配置类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "usersSqlSessionFactory")
public class DataSourceConfig extends AbstractDataSourceConfig {
    static final String PACKAGE = "cc.newex.dax.users.data";
    private static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/dax/users/**/*.xml";

    @Value("${newex.users.datasource.master.type}")
    private Class<? extends DataSource> datasourceType;

    @Primary
    @ConfigurationProperties(prefix = "newex.users.datasource.master")
    @Bean(name = "usersDataSource")
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .type(this.datasourceType)
                .build();
    }

    @Primary
    @Bean(name = "usersTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("usersDataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "usersSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("usersDataSource") final DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = this.createSqlSessionFactoryBean(dataSource,
                DataSourceConfig.MAPPER_LOCATION);
        return sessionFactory.getObject();
    }

    @Primary
    @Bean(name = "usersSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("usersSqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "usersTransactionTemplate")
    public TransactionTemplate transactionTemplate(
            @Qualifier("usersTransactionManager") final DataSourceTransactionManager transactionManager)
            throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }

}
