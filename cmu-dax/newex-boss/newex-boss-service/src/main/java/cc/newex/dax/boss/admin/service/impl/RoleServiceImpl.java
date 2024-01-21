package cc.newex.dax.boss.admin.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.admin.criteria.RoleExample;
import cc.newex.dax.boss.admin.data.RoleRepository;
import cc.newex.dax.boss.admin.domain.Role;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 后台系统角色表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class RoleServiceImpl
        extends AbstractCrudService<RoleRepository, Role, RoleExample, Integer>
        implements RoleService {

    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

    @Autowired
    private RoleRepository roleRepos;

    @Override
    protected RoleExample getPageExample(final String fieldName, final String keyword) {
        final RoleExample example = new RoleExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<Role> getByPage(final PageInfo page, final User currentUser, final String fieldName,
                                final String keyword) {
        if (this.isSuperAdminRole(currentUser.getRoles())) {
            return this.getByPage(page, fieldName, keyword);
        }
        final RoleExample example = new RoleExample();
        final RoleExample.Criteria criteria = example.createCriteria();
        criteria.andAdminUsernameEqualTo(currentUser.getAccount());
        criteria.andFieldLike(fieldName, keyword);
        criteria.andBrokerIdEqualTo(currentUser.getBrokerId());
        return this.getByPage(page, example);
    }

    @Override
    public boolean isSuperAdminRole(final String roleIds) {
        final List<Role> list = this.getIn(roleIds);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        return list.stream().anyMatch(x -> ROLE_SUPER_ADMIN.equals(x.getCode()) && x.getBrokerId() == 0);
    }

    @Override
    public String getNames(final String roleIds) {
        final List<Role> list = this.getIn(roleIds);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        final List<String> namesList = new ArrayList<>(list.size());
        namesList.addAll(list.stream().map(Role::getName).collect(Collectors.toList()));
        return StringUtils.join(namesList, ',');
    }

    @Override
    public List<String> getCodes(final String roleIds) {
        final List<Role> list = this.getIn(roleIds);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(Role::getCode).collect(Collectors.toList());
    }

    @Override
    public String getModuleIds(final String roleIds) {
        return this.getIds(roleIds, Role::getModules);
    }

    @Override
    public String getPermissionIds(final String roleIds) {
        return this.getIds(roleIds, Role::getPermissions);
    }

    @Override
    public String getRoleIdsBy(final String createUser) {
        final List<Role> list = this.getByCreateUser(createUser);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        final List<Integer> roleIdList = new ArrayList<>(list.size());
        roleIdList.addAll(list.stream().map(Role::getId).collect(Collectors.toList()));
        return StringUtils.join(roleIdList, ',');
    }

    @Override
    public List<Role> getRolesList(final User logingUser) {
        if (this.isSuperAdminRole(logingUser.getRoles())) {
            return this.getAll();
        }
        return this.getByCreateUser(logingUser.getAccount());
    }

    @Override
    public Map<String, String[]> getRoleModulesAndPermissions(final Integer roleId) {
        final Role role = this.roleRepos.selectById(roleId);
        if (role == null) {
            return null;
        }

        final Map<String, String[]> map = new HashMap<>(2);
        final String distinctModules = this.distinct(StringUtils.split(role.getModules(), ','));
        final String distinctPerms = this.distinct(StringUtils.split(role.getPermissions(), ','));
        map.put("modules", StringUtils.split(distinctModules, ','));
        map.put("permissions", StringUtils.split(distinctPerms, ','));
        return map;
    }

    private String getIds(final String roleIds, final Function<? super Role, ? extends String> mapper) {
        final List<Role> list = this.getIn(roleIds);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        final List<String> idList = new ArrayList<>(list.size());
        idList.addAll(list.stream().map(mapper).collect(Collectors.toList()));
        final String joinIds = StringUtils.join(idList, ',');
        final String[] ids = StringUtils.split(joinIds, ',');
        return this.distinct(ids);
    }

    private String distinct(final String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return StringUtils.EMPTY;
        }
        final Set<String> idSet = new HashSet<>(ids.length);
        Collections.addAll(idSet, ids);
        return StringUtils.join(idSet, ',');
    }

    private List<Role> getIn(final String roleIds) {
        if (StringUtils.isBlank(roleIds)) {
            return new ArrayList<>(0);
        }
        final String[] ids = StringUtils.split(roleIds, ',');
        final List<Role> list = new ArrayList<>(10);
        for (final String id : ids) {
            list.add(Role.builder().id(Integer.valueOf(id)).build());
        }
        return this.roleRepos.selectIn(list);
    }

    private List<Role> getByCreateUser(final String createUser) {
        final RoleExample example = new RoleExample();
        example.createCriteria()
                .andAdminUsernameEqualTo(createUser);
        return this.roleRepos.selectByExample(example);
    }
}