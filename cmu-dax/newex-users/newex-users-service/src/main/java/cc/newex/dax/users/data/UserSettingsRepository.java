package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserSettingsExample;
import cc.newex.dax.users.domain.UserSettings;
import org.springframework.stereotype.Repository;

/**
 * 用户设置表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-04 16:12:57
 */
@Repository
public interface UserSettingsRepository extends CrudRepository<UserSettings, UserSettingsExample, Long> {
}