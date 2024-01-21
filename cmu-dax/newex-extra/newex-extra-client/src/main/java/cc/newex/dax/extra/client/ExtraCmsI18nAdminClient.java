package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.cms.I18nLanguageDTO;
import cc.newex.dax.extra.dto.cms.I18nMessageCodeDTO;
import cc.newex.dax.extra.dto.cms.I18nMessageDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liutiejun
 * @date 2018-06-07
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/cms/i18n")
public interface ExtraCmsI18nAdminClient {

    /**
     * 保存I18nLanguage
     *
     * @param i18nLanguageDTO
     * @return
     */
    @PostMapping(value = "/languages/saveI18nLanguage")
    ResponseResult saveI18nLanguage(@RequestBody I18nLanguageDTO i18nLanguageDTO);

    /**
     * 更新I18nLanguage
     *
     * @param i18nLanguageDTO
     * @return
     */
    @PostMapping(value = "/languages/updateI18nLanguage")
    ResponseResult updateI18nLanguage(@RequestBody I18nLanguageDTO i18nLanguageDTO);

    /**
     * List I18nLanguage
     *
     * @return
     */
    @PostMapping(value = "/languages/listI18nLanguage")
    ResponseResult listI18nLanguage(@RequestBody DataGridPager<I18nLanguageDTO> pager);

    /**
     * List All I18nLanguage
     *
     * @return
     */
    @PostMapping(value = "/languages/listAllI18nLanguage")
    ResponseResult listAllI18nLanguage();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/languages/getI18nLanguageById")
    ResponseResult getI18nLanguageById(@RequestParam("id") Integer id);

    /**
     * 保存I18nMessage
     *
     * @param i18nMessageDTO
     * @return
     */
    @PostMapping(value = "/messages/saveI18nMessage")
    ResponseResult saveI18nMessage(@RequestBody I18nMessageDTO i18nMessageDTO);

    /**
     * 更新I18nMessage
     *
     * @param i18nMessageDTO
     * @return
     */
    @PostMapping(value = "/messages/updateI18nMessage")
    ResponseResult updateI18nMessage(@RequestBody I18nMessageDTO i18nMessageDTO);

    /**
     * List I18nMessage
     *
     * @return
     */
    @PostMapping(value = "/messages/listI18nMessage")
    ResponseResult listI18nMessage(@RequestBody DataGridPager<I18nMessageDTO> pager);

    /**
     * List All I18nMessage
     *
     * @return
     */
    @PostMapping(value = "/messages/listAllI18nMessage")
    ResponseResult listAllI18nMessage();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/messages/getI18nMessageById")
    ResponseResult getI18nMessageById(@RequestParam("id") Integer id);

    /**
     * 保存I18nMessageCode
     *
     * @param i18nMessageCodeDTO
     * @return
     */
    @PostMapping(value = "/message-codes/saveI18nMessageCode")
    ResponseResult saveI18nMessageCode(@RequestBody I18nMessageCodeDTO i18nMessageCodeDTO);

    /**
     * 更新I18nMessageCode
     *
     * @param i18nMessageCodeDTO
     * @return
     */
    @PostMapping(value = "/message-codes/updateI18nMessageCode")
    ResponseResult updateI18nMessageCode(@RequestBody I18nMessageCodeDTO i18nMessageCodeDTO);

    /**
     * List I18nMessageCode
     *
     * @return
     */
    @PostMapping(value = "/message-codes/listI18nMessageCode")
    ResponseResult listI18nMessageCode(@RequestBody DataGridPager<I18nMessageCodeDTO> pager);

    /**
     * List All I18nMessageCode
     *
     * @return
     */
    @PostMapping(value = "/message-codes/listAllI18nMessageCode")
    ResponseResult listAllI18nMessageCode();

    /**
     * 以树形结构分组显示I18nMessageCode
     *
     * @return
     */
    @PostMapping(value = "/message-codes/listAllI18nMessageCodeByTree")
    ResponseResult listAllI18nMessageCodeByTree();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/message-codes/getI18nMessageCodeById")
    ResponseResult getI18nMessageCodeById(@RequestParam("id") Integer id);

    /**
     * 根据code获取数据
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/messages/getMessageByCode")
    ResponseResult getI18nMessageByCode(@RequestParam("code") String code);

    /**
     * 根据节点获取message code
     *
     * @param parentId
     * @return
     */
    @GetMapping(value = "/message-codes/getMessageCodeByParentId")
    ResponseResult getMessageCodeByParentId(@RequestParam(value = "parentId") Integer parentId);

    /**
     * 下载语言js文本
     * @return
     */
    @GetMapping(value = "/messages/download")
    ResponseResult download();
}