package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.cms.I18nLanguageExtraVO;
import cc.newex.dax.extra.client.ExtraCmsI18nAdminClient;
import cc.newex.dax.extra.dto.cms.I18nLanguageDTO;
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
 * @date 2018-06-07
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/cms/i18n/languages")
public class I18nLanguageController {

    @Autowired
    private ExtraCmsI18nAdminClient extraCmsI18nAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增I18nLanguage")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_LANGUAGE_ADD"})
    public ResponseResult add(@Valid final I18nLanguageExtraVO i18nLanguageExtraVO, final HttpServletRequest request) {
        try {
            final I18nLanguageDTO i18nLanguageDTO = I18nLanguageDTO.builder()
                    .code(i18nLanguageExtraVO.getCode())
                    .name(i18nLanguageExtraVO.getName())
                    .createdDate(new Date())
                    .updatedDate(new Date())
                    .build();

            final ResponseResult result = this.extraCmsI18nAdminClient.saveI18nLanguage(i18nLanguageDTO);

            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("add i18nLanguage api error: " + e.getMessage(), e);
        }

        return ResultUtils.success();

    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改I18nLanguage")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_LANGUAGE_EDIT"})
    public ResponseResult edit(@Valid final I18nLanguageExtraVO i18nLanguageExtraVO,
                               @RequestParam(value = "id", required = false) final Integer id,
                               final HttpServletRequest request) {
        try {
            final I18nLanguageDTO i18nLanguageDTO = I18nLanguageDTO.builder()
                    .id(id)
                    .code(i18nLanguageExtraVO.getCode())
                    .name(i18nLanguageExtraVO.getName())
                    .createdDate(new Date())
                    .updatedDate(new Date())
                    .build();

            final ResponseResult result = this.extraCmsI18nAdminClient.updateI18nLanguage(i18nLanguageDTO);

            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("edit i18nLanguage api error: " + e.getMessage(), e);
        }

        return ResultUtils.success();
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取I18nLanguage列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_LANGUAGE_VIEW"})
    public ResponseResult list(@RequestParam(value = "name", required = false) final String name, final DataGridPager<I18nLanguageDTO> pager) {
        try {
            final I18nLanguageDTO.I18nLanguageDTOBuilder builder = I18nLanguageDTO.builder();

            if (StringUtils.isNotBlank(name)) {
                builder.name(name);
            }

            final I18nLanguageDTO i18nLanguageDTO = builder.build();
            pager.setQueryParameter(i18nLanguageDTO);
            final ResponseResult responseResult = this.extraCmsI18nAdminClient.listI18nLanguage(pager);

            return ResultUtil.getCheckedResponseResult(responseResult);
        } catch (final Exception e) {
            log.error("get i18nLanguage list api error: " + e.getMessage(), e);
        }

        return ResultUtils.success();
    }

    @GetMapping(value = "/list/all")
    @OpLog(name = "获取I18nLanguage列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_LANGUAGE_VIEW"})
    public Object listAll() {
        try {
            final ResponseResult responseResult = this.extraCmsI18nAdminClient.listAllI18nLanguage();

            if (responseResult == null) {
                return new ArrayList<I18nLanguageExtraVO>();
            }

            final Object data = responseResult.getData();
            if (data == null) {
                return new ArrayList<I18nLanguageExtraVO>();
            }

            return data;
        } catch (final Exception e) {
            log.error("get i18nLanguage list api error: " + e.getMessage(), e);
        }

        return new ArrayList<I18nLanguageExtraVO>();
    }

}
