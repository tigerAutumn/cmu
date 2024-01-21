package cc.newex.dax.extra.rest.controller.outer.v1.common.cms;

import cc.newex.dax.extra.template.NewsTemplateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 网站新闻通知表 控制器类
 *
 * @author liutiejun
 * @date 2018-08-06
 */
@RestController
@RequestMapping(value = "/v1/extra/cms/news")
public class NewsController {

    @Autowired
    private NewsTemplateParser newsTemplateParser;

    @GetMapping("/source/{locale}/{uid}")
    public String getHtmlSource(@PathVariable("locale") final String locale, @PathVariable("uid") final String uid,
                                final HttpServletRequest request) {
        return this.newsTemplateParser.parse(locale, uid);
    }

}