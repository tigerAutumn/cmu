package cc.newex.dax.boss.web.controller.outer.v1.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.criteria.UserExample;
import cc.newex.dax.boss.admin.domain.Group;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.GroupService;
import cc.newex.dax.boss.admin.service.UserService;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.security.service.AdminUserPasswordService;
import cc.newex.dax.boss.web.controller.common.BaseController;
import cc.newex.dax.boss.web.model.admin.GoogleAuthVO;
import cc.newex.dax.boss.web.model.admin.UserVO;
import com.google.common.collect.Lists;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@Validated
@RestController
@RequestMapping(value = "/v1/boss/admin/users")
public class UserController
        extends BaseController<UserService, User, UserExample, Integer> {

    @Resource
    private GroupService groupService;

    @Resource
    private AdminUserPasswordService passwordService;

    @RequestMapping(value = "/getAll")
    @OpLog(name = "获取所有用户信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_USER_VIEW"})
    public ResponseResult getAll() {
        final List<User> list = this.service.getAll();
        if (CollectionUtils.isEmpty(list)) {
            return ResultUtils.success(Lists.newArrayListWithCapacity(0));
        }
        final ModelMapper modelMapper = new ModelMapper();
        final List<UserVO> voList = Lists.newArrayListWithCapacity(list.size());
        list.forEach(x -> voList.add(modelMapper.map(x, UserVO.class)));
        return ResultUtils.success(voList);
    }

    @GetMapping(value = "/listByPage")
    @OpLog(name = "分页获取用户列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_USER_VIEW"})
    public ResponseResult listByPage(@CurrentUser final User loginUser, final DataGridPager pager,
                                     @RequestParam(value = "fieldName", required = false) final String fieldName,
                                     @RequestParam(value = "keyword", required = false) final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final UserExample example = new UserExample();
        final UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(fieldName) && !Objects.isNull(keyword)) {
            criteria.andFieldEqualTo(fieldName, keyword);
        }
        if (loginUser.getBrokerId() != 0) {
            criteria.andBrokerIdEqualTo(loginUser.getBrokerId());
        }
        final List<User> list = this.service.getByPage(pageInfo, example);
        final List<UserVO> newList = list.stream().map(l -> {
            String name = "";
            final Group infoById = this.groupService.getInfoById(l.getGroupId());
            if (infoById != null) {
                name = infoById.getName();
            }
            return UserVO.builder()
                    .account(l.getAccount())
                    .comment(l.getComment())
                    .dutyStatus(l.getDutyStatus())
                    .email(l.getEmail())
                    .imagePath(l.getImagePath())
                    .name(l.getName())
                    .orderNum(l.getOrderNum())
                    .password(l.getPassword())
                    .roles(l.getRoles())
                    .status(l.getStatus())
                    .telephone(l.getTelephone())
                    .totpKey(l.getTotpKey())
                    .type(l.getType())
                    .updatedDate(l.getUpdatedDate())
                    .createdDate(l.getCreatedDate())
                    .id(l.getId())
                    .groupId(l.getGroupId())
                    .groupName(name)
                    .brokerId(l.getBrokerId())
                    .build();

        }).collect(Collectors.toList());

        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", newList);
        return ResultUtils.success(modelMap);
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增用户")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_USER_ADD"})
    public ResponseResult add(@Valid final User po) {
        po.setCreatedDate(new Date());
        po.setUpdatedDate(new Date());
        po.setPassword(UUID.randomUUID().toString());
        po.setImagePath("0");
        this.service.add(po);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改用户")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_USER_EDIT"})
    public ResponseResult edit(@Valid final User po) {
        this.service.editById(po);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除用户")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_USER_REMOVE"})
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/editPassword")
    @OpLog(name = "修改用户登录密码")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_USER_EDIT_PASSWORD"})
    public ResponseResult editPassword(final Integer userId,
                                       @Length(min = 6, max = 20) final String password) {
        final User user = this.service.getById(userId);
        user.setPassword(password);
        this.service.encryptPassword(user);
        this.service.editById(user);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/changeMyPassword")
    @OpLog(name = "修改个人登录密码")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_USER_CHANGE_MY_PASSWORD"})
    public ResponseResult changeMyPassword(@CurrentUser final User loginUser,
                                           final String password,
                                           final String oldPassword) {
        final User user = this.service.getById(loginUser.getId());
        if (!this.passwordService.matches(oldPassword, user.getPassword())) {
            return ResultUtils.failure(BizErrorCodeEnum.OLD_PASSWORD_ERROR);
        }
        loginUser.setPassword(password);
        this.service.encryptPassword(loginUser);
        this.service.editById(loginUser);
        return ResultUtils.success();
    }

    @RequestMapping(value = "/generateGoogleKey")
    @OpLog(name = "生成google key")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_USER_ADD"})
    public ResponseResult generateGoogleKey() {
        final GoogleAuthenticator gAuth = new GoogleAuthenticator();
        final GoogleAuthenticatorKey key = gAuth.createCredentials();
        return ResultUtils.success(GoogleAuthVO.builder()
                .key(key.getKey())
                .qRCode(GoogleAuthenticatorQRGenerator.getOtpAuthURL("boss", "boss", key))
                .build());
    }

    @GetMapping(value = "/getByGroupId")
    @OpLog(name = "获取该组织的用户")
    public ResponseResult getUserByGroupId(@RequestParam(value = "groupId") final Integer groupId) {
        final UserExample userExample = new UserExample();
        final UserExample.Criteria criteria = userExample.createCriteria();
        if (Objects.nonNull(groupId)) {
            criteria.andGroupIdEqualTo(groupId);
        }
        return ResultUtils.success(this.service.getByExample(userExample));
    }


}
