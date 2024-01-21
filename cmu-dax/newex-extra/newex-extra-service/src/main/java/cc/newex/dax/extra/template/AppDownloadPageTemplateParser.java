package cc.newex.dax.extra.template;

import cc.newex.dax.extra.common.html.HTMLDecoder;
import cc.newex.dax.extra.common.util.ReflectUtils;
import cc.newex.dax.extra.common.util.RegexUtils;
import cc.newex.dax.extra.domain.cms.AppDownloadPage;
import cc.newex.dax.extra.domain.cms.NewsTemplate;
import cc.newex.dax.extra.service.admin.cms.AppDownloadPageAdminService;
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
 * @date 2018-08-08
 */
@Slf4j
@Component
public class AppDownloadPageTemplateParser implements TemplateParser {

    @Autowired
    private AppDownloadPageAdminService appDownloadPageAdminService;

    @Autowired
    private NewsTemplateAdminService newsTemplateAdminService;

    @Autowired
    private ListTagProcessor listTagProcessor;

    @Autowired
    private AppDownloadPageTagProcessor appDownloadPageTagProcessor;

    @Override
    public String parse(final String locale, final String uid) {
        final List<AppDownloadPage> appDownloadPageList = this.appDownloadPageAdminService.getByUid(locale, uid);

        if (CollectionUtils.isEmpty(appDownloadPageList)) {
            return null;
        }

        final AppDownloadPage appDownloadPage = appDownloadPageList.get(0);
        if (appDownloadPage == null) {
            return null;
        }

        String html = this.getHtml(appDownloadPage);
        final Map<String, String> paramsMap = this.getParamsMap(appDownloadPage);
        final List<Map<String, String>> paramsList = this.getParamsList(html);

        html = this.listTagProcessor.processList(html, paramsList);
        html = this.appDownloadPageTagProcessor.process(html, paramsMap);

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
        final List<AppDownloadPage> appDownloadPageList = this.appDownloadPageAdminService.getAll();
        if (CollectionUtils.isEmpty(appDownloadPageList)) {
            return null;
        }

        final List<Map<String, String>> paramsList = new ArrayList<>();

        for (final AppDownloadPage appDownloadPage : appDownloadPageList) {
            paramsList.add(this.getParamsMap(appDownloadPage));
        }

        return paramsList;
    }

    private Map<String, String> getParamsMap(final AppDownloadPage appDownloadPage) {

        return ReflectUtils.getAllFieldValue(appDownloadPage);
    }

    private String getHtml(final AppDownloadPage appDownloadPage) {
        final NewsTemplate newsTemplate = this.newsTemplateAdminService.getById(appDownloadPage.getTemplateId());
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
