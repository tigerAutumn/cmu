package cc.newex.dax.extra.service.admin.currency.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.currency.TagCategoryExample;
import cc.newex.dax.extra.data.currency.CurrencyTagRepository;
import cc.newex.dax.extra.data.currency.ProductTagRepository;
import cc.newex.dax.extra.data.currency.TagCategoryRepository;
import cc.newex.dax.extra.domain.currency.TagCategory;
import cc.newex.dax.extra.domain.currency.TagInfo;
import cc.newex.dax.extra.dto.common.TreeNodeDTO;
import cc.newex.dax.extra.service.admin.currency.TagCategoryAdminService;
import cc.newex.dax.extra.service.admin.currency.TagInfoAdminService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 标签表 服务实现
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Slf4j
@Service
public class TagCategoryAdminServiceImpl
        extends AbstractCrudService<TagCategoryRepository, TagCategory, TagCategoryExample, Long>
        implements TagCategoryAdminService {

    @Autowired
    private TagCategoryRepository tagCategoryRepository;

    @Autowired
    private TagInfoAdminService tagInfoAdminService;


    @Override
    protected TagCategoryExample getPageExample(final String fieldName, final String keyword) {
        final TagCategoryExample example = new TagCategoryExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public List<TreeNodeDTO> listTreeNode(final String locale, final Integer type) {
        final List<TagCategory> tagCategoryList = this.getByLocale(locale, type);

        if (CollectionUtils.isEmpty(tagCategoryList)) {
            return null;
        }

        final List<TreeNodeDTO> treeNodeList = new ArrayList<>();

        for (final TagCategory tagCategory : tagCategoryList) {
            final TreeNodeDTO treeNode = this.createTreeNode(tagCategory);
            final List<TreeNodeDTO> children = this.createChildrenTreeNode(tagCategory);

            treeNode.setChildren(children);

            treeNodeList.add(treeNode);
        }

        return treeNodeList;
    }

    private TreeNodeDTO createTreeNode(final TagCategory tagCategory) {
        final String id = tagCategory.getCode();
        final String pid = "";
        final String text = tagCategory.getName();

        final TreeNodeDTO treeNode = TreeNodeDTO.builder()
                .id(id)
                .pid(pid)
                .text(text)
                .build();

        return treeNode;
    }

    private List<TreeNodeDTO> createChildrenTreeNode(final TagCategory tagCategory) {
        final String code = tagCategory.getCode();
        final String locale = tagCategory.getLocale();

        final List<TagInfo> tagInfoList = this.tagInfoAdminService.getByTagCategoryCode(code, locale);

        final List<TreeNodeDTO> treeNodeList = this.createTreeNode(tagInfoList);

        return treeNodeList;
    }

    private TreeNodeDTO createTreeNode(final TagInfo tagInfo) {
        final String id = tagInfo.getCode();
        final String pid = tagInfo.getTagCategoryCode();
        final String text = tagInfo.getName();

        final TreeNodeDTO treeNode = TreeNodeDTO.builder()
                .id(id)
                .pid(pid)
                .text(text)
                .build();

        return treeNode;
    }

    private List<TreeNodeDTO> createTreeNode(final List<TagInfo> tagInfoList) {
        if (CollectionUtils.isEmpty(tagInfoList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        final List<TreeNodeDTO> treeNodeList = tagInfoList.stream().map(tagInfo -> this.createTreeNode(tagInfo)).collect(Collectors.toList());

        return treeNodeList;
    }

    @Override
    public List<TagCategory> getByLocale(final String locale, final Integer type) {
        if (StringUtils.isBlank(locale)) {
            return null;
        }

        final TagCategoryExample example = new TagCategoryExample();
        final TagCategoryExample.Criteria criteria = example.createCriteria();

        criteria.andLocaleEqualTo(locale);

        if (Objects.nonNull(type)) {
            criteria.andTypeEqualTo(type);
        }
        example.setOrderByClause("sort");

        return this.tagCategoryRepository.selectByExample(example);
    }

    @Override
    public List<TagCategory> getByCode(final String code, final String locale) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        final TagCategoryExample example = new TagCategoryExample();
        final TagCategoryExample.Criteria criteria = example.createCriteria();

        criteria.andCodeEqualTo(code);

        if (StringUtils.isNotBlank(locale)) {
            criteria.andLocaleEqualTo(locale);
        }

        return this.tagCategoryRepository.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeTagCategoryById(final Long id, final String tagCategoryCode) {

        final List<TagCategory> list = this.getByCode(tagCategoryCode,null);
        if(CollectionUtils.isNotEmpty(list) && list.size() == 1 && id.intValue() == list.get(0).getId().longValue()){
            final List<TagInfo> tagInfoList = this.tagInfoAdminService.getByTagCategoryCode(tagCategoryCode,null);
            if(CollectionUtils.isNotEmpty(tagInfoList)){
                tagInfoList.forEach(info->{
                    this.tagInfoAdminService.removeTagInfoById(info.getId(),info.getCode());
                });
            }
        }
        return tagCategoryRepository.deleteById(id);
    }
}
