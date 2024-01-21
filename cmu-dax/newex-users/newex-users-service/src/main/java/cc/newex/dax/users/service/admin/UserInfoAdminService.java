package cc.newex.dax.users.service.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;

import java.util.List;

/**
 * 用户表 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface UserInfoAdminService {

    UserInfo getUserInfoById(Long userId);

    boolean isAuthenticatorMobile(Long userId);

    boolean isCanModifyEmail(UserInfo uBean);

    int updateUserMobile(Long userId, String mobile, int areaCode);

    int updateUserEmail(Long userId, String email);

    boolean unbindGoogle(Long userId);

    /**
     * 解绑手机,验证邮箱绑定后才能解除手机
     *
     * @param userId
     * @return
     */
    boolean unbindMobile(Long userId);

    boolean resetUserPassword(UserInfo userInfo, int pwdType);

    int changeEmailAntiPhishingCode(Long userId, String antiPhishingCode);

    /**
     * 分页查询列表
     *
     * @param pageInfo
     * @param userInfoResDTO
     * @return
     */
    List<UserInfo> listByPage(PageInfo pageInfo, UserInfoResDTO userInfoResDTO);

    String getSession(Long userId);
}