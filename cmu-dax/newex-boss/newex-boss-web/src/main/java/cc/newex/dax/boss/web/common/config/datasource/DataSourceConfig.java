package cc.newex.dax.boss.web.common.config.datasource;

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
 * newex-boss 数据源配置类
 *
 * @author newex-team
 * @date 2017-03-18
 **/
@Configuration
@MapperScan(basePackages = {
        DataSourceConfig.ADMIN_PACKAGE,
        DataSourceConfig.REPORT_PACKAGE,
        DataSourceConfig.CONFIG_PACKAGE},
        sqlSessionFactoryRef = "bossSqlSessionFactory")
public class DataSourceConfig extends AbstractDataSourceConfig {
    static final String ADMIN_PACKAGE = "cc.newex.dax.boss.admin.data";
    static final String CONFIG_PACKAGE = "cc.newex.dax.boss.config.data";
    static final String REPORT_PACKAGE = "cc.newex.dax.boss.report.data";

    private static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/dax/boss/**/*.xml";

    @Value("${newex.boss.datasource.master.type}")
    private Class<? extends DataSource> datasourceType;

    @Primary
    @ConfigurationProperties(prefix = "newex.boss.datasource.master")
    @Bean(name = "bossDataSource")
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .type(this.datasourceType)
                .build();
    }

    @Primary
    @Bean(name = "bossTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("bossDataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "bossSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("bossDataSource") final DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = this.createSqlSessionFactoryBean(dataSource, MAPPER_LOCATION);
        return sessionFactory.getObject();
    }

    @Primary
    @Bean(name = "bossSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("bossSqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "bossTransactionTemplate")
    public TransactionTemplate transactionTemplate(
            @Qualifier("bossTransactionManager") final DataSourceTransactionManager transactionManager)
            throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }

}
