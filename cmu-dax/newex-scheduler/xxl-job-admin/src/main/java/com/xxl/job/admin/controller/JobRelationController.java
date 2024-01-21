package com.xxl.job.admin.controller;

import com.xxl.job.admin.consts.OperationEnum;
import com.xxl.job.admin.core.enums.ExecutorFailStrategyEnum;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobRelation;
import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.core.route.ExecutorRouteStrategyEnum;
import com.xxl.job.admin.dao.XxlJobGroupDao;
import com.xxl.job.admin.dao.XxlJobRelationDao;
import com.xxl.job.admin.dao.XxlJobUserDao;
import com.xxl.job.admin.service.MsgSendService;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * index controller
 *
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobrelation")
@Slf4j
public class JobRelationController {

    @Resource
    private XxlJobGroupDao xxlJobGroupDao;
    @Resource
    private XxlJobUserDao xxlJobUserDao;
    @Resource
    private XxlJobRelationDao xxlJobRelationDao;



    @RequestMapping
    public String index(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "-1") int jobGroup) {

        XxlJobUser xxlJobUser = (XxlJobUser) request.getSession().getAttribute("xxlJobUser");


        if (xxlJobUser.getType() ==0){
            model.addAttribute("jobRelation", new ArrayList<>(0));
            model.addAttribute("jobGroup", new ArrayList<>(0));
            model.addAttribute("jobUser",new ArrayList<>(0));
            return "jobrelation/jobrelation.index";
        }


        List<XxlJobRelation> relations = xxlJobRelationDao.findAll();
        List<XxlJobUser> users = xxlJobUserDao.findAll();
        List<XxlJobGroup> groups = xxlJobGroupDao.findAll();

        model.addAttribute("jobRelation", relations);
        model.addAttribute("jobGroup", groups);
        model.addAttribute("jobUser", users);

        return "jobrelation/jobrelation.index";
    }


    @RequestMapping("/save")
    @ResponseBody
    public ReturnT<String> save(HttpServletRequest request, Model model, XxlJobRelation jobRelation) {
        XxlJobUser xxlJobUser = (XxlJobUser) request.getSession().getAttribute("xxlJobUser");

        if (xxlJobUser.getType() ==0){
            return ReturnT.SUCCESS;
        }

        List<XxlJobRelation> list = xxlJobRelationDao.queryXxlJobRelationByUseridAndRelationId(jobRelation.getId(),jobRelation.getRelationId());
        if (!CollectionUtils.isEmpty(list)){
            return ReturnT.SUCCESS;
        }

        jobRelation.setAddTime(new Date());
        xxlJobRelationDao.save(jobRelation);

        return ReturnT.SUCCESS;
    }


    @RequestMapping("/remove")
    @ResponseBody
    public ReturnT<String> remove(HttpServletRequest request, Model model,int id) {
        XxlJobUser xxlJobUser = (XxlJobUser) request.getSession().getAttribute("xxlJobUser");
        if (xxlJobUser.getType() ==0){
            return ReturnT.SUCCESS;
        }
        xxlJobRelationDao.deleteById(id);

        List<XxlJobRelation> relations = xxlJobRelationDao.findAll();
        List<XxlJobUser> users = xxlJobUserDao.findAll();
        List<XxlJobGroup> groups = xxlJobGroupDao.findAll();

        model.addAttribute("jobRelation", relations);
        model.addAttribute("jobGroup", groups);
        model.addAttribute("jobUser", users);

        return ReturnT.SUCCESS;
    }


}
