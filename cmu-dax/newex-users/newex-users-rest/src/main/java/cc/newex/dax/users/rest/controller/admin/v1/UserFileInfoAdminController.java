package cc.newex.dax.users.rest.controller.admin.v1;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.enums.FileTypeEnum;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.criteria.UserFileInfoExample;
import cc.newex.dax.users.domain.UserFileInfo;
import cc.newex.dax.users.dto.admin.UserFileInfoDTO;
import cc.newex.dax.users.service.admin.UserFileInfoService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户文件表 Admin Controller
 *
 * @author newex-team
 * @date 2018-08-15 18:27:33
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/users/file/info")
public class UserFileInfoAdminController {
    @Resource
    private UserFileInfoService userFileInfoService;

    /**
     * @param file
     * @description kyc上传图片
     * @date 2018/5/4 下午4:09
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult<String> uploadFile(@RequestPart(value = "file") final MultipartFile file, @RequestParam(value = "userId") final Long userId) {
        /**
         * 控制图片上传不大于100M
         */
        if (file.getSize() > 100L * 1024 * 1024) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_BAD_DATA);
        }
        try {
            final ResponseResult<String> responseResult = this.userFileInfoService.uploadFile(file, userId);
            return responseResult;
        } catch (final Exception e) {
            log.error("UserFileInfoAdminController uploadFile fail: ", e);
        }
        return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
    }

    @PostMapping(value = "")
    public ResponseResult<Integer> add(@RequestBody final UserFileInfoDTO dto) {
        final UserFileInfo po = ObjectCopyUtils.map(dto, UserFileInfo.class);
        if (!FileTypeEnum.checkType(po.getFileType())) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        return ResultUtils.success(this.userFileInfoService.add(po));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult<Integer> remove(@PathVariable("id") final Long id) {
        return ResultUtils.success(this.userFileInfoService.removeById(id));
    }

    @PutMapping(value = "")
    public ResponseResult<Integer> edit(@RequestBody final UserFileInfoDTO dto) {
        final UserFileInfo po = ObjectCopyUtils.map(dto, UserFileInfo.class);
        if (!FileTypeEnum.checkType(po.getFileType())) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        return ResultUtils.success(this.userFileInfoService.editById(po));
    }

    @PostMapping(value = "/pager")
    public ResponseResult<DataGridPagerResult<UserFileInfoDTO>> getPagerList(@RequestBody final DataGridPager pager,
                                                                             @RequestParam(value = "userId", required = false) final Long userId,
                                                                             @RequestParam(value = "fileType", required = false) final String fileType,
                                                                             @RequestParam(value = "fileName", required = false) final String fileName,
                                                                             @RequestParam(value = "filePath", required = false) final String filePath,
                                                                             @RequestParam(value = "note", required = false) final String note) {
        final PageInfo pageInfo = pager.toPageInfo();
        final UserFileInfoExample example = new UserFileInfoExample();
        final UserFileInfoExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotEmpty(fileType)) {
            criteria.andFileTypeEqualTo(fileType);
        }
        if (StringUtils.isNotEmpty(fileName)) {
            criteria.andFileNameLike('%' + fileName.trim() + '%');
        }
        if (StringUtils.isNotEmpty(filePath)) {
            criteria.andFilePathLike('%' + filePath.trim() + '%');
        }
        if (StringUtils.isNotEmpty(note)) {
            criteria.andNoteLike('%' + note.trim() + '%');
        }
        final List<UserFileInfo> domainList = this.userFileInfoService.getByPage(pageInfo, example);
        final List<UserFileInfoDTO> dtoList = Lists.newArrayListWithCapacity(CollectionUtils.size(domainList));
        domainList.forEach(domain -> {
            final UserFileInfoDTO dto = ObjectCopyUtils.map(domain, UserFileInfoDTO.class);
            dto.setFilePath(this.userFileInfoService.getFullPath(dto.getFilePath()));
            dtoList.add(dto);
        });
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), dtoList));
    }

    @GetMapping(value = "/{id}")
    public ResponseResult<UserFileInfoDTO> get(@PathVariable("id") final Long id) {
        final UserFileInfo po = this.userFileInfoService.getById(id);
        po.setFilePath(this.userFileInfoService.getFullPath(po.getFilePath()));
        return ResultUtils.success(ObjectCopyUtils.map(po, UserFileInfoDTO.class));
    }
}