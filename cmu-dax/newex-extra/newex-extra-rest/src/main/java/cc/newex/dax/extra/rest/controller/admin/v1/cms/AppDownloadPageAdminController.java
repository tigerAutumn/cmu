package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cms.AppDownloadPage;
import cc.newex.dax.extra.dto.cms.AppDownloadPageDTO;
import cc.newex.dax.extra.service.admin.cms.AppDownloadPageAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * App下载页
 *
 * @author liutiejun
 * @date 2018-08-08
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/news/app-download-page")
public class AppDownloadPageAdminController {

    @Autowired
    private AppDownloadPageAdminService appDownloadPageAdminService;

    private void updateLink(final AppDownloadPageDTO appDownloadPageDTO) {
        if (appDownloadPageDTO == null) {
            return;
        }

        final String uid = appDownloadPageDTO.getUid();
        if (StringUtils.isBlank(uid)) {
            return;
        }

        String link = null;

        if (AppEnvConsts.isDailyMode()) {
            link = "http://daily.cmx.com";
        } else if (AppEnvConsts.isDevelopmentMode()) {
            link = "http://test.cmx.com";
        } else if (AppEnvConsts.isPreMode()) {
            link = "https://pre.coinmex.com";
        } else if (AppEnvConsts.isProductionMode()) {
            link = "https://www.coinmex.com";
        } else {
            link = "http://localhost:8105";
        }

        link += "/v1/extra/public/app/" + uid;

        appDownloadPageDTO.setLink(link);
    }

    /**
     * 保存AppDownloadPage
     *
     * @param appDownloadPageDTO
     * @return
     */
    @PostMapping(value = "/saveAppDownloadPage")
    public ResponseResult saveAppDownloadPage(@RequestBody final AppDownloadPageDTO appDownloadPageDTO) {
        this.updateLink(appDownloadPageDTO);

        final ModelMapper mapper = new ModelMapper();
        final AppDownloadPage appDownloadPage = mapper.map(appDownloadPageDTO, AppDownloadPage.class);

        final int save = this.appDownloadPageAdminService.add(appDownloadPage);

        return ResultUtils.success(save);
    }

    /**
     * 更新AppDownloadPage
     *
     * @param appDownloadPageDTO
     * @return
     */
    @PostMapping(value = "/updateAppDownloadPage")
    public ResponseResult updateAppDownloadPage(@RequestBody final AppDownloadPageDTO appDownloadPageDTO) {
        this.updateLink(appDownloadPageDTO);

        final ModelMapper mapper = new ModelMapper();
        final AppDownloadPage appDownloadPage = mapper.map(appDownloadPageDTO, AppDownloadPage.class);

        final int update = this.appDownloadPageAdminService.editById(appDownloadPage);

        return ResultUtils.success(update);
    }

    /**
     * 删除AppDownloadPage
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeAppDownloadPage")
    public ResponseResult removeAppDownloadPage(@RequestParam("id") final Long id) {
        final int remove = this.appDownloadPageAdminService.removeById(id);

        return ResultUtils.success(remove);
    }

    /**
     * List AppDownloadPage
     *
     * @return
     */
    @PostMapping(value = "/listAppDownloadPage")
    public ResponseResult listAppDownloadPage(@RequestBody final DataGridPager<AppDownloadPageDTO> pager) {

        final DataGridPagerResult<AppDownloadPageDTO> result = this.appDownloadPageAdminService.getAppDownloadPagerInfo(pager);

        return ResultUtils.success(result);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getAppDownloadPageById")
    public ResponseResult getAppDownloadPageById(@RequestParam("id") final Long id) {
        final AppDownloadPage appDownloadPage = this.appDownloadPageAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final AppDownloadPageDTO appDownloadPageDTO = mapper.map(appDownloadPage, AppDownloadPageDTO.class);

        return ResultUtils.success(appDownloadPageDTO);
    }

}
