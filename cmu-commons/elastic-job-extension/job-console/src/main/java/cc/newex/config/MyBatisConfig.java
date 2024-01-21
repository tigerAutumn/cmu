package cc.newex.config;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by liuhailin on 2017/7/17.
 */

@Configuration
@EnableTransactionManagement
@MapperScan("cc.newex.dao")
public class MyBatisConfig {

    private static String MYBATIS_CONFIG = "mybatis-config.xml";

    private static String MAPPER_PATH = "/mapper/**.xml";

    private String typeAliasPackage = "cc.newex.domain";

    @Autowired
    private DBConfig dbConfig;

    @Bean(name = "datasource")
    public DataSource createDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dbConfig.getUrl());
        datasource.setDriverClassName(dbConfig.getDriverClassName());
        datasource.setUsername(dbConfig.getUsername());
        datasource.setPassword(dbConfig.getPassword());
        datasource.setMaxActive(dbConfig.getMaxActive());
        datasource.setMinIdle(dbConfig.getMinIdle());
        datasource.setTestOnBorrow(true);

        return datasource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory createSqlSessionFactoryBean(@Qualifier("datasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        /** 设置mybatis configuration 扫描路径 */
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
        /** 添加mapper 扫描路径 */
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + MAPPER_PATH;
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver
            = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
        /** 设置datasource */
        sqlSessionFactoryBean.setDataSource(dataSource);
        /** 设置typeAlias 包扫描路径 */
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager createTransactionManager(@Qualifier("datasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
