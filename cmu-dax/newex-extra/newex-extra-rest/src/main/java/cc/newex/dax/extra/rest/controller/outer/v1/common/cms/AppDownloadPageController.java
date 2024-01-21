package cc.newex.dax.extra.rest.controller.outer.v1.common.cms;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.dax.extra.template.AppDownloadPageTemplateParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @date 2018-08-08
 */
@RestController
@RequestMapping(value = "/v1/extra/public/app")
public class AppDownloadPageController {

    @Autowired
    private AppDownloadPageTemplateParser appDownloadPageTemplateParser;

    @GetMapping("/{uid}")
    public String getHtmlSource(@RequestParam(value = "locale", required = false) String locale, @PathVariable("uid") final String uid,
                                final HttpServletRequest request) {

        if (StringUtils.isBlank(locale)) {
            locale = LocaleUtils.getLocale(request);
        }

        return this.appDownloadPageTemplateParser.parse(locale, uid);
    }

}
