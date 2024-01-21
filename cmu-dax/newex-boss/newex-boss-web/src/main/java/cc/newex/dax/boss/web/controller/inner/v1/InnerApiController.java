package cc.newex.dax.boss.web.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.UserService;
import cc.newex.dax.boss.dto.domain.UserDTO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 内部服务调用API Controller
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/boss")
public class InnerApiController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseResult<List<UserDTO>> list(@RequestParam("groupIds") final String groupIds) {
        final List<User> users = this.userService.selectUserInfoByGroupIds(groupIds);
        if (users == null) {
            return ResultUtils.success(Lists.newArrayList());
        }
        final ModelMapper modelMapper = new ModelMapper();
        final List<UserDTO> userList = modelMapper.map(
                users, new TypeToken<List<UserDTO>>() {
                }.getType()
        );
        return ResultUtils.success(userList);
    }
}
