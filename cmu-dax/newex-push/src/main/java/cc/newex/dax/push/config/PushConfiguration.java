package cc.newex.dax.push.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import cc.newex.commons.security.jwt.crypto.AesJwtTokenCryptoProvider;
import cc.newex.commons.security.jwt.crypto.JwtTokenCryptoProvider;
import cc.newex.commons.security.jwt.model.JwtConfig;
import cc.newex.commons.security.jwt.token.JwtTokenProvider;
import cc.newex.dax.push.handler.PushHandler;

/**
 * 配置HandlerMapping和Adapter以及相关的config
 *
 * @author xionghui
 * @date 2018/09/12
 */
@Configuration
public class PushConfiguration {
  @Value("${jwt.cryptoKey}")
  private String jwtKey;
  @Value("${jwt.secret}")
  private String jwtSecret;

  @Bean
  @Autowired
  public HandlerMapping webSocketMapping(final PushHandler pushHandler) {
    final Map<String, WebSocketHandler> map = new HashMap<>(1);
    map.put("/", pushHandler);

    final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
    mapping.setUrlMap(map);
    return mapping;
  }

  @Bean
  public WebSocketHandlerAdapter handlerAdapter() {
    return new WebSocketHandlerAdapter();
  }

  @Bean
  public JwtTokenProvider jwtTokenProvider() {
    final JwtConfig jwtConfig = new JwtConfig();
    jwtConfig.setCryptoKey(this.jwtKey);
    jwtConfig.setSecret(this.jwtSecret);
    final JwtTokenCryptoProvider cryptoProvider = new AesJwtTokenCryptoProvider();
    return new JwtTokenProvider(jwtConfig, cryptoProvider);
  }

  @Bean
  public RedisMessageListenerContainer redisMessageListenerContainer(
      RedisConnectionFactory redisConnectionFactory) {
    RedisMessageListenerContainer redisMessageListenerContainer =
        new RedisMessageListenerContainer();
    redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
    return redisMessageListenerContainer;
  }
}
