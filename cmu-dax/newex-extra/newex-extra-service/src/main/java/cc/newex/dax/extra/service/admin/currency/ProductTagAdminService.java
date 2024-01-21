package cc.newex.dax.extra.service.admin.currency;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.currency.ProductTagExample;
import cc.newex.dax.extra.domain.currency.ProductTag;
import cc.newex.dax.extra.dto.currency.ProductTagDTO;

import java.util.List;

/**
 * 币对标签表 服务接口
 *
 * @author newex-team
 * @date 2018-09-26 12:07:36
 */
public interface ProductTagAdminService extends CrudService<ProductTag, ProductTagExample, Long> {

    int batchSaveOrUpdateProductTag(final List<ProductTagDTO> productTagDTOList);

    int removeByCode(String code, Integer type);

    List<ProductTag> getByCode(String code);

    List<ProductTag> getByTagInfo(List<String> tagInfoCodeList);

    int countByTagInfo(String tagInfoCode);

    int countByTagInfo(String tagInfoCode, Byte online,Integer zone);

}