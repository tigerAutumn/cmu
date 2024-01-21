package com.xxl.job.admin.core.model;

import java.util.Date;

import lombok.Data;

/**
 * xxl-job info
 * @author xuxueli  2016-1-12 18:25:49
 */
@Data
public class XxlJobInfo {
	
	private int id;				// 主键ID	    (JobKey.name)
	
	private int jobGroup;		// 执行器主键ID	(JobKey.group)
	private String jobCron;		// 任务执行CRON表达式 【base on quartz】
	private String jobDesc;
	
	private Date addTime;
	private Date updateTime;
	
	private String author;		// 负责人
	private String alarmEmail;	// 报警邮件

	private String executorRouteStrategy;	// 执行器路由策略
	private String executorHandler;		    // 执行器，任务Handler名称
	private String executorParam;		    // 执行器，任务参数
	private String executorBlockStrategy;	// 阻塞处理策略
	private String executorFailStrategy;	// 失败处理策略
	
	private String glueType;		// GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum
	private String glueSource;		// GLUE源代码
	// GLUE备注
	private String glueRemark;
	// GLUE更新时间
	private Date glueUpdatetime;

	/**
	 * 子任务ID，多个逗号分隔
	 */
	private String childJobId;

	/**
	 * 任务状态 【base on quartz】
	 */
	private String jobStatus;


}
