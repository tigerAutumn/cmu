package cc.newex.dax.users.service.subaccount.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.subaccount.TransferRecordExample;
import cc.newex.dax.users.data.subaccount.TransferRecordRepository;
import cc.newex.dax.users.domain.subaccount.TransferRecord;
import cc.newex.dax.users.service.subaccount.TransferRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 母子账户划转记录表 服务实现
 *
 * @author newex-team
 * @date 2018-11-05 17:21:17
 */
@Slf4j
@Service
public class TransferRecordServiceImpl extends AbstractCrudService<TransferRecordRepository, TransferRecord, TransferRecordExample, Long> implements TransferRecordService {
    @Autowired
    private TransferRecordRepository transferRecordRepository;

    @Override
    protected TransferRecordExample getPageExample(final String fieldName, final String keyword) {
        final TransferRecordExample example = new TransferRecordExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}