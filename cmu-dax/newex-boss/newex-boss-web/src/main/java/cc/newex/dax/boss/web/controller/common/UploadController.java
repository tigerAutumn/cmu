package cc.newex.dax.boss.web.controller.common;

import cc.newex.commons.fileupload.FileUploadResponse;
import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.activity.client.uploadFile.UploadFileClient;
import cc.newex.dax.activity.dto.ccex.lockasset.UploadDTO;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.web.common.config.FillUploadConfig;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author allen
 * @date 2018/5/18
 * @des
 */
@RestController
@RequestMapping(value = "/v1/boss/upload")
@Slf4j
public class UploadController {
    /**
     * 上传文件最大容量为8M
     */
    private static final long UPLOAD_FILE_MAX_SIZE = 8 * 1024 * 1024;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private FillUploadConfig fillUploadConfig;

    @Autowired
    private UploadFileClient uploadFileClient;

    /**
     * 图片上传接口
     *
     * @return
     */
    @RequestMapping(value = "/oss-upload")
    public ResponseResult ossFile(final HttpServletRequest request) {
        return this.uploadFile(request, "imgfile");
    }

    /**
     * 前台页面同时存在2个上传时候用
     *
     * @return
     */
    @RequestMapping(value = "/oss-upload1")
    public ResponseResult ossFile1(final HttpServletRequest request) {
        return this.uploadFile(request, "imgfile1");
    }

    /**
     * 前台页面同时存在3个图片上传用
     *
     * @return
     */
    @RequestMapping(value = "/oss-upload2")
    public ResponseResult ossFile2(final HttpServletRequest request) {
        return this.uploadFile(request, "imgfile2");
    }

    private ResponseResult uploadFile(final HttpServletRequest request, final String name) {
//        log.info("--> sg-coinmex-test-tk getBucketAcl: {}", this.s3Client.getBucketAcl("sg-coinmex-test-tk"));

        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        multipartResolver.setDefaultEncoding("utf-8");
        if (multipartResolver.isMultipart(request)) {
            final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            final List<MultipartFile> file = multiRequest.getFiles(name);

            if (file == null || file.get(0) == null || file.get(0).getSize() <= 0) {
                return ResultUtils.failure(BizErrorCodeEnum.NO_IMAGE);
            }

            String imgPath = null;
            try {
                String part1 = UUID.randomUUID().toString();
                final String filename = file.get(0).getOriginalFilename();
                final String prefix = filename.substring(filename.lastIndexOf(".") + 1);
                final List<String> availablePrefix = Arrays.asList("jpeg", "jpg", "png");
                if (!availablePrefix.contains(prefix.toLowerCase())) {
                    return ResultUtils.failure(BizErrorCodeEnum.IMAGE_SUPPORT_TYPE);
                }

                final long fileSize = file.get(0).getSize();
                if (fileSize <= 0 || fileSize >= UPLOAD_FILE_MAX_SIZE) {
                    return ResultUtils.failure(BizErrorCodeEnum.FILE_TOO_LARGE_8M);
                }

                part1 = part1 + "." + prefix;
                final FileUploadResponse fileUploadResponse = this.fileUploadService.uploadFileWithPublicRead(
                        String.format("%s/%s", this.fillUploadConfig.getExtraPath(), part1),
                        new ByteArrayInputStream(file.get(0).getBytes()));
                imgPath = fileUploadResponse.getFileName();
            } catch (final Exception e) {
                log.error("update s3 error", e);
            }
            final JSONObject json = new JSONObject();
            json.put("s3path", imgPath);
            return ResultUtils.success(json);
        } else {
            return ResultUtils.failure(BizErrorCodeEnum.FILEUPLOAD_ERROR);
        }
    }

    @PostMapping(value = "/upload")
    public ResponseResult uploadFile(final MultipartFile file) {
        if (file == null || file.getSize() <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.FILEUPLOAD_ERROR);
        }

        final String imgPath = null;
        String fileName = null;
        try {
            final String part1 = UUID.randomUUID().toString();
            final String filename = file.getOriginalFilename();
            if (StringUtil.isNotAllowedImageFileExtension(filename)) {
                return ResultUtils.failure(BizErrorCodeEnum.IMAGE_SUPPORT_TYPE);
            }

            final long fileSize = file.getSize();
            if (fileSize <= 0) {
                return ResultUtils.failure(BizErrorCodeEnum.FILEUPLOAD_ERROR);
            }
            if (fileSize > UPLOAD_FILE_MAX_SIZE) {
                return ResultUtils.failure(BizErrorCodeEnum.FILE_TOO_LARGE_8M);
            }

            final FileUploadResponse fileUploadResponse = this.fileUploadService.uploadFile(part1, new ByteArrayInputStream(file.getBytes()));
            fileName = fileUploadResponse.getFileName();
        } catch (final IOException e) {
            log.error("图片上传出现异常", e);
        }

