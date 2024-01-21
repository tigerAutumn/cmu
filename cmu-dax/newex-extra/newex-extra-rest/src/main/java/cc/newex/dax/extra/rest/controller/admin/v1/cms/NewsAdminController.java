package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.html.HTMLDecoder;
import cc.newex.dax.extra.criteria.cms.NewsExample;
import cc.newex.dax.extra.domain.cms.News;
import cc.newex.dax.extra.dto.cms.NewsDTO;
import cc.newex.dax.extra.service.admin.cms.NewsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 新闻文章表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/news")
public class NewsAdminController {

    @Autowired
    private NewsAdminService newsAdminService;

    private void updateLink(final NewsDTO newsDTO) {
        if (newsDTO == null) {
            return;
        }

        final String uid = newsDTO.getUid();
        final String locale = newsDTO.getLocale();
        if (StringUtils.isBlank(uid) || StringUtils.isBlank(locale)) {
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

        link += "/v1/extra/cms/news/source/" + locale + "/" + uid;

        newsDTO.setLink(link);
    }

    /**
     * 保存News
     *
     * @param newsDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/saveNews")
    public ResponseResult saveNews(@RequestBody final NewsDTO newsDTO, final HttpServletRequest request) {
        this.updateLink(newsDTO);

        final ModelMapper mapper = new ModelMapper();
        final News news = mapper.map(newsDTO, News.class);

        final int save = this.newsAdminService.add(news);

        return ResultUtils.success(save);
    }

    /**
     * 更新News
     *
     * @param newsDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/updateNews")
    public ResponseResult updateNews(@RequestBody final NewsDTO newsDTO, final HttpServletRequest request) {
        this.updateLink(newsDTO);

        final ModelMapper mapper = new ModelMapper();
        final News news = mapper.map(newsDTO, News.class);

        final int update = this.newsAdminService.editById(news);

        return ResultUtils.success(update);
    }

    /**
     * List News
     *
     * @return
     */
    @PostMapping(value = "/listNews")
    public ResponseResult listNews(@RequestBody final DataGridPager<NewsDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final News news = mapper.map(pager.getQueryParameter(), News.class);
        final NewsExample example = NewsExample.toExample(news);

        final List<News> list = this.newsAdminService.getByPage(pageInfo, example);
        final List<NewsDTO> newsDTOS = mapper.map(
                list, new TypeToken<List<NewsDTO>>() {
                }.getType()
        );

        if (CollectionUtils.isNotEmpty(newsDTOS)) {
            newsDTOS.forEach(newsDTO -> newsDTO.setContent(HTMLDecoder.decode(newsDTO.getContent())));
        }

        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), newsDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/getNewsById")
    public ResponseResult getNewsById(@RequestParam("id") final Integer id, final HttpServletRequest request) {
        final News news = this.newsAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final NewsDTO newsDTO = mapper.map(news, NewsDTO.class);

        return ResultUtils.success(newsDTO);
    }

}