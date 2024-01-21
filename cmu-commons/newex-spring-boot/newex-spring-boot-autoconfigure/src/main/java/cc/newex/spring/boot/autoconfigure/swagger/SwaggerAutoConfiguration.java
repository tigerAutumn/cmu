package cc.newex.spring.boot.autoconfigure.swagger;

import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2017/12/09
 * @see <a href="http://springfox.github.io/springfox/docs/current/#getting-started">swagger</a>
 */
@Configuration
@EnableSwagger2
@ConditionalOnClass(EnableSwagger2.class)
@EnableConfigurationProperties({SwaggerProperties.class})
public class SwaggerAutoConfiguration implements BeanFactoryAware {
    private final SwaggerProperties swaggerProperties;
    private BeanFactory beanFactory;

    public SwaggerAutoConfiguration(final SwaggerProperties properties) {
        this.swaggerProperties = properties;
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public List<Docket> createRestApi() {
        final ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) this.beanFactory;

        final List<Docket> docketList = new ArrayList<>();
        final List<SwaggerProperties.DocketInfo> docketInfos = this.swaggerProperties.getDockets();
        for (final SwaggerProperties.DocketInfo docketInfo : docketInfos) {
            if (docketInfo.getBasePath().isEmpty()) {
                docketInfo.getBasePath().add("/**");
            }
            final Iterable<Predicate<String>> basePath =
                    docketInfo.getBasePath()
                            .stream()
                            .map(PathSelectors::ant)
                            .collect(Collectors.toList());
            final Iterable<Predicate<String>> excludePath =
                    docketInfo.getExcludePath()
                            .stream()
                            .map(PathSelectors::ant)
                            .collect(Collectors.toList());

            final Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    .host(this.swaggerProperties.getHost())
                    .groupName(docketInfo.getGroupName())
                    .globalOperationParameters(
                            this.buildGlobalOperationParameters(docketInfo.getGlobalOperationParameters()))
                    .tags(new Tag("Other", "其他"), this.buildTags(docketInfo.getTags()))
                    .apiInfo(this.apiInfo(docketInfo))
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
                    .paths(PathSelectors.any())
                    .build();

            configurableBeanFactory.registerSingleton(docketInfo.getName(), docket);
            docketList.add(docket);
        }
        return docketList;
    }

    private ApiInfo apiInfo(final SwaggerProperties.DocketInfo docketInfo) {
        Contact contact = null;
        if (this.swaggerProperties.getContact() != null) {
            contact = new Contact(this.swaggerProperties.getContact().getName(),
                    this.swaggerProperties.getContact().getUrl(),
                    this.swaggerProperties.getContact().getEmail());
        }

        return new ApiInfoBuilder()
                .title(docketInfo.getTitle())
                .description(docketInfo.getDescription())
                .version(docketInfo.getVersion())
                .termsOfServiceUrl(this.swaggerProperties.getTermsOfServiceUrl())
                .license(this.swaggerProperties.getLicense())
                .licenseUrl(this.swaggerProperties.getLicenseUrl())
                .contact(contact)
                .build();
    }

    private List<Parameter> buildGlobalOperationParameters(
            final List<SwaggerProperties.GlobalOperationParameter> globalOperationParameters) {
        final List<Parameter> parameters = Lists.newArrayList();
        for (final SwaggerProperties.GlobalOperationParameter parameter : globalOperationParameters) {
            parameters.add(new ParameterBuilder()
                    .name(parameter.getName())
                    .description(parameter.getDescription())
                    .modelRef(new ModelRef(parameter.getModelRef()))
                    .parameterType(parameter.getParameterType())
                    .required(Boolean.parseBoolean(parameter.getRequired()))
                    .build());
        }
        return parameters;
    }

    private Tag[] buildTags(final List<SwaggerProperties.Tag> tags) {
        return tags.stream()
                .map(tag -> new Tag(tag.getName(), tag.getDescription()))
                .collect(Collectors.toList())
                .toArray(new Tag[]{});
    }

}
