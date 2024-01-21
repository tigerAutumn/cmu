package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.UserFeeExample;
import cc.newex.dax.perpetual.data.UserFeeRepository;
import cc.newex.dax.perpetual.domain.Fee;
import cc.newex.dax.perpetual.domain.UserFee;
import cc.newex.dax.perpetual.dto.UserFeeDTO;
import cc.newex.dax.perpetual.service.FeeService;
import cc.newex.dax.perpetual.service.UserFeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户手续费配置表 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:30:43
 */
@Slf4j
@Service
public class UserFeeServiceImpl extends AbstractCrudService<UserFeeRepository, UserFee, UserFeeExample, Long> implements UserFeeService {
    @Autowired
    private UserFeeRepository userFeeRepository;
    @Autowired
    private FeeService feeService;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    protected UserFeeExample getPageExample(final String fieldName, final String keyword) {
        final UserFeeExample example = new UserFeeExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }


    @Override
    public List<UserFee> getList(final String pairCode) {
        final UserFeeExample example = this.getPageExample("pair_code", pairCode);
        return this.userFeeRepository.selectByExample(example);
    }


    @Override
    public BigDecimal getRate(final String pairCode) {

        BigDecimal rate = BigDecimal.ZERO;
        final List<UserFee> userFeeList = this.getList(pairCode);
        if (CollectionUtils.isEmpty(userFeeList)) {
            final UserFee userFee = userFeeList.stream().max(Comparator.comparing(x -> x.getRate().abs())).get();
            rate = userFee.getRate();
            return rate;
        }
        final List<Fee> feeList = this.feeService.getList(pairCode);
        if (CollectionUtils.isEmpty(userFeeList)) {
            final Fee fee = feeList.stream().max(Comparator.comparing(x -> x.getRate().abs())).get();
            rate = fee.getRate();
        }

        return rate;
    }

    /**
     * List user fee by condition list.
     *
     * @param pageInfo       the page info
     * @param queryParameter the query parameter
     * @return the list
     */
    @Override
    public List<UserFeeDTO> listUserFeeByCondition(PageInfo pageInfo, UserFeeDTO queryParameter) {

        UserFeeExample example = new UserFeeExample();
        UserFeeExample.Criteria criteria = example.createCriteria();

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
                        .map(item -> modelMapper.map(item, UserFeeDTO.class)).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}