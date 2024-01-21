package cc.newex.dax.extra.rest.controller.admin.v1.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import cc.newex.dax.extra.criteria.currency.TagCategoryExample;
import cc.newex.dax.extra.domain.currency.TagCategory;
import cc.newex.dax.extra.dto.currency.TagCategoryDTO;
import cc.newex.dax.extra.service.admin.currency.TagCategoryAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 标签分类表 控制器类
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/currency/tag-category")
public class TagCategoryAdminController {

    @Autowired
    private TagCategoryAdminService tagCategoryAdminService;

    /**
     * 保存TagCategory
     *
     * @param tagCategoryDTO
     * @return
     */
    @PostMapping(value = "/saveTagCategory")
    public ResponseResult saveTagCategory(@RequestBody @Valid final TagCategoryDTO tagCategoryDTO) {
        final ModelMapper mapper = new ModelMapper();
        final TagCategory tagCategory = mapper.map(tagCategoryDTO, TagCategory.class);


        final List<TagCategory> list = tagCategoryAdminService.getByCode(tagCategoryDTO.getCode(),tagCategory.getLocale());
        if(CollectionUtils.isNotEmpty(list)){
            return ResultUtils.failure(ExtraBizErrorCodeEnum.ADD_ERROR);
        }
        final int save = this.tagCategoryAdminService.add(tagCategory);

        return ResultUtils.success(save);
    }

    /**
     * 更新TagCategory
     *
     * @param tagCategoryDTO
     * @return
     */
    @PostMapping(value = "/updateTagCategory")
    public ResponseResult updateTagCategory(@RequestBody @Valid final TagCategoryDTO tagCategoryDTO) {
        final ModelMapper mapper = new ModelMapper();
        final TagCategory tagCategory = mapper.map(tagCategoryDTO, TagCategory.class);

        final List<TagCategory> list = tagCategoryAdminService.getByCode(tagCategoryDTO.getCode(),tagCategory.getLocale());
        if(CollectionUtils.isNotEmpty(list) && list.get(0).getId().intValue() != tagCategory.getId().intValue()){
            return ResultUtils.failure(ExtraBizErrorCodeEnum.UPDATE_ERROR);
        }

        final int update = this.tagCategoryAdminService.editById(tagCategory);

        return ResultUtils.success(update);
    }

    /**
     * 删除TagCategory
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeTagCategory")
    public ResponseResult removeTagCategory(@RequestParam("id") final Long id,@RequestParam("code")final String code) {
        final int remove = this.tagCategoryAdminService.removeTagCategoryById(id,code);

        return ResultUtils.success(remove);
    }

    /**
     * List TagCategory
     *
     * @return
     */
    @PostMapping(value = "/listTagCategory")
    public ResponseResult listTagCategory(@RequestBody final DataGridPager<TagCategoryDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final TagCategory tagCategory = mapper.map(pager.getQueryParameter(), TagCategory.class);
        final TagCategoryExample example = TagCategoryExample.toExample(tagCategory);

        final List<TagCategory> list = this.tagCategoryAdminService.getByPage(pageInfo, example);
        final List<TagCategoryDTO> tagCategoryDTOS = mapper.map(
                list, new TypeToken<List<TagCategoryDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), tagCategoryDTOS));
    }

    /**
     * List All TagCategory
     *
     * @return
     */
    @GetMapping(value = "/listAllTagCategory")
    public ResponseResult<List<TagCategoryDTO>> listAllTagCategory() {
        final List<TagCategory> tagCategoryList = this.tagCategoryAdminService.getAll();

        final ModelMapper mapper = new ModelMapper();

        final List<TagCategoryDTO> tagCategoryDTOS = mapper.map(
                tagCategoryList, new TypeToken<List<TagCategoryDTO>>() {
                }.getType()
        );

        return ResultUtils.success(tagCategoryDTOS);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getTagCategoryById")
    public ResponseResult getTagCategoryById(@RequestParam("id") final Long id) {
        final TagCategory tagCategory = this.tagCategoryAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final TagCategoryDTO tagCategoryDTO = mapper.map(tagCategory, TagCategoryDTO.class);

        return ResultUtils.success(tagCategoryDTO);
    }

}
