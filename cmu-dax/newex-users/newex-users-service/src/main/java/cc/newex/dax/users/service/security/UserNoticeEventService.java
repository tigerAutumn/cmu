package cc.newex.dax.users.service.security;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserNoticeEventExample;
import cc.newex.dax.users.domain.UserNoticeEvent;
import cc.newex.dax.users.limited.BehaviorTheme;

import java.util.Map;

/**
 * 信息发送记录 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface UserNoticeEventService extends CrudService<UserNoticeEvent, UserNoticeEventExample, Long> {

    /**
     * 发送邮箱验证码
     *
     * @param locale       本地语言
     * @param behavior     行为
     * @param business     和NoticeSendLogConsts对应
     * @param email        邮箱
     * @param templateCode 邮件模板
     * @param userId       用户ID
     * @param params       邮件模板中使用的参数
     * @param brokerId     券商ID
     * @return
     */
    boolean sendEmail(String locale, BehaviorTheme behavior, int business, String email, String templateCode, Long userId, Map<String, Object> params, Integer brokerId);

    /**
     * 发送短信验证码
     *
     * @param locale       本地语言
     * @param behavior     行为
     * @param business     和NoticeSendLogConsts对应
     * @param mobile       手机号
     * @param areaCode     地区码
     * @param templateCode 短信模板
     * @param userId       用户ID
     * @param params       短信模板中使用的参数
     * @param brokerId     券商ID
     * @return
     */
    boolean sendSMS(String locale, BehaviorTheme behavior, int business, String mobile, int areaCode, String templateCode, Long userId, Map<String, Object> params, Integer brokerId);
}
