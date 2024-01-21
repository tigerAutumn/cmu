package cc.newex.spring.boot.autoconfigure.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Data
@ConfigurationProperties(prefix = "newex.swagger")
public class SwaggerProperties {
    /**
     * host信息
     **/
    private String host = "";
    /**
     * 许可证
     **/
    private String license = "";
    /**
     * 许可证URL
     **/
    private String licenseUrl = "";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";
    /**
     * swagger联系人
     */
    private Contact contact = new Contact();
    /**
     * 分组文档
     **/
    private List<DocketInfo> dockets = new ArrayList<>();

    @Data
    public static class DocketInfo {
        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";
        /**
         * 标题
         **/
        private String title = "";
        /**
         * 描述
         **/
        private String description = "";
        /**
         * 版本
         **/
        private String version = "";
        /**
         * swagger会解析的url规则
         **/
        private List<String> basePath = new ArrayList<>();
        /**
         * 在basePath基础上需要排除的url规则
         **/
        private List<String> excludePath = new ArrayList<>();
        /**
         * 全局参数配置
         **/
        private List<GlobalOperationParameter> globalOperationParameters = new ArrayList<>();
        /**
         * swagger文档tags列表
         */
        private List<Tag> tags = new ArrayList<>();
        /**
         * swagger docket name
         * 名称必需要唯一
         **/
        private String name;
        /**
         * swagger docs组名称
         **/
        private String groupName;
    }

    @Data
    public static class GlobalOperationParameter {
        /**
         * 参数名
         **/
        private String name;
        /**
         * 描述信息
         **/
        private String description;
        /**
         * 指定参数类型
         **/
        private String modelRef;
        /**
         * 参数放在哪个地方:header,query,path,body.form
         **/
        private String parameterType;
        /**
         * 参数是否必须传
         **/
        private String required;
    }

    @Data
    public static class Tag {
        private String name;
        private String description;
    }

    @Data
    public static class Contact {
        private String name;
        private String url;
        private String email;
    }
}
