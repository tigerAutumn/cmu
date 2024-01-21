package cc.newex.extension.autoconfigure;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.test.TestingServer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Liu Hailin
 * @create 2017-08-17 下午8:44
 **/
@Configuration
@ConditionalOnExpression("'${task.center.serverList}'.length() > 0")
@Slf4j
public class ZkRegistryCenterAutoConfigure {

    @Bean(value = "regCenter", initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${task.center.serverList}") final String serverList,
                                             @Value("${task.center.namespace}") final String namespace) {

        if (serverList.equals( "testing" ) || serverList.equals( "test" )) {
            try {
                TestingServer testingServer = new TestingServer();
                return new ZookeeperRegistryCenter(
                    new ZookeeperConfiguration( testingServer.getConnectString(), namespace ) );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ZookeeperRegistryCenter( new ZookeeperConfiguration( serverList, namespace ) );
    }

    @Component
    static class ShutDownQuartz implements DisposableBean {

        @Autowired
        private ZookeeperRegistryCenter center;

        @Override
        public void destroy() throws Exception {
            center.close();
            log.info( "Spring Container will ShutDown" );
            Thread.sleep( 20000 );
        }
    }
}
