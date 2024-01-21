package cc.newex.wallet.signature.scanner;

import cc.newex.wallet.signature.annotation.HessianClient;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author newex-team
 * @create 2018-11-21 上午11:04
 **/
public class HessianClientScanner extends ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    public HessianClientScanner(final BeanDefinitionRegistry registry) {
        this(registry, false);
    }

    public HessianClientScanner(final BeanDefinitionRegistry registry, final boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
        this.registry = registry;
    }

    public Set<BeanDefinition> scanPackage(final String... basePackages) {
        final Set<BeanDefinition> beanDefinitions = new LinkedHashSet();
        for (final String b : basePackages) {
            final Set<BeanDefinition> set = this.findCandidateComponents(b);
            beanDefinitions.addAll(set);
        }

        return beanDefinitions;
    }

    @Override
    public Set<BeanDefinition> findCandidateComponents(final String basePackage) {
        final LinkedHashSet candidates = new LinkedHashSet();

        final ResourcePatternResolver resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(
                this.getResourceLoader());
        try {
            final String packageSearchPath = "classpath*:" + this.resolveBasePackage(basePackage) + '/' + "**/*.class";
            final Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);

            for (final Resource resource : resources) {

                if (resource.isReadable()) {
                    try {
                        final MetadataReader metadataReader = this.getMetadataReaderFactory().getMetadataReader(resource);
                        final ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                        sbd.setResource(resource);
                        sbd.setSource(resource);
                        if (metadataReader.getAnnotationMetadata().hasAnnotation(HessianClient.class.getName())) {
                            candidates.add(sbd);
                        }
                    } catch (final Throwable var13) {
                        throw new BeanDefinitionStoreException("Failed to read candidate component class: " + resource,
                                var13);
                    }
                }
            }

            return candidates;
        } catch (final IOException var14) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", var14);
        }
    }
}
