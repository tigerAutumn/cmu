package cc.newex.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author newex-team
 * @data 26/03/2018
 */
@SpringBootApplication(scanBasePackages = {"cc.newex.wallet", "cc.newex.commons"})
@EnableConfigurationProperties
@ComponentScan(basePackages = {
        "cc.newex.commons",
        "cc.newex.wallet"},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                //classes = {CommonErrorController.class, LocaleUtils.class, CustomErrorController.class},
                pattern = {"cc.newex.commons.spring.boot.*", "cc.newex.commons.support.i18n.*", "cc.newex.commons.support.*"})
)
public class JobsApplication {
    public static void main(final String[] args) {
        SpringApplication.run(JobsApplication.class, args);
    }
}
