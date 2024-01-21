package cc.newex.wallet.signature.autoconfig;

import cc.newex.wallet.signature.annotation.HessianClient;
import cc.newex.wallet.signature.scanner.HessianClientScanner;
import cc.newex.wallet.signature.security.HessianBagProxyFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author newex-team
 * @create 2018-11-21 下午3:55
 **/
@Configuration
@ConditionalOnProperty(value = "hessian.client.basePackage")
@Slf4j
public class HessianClientAutoConfiguration implements EnvironmentAware, BeanDefinitionRegistryPostProcessor {

    private RelaxedPropertyResolver resolver;

    @Override
    public void postProcessBeanDefinitionRegistry(final BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        final Set<BeanDefinition> serviceSet = this.scanHessianClient(beanDefinitionRegistry);

        final String remoteServer = this.resolver.getProperty("remoteServer");
        if (StringUtils.isBlank(remoteServer)) {
            HessianClientAutoConfiguration.log.error("Remote Server is Null........................");
            return;
        }

        for (final BeanDefinition beanDefinition : serviceSet) {
            final String beanName = beanDefinition.getBeanClassName();
            Class<?> cls = null;
            try {
                cls = Class.forName(beanName);
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (cls == null) {
                continue;
            }

            final AnnotationMetadata annotationMetadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();

            final Map<String, Object> annoAttr = annotationMetadata.getAnnotationAttributes(HessianClient.class.getName());

            final String serviceImpl = (String) annoAttr.get("value");

            final String serviceName = StringUtils.substringAfterLast(serviceImpl, ".");

            final String serviceBeanName = StringUtils.uncapitalize(serviceName);

            final String requestUrl = remoteServer + "/" + (serviceBeanName + "_provider")
                    .hashCode();

            final BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                    HessianBagProxyFactoryBean.class);

            beanDefinitionBuilder.addPropertyValue("serviceUrl", requestUrl);

            beanDefinitionBuilder.addPropertyValue("serviceInterface", cls);

            final String proxyFactoryBean = beanName + "_proxy";
            beanDefinitionRegistry.registerBeanDefinition(proxyFactoryBean,
                    beanDefinitionBuilder.getBeanDefinition());
        }

    }

    private Set<BeanDefinition> scanHessianClient(final BeanDefinitionRegistry beanDefinitionRegistry) {

        final String basePackage = this.resolver.getProperty("basePackage");
        if (StringUtils.isEmpty(basePackage)) {
            return Collections.emptySet();
        }
        final String[] basePackages = StringUtils.split(basePackage, ";");

        final HessianClientScanner serviceScanner = new HessianClientScanner(beanDefinitionRegistry);

        return serviceScanner.scanPackage(basePackages);
    }

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {

    }

    @Override
    public void setEnvironment(final Environment environment) {
        this.resolver = new RelaxedPropertyResolver(environment, "hessian.client.");
    }
}
