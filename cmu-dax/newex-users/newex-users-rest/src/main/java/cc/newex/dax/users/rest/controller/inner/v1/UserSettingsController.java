package cc.newex.dax.users.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.service.membership.UserSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liutiejun
 * @date 2018-11-28
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/v1/users/settings")
public class UserSettingsController {

    @Autowired
    private UserSettingsService userSettingsService;

    /**
     * 更新永续合约协议
     *
     * @param userId
     * @param perpetualProtocolFlag
     * @return
     */
    @PostMapping(value = "/perpetualProtocolFlag")
    public ResponseResult<Integer> updatePerpetualProtocolFlag(@RequestParam("userId") final Long userId,
                                                               @RequestParam("perpetualProtocolFlag") final Integer perpetualProtocolFlag) {
        final int update = this.userSettingsService.updatePerpetualProtocolFlag(userId, perpetualProtocolFlag);

        return ResultUtils.success(update);
    }

}
