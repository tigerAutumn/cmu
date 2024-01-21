package com.xxl.job.admin.job;

import com.xxl.job.admin.config.CommonProperties;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobTrigger;
import com.xxl.job.admin.dao.XxlJobInfoDao;
import com.xxl.job.admin.dao.XxlJobTriggerDao;
import com.xxl.job.admin.service.MsgSendService;
import com.xxl.job.admin.service.XxlJobLogService;
import com.xxl.job.core.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ticker最新数据更新移动到one minute job里面了
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Component
public class JobAlert {

    public static final String PAUSED = "PAUSED";

    @Autowired
    private MsgSendService msgSendService;
    @Resource
    private XxlJobInfoDao xxlJobInfoDao;
    @Resource
    private XxlJobTriggerDao xxlJobTriggerDao;
    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private XxlJobLogService xxlJobLogService;

    private final Map<Integer, String> preMap = new HashMap<>();

    @Scheduled(initialDelay = 500, fixedRate = 1000 * 60)
    public void alert() {
        final InetAddress inetAddress = IpUtil.getAddress();
        final String hostName = inetAddress.getHostName();
        final String realIp = inetAddress.getHostAddress();

        final String configIp = this.commonProperties.getIp();

        if (StringUtils.isNotBlank(configIp)) {
            if (!StringUtils.equalsAnyIgnoreCase(configIp, realIp, hostName)) {
                log.error("config realIp:{}, realIp:{}, hostName: {}", configIp, realIp, hostName);

                return;
            }
        }

        final Boolean[] isNotDiff = {true};
        final List<XxlJobInfo> xxlJobInfoList = this.xxlJobInfoDao.findAll();
        final StringBuffer sb = new StringBuffer();

        Boolean isFirst = true;
        if (this.preMap.size() != 0) {
            isFirst = false;
        }

        final Boolean finalIsFirst = isFirst;
        xxlJobInfoList.stream().forEach(xxlJobInfo -> {
            final XxlJobTrigger xxlJobTrigger = this.xxlJobTriggerDao.find(xxlJobInfo.getId());

            if (!finalIsFirst) {
                if (this.preMap.get(xxlJobInfo.getId()) == null) {
                    isNotDiff[0] = false;
                } else {
                    final String status = this.preMap.get(xxlJobInfo.getId());
                    if (PAUSED.equalsIgnoreCase(status) && !PAUSED.equalsIgnoreCase(xxlJobTrigger.getTriggerState())) {
                        isNotDiff[0] = false;
                        log.error("diff:{},{},{}", status, xxlJobInfo.toString(), xxlJobTrigger.toString());
                    } else if (!PAUSED.equalsIgnoreCase(status) && PAUSED.equalsIgnoreCase(xxlJobTrigger.getTriggerState())) {
                        isNotDiff[0] = false;
                        log.error("diff:{},{},{}", status, xxlJobInfo.toString(), xxlJobTrigger.toString());
                    } else {
                        isNotDiff[0] = true;
                    }

                }

                if (!isNotDiff[0]) {
                    sb.append("任务名：" + xxlJobInfo.getJobDesc());
                    sb.append(",创建者：" + xxlJobInfo.getAuthor());
                    sb.append(",执行器：" + xxlJobInfo.getExecutorHandler());
                    if (PAUSED.equalsIgnoreCase(xxlJobTrigger.getTriggerState())) {
                        sb.append(",状态：" + xxlJobTrigger.getTriggerState());
                    } else {
                        sb.append(",状态：" + xxlJobTrigger.getTriggerState());
                    }
                    sb.append("<br>");
                }
            }
            this.preMap.put(xxlJobInfo.getId(), xxlJobTrigger.getTriggerState());
        });

        if (sb.length() > 0) {
            log.error("send: {}", sb.toString());
            this.msgSendService.sendSMS("任务调度：任务状态有变化，请及时查看监控邮件");
            this.msgSendService.sendMail(this.commonProperties.getAdminEmail(), sb.toString());
        }

        this.xxlJobLogService.replaceLog();
    }
}
