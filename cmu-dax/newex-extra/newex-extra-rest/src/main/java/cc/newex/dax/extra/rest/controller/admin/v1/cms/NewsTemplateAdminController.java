package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.html.HTMLDecoder;
import cc.newex.dax.extra.criteria.cms.NewsTemplateExample;
import cc.newex.dax.extra.domain.cms.NewsTemplate;
import cc.newex.dax.extra.dto.cms.NewsTemplateDTO;
import cc.newex.dax.extra.service.admin.cms.NewsTemplateAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
 * 新闻模板表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/news/templates")
public class NewsTemplateAdminController {

    @Autowired
    private NewsTemplateAdminService newsTemplateAdminService;

    @PostMapping(value = "/saveNewsTemplate")
    public ResponseResult saveNewsTemplate(@RequestBody final NewsTemplateDTO newsTemplateDTO, final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final NewsTemplate newsTemplate = mapper.map(newsTemplateDTO, NewsTemplate.class);

        final int save = this.newsTemplateAdminService.add(newsTemplate);

        return ResultUtils.success(save);
    }

    @PostMapping(value = "/updateNewsTemplate")
    public ResponseResult updateNewsTemplate(@RequestBody final NewsTemplateDTO newsTemplateDTO, final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final NewsTemplate newsTemplate = mapper.map(newsTemplateDTO, NewsTemplate.class);

        final int update = this.newsTemplateAdminService.editById(newsTemplate);

        return ResultUtils.success(update);
    }

    @PostMapping(value = "/listNewsTemplate")
    public ResponseResult listNewsTemplate(@RequestBody final DataGridPager<NewsTemplateDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final NewsTemplate newsTemplate = mapper.map(pager.getQueryParameter(), NewsTemplate.class);
        final NewsTemplateExample example = NewsTemplateExample.toExample(newsTemplate);

        final List<NewsTemplate> list = this.newsTemplateAdminService.getByPage(pageInfo, example);
        final List<NewsTemplateDTO> newsTemplateDTOS = mapper.map(
                list, new TypeToken<List<NewsTemplateDTO>>() {
                }.getType()
        );

        if (CollectionUtils.isNotEmpty(newsTemplateDTOS)) {
            newsTemplateDTOS.forEach(newsTemplateDTO -> newsTemplateDTO.setContent(HTMLDecoder.decode(newsTemplateDTO.getContent())));
        }

        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), newsTemplateDTOS));
    }

    @PostMapping(value = "/listAllNewsTemplate")
    public ResponseResult listAllNewsTemplate() {
        final List<NewsTemplate> newsTemplateList = this.newsTemplateAdminService.getAll();

        final ModelMapper mapper = new ModelMapper();

        final List<NewsTemplateDTO> newsTemplateDTOS = mapper.map(
                newsTemplateList, new TypeToken<List<NewsTemplateDTO>>() {
                }.getType()
        );

        return ResultUtils.success(newsTemplateDTOS);
    }

    @GetMapping(value = "/getNewsTemplateById")
    public ResponseResult getNewsTemplateById(@RequestParam("id") final Integer id, final HttpServletRequest request) {
        final NewsTemplate newsTemplate = this.newsTemplateAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final NewsTemplateDTO newsTemplateDTO = mapper.map(newsTemplate, NewsTemplateDTO.class);

        return ResultUtils.success(newsTemplateDTO);
    }

}