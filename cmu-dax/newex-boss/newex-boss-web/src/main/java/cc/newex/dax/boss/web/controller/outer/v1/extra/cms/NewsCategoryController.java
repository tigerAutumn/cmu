package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.common.ComboBoxGroupVO;
import cc.newex.dax.boss.web.model.common.ComboTreeVO;
import cc.newex.dax.boss.web.model.extra.cms.NewsCategoryExtraVO;
import cc.newex.dax.extra.client.ExtraCmsNewsAdminClient;
import cc.newex.dax.extra.dto.cms.NewsCategoryDTO;
import com.google.common.collect.Lists;
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
import java.util.ArrayList;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-06-04
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/cms/news/categories")
public class NewsCategoryController {

    @Autowired
    private ExtraCmsNewsAdminClient extraCmsNewsAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增NewsCategory")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_CATEGORY_ADD"})
    public ResponseResult add(@Valid final NewsCategoryExtraVO newsCategoryExtraVO, final HttpServletRequest request) {
        Integer parentId = newsCategoryExtraVO.getParentId();
        if (parentId == null || parentId < 0) {
            parentId = 0;
        }

        final NewsCategoryDTO newsCategoryDTO = NewsCategoryDTO.builder()
                .parentId(parentId)
                .locale(newsCategoryExtraVO.getLocale())
                .name(newsCategoryExtraVO.getName())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsNewsAdminClient.saveNewsCategory(newsCategoryDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改NewsCategory")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_CATEGORY_EDIT"})
    public ResponseResult edit(@Valid final NewsCategoryExtraVO newsCategoryExtraVO, final Integer id,
                               final HttpServletRequest request) {
        Integer parentId = newsCategoryExtraVO.getParentId();
        if (parentId == null || parentId < 0) {
            parentId = 0;
        }

        final NewsCategoryDTO newsCategoryDTO = NewsCategoryDTO.builder()
                .id(id)
                .parentId(parentId)
                .locale(newsCategoryExtraVO.getLocale())
                .name(newsCategoryExtraVO.getName())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsNewsAdminClient.updateNewsCategory(newsCategoryDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取NewsCategory列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_CATEGORY_VIEW"})
    public ResponseResult list(@RequestParam(value = "locale", required = false) final String locale,
                               @RequestParam(value = "name", required = false) final String name,
                               final DataGridPager<NewsCategoryDTO> pager) {
        final NewsCategoryDTO.NewsCategoryDTOBuilder builder = NewsCategoryDTO.builder();

        if (StringUtils.isNotBlank(locale)) {
            builder.locale(locale);
        }

        if (StringUtils.isNotBlank(name)) {
            builder.name(name);
        }

        final NewsCategoryDTO newsCategoryDTO = builder.build();
        pager.setQueryParameter(newsCategoryDTO);

        final ResponseResult responseResult = this.extraCmsNewsAdminClient.listNewsCategory(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

    @GetMapping(value = "/list/all")
    @OpLog(name = "获取NewsCategory列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_CATEGORY_VIEW"})
    public Object listAll() {
        try {
            final ResponseResult responseResult = this.extraCmsNewsAdminClient.listAllNewsCategory();

            if (responseResult == null) {
                return new ArrayList<NewsCategoryExtraVO>();
            }

            final Object data = responseResult.getData();
            if (data == null) {
                return new ArrayList<NewsCategoryExtraVO>();
            }

            return data;
        } catch (final Exception e) {
            log.error("get newsCategory list api error: " + e.getMessage(), e);
        }

        return Lists.newArrayListWithCapacity(0);
    }

    @GetMapping(value = "/list/all/group")
    @OpLog(name = "获取NewsCategory列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_CATEGORY_VIEW"})
    public Object listAllByGroup() {
        try {
            final ResponseResult responseResult = this.extraCmsNewsAdminClient.listAllNewsCategoryByGroup();

            if (responseResult == null) {
                return new ArrayList<ComboBoxGroupVO>();
            }

            return responseResult.getData();
        } catch (final Exception e) {
            log.error("get newsCategory list api error: " + e.getMessage(), e);
        }

        return Lists.newArrayListWithCapacity(0);
    }

    @GetMapping(value = "/list/all/tree")
    @OpLog(name = "获取NewsCategory列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_NEWS_CATEGORY_VIEW"})
    public Object listAllByTree() {
        try {
            final ResponseResult responseResult = this.extraCmsNewsAdminClient.listAllNewsCategoryByTree();

            if (responseResult == null) {
                return new ArrayList<ComboTreeVO>();
            }

            final Object data = responseResult.getData();
            if (data == null) {
                return new ArrayList<ComboTreeVO>();
            }

            return data;
        } catch (final Exception e) {
            log.error("get newsCategory list api error: " + e.getMessage(), e);
        }

        return Lists.newArrayListWithCapacity(0);
    }

}
