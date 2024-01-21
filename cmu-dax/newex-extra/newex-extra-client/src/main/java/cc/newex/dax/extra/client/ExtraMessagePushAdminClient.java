package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.cms.MessagePushDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huxingkong
 * @date 2018/10/22 10:50 AM
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/cms/push")
public interface ExtraMessagePushAdminClient {

    /**
     * 新增保存推送
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/save")
    ResponseResult saveMessagePush(@RequestBody final MessagePushDTO params);


    /**
     * 更新推送
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/update")
    ResponseResult updateMessagePush(@RequestBody final MessagePushDTO params);

    /**
     * 推送列表
     *
     * @return
     */
    @PostMapping(value = "/list")
    ResponseResult listMessagePush(@RequestBody final DataGridPager<MessagePushDTO> pager);


    /**
     * 删除推送
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    ResponseResult deleteMessagePush(@RequestParam("id") final Long id);
}
