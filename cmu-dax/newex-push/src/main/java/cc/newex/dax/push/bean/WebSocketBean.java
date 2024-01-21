package cc.newex.dax.push.bean;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.UnicastProcessor;

/**
 * 封装processor和session
 *
 * @author xionghui
 * @date 2018/09/14
 */
@Data
@Builder
public class WebSocketBean {

  private UnicastProcessor<WebSocketMessage> processor;
  private WebSocketSession session;
}
