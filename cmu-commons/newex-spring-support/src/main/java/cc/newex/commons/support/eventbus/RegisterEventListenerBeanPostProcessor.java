package cc.newex.commons.support.eventbus;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author newex-team
 * @date 2018/05/05
 */
@Service
@ConditionalOnBean(name = "myEventBusConfig")
public class RegisterEventListenerBeanPostProcessor implements BeanPostProcessor {
    @Resource(name = "myAsyncEventBus")
    private EventBus eventBus;

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(EventListener.class)) {
            this.eventBus.register(bean);
        }
        return bean;
    }
}
