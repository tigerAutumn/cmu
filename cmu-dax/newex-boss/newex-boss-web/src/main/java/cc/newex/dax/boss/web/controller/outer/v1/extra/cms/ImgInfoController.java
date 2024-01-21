package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.cms.ImgInfoExtraVO;
import cc.newex.dax.extra.client.ExtraCmsNewsAdminClient;
import cc.newex.dax.extra.dto.cms.ImgInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-08-08
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/cms/news/img-info")
public class ImgInfoController {

    @Autowired
    private ExtraCmsNewsAdminClient extraCmsNewsAdminClient;

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增ImgInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_IMG_INFO_ADD"})
    public ResponseResult add(@Valid final ImgInfoExtraVO imgInfoExtraVO, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final ImgInfoDTO imgInfoDTO = ImgInfoDTO.builder()
                .name(imgInfoExtraVO.getName())
                .imgUrl(this.getImgAbsolutePath(imgInfoExtraVO.getImgUrl()))
                .publisherId(user.getId())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsNewsAdminClient.saveImgInfo(imgInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改ImgInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_IMG_INFO_EDIT"})
    public ResponseResult edit(@Valid final ImgInfoExtraVO imgInfoExtraVO, final Long id,
                               final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final ImgInfoDTO imgInfoDTO = ImgInfoDTO.builder()
                .id(id)
                .name(imgInfoExtraVO.getName())
                .imgUrl(this.getImgAbsolutePath(imgInfoExtraVO.getImgUrl()))
                .publisherId(user.getId())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsNewsAdminClient.updateImgInfo(imgInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    private String getImgAbsolutePath(final String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        if (StringUtils.startsWithIgnoreCase(url, "https://")) {
            return url;
        }

        return this.fileUploadService.getUrl(url);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取ImgInfo列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_IMG_INFO_VIEW"})
    public ResponseResult list(@RequestParam(value = "name", required = false) final String name, final DataGridPager<ImgInfoDTO> pager) {

        final ImgInfoDTO.ImgInfoDTOBuilder builder = ImgInfoDTO.builder();

        if (StringUtils.isNotBlank(name)) {
            builder.name(name);
        }

        final ImgInfoDTO imgInfoDTO = builder.build();
        pager.setQueryParameter(imgInfoDTO);

        final ResponseResult responseResult = this.extraCmsNewsAdminClient.listImgInfo(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

}
