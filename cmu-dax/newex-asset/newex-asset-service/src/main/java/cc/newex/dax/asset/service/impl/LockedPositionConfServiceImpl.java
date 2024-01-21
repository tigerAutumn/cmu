package cc.newex.dax.asset.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.criteria.LockedPositionConfExample;
import cc.newex.dax.asset.dao.LockedPositionConfRepository;
import cc.newex.dax.asset.domain.LockedPositionConf;
import cc.newex.dax.asset.domain.PageBossEntity;
import cc.newex.dax.asset.dto.LockedPositionConfDto;
import cc.newex.dax.asset.service.LockedPositionConfService;
import cc.newex.wallet.currency.CurrencyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 锁仓配置表 服务实现
 *
 * @author newex-team
 * @date 2018-07-04
 */
@Slf4j
@Service
public class LockedPositionConfServiceImpl
        extends AbstractCrudService<LockedPositionConfRepository, LockedPositionConf, LockedPositionConfExample, Long>
        implements LockedPositionConfService {


    @Override
    protected LockedPositionConfExample getPageExample(final String fieldName, final String keyword) {
        final LockedPositionConfExample example = new LockedPositionConfExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public ResponseResult getConfigListPage(DataGridPager pager) {
        LockedPositionConfExample example = new LockedPositionConfExample();
        example.setOrderByClause(pager.getSort() + " " + pager.getOrder());
        PageBossEntity pageBossEntity = PageBossEntity.getPage(this, pager.getPage(), pager.getRows(), example);
        List<LockedPositionConf> confs = pageBossEntity.getRows();
        List<LockedPositionConfDto> newData = confs.stream().map((record) -> {
            LockedPositionConfDto dto = new LockedPositionConfDto();
            BeanUtils.copyProperties(record, dto);
            dto.setAmount(record.getAmount().toPlainString());
            dto.setCurrencyName(CurrencyEnum.parseValue(record.getCurrency()).getName().toUpperCase());
            return dto;
        }).collect(Collectors.toList());
        pageBossEntity.setRows(newData);
        return ResultUtils.success(pageBossEntity);
    }
}