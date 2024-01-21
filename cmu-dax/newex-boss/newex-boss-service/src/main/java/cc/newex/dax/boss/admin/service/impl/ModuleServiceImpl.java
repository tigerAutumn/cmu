package cc.newex.dax.boss.admin.service.impl;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.admin.criteria.ModuleExample;
import cc.newex.dax.boss.admin.data.ModuleRepository;
import cc.newex.dax.boss.admin.domain.Module;
import cc.newex.dax.boss.admin.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 后台系统模块表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class ModuleServiceImpl
        extends AbstractCrudService<ModuleRepository, Module, ModuleExample, Integer>
        implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepos;

    private static final String SYSTEM_MODULE_ID = "0";

    @Override
    protected ModuleExample getPageExample(final String fieldName, final String keyword) {
        final ModuleExample example = new ModuleExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int add(final Module record) {
        this.dao.insert(record);
        this.updateHasChild(record.getParentId(), true);
        final String path = this.getPath(record.getParentId(), record.getId());
        return this.updatePath(record.getId(), path);
    }

    @Override
    public List<Module> getByPage(final PageInfo pageInfo, final Integer pid) {
        final ModuleExample example = new ModuleExample();
        example.or().andParentIdEqualTo(pid);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<Module> getModules(final String moduleIds) {
        if (StringUtils.isBlank(moduleIds)) {
            return new ArrayList<>();
        }

        final List<Module> moduleList = this.getAll();
        // 0表示返回所有系统模块
        if (SYSTEM_MODULE_ID.equals(moduleIds)) {
            return moduleList;
        }
        final Map<Integer, Module> moduleMap = new HashMap<>(moduleList.size());
        for (final Module module : moduleList) {
            moduleMap.put(module.getId(), module);
        }
        final String[] ids = StringUtils.split(moduleIds, ',');
        final List<Module> modules = new ArrayList<>(ids.length);
        for (final String id : ids) {
            final Integer mId = Integer.valueOf(id);
            if (moduleMap.containsKey(mId)) {
                modules.add(moduleMap.get(mId));
            }
        }

        return modules;
    }

    @Override
    public List<Module> getChildren(final int id) {
        final ModuleExample example = new ModuleExample();
        example.or().andParentIdEqualTo(id);
        return this.moduleRepos.selectByExample(example);
    }

    @Override
    public boolean hasChildren(final int id) {
        final ModuleExample example = new ModuleExample();
        example.or().andParentIdEqualTo(id);
        return this.moduleRepos.countByExample(example) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(final int id, final int pid) {
        this.moduleRepos.deleteById(id);
        final boolean hasChild = this.hasChildren(pid);
        return this.updateHasChild(pid, hasChild) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void move(final int sourceId, final int targetId, final int sourcePid, final String sourcePath) {
        // 修改source节点的pid与path，hasChild值
        this.updateParentId(sourceId, targetId);
        this.updateHasChild(targetId, true);
        this.moduleRepos.updatePath(sourcePath, this.getPath(targetId, sourceId));
        // 修改source节点的父节点hasChild值
        this.updateHasChild(sourcePid, this.hasChildren(sourcePid));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Module paste(final int sourceId, final int targetId) {
        final Module module = this.moduleRepos.selectById(sourceId);
        final int count = this.count(targetId, module.getName());
        if (count > 0) {
            module.setName(String.format("%s_复件%s", module.getName(), count));
        }
        module.setParentId(targetId);
        this.moduleRepos.insert(module);
        this.updateHasChild(targetId, true);
        return module;
    }

    @Override
    public void rebuildPathById(final int id) {
        final List<Module> modules = this.getChildren(id);
        for (final Module entity : modules) {
            final String path = this.getPath(entity.getParentId(), entity.getId());
            this.updateHasChild(entity.getParentId(), path.split(",").length > 1);
            this.updatePath(entity.getId(), path);
            this.rebuildPathById(entity.getId());
        }
    }

    @Override
    public void clone(final int sourceId, final int targetId) {
        final List<Module> modules = this.getChildren(sourceId);
        modules.stream().filter(child -> child.getId().equals(sourceId)).forEach(child -> {
            child.setParentId(targetId);
            final int srcPid = child.getId();
            this.moduleRepos.insert(child);
            final int newPid = child.getId();
            this.recursionClone(modules, newPid, srcPid);
        });
    }

    private void recursionClone(final List<Module> modules, int newPid, int srcPid) {
        for (final Module child : modules) {
            if (child.getParentId().equals(srcPid)) {
                child.setParentId(newPid);
                srcPid = child.getId();
                this.moduleRepos.insert(child);
                newPid = child.getId();
                this.recursionClone(modules, newPid, srcPid);
            }
        }
    }

    @Override
    public void rebuildAllPath() {
        final List<Module> modules = this.getAll();
        final List<Module> roots = modules.stream()
                .filter(module -> module.getParentId().equals(0))
                .collect(Collectors.toList());
        for (final Module root : roots) {
            root.setPath(root.getId().toString());
            this.rebuildPath(modules, root);
        }
        this.moduleRepos.batchUpdate(modules);
    }

    @Override
    public List<TreeNode<Module>> getModuleTree(final List<Module> modules, final Predicate<Module> predicate) {
        final List<TreeNode<Module>> roots = new ArrayList<>();
        modules.stream()
                .filter(predicate)
                .filter(module -> Objects.equals(module.getParentId(), 0))
                .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : -1)
                .forEach((Module module) -> this.addModuleTreeNode(roots, modules, module, predicate));
        return roots;
    }

    private void addModuleTreeNode(final List<TreeNode<Module>> children, final List<Module> modules,
                                   final Module module,
                                   final Predicate<Module> predicate) {
        final String cateId = Integer.toString(module.getId());
        final String pid = Integer.toString(module.getParentId());
        final String text = module.getName();
        final String state = module.getHasChild() > 0 ? "closed" : "open";
        final TreeNode<Module> parentNode =
                new TreeNode<>(cateId, pid, text, state, module.getIcon(), false, module);
        this.addChildModuleTreeNodes(modules, parentNode, predicate);
        children.add(parentNode);
    }

    private void addChildModuleTreeNodes(final List<Module> modules, final TreeNode<Module> parentNode,
                                         final Predicate<Module> predicate) {
        final Integer id = Integer.valueOf(parentNode.getId());
        modules.stream()
                .filter(predicate)
                .filter(module -> Objects.equals(module.getParentId(), id))
                .sorted((x, y) -> x.getSequence() > y.getSequence() ? 1 : 0)
                .forEach(module -> this.addModuleTreeNode(parentNode.getChildren(), modules, module, predicate));
    }

    private void rebuildPath(final List<Module> modules, final Module parent) {
        final List<Module> children = modules.stream()
                .filter(module -> module.getParentId().equals(parent.getId()))
                .collect(Collectors.toList());
        final int hasChild = CollectionUtils.isEmpty(children) ? 0 : 1;
        parent.setHasChild(hasChild);
        for (final Module child : children) {
            final String path = parent.getPath() + "," + child.getId().toString();
            child.setPath(path);
            this.rebuildPath(modules, child);
        }
    }

    private int count(final int parentId, final String name) {
        final ModuleExample example = new ModuleExample();
        example.or()
                .andParentIdEqualTo(parentId)
                .andNameEqualTo(name);
        return this.moduleRepos.countByExample(example);
    }

    private int updateHasChild(final int id, final boolean hasChild) {
        final Module module = Module.builder()
                .hasChild(hasChild ? 1 : 0)
                .id(id).build();
        return this.moduleRepos.updateById(module);
    }

    @Override
    public int updatePath(final Integer id, final String path) {
        final Module module = Module.builder().id(id).path(path).build();
        return this.moduleRepos.updateById(module);
    }

    private int updateParentId(final int sourceId, final int targetId) {
        final Module module = Module.builder().id(sourceId).parentId(targetId).build();
        return this.moduleRepos.updateById(module);
    }

    @Override
    public String getPath(final int pid, final int id) {
        if (pid <= 0) {
            return "" + id;
        }
        final Module po = this.moduleRepos.selectById(pid);
        return po.getPath() + "," + id;
    }
}