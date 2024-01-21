package cc.newex.dax.extra.template;

import cc.newex.dax.extra.common.util.RegexUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @date 2018-08-07
 */
public interface TagProcessor {

    String process(String html);

    String process(String html, Map<String, String> paramsMap);

    String processList(String html, List<Map<String, String>> paramsList);

    default String process(final String tagReg, String html, final Map<String, String> paramsMap) {
        if (StringUtils.isBlank(tagReg) || StringUtils.isBlank(html) || MapUtils.isEmpty(paramsMap)) {
            return html;
        }

        final List<String[]> tagInfoList = RegexUtils.getAllValue(html, tagReg, 0, 1);
        if (CollectionUtils.isEmpty(tagInfoList)) {
            return html;
        }

        final Map<String, String> tagMap = new HashMap<>();

        for (final String[] tagInfo : tagInfoList) {
            final String tag = tagInfo[0];
            final String property = tagInfo[1];

            if (tagMap.get(tag) == null) {
                tagMap.put(tag, property);

                html = html.replace(tag, paramsMap.get(property));
            }
        }

        return html;
    }

}
