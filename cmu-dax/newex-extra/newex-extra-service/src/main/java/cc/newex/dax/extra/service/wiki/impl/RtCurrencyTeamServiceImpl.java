package cc.newex.dax.extra.service.wiki.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyTeamExample;
import cc.newex.dax.extra.data.wiki.RtCurrencyTeamRepository;
import cc.newex.dax.extra.domain.wiki.RtCurrencyTeam;
import cc.newex.dax.extra.dto.wiki.RtCurrencyProjectInfoDTO;
import cc.newex.dax.extra.service.wiki.RtCurrencyTeamService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * rt代币团队信息 服务实现
 *
 * @author mbg.generated
 * @date 2018-11-28 14:55:25
 */
@Slf4j
@Service
public class RtCurrencyTeamServiceImpl extends AbstractCrudService<RtCurrencyTeamRepository, RtCurrencyTeam, RtCurrencyTeamExample, Long> implements RtCurrencyTeamService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RtCurrencyTeamRepository rtCurrencyTeamRepos;

    @Override
    protected RtCurrencyTeamExample getPageExample(final String fieldName, final String keyword) {
        final RtCurrencyTeamExample example = new RtCurrencyTeamExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * 获取Rt币种的团队信息
     *
     * @param cid 币种code
     * @return list
     */
    @Override
    public List<RtCurrencyProjectInfoDTO.TeamDTO> listRtTeamByCid(final String cid) {

        final RtCurrencyTeamExample example = new RtCurrencyTeamExample();
        final RtCurrencyTeamExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(cid);

        return Optional.ofNullable(this.getByExample(example))
                .map(itemList -> itemList.stream()
                        .map(item -> this.modelMapper.map(item, RtCurrencyProjectInfoDTO.TeamDTO.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}