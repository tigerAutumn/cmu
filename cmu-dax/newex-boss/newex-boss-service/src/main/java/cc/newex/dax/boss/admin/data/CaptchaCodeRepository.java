package cc.newex.dax.boss.admin.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.admin.criteria.CaptchaCodeExample;
import cc.newex.dax.boss.admin.domain.CaptchaCode;
import org.springframework.stereotype.Repository;

/**
 * 后台系统用户登录验证码表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface CaptchaCodeRepository
        extends CrudRepository<CaptchaCode, CaptchaCodeExample, Long> {
}