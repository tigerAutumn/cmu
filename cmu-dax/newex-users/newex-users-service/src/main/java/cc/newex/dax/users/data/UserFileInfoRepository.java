package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserFileInfoExample;
import cc.newex.dax.users.domain.UserFileInfo;
import org.springframework.stereotype.Repository;

/**
 * 用户文件表 数据访问类
 *
 * @author newex-team
 * @date 2018-08-15 18:27:33
 */
@Repository
public interface UserFileInfoRepository extends CrudRepository<UserFileInfo, UserFileInfoExample, Long> {
}