package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserFiatSettingExample;
import cc.newex.dax.users.domain.UserFiatSetting;
import org.springframework.stereotype.Repository;

/**
 * 用户法币交易设置 数据访问类
 *
 * @author newex-team
 * @date 2018-05-14
 */
@Repository
public interface UserFiatSettingRepository
        extends CrudRepository<UserFiatSetting, UserFiatSettingExample, Long> {

}