package com.xxl.job.admin.core.model;

import lombok.Data;

import java.util.Date;

/**
 * xxl-job info
 * @author xuxueli  2016-1-12 18:25:49
 */
@Data
public class XxlJobTrigger {

	private int triggerName;

	private int triggerGroup;

	private int jobName;

	private int jobGroup;

	private int priority;

	private String triggerState;

	private String triggerType;

}
