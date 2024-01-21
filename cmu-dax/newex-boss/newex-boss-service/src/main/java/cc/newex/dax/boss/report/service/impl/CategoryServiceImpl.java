package cc.newex.dax.boss.report.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.report.criteria.CategoryExample;
import cc.newex.dax.boss.report.data.CategoryRepository;
import cc.newex.dax.boss.report.domain.Category;
import cc.newex.dax.boss.report.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 报表类别表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class CategoryServiceImpl
        extends AbstractCrudService<CategoryRepository, Category, CategoryExample, Integer>
        implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepos;

    @Override
    protected CategoryExample getPageExample(final String fieldName, final String keyword) {
        final CategoryExample example = new CategoryExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(final Category record) {
        this.categoryRepos.insert(record);
        this.updateHasChild(record.getParentId(), true);
        return this.updatePath(record.getId(), this.getPath(record.getParentId(), record.getId()));
    }

    @Override
    public List<Category> getChildren(final int id) {
        final CategoryExample example = new CategoryExample();
        example.or().andParentIdEqualTo(id);
        return this.categoryRepos.selectByExample(example);
    }

    @Override
    public boolean hasChildren(final int id) {
        final CategoryExample example = new CategoryExample();
        example.or().andParentIdEqualTo(id);
        return this.exists(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(final int id, final int pid) {
        this.categoryRepos.deleteById(id);
        return this.updateHasChild(pid, this.hasChildren(pid)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void move(final int sourceId, final int targetId, final int sourcePid, final String sourcePath) {
        // 修改source节点的pid与path，hasChild值
        this.updateParentId(sourceId, targetId);
        this.updateHasChild(targetId, true);
        this.categoryRepos.updatePath(sourcePath, this.getPath(targetId, sourceId));
        // 修改source节点的父节点hasChild值
        this.updateHasChild(sourcePid, this.hasChildren(sourcePid));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Category paste(final int sourceId, final int targetId) {
        final Category category = this.categoryRepos.selectById(sourceId);
        final int count = this.count(targetId, category.getName());
        if (count > 0) {
            category.setName(String.format("%s_复件%s", category.getName(), count));
        }
        category.setParentId(targetId);
        this.categoryRepos.insert(category);
        this.updateHasChild(targetId, true);
        return category;
    }

    @Override
    public void clone(final int sourceId, final int targetId) {
        final List<Category> categories = this.getChildren(sourceId);
        categories.stream().filter(child -> child.getId().equals(sourceId)).forEach(child -> {
            child.setParentId(targetId);
            final int srcPid = child.getId();
            this.categoryRepos.insert(child);
            final int newPid = child.getId();
            this.recursionClone(categories, newPid, srcPid);
        });
    }

    private void recursionClone(final List<Category> categories, int newPid, int srcPid) {
        for (final Category child : categories) {
            if (child.getParentId().equals(srcPid)) {
                child.setParentId(newPid);
                srcPid = child.getId();
                this.categoryRepos.insert(child);
                newPid = child.getId();
                this.recursionClone(categories, newPid, srcPid);
            }
        }
    }

    @Override
    public void rebuildPathById(final int id) {
        final List<Category> categories = this.getChildren(id);
        for (final Category entity : categories) {
            final String path = this.getPath(entity.getParentId(), entity.getId());
            this.updateHasChild(entity.getParentId(), path.split(",").length > 1);
            this.updatePath(entity.getId(), path);
            this.rebuildPathById(entity.getId());
        }
    }

    @Override
    public void rebuildAllPath() {
        final List<Category> categories = this.getAll();
        final List<Category> roots = categories.stream()
                .filter(cate -> cate.getParentId().equals(0))
                .collect(Collectors.toList());
        for (final Category root : roots) {
            root.setPath(root.getId().toString());
            this.rebuildPath(categories, root);
        }
        this.categoryRepos.batchUpdate(categories);
    }

    private void rebuildPath(final List<Category> entities, final Category parent) {
        final List<Category> children = entities.stream()
                .filter(cate -> cate.getParentId().equals(parent.getId()))
                .collect(Collectors.toList());
        final int hasChild = CollectionUtils.isEmpty(children) ? 0 : 1;
        parent.setHasChild(hasChild);
        for (final Category child : children) {
            final String path = parent.getPath() + "," + child.getId().toString();
            child.setPath(path);
            this.rebuildPath(entities, child);
        }
    }

    private int count(final int parentId, final String name) {
        final CategoryExample example = new CategoryExample();
        example.or()
                .andParentIdEqualTo(parentId)
                .andNameEqualTo(name);
        return this.categoryRepos.countByExample(example);
    }

    private int updateHasChild(final int id, final boolean hasChild) {
        final Category category = Category.builder().id(id).hasChild(hasChild ? 1 : 0).build();
        return this.categoryRepos.updateById(category);
    }

    private int updateParentId(final int sourceId, final int targetId) {
        final Category category = Category.builder().id(sourceId).parentId(targetId).build();
        return this.categoryRepos.updateById(category);
    }

    private int updatePath(final Integer id, final String path) {
        final Category category = Category.builder().id(id).path(path).build();
        return this.categoryRepos.updateById(category);
    }

    private String getPath(final int pid, final int id) {
        if (pid <= 0) {
            return "" + id;
        }
        final Category po = this.categoryRepos.selectById(pid);
        return po.getPath() + "," + id;
    }
}