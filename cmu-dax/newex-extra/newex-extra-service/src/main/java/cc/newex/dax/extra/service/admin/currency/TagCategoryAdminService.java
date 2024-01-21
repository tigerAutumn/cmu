package cc.newex.dax.extra.service.admin.currency;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.currency.TagCategoryExample;
import cc.newex.dax.extra.domain.currency.TagCategory;
import cc.newex.dax.extra.dto.common.TreeNodeDTO;

import java.util.List;

/**
 * 标签分类表 服务接口
 *
 * @author liutiejun
 * @date 2018-07-19
 */
public interface TagCategoryAdminService extends CrudService<TagCategory, TagCategoryExample, Long> {

    List<TreeNodeDTO> listTreeNode(String locale, Integer type);

    List<TagCategory> getByLocale(String locale, Integer type);

    List<TagCategory> getByCode(String code, String locale);

    int removeTagCategoryById(Long id,String code);

}

