package cc.newex.dax.extra.template;

import cc.newex.dax.extra.common.util.RegexUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>List标签</p>
 * <p>
 * <#list value="news">xxx</#list>
 * </p>
 *
 * @author liutiejun
 * @date 2018-08-07
 */
@Component
public class ListTagProcessor implements TagProcessor {

    @Autowired
    private NewsTagProcessor newsTagProcessor;

    @Autowired
    private AppDownloadPageTagProcessor appDownloadPageTagProcessor;

    public static final String TAG_REG = "<#list value=\"(.*?)\">([\\s\\S]*?)</#list>";

    @Override
    public String process(final String html) {
        return null;
    }

    @Override
    public String process(final String html, final Map<String, String> paramsMap) {
        return null;
    }

    @Override
    public String processList(String html, final List<Map<String, String>> paramsList) {
        if (StringUtils.isBlank(html) || CollectionUtils.isEmpty(paramsList)) {
            return html;
        }

        final List<String[]> tagInfoList = RegexUtils.getAllValue(html, TAG_REG, 0, 1, 2);
        if (CollectionUtils.isEmpty(tagInfoList)) {
            return html;
        }

        for (int i = 0; i < tagInfoList.size(); i++) {
            final String[] tagInfo = tagInfoList.get(i);

            final String realValue = this.getRealValue(tagInfo[1], tagInfo[2], paramsList);

            html = html.replace(tagInfo[0], realValue);
        }

        return html;
    }

    private String getRealValue(final String type, final String html, final List<Map<String, String>> paramsList) {

        switch (type) {
            case "news":
                return this.getRealValueForNews(html, paramsList);
            case "appDownloadPage":
                return this.getRealValueForAppDownloadPage(html, paramsList);
            default:
                return null;
        }

    }

    private String getRealValueForAppDownloadPage(final String html, final List<Map<String, String>> paramsList) {
        final List<String> htmlList = new ArrayList<>();

        for (final Map<String, String> paramsMap : paramsList) {
            htmlList.add(this.appDownloadPageTagProcessor.process(html, paramsMap));
        }

        return this.listToString(htmlList);
    }

    private String getRealValueForNews(final String html, final List<Map<String, String>> paramsList) {
        final List<String> htmlList = new ArrayList<>();

        for (final Map<String, String> paramsMap : paramsList) {
            htmlList.add(this.newsTagProcessor.process(html, paramsMap));
        }

        return this.listToString(htmlList);
    }

    private String listToString(final List<String> stringList) {
        if (CollectionUtils.isEmpty(stringList)) {
            return null;
        }

        final StringBuilder result = new StringBuilder();

        for (final String line : stringList) {
            result.append(line);
        }

        return result.toString();
    }
}
