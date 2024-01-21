package cc.newex.extension.autoconfigure;

import java.util.Map.Entry;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import com.dangdang.ddframe.job.executor.handler.JobProperties.JobPropertiesEnum;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.google.common.base.Strings;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author Liu Hailin
 * @create 2017-07-26 下午1:41
 **/
//@Configuration
//@AutoConfigureAfter(ZkRegistryCenterAutoConfigure.class)
//@Slf4j
public class JobAutoConfigure implements ApplicationContextAware, BeanFactoryPostProcessor {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
        throws BeansException {
        for (Entry<String, ElasticJob> entry : context.getBeansOfType(ElasticJob.class).entrySet()) {

            ElasticJob job = entry.getValue();

            if (job.getClass().isAnnotationPresent(ElasticJobExtConfig.class)) {

                ElasticJobExtConfig config = job.getClass().getAnnotation(ElasticJobExtConfig.class);

                DefaultListableBeanFactory factory = (DefaultListableBeanFactory)configurableListableBeanFactory;

                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                    SpringJobScheduler.class);

                beanDefinitionBuilder.setInitMethodName("init");
                beanDefinitionBuilder.addConstructorArgValue(job);
                beanDefinitionBuilder.addConstructorArgReference("regCenter");

                beanDefinitionBuilder.addConstructorArgValue(createLiteJobConfigurationBeanDefinition(config,job.getClass().getName()));

                beanDefinitionBuilder.addConstructorArgValue(new ElasticJobListener() {

                    @Override
                    public void beforeJobExecuted(ShardingContexts shardingContexts) {

                    }

                    @Override
                    public void afterJobExecuted(ShardingContexts shardingContexts) {

                    }
                });
                String key = job.getClass().getName() + "_inner";

                factory.registerBeanDefinition(key, beanDefinitionBuilder.getBeanDefinition());
            }

        }
    }

    private BeanDefinition createLiteJobConfigurationBeanDefinition(ElasticJobExtConfig config, String name) {
        BeanDefinitionBuilder result = BeanDefinitionBuilder.rootBeanDefinition(LiteJobConfiguration.class);
        result.addConstructorArgValue(this.getJobTypeConfigurationBeanDefinition(config, name));
        result.addConstructorArgValue(true);
        result.addConstructorArgValue(-1);
        result.addConstructorArgValue(-1);
        result.addConstructorArgValue("");
        result.addConstructorArgValue(10);
        result.addConstructorArgValue(false);
        result.addConstructorArgValue(true);
        return result.getBeanDefinition();
    }

    private BeanDefinition createJobCoreBeanDefinition(ElasticJobExtConfig config, String name) {

        BeanDefinitionBuilder jobCoreBeanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(JobCoreConfiguration.class);
        jobCoreBeanDefinitionBuilder.addConstructorArgValue(name);
        jobCoreBeanDefinitionBuilder.addConstructorArgValue(config.cron());
        jobCoreBeanDefinitionBuilder.addConstructorArgValue(config.shardingTotalCount());
        jobCoreBeanDefinitionBuilder.addConstructorArgValue("");
        jobCoreBeanDefinitionBuilder.addConstructorArgValue("");
        jobCoreBeanDefinitionBuilder.addConstructorArgValue(false);
        jobCoreBeanDefinitionBuilder.addConstructorArgValue(true);
        jobCoreBeanDefinitionBuilder.addConstructorArgValue("");
        jobCoreBeanDefinitionBuilder.addConstructorArgValue(this.createJobPropertiesBeanDefinition());

        return jobCoreBeanDefinitionBuilder.getBeanDefinition();
    }


    private BeanDefinition createJobPropertiesBeanDefinition() {
        BeanDefinitionBuilder result = BeanDefinitionBuilder.rootBeanDefinition(JobProperties.class);
        return result.getBeanDefinition();
    }

    protected BeanDefinition getJobTypeConfigurationBeanDefinition(ElasticJobExtConfig config, String name) {
        BeanDefinitionBuilder result = BeanDefinitionBuilder.rootBeanDefinition(SimpleJobConfiguration.class);
        result.addConstructorArgValue(createJobCoreBeanDefinition(config, name));
        result.addConstructorArgValue(name);
        return result.getBeanDefinition();
    }
}
