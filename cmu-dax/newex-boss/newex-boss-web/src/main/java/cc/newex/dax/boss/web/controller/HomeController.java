package cc.newex.dax.boss.web.controller;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.Module;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.security.service.AdminUserFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Home页控制器
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {
    @Resource
    private AdminUserFacadeService adminUserFacadeService;

    @GetMapping(value = {"", "/", "/index"})
    public String index(@CurrentUser final User loginUser, final Model model) {
        model.addAttribute("roleNames", this.adminUserFacadeService.getRoleNames(loginUser.getRoles()));
        model.addAttribute("user", loginUser);
        return "index";
    }

    @ResponseBody
    @GetMapping(value = "/home/getMenus")
    public ResponseResult<List<TreeNode<Module>>> getMenus(@CurrentUser final User loginUser) {
        // 系统用户所属角色集合(role_id以英文逗号分隔)
        final String roles = loginUser.getRoles();

        final List<Module> modules = this.adminUserFacadeService.getModules(roles);

        final List<TreeNode<Module>> treeNodeList = this.adminUserFacadeService.getModuleTree(
                modules.stream()
                        .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : -1)
                        .collect(Collectors.toList()),
                x -> x.getStatus() == 1);

        return ResultUtils.success(treeNodeList);
    }

    @GetMapping(value = "/logout")
    public String logout(final HttpServletRequest req) {
        SecurityContextHolder.clearContext();
        req.getSession().removeAttribute(UserAuthConsts.CURRENT_USER);
        return "login";
    }
}