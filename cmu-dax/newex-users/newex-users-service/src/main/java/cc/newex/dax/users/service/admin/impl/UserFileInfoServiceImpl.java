package cc.newex.dax.users.service.admin.impl;

import cc.newex.commons.fileupload.FileUploadResponse;
import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.criteria.UserFileInfoExample;
import cc.newex.dax.users.data.UserFileInfoRepository;
import cc.newex.dax.users.domain.UserFileInfo;
import cc.newex.dax.users.service.admin.UserFileInfoService;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户文件表 服务实现
 *
 * @author newex-team
 * @date 2018-08-15 18:27:33
 */
@Slf4j
@Service
public class UserFileInfoServiceImpl extends AbstractCrudService<UserFileInfoRepository, UserFileInfo, UserFileInfoExample, Long> implements UserFileInfoService {
    @Autowired
    private UserFileInfoRepository userFileInfoRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    protected UserFileInfoExample getPageExample(final String fieldName, final String keyword) {
        final UserFileInfoExample example = new UserFileInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public ResponseResult<String> uploadFile(final MultipartFile file, final Long userId) {
        final String currentDay = DateUtil.getCurrentFormatTime();
        final Long currentTime = System.currentTimeMillis();
        final String path = Joiner.on('/').skipNulls().join(currentDay,
                currentTime.toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), "."));

        final FileUploadResponse fileUploadResponse;
        try {
            fileUploadResponse = this.fileUploadService.uploadFile(path, file.getInputStream());
        } catch (final Exception e) {
            log.error("uploadFile is error ,userId={}", userId, e);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
        }

        return ResultUtils.success(fileUploadResponse.getFileName());
    }

    @Override
    public String getFullPath(final String fileName) {
        return this.fileUploadService.getSignedUrl(fileName);
    }
}