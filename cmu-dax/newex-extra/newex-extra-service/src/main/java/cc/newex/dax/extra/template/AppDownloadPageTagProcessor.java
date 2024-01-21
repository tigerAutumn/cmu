package cc.newex.dax.extra.template;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>AppDownloadPage字段取值标签</p>
 * <ul>
 * <li>1、${appDownloadPage.name}</li>
 * <li>2、${appDownloadPage.locale}</li>
 * <li>2、${appDownloadPage.uid}</li>
 * </ul>
 *
 * @author liutiejun
 * @date 2018-08-07
 */
@Component
public class AppDownloadPageTagProcessor implements TagProcessor {

    private static final String TAG_REG = "\\$\\{appDownloadPage.(.*?)\\}";

    @Override
    public String process(final String html) {
        return null;
    }

    @Override
    public String process(final String html, final Map<String, String> paramsMap) {
        return this.process(TAG_REG, html, paramsMap);
    }

    @Override
    public String processList(final String html, final List<Map<String, String>> paramsList) {
        return null;
    }

}
