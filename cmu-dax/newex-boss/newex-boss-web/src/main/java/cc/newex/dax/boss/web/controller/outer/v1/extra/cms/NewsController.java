package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.util.UUIDUtils;
import cc.newex.dax.boss.web.model.extra.cms.NewsExtraVO;
import cc.newex.dax.extra.client.ExtraCmsNewsAdminClient;
import cc.newex.dax.extra.dto.cms.NewsDTO;
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
import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-06-03
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/cms/news")
public class NewsController {

    @Autowired
    private ExtraCmsNewsAdminClient extraCmsNewsAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增News")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_ADD"})
    public ResponseResult add(@Valid final NewsExtraVO newsExtraVO, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final NewsDTO newsDTO = NewsDTO.builder()
                .categoryId(newsExtraVO.getCategoryId())
                .templateId(newsExtraVO.getTemplateId())
                .publisherId(user.getId())
                .uid(UUIDUtils.getUUID32())
                .locale(newsExtraVO.getLocale())
                .title(newsExtraVO.getTitle())
                .content(newsExtraVO.getContent())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsNewsAdminClient.saveNews(newsDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改News")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_EDIT"})
    public ResponseResult edit(@Valid final NewsExtraVO newsExtraVO, final Integer id,
                               final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        String uid = newsExtraVO.getUid();
        if (StringUtils.isBlank(uid)) {
            uid = UUIDUtils.getUUID32();
        }

        final NewsDTO newsDTO = NewsDTO.builder()
                .id(id)
                .categoryId(newsExtraVO.getCategoryId())
                .templateId(newsExtraVO.getTemplateId())
                .publisherId(user.getId())
                .uid(uid)
                .locale(newsExtraVO.getLocale())
                .title(newsExtraVO.getTitle())
                .content(newsExtraVO.getContent())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsNewsAdminClient.updateNews(newsDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取News列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_VIEW"})
    public ResponseResult list(@RequestParam(value = "categoryId", required = false) final Integer categoryId,
                               @RequestParam(value = "locale", required = false) final String locale,
                               @RequestParam(value = "title", required = false) final String title, final DataGridPager<NewsDTO> pager) {

        final NewsDTO.NewsDTOBuilder builder = NewsDTO.builder();

        if (categoryId != null && categoryId > 0) {
            builder.categoryId(categoryId);
        }

        if (StringUtils.isNotBlank(locale)) {
            builder.locale(locale);
        }

        if (StringUtils.isNotBlank(title)) {
            builder.title(title);
        }

        final NewsDTO newsDTO = builder.build();
        pager.setQueryParameter(newsDTO);

        final ResponseResult responseResult = this.extraCmsNewsAdminClient.listNews(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

}
