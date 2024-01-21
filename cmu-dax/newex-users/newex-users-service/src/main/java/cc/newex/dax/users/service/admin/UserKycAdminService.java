package cc.newex.dax.users.service.admin;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.dto.common.PageResultDTO;
import cc.newex.dax.users.dto.kyc.AdminUserKycAuditDTO;
import cc.newex.dax.users.dto.kyc.UserInfoStatDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycFirstDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycSecondDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminListReqDTO;

public interface UserKycAdminService {

    /**
     * 按条件查询kyc列表信息
     *
     * @param userKycAdminListReqDTO
     * @return
     */
    PageResultDTO list(UserKycAdminListReqDTO userKycAdminListReqDTO);

    /**
     * 通过用户id获取kyc1认证信息
     *
     * @param userId
     * @param level
     * @return
     */
    UserKycAdminKycFirstDetailDTO selectKycAdminFirstDetail(Long userId, Integer level);

    /**
     * 通过用户id获取kyc2认证信息
     *
     * @param userId
     * @param level
     * @return
     */
    UserKycAdminKycSecondDetailDTO selectKycAdminSecondDetail(Long userId, Integer level);

    /**
     * 删除kyc信息
     *
     * @param userId
     */
    void deleteKycInfo(Long userId);

    void deleteByUserId(Long userId);

    /**
     * 审核kyc等级
     *
     * @param audit
     * @return
     */
    ResponseResult auditKyc(AdminUserKycAuditDTO audit);

    /**
     * 根据开始时间和结束时间查询kyc1和kyc2这段时间的认证人数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    UserInfoStatDTO getUserInfoStat(Long startTime, Long endTime);

    /**
     * 打开或者关闭法币后台管理使用接口 状态 0:正常 1:冻结账户
     *
     * @param userId
     * @param status
     * @return
     */
    boolean enableFiatStatus(long userId, int status);
}
