package cc.newex.dax.users.service.membership;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserInviteRecordExample;
import cc.newex.dax.users.domain.UserInviteRecord;
import cc.newex.dax.users.dto.admin.UserInviteDetailDTO;
import cc.newex.dax.users.dto.admin.UserInviteReqDTO;
import cc.newex.dax.users.dto.membership.UserInviteDTO;

import java.util.Date;
import java.util.List;

/**
 * 邀请好友记录表 服务接口
 *
 * @author newex-team
 * @date 2018-05-29
 */
public interface UserInviteRecordService
        extends CrudService<UserInviteRecord, UserInviteRecordExample, Long> {
    /**
     * 通过用户id查询好友邀请记录
     *
     * @param userId
     * @return
     */
    UserInviteRecord getByUserId(Long userId);

    /**
     * @param userId       注册人id
     * @param inviteCode   邀请码
     * @param activityCode 活动编码
     * @description 邀请注册后记录
     * @date 2018/5/29 下午5:02
     */
    void inviteRegister(Long userId, String inviteCode, String activityCode, Integer brokerId);

    /**
     * @param id
     * @param pageSize
     */
    List<UserInviteRecord> queryUserInviteList(Long id, Integer pageSize);

    /**
     * 分页查询
     *
     * @param id
     * @param startDate
     * @param endDate
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<UserInviteRecord> queryUserInviteList(Long id, Date startDate, Date endDate, Integer pageIndex, Integer pageSize);

    /**
     * 返回注册用户和邀请人信息
     *
     * @param userId
     * @return
     */
    UserInviteDetailDTO queryUserInviteDetail(Long userId);

    /**
     * 邀请记录boss列表查询
     *
     * @param dto
     * @return
     */
    List<UserInviteReqDTO> queryUserInviteList(UserInviteReqDTO dto);

    /**
     * @param userId
     * @param activityCode 活动编码
     * @param brokerId
     * @description 通过用户id获取邀请用户信息
     * @date 2018/5/30 下午3:57
     */
    UserInviteDTO queryUserInviteInfo(Long userId, final String activityCode, Integer brokerId);
}
