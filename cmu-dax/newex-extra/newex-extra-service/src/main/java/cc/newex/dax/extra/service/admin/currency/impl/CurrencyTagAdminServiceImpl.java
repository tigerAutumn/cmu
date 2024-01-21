package cc.newex.dax.extra.service.admin.currency.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.currency.CurrencyTagExample;
import cc.newex.dax.extra.data.currency.CurrencyTagRepository;
import cc.newex.dax.extra.domain.currency.CurrencyTag;
import cc.newex.dax.extra.dto.currency.CurrencyTagDTO;
import cc.newex.dax.extra.service.admin.currency.CurrencyTagAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 标签表 服务实现
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Slf4j
@Service
public class CurrencyTagAdminServiceImpl
        extends AbstractCrudService<CurrencyTagRepository, CurrencyTag, CurrencyTagExample, Long>
        implements CurrencyTagAdminService {

    @Autowired
    private CurrencyTagRepository currencyTagRepository;

    @Override
    protected CurrencyTagExample getPageExample(final String fieldName, final String keyword) {
        final CurrencyTagExample example = new CurrencyTagExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchSaveOrUpdateCurrencyTag(final List<CurrencyTagDTO> currencyTagDTOList) {
        final ModelMapper mapper = new ModelMapper();

        final List<CurrencyTag> saveList = new ArrayList<>();

        final Set<String> codeSet = new HashSet<>();

        if (CollectionUtils.isNotEmpty(currencyTagDTOList)) {
            currencyTagDTOList.stream().forEach(currencyTagDTO -> {
                final CurrencyTag currencyTag = mapper.map(currencyTagDTO, CurrencyTag.class);
                currencyTag.setId(null);

                final String code = currencyTag.getCode();
                final String tagInfoCode = currencyTag.getTagInfoCode();

                codeSet.add(code);

                if (StringUtils.isNotBlank(tagInfoCode)) {
                    saveList.add(currencyTag);
                }

            });
        }

        // 删除原来的数据
        codeSet.stream().forEach(code -> this.removeByCode(code));

        if (CollectionUtils.isNotEmpty(saveList)) {
            final int save = this.currencyTagRepository.batchInsert(saveList);

            return save;
        }

        return 0;
    }

    @Override
    public int removeByCode(final String code) {
        if (StringUtils.isBlank(code)) {
            return 0;
        }

        final CurrencyTagExample example = new CurrencyTagExample();
        final CurrencyTagExample.Criteria criteria = example.createCriteria();

        criteria.andCodeEqualTo(code);

        return this.currencyTagRepository.deleteByExample(example);
    }

    @Override
    public List<CurrencyTag> getByCode(final String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        final CurrencyTagExample example = new CurrencyTagExample();
        final CurrencyTagExample.Criteria criteria = example.createCriteria();

        criteria.andCodeEqualTo(code);

        return this.currencyTagRepository.selectByExample(example);
    }

    @Override
    public int countByTagInfo(final String tagInfoCode) {
        if (StringUtils.isBlank(tagInfoCode)) {
            return 0;
        }

        final CurrencyTagExample example = new CurrencyTagExample();
        final CurrencyTagExample.Criteria criteria = example.createCriteria();

        criteria.andTagInfoCodeEqualTo(tagInfoCode);

        return this.currencyTagRepository.countByExample(example);
    }

}
