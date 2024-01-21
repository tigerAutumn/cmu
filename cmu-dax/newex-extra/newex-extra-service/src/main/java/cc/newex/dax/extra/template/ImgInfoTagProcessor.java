package cc.newex.dax.extra.template;

import cc.newex.dax.extra.common.util.ReflectUtils;
import cc.newex.dax.extra.common.util.RegexUtils;
import cc.newex.dax.extra.domain.cms.ImgInfo;
import cc.newex.dax.extra.service.admin.cms.ImgInfoAdminService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>图片标签</p>
 * <p>${imgInfo.imgUrl id="xxx"}</p>
 *
 * @author liutiejun
 * @date 2018-08-09
 */
@Component
public class ImgInfoTagProcessor implements TagProcessor {

    private static final String TAG_REG = "\\$\\{imgInfo.(.*?) id=\"(.*?)\"\\}";

    @Autowired
    private ImgInfoAdminService imgInfoAdminService;

    @Override
    public String process(String html) {
        if (StringUtils.isBlank(html)) {
            return html;
        }

        final List<String[]> tagInfoList = RegexUtils.getAllValue(html, TAG_REG, 0, 1, 2);
        if (CollectionUtils.isEmpty(tagInfoList)) {
            return html;
        }

        final Map<String, String> tagMap = new HashMap<>();

        for (final String[] tagInfo : tagInfoList) {
            final String tag = tagInfo[0];
            final String property = tagInfo[1];
            final Long id = Long.parseLong(tagInfo[2]);

            final Map<String, String> paramsMap = this.getParamsMap(id);

            if (MapUtils.isEmpty(paramsMap)) {
                continue;
            }

            if (tagMap.get(tag) == null) {
                tagMap.put(tag, property);

                html = html.replace(tag, paramsMap.get(property));
            }
        }

        return html;
    }

    private Map<String, String> getParamsMap(final Long id) {
        final ImgInfo imgInfo = this.imgInfoAdminService.getById(id);
        if (imgInfo == null) {
            return null;
        }

        return ReflectUtils.getAllFieldValue(imgInfo);
    }

    @Override
    public String process(final String html, final Map<String, String> paramsMap) {
        return null;
    }

    @Override
    public String processList(final String html, final List<Map<String, String>> paramsList) {
        return null;
    }

}
