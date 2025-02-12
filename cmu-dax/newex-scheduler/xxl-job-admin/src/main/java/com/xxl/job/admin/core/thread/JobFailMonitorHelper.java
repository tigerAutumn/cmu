package com.xxl.job.admin.core.thread;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.xxl.job.admin.config.CommonProperties;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.core.schedule.XxlJobDynamicScheduler;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.core.util.MailUtil;
import com.xxl.job.admin.dao.XxlJobInfoDao;
import com.xxl.job.admin.service.MsgSendService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * job monitor instance
 * @author xuxueli 2015-9-1 18:05:56
 */
@Slf4j
public class JobFailMonitorHelper {
	private static Logger logger = log;

	private static JobFailMonitorHelper instance = new JobFailMonitorHelper();
	public static JobFailMonitorHelper getInstance(){
		return instance;
	}

	// ---------------------- monitor ----------------------

	private LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(0xfff8);

	private Thread monitorThread;
	private volatile boolean toStop = false;
	public void start(){
		monitorThread = new Thread(() -> {
            // monitor
            while (!toStop) {
                try {
                    List<Integer> jobLogIdList = new ArrayList<>();
                    int drainToNum = JobFailMonitorHelper.instance.queue.drainTo(jobLogIdList);

                    if (CollectionUtils.isNotEmpty(jobLogIdList)) {
                        for (Integer jobLogId : jobLogIdList) {
                            if (jobLogId==null || jobLogId==0) {
                                continue;
                            }
                            XxlJobLog log = XxlJobDynamicScheduler.xxlJobLogDao.load(jobLogId);
                            if (log == null) {
                                continue;
                            }
                            if (IJobHandler.SUCCESS.getCode() == log.getTriggerCode() && log.getHandleCode() == 0) {
                                JobFailMonitorHelper.monitor(jobLogId);
                                logger.info(">>>>>>>>>>> job monitor, job running, JobLogId:{}", jobLogId);
                            } else if (IJobHandler.SUCCESS.getCode() == log.getHandleCode()) {
                                // job success, pass
                                logger.info(">>>>>>>>>>> job monitor, job success, JobLogId:{}", jobLogId);
                            } else if (IJobHandler.FAIL.getCode() == log.getTriggerCode()
                                    || IJobHandler.FAIL.getCode() == log.getHandleCode()
                                    || IJobHandler.FAIL_RETRY.getCode() == log.getHandleCode() ) {
                                // job fail,
                                failAlarm(log);
                                logger.info(">>>>>>>>>>> job monitor, job fail, JobLogId:{}", jobLogId);
                            } else {
                                JobFailMonitorHelper.monitor(jobLogId);
                                logger.info(">>>>>>>>>>> job monitor, job status unknown, JobLogId:{}", jobLogId);
                            }
                        }
                    }

                    TimeUnit.SECONDS.sleep(10);
                } catch (Exception e) {
                    logger.error("job monitor error:{}", e);
                }
            }

            // monitor all clear
            List<Integer> jobLogIdList = new ArrayList<>();
            int drainToNum = getInstance().queue.drainTo(jobLogIdList);
            if (jobLogIdList!=null && jobLogIdList.size()>0) {
                for (Integer jobLogId: jobLogIdList) {
                    XxlJobLog log = XxlJobDynamicScheduler.xxlJobLogDao.load(jobLogId);
                    if (ReturnT.FAIL_CODE == log.getTriggerCode()|| ReturnT.FAIL_CODE==log.getHandleCode()) {
                        // job fail,
                        failAlarm(log);
                        logger.info(">>>>>>>>>>> job monitor last, job fail, JobLogId:{}", jobLogId);
                    }
                }
            }

        });
		monitorThread.setDaemon(true);
		monitorThread.start();
	}

	public void toStop(){
		toStop = true;
		// interrupt and wait
		monitorThread.interrupt();
		try {
			monitorThread.join();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	// producer
	public static void monitor(int jobLogId){
		getInstance().queue.offer(jobLogId);
	}


	// ---------------------- alarm ----------------------

	/**
	 * email alarm template
	 */
	private static final String mailBodyTemplate = "<h5>" + I18nUtil.getString("jobconf_monitor_detail") + "：</span>" +
			"<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >\n" +
			"   <thead style=\"font-weight: bold;color: #ffffff;background-color: #ff8c00;\" >" +
			"      <tr>\n" +
			"         <td>"+ I18nUtil.getString("jobinfo_field_jobgroup") +"</td>\n" +
			"         <td>"+ I18nUtil.getString("jobinfo_field_id") +"</td>\n" +
			"         <td>"+ I18nUtil.getString("jobinfo_field_jobdesc") +"</td>\n" +
			"         <td>"+ I18nUtil.getString("jobconf_monitor_alarm_title") +"</td>\n" +
			"      </tr>\n" +
			"   <thead/>\n" +
			"   <tbody>\n" +
			"      <tr>\n" +
			"         <td>{0}</td>\n" +
			"         <td>{1}</td>\n" +
			"         <td>{2}</td>\n" +
			"         <td>"+ I18nUtil.getString("jobconf_monitor_alarm_type") +"</td>\n" +
			"      </tr>\n" +
			"   <tbody>\n" +
			"</table>";

	@Autowired
	private MsgSendService msgSendService;
	@Resource
	private CommonProperties commonProperties;

	/**
	 * fail alarm
	 *
	 * @param jobLog
	 */
	private void failAlarm(XxlJobLog jobLog){

		// send monitor email
		XxlJobInfo info = XxlJobDynamicScheduler.xxlJobInfoDao.loadById(jobLog.getJobId());
		if (info!=null && info.getAlarmEmail()!=null && info.getAlarmEmail().trim().length()>0) {
			XxlJobGroup group = XxlJobDynamicScheduler.xxlJobGroupDao.load(Integer.valueOf(info.getJobGroup()));

			String content = MessageFormat.format(mailBodyTemplate, group!=null?group.getTitle():"null", info.getId(), info.getJobDesc());
			msgSendService.sendSMS(info.getJobDesc()+"-执行失败-负责人["+info.getAuthor()+"],请查看邮件");
			msgSendService.sendMail(commonProperties.getAdminEmail(), content);
//			Set<String> emailSet = new HashSet<>(Arrays.asList(info.getAlarmEmail().split(",")));
//			for (String email: emailSet) {
//				XxlJobGroup group = XxlJobDynamicScheduler.xxlJobGroupDao.load(Integer.valueOf(info.getJobGroup()));
//
//				String content = MessageFormat.format(mailBodyTemplate, group!=null?group.getTitle():"null", info.getId(), info.getJobDesc());
//				msgSendService.sendSMS(info.getJobDesc()+"-执行失败-负责人["+info.getAuthor()+"],请查看邮件");
//				msgSendService.sendMail(commonProperties.getAdminEmail(), content);
//			}
		}
	}

}
