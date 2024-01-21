package cc.newex.dax.extra.template;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>News字段取值标签</p>
 * <ul>
 * <li>1、${news.title}</li>
 * <li>2、${news.content}</li>
 * </ul>
 *
 * @author liutiejun
 * @date 2018-08-07
 */
@Component
public class NewsTagProcessor implements TagProcessor {

    private static final String TAG_REG = "\\$\\{news.(.*?)\\}";

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
