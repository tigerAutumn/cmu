package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.util.UUIDUtils;
import cc.newex.dax.boss.web.model.extra.cms.AppDownloadPageExtraVO;
import cc.newex.dax.extra.client.ExtraCmsNewsAdminClient;
import cc.newex.dax.extra.dto.cms.AppDownloadPageDTO;
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
 * App下载页
 *
 * @author liutiejun
 * @date 2018-08-08
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/cms/news/app-download-page")
public class AppDownloadPageController {

    @Autowired
    private ExtraCmsNewsAdminClient extraCmsNewsAdminClient;

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增AppDownloadPage")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_APP_DOWNLOAD_PAGE_ADD"})
    public ResponseResult add(@Valid final AppDownloadPageExtraVO appDownloadPageExtraVO, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final AppDownloadPageDTO appDownloadPageDTO = AppDownloadPageDTO.builder()
                .name(appDownloadPageExtraVO.getName())
                .logoImg(this.getImgAbsolutePath(appDownloadPageExtraVO.getLogoImg()))
                .locale(appDownloadPageExtraVO.getLocale())
                .uid(appDownloadPageExtraVO.getUid())
                .link(appDownloadPageExtraVO.getLink())
                .androidUrl(appDownloadPageExtraVO.getAndroidUrl())
                .iosUrl(appDownloadPageExtraVO.getIosUrl())
                .status(appDownloadPageExtraVO.getStatus())
                .templateId(appDownloadPageExtraVO.getTemplateId())
                .firstTitle(appDownloadPageExtraVO.getFirstTitle())
                .firstIntro(appDownloadPageExtraVO.getFirstIntro())
                .firstImg(this.getImgAbsolutePath(appDownloadPageExtraVO.getFirstImg()))
                .secondTitle(appDownloadPageExtraVO.getSecondTitle())
                .secondIntro(appDownloadPageExtraVO.getSecondIntro())
                .secondImg(this.getImgAbsolutePath(appDownloadPageExtraVO.getSecondImg()))
                .thirdFeature1(appDownloadPageExtraVO.getThirdFeature1())
                .thirdFeature2(appDownloadPageExtraVO.getThirdFeature2())
                .thirdFeature3(appDownloadPageExtraVO.getThirdFeature3())
                .thirdFeature4(appDownloadPageExtraVO.getThirdFeature4())
                .thirdIntro1(appDownloadPageExtraVO.getThirdIntro1())
                .thirdIntro2(appDownloadPageExtraVO.getThirdIntro2())
                .thirdIntro3(appDownloadPageExtraVO.getThirdIntro3())
                .thirdIntro4(appDownloadPageExtraVO.getThirdIntro4())
                .thirdImg1(this.getImgAbsolutePath(appDownloadPageExtraVO.getThirdImg1()))
                .thirdImg2(this.getImgAbsolutePath(appDownloadPageExtraVO.getThirdImg2()))
                .thirdImg3(this.getImgAbsolutePath(appDownloadPageExtraVO.getThirdImg3()))
                .thirdImg4(this.getImgAbsolutePath(appDownloadPageExtraVO.getThirdImg4()))
                .publisherId(user.getId())
                .createdDate(new Date())
                .updatedDate(new Date())
                .brokerId(appDownloadPageExtraVO.getBrokerId())
                .build();

        final ResponseResult result = this.extraCmsNewsAdminClient.saveAppDownloadPage(appDownloadPageDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改AppDownloadPage")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_APP_DOWNLOAD_PAGE_EDIT"})
    public ResponseResult edit(@Valid final AppDownloadPageExtraVO appDownloadPageExtraVO, final Long id,
                               final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        String uid = appDownloadPageExtraVO.getUid();
        if (StringUtils.isBlank(uid)) {
            uid = UUIDUtils.getUUID32();
        }

        final AppDownloadPageDTO appDownloadPageDTO = AppDownloadPageDTO.builder()
                .id(id)
                .name(appDownloadPageExtraVO.getName())
                .logoImg(this.getImgAbsolutePath(appDownloadPageExtraVO.getLogoImg()))
                .locale(appDownloadPageExtraVO.getLocale())
                .uid(appDownloadPageExtraVO.getUid())
                .link(appDownloadPageExtraVO.getLink())
                .androidUrl(appDownloadPageExtraVO.getAndroidUrl())
                .iosUrl(appDownloadPageExtraVO.getIosUrl())
                .status(appDownloadPageExtraVO.getStatus())
                .templateId(appDownloadPageExtraVO.getTemplateId())
                .firstTitle(appDownloadPageExtraVO.getFirstTitle())
                .firstIntro(appDownloadPageExtraVO.getFirstIntro())
                .firstImg(this.getImgAbsolutePath(appDownloadPageExtraVO.getFirstImg()))
                .secondTitle(appDownloadPageExtraVO.getSecondTitle())
                .secondIntro(appDownloadPageExtraVO.getSecondIntro())
                .secondImg(this.getImgAbsolutePath(appDownloadPageExtraVO.getSecondImg()))
                .thirdFeature1(appDownloadPageExtraVO.getThirdFeature1())
                .thirdFeature2(appDownloadPageExtraVO.getThirdFeature2())
                .thirdFeature3(appDownloadPageExtraVO.getThirdFeature3())
                .thirdFeature4(appDownloadPageExtraVO.getThirdFeature4())
                .thirdIntro1(appDownloadPageExtraVO.getThirdIntro1())
                .thirdIntro2(appDownloadPageExtraVO.getThirdIntro2())
                .thirdIntro3(appDownloadPageExtraVO.getThirdIntro3())
                .thirdIntro4(appDownloadPageExtraVO.getThirdIntro4())
                .thirdImg1(this.getImgAbsolutePath(appDownloadPageExtraVO.getThirdImg1()))
                .thirdImg2(this.getImgAbsolutePath(appDownloadPageExtraVO.getThirdImg2()))
                .thirdImg3(this.getImgAbsolutePath(appDownloadPageExtraVO.getThirdImg3()))
                .thirdImg4(this.getImgAbsolutePath(appDownloadPageExtraVO.getThirdImg4()))
                .publisherId(user.getId())
                .updatedDate(new Date())
                .brokerId(appDownloadPageExtraVO.getBrokerId())
                .build();

        final ResponseResult result = this.extraCmsNewsAdminClient.updateAppDownloadPage(appDownloadPageDTO);

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
    @OpLog(name = "分页获取AppDownloadPage列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_APP_DOWNLOAD_PAGE_VIEW"})
    public ResponseResult list(@RequestParam(value = "name", required = false) final String name,
                               @RequestParam(value = "locale", required = false) final String locale,
                               @RequestParam(value = "uid", required = false) final String uid,
                               @CurrentUser final User loginUser, final DataGridPager<AppDownloadPageDTO> pager) {

        final AppDownloadPageDTO.AppDownloadPageDTOBuilder builder = AppDownloadPageDTO.builder().brokerId(loginUser.getLoginBrokerId());

        if (StringUtils.isNotBlank(name)) {
            builder.name(name);
        }

        if (StringUtils.isNotBlank(locale)) {
            builder.locale(locale);
        }

        if (StringUtils.isNotBlank(uid)) {
            builder.uid(uid);
        }

        final AppDownloadPageDTO appDownloadPageDTO = builder.build();
        pager.setQueryParameter(appDownloadPageDTO);

        final ResponseResult responseResult = this.extraCmsNewsAdminClient.listAppDownloadPage(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }
}
