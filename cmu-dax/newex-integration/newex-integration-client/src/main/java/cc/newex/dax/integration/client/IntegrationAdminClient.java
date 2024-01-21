package cc.newex.dax.integration.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.dto.admin.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author newex-team
 * @date 2018-05-10
 */
@FeignClient(value = "${newex.feignClient.dax.integration}", path = "/admin/v1/integration")
public interface IntegrationAdminClient {

    //-----------------------Message ---------------------------------------------------------------------------//

    @PostMapping("/messages/pager")
    ResponseResult getMessages(@RequestBody final DataGridPager<MessageDTO> pager);

    @GetMapping(value = "/messages/{id}")
    ResponseResult<MessageDTO> getMessage(@RequestParam("brokerId") Integer brokerId,
                                          @PathVariable("id") Long id);

    @DeleteMapping(value = "/messages")
    ResponseResult<Integer> removeMessage(@RequestBody MessageDTO dto);


    //-----------------------MessageTemplate---------------------------------------------------------------------------//

    @PostMapping(value = "/message-templates")
    ResponseResult<Integer> addMessageTemplate(@RequestBody MessageTemplateDTO messageTemplate);

    @PutMapping(value = "/message-templates")
    ResponseResult<Integer> editMessageTemplate(@RequestBody MessageTemplateDTO messageTemplate);

    @PostMapping("/message-templates/pager")
    ResponseResult getMessageTemplates(@RequestBody final DataGridPager<MessageTemplateDTO> pager);

    @GetMapping(value = "/message-templates/{id}")
    ResponseResult<MessageTemplateDTO> getMessageTemplate(@PathVariable("id") Integer id);

    @DeleteMapping(value = "/message-templates/{id}")
    ResponseResult<Integer> removeMessageTemplate(@PathVariable("id") Integer id);

    @GetMapping(value = "/message-templates/refresh")
    ResponseResult refreshMessageTemplateCache();

    //-----------------------MessageSendStatusDetail---------------------------------------------------------------------------//

    @PostMapping("/message-send-status-details/pager")
    ResponseResult getMessageSendStatusDetails(@RequestBody final DataGridPager<MessageSendStatusDetailDTO> pager);


    @GetMapping(value = "/message-send-status-details/{id}")
    ResponseResult<MessageSendStatusDetailDTO> getMessageSendStatusDetail(@PathVariable("id") Long id);

    //-----------------------MessageSuccessRatio ---------------------------------------------------------------------------//

    @PostMapping("/message-success-ratios/pager")
    ResponseResult getMessageSuccessRatios(@RequestBody final DataGridPager<MessageSuccessRatioDTO> pager);

    @GetMapping(value = "/message-success-ratios/{id}")
    ResponseResult<MessageSuccessRatioDTO> getMessageSuccessRatio(@PathVariable("id") Long id);

    @DeleteMapping(value = "/message-success-ratios/{id}")
    ResponseResult<MessageSuccessRatioDTO> removeMessageSuccessRatio(@PathVariable("id") Long id);

    //----------------------- Provider Conf---------------------------------------------------------------------------//

    @PostMapping(value = "/conf/providers")
    ResponseResult<Integer> addProviderConf(@RequestBody ProviderConfDTO dto);

    @PutMapping(value = "/conf/providers")
    ResponseResult<Integer> editProviderConf(@RequestBody ProviderConfDTO dto);

    @GetMapping("/conf/providers/pager")
    ResponseResult getProviderConfList(@RequestBody final DataGridPager pager,
                                       @RequestParam("brokerId") final Integer brokerId,
                                       @RequestParam("fieldName") final String fieldName,
                                       @RequestParam("keyword") final String keyword);

    @GetMapping(value = "/conf/providers/{id}")
    ResponseResult<ProviderConfDTO> getProviderConf(@RequestParam("brokerId") Integer brokerId,
                                                    @PathVariable("id") Integer id);

    @DeleteMapping(value = "/conf/providers")
    ResponseResult<Integer> removeProviderConf(@RequestBody ProviderConfDTO dto);

    //----------------------- Message Back Lists ---------------------------------------------------------------------------//

    @PostMapping(value = "/message-blacklists")
    ResponseResult<Integer> addMessageBlacklist(@RequestBody MessageBlacklistDTO dto);

    @PutMapping(value = "/message-blacklists")
    ResponseResult<Integer> editMessageBlacklist(@RequestBody MessageBlacklistDTO dto);

    @GetMapping("/message-blacklists/pager")
    ResponseResult getMessageBlacklists(@RequestBody final DataGridPager pager,
                                        @RequestParam("brokerId") final Integer brokerId,
                                        @RequestParam("fieldName") final String fieldName,
                                        @RequestParam("keyword") final String keyword);

    @GetMapping(value = "/message-blacklists/{id}")
    ResponseResult<MessageBlacklistDTO> getMessageBlacklist(@RequestParam("brokerId") Integer brokerId,
                                                            @PathVariable("id") Integer id);

    @DeleteMapping(value = "/message-blacklists")
    ResponseResult<Integer> removeMessageBlacklist(@RequestBody MessageBlacklistDTO dto);

    @GetMapping(value = "/message-blacklists/refresh")
    ResponseResult refreshMessageBlacklistCache();

    //----------------------- White Lists ---------------------------------------------------------------------------//

    @PostMapping(value = "/message-whitelists")
    ResponseResult<Integer> addMessageWhitelist(@RequestBody MessageWhitelistDTO dto);

    @PutMapping(value = "/message-whitelists")
    ResponseResult<Integer> editMessageWhitelist(@RequestBody MessageWhitelistDTO dto);

    @GetMapping("/message-whitelists/pager")
    ResponseResult getMessageWhitelists(@RequestBody final DataGridPager pager,
                                        @RequestParam("brokerId") final Integer brokerId,
                                        @RequestParam("fieldName") final String fieldName,
                                        @RequestParam("keyword") final String keyword);

    @GetMapping(value = "/message-whitelists/{id}")
    ResponseResult<MessageWhitelistDTO> getMessageWhitelist(@RequestParam("brokerId") Integer brokerId,
                                                            @PathVariable("id") Integer id);

    @DeleteMapping(value = "/message-whitelists")
    ResponseResult<Integer> removeMessageWhitelist(@RequestBody MessageWhitelistDTO dto);

    @GetMapping(value = "/message-whitelists/refresh")
    ResponseResult refreshMessageWhitelistCache();
}
