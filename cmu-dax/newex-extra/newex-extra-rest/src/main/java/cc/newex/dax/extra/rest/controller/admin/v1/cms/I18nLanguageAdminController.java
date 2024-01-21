package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.cms.I18nLanguageExample;
import cc.newex.dax.extra.domain.cms.I18nLanguage;
import cc.newex.dax.extra.dto.cms.I18nLanguageDTO;
import cc.newex.dax.extra.service.admin.cms.I18nLanguageAdminService;
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
import java.util.List;

/**
 * 本地化语言表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/i18n/languages")
public class I18nLanguageAdminController {

    @Autowired
    private I18nLanguageAdminService i18nLanguageAdminService;

    @PostMapping(value = "/saveI18nLanguage")
    public ResponseResult saveI18nLanguage(@RequestBody final I18nLanguageDTO i18nLanguageDTO, final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final I18nLanguage i18nLanguage = mapper.map(i18nLanguageDTO, I18nLanguage.class);

        final int save = this.i18nLanguageAdminService.add(i18nLanguage);

        return ResultUtils.success(save);
    }

    @PostMapping(value = "/updateI18nLanguage")
    public ResponseResult updateI18nLanguage(@RequestBody final I18nLanguageDTO i18nLanguageDTO, final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final I18nLanguage i18nLanguage = mapper.map(i18nLanguageDTO, I18nLanguage.class);

        final int update = this.i18nLanguageAdminService.editById(i18nLanguage);

        return ResultUtils.success(update);
    }

    @PostMapping(value = "/listI18nLanguage")
    public ResponseResult listI18nLanguage(@RequestBody final DataGridPager<I18nLanguageDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final I18nLanguage i18nLanguage = mapper.map(pager.getQueryParameter(), I18nLanguage.class);
        final I18nLanguageExample example = I18nLanguageExample.toExample(i18nLanguage);
        final List<I18nLanguage> list = this.i18nLanguageAdminService.getByPage(pageInfo, example);
        final List<I18nLanguageDTO> i18nLanguageDTOS = mapper.map(
                list, new TypeToken<List<I18nLanguageDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), i18nLanguageDTOS));
    }

    @PostMapping(value = "/listAllI18nLanguage")
    public ResponseResult listAllI18nLanguage() {
        final List<I18nLanguage> i18nLanguageList = this.i18nLanguageAdminService.getAll();
        final ModelMapper mapper = new ModelMapper();
        final List<I18nLanguageDTO> i18nLanguageDTOS = mapper.map(
                i18nLanguageList, new TypeToken<List<I18nLanguageDTO>>() {
                }.getType()
        );
        return ResultUtils.success(i18nLanguageDTOS);
    }

    @GetMapping(value = "/getI18nLanguageById")
    public ResponseResult getI18nLanguageById(@RequestParam("id") final Integer id, final HttpServletRequest request) {
        final I18nLanguage i18nLanguage = this.i18nLanguageAdminService.getById(id);
        final ModelMapper mapper = new ModelMapper();
        final I18nLanguageDTO i18nLanguageDTO = mapper.map(i18nLanguage, I18nLanguageDTO.class);
        return ResultUtils.success(i18nLanguageDTO);
    }

}