package cc.newex.dax.extra.rest.controller.admin.v1.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import cc.newex.dax.extra.criteria.currency.TagInfoExample;
import cc.newex.dax.extra.domain.currency.TagInfo;
import cc.newex.dax.extra.dto.currency.TagInfoDTO;
import cc.newex.dax.extra.service.admin.currency.TagInfoAdminService;
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
 * 标签表 控制器类
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/currency/tag-info")
public class TagInfoAdminController {

    @Autowired
    private TagInfoAdminService tagInfoAdminService;

    /**
     * 保存TagInfo
     *
     * @param tagInfoDTO
     * @return
     */
    @PostMapping(value = "/saveTagInfo")
    public ResponseResult saveTagInfo(@RequestBody @Valid final TagInfoDTO tagInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final TagInfo tagInfo = mapper.map(tagInfoDTO, TagInfo.class);

        final List<TagInfo> list = tagInfoAdminService.getByTagCode(tagInfoDTO.getCode(),tagInfoDTO.getLocale());
        if(CollectionUtils.isNotEmpty(list)){
            return ResultUtils.failure(ExtraBizErrorCodeEnum.ADD_ERROR);
        }

        final int save = this.tagInfoAdminService.add(tagInfo);

        return ResultUtils.success(save);
    }

    /**
     * 更新TagInfo
     *
     * @param tagInfoDTO
     * @return
     */
    @PostMapping(value = "/updateTagInfo")
    public ResponseResult updateTagInfo(@RequestBody @Valid final TagInfoDTO tagInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final TagInfo tagInfo = mapper.map(tagInfoDTO, TagInfo.class);

        final List<TagInfo> list = tagInfoAdminService.getByTagCode(tagInfoDTO.getCode(),tagInfoDTO.getLocale());
        if(CollectionUtils.isNotEmpty(list) && list.get(0).getId().intValue() != tagInfo.getId().intValue()){
            return ResultUtils.failure(ExtraBizErrorCodeEnum.UPDATE_ERROR);
        }

        final int update = this.tagInfoAdminService.editById(tagInfo);

        return ResultUtils.success(update);
    }


    /**
     * 删除TagInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeTagInfo")
    public ResponseResult removeTagInfo(@RequestParam("id") final Long id,@RequestParam("code")final String code) {
        final int remove = this.tagInfoAdminService.removeTagInfoById(id,code);

        return ResultUtils.success(remove);
    }

    /**
     * List TagInfo
     *
     * @return
     */
    @PostMapping(value = "/listTagInfo")
    public ResponseResult listTagInfo(@RequestBody final DataGridPager<TagInfoDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final TagInfo tagInfo = mapper.map(pager.getQueryParameter(), TagInfo.class);
        final TagInfoExample example = TagInfoExample.toExample(tagInfo);

        final List<TagInfo> list = this.tagInfoAdminService.getByPage(pageInfo, example);
        final List<TagInfoDTO> tagInfoDTOS = mapper.map(
                list, new TypeToken<List<TagInfoDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), tagInfoDTOS));
    }

    /**
     * List All TagInfo
     *
     * @return
     */
    @GetMapping(value = "/listAllTagInfo")
    public ResponseResult<List<TagInfoDTO>> listAllTagInfo() {
        final List<TagInfo> tagInfoList = this.tagInfoAdminService.getAll();

        final ModelMapper mapper = new ModelMapper();

        final List<TagInfoDTO> tagInfoDTOS = mapper.map(
                tagInfoList, new TypeToken<List<TagInfoDTO>>() {
                }.getType()
        );

        return ResultUtils.success(tagInfoDTOS);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getTagInfoById")
    public ResponseResult getTagInfoById(@RequestParam("id") final Long id) {
        final TagInfo tagInfo = this.tagInfoAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final TagInfoDTO tagInfoDTO = mapper.map(tagInfo, TagInfoDTO.class);

        return ResultUtils.success(tagInfoDTO);
    }

}
