package cc.newex.dax.extra.template;

import cc.newex.dax.extra.common.html.HTMLDecoder;
import cc.newex.dax.extra.common.util.ReflectUtils;
import cc.newex.dax.extra.common.util.RegexUtils;
import cc.newex.dax.extra.domain.cms.News;
import cc.newex.dax.extra.domain.cms.NewsTemplate;
import cc.newex.dax.extra.service.admin.cms.NewsAdminService;
import cc.newex.dax.extra.service.admin.cms.NewsTemplateAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @date 2018-08-07
 */
@Slf4j
@Component
public class NewsTemplateParser implements TemplateParser {

    @Autowired
    private NewsAdminService newsAdminService;

    @Autowired
    private NewsTemplateAdminService newsTemplateAdminService;

    @Autowired
    private ListTagProcessor listTagProcessor;

    @Autowired
    private NewsTagProcessor newsTagProcessor;

    @Override
    public String parse(final String locale, final String uid) {
        final List<News> newsList = this.newsAdminService.getByUid(locale, uid);

        if (CollectionUtils.isEmpty(newsList)) {
            return null;
        }

        final News news = newsList.get(0);
        if (news == null) {
            return null;
        }

        String html = this.getHtml(news);
        final Map<String, String> paramsMap = this.getParamsMap(news);
        final List<Map<String, String>> paramsList = this.getParamsList(html);

        html = this.listTagProcessor.processList(html, paramsList);
        html = this.newsTagProcessor.process(html, paramsMap);

        return html;
    }

    private List<Map<String, String>> getParamsList(final String html) {
        if (StringUtils.isBlank(html)) {
            return null;
        }

        if (RegexUtils.matches(html, ListTagProcessor.TAG_REG)) {
            return this.getParamsList();
        }

        return null;
    }

    private List<Map<String, String>> getParamsList() {
        final List<News> newsList = this.newsAdminService.getAll();
        if (CollectionUtils.isEmpty(newsList)) {
            return null;
        }

        final List<Map<String, String>> paramsList = new ArrayList<>();

        for (final News news : newsList) {
            paramsList.add(this.getParamsMap(news));
        }

        return paramsList;
    }

    private Map<String, String> getParamsMap(final News news) {
        news.setContent(HTMLDecoder.decode(news.getContent()));

        return ReflectUtils.getAllFieldValue(news);
    }

    private String getHtml(final News news) {
        final NewsTemplate newsTemplate = this.newsTemplateAdminService.getById(news.getTemplateId());
        if (newsTemplate == null) {
            return null;
        }

        String html = newsTemplate.getContent();
        if (StringUtils.isBlank(html)) {
            return null;
        }

        html = HTMLDecoder.decode(html);

        return html;
    }

}
