package cc.newex.dax.asset.service.impl;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.asset.common.enums.TransferAuditStatus;
import cc.newex.dax.asset.criteria.TransferRecordAuditExample;
import cc.newex.dax.asset.dao.TransferRecordAuditRepository;
import cc.newex.dax.asset.domain.TransferRecordAudit;
import cc.newex.dax.asset.dto.TransferRecordAuditResDto;
import cc.newex.dax.asset.service.TransferRecordAuditService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author newex-team
 * @date 2019-04-23 18:58:22
 */
@Slf4j
@Service
public class TransferRecordAuditServiceImpl extends AbstractCrudService<TransferRecordAuditRepository, TransferRecordAudit, TransferRecordAuditExample, Long> implements TransferRecordAuditService {
    @Autowired
    private TransferRecordAuditRepository transferRecordAuditRepository;

    @Override
    protected TransferRecordAuditExample getPageExample(final String fieldName, final String keyword) {
        final TransferRecordAuditExample example = new TransferRecordAuditExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public TransferRecordAudit getByTradeNo(String tradeNo) {
        TransferRecordAuditExample example = new TransferRecordAuditExample();
        example.createCriteria().andTraderNoEqualTo(tradeNo);
        return this.getOneByExample(example);
    }

    @Override
    public List<TransferRecordAudit> getAuditSuccess() {
        TransferRecordAuditExample example = new TransferRecordAuditExample();
        example.createCriteria()
                .andStatusEqualTo((byte) TransferAuditStatus.SUCCEED.getCode());
        return this.getByExample(example);
    }

    @Override
    public List<TransferRecordAudit> listByPage(PageInfo pageInfo, Integer stauts,String tradeNo, Integer brokerId) {
        TransferRecordAuditExample transferRecordAuditExample=new TransferRecordAuditExample();
        TransferRecordAuditExample.Criteria criteria = transferRecordAuditExample.createCriteria();
        if (!ObjectUtils.isEmpty(brokerId) && NumberUtil.gt(brokerId, 0)) {
            criteria.andBrokerIdEqualTo(brokerId);
        }
        if (StringUtils.isNotEmpty(tradeNo)) {
            criteria.andTraderNoLike("%" + tradeNo + "%");
        }
        if (!ObjectUtils.isEmpty(stauts)) {
            criteria.andStatusEqualTo(stauts.byteValue());
        }
        return this.getByPage(pageInfo, transferRecordAuditExample);
    }
}