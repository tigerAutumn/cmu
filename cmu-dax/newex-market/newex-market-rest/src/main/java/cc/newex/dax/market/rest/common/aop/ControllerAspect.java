package cc.newex.dax.market.rest.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * @author baoshuai.yang
 * @date 2018/03/18
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut(value = "@annotation(cc.newex.dax.market.rest.common.aop.ControllerLog)")
    private void pointcut() {
    }

    /**
     * 环绕
     */
    @Around(value = "pointcut() && @annotation(controllerLog)")
    public Object around(final ProceedingJoinPoint point, final ControllerLog controllerLog) {

        //拦截的类名
        final Class clazz = point.getTarget().getClass();
        //拦截的方法
        final String methodName = point.getSignature().getName();

        try {
            final Object ret = point.proceed();
            //执行程序
            return ret;
        } catch (final Throwable throwable) {
            ControllerAspect.log.error("error class:" + StringUtils.substringAfterLast(clazz.getName(), ".") + ",method:" + methodName,
                throwable);

        }
        return null;

    }

}
