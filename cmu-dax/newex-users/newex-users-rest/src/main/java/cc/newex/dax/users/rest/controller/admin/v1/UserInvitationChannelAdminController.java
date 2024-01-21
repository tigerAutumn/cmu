package cc.newex.dax.users.rest.controller.admin.v1;


import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.criteria.UserInvitationChannelExample;
import cc.newex.dax.users.domain.UserInvitationChannel;
import cc.newex.dax.users.dto.admin.UserInvitationChannelDTO;
import cc.newex.dax.users.rest.common.config.business.ChannelinkProperties;
import cc.newex.dax.users.service.admin.UserInvitationChannelAdminService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 提供给boss后台管理系统使用的接口服务
 *
 * @author newex-team
 * @date 2018/7/25
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/users/channel")
public class UserInvitationChannelAdminController {

    @Resource
    private UserInvitationChannelAdminService userInvitationChannelService;
    @Resource
    private ChannelinkProperties envProperties;

    @PostMapping(value = "/pager")
    public ResponseResult<DataGridPagerResult<UserInvitationChannelDTO>> getList(@RequestBody final DataGridPager pager,
                                                                                 @RequestParam(value = "channelCode", required = false) final String channelCode,
                                                                                 @RequestParam(value = "channelName", required = false) final String channelName,
                                                                                 @RequestParam(value = "channelFullName", required = false) final String channelFullName) {
        final PageInfo pageInfo = pager.toPageInfo();
        final UserInvitationChannelExample example = new UserInvitationChannelExample();
        final UserInvitationChannelExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(channelCode)) {
            criteria.andChannelCodeLike("%" + channelCode + "%");
        }
        if (StringUtils.isNotEmpty(channelName)) {
            criteria.andChannelNameLike("%" + channelName + "%");
        }
        if (StringUtils.isNotEmpty(channelFullName)) {
            criteria.andChannelFullNameLike("%" + channelFullName + "%");
        }
        final List<UserInvitationChannel> list = this.userInvitationChannelService.getByPage(pageInfo, example);

        final List<UserInvitationChannelDTO> dtoList = Lists.newArrayListWithCapacity(CollectionUtils.size(list));
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(x -> dtoList.add(ObjectCopyUtils.map(x, UserInvitationChannelDTO.class)));
        }
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), dtoList));
    }

    @GetMapping(value = "/generate")
    public ResponseResult<UserInvitationChannelDTO> generate() {
        final UserInvitationChannelExample example = new UserInvitationChannelExample();
        example.setOrderByClause(" channel_code desc");
        final UserInvitationChannel userInvitationChannel = this.userInvitationChannelService.getOneByExample(example);
        //从100000开始
        final String channelCode = userInvitationChannel == null ? "99999" : userInvitationChannel.getChannelCode();
        final String generateChannelCode = this.addStrings(channelCode,
                "1");
        final String channeLink = this.envProperties.getUri() + "/channel/signup?utm_source=" + generateChannelCode;
        return ResultUtils.success(UserInvitationChannelDTO.builder().channelCode(generateChannelCode).channeLink(channeLink).build());
    }

    /**
     * 参考 https://leetcode-cn.com/problems/add-strings/description/
     */
    private String addStrings(final String num1,
                              final String num2) {
        if (num1.length() < num2.length()) {
            return this.addStrings(num2, num1);
        }
        final char[] cs1 = num1.toCharArray();
        final char[] cs2 = num2.toCharArray();
        for (int i = 0; i < cs2.length; i++) {
            cs1[(num1.length() - num2.length()) + i] += (cs2[i] - '0');
        }
        final char[] cs = new char[cs1.length];
        int lift = 0;
        for (int i = cs1.length - 1; i >= 0; i--) {
            cs1[i] += lift;
            if (cs1[i] > '9') {
                cs1[i] -= 10;
                lift = 1;
            } else {
                lift = 0;
            }
            cs[i] = cs1[i];
        }
        return lift == 1 ? "1" + new String(cs) : new String(cs);
    }

    @PostMapping(value = "")
    public ResponseResult<Integer> add(@RequestBody final UserInvitationChannelDTO dto) {
        final UserInvitationChannel po = ObjectCopyUtils.map(dto, UserInvitationChannel.class);
        final int add = this.userInvitationChannelService.add(po);
        return ResultUtils.success(add);
    }

    @PutMapping(value = "")
    public ResponseResult<Integer> edit(@RequestBody final UserInvitationChannelDTO dto) {
        final UserInvitationChannel po = ObjectCopyUtils.map(dto, UserInvitationChannel.class);
        return ResultUtils.success(this.userInvitationChannelService.editById(po));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult<Integer> remove(@PathVariable("id") final Long id) {
        return ResultUtils.success(this.userInvitationChannelService.removeById(id));
    }
}
