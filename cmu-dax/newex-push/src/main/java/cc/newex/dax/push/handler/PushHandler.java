package cc.newex.dax.push.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;

import cc.newex.dax.push.bean.RegisterBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.bean.enums.ErrorCodeEnum;
import cc.newex.dax.push.cache.PushCache;
import cc.newex.dax.push.exception.PushRuntimeException;
import cc.newex.dax.push.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

/**
 * 处理push的数据
 *
 * @author xionghui
 * @date 2018/09/12
 */
@Slf4j
@Component
public class PushHandler implements WebSocketHandler {
  /**
   * 每秒限流1000 permits
   */
  private final RateLimiter rateLimiter = RateLimiter.create(1000);

  @Autowired
  private ApplicationContext applicationContext;

  @Override
  public Mono<Void> handle(final WebSocketSession session) {
    // get ip
    String ip = this.parseClientIp(session.getHandshakeInfo().getHeaders());
    log.info("PushHandler handle parseClientIp: {}", ip);
    PushCache.initIp(ip);
    UnicastProcessor<WebSocketMessage> processor = UnicastProcessor.create();
    Mono<Void> input = session.receive().doOnNext(msg -> {
      try {
        String text = msg.getPayloadAsText();
        log.info("PushHandler dealMsg: {}, {}", session.getId(), text);
        // idle update
        boolean check = PushCache.updateIdle(session.getId(), processor);
        if (!check) {
          log.error("PushHandler session illegal: {}", session.getId());
          throw new PushRuntimeException(ErrorCodeEnum.SESSION_INVILD);
        }
        // 限流检查
        if (!this.rateLimiter.tryAcquire()) {
          log.error("PushHandler rateLimiter: {}", this.rateLimiter.toString());
          throw new PushRuntimeException(ErrorCodeEnum.RATE_LIMIT);
        }
        RegisterBean registerBean = JSONObject.parseObject(text, RegisterBean.class);
        RegisterService registerService =
            this.applicationContext.getBean(registerBean.getEvent(), RegisterService.class);
        registerService.register(ip,
            WebSocketBean.builder().processor(processor).session(session).build(), registerBean);
      } catch (PushRuntimeException pe) {
        log.error("PushHandler handle PushRuntimeException: ", pe);
        this.dealError(processor, session, pe.getErrorCodeEnum());
      } catch (Exception e) {
        log.error("PushHandler handle Exception: ", e);
        this.dealError(processor, session, ErrorCodeEnum.EXCEPTION);
      }
    }).then();

    Flux<WebSocketMessage> hotFlux = processor.publish().autoConnect();
    // 向client发送数据
    Mono<Void> output = session.send(hotFlux);
    // idle check begin
    PushCache.initIdle(session.getId(), ip, processor);
    return Mono.zip(input, output).then();
  }

  /**
   * 解析客户端ip: 需要获取X-Forwarded-For，因为有三层代理：加速代理->ELB->Nginx
   */
  private String parseClientIp(HttpHeaders headers) {
    if (headers != null) {
      for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
        if ("X-Real-IP".equals(entry.getKey())) {
          List<String> value = entry.getValue();
          if (value != null && value.size() == 1) {
            return value.get(0);
          }
        }
      }
    }
    return "0.0.0.0";
  }

  /**
   * 出错处理
   */
  private void dealError(UnicastProcessor<WebSocketMessage> processor, WebSocketSession session,
      ErrorCodeEnum errorCodeEnum) {
    JSONObject data = new JSONObject();
    data.put("error_code", errorCodeEnum.code());
    data.put("error_msg", errorCodeEnum.errorMsg());
    data.put("result", false);
    processor.onNext(session.textMessage(data.toString()));
  }
}
