package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.cms.NewsTemplateExtraVO;
import cc.newex.dax.extra.client.ExtraCmsNewsAdminClient;
import cc.newex.dax.extra.dto.cms.NewsTemplateDTO;
import com.google.common.collect.Lists;
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
import java.util.ArrayList;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-06-04
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/cms/news/templates")
public class NewsTemplateController {

    @Autowired
    private ExtraCmsNewsAdminClient extraCmsNewsAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增NewsTemplate")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_TEMPLATE_ADD"})
    public ResponseResult add(@Valid final NewsTemplateExtraVO newsTemplateExtraVO, final HttpServletRequest request) {
        final NewsTemplateDTO newsTemplateDTO = NewsTemplateDTO.builder()
                .name(newsTemplateExtraVO.getName())
                .content(newsTemplateExtraVO.getContent())
                .memo(newsTemplateExtraVO.getMemo())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsNewsAdminClient.saveNewsTemplate(newsTemplateDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改NewsTemplate")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_TEMPLATE_EDIT"})
    public ResponseResult edit(@Valid final NewsTemplateExtraVO newsTemplateExtraVO, final Integer id,
                               final HttpServletRequest request) {
        final NewsTemplateDTO newsTemplateDTO = NewsTemplateDTO.builder()
                .id(id)
                .name(newsTemplateExtraVO.getName())
                .content(newsTemplateExtraVO.getContent())
                .memo(newsTemplateExtraVO.getMemo())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsNewsAdminClient.updateNewsTemplate(newsTemplateDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取NewsTemplate列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_TEMPLATE_VIEW"})
    public ResponseResult list(@RequestParam(value = "name", required = false) final String name, final DataGridPager<NewsTemplateDTO> pager) {
        final NewsTemplateDTO.NewsTemplateDTOBuilder builder = NewsTemplateDTO.builder();

        if (StringUtils.isNotBlank(name)) {
            builder.name(name);
        }

        final NewsTemplateDTO newsTemplateDTO = builder.build();
        pager.setQueryParameter(newsTemplateDTO);

        final ResponseResult responseResult = this.extraCmsNewsAdminClient.listNewsTemplate(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

    @GetMapping(value = "/list/all")
    @OpLog(name = "获取NewsTemplate列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_TEMPLATE_VIEW"})
    public Object listAll() {
        try {
            final ResponseResult responseResult = this.extraCmsNewsAdminClient.listAllNewsTemplate();

            if (responseResult == null) {
                return new ArrayList<NewsTemplateExtraVO>();
            }

            final Object data = responseResult.getData();
            if (data == null) {
                return new ArrayList<NewsTemplateExtraVO>();
            }

            return data;
        } catch (final Exception e) {
            log.error("get newsTemplate list api error: " + e.getMessage(), e);
        }

        return Lists.newArrayListWithCapacity(0);
    }

}
