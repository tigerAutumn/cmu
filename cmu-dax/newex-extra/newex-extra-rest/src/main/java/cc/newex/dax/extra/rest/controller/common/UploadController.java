package cc.newex.dax.extra.rest.controller.common;

import cc.newex.commons.fileupload.FileUploadResponse;
import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 文件、图片上传API
 *
 * @author newex-team
 * @date 2018/6/19
 */
@Slf4j
@RestController
@RequestMapping("/v1/extra/customer")
public class UploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/uploadFile")
    public ResponseResult uploadFile(final HttpServletRequest request) throws IOException {
        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        multipartResolver.setDefaultEncoding("utf-8");

//        if (!multipartResolver.isMultipart(request)) {
//            return ResultUtils.success();
//        }

        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

        final List<MultipartFile> files = multiRequest.getFiles("file");
        if (CollectionUtils.isEmpty(files)) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.UPLOAD_FILE_NOT_EXIST);
        }

        final String fileName = files.get(0).getOriginalFilename();
        final String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

        log.info("fileName: {}", fileName);

        if (StringUtil.isNotAllowedFileExtension(fileName)) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.INCORRECT_FILE_TYPE);
        }

        final long fileSize = files.get(0).getSize();
        final long maxSize = 5 * 1024 * 1024;

        if (fileSize <= 0) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.INCORRECT_FILE_TO_SMALL);
        }

        if (fileSize > maxSize) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.INCORRECT_FILE_TO_LARGE);
        }

        final FileUploadResponse fileUploadResponse = this.fileUploadService.uploadFile(String.format("%s.%s", UUID.randomUUID().toString(), prefix), new
                ByteArrayInputStream(files.get(0).getBytes()));

        log.info("fileUploadResponse: {}", fileUploadResponse.toString());

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileName", fileUploadResponse.getFileName());

        return ResultUtils.success(jsonObject);
    }

}
