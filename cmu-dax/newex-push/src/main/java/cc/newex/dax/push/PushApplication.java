package cc.newex.dax.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * @author xionghui
 * @date 2018/09/12
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = "cc.newex.dax.push.client")
public class PushApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(PushApplication.class);
    application.run(args);
  }
}
