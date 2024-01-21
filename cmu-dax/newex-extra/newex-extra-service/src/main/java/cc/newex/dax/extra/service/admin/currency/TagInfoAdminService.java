package cc.newex.dax.extra.service.admin.currency;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.currency.TagInfoExample;
import cc.newex.dax.extra.domain.currency.TagInfo;

import java.util.List;

/**
 * 标签表 服务接口
 *
 * @author liutiejun
 * @date 2018-07-19
 */
public interface TagInfoAdminService extends CrudService<TagInfo, TagInfoExample, Long> {

    List<TagInfo> getByTagCategoryCode(String tagCategoryCode, String locale);

    List<TagInfo> getByTagCode(String tagCode, String locale);

    int removeTagInfoById(Long id,String code);

}

