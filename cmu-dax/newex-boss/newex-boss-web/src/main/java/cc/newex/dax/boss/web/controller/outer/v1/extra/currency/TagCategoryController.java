package cc.newex.dax.boss.web.controller.outer.v1.extra.currency;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.currency.TagCategoryExtraVO;
import cc.newex.dax.extra.client.ExtraCmsCurrencyAdminClient;
import cc.newex.dax.extra.dto.currency.TagCategoryDTO;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 标签分类
 *
 * @author liutiejun
 * @date 2018-07-20
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/currency/tag-category")
public class TagCategoryController {

    @Autowired
    private ExtraCmsCurrencyAdminClient extraCmsCurrencyAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增TagCategory")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TAG_CATEGORY_ADD"})
    public ResponseResult add(@Valid final TagCategoryExtraVO tagCategoryExtraVO, final HttpServletRequest request) {

        final TagCategoryDTO tagCategoryDTO = TagCategoryDTO.builder()
                .locale(tagCategoryExtraVO.getLocale())
                .code(tagCategoryExtraVO.getCode())
                .name(tagCategoryExtraVO.getName())
                .type(tagCategoryExtraVO.getType())
                .sort(tagCategoryExtraVO.getSort())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.saveTagCategory(tagCategoryDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改TagCategory")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TAG_CATEGORY_EDIT"})
    public ResponseResult edit(@Valid final TagCategoryExtraVO tagCategoryExtraVO, @RequestParam("id") final Long id,
                               final HttpServletRequest request) {

        final TagCategoryDTO tagCategoryDTO = TagCategoryDTO.builder()
                .id(id)
                .name(tagCategoryExtraVO.getName())
                .type(tagCategoryExtraVO.getType())
                .code(tagCategoryExtraVO.getCode())
                .locale(tagCategoryExtraVO.getLocale())
                .sort(tagCategoryExtraVO.getSort())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.updateTagCategory(tagCategoryDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除TagCategory")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TAG_CATEGORY_REMOVE"})
    public ResponseResult remove(@RequestParam("id") final Long id,@RequestParam("code") final String code ) {
        final ResponseResult result = this.extraCmsCurrencyAdminClient.removeTagCategory(id,code);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取TagCategory列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TAG_CATEGORY_VIEW"})
    public ResponseResult list(@RequestParam(value = "name", required = false) final String name,
                               @RequestParam(value = "type", required = false) final Integer type,
                               @RequestParam(value = "locale", required = false) final String locale, final DataGridPager<TagCategoryDTO> pager) {

        final TagCategoryDTO.TagCategoryDTOBuilder builder = TagCategoryDTO.builder();

        if (StringUtils.isNotBlank(name)) {
            builder.name(name);
        }

        if (StringUtils.isNotBlank(locale)) {
            builder.locale(locale);
        }

        if (type != null && type > 0) {
            builder.type(type);
        }

        final TagCategoryDTO tagCategoryDTO = builder.build();
        pager.setQueryParameter(tagCategoryDTO);

        final ResponseResult result = this.extraCmsCurrencyAdminClient.listTagCategory(pager);

        return ResultUtil.getDataGridResult(result);
    }

    @GetMapping(value = "/listAll")
    @OpLog(name = "获取所有的TagCategory列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TAG_CATEGORY_VIEW"})
    public ResponseResult listAll() {
        final ResponseResult result = this.extraCmsCurrencyAdminClient.listAllTagCategory();
        List<TagCategoryDTO> list = (List<TagCategoryDTO>) result.getData();
        if(!list.isEmpty()){

            Set<String> set = new HashSet<>();
            List<TagCategoryDTO> resultList = new ArrayList<>();
            list.forEach(t ->{
                if(!set.contains(t.getCode())){
                    resultList.add(t);
                    set.add(t.getCode());
                }
            } );
            result.setData(resultList);
        }
        return ResultUtil.getCheckedResponseResult(result);
    }

}
