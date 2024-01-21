package cc.newex.dax.portal.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Order(3)
@RestControllerAdvice
public class PortalExceptionAdvice {
}
