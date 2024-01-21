package cc.newex.dax.boss.admin.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.admin.criteria.CaptchaCodeExample;
import cc.newex.dax.boss.admin.domain.CaptchaCode;

/**
 * 后台系统用户登录验证码表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface CaptchaCodeService
        extends CrudService<CaptchaCode, CaptchaCodeExample, Long> {

    boolean addGoogleCaptcha(final String key, final String code);

    CaptchaCode getGoogleCaptcha(final String key);
}
