package cc.newex.dax.market.job.service;

/**
 * @author minhan
 * @Date: 2018/7/19 15:29
 * @Description:
 */
public interface MsgSendService {


    void sendSMS(String msg,String phones);

    void sendMail(String mails, String msg);
}
