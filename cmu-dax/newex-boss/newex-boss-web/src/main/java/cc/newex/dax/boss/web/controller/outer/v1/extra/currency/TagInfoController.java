package cc.newex.dax.boss.web.controller.outer.v1.extra.currency;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.currency.TagInfoExtraVO;
import cc.newex.dax.extra.client.ExtraCmsCurrencyAdminClient;
import cc.newex.dax.extra.dto.currency.TagInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * 标签
 *
 * @author liutiejun
 * @date 2018-07-20
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/currency/tag-info")
public class TagInfoController {

    @Autowired
    private ExtraCmsCurrencyAdminClient extraCmsCurrencyAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增TagInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TAG_INFO_ADD"})
    public ResponseResult add(@Valid final TagInfoExtraVO tagInfoExtraVO, final HttpServletRequest request) {

        final TagInfoDTO tagInfoDTO = TagInfoDTO.builder()
                .name(tagInfoExtraVO.getName())
                .code(tagInfoExtraVO.getCode())
                .locale(tagInfoExtraVO.getLocale())
                .tagCategoryCode(tagInfoExtraVO.getTagCategoryCode())
                .sort(tagInfoExtraVO.getSort())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        log.info(tagInfoDTO.toString());

        final ResponseResult result = this.extraCmsCurrencyAdminClient.saveTagInfo(tagInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改TagInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TAG_INFO_EDIT"})
    public ResponseResult edit(@Valid final TagInfoExtraVO tagInfoExtraVO, @RequestParam("id") final Long id,
                               final HttpServletRequest request) {

        final TagInfoDTO tagInfoDTO = TagInfoDTO.builder()
                .id(id)
                .name(tagInfoExtraVO.getName())
                .tagCategoryCode(tagInfoExtraVO.getTagCategoryCode())
                .locale(tagInfoExtraVO.getLocale())
                .code(tagInfoExtraVO.getCode())
                .sort(tagInfoExtraVO.getSort())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.updateTagInfo(tagInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取TagInfo列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TAG_INFO_VIEW"})
    public ResponseResult list(@RequestParam(value = "name", required = false) final String name,
                               @RequestParam(value = "tagCategoryCode", required = false) final String tagCategoryCode,
                               @RequestParam(value = "locale", required = false) final String locale,
                               final DataGridPager<TagInfoDTO> pager) {

        final TagInfoDTO.TagInfoDTOBuilder builder = TagInfoDTO.builder();

        if (StringUtils.isNotBlank(name)) {
            builder.name(name);
        }
        if (StringUtils.isNotBlank(tagCategoryCode)) {
            builder.tagCategoryCode(tagCategoryCode);
        }
        if (StringUtils.isNotBlank(locale)) {
            builder.locale(locale);
        }

        final TagInfoDTO tagInfoDTO = builder.build();
        pager.setQueryParameter(tagInfoDTO);

        final ResponseResult responseResult = this.extraCmsCurrencyAdminClient.listTagInfo(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }


    @PostMapping("/remove")
    @OpLog(name = "删除TagInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TAG_INFO_REMOVE"})
    public ResponseResult deleteTagById(@RequestParam("id") final Long id,@RequestParam("code") final String code){
        ResponseResult responseResult = this.extraCmsCurrencyAdminClient.removeTagInfo(id, code);
        return ResultUtil.getCheckedResponseResult(responseResult);
    }

}
