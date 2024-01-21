package cc.newex.extension.autoconfigure;

import java.util.Map.Entry;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author Liu Hailin
 * @create 2017-10-25 下午6:46
 **/

@Configuration
@AutoConfigureAfter(ZkRegistryCenterAutoConfigure.class)
public class JobRegisterBean implements ApplicationContextAware, InitializingBean {

    @Autowired
    ZookeeperRegistryCenter zookeeperRegistryCenter;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (Entry<String, SimpleJob> entry : context.getBeansOfType( SimpleJob.class ).entrySet()) {

            SimpleJob job = entry.getValue();

            if (job.getClass().isAnnotationPresent( ElasticJobExtConfig.class )) {

                ElasticJobExtConfig config = job.getClass().getAnnotation( ElasticJobExtConfig.class );

                SpringJobScheduler springJobScheduler = new SpringJobScheduler( job, zookeeperRegistryCenter,
                    getLiteJobConfiguration
                        ( job.getClass(), config.cron(), config.shardingTotalCount(), "" ), new ElasticJobListener() {

                    @Override
                    public void beforeJobExecuted(ShardingContexts shardingContexts) {

                    }

                    @Override
                    public void afterJobExecuted(ShardingContexts shardingContexts) {

                    }
                } );
                springJobScheduler.init();
            }

        }
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder( new SimpleJobConfiguration( JobCoreConfiguration.newBuilder(
            jobClass.getName(), cron, shardingTotalCount ).shardingItemParameters( shardingItemParameters ).build(),
            jobClass.getCanonicalName() ) ).overwrite( true ).build();
    }

}
