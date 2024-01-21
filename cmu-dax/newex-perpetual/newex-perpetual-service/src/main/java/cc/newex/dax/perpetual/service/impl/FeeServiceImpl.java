package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.FeeExample;
import cc.newex.dax.perpetual.data.FeeRepository;
import cc.newex.dax.perpetual.domain.Fee;
import cc.newex.dax.perpetual.dto.FeeDTO;
import cc.newex.dax.perpetual.service.FeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 平台手续费配置表 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:30:37
 */
@Slf4j
@Service
public class FeeServiceImpl extends AbstractCrudService<FeeRepository, Fee, FeeExample, Long> implements FeeService {

    @Autowired
    private FeeRepository feeRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    protected FeeExample getPageExample(final String fieldName, final String keyword) {
        final FeeExample example = new FeeExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }


    @Override
    public List<Fee> getList(final String pairCode) {
        /**
         * 获取系统手续费列表
         *
         * @param pairCode
         */
        final FeeExample example = this.getPageExample("pair_code", pairCode);
        return this.feeRepository.selectByExample(example);
    }

    /**
     * List fee by condition list.
     *
     * @param pageInfo       the page info
     * @param queryParameter the query parameter
     * @return the list
     */
    @Override
    public List<FeeDTO> listFeeByCondition(PageInfo pageInfo, FeeDTO queryParameter) {

        FeeExample example = new FeeExample();
        FeeExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(queryParameter.getPairCode())) {
            criteria.andPairCodeEqualTo(queryParameter.getPairCode());
        }
        if (Objects.nonNull(queryParameter.getBrokerId())) {
            criteria.andBrokerIdEqualTo(queryParameter.getBrokerId());
        }
        if (Objects.nonNull(queryParameter.getSide())) {
            criteria.andSideEqualTo(queryParameter.getSide());
        }

        return Optional.ofNullable(this.getByPage(pageInfo, example))
                .map(itemList -> itemList.stream()
                        .map(item -> modelMapper.map(item, FeeDTO.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
