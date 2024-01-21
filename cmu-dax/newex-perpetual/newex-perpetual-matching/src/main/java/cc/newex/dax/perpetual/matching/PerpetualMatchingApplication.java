package cc.newex.dax.perpetual.matching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * matching启动类
 *
 * @author xionghui
 * @date 2018/10/17
 */
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients(basePackages = {
        "cc.newex.dax.asset.client",
        "cc.newex.dax.market.client",
        "cc.newex.dax.integration.client",
        "cc.newex.dax.users.client"})
@ComponentScan(basePackages = {
        "cc.newex.commons",
        "cc.newex.dax.perpetual"})
@SpringBootApplication
@Slf4j
public class PerpetualMatchingApplication {

    public static void main(final String[] args) {
        if (tryLockSocket()) {
            SpringApplication.run(PerpetualMatchingApplication.class, args);
            synchronized (PerpetualMatchingApplication.class) {
                while (true) {
                    try {
                        PerpetualMatchingApplication.class.wait();
                    } catch (final InterruptedException e) {
                        // swallow it
                    }
                }
            }
        }
    }

    /**
     * socket锁防止启动多个进程
     */
    private static boolean tryLockSocket() {
        try {
            final ServerSocketChannel serverSock = ServerSocketChannel.open();
            final InetSocketAddress addr = new InetSocketAddress(11111);
            serverSock.socket().bind(addr);
        } catch (final Exception e) {
            log.error("try lock socket fail: ", e);
            return false;
        }
        return true;
    }
}
