package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserInvitationChannelExample;
import cc.newex.dax.users.domain.UserInvitationChannel;
import org.springframework.stereotype.Repository;

/**
 * 渠道推广表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-07-25 15:34:50
 */
@Repository
public interface UserInvitationChannelRepository extends CrudRepository<UserInvitationChannel, UserInvitationChannelExample, Long> {
}