package cc.newex.dax.asset.jobs.xxljobs;

import com.xxl.job.core.executor.XxlJobExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackages = "cc.newex.dax.asset.jobs.xxljobs")
public class XxlJobConfig {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.executor.ip}")
    private String ip;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;


    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobExecutor xxlJobExecutor() {
        XxlJobConfig.log.info(">>>>>>>>>>> xxl-job config init.");
        final XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
        xxlJobExecutor.setAdminAddresses(this.adminAddresses);
        xxlJobExecutor.setAppName(this.appName);
        xxlJobExecutor.setIp(this.ip);
        xxlJobExecutor.setPort(this.port);
        xxlJobExecutor.setAccessToken(this.accessToken);
        xxlJobExecutor.setLogPath(this.logPath);
        xxlJobExecutor.setLogRetentionDays(this.logRetentionDays);

        return xxlJobExecutor;
    }

}
