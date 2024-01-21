package cc.newex.dax.users.service.membership;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.criteria.UserInfoExample;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserLoginInfo;
import cc.newex.dax.users.domain.UserStatisticsInfo;

import java.util.Date;
import java.util.List;

/**
 * 用户表 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface UserInfoService extends CrudService<UserInfo, UserInfoExample, Long> {

    /**
     * @param userId
     * @return
     */
    boolean exists(Long userId);

    /**
     * @param loginName
     * @return
     */
    boolean checkLoginName(String loginName);

    boolean checkLoginName(String loginName, String type);

    /**
     * 注册用户
     *
     * @param loginName
     * @param password
     * @param areaCode
     * @param ip
     * @param regFrom
     * @param brokerId
     * @return
     */
    long register(String loginName, String password, Integer areaCode, String ip, Integer regFrom, final String channel,
                  final Integer brokerId);

    /**
     * 生成JWT用户
     */
    JwtUserDetails createJwtUserDetails(UserDetailInfo userInfo);

    /**
     * 生成需要二次验证的JWT用户
     *
     * @param userDetailInfo
     * @return
     */
    JwtUserDetails createJwtStep2UserDetails(UserDetailInfo userDetailInfo);

    /**
     * 根据用户id更新用户的邮箱信息
     *
     * @param userId   用户id
     * @param newEmail 新邮箱
     * @return
     */
    int updateEmail(long userId, String newEmail, long ip, final boolean update);

    /**
     * 设置邮箱防钓鱼码
     *
     * @param userId
     * @param antiPhishingCode
     * @return
     */
    int updateEmailVerify(long userId, String antiPhishingCode);

    /**
     * 修改手机号
     *
     * @param userId
     * @param mobile
     * @param areaCode
     * @param ip
     * @return
     */
    int updateMobile(long userId, String mobile, int areaCode, long ip, boolean update);

    int resetPassword(long userId, String newPassword, int pwdStrengthLevel);

    int generateGoogelKey(long userId, String googleKey);

    /**
     * 获取用户登录时所需要的信息(包括密码字段)
     *
     * @param mobile
     * @return
     */
    UserLoginInfo getUserLoginInfoByMobile(String mobile);

    /**
     * 获取用户登录时所需要的信息(包括密码字段)
     *
     * @param email
     * @return
     */
    UserLoginInfo getUserLoginInfoByEmail(String email);

    /**
     * 获取用户登录时所需要的信息(包括密码字段)
     *
     * @param username
     * @return
     */
    UserLoginInfo getUserLoginInfo(final String username);

    /**
     * 获取用户登录时所需要的信息(包括密码字段)
     *
     * @param userId
     * @return
     */
    UserLoginInfo getUserLoginInfoById(final long userId);

    /**
     * 重置谷歌验证码
     *
     * @param userId
     * @return
     */
    int updateGoogelKey(long userId, String googleKey);

    /**
     * 解绑谷歌
     *
     * @param userId
     * @return
     */
    boolean unbindGoogle(long userId);

    /**
     * 获取用户信息(不包括密码字段)
     *
     * @param mobile
     * @return
     */
    UserInfo getUserInfoByMobile(String mobile);

    /**
     * 获取用户信息(不包括密码字段)
     *
     * @param email
     * @return
     */
    UserInfo getUserInfoByEmail(String email);

    /**
     * 获取用户详细信息(不包括密码字段)
     *
     * @param mobile
     * @return
     */
    UserDetailInfo getDetailUserInfoByMobile(String mobile);

    /**
     * 获取用户详细信息(不包括密码字段)
     *
     * @param email
     * @return
     */
    UserDetailInfo getDetailUserInfoByEmail(String email);

    /**
     * 获取用户详细信息(不包括密码字段)
     *
     * @param userId
     * @return
     */
    UserDetailInfo getUserDetailInfo(long userId);

    /**
     * 获取用户信息(不包括密码字段)
     *
     * @param userId
     * @return
     */
    UserInfo getUserInfo(final long userId);

    /**
     * 获取用户信息(不包括密码字段)
     *
     * @param username
     * @return
     */
    UserInfo getUserInfo(final String username);

    /**
     * 根据用户id分页获取用户列表
     *
     * @param userId
     * @return
     */
    List<UserInfo> selectByPager(Long userId, final int pageIndex, final int pageSize);

    /**
     * @param uid
     * @description 查询用户邀请id是否存在, true:存在,false:不存在
     * @date 2018/5/28 下午5:55
     */
    boolean isExistUid(String uid);

    /**
     * @param uid
     * @description 通过邀请码查询用户信息
     * @date 2018/5/28 下午9:22
     */
    UserInfo getUserInfoByUid(String uid);

    /**
     * @param
     * @description 生成唯一的邀请码
     * @date 2018/5/28 下午6:22
     */
    String generateRandomCode();

    List<UserStatisticsInfo> getUserStatisticsInfoByTime(Date beginDate, Date endDate);

    /**
     * 查询用户ID对应的uid
     *
     * @param userIdList
     * @return
     */
    List<String> getUid(List<Long> userIdList);

    /**
     * 判断当前用户当天是否登录
     *
     * @param userId
     * @return
     */
    Boolean isLoginWithTodayByUserId(Long userId);
}
