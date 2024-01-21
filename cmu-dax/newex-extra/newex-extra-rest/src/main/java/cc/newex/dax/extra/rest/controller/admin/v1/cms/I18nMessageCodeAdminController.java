package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.cms.I18nMessageCodeExample;
import cc.newex.dax.extra.domain.cms.I18nMessageCode;
import cc.newex.dax.extra.dto.cms.I18nMessageCodeDTO;
import cc.newex.dax.extra.dto.common.ComboTreeDTO;
import cc.newex.dax.extra.service.admin.cms.I18nMessageCodeAdminService;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地化文本编码表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/i18n/message-codes")
public class I18nMessageCodeAdminController {

    @Autowired
    private I18nMessageCodeAdminService i18nMessageCodeAdminService;

    @PostMapping(value = "/saveI18nMessageCode")
    public ResponseResult saveI18nMessageCode(@RequestBody I18nMessageCodeDTO i18nMessageCodeDTO, HttpServletRequest request) {
        ModelMapper mapper = new ModelMapper();
        I18nMessageCode i18nMessageCode = mapper.map(i18nMessageCodeDTO, I18nMessageCode.class);

        int save = this.i18nMessageCodeAdminService.add(i18nMessageCode);

        return ResultUtils.success(save);
    }

    @PostMapping(value = "/updateI18nMessageCode")
    public ResponseResult updateI18nMessageCode(@RequestBody I18nMessageCodeDTO i18nMessageCodeDTO, HttpServletRequest request) {
        ModelMapper mapper = new ModelMapper();
        I18nMessageCode i18nMessageCode = mapper.map(i18nMessageCodeDTO, I18nMessageCode.class);

        int update = this.i18nMessageCodeAdminService.editById(i18nMessageCode);

        return ResultUtils.success(update);
    }

    @PostMapping(value = "/listI18nMessageCode")
    public ResponseResult listI18nMessageCode(@RequestBody DataGridPager<I18nMessageCodeDTO> pager) {
        PageInfo pageInfo = pager.toPageInfo();

        ModelMapper mapper = new ModelMapper();
        I18nMessageCode i18nMessageCode = mapper.map(pager.getQueryParameter(), I18nMessageCode.class);
        I18nMessageCodeExample example = I18nMessageCodeExample.toExample(i18nMessageCode);

        List<I18nMessageCode> list = this.i18nMessageCodeAdminService.getByPage(pageInfo, example);
        List<I18nMessageCodeDTO> i18nMessageCodeDTOS = mapper.map(
                list, new TypeToken<List<I18nMessageCodeDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), i18nMessageCodeDTOS));
    }

    @PostMapping(value = "/listAllI18nMessageCode")
    public ResponseResult listAllI18nMessageCode() {
        List<I18nMessageCode> i18nMessageCodeList = this.i18nMessageCodeAdminService.getAll();

        ModelMapper mapper = new ModelMapper();

        List<I18nMessageCodeDTO> i18nMessageCodeDTOS = mapper.map(
                i18nMessageCodeList, new TypeToken<List<I18nMessageCodeDTO>>() {
                }.getType()
        );

        return ResultUtils.success(i18nMessageCodeDTOS);
    }

    @PostMapping(value = "/listAllI18nMessageCodeByTree")
    public ResponseResult listAllI18nMessageCodeByTree() {
        List<I18nMessageCode> i18nMessageCodeList = this.i18nMessageCodeAdminService.getAll();

        if (CollectionUtils.isEmpty(i18nMessageCodeList)) {
            return ResultUtils.success(new ArrayList<ComboTreeDTO>());
        }

        Map<Integer, ComboTreeDTO> comboTreeDTOMap = new HashMap<>();
        Map<Integer, List<ComboTreeDTO>> parentMap = new HashMap<>();

        Integer parentId = null;

        Integer id = null;
        String text = null;
        String text2 = null;

        ComboTreeDTO comboTreeDTO = null;
        List<ComboTreeDTO> comboTreeDTOList = null;

        for (I18nMessageCode i18nMessageCode : i18nMessageCodeList) {
            parentId = i18nMessageCode.getParentId();
            if (parentId == null || parentId < 0) {
                parentId = 0;
            }

            id = i18nMessageCode.getId();
            text = i18nMessageCode.getName();
            text2 = i18nMessageCode.getCode();

            comboTreeDTO = ComboTreeDTO.builder()
                    .id(id)
                    .text(text)
                    .text2(text2)
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
            List<ComboTreeDTO> comboTreeDTOS = parentMap.get(k);
            if (CollectionUtils.isNotEmpty(comboTreeDTOS)) {
                v.setChildren(comboTreeDTOS.toArray(new ComboTreeDTO[0]));
            }
        });

        List<ComboTreeDTO> rootList = parentMap.get(0);

        return ResultUtils.success(rootList);
    }

    @GetMapping(value = "/getI18nMessageCodeById")
    public ResponseResult getI18nMessageCodeById(@RequestParam("id") Integer id, HttpServletRequest request) {
        I18nMessageCode i18nMessageCode = this.i18nMessageCodeAdminService.getById(id);

        ModelMapper mapper = new ModelMapper();
        I18nMessageCodeDTO i18nMessageCodeDTO = mapper.map(i18nMessageCode, I18nMessageCodeDTO.class);

        return ResultUtils.success(i18nMessageCodeDTO);
    }

    @GetMapping(value = "/getMessageCodeByParentId")
    public ResponseResult getMessageCodeByParentId(@RequestParam(value = "parentId") Integer parentId) {
        I18nMessageCodeExample example = new I18nMessageCodeExample();
        I18nMessageCodeExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<I18nMessageCode> result = this.i18nMessageCodeAdminService.getByExample(example);
        return ResultUtils.success(result);
    }

}