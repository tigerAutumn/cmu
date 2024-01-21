package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.cms.AppDownloadPageDTO;
import cc.newex.dax.extra.dto.cms.ImgInfoDTO;
import cc.newex.dax.extra.dto.cms.NewsCategoryDTO;
import cc.newex.dax.extra.dto.cms.NewsDTO;
import cc.newex.dax.extra.dto.cms.NewsTemplateDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liutiejun
 * @date 2018-06-03
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/cms/news")
public interface ExtraCmsNewsAdminClient {

    /**
     * 保存News
     *
     * @param newsDTO
     * @return
     */
    @PostMapping(value = "/saveNews")
    ResponseResult saveNews(@RequestBody final NewsDTO newsDTO);

    /**
     * 更新News
     *
     * @param newsDTO
     * @return
     */
    @PostMapping(value = "/updateNews")
    ResponseResult updateNews(@RequestBody final NewsDTO newsDTO);

    /**
     * List News
     *
     * @return
     */
    @PostMapping(value = "/listNews")
    ResponseResult listNews(@RequestBody final DataGridPager<NewsDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getNewsById")
    ResponseResult getNewsById(@RequestParam("id") final Integer id);


    /**
     * 保存NewsCategory
     *
     * @param newsCategoryDTO
     * @return
     */
    @PostMapping(value = "/categories/saveNewsCategory")
    ResponseResult saveNewsCategory(@RequestBody final NewsCategoryDTO newsCategoryDTO);

    /**
     * 更新NewsCategory
     *
     * @param newsCategoryDTO
     * @return
     */
    @PostMapping(value = "/categories/updateNewsCategory")
    ResponseResult updateNewsCategory(@RequestBody final NewsCategoryDTO newsCategoryDTO);

    /**
     * List NewsCategory
     *
     * @return
     */
    @PostMapping(value = "/categories/listNewsCategory")
    ResponseResult listNewsCategory(@RequestBody final DataGridPager<NewsCategoryDTO> pager);

    /**
     * List All NewsCategory
     *
     * @return
     */
    @PostMapping(value = "/categories/listAllNewsCategory")
    ResponseResult listAllNewsCategory();

    /**
     * 以树形结构分组显示新闻类别
     *
     * @return
     */
    @PostMapping(value = "/listAllNewsCategoryByGroup")
    ResponseResult listAllNewsCategoryByGroup();

    /**
     * 以树形结构分组显示新闻类别
     *
     * @return
     */
    @PostMapping(value = "/categories/listAllNewsCategoryByTree")
    ResponseResult listAllNewsCategoryByTree();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/categories/getNewsCategoryById")
    ResponseResult getNewsCategoryById(@RequestParam("id") final Integer id);

    /**
     * 保存NewsTemplate
     *
     * @param newsTemplateDTO
     * @return
     */
    @PostMapping(value = "/templates/saveNewsTemplate")
    ResponseResult saveNewsTemplate(@RequestBody final NewsTemplateDTO newsTemplateDTO);

    /**
     * 更新NewsTemplate
     *
     * @param newsTemplateDTO
     * @return
     */
    @PostMapping(value = "/templates/updateNewsTemplate")
    ResponseResult updateNewsTemplate(@RequestBody final NewsTemplateDTO newsTemplateDTO);

    /**
     * List NewsTemplate
     *
     * @return
     */
    @PostMapping(value = "/templates/listNewsTemplate")
    ResponseResult listNewsTemplate(@RequestBody final DataGridPager<NewsTemplateDTO> pager);

    /**
     * List All NewsTemplate
     *
     * @return
     */
    @PostMapping(value = "/templates/listAllNewsTemplate")
    ResponseResult listAllNewsTemplate();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/templates/getNewsTemplateById")
    ResponseResult getNewsTemplateById(@RequestParam("id") final Integer id);

    /**
     * 保存AppDownloadPage
     *
     * @param appDownloadPageDTO
     * @return
     */
    @PostMapping(value = "/app-download-page/saveAppDownloadPage")
    ResponseResult saveAppDownloadPage(@RequestBody final AppDownloadPageDTO appDownloadPageDTO);

    /**
     * 更新AppDownloadPage
     *
     * @param appDownloadPageDTO
     * @return
     */
    @PostMapping(value = "/app-download-page/updateAppDownloadPage")
    ResponseResult updateAppDownloadPage(@RequestBody final AppDownloadPageDTO appDownloadPageDTO);

    /**
     * 删除AppDownloadPage
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/app-download-page/removeAppDownloadPage")
    ResponseResult removeAppDownloadPage(@RequestParam("id") final Long id);

    /**
     * List AppDownloadPage
     *
     * @return
     */
    @PostMapping(value = "/app-download-page/listAppDownloadPage")
    ResponseResult listAppDownloadPage(@RequestBody final DataGridPager<AppDownloadPageDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/app-download-page/getAppDownloadPageById")
    ResponseResult getAppDownloadPageById(@RequestParam("id") final Long id);

    /**
     * 保存ImgInfo
     *
     * @param imgInfoDTO
     * @return
     */
    @PostMapping(value = "/img-info/saveImgInfo")
    ResponseResult saveImgInfo(@RequestBody final ImgInfoDTO imgInfoDTO);

    /**
     * 更新ImgInfo
     *
     * @param imgInfoDTO
     * @return
     */
    @PostMapping(value = "/img-info/updateImgInfo")
    ResponseResult updateImgInfo(@RequestBody final ImgInfoDTO imgInfoDTO);

    /**
     * 删除ImgInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/img-info/removeImgInfo")
    ResponseResult removeImgInfo(@RequestParam("id") final Long id);

    /**
     * List ImgInfo
     *
     * @return
     */
    @PostMapping(value = "/img-info/listImgInfo")
    ResponseResult listImgInfo(@RequestBody final DataGridPager<ImgInfoDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/img-info/getImgInfoById")
    ResponseResult getImgInfoById(@RequestParam("id") final Long id);

}
