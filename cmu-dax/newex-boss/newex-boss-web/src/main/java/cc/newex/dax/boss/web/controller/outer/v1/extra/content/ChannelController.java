package cc.newex.dax.boss.web.controller.outer.v1.extra.content;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.users.client.UsersAdminClient;
import cc.newex.dax.users.dto.admin.UserInvitationChannelDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/v1/boss/extra/content/channel")
@Slf4j
public class ChannelController {

    @Autowired
    private UsersAdminClient usersAdminClient;

    @GetMapping(value = "/list")
    @OpLog(name = "查看列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_CHANNEL_VIEW"})
    public ResponseResult getApps(DataGridPager pager,
                                  @RequestParam(value = "channelCode", required = false) String channelCode,
                                  @RequestParam(value = "channelName", required = false) String channelName,
                                  @RequestParam(value = "channelFullName", required = false) String channelFullName) {
        ResponseResult<DataGridPagerResult<UserInvitationChannelDTO>> result = usersAdminClient.getUserInvitationChannelDTO(pager, channelCode, channelName, channelFullName);
        return ResultUtil.getDataGridResult(result);
    }


    @GetMapping(value = "/code")
    @OpLog(name = "获取渠道码")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_CHANNEL_CODE"})
    public ResponseResult code() {
        ResponseResult<UserInvitationChannelDTO> result = usersAdminClient.generateChannelCode();
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/delete")
    @OpLog(name = "删除渠道")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_CHANNEL_DELETE"})
    public ResponseResult delete(@RequestParam(value = "id") Long id) {
        ResponseResult result = usersAdminClient.remove(id);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑渠道")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_CHANNEL_EDIT"})
    public ResponseResult edit(UserInvitationChannelDTO dto) {
        ResponseResult result = usersAdminClient.editUserInvitationChannelDTO(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "添加渠道")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_CHANNEL_ADD"})
    public ResponseResult add(UserInvitationChannelDTO dto) {
        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        ResponseResult result = usersAdminClient.addUserInvitationChannelDTO(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

}
