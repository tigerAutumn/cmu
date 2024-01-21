package cc.newex.dax.boss.admin.service.impl;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.admin.criteria.GroupExample;
import cc.newex.dax.boss.admin.data.GroupRepository;
import cc.newex.dax.boss.admin.domain.Group;
import cc.newex.dax.boss.admin.domain.GroupUser;
import cc.newex.dax.boss.admin.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 后台系统企业组织机构表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class GroupServiceImpl
        extends AbstractCrudService<GroupRepository, Group, GroupExample, Integer>
        implements GroupService {

    @Autowired
    private GroupRepository groupRepos;

    @Override
    public List<GroupUser> getAdminUsers(final Integer id) {
        return this.groupRepos.selectAdminUsers(id);
    }

    @Override
    protected GroupExample getPageExample(final String fieldName, final String keyword) {
        final GroupExample example = new GroupExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int add(final Group record) {
        this.dao.insert(record);
        final String path = this.getPath(record.getParentId(), record.getId());
        return this.updatePath(record.getId(), path);
    }

    @Override
    public List<Group> getByPage(final PageInfo pageInfo, final Integer pid, final Integer brokerId) {
        final GroupExample example = new GroupExample();
        final GroupExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(pid);
        criteria.andBrokerIdEqualTo(brokerId);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<Group> getChildren(final int id) {
        final GroupExample example = new GroupExample();
        example.or().andParentIdEqualTo(id);
        return this.groupRepos.selectByExample(example);
    }

    @Override
    public Group getInfoById(final int id) {
        return this.groupRepos.selectById(id);

    }

    @Override
    public boolean hasChildren(final int id) {
        final GroupExample example = new GroupExample();
        example.or().andParentIdEqualTo(id);
        return this.groupRepos.countByExample(example) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(final int id, final int pid) {
        final boolean hasChild = this.hasChildren(pid);
        //如果有子节点删除先应该删除所有的节点
        if (hasChild) {
            return false;
        }
        return this.groupRepos.deleteById(id) > 0;
    }

    @Override
    public void rebuildPathById(final int id) {
        final List<Group> groups = this.getChildren(id);
        for (final Group entity : groups) {
            final String path = this.getPath(entity.getParentId(), entity.getId());
            this.updatePath(entity.getId(), path);
            this.rebuildPathById(entity.getId());
        }
    }

    @Override
    public void rebuildAllPath() {
        final List<Group> groups = this.getAll();
        final List<Group> roots = groups.stream()
                .filter(group -> group.getParentId().equals(0))
                .collect(Collectors.toList());
        for (final Group root : roots) {
            root.setPath(root.getId().toString());
            this.rebuildPath(groups, root);
        }
        this.groupRepos.batchUpdate(groups);
    }

    @Override
    public List<TreeNode<Group>> getGroupTree(final List<Group> groups, final Predicate<Group> predicate) {
        final List<TreeNode<Group>> roots = new ArrayList<>();
        groups.stream()
                .filter(predicate)
                .filter(group -> Objects.equals(group.getParentId(), 0))
                .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : -1)
                .forEach((Group group) -> this.addGroupTreeNode(roots, groups, group, predicate));
        return roots;
    }

    @Override
    public List<Group> getAllByBrokerId(final Integer id) {
        final GroupExample example = new GroupExample();
        if (id != 0) {
            example.or().andBrokerIdEqualTo(id);
        }
        return this.groupRepos.selectByExample(example);
    }

    private void addGroupTreeNode(final List<TreeNode<Group>> children, final List<Group> groups,
                                  final Group group,
                                  final Predicate<Group> predicate) {
        final String cateId = Integer.toString(group.getId());
        final String pid = Integer.toString(group.getParentId());
        final String text = group.getName();
        final String state = ArrayUtils.getLength(StringUtils.split(group.getPath(), ',')) > 0 ? "closed" : "open";
        final TreeNode<Group> parentNode =
                new TreeNode<>(cateId, pid, text, state, "", false, group);
        this.addChildGroupTreeNodes(groups, parentNode, predicate);
        children.add(parentNode);
    }

    private void addChildGroupTreeNodes(final List<Group> groups, final TreeNode<Group> parentNode,
                                        final Predicate<Group> predicate) {
        final Integer id = Integer.valueOf(parentNode.getId());
        groups.stream()
                .filter(predicate)
                .filter(group -> Objects.equals(group.getParentId(), id))
                .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : 0)
                .forEach(group -> this.addGroupTreeNode(parentNode.getChildren(), groups, group, predicate));
    }

    private void rebuildPath(final List<Group> groups, final Group parent) {
        final List<Group> children = groups.stream()
                .filter(group -> group.getParentId().equals(parent.getId()))
                .collect(Collectors.toList());
        for (final Group child : children) {
            final String path = parent.getPath() + "," + child.getId().toString();
            child.setPath(path);
            this.rebuildPath(groups, child);
        }
    }

    private int updatePath(final Integer id, final String path) {
        final Group group = Group.builder().id(id).path(path).build();
        return this.groupRepos.updateById(group);
    }

    private String getPath(final int pid, final int id) {
        if (pid <= 0) {
            return "" + id;
        }
        final Group po = this.groupRepos.selectById(pid);
        return po.getPath() + "," + id;
    }
}