        final JSONObject json = new JSONObject();
        json.put("imgPath", imgPath);
        json.put("fileName", fileName);
        return ResultUtils.success(json);
    }

    @PostMapping("/extra/upload")
    public ResponseResult uploadFile(final HttpServletRequest request) throws IOException {
        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        multipartResolver.setDefaultEncoding("utf-8");

        if (!multipartResolver.isMultipart(request)) {
            return ResultUtils.success();
        }

        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

        final List<MultipartFile> files = multiRequest.getFiles("files");
        if (CollectionUtils.isEmpty(files)) {
            return ResultUtils.success();
        }

        final String fileName = files.get(0).getOriginalFilename();
        final String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

        final List<String> availablePrefix = Arrays.asList("jpeg", "jpg", "png", "bmp");
        if (!availablePrefix.contains(prefix.toLowerCase())) {
            return ResultUtils.failure(BizErrorCodeEnum.IMAGE_SUPPORT_TYPE);
        }

        final long fileSize = files.get(0).getSize();
        final long maxSize = 8 * 1024 * 1024;

        if (fileSize <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.FILE_TOO_SMALL);
        }

        if (fileSize > maxSize) {
            return ResultUtils.failure(BizErrorCodeEnum.FILE_TOO_LARGE_8M);
        }

        final FileUploadResponse fileUploadResponse = this.fileUploadService.uploadFile(String.format("%s.%s", UUID.randomUUID().toString(), prefix), new
                ByteArrayInputStream(files.get(0).getBytes()));

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileName", fileUploadResponse.getFileName());

        return ResultUtils.success(jsonObject);
    }

    @PostMapping("/public/upload")
    public ResponseResult publicUploadFile(final HttpServletRequest request) throws IOException {
        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        multipartResolver.setDefaultEncoding("utf-8");

        if (!multipartResolver.isMultipart(request)) {
            return ResultUtils.success();
        }

        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

        final List<MultipartFile> files = multiRequest.getFiles("file");
        if (CollectionUtils.isEmpty(files)) {
            return ResultUtils.success();
        }

        final String fileName = files.get(0).getOriginalFilename();
        final String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

        final List<String> availablePrefix = Arrays.asList("jpeg", "jpg", "png", "bmp");
        if (!availablePrefix.contains(prefix.toLowerCase())) {
            return ResultUtils.failure(BizErrorCodeEnum.IMAGE_SUPPORT_TYPE);
        }

        final long fileSize = files.get(0).getSize();
        final long maxSize = 8 * 1024 * 1024;

        if (fileSize <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.FILE_TOO_SMALL);
        }

        if (fileSize > maxSize) {
            return ResultUtils.failure(BizErrorCodeEnum.FILE_TOO_LARGE_8M);
        }

        final FileUploadResponse fileUploadResponse = this.fileUploadService.uploadFileWithPublicRead(String.format("%s.%s", UUID.randomUUID().toString(), prefix), new
                ByteArrayInputStream(files.get(0).getBytes()));

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileName", fileUploadResponse.getFileName());

        return ResultUtils.success(jsonObject);
    }

    @PostMapping("/activity/upload")
    public ResponseResult activityUploadFile(final HttpServletRequest request) throws IOException {
        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        multipartResolver.setDefaultEncoding("utf-8");

        if (!multipartResolver.isMultipart(request)) {
            return ResultUtils.success();
        }

        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

        final List<MultipartFile> files = multiRequest.getFiles("file");
        if (CollectionUtils.isEmpty(files)) {
            return ResultUtils.success();
        }

        final String fileName = files.get(0).getOriginalFilename();
        final String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

        final List<String> availablePrefix = Arrays.asList("jpeg", "jpg", "png", "bmp");
        if (!availablePrefix.contains(prefix.toLowerCase())) {
            return ResultUtils.failure(BizErrorCodeEnum.IMAGE_SUPPORT_TYPE);
        }

        final long fileSize = files.get(0).getSize();
        final long maxSize = 8 * 1024 * 1024;

        if (fileSize <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.FILE_TOO_SMALL);
        }

        if (fileSize > maxSize) {
            return ResultUtils.failure(BizErrorCodeEnum.FILE_TOO_LARGE_8M);
        }

        final UploadDTO uploadDTO = UploadDTO.builder().fileName(String.format("%s.%s", UUID.randomUUID().toString(), prefix)).fileByte(files.get(0).getBytes()).build();

        return this.uploadFileClient.uplaodFile(uploadDTO);

    }

}
