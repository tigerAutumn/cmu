package cc.newex.dax.users.service.kyc.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserKycImgExample;
import cc.newex.dax.users.data.UserKycImgRepository;
import cc.newex.dax.users.domain.UserKycImg;
import cc.newex.dax.users.service.kyc.UserKycImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户kyc证件照片信息 服务实现
 *
 * @author newex-team
 * @date 2018-05-03
 */
@Slf4j
@Service
public class UserKycImgServiceImpl extends AbstractCrudService<UserKycImgRepository, UserKycImg, UserKycImgExample, Long>
        implements UserKycImgService {

    @Autowired
    private UserKycImgRepository userKycImgRepos;

    @Override
    protected UserKycImgExample getPageExample(final String fieldName, final String keyword) {
        final UserKycImgExample example = new UserKycImgExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public UserKycImg getByUserid(final Long userId) {
        if (userId == null || userId <= 0) {
            return null;
        }

        final UserKycImgExample example = new UserKycImgExample();
        example.createCriteria().andUserIdEqualTo(userId);

        return this.userKycImgRepos.selectOneByExample(example);
    }

    @Override
    public void deleteByUserId(final Long userId) {
        if (userId == null) {
            return;
        }

        final UserKycImgExample example = new UserKycImgExample();
        example.createCriteria().andUserIdEqualTo(userId);

        this.userKycImgRepos.deleteByExample(example);
    }

    @Override
    public int replace(final UserKycImg record) {
        return this.userKycImgRepos.replace(record);
    }

}