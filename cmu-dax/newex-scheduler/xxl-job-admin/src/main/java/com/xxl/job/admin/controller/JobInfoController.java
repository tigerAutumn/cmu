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
import java.util.*;

/**
 * index controller
 *
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobinfo")
@Slf4j
public class JobInfoController {

    @Resource
    private XxlJobGroupDao xxlJobGroupDao;
    @Resource
    private XxlJobService xxlJobService;
    @Resource
    private XxlJobRelationDao xxlJobRelationDao;
    @Autowired
    private MsgSendService msgSendService;


    @RequestMapping
    public String index(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "-1") int jobGroup) {

        XxlJobUser xxlJobUser = (XxlJobUser) request.getSession().getAttribute("xxlJobUser");


        List<XxlJobGroup> jobGroupList;

        if (xxlJobUser.getType() == 1) {
            jobGroupList = xxlJobGroupDao.findAll();
        } else {
            List<XxlJobRelation> relations = xxlJobRelationDao.queryXxlJobRelationByUserid(xxlJobUser.getId());
            List<Integer> ids = new ArrayList<>(relations.size());
            if (!CollectionUtils.isEmpty(relations)) {
                relations.stream().forEach(xxlJobRelation -> ids.add(xxlJobRelation.getRelationId()));
                jobGroupList = xxlJobGroupDao.findByIds(ids);
            }else {
                jobGroupList = new ArrayList<>(0);
            }
        }

        // 枚举-字典
        // 路由策略-列表
        model.addAttribute("ExecutorRouteStrategyEnum", ExecutorRouteStrategyEnum.values());
        // Glue类型-字典
        model.addAttribute("GlueTypeEnum", GlueTypeEnum.values());
        // 阻塞处理策略-字典
        model.addAttribute("ExecutorBlockStrategyEnum", ExecutorBlockStrategyEnum.values());
        // 失败处理策略-字典
        model.addAttribute("ExecutorFailStrategyEnum", ExecutorFailStrategyEnum.values());

        // 任务组
        model.addAttribute("JobGroupList", jobGroupList);
        model.addAttribute("jobGroup", jobGroup);

        return "jobinfo/jobinfo.index";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        int jobGroup, String jobDesc, String executorHandler, String filterTime) {

        return xxlJobService.pageList(start, length, jobGroup, jobDesc, executorHandler, filterTime);
    }

    @RequestMapping("/add")
    @ResponseBody
    public ReturnT<String> add(HttpServletRequest request, XxlJobInfo jobInfo) {
        XxlJobUser xxlJobUser = (XxlJobUser) request.getSession().getAttribute("xxlJobUser");

        List<XxlJobRelation> list = xxlJobRelationDao.queryXxlJobRelationByUseridAndRelationId(xxlJobUser.getId(),jobInfo.getJobGroup());
        if (!CollectionUtils.isEmpty(list)){
            ReturnT<String> returnString = xxlJobService.add(jobInfo);
            return returnString;
        }

        XxlJobRelation xxlJobRelation = new XxlJobRelation();
        xxlJobRelation.setRelationId(jobInfo.getJobGroup());
        xxlJobRelation.setUserId(xxlJobUser.getId());
        xxlJobRelation.setAddTime(new Date());
        xxlJobRelationDao.save(xxlJobRelation);
        ReturnT<String> returnString = xxlJobService.add(jobInfo);
        msgSendService.sendSMS(jobInfo.getId(),(XxlJobUser) request.getSession().getAttribute("xxlJobUser"),OperationEnum.ADD);
        return returnString;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ReturnT<String> update(HttpServletRequest request,XxlJobInfo jobInfo) {
        msgSendService.sendSMS(jobInfo.getId(),(XxlJobUser) request.getSession().getAttribute("xxlJobUser"),OperationEnum.EDIT);
        return xxlJobService.update(jobInfo);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public ReturnT<String> remove(HttpServletRequest request,int id) {
        msgSendService.sendSMS(id,(XxlJobUser) request.getSession().getAttribute("xxlJobUser"),OperationEnum.DELETE);
        return xxlJobService.remove(id);
    }



    @RequestMapping("/pause")
    @ResponseBody
    public ReturnT<String> pause(HttpServletRequest request,int id) {
        msgSendService.sendSMS(id,(XxlJobUser) request.getSession().getAttribute("xxlJobUser"),OperationEnum.PAUSE);
        return xxlJobService.pause(id);
    }

    @RequestMapping("/resume")
    @ResponseBody
    public ReturnT<String> resume(HttpServletRequest request,int id) {
        msgSendService.sendSMS(id,(XxlJobUser) request.getSession().getAttribute("xxlJobUser"),OperationEnum.RESUME);
        return xxlJobService.resume(id);
    }

    @RequestMapping("/trigger")
    @ResponseBody
    public ReturnT<String> triggerJob(HttpServletRequest request,int id) {
        msgSendService.sendSMS(id,(XxlJobUser) request.getSession().getAttribute("xxlJobUser"),OperationEnum.TRIGGER);
        return xxlJobService.triggerJob(id);
    }

}
