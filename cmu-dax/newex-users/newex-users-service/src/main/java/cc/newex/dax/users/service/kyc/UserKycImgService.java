package cc.newex.dax.users.service.kyc;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserKycImgExample;
import cc.newex.dax.users.domain.UserKycImg;

/**
 * 用户kyc证件照片信息 服务接口
 *
 * @author newex-team
 * @date 2018-05-03
 */
public interface UserKycImgService extends CrudService<UserKycImg, UserKycImgExample, Long> {

    /**
     * @param userId
     * @description 通过用户id获取用户kyc证件
     * @date 2018/5/8 上午11:56
     */
    UserKycImg getByUserid(Long userId);

    void deleteByUserId(Long userId);

    /**
     * 插入一条数据，如果存在则替换
     *
     * @param record
     * @return 影响的记录数
     */
    int replace(UserKycImg record);

}
