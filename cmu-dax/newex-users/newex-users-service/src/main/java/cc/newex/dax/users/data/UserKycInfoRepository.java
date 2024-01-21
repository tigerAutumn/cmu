package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.users.criteria.UserKycInfoExample;
import cc.newex.dax.users.domain.UserKycInfo;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycFirstDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycSecondDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminListReqDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminListResDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户kyc表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-03
 */
@Repository
public interface UserKycInfoRepository extends CrudRepository<UserKycInfo, UserKycInfoExample, Long> {
    /**
     * 插入一条数据，如果存在
     *
     * @param record
     * @return 影响的记录数
     */
    int replace(@Param("record") UserKycInfo record);


    /**
     * 分页查询
     *
     * @param pager
     * @param userKycAdminListReqDTO 查询条件参数
     * @return 分页记录列表
     */
    List<UserKycAdminListResDTO> selectKycAdminListByPager(@Param("pager") PageInfo pager, @Param("dto") UserKycAdminListReqDTO userKycAdminListReqDTO);

    /**
     * 查询kyc总数
     *
     * @param pager
     * @param userKycAdminListReqDTO
     * @return
     */
    UserKycAdminListResDTO countKycAdminListByPager(@Param("pager") PageInfo pager, @Param("dto") UserKycAdminListReqDTO userKycAdminListReqDTO);

    /**
     * 查询用户kyc1数据
     *
     * @param userId
     * @param level
     * @return
     */
    UserKycAdminKycFirstDetailDTO selectChinaFirstDetail(@Param("userId") Long userId, @Param("level") Integer level);

    /**
     * 查询国际用户kyc1数据
     *
     * @param userId
     * @return
     */
    UserKycAdminKycFirstDetailDTO selectForeignFirstDetail(@Param("userId") Long userId, @Param("level") Integer level);

    /**
     * @param userId
     * @param level
     * @return
     */
    UserKycAdminKycSecondDetailDTO selectKycAdminSecondDetail(@Param("userId") Long userId, @Param("level") Integer level);
}