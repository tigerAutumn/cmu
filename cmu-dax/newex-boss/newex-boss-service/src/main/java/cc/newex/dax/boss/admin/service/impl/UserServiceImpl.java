package cc.newex.dax.boss.admin.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.security.PasswordService;
import cc.newex.dax.boss.admin.criteria.UserExample;
import cc.newex.dax.boss.admin.data.UserRepository;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台系统用户表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class UserServiceImpl
        extends AbstractCrudService<UserRepository, User, UserExample, Integer>
        implements UserService {

    @Autowired
    private UserRepository userRepos;
    @Autowired
    private PasswordService passwordService;

    @Override
    protected UserExample getPageExample(final String fieldName, final String keyword) {
        final UserExample example = new UserExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public User getUserByAccount(final String account) {
        final UserExample example = new UserExample();
        example.or().andAccountEqualTo(account);
        return this.userRepos.selectOneByExample(example);
    }

    @Override
    public void encryptPassword(final User user) {
        user.setPassword(this.passwordService.encode(user.getPassword()));
    }

    @Override
    public User getUserWithoutSensitiveInfo(final String account) {
        final UserExample example = new UserExample();
        example.or().andAccountEqualTo(account);
        return this.userRepos.selectUserWithoutSensitiveInfo(example);
    }

    /**
     * 按照用户groupids 查询用户信息
     *
     * @param groupIds
     * @return
     */
    @Override
    public List<User> selectUserInfoByGroupIds(final String groupIds) {
        return this.userRepos.selectUserInfoByGroupIds(groupIds);
    }
}