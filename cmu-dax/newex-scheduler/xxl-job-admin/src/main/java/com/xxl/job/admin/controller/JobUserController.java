package com.xxl.job.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.dao.XxlJobUserDao;
import com.xxl.job.core.biz.model.ReturnT;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: JobUserController
 * @Description: 用户管理
 * @author: xin.hui
 * @date: 2018/3/5下午5:30
 */
@Controller
@RequestMapping("/jobusers")
public class JobUserController {

    @Resource
    private XxlJobUserDao xxlJobUserDao;

    private static String ADMIN = "admin";

    private XxlJobUser getUser(HttpServletRequest request) {
        XxlJobUser xxlJobUser = (XxlJobUser)request.getSession().getAttribute("xxlJobUser");
        return xxlJobUser == null ? new XxlJobUser() : xxlJobUser;
    }

    /**
     * 查询用户
     *
     * @return
     */
    @RequestMapping
    public String index(HttpServletRequest request, Model model) {
        XxlJobUser xxlJobUser = getUser(request);
        if (ADMIN.equalsIgnoreCase(xxlJobUser.getUserName())) {
            List<XxlJobUser> allJobUsers = xxlJobUserDao.findAll();
            model.addAttribute("list", allJobUsers);
        }else {
            List<XxlJobUser> list = new ArrayList<>();
            list.add(xxlJobUser);
            model.addAttribute("list", list);
        }
        return "jobusers/jobusers.index";
    }

    /**
     * 新增用户
     *
     * @param model,id
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ReturnT<String> save(HttpServletRequest request, XxlJobUser model) {
        XxlJobUser xxlJobUser = getUser(request);

        if (!ADMIN.equalsIgnoreCase(xxlJobUser.getUserName())) {
            return new ReturnT<>(500, "不是管理员用户！！");
        }

        // valid
        if (model.getUserName() == null || StringUtils.isBlank(model.getUserName())) {
            return new ReturnT<>(500, "请输入用户名");
        }
        if (model.getPassword() == null || StringUtils.isBlank(model.getPassword())) {
            return new ReturnT<>(500, "请输入密码");
        }
        XxlJobUser xxlJobUserModel = xxlJobUserDao.getUserByUserName(model.getUserName());
        if (xxlJobUserModel != null) {
            return new ReturnT<>(500, "用户名已经存在！！");
        }
        model.setPassword(DigestUtils.md5Hex(model.getPassword()));
        int ret = xxlJobUserDao.save(model);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    /**
     * 更新用户
     *
     * @param model,id
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ReturnT<String> updateUser(XxlJobUser model, int id) {

        XxlJobUser xxlJobUser = xxlJobUserDao.loadById(id);
        if (xxlJobUser == null) {
            return new ReturnT<String>(500, "id 错误，用户不存在！！");
        }
        if (model.getUserName() != null || !StringUtils.isBlank(model.getUserName())) {

            XxlJobUser xxlJobUserModel = xxlJobUserDao.getUserByUserName(model.getUserName());
            if (xxlJobUserModel == null) {
                return new ReturnT<String>(500, "用户名已经存在！！");
            }
            xxlJobUser.setUserName(model.getUserName());
        }
        if (model.getPassword() != null || !StringUtils.isBlank(model.getPassword())) {
            xxlJobUser.setPassword(DigestUtils.md5Hex(model.getPassword()));
        }
        int ret = xxlJobUserDao.update(xxlJobUser);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    @RequestMapping("/remove")
    @ResponseBody
    public ReturnT<String> remove(int id) {
        //删除
        int ret = xxlJobUserDao.delete(id);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

}
