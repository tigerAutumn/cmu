package cc.newex.wallet.signature.scanner;

import cc.newex.wallet.signature.annotation.HessianService;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Set;

/**
 * @author newex-team
 * @create 2018-11-21 上午11:04
 **/
public class HessianServerScanner extends ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    public HessianServerScanner(final BeanDefinitionRegistry registry) {
        this(registry, false);
    }

    public HessianServerScanner(final BeanDefinitionRegistry registry, final boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
        this.registry = registry;
        this.addHessianFilter();
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(final String... basePackages) {
        return super.doScan(basePackages);
    }

    private void addHessianFilter() {

        this.setIncludeAnnotationConfig(true);

        this.addIncludeFilter(new AnnotationTypeFilter(HessianService.class));
        this.addExcludeFilter(new TypeFilter() {
            @Override
            public boolean match(final MetadataReader metadataReader, final MetadataReaderFactory metadataReaderFactory)
                    throws IOException {
                final String className = metadataReader.getClassMetadata().getClassName();
                return className.endsWith("package-info");
            }
        });

    }

}
