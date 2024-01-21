package cc.newex.dax.users.client;

import cc.newex.commons.support.model.ResponseResult;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author newex-team
 * @date 2018/8/21
 */
@FeignClient(value = "${newex.feignClient.dax.users}", path = "/admin/v1/users/file/info", configuration = UsersAdminUploadClient.MultipartSupportConfig.class)
public interface UsersAdminUploadClient {

    /**
     * 文件上传
     *
     * @param file
     * @param userId
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseResult<String> fileInfoUploadFile(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "userId") final Long userId);


    class MultipartSupportConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(this.messageConverters));
        }
    }
}
