package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserInviteRecordExample;
import cc.newex.dax.users.domain.UserInviteRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 邀请好友记录表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-29
 */
@Repository
public interface UserInviteRecordRepository
        extends CrudRepository<UserInviteRecord, UserInviteRecordExample, Long> {
    /**
     * @param id       分页id
     * @param pageSize 分页大小
     * @description 根据分页id获取记录列表
     * @date 2018/5/29 下午8:18
     */
    List<UserInviteRecord> selectUserInviteList(@Param("id") final Long id, @Param("pageSize") final Integer pageSize);

    /**
     * 24h被邀请认证且完成KYC2的用户数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer inviteKyc2Count(@Param("startTime") final Date startTime, @Param("endTime") final Date endTime);
}