package com.xxl.job.admin.service;

import com.xxl.job.admin.consts.OperationEnum;
import com.xxl.job.admin.core.model.XxlJobUser;

/**
 * @author minhan
 * @Date: 2018/7/19 15:29
 * @Description:
 */
public interface MsgSendService {


    void sendSMS(int id,XxlJobUser xxlJobUser,OperationEnum operationEnum);


    void sendSMS(String msg);

    void sendMail(String mails, String msg);
}
