package cc.newex.dax.boss.web.controller.outer.v1.activity.redpacket;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.activity.client.redenvelope.RedEnvelopeAdminClient;
import cc.newex.dax.activity.dto.ccex.redenvelope.*;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.web.model.activity.redpacket.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cc.newex.dax.activity.enums.redenvelope.RedEnvelopeThemeStatusEnum.DEFAULT;
import static cc.newex.dax.activity.enums.redenvelope.RedEnvelopeThemeStatusEnum.PUBLISH;

/**
 * 红包相关的控制器
 *
 * @author better
 * @date create in 2019-01-08 14:29
 */
@RestController
@RequestMapping(value = "/v1/boss/activity/red_packet")
public class RedPacketController {

    private static final String DATE_PARSE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final RedEnvelopeAdminClient client;
    private final ModelMapper modelMapper;

    /**
     * Instantiates a new Red packet controller.
     *
     * @param client      the client
     * @param modelMapper the model mapper
     */
    @Autowired
    public RedPacketController(final RedEnvelopeAdminClient client, final ModelMapper modelMapper) {
        this.client = client;
        this.modelMapper = modelMapper;
    }

    // --------------> search

    /**
     * Search red packet config response result.
     *
     * @param pager     the pager
     * @param userId    the user id
     * @param type      the type
     * @param validTime the valid time
     * @param status    the status
     * @return the response result
     */
    @GetMapping(value = "/config/list")
    @OpLog(name = "查看红包配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_CONFIG_VIEW"})
    public ResponseResult searchRedPacketConfig(final DataGridPager<RedEnvelopeConfigDTO> pager, final Long userId, final Integer type, final Integer validTime,
                                                final Integer status) {

        final RedEnvelopeConfigDTO queryParam = new RedEnvelopeConfigDTO();
        queryParam.setUserId(userId);
        queryParam.setRedType(type);
        queryParam.setValidityPeriod(validTime);
        queryParam.setStatus(status);
        pager.setQueryParameter(queryParam);
        return this.client.listRedEnvelopeConfig(pager);
    }

    /**
     * Search red packet amount limit response result.
     *
     * @param pager      the pager
     * @param currencyId the currency id
     * @return the response result
     */
    @GetMapping(value = "/amount_limit/list")
    @OpLog(name = "查看限额配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_AMOUNT_LIMIT_VIEW"})
    public ResponseResult searchRedPacketAmountLimit(final DataGridPager<RedEnvelopeAmountLimitDTO> pager, final Integer currencyId) {
        final RedEnvelopeAmountLimitDTO queryParam = new RedEnvelopeAmountLimitDTO();
        queryParam.setCurrencyId(currencyId);
        pager.setQueryParameter(queryParam);
        return this.client.listRedEnvelopeAmountLimit(pager);
    }

    /**
     * Search red packet send record response result.
     *
     * @param pager  the pager
     * @param userId the user id
     * @param type   the type
     * @return the response result
     */
    @GetMapping(value = "/send_record/list")
    @OpLog(name = "查看红包发送纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_SEND_RECORD_VIEW"})
    public ResponseResult searchRedPacketSendRecord(final DataGridPager<RedEnvelopeSendRecordDTO> pager, final Long userId, final Integer type) {
        final RedEnvelopeSendRecordDTO queryParam = new RedEnvelopeSendRecordDTO();
        queryParam.setSendUserId(userId);
        queryParam.setRedType(type);
        pager.setQueryParameter(queryParam);
        return this.client.listRedEnvelopeSendRecord(pager);
    }

    /**
     * Search red packet receive record response result.
     *
     * @param pager the pager
     * @param type  the type
     * @return the response result
     */
    @GetMapping(value = "/receive_record/list")
    @OpLog(name = "查看红包领取纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_RECEIVE_RECORD_VIEW"})
    public ResponseResult searchRedPacketReceiveRecord(final DataGridPager<RedEnvelopeReceiveRecordDTO> pager, final Integer type, final String redUid) {
        final RedEnvelopeReceiveRecordDTO queryParam = new RedEnvelopeReceiveRecordDTO();
        queryParam.setRedType(type);
        queryParam.setRedUid(redUid);
        pager.setQueryParameter(queryParam);
        return this.client.listRedEnvelopeReceiveRecord(pager);
    }

    /**
     * Search red packet theme response result.
     *
     * @param pager     the pager
     * @param themeName the theme name
     * @param status    the status
     * @return the response result
     */
    @GetMapping(value = "/theme/list")
    @OpLog(name = "查看红包主题配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_THEME_VIEW"})
    public ResponseResult searchRedPacketTheme(final DataGridPager<RedEnvelopeThemeDTO> pager, final String themeName, final Integer status) {
        final RedEnvelopeThemeDTO queryParam = new RedEnvelopeThemeDTO();
        queryParam.setThemeName(themeName);
        queryParam.setStatus(status);
        pager.setQueryParameter(queryParam);
        return this.client.listRedEnvelopeTheme(pager);
    }

    // --------------> add

    /**
     * Save red packet config response result.
     *
     * @param redPacketVO the red packet vo
     * @return the response result
     */
    @PostMapping(value = "/config/save")
    @OpLog(name = "新增红包配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_CONFIG_ADD"})
    public ResponseResult saveRedPacketConfig(final RedPacketVO redPacketVO) {
        final RedEnvelopeConfigDTO redPacket = this.modelMapper.map(redPacketVO, RedEnvelopeConfigDTO.class);
        return ResultUtils.success(this.client.saveRedEnvelopeConfig(redPacket));
    }

    /**
     * Save red packet amount limit response result.
     *
     * @param redPacketAmountLimitVO the red packet amount limit vo
     * @return the response result
     */
    @PostMapping(value = "/amount_limit/save")
    @OpLog(name = "新增限额配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_AMOUNT_LIMIT_ADD"})
    public ResponseResult saveRedPacketAmountLimit(final RedPacketAmountLimitVO redPacketAmountLimitVO) {
        final RedEnvelopeAmountLimitDTO redPacketAmountLimit = this.modelMapper.map(redPacketAmountLimitVO, RedEnvelopeAmountLimitDTO.class);
        return ResultUtils.success(this.client.saveRedEnvelopeAmountLimit(redPacketAmountLimit));
    }

    /**
     * Save red packet send record response result.
     *
     * @param redPacketSendRecordVO the red packet send record vo
     * @return the response result
     */
    @PostMapping(value = "/send_record/save")
    @OpLog(name = "新增红包发送纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_SEND_RECORD_ADD"})
    public ResponseResult saveRedPacketSendRecord(final RedPacketSendRecordVO redPacketSendRecordVO) {
        final RedEnvelopeSendRecordDTO redPacketSendRecord = this.modelMapper.map(redPacketSendRecordVO, RedEnvelopeSendRecordDTO.class);
        return ResultUtils.success(this.client.saveRedEnvelopeSendRecord(redPacketSendRecord));
    }

    /**
     * Save red packet receive record response result.
     *
     * @param redPacketReceiveRecordVO the red packet receive record vo
     * @return the response result
     */
    @PostMapping(value = "/receive_record/save")
    @OpLog(name = "新增红包领取纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_RECEIVE_RECORD_ADD"})
    public ResponseResult saveRedPacketReceiveRecord(final RedPacketReceiveRecordVO redPacketReceiveRecordVO) {
        final RedEnvelopeReceiveRecordDTO redPacketReceiveRecord = this.modelMapper.map(redPacketReceiveRecordVO, RedEnvelopeReceiveRecordDTO.class);
        return ResultUtils.success(this.client.saveRedEnvelopeReceiveRecord(redPacketReceiveRecord));
    }

    /**
     * Save red packet theme response result.
     *
     * @param redPacketThemeVO the red packet theme vo
     * @return the response result
     */
    @PostMapping(value = "/theme/save")
    @OpLog(name = "新增红包主题配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_THEME_ADD"})
    public ResponseResult saveRedPacketTheme(final RedPacketThemeVO redPacketThemeVO) {
        final RedEnvelopeThemeDTO redPacketTheme = this.modelMapper.map(redPacketThemeVO, RedEnvelopeThemeDTO.class);
        return ResultUtils.success(this.client.saveRedEnvelopeTheme(redPacketTheme));
    }

    // --------------> edit

    /**
     * Edit red packet config response result.
     *
     * @param redPacketVO the red packet vo
     * @return the response result
     */
    @PostMapping(value = "/config/edit")
    @OpLog(name = "修改红包配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_CONFIG_EDIT"})
    public ResponseResult editRedPacketConfig(final RedPacketVO redPacketVO) {
        final RedEnvelopeConfigDTO redPacket = this.modelMapper.map(redPacketVO, RedEnvelopeConfigDTO.class);
        return ResultUtils.success(this.client.updateRedEnvelopeConfig(redPacket));
    }

    /**
     * Edit red packet amount limit response result.
     *
     * @param redPacketAmountLimitVO the red packet amount limit vo
     * @return the response result
     */
    @PostMapping(value = "/amount_limit/edit")
    @OpLog(name = "修改限额配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_AMOUNT_LIMIT_EDIT"})
    public ResponseResult editRedPacketAmountLimit(final RedPacketAmountLimitVO redPacketAmountLimitVO) {
        final RedEnvelopeAmountLimitDTO redPacketAmountLimit = this.modelMapper.map(redPacketAmountLimitVO, RedEnvelopeAmountLimitDTO.class);
        return ResultUtils.success(this.client.updateRedEnvelopeAmountLimit(redPacketAmountLimit));
    }

    /**
     * Edit red packet send record response result.
     *
     * @param redPacketSendRecordVO the red packet send record vo
     * @return the response result
     */
    @PostMapping(value = "/send_record/edit")
    @OpLog(name = "修改红包发送纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_SEND_RECORD_EDIT"})
    public ResponseResult editRedPacketSendRecord(final RedPacketSendRecordVO redPacketSendRecordVO) {
        final RedEnvelopeSendRecordDTO redPacketSendRecord = this.modelMapper.map(redPacketSendRecordVO, RedEnvelopeSendRecordDTO.class);
        return ResultUtils.success(this.client.updateRedEnvelopeSendRecord(redPacketSendRecord));
    }

    /**
     * Edit red packet receive record response result.
     *
     * @param redPacketReceiveRecordVO the red packet receive record vo
     * @return the response result
     */
    @PostMapping(value = "/receive_record/edit")
    @OpLog(name = "修改红包领取纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_RECEIVE_RECORD_EDIT"})
    public ResponseResult editRedPacketReceiveRecord(final RedPacketReceiveRecordVO redPacketReceiveRecordVO) {
        final RedEnvelopeReceiveRecordDTO redPacketReceiveRecord = this.modelMapper.map(redPacketReceiveRecordVO, RedEnvelopeReceiveRecordDTO.class);
        return ResultUtils.success(this.client.updateRedEnvelopeReceiveRecord(redPacketReceiveRecord));
    }

    /**
     * Edit red packet theme response result.
     *
     * @param redPacketThemeVO the red packet theme vo
     * @return the response result
     */
    @PostMapping(value = "/theme/edit")
    @OpLog(name = "修改红包主题配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_THEME_EDIT"})
    public ResponseResult editRedPacketTheme(final RedPacketThemeVO redPacketThemeVO) {
        final RedEnvelopeThemeDTO redPacketTheme = this.modelMapper.map(redPacketThemeVO, RedEnvelopeThemeDTO.class);
        return ResultUtils.success(this.client.updateRedEnvelopeTheme(redPacketTheme));
    }

    // --------------> remove

    /**
     * Delete red packet config response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/config/remove")
    @OpLog(name = "删除红包配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_CONFIG_REMOVE"})
    public ResponseResult deleteRedPacketConfig(final Long id) {
        return ResultUtils.success(this.client.removeRedEnvelopeConfig(id));
    }

    /**
     * Delete red packet amount limit response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/amount_limit/remove")
    @OpLog(name = "删除限额配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_AMOUNT_LIMIT_REMOVE"})
    public ResponseResult deleteRedPacketAmountLimit(final Long id) {
        return ResultUtils.success(this.client.removeRedEnvelopeAmountLimit(id));
    }

    /**
     * Delete red packet send record response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/send_record/remove")
    @OpLog(name = "删除红包发送纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_SEND_RECORD_REMOVE"})
    public ResponseResult deleteRedPacketSendRecord(final Long id) {
        return ResultUtils.success(this.client.removeRedEnvelopeSendRecord(id));
    }

    /**
     * Delete red packet receive record response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/receive_record/remove")
    @OpLog(name = "删除红包领取纪录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_RECEIVE_RECORD_REMOVE"})
    public ResponseResult deleteRedPacketReceiveRecord(final Long id) {
        return ResultUtils.success(this.client.removeRedEnvelopeReceiveRecord(id));
    }

    /**
     * Delete red packet theme response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/theme/remove")
    @OpLog(name = "删除红包主题配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RED_PACKET_THEME_REMOVE"})
    public ResponseResult deleteRedPacketTheme(final Long id) {
        return ResultUtils.success(this.client.removeRedEnvelopeTheme(id));
    }

    // --------------> other

    /**
     * List red packet theme response result.
     *
     * @param loginUser the login user
     * @return the response result
     */
    @GetMapping(value = "/config/theme/list")
    public ResponseResult listRedPacketTheme(@CurrentUser final User loginUser) {

        return this.client.listAllRedEnvelopeTheme(loginUser.getLoginBrokerId(), new Integer[]{DEFAULT.getCode(), PUBLISH.getCode()});
    }

    /**
     * List red packet currency response result.
     *
     * @param loginUser the login user
     * @return the response result
     */
    @GetMapping(value = "/config/amount_list/currency")
    public ResponseResult listRedPacketCurrency(@CurrentUser final User loginUser) {
        return this.client.listRedEnvelopeAmountLimit(loginUser.getLoginBrokerId());
    }

    /**
     * Init binder.
     *
     * @param binder     the binder
     * @param webRequest the web request
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder, final WebRequest webRequest) {
        final DateFormat dateFormat = new SimpleDateFormat(DATE_PARSE_PATTERN);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
