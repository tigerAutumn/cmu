package cc.newex.dax.extra.service.admin.currency.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.currency.ProductTagExample;
import cc.newex.dax.extra.data.currency.ProductTagRepository;
import cc.newex.dax.extra.domain.currency.ProductTag;
import cc.newex.dax.extra.dto.currency.ProductTagDTO;
import cc.newex.dax.extra.service.admin.currency.ProductTagAdminService;
import cc.newex.dax.spot.client.SpotCurrencyPairServiceClient;
import cc.newex.dax.spot.dto.ccex.CurrencyPairDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 币对标签表 服务实现
 *
 * @author newex-team
 * @date 2018-09-26 12:07:36
 */
@Slf4j
@Service
public class ProductTagAdminServiceImpl
        extends AbstractCrudService<ProductTagRepository, ProductTag, ProductTagExample, Long>
        implements ProductTagAdminService {

    @Autowired
    private ProductTagRepository productTagRepository;

    @Autowired
    private SpotCurrencyPairServiceClient spotCurrencyPairServiceClient;

    @Override
    protected ProductTagExample getPageExample(final String fieldName, final String keyword) {
        final ProductTagExample example = new ProductTagExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSaveOrUpdateProductTag(final List<ProductTagDTO> productTagDTOList) {
        final ModelMapper mapper = new ModelMapper();

        final List<ProductTag> saveList = new ArrayList<>();

        final Map<Integer, Set<String>> codeMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(productTagDTOList)) {
            productTagDTOList.stream().forEach(productTagDTO -> {
                final ProductTag productTag = mapper.map(productTagDTO, ProductTag.class);
                productTag.setId(null);

                final String code = productTag.getProductCode();
                final String tagInfoCode = productTag.getTagInfoCode();
                final Integer type = productTag.getType();

                if (StringUtils.isNotBlank(tagInfoCode)) {
                    saveList.add(productTag);
                }

                if (Objects.nonNull(type)) {
                    Set<String> codeSet = codeMap.get(type);

                    if (codeSet == null) {
                        codeSet = new HashSet<>();

                        codeMap.put(type, codeSet);
                    }

                    codeSet.add(code);
                }

            });
        }

        // 删除原来的数据
        codeMap.forEach((type, codeSet) -> {
            codeSet.stream().forEach(code -> this.removeByCode(code, type));
        });

        if (CollectionUtils.isNotEmpty(saveList)) {
            final int save = this.productTagRepository.batchInsert(saveList);

            return save;
        }

        return 0;
    }

    @Override
    public int removeByCode(final String code, final Integer type) {
        if (StringUtils.isBlank(code)) {
            return 0;
        }

        final ProductTagExample example = new ProductTagExample();
        final ProductTagExample.Criteria criteria = example.createCriteria();

        criteria.andProductCodeEqualTo(code);

        if (Objects.nonNull(type)) {
            criteria.andTypeEqualTo(type);
        }

        return this.productTagRepository.deleteByExample(example);
    }

    @Override
    public List<ProductTag> getByCode(final String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        final ProductTagExample example = new ProductTagExample();
        final ProductTagExample.Criteria criteria = example.createCriteria();

        criteria.andProductCodeEqualTo(code);

        return this.productTagRepository.selectByExample(example);
    }

    @Override
    public List<ProductTag> getByTagInfo(final List<String> tagInfoCodeList) {
        if (CollectionUtils.isEmpty(tagInfoCodeList)) {
            return null;
        }

        final ProductTagExample example = new ProductTagExample();
        final ProductTagExample.Criteria criteria = example.createCriteria();

        criteria.andTagInfoCodeIn(tagInfoCodeList);

        return this.productTagRepository.selectByExample(example);
    }

    @Override
    public int countByTagInfo(final String tagInfoCode) {
        if (StringUtils.isBlank(tagInfoCode)) {
            return 0;
        }

        final ProductTagExample example = new ProductTagExample();
        final ProductTagExample.Criteria criteria = example.createCriteria();

        criteria.andTagInfoCodeEqualTo(tagInfoCode);

        return this.productTagRepository.countByExample(example);
    }

    @Override
    public int countByTagInfo(final String tagInfoCode, final Byte online, final Integer zone) {
        if (StringUtils.isBlank(tagInfoCode)) {
            return 0;
        }

        if (online == null) {
            return this.countByTagInfo(tagInfoCode);
        }

        final ProductTagExample example = new ProductTagExample();
        final ProductTagExample.Criteria criteria = example.createCriteria();

        criteria.andTagInfoCodeEqualTo(tagInfoCode);

        final List<ProductTag> productTagList = this.productTagRepository.selectByExample(example);

        if (CollectionUtils.isEmpty(productTagList)) {
            return 0;
        }

        // 根据online过滤
        int count = 0;

        for (final ProductTag productTag : productTagList) {
            final Long productId = productTag.getProductId();

            final CurrencyPairDTO currencyPairDTO = this.spotCurrencyPairServiceClient.getCurrencyPair(productId.intValue()).getData();

            if (currencyPairDTO != null && currencyPairDTO.getOnline().equals(online) && currencyPairDTO.getZone().intValue()==zone.intValue()) {
                count++;
            }
        }

        return count;
    }

}