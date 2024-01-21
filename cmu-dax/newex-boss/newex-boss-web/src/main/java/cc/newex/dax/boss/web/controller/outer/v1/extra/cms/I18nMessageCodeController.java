package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.common.ComboTreeVO;
import cc.newex.dax.boss.web.model.extra.cms.I18nMessageCodeExtraVO;
import cc.newex.dax.extra.client.ExtraCmsI18nAdminClient;
import cc.newex.dax.extra.dto.cms.I18nMessageCodeDTO;
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
@RequestMapping(value = "/v1/boss/extra/cms/i18n/message-codes")
public class I18nMessageCodeController {

    @Autowired
    private ExtraCmsI18nAdminClient extraCmsI18nAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增I18nMessageCode")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_MESSAGE_CODE_ADD"})
    public ResponseResult add(@Valid final I18nMessageCodeExtraVO i18nMessageCodeExtraVO, final HttpServletRequest request) {
        try {
            final I18nMessageCodeDTO i18nMessageCodeDTO = I18nMessageCodeDTO.builder()
                    .parentId(i18nMessageCodeExtraVO.getParentId())
                    .code(i18nMessageCodeExtraVO.getCode())
                    .name(i18nMessageCodeExtraVO.getName())
                    .type(i18nMessageCodeExtraVO.getType())
                    .createdDate(new Date())
                    .updatedDate(new Date())
                    .build();

            final ResponseResult result = this.extraCmsI18nAdminClient.saveI18nMessageCode(i18nMessageCodeDTO);

            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("add i18nMessageCode api error: " + e.getMessage(), e);
        }

        return ResultUtils.success();

    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改I18nMessageCode")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_MESSAGE_CODE_EDIT"})
    public ResponseResult edit(@Valid final I18nMessageCodeExtraVO i18nMessageCodeExtraVO,
                               @RequestParam(value = "id", required = false) final Integer id,
                               final HttpServletRequest request) {
        try {
            final I18nMessageCodeDTO i18nMessageCodeDTO = I18nMessageCodeDTO.builder()
                    .id(id)
                    .parentId(i18nMessageCodeExtraVO.getParentId())
                    .code(i18nMessageCodeExtraVO.getCode())
                    .name(i18nMessageCodeExtraVO.getName())
                    .type(i18nMessageCodeExtraVO.getType())
                    .createdDate(new Date())
                    .updatedDate(new Date())
                    .build();

            final ResponseResult result = this.extraCmsI18nAdminClient.updateI18nMessageCode(i18nMessageCodeDTO);

            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("edit i18nMessageCode api error: " + e.getMessage(), e);
        }

        return ResultUtils.success();
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取I18nMessageCode列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_MESSAGE_CODE_VIEW"})
    public ResponseResult list(@RequestParam(value = "name", required = false) final String name, final DataGridPager<I18nMessageCodeDTO> pager) {
        try {
            final I18nMessageCodeDTO.I18nMessageCodeDTOBuilder builder = I18nMessageCodeDTO.builder();

            if (StringUtils.isNotBlank(name)) {
                builder.name(name);
            }

            final I18nMessageCodeDTO i18nMessageCodeDTO = builder.build();
            pager.setQueryParameter(i18nMessageCodeDTO);
            final ResponseResult responseResult = this.extraCmsI18nAdminClient.listI18nMessageCode(pager);

            return ResultUtil.getCheckedResponseResult(responseResult);
        } catch (final Exception e) {
            log.error("get i18nMessageCode list api error: " + e.getMessage(), e);
        }

        return ResultUtils.success();
    }

    @GetMapping(value = "/list/all")
    @OpLog(name = "获取I18nMessageCode列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_MESSAGE_CODE_VIEW"})
    public Object listAll() {
        try {
            final ResponseResult responseResult = this.extraCmsI18nAdminClient.listAllI18nMessageCode();

            if (responseResult == null) {
                return new ArrayList<I18nMessageCodeExtraVO>();
            }

            final Object data = responseResult.getData();
            if (data == null) {
                return new ArrayList<I18nMessageCodeExtraVO>();
            }

            return data;
        } catch (final Exception e) {
            log.error("get i18nMessageCode list api error: " + e.getMessage(), e);
        }

        return new ArrayList<I18nMessageCodeExtraVO>();
    }

    @GetMapping(value = "/list/all/tree")
    @OpLog(name = "获取I18nMessageCode列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_MESSAGE_CODE_VIEW"})
    public Object listAllByTree() {
        try {
            final ResponseResult responseResult = this.extraCmsI18nAdminClient.listAllI18nMessageCodeByTree();

            if (responseResult == null) {
                return new ArrayList<ComboTreeVO>();
            }

            final Object data = responseResult.getData();
            if (data == null) {
                return new ArrayList<ComboTreeVO>();
            }

            return data;
        } catch (final Exception e) {
            log.error("get i18nMessageCode list api error: " + e.getMessage(), e);
        }

        return new ArrayList<ComboTreeVO>();
    }

}
