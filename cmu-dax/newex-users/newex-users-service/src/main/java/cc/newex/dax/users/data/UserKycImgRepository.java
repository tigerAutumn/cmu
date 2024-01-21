package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserKycImgExample;
import cc.newex.dax.users.domain.UserKycImg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户kyc证件照片信息 数据访问类
 * @author newex-team
 * @date 2018-05-03
 */
@Repository
public interface UserKycImgRepository extends CrudRepository<UserKycImg, UserKycImgExample, Long> {
    /**
     * 插入一条数据，如果存在则替换
     *
     * @param record
     * @return 影响的记录数
     */
    int replace(@Param("record") UserKycImg record);
}