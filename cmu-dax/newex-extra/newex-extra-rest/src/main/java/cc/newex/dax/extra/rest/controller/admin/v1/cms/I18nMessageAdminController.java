package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.cms.I18nMessageExample;
import cc.newex.dax.extra.domain.cms.I18nMessage;
import cc.newex.dax.extra.dto.cms.I18nMessageDTO;
import cc.newex.dax.extra.service.admin.cms.I18nMessageAdminService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 本地化文本表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/i18n/messages")
public class I18nMessageAdminController {

    @Autowired
    private I18nMessageAdminService i18nMessageAdminService;

    @PostMapping(value = "/saveI18nMessage")
    public ResponseResult saveI18nMessage(@RequestBody I18nMessageDTO i18nMessageDTO, HttpServletRequest request) {
        ModelMapper mapper = new ModelMapper();
        I18nMessage i18nMessage = mapper.map(i18nMessageDTO, I18nMessage.class);

        int save = this.i18nMessageAdminService.add(i18nMessage);

        return ResultUtils.success(save);
    }

    @PostMapping(value = "/updateI18nMessage")
    public ResponseResult updateI18nMessage(@RequestBody I18nMessageDTO i18nMessageDTO, HttpServletRequest request) {
        ModelMapper mapper = new ModelMapper();
        I18nMessage i18nMessage = mapper.map(i18nMessageDTO, I18nMessage.class);
        int update = this.i18nMessageAdminService.editById(i18nMessage);
        return ResultUtils.success(update);
    }

    @PostMapping(value = "/listI18nMessage")
    public ResponseResult listI18nMessage(@RequestBody DataGridPager<I18nMessageDTO> pager) {
        PageInfo pageInfo = pager.toPageInfo();

        ModelMapper mapper = new ModelMapper();
        I18nMessage i18nMessage = mapper.map(pager.getQueryParameter(), I18nMessage.class);
        I18nMessageExample example = I18nMessageExample.toExample(i18nMessage);

        List<I18nMessage> list = this.i18nMessageAdminService.getByPage(pageInfo, example);
        List<I18nMessageDTO> i18nMessageDTOS = mapper.map(
                list, new TypeToken<List<I18nMessageDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), i18nMessageDTOS));
    }

    @PostMapping(value = "/listAllI18nMessage")
    public ResponseResult listAllI18nMessage() {
        List<I18nMessage> i18nMessageList = this.i18nMessageAdminService.getAll();

        ModelMapper mapper = new ModelMapper();

        List<I18nMessageDTO> i18nMessageDTOS = mapper.map(
                i18nMessageList, new TypeToken<List<I18nMessageDTO>>() {
                }.getType()
        );

        return ResultUtils.success(i18nMessageDTOS);
    }

    @GetMapping(value = "/getI18nMessageById")
    public ResponseResult getI18nMessageById(@RequestParam("id") Integer id, HttpServletRequest request) {
        I18nMessage i18nMessage = this.i18nMessageAdminService.getById(id);

        ModelMapper mapper = new ModelMapper();
        I18nMessageDTO i18nMessageDTO = mapper.map(i18nMessage, I18nMessageDTO.class);

        return ResultUtils.success(i18nMessageDTO);
    }

    @GetMapping(value = "/getMessageByCode")
    public ResponseResult getI18nMessageByCode(@RequestParam("code") String code) {
        I18nMessageExample example = new I18nMessageExample();
        I18nMessageExample.Criteria criteria = example.createCriteria();
        criteria.andMsgCodeEqualTo(code);
        List<I18nMessage> i18nMessages = this.i18nMessageAdminService.getByExample(example);
        ModelMapper mapper = new ModelMapper();
        List<I18nMessageDTO> result = mapper.map(i18nMessages, new TypeToken<List<I18nMessageDTO>>() {
        }.getType());
        return ResultUtils.success(result);
    }

    @GetMapping(value = "/download")
    public ResponseResult download() {
        List<I18nMessage> source = this.i18nMessageAdminService.getAll();
        Map<String, List<I18nMessage>> map = source.stream().collect(Collectors.groupingBy(I18nMessage::getLocale));
        return ResultUtils.success(map);
    }

}