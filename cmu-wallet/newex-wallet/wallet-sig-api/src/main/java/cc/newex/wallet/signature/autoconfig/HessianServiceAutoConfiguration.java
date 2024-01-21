package cc.newex.wallet.signature.autoconfig;

import cc.newex.wallet.signature.KeyConfig;
import cc.newex.wallet.signature.annotation.HessianClient;
import cc.newex.wallet.signature.annotation.HessianService;
import cc.newex.wallet.signature.scanner.HessianServerScanner;
import cc.newex.wallet.signature.security.HessianBagServiceExporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author newex-team
 * @create 2018-11-21 上午11:20
 **/
@Configuration
@Slf4j
@ConditionalOnProperty(value = "hessian.server.basePackage")
public class HessianServiceAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {

    private RelaxedPropertyResolver resolver;

    private Class<?> getServiceInterface(final Class<?> beanClass) {

        Class<?> serviceInterface = null;

        if (AnnotationUtils.isAnnotationDeclaredLocally(HessianService.class, beanClass)) {

            for (final Class<?> interfaceClass : beanClass.getInterfaces()) {
                if (AnnotationUtils.isAnnotationDeclaredLocally(HessianClient.class, interfaceClass)) {
                    serviceInterface = interfaceClass;
                    break;
                }
            }
        }
        return serviceInterface;
    }

    private Set<BeanDefinitionHolder> scanHessianService(final BeanDefinitionRegistry beanDefinitionRegistry) {

        final String basePackage = this.resolver.getProperty("basePackage");
        final String[] basePackages = StringUtils.tokenizeToStringArray(basePackage, ";");

        final HessianServerScanner serviceScanner = new HessianServerScanner(beanDefinitionRegistry);
        //service 已经注册了
        return serviceScanner.doScan(basePackages);
    }

    @Override
    public void setEnvironment(final Environment environment) {
        this.resolver = new RelaxedPropertyResolver(environment, "hessian.server.");
    }

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {

        final Set<BeanDefinitionHolder> serviceHolder = this.scanHessianService(
                (BeanDefinitionRegistry) configurableListableBeanFactory);

        AnnotationConfigUtils.registerAnnotationConfigProcessors((BeanDefinitionRegistry) configurableListableBeanFactory);

        for (final BeanDefinitionHolder holder : serviceHolder) {

            final String beanName = holder.getBeanName();

            final Object beanEntity = configurableListableBeanFactory.getBean(beanName);

            final BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                    HessianBagServiceExporter.class);
            beanDefinitionBuilder.addPropertyReference("service", beanName);
            beanDefinitionBuilder.addPropertyValue("serviceInterface", this.getServiceInterface(beanEntity.getClass()));

            final String publisherName = "/" + (beanName + "_provider").hashCode();

            ((BeanDefinitionRegistry) configurableListableBeanFactory).registerBeanDefinition(publisherName,
                    beanDefinitionBuilder.getBeanDefinition());
        }
    }

    private String createKeyConfigBean(final ConfigurableListableBeanFactory configurableListableBeanFactory) {

        final String beanName = "keyConfig";
        final BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                KeyConfig.class);
        ((BeanDefinitionRegistry) configurableListableBeanFactory).registerBeanDefinition(beanName,
                beanDefinitionBuilder.getBeanDefinition());
        return beanName;
    }
}



