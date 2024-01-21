package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.cms.MessagePush;
import cc.newex.dax.extra.dto.cms.MessagePushDTO;
import cc.newex.dax.extra.service.admin.cms.MessagePushService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Message push admin controller.
 *
 * @author huxingkong
 * @date 2018 /10/19 5:03 PM
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/push")
public class MessagePushAdminController {

    @Autowired
    private MessagePushService messagePushService;

    /**
     * Save response result.
     *
     * @param messagePushDTO the message push dto
     * @param request        the request
     * @return the response result
     */
    @PostMapping(value = "/save")
    public ResponseResult save(@RequestBody final MessagePushDTO messagePushDTO, final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final MessagePush messagePush = mapper.map(messagePushDTO, MessagePush.class);
        final int save = this.messagePushService.add(messagePush);
        return ResultUtils.success(save);
    }

    /**
     * Update response result.
     *
     * @param messagePushDTO the message push dto
     * @param request        the request
     * @return the response result
     */
    @PostMapping(value = "/update")
    public ResponseResult update(@RequestBody final MessagePushDTO messagePushDTO, final HttpServletRequest request) {
        final ModelMapper mapper = new ModelMapper();
        final MessagePush messagePush = mapper.map(messagePushDTO, MessagePush.class);
        final int save = this.messagePushService.editById(messagePush);
        return ResultUtils.success(save);
    }

    /**
     * List response result.
     *
     * @param pager the pager
     * @return the response result
     */
    @PostMapping(value = "/list")
    public ResponseResult list(@RequestBody final DataGridPager<MessagePushDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<MessagePushDTO> result = this.messagePushService.listByConditionAndPage(pageInfo, pager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), result));
    }


    /**
     * Delete response result.
     *
     * @param pushId  the push id
     * @param request the request
     * @return the response result
     */
    @PostMapping(value = "/delete")
    public ResponseResult delete(@RequestParam final Integer pushId, final HttpServletRequest request) {
        final int save = this.messagePushService.removeById(pushId);
        return ResultUtils.success(save);
    }

}

