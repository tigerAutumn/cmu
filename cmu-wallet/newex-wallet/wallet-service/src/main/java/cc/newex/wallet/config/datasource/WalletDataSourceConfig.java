package cc.newex.wallet.config.datasource;

import cc.newex.commons.support.mybatis.AbstractDataSourceConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import static cc.newex.wallet.config.datasource.WalletDataSourceConfig.WALLET_DAO;

/**
 * @author newex-team
 * @data 26/03/2018
 */
@Configuration
@MapperScan(basePackages = {WALLET_DAO}, sqlSessionFactoryRef = "walletSqlSessionFactory")
public class WalletDataSourceConfig extends AbstractDataSourceConfig {
    static final String WALLET_DAO = "cc.newex.wallet.dao";

    private static final String[] MAPPER_LOCATIONS = {
            "classpath*:mapper/*.xml"
    };

    @Primary
    @ConfigurationProperties(prefix = "newex.wallet.datasource")
    @Bean(name = "walletDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(com.alibaba.druid.pool.DruidDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "walletTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("walletDataSource") final DataSource dataSource) {
        return this.createTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "walletSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("walletDataSource") final DataSource dataSource)
            throws Exception {
        return this.createSqlSessionFactory(dataSource, WalletDataSourceConfig.MAPPER_LOCATIONS);
    }

    @Primary
    @Bean(name = "walletSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("walletSqlSessionFactory") final
                                                 SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return this.createSqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "walletTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("walletTransactionManager") final
                                                   DataSourceTransactionManager transactionManager)
            throws Exception {
        return this.createTransactionTemplate(transactionManager);
    }


}
