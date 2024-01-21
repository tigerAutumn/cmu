package cc.newex.wallet;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.transaction.TransactionUtils;
import cc.newex.commons.support.web.CommonErrorController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"cc.newex.wallet", "cc.newex.commons"})
@ComponentScan(basePackages = {
        "cc.newex.commons",
        "cc.newex.wallet"},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {CommonErrorController.class, LocaleUtils.class, TransactionUtils.class})
)
public class Signature {
    public static void main(final String[] args) {
        SpringApplication.run(Signature.class, args);
    }
}
