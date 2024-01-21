package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserInfoExample;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserLoginInfo;
import cc.newex.dax.users.domain.UserStatisticsInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 用户信息表 数据访问类
 *
 * @author newex-team
 * @date 2018-04-06
 */
@Repository
public interface UserInfoRepository
        extends CrudRepository<UserInfo, UserInfoExample, Long> {

    UserInfo selectUserInfoById(@Param("id") long id);

    UserDetailInfo selectUserDetailInfo(@Param("id") long id);

    UserLoginInfo selectUserLoginInfoByMobile(@Param("mobile") String phone);

    UserLoginInfo selectUserLoginInfoByEmail(@Param("email") String email);

    UserLoginInfo selectUserLoginInfoById(@Param("id") long userId);

    UserInfo selectUserInfoByMobile(@Param("mobile") String phone);

    UserInfo selectUserInfoByEmail(@Param("email") String email);

    UserDetailInfo selectDetailUserInfoByMobile(@Param("mobile") String phone);

    UserDetailInfo selectDetailUserInfoByEmail(@Param("email") String email);

    List<UserStatisticsInfo> selectUserStatisticsInfoByTime(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<String> selectUid(@Param("ids") List<Long> ids);

}