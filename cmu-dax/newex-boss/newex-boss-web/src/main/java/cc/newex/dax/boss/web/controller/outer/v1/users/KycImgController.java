package cc.newex.dax.boss.web.controller.outer.v1.users;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.users.client.UsersAdminClient;
import cc.newex.dax.users.client.UsersAdminUploadClient;
import cc.newex.dax.users.dto.admin.UserFileInfoDTO;
import cc.newex.dax.users.dto.common.PageResultDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminListReqDTO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author gi
 * @date 8/21/18
 */
@RestController
@Slf4j
@RequestMapping(value = "/v1/boss/users/kyc/img")
public class KycImgController {
    @Autowired
    private UsersAdminClient usersAdminClient;
    @Autowired
    private UsersAdminUploadClient usersAdminUploadClient;

    @GetMapping(value = "/list")
    @OpLog(name = "查看文件列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_IMG_VIEW"})
    public ResponseResult list(final DataGridPager pager, @RequestParam(value = "userId", required = false) final Long userId,
                               @RequestParam(value = "fileType", required = false) final String fileType,
                               @RequestParam(value = "fileName", required = false) final String fileName,
                               @RequestParam(value = "filePath", required = false) final String filePath,
                               @RequestParam(value = "note", required = false) final String note) {
        final ResponseResult result = this.usersAdminClient.fileInfoGetPagerList(pager, userId, fileType, fileName, filePath, note);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/upload")
    @OpLog(name = "上传文件")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_IMG_UPLOAD"})
    public ResponseResult upload(final MultipartFile file, @RequestParam(value = "userId") final Long userId) {
        final ResponseResult result = this.usersAdminUploadClient.fileInfoUploadFile(file, userId);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "保存文件")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_IMG_ADD"})
    public ResponseResult add(final UserFileInfoDTO dto) {
        final ResponseResult result = this.usersAdminClient.fileInfoAdd(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/batch-upload")
    @OpLog(name = "批量KYC上传文件")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_IMG_BATCH_UPLOAD"})
    public ResponseResult upload(final HttpServletRequest request) {
        final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        final List<MultipartFile> mFiles = multipartRequest.getFiles("file");
        for (final MultipartFile file : mFiles) {
            try {
                final String cardNumber = file.getOriginalFilename().subSequence(0, 18).toString();
                final UserKycAdminListReqDTO reqDTO = UserKycAdminListReqDTO.builder()
                        .cardNumber(cardNumber)
                        .level("2")
                        .build();
                final ResponseResult<PageResultDTO> responseResult = this.usersAdminClient.list(reqDTO);
                final Object user = responseResult.getData().getRows().get(0).toString();
                final JSONObject json = JSONObject.parseObject(user.toString());
                if (json.containsKey("userId")) {
                    final Long userId = Long.parseLong(json.get("userId").toString());
                    log.info(file.getName());
                    final ResponseResult result = this.usersAdminUploadClient.fileInfoUploadFile(file, userId);
                    final String imgUrl = result.getData().toString();
                    final UserFileInfoDTO dto = UserFileInfoDTO.builder()
                            .filePath(imgUrl)
                            .fileName(cardNumber)
                            .fileType("kyc2")
                            .userId(userId).build();
                    this.usersAdminClient.fileInfoAdd(dto);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return ResultUtils.success();
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑文件信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_IMG_EDIT"})
    public ResponseResult edit(final UserFileInfoDTO dto) {
        final ResponseResult result = this.usersAdminClient.fileInfoEdit(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/delete")
    @OpLog(name = "删除文件")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_KYC_IMG_DELETE"})
    public ResponseResult delete(@RequestParam(value = "id") final Long id) {
        final ResponseResult result = this.usersAdminClient.fileInfoRemove(id);
        return ResultUtil.getCheckedResponseResult(result);
    }

}
