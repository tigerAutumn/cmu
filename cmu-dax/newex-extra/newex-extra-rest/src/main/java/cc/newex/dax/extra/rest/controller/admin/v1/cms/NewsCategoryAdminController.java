package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.cms.NewsCategoryExample;
import cc.newex.dax.extra.domain.cms.NewsCategory;
import cc.newex.dax.extra.dto.cms.NewsCategoryDTO;
import cc.newex.dax.extra.dto.common.ComboBoxGroupDTO;
import cc.newex.dax.extra.dto.common.ComboTreeDTO;
import cc.newex.dax.extra.service.admin.cms.NewsCategoryAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新闻或文章类别表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/news/categories")
public class NewsCategoryAdminController {

    @Autowired
    private NewsCategoryAdminService newsCategoryAdminService;

    @PostMapping(value = "/saveNewsCategory")
    public ResponseResult saveNewsCategory(@RequestBody final NewsCategoryDTO newsCategoryDTO, final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final NewsCategory newsCategory = mapper.map(newsCategoryDTO, NewsCategory.class);

        final int save = this.newsCategoryAdminService.add(newsCategory);

        return ResultUtils.success(save);
    }

    @PostMapping(value = "/updateNewsCategory")
    public ResponseResult updateNewsCategory(@RequestBody final NewsCategoryDTO newsCategoryDTO, final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final NewsCategory newsCategory = mapper.map(newsCategoryDTO, NewsCategory.class);

        final int update = this.newsCategoryAdminService.editById(newsCategory);

        return ResultUtils.success(update);
    }

    @PostMapping(value = "/listNewsCategory")
    public ResponseResult listNewsCategory(@RequestBody final DataGridPager<NewsCategoryDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final NewsCategory newsCategory = mapper.map(pager.getQueryParameter(), NewsCategory.class);

        final NewsCategoryExample example = NewsCategoryExample.toExample(newsCategory);
        final List<NewsCategory> list = this.newsCategoryAdminService.getByPage(pageInfo, example);
        final List<NewsCategoryDTO> newsCategoryDTOS = mapper.map(
                list, new TypeToken<List<NewsCategoryDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), newsCategoryDTOS));
    }

    @PostMapping(value = "/listAllNewsCategory")
    public ResponseResult listAllNewsCategory() {
        final List<NewsCategory> newsCategoryList = this.newsCategoryAdminService.getAll();

        final ModelMapper mapper = new ModelMapper();

        final List<NewsCategoryDTO> newsCategoryDTOS = mapper.map(
                newsCategoryList, new TypeToken<List<NewsCategoryDTO>>() {
                }.getType()
        );

        return ResultUtils.success(newsCategoryDTOS);
    }

    private String getParentName(final Integer parentId, final Map<Integer, String> parentNameMap) {
        String group = parentNameMap.get(parentId);

        if (group != null) {
            return group;
        }

        final NewsCategory parent = this.newsCategoryAdminService.getById(parentId);

        if (parent != null) {
            group = parent.getName();

            parentNameMap.put(parentId, group);
        }

        return group;
    }

    @PostMapping(value = "/listAllNewsCategoryByGroup")
    public ResponseResult listAllNewsCategoryByGroup() {
        final List<NewsCategory> newsCategoryList = this.newsCategoryAdminService.getAll();

        if (CollectionUtils.isEmpty(newsCategoryList)) {
            return ResultUtils.success(new ArrayList<ComboBoxGroupDTO>());
        }

        final Map<Integer, ComboBoxGroupDTO> comboBoxGroupDTOMap = new HashMap<>();
        final Map<Integer, String> parentNameMap = new HashMap<>();

        Integer parentId = null;

        String group = null;
        String value = null;
        String text = null;

        for (final NewsCategory newsCategory : newsCategoryList) {
            parentId = newsCategory.getParentId();

            group = this.getParentName(parentId, parentNameMap);
            value = newsCategory.getId().toString();
            text = newsCategory.getName();

            final ComboBoxGroupDTO comboBoxGroupDTO = ComboBoxGroupDTO.builder()
                    .group(group)
                    .value(value)
                    .text(text)
                    .build();

            comboBoxGroupDTOMap.put(newsCategory.getId(), comboBoxGroupDTO);
        }

        final List<ComboBoxGroupDTO> comboBoxGroupDTOList = new ArrayList<>();
        comboBoxGroupDTOMap.forEach((k, v) -> {
            if (!parentNameMap.containsKey(k)) {
                comboBoxGroupDTOList.add(v);
            }
        });

        return ResultUtils.success(comboBoxGroupDTOList);
    }

    @PostMapping(value = "/listAllNewsCategoryByTree")
    public ResponseResult listAllNewsCategoryByTree() {
        final List<NewsCategory> newsCategoryList = this.newsCategoryAdminService.getAll();

        if (CollectionUtils.isEmpty(newsCategoryList)) {
            return ResultUtils.success(new ArrayList<ComboTreeDTO>());
        }

        final Map<Integer, ComboTreeDTO> comboTreeDTOMap = new HashMap<>();
        final Map<Integer, List<ComboTreeDTO>> parentMap = new HashMap<>();

        Integer parentId = null;

        Integer id = null;
        String text = null;

        ComboTreeDTO comboTreeDTO = null;
        List<ComboTreeDTO> comboTreeDTOList = null;

        for (final NewsCategory newsCategory : newsCategoryList) {
            parentId = newsCategory.getParentId();
            if (parentId == null || parentId < 0) {
                parentId = 0;
            }

            id = newsCategory.getId();
            text = newsCategory.getName();

            comboTreeDTO = ComboTreeDTO.builder()
                    .id(id)
                    .text(text)
                    .build();

            comboTreeDTOMap.put(id, comboTreeDTO);

            comboTreeDTOList = parentMap.get(parentId);
            if (comboTreeDTOList == null) {
                comboTreeDTOList = new ArrayList<>();

                parentMap.put(parentId, comboTreeDTOList);
            }

            comboTreeDTOList.add(comboTreeDTO);
        }

        comboTreeDTOMap.forEach((k, v) -> {
            final List<ComboTreeDTO> comboTreeDTOS = parentMap.get(k);
            if (CollectionUtils.isNotEmpty(comboTreeDTOS)) {
                v.setChildren(comboTreeDTOS.toArray(new ComboTreeDTO[0]));
            }
        });

        final List<ComboTreeDTO> rootList = parentMap.get(0);

        return ResultUtils.success(rootList);
    }

    @GetMapping(value = "/getNewsCategoryById")
    public ResponseResult getNewsCategoryById(@RequestParam("id") final Integer id, final HttpServletRequest request) {
        final NewsCategory newsCategory = this.newsCategoryAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final NewsCategoryDTO newsCategoryDTO = mapper.map(newsCategory, NewsCategoryDTO.class);

        return ResultUtils.success(newsCategoryDTO);
    }

}